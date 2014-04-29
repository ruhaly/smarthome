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
import com.changhong.smarthome.phone.foundation.baseapi.MsgWhat;
import com.changhong.smarthome.phone.foundation.baseapi.RequestId;
import com.changhong.smarthome.phone.foundation.baseapi.ResultCode;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class AddCommunityLogic extends SuperLogic
{
    public List<Community> commList = new ArrayList<Community>();
    
    public HttpHandler<String> httpHanlder;
    
    public static AddCommunityLogic ins;
    
    public static synchronized AddCommunityLogic getInstance()
    {
        if (null == ins)
        {
            ins = new AddCommunityLogic();
        }
        return ins;
    }
    
    /**
     * 添加小区
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestAddCommunity(String userId, String communityId,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", userId);
        serviceInfo.put("communityId", communityId);
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_ADD_COMMUNITY_MANAGE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_ADD_COMMUNITY_MANAGE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.COMMUNITY_ADD_REQUESTID,
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
            case RequestId.COMMUNITY_ADD_REQUESTID:
            {
                if (ResultCode.RESULT_SUCCESS.equals(serviceRes.getBody().getResult()))
                {
                        handler.sendEmptyMessage(MsgWhat.MSGWHAT_ADD_COMMUNITY_SUCCESS);
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
    }
    
    @Override
    public void stopRequest()
    {
    }
    
}
