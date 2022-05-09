package com.nsz.kotlin.aac.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.aac.architecture.data.binding.DataBindingActivity
import com.nsz.kotlin.aac.architecture.data.binding.DataBindingRecyclerViewActivity
import com.nsz.kotlin.aac.architecture.data.store.DataStoreActivity
import com.nsz.kotlin.aac.architecture.foreground.service.ForegroundServiceActivity
import com.nsz.kotlin.aac.architecture.lifecycle.LifecycleActivity
import com.nsz.kotlin.aac.architecture.live.data.LiveDataActivity
import com.nsz.kotlin.aac.architecture.paging.PagingActivity
import com.nsz.kotlin.aac.architecture.room.RoomActivity
import com.nsz.kotlin.aac.architecture.view.model.ViewModelActivity
import com.nsz.kotlin.databinding.ActivityAacArchitectureBinding
import com.nsz.kotlin.ux.common.startActivity

class ArchitectureActivity : AppCompatActivity() {

    private val binding: ActivityAacArchitectureBinding by lazy { ActivityAacArchitectureBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbRoom.setOnClickListener { startActivity<RoomActivity>() }
        binding.mbPaging.setOnClickListener { startActivity<PagingActivity>() }
        binding.mbLiveData.setOnClickListener { startActivity<LiveDataActivity>() }
        binding.mbDataStore.setOnClickListener { startActivity<DataStoreActivity>() }
        binding.mbLifecycle.setOnClickListener { startActivity<LifecycleActivity>() }
        binding.mbViewModel.setOnClickListener { startActivity<ViewModelActivity>() }
        binding.mbNavigation.setOnClickListener {  }
        binding.mbDataBinding.setOnClickListener { startActivity<DataBindingActivity>() }
        binding.mbForegroundService.setOnClickListener { startActivity<ForegroundServiceActivity>() }
        binding.mbDataBindingRecyclerView.setOnClickListener { startActivity<DataBindingRecyclerViewActivity>() }
    }

}