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
import com.changhong.smarthome.phone.cinema.entry.MovieSource;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetMovieResourceLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    public ArrayList<MovieSource> movieSourceList;
    
    private static GetMovieResourceLogic ins;
    
    public static synchronized GetMovieResourceLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetMovieResourceLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetMovieSourceList(long contentId,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("contentId", contentId);
        //serviceInfo.put("adfasf",  "sdfsadfasdf"); 
        
        fixRequestParams(params, serviceInfo, "findMediaContentSrc","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaContentSrc",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIESOURCE_GET,
                this,
                false,
                HttpAction.url);
        
    }
   
    
    public void handleGetMovieSourceData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            movieSourceList = josonparseMovieSource(response.getBody()
                    .getParamsString());
            handler.sendEmptyMessage(HttpAction.MOVIERESOURCE_SUCCESS_GET);
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
            case RequestId.MOVIESOURCE_GET:
                handleGetMovieSourceData(serviceRes);
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
    public static ArrayList<MovieSource> josonparseMovieSource(String response)
    {
        ArrayList<MovieSource> list_MovieSource = new ArrayList<MovieSource>();
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
                    MovieSource movieSource = new MovieSource();
                    jobObject = joba.getJSONObject(i);
                    System.out.println("获取数据到了数据");
                    
                    movieSource.setId(jobObject.optInt("id"));
                    movieSource.setName(jobObject.optString("name"));
                    movieSource.setSourcePicUrl(jobObject.optString("sourcePicURL"));
                    list_MovieSource.add(movieSource);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list_MovieSource;
        
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
