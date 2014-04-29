package com.changhong.smarthome.phone.sns.bean;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserInfo implements Comparable<UserInfo>
{
    private String uid;
    
    private String name;
    
    private String sortKey;
    
    private String sex;
    
    private String birth;
    
    private String homeAddress;
    
    /**
     * 标记加好友是否已经操作（接受/拒绝）
     */
    private boolean isOperation;
    
    /**
     * 标记当前是否被接受(即已经是好友)
     */
    private boolean isAccept;
    
    /**
     * 标记当前是否被选中
     */
    private boolean friendStatu;
    
    /**
     * 好友头像地址
     */
    private String headImgPath;
    
    public String getHeadImgPath()
    {
        return headImgPath;
    }
    
    public void setHeadImgPath(String headImgPath)
    {
        this.headImgPath = headImgPath;
    }
    
    public String getUid()
    {
        return uid;
    }
    
    public void setUid(String uid)
    {
        this.uid = uid;
    }
    
    public boolean isOperation()
    {
        return isOperation;
    }
    
    public void setOperation(boolean isOperation)
    {
        this.isOperation = isOperation;
    }
    
    public boolean isAccept()
    {
        return isAccept;
    }
    
    public void setAccept(boolean isAccept)
    {
        this.isAccept = isAccept;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getBirth()
    {
        return birth;
    }
    
    public void setBirth(String birth)
    {
        this.birth = birth;
    }
    
    public String getHomeAddress()
    {
        return homeAddress;
    }
    
    public void setHomeAddress(String homeAddress)
    {
        this.homeAddress = homeAddress;
    }
    
    public String getSortKey()
    {
        return sortKey;
    }
    
    public void setSortKey(String sortKey)
    {
        this.sortKey = sortKey;
    }
    
    public boolean isFriendStatu()
    {
        return friendStatu;
    }
    
    public void setFriendStatu(boolean friendStatu)
    {
        this.friendStatu = friendStatu;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    //
    /**
     * 动态发布人ID
     */
    private String creatorId;
    
    /**
     * 动态发布人昵称
     */
    private String creatorNickName;
    
    /**
     * 动态发布人头像
     */
    private String creatorPicUrl;
    
    /**
     * 主题表动态发布时间
     */
    private String creatorTime;
    
    /**
     * 业主用户类型
     */
    private String creatorType;
    
    /**
     * 发帖类型(0.原创，1.转发)
     */
    private String postType;
    
    /**
     * 主题类型类(1：求助2：二手信息3：随笔4：随拍5：意见投诉)
     */
    private String themeCode;
    
    /**
     * 动态修改时间
     */
    private String updateTime;
    
    /**
     * 经度、纬度
     */
    private String coordinate;
    
    /**
     * 回复次数
     */
    private int replyCount;
    
    /**
     * 浏览次数
     */
    private int browseCount;
    
    /**
     * 转发次数
     */
    private int forwardCount;
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorNickName()
    {
        return creatorNickName;
    }
    
    public void setCreatorNickName(String creatorNickName)
    {
        this.creatorNickName = creatorNickName;
    }
    
    public String getCreatorPicUrl()
    {
        return creatorPicUrl;
    }
    
    public void setCreatorPicUrl(String creatorPicUrl)
    {
        this.creatorPicUrl = creatorPicUrl;
    }
    
    public String getCreatorTime()
    {
        return creatorTime;
    }
    
    public void setCreatorTime(String creatorTime)
    {
        this.creatorTime = creatorTime;
    }
    
    public String getCreatorType()
    {
        return creatorType;
    }
    
    public void setCreatorType(String creatorType)
    {
        this.creatorType = creatorType;
    }
    
    public String getPostType()
    {
        return postType;
    }
    
    public void setPostType(String postType)
    {
        this.postType = postType;
    }
    
    public String getThemeCode()
    {
        return themeCode;
    }
    
    public void setThemeCode(String themeCode)
    {
        this.themeCode = themeCode;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    public int getReplyCount()
    {
        return replyCount;
    }
    
    public void setReplyCount(int replyCount)
    {
        this.replyCount = replyCount;
    }
    
    public int getBrowseCount()
    {
        return browseCount;
    }
    
    public void setBrowseCount(int browseCount)
    {
        this.browseCount = browseCount;
    }
    
    public int getForwardCount()
    {
        return forwardCount;
    }
    
    public void setForwardCount(int forwardCount)
    {
        this.forwardCount = forwardCount;
    }
    
    @Override
    public int compareTo(UserInfo another)
    {
        // TODO Auto-generated method stub
        return this.getSortKey().compareToIgnoreCase(another.getSortKey());
    }
}
