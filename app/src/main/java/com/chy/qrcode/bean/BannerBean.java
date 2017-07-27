package com.chy.qrcode.bean;

/**
 * Created by 47250 on 2017/6/19.
 */
public class BannerBean {
    private String title;
    private String imgUrl;
    private String detail;

    public BannerBean(String title, String imgUrl, String detail) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
