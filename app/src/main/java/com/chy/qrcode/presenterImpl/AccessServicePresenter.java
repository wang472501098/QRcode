package com.chy.qrcode.presenterImpl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import com.chy.qrcode.service.AccessibilityService_;
import com.chy.qrcode.view.MyToast;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wang on 2017/5/11.
 */

public class AccessServicePresenter {
    private String TAG = "AccessServicePresenter";
    private Context mContext;
    private AccessibilityService_ mAccessibilityService_;
    private String mManey;
    private MyToast myToast;
    private Thread mThread;
    private Map<String, String> map = new HashMap<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    myToast.show();
                    handler.sendEmptyMessage(1);
                    break;
            }
        }
    };

    public AccessServicePresenter(Context context, AccessibilityService_ accessibilityService_, String money) {
        this.mContext = context;
        this.mAccessibilityService_ = accessibilityService_;
        this.mManey = money;
    }


    /**
     * 关注流程开始
     */
    public void startProcess() {
        myToast = MyToast.makeText(mContext, "谢谢老板", Toast.LENGTH_LONG);
        handler.sendEmptyMessage(1);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                attentionOutTime();
            }
        });
        handler.postDelayed(mThread, 15000);
    }


    /**
     * 关注流程预期结束
     */
    public void endProcess() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(mThread);
                attentionResult();
            }
        }, 5000);

    }

    /**
     * 关注流程结束处理
     */
    public void attentionResult() {
        conductAttentionResultPostBack();
    }

    /**
     * 关注超时  attentintType=4
     */
    private void attentionOutTime() {

        mAccessibilityService_.globalBack();
        map.put("outTime", "关注超时");
        conductAttentionResultPostBack();
    }

    /**
     * 关注情况回传
     */
    private void conductAttentionResultPostBack() {
        mAccessibilityService_.replacement();
        map = null;
        myToast.toastCancel();
        handler.removeMessages(1);
    }
}
