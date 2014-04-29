/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-1 上午10:42:31 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.cinema.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.MyVideoShareVO;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetMyVideoShareLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    public MyVideoShareVO vo;
    
    private static GetMyVideoShareLogic ins;
    
    public static synchronized GetMyVideoShareLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetMyVideoShareLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetMyShareList()
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        
        fixRequestParams(params,
                serviceInfo,
                "findPersonShareMedia",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findPersonShareMedia",
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.MEDIA_MY_SHARE,
                this,
                false,
                HttpAction.url);
        
    }
    
    public void handleGetCinemaDetailData(ServiceResponse response)
    {
        if (response.getBody()
                .getResult()
                .equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            if (response.getBody().getParamsString().length() > 0)
            {
                vo = new Gson().fromJson(response.getBody().getParamsString(),
                        MyVideoShareVO.class);
                
            }
            handler.sendEmptyMessage(HttpAction.MEDIA_MY_SHARE);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
            case RequestId.MEDIA_MY_SHARE:
                handleGetCinemaDetailData(serviceRes);
                break;
        
        }
        
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
    
    public void stopRequest()
    {
        if (null != httpHanlder)
        {
            httpHanlder.stop();
        }
    }
}
