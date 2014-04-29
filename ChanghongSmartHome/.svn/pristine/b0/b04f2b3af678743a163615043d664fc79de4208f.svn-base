package com.changhong.smarthome.phone.store.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.dialog.CustomCommonDialog;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.OrderDetailLogic;
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoBean;
import com.lidroid.xutils.HttpUtils;

public class OrderDetailActivity extends SuperActivity implements OnClickListener
{
    /**
     * 提交订单按钮
     */
    private Button order_button;
    
    /**
     * 提交订单布局
     */
    private LinearLayout orderLayout;
    
    
//    private String texeString;
    
    /**
     * 名称
     */
    private OrderDetailItem orderNameItem;
    
    /**
     * 数量
     */
    private OrderDetailItem orderQuantityItem;
    
    /**
     * 收件人地址
     */
    private OrderDetailItem orderAddresseeItem;
    
    /**
     * 收件人电话
     */
    private OrderDetailItem orderPhoneItem;
    
    /**
     * 送货地址
     */
    private OrderDetailItem orderDeliveryAddressItem;
    
    /**
     * 订单备注
     */
    private OrderDetailItem orderRemarkItem;
    
    /**
     * 订单日期
     */
    private OrderDetailItem orderDateItem;
    
    /**
     * 订单编号
     */
    private OrderDetailItem orderNumberItem;
    
    /**
     * 订单金额
     */
    private OrderDetailItem orderMoneyItem;
    
    private OrderInfoBean orderInfoBean;
    
    private OrderDetailLogic orderDetailLogic;
    
    private HttpUtils httpUtils;
    
    /**
     * 标题栏
     */
    private StoreTitleItem titleItem;
    
    
    /**
     * 订单详情的类型 0 确认订单 1 显示订单
     */
    private int orderType;
    
    /**
     * ordertype = 1 时候，从商品详情界面传过来的商品详情
     */
    private GoodsDetailInfo goodsDetailInfo;
       
