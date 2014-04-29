package com.changhong.smarthome.phone.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;

public class PayDetailActivity extends BaseActivity
{
    private ImageView backToCommen;
    
    private TextView toHistory;
    
    private TextView detailPay;
    
    private Button payNow;
    
    @Override
    public void initData()
    {
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.pay_detail);
        backToCommen = (ImageView) findViewById(R.id.detail_back_commen);
        toHistory = (TextView) findViewById(R.id.detail_to_history);
        detailPay = (TextView) findViewById(R.id.text_pay_detail);
        payNow = (Button) findViewById(R.id.btn_pay_now);
        
        backToCommen.setOnClickListener(this);
        toHistory.setOnClickListener(this);
        payNow.setOnClickListener(this);
        
        String payType = getIntent().getStringExtra("payType");
        detailPay.setText(payType);
    }
    
    @Override
    public void onClick(android.view.View v)
    {
        switch (v.getId())
        {
            case R.id.detail_back_commen:
            {
                finish();
                break;
            }
            case R.id.detail_to_history:
            {
                Intent intent = new Intent();
                intent.setClass(PayDetailActivity.this,
                        PayHistoryListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_pay_now:
            {
                break;
            }
            default:
            {
                break;
            }
        }
        
    };
    
    @Override
    public void clearData()
    {
    }
    
}
