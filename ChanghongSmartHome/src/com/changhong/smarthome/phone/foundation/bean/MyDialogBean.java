package com.changhong.smarthome.phone.foundation.bean;

import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialogBean
{
    private Dialog dialog;
    
    private EditText etAccount;
    
    private EditText etPwd;
    
    private Button btLogin;
    
    private TextView tvForgetPwd;
    
    private TextView tvRegister30;
    
    private ImageView imgDelete;
    
    private CallBack callBack;
    
    public ImageView getImgDelete()
    {
        return imgDelete;
    }
    
    public void setImgDelete(ImageView imgDelete)
    {
        this.imgDelete = imgDelete;
    }
    
    public CallBack getCallBack()
    {
        return callBack;
    }
    
    public void setCallBack(CallBack callBack)
    {
        this.callBack = callBack;
    }
    
    public MyDialogBean(CallBack callBack)
    {
        this.callBack = callBack;
    }
    
    public Dialog getDialog()
    {
        return dialog;
    }
    
    public void setDialog(Dialog dialog)
    {
        this.dialog = dialog;
    }
    
    public EditText getEtAccount()
    {
        return etAccount;
    }
    
    public void setEtAccount(EditText etAccount)
    {
        this.etAccount = etAccount;
    }
    
    public EditText getEtPwd()
    {
        return etPwd;
    }
    
    public void setEtPwd(EditText etPwd)
    {
        this.etPwd = etPwd;
    }
    
    public Button getBtLogin()
    {
        return btLogin;
    }
    
    public void setBtLogin(Button btLogin)
    {
        this.btLogin = btLogin;
    }
    
    public TextView getTvForgetPwd()
    {
        return tvForgetPwd;
    }
    
    public void setTvForgetPwd(TextView tvForgetPwd)
    {
        this.tvForgetPwd = tvForgetPwd;
    }
    
    public TextView getTvRegister30()
    {
        return tvRegister30;
    }
    
    public void setTvRegister30(TextView tvRegister30)
    {
        this.tvRegister30 = tvRegister30;
    }
    
    public void update()
    {
        callBack.update(null);
    }
    
}