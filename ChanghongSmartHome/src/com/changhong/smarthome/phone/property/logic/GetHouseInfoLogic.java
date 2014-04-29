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
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.property.entry.HouseInfo;
import com.changhong.smarthome.phone.property.entry.HouseInfoRent;
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
public class GetHouseInfoLogic extends SuperLogic
{
    
    HttpHandler<String> httpHanlder;
    
    private static GetHouseInfoLogic ins;
    
    public static synchronized GetHouseInfoLogic getInstance()
    {
        if (null == ins)
        {
            ins = new GetHouseInfoLogic();
        }
        return ins;
    }
    
    public void putHouseInfoList(String id, int low_price, int high_price,
            String description, int type, ArrayList<String> addImageList,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        HouseInfo houseInfo = new HouseInfo();
                /*        serviceInfo.put("accountInfo", BillLogic.accountInfo);*/
        houseInfo.setId(id);
        houseInfo.setHigh_price(high_price);
        houseInfo.setLow_price(low_price);
        houseInfo.setImgs(addImageList);
        houseInfo.setType(type);
        houseInfo.setDescription(description);
        
        serviceInfo.put("houseInfo", houseInfo);
        
        fixRequestParams(params, serviceInfo, "houseApply", "sc", "sc", "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("houseApply",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.HOUSEPUT,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    public void getHouseInfoList(String id, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        /*        serviceInfo.put("accountInfo", BillLogic.accountInfo);*/
        serviceInfo.put("id", id);
        fixRequestParams(params, serviceInfo, "houseDetail", "sc", "sc", "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("houseDetail",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.HOUSEI,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    public void canelHouseInfo(String id, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        /*        serviceInfo.put("accountInfo", BillLogic.accountInfo);*/
        serviceInfo.put("id", id);
        fixRequestParams(params, serviceInfo, "houseCancel", "sc", "sc", "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("houseCancel",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.HOUSECANEL,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    public void getHouseInfoRentalList(String id, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        /*        serviceInfo.put("accountInfo", BillLogic.accountInfo);*/
        serviceInfo.put("id", id);
        fixRequestParams(params, serviceInfo, "houseRental", "sc", "sc", "4100");
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("houseRental",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.HOUSERENTAL,
                this,
                false,
                HttpUrl.URL_CBS);
        
    }
    
    @Override
    public void handleHttpResponse(String response, int rspCode, int requestId)
    {
        
    }
    
    @Override
    public void handleHttpResponse(String response, int requestId,
            InputStream is)
    {
    }
    
    public void handlegetHouseCancel(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            Message msg = new Message();
            
            msg.what = HttpAction.HOUSECANEL_SUCCESS_MSGWHAT;
            handler.sendMessage(msg);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handlegetHousePut(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            Message msg = new Message();
            
            msg.what = HttpAction.HOUSEPUT_SUCCESS_MSGWHAT;
            handler.sendMessage(msg);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handlegetHouseIListData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            Message msg = new Message();
            
            HouseInfo housedList1 = Josonparse_HouseInfoList(response.getBody()
                    .getParamsString());
            msg.what = HttpAction.HOUSEI_SUCCESS_MSGWHAT;
            msg.obj = housedList1;
            handler.sendMessage(msg);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public void handlegetHouseIListRentalData(ServiceResponse response)
    {
        if (response.getBody().getResult().equals("200"))
        {
            Message msg = new Message();
            
            HouseInfoRent housedList1 = Josonparse_HouseInfoRentalList(response.getBody()
                    .getParamsString());
            msg.what = HttpAction.HOUSERENTAL_SUCCESS_MSGWHAT;
            msg.obj = housedList1;
            handler.sendMessage(msg);
        }
        else
        {
            handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
        }
    }
    
    public static HouseInfo Josonparse_HouseInfoList(String response)
    {
        
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        // bx_property = new Wyfw();
        
        HouseInfo housei_property = new HouseInfo();
        try
        {
            JSONObject job = new JSONObject(response);
            
            if (null != job)
            {
                
                JSONObject joba = job.optJSONObject("data");
                
                housei_property.setId(joba.optString("id"));
                housei_property.setLow_price(joba.optInt("low_price"));
                housei_property.setHigh_price(joba.optInt("high_price"));
                housei_property.setDescription(joba.optString("description"));
                housei_property.setType(joba.optInt("type"));
                JSONArray array = joba.optJSONArray("imgs");
                
                if (null != array && array.length() > 0)
                {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    for (int x = 0; x < array.length(); x++)
                    {
                        
                        arrayList.add(array.optString(x));
                    }
                    housei_property.setImgs(arrayList);
                }
                
//                System.out.println("获取数据到了数据");
                
            }
            
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            
        }
        
        return housei_property;
        
    }
    
    public static HouseInfoRent Josonparse_HouseInfoRentalList(String response)
    {
        
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        // bx_property = new Wyfw();
        
        HouseInfoRent housei_property = new HouseInfoRent();
        try
        {
            JSONObject job = new JSONObject(response);
            
            if (null != job)
            {
                
                JSONObject joba = job.optJSONObject("data");
                
                housei_property.setHouse_no(joba.optString("house_no"));
                housei_property.setDeposit(joba.optInt("deposit"));
                housei_property.setMobile(joba.optString("mobile"));
                housei_property.setName(joba.optString("name"));
                housei_property.setPrice(joba.optInt("price"));
                housei_property.setRental_time(joba.optString("rental_time"));
                housei_property.setRental_type(joba.optString("rental_type"));
                
                System.out.println("获取数据到了数据");
                
            }
            
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            
        }
        
        return housei_property;
        
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
        
            case RequestId.HOUSEI:
                handlegetHouseIListData(serviceRes);
                break;
            case RequestId.HOUSERENTAL:
                handlegetHouseIListRentalData(serviceRes);
                break;
            case RequestId.HOUSEPUT:
                handlegetHousePut(serviceRes);
                break;
            case RequestId.HOUSECANEL:
                handlegetHouseCancel(serviceRes);
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