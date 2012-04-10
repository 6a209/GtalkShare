package com.gtalkshare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class GtalkShareAct extends Activity {
	
	private GtalkService mService;
	private GtalkUtils mUtils;
	
	private EditText mContent;
	private AutoCompleteTextView mUserNameAutoComplete;
	private Button mSendBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent();
        String content = intent.getStringExtra(Intent.EXTRA_TEXT);
        mUtils = GtalkUtils.getInstance(this);
        mService = new GtalkService();
        
        mContent = (EditText)findViewById(R.id.content);
        mUserNameAutoComplete = (AutoCompleteTextView)findViewById(R.id.user_name);
        mSendBtn = (Button)findViewById(R.id.send_btn);
        
        init(content);
    }
    
    private void init(String content){
    	mContent.setText(content);
    	String [] array = null;
    	String userName = mUtils.getUser();
    	String password = mUtils.getPassword();
    	boolean isSucc = mService.initAccount(userName, password);
    	if(isSucc){
    		array = mService.getFriends();
    	}
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array);
    	Log.v("the list is ", array.toString() + array.length);
    	mUserNameAutoComplete.setAdapter(adapter);
    	mSendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mService.sendMessage(mUserNameAutoComplete.getText().toString(),
					mContent.getText().toString());
			}
		});
    	
    }
    
}