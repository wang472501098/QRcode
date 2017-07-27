package com.chy.qrcode.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.chy.qrcode.presenterImpl.AccessServicePresenter;


@SuppressLint("NewApi")
public class AccessibilityService_ extends AccessibilityService {
    public static final String BASESCNUI = "com.tencent.mm.plugin.scanner.ui.BaseScanUI";//二维码扫描页面
    public static final String WIDGET_C = "android.support.design.widget.c";//相册选择弹框
    public static final String ALBUMPREVIEWUI = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI";//相册列表页


    private static final String TAG = "AccessibilityService_";
    private boolean flg = true;
    public AccessServicePresenter mAccessServicePresenter;
    private String mMoney;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        if (null != mMoney && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            openEnvelope(event);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            mMoney = intent.getStringExtra("mMoney");
            mAccessServicePresenter = new AccessServicePresenter(this, this, mMoney);
            mAccessServicePresenter.startProcess();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 页面切换监听
     *
     * @param event
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void openEnvelope(AccessibilityEvent event) {
        Log.e(TAG, "**********监听到页面变化：" + event.getClassName().toString());
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (null == nodeInfo) {
            nodeInfo = event.getSource();
        }
        if (nodeInfo == null) {
            return;
        }
        //监听打开微信扫一扫页面打开
        if (BASESCNUI.equals(event.getClassName().toString())) {
            if (flg) {
                recycle1(nodeInfo);
                flg = false;
            }
            //监听微信扫一扫页面底部弹出相册选择弹框
        } else if (WIDGET_C.equals(event.getClassName().toString())) {
            recycle2(nodeInfo);
            //监听微信打开图片选择页面
        } else if (ALBUMPREVIEWUI.equals(event.getClassName().toString())) {
            recycle3(nodeInfo);
            //监听微信打开公众号信息页面
        }
    }

    /**
     * 处理微信扫一扫页面,点击右上角图标
     *
     * @param info
     */
    private void recycle1(AccessibilityNodeInfo info) {
        info.getChild(4).performAction(
                AccessibilityNodeInfo.ACTION_CLICK);
        for (int i = 0; i < info.getChildCount(); i++) {
            if (info.getChild(i) != null) {
                Log.e(TAG, info.getChild(i).getClassName().toString());
            }
        }
    }

    /**
     * 处理微信扫一扫页面底部相册选择弹框
     *
     * @param info
     */
    private void recycle2(AccessibilityNodeInfo info) {

        for (int i = 0; i < info.getChildCount(); i++) {
            if (info.getChild(i) != null) {
                Log.e(TAG, info.getChild(i).getClassName().toString());
                if (info.getChild(i).getClassName().toString().equals("android.widget.TextView")) {
                    if (info.getChild(i).getText() != null) {
                        Log.e(TAG, info.getChild(i).getText().toString());
                        if ("从相册选取二维码".equals(info.getChild(i).getText().toString())) {
                            info.performAction(
                                    AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
                recycle2(info.getChild(i));
            }
        }
    }

    /**
     * 处理图片选择页面
     *
     * @param info
     */
    public void recycle3(AccessibilityNodeInfo info) {
        for (int i = 0; i < info.getChildCount(); i++) {
            Log.e(TAG, info.getClassName().toString());
            if (info.getChild(i) != null) {
                Log.e(TAG, info.getChild(i).getClassName().toString());
                if ("android.widget.GridView".equals(info.getChild(i).getClassName().toString())) {
                    info.getChild(i).getChild(1).performAction(
                            AccessibilityNodeInfo.ACTION_CLICK);
                    mAccessServicePresenter.endProcess();
                }
            }
        }
    }

    /**
     * 页面后退
     */
    public void globalBack() {
        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

    }

    /**
     * 数据重置
     */
    public void replacement() {
        mMoney = null;
        flg = true;
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "打赏服务中断", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this, "打赏服务开启", Toast.LENGTH_LONG).show();

    }
}
