package com.gtalkshare;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * a section list friends
 * @author 6a209
 * Jun 2, 2013
 */
public class GtalkShareFriednsAdapter extends ArrayAdapter<String> {

//	public static final int CONTENT_TYPE = 0x01;
//	public static final int SECTION_TYPE = 0x02;
	
	
	public GtalkShareFriednsAdapter(Context context, int resource,
			int textViewResourceId, String[] mFriendsArray) {
		super(context, resource, textViewResourceId, mFriendsArray);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		TextView tv = (TextView)super.getView(position, convertView, parent);
//		tv.setBackgroundColor(Color.WHITE);
		tv.setText(getItem(position));
		return tv;
	}
	
//	@Override
//	public boolean isItemViewTypePinned(int pos) {
//		if(getItem(pos).mType == SECTION_TYPE){
//			return true;
//		}
//		return false;
//	}
	
//	public static class FriendsType{
//		public String mContent;
//		public int mType;
//	}
	
//	private int getSecionColor(){
//		
//	}
}