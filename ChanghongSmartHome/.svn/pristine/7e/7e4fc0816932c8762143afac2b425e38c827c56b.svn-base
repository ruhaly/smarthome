package com.changhong.smarthome.phone.cinema.logic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.entry.MovieDetail;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetMovieDetailLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    public MovieDetail movieDetail;
    
    private static GetMovieDetailLogic ins;
    
    public static synchronized GetMovieDetailLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetMovieDetailLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetMovieDetailList(long contentId,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("contentId", contentId);
        //serviceInfo.put("adfasf",  "sdfsadfasdf"); 
        
        fixRequestParams(params, serviceInfo, "findMediaContentDetailById","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaContentDetailById",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIEDETAIL_GET,
                this,
                false,
                HttpAction.url);
        
    }
   
    
    public void handleGetMovieDetailData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            movieDetail = josonparseMovieDetail(response.getBody()
                    .getParamsString());
            handler.sendEmptyMessage(HttpAction.MOVIEDETAIL_SUCCESS_GET);
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
            case RequestId.MOVIEDETAIL_GET:
                handleGetMovieDetailData(serviceRes);
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
    public static MovieDetail josonparseMovieDetail(String response)
    {
        MovieDetail movieDetail = new MovieDetail();
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
                    
                    movieDetail.setActor(job.optString("actor"));
                    movieDetail.setContentDesc(job.optString("contentDesc"));
                    movieDetail.setContentName(job.optString("contentName"));
                    movieDetail.setContentType(job.optString("contenttype"));
                    movieDetail.setDirector(job.optString("director"));
                    movieDetail.setLanguage(job.optString("language"));
                    movieDetail.setPublishTime(job.optString("publishTime"));
                    movieDetail.setRecommendationLevel(job.optInt("recommendationLevel"));
                    
                
                    
                
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return movieDetail;
        
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
