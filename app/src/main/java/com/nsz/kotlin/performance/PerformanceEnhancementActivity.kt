package com.nsz.kotlin.performance

import com.nsz.kotlin.ViewBindingActivity
import com.nsz.kotlin.databinding.ActivityPerformanceEnhancementBinding
import com.nsz.kotlin.performance.monitor.AntiDebugActivity
import com.nsz.kotlin.ux.common.startActivity

class PerformanceEnhancementActivity : ViewBindingActivity<ActivityPerformanceEnhancementBinding>() {

    override fun init() {
        binding.btnDebugInfo.setOnClickListener { startActivity<AntiDebugActivity>() }
    }

}