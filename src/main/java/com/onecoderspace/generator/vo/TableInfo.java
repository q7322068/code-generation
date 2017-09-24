package com.onecoderspace.generator.vo;


/**
 * 
 * @author Administrator
 *
 */
public class TableInfo {

	//TABLE_NAME,TABLE_COMMENT
	private String tabelName;
	
	private String modleName;
	
	private String tableComment;
	
	private String idType;
	

	public TableInfo(Object[] arr) {
		this.tabelName = String.valueOf(arr[0]);
		String[] items = this.tabelName.split("_");
		
		String modelName = "";
		for (String item : items) {
			modelName += item.substring(0,1).toUpperCase()+item.substring(1);
		}
		this.modleName = modelName;
		this.tableComment = arr[1] != null ? String.valueOf(arr[1]) : "";
	}

	public String getTabelName() {
		return tabelName;
	}

	public void setTabelName(String tabelName) {
		this.tabelName = tabelName;
	}

	public String getModleName() {
		return modleName;
	}

	public void setModleName(String modleName) {
		this.modleName = modleName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Override
	public String toString() {
		return "TableInfo [tabelName=" + tabelName + ", modleName=" + modleName
				+ ", tableComment=" + tableComment + ", idType=" + idType + "]";
	}
	
}
