package com.changhong.smarthome.phone.store.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.entity.BaseAccountInfo;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView.NewScrollerListener;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView.OnRefreshListener;
import com.changhong.smarthome.phone.store.adapter.AllshoppingAdapter;
import com.changhong.smarthome.phone.store.adapter.Expandadapter;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.MainLogic;
import com.changhong.smarthome.phone.store.logic.OrderDetailLogic;
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.changhong.smarthome.phone.store.logic.bean.PagerBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * [周边商圈主界面]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-23] 
 */
public class StoreMainActivity extends BaseActivity implements OnClickListener
{
    private final static String TAG = "MainActivity";
    
    private LinearLayout radioGroup;
    
    /**
     * 全部分类
     */
    private TextView allTextView;
    
    /**
     * 周边分类
     */
    private TextView nearTextView;
    
    private RelativeLayout allLoyout;
    
    private RelativeLayout nearLoyout;
    
    /**
     * 全部的小箭头
     */
    private TextView allarrowTextView;
    
    private TextView neararrowTextView;
    
    /**
     * 搜索框
     */
    private EditText searchEdit;
    
    private MainLogic mainLogic;
    
    /**
     * 标题栏
     */
    private StoreTitleItem titleItem;
    
    private AllshoppingAdapter adapter;
    
    private PullRefreshListView listView;
    
    private BitmapUtils mBitmapUtils;
    
    private HttpUtils httpUtils;
    
    private static final int PAGESIZE = 10;
    
    /**
     * 当前页
     */
    private int curPageIndex = 1;
    
    /**
     * 是否正在加载更多
     */
    private boolean isLoadingMore = false;
    
    /**
     * 是否有数据，能够加载更多
     */
    private boolean hasMore = true;
    
    /**
     * 加载模式： 0 第一次 ；1 刷新 ；2 加载更多
     */
    private int mode = 0;
    
    private int waresType;
    
    private int distanceType;
    
    private InputMethodManager imm;
    
    private List<GoodsDetailInfo> goodsDetailInfos;
    
    /**
     * 商品分类列表
     */
    private ListView expandableListView;
    
    /**
     * 商品分类adapater
     */
    private Expandadapter expandadapter;
    
    /**
     * 商品分类数据
     */
    //    private List<String> goodsColumnGroups;
    
    private List<String> allList;
    
    private List<String> nearList;
    
    protected PopupWindow mPopupWindow;
    
    private boolean isSearch;
    
    //    private String searchKey = "";
    
    @SuppressWarnings("unchecked")
    public void handleMsg(Message msg)
    {
        super.handleMsg(msg);
        listView.onRefreshComplete();
        listView.showLoadFinish();
        isLoadingMore = false;
        switch (msg.what)
        {
            case StoreConstant.NEAR_TYPE:
                if (mPopupWindow != null)
                {
                    mPopupWindow.dismiss();
                }
                if (msg.obj != null)
                {
                    distanceType = (Integer) msg.obj;
                    waresType = 0;
                    nearTextView.setText(nearList.get(distanceType));
                    findGoods();
                }
                break;
            case StoreConstant.ALL_TYPE:
                if (mPopupWindow != null)
                {
                    mPopupWindow.dismiss();
                }
                if (msg.obj != null)
                {
                    waresType = (Integer) msg.obj;
                    distanceType = 1;
                    allTextView.setText(allList.get(waresType));
                    findGoods();
                }
                break;
            case StoreConstant.GET_FINDGOODS_SUCCESS:
                if (msg.obj != null)
                {
                    List<GoodsDetailInfo> temp = (List<GoodsDetailInfo>) msg.obj;
                    if (temp.size() > 0)
                    {
                        goodsDetailInfos.clear();
                        goodsDetailInfos.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        hasMore = false;
                    }
                }
                break;
            case StoreConstant.GET_FINDGOODS_FAILED:
                showToast(getResources().getString(R.string.error_1));
                break;
            case StoreConstant.GET_FINDGOODS_GET_MORE_SUCCESS:
                if (msg.obj != null)
                {
                    List<GoodsDetailInfo> temp = (List<GoodsDetailInfo>) msg.obj;
                    if (temp.size() > 0)
                    {
                        goodsDetailInfos.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        hasMore = false;
                    }
                }
                break;
            case StoreConstant.GET_FINDGOODS_GET_MORE_FAILED:
                showToast(getResources().getString(R.string.error_1));
                break;
            //            case StoreConstant.GET_FINDGOODSCOLUMN_SUCCESS:
            //                if (msg.obj != null)
            //                {
            ////                    List<GoodsColumnGroup> temp = (List<GoodsColumnGroup>) msg.obj;
            ////                    goodsColumnGroups.addAll(temp);
            ////                    expandadapter.notifyDataSetChanged();
            //                    List<String> temp = new ArrayList<String>();
            //                    goodsColumnGroups.add("离我最近");
            //                    goodsColumnGroups.add("1km以内");
            //                    goodsColumnGroups.add("3km以内");
            //                    goodsColumnGroups.add("更远");
            //                    goodsColumnGroups.addAll(temp);
            //                    expandadapter.notifyDataSetChanged();
            //                }
            //                break;
            //            case StoreConstant.GET_FINDGOODSCOLUMN_FAILED:
            //                showToast(getResources().getString(R.string.error_1));
            //                break;
            //            case StoreConstant.GET_FINDGOODSCOLUMN_NO_DATA:
            //                showToast(getResources().getString(R.string.store_error_1));
            //                break;
        }
    };
    
