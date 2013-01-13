package com.app.onenet.activity;

import java.util.TreeMap;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.model.DataRequest;
import com.app.onenet.model.UserInfo;
import com.app.onenet.model.api.User;
import com.app.onenet.sync.http.RequestParams;
import com.app.onenet.utils.AsynImageLoader;
import com.app.onenet.utils.SignatureHelper;
import com.app.onenet.widget.NavigationView;

/**
 * 个人资料页面
 * 
 * @author niu
 * 
 */
public class ProfileHomeActivity extends DefaultActivity implements
		OnItemLongClickListener, android.view.View.OnClickListener,
		OnItemClickListener {
	private NavigationView nv;
	private ImageView user_portrait_icon_iv;
	private ImageView user_vip_icon_iv;
	private ImageView user_sex_icon_iv;
	private TextView user_nick_tv;
	private TextView user_address_tv;
	private TextView user_describe_tv;
	private Button msg_op_btn;
	private Button follow_op_btn;
	private TextView weibo_count_tip_tv;
	private View comments_contain_lt;
	private View fans_contain_lt;
	private View friends_contain_lt;
	// private RefreshListView weibo_list_lv;
	private TextView fans_count_tv;
	private TextView friends_count_tv;
	private TextView comments_count_tv;
	private String uname;
	private Integer pid;
	private boolean status_show = true;
	private int follow_flag = 0;// 请求状态
	private Bundle bundle;
	private AsynImageLoader asynImageLoader = new AsynImageLoader();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = null;
		switch (v.getId()) {
		case R.id.lt_fans_contain:
			bundle = new Bundle();
			intent.setClass(this, FollowersActivity.class);
			bundle.putString(Constants.PARAM_UNAME, uname);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.lt_friends_contain:
			intent.setClass(this, FriendsActivity.class);
			bundle = new Bundle();
			bundle.putString(Constants.PARAM_UNAME, uname);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case 0:
			onBackPressed();
			break;
		case 1:
			bundle = new Bundle();
			bundle.putString(Constants.PARAM_UNAME, uname);
			intent.setClass(ProfileHomeActivity.this, MainActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);

		case R.id.btn_follow_op:

			break;

		default:
			break;
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	@Override
	public void logicDispose() {
		bundle = getIntent().getExtras();
		//

		// user_info.action
		DataRequest dr = new DataRequest();
		dr.cacheFlag = true;// 开启缓存
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("auth_id", bundle.getString(UserInfo.AUTHID));// 授权ID
		treeMap.put("screen_name", bundle.getString(UserInfo.USERNAME));
		treeMap = SignatureHelper.sigParams(treeMap);
		dr.requestParams = new RequestParams(treeMap);
		dr.url = Constants.APP_DOMAIN + Constants.USER_INFO_API;
		buildData(dr, new DataCallback<String>() {
			@Override
			public void processData(String paramObject, boolean paramBoolean)
					throws Exception {
				log.i(paramBoolean);

				User user = new User(new JSONObject(paramObject));
				asynImageLoader.showImageAsyn(user_portrait_icon_iv,
						user.getProfileImageUrl(), R.drawable.portrait);
				if (user.isVerified()) {
					user_vip_icon_iv.setImageResource(R.drawable.portrait_v);
				} else {
					user_vip_icon_iv.setVisibility(View.GONE);// 隐藏
				}
				if ("m".equals(user.getGender())) {
					user_sex_icon_iv
							.setImageResource(R.drawable.user_info_male);
				} else if ("f".equals(user.getGender())) {
					user_sex_icon_iv
							.setImageResource(R.drawable.user_info_female);
				}
				user_nick_tv.setText(user.getScreenName());
				user_address_tv.setText(user.getLocation());

				user_describe_tv.setText(user.getDescription());
				//
				if (user.isallowAllActMsg()) {
					msg_op_btn.setVisibility(View.VISIBLE);
				} else {
					msg_op_btn.setVisibility(View.GONE);
				}
				if (user.isfollowMe()) {
					follow_op_btn.setText("取消关注");
					follow_flag = 1;
				}
				weibo_count_tip_tv.setText(weibo_count_tip_tv.getText()
						.toString()
						.replace("..", String.valueOf(user.getStatusesCount())));
				fans_count_tv.setText(String.valueOf(user.getFollowersCount()));
				friends_count_tv.setText(String.valueOf(user.getFriendsCount()));

			}

		});
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.profile_home_activity);
	}

	@Override
	public void initView() {
		user_portrait_icon_iv = (ImageView) findViewById(R.id.iv_user_portrait_icon);
		user_vip_icon_iv = (ImageView) findViewById(R.id.iv_user_vip_icon);
		user_sex_icon_iv = (ImageView) findViewById(R.id.iv_user_sex_icon);
		user_nick_tv = (TextView) findViewById(R.id.tv_user_nick);
		user_address_tv = (TextView) findViewById(R.id.tv_user_address);
		user_describe_tv = (TextView) findViewById(R.id.tv_user_describe);
		msg_op_btn = (Button) findViewById(R.id.btn_msg_op);
		follow_op_btn = (Button) findViewById(R.id.btn_follow_op);
		weibo_count_tip_tv = (TextView) findViewById(R.id.tv_weibo_count_tip);
		comments_contain_lt = findViewById(R.id.lt_comments_contain);
		fans_contain_lt = findViewById(R.id.lt_fans_contain);
		friends_contain_lt = findViewById(R.id.lt_friends_contain);
		// weibo_list_lv=(RefreshListView)findViewById(R.id.lv_weibo_list);
		fans_count_tv = (TextView) findViewById(R.id.tv_fans_count);
		friends_count_tv = (TextView) findViewById(R.id.tv_friends_count);
		// comments_count_tv=(TextView)findViewById(R.id.tv_comment_count);
		nv = (NavigationView) findViewById(R.id.navigationView);
		// pid=SPUtil.getPID(this);

	}

	@Override
	public void listener() {
		fans_contain_lt.setOnClickListener(this);
		friends_contain_lt.setOnClickListener(this);
		nv.getBtn_left().setOnClickListener(this);
		nv.getBtn_right().setOnClickListener(this);
		follow_op_btn.setOnClickListener(this);

	}

}
