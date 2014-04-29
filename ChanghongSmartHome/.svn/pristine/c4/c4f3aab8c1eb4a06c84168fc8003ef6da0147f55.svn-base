package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.entity.User;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.baseapi.HttpAction;
import com.changhong.smarthome.phone.foundation.baseapi.MsgWhat;
import com.changhong.smarthome.phone.foundation.baseapi.RequestId;
import com.changhong.smarthome.phone.foundation.baseapi.ResultCode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class SendFeedBackLogic extends SuperLogic
{  
    public List<User> list = new ArrayList<User>();
    
    public HttpHandler<String> httpHanlder;
    
    public static SendFeedBackLogic ins;
    
    public static synchronized SendFeedBackLogic getInstance()
    {
        if (null == ins)
        {
            ins = new SendFeedBackLogic();
        }
        return ins;
    }
    
    /**
     * 获取小区
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestSendFeedBack(String userId,String content,HttpUtils httpUtils)
    {   
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", userId);
        serviceInfo.put("content", content);

        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GETCOMMUNITY_BYNAME,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GETCOMMUNITY_BYNAME,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.COMMUNITY_GET_BYNAME_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
            case RequestId.COMMUNITY_DELETE_REQUESTID:
            {
                if (ResultCode.RESULT_SUCCESS.equals(serviceRes.getBody().getResult()))
                {
                    handler.sendEmptyMessage(MsgWhat.MSGWHAT_DELETE_COMMUNITY_SUCCESS);
                }
                else
                {
                    handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
                }
                break;
            }
            default:
                break;
        }
    }
    
    
    @Override
    public void clear()
    {
       // list.clear();
    }
    
    @Override
    public void stopRequest()
    {
        httpHanlder.stop();
    }
    
}
