package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.baseapi.HttpAction;
import com.changhong.smarthome.phone.foundation.baseapi.RequestId;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class MoreGroupLogic extends SuperLogic
{
    
    public List<Community> communityList = new ArrayList<Community>();
    
    public HttpHandler<String> httpHanlder;
    
    public static MoreGroupLogic ins;
    
    public static synchronized MoreGroupLogic getInstance()
    {
        if (null == ins)
        {
            ins = new MoreGroupLogic();
        }
        return ins;
    }
    
    /**
     * 获取市
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestMoreGroup(HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GET_PROVINCE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GET_PROVINCE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.REGISTER_PROVINCE_REQUESTID,
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
            case RequestId.REGISTER_PROVINCE_REQUESTID:
            {
                break;
            }
            default:
                break;
        }
    }
    
    @Override
    public void clear()
    {
        communityList.clear();
    }
    
    @Override
    public void stopRequest()
    {
        httpHanlder.stop();
    }
    
}
