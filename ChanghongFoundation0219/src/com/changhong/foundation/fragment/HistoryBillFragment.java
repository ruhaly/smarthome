/**
 * HistoryBillFragment.java
 * com.pactera.ch_bedframe.fragment
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-5 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.entity.BillInfo;
import com.changhong.foundation.logic.HistoryBillLogic;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.sdk.fragment.SuperFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ClassName:HistoryBillFragment Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-5 下午2:28:10
 */
public class HistoryBillFragment extends SuperFragment implements
        OnChildClickListener
{
    
    private HistoryBillLogic logic;
    
    private ExpandAdapter adapter;
    
    @ViewInject(R.id.expandableListView)
    private ExpandableListView expandableListView;
    
    View convertView;
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new ExpandAdapter(getActivity().getBaseContext());
            expandableListView.setGroupIndicator(null);
            expandableListView.setAdapter(adapter);
            expandableListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            expandableListView.setOnChildClickListener(this);
            expandableListView.setOnGroupExpandListener(new OnGroupExpandListener()
            {
                
                @Override
                public void onGroupExpand(int arg0)
                {
                    for (int i = 0; i < logic.hList.size(); i++)
                    {
                        if (i != arg0)
                        {
                            expandableListView.collapseGroup(i);
                        }
                    }
                    
                }
            });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return logic.hList.size();
        }
        
        @Override
        public BillInfo getItem(int arg0)
        {
            return logic.hList.get(arg0);
        }
        
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup arg2)
        {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getActivity().getBaseContext())
                        .inflate(R.layout.bill_info_item_layout, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);
                holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tv_paymoney = (TextView) convertView.findViewById(R.id.tv_paymoney);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(getItem(position).getName());
            holder.tv_content.setText(getItem(position).getContent());
            holder.tv_integral.setText(getItem(position).getIntegral());
            holder.tv_paymoney.setText(getItem(position).getPaymoney());
            holder.tv_date.setText(getItem(position).getDate());
            return convertView;
        }
        
        class ViewHolder
        {
            TextView tv_name;
            
            TextView tv_content;
            
            TextView tv_integral;
            
            TextView tv_date;
            
            TextView tv_paymoney;
        }
    }
    
    @Override
    public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
            int arg3, long arg4)
    {
        return false;
        
    }
    
    @Override
    public void onClick(View v)
    {
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.history_bill_layout,
                container,
                false);
        ViewUtils.inject(this, convertView);
        initAdapter();
        requestHistoryBill(false);
        return convertView;
        
    }
    
    @Override
    public void initData()
    {
        logic = HistoryBillLogic.getInstance();
    }
    
    private HttpUtils httpUtils;
    
    public void requestHistoryBill(boolean showDialog)
    {
        httpUtils = new HttpUtils();
        logic.setData(fHandler);
        if (showDialog)
            showProcessDialog(dismiss);
        logic.reqeustHistoryBills(LoginLogic.getInstance().user.getAccount(),
                httpUtils);
        
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MsgWhat.MSGWHAT_HISTORY_BILL_SUCCESS:
            {
                initAdapter();
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    @Override
    public void onDestroy()
    {
        logic.clear();
        super.onDestroy();
    }
    
    class ExpandAdapter extends BaseExpandableListAdapter
    {
        
        private Context mContext;
        
        private LayoutInflater mInflater = null;
        
        public ExpandAdapter(Context ctx)
        {
            mContext = ctx;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        @Override
        public int getGroupCount()
        {
            return logic.hList.size();
        }
        
        @Override
        public int getChildrenCount(int groupPosition)
        {
            return logic.hList.get(groupPosition).getList().size();
        }
        
        @Override
        public BillInfo getGroup(int groupPosition)
        {
            return logic.hList.get(groupPosition);
        }
        
        @Override
        public BillInfo getChild(int groupPosition, int childPosition)
        {
            return logic.hList.get(groupPosition).getList().get(childPosition);
        }
        
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }
        
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }
        
        @Override
        public boolean hasStableIds()
        {
            return false;
        }
        
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent)
        {
            GroupViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.group_item_layout,
                        null);
                holder = new GroupViewHolder();
                holder.mGroupName = (TextView) convertView.findViewById(R.id.group_name);
                holder.img_expand = (ImageView) convertView.findViewById(R.id.img_expand);
                convertView.setTag(holder);
            }
            else
            {
                holder = (GroupViewHolder) convertView.getTag();
            }
            if (isExpanded)
            {
                holder.img_expand.setImageResource(R.drawable.icon_arrow_selected);
            }
            else
            {
                holder.img_expand.setImageResource(R.drawable.icon_arrow_default);
            }
            holder.mGroupName.setText(getGroup(groupPosition).getMonth());
            return convertView;
        }
        
        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent)
        {
            ChildViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.bill_info_item_layout,
                        null);
                holder = new ChildViewHolder();
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);
                holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tv_paymoney = (TextView) convertView.findViewById(R.id.tv_paymoney);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ChildViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(getChild(groupPosition, childPosition).getName());
            holder.tv_content.setText(getChild(groupPosition, childPosition).getContent());
            holder.tv_integral.setText(getChild(groupPosition, childPosition).getIntegral());
            holder.tv_paymoney.setText(getChild(groupPosition, childPosition).getPaymoney());
            holder.tv_date.setText(getChild(groupPosition, childPosition).getDate());
            return convertView;
        }
        
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            // TODO Auto-generated method stub
            /* 很重要：实现ChildView点击事件，必须返回true */
            return true;
        }
        
        private class GroupViewHolder
        {
            TextView mGroupName;
            
            ImageView img_expand;
        }
        
        private class ChildViewHolder
        {
            TextView tv_name;
            
            TextView tv_content;
            
            TextView tv_integral;
            
            TextView tv_date;
            
            TextView tv_paymoney;
        }
        
    }

    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
}
