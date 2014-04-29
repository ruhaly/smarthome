package com.changhong.smarthome.phone.cinema.entry;

import java.io.Serializable;

public class Title implements Serializable
{
    private String title_id;
    
    private String title_name;
    
    public String getTitle_id()
    {
        return title_id;
    }
    
    public void setTitle_id(String title_id)
    {
        this.title_id = title_id;
    }
    
    public String getTitle_name()
    {
        return title_name;
    }
    
    public void setTitle_name(String title_name)
    {
        this.title_name = title_name;
    }
    
}
