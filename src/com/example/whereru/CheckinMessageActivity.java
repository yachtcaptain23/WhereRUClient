package com.example.whereru;


import ws.local.ClientToServerComm;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * CheckinMessageActivity handles sending messages to other users
 * Send message to the server
 * Other clients pull all information including the message from the server
 * @author albertw
 *
 */
public class CheckinMessageActivity extends Activity {
	
	private String receiver;
	private String content;
	private EditText inputContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin_message);
		
		Bundle extras = getIntent().getExtras();
		receiver = extras.getString("receiver");
		
		inputContent = (EditText) findViewById(R.id.editText1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin_message, menu);
		return true;
	}
	
	/**
	 * Sends a message to the server
	 * @param view Current activity view
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
		
		c.pushMessage(content, receiver);
		c.terminate();
	}

}
