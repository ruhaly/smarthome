package com.changhong.foundation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.entity.User;
import com.changhong.sdk.activity.SuperActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 成员详情
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年1月28日]
 */
public class MemberDetailActivity extends SuperActivity
{
    
    @ViewInject(R.id.tvNickName)
    private TextView tvNickName;
    
    @ViewInject(R.id.tvReallyName)
    private TextView tvReallyName;
    
    @ViewInject(R.id.tvBoundPhone)
    private TextView tvBoundPhone;
    
    @ViewInject(R.id.tvSex)
    private TextView tvSex;
    
    @ViewInject(R.id.tvBirth)
    private TextView tvBirth;
    
    @ViewInject(R.id.tvAddress)
    private TextView tvAddress;
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.member_detail_layout);
        ViewUtils.inject(this);
        User user = (User) getIntent().getBundleExtra("member")
                .getParcelable("parkey");
        updateView(user);
    }
    
    public void updateView(User user)
    {
        tvNickName.setText(user.getNickName());
        tvReallyName.setText(user.getReallyName());
        tvBoundPhone.setText(user.getBoundPhone());
        tvSex.setText(user.getSex());
        tvBirth.setText(user.getBirth());
        tvAddress.setText(user.getAddress());
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
    }
}
