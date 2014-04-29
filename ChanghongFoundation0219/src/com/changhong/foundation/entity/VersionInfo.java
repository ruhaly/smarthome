package com.changhong.foundation.entity;

public class VersionInfo
{
    private String versionName;
    
    //版本号
    private String versionCode;
    
    //版本地址
    private String sourceUrl;
    
    //创建时间
    private String createTime;
    
    public String getVersionName()
    {
        return versionName;
    }
    
    public void setVersionName(String versionName)
    {
        this.versionName = versionName;
    }
    
    public String getVersionCode()
    {
        return versionCode;
    }
    
    public void setVersionCode(String versionCode)
    {
        this.versionCode = versionCode;
    }
    
    public String getSourceUrl()
    {
        return sourceUrl;
    }
    
    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
}
