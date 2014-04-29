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
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.changhong.smarthome.phone.store.logic.bean.PagerBean;
import com.changhong.smarthome.phone.store.tools.StringUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * [商品分类逻辑]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class ClassifyshowLogic extends SuperLogic
{
    private final static String TAG = "ClassifyshowLogic";
    
    private HttpHandler<String> httpHanlder;
    
    private Context mContext;
    
    private static volatile ClassifyshowLogic instance;
    
    public int screenHeight;
    
    public int screenWidth;
    
//    private BaseAccountInfo accountInfo;
    
    //    public BitmapUtils bitmapUtils;
    
    public static ClassifyshowLogic getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (ClassifyshowLogic.class)
            {
                if (instance == null)
                {
                    instance = new ClassifyshowLogic(context);
                }
            }
        }
        return instance;
    }
    
    private ClassifyshowLogic(Context context)
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
     * [搜索商品]<BR>
     * [功能详细描述]
     * @param searchWords  搜索关键字
     * @param columnId     栏目Id
     * @param pagerBean    分页信息
     */
    public void sendFindGoodsReq(String searchWords,String columnId,PagerBean pager,int mode,HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("searchWords", searchWords);
        serviceInfo.put("columnId", columnId);
        serviceInfo.put("pager", pager);
        serviceInfo.put("accountInfo", LoginLogic.getInstance().getBaseAccountInfo());
        fixRequestParams(params,
                serviceInfo,
                "findGoods",
                "sc",
                "sc",
                "4100");
        //ACTION_LOGIN没有用到
        int reqid = StoreRequestId.REQUESTID_GET_FINDGOODS;
        if(mode == 2)
        {
            reqid = StoreRequestId.REQUESTID_GET_MORE_FINDGOODS;
        }
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                reqid,
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
            //搜索商品请求 返回数据解析
            case StoreRequestId.REQUESTID_GET_FINDGOODS:
                handleGetFindGoods(serviceRes);
                break;
            case StoreRequestId.REQUESTID_GET_MORE_FINDGOODS:
                handleGetMoreFindGoods(serviceRes);
                break;  
           
        }
    }
    
    /**
     * 处理搜索商品的响应
     * @param response
     */
    public void handleGetFindGoods(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtil.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                List<GoodsDetailInfo> dynamicBeans = parseGetFindGoodsRsp(response.getBody()
                        .getParamsString());
                
                if (null != dynamicBeans && dynamicBeans.size() > 0)
                {
                    message.what = StoreConstant.GET_FINDGOODS_SUCCESS;
                    message.obj = dynamicBeans;
                }
                else
                {
                    message.what = StoreConstant.GET_FINDGOODS_FAILED;
                    
                }
            }
            else
            {
                message.what = StoreConstant.GET_FINDGOODS_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理搜索商品的响应
     * @param response
     */
    public void handleGetMoreFindGoods(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtil.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                List<GoodsDetailInfo> dynamicBeans = parseGetFindGoodsRsp(response.getBody()
                        .getParamsString());
                
                if (null != dynamicBeans)
                {
                    message.what = StoreConstant.GET_FINDGOODS_GET_MORE_SUCCESS;
                    message.obj = dynamicBeans;
                }
                else
                {
                    message.what = StoreConstant.GET_FINDGOODS_GET_MORE_FAILED;
                    
                }
            }
            else
            {
                message.what = StoreConstant.GET_FINDGOODS_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    
    /**
     * 解析搜索商品信息返回 
     */
    public ArrayList<GoodsDetailInfo> parseGetFindGoodsRsp(String response)
    {
        if (StringUtil.isEmpty(response))
        {
            return null;
        }
        ArrayList<GoodsDetailInfo> list = new ArrayList<GoodsDetailInfo>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("dataList");
                
                if (null != joa && joa.length() > 0)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        jsonObject = joa.optJSONObject(i);
                        GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
                        goodsDetailInfo.setId(jsonObject.optString("id"));
//                        goodsDetailInfo.setActualPrice(Double.parseDouble(jsonObject.optString("actualPrice")));
                        goodsDetailInfo.setDesc(jsonObject.optString("desc"));
                        goodsDetailInfo.setName(jsonObject.optString("name"));
//                        goodsDetailInfo.setSpId(jsonObject.optString("spId"));
//                        goodsDetailInfo.setStock(Integer.parseInt(jsonObject.optString("stock")));
//                        goodsDetailInfo.setAddress(jsonObject.optString("address"));
                        
                        JSONArray urlArray = jsonObject.optJSONArray("picURL");
                        if (null != urlArray && urlArray.length() > 0)
                        {
                            List<String> picList = new ArrayList<String>();
                            for (int j = 0; j < urlArray.length(); j++)
                            {
                                picList.add(urlArray.getString(j));
                            }
                            goodsDetailInfo.setPicURL(picList);
                        }
                        list.add(goodsDetailInfo);
                    }
                    
                }
                
            }
        }
        catch (Exception e)
        {
            //            list = null;
            e.printStackTrace();
        }
        return list;
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
        handler.sendEmptyMessage(com.changhong.sdk.baseapi.SuperMsgWhat.DATA_FORMAT_ERROR_MSGWHAT );
    }
    
    
    
}
