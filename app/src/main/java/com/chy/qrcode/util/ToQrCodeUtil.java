package com.chy.qrcode.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.chy.qrcode.util.zxing.EncodingHandler;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wang on 2017/5/10.
 */

public class ToQrCodeUtil {
    private static String TAG = "ToQrCodeUtil";

    /**
     * 生成二维码
     */
    public static void BC_2weima(String uri, Context context) {
        try {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
                    uri, 600);
            saveImageToGallery(context, qrCodeBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveImageToGallery(Context context, Bitmap bitMap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM + "/Screenshots/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "erweima.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            // 最后通知图库更新
            LogUtils.e(TAG, System.currentTimeMillis() + "");
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
