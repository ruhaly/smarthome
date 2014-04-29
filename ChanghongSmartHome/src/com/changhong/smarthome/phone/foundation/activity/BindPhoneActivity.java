package com.changhong.smarthome.phone.foundation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 绑定手机号界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class BindPhoneActivity extends BaseActivity
{
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.bind_phone_layout);
        ViewUtils.inject(this);
    }
    
    @OnClick(R.id.frameAccount)
    public void frameAccountClick(View view)
    {
        startActivity(new Intent(getBaseContext(), ModifyAccountActivity.class));
    }
    
    @OnClick(R.id.btnBind)
    public void btnBindClick(View view)
    {
        showTipDialog(getString(R.string.bind_phone_error), new CallBack()
        {
            
            @Override
            public void update(Object object)
            {
                
            }
        }, false, getString(R.string.i_know));
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
