package com.taotao.common.vo;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable {
	
	private long total;
	//？表示占位符，如果设置值后不能再修改
	private List<?> rows;
	public DataGridResult() {
		super();
	}
	public DataGridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
