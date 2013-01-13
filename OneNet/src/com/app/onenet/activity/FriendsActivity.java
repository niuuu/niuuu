package com.app.onenet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.onenet.R;
import com.app.onenet.widget.NavigationView;
import com.app.onenet.widget.RefreshListView;

/**
 * 用户微博关注页面
 * @author niu
 *
 */
public class FriendsActivity  extends DefaultActivity implements OnItemClickListener,OnClickListener{
	private NavigationView nv;
	private RefreshListView followers_lv;
	private Bundle bundle;
	private Integer pid;
	private String uname;
	private int r_type;
	private boolean status_show=true;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 0:
			onBackPressed();
			break;

		default:
			break;
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	@Override
	public void logicDispose() {
		Intent intent=getIntent();
		bundle=intent.getExtras();
		
	}
	@Override
	public void setupViewLayout() {
		setContentView(R.layout.friends_activity);
		
	}
	@Override
	public void initView() {
		followers_lv=(RefreshListView)this.findViewById(R.id.lv_followers);
		nv=(NavigationView)findViewById(R.id.navigationView);
		
	}
	@Override
	public void listener() {
		followers_lv.setOnItemClickListener(this);
		nv.getBtn_left().setOnClickListener(this);
		
	}
	
}
