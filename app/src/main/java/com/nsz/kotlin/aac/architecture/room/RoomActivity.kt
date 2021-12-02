package com.nsz.kotlin.aac.architecture.room

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityAacArchitectureRoomBinding

class RoomActivity : AppCompatActivity(), UserView {

    private val userPresenter by lazy { UserPresenter(this, this) }
    private val binding: ActivityAacArchitectureRoomBinding by lazy { ActivityAacArchitectureRoomBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbUpdateUser.setOnClickListener {
            val updateName = binding.editUserNameInput.text.toString()
            userPresenter.updateUserName(updateName)
        }
    }

    override fun onStart() {
        super.onStart()
        userPresenter.start()
    }

    override fun onStop() {
        super.onStop()
        userPresenter.stop()
    }

    override fun showUserName(userName: String) {
        binding.tvUserName.visibility = View.VISIBLE
        binding.tvUserName.text = userName
    }

    override fun hideUserName() {
        binding.tvUserName.visibility = View.INVISIBLE
    }

}