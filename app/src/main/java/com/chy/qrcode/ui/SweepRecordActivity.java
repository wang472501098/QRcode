package com.chy.qrcode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chy.qrcode.R;
import com.chy.qrcode.adapter.SweepRecordAdapter;
import com.chy.qrcode.dao.SWEEP_RECORD;
import com.chy.qrcode.presenterImpl.SweepRecordActivityPresenter;
import com.chy.qrcode.util.zxing.CaptureActivity;
import com.chy.qrcode.view.SpacesItemDecoration;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/24.
 */
public class SweepRecordActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_sweepRecordList)
    RecyclerView mRvSweepRecordList;
    @Bind(R.id.ll_nullData)
    LinearLayout mLlNullDatat;
    @Bind(R.id.btn_qusaomiao)
    TextView mBtnQuSaoMiao;

    private SweepRecordAdapter mSweepRecordAdapter;
    private SweepRecordActivityPresenter mSweepRecordActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep_record);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("扫描记录");
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
        mSweepRecordAdapter = new SweepRecordAdapter(this);
        mSweepRecordActivityPresenter = new SweepRecordActivityPresenter(this, this);
    }

    private void initData() {
    }

    private void initView() {
        mBtnQuSaoMiao.setOnClickListener(this);
        mRvSweepRecordList.setLayoutManager(new LinearLayoutManager(this));
        // mChooseImageAdapter.setmMyItemClickListener(this);
        int spacingInPixels = 10;
        mRvSweepRecordList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRvSweepRecordList.setAdapter(mSweepRecordAdapter);

    }

    public void showImageViewData(List<SWEEP_RECORD> sweepRecordList) {
        mLlNullDatat.setVisibility(View.GONE);
        mRvSweepRecordList.setVisibility(View.VISIBLE);
        mSweepRecordAdapter.setImageViewData(sweepRecordList);
    }

    public void showNullData() {
        mLlNullDatat.setVisibility(View.VISIBLE);
        mRvSweepRecordList.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qusaomiao:
                startActivity(new Intent(this, CaptureActivity.class));
                finish();
                break;
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
