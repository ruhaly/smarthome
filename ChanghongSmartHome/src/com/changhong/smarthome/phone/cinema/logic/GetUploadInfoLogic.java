/**
 * 
 */
package com.changhong.smarthome.phone.cinema.logic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.http.RequestId;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * @author yang_jun
 * @date 2014-3-17 上午10:48:48
 */
public class GetUploadInfoLogic extends SuperLogic
{
    private static final String TAG = "GetUploadInfoLogic";
    
    public MediaShareFtpInfo ftpInfo = null;
    
    HttpHandler<String> httpHanlder;
    
    private static GetUploadInfoLogic ins;
    
    public static synchronized GetUploadInfoLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetUploadInfoLogic();
        }
        return ins;
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
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param user
     * @param cinemaInfo
     */
    public void requestPostUploadInfo()
    {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        Map serviceInfo = new HashMap();
        serviceInfo.put("accountInfo", BillLogic.accountInfo);
        fixRequestParams(params,
                serviceInfo,
                "findMediaShareFtpInfo",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("findMediaShareFtpInfo",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.MOVIE_UPLOAD,
                this,
                false,
                HttpAction.url);
    }
    
    // 处理异步的http请求response
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
            case RequestId.MOVIE_UPLOAD:
                handleMediaShareFtpInfo(serviceRes);
                break;
        
        }
        
    }
    
    /**
     * @param serviceRes
     */
    private void handleMediaShareFtpInfo(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            //给对象赋值
            String resp = response.getBody().getParamsString();
            if (resp != null && resp.length() > 0)
            {
                ftpInfo = new Gson().fromJson(resp, MediaShareFtpInfo.class);
                handler.sendEmptyMessage(HttpAction.FTP_UPLOAD_INFO_SUCCESS_GET);
            }
            
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
}
