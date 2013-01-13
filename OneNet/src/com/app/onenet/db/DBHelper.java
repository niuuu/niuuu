package com.app.onenet.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.onenet.model.CacheInfo;
import com.app.onenet.model.PlatformInfo;
import com.app.onenet.model.UserInfo;

public class DBHelper {
	/* 数据库名称 */
	public static final String DB_NAME = "ywt.db";
	/* 用户平台授权信息表 */
	public static final String TB_USERINFO = "userinfo";
	/* 平台信息表 */
	public static final String TB_PLATFORM = "platform";
	/*缓存信息表*/
	public static final String TB_CACHEINFO="cacheinfo";

	public static final int DB_VERSION = 1;
	private final SqlLiteHelper helper;
	public SQLiteDatabase db;
	public static DBHelper dbHelper = null;

	/**
	 * SQLite工具类
	 * 
	 * @author niu
	 * 
	 */
	private static class SqlLiteHelper extends SQLiteOpenHelper {
		SqlLiteHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_USERINFO + "("
					+ UserInfo.ID + " integer primary key," + UserInfo.AUTHID
					+ " varchar," + UserInfo.USERID + " varchar,"
					+ UserInfo.USERNAME + " varchar," + UserInfo.NICKNAME
					+ " varchar," + UserInfo.ISDEFAULT + " integer default 0,"
					+ UserInfo.PID + " integer," + UserInfo.USERICON
					+ " varchar" + ")");
//			 db.execSQL("INSERT INTO "+TB_USERINFO+"("+UserInfo.AUTHID
//			 + " ," + UserInfo.USERID + " ,"
//			 + UserInfo.USERNAME + " ," + UserInfo.NICKNAME
//			 + " ," + UserInfo.ISDEFAULT + " ,"
//			 + UserInfo.PID + " ," + UserInfo.USERICON
//			 +")"+" VALUES ('69', '1', '哇哇三牛','哇哇三牛','1', 'vaayYn','');");

