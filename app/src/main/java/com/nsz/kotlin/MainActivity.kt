package com.nsz.kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.demo.proto.LoginInfo
import com.nsz.kotlin.aac.AndroidArchitectureComponentsActivity
import com.nsz.kotlin.databinding.ActivityMainBinding
import com.nsz.kotlin.nfc.NFCActivity
import com.nsz.kotlin.open.source.OpenSourceActivity
import com.nsz.kotlin.storage.StorageActivity
import com.nsz.kotlin.thread.LaunchScopeActivity
import com.nsz.kotlin.ux.common.startActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        LoginInfo.Login.newBuilder().setAccount("01").setPassword("123456").build()
        binding.mbScopedStorage.setOnClickListener {
            val intent = Intent(this@MainActivity, StorageActivity::class.java)
            startActivity(intent)
        }
        binding.mbNfc.setOnClickListener { startActivity<NFCActivity>() }
        binding.mbThread.setOnClickListener { startActivity<LaunchScopeActivity>() }
        binding.mbOpenSource.setOnClickListener { startActivity<OpenSourceActivity>() }
        binding.mbAac.setOnClickListener { startActivity<AndroidArchitectureComponentsActivity>() }
    }

}