package com.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class HotSourceDetailActivity  extends Activity {

	  @Override                                                                                            
	    public void onCreate(Bundle savedInstanceState) {                                                    
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE); 
			setContentView(R.layout.hot_source_detail);   
			
			
	  }
}
