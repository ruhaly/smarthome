/**
 * LoginLogic.java
 * com.pactera.ch.bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-12 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

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

/**
 * ClassName:LoginLogic Function: TODO ADD FUNCTION
 * 
 * @author hanliangru
 * @version
 * @since Ver 1.1
 * @Date 2013-12-12 下午2:32:07
 */
public class ApplyJoinLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private static ApplyJoinLogic ins;
    
    public static synchronized ApplyJoinLogic getInstance()
    {
        if (null == ins)
        {
            ins = new ApplyJoinLogic();
        }
        return ins;
    }
    
    public void requestApplyJoin(String userId, String phone,
            String communityId, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", userId);
        serviceInfo.put("mobile", phone);
        serviceInfo.put("communityId", communityId);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_APPLY_JOIN,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_APPLY_JOIN,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.APPLY_JOIN_REQUESTID,
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
    public void clear()
    {
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
            case RequestId.APPLY_JOIN_REQUESTID:
            {
                handleApplyJoinData(serviceRes);
                break;
            }
            default:
                break;
        }
        
    }
    
    public void handleApplyJoinData(ServiceResponse response)
    {
        
        try
        {
            if (response.getBody()
                    .getResult()
                    .equals(ResultCode.RESULT_SUCCESS))
            {
                boolean b = JsonParse.parseApplyJoinRes(response.getBody()
                        .getParamsString());
                handler.obtainMessage(MsgWhat.MSGWHAT_APPLY_JOIN_SUCCESS, b)
                        .sendToTarget();
            }
            else
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_APPLY_JOIN_FAILED);
            }
        }
        catch (Exception e)
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
        
    }
    
    public void stopRequest()
    {
        if (null != httpHanlder)
        {
            httpHanlder.stop();
        }
    }
}
