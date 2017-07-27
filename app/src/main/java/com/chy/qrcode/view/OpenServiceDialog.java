package com.chy.qrcode.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chy.qrcode.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/27.
 */
public class OpenServiceDialog extends Dialog implements DialogInterface.OnDismissListener, View.OnClickListener {
    @Bind(R.id.btn_openService)
    TextView mBtntnOpenService;
    private Context mContext;

    public OpenServiceDialog(Context context) {
        super(context, R.style.customDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialig_open_service);
        ButterKnife.bind(this);
        mBtntnOpenService.setOnClickListener(this);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_openService:
                Intent serviceIntent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                mContext.startActivity(serviceIntent);
                dismiss();
                break;
        }

    }
}
