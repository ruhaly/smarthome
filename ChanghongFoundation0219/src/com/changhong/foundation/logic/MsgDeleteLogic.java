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

public class MsgDeleteLogic extends SuperLogic
{
    
    HttpHandler<String> httpHanlder;
    
    private static MsgDeleteLogic ins;
    
    public static synchronized MsgDeleteLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MsgDeleteLogic();
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
        switch (requestId)
        {
            case RequestId.MSGDELETE_REQUESTID:
                if (serviceRes.getBody().getResult().equals("0"))
                {
                    System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkk");
                }
                return;
        }
    }
    
    public void requestMsgDelete(String originalType, String msgId)
    {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("originalType", originalType);
        serviceInfo.put("msgId", msgId);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_DELETE_MSG,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_DELETE_MSG,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MSGDELETE_REQUESTID,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
}
