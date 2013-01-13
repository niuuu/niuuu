package com.app.onenet.activity;


/**
 * 显示图片
 * @author niu
 *
 */
public class ShowImageActivity {
//	private ImageView show_image_iv;
//	private View base_title_layout;
//	private ZoomControls zooming_zc;
//	private Button down_btn;
//	private Button back_btn;
//	private TextView title_name_tv;
//	private ImageZoomView zoomView;//自定义
//	private ImageZoomState zoomState;// 图片缩放和移动状态类
//	private SimpleImageZoomListener zoomListener;// 缩放事件监听器
//	private Bitmap bitmap;// 要显示的图片位图
//	
//
//	@Override
//	public void logicDispose() {
////		Bundle bundle = this.getIntent().getExtras(); 
////        Bitmap bitmap= bundle.getParcelable("image"); 
////        show_image_iv.setImageBitmap(bitmap); 
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.log_v);
//        zoomState = new ImageZoomState();
//        zoomListener = new SimpleImageZoomListener();
//        zoomListener.setZoomState(zoomState);
//        zoomView.setImage(bitmap);
//        zoomView.setImageZoomState(zoomState);
//        zoomView.setOnTouchListener(zoomListener);
//       
//        
//        
//	}
//
//	@Override
//	public void setupViewLayout() {
//		setContentView(R.layout.show_image_activity);
//		
//	}
//
//	@Override
//	public void initView() {
//		base_title_layout=findViewById(R.id.base_title_layout);
//		zoomView=(ImageZoomView)findViewById(R.id.zoomView);
////		show_image_iv=(ImageView)findViewById(R.id.iv_show_image);
//		zooming_zc=(ZoomControls)findViewById(R.id.zc_Zooming);
//		back_btn=(Button)findViewById(R.id.btn_title_left);
//		down_btn=(Button) findViewById(R.id.btn_title_right);
//		down_btn.setBackgroundResource(R.drawable.title_right_button);
//		title_name_tv=(TextView) findViewById(R.id.tv_title_name);
//		title_name_tv.setText("浏览图片");
//		down_btn.setText("下载");
//	}
//
//	/* (non-Javadoc)
//	 * @see com.net.ywt.activity.DefaultActivity#listener()
//	 */
//	@Override
//	public void listener() {
//		back_btn.setOnClickListener(this);
//		down_btn.setOnClickListener(this);
//		zooming_zc.setOnZoomInClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				 float z = zoomState.getmZoom() + 0.25f;// 图像大小增加原来的0.25倍
//	              zoomState.setmZoom(z);
//	              zoomState.notifyObservers();
//			}
//		});
//		zooming_zc.setOnZoomOutClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				 float z = zoomState.getmZoom() - 0.25f;// 图像大小减少原来的0.25倍
//	              zoomState.setmZoom(z);
//	              zoomState.notifyObservers();
//				
//			}
//		});
//		zoomView.setOnTouchListener(zoomListener);
//		zoomView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               setFullScreen();
//            }
//        });
//		
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_title_left:
//			onBackPressed();
//			break;
//		case R.id.btn_title_right:
//			
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void setFullScreen() {
//		if (zooming_zc != null) {
//			if (zooming_zc.getVisibility() == View.VISIBLE) {
//				// zoomCtrl.setVisibility(View.GONE);
//				zooming_zc.hide(); // 有过度效果
//				base_title_layout.setVisibility(View.GONE);
//			} else if (zooming_zc.getVisibility() == View.GONE) {
//				// zoomCtrl.setVisibility(View.VISIBLE);
//				zooming_zc.show();// 有过渡效果
//				base_title_layout.setVisibility(View.VISIBLE);
//
//			}
//
//		}
//
//	    }
	

}
