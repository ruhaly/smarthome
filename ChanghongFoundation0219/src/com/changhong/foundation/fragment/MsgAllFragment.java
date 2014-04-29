/**
 * AllMsgFragment.java
 * com.pactera.ch_bedframe.fragment
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-3 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.changhong.foundation.R;
import com.changhong.foundation.activity.MsgDetailInfoActivity;
import com.changhong.foundation.adapter.MessageAdapter;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.entity.MessageInfo;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.foundation.logic.MsgAllLogic;
import com.changhong.foundation.logic.MsgDeleteLogic;
import com.changhong.sdk.fragment.SuperFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ClassName:AllMsgFragment Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午5:17:50
 */
/**
 * @author b
 *
 */
/**
 * @author b
 * 
 */
public class MsgAllFragment extends SuperFragment
{
    
    public MessageAdapter adapter;
    
    @ViewInject(R.id.listview)
    public ListView listview;
    
    @ViewInject(R.id.frame_empty)
    public RelativeLayout frameEmpty;
    
    public MsgAllLogic msgLogic;
    
    public MsgDeleteLogic msgDeleteLogic;
    
    private View convertView;
    
    private HttpUtils httpUtil;
    
    private int[] type = { 1, 2, 3, 4 };
    
    private boolean isLoadMoreFile = false;
    
    private boolean hasMoreData = true;
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new MessageAdapter(getActivity().getBaseContext(),
                    msgLogic.allMsgList, listview, msgDeleteLogic);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3)
                {
                    // TODO Auto-generated method stub
                    MessageInfo message = msgLogic.allMsgList.get(arg2);
                    Intent intent = new Intent(getActivity().getBaseContext(),
                            MsgDetailInfoActivity.class);
                    intent.putExtra("id", message.getId())
                            .putExtra("img", message.getImg())
                            .putExtra("content", message.getContent())
                            .putExtra("title", message.getTitle())
                            .putExtra("date", message.getDate())
                            .putExtra("msgtype", message.getMsgtype());
                    
                    (getActivity()).startActivity(intent);
                }
            });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        //        setUserVisibleHint(false);
        super.onSaveInstanceState(outState);
        
    }
    
    //    @Override
    //    public void onResume()
    //    {
    //        if (null != listview)
    //            listview.refreshDrawableState();
    //        super.onResume();
    //        
    //    }
    
    @Override
    public void onClick(View arg0)
    {
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.message_all_layout,
                container,
                false);
        ViewUtils.inject(this, convertView);
        initAdapter();
        requestMsgList(true);
        return convertView;
    }
    
    public void requestMsgList(boolean isRefresh)
    {
        httpUtil = new HttpUtils();
        showProcessDialog(dismiss);
        msgLogic.setData(fHandler);
        msgLogic.isRefresh = isRefresh;
        msgLogic.requestAllMsgList(LoginLogic.getInstance().baseAccountInfo,
                type,
                httpUtil);
    }
    
    @Override
    public void initData()
    {
        if (null != MsgAllLogic.getInstance())
        {
            msgLogic = MsgAllLogic.getInstance();
        }
        if (null != MsgDeleteLogic.getInstance())
        {
            msgDeleteLogic = MsgDeleteLogic.getInstance();
        }
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            msgLogic.stopRequest();
        }
    };
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MsgWhat.MSGWHAT_ALLMSG_SUCCESS:
            {
                hasMoreData = (Boolean) msg.obj;
                
                if (msgLogic.allMsgList.size() == 0)
                {
                    frameEmpty.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }
                else
                {
                    frameEmpty.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);
                }
                initAdapter();
                
                break;
            }
        }
        super.handleMsg(msg);
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        
    }

    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
    
}
