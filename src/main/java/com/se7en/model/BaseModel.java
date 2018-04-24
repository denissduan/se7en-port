package com.se7en.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class BaseModel.
 * 固有字段基类模型
 */
public class BaseModel implements Serializable{
	
	public final static BaseModel EMPTY_BASEMODEL = new BaseModel();
	
	private Integer id;

	private Date dtCreate;
	
	private Date dtLastUpdate;
	
	private Integer state;

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Date getDtLastUpdate() {
		return dtLastUpdate;
	}

	public void setDtLastUpdate(Date dtLastUpdate) {
		this.dtLastUpdate = dtLastUpdate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
