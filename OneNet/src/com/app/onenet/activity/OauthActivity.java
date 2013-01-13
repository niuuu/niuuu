package com.app.onenet.activity;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.TreeMap;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.utils.SPUtils;
import com.app.onenet.utils.SignatureHelper;
import com.app.onenet.utils.UrlHelper;
import com.app.onenet.widget.NavigationView;

public class OauthActivity  extends DefaultActivity implements OnClickListener{
	protected static OauthActivity oauthActivity;
	private TextView titleTV;
	private WebView oauthWV;
	private View progressBar;
	private Bundle bundle;
	private NavigationView nv;
	@Override
	public void logicDispose() {
		Intent intent = getIntent();
		bundle = intent.getExtras();
		titleTV.setText(bundle.getString(Constants.OAUTH_TITLE));//设置标题
		TreeMap<String, String> params=new TreeMap<String, String>();
		boolean isFirst=SPUtils.getBooleanPreference(context, Constants.SP_FILE_NAME, Constants.SP_ISFIRST, true);
		log.i(isFirst);
		if(!isFirst){
			String gid=SPUtils.getStringPreference(context, Constants.SP_FILE_NAME, Constants.SP_GID, "");
			params.put("team", gid);//分组
		}
		params.put("platform_id", String.valueOf(bundle.getInt(Constants.PID)));//平台ID
		params.put("display", "mobile");
		params.put("app_id", Constants.APP_KEY);
		params=SignatureHelper.sigParams(params);
		String url=UrlHelper.addParam(Constants.APP_DOMAIN+Constants.OAUTH_API, params);
		log.i(url);
		oauthWV.loadUrl(url);//设置请求内容
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.oauth_webview_activity);
	}

	@Override
	public void initView() {
		nv=(NavigationView)findViewById(R.id.navigationView);
		titleTV=nv.getTv_title();
		oauthWV = (WebView) this.findViewById(R.id.oauth_webview);
		progressBar = findViewById(R.id.show_progress_bar);
		oauthWV.getSettings().setJavaScriptEnabled(true);
		oauthWV.clearCache(true);
		oauthWV.clearHistory();
		oauthWV.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				showProgress();
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showProgress();
				log.i(url);
				//是否是服务器地址
				if(url.startsWith(Constants.APP_DOMAIN)){
					Bundle extras=parseUrl(url);//解析是否正确结果
					String state = extras.getString("state");  
					String error_code = extras.getString("error_code");
					if (!TextUtils.isEmpty(state)&&state.equals("1") && error_code == null) {
						Intent intent = new Intent(OauthActivity.this,CallbackActivity.class);
						extras.putAll(bundle);
						intent.putExtras(extras);
						startActivity(intent);
						finish();
						view.stopLoading();
						return;
					}
				}
				super.onPageStarted(view, url, favicon);

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				hideProgress();
				super.onPageFinished(view, url);
			}
		});
		

	}

	@Override
	public void listener() {
		nv.getBtn_left().setOnClickListener(this);
		nv.getBtn_right().setOnClickListener(this);
		
	}
	
	private void showProgress() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressBar.setVisibility(View.VISIBLE);
			}
		});

	}

	private void hideProgress() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressBar.setVisibility(View.INVISIBLE);
			}
		});

	}
    private  Bundle parseUrl(String url) {
        if(url==null)
        	return new Bundle();
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }
    private Bundle decodeUrl(String s) {
	        Bundle params = new Bundle();
	        if (s != null) {
	            String array[] = s.split("&");
	            for (String parameter : array) {
	                String v[] = parameter.split("=");
	                if(v!=null&&v.length==2){
	                	params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
	                }
	            }
	        }
	        return params;
	    }

	@Override
	public void onClick(View v) {
		log.d(v.getId());
		switch (v.getId()) {
		case 0:
			onBackPressed();
			break;
		case 1:
			ComponentName componetName = new ComponentName(  
	                //这个是另外一个应用程序的包名  
	                "com.sina.weibo",  
	                //这个参数是要启动的Activity  
	                "com.sina.weibo.RegisterSquareActivity");  
	         
	            try {  
	                Intent intent = new Intent();  
	                intent.setComponent(componetName);  
	                startActivity(intent);  
	            } catch (Exception e) {  
	              Toast.makeText(getApplicationContext(), "可以在这里提示用户没有找到应用程序，或者是做其他的操作！", 0).show();  
	                  
	            }  
			break;

		default:
			break;
		}
	}

}
