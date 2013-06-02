package com.gtalkshare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author 6a209
 * Jun 2, 2013
 */
public class GtalkFriendsAct extends BaseAct{

	private GtalkService mService;	
	private GtalkShareFriednsAdapter mAdapter;
	private GtalkUtils mUtils;
	
	private ListView mListView;
	private EditText mFilterEt;
	private boolean mIsSucc = false;
	private String [] mFriendsArray;
	
	private static final int TO_SETTING = 0x01;
	
	private static final int INIT_ACCOUNT_MSG = 0x10;
	private static final int GET_FIRENDS_MSG = 0x12;
	
	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case INIT_ACCOUNT_MSG:
				if(mIsSucc){
		    		reqFriends();
		    	}else{
		    		Toast.makeText(GtalkFriendsAct.this, getString(R.string.err_toast), Toast.LENGTH_SHORT).show();
		    	}
				
				break;
			case GET_FIRENDS_MSG:
				mAdapter = new GtalkShareFriednsAdapter(GtalkFriendsAct.this, 
					android.R.layout.simple_list_item_1, android.R.id.text1, mFriendsArray);
				break;
			}
		}
	};
	
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.friend_list);
		mListView = (ListView)findViewById(R.id.friend_list);
		mFilterEt = (EditText)findViewById(R.id.friend_filter);
		mUtils = GtalkUtils.getInstance(this);
		initAccount();
	}
	
	private void initAccount(){
		String userName = mUtils.getUser();
    	String password = mUtils.getPassword();
    	if(userName.equals(GtalkUtils.INVALUE) || password.equals(GtalkUtils.INVALUE)){
    		toSetting();
    		return;
    	}
    	reqInitAccount(userName, password);
	}
	
	private void reqInitAccount(final String userName, final String password) {
		new Thread() {
			@Override
			public void run() {
				mIsSucc = mService.initAccount(userName, password);
				Message msg = mHander.obtainMessage(INIT_ACCOUNT_MSG);
				mHander.sendMessage(msg);
			}
		}.start();
	}

	private void reqFriends() {
		new Thread() {
			@Override
			public void run() {
				String[] array = null;
				array = mService.getFriends();

			}
		}.start();
	}
	
	private void toSetting() {
		Intent intent = new Intent();
		intent.setClass(this, GtalkPreferenceAct.class);
		startActivityForResult(intent, TO_SETTING);
	}
	
}