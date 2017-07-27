package com.chy.qrcode.view;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chy.qrcode.Mapp;
import com.chy.qrcode.R;
import com.chy.qrcode.adapter.BuildRecordAdapter;
import com.chy.qrcode.adapter.SweepRecordAdapter;
import com.chy.qrcode.util.GetImageListUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wang on 2017/5/11.
 */

public class BuildContextDialog extends Dialog implements DialogInterface.OnDismissListener, View.OnClickListener {
    @Bind(R.id.tv_build_delete)
    TextView mTvBuildDelete;

    private String TAG = "GenerateQrCodeDialog";

    private Context mContext;
    private String mBuildContext;
    private BuildRecordAdapter mBuildRecordAdapter;

    public BuildContextDialog(Context context, String buildContext, BuildRecordAdapter buildRecordAdapter) {
        super(context, R.style.customDialog);
        this.mContext = context;
        this.mBuildContext = buildContext;
        this.mBuildRecordAdapter = buildRecordAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialig_build_context);
        ButterKnife.bind(this);
        mTvBuildDelete.setOnClickListener(this);

    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        ButterKnife.unbind(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_build_delete:
                sweepDelete();
                dismiss();
                break;

        }
    }


    private void sweepDelete() {
        new File(mBuildContext).delete();
        mBuildRecordAdapter.setImageViewData(GetImageListUtil.getBuildRecordImage());
    }
}
