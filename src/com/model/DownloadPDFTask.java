package com.model;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DownloadPDFTask extends AsyncTask<String, Void, Integer> 
{
    protected ProgressDialog mWorkingDialog;    // progress dialog
    protected String mFileName;         // downloaded file
    protected String mError;            // for errors
    protected Activity myActivity;
    
    public DownloadPDFTask(Activity myActivity){
    	this.myActivity = myActivity;
    }
    
    @Override
    protected Integer doInBackground(String... urls)
    {
    	byte[] dataBuffer = new byte[4096];
        int nRead = 0;

        // set local filename to last part of URL
        String[] strURLParts = urls[0].split("/");
        if (strURLParts.length > 0)
      	  	mFileName = strURLParts[strURLParts.length - 1];
        else
            mFileName = "REPORT.pdf";
        
        try
        {  
        	URL urlReport = new URL( urls[0].replace(mFileName, URLEncoder.encode(mFileName,"utf-8")) );
            HttpURLConnection urlConn = (HttpURLConnection)urlReport.openConnection();
			urlConn.connect();
			if(urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return -1;
			}
			InputStream streamInput = urlConn.getInputStream();
			BufferedInputStream bufferedStreamInput = new BufferedInputStream(streamInput);
			FileOutputStream outputStream =  myActivity.openFileOutput(mFileName,Context.MODE_WORLD_READABLE); // must be world readable so external Intent can open!
			while ((nRead = bufferedStreamInput.read(dataBuffer)) > 0)
				outputStream.write(dataBuffer, 0, nRead);
			streamInput.close();
			outputStream.close();
			urlConn.disconnect();
      }
      catch (Exception e)
      {
    	  Log.e("myApp", e.getMessage());
    	  mError = e.getMessage();
    	  return (1);
      }
     return (0);
    }

    //-------------------------------------------------------------------------
    // PreExecute - UI thread setup
    //-------------------------------------------------------------------------

    @Override
    protected void onPreExecute()
    {
     // show "Downloading, Please Wait" dialog
     mWorkingDialog = ProgressDialog.show(myActivity, "", "Downloading PDF Document, Please Wait...", true);
     return;
    }

    //-------------------------------------------------------------------------
    // PostExecute - UI thread finish
    //-------------------------------------------------------------------------

    @Override
    protected void onPostExecute (Integer result)
    {
         if (mWorkingDialog != null)
      {
       mWorkingDialog.dismiss();
       mWorkingDialog = null;
      }

         switch (result)
         {
         case 0:                            // a URL

            // Intent to view download PDF
            Uri uri  = Uri.fromFile(myActivity.getFileStreamPath(mFileName));

            try
            {
                Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                intentUrl.setDataAndType(uri, "application/pdf");
                intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myActivity.startActivity(intentUrl);
            }
            catch (ActivityNotFoundException e)
            {
                Toast.makeText(myActivity, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
            }

            break;

        case 1:                         // Error

            Toast.makeText(myActivity, mError, Toast.LENGTH_LONG).show();
            break;

        }

    }

}