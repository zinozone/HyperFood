package com.project.hyperfood.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static final int MORNING = 1;
    public static final int NOON = 2;
    public static final int EVENING = 3;
    public static final int NIGHT = 4;


    public static String getCurrentDateTime(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(new Date());
    }

    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(new Date());
    }

    public static String getDateSaveFood(){
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        return format.format(new Date());
    }

    public static String getTimeSaveFood(){
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        return format.format(new Date());
    }

    public static int getDayTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(format.format(new Date()));
        if (hour >= 2 && hour < 12){
            return MORNING;
        }else if (hour >= 12 && hour < 17){
            return NOON;
        }else if (hour >= 17 && hour < 22){
            return EVENING;
        }else {
            return NIGHT;
        }
    }
}
