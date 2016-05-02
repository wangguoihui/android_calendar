package com.hz.android_calendar.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.hz.android_calendar.activity.R;
import com.hz.android_calendar.utils.LunarCalendar;
import com.hz.android_calendar.utils.SpecialCalendar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Created by shuiyuev
 *
 */
@SuppressLint("SimpleDateFormat")
public class CalendarAdapter extends BaseAdapter {
	
	private boolean isLeapyear = false; //是否为闰年
	private int daysOfMonth = 0;       //某月的天数
	private int dayOfWeek = 0;        //具体某一天是星期几
	private int lastDaysOfMonth = 0; //上一个月的总天数
	private int nextDaysOfMonth = 0;//下一个月的总天数
	private Context context;
	public String[] dayNumber = new String[42];  //一个gridview中的日期存入此数组中
//	public List<CalendarItem>  dayNumber = new ArrayList<CalendarItem>(42);
	private SpecialCalendar sc = null;
	private LunarCalendar lc = null; 
	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int curPosition = -1;     //用于标记当天
	private int clickPosition = -1;  //用于标记点击位置
	private String showYear = "";   //用于在头部显示的年份
	private String showMonth = "";  //用于在头部显示的月份
	private String animalsYear = ""; 
	private String leapMonth = "";  //闰哪一个月
	private String cyclical = "";  //天干地支
	//系统当前时间
	private String sysDate = "";  
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private String clickedDay = "";
	//阳历，农历
	public TextView cldSolar, cldLunar;
	
	public CalendarAdapter(){
		Date date = new Date();
		sysDate = sdf.format(date); 
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
	}
	
	public CalendarAdapter(Context context,int jumpMonth,int day_c,String clickedDay){
		this();
		this.context= context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
//		this.clickedDay = clickedDay == null ? String.valueOf(day_c) : clickedDay;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, jumpMonth);
		int stepYear = calendar.get(Calendar.YEAR);
		int stepMonth = calendar.get(Calendar.MONTH) + 1;
		currentYear = String.valueOf(stepYear);;  //得到当前的年份
		currentMonth = String.valueOf(stepMonth); //得到本月 
		currentDay = String.valueOf(day_c);  //得到当前日期是哪天
		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));
	}
	
	//得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month){
		isLeapyear = sc.isLeapYear(year); //是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); //某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); //某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month-1); //上一个月的总天数
		nextDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month+1); //下一个月的总天数
		getweek(year,month);
	}
	
	private void getweek(int year, int month) {
		int j = 1;
		String lunarDay = "";
		String lunarMonth = "";
		//得到当前月的所有日程日期(这些日期需要标记)
		for (int i = 0; i < dayNumber.length; i++) {
			if(i < dayOfWeek){  //前一个月
				int temp = lastDaysOfMonth - dayOfWeek+1;
				lunarDay = lc.getLunarDate(year, month-1, temp+i,true); //2016.03.03修改为true
				lunarMonth = lc.getTransLunarMonth(); //农历月份
				dayNumber[i] = (temp + i)+"."+lunarDay+"."+lunarMonth;
			}else if(i < daysOfMonth + dayOfWeek){   //本月
				String day = String.valueOf(i-dayOfWeek+1); //得到的日期
				lunarDay = lc.getLunarDate(year, month, i-dayOfWeek+1,true);
				lunarMonth = lc.getTransLunarMonth();
				dayNumber[i] = i-dayOfWeek+1+"."+lunarDay+"."+lunarMonth;
				if(sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) 
						&& sys_day.equals(day)) {
					curPosition = i; //标记当前日期
					clickPosition = curPosition;
				}	
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
				setAnimalsYear(lc.animalsYear(year));
				setLeapMonth(lc.leapMonth == 0?"":String.valueOf(lc.leapMonth));
				setCyclical(lc.cyclical(year));
			}else{//下一个月
				lunarDay = lc.getLunarDate(year, month+1, j,true);
				lunarMonth = lc.getTransLunarMonth();
				dayNumber[i] = j+"."+lunarDay+"."+lunarMonth;
				j++;
			}
		}
	}
	@Override
	public int getCount() {
		return dayNumber.length;
	}
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
		}
		TextView itemtx = (TextView) convertView.findViewById(R.id.item_text);
		ImageView itemker = (ImageView) convertView.findViewById(R.id.item_marker);
		ImageView itemcur = (ImageView) convertView.findViewById(R.id.item_ring);
		String solarDay = dayNumber[position].split("\\.")[0];//阳历几号
		String lunarDay = dayNumber[position].split("\\.")[1];//农历几日
		String lunarMonth = dayNumber[position].split("\\.")[2]; //农历月份
		SpannableString sp = new SpannableString(solarDay+"\n"+lunarDay);
	
//		sp.setSpan(new StyleSpan(Color.parseColor("#444444")), 0, 
//			solarDay.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan(14,true), 0, 
			solarDay.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if(lunarDay != null ||lunarDay != ""){
//		    sp.setSpan(new StyleSpan(Color.parseColor("#999999")), solarDay.length()+1, 
//					dayNumber[position].length()-lunarMonth.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			sp.setSpan(new AbsoluteSizeSpan(8,true), solarDay.length()+1, 
					dayNumber[position].length()-lunarMonth.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		itemtx.setText(sp);
		itemtx.setTextColor(Color.BLACK);
		
		//两边颜色
		if ((position+1)%7 == 0 || (position+1)%7 == 1) {
			itemtx.setTextColor(Color.parseColor("#48BFEA"));
		}
		//不是当月
		if (position < dayOfWeek || position >= daysOfMonth + dayOfWeek) {
			itemtx.setTextColor(Color.GRAY);
		}
		//当天
		if(curPosition == position){ 
			if(itemClicked && curPosition != positionSelected){
				itemtx.setBackgroundColor(-1);
				itemker.setVisibility(View.INVISIBLE);
				itemcur.setVisibility(View.INVISIBLE);
			}else{
				itemtx.setBackgroundResource(R.drawable.cld_ring);
				itemker.setVisibility(View.VISIBLE);
				itemcur.setVisibility(View.VISIBLE);
			}
		}else{
			itemker.setVisibility(View.INVISIBLE);
			itemcur.setVisibility(View.INVISIBLE);
		}
		//选择某一天
		if(positionSelected == position && curPosition != positionSelected) {
			itemtx.setBackgroundResource(R.drawable.bg_gray);
		}else{
			itemtx.setBackgroundColor(-1);
		}
		return convertView;
	}
	
	public int positionSelected = -1;//没有点击
	public boolean itemClicked; //是否点击
	public void setItemClicked(int position) {
		itemClicked = true;
		positionSelected = position;
		notifyDataSetChanged();
	}
	public void setItemClicked() {
		itemClicked = true;
		positionSelected = clickPosition;
		notifyDataSetChanged();
	}
	
	/**
	 * 点击每一个item时返回item中的日期
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position){
		return dayNumber[position];
	}
	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * @return
	 */
	public int getStartPositon(){
		return dayOfWeek+7;
	}
	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 * @return
	 */
	public int getEndPosition(){
		return  (dayOfWeek+daysOfMonth+7)-1;
	}
	public String getShowYear() {
		return showYear;
	}
	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}
	public String getShowMonth() {
		return showMonth;
	}
	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}
	public String getAnimalsYear() {
		return animalsYear;
	}
	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}
	public String getLeapMonth() {
		return leapMonth;
	}
	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}
	public String getCyclical() {
		return cyclical;
	}
	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}
}














