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
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoBean;
import com.changhong.smarthome.phone.store.logic.bean.PagerBean;
import com.changhong.smarthome.phone.store.tools.StringUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

public class OrderNewestLogic extends SuperLogic
{
    
    private HttpHandler<String> httpHanlder;
    
    private Context mContext;
    
    public int screenHeight;
    
    public int screenWidth;
    
//    private BaseAccountInfo accountInfo;
    
    private static volatile OrderNewestLogic instance;
    
    public static OrderNewestLogic getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (MainLogic.class)
            {
                if (instance == null)
                {
                    instance = new OrderNewestLogic(context);
                }
            }
        }
        return instance;
    }
    
    private OrderNewestLogic(Context context)
    {
        mContext = context;
        getScreenSize();
        
    }
    
    private void getScreenSize()
    {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }
    
    /**
     * [订单查询]<BR>
     * [功能详细描述]
     * @param orderType    1 近期三个月 2 除近期三个月再往前推就个月
     * @param pagerBean    分页信息
     */
    public void sendFindOrdersReq(String orderType, PagerBean pager, int mode,int state,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("orderType", orderType);
        serviceInfo.put("state", state);
        serviceInfo.put("pager", pager);
        serviceInfo.put("accountInfo", LoginLogic.getInstance().getBaseAccountInfo());
        fixRequestParams(params, serviceInfo, "findWaresOrders", "sc", "sc", "4100");//findOrders
        //ACTION_LOGIN没有用到
        int reqId = StoreRequestId.REQUESTID_GET_FINDORDERS;
        
        if (mode == 2)
        {
            reqId = StoreRequestId.REQUESTID_GET_MORE_FINDORDERS;
        }
        
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                reqId,
                this,
                false,
                StoreConstant.URL_STORE,
                -1);
    }
    
    @Override
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is, int pos)
    {
        switch (requestId)
        {
            case StoreRequestId.REQUESTID_GET_FINDORDERS:
                handleGetFindOrders(serviceRes);
                break;
            case StoreRequestId.REQUESTID_GET_MORE_FINDORDERS:
                handleGetMoreFindOrders(serviceRes);
                break;
        }
    }
    
    /**
     * 处理查询订单的响应
     * @param response
     */
    public void handleGetFindOrders(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtil.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                List<OrderInfoBean> dynamicBeans = parseGetFindOrdersRsp(response.getBody()
                        .getParamsString());
                
                if (null != dynamicBeans)
                {
                    message.what = StoreConstant.GET_FINDORDERS_SUCCESS;
                    message.obj = dynamicBeans;
                }
                else
                {
                    message.what = StoreConstant.GET_FINDORDERS_FAILED;
                    
                }
            }
            else
            {
                message.what = StoreConstant.GET_FINDORDERS_FAILED;
            }
            handler.sendMessage(message);
        }
    }
    
    /**
     * 处理查询更多订单的响应
     * @param response
     */
    public void handleGetMoreFindOrders(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtil.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                List<OrderInfoBean> dynamicBeans = parseGetFindOrdersRsp(response.getBody()
                        .getParamsString());
                
                if (null != dynamicBeans)
                {
                    message.what = StoreConstant.GET_FINDORDERS_GETMORE_SUCCESS;
                    message.obj = dynamicBeans;
                }
                else
                {
                    message.what = StoreConstant.GET_FINDORDERS_GETMORE_FAILED;
                    
                }
            }
            else
            {
                message.what = StoreConstant.GET_FINDORDERS_GETMORE_FAILED;
            }
            handler.sendMessage(message);
        }
    }
    
    /**
     * 解析搜索商品信息返回 
     */
    public ArrayList<OrderInfoBean> parseGetFindOrdersRsp(String response)
    {
        if (StringUtil.isEmpty(response))
        {
            return null;
        }
        ArrayList<OrderInfoBean> list = new ArrayList<OrderInfoBean>();
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
                        OrderInfoBean orderInfoBean = new OrderInfoBean();
                        orderInfoBean.setAccountId(jsonObject.optString("accountId"));
                        orderInfoBean.setIsDated(Integer.parseInt(jsonObject.optString("isDated")));
                        orderInfoBean.setOrderId(jsonObject.optString("orderId"));
                        orderInfoBean.setOrderType(Integer.parseInt(jsonObject.optString("orderType")));
                        orderInfoBean.setPayTime(jsonObject.optString("payTime"));
                        orderInfoBean.setPhoneNum(jsonObject.optString("phoneNum"));
                        orderInfoBean.setPrice(Double.parseDouble(jsonObject.optString("price")));
                        orderInfoBean.setState(Integer.parseInt(jsonObject.optString("state")));
                        
                        orderInfoBean.setTotalNum(Integer.parseInt(jsonObject.optString("totalNum")));
                        orderInfoBean.setTotalPrice(Double.parseDouble(jsonObject.optString("totalPrice")));
                        orderInfoBean.setUsed(Integer.parseInt(jsonObject.optString("used")));
                        orderInfoBean.setUserId(jsonObject.optString("userId"));
                        orderInfoBean.setSpId(jsonObject.optString("spId"));
                        
                        JSONObject goodsinfoobj = jsonObject.getJSONObject("wares");
                        if(goodsinfoobj != null)
                        {
                            GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
                            goodsDetailInfo.setId(goodsinfoobj.optString("waresId"));
                            goodsDetailInfo.setOriginalPrice(Double.parseDouble(goodsinfoobj.optString("originalPrice")));
                            goodsDetailInfo.setSalePrice(Double.parseDouble(goodsinfoobj.optString("salePrice")));
                            goodsDetailInfo.setScore(Integer.parseInt(goodsinfoobj.optString("score")));
                            goodsDetailInfo.setDesc(goodsinfoobj.optString("descriptions"));
                            goodsDetailInfo.setName(goodsinfoobj.optString("waresName"));
                            
                            JSONArray urlArray = goodsinfoobj.optJSONArray("picUrls");
                            if (null != urlArray && urlArray.length() > 0)
                            {
                                List<String> picList = new ArrayList<String>();
                                for (int j = 0; j < urlArray.length(); j++)
                                {
                                    picList.add(urlArray.getString(j));
                                }
                                goodsDetailInfo.setPicURL(picList);
                            }
                            orderInfoBean.setGoodsDetailInfo(goodsDetailInfo);
                        }
                        
                        
                        list.add(orderInfoBean);
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
