package com.changhong.smarthome.phone.store.logic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Message;
import android.util.DisplayMetrics;

import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.entity.StoreRequestId;
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoBean;
import com.changhong.smarthome.phone.store.tools.StringUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * [订单详情逻辑管理类]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class OrderDetailLogic extends SuperLogic
{
//    private final static String TAG = "OrderDetailLogic";
    
    private HttpHandler<String> httpHanlder;
    
    private Context mContext;
    
    private static volatile OrderDetailLogic instance;
    
    public int screenHeight;
    
    public int screenWidth;
    
    public String reallyName;
    
    /**
     * 送货地址
     */
    public String deliveryAddress;
    
    public String phone;
    
//    private BaseAccountInfo accountInfo;
    
    //    public BitmapUtils bitmapUtils;
    
    public static OrderDetailLogic getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (OrderDetailLogic.class)
            {
                if (instance == null)
                {
                    instance = new OrderDetailLogic(context);
                }
            }
        }
        return instance;
    }
    
    private OrderDetailLogic(Context context)
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
     * [订单提交]<BR>
     * [功能详细描述]
     * @param orderInfoBean
     * @param httpUtils
     */
    public void sendAddOrderReq(OrderInfoBean orderInfoBean, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        
//        serviceInfo.put("accountId", MainLogic.getInstance(mContext).getBaseAccountInfo().getAccountId());
//        serviceInfo.put("userId", MainLogic.getInstance(mContext).getBaseAccountInfo().getUserId());
//        serviceInfo.put("receiver", orderInfoBean.getAddress());
//        serviceInfo.put("address", orderInfoBean.getDeliveryAddress());
//        serviceInfo.put("phone", orderInfoBean.getPhone());
//        
//        serviceInfo.put("goodsId", orderInfoBean.getGoodsId());
//        serviceInfo.put("spId", orderInfoBean.getSpId());
//        serviceInfo.put("quantity",
//                String.valueOf(orderInfoBean.getOrderQuantity()));
//        serviceInfo.put("price", String.valueOf(orderInfoBean.getOrderMoney()));
//        serviceInfo.put("remark", orderInfoBean.getOrderRemark());
        
        
        serviceInfo.put("accountId", LoginLogic.getInstance().getBaseAccountInfo().getAccountId());
        serviceInfo.put("userId", LoginLogic.getInstance().getBaseAccountInfo().getUserId());
        serviceInfo.put("spId", orderInfoBean.getSpId());
        serviceInfo.put("phoneNum", orderInfoBean.getPhoneNum());
        serviceInfo.put("address", orderInfoBean.getAddress());
        serviceInfo.put("waresId", orderInfoBean.getGoodsDetailInfo().getId());
        serviceInfo.put("totalNum",
                String.valueOf(orderInfoBean.getTotalNum()));
        serviceInfo.put("price", String.valueOf(orderInfoBean.getGoodsDetailInfo().getSalePrice()));
        serviceInfo.put("totalPrice", String.valueOf(orderInfoBean.getTotalPrice()));
        serviceInfo.put("orderType",
                String.valueOf(orderInfoBean.getOrderType()));
        fixRequestParams(params, serviceInfo, "addWaresOrder", "sc", "sc", "4100");//addOrder
        //ACTION_LOGIN没有用到
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                StoreRequestId.REQUESTID_GET_ADDORDER,
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
            
            case StoreRequestId.REQUESTID_GET_ADDORDER:
                handleAddOrder(serviceRes);
                break;
        }
    }
    
    /**
     * 处理提交订单的响应
     * @param response
     */
    public void handleAddOrder(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtil.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = StoreConstant.GET_ADDORDER_SUCCESS;
            }
            else
            {
                message.what = StoreConstant.GET_ADDORDER_FAILED;
            }
        }
        handler.sendMessage(message);
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
