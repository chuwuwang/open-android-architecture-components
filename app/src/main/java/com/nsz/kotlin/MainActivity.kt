package com.nsz.kotlin

import android.os.Bundle
import com.kotlin.demo.proto.LoginInfo
import com.nsz.kotlin.aac.AndroidArchitectureComponentsActivity
import com.nsz.kotlin.databinding.ActivityMainBinding
import com.nsz.kotlin.open.source.OpenSourceActivity
import com.nsz.kotlin.performance.PerformanceEnhancementActivity
import com.nsz.kotlin.ux.common.startActivity

class MainActivity : PermissionsActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        LoginInfo.Login.newBuilder().setAccount("01").setPassword("123456").build()
        binding.btnOpenSource.setOnClickListener { startActivity<OpenSourceActivity>() }
        binding.btnAdvancedSkill.setOnClickListener { startActivity<AdvancedSkillActivity>() }
        binding.btnPerformanceEnhancement.setOnClickListener { startActivity<PerformanceEnhancementActivity>() }
        binding.btnAndroidArchitectureComponent.setOnClickListener { startActivity<AndroidArchitectureComponentsActivity>() }
    }

}