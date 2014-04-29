package com.changhong.smarthome.phone.property.logic;

import io.vov.vitamio.utils.Log;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.logic.BillLogic;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.property.entry.ExchangeOBJ;
import com.changhong.smarthome.phone.property.entry.ExchangeVO;
import com.changhong.smarthome.phone.property.entry.IntegrationVO;
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
 * @date 2014年4月22日 下午2:52:08
 */
public class IntegrationLogic extends SuperLogic
{
    
    HttpHandler<String> httpHanlder;
    
    private static IntegrationLogic ins;
    
    public IntegrationVO respVO;
    
    public ExchangeOBJ exchangeOBJ;
    
    public static synchronized IntegrationLogic getInstance()
    {
        if (null == ins)
        {
            ins = new IntegrationLogic();
        }
        return ins;
    }
    
    /**
     * @param request
     */
    public void requestIntegrationInfo(String request)
    {
        RequestParams params = new RequestParams();
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        
        serviceInfo.put("accountInfo", LoginLogic.getInstance()
                .getBaseAccountInfo());
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.INTEGRATION_EXCHANGE_MAIN,
                "sc",
                "sc",
                "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.INTEGRATION_EXCHANGE_MAIN,
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.INTEGRATION_MAIN,
                this,
                false,
                HttpUrl.URL_CBS);
        Log.i("--------" + "请求已发送"+HttpUrl.URL_CBS + "----------");
    }
    
    public void requestExchangeInfo(ExchangeVO exchangeVO)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        
        serviceInfo.put("accountInfo", LoginLogic.getInstance()
                .getBaseAccountInfo());
        serviceInfo.put("giftId", exchangeVO.getId());
        serviceInfo.put("point", exchangeVO.getPoint());
        fixRequestParams(params,
                serviceInfo,
                HttpAction.GIFT_EXCHANGE,
                "sc",
                "sc",
                "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.GIFT_EXCHANGE,
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.EXCHANGE_CODE,
                this,
                false,
                HttpUrl.URL_CBS);
        Log.i("--------" + "请求已发送" + "----------");
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
            
            case RequestId.INTEGRATION_MAIN:
                if (response.getBody().getResult().equals(HttpAction.STATUS_OK))
                {
                    // 给对象赋值
                    if (response.getBody().getParamsString().length() > 0)
                    {
                        respVO = new Gson().fromJson(response.getBody()
                                .getParamsString(), IntegrationVO.class);
                        for (int i = 0; i < respVO.getPointGiftList().size(); i++)
                        {
                            respVO.getPointGiftList()
                                    .get(i)
                                    .setGiftPicUrl(CHUtils.handlerImageURL(respVO.getPointGiftList()
                                            .get(i)
                                            .getGiftPicUrl(),
                                            HttpUrl.URL_CBS));
                            System.out.println("---------------图片地址："
                                    + respVO.getPointGiftList()
                                            .get(i)
                                            .getGiftPicUrl()
                                    + "----------------------------");
                        }
                    }
                    
                    handler.sendEmptyMessage(HttpAction.INTEGRATION_MAIN_SUCCESSFUL_GET);
                }
                else
                {
                    handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
                }
                break;
            
            case RequestId.EXCHANGE_CODE:
                if (response.getBody().getResult().equals(HttpAction.STATUS_OK))
                {
                    
                    if (response.getBody().getParamsString().length() > 0)
                    {
                        exchangeOBJ = new Gson().fromJson(response.getBody()
                                .getParamsString(), ExchangeOBJ.class);
                    }
                    
                    handler.sendEmptyMessage(HttpAction.GIFT_EXCHANGE_SUCCESS_GET);
                    
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
