package entities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.whereru.R;
import com.example.whereru.R.id;
import com.example.whereru.R.layout;
import com.example.whereru.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	static final LatLng MyLocation = new LatLng(40.44141, -79.94378);
    private GoogleMap map;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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

}
