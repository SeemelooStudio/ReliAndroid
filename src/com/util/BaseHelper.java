package com.util;

import java.net.URLEncoder;
import java.util.List;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class BaseHelper {

    /**
     * @param context
     * @param title
     * @param message
     * @param indeterminate
     * @param cancelable
     * @return
     */
	public static ProgressDialog showProgress(Context context,CharSequence message, 
			boolean indeterminate)
	{
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		try {
		dialog.show();
		}catch(Exception ex){}
		return dialog;
	}
	
	
	/**
	 * 
	 * @param strErrMsg
	 */
	public static void  showErrorDialog(final Activity activity, String strErrMsg){
		
		 AlertDialog.Builder digErrInfo = new AlertDialog.Builder(activity);
		 digErrInfo.setTitle("������Ϣ");
		 digErrInfo.setMessage(strErrMsg);
	    	
		 digErrInfo.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
	     {
	          public void onClick(DialogInterface dialog, int which)
	          {   
	        	 activity.finish();
	          }
	     });

		 digErrInfo.show(); 	  
	}
	

	/**
	 * ��ʾ�����Ի���
	 * @param context
	 * @param strTitle
	 * @param strText
	 */
	public static void showDialog(Activity context,String strTitle ,String strText)
	{
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setTitle(strTitle);
		tDialog.setMessage(strText);
		tDialog.setPositiveButton("ȷ��",null);
		tDialog.show();
	}
	
	/**
	 * @param context
	 * @param strTitle
	 * @param strText
	 */
	public static void showToastMsg(Activity context,String strMsg)
	{
		Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
	}
	
	

	/**
     * 
     * @param titles
     * @param xValues
     * @param yValues
     * @return
     */
	public static XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,List<double[]> yValues)
    {
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    int length = titles.length;
	    for (int i = 0; i < length; i++) {
	      XYSeries series = new XYSeries(titles[i]);
	      double[] xV = xValues.get(i);
	      double[] yV = yValues.get(i);
	      int seriesLength = xV.length;
	      for (int k = 0; k < seriesLength; k++) {
	        series.add(xV[k], yV[k]);
	      }
	      dataset.addSeries(series);
	    }
	    return dataset;
    }
    
    /**
     * 
     * @param colors
     * @param styles
     * @return
     */
    public static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++) {
          XYSeriesRenderer r = new XYSeriesRenderer();
          r.setColor(colors[i]);
          r.setPointStyle(styles[i]);
          renderer.addSeriesRenderer(r);
        }
        return renderer;
      }

    
    /**
	  * 
	  * @param pdfView
	  * @param strUrl
	  * @throws Exception 
	  */
	 public static void loadNetPdfFile(WebView pdfView,String strUrl) throws Exception{
		 try {
			 pdfView.getSettings().setJavaScriptEnabled(true); 
			 pdfView.getSettings().setSupportZoom(true); 
			 pdfView.getSettings().setDomStorageEnabled(true); 
			 pdfView.getSettings().setAllowFileAccess(true); 
			 pdfView.getSettings().setPluginsEnabled(true); 
			 pdfView.getSettings().setUseWideViewPort(true); 
			 pdfView.getSettings().setBuiltInZoomControls(true); 
			 pdfView.requestFocus(); 
			 pdfView.getSettings().setLoadWithOverviewMode(true); 
			 pdfView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
			 pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url="+ strUrl); 
			 pdfView.setWebViewClient(new WebViewClient() { 
			       @Override 
			       public void onPageStarted(WebView view, String url,Bitmap favicon) { 
			         super.onPageStarted(view, url, favicon); 
			       } 
			       @Override 
			       public boolean shouldOverrideUrlLoading(WebView view, String url) { 
			         view.loadUrl(url); 
			         return true; 
			       } 
			       @Override 
			       public void onPageFinished(WebView view, String url) { 
			         super.onPageFinished(view, url);
			       } 
			       @Override 
			       public void onReceivedError(WebView view, int errorCode, 
			           String description, String failingUrl) { 
			           super.onReceivedError(view, errorCode, description, failingUrl);
			       } 
			  }); 
			 
			 pdfView.setDownloadListener(new DownloadListener() { 
			       @Override
			       public void onDownloadStart(String url, String userAgent,
			    		   String contentDisposition, String mimetype,long contentLength) { 
				         System.out.println("=========>��ʼ���� url =" + url); 
				         Uri uri = Uri.parse(url);
				         Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				         //startActivity(intent); 
			       } 
			  }); 
			 
			 
		 } 
		 catch (Exception ex)
		 {
			 throw ex;
		 }
	 }

	 /**
	  * 
	  * @param pdfView
	  * @param strUrl
	  * @throws Exception 
	  */
	 public static void loadPdfFile(WebView pdfView,String strUrl) throws Exception{
		 
		 try {
			 pdfView.getSettings().setJavaScriptEnabled(true);
			 pdfView.getSettings().setSupportZoom(true);
			 pdfView.getSettings().setDomStorageEnabled(true);
			 pdfView.getSettings().setAllowFileAccess(true);
			 pdfView.getSettings().setUseWideViewPort(true);
			 pdfView.getSettings().setBuiltInZoomControls(true);
			 pdfView.requestFocus();
			 pdfView.getSettings().setLoadWithOverviewMode(true);
			 pdfView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			 String data="<iframe src='http://docs.google.com/gview?embedded=true&url="+strUrl+"'"+"width='100%'height='100%'style='border:none;'></iframe>";
			 //pdfView.loadData(data,"text/html","UTF-8");
			 pdfView.loadData(URLEncoder.encode(data,"UTF-8"),"text/html","UTF-8");
		 } 
		 catch (Exception ex)
		 {
			 throw ex;
		 }
	}
}
