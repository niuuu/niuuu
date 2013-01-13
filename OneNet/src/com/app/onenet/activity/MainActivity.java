package com.app.onenet.activity;

import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.app.onenet.ActivityManager;
import com.app.onenet.R;
import com.app.onenet.utils.Utils;

/**
 * 带Tab的主页面
 * 
 * @author niu
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	private static final String MESSAGECHANGEACTION = "Intent.ACTION_MESSAGE_CHANGE";
	private MessageUpdateChanageReceiver messageUpdateChanageReceiver;
	private TabHost tabhost;
	// 内容Intent
	private Intent tabIntent1, tabIntent2, tabIntent3, tabIntent4;
	private RadioButton tab_0, tab_1, tab_2, tab_3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		// tabhost = getTabHost();
		LocalActivityManager activityGroup = new LocalActivityManager(this,
				true);
		tabhost = (TabHost) findViewById(android.R.id.tabhost);
		activityGroup.dispatchCreate(savedInstanceState);
		tabhost.setup(activityGroup);
		init();
		this.tabhost.setCurrentTabByTag("a");// 设置默认
		tab_0.setBackgroundResource(R.drawable.home_btn_bg_d);
		// 注册广播接受者
		IntentFilter filter = new IntentFilter();
		filter.addAction(MESSAGECHANGEACTION);
		messageUpdateChanageReceiver = new MessageUpdateChanageReceiver();
		registerReceiver(messageUpdateChanageReceiver, filter);
	}
	private void init() {
		tabIntent1 = new Intent(this, HomeActivity.class);
		// 获取intent
		// Intent intent = getIntent();
		// tabIntent1.putExtras(intent.getExtras());
		// System.out.println(intent);
		tabhost.addTab(tabhost
				.newTabSpec("a")
				.setIndicator(
						getResources().getString(R.string.activity_tab_home),
						getResources().getDrawable(R.drawable.icon_1_n))
				.setContent(tabIntent1));
		tabIntent2 = new Intent(this, MyWeiboActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("b")
				.setIndicator(
						getResources().getString(R.string.activity_tab_1),
						getResources().getDrawable(R.drawable.icon_2_n))
				.setContent(tabIntent2));

		tabIntent3 = new Intent(this, ChartActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("c")
				.setIndicator(
						getResources().getString(R.string.activity_tab_2),
						getResources().getDrawable(R.drawable.icon_3_n))
				.setContent(tabIntent3));

		tabIntent4 = new Intent(this, PersonHomeActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("d")
				.setIndicator(
						getResources().getString(R.string.activity_tab_3),
						getResources().getDrawable(R.drawable.icon_4_n))
				.setContent(tabIntent4));
		tab_0 = (RadioButton) findViewById(R.id.radio_button0);
		tab_1 = (RadioButton) findViewById(R.id.radio_button1);
		tab_2 = (RadioButton) findViewById(R.id.radio_button2);
		tab_3 = (RadioButton) findViewById(R.id.radio_button3);
		tab_0.setOnClickListener(this);
		tab_1.setOnClickListener(this);
		tab_2.setOnClickListener(this);
		tab_3.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:

			break;
		case R.id.menu_account:

			break;
		case R.id.menu_settings:

			break;
		case R.id.menu_feedback:

			break;
		case R.id.menu_exit:
			Utils.showExitDialog(this);
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(messageUpdateChanageReceiver);
		notificationEx.cancelNotification();
		ActivityManager.getScreenManager().popAllActivityExceptOne(
				MainActivity.class);
		
	}

	class MessageUpdateChanageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 更新界面
			if (intent.getAction().equals(MESSAGECHANGEACTION)) {
				
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radio_button0:
			this.tabhost.setCurrentTabByTag("a");
			tab_1.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_2_n), null, null);
			tab_2.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_3_n), null, null);
			tab_3.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_4_n), null, null);
			tab_0.setBackgroundResource(R.drawable.home_btn_bg);
			tab_1.setBackgroundResource(R.drawable.transparent);
			tab_2.setBackgroundResource(R.drawable.transparent);
			tab_3.setBackgroundResource(R.drawable.transparent);
			break;
		case R.id.radio_button1:
			this.tabhost.setCurrentTabByTag("b");
			tab_0.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_1_n), null, null);
			tab_2.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_3_n), null, null);
			tab_3.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_4_n), null, null);
			tab_0.setBackgroundResource(R.drawable.transparent);
			tab_1.setBackgroundResource(R.drawable.home_btn_bg);
			tab_2.setBackgroundResource(R.drawable.transparent);
			tab_3.setBackgroundResource(R.drawable.transparent);
			break;
		case R.id.radio_button2:
			this.tabhost.setCurrentTabByTag("c");
			tab_0.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_1_n), null, null);
			tab_1.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_2_n), null, null);
			tab_3.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_4_n), null, null);
			tab_0.setBackgroundResource(R.drawable.transparent);
			tab_1.setBackgroundResource(R.drawable.transparent);
			tab_2.setBackgroundResource(R.drawable.home_btn_bg);
			tab_3.setBackgroundResource(R.drawable.transparent);
			break;
		case R.id.radio_button3:
			this.tabhost.setCurrentTabByTag("d");
			tab_0.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_1_n), null, null);
			tab_1.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_2_n), null, null);
			tab_2.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
					.getDrawable(R.drawable.icon_3_n), null, null);
			tab_0.setBackgroundResource(R.drawable.transparent);
			tab_1.setBackgroundResource(R.drawable.transparent);
			tab_2.setBackgroundResource(R.drawable.transparent);
			tab_3.setBackgroundResource(R.drawable.home_btn_bg);
			break;
		default:
			break;
		
		}
			
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(event.getAction() == KeyEvent.ACTION_DOWN&&keyCode == KeyEvent.KEYCODE_BACK){
			//判断是否是当前 if ("a".equals(tabhost.getCurrentTabTag()))
			Utils.showExitDialog(this);
			return true;
		}else{		
			return super.onKeyDown(keyCode, event);

		}

	}
	@Override
	protected void onNewIntent(Intent intent) {
		Utils.showToast(this, "onnewsIntent", false);
		super.onNewIntent(intent);
	}

}
