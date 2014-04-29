package com.changhong.foundation.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.logic.FindPwdLogic;
import com.changhong.sdk.baseapi.StringUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FindPwdActivity extends BaseActivity
{
    
    @ViewInject(R.id.frame_pre)
    public LinearLayout frame_pre;
    
    @ViewInject(R.id.frame_next)
    public LinearLayout frame_next;
    
    @ViewInject(R.id.btn_next)
    public Button btn_next;
    
    @ViewInject(R.id.et_phone)
    private EditText etPhone;
    
    @ViewInject(R.id.et_identity_card)
    private EditText etIdentityCard;
    
    @ViewInject(R.id.et_valicode)
    private EditText et_valicode;
    
    private FindPwdLogic logic;
    
    private HttpUtils httpUtils;
    
    @ViewInject(R.id.et_pwd_again)
    private EditText etPwdAgain;
    
    @ViewInject(R.id.et_pwd)
    private EditText etPwd;
    
    @Override
    public void initData()
    {
        logic = FindPwdLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.find_pwd_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.btn_next)
    public void btnNextClick(View view)
    {
        if (checkEmpty(true))
        {
            return;
        }
        frame_pre.setVisibility(View.GONE);
        frame_next.setVisibility(View.VISIBLE);
    }
    
    @OnClick(R.id.btn_getValicode)
    public void btnGetValicodeClick(View view)
    {
        if (checkEmpty(false))
        {
            return;
        }
        logic.setData(mHandler);
        httpUtils = new HttpUtils();
        logic.requestGetPwdValicode(etPhone.getText().toString(),
                Constant.TYPE_FORGET_PWD_VALICEDE,
                etIdentityCard.getText().toString(),
                httpUtils);
        
    }
    
    public boolean checkEmpty(boolean isnext)
    {
        if (StringUtils.isEmpty(etPhone.getText().toString()))
        {
            showToast(etPhone.getHint().toString());
            return true;
        }
        if (StringUtils.isEmpty(etIdentityCard.getText().toString()))
        {
            showToast(etIdentityCard.getHint().toString());
            return true;
        }
        if (isnext)
        {
            if (StringUtils.isEmpty(et_valicode.getText().toString()))
            {
                showToast(et_valicode.getHint().toString());
                return true;
            }
        }
        
        return false;
    }
    
    public boolean checkFindPwd()
    {
        if (StringUtils.isEmpty(etPwd.getText().toString()))
        {
            showToast(etPwd.getHint().toString());
            return true;
        }
        if (StringUtils.isEmpty(etPwdAgain.getText().toString()))
        {
            showToast(etPwdAgain.getHint().toString());
            return true;
        }
        if (!etPwdAgain.getText().toString().equals(etPwd.getText().toString()))
        {
            showToast(getString(R.string.pwd_same));
            return true;
        }
        
        return false;
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_FINDPWD_GETVALICODE_SUCCESS:
            {
                showToast(getString(R.string.get_valicode_success));
                break;
            }
            case MSGWHAT_FINDPWD_SUCCESS:
            {
                showToast(getString(R.string.modify_pwd_success));
                finish();
                break;
            }
            case MSGWHAT_USER_NOT_EXIT:
            {
                showToast(getString(R.string.user_not_exit));
                break;
            }
            case MSGWHAT_PARAMS_ERROR:
            {
                showToast(getString(R.string.params_error));
                break;
            }
            case MSGWHAT_VALICODE_ERROR:
            {
                showToast(getString(R.string.valicode_error));
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    @OnClick(R.id.btn_send)
    public void btnSendClick(View view)
    {
        if (checkFindPwd())
        {
            return;
        }
        
        logic.setData(mHandler);
        httpUtils = new HttpUtils();
        logic.requestFindPwd(etPhone.getText().toString(),
                et_valicode.getText().toString(),
                etIdentityCard.getText().toString(),
                etPwd.getText().toString(),
                httpUtils);
        
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
    }
}
