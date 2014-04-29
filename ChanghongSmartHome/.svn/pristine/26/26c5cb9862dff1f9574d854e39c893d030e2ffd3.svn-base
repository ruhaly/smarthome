package com.changhong.smarthome.phone.cinema.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.HotCinemaAdapter;
import com.changhong.smarthome.phone.cinema.adapter.PopupAdapter;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.BillLogic;
import com.changhong.smarthome.phone.cinema.logic.GetCinemaDetailLogic;
import com.changhong.smarthome.phone.store.activity.SearchResultActivity;
import com.changhong.smarthome.phone.store.activity.StoreMainActivity;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.lidroid.xutils.HttpUtils;

/**
* @ClassName: HotCinemaActivity
* @author yang_jun
* @Description: 热门电影
*/
public class HotCinemaActivity extends CinemaSuperActivity implements
        OnClickListener
{
    private GridView gridviews;
    
    //title 整个布局
    private RelativeLayout exitLayout;
    
    private EditText seacrhing_park_et;
    
    //弹出窗口黑条
    private RelativeLayout allLoyout;
    
    private RelativeLayout arrowLoyout;
    
    private int screenWidth;
    
    private int screenHeight;
    
    private LinearLayout lineLayout;
    
    private TextView titletext;
    
    private TextView linetext;
    
    private ImageView exitImg1;
    
    private ImageView exitImg2;
    
    private Button buttonUpload;
    
    private Button buttonShare;
    
    private ImageView lineImg;
    
    private PopupWindow popupWindow;
    
    private View popupWindow_view;
    
    private PopupWindow searchPopupWindow;
    
    private View searchPopupWindowView;
    
    private ListView popup_listview;
    
    private int popupheight;
    
    private GetCinemaDetailLogic getCinemaDetailLogic;
    
    private RelativeLayout exitRelative;
    
    private HotCinemaAdapter hotAdapter;
    
    /**
     * isAlpha 
     * */
    private boolean isAlpha = false;
    
    private boolean isUpDown = false;
    
    private HttpUtils httpUtil;
    
    private Pager pager;
    
    private int columnId;
    
    private String titleLogo;
    
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
        hotAdapter = new HotCinemaAdapter(this,
                getCinemaDetailLogic.cinemaDetailList);
        /*final HotCinemaAdapter hotAdapter = new HotCinemaAdapter(this,
                BillLogic.getInstance().cinemaList);*/
        gridviews.setAdapter(hotAdapter);
        
        gridviews.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent intent_play = new Intent(HotCinemaActivity.this,
                        PlayerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cinema",
                        (Cinema) hotAdapter.getItem(position));
                bundle.putString("titleLogo", titleLogo);
                intent_play.putExtras(bundle);
                startActivity(intent_play);
                //                showProcessDialog(orderInfodismiss);
                //                requestOrderInfo(position);
            }
        });
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotcinema);
        initId();
        
        //8Ŀid
        columnId = this.getIntent().getIntExtra("columnId", 0);
        
        int queryType = this.getIntent().getIntExtra("queryType", 3);
        
        titleLogo = this.getIntent().getStringExtra("titleLogo");
        titletext.setText(titleLogo);
        
        gridviews = (GridView) findViewById(R.id.gridviews);
        
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
    
    private void initId()
    {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        exitImg2 = (ImageView) findViewById(R.id.exitImg2);
        
        lineImg = (ImageView) findViewById(R.id.lineImg);
        linetext = (TextView) findViewById(R.id.linetext);
        //init the pop window height and width
        
        //exitLayout
        
        arrowLoyout = (RelativeLayout) findViewById(R.id.tab_arrow_layout);
        arrowLoyout.setOnClickListener(this);
        lineLayout = (LinearLayout) findViewById(R.id.lineLayout);
        LinearLayout.LayoutParams radioGroupParams = (android.widget.LinearLayout.LayoutParams) lineLayout.getLayoutParams();
        radioGroupParams.height = ((screenHeight * 90) / 1280);
        radioGroupParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lineLayout.setLayoutParams(radioGroupParams);
        
        linetext.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        //下拉箭头
        RelativeLayout.LayoutParams params2 = (android.widget.RelativeLayout.LayoutParams) lineImg.getLayoutParams();
        params2.width = ((screenWidth * 19) / 720);
        params2.height = ((screenHeight * 13) / 1280);
        lineImg.setLayoutParams(params2);
        
        exitRelative = (RelativeLayout) findViewById(R.id.exitRelative);
        exitRelative.setOnClickListener(this);
        exitImg1.setOnClickListener(this);
        exitImg2.setOnClickListener(this);
        exitLayout = (RelativeLayout) findViewById(R.id.exitLayout);
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
        if (v.getId() == R.id.exitRelative || v.getId() == R.id.exitImg1)
        {
            finish();
        }
        else if (v.getId() == R.id.exitImg2)
        {
            //                showSearchPopwindow();
            showSearchPop(arrowLoyout.getHeight(),
                    HotCinemaActivity.this,
                    exitLayout);
        }
        else if (v.getId() == R.id.tab_arrow_layout)
        {
            Log.i(TAG, "show popwindow-----");
            //            isUpDown = false;
            //            isAlpha = true;
            //            getPopupWindow();
            showPopupWindow();
            
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
        //        
        
    }
    
    private void showSearchPopwindow()
    {
        //                searchPopupWindow;
        //              searchPopupWindowView;
        if (null != searchPopupWindow)
        {
            searchPopupWindow.dismiss();
        }
        searchPopupWindowView = getLayoutInflater().inflate(R.layout.cinema_search_pop,
                null);
        seacrhing_park_et = (EditText) searchPopupWindowView.findViewById(R.id.seacrhing_park_et);
        seacrhing_park_et.setHeight(arrowLoyout.getHeight());
        searchPopupWindowView.setFocusable(true);
        //)
        searchPopupWindow = new PopupWindow(searchPopupWindowView, screenWidth,
                screenHeight, true);
        searchPopupWindow.setFocusable(true);
        searchPopupWindowView.setOnTouchListener(new OnTouchListener()
        {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (searchPopupWindow != null && searchPopupWindow.isShowing())
                {
                    
                    searchPopupWindow.dismiss();
                    searchPopupWindow = null;
                    Log.d("onTouchPopup", "onTouch");
                }
                
                return false;
            }
        });
        
        if (seacrhing_park_et != null)
        {
            seacrhing_park_et.setOnEditorActionListener(new OnEditorActionListener()
            {
                
                @Override
                public boolean onEditorAction(TextView v, int actionId,
                        KeyEvent event)
                {
                    Log.i(TAG, "--------------001---actionId:" + actionId);
                    String key = seacrhing_park_et.getText().toString();
                    if (key != null && !key.equals(""))
                    {
                        Intent intent = new Intent(HotCinemaActivity.this,
                                SearchActivity.class);
                        intent.putExtra(StoreConstant.SEARCHKEY, key);
                        startActivity(intent);
                    }
                    else
                    {
                        showToast(getResources().getString(R.string.park_place_edit_hint));
                    }
                    
                    return false;
                }
            });
            
            Log.i(TAG, "------seacrhing_park_et-1=null");
        }
        else
        {
            Log.i(TAG, "------seacrhing_park_et-=========null");
        }
        searchPopupWindow.showAsDropDown(exitLayout);
        //        int[] location = new int[2];
        //        exitLayout.getLocationOnScreen(location);
        //        Log.i(TAG, "--------------location[1]:" + location[1]);
        //        searchPopupWindow.showAtLocation(exitLayout,
        //                Gravity.NO_GRAVITY,
        //                location[0] + screenWidth,
        //                location[1] + exitLayout.getHeight());
        //        searchPopupWindow.update();
        
    }
    
    /**
     * ��ʼ��popupWindow
     * */
    public void initPopupWindow()
    {
        popupWindow_view = getLayoutInflater().inflate(R.layout.pop_layout,
                null);
        popupWindow_view.setFocusable(true);
        
        popup_listview = (ListView) popupWindow_view.findViewById(R.id.popup_listview);
        PopupAdapter popupAdapter = new PopupAdapter(this,
                BillLogic.getInstance().titleList_complex);
        popup_listview.setAdapter(popupAdapter);
        
        popup_listview.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                linetext.setText(BillLogic.getInstance().titleList_complex.get(position)
                        .getTitle_name());
                showProcessDialog(dismiss);
                getCinemaDetailLogic.requestGetCinemaDetailList(columnId,
                        BillLogic.getInstance().titleList_complex.get(position)
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
        
        Log.i(TAG, "screenWidth:" + screenWidth);
        Log.i(TAG, "screenHeight:" + screenHeight);
        
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
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getCinemaDetailLogic.stopRequest();
        }
    };
    
    //orderInfodismiss
    DialogInterface.OnDismissListener orderInfodismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            Log.i(TAG,
                    "-------------------orderInfodismiss dialog was dismissed");
        }
    };
}
