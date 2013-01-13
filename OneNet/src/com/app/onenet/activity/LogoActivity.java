package com.app.onenet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.service.OneNetService;
import com.app.onenet.utils.SPUtils;

/**
 * 欢迎界面
 * @author niu
 *
 */
public class LogoActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.logo);
		LinearLayout layout = (LinearLayout) findViewById(R.id.logo_layout);
		AutoBackground(this, layout, R.drawable.logo, R.drawable.logo);
		start();
	}

	// 获取首屏
	public static void AutoBackground(Activity activity, View view,
			int Background_v, int Background_h) {
		int orient = ScreenOrient(activity);
		if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { // 纵向
			view.setBackgroundResource(Background_v);
		} else { // 横向
			view.setBackgroundResource(Background_h);
		}
	}

	// 获取屏幕方向
	public static int ScreenOrient(Activity activity) {
		int orient = activity.getRequestedOrientation();
		if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				&& orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			// 宽>高为横屏,反正为竖屏
			WindowManager windowManager = activity.getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
					: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		}
		return orient;
	}

	public void start() {
		new CountDownTimer(2000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				Intent intent = new Intent();
				intent.setClass(LogoActivity.this, OneNetService.class);
				intent.putExtra("op", -1);
				startService(intent);
				boolean isFirst=SPUtils.getBooleanPreference(getApplicationContext(),Constants.SP_FILE_NAME, Constants.SP_ISFIRST, true);
				if(isFirst){
					intent.setClass(LogoActivity.this, MainActivity.class);
					startActivity(intent);
				}
				else{
					intent.setClass(LogoActivity.this, MainActivity.class);
					startActivity(intent);
				}

				int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
				if (VERSION >= 5) {
					LogoActivity.this.overridePendingTransition(
							R.anim.alpha_in, R.anim.alpha_out);
				}
				finish();
			}
		}.start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 在欢迎界面屏蔽BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

}
