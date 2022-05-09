package com.nsz.kotlin.aac.behavior

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.aac.architecture.room.RoomActivity
import com.nsz.kotlin.databinding.ActivityAacBehaviorBinding
import com.nsz.kotlin.ux.common.startActivity

class BehaviorActivity : AppCompatActivity() {

    private val binding: ActivityAacBehaviorBinding by lazy { ActivityAacBehaviorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.cameraXBtn.setOnClickListener { startActivity<RoomActivity>() }
    }

}