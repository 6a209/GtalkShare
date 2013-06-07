package com.gtalkshare;

import org.jivesoftware.smack.RosterEntry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * a section list friends
 * @author 6a209
 * Jun 2, 2013
 */
public class GtalkShareFriednsAdapter extends ArrayAdapter<RosterEntry> {

	public GtalkShareFriednsAdapter(Context context, int resource,
			int textViewResourceId, RosterEntry[] mFriendsArray) {
		super(context, resource, textViewResourceId, mFriendsArray);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		TextView tv = (TextView)super.getView(position, convertView, parent);
		String name = getItem(position).getName();
		if(null == name || name.length() == 0){
			name = getItem(position).getUser();
		}
		tv.setText(name);
		return tv;
	}
	
	public class FriendsItem{
		public String mNickName;
		public String mUserName;
	}
}