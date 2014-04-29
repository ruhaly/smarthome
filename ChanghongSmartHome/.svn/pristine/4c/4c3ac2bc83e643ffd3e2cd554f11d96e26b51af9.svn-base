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
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * @author yang
 *获取电影详情的logic
 */
public class GetCinemaDetailLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    public ArrayList<Cinema> cinemaDetailList;
    
    private static GetCinemaDetailLogic ins;
    
    public static synchronized GetCinemaDetailLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetCinemaDetailLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetCinemaDetailList(int columnId,String type1,Pager pager,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        serviceInfo.put("columnId", columnId);
        serviceInfo.put("type1", type1);
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        serviceInfo.put("pager", pager);
        
        fixRequestParams(params, serviceInfo, "findMediaContent","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaContent",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.CINEMA_GET,
                this,
                false, HttpAction.url);
    }
    
    public void handleGetCinemaDetailData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            cinemaDetailList = josonparseCinemaDetail(response.getBody().getParamsString());
            handler.sendEmptyMessage(HttpAction.CINEMADETAIL_SUCCESS_GET);
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
            case RequestId.CINEMA_GET:
                handleGetCinemaDetailData(serviceRes);
                break;
        
        }
        
    }
    
    
    //解析数据
    public static ArrayList<Cinema> josonparseCinemaDetail(String response)
    {
        ArrayList<Cinema> list_cinemaDetail = new ArrayList<Cinema>();
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
                    Cinema cinema = new Cinema();
                    jobObject = joba.getJSONObject(i);
                    System.out.println("获取数据到了数据");
                    
                    cinema.setId(Integer.parseInt(jobObject.optString("id")));
                    cinema.setPrice(jobObject.optString("price"));
                    cinema.setContentName(jobObject.optString("contentName"));
                    cinema.setPicUrl(jobObject.optString("picUrl"));
                    cinema.setContentScore(jobObject.optString("contenScore"));
                    //System.out.println(jobObject.optDouble("contentScore"));
                   // cinema.setContentScore(jobObject.getDouble("contentScore"));
                   // cinema.setContentScore(Double.parseDouble(jobObject.optString("contentScore")));
                    list_cinemaDetail.add(cinema);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list_cinemaDetail;
        
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
