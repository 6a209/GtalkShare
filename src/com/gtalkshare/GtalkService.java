package com.gtalkshare;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.accounts.AccountManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author 6a209 4:03:20 PM Apr 9, 2012
 */

public class GtalkService {

	private XMPPConnection mConnection;
	
	// private Handler
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
		Presence presence = new Presence(Presence.Type.available);
		mConnection.sendPacket(presence);

		return true;
	}

	/**
	 * 获取好友列表
	 */
	public String[] getFriends() {
		Collection<RosterEntry> rosterEntries = mConnection.getRoster().getEntries();
//		Log.v("the size is ", " " + rosterEntries.size());
		String [] array = new String[rosterEntries.size()];
		RosterEntry [] rosterArray = new RosterEntry[rosterEntries.size()];
		rosterEntries.toArray(rosterArray);
		for(int i = 0; i < rosterEntries.size(); i++){
			array[i] = rosterArray[i].getUser();
		}
//		Log.d("user is ", array.toString());
		return array;
	}

	/**
	 * 发送消息
	 * 
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