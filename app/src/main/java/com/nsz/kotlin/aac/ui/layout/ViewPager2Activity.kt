package com.nsz.kotlin.aac.ui.layout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityAccUiPager2Binding
import com.nsz.kotlin.ux.common.extension.startActivity

class ViewPager2Activity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivityAccUiPager2Binding by lazy { ActivityAccUiPager2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbVertical.setOnClickListener(this)
        binding.mbFragment.setOnClickListener(this)
    }

    override fun onClick(v: View ? ) {
        when (v) {
            binding.mbVertical -> startActivity<ViewPager2VerticalActivity>()
        }
    }

}