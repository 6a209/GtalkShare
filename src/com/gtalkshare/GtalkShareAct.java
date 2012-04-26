package com.gtalkshare;

import org.xbill.DNS.MRRecord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class GtalkShareAct extends A6BaseAct {
	
	private GtalkService mService;
	private GtalkUtils mUtils;
	
	private Button mBtnRight;
	private TextView mTvTitle;
	private EditText mEditContent;
	private AutoCompleteTextView mUserNameAutoComplete;
	private Button mSendBtn;
	
	private boolean mIsSucc = false;
	
	private static final int TO_SETTING = 0x01;
	
	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg){
			String str = getResources().getString(R.string.share_success);
			Toast.makeText(GtalkShareAct.this, str, Toast.LENGTH_SHORT).show();
			finish();
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.base_title, mLyTitle, true);
        inflater.inflate(R.layout.main, mLyBody, true);
        mTvTitle = (TextView)findViewById(R.id.title_tv_content);
        mTvTitle.setText("Gtalk");
        Intent intent = getIntent();
        String content = intent.getStringExtra(Intent.EXTRA_TEXT);
        mUtils = GtalkUtils.getInstance(this);
        mService = new GtalkService();
        
        mEditContent = (EditText)findViewById(R.id.content);
        mUserNameAutoComplete = (AutoCompleteTextView)findViewById(R.id.user_name);
        mSendBtn = (Button)findViewById(R.id.send_btn);
        
        mBtnRight = (Button)findViewById(R.id.title_btn_right);
        mBtnRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toSetting();
			}
		});
        init(content);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(TO_SETTING == requestCode){
    		if(!mIsSucc){
        		init(mEditContent.getText().toString());
    		}
    	}
    }
    
    private void init(String content){
    	mEditContent.setText(content);
    	String [] array = null;
    	String userName = mUtils.getUser();
    	String password = mUtils.getPassword();
    	if(userName.equals(GtalkUtils.INVALUE) || password.equals(GtalkUtils.INVALUE)){
    		toSetting();
    		return;
    	}
    	
    	mIsSucc = mService.initAccount(userName, password);
    	if(mIsSucc){
    		array = mService.getFriends();
    	}else{
    		Toast.makeText(this, getString(R.string.err_toast), Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array);
//    	Log.v("the list is ", array.toString() + array.length);
    	mUserNameAutoComplete.setAdapter(adapter);

    	mSendBtn.setOnClickListener(new OnClickListener() {
    		
			@Override
			public void onClick(View v) {
				final String content = mEditContent.getText().toString();
				if(null == content || 0 == content.length()){
					Toast.makeText(GtalkShareAct.this, 
						mUtils.getString(R.string.content_hint), Toast.LENGTH_SHORT).show();
					return;
				}
				final String userName = mUserNameAutoComplete.getText().toString();
//				Log.v("the cur user name is ", userName);
				if(null == userName || 0 == userName.length()){
					Toast.makeText(GtalkShareAct.this, 
						mUtils.getString(R.string.user_name_filter), Toast.LENGTH_SHORT).show();
					return;
				}
				new Thread(){
					@Override
					public void run(){
						boolean success = mService.sendMessage(userName, content);
						if(success){
							mHander.sendEmptyMessage(0);
						}
					}
				}.start();
				
			}
		});
    	
    }
    
    private void toSetting(){
    	Intent intent = new Intent();
		intent.setClass(this, GtalkPreferenceAct.class);
		startActivityForResult(intent, TO_SETTING);
    }
    
    
}