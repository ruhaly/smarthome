package com.changhong.smarthome.phone.property.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.entity.Pager;
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

public class PayServiceLogic extends SuperLogic
{
    public List<Community> list = new ArrayList<Community>();
    
    public HttpHandler<String> httpHanlder;
    
    public static PayServiceLogic ins;
    
    public static synchronized PayServiceLogic getInstance()
    {
        if (null == ins)
        {
            ins = new PayServiceLogic();
        }
        return ins;
    }
    
    /**
     * 获取小区
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestCommunity(String userId, Pager pager, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", userId);
        serviceInfo.put("pager", pager);
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GET_COMMUNITY_MANAGE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GET_COMMUNITY_MANAGE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.COMMUNITY_GET_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
    }
    
    /**
     * 获取小区
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestChangeCommunity(String userId, String communityId,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", userId);
        serviceInfo.put("organId", communityId);
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_CHANGE_COMMUNITY,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_CHANGE_COMMUNITY,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.COMMUNITY_CHANGE_REQUESTID,
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
            case RequestId.COMMUNITY_GET_REQUESTID:
            {
                handleGetCommunityData(serviceRes);
                break;
            }
            case RequestId.COMMUNITY_CHANGE_REQUESTID:
            {
                handleChangeCommunityData(serviceRes);
                break;
            }
            default:
                break;
        }
    }
    
    public void handleGetCommunityData(ServiceResponse response)
    {
        if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
        {
            List<Community> tempList = JsonParse.parseCommunityList(response.getBody()
                    .getParamsString());
            if (null != tempList)
            {
               // LoginLogic.getInstance().communityList = tempList;
                list.addAll(0, tempList);
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_GET_COMMUNITY_SUCCESS);
            }
            else
            {
                handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
            }
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleChangeCommunityData(ServiceResponse response)
    {
        try
        {
            if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
            {
                String isexist = JsonParse.parseChangeCommunityRes(response.getBody()
                        .getParamsString());
                //0不需要认证1需要认证
               // LoginLogic.getInstance().user.setIsExist(isexist);
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_GET_COMMUNITY_SUCCESS);
            }
            else
            {
                handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
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
