/**
 * LoginActivity.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-2 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Function: 登陆界面
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-2 下午4:51:42
 */
public class LoginActivity extends BaseActivity
{
    
    @ViewInject(R.id.tv_register)
    public TextView tv_register;
    
    @ViewInject(R.id.tv_findpwd)
    public TextView tv_findpwd;
    
    public LoginLogic logic;
    
    HttpUtils httpUtil;
    
    @ViewInject(R.id.frame)
    public FrameLayout frame;
    
    @ViewInject(R.id.tv_online)
    public TextView tv_online;
    
    @ViewInject(R.id.tv_offline)
    public TextView tv_offline;
    
    @ViewInject(R.id.et_account)
    public EditText et_account;
    
    @ViewInject(R.id.et_pwd)
    public EditText et_pwd;
    
    @ViewInject(R.id.img_account_delete)
    public ImageView img_account_delete;
    
    @ViewInject(R.id.img_pwd_delete)
    public ImageView img_pwd_delete;
    
    @ViewInject(R.id.cb_autologin)
    public CheckBox cb_autologin;
    
    // 0在线、1离线
    public int type = 0;
    
    public boolean checked = false;
    
    @Override
    public void initData()
    {
        logic = LoginLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.login_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        CHUtils.addUnderlineTextView(tv_register);
        CHUtils.addUnderlineTextView(tv_findpwd);
        cb_autologin.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked)
            {
                checked = isChecked;
            }
        });
        init();
    }
    
    /**
     * 
     * 
     * init(自动登陆功能)
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
     * @since 2014年1月14日
     */
    public void init()
    {
        initUrl();
        logic.user = restoreUser();
        et_account.setText(logic.user.getAccount());
        et_pwd.setText(logic.user.getPwd());
        if (isAutoLogin())
        {
            cb_autologin.setChecked(true);
            if (!getIntent().getBooleanExtra("iszhuxiao", false))
            {
                btnLoginClick(null);
            }
            
        }
    }
    
    @OnClick(R.id.btn_login)
    public void btnLoginClick(View view)
    {
        boolean isTest = false;
        if (isTest)
        {
            
            finish();
            startActivity(new Intent(getBaseContext(), MainActivity.class),
                    true);
        }
        else
        {
            
            if (checkIsEmpty())
            {
                showToast(getString(R.string.account_or_pwd_not_empty));
                return;
            }
            
            showProcessDialog(dismiss);
            httpUtil = new HttpUtils();
            logic.setData(mHandler);
            logic.requestLogin(et_account.getText().toString().trim(),
                    et_pwd.getText().toString().trim(),
                    httpUtil);
        }
    }
    
    public boolean checkIsEmpty()
    {
        return (StringUtils.isEmpty(et_account.getText().toString()) || StringUtils.isEmpty(et_pwd.getText()
                .toString()
                .trim()));
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_LOGIN_SUCCESS:
            {
                logic.user.setAccount(et_account.getText().toString());
                logic.user.setPwd(et_pwd.getText().toString());
                logic.user.setType(type + "");
                saveData();
                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class),
                        true);
                break;
            }
            case MSGWHAT_PWD_ERROR:
            {
                showToast(getString(R.string.pwd_error));
                break;
            }
            case MSGWHAT_USER_NOT_EXIST:
            {
                showToast(getString(R.string.user_not_exit));
                break;
            }
            case MSGWHAT_USER_NOT_ACTIVATE:
            {
                showToast(getString(R.string.user_not_activate));
                break;
            }
        }
        super.handleMsg(msg);
    }
    
    /**
     * 
     * 
     * saveData(保存用户数据)
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
     * @since 2014年1月14日
     */
    public void saveData()
    {
        logic.baseAccountInfo.setAccountId(logic.user.getAccount());
        logic.baseAccountInfo.setUserId(logic.user.getUid());
        logic.baseAccountInfo.setCommunityCode(logic.user.getCommuntyId());
        logic.baseAccountInfo.setFlag(Constant.TERMINAL_TYPE);
        logic.baseAccountInfo.setResolution("QHD");
        logic.baseAccountInfo.setType("1");
        saveUser(logic.user);
        setIsAutoLogin(checked);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            showLogoutDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
        
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    // 当前模式0在线、1离线
    int n = 0;
    
    @OnClick(R.id.frame)
    public void frameClick(View view)
    {
        if (n == 0)
        {
            n = 1;
            type = 1;
            frame.setBackgroundResource(R.drawable.offline_bg);
            tv_online.setVisibility(View.VISIBLE);
            tv_offline.setVisibility(View.GONE);
        }
        else
        {
            n = 0;
            type = 0;
            frame.setBackgroundResource(R.drawable.online_bg);
            tv_online.setVisibility(View.GONE);
            tv_offline.setVisibility(View.VISIBLE);
        }
    }
    
    @OnClick(R.id.img_account_delete)
    public void imgAccountClick(View view)
    {
        et_account.setText("");
    }
    
    @OnClick(R.id.img_pwd_delete)
    public void imgPwdClick(View view)
    {
        et_pwd.setText("");
    }
    
    @OnClick(R.id.img_set)
    public void imgSetClick(View view)
    {
        showPayConfirm();
    }
    
    public EditText et_oss;
    
    public EditText et_cbs;
    
    public EditText et_sns;
    
    public Button btn_cancel;
    
    public Button btn_confirm;
    
    public ImageView img_uninstall;
    
    public void showPayConfirm()
    {
        
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.http_url_set, null);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        img_uninstall = (ImageView) view.findViewById(R.id.img_uninstall);
        et_oss = (EditText) view.findViewById(R.id.et_oss);
        et_cbs = (EditText) view.findViewById(R.id.et_cbs);
        et_sns = (EditText) view.findViewById(R.id.et_sns);
        et_oss.setText(getOSSURL());
        et_cbs.setText(getCBSURL());
        et_sns.setText(getSNSURL());
        img_uninstall.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View arg0)
            {
                
                List<BusinessInfo> list = CHUtils.getApplicationInfo(getBaseContext(),
                        "com.changhong");
                if (null != list && !list.isEmpty())
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        //通过程序的包名创建URI     
                        Uri packageURI = Uri.parse("package:"
                                + list.get(i).getPackageName());
                        //创建Intent意图      
                        Intent intent = new Intent(Intent.ACTION_DELETE,
                                packageURI);
                        //执行卸载程序      
                        startActivity(intent);
                    }
                }
                else
                {
                    showToast("没有所要卸载的程序");
                }
                
            }
        });
        btn_cancel.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View arg0)
            {
                dismissDialog();
            }
        });
        btn_confirm.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View arg0)
            {
                if (StringUtils.isEmpty(et_oss.getText().toString())
                        || StringUtils.isEmpty(et_cbs.getText().toString())
                        || StringUtils.isEmpty(et_sns.getText().toString()))
                {
                    showToast(getString(R.string.please_input_url));
                    return;
                }
                HttpUrl.OSS = et_oss.getText().toString().trim();
                HttpUrl.CBS = et_cbs.getText().toString().trim();
                HttpUrl.SNS = et_sns.getText().toString().trim();
                putOSSURL(HttpUrl.OSS);
                putCBSURL(HttpUrl.CBS);
                putSNSURL(HttpUrl.SNS);
                initUrl();
                
                dismissDialog();
            }
        });
        showDialog(view, false, R.style.MyDialog);
    }
    
    public void initUrl()
    {
        HttpUrl.OSS = getOSSURL();
        HttpUrl.CBS = getCBSURL();
        HttpUrl.SNS = getSNSURL();
        HttpUrl.URL_OSS = HttpUrl.OSS + "/service";
        HttpUrl.URL_CBS = HttpUrl.CBS + "/msg";
    }
    
    @OnClick(R.id.tv_register)
    public void tvRegisterClick(View view)
    {
        startActivity(new Intent(getBaseContext(), RegisterActivity.class),
                false);
    }
    
    @OnClick(R.id.tv_findpwd)
    public void tvFindpwdClick(View view)
    {
        startActivity(new Intent(getBaseContext(), FindPwdActivity.class),
                false);
    }
}