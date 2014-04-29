package com.changhong.smarthome.phone;

import com.changhong.sdk.baseapi.SuperApplication;

public class CHApplication extends SuperApplication
{
    // 登录标识
    public static boolean LOGIN = false;
    
    public static boolean CHECKVERSION = false;
    
    private static CHApplication ins;
    
    public static CHApplication getIns()
    {
        if (null == ins)
        {
            ins = new CHApplication();
        }
        return ins;
    }
}