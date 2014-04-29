package com.changhong.smarthome.phone.property.entry;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil
{
    
    private SharedPreferences mSharedPreferences;
    
    private static SharedPreferenceUtil mInstance;
    
    private static final String mSharedFileName = "PropertyServices";
    
    private SharedPreferenceUtil(Context context)
    {
        this(context, mSharedFileName);
    }
    
    private SharedPreferenceUtil(Context context, String aFileName)
    {
        if (context != null)
        {
            String fileName = aFileName;
            if (fileName == null || fileName.equals(""))
            {
                fileName = mSharedFileName;
            }
            mSharedPreferences = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
        }
    }
    
    public static SharedPreferenceUtil getInstance(Context context,
            String sharedFileName)
    {
        if (mInstance == null)
        {
            mInstance = new SharedPreferenceUtil(context, sharedFileName);
        }
        return mInstance;
    }
    
    public int getInt(String key)
    {
        return getInt(key, -1);
    }
    
    public int getInt(String key, int defalutValue)
    {
        return mSharedPreferences.getInt(key, defalutValue);
    }
    
    public String getString(String key)
    {
        return getString(key, "");
    }
    
    public String getString(String key, String defValue)
    {
        return mSharedPreferences.getString(key, defValue);
    }
    
    public long getLong(String key)
    {
        return getLong(key, -1);
    }
    
    public long getLong(String key, long defValue)
    {
        return mSharedPreferences.getLong(key, defValue);
    }
    
    public boolean getBoolean(String key)
    {
        return getBoolean(key, false);
    }
    
    public boolean getBoolean(String key, boolean defValue)
    {
        return mSharedPreferences.getBoolean(key, defValue);
    }
    
    public boolean putInt(String key, int value)
    {
        return mSharedPreferences.edit().putInt(key, value).commit();
    }
    
    public boolean putString(String key, String value)
    {
        return mSharedPreferences.edit().putString(key, value).commit();
    }
    
    public boolean putLong(String key, long value)
    {
        return mSharedPreferences.edit().putLong(key, value).commit();
    }
    
    public boolean putBoolean(String key, boolean value)
    {
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }
}
