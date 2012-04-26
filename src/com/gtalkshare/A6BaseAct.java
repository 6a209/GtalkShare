package com.gtalkshare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.flurry.android.FlurryAgent;


public class A6BaseAct extends Activity{
	
	protected LinearLayout mLyTitle;
	protected FrameLayout mLyBody;
	protected ProgressDialog mProgressDialog;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLyTitle = new LinearLayout(this);
		setContentView(R.layout.base_layout);
		mLyBody = (FrameLayout)findViewById(R.id.base_body);
		mLyTitle = (LinearLayout)findViewById(R.id.base_title);
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
		if(null != mProgressDialog){
			mProgressDialog.dismiss();
		}
	}
	
	/**
	 * 刷新当前页面！
	 */
	protected void refresh(){
		
	}
	
	
}