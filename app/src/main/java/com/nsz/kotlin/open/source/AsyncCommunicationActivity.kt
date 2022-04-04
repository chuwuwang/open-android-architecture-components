package com.nsz.kotlin.open.source

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.R

class AsyncCommunicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source_async_communication)
        initView()
    }

    private fun initView() {
        HttpServerKernel.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        HttpServerKernel.stop()
    }

}