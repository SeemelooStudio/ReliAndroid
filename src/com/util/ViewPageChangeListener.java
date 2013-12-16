package com.util;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.ui.R;



public class ViewPageChangeListener implements OnPageChangeListener {  
 	  
	
	 private View[] views;
	

	public ViewPageChangeListener(View[] indicatorViews)
	{
		this.views = indicatorViews;
	}
	
     @Override  
     public void onPageScrollStateChanged(int arg0) {  
         // TODO Auto-generated method stub  

     }  

     @Override  
     public void onPageScrolled(int arg0, float arg1, int arg2) {  
         // TODO Auto-generated method stub  

     }  

     @Override  
     public void onPageSelected(int arg0) {
		int focusedSize = 30;
		int normalSize = 20;
		
         for (int i = 0; i < views.length; i++) {  
        	 TextView view = (TextView)views[i];
             
             if (arg0 != i) { 
            	 view.setLayoutParams(new LayoutParams(normalSize,normalSize));
            	 view.setBackgroundResource(R.drawable.page_indicator);
            	 view.setText("");
             }  else {
            	 view.setLayoutParams(new LayoutParams(focusedSize,focusedSize));
            	 view.setBackgroundResource(R.drawable.page_indicator_focused);
            	 view.setText("" + (i + 1) );
             }
         }
     }  
 }  