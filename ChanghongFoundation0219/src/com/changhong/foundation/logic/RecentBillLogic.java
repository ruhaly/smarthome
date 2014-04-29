/**
 * BillLogic.java
 * com.pactera.ch_bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-5 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.foundation.baseapi.HttpAction;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.JsonParse;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.baseapi.RequestId;
import com.changhong.foundation.baseapi.ResultCode;
import com.changhong.foundation.entity.BillInfo;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * ClassName:BillLogic Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-5 下午2:16:03
 */
public class RecentBillLogic extends SuperLogic
{
    
    public List<BillInfo> rList = new ArrayList<BillInfo>();
    
    public HttpHandler<String> httpHanlder;
    
    private static RecentBillLogic ins;
    
    public static synchronized RecentBillLogic getInstance()
    {
        if (null == ins)
        {
            ins = new RecentBillLogic();
        }
        return ins;
    }
    
    public void reqeustRecentBills(String account, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("ACCOUNT", account);
        serviceInfo.put("QueryType", "1");
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GETRECORDS,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GETRECORDS,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.RECENT_BILL_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
        
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
            case RequestId.RECENT_BILL_REQUESTID:
            {
                handleRecentBillsData(serviceRes);
                break;
            }
        }
        
    }
    
    public void handleRecentBillsData(ServiceResponse response)
    {
        
        if (response.getBody().getResult().equals(ResultCode.RESULT_SUCCESS))
        {
            rList = JsonParse.parseRecentBillRes(response.getBody()
                    .getParamsString());
            if (null != rList)
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_RECENT_BILL_SUCCESS);
            }
            else
            {
                handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
            }
        }
        else if (response.getBody().getResult().equals("0"))
        {
            if (null != rList)
            {
                rList.clear();
            }
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_RECENT_BILL_SUCCESS);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
        
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    @Override
    public void clear()
    {
        rList.clear();
    }
    
    @Override
    public void stopRequest()
    {
        if (null != httpHanlder)
        {
            httpHanlder.stop();
        }
    }
    
}
