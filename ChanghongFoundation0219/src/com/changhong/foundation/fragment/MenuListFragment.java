package com.changhong.foundation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.activity.AppManagerActivity;
import com.changhong.foundation.activity.BaseActivity;
import com.changhong.foundation.activity.FamilyAccountActiviy;
import com.changhong.foundation.activity.MemberActivity;
import com.changhong.foundation.activity.PersonInfoActivity;
import com.changhong.foundation.activity.PwdServiceActivity;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.foundation.logic.MenuLogic;
import com.changhong.sdk.fragment.SuperFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MenuListFragment extends SuperFragment
{
    
    public MenuLogic logic;
    
    public View convertView;
    
    @ViewInject(R.id.tv_name)
    public TextView tv_name;
    
    @ViewInject(R.id.tv_account)
    public TextView tv_account;
    
    @ViewInject(R.id.frame_parent)
    public LinearLayout frame_parent;
    
    // 上一次点击的是哪个item 用于保持高亮效果
    public int last_index = 0;
    
    // 当前点击的是哪个item 用于保持高亮效果
    public int cur_index = 0;
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        
    }
    
    @Override
    public void onClick(View v)
    {
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        
    }
    
    @Override
    public void initData()
    {
    }
    
    /**
     * 
     * 
     * changeItemBg(更换左侧菜单点击背景)
     * 
     * TODO(这里描述这个方法适用条件 – 可选)
     * 
     * @param name
     * 
     * @param @return 设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since 2013年12月26日
     */
    public void changeItemBg(View view)
    {
        for (int i = 0; i < frame_parent.getChildCount(); i++)
        {
            if (frame_parent.getChildAt(i) == view)
            {
                frame_parent.getChildAt(i)
                        .setBackgroundResource(R.drawable.menu_item_press_bg);
            }
            else
            {
                
                frame_parent.getChildAt(i)
                        .setBackgroundResource(R.drawable.menu_item_default_bg);
                
            }
        }
    }
    
    @OnClick(R.id.frame_head)
    public void frameHeadClick(View view)
    {
        changeItemBg(view);
        ((BaseActivity) getActivity()).toggle();
        // 个人资料
        startActivity(new Intent(getActivity().getBaseContext(),
                PersonInfoActivity.class));
    }
    
    @OnClick(R.id.frame_family)
    public void frameFamilyClick(View view)
    {
        changeItemBg(view);
        ((BaseActivity) getActivity()).toggle();
        // 家庭账户
        startActivity(new Intent(getActivity().getBaseContext(),
                FamilyAccountActiviy.class));
    }
    
    @OnClick(R.id.frame_zhuyedingzhi)
    public void frameZhuyeClick(View view)
    {
        changeItemBg(view);
    }
    
    @OnClick(R.id.frame_update)
    public void frameUpdateClick(View view)
    {
        changeItemBg(view);
    }
    
    @OnClick(R.id.frame_serm)
    public void frameSermClick(View view)
    {
        changeItemBg(view);
        ((BaseActivity) getActivity()).toggle();
        // 家庭账户
        startActivity(new Intent(getActivity().getBaseContext(),
                AppManagerActivity.class));
    }
    
    @OnClick(R.id.frame_zhuxiao)
    public void frameZhuxiaoClick(View view)
    {
        changeItemBg(view);
        ((BaseActivity) getActivity()).toggle();
        ((BaseActivity) getActivity()).showZhuxiaoDialog();
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.menu_layout, null);
        ViewUtils.inject(this, convertView);
        tv_name.setText(LoginLogic.getInstance().user.getNickName());
        tv_account.setText(LoginLogic.getInstance().user.getReallyName());
        return convertView;
    }
    
    @OnClick(R.id.framePwdService)
    public void framePwdServiceClick(View view)
    {
        changeItemBg(view);
        ((BaseActivity) getActivity()).toggle();
        startActivity(new Intent(getActivity().getBaseContext(),
                PwdServiceActivity.class));
    }
    
    @OnClick(R.id.frameMemberManger)
    public void frameMemberMangerClick(View view)
    {
        changeItemBg(view);
        ((BaseActivity) getActivity()).toggle();
        startActivity(new Intent(getActivity().getBaseContext(),
                MemberActivity.class));
        
    }
    
    @OnClick(R.id.frameContactUs)
    public void frameContactUsClick(View view)
    {
        
    }
    
    @OnClick(R.id.frameEmptyCache)
    public void frameEmptyCacheClick(View view)
    {
        
    }

    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
}
