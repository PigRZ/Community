package com.pigrz.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtil {
    public static Timestamp getCurTime(){
        Timestamp time = new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
        System.out.println(time);
        return time;
    }
}
