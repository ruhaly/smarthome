package com.changhong.smarthome.phone.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;

public class PayServiceActivity extends BaseActivity
{
    
    private ImageView backMainPage;
    
    private RelativeLayout wuyePay;
    
    private RelativeLayout phonePay;
    
    private RelativeLayout widebindPay;
    
    private String payType;
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.pay_service);
        backMainPage = (ImageView) findViewById(R.id.back_pay_main);
        wuyePay = (RelativeLayout) findViewById(R.id.wuye_pay);
        phonePay = (RelativeLayout) findViewById(R.id.phone_pay);
        widebindPay = (RelativeLayout) findViewById(R.id.widebind_pay);
        
        backMainPage.setOnClickListener(this);
        wuyePay.setOnClickListener(this);
        phonePay.setOnClickListener(this);
        widebindPay.setOnClickListener(this);
        
    }
    
    /**
     * 
     * 重写onclick事件<BR>
     * [功能详细描述]
     * @param v
     * @see com.changhong.sdk.activity.SuperActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.back_pay_main:
            {
                finish();
                break;
            }
            case R.id.wuye_pay:
            {
                payType = "物业费";
                Intent wuyeIntent = new Intent();
                wuyeIntent.putExtra("payType", payType);
                wuyeIntent.setClass(PayServiceActivity.this,
                        PayCommenActivity.class);
                startActivity(wuyeIntent);
                break;
            }
            case R.id.phone_pay:
            {
                payType = "电话费";
                Intent phomeIntent = new Intent();
                phomeIntent.putExtra("payType", payType);
                phomeIntent.setClass(PayServiceActivity.this,
                        PayCommenActivity.class);
                startActivity(phomeIntent);
                break;
            }
            case R.id.widebind_pay:
            {
                payType = "宽带费";
                Intent wideBindIntent = new Intent();
                wideBindIntent.putExtra("payType", payType);
                wideBindIntent.setClass(PayServiceActivity.this,
                        PayCommenActivity.class);
                startActivity(wideBindIntent);
                break;
            }
            default:
            {
                break;
            }
        }
        
    };
    
    @Override
    public void initData()
    {
    }
    
    @Override
    public void clearData()
    {
    }
    
}
