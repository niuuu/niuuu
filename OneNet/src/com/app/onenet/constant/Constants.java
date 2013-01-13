package com.app.onenet.constant;

public class Constants {

//	 public static final String APP_DOMAIN="http://172.16.10.73:8080/";
	public static final String APP_DOMAIN = "http://ywt.blogchina.com/";
	// public static final String APP_DOMAIN="http://172.16.10.60:8080";
	public static final String APP_DIR = "/ywt";
	public static final String DATA_CACHE_DIR = "/cache/data";
	public static final String IMG_CACHE_DIR = "/cache/images";
	// 软件升级的基本常量
	public static final String UPDATE_PATH = "/download";
	/*------------------------*/

	public static final String OAUTH_TITLE = "oauth_title";
	public static final String OAUTH_NAME = "oauth_name";
	public static final String APP_KEY = "447ad97d40c592fbda322ec14794132a";
	public static final String APP_SECRET = "ec78223ce3cc5bbfc549672f6e4ede34";
	public static final String REDIRECT_URL = "redirect_url";
	public static final String WEIBO_ID = "weibo_id";
	public static final String WEIBO_UNAME = "weibo_uname";
	public static final String PID = "pid";

	public static final int WEIBO_MAX_LENGTH = 140;/* 微博文字长度 */

	public static String SP_FILE_NAME = "ywt";
	public static final String SP_GID = "gid";
	public static final String SP_ISFIRST = "is_first";

	public static final String MENTIONS_SCHEMA = "ywt://platform_profile";
	public static final String TRENDS_SCHEMA = "ywt://platform_trends";
	public static final String PARAM_UID = "uid";
	public static final String PARAM_UNAME = "uname";
	public static final int SUCCESS = 1;
	public static final int ERROR = 0;
	public static final int FAILED = -1;
	public static final int NET_FAILED = -2;

	/* 接口相关定义 */
	public static final String OAUTH_LIST_API = "bind_list.action";
	public static final String OAUTH_API = "auth.action";
	public static final String OAUTH_DEL_API = "unbind.action";
	public static final String VERSIONS_API = "ver_code.action";
	public static final String SYNCPLATFORM_API = "platform_list.action";
	public static final String PLATFORM_LIST_API = "plat_list.action";
	public static final String HOME_TIMELINE_API = "home_timeLine.action";
	public static final String USER_INFO_API = "user_info.action";
	public static final String SHOW_STATUS_API = "show_status.action";
	public static final String UPDATE_STATUSES = "update_statuses.action";

}
