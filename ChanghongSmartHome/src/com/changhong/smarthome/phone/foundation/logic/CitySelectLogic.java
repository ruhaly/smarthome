package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.changhong.smarthome.phone.foundation.bean.Address;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class CitySelectLogic extends SuperLogic
{
    
    public List<Address> cityList = new ArrayList<Address>();
    
    public HttpHandler<String> httpHanlder;
    
    public static CitySelectLogic ins;
    
    //当前位置
    public Address address = new Address();
    
    public static synchronized CitySelectLogic getInstance()
    {
        if (null == ins)
        {
            ins = new CitySelectLogic();
        }
        return ins;
    }
    
    /**
     * 获取市
     * [功能详细描述]
     * @param httpUtils
     */
    public void requestCity(HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        fixRequestParams(params,
                serviceInfo,
                HttpAction.ACTION_GET_CITY,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.ACTION_GET_CITY,
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.REGISTER_CITY_REQUESTID,
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
            case RequestId.REGISTER_CITY_REQUESTID:
            {
                handleGetCityData(serviceRes);
                break;
            }
            default:
                break;
        }
    }
    
    public List<Address> handleGetCityData(ServiceResponse response)
    {
        if (ResultCode.RESULT_SUCCESS.equals(response.getBody().getResult()))
        {
            List<Address> tempList = JsonParse.parseCityList(response.getBody()
                    .getParamsString());
            if (null != tempList)
            {
                cityList = tempList;
                handler.sendEmptyMessage(MsgWhat.MSGWHAT_GET_PROVINCE_SUCCESS);
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
        return cityList;
    }
    
    @Override
    public void clear()
    {
        cityList.clear();
    }
    
    @Override
    public void stopRequest()
    {
        httpHanlder.stop();
    }
    
}
