package com.nsz.kotlin.aac.architecture.view.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.nsz.kotlin.databinding.ActivityAacArchitectureViewModelBinding
import com.nsz.kotlin.ux.common.CommonLog

class ViewModelActivity : AppCompatActivity() {

    private val binding: ActivityAacArchitectureViewModelBinding by lazy { ActivityAacArchitectureViewModelBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val model = ViewModelProvider(this)[MyViewModel::class.java]
        model.userData.observe(this) {
            val age = it.age
            val name = it.name
            binding.tvMessage.text = "name: $name age: $age"
        }
        Transformations.map(model.stringData) {
            val bool = it.endsWith("2") || it.endsWith("4") || it.endsWith("6") || it.endsWith("8")
            var value = it
            if (bool) { value += " ktx" }
            value
        }.observe(this) {
            binding.tvMessageMap.text = it
        }
        model.mediatorLiveData.observe(this) {
            CommonLog.e("mediatorLiveData $it")
            binding.tvMessageMutable.text = it
        }
        binding.btnData.setOnClickListener {
            model.getUserInfo()
            model.map()
            model.merge()
        }
    }

}