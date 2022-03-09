package com.example.coindesk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class DateTimeUtils {

    public static String convertDateText(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);
    }
}
