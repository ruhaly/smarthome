package com.changhong.smarthome.phone.property.logic;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BaseAccountInfo;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.property.entry.ServicesItem;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.http.RequestId;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * LoginLogic.java com.pactera.ch.bedframe.logic
 * 
 * Function�?TODO
 * 
 * ver date author
 * 2013-12-12 b
 * 
 * Copyright (c) 2013, TNT All Rights
 * Reserved.com.example.changhongwyfw.logic.foundation.logic;
 * 
 * import java.io.InputStream; import java.util.HashMap; import java.util.Map;
 * 
 * import com.lidroid.xutils.exception.HttpException; import
 * com.lidroid.xutils.http.HttpHandler; import
 * com.lidroid.xutils.http.RequestParams; import
 * com.pactera.ch.foundation.baseapi.JsonParse; import
 * com.pactera.ch.foundation.entity.User; import
 * com.pactera.ch.foundation.http.HttpSenderUtils; import
 * com.pactera.ch.foundation.http.RequestId; import
 * com.pactera.ch.foundation.httpbean.ServiceResponse;
 * 
 * /** ClassName:LoginLogic Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-12 下午2:32:07
 */
public class GetTsItemList_wLogic extends SuperLogic
{
    HttpHandler<String> httpHanlder;
    
    private static GetTsItemList_wLogic ins;
    
    
    public ArrayList<ServicesItem> services_tsList_w;
    

    
    public static synchronized GetTsItemList_wLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetTsItemList_wLogic();
        }
        return ins;
    }
    
  
    public void requesttsItemList(String eventType, BaseAccountInfo accountInfo
            ,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("accountInfo", accountInfo);
        serviceInfo.put("compornot", 2);
        
        fixRequestParams(params, serviceInfo, "ComplainHandler" ,"sc",
                "sc",
                "4100");
      
            httpHanlder = HttpSenderUtils.sendMsgImpl("ComplainHandler",
                    params,
                    HttpSenderUtils.METHOD_POST,
                    httpUtils,
                    RequestId.TS_CX_W,
                    this,
                    false ,HttpUrl.URL_CBS);
            
      
    }
    
    public void updateItem(String eventType, String personId, String fwid,
            String fwpj, String fwpf,HttpUtils httpUtils)
    {
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("personId", personId);
        serviceInfo.put("fwid", fwid);
        serviceInfo.put("fwpj", fwpj);
        serviceInfo.put("fwpf", fwpf);
        fixRequestParams(params, serviceInfo, "ComplainHandler","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("ComplainHandler",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.TS_UPDATE,
                this,
                false,HttpUrl.URL_CBS);
        
    }
    
    public void handletsDeleteALL(String eventType, BaseAccountInfo accountInfo,HttpUtils httpUtils)
    {
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("accountInfo", accountInfo);
        fixRequestParams(params, serviceInfo, "ComplainHandler","sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("ComplainHandler",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.TS_DELETE_ALL,
                this,
                false,HttpUrl.URL_CBS);
    }
    
    public void deleteTsItem(String eventType, BaseAccountInfo accountInfo, long id,
            HttpUtils httpUtils)
    {
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("accountInfo", accountInfo);
        serviceInfo.put("fwid", id + "");
        fixRequestParams(params, serviceInfo, "ComplainHandler","sc",
                "sc",
                "4100");
      
            httpHanlder = HttpSenderUtils.sendMsgImpl("ComplainHandler",
                    params,
                    HttpSenderUtils.METHOD_POST,
                    httpUtils,
                    RequestId.TS_DELETE_W,
                    this,
                    false,HttpUrl.URL_CBS);
        
    }
    
    
    
   
    public void handleTsDeleteALL(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            

            
            handler.sendEmptyMessage(HttpAction.TSDELETEALL_SUCCESS_MSGWHAT);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleTsDelete(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            

            
                handler.sendEmptyMessage(HttpAction.TSDELETE_SUCCESS_MSGWHAT_W);
            
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleTsUpdate(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            
            
            handler.sendEmptyMessage(HttpAction.TSUPDATE_SUCCESS_MSGWHAT);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public static int Josonparse_delete(String response)
    {
        int a = 0;
        if (StringUtils.isEmpty(response))
        {
            return 1;
        }
        try
        {
            
            JSONObject job = new JSONObject(response);
            JSONObject jobObject = null;
            if (null != job)
            {
                
                JSONArray joba = job.optJSONArray("data");
                for (int i = 0; i < joba.length(); i++)
                {
//                    ServicesItem bx_property = new ServicesItem();
                    jobObject = joba.getJSONObject(i);
//                    System.out.println("获取数据到了数据");
                    a = Integer.parseInt(jobObject.optString("result"));
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        return a;
    }
    
    public static ServicesItem Josonparse_add(String response)
    {
//        ArrayList<ServicesItem> list0 = new ArrayList<ServicesItem>();
        ServicesItem bx_property = new ServicesItem();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        try
        {
            
            JSONObject job = new JSONObject(response);
            // job.get("result");
            JSONObject jobObject = null;
            if (null != job)
            {
                JSONObject joba = job.optJSONObject("data");
                jobObject = joba;
                // for (int i = 0; i < joba.length(); i++) {
                
                bx_property.setFwid(Integer.parseInt(jobObject.getString("id")));
                bx_property.setFwzt(Integer.parseInt(jobObject.optString("complainState")));
                bx_property.setContent(jobObject.optString("content"));
                bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                bx_property.setContent_pj(jobObject.optString("userComment"));
                bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                JSONArray array  = jobObject.optJSONArray("picStrList");
                ArrayList<String> arrayList =new ArrayList<String>();
                if(null!=array){
                    for(int h=0;h<array.length();h++){
                   
                    arrayList.add( array.optString(h));
                    }
                }
                bx_property.setPicStrList(arrayList);
                bx_property.setVoicePath(jobObject.optString("voicePath"));
                
                // }
                // a = Integer.parseInt(joba.optString("result"));
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        return bx_property;
    }
    
    /*
     * public void deleteList(String eventType, String fwid) { RequestParams
     * params = new RequestParams();
     * 
     * Map serviceInfo = new HashMap(); serviceInfo.put("eventType", eventType);
     * serviceInfo.put("fwid", fwid);
     * 
     * fixRequestParams(params, serviceInfo, "RepairHandler"); httpHanlder =
     * HttpSenderUtils.sendMsgImpl(ACTION_GETBXLIST, params,
     * HttpSenderUtils.METHOD_POST, httpUtils, RequestId.LOGIN_DL, this, false);
     * }
     */
    @Override
    public void handleHttpResponse(String response, int rspCode, int requestId)
    {
        
    }
    
    @Override
    public void handleHttpResponse(String response, int requestId,
            InputStream is)
    {
    }
    
    public void handlegetTsListData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
           
            ArrayList<ServicesItem> tsList2 = Josonparse_tsList_W(response.getBody()
                        .getParamsString());
                Message msg = new Message();
                msg.what=HttpAction.TSLIST_SUCCESS_MSGWHAT_W;
                msg.obj=tsList2;
                handler.sendMessage(msg);
                
                
               
            
           
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public static ArrayList<ServicesItem> Josonparse_tsList(String response)
    {
        ArrayList<ServicesItem> list1 = new ArrayList<ServicesItem>();
        //ArrayList<ServicesItem> list2 = new ArrayList<ServicesItem>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        // bx_property = new Wyfw();
        try
        {
            
            JSONObject job = new JSONObject(response);
            JSONObject jobObject = null;
            if (null != job)
            {
                
                JSONArray joba = job.optJSONArray("data");
                for (int i = 0; i < joba.length(); i++)
                {
                    ServicesItem bx_property = new ServicesItem();
                    jobObject = joba.getJSONObject(i);
//                    System.out.println("获取数据到了数据");
                    bx_property.setFwid(Integer.parseInt(jobObject.getString("id")));
                    bx_property.setFwzt(Integer.parseInt(jobObject.optString("complainState")));
                    bx_property.setContent(jobObject.optString("content"));
                    bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                    bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                    bx_property.setContent_pj(jobObject.optString("userComment"));
                    bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                    JSONArray array  = jobObject.optJSONArray("picStrList");
                    ArrayList<String> arrayList =new ArrayList<String>();
                    if(null!=array){
                        for(int h=0;h<array.length();h++){
                       
                        arrayList.add( array.optString(h));
                        }
                    }
                    bx_property.setPicStrList(arrayList);
                    bx_property.setVoicePath(jobObject.optString("voicePath"));
                    
                    list1.add(bx_property);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        return list1;
        
    }
    
    public static ArrayList<ServicesItem> Josonparse_tsList_W(String response)
    {
        ArrayList<ServicesItem> list1 = new ArrayList<ServicesItem>();
        //ArrayList<ServicesItem> list2 = new ArrayList<ServicesItem>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        // bx_property = new Wyfw();
        try
        {
            
            JSONObject job = new JSONObject(response);
            JSONObject jobObject = null;
            if (null != job)
            {
                
                JSONArray joba = job.optJSONArray("data");
                for (int i = 0; i < joba.length(); i++)
                {
                    ServicesItem bx_property = new ServicesItem();
                    jobObject = joba.getJSONObject(i);
//                    System.out.println("获取数据到了数据");
                    bx_property.setFwid(Integer.parseInt(jobObject.getString("id")));
                    bx_property.setFwzt(Integer.parseInt(jobObject.optString("complainState")));
                    bx_property.setContent(jobObject.optString("content"));
                    bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                    bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                    bx_property.setContent_pj(jobObject.optString("userComment"));
                    bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                    JSONArray array  = jobObject.optJSONArray("picStrList");
                    ArrayList<String> arrayList =new ArrayList<String>();
                    if(null!=array){
                        for(int h=0;h<array.length();h++){
                       
                        arrayList.add( array.optString(h));
                        }
                    }
                    bx_property.setPicStrList(arrayList);
                    bx_property.setVoicePath(jobObject.optString("voicePath"));
                    
                    list1.add(bx_property);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        return list1;
        
    }
    
    public static ArrayList<ServicesItem> Josonparse_tsList_ALL(
            String response, int x)
    {
        ArrayList<ServicesItem> list = null;
        // ArrayList<ServicesItem> list0 = new ArrayList<ServicesItem>();
        ArrayList<ServicesItem> list1 = new ArrayList<ServicesItem>();
        ArrayList<ServicesItem> list2 = new ArrayList<ServicesItem>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        // bx_property = new Wyfw();
        try
        {
            
            JSONObject job = new JSONObject(response);
            JSONObject jobObject = null;
            if (null != job)
            {
                
                JSONArray joba = job.optJSONArray("data");
                for (int i = 0; i < joba.length(); i++)
                {
                    ServicesItem bx_property = new ServicesItem();
                    jobObject = joba.getJSONObject(i);
//                    System.out.println("获取数据到了数据");
                    bx_property.setFwid(Integer.parseInt(jobObject.getString("id")));
                    bx_property.setFwzt(Integer.parseInt(jobObject.optString("complainState")));
                    bx_property.setContent(jobObject.optString("content"));
                    bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                    bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                    bx_property.setContent_pj(jobObject.optString("userComment"));
                    bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                    JSONArray array  = jobObject.optJSONArray("picStrList");
                    ArrayList<String> arrayList =new ArrayList<String>();
                    if(null!=array){
                        for(int h=0;h<array.length();h++){
                       
                        arrayList.add( array.optString(h));
                        }
                    }
                    bx_property.setPicStrList(arrayList);
                    bx_property.setVoicePath(jobObject.optString("voicePath"));
                    
                    if (x == 1)
                    {
                        if (jobObject.optString("complainState").equals("1")
                                || jobObject.optString("complainState")
                                        .equals("2"))
                        {
                            list1.add(bx_property);
                        }
                    }
                    else
                    {
                        if (jobObject.optString("complainState").equals("3")
                                || jobObject.optString("complainState")
                                        .equals("4"))
                        {
                            list2.add(bx_property);
                        }
                    }
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        if (x == 1)
        {
            list = list1;
        }
        else
        {
            list = list2;
        }
        return list;
        
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
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is)
    {
        switch (requestId)
        {
         
            case RequestId.TS_CX_W:
                handlegetTsListData(serviceRes);
                break;
            
            case RequestId.TS_DELETE_W:
                handleTsDelete(serviceRes);
                break;
            case RequestId.TS_UPDATE:
                handleTsUpdate(serviceRes);
                break;
            
            case RequestId.TS_DELETE_ALL:
                handleTsDeleteALL(serviceRes);
                break;

            default:
                break;
        }
        
    }
    
    public void stopRequest()
    {
        if (null != httpHanlder)
        {
            httpHanlder.stop();
        }
    }

}
