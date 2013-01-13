package com.app.onenet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.widget.NavigationView;

public class NewCommentActivity extends DefaultActivity implements OnClickListener,TextWatcher,OnFocusChangeListener,OnItemClickListener{
	private TextView title_name_tv;
	private Button submit_btn;
	private Button back_btn;
	private ImageView topic_iv;
	private ImageView at_iv;
	private ImageView face_iv;
	private TextView words_limit_tv;
	private View word_delete;
	private View face_contain_lt;
	private EditText comment_content_etv;
	private GridView face_list_gv;
	private Bundle bundle;
	private NavigationView nv;
	@Override
	public void logicDispose() {
		Intent intent=getIntent();
		bundle=intent.getExtras();
		log.d(bundle.getString(Constants.WEIBO_ID));
		
	}

	@Override
	public void setupViewLayout() {
		setContentView(R.layout.new_comment_activity);
		
	}

	@Override
	public void initView() {
		title_name_tv=(TextView)findViewById(R.id.tv_title_name);
		topic_iv = (ImageView) this.findViewById(R.id.iv_topic);
		at_iv = (ImageView) this.findViewById(R.id.iv_at);
		face_iv = (ImageView) findViewById(R.id.iv_face);
		words_limit_tv=(TextView)findViewById(R.id.tv_words_limit);
		word_delete=findViewById(R.id.rlt_word_delete);
		face_contain_lt=findViewById(R.id.lt_face_contain);
		comment_content_etv=(EditText)findViewById(R.id.et_comment_content);
		face_list_gv=(GridView)findViewById(R.id.gv_face_list);
		nv=(NavigationView)findViewById(R.id.navigationView);
		nv.getBtn_right().setText(getString(R.string.comment));
	}

	@Override
	public void listener() {
		submit_btn.setOnClickListener(this);
		back_btn.setOnClickListener(this);
		topic_iv.setOnClickListener(this);
		at_iv.setOnClickListener(this);
		face_iv.setOnClickListener(this);
		word_delete.setOnClickListener(this);
		face_list_gv.setOnItemClickListener(this);
		comment_content_etv.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.et_comment_content:
			break;
		case 0:
			if(!TextUtils.isEmpty(comment_content_etv.getText().toString()))
				new AlertDialog.Builder(context).setTitle(R.string.app_title_info).setMessage(R.string.app_msg_weibo_is_save).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//开始保存
						onBackPressed();
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onBackPressed();
					}
				}).setCancelable(true).show();
			else 
				onBackPressed();
			
			break;
		case 1:
			if(checkComment()){
				submit_btn.setEnabled(false);
			}
			break;
		case R.id.iv_face:
			
			break;
		case R.id.iv_topic:
			//话题
			new AlertDialog.Builder(this).setItems(getResources().getStringArray(R.array.item_insert_dialog_topic),new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						
						break;
					case 1:
						break;
					default:
						break;
					}
					
				}
			} ).show();
			break;
		case R.id.iv_at:
			//at
			
			break;
		case R.id.rlt_word_delete:
			//删除
			new AlertDialog.Builder(context).setTitle(R.string.app_title_info).setMessage(R.string.app_msg_is_clear).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					comment_content_etv.setText("");
					
				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}).setCancelable(true).show();
			break;
		default:
			break;
		}
		
	}
	private boolean checkComment() {
		return true;
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		 String content=comment_content_etv.getText().toString();
         int len = content.length();
         if (len <= Constants.WEIBO_MAX_LENGTH) {
             len = Constants.WEIBO_MAX_LENGTH - len;
             words_limit_tv.setTextColor(Color.GRAY);
             if (!submit_btn.isEnabled())
            	 submit_btn.setEnabled(true);
         } else {
             len = len - Constants.WEIBO_MAX_LENGTH;
             words_limit_tv.setTextColor(Color.RED);
             if (submit_btn.isEnabled())
            	 submit_btn.setEnabled(false);
         }
         words_limit_tv.setText(String.valueOf(len));
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		log.d(v.getId());
		switch (v.getId()) {
		case R.id.et_comment_content:
			log.d(hasFocus);
			if(hasFocus){
				//判断表情是否打开
				if(face_contain_lt.getVisibility()==View.VISIBLE){
					face_contain_lt.setVisibility(View.GONE);//隐藏
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

}
