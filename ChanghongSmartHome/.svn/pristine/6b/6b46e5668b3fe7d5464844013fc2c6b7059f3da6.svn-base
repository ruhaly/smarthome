package com.changhong.smarthome.phone.foundation.logic;

import java.util.ArrayList;
import java.util.List;

import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.bean.PrivateLetter;

public class PrivateLetterLogic extends SuperLogic
{
    
    public List<PrivateLetter> list = new ArrayList<PrivateLetter>();
    
    private static PrivateLetterLogic ins;
    
    public static synchronized PrivateLetterLogic getInstance()
    {
        if (null == ins)
        {
            ins = new PrivateLetterLogic();
        }
        return ins;
    }
    
    @Override
    public void clear()
    {
        list.clear();
    }
    
    @Override
    public void stopRequest()
    {
        
    }
    
}
