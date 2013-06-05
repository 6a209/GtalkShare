package com.gtalkshare;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * 
 * @author 6a209
 * 5:04:01 PM Apr 9, 2012
 */
public class GtalkUtils{
	
	private Context mCtx;
	private Account mAccount;
	
	
	private static GtalkUtils sUtils;
	private SharedPreferences mPreferences;
//	private static final String KEY_USER = "user";
//	private static final String KEY_PWD = "password";
	
	public static final String INVALUE = "invalue"; 
	
	
	private GtalkUtils(Context ctx){
		mCtx = ctx;
		mPreferences = mCtx.getSharedPreferences(
				GtalkConst.PREFERENCE_NAME, Context.MODE_PRIVATE);
	}
	
	public static GtalkUtils getInstance(Context ctx){
		
		if(null == sUtils){
			sUtils = new GtalkUtils(ctx);
		}
		return sUtils;
	}
	
	public String getString(String key){
		return mPreferences.getString(key, "");
	}
	
	public void setString(String key, String value){
		Editor edit = mPreferences.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	
	public String getUser(){
		SharedPreferences preferences = mCtx.getSharedPreferences(
				GtalkConst.PREFERENCE_NAME, Context.MODE_PRIVATE);
		String userName = INVALUE;
		AccountManager manager = AccountManager.get(mCtx);
		
		Account [] array = manager.getAccountsByType("com.google");
		if(array.length != 0){
			mAccount = array[0];
			userName = array[0].name;
		}
		if(preferences.getString(getString(R.string.user_name), INVALUE).equals(INVALUE)){
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(getString(R.string.user_name), userName);
			editor.commit();
		}
		return preferences.getString(getString(R.string.user_name), userName);
	}
	
	public String getPassword(){
		SharedPreferences preferences = mCtx.getSharedPreferences(
				GtalkConst.PREFERENCE_NAME, Context.MODE_PRIVATE);
		return preferences.getString(getString(R.string.password), INVALUE);
//		if(null != mAccount){
//			AccountManager manager = AccountManager.get(mCtx);
//			Log.v("the password is", manager.getPassword(mAccount));
//			return manager.getPassword(mAccount);
//		}
//		return null;
		
	}
	
	public String getString(int id){
		return mCtx.getResources().getString(id);
	}
	
	
}