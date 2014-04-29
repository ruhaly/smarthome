package com.changhong.smarthome.phone.cinema.entry;

import java.io.Serializable;

public class Pager implements Serializable
{
    private int pageId;
    private int pageSize;
    public int getPageId()
    {
        return pageId;
    }
    public void setPageId(int pageId)
    {
        this.pageId = pageId;
    }
    public int getPageSize()
    {
        return pageSize;
    }
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    
    
    
   
}
