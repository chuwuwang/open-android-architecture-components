package com.nsz.kotlin.aac.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.aac.ui.animation.AnimationActivity
import com.nsz.kotlin.aac.ui.layout.LayoutActivity
import com.nsz.kotlin.databinding.ActivityAacUiBinding
import com.nsz.kotlin.ux.common.extension.startActivity

class UIActivity : AppCompatActivity() {

    private val binding: ActivityAacUiBinding by lazy { ActivityAacUiBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbAnimation.setOnClickListener { startActivity<AnimationActivity>() }
        binding.mbTransitions.setOnClickListener {}
        binding.mbFragment.setOnClickListener {}
        binding.mbLayout.setOnClickListener { startActivity<LayoutActivity>() }
    }

}