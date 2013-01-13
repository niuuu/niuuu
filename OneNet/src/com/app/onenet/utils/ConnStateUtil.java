package com.app.onenet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**网络连接
 * @author niu
 * @date 2010-12-23 
 */
public class ConnStateUtil {

	public static boolean isConnectInternet(Context context){
		boolean connFalg=false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		 if (networkInfo != null){
			 connFalg = networkInfo.isAvailable();
		 }
		return connFalg;
	}
	
	public static boolean isWifiConnected(Context context)
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if(wifiNetworkInfo.isConnected())
	    {
	        return true ;
	    }
	 
	    return false ;
	}
}
