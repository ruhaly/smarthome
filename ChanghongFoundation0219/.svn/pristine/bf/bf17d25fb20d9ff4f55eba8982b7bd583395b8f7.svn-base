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

package com.changhong.foundation.logic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.baseapi.EncryptionUtils;
import com.changhong.foundation.baseapi.HttpAction;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.JsonParse;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.baseapi.RequestId;
import com.changhong.foundation.baseapi.ResultCode;
import com.changhong.foundation.entity.User;
import com.changhong.foundation.entity.VersionInfo;
import com.changhong.sdk.entity.BaseAccountInfo;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * ClassName:LoginLogic Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-12 下午2:32:07
 */
public class LoginLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private static LoginLogic ins;
    
    public VersionInfo vi = new VersionInfo();
    
    public User user = new User();
    
    public BaseAccountInfo baseAccountInfo = new BaseAccountInfo();
    
    public static synchronized LoginLogic getInstance()
    {
        if (null == ins)
        {
            ins = new LoginLogic();
            //            LoginLogic.getInstance().baseAccountInfo.setAccountId(ins.user.getAccount());
            //            LoginLogic.getInstance().baseAccountInfo.setUserId(ins.user.getUid());
            //            LoginLogic.getInstance().baseAccountInfo.setCommunityCode(ins.user.getCommuntyId());
            //            LoginLogic.getInstance().baseAccountInfo.setFlag(Constant.TERMINAL_TYPE);
            //            LoginLogic.getInstance().baseAccountInfo.setResolution("QHD");
            //            LoginLogic.getInstance().baseAccountInfo.setType("1");
        }
        return ins;
    }
    
    public void requestLogin(String account, String pwd, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("account", account);
        serviceInfo.put("password", EncryptionUtils.MD5(pwd));
        serviceInfo.put("clientType", Constant.CLIENTTYPE);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_LOGIN,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_LOGIN,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.LOGIN_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
        
    }
    
    public void requestCheckUpdate(HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("clientType", Constant.CLIENTTYPE);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_FOUNDATION_CHECK_UPDATE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_FOUNDATION_CHECK_UPDATE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.FOUNDATION_CHECK_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
        
    }
    
    @Override
    public void handleHttpResponse(String response, int rspCode, int requestId)
    {
        
    }
    
    @Override
    public void handleHttpResponse(String response, int requestId,
            InputStream is)
    {
    }
    
    public void handleLoginData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(ResultCode.RESULT_SUCCESS))
        {
            user = JsonParse.parseLoginRes(response.getBody().getParamsString());
            if (null != user)
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_LOGIN_SUCCESS);
            }
            else
            {
                handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
            }
        }
        else if (response.getBody()
                .getResult()
                .equals(ResultCode.RESULT_PWD_ERROR))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_PWD_ERROR);
        }
        else if (response.getBody()
                .getResult()
                .equals(ResultCode.RESULT_USER_NOT_EXIST))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_USER_NOT_EXIST);
        }
        else if (response.getBody()
                .getResult()
                .equals(ResultCode.RESULT_USER_NOT_ACTIVATE))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_USER_NOT_ACTIVATE);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleCheckUpdateData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(ResultCode.RESULT_SUCCESS))
        {
            vi = JsonParse.parseCheckUpdateRes(response.getBody()
                    .getParamsString());
            if (null != vi)
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_FOUNDATION_CHECK_UPDATE_SUCCESS);
            }
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        // if (error.getExceptionCode() == 0 || error.getExceptionCode() == 404)
        // {
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
        // }
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
            case RequestId.LOGIN_REQUESTID:
            {
                handleLoginData(serviceRes);
                break;
            }
            case RequestId.FOUNDATION_CHECK_REQUESTID:
            {
                handleCheckUpdateData(serviceRes);
                break;
            }
            default:
                break;
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
