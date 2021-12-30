package com.nsz.kotlin.open.source

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityOpenSourceBinding
import com.nsz.kotlin.open.source.realm.RealmActivity
import com.nsz.kotlin.ux.common.extension.startActivity

class OpenSourceActivity : AppCompatActivity() {

    private val binding: ActivityOpenSourceBinding by lazy { ActivityOpenSourceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbUssd.setOnClickListener { startActivity<UssdActivity>() }
        binding.mbRealm.setOnClickListener { startActivity<RealmActivity>() }
    }

}