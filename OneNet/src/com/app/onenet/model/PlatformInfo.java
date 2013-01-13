package com.app.onenet.model;

import java.io.Serializable;

/**
 * 平台信息
 * @author niu
 *
 */
public class PlatformInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID = "_id";
	public static final String PID="pid";
    public static final String NAME = "name";
    public static final String SNAME="sname";
    public static final String DESCRIBE = "describe";
    public static final String ICON = "icon";
    public static final String COUNT="count";
    private Integer id;
    private Integer pid;
    private String name; 
    private String sname;
    private String describe;
    private String icon;
    private Integer count; 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PlatformInfo other = (PlatformInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}


    
    

}
