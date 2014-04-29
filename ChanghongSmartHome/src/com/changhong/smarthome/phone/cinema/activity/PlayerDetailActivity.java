package com.changhong.smarthome.phone.cinema.activity;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CenterLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.sdk.baseapi.AppLog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.FragAdapter;
import com.changhong.smarthome.phone.cinema.dao.DataHelper;
import com.changhong.smarthome.phone.cinema.dao.SimpleDataDao;
import com.changhong.smarthome.phone.cinema.dao.SimpleDataDaoImpl;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.MediaOrderVO;
import com.changhong.smarthome.phone.cinema.entry.PriceVO;
import com.changhong.smarthome.phone.cinema.entry.VideoFile;
import com.changhong.smarthome.phone.cinema.fragment.MovieCommentFragment;
import com.changhong.smarthome.phone.cinema.fragment.MovieDetailFragment;
import com.changhong.smarthome.phone.cinema.fragment.MovieResourceFragment;
import com.changhong.smarthome.phone.cinema.fragment.MovieSelectionsFragment;
import com.changhong.smarthome.phone.cinema.fragment.MovieSelectionsFragment.OnMyButtonClickListener;
import com.changhong.smarthome.phone.cinema.view.MediaController;
import com.changhong.smarthome.phone.cinema.view.VideoView;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

/**
 * @ClassName: PlayerDetailActivity
 * @author yang_jun
 * @Description: 电影播放的详细界面
 */
