package com.changhong.smarthome.phone.foundation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.changhong.smarthome.phone.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 设置界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class SettingActivity extends BaseActivity
{
	private ImageView setting_back_list;
    
    @Override
    public void initData()
    {
    	
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.setting_layout);
        ViewUtils.inject(this);
        setting_back_list = (ImageView)findViewById(R.id.setting_back_list);
    	setting_back_list.setOnClickListener(this);
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.frameMsgTip)
    public void frameMsgTipClick(View view)
    {
        startActivity(new Intent(getBaseContext(), MsgTipActivity.class));
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId())
        {
            case R.id.setting_back_list:
            {
                finish();
                break;
            }
            default:
            {
                break;
            }
        }
	}
    
    
}
