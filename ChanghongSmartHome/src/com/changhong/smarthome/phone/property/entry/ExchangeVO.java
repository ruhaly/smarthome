package com.changhong.smarthome.phone.property.entry;

import java.math.BigDecimal;

/**
 * <功能详细描述>
 * 可兑换礼品的实体类
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:23:08
 */
public class ExchangeVO
{
    private BigDecimal id;
    
    private BigDecimal campaignId;
    
    private String name;
    
    private BigDecimal point;
    
    private BigDecimal stock;
    
    private String giftPicUrl;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(BigDecimal campaignId) {
		this.campaignId = campaignId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public String getGiftPicUrl() {
		return giftPicUrl;
	}

	public void setGiftPicUrl(String giftPicUrl) {
		this.giftPicUrl = giftPicUrl;
	}
	
}
    
