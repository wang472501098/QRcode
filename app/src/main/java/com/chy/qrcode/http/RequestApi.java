package com.chy.qrcode.http;

import com.chy.qrcode.bean.BannerBean;
import com.chy.qrcode.bean.DataBean;
import com.chy.qrcode.bean.ListResult;
import com.chy.qrcode.bean.NotificationBean;
import com.chy.qrcode.bean.Result;
import com.chy.qrcode.bean.ResultTest;
import com.chy.qrcode.bean.UpdateApp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 类名称：com.publicnum.commission.http
 * 类描述：
 * 创建人：yd_10
 * 创建时间：2017/5/9 19:38
 * 修改人：
 * 修改时间：2017/5/9 19:38
 * 修改备注：
 */
public interface RequestApi {

    /**
     * 首页banner
     *
     * @return
     */
    @GET("erweima/banner.txt")
    Observable<ListResult<BannerBean>> getBanner();

    /**
     * 首页banner
     *
     * @return
     */
    @GET("erweima/notification.txt")
    Observable<ListResult<NotificationBean>> getNotification();

    /**
     * 检测更新
     *
     * @return
     */
    @GET("erweima/update.txt")
    Observable<Result<UpdateApp>> isUpdate();




}
