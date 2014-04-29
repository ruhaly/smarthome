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
import com.changhong.smarthome.phone.cinema.callback.IHttpRequestCallback;
import com.changhong.smarthome.phone.cinema.entry.MediaOrderVO;
import com.changhong.smarthome.phone.cinema.entry.MovieSelects;
import com.changhong.smarthome.phone.cinema.entry.NewOrderResultVO;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetMovieSelectionsLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private IHttpRequestCallback callback;
    
    public MediaOrderVO mediaOrderVO;
    
    public NewOrderResultVO newOrderResultVO;
    
    public ArrayList<MovieSelects> movieSelectsList;
    
    private static GetMovieSelectionsLogic ins;
    
    public static synchronized GetMovieSelectionsLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetMovieSelectionsLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetMovieSelectsList(Long contentId, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("contentId", contentId);
        fixRequestParams(params,
                serviceInfo,
                "findMediaConentCollectionById",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaConentCollectionById",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIESELECTS_GET,
                this,
                false,
                HttpAction.url);
        
    }
    
    public void addMoviePlayTimes(Long contentId, Long mediaId,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("contentId", contentId);
        serviceInfo.put("mediaId", mediaId);
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        
        fixRequestParams(params,
                serviceInfo,
                "modifyMediaContentClick",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("modifyMediaContentClick",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIEPLAYTIMES_ADD,
                this,
                false,
                HttpAction.url);
        
    }
    
    public void handleGetMovieSelectsData(ServiceResponse response)
    {
        if (response.getBody()
                .getResult()
                .equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            movieSelectsList = josonparseMovieSelects(response.getBody()
                    .getParamsString());
            handler.sendEmptyMessage(HttpAction.MOVIESELECTS_SUCCESS_GET);
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
            case RequestId.MOVIESELECTS_GET:
                handleGetMovieSelectsData(serviceRes);
                break;
            case RequestId.MOVIEPLAYTIMES_ADD:
                handler.sendEmptyMessage(HttpAction.MOVIEPLAYTIMES_SUCCESS_ADD);
                break;
            case RequestId.MEDIA_ORDER_SITUATION:
                handleMediaOrderSituationData(serviceRes);
                break;
            case RequestId.MEDIA_NEW_ORDER:
                handleMediaNewOrderDataSituation(serviceRes);
                break;
        }
        
    }
    
    private void handleMediaOrderSituationData(ServiceResponse serviceRes)
    {
        String resp = null;
        if (serviceRes.getBody()
                .getResult()
                .equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            resp = serviceRes.getBody().getParamsString();
            if (resp != null && resp.length() > 0)
            {
                mediaOrderVO = new Gson().fromJson(resp, MediaOrderVO.class);
            }
            handler.sendEmptyMessage(HttpAction.MEDIA_ORDER_INFO_SUCCESS_GET);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
        
        callback.callback(resp);
    }
    
    private void handleMediaNewOrderDataSituation(ServiceResponse response)
    {
        if (response.getBody()
                .getResult()
                .equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            String resp = response.getBody().getParamsString();
            if (resp != null && resp.length() > 0)
            {
                newOrderResultVO = new Gson().fromJson(resp,
                        NewOrderResultVO.class);
            }
            //TODO 这里应该根据具体情况判断
            handler.sendEmptyMessage(HttpAction.NewOrderData_SUCCESS_GET);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    //解析数据
    public static ArrayList<MovieSelects> josonparseMovieSelects(String response)
    {
        ArrayList<MovieSelects> list_MovieSelects = new ArrayList<MovieSelects>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        try
        {
            
            JSONObject job = new JSONObject(response);
            JSONObject jobObject = null;
            if (null != job)
            {
                
                JSONArray joba = job.optJSONArray("dataList");
                for (int i = 0; i < joba.length(); i++)
                {
                    MovieSelects movieSelects = new MovieSelects();
                    jobObject = joba.getJSONObject(i);
                    System.out.println("获取数据到了数据");
                    
                    movieSelects.setId(jobObject.optInt("id"));
                    movieSelects.setSeqNum(jobObject.optInt("seqnum"));
                    movieSelects.setPlayUrl(jobObject.optString("playUrl"));
                    movieSelects.setMediaId(jobObject.optLong("mediaId"));
                    list_MovieSelects.add(movieSelects);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list_MovieSelects;
        
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
    
    /**
     * @param contentId
     * @param iHttpRequestCallback
     */
    //视频是否订购，订购情况
    public void requestVideoOrderSituation(long contentId,
            IHttpRequestCallback callback)
    {
        this.callback = callback;
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        serviceInfo.put("contentId", contentId);
        
        //        serviceInfo.put("serviceCode", "test");
        fixRequestParams(params,
                serviceInfo,
                HttpAction.MEDIA_ORDER_SITUATION,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.MEDIA_ORDER_SITUATION,
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.MEDIA_ORDER_SITUATION,//MEDIA_ORDER_COMMAND
                this,
                false,
                HttpAction.url);
    } //视频新订购
    
    public void requestVideoNewOrder(long contentId, long id_pay,
            String name_pay, float money_pay)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        serviceInfo.put("contentId", contentId);
        serviceInfo.put(HttpAction.strategyID, id_pay);
        serviceInfo.put("strategyName", name_pay);
        serviceInfo.put("money", money_pay);
        
        fixRequestParams(params,
                serviceInfo,
                HttpAction.MEDIA_NEW_ORDER,
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl(HttpAction.MEDIA_NEW_ORDER,
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.MEDIA_NEW_ORDER,//MEDIA_ORDER_COMMAND
                this,
                false,
                HttpAction.pay_test_url);
        
    }
}
