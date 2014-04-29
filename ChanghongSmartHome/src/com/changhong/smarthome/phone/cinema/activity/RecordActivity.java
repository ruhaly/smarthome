package com.changhong.smarthome.phone.cinema.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.PopupAdapter;
import com.changhong.smarthome.phone.cinema.adapter.RecordAdapter;
import com.changhong.smarthome.phone.cinema.dao.DataHelper;
import com.changhong.smarthome.phone.cinema.dao.SimpleDataDao;
import com.changhong.smarthome.phone.cinema.dao.SimpleDataDaoImpl;
import com.changhong.smarthome.phone.cinema.entry.VideoFile;
import com.changhong.smarthome.phone.cinema.logic.BillLogic;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

/**
* @ClassName: RecordActivity
* @author yang_jun
* @Description: 播放历史
*/
public class RecordActivity extends CinemaSuperActivity implements
        OnClickListener
{
    private int screenWidth;
    
    private int screenHeight;
    
    private RelativeLayout exitLayout;
    
    private LinearLayout lineLayout;
    
    private GridView gridviews;
    
    private ListView listviews;
    
    private ArrayList<VideoFile> cinemaList;
    
    private TextView titletext;
    
    private ImageView exitImg1;
    
    private ImageView exitImg2;
    
    private ImageView lineImg;
    
    private Boolean isUpDown;
    
    private Boolean isAlpha;
    
    private PopupWindow popupWindow;
    
    private View popupWindow_view;
    
    private ListView popup_listview;
    
    private TextView linetext;
    
    private int popupheight;
    
    private RelativeLayout exitRelative;
    
    private RelativeLayout arrowLoyout;
    
    private SimpleDataDao simpledataDao;
    
    private Dao<VideoFile, String> simpledatadao;
    
    private DataHelper dataHelper = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotcinema);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        exitLayout = (RelativeLayout) findViewById(R.id.exitLayout);
        arrowLoyout = (RelativeLayout) findViewById(R.id.tab_arrow_layout);
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        exitImg2 = (ImageView) findViewById(R.id.exitImg2);
        linetext = (TextView) findViewById(R.id.linetext);
        lineImg = (ImageView) findViewById(R.id.lineImg);
        arrowLoyout = (RelativeLayout) findViewById(R.id.tab_arrow_layout);
        exitRelative = (RelativeLayout) findViewById(R.id.exitRelative);
        //init the 黑色的下拉框
        lineLayout = (LinearLayout) findViewById(R.id.lineLayout);
        LinearLayout.LayoutParams radioGroupParams = (android.widget.LinearLayout.LayoutParams) lineLayout.getLayoutParams();
        radioGroupParams.height = ((screenHeight * 90) / 1280);
        radioGroupParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lineLayout.setLayoutParams(radioGroupParams);
        linetext.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        RelativeLayout.LayoutParams params2 = (android.widget.RelativeLayout.LayoutParams) lineImg.getLayoutParams();
        params2.width = ((screenWidth * 19) / 720);
        params2.height = ((screenHeight * 13) / 1280);
        lineImg.setLayoutParams(params2);
        
        lineImg.setOnClickListener(this);
        exitImg1.setOnClickListener(this);
        exitImg2.setOnClickListener(this);
        exitRelative.setOnClickListener(this);
        arrowLoyout.setOnClickListener(this);//弹出窗口
        int titleLogo = this.getIntent().getIntExtra("titleLogo", 0);
        
        if (titleLogo == R.id.record)
        {
            titletext.setText(getResources().getString(R.string.record));
        }
        
        gridviews = (GridView) findViewById(R.id.gridviews);
        listviews = (ListView) findViewById(R.id.listviews);
        gridviews.setVisibility(View.GONE);
        listviews.setVisibility(View.VISIBLE);
        checkhistory();
        
    }
    
    private void checkhistory()
    {
        simpledataDao = new SimpleDataDaoImpl();
        simpledatadao = getHelper().getSimpleDataDao();
        
        try
        {
            cinemaList = (ArrayList<VideoFile>) simpledataDao.findallSimpleData(simpledatadao);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RecordAdapter hotAdapter = new RecordAdapter(this, cinemaList);
        listviews.setAdapter(hotAdapter);
        listviews.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                /*  Intent  intent = new Intent();
                  intent.putExtra("time",  cinemaList.get(position).getTime());
                  intent.putExtra("playUrl", cinemaList.get(position).getPath());
                  intent.setClass(RecordActivity.this, RecordPlayerActivity.class);
                  startActivity(intent);
                  overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);*/
                
            }
        });
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
    public void onClick(View v)
    
    {
        if (v.getId() == R.id.lineImg || v.getId() == R.id.tab_arrow_layout)
        {
            showPopupWindow();
            //            isUpDown = false;
            //            isAlpha = true;
            //            getPopupWindow();
            //            popupWindow.showAsDropDown(arrowLoyout);
            //            if (popupWindow != null && popupWindow.isShowing())
            //            {
            //                lineImg.setBackgroundResource(R.drawable.drawable_expand_close);
            //            }
            //            else
            //            {
            //                lineImg.setBackgroundResource(R.drawable.drawable_expand_open);
            //            }
        }
        else if (v.getId() == R.id.exitImg1 || v.getId() == R.id.exitRelative)
        {
            
            finish();
            
        }
        else if (v.getId() == R.id.exitImg2)
        {
            showSearchPop(arrowLoyout.getHeight(),
                    RecordActivity.this,
                    exitLayout);
            //            Intent intent = new Intent();
            //            intent.setClass(RecordActivity.this, SearchActivity.class);
            //            startActivity(intent);
            //            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
        
    }
    
    /**
     * 
     */
   
    
    private void showPopupWindow()
    {
        if (null != popupWindow)
        {
            popupWindow.dismiss();
        }
        popupWindow_view = getLayoutInflater().inflate(R.layout.pop_layout,
                null);
        popupWindow_view.setFocusable(true);
        
        popup_listview = (ListView) popupWindow_view.findViewById(R.id.popup_listview);
        PopupAdapter popupAdapter = new PopupAdapter(this,
                BillLogic.getInstance().titleList_easy);
        popup_listview.setAdapter(popupAdapter);
        popup_listview.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                linetext.setText(BillLogic.getInstance().titleList_easy.get(position)
                        .getTitle_name());
                
                if (popupWindow != null && popupWindow.isShowing())
                {
                    
                    popupWindow.dismiss();
                    popupWindow = null;
                    Log.d("onItemClick", "onItemClick");
                }
                
            }
        });
        popupWindow = new PopupWindow(popupWindow_view,
                screenWidth * 360 / 720, screenHeight * 345 / 1280, true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        
        popupWindow_view.setOnTouchListener(new View.OnTouchListener()
        {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (popupWindow != null && popupWindow.isShowing())
                {
                    
                    popupWindow.dismiss();
                    popupWindow = null;
                    Log.d("onTouchPopup", "onTouch");
                }
                
                return false;
            }
        });
        //        popupWindow.showAsDropDown(lineImg);
        int[] location = new int[2];
        arrowLoyout.getLocationOnScreen(location);
        popupWindow.showAtLocation(arrowLoyout,
                Gravity.NO_GRAVITY,
                location[0],
                location[1] + arrowLoyout.getHeight());
        popupWindow.update();
        
    }
    
}
