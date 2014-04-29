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
 * [报修详情界面逻辑类]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-24] 
 */
public class GetBxItemList_wLogic extends SuperLogic
{
    
    private HttpHandler<String> httpHanlder;
    
    private static GetBxItemList_wLogic ins;
    
    public ArrayList<ServicesItem> services_bxList_w;
    
    
    public static synchronized GetBxItemList_wLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetBxItemList_wLogic();
        }
        return ins;
    }
    
    /**
     * [获取状态为 3，4 的报修数据]<BR>
     * [功能详细描述]
     * @param eventType
     * @param accountInfo
     * @param httpUtils
     */
    public void requestbxItemList(String eventType, BaseAccountInfo accountInfo,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("accountInfo", accountInfo);
        serviceInfo.put("compornot", 2);
        
        fixRequestParams(params,
                serviceInfo,
                "RepairHandler",
                "sc",
                "sc",
                "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("RepairHandler",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.BX_CX_W,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    public void requestbxDeleteAll(String eventType, BaseAccountInfo accountInfo,
            HttpUtils httpUtils)
    {
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("accountInfo", accountInfo);
        fixRequestParams(params,
                serviceInfo,
                "RepairHandler",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("RepairHandler",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.BX_DELETE_ALL,
                this,
                false,
                HttpUrl.URL_CBS);
    }
    
    public void updateItem(String eventType, String personId, String fwid,
            String fwpj, String fwpf, HttpUtils httpUtils)
    {
        
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("personId", personId);
        serviceInfo.put("fwid", fwid);
        serviceInfo.put("fwpj", fwpj);
        serviceInfo.put("fwpf", fwpf);
        fixRequestParams(params,
                serviceInfo,
                "RepairHandler",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("RepairHandler",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.BX_UPDATE,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    /**
     * [删除报修]<BR>
     * [功能详细描述]
     * @param eventType
     * @param accountInfo
     * @param id
     * @param httpUtils
     */
    public void deleteBxItem(String eventType, BaseAccountInfo accountInfo, long id,
            HttpUtils httpUtils)
    {
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        RequestParams params = new RequestParams();
        serviceInfo.put("eventType", eventType);
        serviceInfo.put("accountInfo", accountInfo);
        serviceInfo.put("fwid", id + "");
        fixRequestParams(params,
                serviceInfo,
                "RepairHandler",
                "sc",
                "sc",
                "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("RepairHandler",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.BX_DELETE_W,
                this,
                false,
                HttpUrl.URL_CBS);
        
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
    
    public void handlegetBxListData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
           
            ArrayList<ServicesItem> bxList2 = Josonparse_bxList_W(response.getBody()
                    .getParamsString());
            Message msg = new Message();
            msg.what=HttpAction.BXLIST_SUCCESS_MSGWHAT_W;
            msg.obj=bxList2;
            handler.sendMessage(msg);
            
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleBxUpdate(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            
            
            handler.sendEmptyMessage(HttpAction.BXUPDATE_SUCCESS_MSGWHAT);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleBxDelete(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            
            handler.sendEmptyMessage(HttpAction.BXDELETE_SUCCESS_MSGWHAT_W);
            
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handleBxDeleteALL(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            
            
            handler.sendEmptyMessage(HttpAction.BXDELETEALL_SUCCESS_MSGWHAT);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public ServicesItem Josonparse_add(String response)
    {
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
                bx_property.setFwzt(Integer.parseInt(jobObject.optString("repairstate")));
                bx_property.setContent(jobObject.optString("content"));
                bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                bx_property.setContent_pj(jobObject.optString("userComment"));
                bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                bx_property.setLxry(jobObject.optString("maintainPeople"));
                bx_property.setLxryhm(jobObject.optLong("maintainPhone"));
                JSONArray array  = jobObject.optJSONArray("picStrList");
                ArrayList<String> arrayList =new ArrayList<String>();
                if(null!=array){
                    for(int x=0;x<array.length();x++){
                   
                    arrayList.add( array.optString(x));
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
    
    public ArrayList<ServicesItem> Josonparse_bxList(String response)
    {
        // ArrayList<ServicesItem> list = null;
        ArrayList<ServicesItem> list0 = new ArrayList<ServicesItem>();
        // ArrayList<ServicesItem> list1 = new ArrayList<ServicesItem>();
        // ArrayList<ServicesItem> list2 = new ArrayList<ServicesItem>();
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
                    bx_property.setFwzt(Integer.parseInt(jobObject.optString("repairstate")));
                    bx_property.setContent(jobObject.optString("content"));
                    bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                    bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                    bx_property.setContent_pj(jobObject.optString("userComment"));
                    bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                    bx_property.setLxry(jobObject.optString("maintainPeople"));
                    bx_property.setLxryhm(jobObject.optLong("maintainPhone"));
                    JSONArray array  = jobObject.optJSONArray("picStrList");
                    ArrayList<String> arrayList =new ArrayList<String>();
                    if(null!=array){
                        for(int x=0;x<array.length();x++){
                       
                        arrayList.add( array.optString(x));
                        }
                    }
                    bx_property.setPicStrList(arrayList);
                    bx_property.setVoicePath(jobObject.optString("voicePath"));
                    list0.add(bx_property);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list0;
        
    }
    
    public ArrayList<ServicesItem> Josonparse_bxList_W(String response)
    {
        // ArrayList<ServicesItem> list = null;
        ArrayList<ServicesItem> list0 = new ArrayList<ServicesItem>();
        // ArrayList<ServicesItem> list1 = new ArrayList<ServicesItem>();
        // ArrayList<ServicesItem> list2 = new ArrayList<ServicesItem>();
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
                    bx_property.setFwzt(Integer.parseInt(jobObject.optString("repairstate")));
                    bx_property.setContent(jobObject.optString("content"));
                    bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                    bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                    bx_property.setContent_pj(jobObject.optString("userComment"));
                    bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                    bx_property.setLxry(jobObject.optString("maintainPeople"));
                    bx_property.setLxryhm(jobObject.optLong("maintainPhone"));
                    JSONArray array  = jobObject.optJSONArray("picStrList");
                    ArrayList<String> arrayList =new ArrayList<String>();
                    if(null!=array){
                        for(int x=0;x<array.length();x++){
                       
                        arrayList.add( array.optString(x));
                        }
                    }
                    bx_property.setPicStrList(arrayList);
                    bx_property.setVoicePath(jobObject.optString("voicePath"));
                    list0.add(bx_property);
                    
                }
                
            }
        }
        catch (JSONException e)
        {
            // wyfwproperty = null;
            e.printStackTrace();
        }
        
        return list0;
        
    }
    
    public ArrayList<ServicesItem> Josonparse_bxList_ALL(
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
                    bx_property.setFwzt(Integer.parseInt(jobObject.optString("repairstate")));
                    bx_property.setContent(jobObject.optString("content"));
                    bx_property.setStateTimeStr(jobObject.optString("stateTimeStr"));
                    bx_property.setPropertyallback(jobObject.optString("stateDesc"));
                    bx_property.setContent_pj(jobObject.optString("userComment"));
                    bx_property.setPf(Integer.parseInt(jobObject.optString("score")));
                    bx_property.setLxry(jobObject.optString("maintainPeople"));
                    bx_property.setLxryhm(jobObject.optLong("maintainPhone"));
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
                        if (jobObject.optString("repairstate").equals("1")
                                || jobObject.optString("repairstate")
                                        .equals("2"))
                        {
                            list1.add(bx_property);
                        }
                    }
                    else
                    {
                        if (jobObject.optString("repairstate").equals("3")
                                || jobObject.optString("repairstate")
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
        
            case RequestId.BX_CX_W:
                handlegetBxListData(serviceRes);
                break;
            case RequestId.BX_DELETE_W:
                handleBxDelete(serviceRes);
                break;
            case RequestId.BX_UPDATE:
                handleBxUpdate(serviceRes);
                break;
            case RequestId.BX_DELETE_ALL:
                handleBxDeleteALL(serviceRes);
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