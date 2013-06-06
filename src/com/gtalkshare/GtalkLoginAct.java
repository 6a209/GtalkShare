package com.gtalkshare;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * oauth login~~~
 * @author 6a209
 * Jun 3, 2013
 */
public class GtalkLoginAct extends BaseAct{
	
	private RadioGroup mRadioGroup;
	private Button mConnectBtn;
	private GtalkAPI mApi;
	private String mAccessToken;
	private boolean mIsSuccess;
	private static final int LOGIN_RESULT_MSG = 0x01;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {
			case LOGIN_RESULT_MSG:
				hideProgress();
				if(null != mAccessToken && 0 != mAccessToken.length()){
					GtalkUtils.getInstance(getApplicationContext()).setString(
						GtalkConst.ACCESS_TOKEN_KEY, mAccessToken);
					Intent intent = new Intent();
					intent.setClass(GtalkLoginAct.this, GtalkFriendsAct.class);
					startActivity(intent);
					finish();
				}else{
					toast("connect err");
				}
				
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.authorize);
		mRadioGroup = (RadioGroup)findViewById(R.id.user_list);
		mConnectBtn = (Button)findViewById(R.id.login_connect);
		mApi = GtalkAPI.instance();
		mConnectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress("connect...");
				new Thread(){
					@Override
					public void run(){
						int id = mRadioGroup.getCheckedRadioButtonId();
						RadioButton rb = (RadioButton)findViewById(id);
						String user = rb.getText().toString();
						mAccessToken = mApi.getAuthToken(user, GtalkLoginAct.this);
						mHandler.sendEmptyMessage(LOGIN_RESULT_MSG);
						GtalkUtils.getInstance(GtalkLoginAct.this).setString(GtalkConst.CUR_USER_KEY, user);
					}
				}.start();
			}
		});
		initUser();
	}
	
	private void initUser(){
		AccountManager am = AccountManager.get(this);
		Account[] userList = am.getAccountsByType("com.google");
		for(int i = 0; i < userList.length; i++){
			RadioButton rb = new RadioButton(this);
			rb.setText(userList[i].name);
			mRadioGroup.addView(rb);
		}
		
//		mRadioGroup.check(id)
	}

}