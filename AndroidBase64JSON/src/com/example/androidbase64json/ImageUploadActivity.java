package com.example.androidbase64json;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.util.Base64;

public class ImageUploadActivity extends Activity {

	final static String TAG = "ImageUploadActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Integer uploadMode = 0; // 0: Simple unencoded HTTP Post 1: Base64 encoded JSON HTTP Post
		String retData;
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.image_upload);
		
		uploadMode = AppProperties.uploadMode;
		
		retData = doImageUpload(uploadMode);
		parseServerResponse(retData);

	}
	

	
	private String doImageUpload(Integer uploadMode){
		String uploadUrl = AppProperties.getUploadUrl();
		String serverResponse = "";
		
		Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),	R.drawable.apollo17_earth);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		byte[] imageData = bao.toByteArray();


		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();			
			HttpPost httppost = new	HttpPost(uploadUrl);
			
            
            MultipartEntity mpEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            
            
            
            if (uploadMode == 0){
            
            mpEntity.addPart("fileData", new ByteArrayBody(imageData,
                    "uploadedImage.jpg"));
            httppost.setEntity(mpEntity);
            
            }else{// Base64 encoded JSON

            	String encodedImageData = Base64.encodeToString(imageData, Base64.DEFAULT);
            	
            	Log.d(TAG,"Encoded Image Data Length: " + encodedImageData.length());
            	Log.d(TAG,"Encoded Image Data String (logcat truncated): " + encodedImageData);
            	try{
            	JSONObject object = new JSONObject();
            	
            		object.put("filename", "uploadedImage.jpg");
            		object.put("fileData", encodedImageData);
            		Log.d(TAG, "JSON object (logcat truncated): " + object.toString());
            		
            		StringEntity entity = new StringEntity(object.toString());
            		
            		httppost.setHeader("Accept", "application/json");
            		httppost.setHeader("content-type", "application/json");
            		httppost.setEntity(entity);
            		
            	} catch (JSONException e) {
            		Log.e(TAG, "Error writing JSON object: " + e.toString());
            		return "";
            	}
            	
            	
            }
            
            
            HttpResponse response = httpclient.execute(httppost,
                    localContext);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent(), "UTF-8"), 2 * 8192);
            serverResponse = reader.readLine();
			
		} catch (Exception e) {
			Log.e(TAG, "Error in http connection: " + e.toString());
		}
		
		return serverResponse;
		
	}

	private void parseServerResponse(String retData){
		
		// Right now this just takes the response and displays it.
		
		if (retData.length() == 0)retData = "{\"OCR_Results\":\"NO DATA\"}";
		
		EditText responseTextField = (EditText) this.findViewById(R.id.editText1);
		
		// Returned data will be a string in JSON format
		
		// debugging output
		Log.d(TAG, "Server Response: " + retData);
		
		try {
			JSONObject jsonResponse = new JSONObject(retData);
			String parsedData = jsonResponse.getString("OCR_Results");
			Log.d(TAG, "Parsed JSON OCR_Results: " + parsedData);
			
			// Write the response text
			responseTextField.setText(parsedData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
