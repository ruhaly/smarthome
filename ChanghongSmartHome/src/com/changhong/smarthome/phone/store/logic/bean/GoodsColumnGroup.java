package com.changhong.smarthome.phone.store.logic.bean;

import java.util.List;

public class GoodsColumnGroup
{
    private GoodsColumnBean key;
    
    private List<GoodsColumnBean> value;

    public GoodsColumnBean getKey()
    {
        return key;
    }

    public void setKey(GoodsColumnBean key)
    {
        this.key = key;
    }

    public List<GoodsColumnBean> getValue()
    {
        return value;
    }

    public void setValue(List<GoodsColumnBean> value)
    {
        this.value = value;
    }
}
