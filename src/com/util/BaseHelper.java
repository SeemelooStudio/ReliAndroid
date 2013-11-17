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


public class BaseHelper {

    /**
     * 显示消息对话框
     * @param context
     * @param title
     * @param message
     * @param indeterminate
     * @param cancelable
     * @return
     */
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable)
	{
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
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
