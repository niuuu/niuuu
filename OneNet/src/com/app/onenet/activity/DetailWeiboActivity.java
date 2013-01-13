package com.app.onenet.activity;

import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.model.DataRequest;
import com.app.onenet.model.UserInfo;
import com.app.onenet.model.api.Status;
import com.app.onenet.sync.http.RequestParams;
import com.app.onenet.utils.AsynImageLoader;
import com.app.onenet.utils.Mention2Link;
import com.app.onenet.utils.SignatureHelper;
import com.app.onenet.utils.TimeUtils;
import com.app.onenet.utils.Utils;
import com.app.onenet.widget.NavigationView;

/**
 * 详细微博最终页
 * 
 * @author niu
 * 
 */
public class DetailWeiboActivity extends DefaultActivity  implements OnClickListener,OnLongClickListener{
	private NavigationView nv;
	private View top_contain;
	private ImageView user_portrait_icon_iv;// 头像
	private TextView nick_name_tv;// 昵称
	private TextView user_address_tv;// 地址
	private TextView content_tv;// 微博内容
	private ImageView content_thumbnail_pic_iv;// 微博内容图片
	private View redirect_contain;// 是否含有转发容器
	private TextView redirect_content_tv;// 转发内容
	private ImageView redirect_thumbnail_pic_iv;// 转发含有图片
	private TextView redirect_weibo_scource_tv;// 转发微博来源
	private TextView redirect_c_count_tv;// 原文的评论数
	private TextView redirect_f_count_tv;// 原文的转发数
	private TextView content_date_tv;// 发表事件
	private TextView weibo_scource_tv;
	private Button comment_btn;
	private Button redirect_btn;
	private Bundle bundle;
	private Integer pid;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_contain:
			//获取uname
//			String url=Constants.MENTIONS_SCHEMA+"?"+Constants.PARAM_UNAME+"="+s.getUser().getName();
//			log.d(url);
//			Uri uri=Uri.parse(url);
//			Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//			startActivity(intent);
			break;
		case 1:
			break;
		case 0:
			onBackPressed();
			break;
		default:
			break;
		}
	}
	@Override
	public void logicDispose() {
		bundle=getIntent().getExtras();
//		bundle.putString(Constants.WEIBO_ID, status.getId());
		DataRequest dr=new DataRequest();
		dr.cacheFlag=false;//开启缓存
		TreeMap<String, String> treeMap=new TreeMap<String, String>();
		treeMap.put("auth_id", bundle.getString(UserInfo.AUTHID));//授权ID
		treeMap.put("id",bundle.getString(Constants.WEIBO_ID) );
		treeMap=SignatureHelper.sigParams(treeMap);
		dr.requestParams=new RequestParams(treeMap);
		dr.url=Constants.APP_DOMAIN+Constants.SHOW_STATUS_API;
		buildData(dr, new DataCallback<String>() {
			@Override
			public void processData(String paramObject, boolean paramBoolean)
					throws Exception {
				Status status=new Status(paramObject);
				setupView(status);
				
			}
			
		});
		
	}
	public void setupView(Status s){
		if (s != null) {
			log.d(s.toString());
			AsynImageLoader asynImageLoader = new AsynImageLoader();
			asynImageLoader.showImageAsyn(user_portrait_icon_iv, s
					.getUser().getProfileImageUrl(),
					R.drawable.portrait);// 头像
			nick_name_tv.setText(s.getUser().getScreenName());// 昵称
			// user_address_tv=(TextView)
			// this.findViewById(R.id.tv_user_address);//地址
			Mention2Link.extractMention2Link(content_tv);
			content_tv.setText(Html.fromHtml(s.getText()));// 微博内容
			log.d(s.getThumbnailPic());
			if (!TextUtils.isEmpty(s.getThumbnailPic())) {
				log.d("not null"+s.getThumbnailPic());
				content_thumbnail_pic_iv.setVisibility(View.VISIBLE);
				asynImageLoader.showImageAsyn(content_thumbnail_pic_iv,s.getThumbnailPic(), R.drawable.portrait);
			}
			else{
				content_thumbnail_pic_iv.setVisibility(View.GONE);
			}
			if (s.getRetweetedStatus() != null) {
				log.d(s.getRetweetedStatus());
				redirect_contain.setVisibility(View.VISIBLE);// 是否含有转发容器
				Mention2Link.extractMention2Link(redirect_content_tv);
				redirect_content_tv.setText(Html.fromHtml(s.getRetweetedStatus()
						.getText()));// 转发内容
				content_thumbnail_pic_iv.setVisibility(View.GONE);
				if (!TextUtils.isEmpty(s.getRetweetedStatus()
						.getThumbnailPic())) {
					log.d(s.getRetweetedStatus().getThumbnailPic());
					redirect_thumbnail_pic_iv.setVisibility(View.VISIBLE);// 可见
					asynImageLoader.showImageAsyn(redirect_thumbnail_pic_iv, s.getRetweetedStatus().getThumbnailPic(),
							R.drawable.portrait);
				}
				redirect_weibo_scource_tv.setText(s
						.getRetweetedStatus().getSource().getName());// 转发微博来源
				redirect_c_count_tv.setText(String.valueOf(s.getRetweetedStatus().getCommentsCount()));// 原文的评论数
				redirect_f_count_tv.setText(String.valueOf(s.getRetweetedStatus().getRepostsCount()));// 原文的转发数
			}
			else{
				redirect_contain.setVisibility(View.GONE);// 是否含有转发容器
			}
			content_date_tv.setText(TimeUtils.getStandardTime(s
					.getCreatedAt().getTime()));// 发表时间
			weibo_scource_tv.setText(s.getSource().getName());
			comment_btn.setText("评论:(" + s.getCommentsCount() + ")");
			redirect_btn.setText("转发:(" + s.getRepostsCount() + ")");
		}
	}
	@Override
	public void setupViewLayout() {
		setContentView(R.layout.detail_weibo_activity);
	}
	@Override
	public void initView() {
		top_contain=findViewById(R.id.rl_top_contain);
		user_portrait_icon_iv = (ImageView) this
				.findViewById(R.id.iv_user_portrait_icon);// 头像
		nick_name_tv = (TextView) this.findViewById(R.id.tv_nick_name);// 昵称
		user_address_tv = (TextView) this.findViewById(R.id.tv_user_address);// 地址
		content_tv = (TextView) this.findViewById(R.id.tv_content);// 微博内容
		content_thumbnail_pic_iv = (ImageView) this
				.findViewById(R.id.iv_content_thumbnail_pic);// 微博内容图片
		redirect_contain = this.findViewById(R.id.lt_redirect_contain);// 是否含有转发容器
		redirect_content_tv = (TextView) this
				.findViewById(R.id.tv_redirect_content);// 转发内容
		redirect_thumbnail_pic_iv = (ImageView) this
				.findViewById(R.id.iv_redirect_thumbnail_pic);// 转发含有图片
		redirect_weibo_scource_tv = (TextView) this
				.findViewById(R.id.tv_redirect_weibo_scource);// 转发微博来源
		redirect_c_count_tv = (TextView) this
				.findViewById(R.id.tv_redirect_c_count);// 原文的评论数
		redirect_f_count_tv = (TextView) this
				.findViewById(R.id.tv_redirect_f_count);// 原文的转发数
		content_date_tv = (TextView) this.findViewById(R.id.tv_content_date);// 发表时间
		weibo_scource_tv = (TextView) this.findViewById(R.id.tv_weibo_scource);
		comment_btn = (Button) this.findViewById(R.id.btn_comment);
		redirect_btn = (Button) this.findViewById(R.id.btn_redirect);
//		reload_btn.setBackgroundResource(R.drawable.title_reload_normal);
		nv=(NavigationView)findViewById(R.id.navigationView);
	}
	@Override
	public void listener() {
		top_contain.setOnClickListener(this);
		nv.getBtn_left().setOnClickListener(this);
		nv.getBtn_right().setOnClickListener(this);
		redirect_content_tv.setOnLongClickListener(this);
		
	}
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.tv_redirect_content:
			new AlertDialog.Builder(DetailWeiboActivity.this).setTitle("提示").setItems(new String[]{"复制微博"}, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//获取内容
					String redirect_content_str=redirect_content_tv.getText().toString();
					// 得到剪贴板管理器
					 ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
					 cmb.setText(redirect_content_str);
					 Utils.showToast(DetailWeiboActivity.this, "复制到剪贴板成功", false);
					
				}
			}).show();
			break;

		default:
			break;
		}
		return false;
	}
	
	

}
