package com.changhong.smarthome.phone.property.logic;

import io.vov.vitamio.utils.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.property.entry.ExchangeHisList;
import com.changhong.smarthome.phone.property.entry.UserIntegrationExchangeHistoryVO;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * <功能详细描述>
 *
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月22日 下午2:52:17
 */
public class UserIntegrationLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private static UserIntegrationLogic ins;
    
    public UserIntegrationExchangeHistoryVO respVO;
    
    public static synchronized UserIntegrationLogic getInstance()
    {
        if (null == ins)
        {
            ins = new UserIntegrationLogic();
        }
        return ins;
    }
    
    public void requestIntegrationInfo(String request)
    {
        RequestParams params = new RequestParams();
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        
        serviceInfo.put("accountInfo", LoginLogic.getInstance()
                .getBaseAccountInfo());
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.USER_INTEGRATION_CHANGE,
                "sc",
                "sc",
                "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.USER_INTEGRATION_CHANGE,
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.My_INTEGRATION_EXCHANGELIST,
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
            
            case RequestId.My_INTEGRATION_EXCHANGELIST:
                if (response.getBody().getResult().equals(HttpAction.STATUS_OK))
                {
                    //给对象赋值
                    if (response.getBody().getParamsString().length() > 0)
                    {
                        respVO = josonParseMyExchangeIntegrationList(response.getBody()
                                .getParamsString());
                        
                        for (int i = 0; i < respVO.getExchangeHisList().size(); i++)
                        {
                            respVO.getExchangeHisList()
                                    .get(i)
                                    .setGiftPicUrl(CHUtils.handlerImageURL(respVO.getExchangeHisList()
                                            .get(i)
                                            .getGiftPicUrl(),
                                            HttpUrl.URL_CBS));
                            System.out.println("---------------图片地址："
                                    + respVO.getExchangeHisList()
                                            .get(i)
                                            .getGiftPicUrl()
                                    + "----------------------------");
                        }
                    }
                    handler.sendEmptyMessage(HttpAction.MY_INTEGRATION_EXCHANGE_SUCCESS_GET);
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
    
    //解析数据
    private static UserIntegrationExchangeHistoryVO josonParseMyExchangeIntegrationList(
            String response)
    {
        // TODO Auto-generated method stub
        UserIntegrationExchangeHistoryVO userExchange = new UserIntegrationExchangeHistoryVO();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        try
        {
            JSONObject job = new JSONObject(response);
            
            if (null != job)
            {
                System.out.println("获取数据到了数据");
                
                userExchange.setEhResult(job.optInt("ehResult"));
                
                List<ExchangeHisList> exchangeHisList = new ArrayList<ExchangeHisList>();
                ExchangeHisList exchangeHis = null;
                JSONArray array = job.optJSONArray("exchangeHisList");
                for (int i = 0; i < array.length(); i++)
                {
                    exchangeHis = new ExchangeHisList();
                    
                    JSONObject obj = array.getJSONObject(i);
                    
                    exchangeHis.setBeginDate(obj.optString("beginDate"));
                    exchangeHis.setEndDate(obj.optString("endDate"));
                    exchangeHis.setGiftPicUrl(obj.optString("giftPicUrl"));
                    exchangeHis.setPoint(obj.optInt("point"));
                    exchangeHis.setName(obj.optString("name"));
                    
                    exchangeHisList.add(exchangeHis);
                }
                userExchange.setExchangeHisList(exchangeHisList);
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userExchange;
    }
    
    public void stopRequest()
    {
        if (null != httpHanlder)
        {
            httpHanlder.stop();
        }
    }
    
}
