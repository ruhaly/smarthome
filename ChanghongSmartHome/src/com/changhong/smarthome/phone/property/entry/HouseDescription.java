package com.changhong.smarthome.phone.property.entry;

import java.io.Serializable;
import java.util.ArrayList;

public class HouseDescription implements Serializable
{
    
    //修改时候标识
    private String id;
    
    //房号(用于显示)
    private String house_no;
    
    //描述信息
    private String description;
    
    //0:无状态1:待租2:已租3:待售4:已售
    private int state;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getHouse_no()
    {
        return house_no;
    }
    
    public void setHouse_no(String house_no)
    {
        this.house_no = house_no;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public int getState()
    {
        return state;
    }
    
    public void setState(int state)
    {
        this.state = state;
    }
    
}
