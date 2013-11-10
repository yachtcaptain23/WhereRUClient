package com.example.whereru;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.*;
import android.view.*;

public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		Intent intent = getIntent();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}
	
	public void detail(View view) {
		Intent intent = new Intent(this, ReadMessageActivity.class);
		
		startActivity(intent);
	}

}
