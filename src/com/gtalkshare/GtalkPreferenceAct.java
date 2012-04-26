package com.gtalkshare;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class GtalkPreferenceAct extends PreferenceActivity{
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		addPreferencesFromResource(R.layout.preference);
	}
	
}