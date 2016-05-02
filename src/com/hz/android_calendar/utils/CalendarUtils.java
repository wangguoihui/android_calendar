package com.hz.android_calendar.utils;

import java.util.Calendar;

public class CalendarUtils {
	
	public CalendarUtils() {
		super();
	}

	public static String position2Year(int position){
    	Calendar calendar = TimeUtils.getCalendar();
    	int jumpMonth = position - 500;
		calendar.add(Calendar.MONTH, jumpMonth);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
    public static String position2Month(int position){
    	Calendar calendar = TimeUtils.getCalendar();
    	int jumpMonth = position - 500;
		calendar.add(Calendar.MONTH, jumpMonth);
        return TimeUtils.transTwoLength(calendar.get(Calendar.MONTH)+1);
    }
}