    private void initListener()
    {
        //        subMenuBtn.setOnClickListener(this);
        
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
        titleItem.setOtherListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                //                Intent intent = new Intent(StoreMainActivity.this,
                //                        OrderManagerActivity.class);
                //                startActivity(intent);
                if (!isSearch)
                {
                    isSearch = true;
                    allTextView.setVisibility(View.GONE);
                    nearTextView.setVisibility(View.GONE);
                    allarrowTextView.setVisibility(View.GONE);
                    neararrowTextView.setVisibility(View.GONE);
                    searchEdit.setVisibility(View.VISIBLE);
                    searchEdit.setFocusable(true);
                    searchEdit.requestFocus();
                    searchEdit.setFocusableInTouchMode(true);
                    imm.showSoftInput(searchEdit,
                            InputMethodManager.SHOW_FORCED);
                    
                }
                else
                {
                    imm.hideSoftInputFromWindow(searchEdit.getApplicationWindowToken(),
                            0);
                    isSearch = false;
                    allTextView.setVisibility(View.VISIBLE);
                    nearTextView.setVisibility(View.VISIBLE);
                    allarrowTextView.setVisibility(View.VISIBLE);
                    neararrowTextView.setVisibility(View.VISIBLE);
                    searchEdit.setVisibility(View.GONE);
//                    searchEdit.setFocusable(false);
//                    searchEdit.requestFocus();
//                    searchEdit.setFocusableInTouchMode(false);
                    
                }
            }
        });
    }
    
    private void initView()
    {
        titleItem = (StoreTitleItem) findViewById(R.id.main_title);
        
        searchEdit = (EditText) findViewById(R.id.seacrhing_park_et);
        LinearLayout.LayoutParams searchLayoutParams = (android.widget.LinearLayout.LayoutParams) searchEdit.getLayoutParams();
        searchLayoutParams.height = ((mainLogic.screenHeight * 74) / 1280);
        searchLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        searchEdit.setLayoutParams(searchLayoutParams);
        searchEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (mainLogic.screenWidth * 24) / 720);
        searchEdit.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event)
            {
                if (actionId == 3)
                {
                    
                    String key = searchEdit.getText().toString();
                    if (key != null && !key.equals(""))
                    {
                        Intent intent = new Intent(StoreMainActivity.this,
                                SearchResultActivity.class);
                        intent.putExtra(StoreConstant.SEARCHKEY, key);
                        startActivity(intent);
                        //                        searchKey = key;
                        //                        findGoods();
                    }
                    else
                    {
                        showToast(getResources().getString(R.string.park_place_edit_hint));
                    }
                }
                return true;
            }
        });
        
        radioGroup = (LinearLayout) findViewById(R.id.radiobutton);
        RelativeLayout.LayoutParams radioGroupParams = (android.widget.RelativeLayout.LayoutParams) radioGroup.getLayoutParams();
        radioGroupParams.height = ((mainLogic.screenHeight * 90) / 1280);
        radioGroupParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        radioGroup.setLayoutParams(radioGroupParams);
        
        allarrowTextView = (TextView) findViewById(R.id.tab_all_arrow);
        RelativeLayout.LayoutParams params1 = (LayoutParams) allarrowTextView.getLayoutParams();
        params1.width = ((mainLogic.screenWidth * 19) / 720);
        params1.height = ((mainLogic.screenHeight * 13) / 1280);
        allarrowTextView.setLayoutParams(params1);
        
        neararrowTextView = (TextView) findViewById(R.id.tab_near_arrow);
        RelativeLayout.LayoutParams params2 = (LayoutParams) neararrowTextView.getLayoutParams();
        params2.width = ((mainLogic.screenWidth * 19) / 720);
        params2.height = ((mainLogic.screenHeight * 13) / 1280);
        neararrowTextView.setLayoutParams(params2);
        
        allLoyout = (RelativeLayout) findViewById(R.id.tab_all_layout);
        allTextView = (TextView) findViewById(R.id.tab_all);
        allTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (mainLogic.screenWidth * 30) / 720);
        allLoyout.setOnClickListener(this);
        
        nearLoyout = (RelativeLayout) findViewById(R.id.tab_near_layout);
        nearTextView = (TextView) findViewById(R.id.tab_near);
        nearTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (mainLogic.screenWidth * 30) / 720);
        nearLoyout.setOnClickListener(this);
        
        listView = (PullRefreshListView) findViewById(R.id.main_listview);
        listView.setOnRefreshListener(new OnRefreshListener()
        {
            
            @Override
            public void onRefresh()
            {
                // TODO Auto-generated method stub
                refresh();
            }
        });
        //加载更多
        listView.setNewScrollerListener(new NewScrollerListener()
        {
            private boolean isLoadMoreFile = false;
            
            @Override
            public void newScrollChanged(AbsListView view, int scrollState)
            {
                // TODO Auto-generated method stub
                if ((scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING)
                        && isLoadMoreFile)
                {
                    startToLoadMore();
                }
            }
            
            @Override
            public void newScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount)
            {
                // TODO Auto-generated method stub
                isLoadMoreFile = (firstVisibleItem + visibleItemCount == totalItemCount) ? true
                        : false;
            }
        });
        adapter = new AllshoppingAdapter(getApplicationContext(), mBitmapUtils,
                goodsDetailInfos);
        listView.setAdapter(adapter);
        
        findGoods();
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        //        Intent intent = null;
        switch (v.getId())
        {
            case R.id.btn_back:
                finish();
                break;
            //            case R.id.btn_ordermanager:
            //                intent = new Intent(StoreMainActivity.this,
            //                        OrderManagerActivity.class);
            //                startActivity(intent);
            //                break;
            //显示周边分类菜单
            case R.id.tab_near_layout:
                showPopupWindow(findViewById(R.id.tab_near_layout),
                        (mainLogic.screenWidth * 360) / 720,
                        (mainLogic.screenHeight * 345) / 1280,
                        nearList);
                break;
            
            //显示全部分类菜单
            case R.id.tab_all_layout:
                showPopupWindow(findViewById(R.id.tab_all_layout),
                        (mainLogic.screenWidth * 360) / 720,
                        (mainLogic.screenHeight * 602) / 1280,
                        allList);
                break;
            default:
                break;
        }
    }
    
    protected void showPopupWindow(View parentView, int viewWidth,
            int viewHeight, List<String> goodsColumnGroups)
    {
        View view = getLayoutInflater().inflate(R.layout.clasifyfragment, null);
        
        view.setFocusable(true); // 这个很重要color="#7a7a7a"
        view.setFocusableInTouchMode(true);
        expandableListView = (ListView) view.findViewById(R.id.expandableListView);
        expandadapter = new Expandadapter(
                this,
                goodsColumnGroups,
                mHandler,
                parentView.getId() == R.id.tab_all_layout ? StoreConstant.ALL_TYPE
                        : StoreConstant.NEAR_TYPE);
//        expandableListView.setAdapter(expandadapter);
        
        mPopupWindow = new PopupWindow(view, viewWidth, viewHeight, true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        if (null != parentView)
        {
            int[] location = new int[2];
            parentView.getLocationOnScreen(location);
            mPopupWindow.showAtLocation(parentView,
                    Gravity.NO_GRAVITY,
                    location[0],
                    location[1] + parentView.getHeight());
            mPopupWindow.update();
        }
        
        //        findGoodsColumn();
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        Log.d(TAG, "dispatchKeyEvent | isSearch == " + isSearch);
        
        switch (event.getKeyCode())
        {
            case KeyEvent.KEYCODE_BACK:
                if (isSearch)
                {
                    imm.hideSoftInputFromWindow(searchEdit.getApplicationWindowToken(),
                            0);
                    isSearch = false;
                    allTextView.setVisibility(View.VISIBLE);
                    nearTextView.setVisibility(View.VISIBLE);
                    allarrowTextView.setVisibility(View.VISIBLE);
                    neararrowTextView.setVisibility(View.VISIBLE);
                    searchEdit.setVisibility(View.GONE);
                }
            default:
                break;
            //            }
        }
        return super.dispatchKeyEvent(event);
    }
    
    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event)
    //    {
    //        if (keyCode == KeyEvent.KEYCODE_BACK
    //                && event.getAction() == KeyEvent.ACTION_DOWN)
    //        {
    //            
    //        }
    //        return super.onKeyDown(keyCode, event);
    //    }
    
    @Override
    public void initData()
    {
        mainLogic = MainLogic.getInstance(getApplicationContext());
        mainLogic.setData(mHandler);
        mBitmapUtils = new BitmapUtils(getApplicationContext());
        httpUtils = new HttpUtils();
        goodsDetailInfos = new ArrayList<GoodsDetailInfo>();
        nearList = new ArrayList<String>();
        nearList.add("离我最近");
        nearList.add("1km以内");
        nearList.add("3km以内");
        nearList.add("更远");
        
        allList = new ArrayList<String>();
        allList.add("美食");
        allList.add("电影");
        allList.add("健身");
        allList.add("美容美发");
        allList.add("KTV");
        allList.add("酒店");
        
        waresType = 0;
        distanceType = 1;
        
        BaseAccountInfo accountInfo = new BaseAccountInfo();
        
        //        accountInfo.setAccountId(getIntent().getStringExtra("account"));
        //        accountInfo.setUserId(getIntent().getStringExtra("userId"));
        //        accountInfo.setCommunityCode(getIntent().getStringExtra("communtyId"));
        accountInfo.setAccountId(getUser().getAccount());
        accountInfo.setUserId(getUser().getUid());
        accountInfo.setCommunityCode(getUser().getCommuntyId());
        accountInfo.setFlag(1);
        mainLogic.setBaseAccountInfo(accountInfo);
        
        Log.d(TAG, "initData | accountid = " + accountInfo.getAccountId());
        Log.d(TAG, "initData | userId = " + accountInfo.getUserId());
        Log.d(TAG, "initData | communtyId = " + accountInfo.getCommunityCode());
        
        StoreConstant.URL_STORE = HttpUrl.CBS + "/msg";
        Log.d(TAG, "initData | StoreConstant.URL_STORE == "
                + StoreConstant.URL_STORE);
        StoreConstant.URL_STORE = HttpUrl.CBS + "/msg";
        
        OrderDetailLogic orderDetailLogic = OrderDetailLogic.getInstance(getApplicationContext());
        orderDetailLogic.deliveryAddress = getUser().getAddress();
        orderDetailLogic.phone = getUser().getMobile();
        orderDetailLogic.reallyName = getUser().getReallyName();
        
        Log.d(TAG, "initData | deliveryAddress = "
                + orderDetailLogic.deliveryAddress);
        Log.d(TAG, "initData | phone = " + orderDetailLogic.phone);
        Log.d(TAG, "initData | reallyName = " + orderDetailLogic.reallyName);
        
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }
    
    //    public void findGoodsColumn()
    //    {
    //        if (null != goodsColumnGroups && goodsColumnGroups.size() > 0)
    //        {
    //            return;
    //        }
    //        showProcessDialog(new DialogInterface.OnDismissListener()
    //        {
    //            @Override
    //            public void onDismiss(DialogInterface dialog)
    //            {
    //                mainLogic.stopRequest();
    //            }
    //        });
    //        
    //        mainLogic.sendFindGoodsColumnReq(httpUtils);
    //    }
    
    private void findGoods()
    {
        //        if (goodsDetailInfos != null && goodsDetailInfos.size() > 0
        //                && mode == 0)
        //        {
        //            return;
        //        }
        showProcessDialog(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                mainLogic.stopRequest();
            }
        });
        PagerBean pagerBean = new PagerBean();
        pagerBean.setPageId(curPageIndex);
        pagerBean.setPageSize(PAGESIZE);
        mainLogic.sendFindGoodsReq("",
                waresType,
                distanceType,
                pagerBean,
                mode,
                httpUtils);
    }
    
    /**
     * 刷新
     */
    private void refresh()
    {
        curPageIndex = 1;
        isLoadingMore = true;
        mode = 1;
        findGoods();
    }
    
    /**
     * 加载更多
     */
    protected void startToLoadMore()
    {
        if (hasMore)
        {
            if (!isLoadingMore)
            {
                
                listView.showLoading();
                curPageIndex = curPageIndex + 1;
                isLoadingMore = true;
                mode = 2;
                Log.d(TAG, "startToLoadMore--->");
                findGoods();
            }
        }
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        radioGroup = null;
        
        /**
         * 全部分类
         */
        allTextView = null;
        
        /**
         * 周边分类
         */
        nearTextView = null;
        
        allLoyout = null;
        
        nearLoyout = null;
        
        /**
         * 全部的小箭头
         */
        allarrowTextView = null;
        
        neararrowTextView = null;
        
        /**
         * 搜索框
         */
        searchEdit = null;
        
        mainLogic = null;
        
        /**
         * 标题栏
         */
        titleItem = null;
        
        adapter = null;
        
        listView = null;
        
        mBitmapUtils = null;
        
        httpUtils = null;
    }
    
}
