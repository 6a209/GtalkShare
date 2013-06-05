package com.gtalkshare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * @author 6a209
 * Jun 1, 2013
 */
public class GtalkShareAct extends BaseAct {
	
	private GtalkAPI mService;
	private GtalkUtils mUtils;
	
//	private Button mBtnRight;
	private TextView mTvTitle;
	private EditText mEditContent;
	private TextView mShare2FriendsTv;
//	private AutoCompleteTextView mUserNameAutoComplete;
	private Button mSendBtn;
	private String mFriendName;
	
	
	private static final int TO_SETTING = 0x01;
	private static final int TO_CHIOCE_FRIENDS = 0x02;
	
	private static final int INIT_ACCOUNT_MSG = 0x10;
	private static final int SEND_MSG = 0x11;
	private static final int GET_FIRENDS_MSG = 0x12;
	
	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case SEND_MSG:
				String str = getResources().getString(R.string.share_success);
				Toast.makeText(GtalkShareAct.this, str, Toast.LENGTH_SHORT).show();
				finish();
				break;
			default:
				break;
			}
			
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.main, mBodyLy, true);
        Intent intent = getIntent();
        String content = intent.getStringExtra(Intent.EXTRA_TEXT);
        mUtils = GtalkUtils.getInstance(this);
        mService = GtalkAPI.instance();
        mEditContent = (EditText)findViewById(R.id.content);
        mShare2FriendsTv = (TextView)findViewById(R.id.share_to_friends);
        mSendBtn = (Button)findViewById(R.id.send_btn);
        
//        mBtnRight = new Button(this);
//        mBtnRight.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				toSetting();
//			}
//		});
        init(content);
        toChioceFriends();
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu){
	
		menu.add("setting")
			.setIcon(R.drawable.moreitems_setting_icon)
			.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(com.actionbarsherlock.view.MenuItem item) {
					Intent intent = new Intent();
					intent.setClass(GtalkShareAct.this, GtalkLoginAct.class);
					startActivity(intent);
					return true;
				}
			})
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			
		return true;
	}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(resultCode != RESULT_OK){
    		return;
    	}
    	if(TO_SETTING == requestCode){
    	}
    	if(TO_CHIOCE_FRIENDS == requestCode){
    		mFriendName = data.getStringExtra(GtalkFriendsAct.FRIEND_NAME_KEY);
    		if(null != mFriendName){
        		mShare2FriendsTv.setText(mFriendName);
    		}
    	}
    }
    
    private void init(String content){
    	mEditContent.setText(content);
    	mSendBtn.setOnClickListener(new OnClickListener() {
    		
			@Override
			public void onClick(View v) {
				reqSendMsg();
			}
		});
    	
    }
    
    private void toSetting(){
    	Intent intent = new Intent();
		intent.setClass(this, GtalkLoginAct.class);
		startActivityForResult(intent, TO_SETTING);
    }
    
    private void toChioceFriends(){
    	Intent intent = new Intent();
    	intent.setClass(this, GtalkFriendsAct.class);
    	startActivityForResult(intent, TO_CHIOCE_FRIENDS);
    }
    
 
    
    private void reqSendMsg(){
    	final String content = mEditContent.getText().toString();
		if(null == content || 0 == content.length()){
			Toast.makeText(GtalkShareAct.this, 
				mUtils.getString(R.string.content_hint), Toast.LENGTH_SHORT).show();
			return;
		}
		if(null == mFriendName || 0 == mFriendName.length()){
			Toast.makeText(GtalkShareAct.this, 
				mUtils.getString(R.string.user_name_filter), Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(){
			@Override
			public void run(){
				boolean success = mService.sendMessage(mFriendName, content);
				if(success){
					mHander.sendEmptyMessage(SEND_MSG);
				}
			}
		}.start();
    }
}