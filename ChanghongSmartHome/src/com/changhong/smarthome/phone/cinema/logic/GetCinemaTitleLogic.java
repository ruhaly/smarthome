package com.changhong.smarthome.phone.cinema.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.entry.CinemaTitle;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetCinemaTitleLogic extends SuperLogic
{
    public static final String TAG = "GetCinemaTitleLogic";
    HttpHandler<String> httpHanlder;
    
    public ArrayList<CinemaTitle> cinemaTitleList;
    
    private static GetCinemaTitleLogic ins;
    
    public static synchronized GetCinemaTitleLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetCinemaTitleLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetCinemaTitleList(Pager pager,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        serviceInfo.put("pager", pager);
        //serviceInfo.put("adfasf",  "sdfsadfasdf"); 
        
        fixRequestParams(params, serviceInfo, "findMediaColumn","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaColumn",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.CINEMATITLE_GET,
                this,
                false,
                HttpAction.url);
        
    }
   
    
    public void handleGetCinemaTitleData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            //给对象赋值
            cinemaTitleList = josonparseCinemaTitle(response.getBody()
                    .getParamsString());
            handler.sendEmptyMessage(HttpAction.CINEMATITLE_SUCCESS_GET);
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
            case RequestId.CINEMATITLE_GET:
                handleGetCinemaTitleData(serviceRes);
                break;
        
        }
        
    }
    
    //给列表排序
    /*private ArrayList<ServicesItem> sout(ArrayList<ServicesItem> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            services_bxList.add(list.get(i));
            services_bxList.remove(i);
        }
        return services_bxList;
    }*/
    
    //解析数据
    public static ArrayList<CinemaTitle> josonparseCinemaTitle(String response)
    {
        ArrayList<CinemaTitle> list_cinematitle = new ArrayList<CinemaTitle>();
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
                    CinemaTitle cinemaTitle = new CinemaTitle();
                    jobObject = joba.getJSONObject(i);
                    System.out.println("获取数据到了数据");
                    
                    cinemaTitle.setId(Integer.parseInt(jobObject.optString("id")));
                    cinemaTitle.setColumnName(jobObject.optString("columnName"));
                    cinemaTitle.setOrderSeq(Integer.parseInt(jobObject.optString("orderSeq")));
                    cinemaTitle.setPicUrl(jobObject.optString("picUrl"));
                    cinemaTitle.setUpdateCount(Integer.parseInt(jobObject.optString("updateCount")));
                    cinemaTitle.setQueryType(Integer.parseInt(jobObject.optString("queryType")));

                    list_cinematitle.add(cinemaTitle);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list_cinematitle;
        
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
