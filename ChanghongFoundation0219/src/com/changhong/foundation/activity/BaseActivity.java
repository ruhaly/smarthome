package com.changhong.foundation.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.CHApplication;
import com.changhong.foundation.baseapi.CHResource;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.entity.User;
import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.ActivityStack;

/**
 * 
 * 私有基类
 * @author b
 * @version [智慧社区-终端底座, 2014年1月17日]
 */
public abstract class BaseActivity extends SuperActivity implements MsgWhat
{
    
    public OnClickListener ok = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            logoutDirect();
        }
    };
    
    public OnClickListener ok_zhuxiao = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            ActivityStack.getIns().popupAllActivity();
            // ActivityStack.getIns().popupUnRelatedActivitys(
            // LoginActivity.class.getName());
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            intent.putExtra("iszhuxiao", true);
            startActivity(intent, false);
        }
    };
    
    public void showLogoutDialog()
    {
        showAlertDialog(0,
                getString(R.string.tip),
                getString(R.string.is_logout),
                null,
                ok,
                DEFAULT_BTN,
                null,
                true,
                true);
    }
    
    public void showZhuxiaoDialog()
    {
        showAlertDialog(0,
                getString(R.string.tip),
                getString(R.string.is_zhuxiao),
                null,
                ok_zhuxiao,
                DEFAULT_BTN,
                null,
                true,
                true);
    }
    
    public void logoutDirect()
    {
        CHApplication.getIns().exitApp();
    }
    
    public void startActivity(Intent intent, boolean isAnimation)
    {
        startActivity(intent);
        // if (isAnimation)
        // overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
    }
    
    /**
     * 
     * 
     * saveUser(保存用户数据)
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
    public void saveUser(User user)
    {
        if (null != user)
        {
            getAppShare().edit()
                    .putString("account", user.getAccount())
                    .putString("password", user.getPwd())
                    .putString("nickname", user.getNickName())
                    .putString("uid", user.getUid())
                    .putString("name", user.getReallyName())
                    .commit();
        }
    }
    
    /**
     * 
     * 
     * restoreUser(恢复用户数据)
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
    public User restoreUser()
    {
        User user = new User();
        user.setAccount(getAppShare().getString("account", ""));
        user.setPwd(getAppShare().getString("password", ""));
        user.setNickName(getAppShare().getString("nickname", ""));
        user.setUid(getAppShare().getString("uid", ""));
        user.setReallyName(getAppShare().getString("name", ""));
        return user;
    }
    
    public String getUserAccount()
    {
        return getAppShare().getString("account", "");
    }
    
    /**
     * 
     * isSuccessLogin(是否成功登录过)
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    public boolean isSuccessLogin()
    {
        return getAppShare().getBoolean(Constant.LOGGED_ON, false);
    }
    
    /**
     * 
     * 
     * isAutoLogin(是否自动登陆)
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
    public boolean isAutoLogin()
    {
        return getAppShare().getBoolean(Constant.AUTO_LOGIN, false);
    }
    
    public void setIsAutoLogin(boolean b)
    {
        getAppShare().edit().putBoolean(Constant.AUTO_LOGIN, b).commit();
    }
    
    public SharedPreferences getAppShare()
    {
        return getSharedPreferences(CHResource.CONFIG_NAME, 0);
    }
    
    public String getOSSURL()
    {
        return getAppShare().getString("OSS", HttpUrl.OSS);
    }
    
    public String getCBSURL()
    {
        return getAppShare().getString("CBS", HttpUrl.CBS);
    }
    
    public String getSNSURL()
    {
        return getAppShare().getString("SNS", HttpUrl.SNS);
    }
    
    public void putOSSURL(String url)
    {
        getAppShare().edit().putString("OSS", url).commit();
    }
    
    public void putCBSURL(String url)
    {
        getAppShare().edit().putString("CBS", url).commit();
    }
    
    public void putSNSURL(String url)
    {
        getAppShare().edit().putString("SNS", url).commit();
    }
}
