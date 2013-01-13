package com.app.onenet.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class MyAccount  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userId; 
	private String token;
	private String tokenSecret;
	private String userName;
	private Drawable userIcon;
	private String isDefault="0";
	private String nickName;
	private String refreshToken;
	private String openId;
	private String openKey;
	private String expiresIn;
	private Integer pid;
	private Integer fansCount;
	private Integer weiboCount;
	private Integer followerCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Drawable getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(Drawable userIcon) {
		this.userIcon = userIcon;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOpenKey() {
		return openKey;
	}
	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getFansCount() {
		return fansCount;
	}
	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}
	public Integer getWeiboCount() {
		return weiboCount;
	}
	public void setWeiboCount(Integer weiboCount) {
		this.weiboCount = weiboCount;
	}
	public Integer getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}
	

}
