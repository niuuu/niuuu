package com.app.onenet.activity;

import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.adpater.MyAccountAdapter;
import com.app.onenet.adpater.PlatformAdapter;
import com.app.onenet.constant.Constants;
import com.app.onenet.db.DBHelper;
import com.app.onenet.model.DataRequest;
import com.app.onenet.model.PlatformInfo;
import com.app.onenet.model.UserInfo;
import com.app.onenet.sync.http.RequestParams;
import com.app.onenet.utils.SPUtils;
import com.app.onenet.utils.SignatureHelper;
import com.app.onenet.utils.Utility;

public class HomeActivity extends DefaultActivity implements OnClickListener,OnItemLongClickListener {
	private Button jump_post_btn;
	private TextView title_myaccount_tv;
	private TextView title_platform_tv;
	private List<PlatformInfo> platformInfos;
	private ListView platform_lv;
	private ListView my_account_lv;
	private List<UserInfo> userInfos;
	private DBHelper dbHelper;
	private MyAccountAdapter myAccountAdapter;

	@Override
	public void logicDispose() {
		// 获取所有的平台列表
		dbHelper = DBHelper.getInstance(this);
		platformInfos = dbHelper.getAllPlatformInfo();
		platform_lv.setAdapter(new PlatformAdapter(this, platformInfos));
		Utility.setListViewHeightBasedOnChildren(platform_lv);
		userInfos = dbHelper.getAllUserInfo();
		myAccountAdapter=new MyAccountAdapter(this, userInfos);
		my_account_lv.setAdapter(myAccountAdapter);
		Utility.setListViewHeightBasedOnChildren(my_account_lv);
		title_myaccount_tv.setText(String.format(getString(R.string.app_tx_title_myaccount_list), userInfos.size()));
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.home_activity);
	}

	@Override
	public void initView() {
		my_account_lv = (ListView) findViewById(R.id.lv_my_account_list);
		title_myaccount_tv=(TextView)findViewById(R.id.tv_tx_title_myaccount);
		title_platform_tv=(TextView)findViewById(R.id.tv_tx_title_platform);
		platform_lv=(ListView)findViewById(R.id.lv_platform_list);
		jump_post_btn=(Button)findViewById(R.id.btn_home_fast_post);
		
	}

	@Override
	public void listener() {
		my_account_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				UserInfo userInfo=userInfos.get(arg2);
				Intent intent= new Intent(HomeActivity.this,MyWeiboActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString(UserInfo.AUTHID, userInfo.getAuthId());
				bundle.putString(UserInfo.NICKNAME, userInfo.getNickName());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		my_account_lv.setOnItemLongClickListener(this);
		platform_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PlatformInfo platformInfo=platformInfos.get(arg2);
				Intent intent=new Intent(HomeActivity.this,OauthActivity.class); 
				Bundle bundle=new Bundle();
				log.i(platformInfo.getName()+platformInfo.getPid());
				bundle.putString(Constants.OAUTH_TITLE, platformInfo.getSname());
				bundle.putString(PlatformInfo.NAME, platformInfo.getName());
				bundle.putInt(PlatformInfo.PID, platformInfo.getPid());
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
		jump_post_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 拍照
		case R.id.btn_home_fast_post:
			startActivity(new Intent(this,NewWeiboActivity.class));
			break;
		default:
			break;
		}

	}
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		final UserInfo userInfo=userInfos.get(arg2);
		new AlertDialog.Builder(this).setTitle(getString(R.string.app_title_info)).setMessage(getString(R.string.app_is_unbind))
		.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				unbindUser(userInfo);
			}

			

			
		})
		.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).setCancelable(true).show()
;
		return false;
	}
	
	//解除绑定
		private void unbindUser(final UserInfo userInfo){
			DataRequest dr=new DataRequest();
			TreeMap<String, String> treeMap=new TreeMap<String, String>();
			treeMap.put("app_id", Constants.APP_KEY);
			treeMap.put("auth_id", userInfo.getAuthId());
			String team=SPUtils.getStringPreference(this, Constants.SP_FILE_NAME, Constants.SP_GID, "");
			treeMap.put("team", team);
			treeMap=SignatureHelper.sigParams(treeMap);
			dr.requestParams=new RequestParams(treeMap);
			dr.url=Constants.APP_DOMAIN+Constants.OAUTH_DEL_API;
			buildData(dr, new DataCallback<String>() {
				@Override
				public void processData(String paramObject, boolean paramBoolean) {
					log.i(paramObject);
					DBHelper dbHelper=DBHelper.getInstance(context);
					dbHelper.delUserInfo(userInfo.getAuthId());
					userInfos.remove(userInfo);
					myAccountAdapter.notifyDataSetChanged();
				}
				
			});
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			return false;
		}

}
