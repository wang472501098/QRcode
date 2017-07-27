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
import com.chy.qrcode.adapter.SweepRecordAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wang on 2017/5/11.
 */

public class SweepContextDialog extends Dialog implements DialogInterface.OnDismissListener, View.OnClickListener {
    @Bind(R.id.tv_sweep_share)
    TextView mTvSweepShare;
    @Bind(R.id.tv_sweep_copy)
    TextView mTvSweepCopy;
    @Bind(R.id.tv_sweep_delete)
    TextView mTvSweepDelete;
    private String TAG = "GenerateQrCodeDialog";

    private Context mContext;
    private long mId;
    private String mSweepContext;
    private SweepRecordAdapter mSweepRecordAdapter;

    public SweepContextDialog(Context context, long id, String sweepContext, SweepRecordAdapter sweepRecordAdapter) {
        super(context, R.style.customDialog);
        this.mContext = context;
        this.mId = id;
        this.mSweepContext = sweepContext;
        this.mSweepRecordAdapter = sweepRecordAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialig_sweep_context);
        ButterKnife.bind(this);
        mTvSweepShare.setOnClickListener(this);
        mTvSweepCopy.setOnClickListener(this);
        mTvSweepDelete.setOnClickListener(this);

    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        ButterKnife.unbind(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sweep_share:
                sweepShare();
                dismiss();
                break;
            case R.id.tv_sweep_copy:
                sweepCopy();
                dismiss();
                break;
            case R.id.tv_sweep_delete:
                sweepDelete();
                dismiss();
                break;
        }
    }

    private void sweepDelete() {
        Mapp.getInstances().getDaoSession().getSWEEP_RECORDDao().deleteByKey(mId);
        mSweepRecordAdapter.setImageViewData(Mapp.getInstances().getDaoSession().getSWEEP_RECORDDao().loadAll());
    }

    private void sweepCopy() {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(mSweepContext);
        Toast.makeText(mContext, "内容已添加到剪贴板", Toast.LENGTH_LONG).show();
    }

    private void sweepShare() {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, mSweepContext);
        mContext.startActivity(Intent.createChooser(textIntent, "分享"));
    }
}
