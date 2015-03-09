package com.example.whereru;

import ws.local.ClientToServerComm;
import DBLayout.DBHelper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * SignupActivity allows the user to SignUp on their own device
 * @author albertw
 *
 */
public class SignupActivity extends Activity {
	
	EditText inputName;
	EditText inputPassword;
	DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		inputName = (EditText) findViewById(R.id.editText1);
		inputPassword = (EditText) findViewById(R.id.editText2);
		inputPassword.setTypeface(Typeface.DEFAULT);
		
		dbHelper = new DBHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}
	
	/**
	 * Signs up by storing user information into the database
	 * @param view
	 */
	public void signUp(View view){
		String name = inputName.getText().toString();
		String password = inputPassword.getText().toString();
		
		dbHelper.createUser(name, password);
		
		
		ClientToServerComm c = ClientToServerComm.getInstance(this); 
		Thread t = new Thread(c); 
		t.start(); 
		try { Thread.sleep(3000); } 
		catch (InterruptedException e) 
		{ // TODO Auto-generated catch block 
			e.printStackTrace(); 
		}
		
		c.createContact(name);
		c.terminate();
		
		Toast.makeText(getBaseContext(), "Account is created", Toast.LENGTH_SHORT)
        .show();
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
