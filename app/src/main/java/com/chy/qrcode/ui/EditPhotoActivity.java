package com.chy.qrcode.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chy.qrcode.R;
import com.chy.qrcode.util.LogUtils;
import com.chy.qrcode.util.QrCodeSaveShareUtil;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/23.
 */
public class EditPhotoActivity extends AppCompatActivity implements View.OnTouchListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_picture)
    ImageView mIvPicture;
    @Bind(R.id.iv_qrCode_picture)
    ImageView mIvQrCodePicture;
    @Bind(R.id.fl_picture)
    FrameLayout mFlPicture;

    private int lastX, lastY;
    private Bitmap mQrCodeBitmap;
    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            byte[] bitmapByte = intent.getByteArrayExtra("qrCodeBitmap");
            mQrCodeBitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
            mImagePath = intent.getStringExtra("imagePath");
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("贴图处理");
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initPresenter();
        initData();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_photo_save) {
            QrCodeSaveShareUtil.saveImageToGallery(this, convertViewToBitmap(mFlPicture));
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPresenter() {
    }

    private void initData() {
    }

    private void initView() {
        mIvQrCodePicture.setOnTouchListener(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImagePath, options);
        int width = options.outWidth;
        int hight = options.outHeight;
        LogUtils.e("width" + width + "--------" + "hight" + hight);
        if (width > 2000 || hight > 3000) {
            width = width / 2;
            hight = hight / 2;
        } else if (width < 500 || hight > 500) {
            width = width * 3;
            hight = hight * 3;
        }
        Picasso.with(this).load("file://" + mImagePath)
                .resize(width, hight)
                .into(mIvPicture);
        mIvQrCodePicture.setImageBitmap(mQrCodeBitmap);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mIvPicture.measure(w, h);
        int screenHeight = mIvPicture.getMeasuredHeight();
        int screenWidth = mIvPicture.getMeasuredWidth();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int l = v.getLeft() + dx;
                int b = v.getBottom() + dy;
                int r = v.getRight() + dx;
                int t = v.getTop() + dy;
                // 下面判断移动是否超出屏幕
                if (l < 0) {
                    l = 0;
                    r = l + v.getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = t + v.getHeight();
                }
                if (r > screenWidth) {
                    r = screenWidth;
                    l = r - v.getWidth();
                }
                if (b > screenHeight) {
                    b = screenHeight;
                    t = b - v.getHeight();
                }
                v.layout(l, t, r, b);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                v.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;

    }

    private Bitmap convertViewToBitmap(View view) {
        view.buildDrawingCache();
        return view.getDrawingCache();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
