package com.se7en.common.util.entity;
/**
 * 字段信息
 * @author dl
 */
public class Field {

	private String tableName;	//表名
	private String tableComment;	//表注释
	private String columnId;		//列id
	private String columnName;	//列名
	private String columnComment;	//列注释
	private String columnType;	//列类型
	private int columnPrecision;		//列精度
	private float columnScale;
	private int columnLength;
	private boolean nullable;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public int getColumnPrecision() {
		return columnPrecision;
	}
	public void setColumnPrecision(int columnPrecision) {
		this.columnPrecision = columnPrecision;
	}
	public float getColumnScale() {
		return columnScale;
	}
	public void setColumnScale(float columnScale) {
		this.columnScale = columnScale;
	}
	public int getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
}
