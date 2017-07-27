package com.chy.qrcode.http;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名称：HttpUtil
 * 类描述： retrofit get请求工具类
 * 创建人：yd-9
 * 创建时间：18:35
 * 修改人：yd-9
 * 修改时间：18:35
 * 修改备注：2016/5/25
 */
public class HttpUtil {
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static String userAgent;

    static {
        userAgent = String.format("%s/%s (Linux; Android %s; %s %s Build/%s)", System.getProperty("java.vm.name"),
                System.getProperty("java.vm.version"), Build.VERSION.RELEASE, Build.BRAND, Build.MODEL, Build.DISPLAY);

        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl httpUrl = originalRequest.url().newBuilder()
                        //    .addQueryParameter("versionCode", AppConstans.getInstance().getVersionCode()+"")
                        //     .addQueryParameter("userId",Constants.getInstance().getUserId())
                        .build();

                Request authorised = originalRequest.newBuilder()
                        .header("User-Agent", userAgent)
                        .url(httpUrl)
                        .build();
                return chain.proceed(authorised);
            }
        };

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
              //  .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(mTokenInterceptor)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });
        okHttpClient = builder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static <T> T createApi(Class<T> clazz, String host) {
        Object api = null;
        if (null == api) {
            api = new Retrofit.Builder().client(okHttpClient).baseUrl(host)
                    .addConverterFactory(gsonConverterFactory).addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build().create(clazz);
        }
        return (T) api;
    }

    public static <T> Subscription subscribe(Observable<T> observable, Observer<T> observer) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
