package com.changhong.smarthome.phone.foundation.bean;

public class Community
{
    private String id;
    
    private String communityId = "";
    
    private String name;
    
    //人员个数
    private String memberNum;
    
    //新成员申请个数
    private String newMemberNum;
    
    //小区图片
    private Integer picUrl;
    
    //大区
    private String area;
    
    private String address;
    
    //是否选中
    private boolean isSelect;
    
    public boolean isSelect()
    {
        return isSelect;
    }
    
    public String getCommunityId()
    {
        return communityId;
    }
    
    public void setCommunityId(String communityId)
    {
        this.communityId = communityId;
    }
    
    public void setSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }
    
    public String getMemberNum()
    {
        return memberNum;
    }
    
    public void setMemberNum(String memberNum)
    {
        this.memberNum = memberNum;
    }
    
    public String getNewMemberNum()
    {
        return newMemberNum;
    }
    
    public void setNewMemberNum(String newMemberNum)
    {
        this.newMemberNum = newMemberNum;
    }
    
    public Integer getPicUrl()
    {
        return picUrl;
    }
    
    public void setPicUrl(Integer picUrl)
    {
        this.picUrl = picUrl;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getArea()
    {
        return area;
    }
    
    public void setArea(String area)
    {
        this.area = area;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
}
