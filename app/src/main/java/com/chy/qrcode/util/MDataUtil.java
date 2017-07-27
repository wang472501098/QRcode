package com.chy.qrcode.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 47250 on 2017/6/27.
 */
public class MDataUtil {
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
