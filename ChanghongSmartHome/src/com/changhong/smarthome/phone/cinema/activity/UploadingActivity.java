package com.changhong.smarthome.phone.cinema.activity;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.UploadTask;
import com.changhong.smarthome.phone.cinema.adapter.LocalCinemaAdapter;
import com.changhong.smarthome.phone.cinema.entry.LocalVideoInfo;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.AddShareSuccessLogic;
import com.changhong.smarthome.phone.cinema.logic.GetUploadInfoLogic;
import com.changhong.smarthome.phone.cinema.logic.MediaShareFtpInfo;

/**
* @ClassName: UploadingActivity
* @author yang_jun
* @Description: 上传界面
*/
public class UploadingActivity extends SuperActivity implements OnClickListener
{
    ProgressDialog progressDialog;
    
    private static final String TAG = "UploadingActivity";
    
    private ImageView imageThumbnail;
    
    LocalCinemaAdapter localAdapter;
    
    private static List<LocalVideoInfo> allVideoList = new ArrayList<LocalVideoInfo>();
    
    MediaShareFtpInfo ftpInfo = null;
    
    private GridView gridviews;
    
    private TextView titletext;
    
    private ImageView exitImg1;
    
    private TextView exitImg2;//完成按钮
    
    private GetUploadInfoLogic getUploadInfoLogic;// 上传logic
    
    private AddShareSuccessLogic addShareSuccessLogic;
    
    int gridViewPosition = -1;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploading);
        
        initWidgets();
        
        // scan videos
        if(allVideoList.size()>0){
            allVideoList.clear();
        }
        
        getVideoFile(allVideoList, Environment.getExternalStorageDirectory());
        
        if (!allVideoList.isEmpty())
        {
            Log.i(TAG, "allVideoList.size():" + allVideoList.size());
            localAdapter = new LocalCinemaAdapter(this, allVideoList);
            gridviews.setAdapter(localAdapter);
            gridviews.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    gridViewPosition = position;
                    localAdapter.setIsSelected(true, position);
                }
                
            });
        }
        
    }
    
    private void initWidgets()
    {
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg2 = (TextView) findViewById(R.id.exitImg2);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        gridviews = (GridView) findViewById(R.id.gridviews);
        getUploadInfoLogic.setData(mHandler);
        getUploadInfoLogic.requestPostUploadInfo();
        
        int titleLogo = this.getIntent().getIntExtra("titleLogo", 0);
        
        if (titleLogo == R.id.uploading)
        {
            titletext.setText("请选择视频");
        }
        exitImg1.setOnClickListener(this);
        exitImg2.setClickable(true);
        exitImg2.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.exitImg1:
                finish();
                break;
            case R.id.exitImg2:
                //先判断 ，然后开始上传视频
                //判断有无视频
                if (allVideoList == null)
                {
                    showToast("sorry ,there is no videos on your sdcard");
                    return;
                }
                
                //判断与服务器链接是否良好
                if (ftpInfo == null)
                {
                    Log.i(TAG, "------ftpInfo ==null ,cannot upload movie");
                    showToast("sorry ,cannot get upload info,please try again later");
                    return;
                }
                //有无选中一个视频
                if (gridViewPosition == -1)
                {
                    showToast("Please choose at least one video");
                    return;
                }
                progressDialog = ProgressDialog.show(this,
                        "uploading",
                        "Please wait.....");
                LocalVideoInfo video = (LocalVideoInfo) localAdapter.getItem(gridViewPosition);
                final UploadTask task = new UploadTask(video, ftpInfo, this,
                        addShareSuccessLogic, progressDialog);
                DialogInterface.OnDismissListener uploadDissmiss = new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialog)
                    {
                        task.cancel(true);
                    }
                };
                
                progressDialog.setCancelable(false);
                if (null != uploadDissmiss)
                {
                    progressDialog.setOnDismissListener(uploadDissmiss);
                }
                task.execute();
                break;
            default:
                break;
        }
        
    }
    
    private void getVideoFile(final List<LocalVideoInfo> list, File file)
    {// 获得视频文件  
        
        file.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                // sdCard找到视频名称  
                String name = file.getName();
                int i = name.indexOf('.');
                if (i != -1)
                {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".rmvb"))
                    {
                        LocalVideoInfo vi = new LocalVideoInfo();
                        vi.setDisplayName(file.getName());
                        vi.setPath(file.getAbsolutePath());
                        try
                        {
                            vi.setVideoBitMap(getBitmapsFromVideo(file));
                        }
                        catch (Exception e)
                        {
                        }
                        
                        list.add(vi);
                        
                        return true;
                    }
                }
                else if (file.isDirectory())
                {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        Log.i(TAG, "----------msg.what:" + msg.what);
        switch (msg.what)
        {
            case HttpAction.FTP_UPLOAD_INFO_SUCCESS_GET:
                Log.i(TAG, "001----successful get ftp upload server info");
                ftpInfo = getUploadInfoLogic.ftpInfo;
                if (ftpInfo == null)
                {
                    Log.i(TAG, "001---ftpInfo==null error happended");
                }
                else
                {
                    Log.i(TAG, "001---ftpInfo!=null ");
                }
                break;
            case DATA_FORMAT_ERROR_MSGWHAT:
                showToast("获取上传信息失败");
                break;
        }
        super.handleMsg(msg);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // getMenuInflater().inflate(R.menu.activity_cinema_start, menu);
        return true;
    }
    
    @Override
    public void initData()
    {
        if (null != GetUploadInfoLogic.getInstance())
        {
            getUploadInfoLogic = GetUploadInfoLogic.getInstance();
        }
        if (null != AddShareSuccessLogic.getInstance())
        {
            addShareSuccessLogic = AddShareSuccessLogic.getInstance();
        }
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    public void uploadFinished()
    {
        dismissDialog();
    }
    
    public Bitmap getBitmapsFromVideo(File file)
    {
        String dataPath = file.getAbsolutePath();
        ;
        Log.i(TAG, "----013dataPath:" + dataPath);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(dataPath);
        // 取得视频的长度(单位为毫秒)
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // 取得视频的长度(单位为秒)
        int seconds = Integer.valueOf(time) / 1000;
        // 得到第一秒时刻的bitmap
        Bitmap bitmap = retriever.getFrameAtTime(1,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        
        //save picture
        String name = file.getName();
        int a = name.indexOf('.');
        if (a != -1)
        {
            name = name.substring(0, a);
        }
        
        String path = file.getParent() + File.separator + name + ".jpg";
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(path);
            bitmap.compress(CompressFormat.JPEG, 80, fos);
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return bitmap;
    }
}
