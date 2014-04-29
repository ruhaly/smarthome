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

import com.changhong.foundation.baseapi.EncryptionUtils;
import com.changhong.foundation.baseapi.HttpAction;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.baseapi.RequestId;
import com.changhong.foundation.baseapi.ResultCode;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * 
 * RegisterLogic
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年1月24日]
 */
public class FindPwdLogic extends SuperLogic
{
    private HttpHandler<String> httpHanlder;
    
    private static FindPwdLogic ins;
    
    public static synchronized FindPwdLogic getInstance()
    {
        if (null == ins)
        {
            ins = new FindPwdLogic();
        }
        return ins;
    }
    
    public void requestFindPwd(String phoneNumber, String authCode,
            String idCard, String newPwd, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("phoneNumber", phoneNumber);
        serviceInfo.put("authCode", authCode);
        serviceInfo.put("idCard", idCard);
        serviceInfo.put("newPwd", newPwd);
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_FINDPWD,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_FINDPWD,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.FINDPWD_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
    }
    
    public void requestGetPwdValicode(String phoneNumber, String type,
            String idCard, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("phoneNumber", phoneNumber);
        serviceInfo.put("type", type);
        serviceInfo.put("idCard", idCard);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_FINDPWD_VALICODE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_FINDPWD_VALICODE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.FINDPWD_VALICODE_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
    }
    
    public void requestModifyPwd(String uid, String oldPwd, String newPwd,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", uid);
        serviceInfo.put("CurrentPwd", EncryptionUtils.MD5(oldPwd));
        serviceInfo.put("newPwd", EncryptionUtils.MD5(newPwd));
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_MODIFYPWD,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_MODIFYPWD,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MODIFYPWD_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
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
            case RequestId.FINDPWD_REQUESTID:
            {
                handleFindPwdData(serviceRes);
                break;
            }
            case RequestId.FINDPWD_VALICODE_REQUESTID:
            {
                handleGetPwdValicodeData(serviceRes);
                break;
            }
            case RequestId.MODIFYPWD_REQUESTID:
            {
                handleModifyPwdData(serviceRes);
                break;
            }
            default:
                break;
        }
        
    }
    
    public void handleFindPwdData(ServiceResponse response)
    {
        if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_FINDPWD_SUCCESS);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
        
    }
    
    public void handleModifyPwdData(ServiceResponse response)
    {
        if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_MODIFYPWD_SUCCESS);
        }
        else if (ResultCode.RESULT_OLDPWD_ERROR.equals(response.getBody()
                .getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_MODIFYPWD_OLDPWD_ERROR);
        }
        else if (ResultCode.RESULT_PWD_MODIFY_ERROR.equals(response.getBody()
                .getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_MODIFYPWD_ERROR);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
        
    }
    
    public void handleGetPwdValicodeData(ServiceResponse response)
    {
        if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_FINDPWD_GETVALICODE_SUCCESS);
        }
        else if (ResultCode.RESULT_USER_NOT_EXIT.equals(response.getBody()
                .getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_USER_NOT_EXIT);
        }
        else if (ResultCode.RESULT_PARAMS_ERROR.equals(response.getBody()
                .getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_PARAMS_ERROR);
        }
        else if (ResultCode.RESULT_VALICODE_ERROR.equals(response.getBody()
                .getResult()))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_VALICODE_ERROR);
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
