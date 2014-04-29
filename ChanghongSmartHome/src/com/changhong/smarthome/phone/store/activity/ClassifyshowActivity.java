package com.changhong.smarthome.phone.store.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView.NewScrollerListener;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView.OnRefreshListener;
import com.changhong.smarthome.phone.store.adapter.AllshoppingAdapter;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.ClassifyshowLogic;
import com.changhong.smarthome.phone.store.logic.bean.GoodsColumnBean;
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.changhong.smarthome.phone.store.logic.bean.PagerBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;


/**
 * [显示分类下面的商品]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class ClassifyshowActivity extends SuperActivity
{
    private GoodsColumnBean goodsColumnBean;
    
    private ClassifyshowLogic classifyshowLogic;
    
    private HttpUtils httpUtils;
    
    private AllshoppingAdapter allshoppingAdapter;
    
    private BitmapUtils mBitmapUtils;
    
    private PullRefreshListView listView;
    
    /**
     * 标题栏
     */
    private StoreTitleItem titleItem;
    
    /**
     * 搜索框
     */
    private EditText searchEdit;
    
    private List<GoodsDetailInfo> goodsDetailInfos;
    
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
    
    @Override
    public void initData()
    {
        
        goodsColumnBean = (GoodsColumnBean) getIntent().getSerializableExtra(StoreConstant.GOODS_COLUMN);
        
        classifyshowLogic = ClassifyshowLogic.getInstance(getApplicationContext());
        classifyshowLogic.setData(mHandler);
        mBitmapUtils = new BitmapUtils(getApplicationContext());
        httpUtils = new HttpUtils();
        goodsDetailInfos = new ArrayList<GoodsDetailInfo>();
        allshoppingAdapter = new AllshoppingAdapter(getApplicationContext(),
                mBitmapUtils, goodsDetailInfos);
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.classify_show);
        titleItem = (StoreTitleItem) findViewById(R.id.main_title);
        titleItem.setTitleTextView(goodsColumnBean.getColumnName());
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
        searchEdit = (EditText) findViewById(R.id.seacrhing_park_et);
        RelativeLayout.LayoutParams searchLayoutParams = (android.widget.RelativeLayout.LayoutParams) searchEdit.getLayoutParams();
        searchLayoutParams.height = ((classifyshowLogic.screenHeight * 74) / 1280);
        searchLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        searchEdit.setLayoutParams(searchLayoutParams);
        searchEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (classifyshowLogic.screenWidth * 24) / 720);
        searchEdit.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event)
            {
                if (actionId == 3)
                {
                    String key = searchEdit.getText().toString();
                    if(key != null && !key.equals(""))
                    {
                        Intent intent = new Intent(ClassifyshowActivity.this,
                                SearchResultActivity.class);
                        intent.putExtra(StoreConstant.SEARCHKEY, key);
                        startActivity(intent);
                    }
                    else
                    {
                        showToast(getResources().getString(R.string.park_place_edit_hint));
                    }
                }
                return true;
            }
        });
        listView = (PullRefreshListView) findViewById(R.id.listview_id);
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
        listView.setAdapter(allshoppingAdapter);
        findGoods();
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void findGoods()
    {
        
        showProcessDialog(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                classifyshowLogic.stopRequest();
            }
        });
        PagerBean pagerBean = new PagerBean();
        pagerBean.setPageId(curPageIndex);
        pagerBean.setPageSize(PAGESIZE);
        classifyshowLogic.sendFindGoodsReq("",goodsColumnBean.getColumnId(),
                pagerBean,mode,httpUtils);
        
    }
    
    @SuppressWarnings("unchecked")
    public void handleMsg(Message msg)
    {
        super.handleMsg(msg);
        listView.onRefreshComplete();
        listView.showLoadFinish();
        isLoadingMore = false;
        switch (msg.what)
        {
            case StoreConstant.GET_FINDGOODS_SUCCESS:
                if (msg.obj != null)
                {
                    List<GoodsDetailInfo> temp = (List<GoodsDetailInfo>) msg.obj;
                    if(temp.size() > 0)
                    {
                        goodsDetailInfos.clear();
                        goodsDetailInfos.addAll(temp);
                        allshoppingAdapter.notifyDataSetChanged();
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
                    if(temp.size() > 0)
                    {
                        goodsDetailInfos.addAll(temp);
                        allshoppingAdapter.notifyDataSetChanged();
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
            
        }
    };
    
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
        if(hasMore)
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
    
}