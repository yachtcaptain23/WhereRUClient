package com.example.whereru;

import java.util.ArrayList;
import java.util.Iterator;

import ws.local.ClientToServerComm;

import DBLayout.DBHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import entities.Contact;

/**
 * Opens up the Map and draws the current user and contacts
 * @author albertw
 *
 */
public class MapActivity extends ActionBarActivity {
	
	private static final LatLng CMU = new LatLng(40.4429, -79.9425);
	private LatLng MyLocation;
	private Marker myLocMarker = null;
    private GoogleMap map;
    private LocationManager myLocationManager;
    private ArrayList<Marker> markerList;
    private ArrayList<LatLng> latlngList;
    private ArrayList<Contact> contactList;
    private DBHelper dbHelper;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		myLocationListener locationListener = new myLocationListener();
		
        dbHelper = new DBHelper(this);
		
		myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, locationListener);
		myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(CMU, 16));
		
		//showLocations();
		//myLocMarker = map.addMarker(new MarkerOptions().position(CMU).title("Hunt Libray").snippet("Here we are"));
	}
	
	public class myLocationListener implements LocationListener {
		
		public void onLocationChanged(Location location) {
			
			double latitude;
			double longitude;
			
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
			MyLocation = new LatLng(latitude, longitude);
			String locationInfo = "Latitude = " + latitude + "\nLongitude = " + longitude;
			
			if (myLocMarker == null) {
				myLocMarker = map.addMarker(new MarkerOptions().position(MyLocation).title("My location").snippet(locationInfo));
				map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
			}
			myLocMarker.setPosition(MyLocation);
			myLocMarker.setSnippet(locationInfo);
			
			dbHelper.updateMyselfLocation(latitude, longitude);
			//pushMyself();
			

	        //map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
	        
	        showLocations();
		}
		
		public void onProviderDisabled(String provider) {
			Toast toast = Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_SHORT );
			toast.show();
		}
		
		public void onProviderEnabled(String provider) {
			Toast toast = Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT );
			toast.show();
		}
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
	}
	
	/**
	 * Draws all contacts
	 */
	public void showLocations () {
		if (markerList == null) {
			markerList = new ArrayList<Marker>();
		}
		if (latlngList == null) {
			latlngList = new ArrayList<LatLng>();
		}
		
		contactList = dbHelper.getAllContacts();
		Iterator<Contact> it2 = contactList.iterator();
		while(it2.hasNext()){
			Contact contact = (Contact)it2.next();
			markerList.add(map.addMarker(new MarkerOptions().position(new LatLng(contact.getLatitude(), contact.getLongitude())).title(contact.getName()).snippet(contact.getGroup()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
		}
		
		
		
		
	}
	
	/**
	 * Pulls all the contacts from the server
	 */
	public void PullAllContacts(){
		ClientToServerComm c = ClientToServerComm.getInstance(this); 
		Thread t = new Thread(c); 
		t.start(); 
		try { Thread.sleep(3000); } 
		catch (InterruptedException e) 
		{ // TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 

			c.pullAll();
			c.terminate();
	}
	
	/**
	 * Pulls all the messages from the server
	 */
	public void PullMessage(){
		ClientToServerComm c = ClientToServerComm.getInstance(this); 
		Thread t = new Thread(c); 
		t.start(); 
		try { Thread.sleep(3000); } 
		catch (InterruptedException e) 
		{ // TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
		
		c.pullMessage();
		c.terminate();
	}
	
	/**
	 * Pushes user's information to the server
	 */
	public void pushMyself(){
		ClientToServerComm c = ClientToServerComm.getInstance(this); 
		Thread t = new Thread(c); 
		t.start(); 
		try { Thread.sleep(3000); } 
		catch (InterruptedException e) 
		{ // TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
			c.pushMyself();
		
			// c.pullAll();
			c.terminate();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.map_activity_actions, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    MenuItemCompat.setOnActionExpandListener(searchItem, new OnActionExpandListener() {
	        @Override
	        public boolean onMenuItemActionCollapse(MenuItem item) {
	            // Do something when collapsed
	            return true;  // Return true to collapse action view
	        }

	        @Override
	        public boolean onMenuItemActionExpand(MenuItem item) {
	            // Do something when expanded
	            return true;  // Return true to expand action view
	        }
	    });
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void statusSwitch() {
		// do something here
		// don't jump to other Activity, switch off the geo location only
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		if(item.getItemId() == R.id.action_share){
    		pushMyself();
    		Toast.makeText(getBaseContext(), "Your location is shared", Toast.LENGTH_SHORT)
            .show();
    		return true;
		}
		else if(item.getItemId() ==  R.id.action_message){
        	PullMessage();
        	Toast.makeText(getBaseContext(), "Your messages are up-to-date now", Toast.LENGTH_SHORT)
            .show();
        	return true;
		}
		else if(item.getItemId() == R.id.action_contact){
        	Intent intentContact = new Intent(this, ContactActivity.class);
        	intentContact.putExtra("mylatitude", MyLocation.latitude);
        	intentContact.putExtra("mylongitude", MyLocation.longitude);
        	startActivity(intentContact);
        	return true;
		}
		else if(item.getItemId() == R.id.action_refresh){
        	PullAllContacts();
        	Toast.makeText(getBaseContext(), "Data updated", Toast.LENGTH_SHORT)
            .show();
        	Intent intentMap = new Intent(this, MapActivity.class);
        	startActivity(intentMap);
        	return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
	}
}