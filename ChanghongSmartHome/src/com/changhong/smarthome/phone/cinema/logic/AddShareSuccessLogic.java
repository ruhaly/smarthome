package com.changhong.smarthome.phone.cinema.logic;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.entry.LocalVideoInfo;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**  
* @author yang_jun
* @date 2014-3-17 下午8:40:34 
* 上传成功额
*/
public class AddShareSuccessLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    LocalVideoInfo video;
    
    public LocalVideoInfo getVideo()
    {
        return video;
    }

    public void setVideo(LocalVideoInfo video)
    {
        this.video = video;
    }

    private static AddShareSuccessLogic ins;
    public static synchronized AddShareSuccessLogic getInstance()
    {
        if (null == ins)
        {
            ins = new AddShareSuccessLogic();
        }
        return ins;
    }
    
    //表示上传完毕
    public void requestSendAddShareSuccess()
    {
        HttpUtils httpUtils = new HttpUtils();
        
        RequestParams params = new RequestParams();
        Map serviceInfo = new HashMap();
        serviceInfo.put("accountInfo",LoginLogic.getInstance().getBaseAccountInfo());
        serviceInfo.put("fileName", video.getDisplayName());
        
        
        fixRequestParams(params,
                serviceInfo,
                "addMediaShare",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("addMediaShare",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIE_UPLOAD_SUCCESS,
                this,
                false,
                HttpAction.url);
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
            case RequestId.MOVIE_UPLOAD_SUCCESS:
                Log.i("dd midea ", "------------add midea  success send");
                break;
        }
        
    }
    
    //解析数据
   
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
