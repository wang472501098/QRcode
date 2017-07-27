package com.chy.qrcode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chy.qrcode.R;
import com.chy.qrcode.adapter.BuildRecordAdapter;
import com.chy.qrcode.adapter.ChooseImageAdapter;
import com.chy.qrcode.presenterImpl.BuildRecordActivityPresenter;
import com.chy.qrcode.presenterImpl.ChooseImageActivityPresenter;
import com.chy.qrcode.view.SpacesItemDecoration;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/24.
 */
public class BuildRecordActivity extends AppCompatActivity implements View.OnClickListener, BuildRecordAdapter.MyItemClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_buildImageList)
    RecyclerView mRvBuildImageList;
    @Bind(R.id.ll_nullData)
    LinearLayout mLlNullData;
    @Bind(R.id.btn_qushengcheng)
    TextView mBtnQuShengCheng;

    private BuildRecordAdapter mBuildRecordAdapter;
    private BuildRecordActivityPresenter mBuildRecordActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_record);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("生成记录");
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
        mBuildRecordAdapter = new BuildRecordAdapter(this);
        mBuildRecordActivityPresenter = new BuildRecordActivityPresenter(this, this);
    }

    private void initData() {
    }

    private void initView() {
        mBtnQuShengCheng.setOnClickListener(this);
        GridLayoutManager mgr = new GridLayoutManager(this, 3);
        mRvBuildImageList.setLayoutManager(mgr);
        int spacingInPixels = 1;
        mRvBuildImageList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mBuildRecordAdapter.setmMyItemClickListener(this);
        mRvBuildImageList.setAdapter(mBuildRecordAdapter);
    }

    public void showImageViewData(List<String> mimageViewList) {
        mLlNullData.setVisibility(View.GONE);
        mRvBuildImageList.setVisibility(View.VISIBLE);
        mBuildRecordAdapter.setImageViewData(mimageViewList);
    }

    public void showNullData() {
        mLlNullData.setVisibility(View.VISIBLE);
        mRvBuildImageList.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qushengcheng:
                startActivity(new Intent(this, GenerateQrCodeActivity.class));
                finish();
                break;
        }

    }

    @Override
    public void onImageItemClick(View view, int p) {
        startActivity(new Intent(this,ViewPagerActivity.class).putExtra("imageItme",p));

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
