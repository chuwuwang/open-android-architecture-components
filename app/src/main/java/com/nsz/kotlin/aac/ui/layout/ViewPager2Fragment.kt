package com.nsz.kotlin.aac.ui.layout

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nsz.kotlin.R
import com.nsz.kotlin.aac.ViewBindingFragment
import com.nsz.kotlin.databinding.FragmentLayoutPager2Binding

class ViewPager2Fragment : ViewBindingFragment<FragmentLayoutPager2Binding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle ? ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.mbVertical.setOnClickListener { findNavController().navigate(R.id.action_viewPager2Fragment_to_viewPager2VerticalFragment) }
    }

}