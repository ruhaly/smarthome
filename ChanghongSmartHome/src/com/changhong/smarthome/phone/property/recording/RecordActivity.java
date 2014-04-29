package com.changhong.smarthome.phone.property.recording;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;

public class RecordActivity extends Activity implements OnClickListener
{
    
    private TextView record;
    
    private MyDialog dialog;
    
    private AudioRecorder mr;
    
    private LinearLayout linear;
    
    private MediaPlayer mediaPlayer;
    
    private File directory;
    
    private Button btn = null;
    
    private Button player;
    
    private Button back;
    
    private Button save;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.record_audio);
        save = (Button) findViewById(R.id.save);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        player = (Button) findViewById(R.id.player);
        player.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mediaPlayer = new MediaPlayer();
                String url = "file:///sdcard/my/jankey.ram";
                try
                {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
                catch (IllegalArgumentException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IllegalStateException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        });
        record = (TextView) this.findViewById(R.id.record);
        linear = (LinearLayout) this.findViewById(R.id.showViews);
        record.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                mr = new AudioRecorder("jankey");
                dialog = new MyDialog(RecordActivity.this, "请说话...");
                try
                {
                    record.setText("正在录音...");
                    mr.start();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                dialog.show();
                return false;
            }
        });
        record.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        try
                        {
                            if (mr != null)
                            {
                              
                                mr.stop();
                             
                                record.setText("长按录音！");
                                if (mediaPlayer != null)
                                {
                                    mediaPlayer.stop();
                                    mediaPlayer.release();
                                    //mediaPlayer.setVolume(AudioManager.STREAM_MUSIC, AudioManager.STREAM_MUSIC);
                                }
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        if (dialog != null)
                        {
                            dialog.dismiss();
                            playFile();
                        }
                        break;
                }
                return false;
            }
        });
    }
    
    private void showView()
    {
        System.out.println("playering..................");
        for (int i = 0; i < apklist.size(); i++)
        {
            btn = new Button(this);
            btn.setBackgroundColor(Color.GRAY);
            btn.setWidth(200);
            btn.setHeight(50);
            btn.setText("什么玩意");
            btn.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    
                    mediaPlayer = new MediaPlayer();
                    try
                    {
                        mediaPlayer.setDataSource(apklist + "");
                        mediaPlayer.prepareAsync();
                    }
                    catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                    }
                    catch (SecurityException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalStateException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    btn.setText("不懂");
                }
            });
            linear.addView(btn);
        }
    }
    
    private void playFile()
    {
        List<String> getFiles = GetFiles(Environment.getExternalStorageDirectory()
                + "/" + "my",
                ".ram",
                true);
        for (String string : getFiles)
        {
            System.out.println(string);
        }
    }
    
    private List<String> apklist = new ArrayList<String>();
    
    public List<String> GetFiles(String Path, String Extension,
            boolean IsIterative)
    {
        File[] files = new File(Path).listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File f = files[i];
            if (f.isFile())
            {
                if (f.getPath()
                        .substring(f.getPath().length() - Extension.length())
                        .equals(Extension))
                    apklist.add(f.getPath());
                if (!IsIterative)
                    break;
            }
            else if (f.isDirectory() && f.getPath().indexOf("/.") == -1)
                GetFiles(f.getPath(), Extension, IsIterative);
        }
        return apklist;
    }
    
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.back)
        {
            finish();
        }
        else if (v.getId() == R.id.save)
        {
            Intent intentx = new Intent();
            intentx.putExtra("recordPath", "file:///sdcard/my/jankey.ram");
            this.setResult(9999, intentx);
            this.finish();
        }
        
    }
}
