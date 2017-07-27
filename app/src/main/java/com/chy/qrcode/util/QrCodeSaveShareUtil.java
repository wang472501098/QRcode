package com.chy.qrcode.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wang on 2017/5/10.
 */

public class QrCodeSaveShareUtil {
    private static String TAG = "QrCodeSaveShareUtil";


    public static void saveImageToGallery(Context context, Bitmap bitMap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "erweimaImg");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        LogUtils.e(fileName);
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            shareSingleImage(context,file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //分享单张图片  
    public static void shareSingleImage(Context context,String imagePath) {
        LogUtils.e(imagePath);
        //由文件得到uri  
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


}