public class PlayerDetailActivity extends CinemaSuperActivity implements
        OnClickListener, OnMyButtonClickListener, Runnable
{
    private boolean test = true;
    
    private FragAdapter adapter;
    
    private TextView notice_pay;// 支付提醒的text
    
    private TextView one_time_pay;// 按次提醒
    
    private TextView month_pay;// 按月
    
    private TextView year_pay;// 按年
    
    private TextView pay_cancel_btn;
    
    private ArrayList<Fragment> fragments;
    
    private ViewPager vp;
    
    private RelativeLayout exitLayout;
    
    private VideoView surface_view;
    
    private RelativeLayout main_Linear;
    
    private CenterLayout enterLayout;
    
    private ImageView exitImg1;
    
    private TextView movie_Detail;
    
    private TextView movie_Resource;
    
    private TextView movie_Selections;
    
    private TextView movie_Comment;
    
    private ImageView movie_Detail_i;
    
    private ImageView movie_Resource_i;
    
    private ImageView movie_Selections_i;
    
    private ImageView movie_Comment_i;
    
    private DataHelper dataHelper = null;
    
    private int surface_viewheight;
    
    private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
    
    private boolean needResume;
    
    private TextView mLoadingView;
    
    private long playtime;
    
    private long playalltime;
    
    private String passUrl;
    
    private String seqNum;
    
    private SimpleDataDao simpledataDao;
    
    private Dao<VideoFile, String> simpledatadao;
    
    private Cinema item;
    
    private TextView titletext;
    
    private ImageView exitImg2;
    
    private RelativeLayout exitRelative;
    
    private ImageView mLoadingImg;
    
    private MediaOrderVO mediaOrderInfo;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.playerdetail);
        initId();
        checkhistory();
        
        initViewPage();
        
    }
    
    private void checkhistory()
    {
        simpledataDao = new SimpleDataDaoImpl();
        simpledatadao = getHelper().getSimpleDataDao();
        
        // simpledataDao.addSimpleData(simpledatadao, id, contentId, mediaId,
        // name, path, time);
    }
    
    private DataHelper getHelper()
    {
        if (dataHelper == null)
        {
            dataHelper = OpenHelperManager.getHelper(this, DataHelper.class);
        }
        return dataHelper;
    }
    
    private void initId()
    {
        
        vp = (ViewPager) findViewById(R.id.player_content);
        exitLayout = (RelativeLayout) findViewById(R.id.exitLayout);
        enterLayout = (CenterLayout) findViewById(R.id.centerLayout);
        surface_view = (VideoView) findViewById(R.id.surface_view);
        main_Linear = (RelativeLayout) findViewById(R.id.main_Linear);
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg2 = (ImageView) findViewById(R.id.exitImg2);
        exitRelative = (RelativeLayout) findViewById(R.id.exitRelative);
        
        movie_Detail = (TextView) findViewById(R.id.movie_Detail);
        movie_Resource = (TextView) findViewById(R.id.movie_Resource);
        movie_Selections = (TextView) findViewById(R.id.movie_Selections);
        movie_Comment = (TextView) findViewById(R.id.movie_Comment);
        
        movie_Detail_i = (ImageView) findViewById(R.id.movie_Detail_i);
        movie_Resource_i = (ImageView) findViewById(R.id.movie_Resource_i);
        movie_Selections_i = (ImageView) findViewById(R.id.movie_Selections_i);
        movie_Comment_i = (ImageView) findViewById(R.id.movie_Comment_i);
        
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        mLoadingView = (TextView) findViewById(R.id.mLoadingView);
        mLoadingImg = (ImageView) findViewById(R.id.mLoadingImg);
        
        movie_Detail.setOnClickListener(this);
        movie_Resource.setOnClickListener(this);
        movie_Selections.setOnClickListener(this);
        movie_Comment.setOnClickListener(this);
        exitImg1.setOnClickListener(this);
        exitImg2.setOnClickListener(this);
        exitRelative.setOnClickListener(this);
        
    }
    
    private void initViewPage()
    {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        Intent intent = getIntent();
        item = (Cinema) intent.getSerializableExtra("cinema");
        String titleLogo = intent.getStringExtra("titleLogo");
        
        Bundle bundle_contentid = new Bundle();
        bundle_contentid.putLong("contentId", item.getId());
        titletext.setText(titleLogo);
        
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle_contentid);
        fragments.add(movieDetailFragment);
        
        MovieResourceFragment movieResourceFragment = new MovieResourceFragment();
        movieResourceFragment.setArguments(bundle_contentid);
        fragments.add(movieResourceFragment);
        
        MovieSelectionsFragment movieSelectionsFragment = new MovieSelectionsFragment();
        movieSelectionsFragment.setArguments(bundle_contentid);
        fragments.add(movieSelectionsFragment);
        
        MovieCommentFragment movieCommentFragment = new MovieCommentFragment();
        movieCommentFragment.setArguments(bundle_contentid);
        fragments.add(movieCommentFragment);
        
        adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(4);
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(new MyVPageChangeListener());
        
    }
    
    private class MyVPageChangeListener implements OnPageChangeListener
    {
        
        @Override
        public void onPageScrollStateChanged(int count)
        {
            // TODO Auto-generated method stub
            // vp.setCurrentItem(count);
            
        }
        
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void onPageSelected(int location)
        {
            changeColor(location);
        }
        
    }
    
    private void changeColor(int location)
    {
        // ��ϸҳ�汻ѡ��
        if (location == 0)
        {
            movie_Detail_i.setBackgroundResource(R.drawable.classify_select);
            movie_Resource_i.setBackgroundResource(0);
            movie_Selections_i.setBackgroundResource(0);
            movie_Comment_i.setBackgroundResource(0);
        }
        // ��Դҳ�汻ѡ��
        else if (location == 1)
        {
            movie_Detail_i.setBackgroundResource(0);
            movie_Resource_i.setBackgroundResource(R.drawable.classify_select);
            movie_Selections_i.setBackgroundResource(0);
            movie_Comment_i.setBackgroundResource(0);
        }
        // ѡ��ҳ�汻ѡ��
        else if (location == 2)
        {
            movie_Detail_i.setBackgroundResource(0);
            movie_Resource_i.setBackgroundResource(0);
            movie_Selections_i.setBackgroundResource(R.drawable.classify_select);
            movie_Comment_i.setBackgroundResource(0);
        }
        else if (location == 3)
        {
            movie_Detail_i.setBackgroundResource(0);
            movie_Resource_i.setBackgroundResource(0);
            movie_Selections_i.setBackgroundResource(0);
            movie_Comment_i.setBackgroundResource(R.drawable.classify_select);
        }
        
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.movie_Detail:
                changeColor(0);
                vp.setCurrentItem(0);
                break;
            case R.id.movie_Resource:
                changeColor(1);
                vp.setCurrentItem(1);
                break;
            case R.id.movie_Selections:
                changeColor(2);
                vp.setCurrentItem(2);
                break;
            case R.id.movie_Comment:
                changeColor(3);
                vp.setCurrentItem(3);
                break;
            case R.id.exitImg1:
            case R.id.exitRelative:
                finish();
                break;
            case R.id.exitImg2:
                showSearchPop(0, PlayerDetailActivity.this, exitLayout);
                //			Intent intent = new Intent();
                //			intent.setClass(PlayerDetailActivity.this, SearchActivity.class);
                //			startActivity(intent);
                //			overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                break;
            case R.id.one_time_pay:
            case R.id.month_pay:
            case R.id.year_pay:
                PriceVO vo = mediaOrderInfo.getPriceList().get(0);
                showPayDialog(vo.getId(), vo.getMoney(), vo.getStrategyName());
                break;
            default:
                break;
        }
        super.onClick(v);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        
        Log.i("change", "change");
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Log.i("landscape", "landscape");
            exitLayout.setVisibility(View.GONE);
            main_Linear.setVisibility(View.GONE);
            
            /*
             * LinearLayout.LayoutParams params2 = (LayoutParams)
             * enterLayout.getLayoutParams();
             * 
             * params2.width = ViewGroup.LayoutParams.MATCH_PARENT;
             * params2.height = ViewGroup.LayoutParams.MATCH_PARENT;
             * params2.topMargin = 0; params2.leftMargin = 0;
             * params2.rightMargin = 0; params2.bottomMargin = 0;
             * enterLayout.setLayoutParams(params2);
             */
            
            Log.i("landscapeenterLayout", "landscapeenterLayout");
            
            ViewGroup.LayoutParams params1 = surface_view.getLayoutParams();
            params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params1.height = ViewGroup.LayoutParams.MATCH_PARENT;
            
            surface_view.setLayoutParams(params1);
            surface_view.setVideoLayout(1, 0);
            
            Log.i("landscapesurface_view", "landscapesurface_view");
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            /*
             * getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
             * , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
             */
            Log.i("portrait", "portrait");
            
            /*
             * LinearLayout.LayoutParams params2 = (LayoutParams)
             * enterLayout.getLayoutParams();
             * 
             * params2.width = ViewGroup.LayoutParams.MATCH_PARENT;
             * params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
             * params2.leftMargin = 5; params2.rightMargin = 5;
             * params2.bottomMargin = 5; enterLayout.setLayoutParams(params2);
             */
            
            ViewGroup.LayoutParams params1 = surface_view.getLayoutParams();
            params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params1.height = surface_viewheight;
            surface_view.setLayoutParams(params1);
            surface_view.setVideoLayout(1, 0);
            exitLayout.setVisibility(View.VISIBLE);
            main_Linear.setVisibility(View.VISIBLE);
            
            // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onPause()
    {
        /*
         * if(surface_view.isPlaying()){ playtime =
         * surface_view.getCurrentPosition() * 1000;
         * System.out.println("playtime: "+playtime); }
         */
        
        if (passUrl != null && !passUrl.equals(""))
        {
            playtime = surface_view.getCurrentPosition() * 1000;
            System.out.println("playtime: " + playtime);
            int x = simpledataDao.addSimpleData(simpledatadao,
                    item.getId() + "",
                    path,
                    item.getContentName(),
                    playtime,
                    seqNum,
                    item.getPicUrl());
            if (x == 0)
            {
                simpledataDao.updateSimpleDataforid(simpledatadao,
                        new VideoFile(item.getId() + "", path,
                                item.getContentName(), playtime, seqNum,
                                item.getPicUrl()));
            }
            
        }
        AppLog.out("onPause()",
                "PlayerDetailActivity.onPause()",
                AppLog.LEVEL_ERROR);
        super.onPause();
    }
    
    /**
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj)
    {
        if (obj == null)
            return true;
        
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;
        
        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();
        
        if (obj instanceof Map)
            return ((Map) obj).isEmpty();
        
        if (obj instanceof Object[])
        {
            Object[] object = (Object[]) obj;
            if (object.length == 0)
            {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++)
            {
                if (!isNullOrEmpty(object[i]))
                {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }
    
    @Override
    protected void onDestroy()
    {
        
        if (dataHelper != null)
        {
            OpenHelperManager.releaseHelper();
            dataHelper = null;
        }
        AppLog.out("onDestroy()",
                "PlayerDetailActivity.onDestroy()",
                AppLog.LEVEL_ERROR);
        super.onDestroy();
        
        /*
         * System.out.println("playalltime: "+playalltime);
         * System.out.println("playtime: "+playtime);
         * if(surface_view.isPlaying()){ surface_view.stopPlayback(); }
         */
        
    }
    
    void setThreeMinutesPlaying()
    {
        final Handler handler = new Handler();
        handler.postDelayed(this, 5000);// 3minutes
    }
    
    private void showPayDialog()
    {
        dismissDialog();
        Log.i(TAG,
                "-------order info:"
                        + new Gson().toJson(mediaOrderInfo, MediaOrderVO.class));
        if (mediaOrderInfo == null || mediaOrderInfo.getPriceList() == null
                || mediaOrderInfo.getPriceList().isEmpty())
        {
            showToast("sorry this video cannot be played");
            finish();
            return;
        }
        else
        {
            
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.pop_layout_pay_movie, null);
            final Dialog dialog = getDialog(view, false, R.style.MyDialog);
            notice_pay = (TextView) view.findViewById(R.id.notice_pay);
            one_time_pay = (TextView) view.findViewById(R.id.one_time_pay);
            month_pay = (TextView) view.findViewById(R.id.month_pay);
            year_pay = (TextView) view.findViewById(R.id.year_pay);
            
            notice_pay.setText(Html.fromHtml("觀看該影片您需支付<font color=\"#ff0000\">2</font>元，获得20积分"));
            one_time_pay.setText("按次2元");
            month_pay.setText("包月10元");
            year_pay.setText("包年50元");
            pay_cancel_btn = (TextView) view.findViewById(R.id.pay_cancel_btn);
            pay_cancel_btn.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
            // ListView lv = (ListView) view.findViewById(R.id.popup_listview);
            /*
             * Button button = (Button) view.findViewById(R.id.btnCancel); final
             * PaymethodAdapter adapter = new PaymethodAdapter(this,
             * mediaOrderInfo); lv.setAdapter(adapter);
             * dialog.setCancelable(false); dialog.show();
             * button.setOnClickListener(new OnClickListener() {
             * 
             * @Override public void onClick(View v) { dialog.dismiss();
             * finish(); } }); lv.setOnItemClickListener(new
             * OnItemClickListener() {
             * 
             * @Override public void onItemClick(AdapterView<?> parent, View
             * view, int position, long id) { dialog.dismiss(); PriceVO vo =
             * (PriceVO) adapter.getItem(position); // 跳转到支付页面
             * showPayDialog(vo.getId(), vo.getMoney(), vo.getStrategyName()); }
             * 
             * });
             */
        }
    }
    
    private void showPayDialog(int id, float money, String strategyName)
    {
        View view = LayoutInflater.from(this).inflate(R.layout.pay_type, null);
        TextView payMoney = (TextView) view.findViewById(R.id.pay_money);
        LinearLayout unionPayLL = (LinearLayout) view.findViewById(R.id.union_pay_ll);
        LinearLayout aliPayLL = (LinearLayout) view.findViewById(R.id.alipay_pay_ll);
        LinearLayout communityPayLL = (LinearLayout) view.findViewById(R.id.community_pay_ll);
        TextView payCancelBtn = (TextView) view.findViewById(R.id.pay_cancel_btn);
        payMoney.setText(money + "");
        final Dialog dialog = getDialog(view, false, R.style.MyDialog);
        dialog.show();
        
        unionPayLL.setOnClickListener(this);
        aliPayLL.setOnClickListener(this);
        communityPayLL.setOnClickListener(this);
        payCancelBtn.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                finish();
            }
        });
        
    }
    
    public Dialog getDialog(View view, boolean hasTheme, int theme)
    {
        Dialog dialog = null;
        if (hasTheme)
        {
            dialog = new Dialog(this);
        }
        else
        {
            dialog = new Dialog(this, theme);
        }
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        dialog.getWindow().setAttributes(p);
        return dialog;
    }
    
    @Override
    public void onMyButtonClick(String seqNum1, String playUrl, MediaOrderVO vo)
    {
        this.mediaOrderInfo = vo;
        seqNum = seqNum1;
        passUrl = playUrl;
        mLoadingImg.setVisibility(View.GONE);
        surface_view.setVideoPath(playUrl);
        surface_view.setMediaController(new MediaController(this));
        surface_view.requestFocus();
        surface_view.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
        
        surface_viewheight = enterLayout.getHeight();
        
        surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                Log.i(TAG, "-----------------onprepared");
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                ViewGroup.LayoutParams params1 = surface_view.getLayoutParams();
                params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params1.height = surface_viewheight;
                surface_view.setLayoutParams(params1);
                surface_view.setVideoLayout(1, 0);
                playalltime = surface_view.getDuration();
                
                if (mediaOrderInfo != null && mediaOrderInfo.getMpResult() == 0)// 已付费，直接看
                {
                    Log.i(TAG, "------had paied for this movie");
                }
                else
                {
                    // setThreeMinutesPlaying();
                }
                
            }
        });
        
        /*
         * surface_view.setBufferSize(512 * 1024);
         * surface_view.setOnInfoListener(new OnInfoListener() {
         * 
         * @Override public boolean onInfo(MediaPlayer arg0, int arg1, int arg2)
         * { switch (arg1) { case MediaPlayer.MEDIA_INFO_BUFFERING_START:
         * surface_view.pause(); needResume = true; }
         * mLoadingView.setVisibility(View.VISIBLE); break; case
         * surface_view.start(); mLoadingView.setVisibility(View.GONE); break;
         * return true; } });
         */
        
    }
    
    @Override
    public void run()
    {
        if (surface_view != null)
        {
            surface_view.pause();
            showPayDialog();
        }
        
    }
}