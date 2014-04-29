package com.changhong.foundation.baseapi;

import android.content.Context;

public class GlobalVar
{
    public static GlobalVar ins = new GlobalVar();
    
    private DatabaseManager dbm;
    
    public void initDbm(Context context)
    {
        dbm = new DatabaseManager(context);
    }
    
    public DatabaseManager getDbm(Context context)
    {
        if (null == dbm)
            initDbm(context);
        return dbm;
    }
}
