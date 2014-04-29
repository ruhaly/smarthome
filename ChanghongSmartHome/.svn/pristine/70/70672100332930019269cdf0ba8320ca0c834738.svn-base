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

package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.exception.HttpException;

/**
 * 
 * 智能界面插件logic
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月28日]
 */
public class PluginLogic extends SuperLogic
{
    
    public List<BusinessInfo> pList = new ArrayList<BusinessInfo>();
    
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
    public void stopRequest()
    {
        
    }
    
    @Override
    public void clear()
    {
        pList.clear();
    }
    
}
