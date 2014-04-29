package com.changhong.smarthome.phone.property.entry;

import java.util.Date;

/**
 * <功能详细描述>
 * 用户积分兑换记录实体类
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:23:46
 */
public class ExchangeHisList {
	
	private long id;
	
    private String name;
    
	private long point;

    private String beginDate;
    
    private String endDate;
    
    private String giftPicUrl;
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}


	public String getGiftPicUrl() {
		return giftPicUrl;
	}

	public void setGiftPicUrl(String giftPicUrl) {
		this.giftPicUrl = giftPicUrl;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
