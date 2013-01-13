package com.app.onenet.model;

public class CacheInfo {
	public static final String ID = "_id";
	public static final String AUTHID="authId";
	public static final String TYPE = "type";
	public static final String INFO = "info";
	public String id;
	public Integer type;
	public String auth_id;
	public String cacheInfo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	public String getCacheInfo() {
		return cacheInfo;
	}
	public void setCacheInfo(String cacheInfo) {
		this.cacheInfo = cacheInfo;
	}

}
