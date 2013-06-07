package com.gtalkshare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.flurry.android.FlurryAgent;


public class BaseAct extends SherlockActivity{
	
	protected FrameLayout mBodyLy;
	protected ProgressDialog mProgressDialog;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.base_layout);
		mBodyLy = (FrameLayout)findViewById(R.id.base_body);
//		mTitleLy = (LinearLayout)findViewById(R.id.base_title);
	}

	@Override
	public void onStart(){
		super.onStart();
		FlurryAgent.onStartSession(this, "G2C7RX9RAHHJ6LMHDEMC");
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	
	@Override
	public void onStop(){
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	protected void showProgress(String message){
		mProgressDialog = ProgressDialog.show(this, null, message);
	}
	
	protected void hideProgress(){
		if(null != mProgressDialog && mProgressDialog.isShowing()){
			mProgressDialog.dismiss();
		}
	}
	
	protected void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 刷新当前页面！
	 */
	protected void refresh(){
		
	}
	
	
}