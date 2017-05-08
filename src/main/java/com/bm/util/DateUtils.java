package com.bm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Administrator
 * @Description: 日期辅助类
 * @date 2017年5月6日 下午3:50:12 
 *
 */
public class DateUtils {
	public static final String FORMAT_DATE_TIME_DEFAULT="yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_FOR_DAY="yyyy-MM-dd";
    public static final String FORMAT_DATE_FOR_BORROWS="yyMMdd";
    public static final String FORMAT_DATE_FOR_YMD000="yyyy-MM-dd 00:00:00";
    public static final String FORMAT_DATE_CH="yyyy年MM月dd日";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECONDS = "ss";
    
    public static String formatDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public static String formatToday(String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	return sdf.format(new Date());
    }
}
