package com.changhong.smarthome.phone.cinema.entry;

import java.io.Serializable;

public class CinemaTitle implements Serializable
{
    
    private int id;
    
    private String columnName;
    
    private int orderSeq;
    
    private String picUrl;
    
    private int updateCount;
    
    private int queryType;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public int getOrderSeq()
    {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq)
    {
        this.orderSeq = orderSeq;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public int getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(int updateCount)
    {
        this.updateCount = updateCount;
    }

    public int getQueryType()
    {
        return queryType;
    }

    public void setQueryType(int queryType)
    {
        this.queryType = queryType;
    }

 
    
    
  
    
   
    
}
