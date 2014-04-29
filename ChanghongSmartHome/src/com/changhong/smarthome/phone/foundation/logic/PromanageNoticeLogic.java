package com.changhong.smarthome.phone.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.bean.MessageInfo;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;

/**
 * 
 * 物管通知
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月25日]
 */
public class PromanageNoticeLogic extends SuperLogic
{
    private static PromanageNoticeLogic ins;
    
    public static synchronized PromanageNoticeLogic getInstance()
    {
        if (null == ins)
        {
            ins = new PromanageNoticeLogic();
        }
        return ins;
    }
    
    public List<MessageInfo> list = new ArrayList<MessageInfo>();
    
    public HttpHandler<String> httpHanlder;
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        super.handleHttpResponse(serviceRes, requestId, is);
    }
    
    @Override
    public void clear()
    {
        list.clear();
    }
    
    @Override
    public void stopRequest()
    {
        
    }
    
}
