package com.changhong.smarthome.phone.foundation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 绑定Ipp界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class BindIppActivity extends BaseActivity
{
    
    @ViewInject(R.id.tvWhatIpp)
    private TextView tvWhatIpp;
    
    @ViewInject(R.id.tvForgetPwd)
    private TextView tvForgetPwd;
    
    @ViewInject(R.id.tvNoIpp)
    private TextView tvNoIpp;
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.bind_ipp_layout);
        ViewUtils.inject(this);
        CHUtils.addUnderlineTextView(tvWhatIpp);
        CHUtils.addUnderlineTextView(tvForgetPwd);
        CHUtils.addUnderlineTextView(tvNoIpp);
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
