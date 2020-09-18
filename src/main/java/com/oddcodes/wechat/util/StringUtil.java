package com.oddcodes.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author dean.lee
 */
public class StringUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String currentTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String currentDate(){
        return sdf.format(new Date());
    }
}
