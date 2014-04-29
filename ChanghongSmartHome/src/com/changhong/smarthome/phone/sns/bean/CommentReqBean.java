package com.changhong.smarthome.phone.sns.bean;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommentReqBean
{
    /**
     * 评论的动态ID
     */
    private String themeId;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 评论时间
     */
    private String replyTime;
    
    /**
     * 评论人ID
     */
    private String creator;
    
    /**
     * 评论人昵称
     */
    private String creatorNickName;
    
    public String getThemeId()
    {
        return themeId;
    }
    
    public void setThemeId(String themeId)
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
    
    public String getReplyTime()
    {
        return replyTime;
    }
    
    public void setReplyTime(String replyTime)
    {
        this.replyTime = replyTime;
    }
    
    public String getCreator()
    {
        return creator;
    }
    
    public void setCreator(String creator)
    {
        this.creator = creator;
    }
    
    public String getCreatorNickName()
    {
        return creatorNickName;
    }
    
    public void setCreatorNickName(String creatorNickName)
    {
        this.creatorNickName = creatorNickName;
    }
}
