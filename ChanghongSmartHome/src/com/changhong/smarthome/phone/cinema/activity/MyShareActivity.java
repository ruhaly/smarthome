package com.changhong.smarthome.phone.cinema.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.MyVideoShareAdapter;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.MyVideoItem;
import com.changhong.smarthome.phone.cinema.entry.MyVideoShareVO;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.GetMyVideoShareLogic;


/**
* @ClassName: MyShareActivity
* @author yang_jun
* @Description: 我的分享，显示我上传的电影
*/
public class MyShareActivity extends SuperActivity implements OnClickListener
{
    private List<MyVideoShareVO> voList;
    
    private GetMyVideoShareLogic logic;
    
    private GridView gridviews;
    
    private TextView titletext;
    
    private ImageView exitImg1;
    
    private ImageView exitImg2;
    
    private RelativeLayout exitRelative;
    
    MyVideoShareAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myvideoshare);
        
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        exitImg2 = (ImageView) findViewById(R.id.exitImg2);
        gridviews = (GridView) findViewById(R.id.gridviews);
        exitRelative = (RelativeLayout) findViewById(R.id.exitRelative);
        logic.setHandler(mHandler);
        logic.requestGetMyShareList();
        
        titletext.setText(R.string.share);
        
        
        exitImg2.setVisibility(View.GONE);
        
        exitRelative.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.MEDIA_MY_SHARE:
                initDataMyShare();
                break;
        }
        super.handleMsg(msg);
        
    }
    
    /**
     * 
     */
    private void initDataMyShare()
    {
        if (logic.vo != null && logic.vo.getMpsResult() == 0)
        {
            
            adapter = new MyVideoShareAdapter(MyShareActivity.this, logic.vo);
            gridviews.setAdapter(adapter);
            gridviews.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {//TODO start play video
                    Intent intent_play = new Intent(MyShareActivity.this,
                            LocalVideoPlayingActivity.class);
                    MyVideoItem item = (MyVideoItem) adapter.getItem(position);
                    intent_play.putExtra("url", item.getMediaURL());
                    startActivity(intent_play);
                    
                }
            });
        }
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetMyVideoShareLogic.getInstance())
        {
            logic = GetMyVideoShareLogic.getInstance();
        }
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        
    }
    
    @Override
    public void clearData()
    {
        
    }
    
}