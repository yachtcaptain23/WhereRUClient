package adapter;

import java.util.List;
import java.util.Map;

import com.example.whereru.CheckinMessageActivity;
import com.example.whereru.R;

import entities.Contact;

import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Activity context;
    private Map<String, List<Contact>> contactsCollections;
    private List<String> contacts;
    private TextView item;
    private TextView middleitem;
    private ImageView delete;
 
    public ExpandableListAdapter(Activity context, List<String> contacts,
            Map<String, List<Contact>> contactCollection) {
        this.context = context;
        this.contactsCollections = contactCollection;
        this.contacts = contacts;
    }
 
    public Object getChild(int groupPosition, int childPosition) {
        return contactsCollections.get(contacts.get(groupPosition)).get(childPosition);
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    public View getChildView(final int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        final String contact = ((Contact)getChild(groupPosition, childPosition)).getName();
        final String message = ((Contact)getChild(groupPosition, childPosition)).getMessage();
        LayoutInflater inflater = context.getLayoutInflater();
 
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
 
        item = (TextView) convertView.findViewById(R.id.contact);
        
        middleitem = (TextView) convertView.findViewById(R.id.message);
 
        delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {
 
            public void onClick(View v) {
            	
                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Contact> child =
                                    contactsCollections.get(contacts.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();*/
            	/*double mylatitude = 0;
            	double mylongitude = 0;
            	Bundle extras = context.getIntent().getExtras();
        		if (extras != null) {
        			mylatitude = extras.getDouble("mylatitude");
        			mylongitude = extras.getDouble("mylongitude");
        		}
            	Intent intent = new Intent(context, CheckinMessageActivity.class);
            	intent.putExtra("mylatitude", mylatitude);
            	intent.putExtra("mylongitude", mylongitude);
            	intent.putExtra("receiver", item.getText());
        		context.startActivity(intent);*/
            	Intent intent = new Intent(context, CheckinMessageActivity.class);
            	intent.putExtra("receiver", item.getText());
            	context.startActivity(intent);
            	//item.getText();
            	
            }
        });
 
        item.setText(contact);
        middleitem.setText(message);
        return convertView;
    }
 
    public int getChildrenCount(int groupPosition) {
        return contactsCollections.get(contacts.get(groupPosition)).size();
    }
 
    public Object getGroup(int groupPosition) {
        return contacts.get(groupPosition);
    }
 
    public int getGroupCount() {
        return contacts.size();
    }
 
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.contact);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(group);
        return convertView;
    }
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}