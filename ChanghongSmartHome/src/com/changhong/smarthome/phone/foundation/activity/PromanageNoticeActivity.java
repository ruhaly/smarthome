package com.changhong.smarthome.phone.foundation.activity;

import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.DateUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.MessageInfo;
import com.changhong.smarthome.phone.foundation.logic.PromanageNoticeLogic;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 物管通知界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月25日]
 */
public class PromanageNoticeActivity extends BaseActivity
{
    
    @ViewInject(R.id.listView)
    private ListView listView;
    
    private Adapter adapter;
    
    private PromanageNoticeLogic logic;
    
    @Override
    public void initData()
    {
        logic = PromanageNoticeLogic.getInstance();
        for (int i = 0; i < 10; i++)
        {
            MessageInfo mi = new MessageInfo();
            mi.setTitle("26日下午三点电线检修");
            mi.setDate(DateUtils.TIMESTAMP_DIAN.format(new Date()));
            
            MessageInfo mi2 = new MessageInfo();
            mi2.setTitle("生鲜配送服务上线！您身边的的美食专家");
            mi2.setDate(DateUtils.TIMESTAMP_DIAN.format(new Date()));
            logic.list.add(mi);
            logic.list.add(mi2);
        }
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.promanage_notice_layout);
        ViewUtils.inject(this);
        initAdapter();
    }
    
    private void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            listView.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return logic.list.size();
        }
        
        @Override
        public MessageInfo getItem(int position)
        {
            return logic.list.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(final int position, View convertView,
                ViewGroup parent)
        {
            final ViewHolder viewHolder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.promanage_notice_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(getItem(position).getTitle());
            viewHolder.tvDate.setText(getItem(position).getDate());
            return convertView;
        }
        
        class ViewHolder
        {
            public TextView tvTitle;
            
            public TextView tvDate;
        }
    }
    
    @OnClick(R.id.imgBack)
    public void imgBack(View view)
    {
        finish();
    }
}
