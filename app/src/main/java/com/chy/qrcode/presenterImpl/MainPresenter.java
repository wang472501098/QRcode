package com.chy.qrcode.presenterImpl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.chy.qrcode.Constant;
import com.chy.qrcode.MainActivity;
import com.chy.qrcode.R;
import com.chy.qrcode.bean.BannerBean;
import com.chy.qrcode.bean.ListResult;
import com.chy.qrcode.bean.NotificationBean;
import com.chy.qrcode.bean.Result;
import com.chy.qrcode.bean.ToolItemBean;
import com.chy.qrcode.bean.UpdateApp;
import com.chy.qrcode.http.HttpUtil;
import com.chy.qrcode.http.RequestApi;
import com.chy.qrcode.http.RetrofitApi;
import com.chy.qrcode.util.LogUtils;
import com.chy.qrcode.view.UpdateDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscription;

/**
 * Created by 47250 on 2017/6/19.
 */
public class MainPresenter {
    private String TAG = "MainPresenter";
    private Context mContext;
    private MainActivity mMainActivity;
    private Subscription updateAppSub;
    private Subscription bannerSub;
    private Subscription notificationSub;

    public MainPresenter(Context context, MainActivity activity) {
        this.mContext = context;
        this.mMainActivity = activity;
        getToolData();
        getBannerData();
        getNoticeData();
        isUpdateApp();
      //  getRetrofitBanner();
    }

    private void getRetrofitBanner() {
    Retrofit retrofit=new Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create()).build();
        

    }

    public void isUpdateApp() {
        updateAppSub = HttpUtil.subscribe(HttpUtil.createApi(RequestApi.class, Constant.HOST).isUpdate(),
                new Observer<Result<UpdateApp>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, "***********" + e.toString());
                    }

                    @Override
                    public void onNext(Result<UpdateApp> result) {
                        if (result.res == 1) {
                            if (result.data.getVersionCode() > getVersionCode()) {
                                LogUtils.e(TAG, result.data.getVersionDesc());
                                UpdateDialog updateDialog = new UpdateDialog(mContext, result.data);
                                updateDialog.setCancelable(false);
                                updateDialog.show();
                            }
                        } else {

                        }
                    }
                });
    }

    /**
     * 获取当前版本号
     *
     * @return
     * @throws Exception
     */
    public int getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void getToolData() {
        List<ToolItemBean> mToolItemList = new ArrayList<>();
        mToolItemList.add(new ToolItemBean("扫描二维码", R.mipmap.icon_saomiao));
        mToolItemList.add(new ToolItemBean("生成二维码", R.mipmap.icon_shengcheng));
        mToolItemList.add(new ToolItemBean("扫描记录", R.mipmap.icon_saomiao_jilu));
        mToolItemList.add(new ToolItemBean("生成记录", R.mipmap.icon_shengcheng_jilu));
        mToolItemList.add(new ToolItemBean("去打赏", R.mipmap.icon_dashang));
        mToolItemList.add(new ToolItemBean("发给朋友", R.mipmap.icon_share));

        mMainActivity.showToolData(mToolItemList);
    }

    private void getBannerData() {
        bannerSub = HttpUtil.subscribe(HttpUtil.createApi(RequestApi.class, Constant.HOST).getBanner(),
                new Observer<ListResult<BannerBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, "***********" + e.toString());
                    }

                    @Override
                    public void onNext(ListResult<BannerBean> result) {
                        if (result.res == 1) {
                            mMainActivity.showBannerData(result.data);
                        }
                    }
                });
//        List<BannerBean> mBannerList = new ArrayList<>();
//        mBannerList.add(new BannerBean("Banner1", R.mipmap.timg, ""));
//        mBannerList.add(new BannerBean("Banner2", R.mipmap.timg1, ""));
//        mMainActivity.showBannerData(mBannerList);
    }

    private void getNoticeData() {

        notificationSub = HttpUtil.subscribe(HttpUtil.createApi(RequestApi.class, Constant.HOST).getNotification(),
                new Observer<ListResult<NotificationBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, "***********" + e.toString());
                    }

                    @Override
                    public void onNext(ListResult<NotificationBean> result) {
                        if (result.res == 1) {
                            mMainActivity.showNoticeData(result.data);
                        }
                    }
                });
//        List<String> mNoticeList = new ArrayList<>();
//        mNoticeList.add("1、欢迎您来处理二维码！");
//        mNoticeList.add("2、欢迎您来处理二维码！");
//        mNoticeList.add("3、欢迎您来处理二维码！");
//        mNoticeList.add("4、欢迎您来处理二维码！");
//        mMainActivity.showNoticeData(mNoticeList);
    }


}
