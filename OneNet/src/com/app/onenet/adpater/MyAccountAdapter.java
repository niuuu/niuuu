package com.app.onenet.adpater;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.model.UserInfo;
import com.app.onenet.utils.AsynImageLoader;

public class MyAccountAdapter extends BaseAdapter {
	private Context context;
	private List<UserInfo> userInfos;
	private AsynImageLoader asynImageLoader;  
	public MyAccountAdapter(Context context, List<UserInfo> userInfos) {
		this.context = context;
		this.userInfos = userInfos;
		asynImageLoader=new AsynImageLoader();
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
			convertView = layoutInflater
					.inflate(R.layout.my_account_item, null);
			holder.account_icon_iv = (ImageView) convertView
					.findViewById(R.id.iv_account_icon);
			holder.platform_icon_iv = (ImageView) convertView
					.findViewById(R.id.iv_platform_icon);
			holder.account_name_tv = (TextView) convertView
					.findViewById(R.id.tv_account_name);
			holder.account_weibo_count_tv = (TextView) convertView
					.findViewById(R.id.tv_account_weibo_count);
			holder.account_fans_count_tv = (TextView) convertView
					.findViewById(R.id.tv_account_fans_count);
			holder.account_follow_count_tv = (TextView) convertView
					.findViewById(R.id.tv_account_follow_count);
			holder.account_fav_count_tv = (TextView) convertView
					.findViewById(R.id.tv_account_fav_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// asset获取
		InputStream inputStream = getClass().getResourceAsStream(
				"/assets/" + (userInfo.getPid()==1?"sina":"tencent") + "_logo.png");
		Drawable icon = Drawable.createFromStream(inputStream, "image");
		holder.platform_icon_iv.setImageDrawable(icon);
		holder.account_name_tv.setText(userInfo.getUserName());
		holder.account_weibo_count_tv.setText("微博数：");
		holder.account_fans_count_tv.setText("粉丝数：");
		holder.account_follow_count_tv.setText("关注数：");
		//获取缓存
		asynImageLoader.showImageAsyn(holder.account_icon_iv, userInfo.getUserIcon(), R.drawable.portrait);
		holder.account_icon_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//设置默认
//				SPUtil.clearAll(context);
//				SPUtil.putAllUser(context, account);
//				Intent intent = new Intent(context, MainActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putString(Constants.PARAM_UNAME, account.getUserName());
//				intent.putExtras(bundle);
//				context.startActivity(intent);

			}
		});
		return convertView;

	}

	class ViewHolder {
		ImageView account_icon_iv;
		ImageView platform_icon_iv;
		TextView account_name_tv;
		TextView account_weibo_count_tv;
		TextView account_fans_count_tv;
		TextView account_follow_count_tv;
		TextView account_fav_count_tv;
	}

}
