package com.changhong.smarthome.phone.sns.bean;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-3-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AccountInfo
{
    /**
     * 账户ID（必填）
     */
    private String accountId;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 社区Code
     */
    private String communityCode;
    
    /**
     * 标识
     */
    private int flag;
    
    private String resolution;
    
    private String type;
    
    public String getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getCommunityCode()
    {
        return communityCode;
    }
    
    public void setCommunityCode(String communityCode)
    {
        this.communityCode = communityCode;
    }
    
    public int getFlag()
    {
        return flag;
    }
    
    public void setFlag(int flag)
    {
        this.flag = flag;
    }
    
    public String getResolution()
    {
        return resolution;
    }
    
    public void setResolution(String resolution)
    {
        this.resolution = resolution;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
}
