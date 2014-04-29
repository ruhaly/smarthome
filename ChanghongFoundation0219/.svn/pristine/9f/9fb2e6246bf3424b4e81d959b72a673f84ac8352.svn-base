/**
 * PluginLogic.java
 * com.pactera.ch_bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-9 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.exception.HttpException;

/**
 * ClassName:PluginLogic Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-9 下午3:50:26
 */
public class PluginLogic extends SuperLogic
{
    
    public List<BusinessInfo> bmList = new ArrayList<BusinessInfo>();
    
    public List<BusinessInfo> gkList = new ArrayList<BusinessInfo>();
    
    public List<BusinessInfo> afList = new ArrayList<BusinessInfo>();
    
    public List<BusinessInfo> otherList = new ArrayList<BusinessInfo>();
    
    public List<BusinessInfo> shortcutList = new ArrayList<BusinessInfo>();
    
    private static PluginLogic ins;
    
    public static synchronized PluginLogic getInstance()
    {
        if (null == ins)
        {
            ins = new PluginLogic();
        }
        return ins;
    }
    
    @Override
    public void handleHttpResponse(String response, int rspCode, int requestId)
    {
        
    }
    
    public List<BusinessInfo> getShortcutList()
    {
        return shortcutList;
    }
    
    public void setShortcutList(List<BusinessInfo> shortcutList)
    {
        this.shortcutList = shortcutList;
    }
    
    @Override
    public void handleHttpResponse(String response, int requestId,
            InputStream is)
    {
        
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        
    }
    
    @Override
    public void clear()
    {
        shortcutList.clear();
        bmList.clear();
        gkList.clear();
        afList.clear();
        otherList.clear();
    }
    
    public List<BusinessInfo> getBmList()
    {
        return bmList;
    }
    
    public void setBmList(List<BusinessInfo> bmList)
    {
        this.bmList = bmList;
    }
    
    public List<BusinessInfo> getGkList()
    {
        return gkList;
    }
    
    public void setGkList(List<BusinessInfo> gkList)
    {
        this.gkList = gkList;
    }
    
    public List<BusinessInfo> getAfList()
    {
        return afList;
    }
    
    public void setAfList(List<BusinessInfo> afList)
    {
        this.afList = afList;
    }
    
    public List<BusinessInfo> getOtherList()
    {
        return otherList;
    }
    
    public void setOtherList(List<BusinessInfo> otherList)
    {
        this.otherList = otherList;
    }
    
    @Override
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        
    }
    
}
