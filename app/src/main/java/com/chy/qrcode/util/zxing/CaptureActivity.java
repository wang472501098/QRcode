package com.chy.qrcode.util.zxing;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockApplication;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chy.qrcode.Mapp;
import com.chy.qrcode.R;
import com.chy.qrcode.dao.SWEEP_RECORD;
import com.chy.qrcode.ui.BrowserActivity;
import com.chy.qrcode.util.FlashlightUtil;
import com.chy.qrcode.util.MDataUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public final class CaptureActivity extends AppCompatActivity implements
        SurfaceHolder.Callback, OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_erweima_context)
    TextView mTvErWeiMaContext;
    @Bind(R.id.iv_copy)
    LinearLayout mIvCopy;
    @Bind(R.id.iv_accessLink)
    LinearLayout mIvAccessLink;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_erweima);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("扫一扫");
        }
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIvCopy.setOnClickListener(this);
        mIvAccessLink.setOnClickListener(this);


        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);
    }

    @Override
    public void onClick(View v) {
        String str = mTvErWeiMaContext.getText().toString();

        switch (v.getId()) {
            case R.id.iv_copy:
                if (!str.isEmpty()) {
                    onClickCopy();
                } else {
                    Toast.makeText(this, "没有要复制的内容", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.iv_accessLink:
                if (!str.isEmpty() && (str.startsWith("http://") || str.startsWith("https://"))) {
                    Intent intent = new Intent(this, BrowserActivity.class);
                    intent.putExtra("linkUrl", mTvErWeiMaContext.getText().toString());
                    startActivity(intent);
                } else if (str.isEmpty()) {
                    Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
                } else if (!str.startsWith("http://") || !str.startsWith("https://")) {
                    Toast.makeText(this, "内容非链接", Toast.LENGTH_LONG).show();
                }

                break;
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;
        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);
        inactivityTimer.onResume();
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        viewfinderView.recycleLineDrawable();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_CAMERA:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * 得到扫描结果
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        String msg = rawResult.getText();
        if (msg == null || "".equals(msg)) {

        } else {
            String wifiMsg = URLDecoder.decode(msg);
            mTvErWeiMaContext.setVisibility(View.VISIBLE);
            mTvErWeiMaContext.setText(wifiMsg);
            if (!wifiMsg.isEmpty()) {
                if (wifiMsg.startsWith("http://") || wifiMsg.startsWith("https://")) {
                    SWEEP_RECORD sweep_record = new SWEEP_RECORD();
                    sweep_record.setContext(wifiMsg);
                    sweep_record.setTpye(1);
                    sweep_record.setCreate_time(MDataUtil.getNowDate());
                    Mapp.getInstances().getDaoSession().getSWEEP_RECORDDao().insert(sweep_record);
                } else {
                    SWEEP_RECORD sweep_record = new SWEEP_RECORD();
                    sweep_record.setContext(wifiMsg);
                    sweep_record.setTpye(0);
                    sweep_record.setCreate_time(MDataUtil.getNowDate());
                    Mapp.getInstances().getDaoSession().getSWEEP_RECORDDao().insert(sweep_record);
                }
            }

        }

    }

    public void onClickCopy() {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(mTvErWeiMaContext.getText());
        Toast.makeText(this, "内容已添加到剪贴板", Toast.LENGTH_LONG).show();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            return;
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("系统可能不支持");
        builder.setPositiveButton("退出", new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


}