			db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_PLATFORM + "("
					+ PlatformInfo.ID + " integer primary key,"
					+ PlatformInfo.PID + " integer," + PlatformInfo.NAME
					+ " varchar," + PlatformInfo.SNAME + " varchar,"
					+ PlatformInfo.DESCRIBE + " varchar," + PlatformInfo.ICON
					+ " varchar," + PlatformInfo.COUNT + " integer default 0"
					+ ")");
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_CACHEINFO + "("
					+ CacheInfo.ID + " integer primary key," + UserInfo.AUTHID
					+ " varchar," + CacheInfo.TYPE + " integer,"
					+ CacheInfo.INFO+ " varchar)");
			// 插入默认的
			db.execSQL("insert into " + TB_PLATFORM + "(" + PlatformInfo.PID
					+ "," + PlatformInfo.NAME + "," + PlatformInfo.SNAME + ","
					+ PlatformInfo.DESCRIBE + "," + PlatformInfo.ICON +","+PlatformInfo.COUNT+ ")"
					+ " values (" + "1" + "," + "'sina'" + "," + "'新浪微博'" + ","
					+ "'新浪微博'" + "," + "''" + ",''" + ")");
			db.execSQL("insert into " + TB_PLATFORM + "(" + PlatformInfo.PID
					+ "," + PlatformInfo.NAME + "," + PlatformInfo.SNAME + ","
					+ PlatformInfo.DESCRIBE + "," + PlatformInfo.ICON + ","+PlatformInfo.COUNT+")"
					+ " values (" + "2" + "," + "'tencent'" + "," + "'腾讯微博'"
					+ "," + "'腾讯微博'" + "," + "''" + ",''" + ")");
			Log.e("Database", "onCreate");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(" DROP TABLE IF EXISTS " + TB_USERINFO);
			onCreate(db);
			Log.e("Database", "onUpgrade");
		}

		/**
		 * 更新列
		 * 
		 * @param db
		 * @param oldColumn
		 * @param newColumn
		 * @param typeColumn
		 */
		@SuppressWarnings("unused")
		public void updateColumn(final SQLiteDatabase db,
				final String oldColumn, final String newColumn,
				final String typeColumn) {
			try {
				db.execSQL("ALTER TABLE " + TB_USERINFO + " CHANGE "
						+ oldColumn + " " + newColumn + " " + typeColumn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public DBHelper(Context context) {
		helper = new SqlLiteHelper(context);
		db = helper.getWritableDatabase();
	}

	public static synchronized DBHelper getInstance(Context context) {
		if (dbHelper == null)
			dbHelper = new DBHelper(context);
		return dbHelper;
	}

	public void close() {
		helper.close();
	}

	/**
	 * 获取所有的用户信息
	 * 
	 * @param issimple
	 * @return
	 */
	public List<UserInfo> getAllUserInfo() {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		Cursor cursor = db.query(TB_USERINFO, null, null, null, null, null,
				UserInfo.ID + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(cursor.getString(cursor.getColumnIndex(UserInfo.ID)));
			userInfo.setAuthId(cursor.getString(cursor
					.getColumnIndex(UserInfo.AUTHID)));
			userInfo.setUserId(cursor.getString(cursor
					.getColumnIndex(UserInfo.USERID)));
			userInfo.setUserName(cursor.getString(cursor
					.getColumnIndex(UserInfo.USERNAME)));
			userInfo.setIsDefault(cursor.getInt(cursor
					.getColumnIndex(UserInfo.ISDEFAULT)));
			userInfo.setNickName(cursor.getString(cursor
					.getColumnIndex(UserInfo.NICKNAME)));
			userInfo.setPid(cursor.getInt(cursor.getColumnIndex(UserInfo.PID)));
			userInfo.setUserIcon(cursor.getString(cursor
					.getColumnIndex(UserInfo.USERICON)));
			userInfos.add(userInfo);
			cursor.moveToNext();

		}
		cursor.close();// 关闭
		return userInfos;
	}

	/**
	 * 根据authId判断用户是否存在
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isExistUser(String authId) {
		boolean result;
		Cursor cursor = db.query(TB_USERINFO, null, UserInfo.USERID + " =?",
				new String[] { authId }, null, null, UserInfo.AUTHID + " DESC");
		result = cursor.moveToFirst();
		cursor.close();// 关闭
		return result;
	}

	public boolean isExistUser(String authId, String pid) {
		boolean result;
		Cursor cursor = db.query(TB_USERINFO, null, UserInfo.AUTHID + " =?"
				+ " and " + UserInfo.PID + "=?", new String[] { authId, pid },
				null, null, UserInfo.ID + " DESC");
		result = cursor.moveToFirst();
		cursor.close();// 关闭
		return result;
	}

	/**
	 * 获取用户
	 * 
	 * @param username
	 * @param issimple
	 * @return
	 */
	public UserInfo getUser(String authId) {
		Cursor cursor = db.query(TB_USERINFO, null, UserInfo.AUTHID + "=?",
				new String[] { authId }, null, null, null);
		UserInfo userInfo = null;
		if (cursor.moveToFirst()) {
			userInfo = new UserInfo();
			userInfo.setId(cursor.getString(cursor.getColumnIndex(UserInfo.ID)));
			userInfo.setAuthId(cursor.getString(cursor
					.getColumnIndex(UserInfo.AUTHID)));
			userInfo.setUserId(cursor.getString(cursor
					.getColumnIndex(UserInfo.USERID)));
			userInfo.setUserName(cursor.getString(cursor
					.getColumnIndex(UserInfo.USERNAME)));
			userInfo.setIsDefault(cursor.getInt(cursor
					.getColumnIndex(UserInfo.ISDEFAULT)));
			userInfo.setNickName(cursor.getString(cursor
					.getColumnIndex(UserInfo.NICKNAME)));
			userInfo.setPid(cursor.getInt(cursor.getColumnIndex(UserInfo.PID)));
		}
		cursor.close();
		Log.e("Database", "getUser-->" + userInfo);
		return userInfo;

	}

	/**
	 * 保存用户信息
	 * 
	 * @param userInfo
	 * @param icon
	 * @return
	 */
	public long saveUserInfo(UserInfo userInfo) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, userInfo.getUserId());
		values.put(UserInfo.AUTHID, userInfo.getAuthId());
		values.put(UserInfo.USERNAME, userInfo.getUserName());
		values.put(UserInfo.ISDEFAULT, userInfo.getIsDefault());
		values.put(UserInfo.NICKNAME, userInfo.getNickName());
		values.put(UserInfo.PID, userInfo.getPid());
		values.put(UserInfo.USERICON, userInfo.getUserIcon());
		return db.insert(TB_USERINFO, UserInfo.ID, values);
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public int delUserInfo(String authId) {
		return db.delete(TB_USERINFO, UserInfo.AUTHID + " =?",
				new String[] { authId });

	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public long updateUserInfo(UserInfo userInfo) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, userInfo.getUserId());
		values.put(UserInfo.AUTHID, userInfo.getAuthId());
		values.put(UserInfo.USERNAME, userInfo.getUserName());
		values.put(UserInfo.ISDEFAULT, userInfo.getIsDefault());
		values.put(UserInfo.NICKNAME, userInfo.getNickName());
		values.put(UserInfo.PID, userInfo.getPid());
		values.put(UserInfo.USERICON, userInfo.getUserIcon());
		return db.update(TB_USERINFO, values, UserInfo.AUTHID + " =?",
				new String[] { userInfo.getAuthId() });
	}

	public static UserInfo getUserByName(String userName,
			List<UserInfo> userList) {
		UserInfo userInfo = null;
		int size = userList.size();
		for (int i = 0; i < size; i++) {
			if (userName.equals(userList.get(i).getUserName())) {
				userInfo = userList.get(i);
				break;
			}
		}
		return userInfo;
	}

	public List<PlatformInfo> getAllPlatformInfo() {
		List<PlatformInfo> platformInfos = new ArrayList<PlatformInfo>();
		Cursor cursor = db.query(TB_PLATFORM, null, null, null, null, null,
				PlatformInfo.ID + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			PlatformInfo platformInfo = new PlatformInfo();
			platformInfo.setId(cursor.getInt(cursor
					.getColumnIndex(PlatformInfo.ID)));
			platformInfo.setPid(cursor.getInt(cursor
					.getColumnIndex(PlatformInfo.PID)));
			platformInfo.setName(cursor.getString(cursor
					.getColumnIndex(PlatformInfo.NAME)));
			platformInfo.setSname(cursor.getString(cursor
					.getColumnIndex(PlatformInfo.SNAME)));
			platformInfo.setDescribe(cursor.getString(cursor
					.getColumnIndex(PlatformInfo.DESCRIBE)));
			platformInfo.setIcon(cursor.getString(cursor
					.getColumnIndex(PlatformInfo.ICON)));
			platformInfo.setCount(cursor.getInt(cursor
					.getColumnIndex(PlatformInfo.COUNT)));

			// if (!issimple) {
			// if(cursor.getString(cursor.getColumnIndex(PlatformInfo.ICON))==null){
			// String
			// path="/assets/"+cursor.getString(cursor.getColumnIndex(PlatformInfo.NAME))+"_logo.png";
			// System.out.println(path);
			// InputStream inputStream = getClass().getResourceAsStream(path);
			// if(inputStream==null){
			// //获取缓存
			// Drawable
			// icon=BitmapDrawable.createFromPath(ApplicationEx.appSdCardPath+Constants.IMG_CACHE_DIR);
			// //不存在下载
			// if(icon==null){
			// icon=new
			// BitmapDrawable(PicUtil.getbitmapAndwrite(cursor.getString(cursor.getColumnIndex(PlatformInfo.ICON))));
			// }
			// }
			// else{
			// Drawable icon = Drawable.createFromStream(inputStream, "image");
			// }
			// }
			// else{
			// //获取缓存
			// Drawable
			// icon=BitmapDrawable.createFromPath(ApplicationEx.appSdCardPath+Constants.IMG_CACHE_DIR);
			// //不存在下载
			// if(icon==null){
			// icon=new
			// BitmapDrawable(PicUtil.getbitmapAndwrite(cursor.getString(cursor.getColumnIndex(PlatformInfo.ICON))));
			// }
			// }
			// }

			platformInfos.add(platformInfo);
			cursor.moveToNext();

		}
		cursor.close();// 关闭
		Log.e("Database", "getPlatforms-->" + platformInfos.size());
		return platformInfos;
	}

	/**
	 * 保存
	 * 
	 * @param platformInfo
	 * @return
	 */
	public long savePlatformInfo(PlatformInfo platformInfo) {
		ContentValues values = new ContentValues();
		values.put(PlatformInfo.PID, platformInfo.getPid());
		values.put(PlatformInfo.NAME, platformInfo.getName());
		values.put(PlatformInfo.SNAME, platformInfo.getSname());
		values.put(PlatformInfo.DESCRIBE, platformInfo.getDescribe());
		values.put(PlatformInfo.ICON, platformInfo.getIcon());
		values.put(PlatformInfo.COUNT, platformInfo.getCount());
		return db.insert(TB_PLATFORM, null, values);
	}

	public int delPlatformInfo(String pid) {
		return db.delete(TB_USERINFO, UserInfo.PID + " =?",
				new String[] { pid });

	}

	/**
	 * 获取用户绑定平台帐号信息
	 * 
	 * @param issimple
	 * @return
	 */
	public List<PlatformInfo> getMyBindPlatforms() {

		List<UserInfo> userInfos = getAllUserInfo();

		System.out.println(userInfos.size());
		if (userInfos != null && userInfos.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			String[] params = new String[userInfos.size()];
			for (int i = 0; i < userInfos.size(); i++) {
				buffer.append("?").append(",");
				params[i] = userInfos.get(i).getPid().toString();
			}
			buffer.deleteCharAt(buffer.length() - 1);
			Cursor cursor = db.query(TB_PLATFORM, null, PlatformInfo.ID
					+ " in(" + buffer.toString() + ")", params, null, null,
					null);
			List<PlatformInfo> platformInfos = new ArrayList<PlatformInfo>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
				PlatformInfo platformInfo = new PlatformInfo();
				platformInfo.setId(cursor.getInt(cursor
						.getColumnIndex(PlatformInfo.ID)));
				platformInfo.setPid(cursor.getInt(cursor
						.getColumnIndex(PlatformInfo.PID)));
				platformInfo.setName(cursor.getString(cursor
						.getColumnIndex(PlatformInfo.NAME)));
				platformInfo.setSname(cursor.getString(cursor
						.getColumnIndex(PlatformInfo.SNAME)));
				platformInfo.setDescribe(cursor.getString(cursor
						.getColumnIndex(PlatformInfo.DESCRIBE)));
				platformInfo.setIcon(cursor.getString(cursor
						.getColumnIndex(PlatformInfo.ICON)));
				platformInfo.setCount(cursor.getInt(cursor
						.getColumnIndex(PlatformInfo.COUNT)));
				platformInfos.add(platformInfo);
				cursor.moveToNext();

			}
			cursor.close();// 关闭
			return platformInfos;
		}
		return null;
	}

	public void syncPlatformData(List<PlatformInfo> serverPInfos) {
		if (serverPInfos != null && serverPInfos.size() > 0) {
			List<PlatformInfo> pInfos = getAllPlatformInfo();// 获取本机
			List<PlatformInfo> delPInfos = new ArrayList<PlatformInfo>();
			List<PlatformInfo> samePInfos = new ArrayList<PlatformInfo>();
			// 本地数据库是否存在
			if (pInfos != null && pInfos.size() > 0) {
				for (PlatformInfo info : pInfos) {
					// 本地的元素在服务器返回列表中不存在 说明是服务器删除了
					if (!serverPInfos.contains(info)) {
						delPInfos.add(info);
						continue;
					}
					// 同样的元素
					samePInfos.add(info);

				}
				serverPInfos.removeAll(samePInfos);// 移除相同的就是新增的
				for (PlatformInfo platformInfo : serverPInfos) {
					savePlatformInfo(platformInfo);
				}
				for (PlatformInfo platformInfo : delPInfos) {
					delPlatformInfo(String.valueOf(platformInfo.getPid()));
				}
			} else {
				for (PlatformInfo platformInfo : delPInfos) {
					savePlatformInfo(platformInfo);
				}
			}

		}
		// 全部删除
		else {
			for (PlatformInfo platformInfo : serverPInfos) {
				delPlatformInfo(String.valueOf(platformInfo.getPid()));
			}
		}

	}

	public void syncUserInfoData(List<UserInfo> serverUserInfos) {
		if (serverUserInfos != null && serverUserInfos.size() > 0) {
			List<UserInfo> uInfos = getAllUserInfo();// 获取本机
			List<UserInfo> delUInfos = new ArrayList<UserInfo>();
			List<UserInfo> sameUInfos = new ArrayList<UserInfo>();
			// 本地数据库是否存在
			if (uInfos != null && uInfos.size() > 0) {
				for (UserInfo info : uInfos) {
					// 本地的元素在服务器返回列表中不存在 说明是服务器删除了
					if (!serverUserInfos.contains(info)) {
						delUInfos.add(info);
						continue;
					}
					// 同样的元素
					sameUInfos.add(info);

				}
				serverUserInfos.removeAll(sameUInfos);// 移除相同的就是新增的
				for (UserInfo userInfo : serverUserInfos) {
					saveUserInfo(userInfo);
				}
				for (UserInfo userInfo : delUInfos) {
					delUserInfo(userInfo.getAuthId());
				}
			} else {
				for (UserInfo userInfo : delUInfos) {
					saveUserInfo(userInfo);
				}
			}

		}
		// 全部删除
		else {
			for (UserInfo userInfo : serverUserInfos) {
				delUserInfo(userInfo.getAuthId());
			}
		}

	}

}
