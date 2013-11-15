package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.model.ListItem;

/***
 * 
 * @author zhaors
 *
 */
public class DailyListActivity extends Activity implements  SearchView.OnQueryTextListener{

	    private ListView listView;  
	    private SearchView searchView;  
	    private List<HashMap<String, Object>> listData; 
	    private ArrayList<ListItem>  dbDatalist = new ArrayList<ListItem>();  
	  
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
    	    super.onCreate(savedInstanceState);
            setContentView(R.layout.daily_list);
            
            //初始化标题栏
            this.getActionBar().setDisplayShowHomeEnabled(false);  
	        this.getActionBar().setDisplayShowTitleEnabled(false);  
	        this.getActionBar().setDisplayShowCustomEnabled(true);  
	        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	        View mTitleView = mInflater.inflate(R.layout.daily_list_search, null);  
	        getActionBar().setCustomView(mTitleView,  
	                new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));  
	        searchView = (SearchView) mTitleView.findViewById(R.id.daily_search_view);    
	        
	        //获取数据并适配到listview中
	        listData = getListData();
	        listView = (ListView) findViewById(R.id.list);  
	        listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.list_item,  
					  new String[] { "list_id", "list_name", "list_other" }, 
					  new int[] {R.id.list_id, R.id.list_name, R.id.list_other}));
	        
	        //添加事件
	        listView.setTextFilterEnabled(true); 
	        searchView.setOnQueryTextListener(this);  
	        searchView.setSubmitButtonEnabled(false);
	        listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
	        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
	        	{   
	        		//跳转到详细画面
	        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
	        		Intent intent = new Intent(DailyListActivity.this, DailyDetailActivity.class); 
	        		Bundle mBundle = new Bundle();
	        		mBundle.putString("list_id", ListItem.get("list_id").toString());
	        		mBundle.putString("list_name", ListItem.get("list_name").toString());
	        		intent.putExtras(mBundle);
	        		startActivity(intent);
	        	}
            });
	        

	    }  
	  
	    /*
	     * 加载数据
	     */
	    public List<HashMap<String, Object>> getListData() {  
	       
	        //原始数据
	        dbDatalist = new ArrayList<ListItem>();
	        for(int i=0; i<10; i++)
	        {
	        	ListItem  lst = new ListItem();
	        	lst.setList_id("" +i);
	        	lst.setList_name("name" +i);
	        	lst.setList_other("other" +i);
	        	dbDatalist.add(lst);
	        }
	        
	        //拼装数据
	        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
	        for (ListItem oneRec: dbDatalist) 
	        {   
	        	HashMap<String, Object> item = new HashMap<String, Object>();  
		        item.put("list_id", oneRec.getList_id()); 
		        item.put("list_name", oneRec.getList_name()); 
		        item.put("list_other", oneRec.getList_other()); 
		        data.add(item);  
	        }
	        
	        //返回结果
	        return data;
	    }  
	  
	
	    @Override  
	    public boolean onQueryTextChange(String newText) {  
	    	List<HashMap<String, Object>>  resultlst = searchItem(newText);  
	        updateLayout(resultlst);  
	        return false;  
	    }  
	  
	    @Override  
	    public boolean onQueryTextSubmit(String query) {  
	        // TODO Auto-generated method stub  
	        return false;  
	    }  

	    /**
	     * 根据名称查询
	     * @param name
	     * @return
	     */
	    public List<HashMap<String, Object>> searchItem(String name) 
	    {  
	    	List<HashMap<String, Object>> mSearchList = new ArrayList<HashMap<String, Object>>(); 
	        
	        for (int i = 0; i < dbDatalist.size(); i++) 
	        { 	
	            int index =((ListItem) dbDatalist.get(i)).getList_name().indexOf(name);  
	            
	            // 存在匹配的数据  重新组装List
	            if (index != -1) {
	            	HashMap<String, Object> item = new HashMap<String, Object>();  
	     	        item.put("list_id", dbDatalist.get(i).getList_id()); 
	     	        item.put("list_name", dbDatalist.get(i).getList_name()); 
	     	        item.put("list_other", dbDatalist.get(i).getList_other()); 
	     	        mSearchList.add(item);  
	            }  
	        }  
            
	        //返回结果
	        return mSearchList;  
	    }  
	  
	    
	    /**
	     * 重置ListView值
	     * @param resultList
	     */
	    public void updateLayout(List<HashMap<String, Object>> resultList) {  
	    	  listView.setAdapter(new SimpleAdapter(getApplicationContext(),resultList, R.layout.list_item,  
					  new String[] { "list_id", "list_name", "list_other" }, 
					  new int[] {R.id.list_id, R.id.list_name, R.id.list_other})); 
	    }  
	      

	}  