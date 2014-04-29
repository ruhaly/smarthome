package com.changhong.smarthome.phone.property.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;

/**
 * <功能详细描述>
 * 积分详情规则
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:30:34
 */
public class IntegrationDetailRulesActivity extends SuperActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.integration_rules);
        initWidgets();
    }
    
    private void initWidgets()
    {
        //change tile
        TextView tv = (TextView) findViewById(R.id.intergrationtitletext);
        tv.setText(R.string.integration_rules_title);
        //hide right textView
        TextView exitImg2 = (TextView) findViewById(R.id.exitImg2);
        exitImg2.setVisibility(View.GONE);
        //back click event
        
        ImageView exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        exitImg1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    
    @Override
    public void initData()
    {
        
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
