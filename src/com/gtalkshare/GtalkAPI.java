package com.gtalkshare;

import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * @author 6a209 4:03:20 PM Apr 9, 2012
 */

public class GtalkAPI {

	private XMPPConnection mConnection;
	private static GtalkAPI mService;
	
	private GtalkAPI(){
		
	}
	
	public static GtalkAPI instance(){
		if(null == mService){
			mService = new GtalkAPI();
		}
		return mService;
	}
	
	
	public String getAuthToken(String name, Activity activity) {
		Context context = activity.getApplicationContext();
		String retVal = "";
		Account account = new Account(name, "com.google");
		AccountManagerFuture<Bundle> accFut = AccountManager.get(context)
				.getAuthToken(account, "mail", null, activity, null, null);
		try {
			Bundle authTokenBundle = accFut.getResult();
			retVal = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN)
					.toString();
			return retVal;
		} catch (OperationCanceledException e) {
			e.printStackTrace();
		} catch (AuthenticatorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean oauthLogin(String userName, String accessToken){
		SASLAuthentication.registerSASLMechanism(GtalkOAuth2.NAME, GtalkOAuth2.class);
		SASLAuthentication.supportSASLMechanism(GtalkOAuth2.NAME, 0 );

		ConnectionConfiguration configuration = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		configuration.setSASLAuthenticationEnabled(true);
 
		mConnection = new XMPPConnection(configuration);
//		String saslAuthString = getAuthToken(userName, act);
		try {
			mConnection.connect();
			mConnection.login(userName, accessToken);
		} catch (XMPPException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 帐号登入
	 */
	public boolean initAccount(String userName, String password) {
		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		mConnection = new XMPPConnection(connConfig);
		try {
			mConnection.connect();
			mConnection.login(userName, password);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
//		Presence presence = new Presence(Presence.Type.available);
//		mConnection.sendPacket(presence);

		return true;
	}

	/**
	 * 获取好友列表
	 */
	public String[] getFriends() {
		Collection<RosterEntry> rosterEntries = mConnection.getRoster().getEntries();
		String [] array = new String[rosterEntries.size()];
		RosterEntry [] rosterArray = new RosterEntry[rosterEntries.size()];
		rosterEntries.toArray(rosterArray);
		for(int i = 0; i < rosterEntries.size(); i++){
			
			array[i] = rosterArray[i].getUser();
		}
		return array;
	}

	/**
	 * 发送消息
	 * @param uid
	 * @param message
	 */
	public boolean sendMessage(String userName, String message) {
		ChatManager chatmanager = mConnection.getChatManager();
		Chat chat = chatmanager.createChat(userName, null);
		try {
			chat.sendMessage(message);
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}