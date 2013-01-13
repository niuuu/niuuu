package com.app.onenet.model;

import java.io.Serializable;

/**
 * 用户授权信息
 * 
 * @author niu
 * 
 */
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ID = "_id";
	public static final String AUTHID="authId";
	public static final String USERID = "userId";
	public static final String USERNAME = "userName";
	public static final String USERICON = "userIcon";
	public static final String ISDEFAULT = "isDefault";
	public static final String NICKNAME = "nickName";
	public static final String PID = "pid";
	private String id;
	private String authId;
	private String userId; 
	private String userName;
	private String userIcon;
	private Integer isDefault=0;
	private String nickName;
	private Integer pid;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authId == null) ? 0 : authId.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (authId == null) {
			if (other.authId != null)
				return false;
		} else if (!authId.equals(other.authId))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
	


}
