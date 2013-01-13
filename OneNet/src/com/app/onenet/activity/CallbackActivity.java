package com.app.onenet.activity;

import android.content.Intent;
import android.os.Bundle;

import com.app.onenet.constant.Constants;
import com.app.onenet.db.DBHelper;
import com.app.onenet.model.UserInfo;
import com.app.onenet.service.OneNetService;
import com.app.onenet.utils.SPUtils;
import com.app.onenet.utils.Utils;

/**
 * 授权回调
 * 
 * @author niu
 * 
 */
public class CallbackActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (Integer.parseInt(bundle.getString("state")) == 1) {
			String authId = bundle.getString("auth_id");// 服务端授权码
			String userId = bundle.getString("uid");// 用户ID
			String userName = bundle.getString("username"); // 用户名
			String nickName = bundle.getString("nickname");//昵称
			String icon = bundle.getString("head_pic");//头像
			String team=bundle.getString("team");//组
			Integer pid = bundle.getInt(Constants.PID);
			log.i("auth_id="+authId+",userName="+userName+",nickName="+nickName+",icon="+icon+",team="+team+",pid="+pid);
			DBHelper dbHelper = DBHelper.getInstance(this);
			UserInfo userInfo = dbHelper.getUser(authId);
			// 本地存在已经授权过
			if (userInfo != null) {
				userInfo.setAuthId(authId);
				userInfo.setUserId(userId);
				userInfo.setUserName(userName);
				userInfo.setUserIcon(icon);
				userInfo.setNickName(nickName);
				userInfo.setPid(pid);
				dbHelper.updateUserInfo(userInfo);
			} else {
				userInfo = new UserInfo();
				// 判断是否是第一次授权如果是
				if (SPUtils.getBooleanPreference(this, Constants.SP_FILE_NAME,
						Constants.SP_ISFIRST, true)) {
					SPUtils.setBooleanPreferences(this, Constants.SP_FILE_NAME,
							Constants.SP_ISFIRST, false);
					SPUtils.setStringPreferences(this, Constants.SP_FILE_NAME, Constants.SP_GID, team);
					//数据同步
					Intent oneIntent=new Intent(CallbackActivity.this,OneNetService.class);
					oneIntent.putExtra("op", 2);
					startService(oneIntent);
					Utils.showToast(getApplicationContext(), "用户资料同步中，请稍后", false);
					userInfo.setIsDefault(1);
					
				}
				userInfo.setAuthId(authId);
				userInfo.setUserId(userId);
				userInfo.setUserName(userName);
				userInfo.setUserIcon(icon);
				userInfo.setNickName(nickName);
				userInfo.setPid(pid);
				dbHelper.saveUserInfo(userInfo);
			}
			startActivity(new Intent(this, MainActivity.class));
			finish();

		}

	}

}
