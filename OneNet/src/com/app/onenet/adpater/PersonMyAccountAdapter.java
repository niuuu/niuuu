package com.app.onenet.adpater;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.model.UserInfo;
import com.app.onenet.utils.AsynImageLoader;

public class PersonMyAccountAdapter extends BaseAdapter {
	private Context context;
	private List<UserInfo> userInfos;
	private AsynImageLoader asynImageLoader;

	public PersonMyAccountAdapter(Context context, List<UserInfo> userInfos) {
		this.context = context;
		this.userInfos = userInfos;
		asynImageLoader = new AsynImageLoader();
	}

	@Override
	public int getCount() {
		return userInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return userInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView " + position);
		final UserInfo userInfo = userInfos.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.person_home_myaccount_item, null);
			holder.account_icon_iv = (ImageView) convertView.findViewById(R.id.iv_account_icon);
			holder.nick_name_tv = (TextView) convertView.findViewById(R.id.tv_nick_name);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 获取缓存
		asynImageLoader.showImageAsyn(holder.account_icon_iv,
				userInfo.getUserIcon(), R.drawable.portrait);
		holder.nick_name_tv.setText(userInfo.getUserName());

		return convertView;
	}

	class ViewHolder {
		ImageView account_icon_iv;
		TextView nick_name_tv;
	}

}
