package com.hz.android_calendar.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Created by shuiyuev
 *
 */
public class CalendarItem implements Serializable {
	
	private static final long serialVersionUID = 1l;
	
	private String solarDay;
	private String lunarDay;
	private String lunarMonth;
	private List<CalendarItem> itemList;
	
	public String getSolarDay() {
		return solarDay;
	}
	public void setSolarDay(String solarDay) {
		this.solarDay = solarDay;
	}
	public String getLunarDay() {
		return lunarDay;
	}
	public void setLunarDay(String lunarDay) {
		this.lunarDay = lunarDay;
	}
	public String getLunarMonth() {
		return lunarMonth;
	}
	public void setLunarMonth(String lunarMonth) {
		this.lunarMonth = lunarMonth;
	}
	public List<CalendarItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<CalendarItem> itemList) {
		this.itemList = itemList;
	}
	
}
