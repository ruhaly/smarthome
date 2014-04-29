package com.changhong.smarthome.phone.foundation.bean;

import com.changhong.sdk.widget.sortlistview.SortModel;

public class Address extends SortModel
{
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Address()
    {
        super();
    }
    
    public Address(String id, String name, String sortLetters)
    {
        this.id = id;
        setName(name);
        setSortLetters(sortLetters);
    }
    
}
