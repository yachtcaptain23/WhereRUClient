package com.example.whereru;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.*;

public class ReadMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_message);
		
		Intent intent = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_message, menu);
		return true;
	}

}
