package com.chy.qrcode.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 类名称：com.publicnum.commission.bean
 * 类描述：
 * 创建人：yd_10
 * 创建时间：2017/5/10 11:17
 * 修改人：
 * 修改时间：2017/5/10 11:17
 * 修改备注：
 */
public class Result<T> implements Serializable {
    public int res;
    @SerializedName("data")
    public T data;

    @Override
    public String toString() {
        return "Result{" +
                "res=" + res +
                ", data=" + data +
                '}';
    }
}
