package com.changhong.foundation.broadcast;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.changhong.sdk.baseapi.AppLog;
import com.changhong.sdk.baseapi.PreferencesUtils;
import com.changhong.sdk.logic.DownloadLogic;

public class CompleteReceiver extends BroadcastReceiver
{
    
    public static final String TAG = "DownloadUtils";
    
    private static DownloadLogic dlLogic = DownloadLogic.getInstance();
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        /**
         * get the id of download which have download success, if the id is my id and it's status is successful,
         * then install it
         **/
        AppLog.out(TAG, intent.getAction(), AppLog.LEVEL_INFO);
        long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,
                -1);
        if (dlLogic.map.containsKey(completeDownloadId))
        {
            //            if (DownloadUtils.downloadManagerPro.getStatusById(completeDownloadId) == DownloadManager.STATUS_SUCCESSFUL)
            //            {
            //                    String fileName = downloadManagerPro.getFileName(downloadId);
            PreferencesUtils.removeByKey(context,
                    String.valueOf(dlLogic.map.get(completeDownloadId)));
            dlLogic.map.remove(String.valueOf(completeDownloadId));
            dlLogic.map.remove(dlLogic.map.get(completeDownloadId));
            //            }
        }
    }
}
