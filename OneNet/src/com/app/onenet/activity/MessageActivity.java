package com.app.onenet.activity;


import com.app.onenet.R;
import com.app.onenet.model.DataRequest;
import com.app.onenet.parse.BaseParser;
import com.app.onenet.sync.http.RequestParams;
import com.app.onenet.utils.Utils;
import com.app.onenet.widget.RefreshListView.RefreshListener;

public class MessageActivity extends DefaultActivity  implements RefreshListener{


	@Override
	public void logicDispose() {
		DataRequest dataRequest=new DataRequest();
		dataRequest.url="http://172.16.10.100:8080/ywtServer/get_trends.action";
		dataRequest.cacheFlag=true;
		dataRequest.requestMethod=0;
		RequestParams requestParams=new RequestParams();
		requestParams.put("auth_id", "25");
		dataRequest.requestParams=requestParams;
		buildData(dataRequest, new DataCallback<String>() {

			@Override
			public void processData(String paramObject, boolean paramBoolean) {
				Utils.showToast(MessageActivity.this,paramObject , true);
				
			}
		});
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.main_activity);
	}

	@Override
	public void initView() {
		
	}

	@Override
	public void listener() {
		
	}
	@Override
	public Object refreshing() {
		//刷新
		return null;
	}
	
	@Override
	public void refreshed(Object obj) {
		//刷新后调用
//		((RefreshListView)listView).addFootView(); 添加
		
	}
	
	@Override
	public void more() {
		//更多
		//判断是否还有更多 
//		((RefreshListView)listView).removeFootView();
	}
	

}
