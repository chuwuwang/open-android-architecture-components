package com.nsz.kotlin.aac.ui.animation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityAacUiAnimationBinding
import com.nsz.kotlin.ux.common.startActivity

class AnimationActivity : AppCompatActivity() {

    private val binding: ActivityAacUiAnimationBinding by lazy { ActivityAacUiAnimationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbBounceInterpolator.setOnClickListener { startActivity<BounceInterpolatorActivity>() }
    }

}