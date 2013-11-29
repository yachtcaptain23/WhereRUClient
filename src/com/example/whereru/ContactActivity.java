package com.example.whereru;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.*;

public class ContactActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		Intent intent = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.contact_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_groupview:
	        	Intent intentGroup = new Intent(this, NewGroupActivity.class);
	    		startActivity(intentGroup);
	            return true;
	        case R.id.action_add_new_contact:
	        	Intent intentContact = new Intent(this, NewContactActivity.class);
	    		startActivity(intentContact);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
