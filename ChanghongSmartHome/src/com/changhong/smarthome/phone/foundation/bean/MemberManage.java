package com.changhong.smarthome.phone.foundation.bean;

import java.util.ArrayList;
import java.util.List;

import com.changhong.sdk.entity.Room;
import com.changhong.sdk.entity.User;

public class MemberManage
{
    //新成员
    public User user;
    
    //新成员个数
    public String newCount;
    
    public List<Room> listRoom = new ArrayList<Room>();
    
    public List<User> userList = new ArrayList<User>();
    
    public List<User> getUserList()
    {
        return userList;
    }
    
    public void setUserList(List<User> userList)
    {
        this.userList = userList;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public String getNewCount()
    {
        return newCount;
    }
    
    public void setNewCount(String newCount)
    {
        this.newCount = newCount;
    }
    
    public List<Room> getListRoom()
    {
        return listRoom;
    }
    
    public void setListRoom(List<Room> listRoom)
    {
        this.listRoom = listRoom;
    }
    
}
