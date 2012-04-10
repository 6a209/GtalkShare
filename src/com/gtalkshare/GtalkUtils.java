package com.gtalkshare;

import com.gtalkshare.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * 
 * @author 6a209
 * 5:04:01 PM Apr 9, 2012
 */
public class GtalkUtils{
	
	private Context mCtx;
	
	private static GtalkUtils sUtils;
	
//	private static final String KEY_USER = "user";
//	private static final String KEY_PWD = "password";
	
	public static final String INVALUE = "invalue"; 
	
	
	private GtalkUtils(Context ctx){
		mCtx = ctx;
	}
	
	public static GtalkUtils getInstance(Context ctx){
		
		if(null == sUtils){
			sUtils = new GtalkUtils(ctx);
		}
		return sUtils;
	}
	
	
	public String getUser(){
		SharedPreferences preferences = 
				PreferenceManager.getDefaultSharedPreferences(mCtx);
		return preferences.getString(getString(R.string.user_name), INVALUE);
	}
	
	public String getPassword(){
		SharedPreferences preferences = 
				PreferenceManager.getDefaultSharedPreferences(mCtx);
		return preferences.getString(getString(R.string.password), INVALUE);
	}
	
	public String getString(int id){
		return mCtx.getResources().getString(id);
	}
	
	
}