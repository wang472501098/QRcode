package com.chy.qrcode.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chy.qrcode.R;
import com.chy.qrcode.view.GenerateQrCodeDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/22.
 */
public class GenerateQrCodeActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_cenerate)
    TextView mBtnCenerate;
    @Bind(R.id.et_context)
    EditText etContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cenerate_qrcode);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("生成二维码");
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnCenerate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = etContext.getText().toString();

        switch (v.getId()) {
            case R.id.btn_cenerate:
                if (!str.isEmpty()) {
                    GenerateQrCodeDialog generateQrCodeDialog = new GenerateQrCodeDialog(this, str);
                    generateQrCodeDialog.setCancelable(false);
                    generateQrCodeDialog.show();
                } else {
                    Toast.makeText(this, "请输入要生成的内容", Toast.LENGTH_LONG).show();
                }
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
