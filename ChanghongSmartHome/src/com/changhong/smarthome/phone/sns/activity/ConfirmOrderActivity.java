package com.changhong.smarthome.phone.sns.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.GroupBuyOrderBean;
import com.changhong.smarthome.phone.sns.bean.GroupOrderVO;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.lidroid.xutils.HttpUtils;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-3-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfirmOrderActivity extends SnsSuperActivity
{
    private ImageView exit_button;//退出返回按钮
    
    private String address;
    
    //按钮被选择的效果
    ImageView order_bind_adress1_selected;
    
    ImageView order_bind_adress2_selected;
    
    //动态生成的控件参数
    private LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    
    private LinearLayout address_layout;
    
    private GroupOrderVO vo;
    
    private Button orderCommitBtn;
    
    private TextView orderNumAddBtn;
    
    private TextView orderNumMinusBtn;
    
    private EditText orderNumShow;
    
    private TextView orderAllPrice;
    
    private TextView orderNumb;
    
    private TextView orderName;
    
    private TextView orderSinglePrice;
    
    private EditText bindTel;
    
    private Button bindAdress1;
    
    private Button bindAdress2;
    
    private EditText bindAdress3;
    
    private int orderNum;
    
    private double singlePrice = 58.00;
    
    private IntShareLogic logic;
    
    private HttpUtils httpUtils;
    
    private int id = 1;
    
    private MCloudProgressDialog progressDialog;
    
    private GroupBuyOrderBean bean;
    
    @Override
    public void onCreate(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        super.onCreate(paramBundle);
        setContentView(R.layout.spell_list_confirm);
        initView();
        logic = IntShareLogic.getInstance();
        vo = new GroupOrderVO();
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(
                    ConfirmOrderActivity.this,
                    getResources().getString(R.string.loading));
        }
        progressDialog.show();
        getOrderDetail();
        
    }
    
    private void initView()
    {
        orderCommitBtn = (Button) findViewById(R.id.order_commit_btn);
        orderNumAddBtn = (TextView) findViewById(R.id.order_info_count_add);
        orderNumMinusBtn = (TextView) findViewById(R.id.order_info_count_minus);
        orderNumShow = (EditText) findViewById(R.id.order_info_count_add_show);
        orderAllPrice = (TextView) findViewById(R.id.order_info_all_price);
        orderNumb = (TextView) findViewById(R.id.order_numb);
        orderName = (TextView) findViewById(R.id.order_info_name);
        orderSinglePrice = (TextView) findViewById(R.id.order_info_price);
        bindTel = (EditText) findViewById(R.id.order_bind_number);
        bindAdress1 = (Button) findViewById(R.id.order_bind_adress1);
        bindAdress2 = (Button) findViewById(R.id.order_bind_adress2);
        bindAdress3 = (EditText) findViewById(R.id.order_bind_adress3);
        bindTel = (EditText) findViewById(R.id.order_bind_number);
        
        order_bind_adress1_selected = (ImageView) findViewById(R.id.order_bind_adress1_selected);
        
        order_bind_adress2_selected = (ImageView) findViewById(R.id.order_bind_adress2_selected);
        
        //simulate data
        bindAdress1.setText("2-1-02");
        bindAdress2.setText("2-1-02");
        bindAdress1.setOnClickListener(this);
        bindAdress2.setOnClickListener(this);
        
        orderCommitBtn.setOnClickListener(this);
        orderNumAddBtn.setOnClickListener(this);
        orderNumMinusBtn.setOnClickListener(this);
        exit_button = (ImageView) findViewById(R.id.exit_button);
        address_layout = (LinearLayout) findViewById(R.id.address_layout);
        super.exitButtonClick(exit_button);
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
                orderAllPrice.setText(String.valueOf(singlePrice * orderNum));
            }
        });
        //        initAddressButton();
    }
    
    //初始化用户的地址按钮
    private void initAddressButton()
    {
        //        address_layout
        Button bt = new Button(this);
        bt.setText(" 取消");
        bt.setWidth(100);
        bt.setHeight(100);
        address_layout.addView(bt);
        
    }
    
    private void getOrderDetail()
    {
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestGroupBuyingGo(id, httpUtils);
    }
    
    private void updateView()
    {
        if (null != bean)
        {
            orderNumb.setText(bean.getOrderNum());
            orderName.setText(bean.getTitle());
            orderSinglePrice.setText(bean.getPrice()
                    + getResources().getString(R.string.order_unit_yuan));
            singlePrice = Double.valueOf(bean.getPrice());
            bindTel.setText(bean.getPhone());
            
            bindAdress3.setText("其他");
            setEditSelect(bindTel);
            
            //            setEditSelect(bindAdress1);
            //            setEditSelect(bindAdress2);
            setEditSelect(bindAdress3);
            orderNum = Integer.parseInt(orderNumShow.getText().toString());
            orderAllPrice.setText(String.valueOf(singlePrice * orderNum));
            
        }
        
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
                View view = LayoutInflater.from(ConfirmOrderActivity.this)
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
            case R.id.alipay_pay_ll:
            case R.id.community_pay_ll:
                //                sendPaticipatePindaRequest();
                //simulate successful pay for the goods
                AlertDialog.Builder builder = new Builder(this);
                builder.setMessage("拼单成功");
                builder.setCancelable(true);
                builder.setPositiveButton("确认", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(ConfirmOrderActivity.this,
                                GroupBuyListActivity.class);
                        startActivity(intent);
                        //send message'
                    }
                });
                builder.create().show();
                break;
            case R.id.pay_cancel_btn:
                dismissDialog();
                break;
            case R.id.order_bind_adress1:
                Log.i(TAG, "---------001click");
                address = bindAdress1.getText().toString();
                order_bind_adress1_selected.setVisibility(View.VISIBLE);
                order_bind_adress2_selected.setVisibility(View.INVISIBLE);
                break;
            case R.id.order_bind_adress2:
                Log.i(TAG, "---------002click");
                address = bindAdress2.getText().toString();
                order_bind_adress1_selected.setVisibility(View.INVISIBLE);
                order_bind_adress2_selected.setVisibility(View.VISIBLE);
                break;
            
            default:
                break;
        }
    }
    
    private void sendPaticipatePindaRequest()
    {
        vo.setAccountId(UserUtils.getUser().getUid());
        vo.setAddr(bindAdress1.getText() + "\r" + bindAdress2.getText() + "\r"
                + bindAdress3.getText() + "\r");
        vo.setGroupId(bean.getId());
        vo.setId(Integer.parseInt(bean.getOrderNum()));
        vo.setNum(Integer.parseInt(orderNumShow.getText().toString()));
        vo.setPhone(bindTel.getText().toString());
        vo.setPrice((int) singlePrice);
        vo.setTitle(bean.getTitle());
        vo.setTotal(Float.valueOf(orderAllPrice.getText().toString())
                .intValue());
        logic.requestGroupBuyingOrder(vo);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        // TODO Auto-generated method stub
        super.handleMsg(msg);
        if (null != progressDialog)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
        switch (msg.what)
        {
            case Constant.GET_GROUPBUYINGGO_SUCCESS:
                bean = (GroupBuyOrderBean) msg.obj;
                updateView();
                break;
            case Constant.GET_GROUPBUYINGGO_FAILED:
                Toast.makeText(ConfirmOrderActivity.this,
                        getResources().getString(R.string.get_detail_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
}
