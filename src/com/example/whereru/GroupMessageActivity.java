package com.example.whereru;

import java.util.ArrayList;
import java.util.Iterator;

import entities.Contact;

import ws.local.ClientToServerComm;
import DBLayout.DBHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * Handles sending a message to a group
 * @author albertw
 *
 */
public class GroupMessageActivity extends Activity {
	
	private String receiverGroup;
	private ArrayList<String> receivers;
	private String content;
	private EditText inputContent;
	private DBHelper dbHelper;
	private ArrayList<Contact> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_message);
		
		dbHelper = new DBHelper(this);
		
		Bundle extras = getIntent().getExtras();
		receiverGroup = extras.getString("receiverGroup");
		
		inputContent = (EditText) findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_message, menu);
		return true;
	}
	
	/**
	 * Sends a message to an entire group
	 * @param view
	 */
	public void sendMessage(View view){
		ClientToServerComm c = ClientToServerComm.getInstance(this); 
		Thread t = new Thread(c); 
		t.start(); 
		try { Thread.sleep(3000); } 
		catch (InterruptedException e) 
		{ // TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
		
		content = inputContent.getText().toString();

		
		contactList = dbHelper.getContactByGroup(receiverGroup);
		Iterator<Contact> it = contactList.iterator();
		
		receivers = new ArrayList<String>();
		
		while(it.hasNext()){
			receivers.add(it.next().getName());
		}
		
		for(String receiver: receivers){
			c.pushMessage(content, receiver);
		}
		
		c.terminate();
	}

}
