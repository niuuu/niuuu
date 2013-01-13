package com.app.onenet.activity;

import com.app.onenet.R;
import com.app.onenet.utils.Utils;
import com.app.onenet.widget.NavigationView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class TestActivity  extends Activity implements OnClickListener{
	private NavigationView nv;
	private Button test_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		nv=(NavigationView)findViewById(R.id.navigationView);
		test_btn=(Button)findViewById(R.id.btn_test);
		test_btn.setOnClickListener(this);
		nv.getBtn_left().setOnClickListener(this);
		nv.getBtn_right().setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case 0:
			Utils.showToast(this, "left", false);
			break;
		case 1:
			Utils.showToast(this, "right", false);
			break;
		case R.id.btn_test:
			Utils.showToast(this, "test", false);
			break;

		default:
			break;
		}
		
	}
	

}
