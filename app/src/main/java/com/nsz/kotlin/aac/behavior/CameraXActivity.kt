package com.nsz.kotlin.aac.behavior

import android.Manifest
import android.content.ContentValues
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.video.VideoCapture
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.nsz.kotlin.PermissionsActivity
import com.nsz.kotlin.databinding.ActivityAacBehaviorCameraXBinding
import com.nsz.kotlin.ux.common.CommonLog
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CameraXActivity : PermissionsActivity() {

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    private lateinit var binding: ActivityAacBehaviorCameraXBinding
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private var imageCapture: ImageCapture ? = null
    private var cameraProvider: ProcessCameraProvider ? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private var recording: Recording ? = null
    private var videoCapture: VideoCapture<Recorder> ? = null

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityAacBehaviorCameraXBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.switchBtn.setOnClickListener {
            lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                CameraSelector.LENS_FACING_FRONT
            } else {
                CameraSelector.LENS_FACING_BACK
            }
            setUpCamera()
        }
        binding.takePhotoBtn.setOnClickListener { takePhoto() }
        binding.startVideoBtn.setOnClickListener { captureVideo() }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Rebind the camera with the updated display metrics
        bindCameraUseCases()

        // Enable or disable switching between cameras
        updateCameraSwitchButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Shut down our background executor
        cameraExecutor.shutdown()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            setUpCamera()
        } else {
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
        }
    }

    /** Initialize CameraX, and prepare to bind the camera use cases  */
    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        val listener = Runnable {
            // CameraProvider
            cameraProvider = cameraProviderFuture.get()

            // Select lensFacing depending on the available cameras
            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            // Enable or disable switching between cameras
            updateCameraSwitchButton()

            // Build and bind the camera use cases
            bindCameraUseCases()
        }

        val executor = ContextCompat.getMainExecutor(this)
        cameraProviderFuture.addListener(listener, executor)
    }

    private fun bindCameraUseCases() {
        val metrics = windowManager.defaultDisplay
        val width = metrics.width
        val height = metrics.height
        CommonLog.e("Screen metrics:$width x $height")

        val screenAspectRatio = aspectRatio(width, height)
        CommonLog.e("Preview aspect ratio:$screenAspectRatio")

        val rotation = binding.previewView.display.rotation

        // CameraProvider
        val cameraProvider = cameraProvider ?: throw IllegalStateException("Camera initialization failed.")

        // CameraSelector
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        // Preview
        val preview = Preview.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation
            .setTargetRotation(rotation)
            .build()

        // ImageCapture
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            // We request aspect ratio but no resolution to match preview config, but letting
            // CameraX optimize for whatever specific resolution best fits our use cases
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation, we will have to call this again if rotation changes
            // during the lifecycle of this use case
            .setTargetRotation(rotation)
            .build()

        // ImageAnalysis
        // Select back camera as a default
        val analyzer = ImageAnalysis.Analyzer { image ->
            CommonLog.e("Average luminosity:$image")
            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val average = pixels.average()
            // listener(average)
            CommonLog.e("average:$average")
            image.close()
        }
        val imageAnalyzer = ImageAnalysis.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation, we will have to call this again if rotation changes
            // during the lifecycle of this use case
            .setTargetRotation(rotation)
            .build()
        imageAnalyzer.setAnalyzer(cameraExecutor, analyzer)

        // VideoCapture
        val quality = FallbackStrategy.higherQualityOrLowerThan(Quality.SD)
        val qualitySelector = QualitySelector.from(Quality.HIGHEST, quality)
        val recorder = Recorder.Builder()
            .setQualitySelector(qualitySelector)
            .build()
        videoCapture = VideoCapture.withOutput(recorder)

        // Must unbind the use-cases before rebinding them
        cameraProvider.unbindAll()

        try {
            // Bind use cases to camera
            val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, videoCapture)

            // Attach the viewfinder's surface provider to preview use case
            preview.setSurfaceProvider(binding.previewView.surfaceProvider)

            observeCameraState(camera.cameraInfo)
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val currentTimeMillis = System.currentTimeMillis()
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(currentTimeMillis)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has been taken
        val callback = object : ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = output.savedUri
                val msg = "Photo capture succeeded:$savedUri"
                CommonLog.d(msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        }
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), callback)
    }

    private fun captureVideo() {
        val videoCapture = videoCapture ?: return

        binding.startVideoBtn.isEnabled = false

        val curRecording = recording
        if (curRecording != null) {
            // Stop the current recording session.
            curRecording.stop()
            recording = null
            return
        }

        // create and start a new recording session
        val currentTimeMillis = System.currentTimeMillis()
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(currentTimeMillis)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()
        val prepareRecording = videoCapture.output.prepareRecording(this, mediaStoreOutputOptions)
        if (PermissionChecker.checkSelfPermission(this@CameraXActivity, Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED) {
            prepareRecording.withAudioEnabled()
        }
        val mainExecutor = ContextCompat.getMainExecutor(this)
        recording = prepareRecording.start(mainExecutor) { event ->
            when (event) {
                is VideoRecordEvent.Start -> {
                    CommonLog.e("VideoRecordEvent -> Start")
                    binding.startVideoBtn.text = "Stop Video"
                    binding.startVideoBtn.isEnabled = true
                }
                is VideoRecordEvent.Finalize -> {
                    CommonLog.e("VideoRecordEvent -> Finalize")
                    val error = event.error
                    val hasError = event.hasError()
                    if (hasError) {
                        val recording = recording
                        if (recording != null) {
                            recording.close()
                            CommonLog.e("Video capture ends with error:$error")
                        }
                    } else {
                        val outputUri = event.outputResults.outputUri
                        val msg = "Video capture succeeded:$outputUri"
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        CommonLog.e(msg)
                    }
                    binding.startVideoBtn.text = "Start Video"
                    binding.startVideoBtn.isEnabled = true
                }
            }
        }
    }

    private fun observeCameraState(cameraInfo: CameraInfo) {
        cameraInfo.cameraState.observe(this) { state ->
            val type = state.type
            val error = state.error
            CommonLog.e("cameraState:$type error:$error")
        }
    }

    /** Enabled or disabled a button to switch cameras depending on the available cameras */
    private fun updateCameraSwitchButton() {
        try {
            binding.switchBtn.isEnabled = hasBackCamera() && hasFrontCamera()
        } catch (exception: Exception) {
            exception.printStackTrace()
            binding.switchBtn.isEnabled = false
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        val bool = abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)
        if (bool) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun Quality.qualityToString(): String {
        return when (this) {
            Quality.UHD -> "UHD"
            Quality.FHD -> "FHD"
            Quality.HD -> "HD"
            Quality.SD -> "SD"
            else -> throw IllegalArgumentException()
        }
    }

    /** Returns true if the device has an available back camera. False otherwise */
    private fun hasBackCamera(): Boolean {
        val cameraProvider = cameraProvider
        return if (cameraProvider != null) {
            CommonLog.e("hasBackCamera")
            cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
        } else {
            false
        }
    }

    /** Returns true if the device has an available front camera. False otherwise */
    private fun hasFrontCamera(): Boolean {
        val cameraProvider = cameraProvider
        return if (cameraProvider != null) {
            CommonLog.e("hasFrontCamera")
            cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
        } else {
            false
        }
    }

}