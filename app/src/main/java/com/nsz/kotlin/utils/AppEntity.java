package com.nsz.kotlin.utils;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;

public class AppEntity implements Serializable {

    public int appId;                       // 应⽤ID
    public String appName;                  // 名称
    public String packageName;              // 包名
    public String iconUrl;                  // 图标的链接
    public int versionCode;                 // 版本号
    public String versionName;              // 版本名称
    public String updateInfo;               // 更新信息
    public String downloadUrl;              // 下载链接
    public long fileSize;                   // 文件大小
    public long downloadNum;                // 下载次数
    public float score;                     // 评分
    public String md5;                      // 文件md5值
    public long lastModifyTime;             // 最后修改时间
    public String developer;                // 开发者
    public String describeInfo;             // 应用的详细文字描述
    public List<String> showPictureList;    // 应用展示图片列表
    public String sha1Str; // 应用的sha1
    public boolean forceUpdate; //是否强制更新
    /**
     * [Description("High")]
     * High = 0,
     * [Description("Medium")]
     * Medium = 1,
     * [Description("Low")]
     * Low = 2
     */
    public int downloadPriority; // 下载优先级
    public long scheduledDownloadStartTimestamp; // 计划下载时间

    public String fileName;                 // 文件名
    public String folderName;               // 文件夹名
    public String targetPath;               // 文件路径
    public long downloadedLength;           // 已经下载的大小
    public int downloadedPercent;           // 已经下载的百分比

    public @AppState int appState = AppState.STATE_APP_NOT_INSTALL; // app初始状态
    /**
     * STATE_APP_NOT_INSTALL: 未安装
     * STATE_APP_UPDATE: 需要更新
     * STATE_APP_NORMAL: 正常状态
     * STATE_APP_ROLL_BACK: App回滚
     */
    @IntDef({
            AppState.STATE_APP_NOT_INSTALL, AppState.STATE_APP_UPDATE, AppState.STATE_APP_NORMAL, AppState.STATE_APP_ROLL_BACK
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AppState {
        int STATE_APP_NOT_INSTALL = 10;
        int STATE_APP_UPDATE = 20;
        int STATE_APP_NORMAL = 30;
        int STATE_APP_ROLL_BACK = 40;
    }

    public @TaskState int taskState = TaskState.STATE_WAIT; // 任务进度状态
    /**
     * STATE_NULL: 表示不需要参与任务流程
     * STATE_WAIT: 等待
     * STATE_DOWNLOADING：下载中
     * STATE_DOWNLOADED_SUCCESS：下载成功
     * STATE_DOWNLOADED_FAILED：下载失败
     * STATE_DOWNLOAD_PAUSE：暂停下载
     * STATE_INSTALL_FAILED：安装失败
     */
    @IntDef({
            TaskState.STATE_WAIT, TaskState.STATE_DOWNLOADING, TaskState.STATE_DOWNLOADED_SUCCESS,
            TaskState.STATE_DOWNLOADED_FAILED, TaskState.STATE_DOWNLOAD_PAUSE, TaskState.STATE_INSTALL_FAILED,
            TaskState.STATE_NULL, TaskState.STATE_UNINSTALLING, TaskState.STATE_UNINSTALL_FAILED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TaskState {
        int STATE_NULL = 0;
        int STATE_WAIT = 1;
        int STATE_DOWNLOADING = 2;
        int STATE_DOWNLOADED_SUCCESS = 3;
        int STATE_DOWNLOADED_FAILED = 4;
        int STATE_DOWNLOAD_PAUSE = 5;
        int STATE_INSTALL_FAILED = 6;
        int STATE_UNINSTALLING = 7;
        int STATE_UNINSTALL_FAILED = 8;
    }

    public void updateAppState(@AppState int appState) {
        this.appState = appState;
    }

    public void updateTaskState(@TaskState int taskState) {
        this.taskState = taskState;
    }

    public void updateAllState(@AppState int appState, @TaskState int taskState) {
        this.appState = appState;
        this.taskState = taskState;
    }

    public void initStateInstalled() {
        this.appState = AppState.STATE_APP_NORMAL;
        this.taskState = TaskState.STATE_NULL;
    }

    public void initStateNeedUpdate() {
        this.appState = AppState.STATE_APP_UPDATE;
        this.taskState = this.forceUpdate ? TaskState.STATE_WAIT : TaskState.STATE_NULL;
    }

    public void initStateNeedInstall() {
        this.appState = AppState.STATE_APP_NOT_INSTALL;
        this.taskState = TaskState.STATE_WAIT;
    }

    public void initStateNeedRollBack() {
        this.appState = AppState.STATE_APP_ROLL_BACK;
        this.taskState = TaskState.STATE_WAIT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppEntity appEntity = (AppEntity) o;
        return fileSize == appEntity.fileSize && packageName.equals(appEntity.packageName) && downloadUrl.equals(appEntity.downloadUrl) && md5.equals(appEntity.md5) && sha1Str.equals(appEntity.sha1Str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName);
    }

    @NonNull
    @Override
    public String toString() {
        return "AppEntity{" +
                "appId=" + appId +
                ", appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode=" + versionCode +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", md5='" + md5 + '\'' +
                ", sha1Str='" + sha1Str + '\'' +
                ", fileName='" + fileName + '\'' +
                ", targetPath='" + targetPath + '\'' +
                ", appState=" + appState + '\'' +
                ", taskState=" + taskState + '\'' +
                ", downloadedPercent=" + downloadedPercent +
                '}';
    }
}