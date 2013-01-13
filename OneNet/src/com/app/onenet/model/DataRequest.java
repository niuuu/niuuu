package com.app.onenet.model;

import android.content.Context;

import com.app.onenet.parse.BaseParser;
import com.app.onenet.sync.http.RequestParams;

public class DataRequest {
	public Context context;/**/
	public String url;/*请求地址*/
	public RequestParams requestParams;/*参数*/
	public BaseParser<?> jsonParse;/*json解析*/
	public boolean cacheFlag;/*请求数据是否缓存*/
	public boolean showDialgFlag=true;
	public int requestMethod;/*请求方法 0GET 1Post*/

}
