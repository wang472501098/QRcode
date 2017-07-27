package com.chy.qrcode.bean;

/**
 * Created by 47250 on 2017/6/28.
 */
public class NotificationBean {
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "NotificationBean{" +
                "detail='" + detail + '\'' +
                '}';
    }
}
