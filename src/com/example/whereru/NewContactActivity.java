package com.example.whereru;

import DBLayout.DBHelper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_contact, menu);
		return true;
	}
	
	public void addContact(View view){
    	EditText text1 = (EditText) findViewById(R.id.editText1);
    	String name = text1.getText().toString();
    	EditText text2 = (EditText) findViewById(R.id.editText2);
    	String group = text2.getText().toString();
    	
    	DBHelper dbHelper = new DBHelper(this);
    	dbHelper.createContact(name, group);
    	
    	Intent intent = new Intent(this, ContactActivity.class);
    	startActivity(intent);
	}

}
