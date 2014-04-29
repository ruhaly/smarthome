package com.changhong.smarthome.phone.store.logic.bean;

import java.io.Serializable;

public class GoodsColumnBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String columnName;
    
    private String columnId;
    
    private String columnIcon;

    public String getColumnIcon()
    {
        return columnIcon;
    }

    public void setColumnIcon(String columnIcon)
    {
        this.columnIcon = columnIcon;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getColumnId()
    {
        return columnId;
    }

    public void setColumnId(String columnId)
    {
        this.columnId = columnId;
    }
    
    
}
