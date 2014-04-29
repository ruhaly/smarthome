package com.changhong.foundation.logic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.changhong.foundation.baseapi.HttpAction;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.RequestId;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class MsgCleanLogic extends SuperLogic
{
    
    HttpHandler<String> httpHanlder;
    
    private static MsgCleanLogic ins;
    
    public static synchronized MsgCleanLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MsgCleanLogic();
        }
        return ins;
    }
    
    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void handleHttpResponse(String response, int rspCode, int requestId)
    {
        // TODO Auto-generated method stub
        super.handleHttpResponse(response, rspCode, requestId);
    }
    
    @Override
    public void handleHttpResponse(String response, int requestId,
            InputStream is)
    {
        // TODO Auto-generated method stub
        super.handleHttpResponse(response, requestId, is);
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        // TODO Auto-generated method stub
        // switch (requestId) {
        // case RequestId.MSGCLEAN_REQUESTID:
        // if (serviceRes.getBody().getResult().equals("0")) {
        // System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkk");
        // }
        // return;
        // }
    }
    
    public void requestMsgClean(String msgType)
    {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("msgType", msgType);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_CLEAN_MSG,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_CLEAN_MSG,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MSGCLEAN_REQUESTID,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
}