    private void initListener()
    {
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    
    private void initView()
    {
        titleItem = (StoreTitleItem)findViewById(R.id.order_detail_title);
        //背景设置边距
        LinearLayout detail_1 = (LinearLayout)findViewById(R.id.detail_1);
        RelativeLayout.LayoutParams detail_1Params = (LayoutParams) detail_1.getLayoutParams();
        detail_1Params.setMargins(16 * orderDetailLogic.screenWidth / 720, 12 * orderDetailLogic.screenHeight / 1280, 16 * orderDetailLogic.screenWidth / 720, 0);
        detail_1.setLayoutParams(detail_1Params);
        
        LinearLayout detail_2 = (LinearLayout)findViewById(R.id.detail_2);
        RelativeLayout.LayoutParams detail_2Params = (LayoutParams) detail_2.getLayoutParams();
        detail_2Params.setMargins(16 * orderDetailLogic.screenWidth / 720, 12 * orderDetailLogic.screenHeight / 1280, 16 * orderDetailLogic.screenWidth / 720, 0);
        detail_2.setLayoutParams(detail_2Params);
        
//        orderNameItem = (OrderDetailItem)findViewById(R.id.order_shopping_name);
//        orderNameItem.setValue(orderInfoBean.getGoodsName());
//        
//        orderQuantityItem = (OrderDetailItem)findViewById(R.id.order_quantity);
//        orderQuantityItem.setValue(String.valueOf(orderInfoBean.getOrderQuantity()) + "件");
//        
//        orderAddresseeItem = (OrderDetailItem)findViewById(R.id.order_addressee);
//        orderAddresseeItem.setValue(orderInfoBean.getAddress());
//        
//        orderPhoneItem = (OrderDetailItem)findViewById(R.id.order_phone);
//        orderPhoneItem.setValue(orderInfoBean.getPhone());
//        
//        orderDeliveryAddressItem = (OrderDetailItem)findViewById(R.id.order_delivery_address);
//        orderDeliveryAddressItem.setValue(orderInfoBean.getDeliveryAddress());
//        
//        orderRemarkItem = (OrderDetailItem)findViewById(R.id.order_remark);
//        orderRemarkItem.setValue(orderInfoBean.getOrderRemark());
//        
//        orderMoneyItem = (OrderDetailItem)findViewById(R.id.order_money);
//        orderMoneyItem.setValue(String.valueOf(orderInfoBean.getOrderMoney()) + "元");
        
        if(orderType == StoreConstant.ORDER_CONFORM)
        {
            //订购按钮布局
            orderLayout = (LinearLayout)findViewById(R.id.order_button_layout);
            orderLayout.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams orderLayoutParams = (LayoutParams) orderLayout.getLayoutParams();
            orderLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            orderLayoutParams.height = 168 * orderDetailLogic.screenHeight / 1280;
            orderLayout.setLayoutParams(orderLayoutParams);
            
            order_button = (Button) findViewById(R.id.order_button);
            order_button.setOnClickListener(this);
            LinearLayout.LayoutParams order_buttonParams = (android.widget.LinearLayout.LayoutParams) order_button.getLayoutParams();
            order_buttonParams.height = orderDetailLogic.screenHeight * 82 / 1280;
            order_buttonParams.width = (orderDetailLogic.screenWidth * 304) / 720;
            order_button.setLayoutParams(order_buttonParams);
            order_button.setPadding((orderDetailLogic.screenWidth * 78) / 720, orderDetailLogic.screenHeight * 22 / 1280, (orderDetailLogic.screenWidth * 78) / 720, orderDetailLogic.screenHeight * 22 / 1280);
            order_button.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (orderDetailLogic.screenWidth * 26) / 720);
            
            //订购数量
            orderQuantityItem.setEditImageViewVisibility(View.VISIBLE);
            orderQuantityItem.setEditImageListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    showOrderQuantityDialog(getResources().getString(R.string.order_detail_quantity),InputType.TYPE_CLASS_NUMBER,orderQuantityItem.getValueTextView());
                }
            });
            
            //收件人
            orderAddresseeItem.setEditImageViewVisibility(View.VISIBLE);
            orderAddresseeItem.setEditImageListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    showOtherDialog(getResources().getString(R.string.order_detail_addressee),InputType.TYPE_CLASS_TEXT,orderAddresseeItem.getValueTextView(),0);
                }
            });
            
            //联系方式
            orderPhoneItem.setEditImageViewVisibility(View.VISIBLE);
            orderPhoneItem.setEditImageListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    showOtherDialog(getResources().getString(R.string.order_detail_phone),InputType.TYPE_CLASS_PHONE,orderPhoneItem.getValueTextView(),1);
                }
            });
            
            //送货地址
            orderDeliveryAddressItem.setEditImageViewVisibility(View.VISIBLE);
            orderDeliveryAddressItem.setEditImageListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    showOtherDialog(getResources().getString(R.string.order_detail_delivery_address),InputType.TYPE_CLASS_TEXT,orderDeliveryAddressItem.getValueTextView(),2);
                }
            });
            
            //备注
            orderRemarkItem.setEditImageViewVisibility(View.VISIBLE);
            orderRemarkItem.setEditImageListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    showOtherDialog(getResources().getString(R.string.order_detail_remark),InputType.TYPE_CLASS_TEXT,orderRemarkItem.getValueTextView(),3);
                }
            });
        }
        else
        {
            orderDateItem = (OrderDetailItem)findViewById(R.id.order_date);
            orderDateItem.setVisibility(View.VISIBLE);
//            orderDateItem.setValue(orderInfoBean.getOrderTime());
            
            orderNumberItem = (OrderDetailItem)findViewById(R.id.order_number);
            orderNumberItem.setVisibility(View.VISIBLE);
//            orderNumberItem.setValue(orderInfoBean.getOrderNumber());
        }
    }
    
    @Override
    protected void onRestart()
    {
        // TODO Auto-generated method stub
        super.onRestart();
    }
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    }
    
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.btn_back:
                finish();
                break;
            case R.id.order_button:
                showConformDialog();
                break;
            default:
                break;
        }
    }
    
    
    /**
     * [订购数量选择对话框]<BR>
     * [功能详细描述]
     * @param title
     * @param editIputType
     * @param textView
     */
    public void showOrderQuantityDialog(String title,int editIputType,final TextView textView)
    {
        final CustomCommonDialog dialog = new CustomCommonDialog(
                OrderDetailActivity.this);
        dialog.setTitle(title);
        dialog.setEdittext();
        dialog.setEditInputType(editIputType);
        dialog.setOnOkButton(getResources().getString(R.string.dialog_ok), new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                String value = dialog.editText.getText().toString().trim();
                if(value != null && !value.equals(""))
                {
                    //数量最多为9999
                    if(value.length() >= 5)
                    {
                        showToast(getResources().getString(R.string.order_detail_error2));
                        return;
                    }
                    int num = Integer.parseInt(value);
                    if(num > goodsDetailInfo.getStock())
                    {
                        showToast(getResources().getString(R.string.order_detail_error4));
                        return;
                    }
                    textView.setText(String.valueOf(num) + getResources().getString(R.string.order_detail_jian));
//                    orderInfoBean.setOrderQuantity(num);
//                    double allMoney = orderInfoBean.getGoodsPrice() * orderInfoBean.getOrderQuantity();
//                    orderInfoBean.setOrderMoney(allMoney);
//                    orderMoneyItem.getValueTextView().setText(String.valueOf(allMoney)+ getResources().getString(R.string.order_detail_jian));
                    dialog.dismiss();
                }
                else
                {
                    showToast(getResources().getString(R.string.order_detail_error1));
                }
            }
        });
        dialog.setOnCancelButton(getResources().getString(R.string.dialog_cancel), new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                
                dialog.dismiss();
            }
        });
        dialog.show();
        
    }
    
    /**
     * [订购数量选择对话框]<BR>
     * [功能详细描述]
     * @param title
     * @param editIputType
     * @param textView
     */
    public void showOtherDialog(String title,int editIputType,final TextView textView,final int key)
    {
//        final CustomCommonDialog dialog = new CustomCommonDialog(
//                OrderDetailActivity.this);
//        dialog.setTitle(title);
//        dialog.setEdittext();
//        dialog.setEditInputType(editIputType);
//        dialog.setOnOkButton(getResources().getString(R.string.dialog_ok), new OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                String value = dialog.editText.getText().toString().trim();
//                textView.setText(value);
//                if(key == 0)
//                {
//                    orderInfoBean.setAddress(value);
//                }
//                else if(key == 1)
//                {
//                    //电话号码长度最多15位
//                    if(value.length() > 15)
//                    {
//                        orderPhoneItem.setValue(orderInfoBean.getPhone());
//                        showToast(getResources().getString(R.string.order_detail_error3));
//                        return;
//                    }
//                    orderInfoBean.setPhone(value);
//                }
//                else if(key == 2)
//                {
//                    orderInfoBean.setDeliveryAddress(value);
//                }
//                else if(key == 3)
//                {
//                    orderInfoBean.setOrderRemark(value);
//                }
//                dialog.dismiss();
//            }
//        });
//        dialog.setOnCancelButton(getResources().getString(R.string.dialog_cancel), new OnClickListener()
//        {
//            
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
        
    }
    
    /**
     * [显示确认支付，选择 一卡通, 支付宝 的弹出框]<BR>
     * [功能详细描述]
     */
    public void showConformDialog()
    {
//        final CustomSingleChoiceListDialog dialog = new CustomSingleChoiceListDialog(
//                OrderDetailActivity.this);
//        
////        int[] i = { 0, 1 };
//        //对收件人，电话，收货地址，购买数量检查
//        if(StringUtil.isNullOrEmpty(orderInfoBean.getAddress()) || StringUtil.isNullOrEmpty(orderInfoBean.getDeliveryAddress())
//                || StringUtil.isNullOrEmpty(orderInfoBean.getPhone()) 
//                || (orderInfoBean.getOrderQuantity() == 0))
//        {
//            showToast(getResources().getString(R.string.order_detail_error));
//            return;
//        }
//        String[] j = { "一卡通", "支付宝" };
//        dialog.setSingleChoiceItems(j, 0);
//        dialog.setOnOkButton(getResources().getString(R.string.dialog_ok),new OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                addOrder();
//                dialog.dismiss();
//            }
//        });
//        dialog.setOnCancelButton(getResources().getString(R.string.dialog_cancel));
//        dialog.show();
        
    }



    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        orderDetailLogic = OrderDetailLogic.getInstance(getApplicationContext());
        orderDetailLogic.setData(mHandler);
        httpUtils = new HttpUtils();
        orderType = getIntent().getIntExtra(StoreConstant.ORDER_TYPE, StoreConstant.ORDER_CONFORM);
        //确认订单
        if(orderType == StoreConstant.ORDER_CONFORM)
        {
            goodsDetailInfo = (GoodsDetailInfo) getIntent().getSerializableExtra(StoreConstant.GOODSDETAIL);
            orderInfoBean = new OrderInfoBean();
//            orderInfoBean.setGoodsName(goodsDetailInfo.getName());
//            orderInfoBean.setGoodsPrice(goodsDetailInfo.getSalePrice());
//            //默认订购1件
//            orderInfoBean.setOrderQuantity(1);
//            double allMoney = orderInfoBean.getGoodsPrice() * orderInfoBean.getOrderQuantity();
//            orderInfoBean.setOrderMoney(allMoney);
//            
//            orderInfoBean.setGoodsId(goodsDetailInfo.getId());
//            orderInfoBean.setSpId(goodsDetailInfo.getsInfo().getSpid());
//            
//            orderInfoBean.setAddress(orderDetailLogic.reallyName);
//            orderInfoBean.setDeliveryAddress(orderDetailLogic.deliveryAddress);
//            orderInfoBean.setPhone(orderDetailLogic.phone);
            
        }
        //查看订单详情
        else
        {
            orderInfoBean = (OrderInfoBean) getIntent().getSerializableExtra(StoreConstant.ORDER_DETAIL);
        }
        
    }


    private void addOrder()
    {
        showProcessDialog(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                orderDetailLogic.stopRequest();
            }
        });
        
        orderDetailLogic.sendAddOrderReq(orderInfoBean, httpUtils);
    }

    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.orderdetail);
//        initData();
        initView();
        initListener();
    }



    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    public void handleMsg(Message msg)
    {
        super.handleMsg(msg);
        switch (msg.what)
        {
            case StoreConstant.GET_ADDORDER_SUCCESS:
                showToast(getResources().getString(R.string.order_add_suessce));
                break;
            case StoreConstant.GET_ADDORDER_FAILED:
                showToast(getResources().getString(R.string.error_1));
                break;
           
        }
    };
    
}
