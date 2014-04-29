package com.changhong.smarthome.phone.property.entry;

import java.util.List;

/**
 * <功能详细描述>
 * 当前积分量、截止日期和可兑换礼品列表的实体类
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:20:17
 */
public class IntegrationVO
{
	private String deadline;
	
	private long totalPoint;
	
	private int peResult;
	
    private List<ExchangeVO> pointGiftList;
    
    public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public long getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(long totalPoint) {
		this.totalPoint = totalPoint;
	}
    
	public int getPeResult() {
		return peResult;
	}
	
	public void setPeResult(int peResult) {
		this.peResult = peResult;
	}
	
	public List<ExchangeVO> getPointGiftList() {
		return pointGiftList;
	}
	
	public void setPointGiftList(List<ExchangeVO> pointGiftList) {
		this.pointGiftList = pointGiftList;
	}
    
    
}