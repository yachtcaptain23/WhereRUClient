package com.example.whereru;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.*;
import android.graphics.Typeface;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView image1 = (ImageView) findViewById(R.id.imageView1);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		image1.requestFocus();
		EditText password = (EditText) findViewById( R.id.editText2);
		password.setTypeface( Typeface.DEFAULT );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void logIn(View view) {
		Intent intent = new Intent(this, MapActivity.class);
		
		/*EditText account = (EditText) findViewById(R.id.editText1);
		EditText password = (EditText) findViewById(R.id.editText2);
		String accountStr = account.getText().toString();
		String passwordStr = password.getText().toString();
		
		
		// use this accountStr and passwordStr to verify login compare these
		// two with the saved account information if the account string matches
		// with the saved account name and the passwords match, then do
		// startActivity(intent) else display a toast message indicating error
		
		/* it should be something like this:
		  get saved account information
		  if (accountStr.equals(<saved account name>) 
		  			&& passwordStr.equals(<saved password for this account>)) {
			startActivity(intent);
		}
		else {
			// create a new toast message and show it
		}*/
		startActivity(intent);
	}
	
	public void signUp(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}

}
