package com.app.onenet.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.onenet.R;
import com.app.onenet.adpater.PersonMyAccountAdapter;
import com.app.onenet.db.DBHelper;
import com.app.onenet.model.UserInfo;
import com.app.onenet.utils.Utility;
import com.app.onenet.widget.CornerListView;
import com.app.onenet.widget.NavigationView;

/**
 * 个人页面
 * @author niu
 *
 */
public class PersonHomeActivity extends DefaultActivity implements OnClickListener,OnItemClickListener{
	private CornerListView cornerListView;
	private List<UserInfo> userInfos;
	@SuppressWarnings("unused")
	private NavigationView nv;
	@Override
	public void logicDispose() {
		//获取我的分类
		DBHelper dbHelper=DBHelper.getInstance(this);
		userInfos=dbHelper.getAllUserInfo();
		cornerListView.setAdapter(new PersonMyAccountAdapter(this, userInfos));
		Utility.setListViewHeightBasedOnChildren(cornerListView);
	}
	@Override
	public void setupViewLayout() {
		setContentView(R.layout.person_home_activity);
	}

	@Override
	public void initView() {
		cornerListView=(CornerListView)findViewById(R.id.lv_my_account_list);
		nv=(NavigationView)findViewById(R.id.navigationView);
	}

	@Override
	public void listener() {
		cornerListView.setOnItemClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//拍照
		case R.id.btn_title_left:
			
			break;
		//微博
		case R.id.btn_title_right:
			startActivity(new Intent(this,NewWeiboActivity.class));
			break;

		default:
			break;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserInfo userInfo=userInfos.get(arg2);
		Intent intent=new Intent(this,ProfileHomeActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString(UserInfo.AUTHID, userInfo.getAuthId());
		bundle.putString(UserInfo.USERNAME, userInfo.getUserName());
		intent.putExtras(bundle);
		startActivity(intent);
		
	}

}
