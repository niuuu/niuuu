package com.app.onenet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.app.onenet.ApplicationEx;
import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.db.DBHelper;
import com.app.onenet.model.PlatformInfo;
import com.app.onenet.model.UserInfo;
import com.app.onenet.sync.http.AsyncHttpClient;
import com.app.onenet.sync.http.AsyncHttpResponseHandler;
import com.app.onenet.sync.http.RequestParams;
import com.app.onenet.utils.CacheUtils;
import com.app.onenet.utils.NetworkUtils;
import com.app.onenet.utils.SPUtils;
import com.app.onenet.utils.SignatureHelper;
import com.app.onenet.utils.Utils;
/**
 * 一网通的后台服务 提供同步更新以及数据检查 版本更新
 * @author niu
 *
 */
public class OneNetService extends Service {
	private static final long SYNCUSER_PERIOD =5*60*1000;/*同步绑定用户资料*/
	private static final long SYNCPLATFORM_PERIOD=5*60*1000;/*同步平台列表*/
	private Timer timer; 
	private int newVerCode;
	private String newVerName;
	private String lastestUpdateInfo;
	private String downloadUrl;
	private int isForced;
	private DBHelper dbHelper;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		timer=new Timer();
		dbHelper=DBHelper.getInstance(getApplicationContext());
	}

	@Override
	public void onDestroy() {
		timer.cancel();//取消
	}

	@Override
	public void onStart(Intent intent, int startId) {
		if (intent != null) {
			int op = intent.getIntExtra("op", -1);
			System.out.println(op);
			switch (op) {
			case 0:
				//获取版本检查APP版本
				getAppVersion();
				break;
			case 1:
				//同步平台列表
				syncPlatformData();
//				timer.schedule(new TimerTask() {
//					@Override
//					public void run() {
//						//同步
//						syncPlatformData();
//					}
//				}, 0, SYNCUSER_PERIOD);
				break;
			case 2:
				//同步用户列表
				syncUserData();
//				timer.schedule(new TimerTask() {
//					@Override
//					public void run() {
//						//同步
//						syncUserData();
//					}
//				}, 0, SYNCPLATFORM_PERIOD);
				break;
			case -1:
				getAppVersion();
				syncPlatformData();
				 //如果不是第一次使用APP
				if(!SPUtils.getBooleanPreference(getApplicationContext(), Constants.SP_FILE_NAME, Constants.SP_ISFIRST, true))
					syncUserData();
				break;
			default:
				
				break;
			}
		}
	}
	/**
	 * 获取应用版本信息
	 */
	public void getAppVersion(){
		//缓存获取
		final String url=Constants.APP_DOMAIN+Constants.VERSIONS_API;
		String content=CacheUtils.getUrlCache(url);
		if(content!=null){
			parseAppVersion(content);
		}
		else{
			System.out.println(url);
			if(ApplicationEx.networkState!=NetworkUtils.NETWORN_NONE){
				AsyncHttpClient client = ApplicationEx.client;
				TreeMap<String, String> params=new TreeMap<String, String>();
				params.put("app_id", Constants.APP_KEY);
				params=SignatureHelper.sigParams(params);
				RequestParams requestParams=new RequestParams(params);
				client.get(this, url, requestParams, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(String content) {
						System.out.println(content);
						//下载到缓存文件里面
						CacheUtils.setUrlCache(content, url);
						parseAppVersion(content);
					}
					
				});
			}
		}
	}
	
	/**
	 * 解析应用版本
	 * @param content
	 */
	public void parseAppVersion(String content){
		//获取到
		try {
			JSONObject jo=new JSONObject(content);
			if(jo.getInt("state")==1){
				JSONObject dataJo=jo.getJSONObject("data");
				if(dataJo!=null){
					newVerCode=  Integer.parseInt(dataJo.getString("ver_code"));//版本号
					newVerName=dataJo.getString("ver_name");//版本名称
					lastestUpdateInfo=dataJo.getString("ver_update");//版本升级内容描述
					isForced=Integer.parseInt(dataJo.getString("is_forced"));//是否强制升级
					String apkName=dataJo.getString("apk_name");//
					downloadUrl=Constants.APP_DOMAIN+apkName;
					checkAppVersion();	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 同步平台列表
	 */
	public void syncPlatformData(){
		if(ApplicationEx.networkState!=NetworkUtils.NETWORN_NONE){
			AsyncHttpClient client = ApplicationEx.client;
			TreeMap<String, String> params=new TreeMap<String, String>();
			params.put("app_id", Constants.APP_KEY);
			params=SignatureHelper.sigParams(params);
			RequestParams requestParams=new RequestParams(params);
			System.out.println(Constants.APP_DOMAIN+Constants.PLATFORM_LIST_API+"?"+requestParams.toString());
			client.get(this, Constants.APP_DOMAIN+Constants.PLATFORM_LIST_API, requestParams, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(String content) {
					System.out.println(content);
					try {
						JSONObject jo=new JSONObject(content);
						if(jo.getInt("state")==1){
							JSONArray ja=jo.getJSONArray("data");
							if(ja!=null){
								List<PlatformInfo> serverPInfos=new ArrayList<PlatformInfo>();
								for(int i=0;i<ja.length();i++){
									JSONObject dataJo=ja.getJSONObject(i);
									PlatformInfo platformInfo=new PlatformInfo();
									platformInfo.setPid(dataJo.getInt("id"));
									platformInfo.setName(dataJo.getString("p_name"));
									platformInfo.setSname(dataJo.getString("s_name"));
									platformInfo.setIcon(dataJo.getString("img_url"));
									platformInfo.setDescribe(dataJo.getString("des"));
									platformInfo.setCount(dataJo.getInt("count"));
									serverPInfos.add(platformInfo);
								}
								//同步本地数据库
								dbHelper.syncPlatformData(serverPInfos);

							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
						Utils.showToast(getApplicationContext(), "同步数据过程中出现错误", false);
					}
				}
				
			});
		}
		
	}
	/**
	 * 同步用户资料数据
	 */
	public void syncUserData(){
		if(ApplicationEx.networkState!=NetworkUtils.NETWORN_NONE){
			String team=SPUtils.getStringPreference(this, Constants.SP_FILE_NAME, Constants.SP_GID, "");
			if(!TextUtils.isEmpty(team)){
				AsyncHttpClient client = ApplicationEx.client;
				TreeMap<String, String> params=new TreeMap<String, String>();
				params.put("team", team);
				params.put("app_id", Constants.APP_KEY);
				params=SignatureHelper.sigParams(params);
				RequestParams requestParams=new RequestParams(params);
				client.get(this, Constants.APP_DOMAIN+Constants.OAUTH_LIST_API, requestParams, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(String content) {
						Log.d("OneNet", content);
						//获取到解析json
						JSONObject jo;
						try {
							jo = new JSONObject(content);
							if(jo.getInt("state")==1){
								JSONArray ja=jo.getJSONArray("data");
								if(ja!=null){
									List<UserInfo> serverUserInfos=new ArrayList<UserInfo>();
									for(int i=0;i<ja.length();i++){
										JSONObject  userJo=ja.getJSONObject(i);
										//判断是否存在
										UserInfo userInfo=new UserInfo();
										userInfo.setAuthId(userJo.getString("auth_id"));
//										userInfo.setNickName(userJo.getString("username"));
//										userInfo.setUserId(userJo.getString("uid"));// 用户ID
										userInfo.setUserName(userJo.getString("username")); // 用户名
										userInfo.setUserIcon(userJo.getString("head_url"));//头像
										serverUserInfos.add(userInfo);
									}
									//同步到数据库
									dbHelper.syncUserInfoData(serverUserInfos);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
					
				});
			}
		}
	}
	/**
	 * 检查更新应用版本弹出层提示
	 */
	public  void checkAppVersion (){
		//判断本地应用版本是否比服务器旧
		if(ApplicationEx.mVersionCode<newVerCode){
			String message=String.format(getString(R.string.app_msg_upgrade), ApplicationEx.mVersionName,ApplicationEx.mVersionCode,newVerName,newVerCode,lastestUpdateInfo);
			System.out.println(message);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);  
			builder.setTitle(getString(R.string.app_title_upgrade)).setMessage(message)  
			.setPositiveButton(getString(R.string.upgrade), new DialogInterface.OnClickListener() {  
				@Override 
				public void onClick(DialogInterface dialog, int which) {  
					Intent intent=new Intent(OneNetService.this,AppUpgradeService.class);
					intent.putExtra("downloadUrl", downloadUrl);
					startService(intent);
				}  
			}).setNegativeButton(getString(R.string.jump), new OnClickListener() {  
				@Override 
				public void onClick(DialogInterface dialog, int which) { 
					dialog.cancel();
				}  
			});  
			AlertDialog alertDialog = builder.create(); 
			alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG); //系统中关机对话框就是这个属性 
			alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);  //窗口可以获得焦点，响应操作 
			//alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);  //窗口不可以获得焦点，点击时响应窗口后面的界面点击事件 
			alertDialog.setCancelable(true);
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.show(); 
		}
	}


}
