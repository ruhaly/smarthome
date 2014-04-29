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
import android.widget.ExpandableListView;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.adapter.Expandadapter;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.FindColumnLogic;
import com.changhong.smarthome.phone.store.logic.bean.GoodsColumnGroup;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * [分类展现商品列表]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class ClasifyFragment extends SuperFragment
{
    private static final String TAG = "ClasifyFragment";
    
    private ExpandableListView expandableListView;
    
    private FindColumnLogic findColumnLogic;
    
    private BitmapUtils mBitmapUtils;
    
    private Expandadapter expandadapter;
    
    private List<GoodsColumnGroup> goodsColumnGroups;
        
    private HttpUtils httpUtils;
    
  @SuppressWarnings("unchecked")
  public void handleMsg(Message msg)
  {
      super.handleMsg(msg);
      switch (msg.what)
      {
          case StoreConstant.GET_FINDGOODSCOLUMN_SUCCESS:
              if (msg.obj != null)
              {
                  List<GoodsColumnGroup> temp = (List<GoodsColumnGroup>) msg.obj;
                  goodsColumnGroups.addAll(temp);
                  expandadapter.notifyDataSetChanged();
              }
              break;
          case StoreConstant.GET_FINDGOODSCOLUMN_FAILED:
              showToast(getResources().getString(R.string.error_1));
              break;
      }
  };
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
    }
    
    private void initLogic()
    {
        findColumnLogic = FindColumnLogic.getInstance(getActivity());
        findColumnLogic.setData(fHandler);
        httpUtils = new HttpUtils();
        mBitmapUtils = new BitmapUtils(getActivity());
        goodsColumnGroups = new ArrayList<GoodsColumnGroup>();
    }
    
    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
//        findGoodsColumn();
//        expandableListView.setOnChildClickListener(this);
        Log.d(TAG, "onResume");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        
        initLogic();
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }
    
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
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
        View view = inflater.inflate(R.layout.clasifyfragment, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
//        expandadapter = new Expandadapter(getActivity(), mBitmapUtils,
//                goodsColumnGroups);
//        expandableListView.setAdapter(expandadapter);
        Log.d(TAG, "onCreateView");
        return view;
    }
    
    public void findGoodsColumn()
    {
        if(null != goodsColumnGroups && goodsColumnGroups.size()>0)
        {
            return;
        }
        showProcessDialog(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                findColumnLogic.stopRequest();
            }
        });
        
        findColumnLogic.sendFindGoodsColumnReq(httpUtils);
    }

    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
    
}
