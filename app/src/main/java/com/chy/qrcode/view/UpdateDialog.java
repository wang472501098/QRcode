package com.chy.qrcode.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;


import com.chy.qrcode.R;
import com.chy.qrcode.bean.UpdateApp;
import com.chy.qrcode.service.UpdateAppService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by wang on 2017/4/27.
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView mTvUpdateDec;
    private TextView mTvDmiss;
    private TextView mTvUpdate;
    private UpdateApp mUpdateAppBean;

    public UpdateDialog(Context context, UpdateApp updateAppBean) {
        super(context, R.style.customDialog);
        this.mContext = context;
        this.mUpdateAppBean = updateAppBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);

        mTvUpdateDec = (TextView) findViewById(R.id.tv_UpdateDec);

        try {
            mTvUpdateDec.setText(URLDecoder.decode(mUpdateAppBean.getVersionDesc(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        mTvDmiss = (TextView) findViewById(R.id.tv_Dmiss);
        mTvDmiss.setOnClickListener(this);
        mTvUpdate = (TextView) findViewById(R.id.tv_Update);
        mTvUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Dmiss:
                dismiss();
                break;
            case R.id.tv_Update:
                File file1 = new File(Environment.getExternalStorageDirectory().getPath(), "erweima.apk");
                if (file1.exists()) {
                    file1.delete();
                }
                mContext.startService(new Intent(mContext, UpdateAppService.class).putExtra("updateUrl", mUpdateAppBean.getDownloadUrl()));
                dismiss();
                break;
        }

    }
}
