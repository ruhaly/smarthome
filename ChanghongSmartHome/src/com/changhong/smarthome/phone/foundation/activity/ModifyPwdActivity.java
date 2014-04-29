package com.changhong.smarthome.phone.foundation.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.changhong.smarthome.phone.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 修改密码
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class ModifyPwdActivity extends BaseActivity
{
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.modify_pwd_layout);
        ViewUtils.inject(this);
        
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.btnForgetPwd)
    public void btnForgetPwdClick(View view)
    {
        View retrieveView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.retrieve_pwd_layout, null);
        final Dialog dialog = getDialog(retrieveView, false, R.style.MyDialog);
        Button btnConfirm = (Button) retrieveView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                showRetrievePwdNextDialog();
            }
        });
        dialog.show();
    }
    
    /**
     * 
     * 忘记密码下一步
     * [功能详细描述]
     */
    public void showRetrievePwdNextDialog()
    {
        View retrieveView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.retrieve_pwd_next_layout, null);
        Button btnConfirm = (Button) retrieveView.findViewById(R.id.btnConfirm);
        final Dialog dialog = getDialog(retrieveView, false, R.style.MyDialog);
        btnConfirm.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    
    @OnClick(R.id.imgBack)
    public void imgBackClick(View view)
    {
        finish();
    }
}
