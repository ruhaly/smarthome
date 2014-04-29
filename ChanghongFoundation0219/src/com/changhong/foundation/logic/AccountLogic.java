/**
 * AccountLogic.java
 * com.pactera.ch_bedframe.logic
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-4 		b
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
import com.changhong.foundation.entity.PayInfo;
import com.changhong.sdk.entity.Pager;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * 
 * 
 * 项目名称：CH_foundation 类名称：AccountLogic 类描述： 创建人：b 创建时间：2013年12月24日 下午2:34:36
 * 修改人：b 修改时间：2013年12月24日 下午2:34:36 修改备注：
 * 
 * @version
 * 
 */
public class AccountLogic extends SuperLogic
{
    
    public List<PayInfo> plList = new ArrayList<PayInfo>();
    
    public HttpHandler<String> httpHanlder;
    
    public HttpHandler<String> httpHanlder1;
    
    private static AccountLogic ins;
    
    // 余额
    public String amount;
    
    public static synchronized AccountLogic getInstance()
    {
        if (null == ins)
        {
            ins = new AccountLogic();
        }
        return ins;
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        
        switch (requestId)
        {
            case RequestId.PAY_INFOS_REQUESTID:
            {
                handlePayInfosData(serviceRes);
                break;
            }
            case RequestId.PAY_TO_SERVER_REQUESTID:
            {
                handlePayToServerData(serviceRes);
                break;
            }
            
            default:
                break;
        }
        
    }
    
    @Override
    public void clear()
    {
        plList.clear();
    }
    
    @Override
    public void stopRequest()
    {
        if (null != httpHanlder)
        {
            httpHanlder.stop();
        }
    }
    
    public void stopRequest1()
    {
        if (null != httpHanlder1)
        {
            httpHanlder1.stop();
        }
    }
    
    /**
     * 
      * requestPayInfos(这里用一句话描述这个方法的作用)  * (这里描述这个方法适用条件 – 可选)  * @param
     * account  * @param httpUtils  *void  * @exception  * @since  1.0.0
     */
    public void requestPayInfos(String account, String familyAccount,
            HttpUtils httpUtils)
    {
        Pager pager = new Pager();
        pager.setPageId(1);
        pager.setPageSize(10);
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("ACCOUNT", account);
        serviceInfo.put("familyAccount", familyAccount);
        serviceInfo.put("pager", pager);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_PAY_INFOS,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_PAY_INFOS,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.PAY_INFOS_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
        
    }
    
    public void requestPaySuccessToSever(String account, String id,
            HttpUtils httpUtils)
    {
        
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("ACCOUNT", account);
        serviceInfo.put("DUNNINGID", id);
        serviceInfo.put("payType", "1");
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_PAYSUCCESS_TO_SEVER,
                "sc",
                "sc",
                "4100");
        httpHanlder1 = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_PAYSUCCESS_TO_SEVER,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.PAY_TO_SERVER_REQUESTID,
                this,
                false,
                HttpUrl.URL_OSS);
        
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        // if (error.getExceptionCode() == 0 || error.getExceptionCode() == 404)
        // {
        // }
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    public void handlePayInfosData(ServiceResponse response)
    {
        
        if (response.getBody().getResult().equals(ResultCode.RESULT_SUCCESS))
        {
            plList = JsonParse.parsePayInfoListRes(response.getBody()
                    .getParamsString());
            if (null != plList)
            {
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_PAY_INFO_SUCCESS);
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
    
    /**
     * 
     * 
     * handlePayToServerData(这里用一句话描述这个方法的作用)
     * 
     * TODO(这里描述这个方法适用条件 – 可选)
     * 
     * @param name
     * 
     * @param @return 设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since 2013年12月24日
     */
    public void handlePayToServerData(ServiceResponse response)
    {
        
        if (response.getBody().getResult().equals(ResultCode.RESULT_SUCCESS))
        {
            handler.sendEmptyMessage(MsgWhat.MSGWHAT_PAY_TO_SERVER_SUCCESS);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
        
    }
    
}
