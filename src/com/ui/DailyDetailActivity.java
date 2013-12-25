package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.model.DailyDetailItem;

public class DailyDetailActivity extends Activity {

	 private TextView labSubTitle;
	 private String ListId;
	 private ListView listDelView;  
	 private List<HashMap<String, Object>> listData; 
	 private ArrayList<DailyDetailItem>  dbDatalist = new ArrayList<DailyDetailItem>();  
	  
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.daily_detail);
		Intent inten = this.getIntent();
		Bundle mBundle = inten.getExtras();
		if (mBundle != null ) 
		{
			ListId = mBundle.getString("list_id");
		    labSubTitle  = (TextView) findViewById(R.id.labsubTitle);  
		    labSubTitle.setText(mBundle.getString("list_name"));
		}
		
		listData = this.getDataByListId(ListId);
		listDelView = (ListView) findViewById(R.id.daily_dellist);  
		listDelView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.daily_detail_item,  
				  new String[] { "fct_name", "is_join", "all_num", "on_num"  }, 
				  new int[] {R.id.fct_name, R.id.is_join, R.id.all_num,R.id.on_num}));
		
		listDelView.setTextFilterEnabled(true); 
	 }
	 
	 private List<HashMap<String, Object>> getDataByListId(String strListId) {  
	        dbDatalist = new ArrayList<DailyDetailItem>();
	        for(int i=0; i<20; i++)
	        {
	        	DailyDetailItem  delLst = new DailyDetailItem();
	        	if(i == 0){
	        		delLst.setFct_name("������");
		        	delLst.setIs_join("�ȵ�����");
		        	delLst.setAll_num("��ˮ¯̨��");
		        	delLst.setOn_num("����̨��");
	        	}
	        	else
	        	{
		        	delLst.setFct_name("" +i);
		        	delLst.setIs_join("Y");
		        	delLst.setAll_num("" +i+1);
		        	delLst.setOn_num("" +i+1);
	        	}
	        	dbDatalist.add(delLst);
	        }
	        
	        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
	        for (DailyDetailItem oneRec: dbDatalist) 
	        {   
	        	HashMap<String, Object> item = new HashMap<String, Object>();  
		        item.put("fct_name", oneRec.getFct_name()); 
		        item.put("is_join", oneRec.getIs_join()); 
		        item.put("all_num", oneRec.getAll_num());
		        item.put("on_num", oneRec.getOn_num()); 
		        data.add(item);  
	        }
	        
	        return data;
	    }
	 
}
