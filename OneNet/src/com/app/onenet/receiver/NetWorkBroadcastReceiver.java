package com.app.onenet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.app.onenet.ApplicationEx;
import com.app.onenet.utils.NetworkUtils;

/**
 * 监听网络变化广播
 * @author niu
 *
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
	            ApplicationEx.networkState = NetworkUtils.getNetworkState(context);
	        }

	}

}
