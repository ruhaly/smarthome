/**
 * BillInfo.java
 * com.pactera.ch_bedframe.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-5 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:BillInfo Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-5 下午2:17:09
 */
public class BillInfo
{
    private String id;
    
    private String name;
    
    private String content;
    
    private String paymoney;
    
    private String integral;
    
    private String date;
    
    private String month;
    
    private String consumerType;
    
    public String getConsumerType()
    {
        return consumerType;
    }
    
    public void setConsumerType(String consumerType)
    {
        this.consumerType = consumerType;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    // 历史账单
    private List<BillInfo> list = new ArrayList<BillInfo>();
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPaymoney()
    {
        return paymoney;
    }
    
    public void setPaymoney(String paymoney)
    {
        this.paymoney = paymoney;
    }
    
    public String getIntegral()
    {
        return integral;
    }
    
    public void setIntegral(String integral)
    {
        this.integral = integral;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getMonth()
    {
        return month;
    }
    
    public void setMonth(String month)
    {
        this.month = month;
    }
    
    public List<BillInfo> getList()
    {
        return list;
    }
    
    public void setList(List<BillInfo> list)
    {
        this.list = list;
    }
    
}
