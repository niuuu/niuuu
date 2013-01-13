package com.app.onenet.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.app.onenet.R;

public class CustomProgressBarDialog extends Dialog {
	private static int default_width = 160; // 默认宽度
	private static int default_height = 120;// 默认高度
	private static int default_layout=R.layout.custom_progressbar;
	private static int default_style=R.style.full_Dialog;
	public CustomProgressBarDialog(Context context, int layout, int style) {
		this(context, default_width, default_height, layout, style);
	}
	public CustomProgressBarDialog(Context context, int style) {
		this(context, default_width, default_height, default_layout, style);
	}
	public CustomProgressBarDialog(Context context) {
		this(context, default_width, default_height, default_layout, default_style);
	}
	public CustomProgressBarDialog(Context context, int width, int height,
			int layout, int style) {
		super(context, style);
		setContentView(layout);
		// set window params
		WindowManager.LayoutParams params = getWindow().getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		params.width = (int) (width * density);
		params.height = (int) (height * density);
		params.gravity = Gravity.CENTER;
		params.dimAmount = 0; // 去背景遮盖
		params.alpha = 1.0f;
		getWindow().setAttributes(params);

	}
	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

}
