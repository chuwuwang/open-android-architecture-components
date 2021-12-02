package com.nsz.kotlin.aac.ui.animation.interpolator

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.R
import com.nsz.kotlin.databinding.ActivityAacUiAnimationInterpolatorBinding

class BounceInterpolatorActivity : AppCompatActivity() {

    private val binding: ActivityAacUiAnimationInterpolatorBinding by lazy { ActivityAacUiAnimationInterpolatorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.button.setOnClickListener {
            val myAnim = AnimationUtils.loadAnimation(this, R.anim.interpolator_bounce)
            // Use bounce interpolator with amplitude 0.2 and frequency 20
            // val interpolator = BounceInterpolator(0.5, 12.0)
            val interpolator = BounceInterpolator()
            myAnim.interpolator = interpolator
            binding.button.startAnimation(myAnim)
        }
    }

}