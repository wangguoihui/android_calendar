package com.hz.android_calendar.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hz.android_calendar.activity.R;
import com.hz.android_calendar.adapter.CalendarAdapter;
import com.hz.android_calendar.utils.TimeUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * Created by shuiyuev
 * 
 *
 */
@SuppressLint("SimpleDateFormat")
public class CalendarFragment extends Fragment {

	private CalendarAdapter calendarAdapter = null;
	private View calendar = null;
	private GridView gridView = null;
	private String dotSolar, strLunar;
	StringBuffer solarDate = new StringBuffer();
	private int day_c = 0;
	private String currentDate = "";
	private String clickedDay;
	private Context context;
	private int pagePosition;
	
	OnCldItemSelectedListener mCallback;
    public interface OnCldItemSelectedListener {
        public void onCldItemSelected(String lineSolar);
		public void addTextToTopTextView(String dotSolar, String clickedDay, String strLunar);    
    }
	@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCldItemSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnCldItemSelectedListener");
        }
    }
	/**
	 * 初始化数据
	 * @param pagePosition
	 */
	public void setData(int pagePosition) {
		this.pagePosition = pagePosition - 500;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    	currentDate = sdf.format(date);
    	day_c = Integer.parseInt(currentDate.split("-")[2]);
	 }
	/**
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		calendar = inflater.inflate(R.layout.calendar_main, null);
		calendarAdapter = new CalendarAdapter(context, pagePosition, day_c, clickedDay);
        addGridView();
        gridView.setAdapter(calendarAdapter);
        calendarAdapter.setItemClicked();
		return calendar;
	}
	/**
	 * 创建gridView
	 */
	private void addGridView() {
		gridView = (GridView) calendar.findViewById(R.id.calendar_gridview);       
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				  //点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				  int startPosition = calendarAdapter.getStartPositon();
				  int endPosition = calendarAdapter.getEndPosition();
				  clickedDay = calendarAdapter.dayNumber[position].split("\\.")[0];
				  if(startPosition <= position+7  && position <= endPosition-7){ 
					  calendarAdapter.setItemClicked(position);
					  String solarYear = calendarAdapter.getShowYear();
	                  String solarMonth = calendarAdapter.getShowMonth();
					  String solarDay = calendarAdapter.getDateByClickItem(position).split("\\.")[0];  //这一天的阳历天
					  String lunarDay = calendarAdapter.getDateByClickItem(position).split("\\.")[1];  //这一天的阴历天
					  String LunarMonth = calendarAdapter.getDateByClickItem(position).split("\\.")[2];  //这一天的阴历月
	                  if(LunarMonth.equals(lunarDay) || "一月".equals(lunarDay)||
	                		  		"十一月".equals(lunarDay) ||"十二月".equals(lunarDay)){
	                	  lunarDay = "初一";
	                  }
					  dotSolar = solarYear + "." +
			                   TimeUtils.transTwoLength(solarMonth) + "." +
			                   TimeUtils.transTwoLength(solarDay);
					  strLunar = "(" + LunarMonth + lunarDay + ")";  
					  mCallback.addTextToTopTextView(dotSolar, TimeUtils.transTwoLength(solarDay), strLunar);
//	                  mCallback.onCldItemSelected(solarYear+"-"+
//	                		  TimeUtils.transTwoLength(solarMonth)+"-"+
//	                		  TimeUtils.transTwoLength(solarDay));
				  }
			}
		});
	}
}





















