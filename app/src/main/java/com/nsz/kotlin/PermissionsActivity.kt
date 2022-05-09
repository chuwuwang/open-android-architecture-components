package com.nsz.kotlin

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.ux.common.CommonLog
import com.permissionx.guolindev.PermissionX

open class PermissionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        initPermissions()
    }

    private fun initPermissions() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    CommonLog.e("All permissions are granted")
                }
                onPermissionResult(allGranted)
                for (permission in grantedList) {
                    CommonLog.e("$permission permissions are granted")
                }
                for (permission in deniedList) {
                    CommonLog.e("$permission permissions are denied")
                }
            }
    }

    open fun onPermissionResult(granted: Boolean) {

    }

}