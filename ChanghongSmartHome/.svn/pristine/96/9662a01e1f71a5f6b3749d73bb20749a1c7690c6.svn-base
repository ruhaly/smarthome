package com.changhong.smarthome.phone.store.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.adapter.OrderManagerHosExpandadapter;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.OrderOldestLogic;
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoGroup;
import com.changhong.smarthome.phone.store.logic.bean.PagerBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * [订单分类展现商品列表]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class orderManagerHosFragment extends SuperFragment
{
    private static final String TAG = "orderManagerHosFragment";
    
    private ExpandableListView expandableListView;
    
    private OrderOldestLogic orderOldestLogic;
    
    private BitmapUtils mBitmapUtils;
    
    private OrderManagerHosExpandadapter expandadapter;
    
    private List<OrderInfoGroup> groups = new ArrayList<OrderInfoGroup>();
    
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
     * 保存上次点击的组id
     */
    private int oldIndex = -1;
    
    /**
     * 加载模式： 0 第一次 ；1 刷新 ；2 加载更多
     */
    private int mode = 0;
    
    private void initLogic()
    {
        orderOldestLogic = OrderOldestLogic.getInstance(getActivity());
        httpUtils = new HttpUtils();
        mBitmapUtils = new BitmapUtils(getActivity());
        orderOldestLogic.setHandler(fHandler);
    }
    
    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
//        findOrders();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initLogic();

    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }
    
    
    
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
//    @Override
//    public boolean onChildClick(ExpandableListView parent, View v,
//            int groupPosition, int childPosition, long id)
//    {
//        Log.d(getTag(), "onItemClick | childPosition = " + childPosition);
//        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//        intent.putExtra(StoreConstant.ORDER_TYPE, StoreConstant.ORDER_SHOW);
//        getActivity().startActivity(intent);
//        return false;
//    }

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
        expandadapter = new OrderManagerHosExpandadapter(getActivity(),
                groups, mBitmapUtils);
        
        View view = inflater.inflate(R.layout.ordermanager_hos_fragment, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListView.setOnGroupClickListener(new OnGroupClickListener()
        {
            
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id)
            {
                // TODO Auto-generated method stub
                
                if(oldIndex == groupPosition)
                {
                    oldIndex = -1;
                    expandadapter.setSelected(-1);
                }
                else
                {
                    oldIndex = groupPosition;
                    expandadapter.setSelected(groupPosition);
                }
                
                return false;
            }
        });
        
        expandableListView.setAdapter(expandadapter);
        return view;
    }
    
    public void findOrders()
    {
        showProcessDialog(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                orderOldestLogic.stopRequest();
            }
        });
        
        PagerBean pagerBean = new PagerBean();
        pagerBean.setPageId(curPageIndex);
        pagerBean.setPageSize(PAGESIZE);
//        orderOldestLogic.sendFindOrdersReq("2", pagerBean, mode,httpUtils);
    }
    
    /**
     * 刷新
     */
    private void refresh()
    {
        curPageIndex = 1;
        isLoadingMore = true;
        mode = 1;
        findOrders();
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
                
//                expandableListView.showLoading();
                curPageIndex = curPageIndex + 1;
                isLoadingMore = true;
                mode = 2;
                Log.d(TAG, "startToLoadMore--->");
                findOrders();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void handleMsg(Message msg)
    {
        super.handleMsg(msg);
        switch (msg.what)
        {
            case StoreConstant.GET_FINDORDERS_OLD_SUCCESS:
                if (msg.obj != null)
                {
                    List<OrderInfoGroup> temp = (List<OrderInfoGroup>) msg.obj;
                    if(temp.size() > 0)
                    {
                        groups.clear();
                        groups.addAll(temp);
                        expandadapter.notifyDataSetChanged();
                    }
                    else
                    {
                        hasMore = false;
                    }
                }
                break;
            case StoreConstant.GET_FINDORDERS_OLD_FAILED:
                showToast(getResources().getString(R.string.error_1));
                break;
            case StoreConstant.GET_FINDORDERS_OLD_GETMORE_SUCCESS:
                if (msg.obj != null)
                {
                    List<OrderInfoGroup> temp = (List<OrderInfoGroup>) msg.obj;
                    if(temp.size() > 0)
                    {
//                        groups.addAll(temp);
                        expandadapter.notifyDataSetChanged();
                    }
                    else
                    {
                        hasMore = false;
                    }
                }
                break;
            case StoreConstant.GET_FINDORDERS_OLD_GETMORE_FAILED:
                showToast(getResources().getString(R.string.error_1));
                break;
        }
    }

    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }

   

}
