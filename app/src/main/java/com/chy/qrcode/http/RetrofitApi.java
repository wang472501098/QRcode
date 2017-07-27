package com.chy.qrcode.http;


import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;

/**
 * 类名称：com.publicnum.commission.http
 * 类描述：首页数据接口信息
 * 创建人：yd_10
 * 创建时间：2017/6/6 10:53
 * 修改人：
 * 修改时间：2017/6/6 10:53
 * 修改备注：
 */
public interface RetrofitApi {
    @GET("")
    Call<Result> getBanner();
}
