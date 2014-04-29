package com.changhong.smarthome.phone.property.entry;

import java.io.Serializable;
import java.util.ArrayList;

public class HouseInfo implements Serializable
{
    //房屋ID（必填）
    private String id;
    
    //最低价格
    private int low_price;
    
    //最高价格
    private int high_price;
    
    //描述信息
    private String description;
    
    //0:无状1:待租2:已租3:待售4:已售
    private int type;
    
    //url
    private ArrayList imgs;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public int getLow_price()
    {
        return low_price;
    }
    
    public void setLow_price(int low_price)
    {
        this.low_price = low_price;
    }
    
    public int getHigh_price()
    {
        return high_price;
    }
    
    public void setHigh_price(int high_price)
    {
        this.high_price = high_price;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }

    public ArrayList getImgs()
    {
        return imgs;
    }

    public void setImgs(ArrayList imgs)
    {
        this.imgs = imgs;
    }
    
   
    
}
