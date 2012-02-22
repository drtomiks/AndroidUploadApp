package com.example.androidbase64json;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class AndroidBase64JSONActivity extends Activity {
	
	final static String TAG = "AndroidBase64JSONActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        // OnClick methods are set in the layout file
		final Button upload_button = (Button) findViewById(R.id.button2);
		final RadioButton radio_simple = (RadioButton) findViewById(R.id.radio_simple);
		final RadioButton radio_json = (RadioButton) findViewById(R.id.radio_json);
        
        setContentView(R.layout.main);
    }
    
	/** Start Upload Image File to Server Activity */
	public void doImageUpload(View view) {
		Intent loadImageUploadActivity = new Intent(
				AndroidBase64JSONActivity.this, ImageUploadActivity.class);
		AndroidBase64JSONActivity.this.startActivity(loadImageUploadActivity);
	}

	/** Set the file upload mode */
	public void doSetUploadMode(View view){
		// handle upload mode radio buttons
		RadioButton rb = (RadioButton) view;
		int radioButtonId = rb.getId();

		switch (radioButtonId) {
		case R.id.radio_simple:
			AppProperties.setUploadMode(0);
			break;
		case R.id.radio_json:
			AppProperties.setUploadMode(1);
			break;
		default:
			AppProperties.setUploadMode(0);

		}
	}
}