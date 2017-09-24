package com.onecoderspace.generator.vo;

import org.apache.commons.lang3.StringUtils;


public class ColumnInfo {
	//COLUMN_NAME,COLUMN_TYPE,COLUMN_DEFAULT,COLUMN_COMMENT,CHARACTER_MAXIMUM_LENGTH 
	
	private String columnName;
	private String columnType;
	private String columnDefault;
	private String columnComment;
	private String columnCharacterMaximumLength;
	
	private String modelName;
	private String modelType;
	private String modelDefault;
	private String modelComment;
	private String modelCharacterMaximumLength;
	
	private String modelNameFirstUpper;
	
	public ColumnInfo(){}
	
	public ColumnInfo(Object[] arr){
		this.columnName = (String) arr[0];
		this.columnType = (String) arr[1];
		this.columnDefault = arr[2] != null ? String.valueOf(arr[2]) : "";
		this.columnComment = arr[3] != null ? String.valueOf(arr[3]) : "";
		this.columnCharacterMaximumLength = arr[4] != null ? String.valueOf(arr[4]) : "";
		
		this.modelComment = this.columnComment;
		this.modelCharacterMaximumLength = this.columnCharacterMaximumLength;
		
		String[] items = this.columnName.split("_");
		String modelName = "";
		String modelNameFirstUpper = "";
		int i=0;
		for (String item : items) {
			if(i == 0){
				modelName += item.substring(0,1)+item.substring(1);
			} else {
				modelName += item.substring(0,1).toUpperCase()+item.substring(1);
			}
			i++;
			modelNameFirstUpper += item.substring(0,1).toUpperCase()+item.substring(1);
		}
		this.modelName = modelName;
		this.modelNameFirstUpper = modelNameFirstUpper;
		this.modelType = this.columnType;
		this.modelDefault = this.columnDefault == null ? "" : this.columnDefault;
		
		String typeLower = this.columnType.toLowerCase();
		if(typeLower.startsWith("varchar") || typeLower.startsWith("char")){
			this.modelType = "String";
		} else if(typeLower.startsWith("text") || typeLower.startsWith("mediumtext") 
				|| typeLower.startsWith("longtext")){
			this.modelType = "String";
			this.modelDefault = typeLower;
		}else if(typeLower.startsWith("int") || typeLower.startsWith("tinyint")){
			this.modelType = "int";
			if(StringUtils.isBlank(this.columnDefault)){
				this.modelDefault = "0";
			}
			this.modelDefault = String.format("%s default %s", this.columnType.substring(0, this.columnType.indexOf("(")),this.modelDefault);
		}else if(typeLower.startsWith("bigint")){
			this.modelType = "long";
			if(StringUtils.isBlank(this.columnDefault)){
				this.modelDefault = "0";
			}
			this.modelDefault = String.format("%s default %s", this.columnType.substring(0, this.columnType.indexOf("(")),this.modelDefault);
		}else if(typeLower.startsWith("timestamp") || typeLower.startsWith("datetime")){
			this.modelType = "Timestamp";
		}else if(typeLower.startsWith("double")){
			this.modelType = "double";
			if(StringUtils.isBlank(this.columnDefault)){
				this.modelDefault = "0.0";
			}
			this.modelDefault = String.format("%s default %s", this.columnType,this.modelDefault);
		}else if(typeLower.startsWith("float")){
			this.modelType = "float";
			if(StringUtils.isBlank(this.columnDefault)){
				this.modelDefault = "0.0";
			}
			this.modelDefault = String.format("%s default %s", this.columnType,this.modelDefault);
		}
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnDefault() {
		return columnDefault;
	}
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getColumnCharacterMaximumLength() {
		return columnCharacterMaximumLength;
	}
	public void setColumnCharacterMaximumLength(String columnCharacterMaximumLength) {
		this.columnCharacterMaximumLength = columnCharacterMaximumLength;
	}
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelNameFirstUpper() {
		return modelNameFirstUpper;
	}

	public void setModelNameFirstUpper(String modelNameFirstUpper) {
		this.modelNameFirstUpper = modelNameFirstUpper;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getModelDefault() {
		return modelDefault;
	}

	public void setModelDefault(String modelDefault) {
		this.modelDefault = modelDefault;
	}

	public String getModelComment() {
		return modelComment;
	}

	public void setModelComment(String modelComment) {
		this.modelComment = modelComment;
	}

	public String getModelCharacterMaximumLength() {
		return modelCharacterMaximumLength;
	}

	public void setModelCharacterMaximumLength(String modelCharacterMaximumLength) {
		this.modelCharacterMaximumLength = modelCharacterMaximumLength;
	}

	@Override
	public String toString() {
		return "ColumnInfo [columnName=" + columnName + ", columnType="
				+ columnType + ", columnDefault=" + columnDefault
				+ ", columnComment=" + columnComment
				+ ", columnCharacterMaximumLength="
				+ columnCharacterMaximumLength + ", modelName=" + modelName
				+ ", modelType=" + modelType + ", modelDefault=" + modelDefault
				+ ", modelComment=" + modelComment
				+ ", modelCharacterMaximumLength="
				+ modelCharacterMaximumLength + ", modelNameFirstUpper="
				+ modelNameFirstUpper + "]";
	}
	
}
