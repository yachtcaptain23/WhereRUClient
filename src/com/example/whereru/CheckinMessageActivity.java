package com.example.whereru;

import com.google.android.gms.maps.model.LatLng;

import ws.local.ClientToServerComm;
import DBLayout.DBHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class CheckinMessageActivity extends Activity {
	
	private DBHelper dbHelper;
	private double mylatitude;
	private double mylongitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin_message);
		
		Bundle extras = getIntent().getExtras();
		mylatitude = extras.getDouble("mylatitude");
		mylongitude = extras.getDouble("mylongitude");
		dbHelper = new DBHelper(this);
		dbHelper.updateMyselfLocation(mylatitude, mylongitude);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin_message, menu);
		return true;
	}
	
	public void sendMessage(View view){
		ClientToServerComm c = ClientToServerComm.getInstance(this); 
		Thread t = new Thread(c); 
		t.start(); 
		try { t.sleep(3000); } 
		catch (InterruptedException e) 
		{ // TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
			c.pushMyself();
		
			// c.pullAll();
			c.terminate();
	}

}
