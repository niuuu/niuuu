package com.app.onenet.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.app.onenet.ApplicationEx;
import com.app.onenet.R;
import com.app.onenet.model.DataRequest;
import com.app.onenet.sync.http.AsyncHttpClient;
import com.app.onenet.sync.http.AsyncHttpResponseHandler;
import com.app.onenet.utils.CacheUtils;
import com.app.onenet.utils.NetworkUtils;
import com.app.onenet.utils.Utils;

/**
 * 
 * @author niu
 *
 */
public abstract  class DefaultActivity extends BaseActivity {
	public  Context context;
//	private CustomProgressBarDialog progressDialog;
	private ProgressDialog progressDialog;
	private static int  LIMITS=2;//请求次数
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context=getApplicationContext();
		setupViewLayout();
		initView();
		listener();
		logicDispose();
		
	}
	protected void showProgressDialog() {
		if ((!isFinishing()) && (this.progressDialog == null)) {
//			this.progressDialog = new CustomProgressBarDialog(this.getParent()!=null?this.getParent():this);
			this.progressDialog=new ProgressDialog(this.getParent()!=null?this.getParent():this);
			this.progressDialog.setMessage("请求数据中...");
			this.progressDialog.setCanceledOnTouchOutside(false);
		}
		this.progressDialog.show();
	}
	protected void closeProgressDialog() {
		if (this.progressDialog != null)
			this.progressDialog.dismiss();
	}
	public void buildData(final DataRequest dataRequest,final DataCallback callback){
		//判断缓存是否有
		 String cacheData = CacheUtils.getUrlCache(dataRequest.url+dataRequest.requestParams!=null?"?"+dataRequest.requestParams.toString():"");
         if (cacheData != null) {
        	 log.i("从缓存中获取数据："+dataRequest.url);
        	 try {
        		 Object object=null;
        		 if(null==dataRequest.jsonParse){
        			 object=cacheData;
        		 }
        		 else{
        			 object=dataRequest.jsonParse.parseJSON(cacheData);
        		 }
				callback.processData(object, true);
			} catch (JSONException e) {
				e.printStackTrace();
				log.i("缓存数据解析出错"+dataRequest.url+"开始网络获取数据中。。。。");
				getDataFromServer(dataRequest,callback);
			}
        	 catch (Exception e) {
        		 e.printStackTrace();
			}
         } 
         else{
        	 log.i("请求网络获取数据："+dataRequest.url);
        	 getDataFromServer(dataRequest,callback);
         }
	}
	 public boolean checkJson(String resp) throws JSONException{
			boolean result = true;
			if (resp != null && !"".equals(resp)) {
				JSONObject jsonObject = new JSONObject(resp);
				int state=jsonObject.optInt("state");
				if(state!=0)
					result=true;
				else 
					result=false;
				
			}
			return result;
		 
	 }
	public void getDataFromServer(final DataRequest dataRequest,final DataCallback callback){
		 if(ApplicationEx.networkState!=NetworkUtils.NETWORN_NONE){
    		 AsyncHttpClient client = ApplicationEx.client;
    		 if(client==null){
    			 client=new AsyncHttpClient();
    		 }
    		 System.out.println(dataRequest.url+"?"+dataRequest.requestParams.toString());
    		 //回调接口
    		 AsyncHttpResponseHandler httpResponseHandler=new AsyncHttpResponseHandler(){
    			 @Override
    			 public void onStart() {
    				 if(dataRequest.showDialgFlag)
    					 showProgressDialog();
    			 }
    			 
    			 @Override
    			 public void onFinish() {
    				 closeProgressDialog();
    				 
    				 
    			 }
    			 @Override
    			 public void onSuccess(String content) {
    				 log.d(content);
    				 try {
//					if(dataRequest.json.checkJson(content)){
    					 if(checkJson(content)){
    						 if(dataRequest.cacheFlag){
    							 //写缓存
    							 CacheUtils.setUrlCache(content, dataRequest.url+dataRequest.requestParams!=null?"?"+dataRequest.requestParams.toString():"");
    						 }
    						 Object object=null;
    		        		 if(null==dataRequest.jsonParse){
    		        			 object=content;
    		        		 }
    		        		 else{
    		        			 object=dataRequest.jsonParse.parseJSON(content);
    		        		 }
    						callback.processData(object, true);
    					 }
    					 else{
    						 Utils.showToast(context, context.getResources().getString(R.string.app_server_error), false);
    					 }
    				 } catch (Exception e) {
    					 e.printStackTrace();
    					 Utils.showToast(context, context.getResources().getString(R.string.app_server_error), false);
    				 }
    				 
    			 }
    			 
    			 @Override
    			 public void onFailure(Throwable error) {
    				 while (LIMITS-->0) {
    					 buildData(dataRequest, callback);
    				 }
    				 error.printStackTrace();
    				 Utils.showToast(context, context.getResources().getString(R.string.app_server_error), false);
    			 }
    			 
    		 };
    		 switch (dataRequest.requestMethod) {
    		 case 0:
    			 client.get(context, dataRequest.url, dataRequest.requestParams,httpResponseHandler );
    			 break;
    		 case 1:
    			 client.post(context, dataRequest.url, dataRequest.requestParams,httpResponseHandler );
    			 break;
    			 
    		 default:
    			 client.get(context, dataRequest.url, dataRequest.requestParams,httpResponseHandler );
    			 break;
    		 }
    	 }
    	 else{
    		 Utils.showToast(context, context.getResources().getString(R.string.app_net_failed), false);
    		 return;
    	 }
	}
	public abstract void logicDispose();
	public abstract void setupViewLayout();
	public abstract void initView();
	public abstract void listener();
	public abstract interface DataCallback<T> {
		public abstract void processData(T paramObject, boolean paramBoolean) throws Exception;
	}

}

