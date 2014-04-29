package com.changhong.foundation.logic;

import java.util.ArrayList;
import java.util.List;

import com.changhong.foundation.entity.User;
import com.changhong.sdk.logic.SuperLogic;

public class MemberLogic extends SuperLogic
{
    
    public List<User> uList = new ArrayList<User>();
    
    private static MemberLogic ins;
    
    public static synchronized MemberLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MemberLogic();
        }
        return ins;
    }
    
    @Override
    public void clear()
    {
        
    }
    
    @Override
    public void stopRequest()
    {
        
    }
    
}
