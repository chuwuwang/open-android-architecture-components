package com.nsz.kotlin.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityNfcBinding
import com.nsz.kotlin.ux.common.extension.startActivity

class NFCActivity : AppCompatActivity() {

    private val binding: ActivityNfcBinding by lazy { ActivityNfcBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbHce.setOnClickListener { startActivity<HCEActivity>() }
        binding.mbReadM1Card.setOnClickListener { startActivity<ReaderM1CardActivity>() }
    }

}