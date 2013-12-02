package com.example.whereru;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Group;
import DBLayout.DBHelper;
import adapter.MyListAdapter;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GroupActivity extends ListActivity {
	
	private List<String> groupNameList;
    private DBHelper dbHelper;
    private ArrayList<Group> groupList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_group);
		
		dbHelper = new DBHelper(this);
		
		groupNameList = new ArrayList<String>();
	    groupList = dbHelper.getAllGroups();
	    
	    Iterator<Group> it1 = groupList.iterator();
		while(it1.hasNext()){
			groupNameList.add(it1.next().getGroupName());			
		}
		
		setListAdapter(new MyListAdapter(this, groupNameList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.group, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_contactview:
	        	Intent intentContactview = new Intent(this, ContactActivity.class);
	    		startActivity(intentContactview);
	            return true;
	        /*case R.id.action_add_new_contact:
	        	Intent intentContact = new Intent(this, NewContactActivity.class);
	    		startActivity(intentContact);
	        	return true;
	        case R.id.action_groupview:
	        	Intent intentGroupview = new Intent(this, GroupActivity.class);
	    		startActivity(intentGroupview);
	        	return true;*/
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
