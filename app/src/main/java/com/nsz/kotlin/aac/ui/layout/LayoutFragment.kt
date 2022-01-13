package com.nsz.kotlin.aac.ui.layout

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nsz.kotlin.R
import com.nsz.kotlin.aac.ViewBindingFragment
import com.nsz.kotlin.databinding.FragmentLayoutBinding

class LayoutFragment : ViewBindingFragment<FragmentLayoutBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle ? ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.mbViewPager2.setOnClickListener { findNavController().navigate(R.id.action_layoutFragment_to_viewPager2Fragment) }
    }

}