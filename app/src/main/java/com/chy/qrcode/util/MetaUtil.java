package com.chy.qrcode.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 类名称：com.uc.addon.facebook.util
 * 类描述：
 * 创建人：rocky
 * 创建时间：2016/8/16 15:04
 * 修改人：
 * 修改时间：2016/8/16 15:04
 * 修改备注：
 */
public class MetaUtil {
    public static String getChannelType(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF/erweima_")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            String[] split = ret.split("_");
            if (split != null && split.length > 0) {
                Log.i("MetaUtil", "--------------s" + split[1]);
                return split[1];
            } else {
                return "erweima";
            }
        } catch (Exception e) {
            LogUtils.e("MetaUtil", "--" + e.toString());
        }
        return "erweima";
    }
}
