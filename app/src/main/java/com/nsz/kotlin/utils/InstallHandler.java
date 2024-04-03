package com.nsz.kotlin.utils;

import android.content.Context;

public class InstallHandler {

    private InstallHandlerCallback callback;

    public void install(Context context, String path, InstallHandlerCallback callback) {
        try {
            this.callback = callback;

        } catch (Throwable e) {
            e.printStackTrace();
            if (callback != null) callback.onFailure(null);
        }
    }

    public interface InstallHandlerCallback {

        void onSuccess(String packageName);

        void onFailure(String packageName);

    }

}