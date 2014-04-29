package com.changhong.smarthome.phone.YellowPages.entry;

import java.io.Serializable;


/**
 * <功能详细描述>
 * 热线电话实体对象
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:32:30
 */
public class LineTelephone implements Serializable {
	
	private String line_name;
	
	private String line_telephoneNumber;
	
	private String work_time;

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public String getLine_telephoneNumber() {
		return line_telephoneNumber;
	}

	public void setLine_telephoneNumber(String line_telephoneNumber) {
		this.line_telephoneNumber = line_telephoneNumber;
	}

	public String getWork_time() {
		return work_time;
	}

	public void setWork_time(String work_time) {
		this.work_time = work_time;
	}

}
