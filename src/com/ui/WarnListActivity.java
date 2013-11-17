package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.model.WarnListItem;
import com.util.BaseHelper;

public class WarnListActivity extends Activity implements android.view.View.OnClickListener{

	private ListView listView;
	private Button   btnWarnRefresh;
	private Button   btnWarnShow;
	private Button   btnWarnEdit;


    private List<HashMap<String, Object>> listData; 
    private ArrayList<WarnListItem>  dbDatalist = new ArrayList<WarnListItem>();  
	  
	 @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.warn_list);
        
        //��ȡ���ݲ����䵽listview��
        listData = getListData();
        listView = (ListView) findViewById(R.id.list);  
        listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.warn_list_item,  
				  new String[] { "warn_id", "warn_title", "warn_content", "warn_date","warn_other"}, 
				  new int[] {R.id.warn_id, R.id.warn_title,R.id.warn_content,R.id.warn_date,R.id.warn_other}));
        //����¼�
        listView.setTextFilterEnabled(true); 
        listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
        	{   
        		//��ת����ϸ����
        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
        		Intent intent = new Intent(WarnListActivity.this, WarnDetailActivity.class); 
        		Bundle mBundle = new Bundle();
        		mBundle.putString("warn_id", ListItem.get("warn_id").toString());
        		mBundle.putString("warn_title", ListItem.get("warn_title").toString());
        		mBundle.putString("warn_date", ListItem.get("warn_date").toString());
        		mBundle.putString("warn_content", ListItem.get("warn_content").toString());
        		intent.putExtras(mBundle);
        		startActivity(intent);
        	}
        });
        
        
        btnWarnRefresh = (Button) findViewById(R.id.btnWarnRefresh);
        btnWarnShow = (Button) findViewById(R.id.btnWarnShow);
        btnWarnEdit = (Button) findViewById(R.id.btnWarnEdit);
        btnWarnRefresh.setOnClickListener((android.view.View.OnClickListener) this);
        btnWarnShow.setOnClickListener((android.view.View.OnClickListener) this);
        btnWarnEdit.setOnClickListener((android.view.View.OnClickListener) this);
	 }
	 
	 /*
     * ��������
     */
    public List<HashMap<String, Object>> getListData() {  
       
        //ԭʼ����
        dbDatalist = new ArrayList<WarnListItem>();
        for(int i=0; i<13; i++)
        {
        	WarnListItem  lst = new WarnListItem();
        	lst.setWarn_id("" +i);
        	lst.setWarn_title("���ׯ����վ�������ճ���" +i);
        	lst.setWarn_content("���ׯ����վ�������ճ���%10�����ׯ����վ�������ճ���%20" +i);
          	lst.setWarn_date("13:30");
        	lst.setWarn_other("image");
        	dbDatalist.add(lst);
        }
        
        //ƴװ����
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
        for (WarnListItem oneRec: dbDatalist) 
        {   
        	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("warn_id", oneRec.getWarn_id()); 
	        item.put("warn_title", oneRec.getWarn_title()); 
	        item.put("warn_content", oneRec.getWarn_content()); 
	        item.put("warn_date", oneRec.getWarn_date()); 
	        item.put("list_other", oneRec.getWarn_other()); 
	        data.add(item);  
        }
        
        //���ؽ��
        return data;
    }  
	  
    /*
     * ��ť�����¼�
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v)
   	{
   		if(R.id.btnWarnRefresh == v.getId())
   		{
   			BaseHelper.showDialog(this, "��Ϣ", "ˢ��");
   		}
   		else if(R.id.btnWarnShow == v.getId())
   		{
   			BaseHelper.showDialog(this, "��Ϣ", "�Ŵ�");
   		}
   		else if(R.id.btnWarnEdit == v.getId())
   		{
   			BaseHelper.showDialog(this, "��Ϣ", "�༭");
   		}
   	}
}
