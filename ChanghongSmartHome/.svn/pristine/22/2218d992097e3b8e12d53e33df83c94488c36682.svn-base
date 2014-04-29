package com.changhong.smarthome.phone.foundation.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.changhong.sdk.baseapi.Constant;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.foundation.logic.RegisterLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 注册
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class RegisterActivity extends BaseActivity
{
    
    @ViewInject(R.id.frameRegister01)
    private LinearLayout frameRegister01;
    
    @ViewInject(R.id.frameRegister02)
    private LinearLayout frameRegister02;
    
    @ViewInject(R.id.etPhone)
    private EditText etPhone;
    
    @ViewInject(R.id.etValicode)
    private EditText etValicode;
    
    @ViewInject(R.id.btnGetValicode)
    private Button btnGetValicode;
    
    @ViewInject(R.id.cbAcceptAgreement)
    private CheckBox cbAcceptAgreement;
    
    @ViewInject(R.id.etPwd)
    private EditText etPwd;
    
    @ViewInject(R.id.rgSalutation)
    private RadioGroup rgSalutation;
    
    private HttpUtils httpUtils;
    
    private RegisterLogic logic;
    
    private String sex = "1";
    
    private String pwd = "";
    
    @ViewInject(R.id.btnRegister)
    private Button btnRegister;
    
    @Override
    public void initData()
    {
        logic = RegisterLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.register_layout);
        ViewUtils.inject(this);
        rgSalutation.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.rMan)
                {
                    sex = "1";
                }
                else
                {
                    sex = "0";
                }
            }
        });
    }
    
    public void changeStep(int type)
    {
        if (1 == type)
        {
            btnRegister.setText(getString(R.string.next));
            frameRegister01.setVisibility(View.VISIBLE);
            frameRegister02.setVisibility(View.GONE);
        }
        else
        {
            btnRegister.setText(getString(R.string.complete));
            frameRegister01.setVisibility(View.GONE);
            frameRegister02.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (frameRegister02.getVisibility() == View.VISIBLE)
            {
                changeStep(1);
                return true;
            }
        }
        
        return super.onKeyDown(keyCode, event);
    }
    
    @OnClick(R.id.btnRegister)
    public void btnRegisterClick(View view)
    {
        if (frameRegister01.getVisibility() == View.VISIBLE)
        {
            if (!checkStep01IsEmpty())
            {
                changeStep(2);
            }
        }
        else
        {
            
            pwd = etPwd.getText().toString();
            if (StringUtils.isEmpty(pwd))
            {
                pwd = etPhone.getText().toString().substring(5);
            }
            showProcessDialog(new OnDismissListener()
            {
                
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    logic.stopRequest();
                }
            });
            httpUtils = new HttpUtils();
            logic.setData(mHandler);
            logic.requestRegister(etPhone.getText().toString(),
                    etValicode.getText().toString(),
                    pwd,
                    sex,
                    LoginLogic.getInstance().curCommunity.getCommunityId(),
                    httpUtils);
            
        }
    }
    
    /**
     * 
     * 检查第一步的参数
     * [功能详细描述]
     * @return
     */
    public boolean checkStep01IsEmpty()
    {
        if (checkEditTextIsEmpty())
        {
            return true;
        }
        if (etPhone.getText().toString().length() != 11)
        {
            showToast(getString(R.string.check_phone));
            return true;
        }
        if (!cbAcceptAgreement.isChecked())
        {
            showToast(getString(R.string.please_accept_agreement));
            return true;
        }
        return false;
    }
    
    @OnClick(R.id.imgDelete)
    public void imgDeleteClick(View view)
    {
        etPwd.setText("");
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_REGISTER_SUCCESS:
            {
                String tip = "";
                String salutation = Constant.MAN.equals(sex) ? getString(R.string.gentlemen)
                        : getString(R.string.lady);
                if (StringUtils.isEmpty(logic.user.getReallyName()))
                {
                    tip = String.format(getString(R.string.register_success_tip1),
                            logic.user.getAccount(),
                            salutation);
                }
                else
                {
                    tip = String.format(getString(R.string.register_success_tip1),
                            logic.user.getReallyName() + "("
                                    + logic.user.getAccount() + ")",
                            salutation);
                }
                
                showTipDialog(tip, new CallBack()
                {
                    @Override
                    public void update(Object object)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("account", logic.user.getAccount());
                        intent.putExtra("pwd", etPwd.getText().toString());
                        setResult(100, intent);
                        finish();
                    }
                }, false, null);
                
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    @OnClick(R.id.imgBack)
    public void imgBackClick(View view)
    {
        if (frameRegister02.getVisibility() == View.VISIBLE)
        {
            changeStep(1);
        }
        else
        {
            finish();
        }
    }
}
