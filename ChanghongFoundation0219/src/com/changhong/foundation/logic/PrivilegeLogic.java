/**
 * PrivilegeLogic.java
 * com.pactera.ch.bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-17 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.logic;

import java.util.ArrayList;
import java.util.List;

import com.changhong.foundation.entity.Privilege;
import com.changhong.sdk.logic.SuperLogic;

/**
 * ClassName:PrivilegeLogic Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-17 下午5:02:55
 */
public class PrivilegeLogic extends SuperLogic
{
    
    public List<Privilege> list = new ArrayList<Privilege>();
    
    private static PrivilegeLogic ins = null;
    
    public static synchronized PrivilegeLogic getInstance()
    {
        if (null == ins)
        {
            ins = new PrivilegeLogic();
        }
        return ins;
    }
    
    public List<Privilege> getList()
    {
        return list;
    }
    
    public void setList(List<Privilege> list)
    {
        this.list = list;
    }
    
    @Override
    public void clear()
    {
        list.clear();
    }
    
    @Override
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        
    }
    
}
