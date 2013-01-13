package com.app.onenet.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onenet.R;
import com.app.onenet.constant.Constants;
import com.app.onenet.widget.NavigationView;

/**
 * 创建微博
 * @author niu
 *
 */
public class NewWeiboActivity  extends DefaultActivity implements OnClickListener,OnFocusChangeListener,TextWatcher,OnItemClickListener{
	private static final int CHOOSEPHOTO_CODE=1;
	private static final int TAKE_PICTURE_CODE=2;
	private EditText weibo_content_et;
	private ImageView location_iv;
	private ImageView pic_iv;
	private ImageView topic_iv;
	private ImageView at_iv;
	private ImageView platform_iv;
	private TextView words_limit_tv;
	private View word_delete;
	private View face_contain_lt;
	private GridView face_list_gv;
	private ImageView attachment_pic_iv;
	private Uri photoUri;
	private NavigationView nv;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_weibo_content:
			//判断表情是否打开
			break;
		case R.id.iv_location:
			
			break;
		case R.id.iv_pic:
			System.out.println("pic");
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_title_op).setItems(getResources().getStringArray(R.array.item_insert_dialog_pic), new DialogInterface.OnClickListener() {


				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						 ContentValues values = new ContentValues();
						 photoUri = getContentResolver().insert(
			                     MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//			             //准备intent，并 指定 新 照片 的文件名（photoUri）
//			            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						
						Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
			             intent1.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
						intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);  
						startActivityForResult(intent1, TAKE_PICTURE_CODE);
						//手机拍摄
						break;
					case 1:
			          //手机相册
						String image_unspecified = "image/*";
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"   
						intent.setType(image_unspecified); //查看类型 String IMAGE_UNSPECIFIED = "image/*";   
//						intent.putExtra("crop", "true");  
//						intent.putExtra("output", Uri.fromFile(tempFile));  
//						intent.putExtra("outputFormat", "JPEG");  
						startActivityForResult(Intent.createChooser(intent, null), CHOOSEPHOTO_CODE);  
						break;
					default:
						break;
					}
				}
			});
			builder.setCancelable(true);
			builder.show();
			break;
		case R.id.iv_topic:
			//获取光标位置
			 int index = weibo_content_et.getSelectionStart();
			 Editable content = weibo_content_et.getEditableText();
			 if(index<0||index>=content.length())
				 content.append("##");
			 else content.insert(index, "##");

			break;
		case R.id.iv_at:
			

			break;
		case R.id.iv_face:
			
			break;
		case 0:
			onBackPressed();
			break;
		case 1:
			//发布
			startPost();
			
			break;
		case R.id.rlt_word_delete:
			//删除
			if(TextUtils.isEmpty(weibo_content_et.getText().toString()))
			new AlertDialog.Builder(context).setTitle(R.string.app_title_info).setMessage(R.string.app_msg_is_clear).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					weibo_content_et.setText("");
					
				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}).setCancelable(true).show();
			break;
		case R.id.iv_attachment_pic:
			//单击图片
			Intent picIntent=new Intent(this, ShowImageActivity.class);
			Bundle bundle=new Bundle();
			//
			break;
		case R.id.iv_platform:
			
			//查询出所有的平台列表
