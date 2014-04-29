package com.changhong.foundation.baseapi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.changhong.sdk.baseapi.AppLog;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.contentprovider.ChangHong.Foundation;
import com.changhong.sdk.entity.BusinessInfo;

/**
 * 
 * [一句话功能简述]<BR>
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年3月4日]
 */
public class DbUtils
{
    private static final String TAG = "DBUtils";
    
    public static int deleteAppInfo(Context context, String pkgName)
    {
        ContentResolver cr = context.getContentResolver();
        return cr.delete(Foundation.CONTENT_URI_ATOM, Foundation.PACKAGENAME
                + "=" + "'" + pkgName + "'", null);
    }
    
    /**
     * 新安装的apk插入数据库
     */
    public static void insertAppInfo(Context context, BusinessInfo bi)
    {
        if (null == bi)
        {
            AppLog.out(TAG, "insertAppInfoToDb | bi = null", AppLog.LEVEL_INFO);
            return;
        }
        int n = deleteAppInfo(context, bi.getPackageName());
        AppLog.out(TAG, "成功删除" + n + "个", AppLog.LEVEL_INFO);
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Foundation.PACKAGENAME, bi.getPackageName());
        values.put(Foundation.MAINACTIVITYPATH, bi.getMainActivityPath());
        values.put(Foundation.APKID, bi.getApkId());
        values.put(Foundation.BUSINESSCODE, bi.getBusinessCode());
        values.put(Foundation.BUSINESSNAME, bi.getBusinessName());
        values.put(Foundation.SIZE, bi.getSize());
        values.put(Foundation.BUSINESSTYPE, bi.getBusinessType());
        values.put(Foundation.ISAUTHORITY, bi.getIsAuthority());
        values.put(Foundation.SERIALNUMBER, bi.getSerialNumber());
        values.put(Foundation.SOURCEURL, bi.getSourceUrl());
        values.put(Foundation.SPID, bi.getSpId());
        values.put(Foundation.VERSIONNO, bi.getVersionNo());
        values.put(Foundation.INSTALLSTATE, bi.getInstallState());
        values.put(Foundation.ICON, bi.getIcon());
        cr.insert(Foundation.CONTENT_URI_ATOM, values);
        values.clear();
    }
    
    /**
     * 
     *  获取已经安装的原子业务列表
     * [功能详细描述]
     * @param context
     * @param pkgName 包名
     * @param shortCut 快捷方式 1为有快捷方式
     * @param businessType 1便民2管控3安防4其他
     * @return
     */
    
    public static List<BusinessInfo> queryBusinessInfoList(Context context,
            String pkgName, String shortCut, String businessType)
    {
        
        Cursor cursor = null;
        ContentResolver cr = context.getContentResolver();
        try
        {
            if (!StringUtils.isEmpty(pkgName))
            {
                
                String selection = Foundation.PACKAGENAME + " = ?";
                String[] selectionArgs = new String[] { pkgName };
                cursor = cr.query(Foundation.CONTENT_URI_ATOM,
                        Foundation.ATOMBUSINESS_COLUMNS,
                        selection,
                        selectionArgs,
                        null);
            }
            else if (!StringUtils.isEmpty(businessType))
            {
                
                String selection = Foundation.BUSINESSTYPE + " = ?";
                String[] selectionArgs = new String[] { businessType };
                cursor = cr.query(Foundation.CONTENT_URI_ATOM,
                        Foundation.ATOMBUSINESS_COLUMNS,
                        selection,
                        selectionArgs,
                        null);
            }
            else if (!StringUtils.isEmpty(shortCut))
            {
                
                String selection = Foundation.SHORTCUTSTATE + " = ?";
                String[] selectionArgs = new String[] { shortCut };
                cursor = cr.query(Foundation.CONTENT_URI_ATOM,
                        Foundation.ATOMBUSINESS_COLUMNS,
                        selection,
                        selectionArgs,
                        null);
            }
            else
            {
                cursor = cr.query(Foundation.CONTENT_URI_ATOM,
                        Foundation.ATOMBUSINESS_COLUMNS,
                        null,
                        null,
                        null);
            }
        }
        catch (Exception e)
        {
            AppLog.out(TAG,
                    "queryBusinessInfoList fail" + e.getMessage(),
                    AppLog.LEVEL_ERROR);
        }
        List<BusinessInfo> list = null;
        
        if (cursor == null || !cursor.moveToFirst())
        {
            if (cursor != null)
            {
                cursor.close();
            }
            return new ArrayList<BusinessInfo>();
        }
        if (cursor.getCount() > 0)
        {
            list = new ArrayList<BusinessInfo>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                BusinessInfo bean = new BusinessInfo();
                bean.setPackageName(cursor.getString(cursor.getColumnIndex(Foundation.PACKAGENAME)));
                bean.setMainActivityPath(cursor.getString(cursor.getColumnIndex(Foundation.MAINACTIVITYPATH)));
                bean.setApkId(cursor.getString(cursor.getColumnIndex(Foundation.APKID)));
                bean.setBusinessCode(cursor.getString(cursor.getColumnIndex(Foundation.BUSINESSCODE)));
                bean.setBusinessName(cursor.getString(cursor.getColumnIndex(Foundation.BUSINESSNAME)));
                bean.setSize(cursor.getString(cursor.getColumnIndex(Foundation.SIZE)));
                bean.setBusinessType(cursor.getString(cursor.getColumnIndex(Foundation.BUSINESSTYPE)));
                bean.setIsAuthority(cursor.getString(cursor.getColumnIndex(Foundation.ISAUTHORITY)));
                bean.setSerialNumber(cursor.getString(cursor.getColumnIndex(Foundation.SERIALNUMBER)));
                bean.setSourceUrl(cursor.getString(cursor.getColumnIndex(Foundation.SOURCEURL)));
                bean.setSpId(cursor.getString(cursor.getColumnIndex(Foundation.SPID)));
                bean.setVersionNo(cursor.getString(cursor.getColumnIndex(Foundation.VERSIONNO)));
                bean.setInstallState(cursor.getString(cursor.getColumnIndex(Foundation.INSTALLSTATE)));
                bean.setIcon(cursor.getString(cursor.getColumnIndex(Foundation.ICON)));
                list.add(bean);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
        
    }
}
