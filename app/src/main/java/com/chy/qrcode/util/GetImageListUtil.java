package com.chy.qrcode.util;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 47250 on 2017/6/23.
 */
public class GetImageListUtil {

    public static List<String> getAllImage() {
        List<String> cameraImagelist = getCameraImage();
        List<String> screenshotsImagelist = getScreenshotsImage();
        cameraImagelist.addAll(screenshotsImagelist);
        return cameraImagelist;
    }

    public static List<String> getCameraImage() {
        List<String> cameraImagelist = new ArrayList<>();
        File cameraDir = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM + "/Camera");
        String fileArray[] = cameraDir.list();
        if (null != fileArray) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].endsWith(".jpg") || fileArray[i].endsWith(".png")) {
                    cameraImagelist.add(cameraDir.getAbsolutePath() + "/" + fileArray[i]);
                    // LogUtils.e(cameraDir.getAbsolutePath() + "/" + fileArray[i]);
                }
            }
        }
        return cameraImagelist;
    }

    public static List<String> getScreenshotsImage() {
        List<String> screenshotsImagelist = new ArrayList<>();
        File screenshotsDir = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM + "/Screenshots/");
        String fileArray[] = screenshotsDir.list();
        if (null != fileArray) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].endsWith(".jpg") || fileArray[i].endsWith(".png")) {
                    screenshotsImagelist.add(screenshotsDir.getAbsolutePath() + "/" + fileArray[i]);
                }
            }
        }

        return screenshotsImagelist;
    }

    public static List<String> getBuildRecordImage() {
        List<String> erweimaImagelist = new ArrayList<>();
        File screenshotsDir = new File(Environment.getExternalStorageDirectory(), "/erweimaImg/");
        String fileArray[] = screenshotsDir.list();
        if (null != fileArray) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].endsWith(".jpg") || fileArray[i].endsWith(".png")) {
                    erweimaImagelist.add(screenshotsDir.getAbsolutePath() + "/" + fileArray[i]);
                }
            }
        }

        return erweimaImagelist;
    }
}
