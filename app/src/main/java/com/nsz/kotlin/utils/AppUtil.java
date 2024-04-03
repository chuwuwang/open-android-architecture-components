package com.nsz.kotlin.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public class AppUtil {

    /**
     * 获取指定 App 的版本号
     *
     * @param context     上下文
     * @param packageName 指定 App 的包名
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        int versionCode = -1;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                versionCode = packageInfo.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取指定 App 的版本名
     *
     * @param context     上下文
     * @param packageName 指定 App 的包名
     * @return
     */
    public static String getVersionName(Context context, String packageName) {
        String versionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                versionName = packageInfo.versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static void launchApp(Context context, String packageName) {
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (packageInfo == null) { // this packageName not installed
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveInfo = resolveInfoList.iterator().next();
        if (resolveInfo != null) {
            String packageNameNew = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(packageNameNew, className);
            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }

    public static void rebootApp(Context context, String packageName) {

    }

}