/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-3 下午8:06:01 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.cinema.activity;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CenterLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.MediaOrderVO;
import com.changhong.smarthome.phone.cinema.fragment.MovieSelectionsFragment.OnMyButtonClickListener;
import com.changhong.smarthome.phone.cinema.view.MediaController;
import com.changhong.smarthome.phone.cinema.view.VideoView;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;

/**
* @ClassName: LocalVideoPlayingActivity
* @author yang_jun
* @Description: 本地电影，SD 卡
*/
public class LocalVideoPlayingActivity extends BaseActivity implements
        OnClickListener, OnMyButtonClickListener
{
    private VideoView surface_view;
    
    private CenterLayout centerLayout;
    
    String url;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.localvideoplay);
        url = getIntent().getStringExtra("url");
        if (url == null)
        {
            showToast("Sorry,this video cannot be played");
            finish();
            return;
        }
        
        initId();
        startPlaying();
    }
    
    /**
     * 
     */
    private void startPlaying()
    {
        Log.i(TAG, "--------paly url is:" + url);
        surface_view.setVideoPath(url);
        surface_view.setMediaController(new MediaController(this));
        surface_view.requestFocus();
        surface_view.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
        surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                Log.i(TAG, "-------001----------onprepared");
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                ViewGroup.LayoutParams params1 = surface_view.getLayoutParams();
                params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params1.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ;
                surface_view.setLayoutParams(params1);
                surface_view.setVideoLayout(1, 0);
                //                playalltime = surface_view.getDuration();
                
            }
        });
    }
    
    /**
     * 
     */
    private void initId()
    {
        centerLayout = (CenterLayout) findViewById(R.id.centerLayout);
        surface_view = (VideoView) findViewById(R.id.surface_view);
    }
    
    @Override
    public void initData()
    {
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @Override
    public void onMyButtonClick(String seqNum, String playUrl,
            MediaOrderVO mediaOrderInfo)
    {
        // TODO Auto-generated method stub
        
    }
    
}
