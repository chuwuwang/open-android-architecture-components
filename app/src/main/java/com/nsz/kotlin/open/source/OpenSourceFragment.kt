package com.nsz.kotlin.open.source

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
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
        binding.mbUssd.setOnClickListener { startActivity<UssdActivity>() }
        binding.mbAsyncCommunication.setOnClickListener { startActivity<AsyncCommunicationActivity>() }
        binding.mbRealm.setOnClickListener { findNavController().navigate(R.id.action_openSourceFragment_to_realmFragment) }
        binding.mbSpannableString.setOnClickListener { findNavController().navigate(R.id.action_openSourceFragment_to_spannableStringFragment) }
    }

}