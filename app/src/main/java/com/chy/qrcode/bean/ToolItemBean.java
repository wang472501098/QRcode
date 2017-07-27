package com.chy.qrcode.bean;

/**
 * Created by 47250 on 2017/6/19.
 */
public class ToolItemBean {
    private String title;
    private int imgPath;

    public ToolItemBean(String title, int imgPath) {
        this.title = title;
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }
}
