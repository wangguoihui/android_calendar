package com.hz.android_calendar.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {
	
	static SimpleDateFormat  sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat  sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static SimpleDateFormat  sdf3 = new SimpleDateFormat("yyyy-MM-dd");
	public static Calendar getCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		return c;
	}
	public static Calendar getCalendar(String dateFormatString) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf1.parse(dateFormatString));
		} catch (ParseException e) {
			try {
				calendar.setTime(sdf2.parse(dateFormatString));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return calendar;
	}
	public static String getCurTime() {
		Calendar calendar = getCalendar();
		calendar.setTime(new Date());
		return sdf1.format(calendar.getTime());
	}
	/**
	 * 
	 * @return 当前 年
	 */
	public static String getCurrentYear() {
		return String.valueOf(getCalendar().get(Calendar.YEAR)); // 获取当前年份
	}

	/**
	 * 
	 * @return 当前 月
	 */
	public static String getCurrentMonth() {
		String month = String.valueOf(getCalendar().get(Calendar.MONTH) + 1);
		if(month.length() == 1){
			month = "0" + month;
		}
		return month;// 获取当前月份
	}

	/**
	 * 
	 * @return 当前 日
	 */
	public static String getCurrentDay() {
		return String.valueOf(getCalendar().get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
	}

	/**
	 * 
	 * @return 当前 小时
	 */
	public static String getCurrentHour() {
		return String.valueOf(getCalendar().get(Calendar.HOUR_OF_DAY));// 获取当前小时
	}
	/**
	 * 
	 * @return 当前 小时 加一个小时
	 */
	public static String getCurrentOneMoreHour() {
		Calendar calendar = getCalendar();
		calendar.add(Calendar.HOUR_OF_DAY,1);
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));// 加一个小时
	}
	/**
	 * 
	 * @return 当前 分钟
	 */
	public static String getCurrentMinuter() {
		return String.valueOf(getCalendar().get(Calendar.MINUTE));// 获取当前分钟
	}

	/**
	 * 
	 * @return 当前 秒
	 */
	public static String getCurrentSecond() {
		return String.valueOf(getCalendar().get(Calendar.SECOND));// 获取当前分钟
	}

	/**
	 * 
	 * @return 获取当前是星期几
	 */
	public static String getCurrentWeekDay() {
		String mWay = String.valueOf(getCalendar().get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return mWay;
	}
	/**
	 * 
	 * @return 获取当前是星期几
	 */
	public static String getCurrentWeekDay(Calendar calendar) {
		String mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return "星期" + mWay;
	}
	/**
	 * 获取给定时间"月日星期"字符串
	 * @param dateFormatString
	 * @return
	 */
	public static String getCldMonthDayWeek(String dateFormatString) {
		Calendar calendar = getCalendar(dateFormatString);
		String month = transTwoLength(String.valueOf(calendar.get(Calendar.MONTH)+1));
		String day = transTwoLength(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		String week = getCurrentWeekDay(calendar);
		return month+"月"+day+"号 "+week;
	}
    /**
     * 获取给定时间"时分"字符串
     * @param dateFormatString
     * @return
     */
	public static String getCldHourMinute(String dateFormatString) {
		Calendar calendar = getCalendar(dateFormatString);
		String hour = transTwoLength(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
		String minute = transTwoLength(String.valueOf(calendar.get(Calendar.MINUTE)));
		return hour+":"+minute;
	}
	/**
	 * 计算提醒时间
	 * @param remindStrategy
	 * @param bgTime
	 * @return
	 */
	public static String calculateRemindTime(String remindStrategy,String bgTime){
		Calendar calendar  = getCalendar(bgTime);
		if("01".equals(remindStrategy)){//提前一小时
			calendar.add(Calendar.MINUTE, -60);
			return sdf1.format(calendar.getTime());
		}else if("02".equals(remindStrategy)){//提前两小时
			calendar.add(Calendar.MINUTE, -120);
			return sdf1.format(calendar.getTime());
		}else if("03".equals(remindStrategy)){//提前一天
			calendar.add(Calendar.DATE, -1);
			return sdf1.format(calendar.getTime());
		}else if("04".equals(remindStrategy)){//提前两天
			calendar.add(Calendar.DATE, -2);
			return sdf1.format(calendar.getTime());
		}else{//开始时间即为提醒时间
			calendar.add(Calendar.MINUTE, +0);
			return sdf1.format(calendar.getTime());
		}
	}
	/**
	 * 计算间隔时间
	 * @param bgTime
	 * @param edTime
	 * @param dayFlag
	 * @param clickedDay
	 * @return
	 */
	public static String calculateDistTime(String bgTime, String edTime, 
			String dayFlag, String clickedDay) {
		if("".equals(bgTime) || bgTime == null || "".equals(edTime) 
				|| edTime == null) return "";
		String rightNow = getCurTime();
		String rightNowYMD = rightNow.substring(0, rightNow.indexOf(" "));
		String bgTimeYMD = bgTime.substring(0, bgTime.indexOf(" "));
		String edTimeYMD = edTime.substring(0, edTime.indexOf(" "));
		clickedDay = "".equals(clickedDay) ? rightNowYMD : clickedDay.replaceAll("\\.", "-");
		
//		Calendar cldbg  = getCalendar(bgTime);
		Calendar clded  = getCalendar(edTime);
//		String bgtime = sdf3.format(cldbg.getTime());
		String edtime = sdf3.format(clded.getTime());
		
		if(rightNowYMD.compareTo(edTimeYMD) > 0){
			return "已过期";
		}else if(bgTimeYMD.compareTo(rightNowYMD) > 0) {
			try {
				Date toDate = sdf3.parse(clickedDay);
				Date fromDate = sdf3.parse(rightNowYMD);
				long secondnum = (toDate.getTime() - fromDate.getTime());
				String dayrslt = transDay(secondnum);
				return dayrslt;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if((rightNowYMD.compareTo(bgTimeYMD) > 0 || rightNowYMD.compareTo(bgTimeYMD) == 0) &&
				(edtime.compareTo(rightNowYMD) > 0 || edtime.compareTo(rightNowYMD) == 0)) {
			try {
				Date fromDate = sdf1.parse(rightNow);
				Date toDate = sdf1.parse(bgTime);
				Date edDate = sdf1.parse(edTime);
				long secondnum = (toDate.getTime() - fromDate.getTime());
				long flag = (edDate.getTime() - fromDate.getTime());
				if(!"01".equals(dayFlag) && flag < 0){
					return "已过期";
				}else if(secondnum < 0){
					return "进行中";
				}else{
					String hourslt = transHour(secondnum);
					return hourslt;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "进行中";
	}
	/**
	 * 时间换算
	 * @param secondnum
	 * @return
	 */
	public static String transHour(long secondnum) {
		long hour = 60 * 60 * 1000;// 1个小时有多少毫秒
		long minute = 60 * 1000;// 1分钟有多少毫秒
		if(secondnum > minute && secondnum < hour){
			return secondnum / minute+"分钟后";
		}else if(secondnum >= hour){
			return secondnum / hour + "小时后";
		}else{
			return "进行中";
		}
	}
	/**
	 * 时间换算
	 * @param secondnum
	 * @return
	 */
	public static String transDay(long secondnum) {
		long day = 24 * 60 * 60 * 1000;// 1天有多少毫秒
		long daynum = secondnum / day;
		if(daynum < 30){
			return daynum+"天后";
		}else if(daynum >= 30 && daynum < 365){
			return daynum / 30 + "个月后";
		}else{
			return daynum / 365 + "年后";
		}
	}
	
	/**
	 * 
	 * @return 转化为两位 3 = > 03
	 */
	public static String transTwoLength(int numLength) {
		return (numLength > 9 ? numLength + "" : "0"+numLength + "");	
	}
	public static String transTwoLength(String str) {
		return (str.length() >= 2 ? str + "" : "0"+str);	
	}
	/**
	 * 时间比较
	 * @param bgtime
	 * @param edtime
	 * @return
	 */
	public static boolean compareDate(String bgtime, String edtime) {
		Date toDate = new Date();
		Date fromDate = new Date();
		long secondnum;
		try {
			fromDate = sdf2.parse(bgtime);
			toDate = sdf2.parse(edtime);
		} catch (ParseException e) {
			try {
				fromDate = sdf3.parse(bgtime);
				toDate = sdf3.parse(edtime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			secondnum = (toDate.getTime() - fromDate.getTime());
		}
		if(secondnum >= 0){
			return true;
		}
		return false;
	}
	/**
	 * 求计划还有几天发生
	 * 
	 * @param planTime
	 * @return
	 */
	public static String getInterval(String planTime) {
		String interval = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ParsePosition pos = new ParsePosition(0);
		Date planDate = (Date) sd.parse(planTime, pos);
		long time = planDate.getTime() - new Date().getTime();// 得出的时间间隔是毫秒
		if (time < 0) {
			time = Math.abs(time);
			return "已过" + getInfo(time);
		}
		return getInfo(time) + "后";
	}
	/**
	 * 求距离生日还有几天
	 * 
	 * @param birthday
	 * @return
	 * @throws ParseException
	 */
	public static String getBirthdays(String birthday) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(formatter.parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.set(Calendar.YEAR, getCalendar().get(Calendar.YEAR));

		Date birthDate = cal.getTime();
		Date currentDate = new Date();
		long days = getTimes(birthDate, currentDate);
		if (days < 0) {// 生日未到
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
			currentDate = cal.getTime();
			days = getTimes(birthDate, currentDate);
		}
		days = days + 1;
		if (days == 0) {
			return "今天";
		} else if (days == 1) {
			return "明天";
		}
		// else if (days == 2) {
		// return "后天";
		// }
		else {
			return days + "天后";
		}

	}

	private static long getTimes(Date birthDate, Date currentDate) {
		return (birthDate.getTime() - currentDate.getTime()) / (3600 * 24 * 1000);
	}

	private static String getInfo(long time) {
		String interval = "";
		long day = 24 * 60 * 60 * 1000;// 1天有多少毫秒
		long second10 = 10 * 1000;// 10秒有多少毫秒
		long hour1 = 60 * 60 * 1000;// 1个小时有多少毫秒
		long minuite1 = 60 * 1000;// 1分钟有多少毫秒
		if (time < second10) { // 时间间隔小于10秒
			interval = "现在";
		} else if (time < minuite1 && time >= second10) {// 时间间隔小于1分钟，大于十秒
			interval = "现在";
		} else if (time < hour1 && time >= minuite1) {// 时间间隔小于一小时，大于一分钟
			interval = (time / minuite1) + "分";
		} else if (time < day && time >= hour1) {// 时间间隔小于一天，大于一小时
			interval = (time / hour1) + "小时";
		} else if (time >= day) {// 时间间隔大于等于一天
			interval = (time / day) + "天";
		}
		return interval;
	}
}
