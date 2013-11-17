package com.util;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/***
 * 
 * @author zhaors
 *
 */
public class ViewPageAdapter extends PagerAdapter {

	
	private ArrayList<View> views;
	
	/**
	 * 
	 * @param viewList
	 */
	public ViewPageAdapter(ArrayList<View> viewList)
	{
		this.views = viewList;
	}
	
	@Override
	public int getCount() {

		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {

		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}
}

