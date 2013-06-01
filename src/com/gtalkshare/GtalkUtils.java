package com.gtalkshare;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
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
		String userName = INVALUE;
		AccountManager manager = AccountManager.get(mCtx);
		Account [] array = manager.getAccountsByType("com.google");
		if(array.length != 0){
			mAccount = array[0];
			userName = array[0].name;
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(getString(R.string.user_name), userName);
			editor.commit();
		}else{
		}
		return preferences.getString(getString(R.string.user_name), userName);
	}
	
	public String getPassword(){
		SharedPreferences preferences = 
				PreferenceManager.getDefaultSharedPreferences(mCtx);
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