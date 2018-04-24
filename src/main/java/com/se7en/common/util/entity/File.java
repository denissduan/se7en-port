package com.se7en.common.util.entity;

public class File implements Comparable<File>{

	private String path;
	
	/** 不带后缀名的文件名  */
	private String name;
	
	private String extName;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	@Override
	public int compareTo(File o) {
		
		return this.name.compareTo(o.getName());
	}
	
}
