package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.achartengine.GraphicalView;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.chart.impl.WeatherPreChart;
import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
import com.model.WeatherPreChartItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;


public class WeatherDetailActivity extends Activity implements SearchView.OnQueryTextListener {


	private TabHost myTabhost;
	private TextView txtTilte;
	private TextView txtOneTab1;
	private TextView txtOneTab2;
	private TextView txtOneTab3;
	private TextView txtTwoTab1;
	private TextView txtTwoTab2;
	private TextView txtTwoTab3;
	
    private ListView oneTabListView;
    private ListView twoTabListView;
    private ListView fourTabListView; 
    
    private SearchView searchView;  
    
  
    private ArrayList<WeatherDetailItem>  oneTabDbDatalist = new ArrayList<WeatherDetailItem>(); 
    private ArrayList<WeatherDetailItem>  twoTabDbDatalist = new ArrayList<WeatherDetailItem>();
    private ArrayList<WeatherDetailItem>  fourTabDbDatalist = new ArrayList<WeatherDetailItem>();  
  
	private String strListId = "";
	private ProgressDialog diaLogProgress = null;
	private WeatherDetailTempInfo tabOneDetail = null;
	private WeatherDetailTempInfo tabTwoDetail = null;
	private List<HashMap<String, Object>> tabOneListData;
	private List<HashMap<String, Object>> tabTwoListData;
	private List<HashMap<String, Object>> tabFourListData;
	
