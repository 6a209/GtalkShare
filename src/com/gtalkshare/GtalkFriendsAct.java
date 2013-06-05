package com.gtalkshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;

/**
 * 
 * @author 6a209
 * Jun 2, 2013
 */
public class GtalkFriendsAct extends BaseAct{

	private GtalkAPI mService;	
	private GtalkShareFriednsAdapter mAdapter;
	private GtalkUtils mUtils;
	
	private ListView mListView;
	private EditText mFilterEt;
	private boolean mIsSucc = false;
	private String [] mFriendsArray;
	private TextView mTitleTv;
	
	private static final int TO_SETTING = 0x01;
	
	private static final int INIT_ACCOUNT_MSG = 0x10;
	private static final int GET_FIRENDS_MSG = 0x12;
	
	public static final String FRIEND_NAME_KEY = "friends_name";
	
	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case INIT_ACCOUNT_MSG:
				if(mIsSucc){
		    		reqFriends();
		    	}else{
		    		Toast.makeText(GtalkFriendsAct.this, 
		    			getString(R.string.err_toast), Toast.LENGTH_SHORT).show();
		    	}
				
				break;
			case GET_FIRENDS_MSG:
				mAdapter = new GtalkShareFriednsAdapter(GtalkFriendsAct.this, 
					android.R.layout.simple_list_item_1, android.R.id.text1, mFriendsArray);
				mListView.setAdapter(mAdapter);
				break;
			}
		}
	};
	
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.friend_list, mBodyLy, true);
		String accessToken = GtalkUtils.getInstance(this).getString(GtalkConst.ACCESS_TOKEN_KEY);
		String user =  GtalkUtils.getInstance(this).getString(GtalkConst.CUR_USER_KEY);
		if(null == accessToken || 0 == accessToken.length()){
			toSetting();
			finish();
		}
		mListView = (ListView)findViewById(R.id.friend_list);
		mUtils = GtalkUtils.getInstance(this);
		mService = GtalkAPI.instance();
		mFilterEt = new EditText(this);
		mFilterEt.setLayoutParams(new LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mFilterEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				mAdapter.getFilter().filter(s.toString());
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(FRIEND_NAME_KEY, 
					parent.getAdapter().getItem(position).toString());
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
		reqInitAccount(user, accessToken);
//		reqFriends();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
	
		mFilterEt.setHint("search");
		menu.add("Search")
			.setIcon(R.drawable.abs__ic_search)
			.setActionView(mFilterEt)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			
		return true;
	}
	
//	private void initAccount(){
//		String userName = mUtils.getUser();
//    	String password = mUtils.getPassword();
//    	if(userName.equals(GtalkUtils.INVALUE) || password.equals(GtalkUtils.INVALUE)){
//    		toSetting();
//    		return;
//    	}
//    	reqInitAccount(userName, password);
//	}
//	
	private void reqInitAccount(final String userName, final String accessToken) {
		new Thread() {
			@Override
			public void run() {
				mIsSucc = mService.oauthLogin(userName, accessToken);
				Message msg = mHander.obtainMessage(INIT_ACCOUNT_MSG);
				mHander.sendMessage(msg);
			}
		}.start();
	}

	private void reqFriends() {
		new Thread() {
			@Override
			public void run() {
				mFriendsArray = mService.getFriends();
				Message msg = mHander.obtainMessage(GET_FIRENDS_MSG);
				Log.d(" size is ======> =====>", mFriendsArray.length + "");
				mHander.sendMessage(msg);
			}
		}.start();
	}
	
	private void toSetting() {
		Intent intent = new Intent();
		intent.setClass(this, GtalkLoginAct.class);
		startActivityForResult(intent, TO_SETTING);
	}
	
}