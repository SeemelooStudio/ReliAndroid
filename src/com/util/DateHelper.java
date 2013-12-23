package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

public class DateHelper {
	public static String getDayOfWeek(String strDate, String strFormat, Context cntApplication) {
		String weekDay = "";
		Calendar calendar = Calendar.getInstance();
		Date parsedDate = parseStringToDate(strDate, strFormat);
		calendar.setTime(parsedDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Resources resources =  cntApplication.getResources();
		
		if (Calendar.MONDAY == dayOfWeek) {
			weekDay = resources.getString(R.string.monday);
	    } else if (Calendar.TUESDAY == dayOfWeek) {
	        weekDay = resources.getString(R.string.tuesday);
	    } else if (Calendar.WEDNESDAY == dayOfWeek) {
	        weekDay = resources.getString(R.string.wednesday);
	    } else if (Calendar.THURSDAY == dayOfWeek) {
	    	weekDay = resources.getString(R.string.thursday);
	    } else if (Calendar.FRIDAY == dayOfWeek) {
	    	weekDay = resources.getString(R.string.friday);
	    } else if (Calendar.SATURDAY == dayOfWeek) {
	    	weekDay = resources.getString(R.string.saturday);
	    } else if (Calendar.SUNDAY == dayOfWeek) {
	    	weekDay = resources.getString(R.string.sunday);
	    }
		
		return weekDay;
	}
	public static String getShortDate(String strDate, String strFormat, Context cntApplication) {
		String formatedDate = "";
		Date parsedDate = parseStringToDate(strDate, strFormat);
		formatedDate = getShortDate(parsedDate, cntApplication);
		
		return formatedDate;
	}
	public static String getShortDate(Date date, Context cntApplication) {
		String formatedDate = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		Resources resources =  cntApplication.getResources();
		
		formatedDate = ( month + 1 ) + resources.getString(R.string.month) 
				+  dayOfMonth + resources.getString(R.string.day);
		
		return formatedDate;
	}
	public static String getFullDate(String strDate, String strFormat, Context cntApplication) {
		String formatedDate = "";
		Calendar calendar = Calendar.getInstance();
		Date parsedDate = parseStringToDate(strDate, strFormat);
		calendar.setTime(parsedDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		Resources resources =  cntApplication.getResources();
		
		formatedDate = year + resources.getString(R.string.year) 
				+ ( month + 1 ) + resources.getString(R.string.month) 
				+  dayOfMonth + resources.getString(R.string.day);
		
		return formatedDate;
	}
	@SuppressLint("SimpleDateFormat")
	public static Date parseStringToDate(String strDate, String strFormat) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(strFormat);
		Date parsedDate;
		try {
			parsedDate = simpledateformat.parse(strDate);
		} catch ( Exception e ) {
			return null;
		}
	    return parsedDate;
	}
	
	public static String getDayOfWeekInChinese(Date date) {
		Calendar c =Calendar.getInstance();  
		c.setTime(date);
		return c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
	}
}
