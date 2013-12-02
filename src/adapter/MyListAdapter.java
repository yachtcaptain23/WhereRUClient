package adapter;

import java.util.List;

import com.example.whereru.CheckinMessageActivity;
import com.example.whereru.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String>{
	
	private final Context context;
	private final List<String> values;
 
	public MyListAdapter(Context context, List<String> values) {
		super(context, R.layout.groupview_item, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.groupview_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.contact_group);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.delete);
        imageView.setOnClickListener(new OnClickListener() {
        	 
            public void onClick(View v) {

            	Intent intent = new Intent(context, CheckinMessageActivity.class);
        		context.startActivity(intent);
            }
        });
		textView.setText((String)values.get(position));
 
		return rowView;
	}
}
