package com.nsz.kotlin.open.source

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.nsz.kotlin.databinding.ActivityOpenSourceUssdBinding


class UssdActivity : AppCompatActivity() {

    private val binding: ActivityOpenSourceUssdBinding by lazy { ActivityOpenSourceUssdBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbOk.setOnClickListener { sendUssd() }
        val encodedHash = Uri.encode("#")
        val ussd = "*130*3621*1146974863110807$encodedHash"
        val parse = Uri.parse("tel:$ussd")
        val intent = Intent("android.intent.action.CALL", parse)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent ? ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) return
        try {
            val bundle = intent.extras
            if (bundle != null) {
                val keySet = bundle.keySet()
                for (key in keySet) {
                    var temp: String
                    val obj = bundle[key]
                    temp = if (obj != null) {
                        val value = obj.toString()
                        "key = $key || value = $value"
                    } else {
                        "key = $key || value = null"
                    }
                    Log.e("ktx", temp)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sendUssd() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 100)
            return
        }
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mainLooper = Looper.getMainLooper()
            val handler = Handler(mainLooper)
            val callback = object : TelephonyManager.UssdResponseCallback() {

                override fun onReceiveUssdResponse(telephonyManager: TelephonyManager ?, request: String ?, response: CharSequence ? ) {
                    super.onReceiveUssdResponse(telephonyManager, request, response)
                    Log.e("ussd", "request: $request")
                    Log.e("ussd", "response: $response")
                    runOnUiThread { binding.contentText.text = "request: $request\nresponse: $response" }
                }

                override fun onReceiveUssdResponseFailed(telephonyManager: TelephonyManager ?, request: String ?, failureCode: Int) {
                    super.onReceiveUssdResponseFailed(telephonyManager, request, failureCode)
                    Log.e("ussd", "request: $request")
                    Log.e("ussd", "failureCode: $failureCode")
                    runOnUiThread { binding.contentText.text = "request: $request\nfailureCode: $failureCode" }
                }

            }
            telephonyManager.sendUssdRequest("*147#", callback, handler)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}