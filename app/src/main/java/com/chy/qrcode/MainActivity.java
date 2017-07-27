package com.chy.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chy.qrcode.adapter.MainAdapter;
import com.chy.qrcode.bean.BannerBean;
import com.chy.qrcode.bean.NotificationBean;
import com.chy.qrcode.bean.ToolItemBean;
import com.chy.qrcode.presenterImpl.MainPresenter;
import com.chy.qrcode.service.AccessibilityService_;
import com.chy.qrcode.ui.BuildRecordActivity;
import com.chy.qrcode.ui.GenerateQrCodeActivity;
import com.chy.qrcode.ui.SettingActivity;
import com.chy.qrcode.ui.SweepRecordActivity;
import com.chy.qrcode.util.ServiceExistsUtil;
import com.chy.qrcode.util.ToQrCodeUtil;
import com.chy.qrcode.util.zxing.CaptureActivity;
import com.chy.qrcode.view.OpenServiceDialog;
import com.chy.qrcode.view.SpacesItemDecoration;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainAdapter.MyItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_HomeList)
    RecyclerView mRvHomeList;
    private MainAdapter mMainAdapter;
    private MainPresenter presenter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initPresenter();
        initData();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_person) {
            startActivity(new Intent(this, SettingActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void initPresenter() {
        mMainAdapter = new MainAdapter(this);
        presenter = new MainPresenter(this, this);
    }

    private void initData() {

    }

    private void initView() {
        GridLayoutManager mgr = new GridLayoutManager(this, 3);
        mRvHomeList.setLayoutManager(mgr);
        int spacingInPixels = 1;
        mRvHomeList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mMainAdapter.setmMyItemClickListener(this);
        mRvHomeList.setAdapter(mMainAdapter);
    }

    @Override
    public void onToolItemClick(View view, int postion) {
        switch (postion) {
            case 2:
                startActivity(new Intent(this, CaptureActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, GenerateQrCodeActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, SweepRecordActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, BuildRecordActivity.class));
                break;
            case 6:
                if (ServiceExistsUtil.isAccessibilitySettingsOn(this)) {
                    StartWeChat();
                } else {
                    OpenServiceDialog openServiceDialog = new OpenServiceDialog(this);
                    openServiceDialog.setCancelable(false);
                    openServiceDialog.show();
                }
                break;
            case 7:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "http://www.diandaitongjinrong.com/erweima/app/erweima.apk");
                startActivity(Intent.createChooser(textIntent, "分享"));
                break;
        }


    }

    /**
     * 启动关注公众号流程
     */
    private void StartWeChat() {
        Intent intent = new Intent(this, AccessibilityService_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mMoney", Constant.MONEY);
        intent.putExtras(bundle);
        startService(intent);
        ToQrCodeUtil.BC_2weima(Constant.MONEY, this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setPackage("com.tencent.mm");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("com.tencent.mm.action.BIZSHORTCUT");
                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                startActivity(intent);
            }
        }, 3000);
    }

    public void showToolData(List<ToolItemBean> mToolItemList) {
        mMainAdapter.setToolData(mToolItemList);

    }

    public void showNoticeData(List<NotificationBean> mNoticeList) {
        mMainAdapter.setNoticeData(mNoticeList);
    }

    public void showBannerData(List<BannerBean> mBannerList) {
        mMainAdapter.setBannerData(mBannerList);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
