package com.chy.qrcode.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chy.qrcode.R;
import com.chy.qrcode.ui.ChooseImageActivity;
import com.chy.qrcode.util.QrCodeSaveShareUtil;
import com.chy.qrcode.util.zxing.EncodingHandler;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wang on 2017/5/11.
 */

public class GenerateQrCodeDialog extends Dialog implements DialogInterface.OnDismissListener, View.OnClickListener {
    private String TAG = "GenerateQrCodeDialog";
    @Bind(R.id.iv_exit)
    ImageView ivExit;
    @Bind(R.id.fl_QrCode)
    FrameLayout flQrCode;
    @Bind(R.id.iv_QrCode)
    ImageView ivQrCode;
    @Bind(R.id.tv_ToUseThe)
    TextView tvToUseThe;
    @Bind(R.id.tv_GoToTheMap)
    TextView tvGoToTheMap;
    private Context mContext;
    private String mUri;
    private Bitmap qrCodeBitmap;

    public GenerateQrCodeDialog(Context context, String uri) {
        super(context, R.style.customDialog);
        this.mContext = context;
        this.mUri = uri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialig_generate_qrcode);
        ButterKnife.bind(this);
        BC_2weima(mUri);
        tvToUseThe.setOnClickListener(this);
        tvGoToTheMap.setOnClickListener(this);
        ivExit.setOnClickListener(this);

    }

    /**
     * 生成二维码
     */
    private void BC_2weima(String uri) {
        try {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            qrCodeBitmap = EncodingHandler.createQRCode(
                    uri, 600);
            ivQrCode.setImageBitmap(qrCodeBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        ButterKnife.unbind(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ToUseThe:
                QrCodeSaveShareUtil.saveImageToGallery(mContext, convertViewToBitmap(flQrCode));
                dismiss();
                break;
            case R.id.tv_GoToTheMap:
                dismiss();
                Intent intent = new Intent(mContext, ChooseImageActivity.class);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                convertViewToBitmap(flQrCode).compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                intent.putExtra("qrCodeBitmap", bitmapByte);
                mContext.startActivity(intent);
                break;
            case R.id.iv_exit:
                dismiss();
                break;


        }

    }

    private Bitmap convertViewToBitmap(View view) {
        view.buildDrawingCache();
        return view.getDrawingCache();
    }


}
