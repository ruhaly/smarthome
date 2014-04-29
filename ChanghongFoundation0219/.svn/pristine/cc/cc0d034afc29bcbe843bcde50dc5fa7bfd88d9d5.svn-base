/**
 * MsgLogic.java
 * com.pactera.ch_bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-4 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.foundation.baseapi.HttpAction;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.JsonParse;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.baseapi.RequestId;
import com.changhong.foundation.entity.MessageInfo;
import com.changhong.sdk.entity.BaseAccountInfo;
import com.changhong.sdk.entity.Pager;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * ClassName:MsgLogic Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-4 上午9:29:41
 */
public class MsgSystemLogic extends SuperLogic
{
    
    private static MsgSystemLogic ins;
    
    public HttpHandler<String> httpHanlder;
    
    public List<MessageInfo> systemMsgList = new ArrayList<MessageInfo>();
    
    public static final int PAGENUM = 10;
    
    public int currentPage = 1;
    
    public static synchronized MsgSystemLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MsgSystemLogic();
        }
        return ins;
    }
    
    public void requestSystemMsgList(BaseAccountInfo baseAccountInfo,
            int[] type, HttpUtils httpUtils)
    {
        Pager pager = new Pager();
        pager.setPageId(currentPage);
        pager.setPageSize(PAGENUM);
        
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("accountInfo", baseAccountInfo);
        serviceInfo.put("msgType", type);
        serviceInfo.put("pager", pager);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GET_MSGLIST,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GET_MSGLIST,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.SYSTEMMSGLIST_REQUESTID,
                this,
                false,
                HttpUrl.URL_CBS);
        
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
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        // TODO Auto-generated method stub
        switch (requestId)
        {
        
            case RequestId.SYSTEMMSGLIST_REQUESTID:
                handlerSystemMsgList(serviceRes);
                return;
        }
    }
    
    private void handlerSystemMsgList(ServiceResponse response)
    {
        // TODO Auto-generated method stub
        if (response.getBody().getResult().equals("0"))
        {
            systemMsgList = JsonParse.parseMsgListRes(response.getBody()
                    .getParamsString());
            if (null != systemMsgList)
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_SYSTEMMSG_SUCCESS);
            }
            else
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_MSG_ERROR);
            }
        }
        else
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_MSG_ERROR);
        }
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        
    }
    
    @Override
    public void clear()
    {
        if (null != systemMsgList)
        {
            systemMsgList.clear();
        }
        
    }
    
    @Override
    public void stopRequest()
    {
        
        if (httpHanlder != null)
        {
            httpHanlder.stop();
        }
        
    }
    
}
