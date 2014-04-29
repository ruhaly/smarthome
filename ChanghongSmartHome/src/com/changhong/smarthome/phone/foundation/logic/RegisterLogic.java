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

import com.changhong.sdk.baseapi.EncryptionUtils;
import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.entity.User;
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
public class RegisterLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private static RegisterLogic ins;
    
    public User user = new User();
    
    public static synchronized RegisterLogic getInstance()
    {
        if (null == ins)
        {
            ins = new RegisterLogic();
        }
        return ins;
    }
    
    public void requestGetValicode(String phone, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("phone", phone);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_VALICODE_FOR_REGISTER,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_VALICODE_FOR_REGISTER,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.UPDATE_PERSON_INFO_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
        
    }
    
    public void requestRegister(String phone, String valicode, String pwd,
            String sex, String communityId, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("mobile", phone);
        serviceInfo.put("valicode", valicode);
        serviceInfo.put("userPwd", EncryptionUtils.MD5(pwd));
        serviceInfo.put("sex", sex);
        serviceInfo.put("communityId", communityId);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_REGISTER,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_REGISTER,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.REGISTER_REQUESTID,
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
            case RequestId.REGISTER_REQUESTID:
            {
                handleRegisterData(serviceRes);
                break;
            }
            case RequestId.UPDATE_PERSON_INFO_REQUESTID:
            {
                handleValicodeData(serviceRes);
                break;
            }
            default:
                break;
        }
        
    }
    
    public void handleRegisterData(ServiceResponse response)
    {
        
        try
        {
            if (response.getBody()
                    .getResult()
                    .equals(ResultCode.RESULT_SUCCESS))
            {
                user = JsonParse.parseRegisterRes(response.getBody()
                        .getParamsString());
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_REGISTER_SUCCESS);
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
    
    public void handleValicodeData(ServiceResponse response)
    {
        try
        {
            if (response.getBody()
                    .getResult()
                    .equals(ResultCode.RESULT_SUCCESS))
            {
                user = JsonParse.parseLoginRes(response.getBody()
                        .getParamsString());
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_REGISTER_SUCCESS);
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
