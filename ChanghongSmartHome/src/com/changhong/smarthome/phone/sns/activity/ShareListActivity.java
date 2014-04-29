package com.changhong.smarthome.phone.sns.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.changhong.sdk.logic.SuperLogic;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.sdk.widget.PullRefreshListView;
import com.changhong.sdk.widget.PullRefreshListView.NewScrollerListener;
import com.changhong.sdk.widget.PullRefreshListView.OnRefreshListener;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.adapter.MySharingAdapter;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.lidroid.xutils.HttpUtils;

/**
 * 晒生活/交换空间列表界面<功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ShareListActivity extends SnsSuperActivity implements
        OnItemClickListener, OnRefreshListener, OnItemLongClickListener

{
    private LinearLayout show_all_rl;
    
    //显示全部还是我的黑条
    private RelativeLayout show_all_layout;
    
    private RelativeLayout show_my_layout;
    
    private ImageView exit_button;//退出返回按钮
    
    private PullRefreshListView listView;
    
    private PullRefreshListView myListView;
    
    private PopupWindow popupWindow;
    
    private LinearLayout allLy;
    
    private boolean isAll = true;
    
    //    private LinearLayout mySwapLy;
    
    private TextView title;
    
    private TextView all;
    
    private TextView showTextView;
    
    private TextView popShowTextView;
    
    private TextView pubBtn;
    
    private MySharingAdapter adapter;
    
    private MySharingAdapter mySharingAdapter;
    
    private List<ShareBean> shareBeans = new ArrayList<ShareBean>();
    
    private List<ShareBean> myShareBeans = new ArrayList<ShareBean>();
    
    private HttpUtils httpUtils;
    
    private IntShareLogic snsLogic;
    
    private MCloudProgressDialog progressDialog;
    
    /**
     * 一页请求的个数
     */
    private int pageSize = 20;
    
    /**
     * 当前请求的是第几页(真正数据从1开始)
     */
    private int curPage = 1;
    
    private int myCurPage = 1;
    
    /**
     * 标记加载更多
     */
    private boolean isMore = false;
    
    /**
     * 标记是否有加载更多请求
     */
    private boolean isLoadingMore = false;
    
    /**
     * 标记是否有更多
     */
    private boolean hasMore = true;
    
    /**
     * 标记是否有更多
     */
    private boolean myHasMore = true;
    
    private int totalNum;
    
    private int myTotalNum;
    
    /**
     * 晒生活/交换空间
     */
    private String subThemeType = "";
    
    /**
     * 删除的是当前list的第几个item
     */
    private int deletePos;
    
    /**
     * lisView滑动加载监听
     * @return
     */
    public NewScrollerListener createScroller()
    {
        NewScrollerListener scroller = new NewScrollerListener()
        {
            private boolean isLoadMoreFile = false;
            
            @Override
            public void newScrollChanged(AbsListView view, int scrollState)
            {
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
                isLoadMoreFile = (firstVisibleItem + visibleItemCount == totalItemCount) ? true
                        : false;
            }
        };
        return scroller;
    }
    
    /**
     * 我的分享lisView滑动加载监听
     * @return
     */
    public NewScrollerListener createMyScroller()
    {
        NewScrollerListener scroller = new NewScrollerListener()
        {
            private boolean isLoadMoreFile = false;
            
            @Override
            public void newScrollChanged(AbsListView view, int scrollState)
            {
                if ((scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING)
                        && isLoadMoreFile)
                {
                    
                    startToLoadMyMore();
                }
            }
            
            @Override
            public void newScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount)
            {
                isLoadMoreFile = (firstVisibleItem + visibleItemCount == totalItemCount) ? true
                        : false;
            }
        };
        return scroller;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_list);
        initView();
        Intent intent = getIntent();
        if (null != intent)
        {
            subThemeType = intent.getStringExtra("subThemeType");
        }
        if (subThemeType.equals(Constant.DYNAMIC_TYPE_ID_SHOT))//04 晒生活
        {
            title.setText(getResources().getString(R.string.sun_life));
            adapter = new MySharingAdapter(ShareListActivity.this, shareBeans,
                    false);
            mySharingAdapter = new MySharingAdapter(ShareListActivity.this,
                    myShareBeans, false);
        }
        else
        {
            title.setText(getResources().getString(R.string.swap_space));
            adapter = new MySharingAdapter(ShareListActivity.this, shareBeans,
                    true);
            mySharingAdapter = new MySharingAdapter(ShareListActivity.this,
                    myShareBeans, true);
        }
        
        listView.setAdapter(adapter);
        myListView.setAdapter(mySharingAdapter);
        snsLogic = IntShareLogic.getInstance();
        snsLogic.setData(mHandler);
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(ShareListActivity.this,
                    getResources().getString(R.string.loading));
        }
        progressDialog.show();
        curPage = 1;
        isMore = false;
        isLoadingMore = true;
        hasMore = true;
        getDynamicList(subThemeType, curPage, pageSize, true, true);
    }
    
    private void initView()
    {
        listView = (PullRefreshListView) findViewById(R.id.share_list);
        myListView = (PullRefreshListView) findViewById(R.id.my_share_list);
        
        title = (TextView) findViewById(R.id.title);
        pubBtn = (TextView) findViewById(R.id.bt_interact_share_publish);
        pubBtn.setVisibility(View.VISIBLE);
        
        //init the pop bar
        
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        int screenWidth = dm.widthPixels;
        //init the popwindow bar
        show_all_rl = (LinearLayout) findViewById(R.id.show_all_rl);
        RelativeLayout.LayoutParams radioGroupParams = (android.widget.RelativeLayout.LayoutParams) show_all_rl.getLayoutParams();
        radioGroupParams.height = ((screenHeight * 90) / 1280);
        radioGroupParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        show_all_rl.setLayoutParams(radioGroupParams);
        
        showTextView = (TextView) findViewById(R.id.show_text);
        showTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        //下拉箭头
        all = (TextView) findViewById(R.id.show_my);
        all.setTextSize(TypedValue.COMPLEX_UNIT_PX, (screenWidth * 30) / 720);
        //        RelativeLayout.LayoutParams params2 = (android.widget.RelativeLayout.LayoutParams) all.getLayoutParams();
        //        params2.width = ((screenWidth * 19) / 720);
        //        params2.height = ((screenHeight * 13) / 1280);
        //        all.setLayoutParams(params2);
        show_all_layout = (RelativeLayout) findViewById(R.id.show_all_layout);
        show_my_layout = (RelativeLayout) findViewById(R.id.show_my_layout);
        show_all_layout.setOnClickListener(this);
        show_my_layout.setOnClickListener(this);
        pubBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setRefreshable(true);
        listView.setOnRefreshListener(this);
        listView.setNewScrollerListener(createScroller());
        
        myListView.setOnItemClickListener(this);
        myListView.setOnItemLongClickListener(this);
        myListView.setRefreshable(true);
        myListView.setOnRefreshListener(this);
        myListView.setNewScrollerListener(createMyScroller());
        LayoutInflater inflater = LayoutInflater.from(this);
        showTextView = (TextView) findViewById(R.id.show_text);
        View view = inflater.inflate(R.layout.pop_sex, null);
        popupWindow = new PopupWindow(view,
                (int) (getResources().getDimension(R.dimen.pop_sex_width)),
                LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失 
        
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        
        //设置点击窗口外边窗口消失 
        
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new OnDismissListener()
        {
            
            @Override
            public void onDismiss()
            {
                // TODO Auto-generated method stub
                all.setBackgroundResource(R.drawable.arrow_down);
            }
        });
        exit_button = (ImageView) findViewById(R.id.exit_button);
        
        super.exitButtonClick(exit_button);
        // 设置此参数获得焦点，否则无法点击 
        
        popupWindow.setFocusable(true);
        //        allLy = (LinearLayout) view.findViewById(R.id.all);
        popShowTextView = (TextView) view.findViewById(R.id.pop_show_text);
        //        allLy.setOnClickListener(this);
        //        mySwapLy.setOnClickListener(this);
        //        all.setOnClickListener(this);
    }
    
    /**
     * 获取动态列表
     * @param userId
     * @param themeType
     * @param pageIndex
     * @param pageSize
     */
    private void getDynamicList(String themeCodeId, int pageIndex,
            int pageSize, boolean isShowAll, boolean isRefreshPage)
    {
        Log.d(TAG, "getDynamicList----start>");
        snsLogic.requestGetDynamicInfo(themeCodeId,
                pageIndex,
                pageSize,
                isShowAll,
                isRefreshPage);
    }
    
    /**
     * 刷新
     */
    private void refresh()
    {
        
        isMore = false;
        isLoadingMore = true;
        
        if (listView.getVisibility() == View.VISIBLE)//所有的界面
        {
            curPage = 1;
            hasMore = true;
            
            getDynamicList(subThemeType, curPage, pageSize, true, true);
        }
        else
        //我的界面
        {
            myCurPage = 1;
            myHasMore = true;
            getDynamicList(subThemeType, myCurPage, pageSize, false, false);
        }
        
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
                curPage = curPage + 1;
                isLoadingMore = true;
                isMore = true;
                Log.d(TAG, "startToLoadMore--->");
                getDynamicList(subThemeType, curPage, pageSize, true, true);
            }
        }
    }
    
    /**
     * 加载更多
     */
    protected void startToLoadMyMore()
    {
        if (myHasMore)
        {
            if (!isLoadingMore)
            {
                
                myListView.showLoading();
                myCurPage = myCurPage + 1;
                isLoadingMore = true;
                isMore = true;
                Log.d(TAG, "startToLoadMyMore--->");
                //requestGetDynamicInfo(String themeCodeId, int pageIndex,
                //        int pageSize, boolean isShowAll, boolean isRefreshPage)
                
                getDynamicList(subThemeType, myCurPage, pageSize, false, true);
            }
        }
    }
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
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
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.bt_interact_share_publish:
                Intent intent = new Intent();
                intent.setClass(ShareListActivity.this, PubEditActivity.class);
                intent.putExtra("subThemeType", subThemeType);
                intent.putExtra("isModify", false);
                startActivity(intent);
                break;
            case R.id.all:
                if (null != popupWindow)
                {
                    popupWindow.dismiss();
                    all.setBackgroundResource(R.drawable.arrow_down);
                }
                if (isAll)
                {
                    //                    showTextView.setText(getResources().getString(R.string.my_swap));
                    //                    popShowTextView.setText(getResources().getString(R.string.all));
                    
                    isAll = false;
                }
                else
                {
                    popShowTextView.setText(getResources().getString(R.string.my_swap));
                    showTextView.setText(getResources().getString(R.string.all));
                    
                    isAll = true;
                }
                
                break;
            //原来显示下拉框，现在显示我的
            case R.id.show_my_layout:
                //                all.setBackgroundResource(R.drawable.arrow_up);
                //                popupWindow.showAsDropDown(all, 0, 38);
                showMyShare();
                break;
            
            case R.id.show_all_layout:
                showAllShare();
            default:
                break;
        }
        
    }
    
    private void showMyShare()
    {
        Log.i(TAG, "-----003 show my");
        listView.setVisibility(View.GONE);
        myListView.setVisibility(View.VISIBLE);
        
        if (mySharingAdapter.getCount() > 0)
        {
            
        }
        else
        {
            if (null == progressDialog)
            {
                progressDialog = new MCloudProgressDialog(
                        ShareListActivity.this,
                        getResources().getString(R.string.loading));
            }
            progressDialog.show();
            getDynamicList(subThemeType, myCurPage, pageSize, false, true);
        }
    }
    
    private void showAllShare()
    {
        Log.i(TAG, "-----004 show all");
        listView.setVisibility(View.VISIBLE);
        myListView.setVisibility(View.GONE);
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        ShareBean shareBean;
        if (listView.getVisibility() == View.VISIBLE)
        {
            shareBean = shareBeans.get(arg2 - 1);
        }
        else
        {
            shareBean = myShareBeans.get(arg2 - 1);
        }
        
        String whichInfo = shareBean.getThemeCodeId();
        
        //判断是活动/二手信息/或者其他
        if (whichInfo.equals(Constant.DYNAMIC_TYPE_ID_SECOND))
        {
            intent.setClass(ShareListActivity.this, ShareBusinessActivity.class);
        }
        else
        {
            intent.setClass(ShareListActivity.this,
                    ShareOriogionalActivity.class);
        }
        intent.putExtra("whichInfo", whichInfo);
        intent.putExtra("themeId", shareBean.getId());
        intent.putExtra("codeId", shareBean.getThemeCodeId());
        intent.putExtra("replyCount", String.valueOf(shareBean.getReplyCount()));
        intent.putExtra("nickName", shareBean.getNickName());
        intent.putExtra("userIcon", shareBean.getCreatorPicUrl());
        intent.putExtra("postType", shareBean.getPostType());
        intent.putExtra("userId", shareBean.getCreatorId());
        intent.putExtra("rangeType", shareBean.getThemeType());
        startActivity(intent);
        
    }
    
    @Override
    public void onRefresh()
    {
        // TODO Auto-generated method stub
        refresh();
    }
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        if (Constant.isNeedToRefresh)
        {
            refresh();
        }
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        // TODO Auto-generated method stub
        super.handleMsg(msg);
        if (null != progressDialog)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
        
        switch (msg.what)
        {
            case Constant.GET_DYNAMIC_INFO_LIST_SUCCESS:
                if (msg.arg1 != -1)
                {
                    return;
                }
                if (listView.getVisibility() == View.VISIBLE)
                {
                    listView.onRefreshComplete();
                    listView.showLoadFinish();
                }
                else
                {
                    myListView.onRefreshComplete();
                    myListView.showLoadFinish();
                }
                
                isLoadingMore = false;
                ShareBean shareBean = (ShareBean) msg.obj;
                List<ShareBean> sBeans = shareBean.getShareBeans();
                if (listView.getVisibility() == View.VISIBLE)
                {
                    totalNum = shareBean.getTotalNum();
                    Log.d(TAG, "totalNum---->" + totalNum);
                    if (!isMore)
                    {
                        shareBeans.clear();
                    }
                    shareBeans.addAll(sBeans);
                    if (shareBeans.size() < totalNum)
                    {
                        hasMore = true;
                    }
                    else
                    {
                        hasMore = false;
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    myTotalNum = shareBean.getTotalNum();
                    Log.d(TAG, "myTotalNum---->" + myTotalNum);
                    if (!isMore)
                    {
                        myShareBeans.clear();
                    }
                    myShareBeans.addAll(sBeans);
                    if (myShareBeans.size() < myTotalNum)
                    {
                        myHasMore = true;
                    }
                    else
                    {
                        myHasMore = false;
                    }
                    mySharingAdapter.notifyDataSetChanged();
                }
                
                break;
            case Constant.GET_DYNAMIC_INFO_LIST_FAILED:
                if (msg.arg1 != -1)
                {
                    return;
                }
                if (listView.getVisibility() == View.VISIBLE)
                {
                    listView.onRefreshComplete();
                    listView.showLoadFinish();
                }
                else
                {
                    myListView.onRefreshComplete();
                    myListView.showLoadFinish();
                }
                
                isLoadingMore = false;
                Toast.makeText(ShareListActivity.this,
                        getResources().getString(R.string.req_new_friend_failed),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
            case Constant.DELETE_SUCCESS:
                Toast.makeText(ShareListActivity.this,
                        getResources().getString(R.string.delete_success),
                        Toast.LENGTH_SHORT).show();
                myShareBeans.remove(deletePos);
                mySharingAdapter.notifyDataSetChanged();
                
                break;
            case Constant.DELETE_FAILED:
                Toast.makeText(ShareListActivity.this,
                        getResources().getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            case SuperLogic.CONNECT_ERROR_MSGWHAT:
                if (listView.getVisibility() == View.VISIBLE)
                {
                    listView.onRefreshComplete();
                    listView.showLoadFinish();
                }
                else
                {
                    myListView.onRefreshComplete();
                    myListView.showLoadFinish();
                }
                isLoadingMore = false;
                break;
            default:
                break;
        }
    }
    
    /**
     * 删除请求
     * @param themeOrReplyId
     * @param delType
     */
    private void deleteShare(String themeOrReplyId)
    {
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(ShareListActivity.this,
                    getResources().getString(R.string.common_pagetip_deleting));
        }
        progressDialog.show();
        httpUtils = new HttpUtils();
        snsLogic.setData(mHandler);
        snsLogic.requestDelete(themeOrReplyId, Constant.DELETE_SHARE, httpUtils);
    }
    
    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
            long arg3)
    {
        deletePos = arg2 - 1;
        
        showAlertDialog(0,
                getString(R.string.tip),
                getString(R.string.delete_tip_share),
                null,
                new DialogInterface.OnClickListener()
                {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        deleteShare(myShareBeans.get(deletePos).getId());
                    }
                },
                DEFAULT_BTN,
                null,
                true,
                false);
        return false;
    }
    
}
