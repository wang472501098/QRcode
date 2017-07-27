package com.chy.qrcode.bean;

/**
 * Created by wang on 2017/5/27.
 */

public class UpdateApp {

    private int versionCode;
    private String versionName;
    private String versionDesc;
    private String downloadUrl;


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


    @Override
    public String toString() {
        return "UpdateApp{" +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", versionDesc='" + versionDesc + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
