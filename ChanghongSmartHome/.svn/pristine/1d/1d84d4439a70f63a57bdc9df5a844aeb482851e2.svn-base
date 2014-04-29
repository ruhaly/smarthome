package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream; 
import java.util.HashMap;
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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetTwoDimensionCodeLogic extends SuperLogic
{     
    public HttpHandler<String> httpHanlder;
    
    public static GetTwoDimensionCodeLogic ins;
    
    public String path = null;
    
    public static synchronized GetTwoDimensionCodeLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetTwoDimensionCodeLogic();
        }
        return ins;
    }
    
    /**
     * 获取小区
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestCommunity(int clientType,HttpUtils httpUtils)
    {   
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("clientType", clientType);

        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GET_TWODIMENSIONCODE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GET_TWODIMENSIONCODE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.TWODIMENSIONCODE_GET_REQUESTID,
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
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,InputStream is)
    {
        switch (requestId)
        {
            case RequestId.TWODIMENSIONCODE_GET_REQUESTID:
            {
                try
                {
                    handleGetCommunityData(serviceRes);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
    }
    
    public void handleGetCommunityData(ServiceResponse response) throws Exception
    {
        if (ResultCode.RESULT_SUCCESS_CBS.equals(response.getBody().getResult()))
        {
            String requestParth = JsonParse.parseTwoDimensionCode(response.getBody()
                    .getParamsString());
            if (null != requestParth)
            {
                path = requestParth; 
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_GET_TWODIMENSION_SUCCESS);
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
    
    @Override
    public void clear()
    {
    }
    
    @Override
    public void stopRequest()
    {
        httpHanlder.stop();
    }
    
}
