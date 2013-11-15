package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.model.WeatherDetailItem;
import com.util.BaseHelper;


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
    
    private List<HashMap<String, Object>> oneTabListData;
    private List<HashMap<String, Object>> twoTabListData;
    private List<HashMap<String, Object>>  fourTabListData;
    private ArrayList<WeatherDetailItem>  oneTabDbDatalist = new ArrayList<WeatherDetailItem>(); 
    private ArrayList<WeatherDetailItem>  twoTabDbDatalist = new ArrayList<WeatherDetailItem>();
    private ArrayList<WeatherDetailItem>  fourTabDbDatalist = new ArrayList<WeatherDetailItem>();  
  
    
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.weather_detail);
	        
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
	        if (mBundle == null )  return;
	        
	        //��tabhost
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
			
			 //����ѡ�������
			 TabWidget tabWidget= myTabhost.getTabWidget() ;
			 for (int i = 0; i < tabWidget.getChildCount(); i++) {
				TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title);
				tv.setGravity(BIND_AUTO_CREATE);
				tv.setPadding(0, 0,0, 0);
				tv.setTextSize(10);//��������Ĵ�С��
				tv.setTextColor(Color.BLACK);//�����������ɫ��
				//��ȡtabsͼƬ��
				ImageView iv=(ImageView)tabWidget.getChildAt(i).findViewById(android.R.id.icon);
			}
	    	myTabhost.setCurrentTab(0);  
	    	
	    	//���ñ���
			txtTilte = (TextView) findViewById(R.id.txtWeaherTitle);
			txtTilte.setText(mBundle.getString("list_name"));
			
			//����tab1��������
			this.setOneTabDate();
			this.setTwoTabDate();
			this.setThreeWeatherChart();
			this.setFourTabDate();
			
	 }
	 
	 
	 /**
	  * ���õ�һ��tab��������
	  */
	 private void setOneTabDate(){
		 
		 //��ȡ���ݲ����䵽listview��
		oneTabListView = (ListView) findViewById(R.id.oneTabList);
		txtOneTab1 = (TextView) findViewById(R.id.txtOneTab1);
		txtOneTab2 = (TextView) findViewById(R.id.txtOneTab2);
		txtOneTab3 = (TextView) findViewById(R.id.txtOneTab3);
		
		//�����������
		txtOneTab1.setText("30");
		txtOneTab2.setText("XXX����վ");
		String strMsg  = "����¶ȣ�31" + "\n";
		       strMsg += "�¶ȣ�48" + "\n";
		       strMsg += "���٣�64km/h" + "\n";
		       strMsg += "��������" + "\n";
		       strMsg += "����ʱ�䣺2013-07-08 13:34";
		txtOneTab3.setText(strMsg);
		
	    //����list����
	 	oneTabListData = getOneTabListData();
	 	oneTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),oneTabListData, R.layout.weather_detail_item,  
	 			 new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
	 	oneTabListView.setTextFilterEnabled(true); 
		 
	 }
	 
	 /**
	  * ���õ�һ��tab��������
	  */
	 private void setTwoTabDate(){
		 
		 //��ȡ���ݲ����䵽listview��
		twoTabListView = (ListView) findViewById(R.id.twoTabList);
		txtTwoTab1 = (TextView) findViewById(R.id.txtTwoTab1);
		txtTwoTab2 = (TextView) findViewById(R.id.txtTwoTab2);
		txtTwoTab3 = (TextView) findViewById(R.id.txtTwoTab3);
		
		//�����������
		txtTwoTab1.setText("40");
		txtTwoTab2.setText("XXX����վ");
		String strMsg  = "����¶ȣ�31" + "\n";
		       strMsg += "�¶ȣ�48" + "\n";
		       strMsg += "���٣�64km/h" + "\n";
		       strMsg += "��������" + "\n";
		       strMsg += "����ʱ�䣺2013-07-07 13:34";
		txtTwoTab3.setText(strMsg);
		
	    //����list����
		twoTabListData = getTowTabListData();
		twoTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),twoTabListData, R.layout.weather_detail_item,  
	 			 new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
		twoTabListView.setTextFilterEnabled(true); 
		 
	 }
	 
	 /**
	  * ���õ�һ��tab��������
	  */
	 private void setFourTabDate(){
		 
		 //��ȡ���ݲ����䵽listview��
		fourTabListView = (ListView) findViewById(R.id.fourTabList);

	    //����list����
		fourTabListData = getFourTabListData();
		fourTabListView.setAdapter(new SimpleAdapter(getApplicationContext(),fourTabListData, R.layout.weather_detail_item,  
				  new String[] { "w_time", "w_wendu", "w_tianqi" }, 
				  new int[] {R.id.w_time, R.id.w_wendu, R.id.w_tianqi}));
		fourTabListView.setTextFilterEnabled(true); 
		
		searchView = (SearchView) findViewById(R.id.wether_his_search);    
		searchView.setOnQueryTextListener(this);  
        searchView.setSubmitButtonEnabled(false); 
		 
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
	 * ����ListViewֵ
	 * @param resultList
	 */
	public void updateLayout(List<HashMap<String, Object>> resultList) {  
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
	 

 	/*
     * ��������
     */
	private List<HashMap<String, Object>> getOneTabListData() {  
       
        //ԭʼ����
    	oneTabDbDatalist = new ArrayList<WeatherDetailItem>();
        for(int i=0; i<14; i++)
        {
        	WeatherDetailItem  lst = new WeatherDetailItem();
        	lst.setW_time("4" + i + ":PM");
        	lst.setW_wendu("2" +i);
        	lst.setW_tianqi("С��");
        	oneTabDbDatalist.add(lst);
        }
        
        //ƴװ����
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
        for (WeatherDetailItem oneRec: oneTabDbDatalist) 
        {   
        	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("w_time", oneRec.getW_time()); 
	        item.put("w_wendu", oneRec.getW_wendu()); 
	        item.put("w_tianqi", oneRec.getW_tianqi()); 
	        data.add(item);  
        }
        
        //���ؽ��
        return data;
    } 
    
 	/*
     * ��������
     */
    private List<HashMap<String, Object>> getTowTabListData() {  
       
        //ԭʼ����
    	twoTabDbDatalist = new ArrayList<WeatherDetailItem>();
        for(int i=0; i<14; i++)
        {
        	WeatherDetailItem  lst = new WeatherDetailItem();
        	lst.setW_time("2" + i + ":PM");
        	lst.setW_wendu("1" +i);
        	lst.setW_tianqi("��");
        	twoTabDbDatalist.add(lst);
        }
        
        //ƴװ����
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
        for (WeatherDetailItem oneRec: twoTabDbDatalist) 
        {   
        	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("w_time", oneRec.getW_time()); 
	        item.put("w_wendu", oneRec.getW_wendu()); 
	        item.put("w_tianqi", oneRec.getW_tianqi()); 
	        data.add(item);  
        }
        
        //���ؽ��
        return data;
    }
    
	/*
     * ��������
     */
    private List<HashMap<String, Object>> getFourTabListData() {  
       
    	  //ԭʼ����
    	fourTabDbDatalist = new ArrayList<WeatherDetailItem>();
        for(int i=0; i<20; i++)
        {
        	WeatherDetailItem  lst = new WeatherDetailItem();
        	lst.setW_time("4" + i + ":PM");
        	lst.setW_wendu("2" +i);
        	lst.setW_tianqi("С��");
        	fourTabDbDatalist.add(lst);
        }
        
        //ƴװ����
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
        for (WeatherDetailItem oneRec: fourTabDbDatalist) 
        {   
        	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("w_time", oneRec.getW_time()); 
	        item.put("w_wendu", oneRec.getW_wendu()); 
	        item.put("w_tianqi", oneRec.getW_tianqi()); 
	        data.add(item);  
        }
        
        //���ؽ��
        return data;
    } 
    
    /**
     * 
     */
    private void setThreeWeatherChart()
    {
		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		GraphicalView mChartView;
		
		//create dataset
	    String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos"};
	    List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
	      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
	    }
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,13.9 });
	    values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14, 11 });
	    values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
	    values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
		mDataset = BaseHelper.buildDataset(titles, x, values);
		
		//create color
		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };   
		PointStyle[] styles = new PointStyle[] {PointStyle.CIRCLE, PointStyle.DIAMOND,PointStyle.TRIANGLE, PointStyle.SQUARE};
		mRenderer = BaseHelper.buildRenderer(colors, styles);
		int length = mRenderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++){
		      ((XYSeriesRenderer) mRenderer.getSeriesRendererAt(i)).setFillPoints(true);
		}
		
        //set chart
		LinearLayout layout = (LinearLayout) findViewById(R.id.weather_chart);
		mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

    }
    
    
}
