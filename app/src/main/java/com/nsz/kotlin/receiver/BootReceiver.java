package com.nsz.kotlin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nsz.kotlin.utils.AppEntity;
import com.nsz.kotlin.utils.AppUtil;
import com.nsz.kotlin.utils.InstallHandler;

/**
 * boot broadcast - Power on the broadcast monitoring device.
 */
public class BootReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (action == null) return;
        this.context = context;

        // Once the device is powered on, run the specified app
        AppUtil.launchApp(context, "Specified app");

        // Get the local version number of the specified App B
        int versionCodeX = AppUtil.getVersionCode(context, "Specified app B");

        // Get the version number of App B from the local configuration file
        int versionCodeY = getVersionCodeByConfigure();

        if (versionCodeX >= versionCodeY) {
            // not do anything
        } else {
            // Get the latest information of App B from the server, including the download address
            AppEntity appEntity = fetchAppInfo();

            // download apk file
            downloadApk(appEntity.downloadUrl);

            // installed apk
            new InstallHandler().install(context, appEntity.targetPath, installHandlerCallback);

        }

    }

    private final InstallHandler.InstallHandlerCallback installHandlerCallback = new InstallHandler.InstallHandlerCallback() {

        @Override
        public void onSuccess(String packageName) {
            // installed apk successful
        }

        @Override
        public void onFailure(String packageName) {
            // installed apk failure
            AppUtil.rebootApp(context, packageName);
        }

    };

    private AppEntity fetchAppInfo() {
        // TODO Request the server to obtain app-related information
        return new AppEntity();
    }

    private void downloadApk(String url) {
        // download Apk file nad save file to sdcard
    }

    private int getVersionCodeByConfigure() {
        // TODO Read the business configuration file and obtain the version number of App B
        return 1;
    }

}