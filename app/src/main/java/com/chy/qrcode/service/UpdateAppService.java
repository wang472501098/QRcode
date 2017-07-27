package com.chy.qrcode.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;


import com.chy.qrcode.R;
import com.chy.qrcode.http.HttpUtil;
import com.chy.qrcode.http.ProgressResponseBody;
import com.chy.qrcode.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wang on 2017/4/27.
 */

public class UpdateAppService extends Service {
    private String TAG = "UpdateAppService";
    private int notification_id = 19172439;
    private NotificationManager nm;
    private Notification notification;
    private int showProgress = 0;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    LogUtils.e(TAG, message.arg1 + "");
                    notification.contentView.setProgressBar(R.id.pb, 100, message.arg1, false);
                    notification.contentView.setTextViewText(R.id.down_tv, "捞红包更新中..." + message.arg1 + "%");
                    showNotification();//这里是更新notification,就是更新进度条
                    break;
                case 2:
                    LogUtils.e(TAG, "下载完成");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            install();
                            nm.cancelAll();
                            handler.removeMessages(1);
                        }
                    }, 1000);
                    break;
            }
            return false;
        }
    });
    /**
     * 下载进度监听
     */
    private ProgressResponseBody.ProgressListener progressListener = new ProgressResponseBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
            if (done) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onProgressUpdate(int progress) {
            if (progress - showProgress >= 1) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = progress;
                handler.sendMessage(message);
                showProgress = progress;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        updateLoadApp(intent.getStringExtra("updateUrl"));
        return START_NOT_STICKY;
    }

    /**
     * 启动下载
     */
    private void updateLoadApp(String updateUrl) {
        initNotification();
        showNotification();
        download(updateUrl, progressListener);
    }

    /**
     * 初始化通知栏
     */
    public void initNotification() {
        //建立notification,前面有学习过，不解释了，不懂看搜索以前的文章
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.mipmap.ic_launcher, "图标边的文字", System.currentTimeMillis());
        notification.contentView = new RemoteViews(getPackageName(), R.layout.notification_updateapp);
        //使用notification.xml文件作VIEW
        notification.contentView.setProgressBar(R.id.pb, 100, 0, false);
    }

    /**
     * 弹出通知栏
     */
    public void showNotification() {
        nm.notify(notification_id, notification);
    }

    /**
     * 下载apk
     *
     * @param url
     * @param progressListener
     */
    private void download(String url, final ProgressResponseBody.ProgressListener progressListener) {

        if (null != url) {
            Request request = new Request.Builder().url(url).build();
            Call call = HttpUtil.getOkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(
                            new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            }).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.e(TAG, "onFailure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.e(TAG, "onResponse");
                    //将返回结果转化为流，并写入文件
                    int len;
                    byte[] buf = new byte[2048];
                    InputStream inputStream = response.body().byteStream();
                    //可以在这里自定义路径
                    File file1 = new File(Environment.getExternalStorageDirectory().getPath(), "erweima.apk");
                    LogUtils.e(TAG, file1.getAbsolutePath());
                    FileOutputStream fileOutputStream = new FileOutputStream(file1);
                    while ((len = inputStream.read(buf)) != -1) {
                        fileOutputStream.write(buf, 0, len);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();
                }
            });
        }
    }

    /**
     * 安装指定apk
     */
    private void install() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file1 = new File(Environment.getExternalStorageDirectory().getPath(), "erweima.apk");
        intent.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
