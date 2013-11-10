package com.example.whereru;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
	static final LatLng MyLocation = new LatLng(40.44141, -79.94378);
    private GoogleMap map;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker mylocation = map.addMarker(new MarkerOptions().position(MyLocation).title("Hunt Libray").snippet("Here we are"));

        // Move the camera instantly to NKUT with a zoom of 16.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation, 16));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void getContact(View view) {
		Intent intent = new Intent(this, ContactActivity.class);
		
		startActivity(intent);
	}
	
	public void getSetting(View view) {
		Intent intent = new Intent(this, SettingActivity.class);
		
		startActivity(intent);
	}
	
	public void checkIn(View view) {
		Intent intent = new Intent(this, CheckinActivity.class);
		// do something here
		startActivity(intent);
	}
	
	public void message(View view) {
		Intent intent = new Intent(this, MessageActivity.class);
		// do something here
		startActivity(intent);
	}
	
	public void statusSwitch(View view) {
		// do something here
		// don't jump to other Activity, switch off the geo location only
	}

}