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
import com.changhong.smarthome.phone.foundation.bean.MemberManage;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class NewMemberLogic extends SuperLogic
{
    
    public MemberManage mm = new MemberManage();
    
    public HttpHandler<String> httpHanlder;
    
    public static NewMemberLogic ins;
    
    public static synchronized NewMemberLogic getInstance()
    {
        if (null == ins)
        {
            ins = new NewMemberLogic();
        }
        return ins;
    }
    
    /**
     * 获取新成员列表
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestNewMembers(String userId, String communityId,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", userId);
        serviceInfo.put("communityId", communityId);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_NEW_MEMBER,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_NEW_MEMBER,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.NEW_MEMBER_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
    }
    
    public void requestAuditMember(String id, String applyUserId,
            String communityId, String houseId, String approveUserId,
            String processType, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("id", id);
        serviceInfo.put("applyUserId", applyUserId);
        serviceInfo.put("communityId", communityId);
        serviceInfo.put("houseId", houseId);
        serviceInfo.put("approveUserId", approveUserId);
        serviceInfo.put("processType", processType);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_AUDIT_MEMBER,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_AUDIT_MEMBER,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.AUDIT_MEMBER_REQUESTID,
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
            case RequestId.NEW_MEMBER_REQUESTID:
            {
                handleNewMembersData(serviceRes);
                break;
            }
            case RequestId.AUDIT_MEMBER_REQUESTID:
            {
                handleAuditMemberData(serviceRes);
                break;
            }
            default:
                break;
        }
    }
    
    public void handleNewMembersData(ServiceResponse response)
    {
        
        try
        {
            if (response.getBody()
                    .getResult()
                    .equals(ResultCode.RESULT_SUCCESS))
            {
                mm = JsonParse.parseNewMembers(response.getBody()
                        .getParamsString());
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_NEW_MEMBER_SUCCESS);
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
    
    public void handleAuditMemberData(ServiceResponse response)
    {
        
        try
        {
            if (response.getBody()
                    .getResult()
                    .equals(ResultCode.RESULT_SUCCESS))
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_AUDIT_MEMBER_SUCCESS);
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
        mm = new MemberManage();
    }
    
    @Override
    public void stopRequest()
    {
        httpHanlder.stop();
    }
    
}
