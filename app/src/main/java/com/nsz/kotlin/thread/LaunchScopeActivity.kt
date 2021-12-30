package com.nsz.kotlin.thread

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityLaunchScopeBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class LaunchScopeActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val binding: ActivityLaunchScopeBinding by lazy { ActivityLaunchScopeBinding.inflate(layoutInflater) }
    private val url = "https://th.bing.com/th/id/R.7d541c7a926e1453fb61576e308cfd7a?rik=ZlN4Gfd8hjJGHw&riu=http%3a%2f%2fup.deskcity.org%2fpic_source%2f7d%2f54%2f1c%2f7d541c7a926e1453fb61576e308cfd7a.jpg&ehk=vZ950PKjcMoEvEDNigd6u0zBPiJPh6syEBp9RP4Z1t4%3d&risl=&pid=ImgRaw&r=0"

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        launch(Dispatchers.Main) {
            val bitmap = getImage()
            val async1 = async {
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width / 2, bitmap.height / 2)
            }
            val async2 = async {
                Bitmap.createBitmap(bitmap, bitmap.width / 2, 0, bitmap.width / 2, bitmap.height / 2)
            }
            val async3 = async {
                Bitmap.createBitmap(bitmap, 0, bitmap.height / 2, bitmap.width / 2, bitmap.height / 2)
            }
            val async4 = async {
                Bitmap.createBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, bitmap.width / 2, bitmap.height / 2)
            }
            binding.iv1.setImageBitmap(async1.await())
            binding.iv2.setImageBitmap(async2.await())
            binding.iv3.setImageBitmap(async3.await())
            binding.iv4.setImageBitmap(async4.await())
        }
    }

    private suspend fun getImage(): Bitmap = withContext(Dispatchers.IO) {
        val build = Request.Builder().url(url).get().build()
        OkHttpClient().newCall(build).execute().body?.byteStream().use { BitmapFactory.decodeStream(it) }
    }

}