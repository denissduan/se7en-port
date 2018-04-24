package com.se7en.common.util.entity;
/**
 * 文件夹(路径)信息
 * @author dl
 *
 */
public class Folder {
	private String id;
	private String pid;
	private String name;
	private String path;
	private String isParent = "true";
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getId() {
		return id;
	}
	public Folder() {
		super();
	}
	public Folder(String id, String pid, String name, String path) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.path = path;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
