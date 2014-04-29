/**
 * YiCardActivity.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-5 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:YiCardActivity Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-5 下午5:39:19
 */
public class YiCardActivity extends BaseActivity
{
    @ViewInject(R.id.tv_amount)
    private TextView tv_amount;
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.yicard_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        tv_amount.setText(getIntent().getStringExtra("amount")
                + getString(R.string.yuan));
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
    
    @OnClick(R.id.btn_recharge)
    public void btnRechargeClick(View view)
    {
        showYicardRecharge();
    }
    
    Button btn_yinlian;
    
    Button btn_zhifubao;
    
    Button btn_cancel2;
    
    private Button btn_cancel;
    
    private Button btn_confirm;
    
    public void showYicardRecharge()
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.yicard_recharge_layout, null);
        btn_yinlian = (Button) view.findViewById(R.id.btn_yinlian);
        btn_zhifubao = (Button) view.findViewById(R.id.btn_zhifubao);
        btn_cancel2 = (Button) view.findViewById(R.id.btn_cancel2);
        btn_yinlian.setOnClickListener(this);
        btn_zhifubao.setOnClickListener(this);
        btn_cancel2.setOnClickListener(this);
        showDialog(view, false, R.style.MyDialog);
    }
    
    public void showPayConfirm()
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.pay_confirm_layout, null);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        showDialog(view, false, R.style.MyDialog);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_cancel:
            {
                dismissDialog();
                break;
            }
            case R.id.btn_cancel2:
            {
                dismissDialog();
                break;
            }
            case R.id.btn_confirm:
            {
                break;
            }
            case R.id.btn_yinlian:
            {
                showPayConfirm();
                break;
            }
            case R.id.btn_zhifubao:
            {
                showPayConfirm();
                break;
            }
            case R.id.btn_shequ:
            {
                showPayConfirm();
                break;
            }
            case R.id.btn_confirm_g:
            {
                showYicardGuashiLoading();
                mHandler.postDelayed(new Runnable()
                {
                    
                    @Override
                    public void run()
                    {
                        showYicardGuashiResult();
                        mHandler.postDelayed(new Runnable()
                        {
                            
                            @Override
                            public void run()
                            {
                                dismissDialog();
                            }
                        }, 1500);
                    }
                }, 3000);
                break;
            }
            case R.id.btn_cancel_g:
            {
                dismissDialog();
                break;
            }
            case R.id.btn_confirm_f:
            {
                break;
            }
            case R.id.btn_cancel_f:
            {
                dismissDialog();
                break;
            }
            default:
                break;
        }
    }
    
    @OnClick(R.id.btn_guashi)
    public void btnGuashiClick(View view)
    {
        showYicardGuashi();
    }
    
    @OnClick(R.id.btn_forgetpwd)
    public void btnForgetpwd(View view)
    {
        showForgetPwd();
    }
    
    Button btn_confirm_g;
    
    Button btn_cancel_g;
    
    public void showYicardGuashi()
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.yicard_guashi_layout, null);
        btn_confirm_g = (Button) view.findViewById(R.id.btn_confirm_g);
        btn_cancel_g = (Button) view.findViewById(R.id.btn_cancel_g);
        btn_confirm_g.setOnClickListener(this);
        btn_cancel_g.setOnClickListener(this);
        showDialog(view, false, R.style.MyDialog);
    }
    
    public void showYicardGuashiLoading()
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.yicard_guashi_loading_layout, null);
        showDialog(view, false, R.style.MyDialog);
    }
    
    public void showYicardGuashiResult()
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.yicard_guashi_result_layout, null);
        showDialog(view, false, R.style.MyDialog);
    }
    
    Button btn_confirm_f;
    
    Button btn_cancel_f;
    
    public void showForgetPwd()
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.forget_pwd_layout, null);
        btn_confirm_f = (Button) view.findViewById(R.id.btn_confirm_f);
        btn_cancel_f = (Button) view.findViewById(R.id.btn_cancel_f);
        btn_confirm_f.setOnClickListener(this);
        btn_cancel_f.setOnClickListener(this);
        showDialog(view, false, R.style.MyDialog);
    }
}
