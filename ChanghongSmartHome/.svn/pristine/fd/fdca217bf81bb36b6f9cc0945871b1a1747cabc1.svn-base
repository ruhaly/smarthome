package com.changhong.smarthome.phone.store.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Message;
import android.util.DisplayMetrics;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.entity.StoreRequestId;
import com.changhong.smarthome.phone.store.logic.bean.GoodsColumnBean;
import com.changhong.smarthome.phone.store.logic.bean.GoodsColumnGroup;
import com.changhong.smarthome.phone.store.tools.StringUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class FindColumnLogic extends SuperLogic
{
    
    private HttpHandler<String> httpHanlder;
    
    private Context mContext;
    
    public int screenHeight;
    
    public int screenWidth;
    
//    private BaseAccountInfo accountInfo;
    
    private static volatile FindColumnLogic instance;
    
    public static FindColumnLogic getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (MainLogic.class)
            {
                if (instance == null)
                {
                    instance = new FindColumnLogic(context);
                }
            }
        }
        return instance;
    }
    
    private FindColumnLogic(Context context)
    {
        mContext = context;
        getScreenSize();
//        accountInfo = MainLogic.getInstance(context).getBaseAccountInfo();
        
    }
    
    private void getScreenSize()
    {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }
    
    /**
     * [获取商品栏目信息]<BR>
     * [功能详细描述]
     * @param httpUtils
     */
    public void sendFindGoodsColumnReq(HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("accountInfo", LoginLogic.getInstance().getBaseAccountInfo());
        fixRequestParams(params,
                serviceInfo,
                "findGoodsColumn",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                StoreRequestId.REQUESTID_GET_FINDGOODSCOLUMN,
                this,
                false,
                StoreConstant.URL_STORE,
                -1);
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is, int pos)
    {
        // TODO Auto-generated method stub
        switch (requestId)
        {
            case StoreRequestId.REQUESTID_GET_FINDGOODSCOLUMN:
                handleGetFindGoodsColumn(serviceRes);
                break;
            
        }
    }
    
 
    /**
     * 处理商品栏目查询的响应
     * @param response
     */
    public void handleGetFindGoodsColumn(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtil.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                List<GoodsColumnGroup> groups = parseGetFindGoodsColumnRsp(response.getBody()
                        .getParamsString());
                
                if (null != groups)
                {
                    message.what = StoreConstant.GET_FINDGOODSCOLUMN_SUCCESS;
                    message.obj = groups;
                }
                else
                {
                    message.what = StoreConstant.GET_FINDGOODSCOLUMN_FAILED;
                    
                }
            }
            else
            {
                message.what = StoreConstant.GET_FINDGOODSCOLUMN_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 解析搜索商品分类返回 
     */
    public List<GoodsColumnGroup> parseGetFindGoodsColumnRsp(String response)
    {
        if (StringUtil.isEmpty(response))
        {
            return null;
        }
        List<GoodsColumnGroup> groups= new ArrayList<GoodsColumnGroup>();
        
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            GoodsColumnGroup goodsColumnGroup = null;
            List<GoodsColumnBean> valueList = null;//new ArrayList<GoodsColumnBean>();
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("dataList");
                
                if (null != joa && joa.length() > 0)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        goodsColumnGroup = new GoodsColumnGroup();
                        
                        jsonObject = joa.optJSONObject(i);
                        GoodsColumnBean key = new GoodsColumnBean();
                        key.setColumnId(jsonObject.optString("id"));
                        key.setColumnName(jsonObject.optString("columnName"));
                        key.setColumnIcon(jsonObject.optString("picURL"));
                        goodsColumnGroup.setKey(key);
                        
                        JSONArray urlArray = jsonObject.optJSONArray("children");
                        if (null != urlArray && urlArray.length() > 0)
                        {
                            valueList = new ArrayList<GoodsColumnBean>();
                            for (int j = 0; j < urlArray.length(); j++)
                            {
                                JSONObject subJsonObject = urlArray.optJSONObject(j);
                                GoodsColumnBean curBean = new GoodsColumnBean();
                                curBean.setColumnId(subJsonObject.optString("id"));
                                curBean.setColumnName(subJsonObject.optString("columnName"));
                                valueList.add(curBean);
                            }
                            goodsColumnGroup.setValue(valueList);
                        }
                       
                        groups.add(goodsColumnGroup);
                    }
                    
                }
                
            }
        }
        catch (Exception e)
        {
            //            list = null;
            e.printStackTrace();
        }
        return groups;
    }
    
    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        if (null != httpHanlder)
        {
            httpHanlder.stop();
            httpHanlder = null;
        }
        
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        handler.sendEmptyMessage(com.changhong.sdk.baseapi.SuperMsgWhat.DATA_FORMAT_ERROR_MSGWHAT);
    }
    
}
