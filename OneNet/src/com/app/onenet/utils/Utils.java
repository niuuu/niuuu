package com.app.onenet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.app.onenet.R;

/**
 * 
 * @author niu
 * 
 */
public class Utils {
	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "确定", null);
	}

	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}

	public static void showToast(Context context, String message, boolean flag) {
		Toast.makeText(context, message,
				flag ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
	}

	public static void showExitDialog(final Activity activity) {
		AlertDialog.Builder builder = new Builder(activity);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.app_title_info);
		builder.setMessage(R.string.app_msg_is_exit);
		builder.setPositiveButton(R.string.ok,
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						activity.finish();
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

}
