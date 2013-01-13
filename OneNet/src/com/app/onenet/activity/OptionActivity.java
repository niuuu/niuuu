package com.app.onenet.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.widget.CornerListView;

public class OptionActivity extends DefaultActivity implements OnItemClickListener {
	private String[] mSettingItems = { "帐号", "", "我的微博", "我的收藏", "草稿箱",
			"",  "@关注提醒", "@全部提醒" };
	private String[] mSettingItemMethods = { "account", "", "weibo",
			"fav", "draft", "",
			"remind", "all_remind" };
	private String[] settingTips ={"帐号","微博","提醒"};
	private HashMap<String, String> mSettingItemMethodMap = new HashMap<String, String>();
	private List<List<Map<String, String>>> listDatas = new ArrayList<List<Map<String, String>>>();
	private LinearLayout cornerContainer; 

	@Override
	public void logicDispose() {
		for (int i = 0; i < mSettingItems.length; i++) {
            mSettingItemMethodMap.put(mSettingItems[i], mSettingItemMethods[i]);
        }
		setListDatas();
        int size = listDatas.size();
        CornerListView cornerListView;
        LayoutParams lp;
        SimpleAdapter adapter;
        for (int i = 0; i < size; i++) {
        	TextView textView=new TextView(this);
    		textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    		textView.setText(settingTips[i]);
    		textView.setTextSize(getResources().getDimension(R.dimen.setting_item_tip_font_size));
    		textView.setPadding(8, 8, 8, 4);
    		cornerContainer.addView(textView);
            cornerListView = new CornerListView(this);
            lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            if (i == 0 && i == (size - 1)) {
                lp.setMargins(8, 8, 8, 8);
            } else if (i == 0) {
                lp.setMargins(8, 8, 8, 4);
            }else if (i == (size - 1)) {
                lp.setMargins(8, 4, 8, 8);
            } else {
                lp.setMargins(8, 4, 8, 4);
            }
            cornerListView.setLayoutParams(lp);
            cornerListView.setCacheColorHint(0);
            cornerListView.setDivider(getResources().getDrawable(R.drawable.app_divider_h_gray));
            cornerListView.setScrollbarFadingEnabled(false);
            cornerContainer.addView(cornerListView);

            adapter = new SimpleAdapter(getApplicationContext(), listDatas.get(i), R.layout.option_list_item , new String[]{"text"}, new int[]{R.id.tv_item_text});
            cornerListView.setAdapter(adapter);
            cornerListView.setOnItemClickListener(this);
            int height = listDatas.get(i).size() * (int)getResources().getDimension(R.dimen.setting_item_height);
            height += 1;
            cornerListView.getLayoutParams().height = height;
        }
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.option_activity);

	}

	@Override
	public void initView() {
		cornerContainer = (LinearLayout) findViewById(R.id.setting);
	}

	@Override
	public void listener() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView textView = (TextView) arg1.findViewById(R.id.tv_item_text);
        String key = textView.getText().toString();
        Class<? extends OptionActivity> clazz = this.getClass();
        try {
            Method method = clazz.getMethod(mSettingItemMethodMap.get(key));
            method.invoke(OptionActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	 public void setListDatas() {
	        listDatas.clear();
	        List<Map<String,String>> listData = new ArrayList<Map<String,String>>();

	        Map<String,String> map;

	        for(int i = 0; i < mSettingItems.length; i++) {
	            if ("".equals(mSettingItems[i])) {
	                listDatas.add(listData);
	                listData = new ArrayList<Map<String,String>>();
	            } else {
	                map = new HashMap<String, String>();
	                map.put("text", mSettingItems[i]);
	                listData.add(map);
	            }
	        }

	        listDatas.add(listData);
	    }
}
