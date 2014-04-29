package com.changhong.smarthome.phone.cinema.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.changhong.smarthome.phone.cinema.adapter.ChildCinemaAdapter;
import com.changhong.smarthome.phone.cinema.adapter.PopupAdapter;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.BillLogic;
import com.changhong.smarthome.phone.cinema.logic.GetCinemaDetailLogic;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.lidroid.xutils.HttpUtils;

/**
* @ClassName: ChildCinemaActivity
* @author yang_jun
* @Description：儿童影院
*/
public class ChildCinemaActivity extends CinemaSuperActivity implements
        OnClickListener
{
    //
    private RelativeLayout arrowLoyout;
    
    private RelativeLayout exitLayout;
    
    private GridView gridviews;
    
    private TextView titletext;
    
    private ImageView exitImg1;
    
    private ImageView exitImg2;
    
    private GetCinemaDetailLogic getCinemaDetailLogic;
    
    private ChildCinemaAdapter childAdapter;
    
    private HttpUtils httpUtil;
    
    private ImageView lineImg;
    
    private Boolean isUpDown;
    
    private Boolean isAlpha;
    
    private PopupWindow popupWindow;
    
    private View popupWindow_view;
    
    private ListView popup_listview;
    
    private TextView linetext;
    
    private int popupheight;
    
    private Pager pager;
    
    private int columnId;
    
    private RelativeLayout exitRelative;
    
    private RelativeLayout tab_arrow_layout;
    
    private LinearLayout lineLayout;
    
    private String titleLogo;
    
    //屏幕的高度和宽度
    private int screenWidth;
    
    private int screenHeight;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childetccinema);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        exitImg2 = (ImageView) findViewById(R.id.exitImg2);
        lineImg = (ImageView) findViewById(R.id.lineImg);
        tab_arrow_layout = (RelativeLayout) findViewById(R.id.tab_arrow_layout);
        linetext = (TextView) findViewById(R.id.linetext);
        exitLayout = (RelativeLayout) findViewById(R.id.exitLayout);
        arrowLoyout = (RelativeLayout) findViewById(R.id.tab_arrow_layout);
        lineLayout = (LinearLayout) findViewById(R.id.lineLayout);
        LinearLayout.LayoutParams radioGroupParams = (android.widget.LinearLayout.LayoutParams) lineLayout.getLayoutParams();
        radioGroupParams.height = ((screenHeight * 90) / 1280);
        radioGroupParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lineLayout.setLayoutParams(radioGroupParams);
        
        linetext.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        //下拉箭头
        RelativeLayout.LayoutParams params2 = (android.widget.RelativeLayout.LayoutParams) lineImg.getLayoutParams();
        params2.width = ((screenWidth * 19) / 720);
        params2.height = ((screenHeight * 13) / 1280);
        lineImg.setLayoutParams(params2);
        
        lineImg.setOnClickListener(this);
        
        exitRelative = (RelativeLayout) findViewById(R.id.exitRelative);
        exitRelative.setOnClickListener(this);
        tab_arrow_layout.setOnClickListener(this);
        
        columnId = this.getIntent().getIntExtra("columnId", 0);
        int queryType = this.getIntent().getIntExtra("queryType", 3);
        
        titleLogo = this.getIntent().getStringExtra("titleLogo");
        titletext.setText(titleLogo);
        
        gridviews = (GridView) findViewById(R.id.gridviews);
        
        exitImg2.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                showSearchPop(arrowLoyout.getHeight(),
                        ChildCinemaActivity.this,
                        exitLayout);
                //                Intent intent = new Intent();
                //                intent.setClass(ChildCinemaActivity.this, SearchActivity.class);
                //                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                //                startActivity(intent);
            }
        });
        
        httpUtil = new HttpUtils();
        showProcessDialog(dismiss);
        getCinemaDetailLogic.setData(mHandler);
        pager = new Pager();
        pager.setPageId(1);
        pager.setPageSize(20);
        getCinemaDetailLogic.requestGetCinemaDetailList(columnId,
                "8",
                pager,
                httpUtil);
        
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.CINEMADETAIL_SUCCESS_GET:
                
                initDataCinemaDetail();
                break;
        }
        super.handleMsg(msg);
        
    }
    
    private void initDataCinemaDetail()
    {
        childAdapter = new ChildCinemaAdapter(this,
                getCinemaDetailLogic.cinemaDetailList);
        gridviews.setAdapter(childAdapter);
        gridviews.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent intent_play = new Intent(ChildCinemaActivity.this,
                        PlayerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cinema",
                        (Cinema) childAdapter.getItem(position));
                bundle.putString("titleLogo", titleLogo);
                intent_play.putExtras(bundle);
                startActivity(intent_play);
                
            }
        });
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
        if (null != GetCinemaDetailLogic.getInstance())
        {
            getCinemaDetailLogic = GetCinemaDetailLogic.getInstance();
        }
        
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
    
    //this was called when dialog was dissmissed
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getCinemaDetailLogic.stopRequest();
        }
    };
    
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.lineImg || v.getId() == R.id.tab_arrow_layout)
        {
            //            isUpDown = false;
            //            isAlpha = true;
            //            getPopupWindow();
            //            popupWindow.setOutsideTouchable(false);
            //            popupWindow.setFocusable(true);
            //            popupWindow.showAsDropDown(tab_arrow_layout);
            showPopupWindow();
        }
        else if (v.getId() == R.id.exitRelative || v.getId() == R.id.exitImg1)
        {
            
            finish();
        }
    }
    
    private void showPopupWindow()
    {
        if (null != popupWindow)
        {
            popupWindow.dismiss();
            //            return;
        }
        initPopupWindow();
    }
    
    /**
     * init popupWindow
     * */
    public void initPopupWindow()
    {
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
                showProcessDialog(dismiss);
                getCinemaDetailLogic.requestGetCinemaDetailList(columnId,
                        BillLogic.getInstance().titleList_easy.get(position)
                                .getTitle_id(),
                        pager,
                        httpUtil);
                
                if (popupWindow != null && popupWindow.isShowing())
                {
                    
                    popupWindow.dismiss();
                    popupWindow = null;
                    Log.d("onItemClick", "onItemClick");
                }
                
            }
        });
        
        //        ViewTreeObserver vto = popup_listview.getViewTreeObserver();
        //        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        //        {
        //            public boolean onPreDraw()
        //            {
        //                popupheight = popup_listview.getMeasuredHeight();
        //                
        //                return true;
        //            }
        //        });
        popupWindow = new PopupWindow(popupWindow_view,
                screenWidth * 360 / 720, screenHeight * 345 / 1280, true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        //        if (!isUpDown)
        //        {
        //            
        //            popupWindow.setAnimationStyle(R.style.AnimationFade);
        //        }
        //        
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
        int[] location = new int[2];
        tab_arrow_layout.getLocationOnScreen(location);
        popupWindow.showAtLocation(tab_arrow_layout,
                Gravity.NO_GRAVITY,
                location[0],
                location[1] + tab_arrow_layout.getHeight());
        popupWindow.update();
    }
    
}
