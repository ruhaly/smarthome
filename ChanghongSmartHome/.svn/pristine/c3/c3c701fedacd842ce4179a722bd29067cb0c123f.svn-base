package com.changhong.smarthome.phone.cinema.activity;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.Window;

import com.changhong.sdk.baseapi.AppLog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.dao.DataHelper;
import com.changhong.smarthome.phone.cinema.dao.SimpleDataDao;
import com.changhong.smarthome.phone.cinema.dao.SimpleDataDaoImpl;
import com.changhong.smarthome.phone.cinema.entry.VideoFile;
import com.changhong.smarthome.phone.cinema.view.MediaController;
import com.changhong.smarthome.phone.cinema.view.VideoView;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

/**
* @ClassName: RecordPlayerActivity
* @author yang_jun
* @Description: 历史播放记录
*/
public class RecordPlayerActivity extends CinemaSuperActivity implements
        OnClickListener
{
    
    private VideoView surface_view;
    
    private DataHelper dataHelper = null;
    
    private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
    
    private long playtime;
    
    private String playUrl;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recordplayer);
        playtime = this.getIntent().getLongExtra("time", 0);
        playUrl = this.getIntent().getStringExtra("playUrl");
        surface_view = (VideoView) findViewById(R.id.surface_view);
        
        surface_view.setVideoPath(playUrl);
        surface_view.setMediaController(new MediaController(this));
        surface_view.requestFocus();
        surface_view.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
        surface_view.setVideoLayout(1, 0);
        
        surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                // surface_view.seekTo(playtime);
                
            }
        });
        
    }
    
    private void checkhistory()
    {
        SimpleDataDao simpledataDao = new SimpleDataDaoImpl();
        Dao<VideoFile, String> simpledatadao = getHelper().getSimpleDataDao();
        
        // simpledataDao.addSimpleData(simpledatadao, id, contentId, mediaId, name, path, time);
    }
    
    private DataHelper getHelper()
    {
        if (dataHelper == null)
        {
            dataHelper = OpenHelperManager.getHelper(this, DataHelper.class);
        }
        return dataHelper;
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
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (dataHelper != null)
        {
            OpenHelperManager.releaseHelper();
            dataHelper = null;
        }
        playtime = surface_view.getCurrentPosition() * 1000;
        
        surface_view.stopPlayback();
        
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            //do something
        }
        else if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            
            //do something
        }
        else if (keyCode == KeyEvent.KEYCODE_HOME)
        {
            
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
