package com.util;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.ui.R;



public class ViewPageChangeListener implements OnPageChangeListener {  
 	  
	
	 private ImageView[] imageViews;
	

	public ViewPageChangeListener(ImageView[] images)
	{
		this.imageViews = images;
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
         for (int i = 0; i < imageViews.length; i++) {  
             imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
             
             if (arg0 != i) {  
                 imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
             }  
         }
     }  
 }  