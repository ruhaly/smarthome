package com.changhong.smarthome.phone.sns.bean;

import java.util.List;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForwardReqBean
{
    /**
     * 主题动态ID
     */
    private String themeId;
    
    /**
     * 用户ID用于获取好友
     */
    private String userId;
    
    /**
     * 转发的状态(公开,好友圈,私信)
     */
    private String themeType;
    
    /**
     * 在转发的动态下用户自己的评论信息
     */
    private String myContent;
    
    /**
     * 私信人ID列表
     */
    private List<PersonalBean> personalIdList;
    
    public String getThemeId()
    {
        return themeId;
    }
    
    public void setThemeId(String themeId)
    {
        this.themeId = themeId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getThemeType()
    {
        return themeType;
    }
    
    public void setThemeType(String themeType)
    {
        this.themeType = themeType;
    }
    
    public String getMyContent()
    {
        return myContent;
    }
    
    public void setMyContent(String myContent)
    {
        this.myContent = myContent;
    }
    
    public List<PersonalBean> getPersonalIdList()
    {
        return personalIdList;
    }
    
    public void setPersonalIdList(List<PersonalBean> personalIdList)
    {
        this.personalIdList = personalIdList;
    }
}
