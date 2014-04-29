package com.changhong.foundation.broadcast;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.baseapi.DbUtils;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.sdk.baseapi.AppLog;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BusinessInfo;

public class AppInstallReceiver extends BroadcastReceiver
{
    public static final String TAG = "AppInstallReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        
        String packageName = intent.getData().getSchemeSpecificPart();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)
                || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED))
        {
            //如果是长虹apk插件并且不是底座的话 就根据包名获取apk信息并存储到数据库中
            if (packageName.startsWith(Constant.PREFIX)
                    && !packageName.equals(Constant.CURRENT_PACKAGE))
            {
                
                DbUtils.deleteAppInfo(context, packageName);
                getPermisson(context, packageName);
                //监听apk卸载 发消息通知更新界面
                Intent it = new Intent(Constant.UPDATE_PLUGIN_VIEW);
                it.putExtra("pkgName", packageName);
                it.putExtra("action", Constant.apk_install_update);
                context.sendBroadcast(it);
            }
        }
        else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED))
        {
            //监听apk卸载 发消息通知更新界面
            DbUtils.deleteAppInfo(context, packageName);
            Intent it = new Intent(Constant.UPDATE_PLUGIN_VIEW);
            it.putExtra("pkgName", packageName);
            it.putExtra("action", Constant.apk_uninstall);
            context.sendBroadcast(it);
        }
        
    }
    
    private void getPermisson(final Context context, String pkgName)
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            final PackageInfo pkgInfo = pm.getPackageInfo(pkgName,
                    PackageManager.GET_PERMISSIONS);//通过包名，返回包信息
            saveAppInfo(context, pkgInfo);
            //            new Thread(new Runnable()
            //            {
            //                
            //                @Override
            //                public void run()
            //                {
            //                    saveAppInfo(context, pkgInfo);
            //                }
            //                
            //            }).start();
        }
        catch (NameNotFoundException e)
        {
            AppLog.out(TAG, e.getMessage(), AppLog.LEVEL_INFO);
        }
    }
    
    /**
     * 
     * 保存apk信息到数据库
     * [功能详细描述]
     * @param pkgInfo
     * @param type TODO
     */
    private void saveAppInfo(Context context, PackageInfo pkgInfo)
    {
        Map<String, BusinessInfo> map = LoginLogic.getInstance().user.map;
        BusinessInfo bi = new BusinessInfo();
        bi.setBusinessName(context.getPackageManager()
                .getApplicationLabel(pkgInfo.applicationInfo)
                .toString());
        bi.setInstallState(Constant.INSTALLED);
        bi.setPackageName(pkgInfo.packageName);
        bi.setVersionNo(pkgInfo.versionName);
        bi.setIsAuthority(Constant.FREE_USE);
        bi.setSize(CHUtils.convertByteToMB(""
                + new File(pkgInfo.applicationInfo.publicSourceDir).length()));
        if (null != map.get(pkgInfo.packageName))
        {
            if (!StringUtils.isEmpty(LoginLogic.getInstance().user.map.get(pkgInfo.packageName)
                    .getSize()))
            {
                bi.setSize(LoginLogic.getInstance().user.map.get(pkgInfo.packageName)
                        .getSize());
            }
            bi.setMainActivityPath(map.get(pkgInfo.packageName)
                    .getMainActivityPath());
            bi.setBusinessType(map.get(pkgInfo.packageName).getBusinessType());
        }
        //以后要改，暂时这样
        if (Arrays.asList(Constant.bianmingArray).contains(pkgInfo.packageName))
        {
            bi.setMainActivityPath(pkgInfo.packageName
                    + Constant.OTHER_APP_MAIN_ACTIVITY);
            bi.setBusinessType(Constant.PLUGIN_BIANMING);
        }
        else if (Arrays.asList(Constant.guankongArray)
                .contains(pkgInfo.packageName))
        {
            bi.setMainActivityPath(pkgInfo.packageName
                    + Constant.OTHER_APP_MAIN_ACTIVITY);
            bi.setBusinessType(Constant.PLUGIN_GUANGKONG);
        }
        else if (Arrays.asList(Constant.anfangArray)
                .contains(pkgInfo.packageName))
        {
            bi.setMainActivityPath(pkgInfo.packageName
                    + Constant.OTHER_APP_MAIN_ACTIVITY);
            bi.setBusinessType(Constant.PLUGIN_ANFANG);
        }
        else if (Arrays.asList(Constant.otherArray)
                .contains(pkgInfo.packageName))
        {
            bi.setMainActivityPath(pkgInfo.packageName
                    + Constant.OTHER_APP_MAIN_ACTIVITY);
            bi.setBusinessType(Constant.PLUGIN_OTHER);
        }
        
        DbUtils.insertAppInfo(context, bi);
    }
}
