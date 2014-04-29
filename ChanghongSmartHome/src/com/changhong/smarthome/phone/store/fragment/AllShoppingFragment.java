package com.changhong.smarthome.phone.store.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView.NewScrollerListener;
import com.changhong.smarthome.phone.store.activity.PullRefreshListView.OnRefreshListener;
import com.changhong.smarthome.phone.store.adapter.AllshoppingAdapter;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.MainLogic;
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.changhong.smarthome.phone.store.logic.bean.PagerBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

public class AllShoppingFragment extends SuperFragment 
{
    private static final String TAG = "AllShoppingFragment";

    private View view;
    
    private MainLogic mainLogic;
    
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
    
    private List<GoodsDetailInfo> goodsDetailInfos;
    
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
                    if(temp.size() > 0)
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
//                    gColumnGroups = (List<GoodsColumnGroup>) msg.obj;
//                    ((ClasifyFragment) list.get(1)).setData(gColumnGroups);
//                    fragmentAdapter.notifyDataSetChanged();
//                }
//                break;
//            case StoreConstant.GET_FINDGOODSCOLUMN_FAILED:
//                showToast(getResources().getString(R.string.error_1));
//                break;
        }
    };
    
    public AllShoppingFragment()
    {
        // TODO Auto-generated constructor stub
        
    }
    
   
//    public void setData(List<GoodsDetailInfo> goodsDetailInfos)
//    {
//        adapter.setList(goodsDetailInfos);
//        adapter.notifyDataSetChanged();
//    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        initLogic();
        Log.d(getTag(), "onCreate");
        super.onCreate(savedInstanceState);
    }
    
    private void initLogic()
    {
        mainLogic = MainLogic.getInstance(getActivity());
        mainLogic.setData(fHandler);
        mBitmapUtils = new BitmapUtils(getActivity());
        httpUtils = new HttpUtils();
        goodsDetailInfos = new ArrayList<GoodsDetailInfo>();
    }
    
    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        findGoods();
        Log.d(getTag(), "onResume");
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
//        try {
//            mCallback = (OnHeadlineSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
        Log.d(getTag(), "onAttach");
    }
    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Log.d(getTag(), "onActivityCreated");
    }
    
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    @Override
    public void onDestroyView()
    {
        mBitmapUtils = null;
        listView = null;
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }
    
    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
    
    @Override
    public void onDetach()
    {
        // TODO Auto-generated method stub  
        Log.d(this.getTag(), "onDetach");
        super.onDetach();
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
    
    private void findGoods()
    {
        if(goodsDetailInfos != null && goodsDetailInfos.size()>0 &&mode == 0)
        {
            return;
        }
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
//        mainLogic.sendFindGoodsReq("", "", pagerBean, mode,httpUtils);
    }



    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void updateView(Message msg)
    {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
    }



    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.allshoppingfragment, container, false);
        
        listView = (PullRefreshListView) view.findViewById(R.id.listview_id);
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
        adapter = new AllshoppingAdapter(getActivity(),
                mBitmapUtils,goodsDetailInfos);
        listView.setAdapter(adapter);
        Log.d(TAG, "initLayout|onCreateView");
        return view;
    }

    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
    
}
