package com.chy.qrcode.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;

import com.chy.qrcode.R;
import com.chy.qrcode.adapter.ChooseImageAdapter;
import com.chy.qrcode.presenterImpl.ChooseImageActivityPresenter;
import com.chy.qrcode.util.LogUtils;
import com.chy.qrcode.view.SpacesItemDecoration;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/23.
 */
public class ChooseImageActivity extends AppCompatActivity implements ChooseImageAdapter.MyItemClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_chooseImageList)
    RecyclerView mRvChooseImageList;

    private ChooseImageActivityPresenter mChooseImageActivityPresenter;
    private ChooseImageAdapter mChooseImageAdapter;
    private byte[] bitmapByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseimage);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            bitmapByte = intent.getByteArrayExtra("qrCodeBitmap");
            // qrCodeBitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("选择图片");
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

    private void initPresenter() {
        mChooseImageAdapter = new ChooseImageAdapter(this);
        mChooseImageActivityPresenter = new ChooseImageActivityPresenter(this, this);
    }

    private void initData() {
    }

    private void initView() {
        GridLayoutManager mgr = new GridLayoutManager(this, 3);
        mRvChooseImageList.setLayoutManager(mgr);
        int spacingInPixels = 1;
        mRvChooseImageList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mChooseImageAdapter.setmMyItemClickListener(this);
        mRvChooseImageList.setAdapter(mChooseImageAdapter);
    }

    public void showImageViewData(List<String> mimageViewList) {
        mChooseImageAdapter.setImageViewData(mimageViewList);
    }

    @Override
    public void onImageItemClick(View view, String path) {
        Intent intent = new Intent(this, EditPhotoActivity.class);
        intent.putExtra("imagePath", path);
        intent.putExtra("qrCodeBitmap", bitmapByte);
        startActivity(intent);
    }

    @Override
    public void onHeadtemClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            saveScalePhoto(bitmap);
            //  mImage.setImageBitmap(bitmap);
        }
    }

    /**
     * * 存储缩放的图片 
     * * @param data 图片数据 
     *      
     */
    private void saveScalePhoto(Bitmap bitmap) {
        LogUtils.e(bitmap.toString());
        File file = new File(Environment.getExternalStorageDirectory()+ "/" +Environment.DIRECTORY_DCIM + "/ErCamera");
        file.mkdirs();// 创建文件夹
        String fileName = file.getAbsolutePath() + "/" + System.currentTimeMillis()+".jpg";
        FileOutputStream b = null;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
                Intent intent = new Intent(this, EditPhotoActivity.class);
                intent.putExtra("imagePath", fileName);
                intent.putExtra("qrCodeBitmap", bitmapByte);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
