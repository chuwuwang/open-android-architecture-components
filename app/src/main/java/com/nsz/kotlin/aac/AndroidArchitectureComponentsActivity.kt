package com.nsz.kotlin.aac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.aac.architecture.ArchitectureActivity
import com.nsz.kotlin.aac.ui.UIActivity
import com.nsz.kotlin.databinding.ActivityAacBinding
import com.nsz.kotlin.ux.common.extension.startActivity

class AndroidArchitectureComponentsActivity : AppCompatActivity() {

    private val binding: ActivityAacBinding by lazy { ActivityAacBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbUi.setOnClickListener { startActivity<UIActivity>() }
        binding.mbBehavior.setOnClickListener {  }
        binding.mbArchitecture.setOnClickListener { startActivity<ArchitectureActivity>() }
        binding.mbFoundation.setOnClickListener {  }
    }

}