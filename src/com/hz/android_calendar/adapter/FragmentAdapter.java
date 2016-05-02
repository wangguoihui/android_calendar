package com.hz.android_calendar.adapter;

import com.hz.android_calendar.fragment.CalendarFragment;
import com.hz.android_calendar.view.JazzyViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * Created by shuiyuev
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private JazzyViewPager mJazzy;

    public FragmentAdapter(FragmentManager fm, JazzyViewPager mJazzy) {
		super(fm);
		this.mJazzy = mJazzy;
	}
    
    @Override
	public Fragment getItem(int position) {
    	CalendarFragment fragment = new CalendarFragment();
    	fragment.setData(position);
    	mJazzy.setObjectForPosition(fragment, position);
   		return fragment;
	}
    
    @Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    /**
     * 重写该方法，为了刷新页面
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
    	return PagerAdapter.POSITION_NONE;
    }
}













