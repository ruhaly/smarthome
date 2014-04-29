package com.changhong.smarthome.phone.property.logic;

import io.vov.vitamio.utils.Log;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.logic.BillLogic;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.property.entry.IntegrationDetailVO;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.http.RequestId;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * <功能详细描述>
 *
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月22日 下午2:52:29
 */
public class IntegrationDetailLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private static IntegrationDetailLogic ins;
    
    public IntegrationDetailVO respVO;
    
    public static synchronized IntegrationDetailLogic getInstance()
    {
        if (null == ins)
        {
            ins = new IntegrationDetailLogic();
        }
        return ins;
    }
    
    public void requestIntegrationInfo(String request,int findFlag)
    {
        RequestParams params = new RequestParams();
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        
        serviceInfo.put("accountInfo", LoginLogic.getInstance().getBaseAccountInfo());
        serviceInfo.put("findFlag", findFlag);
        fixRequestParams(params,
                serviceInfo,
                HttpAction.INTEGRATION_DETAIL_CHANGE,
                "sc",
                "sc",
                "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.INTEGRATION_DETAIL_CHANGE,
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.INTEGRATION_DETAIL,
                this,
                false,
                HttpUrl.URL_CBS);
        Log.i("--------" + "请求已发送"+ "----------");
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        handler.sendEmptyMessage(SuperLogic.DATA_FORMAT_ERROR_MSGWHAT);
    }
    
    @Override
    public void clear()
    {
        
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse response, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
        
            case RequestId.INTEGRATION_DETAIL:
                if (response.getBody().getResult().equals(HttpAction.STATUS_OK))
                {
                    //给对象赋值
                    if (response.getBody().getParamsString().length() > 0)
                    {
                        respVO = new Gson().fromJson(response.getBody()
                                .getParamsString(), IntegrationDetailVO.class);
                    }
                    
                    handler.sendEmptyMessage(HttpAction.INTEGRATION_DETAIL_SUCCESS_GET);
                }
                else
                {
                    handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
                }
                break;
            
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
