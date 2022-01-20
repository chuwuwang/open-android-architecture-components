package com.nsz.kotlin.open.source;

import android.util.Log;

import androidx.fragment.app.Fragment;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class UssdHookHelper {

    public void sendUssdResponse() {
        try {
            XC_MethodHook hook = new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Object hookObj = param.thisObject;
                    String clsName = "unknownClass";
                    if (hookObj != null) {
                        clsName = hookObj.getClass().getName();
                    }
                    String mdName = "unknownMethod";
                    if (param.method != null) {
                        mdName = param.method.getName();
                    }
                    Log.d("ktx", "beforeHookedMethod: " + clsName + "-" + mdName);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object hookObj = param.thisObject;
                    String clsName = "unknownClass";
                    if (hookObj != null) {
                        clsName = hookObj.getClass().getName();
                    }
                    String mdName = "unknownMethod";
                    if (param.method != null) {
                        mdName = param.method.getName();
                    }
                    Log.d("ktx", "afterHookedMethod: " + clsName + "-" + mdName);
                }

            };
            XposedBridge.hookAllMethods(Fragment.class, "startActivity", hook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}