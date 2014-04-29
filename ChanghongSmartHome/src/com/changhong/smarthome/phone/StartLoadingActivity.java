package com.changhong.smarthome.phone;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.AppLog;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.DownloadManagerPro;
import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.baseapi.PreferencesUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.smarthome.phone.foundation.activity.CitySelectActivity;
import com.changhong.smarthome.phone.foundation.baseapi.MsgWhat;
import com.changhong.smarthome.phone.foundation.bean.VersionInfo;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.lidroid.xutils.HttpUtils;

/**
 * 
 * 启动loading界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class StartLoadingActivity extends SuperActivity
{
    
    private HttpUtils httpUtils;
    
    private LoginLogic logic;
    
    private DownloadManager downloadManager;
    
    private DownloadManagerPro downloadManagerPro;
    
    private CompleteReceiver completeReceiver;
    
    private long downloadId = 0;
    
    public static final String DOWNLOAD_FOLDER_NAME = "ch_apk";
    
    public static final String KEY_NAME_DOWNLOAD_ID = "downloadId";
    
    public static final String MMIMETYPE = "application/cn.changhong.download.file";
    
    @Override
    public void initData()
    {
        logic = LoginLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.activity_start_loading);
        //        checkUpdate();
        mHandler.postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                finish();
                //                
                Intent intent = new Intent(getBaseContext(),
                        CitySelectActivity.class);
                intent.putExtra("typeSrc", 0);
                //                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent, true);
            }
        }, 3000);
    }
    
    public OnClickListener ok = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            //版本不匹配下载apk退出当前界面
            download(logic.vi);
            finish();
        }
    };
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MsgWhat.MSGWHAT_FOUNDATION_CHECK_UPDATE_SUCCESS:
            {
                
                BusinessInfo plugin = getPackageInfo(null);
                if (null != plugin)
                {
                    if (!plugin.getVersionName()
                            .equals(logic.vi.getVersionCode()))
                    {
                        showAlertDialog(0,
                                getString(R.string.tip),
                                getString(R.string.tip_to_update),
                                null,
                                ok,
                                null,
                                null,
                                false,
                                false);
                        
                    }
                    else
                    {
                        //                        finish();
                        //                        startActivity(new Intent(getBaseContext(),
                        //                                LoginActivity.class), true);
                    }
                }
                
                break;
            }
            case CONNECT_ERROR_MSGWHAT:
            {
                showToast(getString(R.string.error_net));
                finish();
                break;
            }
            case DATA_FORMAT_ERROR_MSGWHAT:
            {
                showToast(getString(R.string.date_format_error));
                finish();
                break;
            }
        }
        dismissProgress();
    }
    
    /**
     * 
     * 版本检查更新
     * [功能详细描述]
     */
    public void checkUpdate()
    {
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestCheckUpdate(httpUtils);
    }
    
    class CompleteReceiver extends BroadcastReceiver
    {
        
        @Override
        public void onReceive(Context context, Intent intent)
        {
            /**
             * get the id of download which have download success, if the id is my id and it's status is successful,
             * then install it
             **/
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,
                    -1);
            if (completeDownloadId == downloadId)
            {
                initData();
                // if download successful, install apk
                if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL)
                {
                    String fileName = downloadManagerPro.getFileName(downloadId);
                    
                    install(context, fileName);
                }
            }
        }
    };
    
    @Override
    public void clearData()
    {
        
    }
    
    public void initDownloaderDir()
    {
        File folder = new File(CHUtils.getDiskCacheDir(getBaseContext(),
                DOWNLOAD_FOLDER_NAME));
        if (!folder.exists() || !folder.isDirectory())
        {
            folder.mkdirs();
        }
    }
    
    public void initDownload()
    {
        /**
         * get download id from preferences.<br/>
         * if download id bigger than 0, means it has been downloaded, then query status and show right text;
         */
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManagerPro = new DownloadManagerPro(downloadManager);
        downloadId = PreferencesUtils.getLong(getBaseContext(),
                KEY_NAME_DOWNLOAD_ID);
        completeReceiver = new CompleteReceiver();
        /** register download success broadcast **/
        registerReceiver(completeReceiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        initDownloaderDir();
    }
    
    @SuppressLint("NewApi")
    public void download(VersionInfo version)
    {
        initDownload();
        AppLog.out(TAG,
                "更新地址:" + HttpUrl.OSS + version.getSourceUrl(),
                AppLog.LEVEL_INFO);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(HttpUrl.OSS + version.getSourceUrl()));
        request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME,
                version.getVersionName());
        request.setTitle(version.getVersionName());
        request.setDescription(getString(R.string.app_name));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        //        request.setMimeType(MMIMETYPE);
        downloadId = downloadManager.enqueue(request);
        /** save download id to preferences **/
        PreferencesUtils.putLong(getBaseContext(),
                KEY_NAME_DOWNLOAD_ID,
                downloadId);
        //        finish();
    }
    
    @Override
    protected void onDestroy()
    {
        if (null != completeReceiver)
        {
            unregisterReceiver(completeReceiver);
            completeReceiver = null;
        }
        super.onDestroy();
    }
}
