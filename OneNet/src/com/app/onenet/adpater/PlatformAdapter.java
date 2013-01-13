package com.app.onenet.adpater;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.ApplicationEx;
import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.model.PlatformInfo;
import com.app.onenet.utils.FileUtils;
import com.app.onenet.utils.PicUtil;

public class PlatformAdapter extends BaseAdapter {
	private Context context;
	private List<PlatformInfo> platformInfos;
	public PlatformAdapter(Context context,List<PlatformInfo> platformInfos){
		this.context=context;
		this.platformInfos=platformInfos;
	}

	@Override
	public int getCount() {
		return platformInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return platformInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getview" +position);
		PlatformInfo platformInfo=platformInfos.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflater=LayoutInflater.from(context);
			convertView=layoutInflater.inflate(R.layout.platform_list_item, null);
			holder.imageView=(ImageView)convertView.findViewById(R.id.iv_platform_icon);
			holder.textView = (TextView) convertView.findViewById(R.id.tv_platform_tip);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//图片获取
		
		Bitmap icon = null;
		String cachePath=ApplicationEx.appSdCardPath+Constants.IMG_CACHE_DIR+FileUtils.getFileName(platformInfo.getIcon());
		if(TextUtils.isEmpty(platformInfo.getIcon())){
			String path=platformInfo.getName()+"_logo.png";
			System.out.println(path);
			InputStream inputStream;
			try {
				inputStream = context.getAssets().open(path);
				if(inputStream==null){
					//获取缓存
					icon=BitmapFactory.decodeFile(cachePath);
					//不存在下载
					if(icon==null){
						icon=PicUtil.getbitmapAndwrite(platformInfo.getIcon()); 
					}
				}
				else{
					icon = BitmapFactory.decodeStream(inputStream);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			//获取缓存
			icon=BitmapFactory.decodeFile(cachePath);
			//不存在下载
			if(icon==null){
				icon=PicUtil.getbitmapAndwrite(platformInfo.getIcon()); 
			}
		}
		holder.imageView.setImageBitmap(PicUtil.getRoundedCornerBitmap(icon, 2));
		holder.textView.setText(String.format(context.getString(R.string.app_tx_platform_tip),platformInfo.getCount()));
		return convertView;
		
	}
	class ViewHolder{
		ImageView imageView;
		TextView textView;
	}
	
  
		
	

}
