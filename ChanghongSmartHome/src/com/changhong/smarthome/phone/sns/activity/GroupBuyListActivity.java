package com.changhong.smarthome.phone.sns.activity;

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
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changhong.sdk.logic.SuperLogic;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.sdk.widget.PullRefreshListView;
import com.changhong.sdk.widget.PullRefreshListView.NewScrollerListener;
import com.changhong.sdk.widget.PullRefreshListView.OnRefreshListener;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.adapter.GroupBuyAdapter;
import com.changhong.smarthome.phone.sns.adapter.GroupBuyMyAdapter;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingListVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingListVO.GroupBuyingList;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingMyVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingMyVO.GroupBuyingMy;
import com.changhong.smarthome.phone.sns.bean.Pager;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.lidroid.xutils.HttpUtils;

/**
 * 团购主界面
 * @author wanghonghong
 * @version [版本号, 2014-1-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GroupBuyListActivity extends SnsSuperActivity implements
        OnItemClickListener, OnRefreshListener, OnItemLongClickListener

{
    private LinearLayout show_all_rl;
    
    private RelativeLayout show_all_layout;
    
    private RelativeLayout show_my_layout;
    
    private PullRefreshListView listView;//全部团购的列表
    
    private PullRefreshListView myListView;//我发布的团购列表
    
    ImageView exit_button;//退出返回按钮
    
    private PopupWindow popupWindow;
    
    private LinearLayout allLy;
    
    private boolean isAll = true;
    
    //    private LinearLayout mySwapLy;
    
    private TextView title;
    
    private TextView all;
    
    private TextView showTextView;
    
    private TextView popShowTextView;
    
    private TextView pubBtn;
    
    private GroupBuyAdapter adapter;
    
    private GroupBuyMyAdapter mySharingAdapter;
    
    //    private List<GroupBuyBean> shareBeans = new ArrayList<GroupBuyBean>();
    GroupBuyingListVO groupBuyingListVO = new GroupBuyingListVO();
    
    //    private List<GroupBuyBean> myShareBeans = new ArrayList<GroupBuyBean>();
    
    private GroupBuyingMyVO myshareVO = new GroupBuyingMyVO();
    
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
    
    //    /**
    //     * 晒生活/交换空间
    //     */
    //    private String subThemeType = "";
    
    /**
     * 删除的是当前list的第几个item
     */
    private int deletePos;
    
    private Pager pager = new Pager();
    
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
        title.setText(getResources().getString(R.string.group_buy));
        adapter = new GroupBuyAdapter(GroupBuyListActivity.this,
                groupBuyingListVO, false);
        
        listView.setAdapter(adapter);
        
        snsLogic = IntShareLogic.getInstance();
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(
                    GroupBuyListActivity.this,
                    getResources().getString(R.string.loading));
        }
        progressDialog.show();
        curPage = 1;
        isMore = false;
        isLoadingMore = true;
        hasMore = true;
        pager.setPageId(1);
        pager.setPageSize(30);
        getGroupList(UserUtils.getUser().getCommuntyId(), pager);
        getGroupBuyingMy(UserUtils.getUser().getCommuntyId(), pager);//获取我的订单
        Log.i(TAG, "---003---group by oncreate");
    }
    
    private void initView()
    {
        listView = (PullRefreshListView) findViewById(R.id.share_list);
        myListView = (PullRefreshListView) findViewById(R.id.my_share_list);
        exit_button = (ImageView) findViewById(R.id.exit_button);
        super.exitButtonClick(exit_button);
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
        
        show_all_layout = (RelativeLayout) findViewById(R.id.show_all_layout);
        show_my_layout = (RelativeLayout) findViewById(R.id.show_my_layout);
        show_all_layout.setOnClickListener(this);
        show_my_layout.setOnClickListener(this);
        
        title = (TextView) findViewById(R.id.title);
        pubBtn = (TextView) findViewById(R.id.bt_interact_share_publish);
        pubBtn.setVisibility(View.VISIBLE);
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
        
        // 设置此参数获得焦点，否则无法点击 
        
        popupWindow.setFocusable(true);
        popShowTextView = (TextView) view.findViewById(R.id.pop_show_text);
        //        mySwapLy.setOnClickListener(this);
    }
    
    /**
     * 
     * @param communityCode
     * @param pager
     */
    private void getGroupList(String communityCode, Pager pager)
    {
        httpUtils = new HttpUtils();
        snsLogic.setData(mHandler);
        snsLogic.requestgroupBuyingList(communityCode, pager, httpUtils);
    }
    
    private void getGroupBuyingMy(String communityCode, Pager pager)
    {
        snsLogic.requestGroupBuyingMy(communityCode, pager);
    }
    
    /**
     * 刷新
     */
    private void refresh()
    {
        
        isMore = false;
        isLoadingMore = true;
        
        if (listView.getVisibility() == View.VISIBLE)
        {
            curPage = 1;
            hasMore = true;
            //            getDynamicList(String.valueOf(Constant.PUBLIC_RANGE),
            //                    subThemeType,
            //                    curPage,
            //                    pageSize,
            //                    -1);
        }
        else
        {
            myCurPage = 1;
            myHasMore = true;
            //            getDynamicList("", "", myCurPage, pageSize, -1);
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
                //                getDynamicList(String.valueOf(Constant.PUBLIC_RANGE),
                //                        subThemeType,
                //                        curPage,
                //                        pageSize,
                //                        -1);
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
                //                getDynamicList("", "", myCurPage, pageSize, -1);
            }
        }
    }
    
    @Override
    public void initData()
    {
        Log.i(TAG, "---002---group by initData");
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
                intent.setClass(GroupBuyListActivity.this,
                        PubEditActivity.class);
                intent.putExtra("subThemeType",
                        Constant.DYNAMIC_TYPE_ID_ACTIVITY);
                startActivity(intent);
                break;
            case R.id.all:
                if (null != popupWindow)
                {
                    popupWindow.dismiss();
                    all.setBackgroundResource(R.drawable.arrow_down);
                }
                break;
            //            case R.id.my_swap:
            //                if (null != popupWindow)
            //                {
            //                    popupWindow.dismiss();
            //                }
            //                
            //                break;
            //原来是弹出下拉框，现在显示我的
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
    
    //显示全部分享
    private void showAllShare()
    {
        Log.i(TAG, "------001show all");
        myListView.setVisibility(View.GONE);
        popShowTextView.setText(getResources().getString(R.string.my_swap));
        showTextView.setText(getResources().getString(R.string.all));
        listView.setVisibility(View.VISIBLE);
        
    }
    
    //显示我的分享
    private void showMyShare()
    {
        Log.i(TAG, "------002-----show my");
        //        showTextView.setText(getResources().getString(R.string.my_swap));
        //        popShowTextView.setText(getResources().getString(R.string.all));
        listView.setVisibility(View.GONE);
        myListView.setVisibility(View.VISIBLE);
        
        if (mySharingAdapter != null && mySharingAdapter.getCount() > 0)
        {
            
        }
        else
        {
            if (null == progressDialog)
            {
                progressDialog = new MCloudProgressDialog(
                        GroupBuyListActivity.this,
                        getResources().getString(R.string.loading));
            }
            progressDialog.show();
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
            long id)
    {
        // TODO Auto-generated method stub
        
        Intent intent = new Intent();
        intent.setClass(GroupBuyListActivity.this, GroupBuyDetailActivity.class);
        if (isAll)
        {
            intent.putExtra("isMy", false);//全部团购
            GroupBuyingList vo = (GroupBuyingList) adapter.getItem(position - 1);
            intent.putExtra("id", vo.getId());
        }
        else
        {
            intent.putExtra("isMy", true);//我发布的团购
            GroupBuyingMy vo = (GroupBuyingMy) mySharingAdapter.getItem(position - 1);
            intent.putExtra("id", vo.getId());
            
        }
        startActivity(intent);
        
    }
    
    @Override
    public void onRefresh()
    {
        refresh();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        if (Constant.isNeedToRefresh)
        {
            //            refresh();
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
            case Constant.GET_GROUPBUYINGLIST_SUCCESS:
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
                GroupBuyingListVO vo = (GroupBuyingListVO) msg.obj;
                //                GroupBuyBean shareBean = (GroupBuyingListVO) msg.obj;
                //                List<GroupBuyBean> sBeans = shareBean.getGroupBuyBeans();
                if (listView.getVisibility() == View.VISIBLE)
                {
                    //                    totalNum = shareBean.getTotalNum();
                    Log.d(TAG, "totalNum---->" + totalNum);
                    if (!isMore)
                    {
                        if (groupBuyingListVO != null
                                && groupBuyingListVO.getData() != null
                                && !groupBuyingListVO.getData().isEmpty())
                        {
                            groupBuyingListVO.getData().clear();
                        }
                        //                        shareBeans.clear();
                    }
                    if (vo != null && vo.getData() != null
                            && !vo.getData().isEmpty())
                    {
                        groupBuyingListVO.getData().addAll(vo.getData());
                    }
                    
                    //                    groupBuyingListVO.addAll(sBeans);
                    if (groupBuyingListVO.getData().size() < totalNum)
                    {
                        hasMore = true;
                    }
                    else
                    {
                        hasMore = false;
                    }
                    adapter.notifyDataSetChanged();
                }
                
                break;
            case Constant.GET_GROUPBUYINGLIST_FAILED:
                //                if (msg.arg1 != -1)
                //                {
                //                    return;
                //                }
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
                Toast.makeText(GroupBuyListActivity.this,
                        getResources().getString(R.string.get_group_buy_failed),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
            case Constant.GET_GROUPBUYINGMY_SUCCESS:
                isLoadingMore = false;
                myshareVO = (GroupBuyingMyVO) msg.obj;
                mySharingAdapter = new GroupBuyMyAdapter(
                        GroupBuyListActivity.this, myshareVO, true);
                myListView.setAdapter(mySharingAdapter);
                break;
            case Constant.GET_GROUPBUYINGMY_FAILED:
                
                break;
            case Constant.DELETE_SUCCESS:
                Toast.makeText(GroupBuyListActivity.this,
                        getResources().getString(R.string.delete_success),
                        Toast.LENGTH_SHORT).show();
                //                myShareBeans.remove(deletePos);
                //                mySharingAdapter.notifyDataSetChanged();
                break;
            case Constant.DELETE_FAILED:
                Toast.makeText(GroupBuyListActivity.this,
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
            progressDialog = new MCloudProgressDialog(
                    GroupBuyListActivity.this,
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
        
        //        showAlertDialog(0,
        //                getString(R.string.tip),
        //                getString(R.string.delete_tip_share),
        //                null,
        //                new DialogInterface.OnClickListener()
        //                {
        //                    
        //                    @Override
        //                    public void onClick(DialogInterface dialog, int which)
        //                    {
        //                        // TODO Auto-generated method stub
        //                        deleteShare(myShareBeans.get(deletePos).getId());
        //                    }
        //                },
        //                DEFAULT_BTN,
        //                null,
        //                true,
        //                false);
        return false;
    }
    
}
