package com.changhong.smarthome.phone.cinema.entry;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class VideoFile implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    //@DatabaseField(generatedId = true)
    
    @DatabaseField(id = true)
    private String contentId;
    
    @DatabaseField
    private String path;
    
    @DatabaseField
    private String name;
    
    @DatabaseField
    private long time;
    
    @DatabaseField
    private String sqlNum;
    
    @DatabaseField
    private String picUrl;
    
    public VideoFile()
    {
    }
    
    public VideoFile(String contentId, String path, String name, long time,
            String sqlNum, String picUrl)
    {
        
        this.contentId = contentId;
        this.path = path;
        this.name = name;
        this.sqlNum = sqlNum;
        this.time = time;
        this.picUrl = picUrl;
    }
    
    public String getPicUrl()
    {
        return picUrl;
    }
    
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }
    
    public String getContentId()
    {
        return contentId;
    }
    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public long getTime()
    {
        return time;
    }
    
    public void setTime(long time)
    {
        this.time = time;
    }
    
    public String getSqlNum()
    {
        return sqlNum;
    }
    
    public void setSqlNum(String sqlNum)
    {
        this.sqlNum = sqlNum;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}