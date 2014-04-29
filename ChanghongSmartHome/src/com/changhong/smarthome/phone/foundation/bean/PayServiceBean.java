package com.changhong.smarthome.phone.foundation.bean;

public class PayServiceBean
{
    private String id;
    
    private String payType;
    
    private String quarter;
    
    private String address;
    
    private String money;
    
    private String state;
    
    public String getMoney()
    {
        return money;
    }
    
    public void setMoney(String money)
    {
        this.money = money;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getQuarter()
    {
        return quarter;
    }
    
    public void setQuarter(String quarter)
    {
        this.quarter = quarter;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
}
