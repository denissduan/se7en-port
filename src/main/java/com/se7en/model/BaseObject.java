package com.se7en.model;

import java.util.Date;

import com.se7en.model.base.BaseUser;

public class BaseObject extends Base{
	
	public final static BaseObject EMPTY_BASEOBJECT = new BaseObject();

	private Integer state;
	
	private Date createTime;
	
	private BaseUser createUser;
	
	private Date updateTime;
	
	private BaseUser updateUser;
	
	private String describ;
	
	private Integer sindex;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescrib() {
		return describ;
	}

	public void setDescrib(String describ) {
		this.describ = describ;
	}

	public Integer getSindex() {
		return sindex;
	}

	public void setSindex(Integer sindex) {
		this.sindex = sindex;
	}

	public BaseUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(BaseUser createUser) {
		this.createUser = createUser;
	}

	public BaseUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(BaseUser updateUser) {
		this.updateUser = updateUser;
	}
	
}
