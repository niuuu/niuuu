package com.app.onenet.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.app.onenet.R;
import com.app.onenet.adpater.PlatformAdapter;
import com.app.onenet.constant.Constants;
import com.app.onenet.db.DBHelper;
import com.app.onenet.model.PlatformInfo;
import com.app.onenet.widget.NavigationView;

/**
 * 接入平台列表
 * @author niu
 *
 */
public class PlatformListActivity  extends DefaultActivity implements OnClickListener,OnItemClickListener {
	private NavigationView nv;
	private ListView platform_lv;
	private List<PlatformInfo> platformInfos;
	@Override
	public void logicDispose() {
		// 获取所有的平台列表
		DBHelper dbHelper = DBHelper.getInstance(this);
		platformInfos = dbHelper.getAllPlatformInfo();
		platform_lv.setAdapter(new PlatformAdapter(this, platformInfos));
		
	}
	@Override
	public void setupViewLayout() {
		setContentView(R.layout.platform_list_activity);
	}
	@Override
	public void initView() {
		platform_lv=(ListView)findViewById(R.id.lv_platform_list);
		nv=(NavigationView)findViewById(R.id.navigationView);
	}
	@Override
	public void listener() {
		platform_lv.setOnItemClickListener(this);
		nv.getBtn_right().setOnClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		PlatformInfo platformInfo=platformInfos.get(arg2);
		Intent intent=new Intent(PlatformListActivity.this,OauthActivity.class); 
		Bundle bundle=new Bundle();
		log.i(platformInfo.getName()+platformInfo.getPid());
		bundle.putString(Constants.OAUTH_TITLE, platformInfo.getSname());
		bundle.putString(PlatformInfo.NAME, platformInfo.getName());
		bundle.putInt(PlatformInfo.PID, platformInfo.getPid());
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(PlatformListActivity.this,MainActivity.class);
		startActivity(intent);
		
	}
	
	
	

}
