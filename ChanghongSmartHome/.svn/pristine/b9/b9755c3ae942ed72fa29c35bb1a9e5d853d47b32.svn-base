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
import com.changhong.smarthome.phone.foundation.baseapi.JsonParse;
import com.changhong.smarthome.phone.foundation.baseapi.MsgWhat;
import com.changhong.smarthome.phone.foundation.baseapi.RequestId;
import com.changhong.smarthome.phone.foundation.baseapi.ResultCode;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class SelectCommunityLogic extends SuperLogic
{
    public List<Community> list = new ArrayList<Community>();
    
    public HttpHandler<String> httpHanlder;
    
    public static SelectCommunityLogic ins;
    
    public static synchronized SelectCommunityLogic getInstance()
    {
        if (null == ins)
        {
            ins = new SelectCommunityLogic();
        }
        return ins;
    }
    
    /**
     * 获取小区
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestCommunityList(String cityId, String cityName,
            String communityName, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("cityCode", cityId);
        serviceInfo.put("cityName", cityName);
        serviceInfo.put("searchName", communityName);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GETCOMMUNITY_BYNAME,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_SELECT_COMMUNITY_MANAGE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.COMMUNITY_SELECT_REQUESTID,
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
            case RequestId.COMMUNITY_SELECT_REQUESTID:
            {
                handleGetCommunityData(serviceRes);
                break;
            }
            default:
                break;
        }
    }
    
    public void handleGetCommunityData(ServiceResponse response)
    {
        try
        {
            if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
            {
                list = JsonParse.parseSelectCommunityList(response.getBody()
                        .getParamsString());
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_SELECT_COMMUNITY_SUCCESS);
            }
        }
        catch (Exception e)
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    @Override
    public void clear()
    {
        list.clear();
    }
    
    @Override
    public void stopRequest()
    {
        httpHanlder.stop();
    }
    
}
