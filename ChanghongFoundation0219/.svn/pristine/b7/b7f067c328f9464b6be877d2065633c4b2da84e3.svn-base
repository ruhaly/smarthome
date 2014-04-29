package com.changhong.foundation.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.logic.FindPwdLogic;
import com.changhong.sdk.baseapi.StringUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 密码服务
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年1月26日]
 */
public class PwdServiceActivity extends BaseActivity
{
    
    public LinearLayout frame_pre;
    
    public LinearLayout frame_next;
    
    private EditText etPhone;
    
    private EditText etIdentityCard;
    
    private EditText et_valicode;
    
    private FindPwdLogic logic;
    
    private HttpUtils httpUtils;
    
    private EditText etPwdAgain;
    
    private EditText etPwd;
    
    private Button btn_next;
    
    @ViewInject(R.id.etOldPwd)
    private EditText etOldPwd;
    
    @ViewInject(R.id.etNewPwd)
    private EditText etNewPwd;
    
    @ViewInject(R.id.etNewPwdAgain)
    private EditText etNewPwdAgain;
    
    private Dialog dialog;
    
    @Override
    public void initData()
    {
        
        logic = FindPwdLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.pwdservice_layout);
        ViewUtils.inject(this);
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
    
    @OnClick(R.id.btnSave)
    public void btnSaveClick(View view)
    {
        if (checkEditTextIsEmpty())
        {
            return;
        }
        if (!etNewPwd.getText()
                .toString()
                .equals(etNewPwdAgain.getText().toString()))
        {
            showToast(getString(R.string.pwd_same));
            return;
        }
        
        showProcessDialog(dismiss);
        logic.setData(mHandler);
        httpUtils = new HttpUtils();
        
        logic.requestModifyPwd(restoreUser().getUid(), etOldPwd.getText()
                .toString(), etNewPwd.getText().toString(), httpUtils);
    }
    
    @OnClick(R.id.btnForgetPwd)
    public void btnForgetPwdClick(View view)
    {
        View v = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.find_pwd_layout, null);
        v.findViewById(R.id.img_back).setVisibility(View.GONE);
        
        etPhone = (EditText) v.findViewById(R.id.et_phone);
        etIdentityCard = (EditText) v.findViewById(R.id.et_identity_card);
        et_valicode = (EditText) v.findViewById(R.id.et_valicode);
        etPwd = (EditText) v.findViewById(R.id.et_pwd);
        etPwdAgain = (EditText) v.findViewById(R.id.et_pwd_again);
        ((Button) v.findViewById(R.id.btn_getValicode)).setOnClickListener(this);
        ((Button) v.findViewById(R.id.btn_send)).setOnClickListener(this);
        
        btn_next = (Button) v.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        frame_pre = (LinearLayout) v.findViewById(R.id.frame_pre);
        frame_next = (LinearLayout) v.findViewById(R.id.frame_next);
        
        dialog = getDialog(v, false, R.style.MyDialog);
        dialog.show();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_next:
            {
                if (checkEmpty(true))
                {
                    return;
                }
                frame_pre.setVisibility(View.GONE);
                frame_next.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.btn_getValicode:
            {
                if (checkEmpty(false))
                {
                    return;
                }
                showProcessDialog(dismiss);
                logic.setData(mHandler);
                httpUtils = new HttpUtils();
                logic.requestGetPwdValicode(etPhone.getText().toString(),
                        Constant.TYPE_FORGET_PWD_VALICEDE,
                        etIdentityCard.getText().toString(),
                        httpUtils);
                
                break;
            }
            case R.id.btn_send:
            {
                if (checkFindPwd())
                {
                    return;
                }
                showProcessDialog(dismiss);
                logic.setData(mHandler);
                httpUtils = new HttpUtils();
                logic.requestFindPwd(etPhone.getText().toString(),
                        et_valicode.getText().toString(),
                        etIdentityCard.getText().toString(),
                        etPwd.getText().toString(),
                        httpUtils);
                break;
            }
            
            default:
                break;
        }
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
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
                if (null != dialog)
                {
                    dialog.dismiss();
                }
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
            case MSGWHAT_MODIFYPWD_SUCCESS:
            {
                showToast(getString(R.string.modify_pwd_success));
                finish();
                break;
            }
            case MSGWHAT_MODIFYPWD_OLDPWD_ERROR:
            {
                showToast(getString(R.string.old_pwd_error));
                break;
            }
            case MSGWHAT_MODIFYPWD_ERROR:
            {
                showToast(getString(R.string.modify_pwd_error));
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
}
