package com.app.onenet;

import java.io.File;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import com.app.onenet.constant.Constants;
import com.app.onenet.sync.http.AsyncHttpClient;
import com.app.onenet.utils.NetworkUtils;

public class ApplicationEx extends Application {
	private ActivityManager activityManager = null;
	public static int mVersionCode;
	public static String mVersionName;
	public static AsyncHttpClient client;
	public static String appSdCardPath;
	public static int networkState;
	public static String APPKEY;
	public static String APPSECRET;

	public ApplicationEx() {
	}

	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		client = new AsyncHttpClient();
		// 初始化自定义Activity管理器
		activityManager = ActivityManager.getScreenManager();
		initLocalVersion();
		initEnv();
		System.out.println(mVersionCode+">>>>>>>>>>>>"+mVersionName);
	}

	private void initEnv() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory().getPath() + Constants.APP_DIR);
			if (!file.exists()) {
				if (file.mkdirs()) {
					appSdCardPath = file.getAbsolutePath();
				}
			} else {
				appSdCardPath = file.getAbsolutePath();
			}
		}
		networkState = NetworkUtils.getNetworkState(this);
		

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		shutdownHttpClient();
	}

	private void shutdownHttpClient() {
		if (client != null
				&& client.getHttpClient().getConnectionManager() != null) {
			client.getHttpClient().getConnectionManager().shutdown();
		}
	}

	public AsyncHttpClient getHttpClient() {
		if (client != null) {
			return client;
		} else {
			return new AsyncHttpClient();
		}
	}
	public void initAppServerInfo(){
		ApplicationInfo appInfo;
		try {
			appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			APPKEY = appInfo.metaData.getString("APP_KEY");
			APPSECRET=appInfo.metaData.getString("APP_SECRET");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 本地版本
	 */
	public void initLocalVersion() {
		PackageInfo pinfo;
		try {
			pinfo = this.getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			mVersionCode = pinfo.versionCode;
			mVersionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 应用退出
	 * 
	 * @param context
	 */
	public void exitApp(final Context context) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
		alertBuilder
				.setTitle(this.getString(R.string.app_title_info))
				.setMessage(this.getString(R.string.app_msg_is_exit))
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		alertBuilder.create().show();
	}
}
