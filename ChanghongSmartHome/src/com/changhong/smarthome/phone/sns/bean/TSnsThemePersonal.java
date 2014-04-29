package com.changhong.smarthome.phone.sns.bean;


@SuppressWarnings("serial")
public class TSnsThemePersonal implements java.io.Serializable
{
    private String id;
    
    private String themeId;
    
    private String personalId;
    
    private String personalName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getThemeId()
    {
        return themeId;
    }
    
    public void setThemeId(String themeId)
    {
        this.themeId = themeId;
    }
    
    public String getPersonalId()
    {
        return personalId;
    }
    
    public void setPersonalId(String personalId)
    {
        this.personalId = personalId;
    }
    
    public String getPersonalName()
    {
        return personalName;
    }
    
    public void setPersonalName(String personalName)
    {
        this.personalName = personalName;
    }
}