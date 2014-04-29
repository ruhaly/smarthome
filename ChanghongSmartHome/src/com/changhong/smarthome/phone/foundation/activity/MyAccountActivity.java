package com.changhong.smarthome.phone.foundation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.changhong.smarthome.phone.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 我的账号界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class MyAccountActivity extends BaseActivity
{
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.my_account_layout);
        ViewUtils.inject(this);
    }
    
    @OnClick(R.id.frameAccount)
    public void frameAccountClick(View view)
    {
        startActivity(new Intent(getBaseContext(), ModifyAccountActivity.class));
    }
    
    @OnClick(R.id.framePhone)
    public void framePhoneClick(View view)
    {
        startActivity(new Intent(getBaseContext(), BindPhoneActivity.class));
    }
    
    @OnClick(R.id.frameIpp)
    public void frameIPPClick(View view)
    {
        startActivity(new Intent(getBaseContext(), BindIppActivity.class));
    }
    
    @OnClick(R.id.frameModifyPwd)
    public void frameModifyPwdClick(View view)
    {
        startActivity(new Intent(getBaseContext(), ModifyPwdActivity.class));
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.imgBack)
    public void imgBackClick(View view)
    {
        finish();
    }
}
