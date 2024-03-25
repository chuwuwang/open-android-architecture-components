package com.nsz.kotlin.open.source

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nsz.kotlin.App
import com.nsz.kotlin.R
import com.nsz.kotlin.aac.ViewBindingFragment
import com.nsz.kotlin.databinding.FragmentOpenSourceBinding
import com.nsz.kotlin.ux.common.startActivity

class OpenSourceFragment : ViewBindingFragment<FragmentOpenSourceBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle ? ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.mbUssd.setOnClickListener {
            // startActivity<UssdActivity>()
            testAutoInjectKey()
        }
        binding.mbWxDetail.setOnClickListener { startActivity<WXDetailActivity>() }
        binding.mbAsyncCommunication.setOnClickListener { startActivity<AsyncCommunicationActivity>() }
        binding.mbRealm.setOnClickListener { findNavController().navigate(R.id.action_openSourceFragment_to_realmFragment) }
        binding.mbSpannableString.setOnClickListener { findNavController().navigate(R.id.action_openSourceFragment_to_spannableStringFragment) }
    }

    private fun testAutoInjectKey() {
        val intent = Intent()
        intent.setClassName("com.sunmi.keyinject", "com.sunmi.keyinject.activity.SunmiRKISolutionActivity")
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent ? ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(App.TAG, "onActivityResult resultCode:$resultCode")
        if (requestCode == 100) {
            // 100-Successful
            // 101-Device binding failed
            // 102-ECDH negotiate failed
            // 103-密钥注入失败
            // 104-Bcak-end check failed
            // 105-Key index has been used
            if (resultCode == 100) {
                Log.e(App.TAG, "auto inject key success...")
            } else {
                Log.e(App.TAG, "auto inject Key failure... ")
            }
        }
    }

}