/**
 * IntegralLogic.java
 * com.pactera.ch_bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-6 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.entity.BaseAccountInfo;
import com.changhong.sdk.entity.Pager;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.baseapi.HttpAction;
import com.changhong.smarthome.phone.foundation.baseapi.JsonParse;
import com.changhong.smarthome.phone.foundation.baseapi.MsgWhat;
import com.changhong.smarthome.phone.foundation.baseapi.RequestId;
import com.changhong.smarthome.phone.foundation.baseapi.ResultCode;
import com.changhong.smarthome.phone.foundation.bean.Integral;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * ClassName:IntegralLogic Function: TODO ADD FUNCTION
 * 
 * @author hanliangru
 * @version
 * @since Ver 1.1
 * @Date 2013-12-6 上午10:49:52
 */
public class IntegralLogic extends SuperLogic
{
    
    public List<Integral> list = new ArrayList<Integral>();
    
    public String startTime = "";
    
    public String endTime = "";
    
    private static IntegralLogic ins;
    
    private HttpHandler<String> httpHanlder;
    
    private static final int PAGENUM = 10;
    
    private int currentPage = 1;
    
    public static synchronized IntegralLogic getInstance()
    {
        if (null == ins)
        {
            ins = new IntegralLogic();
        }
        return ins;
    }
    
    public void requestIntegralList(BaseAccountInfo baseAccountInfo,
            int integral, HttpUtils httpUtils)
    {
        Pager pager = new Pager();
        pager.setPageId(currentPage);
        pager.setPageSize(PAGENUM);
        
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("accountInfo", baseAccountInfo);
        serviceInfo.put("integral", integral);
        serviceInfo.put("pager", pager);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_INTEGRALEXCHANGE,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_INTEGRALEXCHANGE,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.INTEGRAL_REQUESTID,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        
        switch (requestId)
        {
            case RequestId.INTEGRAL_REQUESTID:
            {
                handleIntegralData(serviceRes);
                break;
            }
            
            default:
                break;
        }
        
    }
    
    private void handleIntegralData(ServiceResponse response)
    {
        
        // TODO Auto-generated method stub
        if (response.getBody()
                .getResult()
                .equals(ResultCode.RESULT_SUCCESS_CBS))
        {
            List<Integral> tempList = JsonParse.parseIntegralListRes(response.getBody()
                    .getParamsString());
            if (null != tempList)
            {
                list.addAll(tempList);
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_INTEGRAL_LIST_SUCCESS);
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
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    @Override
    public void clear()
    {
        if (null != list)
        {
            list.clear();
        }
    }
    
    @Override
    public void stopRequest()
    {
        
        if (httpHanlder != null)
        {
            httpHanlder.stop();
        }
        
    }
    
}