//			DBHelper dbHelper=DBHelper.getInstance(this);
//			final List<PlatformInfo> platformInfos=dbHelper.getMyBindPlatforms(true);
//			log.d(platformInfos.size());
//			if(platformInfos!=null&&platformInfos.size()>0){
//				final String[] platform_items=new String[platformInfos.size()];
//				final boolean[] checked_items=new boolean[platformInfos.size()];
//				for(int i=0;i<platformInfos.size();i++){
//					platform_items[i]=platformInfos.get(i).getSname();
//					if(pid==platformInfos.get(i).getId()){
//						checked_items[i]=true;
//					}
//					else{
//						checked_items[i]=false;
//					}
//					
//				}
//				new AlertDialog.Builder(this)
//				.setTitle(R.string.app_title_op).setMultiChoiceItems(platform_items, checked_items, new DialogInterface.OnMultiChoiceClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//						checked_items[which]=isChecked;
//					}
//				}).setCancelable(true)
//				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						for(int i=0;i<checked_items.length;i++){
//							if(checked_items[i]){
//								platformInfos.get(i).getId();//设置平台
//								
//							}
//						}
//					}
//				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.cancel();
//					}
//				}).show();
//				
//			}
//			else {
//				Utils.showToast(context, getResources().getString(R.string.tip_no_data), false);
//			}
			break;
		default:
			break;
		}
		
	}
	//发布微博
	private void startPost() {
		//检查输入
		if(checkForm()){
			//判断选择的平台
			
		}
		else{
			
		}
	}
	private boolean checkForm() {
		return true;
	}
	@Override
	public void logicDispose() {
		
	}
	@Override
	public void setupViewLayout() {
		setContentView(R.layout.new_weibo_activity);
		
	}
	@Override
	public void initView() {
		weibo_content_et = (EditText) this.findViewById(R.id.et_weibo_content);
		location_iv = (ImageView) this.findViewById(R.id.iv_location);
		pic_iv = (ImageView) this.findViewById(R.id.iv_pic);
		topic_iv = (ImageView) this.findViewById(R.id.iv_topic);
		at_iv = (ImageView) this.findViewById(R.id.iv_at);
		platform_iv=(ImageView)findViewById(R.id.iv_platform);
		words_limit_tv=(TextView)findViewById(R.id.tv_words_limit);
		word_delete=findViewById(R.id.rlt_word_delete);
		face_contain_lt=findViewById(R.id.lt_face_contain);
		face_list_gv=(GridView) findViewById(R.id.gv_face_list);
		attachment_pic_iv=(ImageView)findViewById(R.id.iv_attachment_pic);
		nv=(NavigationView)findViewById(R.id.navigationView);
//		pid=SPUtil.getPID(context);
		
	}
	@Override
	public void listener() {
		location_iv.setOnClickListener(this);
		pic_iv.setOnClickListener(this);
		topic_iv.setOnClickListener(this);
		at_iv.setOnClickListener(this);
		platform_iv.setOnClickListener(this);
		weibo_content_et.addTextChangedListener(this);
		weibo_content_et.setOnClickListener(this);
		nv.getBtn_left().setOnClickListener(this);
		nv.getBtn_right().setOnClickListener(this);
		word_delete.setOnClickListener(this);
		attachment_pic_iv.setOnClickListener(this);
		face_list_gv.setOnItemClickListener(this);
		
		
		
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
		 String weibo_content=weibo_content_et.getText().toString();
         String mStr;
         long len = calculateWeiboLength(weibo_content);
         if (len <= Constants.WEIBO_MAX_LENGTH) {
             len = Constants.WEIBO_MAX_LENGTH - len;
             words_limit_tv.setTextColor(Color.GRAY);
             if (!nv.getBtn_right().isEnabled())
            	 nv.getBtn_right().setEnabled(true);
         } else {
             len = len - Constants.WEIBO_MAX_LENGTH;
             words_limit_tv.setTextColor(Color.RED);
             if (nv.getBtn_right().isEnabled())
            	 nv.getBtn_right().setEnabled(false);
         }
         words_limit_tv.setText(String.valueOf(len));
	}
	public static long calculateWeiboLength(CharSequence c) { 
	      
        double len = 0; 
        for (int i = 0; i < c.length(); i++) { 
                int temp = (int)c.charAt(i); 
                if (temp > 0 && temp < 127) { 
                        len += 0.5; 
                }else{ 
                        len ++; 
                } 
        } 
     return Math.round(len); 
 } 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if (data != null) {  
                //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意  
                Uri mImageCaptureUri = data.getData();  
                log.d(mImageCaptureUri);
                //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取  
                if (mImageCaptureUri != null) {  
                    Bitmap image;  
                    try {  
                        //这个方法是根据Uri获取Bitmap图片的静态方法  
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);  
                        if (image != null) {  
                            attachment_pic_iv.setImageBitmap(image);  
                        }  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                } else {  
                    Bundle extras = data.getExtras();  
                    if (extras != null) {  
                        //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片  
                        Bitmap image = extras.getParcelable("data");
                        log.d(">>>"+image);
                        if (image != null) {  
                        	log.d(image);
                            attachment_pic_iv.setImageBitmap(image);  
                        }  
                    }  
                }  
  
            }  
		}
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
	private Bitmap getBitMapFromPath(String imageFilePath) {
		Display currentDisplay = getWindowManager().getDefaultDisplay();
		int dw = currentDisplay.getWidth();
		int dh = currentDisplay.getHeight();
		// Load up the image's dimensions not the image itself
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);

		// If both of the ratios are greater than 1,
		// one of the sides of the image is greater than the screen
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// Height ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// Width ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// Decode it for real
		bmpFactoryOptions.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		return bmp;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
	

}
