package com.app.onenet.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.adpater.MyWeiboAdapter;
import com.app.onenet.constant.Constants;
import com.app.onenet.model.DataRequest;
import com.app.onenet.model.UserInfo;
import com.app.onenet.model.api.Status;
import com.app.onenet.sync.http.RequestParams;
import com.app.onenet.utils.SignatureHelper;
import com.app.onenet.widget.RefreshListView;
import com.app.onenet.widget.RefreshListView.RefreshListener;

/**
 * 我的微博列表
 * @author niu
 *
 */
public class MyWeiboActivity extends DefaultActivity  implements OnClickListener,OnItemClickListener,OnItemLongClickListener,RefreshListener{
	private static final int FIRST_LOAD_FLAG = 1;
	private static final int REFRESH_FLAG = -1;
	private static final int MORE_FLAG = 0;
	private RefreshListView home_weibo_list_lv;

	private Button title_new_btn;
	private Button title_reload_btn;
	private TextView title_name_tv;
	private Bundle bundle;
	private MyWeiboAdapter myWeiboAdapter;
	private int pageIndex=1;
	private List<Status> listData;
	private Map<String , String> paramsMap=new HashMap<String, String>();
	private Integer pid;
	private Integer r_type=FIRST_LOAD_FLAG;
	private boolean status_show=true;
	@Override
	public void logicDispose() {
		bundle=getIntent().getExtras();
		paramsMap.put("page", String.valueOf(pageIndex));
		paramsMap.put("count", "20");
		paramsMap.put("since_id", "0");
		paramsMap.put("max_id", "0");
		getData(paramsMap);
	}
	public void getData(Map<String, String> paramsMap){
		//请求服务
				DataRequest dr=new DataRequest();
				dr.cacheFlag=true;//开启缓存
				dr.showDialgFlag=status_show;
				TreeMap<String, String> treeMap=new TreeMap<String, String>();
				treeMap.put("auth_id", bundle.getString(UserInfo.AUTHID));//授权ID
//				{"page":1,"count":2,"since_id":"0","max_id":"0"})
				
				treeMap.put("param", new JSONObject(paramsMap).toString());//params
				treeMap=SignatureHelper.sigParams(treeMap);
				dr.requestParams=new RequestParams(treeMap);
				dr.url=Constants.APP_DOMAIN+Constants.HOME_TIMELINE_API;
				buildData(dr, new DataCallback<String>() {
					@Override
					public void processData(String paramObject,
							boolean paramBoolean) throws Exception {
						log.d(paramObject);
						JSONObject jsonStatus = new JSONObject(paramObject); //asJSONArray();
						JSONArray statuses = null;
							if(!jsonStatus.isNull("statuses")){				
								statuses = jsonStatus.getJSONArray("statuses");
							}
							if(!jsonStatus.isNull("reposts")){
								statuses = jsonStatus.getJSONArray("reposts");
							}
							int size = statuses.length();
							List<Status> status = new ArrayList<Status>(size);
							for (int i = 0; i < size; i++) {
								status.add(new Status(statuses.getJSONObject(i)));
							}
						listData =status;
						myWeiboAdapter=new MyWeiboAdapter(context, listData);
						home_weibo_list_lv.setAdapter(myWeiboAdapter);
					}
				});
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.myweibo_activity);
	}

	@Override
	public void initView() {
		home_weibo_list_lv = (RefreshListView) findViewById(R.id.iv_home_weibo_list);
		title_new_btn =  (Button) this.findViewById(R.id.btn_title_left);
		title_reload_btn =(Button) this.findViewById(R.id.btn_title_right);
		title_new_btn.setBackgroundResource(R.drawable.title_new_button);
		title_reload_btn.setBackgroundResource(R.drawable.title_reload_button);
		title_name_tv=(TextView)findViewById(R.id.tv_title_name);
		title_name_tv.setText(getString(R.string.app_title_myweibo_activity));//标题
		
	}

	@Override
	public void listener() {
		home_weibo_list_lv.setOnItemLongClickListener(this);
		title_new_btn.setOnClickListener(this);
		title_reload_btn.setOnClickListener(this);
		home_weibo_list_lv.setOnRefreshListener(this);
		home_weibo_list_lv.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Status status=listData.get(arg2-1);
		Intent intent=new Intent(this,DetailWeiboActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString(Constants.WEIBO_ID, status.getId());
		bundle.putString(UserInfo.AUTHID,this.bundle.getString(UserInfo.AUTHID) );
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_left:
			break;
		case R.id.btn_title_right:
			
			break;
		

		default:
			break;
		}
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String[] item_menu_arrays=getResources().getStringArray(R.array.item_menu);
		new AlertDialog.Builder(this)
		.setTitle("操作")
		.setItems(item_menu_arrays,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						
						Intent intent=new Intent();
						switch (which) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							//评论
							intent.setClass(MyWeiboActivity.this,NewCommentActivity.class);
							Bundle b=new Bundle();
//							b.putString(Constants.WEIBO_ID, s.getId());
							intent.putExtras(b);
							startActivity(intent);

							break;
						case 3:
							
							break;
						case 4:
//							String url=Constants.MENTIONS_SCHEMA+"?"+Constants.PARAM_UNAME+"="+s.getUser().getName();
//							log.d(url);
//							Uri uri=Uri.parse(url);
//							intent.setAction(Intent.ACTION_VIEW);
//							intent.setData(uri);
//							startActivity(intent);
							break;
						case 5:
							break;

						default:
							break;
						}

					}
				}).show();
		
		return false;
	}

	@Override
	public List<Status> refreshing() {
		Status s = null;
		if(listData!=null&&listData.size()>0){
			s= listData.get(0);
		}
		paramsMap.put("since_id", s!=null?s.getId():"0");
		paramsMap.put("max_id", "0");
		status_show=false;
		r_type=REFRESH_FLAG;
		getData(paramsMap);
		return listData;
	}

	@Override
	public void refreshed(Object obj) {
		log.i(obj);
		 if (obj != null) {
//	            listData.clear();
	            pageIndex = 1;
	            ((RefreshListView)home_weibo_list_lv).addFootView();
	            myWeiboAdapter=new MyWeiboAdapter(context, (ArrayList<Status>) obj);
	    		home_weibo_list_lv.setAdapter(myWeiboAdapter);
	    		myWeiboAdapter.notifyDataSetChanged();
	            
		 }
	            
	}

	@Override
	public void more() {
		// 获取最后一条的id
		Status s = listData.get(listData.size() - 1);
		paramsMap.put("max_id", s.getId());
		paramsMap.put("since_id","0");
		r_type=MORE_FLAG;
		status_show=false;
		getData(paramsMap);
		myWeiboAdapter=new MyWeiboAdapter(context, (List<Status>) listData);
		home_weibo_list_lv.setAdapter(myWeiboAdapter);
	}


}
