package com.changhong.smarthome.phone.store.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.OrderDetailLogic;
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoBean;
import com.lidroid.xutils.HttpUtils;

public class OrderConfirmActivity extends SuperActivity
{

private Button orderCommitBtn;
    
    private TextView orderNumAddBtn;
    
    private TextView orderNumMinusBtn;
    
    private EditText orderNumShow;
    
    private TextView orderAllPrice;
    
//    private TextView orderNumb;
    
    private TextView orderName;
    
    private TextView orderSinglePrice;
    
    private EditText bindTel;
    
    private EditText bindAdress1;
    
    private EditText bindAdress2;
    
    private EditText bindAdress3;
    
    private int orderNum;
    
//    private double singlePrice;
    
    private OrderDetailLogic orderDetailLogic;
    
    private HttpUtils httpUtils;
    
    
    /**
     * 标题栏
     */
    private StoreTitleItem titleItem;
    
    private void initView()
    {
        titleItem = (StoreTitleItem)findViewById(R.id.store_order_title);
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
        
        orderCommitBtn = (Button) findViewById(R.id.order_commit_btn);
        orderNumAddBtn = (TextView) findViewById(R.id.order_info_count_add);
        orderNumMinusBtn = (TextView) findViewById(R.id.order_info_count_minus);
        orderNumShow = (EditText) findViewById(R.id.order_info_count_add_show);
        orderAllPrice = (TextView) findViewById(R.id.order_info_all_price);
//        orderNumb = (TextView) findViewById(R.id.order_numb);
        orderName = (TextView) findViewById(R.id.order_info_name);
        orderSinglePrice = (TextView) findViewById(R.id.order_info_price);
        bindTel = (EditText) findViewById(R.id.order_bind_number);
        bindAdress1 = (EditText) findViewById(R.id.order_bind_adress1);
        bindAdress2 = (EditText) findViewById(R.id.order_bind_adress2);
        bindAdress3 = (EditText) findViewById(R.id.order_bind_adress3);
        bindTel = (EditText) findViewById(R.id.order_bind_number);
        orderCommitBtn.setOnClickListener(this);
        orderNumAddBtn.setOnClickListener(this);
        orderNumMinusBtn.setOnClickListener(this);
        orderNum = Integer.parseInt(orderNumShow.getText().toString());
        Selection.setSelection((Spannable) orderNumShow.getText(),
                orderNumShow.length());
        orderNumShow.addTextChangedListener(new TextWatcher()
        {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
                if (StringUtils.isEmpty(orderNumShow.getText().toString()))
                {
                    orderNum = 0;
                }
                else
                {
                    orderNum = Integer.parseInt(orderNumShow.getText()
                            .toString());
                }
                setEditSelect(orderNumShow);
                orderAllPrice.setText(String.valueOf(goodsDetailInfo.getSalePrice() * orderNum));
            }
        });
    }
    
    private void updateView()
    {
//        orderNumb.setText("123333");
        orderName.setText(goodsDetailInfo.getName());
        orderSinglePrice.setText(goodsDetailInfo.getSalePrice()
                + getResources().getString(R.string.order_unit_yuan));
        
        bindTel.setText("1321423432");
        bindAdress1.setText("2-1-02");
        bindAdress2.setText("2-1-02");
        bindAdress3.setText("其他");
        setEditSelect(bindTel);
        setEditSelect(bindAdress1);
        setEditSelect(bindAdress2);
        setEditSelect(bindAdress3);
        orderNum = Integer.parseInt(orderNumShow.getText().toString());
        orderAllPrice.setText(String.valueOf(goodsDetailInfo.getSalePrice() * orderNum));
        
    }
    
    /**
     * 设置编辑框输入光标在字的最后面
     * @param editText
     */
    private void setEditSelect(EditText editText)
    {
        Selection.setSelection((Spannable) editText.getText(),
                editText.length());
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.order_commit_btn:
//                addOrder();
                View view = LayoutInflater.from(OrderConfirmActivity.this)
                        .inflate(R.layout.pay_type, null);
                TextView payMoney = (TextView) view.findViewById(R.id.pay_money);
                LinearLayout unionPayLL = (LinearLayout) view.findViewById(R.id.union_pay_ll);
                LinearLayout aliPayLL = (LinearLayout) view.findViewById(R.id.alipay_pay_ll);
                LinearLayout communityPayLL = (LinearLayout) view.findViewById(R.id.community_pay_ll);
                TextView payCancelBtn = (TextView) view.findViewById(R.id.pay_cancel_btn);
                payMoney.setText(orderAllPrice.getText());
                unionPayLL.setOnClickListener(this);
                aliPayLL.setOnClickListener(this);
                communityPayLL.setOnClickListener(this);
                payCancelBtn.setOnClickListener(this);
                showDialog(view, false, R.style.MyDialog);
                break;
            case R.id.order_info_count_add:
                orderNum++;
                orderNumShow.setText(String.valueOf(orderNum));
                
                break;
            case R.id.order_info_count_minus:
                if (orderNum > 1)
                {
                    orderNum--;
                    orderNumShow.setText(String.valueOf(orderNum));
                }
                
                break;
            case R.id.union_pay_ll:
                addOrder();
                break;
            case R.id.alipay_pay_ll:
                addOrder();
                break;
            case R.id.community_pay_ll:
                addOrder();
                break;
            case R.id.pay_cancel_btn:
                dismissDialog();
                break;
            default:
                break;
        }
    }
    
    /**
     * 从商品详情界面传过来的商品详情
     */
    private GoodsDetailInfo goodsDetailInfo;
    
    /**
     * 生成的订单信息
     */
    private OrderInfoBean orderInfoBean;
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        orderDetailLogic = OrderDetailLogic.getInstance(getApplicationContext());
        orderDetailLogic.setData(mHandler);
        httpUtils = new HttpUtils();
        goodsDetailInfo = (GoodsDetailInfo) getIntent().getSerializableExtra(StoreConstant.GOODSDETAIL);
        orderInfoBean = new OrderInfoBean();
        orderInfoBean.setGoodsDetailInfo(goodsDetailInfo);
//        orderInfoBean.setGoodsName(goodsDetailInfo.getName());
//        orderInfoBean.setGoodsPrice(goodsDetailInfo.getSalePrice());
        //默认订购1件
        orderInfoBean.setTotalNum(1);
        double allMoney = goodsDetailInfo.getSalePrice() * orderInfoBean.getTotalNum();
        orderInfoBean.setTotalPrice(allMoney);
        
//        orderInfoBean.setGoodsId(goodsDetailInfo.getId());
        orderInfoBean.setSpId(goodsDetailInfo.getsInfo().getSpid());
        
        orderInfoBean.setAddress(orderDetailLogic.reallyName);
//        orderInfoBean.setDeliveryAddress(orderDetailLogic.deliveryAddress);
        orderInfoBean.setPhoneNum(orderDetailLogic.phone);
        orderInfoBean.setOrderType(2);
    }

    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.store_order_confirm);
        initView();
        updateView();
    }

    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
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
