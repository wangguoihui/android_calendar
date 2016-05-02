package com.hz.android_calendar.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import com.hz.android_calendar.adapter.FragmentAdapter;
import com.hz.android_calendar.fragment.CalendarFragment;
import com.hz.android_calendar.utils.CalendarUtils;
import com.hz.android_calendar.utils.LunarCalendar;
import com.hz.android_calendar.utils.TimeUtils;
import com.hz.android_calendar.view.JazzyViewPager;
import com.hz.android_calendar.view.JazzyViewPager.TransitionEffect;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Created by shuiyuev
 *
 */
@SuppressLint("SimpleDateFormat")
public class MainActivity extends FragmentActivity implements OnClickListener,
		CalendarFragment.OnCldItemSelectedListener {

	private TextView cldSolar,cldLunar;
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	CalendarFragment calendarComponent = new CalendarFragment();
	private String selectedDate;
	private String day;
	private JazzyViewPager mJazzy;
	private FragmentAdapter adapter;
	private static int mCurrentIndex = 500;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.activity_main);
		setupJazziness(TransitionEffect.CubeOut);
		
		initView();
		
		initData();
	}
	
	@SuppressWarnings("deprecation")
	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.calendar_view);
		mJazzy.setTransitionEffect(effect);
		adapter = new FragmentAdapter(this.getSupportFragmentManager(), mJazzy);
		mJazzy.setAdapter(adapter);
		mJazzy.setCurrentItem(mCurrentIndex);
		mJazzy.setOnPageChangeListener(new OnPageChangeListener() {//监听滑动事件
			@Override
			public void onPageSelected(int position) {
				String year = CalendarUtils.position2Year(position);
				String month = CalendarUtils.position2Month(position);
				String dotSolar = year + "." + month + "." + day;
				String lineSolar = year + "-" + month + "-" + day;
				LunarCalendar lc = new LunarCalendar();
				String lunarDay = lc.getLunarDate(Integer.parseInt(year),
						Integer.parseInt(month),Integer.parseInt(day),true);
				String lunarMonth = lc.getTransLunarMonth();//农历月份
				String strLunar = "("+ lunarMonth + lunarDay + ")";

				addTextToTopTextView(dotSolar, day, strLunar);//刷新阳历、农历
			}
			@Override
			public void onPageScrolled(int i, float f, int j) {
			}
			@Override
			public void onPageScrollStateChanged(int i) {
			}
		});
	}

	public void initView() {
		cldSolar = (TextView) findViewById(R.id.cld_solar);
		cldLunar = (TextView) findViewById(R.id.cld_lunar);
	}
	
	public void initData() {
		Calendar calendar = TimeUtils.getCalendar();
		calendar.setTime(new Date());
		selectedDate = sdf1.format(calendar.getTime());
		String yyyy = String.valueOf(calendar.get(Calendar.YEAR));
		String mm = TimeUtils.transTwoLength(calendar.get(Calendar.MONTH)+1);
		String dd = TimeUtils.transTwoLength(calendar.get(Calendar.DAY_OF_MONTH));
		day = dd;
		String ymd = yyyy+"."+mm+"."+dd;
		
		LunarCalendar lc = new LunarCalendar();
		String lunarDay = lc.getLunarDate(Integer.parseInt(yyyy),Integer.parseInt(mm),Integer.parseInt(dd),true);
		String lunarMonth = lc.getTransLunarMonth();//农历月份
		String strLunar = "("+ lunarMonth + lunarDay + ")";
		addTextToTopTextView(ymd, day, strLunar);
	}

	@Override
	public void addTextToTopTextView(String dotSolar, String clickedDay, String strLunar){
		day = clickedDay;
		cldSolar.setText(dotSolar);
		cldLunar.setText(strLunar);
	}

	@Override
	public void onClick(View v) {
	}
	
	@Override
	public void onCldItemSelected(String lineSolar) {
	}

}














