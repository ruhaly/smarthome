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
import com.changhong.smarthome.phone.cinema.entry.MovieComment;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class GetMovieCommentLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    public ArrayList<MovieComment> movieCommentList;
    
    private static GetMovieCommentLogic ins;
    
    public static synchronized GetMovieCommentLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetMovieCommentLogic();
        }
        return ins;
    }
    
    //主页面获取到的视频栏目
    public void requestGetMovieCommentList(long contentId,Pager pager,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("contentId", contentId);
        serviceInfo.put("pager", pager);
        //serviceInfo.put("adfasf",  "sdfsadfasdf"); 
        
        fixRequestParams(params, serviceInfo, "findMediaContentComment","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaContentComment",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIECOMMENT_GET,
                this,
                false,
                HttpAction.url);
        
    }
    //主页面获取到的视频栏目
    public void requestaddMovieCommentList(long contentId,String comment,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map serviceInfo = new HashMap();
        
        serviceInfo.put("contentId", contentId);
        serviceInfo.put("comment", comment);
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
    
        //serviceInfo.put("adfasf",  "sdfsadfasdf"); 
        
        fixRequestParams(params, serviceInfo, "addMediaContentComment","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("addMediaContentComment",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIECOMMENT_ADD,
                this,
                false,
                HttpAction.url);
        
    }
   
    
    public void handleGetMovieCommentData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            movieCommentList = josonparseMovieComment(response.getBody()
                    .getParamsString());
            handler.sendEmptyMessage(HttpAction.MOVIECOMMENT_SUCCESS_GET);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    public void handleAddMovieCommentData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals(HttpAction.HTTP_RESPONSE_STATUS_OK))
        {
            //给对象赋值
            movieCommentList.add(0,josonparseMovieComment_Add(response.getBody()
                    .getParamsString()));
            handler.sendEmptyMessage(HttpAction.MOVIECOMMENT_SUCCESS_ADD);
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
            case RequestId.MOVIECOMMENT_GET:
                handleGetMovieCommentData(serviceRes);
                break;
            case RequestId.MOVIECOMMENT_ADD:
                handleAddMovieCommentData(serviceRes);
               
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
    public static MovieComment josonparseMovieComment_Add(String response)
    {
      MovieComment movieComment = new MovieComment();
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
                    
                    movieComment.setId(Integer.parseInt(job.optString("id")));
                    movieComment.setComment(job.optString("usercomment"));
                    movieComment.setTime(job.optString("commenttimeStr"));
                    movieComment.setUserId(job.optString("userid"));
                    
                  
                    
                }
                
            }
        
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return movieComment;
        
    }
    
    
    //解析数据
    public static ArrayList<MovieComment> josonparseMovieComment(String response)
    {
        ArrayList<MovieComment> list_movieComment = new ArrayList<MovieComment>();
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
                    MovieComment movieComment = new MovieComment();
                    jobObject = joba.getJSONObject(i);
                    System.out.println("获取数据到了数据");
                    
                    movieComment.setId(Integer.parseInt(jobObject.optString("id")));
                    movieComment.setComment(jobObject.optString("usercomment"));
                    movieComment.setTime(jobObject.optString("commenttimeStr"));
                    movieComment.setUserId(jobObject.optString("userid"));
                    
                    list_movieComment.add(movieComment);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list_movieComment;
        
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
