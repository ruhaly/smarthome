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
public class MsgAllLogic extends SuperLogic
{
    
    private static MsgAllLogic ins;
    
    private HttpHandler<String> httpHanlder;
    
    public List<MessageInfo> allMsgList = new ArrayList<MessageInfo>();
    
    public static final int PAGENUM = 10;
    
    public int currentPage = 1;
    
    //当前请求是刷新还是加载更多
    public boolean isRefresh = true;
    
    public static synchronized MsgAllLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MsgAllLogic();
        }
        return ins;
    }
    
    public void requestAllMsgList(BaseAccountInfo baseAccountInfo, int[] type,
            HttpUtils httpUtils)
    {
        Pager pager = new Pager();
        pager.setPageId(isRefresh ? 1 : currentPage + 1);
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
                RequestId.AllMSGLIST_REQUESTID,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        // TODO Auto-generated method stub
        switch (requestId)
        {
            case RequestId.AllMSGLIST_REQUESTID:
                handlerAllMsgList(serviceRes);
                return;
        }
    }
    
    private void handlerAllMsgList(ServiceResponse response)
    {
        // TODO Auto-generated method stub
        if (response.getBody().getResult().equals("0"))
        {
            List<MessageInfo> tempList = JsonParse.parseMsgListRes(response.getBody()
                    .getParamsString());
            if (null != tempList)
            {
                
                if (isRefresh)
                {
                    allMsgList.clear();
                    currentPage = 1;
                }
                allMsgList.addAll(tempList);
                if (tempList.size() > 0)
                {
                    if (!isRefresh)
                    {
                        currentPage++;
                    }
                    handler.obtainMessage(MsgWhat.MSGWHAT_ALLMSG_SUCCESS, true)
                            .sendToTarget();
                }
                else
                {
                    handler.obtainMessage(MsgWhat.MSGWHAT_ALLMSG_SUCCESS, false)
                            .sendToTarget();
                }
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
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    @Override
    public void clear()
    {
        if (null != allMsgList)
        {
            allMsgList.clear();
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
