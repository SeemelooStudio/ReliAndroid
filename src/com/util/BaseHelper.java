package com.util;

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
		dialog.show();
		return dialog;
	}
	
	
	/**
	 * 
	 * @param strErrMsg
	 */
	public static void  showErrorDialog(final Activity activity, String strErrMsg){
		
		 AlertDialog.Builder digErrInfo = new AlertDialog.Builder(activity);
		 digErrInfo.setTitle("错误信息");
		 digErrInfo.setMessage(strErrMsg);
	    	
		 digErrInfo.setPositiveButton("确定", new DialogInterface.OnClickListener()
	     {
	          public void onClick(DialogInterface dialog, int which)
	          {   
	        	 activity.finish();
	          }
	     });

		 digErrInfo.show(); 	  
	}
	

	/**
	 * 显示弹出对话款
	 * @param context
	 * @param strTitle
	 * @param strText
	 */
	public static void showDialog(Activity context,String strTitle ,String strText)
	{
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setTitle(strTitle);
		tDialog.setMessage(strText);
		tDialog.setPositiveButton("确定",null);
		tDialog.show();
	}
	
	/**
	 * 显示弹出对话款
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
	

	
}
