package com.app.onenet.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

import com.app.onenet.ApplicationEx;
import com.app.onenet.constant.Constants;

public class CacheUtils {

	private static final String TAG = CacheUtils.class.getName();

    public static final int CONFIG_CACHE_MOBILE_TIMEOUT  = 3600000;  //一小时
    public static final int CONFIG_CACHE_WIFI_TIMEOUT    = 300000;   //五分钟

    public static String getUrlCache(String url) {
        if (url == null) {
            return null;
        }
        String result = null;
        File file = new File(ApplicationEx.appSdCardPath+Constants.DATA_CACHE_DIR+"/"+FileUtils.getFileName(url));
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            if (ApplicationEx.networkState != NetworkUtils.NETWORN_NONE && expiredTime < 0) {
                return null;
            }
            if(ApplicationEx.networkState == NetworkUtils.NETWORN_WIFI && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
                return null;
            } else if (ApplicationEx.networkState == NetworkUtils.NETWORN_MOBILE && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
            try {
                result = FileUtils.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static File getCacheFile(String url) {
		File file = new File(ApplicationEx.appSdCardPath+Constants.IMG_CACHE_DIR+"/"+FileUtils.getFileName(url));
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            if (ApplicationEx.networkState != NetworkUtils.NETWORN_NONE && expiredTime < 0) {
                return null;
            }
            if(ApplicationEx.networkState == NetworkUtils.NETWORN_WIFI && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
                return null;
            } else if (ApplicationEx.networkState == NetworkUtils.NETWORN_MOBILE && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
				Log.i(TAG, "exists:" + file.exists() );
				return file;
			}
		return null;
	}
	public static File createCacheFile(String url){
		File cacheFile = null;
		//如果没有则不缓存
        if (ApplicationEx.appSdCardPath == null) {
            return cacheFile;
        }
        File dir = new File(ApplicationEx.appSdCardPath+Constants.IMG_CACHE_DIR); 
        if (!dir.exists() && Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            dir.mkdirs();
        }
        cacheFile = new File(dir, FileUtils.getFileName(url));
        return cacheFile;
	}
    

    public static void setUrlCache(String data, String url) {
    	//如果没有则不缓存
        if (ApplicationEx.appSdCardPath == null) {
            return;
        }
        File dir = new File(ApplicationEx.appSdCardPath+Constants.DATA_CACHE_DIR); 
        if (!dir.exists() && Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            dir.mkdirs();
        }
        File file = new File(ApplicationEx.appSdCardPath+Constants.DATA_CACHE_DIR +"/"+ FileUtils.getFileName(url));
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
