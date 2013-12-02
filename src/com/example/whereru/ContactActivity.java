package com.example.whereru;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entities.Contact;
import entities.Group;

import DBLayout.DBHelper;
import adapter.ExpandableListAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import android.content.*;

public class ContactActivity extends ActionBarActivity {
	
	private List<String> groupNameList;
	private List<Contact> childList;
	private Map<String, List<Contact>> contactCollection;
	private ExpandableListView expListView;
    private DBHelper dbHelper;
    private ArrayList<Group> groupList;
    private ArrayList<Contact> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		dbHelper = new DBHelper(this);
		
		createGroupList();
		//System.out.println("1");
		 
        createCollection();
        //System.out.println("2");
 
        expListView = (ExpandableListView) findViewById(R.id.contact_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupNameList, contactCollection);
        expListView.setAdapter(expListAdapter);
 
        //setGroupIndicatorToRight();
 
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                final String selected = ((Contact)expListAdapter.getChild(
                        groupPosition, childPosition)).getName() + ": " 
                		+ ((Contact)expListAdapter.getChild(
                                groupPosition, childPosition)).getMessage();
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_SHORT)
                        .show();
 
                return true;
            }
        });
	}

	private void createCollection() {
		// TODO Auto-generated method stub
		
		
		Iterator<String> it1 = groupNameList.iterator();
		contactCollection = new LinkedHashMap<String, List<Contact>>();
		
		while(it1.hasNext()){
			childList = new ArrayList<Contact>();
			String groupName = it1.next();
			contactList = dbHelper.getContactByGroup(groupName);
			Iterator<Contact> it2 = contactList.iterator();
			
			while(it2.hasNext()){
				childList.add(it2.next());
			}
			
			contactCollection.put(groupName, childList);
		}
	}

	private void createGroupList() {
		// TODO Auto-generated method stub
		groupNameList = new ArrayList<String>();
		//childList = new ArrayList<String>();
	    groupList = dbHelper.getAllGroups();
	    //System.out.println("1");
	    
	    Iterator<Group> it1 = groupList.iterator();
	    //System.out.println("2");
		while(it1.hasNext()){
			//String groupName = it1.next().getGroupName();
			groupNameList.add(it1.next().getGroupName());
			
		}
		
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
	        case R.id.action_add_new_group:
	        	Intent intentGroup = new Intent(this, NewGroupActivity.class);
	    		startActivity(intentGroup);
	            return true;
	        case R.id.action_add_new_contact:
	        	Intent intentContact = new Intent(this, NewContactActivity.class);
	    		startActivity(intentContact);
	        	return true;
	        case R.id.action_groupview:
	        	Intent intentGroupview = new Intent(this, GroupActivity.class);
	    		startActivity(intentGroupview);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
