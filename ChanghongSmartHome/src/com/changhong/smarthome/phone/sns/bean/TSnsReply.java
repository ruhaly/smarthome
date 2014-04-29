package com.changhong.smarthome.phone.sns.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TSnsReply implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 评论ID
     */
    private BigDecimal id;
    
    /**
     * 主题动态ID
     */
    private BigDecimal themeId;
    
    /**
     * 回复动态评论内容
     */
    private String content;
    
    /**
     * 类型(0：回复1：引用)
     */
    private String replyType;
    
    /**
     * 回复时间
     */
    private String replyTime;
    
    /**
     * 主题表动态回复人信息
     */
    private UserInfoDto user;
    
    public UserInfoDto getUser()
    {
        return user;
    }
    
    public void setUser(UserInfoDto user)
    {
        this.user = user;
    }
    
    public BigDecimal getId()
    {
        return id;
    }
    
    public void setId(BigDecimal id)
    {
        this.id = id;
    }
    
    public BigDecimal getThemeId()
    {
        return themeId;
    }
    
    public void setThemeId(BigDecimal themeId)
    {
        this.themeId = themeId;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getReplyType()
    {
        return replyType;
    }
    
    public void setReplyType(String replyType)
    {
        this.replyType = replyType;
    }
    
    public String getReplyTime()
    {
        return replyTime;
    }
    
    public void setReplyTime(String replyTime)
    {
        this.replyTime = replyTime;
    }
}