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
public class MsgAnfangLogic extends SuperLogic
{
    
    private static MsgAnfangLogic ins;
    
    public HttpHandler<String> httpHanlder;
    
    public List<MessageInfo> securityMsgList = new ArrayList<MessageInfo>();
    
    public static final int PAGENUM = 10;
    
    public int currentPage = 1;
    
    public static synchronized MsgAnfangLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MsgAnfangLogic();
        }
        return ins;
    }
    
    public void requestSecurityMsgList(BaseAccountInfo baseAccountInfo,
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
                RequestId.SECURITYMSGLIST_REQUESTID,
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
        switch (requestId)
        {
            case RequestId.SECURITYMSGLIST_REQUESTID:
                handlerSecurityMsgList(serviceRes);
                return;
        }
    }
    
    private void handlerSecurityMsgList(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("0"))
        {
            securityMsgList = JsonParse.parseMsgListRes(response.getBody()
                    .getParamsString());
            if (null != securityMsgList)
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_SECURITYMSG_SUCCESS);
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
        if (null != securityMsgList)
        {
            securityMsgList.clear();
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