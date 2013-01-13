package com.app.onenet.adpater;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.activity.DetailWeiboActivity;
import com.app.onenet.constant.Constants;
import com.app.onenet.model.api.Status;
import com.app.onenet.utils.AsynImageLoader;
import com.app.onenet.utils.Mention2Link;
import com.app.onenet.utils.TimeUtils;

public class MyWeiboAdapter extends BaseAdapter {
	private Context context;
	private List<Status> status;
	private AsynImageLoader asynImageLoader;  
	public MyWeiboAdapter(Context context,List<Status> status){
		this.context=context;
		this.status=status;
		asynImageLoader = new AsynImageLoader(); 
	}

	@Override
	public int getCount() {
		return status.size();
	}

	@Override
	public Object getItem(int position) {
		return status.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Status s=status.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflater=LayoutInflater.from(context);
			convertView=layoutInflater.inflate(R.layout.home_list_item, null);
			holder.user_portrait_icon_iv=(ImageView)convertView.findViewById(R.id.iv_user_portrait_icon);
			holder.pic_icon_iv=(ImageView)convertView.findViewById(R.id.iv_pic_icon);
			holder.content_thumbnail_pic_iv=(ImageView)convertView.findViewById(R.id.iv_content_thumbnail_pic);
			holder.redirect_thumbnail_pic_iv=(ImageView)convertView.findViewById(R.id.iv_recirect_thumbnail_pic);
			holder.nick_name_tv = (TextView) convertView.findViewById(R.id.tv_nick_name);
			holder.content_date_tv = (TextView) convertView.findViewById(R.id.tv_content_date);
			holder.content_tv=(TextView)convertView.findViewById(R.id.tv_content);
			holder.redirect_content_tv = (TextView) convertView.findViewById(R.id.tv_redirect_content);
			holder.weibo_scource_tv = (TextView) convertView.findViewById(R.id.tv_weibo_scource);
			holder.comment_count_tv = (TextView) convertView.findViewById(R.id.tv_comment_count);
			holder.redirect_count_tv = (TextView) convertView.findViewById(R.id.tv_redirect_count);
			holder.redirect_contain_lt=(View)convertView.findViewById(R.id.lt_redirect_contain);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		asynImageLoader.showImageAsyn(holder.user_portrait_icon_iv, s.getUser().getProfileImageUrl(), R.drawable.portrait); 
		//判断是否含有图片
		if(!TextUtils.isEmpty(s.getThumbnailPic())){
			holder.pic_icon_iv.setVisibility(View.VISIBLE);
		}
		
		holder.nick_name_tv.setText(s.getUser().getScreenName());
		holder.content_date_tv.setText(TimeUtils.converTime(s.getCreatedAt().getTime()));
		holder.content_tv.setText(Html.fromHtml(s.getText()));
		Mention2Link.extractMention2Link(holder.content_tv);
		if(s.getRetweetedStatus()!=null){
			holder.content_thumbnail_pic_iv.setVisibility(View.GONE);
			holder.redirect_contain_lt.setVisibility(View.VISIBLE);//可见
			holder.redirect_content_tv.setText(Html.fromHtml(s.getRetweetedStatus().getText()));
			Mention2Link.extractMention2Link(holder.redirect_content_tv);
			if(!TextUtils.isEmpty(s.getRetweetedStatus().getThumbnailPic())){
				holder.redirect_thumbnail_pic_iv.setVisibility(View.VISIBLE);//可见
				asynImageLoader.showImageAsyn(holder.redirect_thumbnail_pic_iv, s.getRetweetedStatus().getThumbnailPic(), R.drawable.portrait); 
			}
		}
		else{
			holder.redirect_contain_lt.setVisibility(View.GONE);
			//是否含有图片
			if(!TextUtils.isEmpty(s.getThumbnailPic())){
				holder.content_thumbnail_pic_iv.setVisibility(View.VISIBLE);//显示
				asynImageLoader.showImageAsyn(holder.content_thumbnail_pic_iv, s.getThumbnailPic(), R.drawable.portrait); 
			}
			else{
				holder.content_thumbnail_pic_iv.setVisibility(View.GONE);//
			}
		}
		holder.weibo_scource_tv.setText("来自："+s.getSource().getName());
		holder.comment_count_tv.setText(String.valueOf(s.getCommentsCount()).toString());
		holder.redirect_count_tv.setText(String.valueOf(s.getRepostsCount()));
		holder.pic_icon_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,DetailWeiboActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString(Constants.WEIBO_ID, s.getId());
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		
		return convertView;
		
	}
	class ViewHolder{
		ImageView user_portrait_icon_iv;
		ImageView pic_icon_iv;
		ImageView content_thumbnail_pic_iv;
		ImageView redirect_thumbnail_pic_iv;
		TextView nick_name_tv;
		TextView content_date_tv;
		TextView content_tv;
		View redirect_contain_lt;
		TextView redirect_content_tv;
		TextView weibo_scource_tv;
		TextView comment_count_tv;
		TextView redirect_count_tv;
	}
	public void refreshData(LinkedList<Status> status2) {
		this.status=status2;
		notifyDataSetChanged();
		
	}

}