	private List<WeatherPreChartItem> weatherChartLst = null;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.weather_detail);
	        
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
	        if (mBundle == null )  return;
	        
	        strListId = mBundle.getString("list_id");
		    if(strListId== null || strListId.length() <= 0) return;
		  
	        //tabhost
	        myTabhost = (TabHost) this.findViewById(R.id.weatherDetailsTabHost);  
	        myTabhost.setup();
	    	
	    	myTabhost.addTab(myTabhost.newTabSpec("One")
					.setIndicator("�����¶�",getResources().getDrawable(R.drawable.ic_launcher))
					.setContent(R.id.widget_layout_one));
			myTabhost.addTab(myTabhost.newTabSpec("Two").setIndicator("�����¶�",
									getResources().getDrawable(R.drawable.snow))
							.setContent(R.id.widget_layout_two));
			myTabhost.addTab(myTabhost.newTabSpec("Three").setIndicator("7���¶�",
									getResources().getDrawable(R.drawable.drizzle2))
							.setContent(R.id.widget_layout_three));
			myTabhost.addTab(myTabhost.newTabSpec("four").setIndicator("��ʷ��¼",
					getResources().getDrawable(R.drawable.sunny))
			.setContent(R.id.widget_layout_four));
			
			 //set tab font
			 TabWidget tabWidget= myTabhost.getTabWidget() ;
			 for (int i = 0; i < tabWidget.getChildCount(); i++) {
				TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title);
				tv.setGravity(BIND_AUTO_CREATE);
				tv.setPadding(0, 0,0, 0);
				tv.setTextSize(10);//��������Ĵ�С��
				tv.setTextColor(Color.BLACK);//�����������ɫ��
				//set tab image
				ImageView iv=(ImageView)tabWidget.getChildAt(i).findViewById(android.R.id.icon);
			}
	    	myTabhost.setCurrentTab(0);  
	    	
	    	//set title
			txtTilte = (TextView) findViewById(R.id.txtWeaherTitle);
			txtTilte.setText(mBundle.getString("list_name"));
			
			//init tab view data
			initTabViewData();
			
		
			
	 }
	 
	@Override  
    public boolean onQueryTextChange(String newText) {  
     	List<HashMap<String, Object>>  resultlst = searchHisItem(newText);  
        updateLayout(resultlst);  
        return false;  
     } 
	 
    @Override  
    public boolean onQueryTextSubmit(String query) {  
        // TODO Auto-generated method stub  
	   return false;  
    }  

	   
    /**
     * 
     */
	private void initTabViewData(){
		 
    	diaLogProgress = BaseHelper.showProgress(WeatherDetailActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                Message msgSend = new Message();
        	    try {
        	    	
        	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
        	    	
        	    	//get today weatherInfo
        	    	tabOneDetail = BusinessRequest.getWenduTabDetailById(strListId,"1");
        		 	tabOneListData = getWenduTabListData(strListId,"1");
        	    	
        		 	//get TheeTab Data
        		 	tabTwoDetail = BusinessRequest.getWenduTabDetailById(strListId,"0");
        		 	tabTwoListData = getWenduTabListData(strListId,"0");
        		 	
        		 	//get Theee Tab Data
        		 	weatherChartLst =  BusinessRequest.getWeatherChartList();
        		 	 
        		 	//get four tab data
        		 	tabFourListData =  getFourTabListData();
        		 	

        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
            	    oneTabhandler.sendMessage(msgSend);
            	}
        }.start();	 
	 }
	 
	 /**
	  * 
	  */
	 private Handler oneTabhandler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	
        		 	//set one tab
        		 	setOneTabData();
        		 	
        		 	//set two tab
        		 	setTwoTabData();
        		 	
        		 	//set three tab
        		 	setThreeWeatherChart();
        		 	
        		 	//set four tab
        		 	setFourTabData();
        		 	
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WeatherDetailActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	 
	  
	  /**
	   * 
	   * @return
	 * @throws JSONException 
	   */
	  private List<HashMap<String, Object>> getWenduTabListData(String strListId ,String dayFlag) throws JSONException {  
	   
	    
	  	ArrayList<WeatherDetailItem> wenduDbDatalist = new ArrayList<WeatherDetailItem>();
      
	  	tabOneDetail.setStrListId(strListId);
	  	tabOneDetail.setStrDayFlag(dayFlag);
	  	wenduDbDatalist = BusinessRequest.getWenduTabDetailListById(tabOneDetail);
	  	
		//getate
		List<HashMap<String, Object>> wenDuList = new ArrayList<HashMap<String, Object>>(); 
		for (WeatherDetailItem oneRec: wenduDbDatalist) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();  
		    item.put("w_time", oneRec.getW_time()); 
		    item.put("w_wendu", oneRec.getW_wendu()); 
		    item.put("w_tianqi", oneRec.getW_tianqi()); 
		    wenDuList.add(item);  
		}
		
		//return
		return wenDuList;
     } 
	 
	/**
	 * 
	 * @return
	 */
	private List<HashMap<String, Object>> getFourTabListData() {  
	   
		fourTabDbDatalist =  BusinessRequest.getWeatherHisListData();
		List<HashMap<String, Object>> tabFourDatalist = new ArrayList<HashMap<String, Object>>(); 
		for (WeatherDetailItem oneRec: fourTabDbDatalist) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();  
		    item.put("w_time", oneRec.getW_time()); 
			item.put("w_wendu", oneRec.getW_wendu()); 
			item.put("w_tianqi", oneRec.getW_tianqi()); 
			tabFourDatalist.add(item);  
		}
		
		return tabFourDatalist;
	}   
  
	  /**
	   * 
	   */
	 private void setOneTabData(){
	 
		oneTabListView = (ListView) findViewById(R.id.oneTabList);
		txtOneTab1 = (TextView) findViewById(R.id.txtOneTab1);
		txtOneTab2 = (TextView) findViewById(R.id.txtOneTab2);
		txtOneTab3 = (TextView) findViewById(R.id.txtOneTab3);
				
		//setDetails
		txtOneTab1.setText(tabOneDetail.getStrTemp());
		txtOneTab2.setText(tabOneDetail.getStrName());
		String strMsg  = "����¶ȣ�"+ tabOneDetail.getStrTiWen() + "\n";
		       strMsg += "�¶ȣ�"+ tabOneDetail.getStrWendu() + "\n";
		       strMsg += "���٣�"+ tabOneDetail.getStrFengsu() + "\n";
		       strMsg += "����"+ tabOneDetail.getStrFengxiang() + "\n";
		       strMsg += "����ʱ�䣺"+ tabOneDetail.getStrUpTime();
		txtOneTab3.setText(strMsg);
		
		//set temperature list
	 	oneTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),tabOneListData, R.layout.weather_detail_item,  
	 			 new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
	 	oneTabListView.setTextFilterEnabled(true); 
	 
	 
	 }
	 
	 /**
	  * 
	  */
	 private void setTwoTabData(){
		 
		 //��ȡ���ݲ����䵽listview��
		twoTabListView = (ListView) findViewById(R.id.twoTabList);
		txtTwoTab1 = (TextView) findViewById(R.id.txtTwoTab1);
		txtTwoTab2 = (TextView) findViewById(R.id.txtTwoTab2);
		txtTwoTab3 = (TextView) findViewById(R.id.txtTwoTab3);
		
		//�����������
		txtTwoTab1.setText(tabTwoDetail.getStrTemp());
		txtTwoTab2.setText(tabTwoDetail.getStrName());
		String strMsg  = "����¶ȣ�"+ tabTwoDetail.getStrTiWen() + "\n";
		       strMsg += "�¶ȣ�"+ tabTwoDetail.getStrWendu() + "\n";
		       strMsg += "���٣�"+ tabTwoDetail.getStrFengsu() + "\n";
		       strMsg += "����"+ tabTwoDetail.getStrFengxiang() + "\n";
		       strMsg += "����ʱ�䣺"+ tabTwoDetail.getStrUpTime();
		txtTwoTab3.setText(strMsg);
		
	    //����list����
		twoTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),tabTwoListData, R.layout.weather_detail_item,  
	 			 new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
		twoTabListView.setTextFilterEnabled(true); 
		 
	 }
	 
    /**
     * 
     */
    private void setThreeWeatherChart()
    {
        //set chartt
	    WeatherPreChart  weatherChart = new WeatherPreChart(weatherChartLst);
		LinearLayout layout = (LinearLayout) findViewById(R.id.weather_chart);
		GraphicalView mChartView = weatherChart.createChart(this);
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

    }
    
    /**
     * 
     */
	private void setFourTabData(){
		 
		fourTabListView = (ListView) findViewById(R.id.fourTabList);
		fourTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),tabFourListData, R.layout.weather_detail_item,  
				  new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
		fourTabListView.setTextFilterEnabled(true); 
		
		searchView = (SearchView) findViewById(R.id.wether_his_search);    
		searchView.setOnQueryTextListener(this);  
        searchView.setSubmitButtonEnabled(false); 
		 
	 }
	 
	
    /**
     * 
     * @param resultList
     */
	private void updateLayout(List<HashMap<String, Object>> resultList) {  
		fourTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),resultList, R.layout.weather_detail_item,  
				  new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
	} 
	
   /**
     * �������Ʋ�ѯ
     * @param name
     * @return
     */
   private List<HashMap<String, Object>> searchHisItem(String name) 
	{  
		List<HashMap<String, Object>> mSearchHisList = new ArrayList<HashMap<String, Object>>(); 
	    
	    for (int i = 0; i < fourTabDbDatalist.size(); i++) 
	    { 	
	        int index =((WeatherDetailItem) fourTabDbDatalist.get(i)).getW_time().indexOf(name);  
	        // ����ƥ�������  ������װList
		    if (index != -1) {
		    	HashMap<String, Object> item = new HashMap<String, Object>(); 
		    	item.put("w_time", fourTabDbDatalist.get(i).getW_time()); 
		        item.put("w_wendu", fourTabDbDatalist.get(i).getW_wendu()); 
		        item.put("w_tianqi", fourTabDbDatalist.get(i).getW_tianqi()); 
		        mSearchHisList.add(item);  
		    } 
	    }
	    
	    return mSearchHisList;
	}  
	 

}
