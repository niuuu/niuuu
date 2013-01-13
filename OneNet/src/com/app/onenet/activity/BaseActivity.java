package com.app.onenet.activity;

import android.app.Activity;
import android.os.Bundle;

import com.app.onenet.ApplicationEx;
import com.app.onenet.NotificationEx;
import com.app.onenet.utils.CommonLog;
import com.app.onenet.utils.LogFactory;

public class BaseActivity extends Activity {
	public NotificationEx notificationEx;
	public static CommonLog log = LogFactory.createLog();
	public ApplicationEx application;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (ApplicationEx) this.getApplication();
		application.getActivityManager().pushActivity(this);
		notificationEx = new NotificationEx(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		application = (ApplicationEx) getApplication();
		application.getActivityManager().popActivity(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		notificationEx.showNotification();
	}

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// 按下HOME键
//		if (keyCode == KeyEvent.KEYCODE_HOME) {
//			notificationEx.showNotification();
//			moveTaskToBack(true);
//			return true;
//		}
//
//		return super.onKeyDown(keyCode, event);
//	}

	

}
