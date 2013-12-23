package com.util;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
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
		int focusedSize = 40;
		int normalSize = 20;
		
		for (int i = 0; i < views.length; i++) {  
       	 TextView indicator = (TextView)views[i];
            
            if (arg0 == i) {
            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusedSize,focusedSize);
            	params.setMargins(0, 0, 10, 0);
            	indicator.setLayoutParams(params);
            	indicator.setText(i + 1 + "");
            	indicator.setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(normalSize,normalSize);
            	params.setMargins(0, 0, 10, 0);
            	indicator.setLayoutParams(params);
            	indicator.setText("");
            	indicator.setBackgroundResource(R.drawable.page_indicator);  
            }  
        }
     }  
 }  