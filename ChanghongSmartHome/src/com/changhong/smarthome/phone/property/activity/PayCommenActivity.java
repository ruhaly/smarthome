package com.changhong.smarthome.phone.property.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.changhong.smarthome.phone.foundation.bean.PayServiceBean;

public class PayCommenActivity extends BaseActivity
{
    private ImageView payBackList;
    
    private TextView toHistory;
    
    private ListView payList;
    
    private TextView titlePay;
    
    private Adapter payAdapter;
    
    private List<PayServiceBean> list = new ArrayList<PayServiceBean>();
    
    private String payType;
    
    /**
     * 
     * 初始化页面<BR>
     * [功能详细描述]
     * @param paramBundle
     * @see com.changhong.sdk.activity.SuperActivity#initLayout(android.os.Bundle)
     */
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.pay_commen);
        payBackList = (ImageView) findViewById(R.id.pay_back_list);
        titlePay = (TextView) findViewById(R.id.text_pay);
        toHistory = (TextView) findViewById(R.id.pay_history);
        payList = (ListView) findViewById(R.id.pay_list);
        
        payBackList.setOnClickListener(this);
        toHistory.setOnClickListener(this);
        
        payType = getIntent().getStringExtra("payType");
        titlePay.setText(payType);
        initAdapter();
    }
    
    /**
     * 
     * 初始化数据<BR>
     * [功能详细描述]
     * @see com.changhong.sdk.activity.SuperActivity#initData()
     */
    @Override
    public void initData()
    {
        payType = getIntent().getStringExtra("payType");
        PayServiceBean bean = new PayServiceBean();
        bean.setAddress("2-1-203");
        bean.setQuarter("第一季度");
        bean.setPayType(payType);
        list.add(bean);
        bean = new PayServiceBean();
        bean.setAddress("1-2-203");
        bean.setQuarter("第二季度");
        bean.setPayType(payType);
        list.add(bean);
        bean = new PayServiceBean();
        bean.setAddress("1-2-203");
        bean.setQuarter("第三季度");
        bean.setPayType(payType);
        list.add(bean);
        
    }
    
    /**
    *
    *  初始化adapter<BR>
    * [功能详细描述]
    */
    public void initAdapter()
    {
        if (null == payAdapter)
        {
            payAdapter = new Adapter();
            payList.setAdapter(payAdapter);
            payList.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    Intent intent = new Intent();
                    intent.putExtra("payType", payType);
                    //                    intent.putExtra("quarter", list.get(position).getQuarter());
                    //                    intent.putExtra("state", list.get(position).getState());
                    //                    intent.putExtra("money", list.get(position).getMoney());
                    intent.setClass(PayCommenActivity.this,
                            PayDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            payAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 
     * 重写onclick事件<BR>
     * [功能详细描述]
     * @param v
     * @see com.changhong.sdk.activity.SuperActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(android.view.View v)
    {
        switch (v.getId())
        {
            case R.id.pay_back_list:
            {
                finish();
                break;
            }
            case R.id.pay_history:
            {
                Intent intent = new Intent();
                intent.setClass(PayCommenActivity.this,
                        PayHistoryListActivity.class);
                startActivity(intent);
                break;
                
            }
        }
    }
    
    /**
     * 自定义适配器
     * @version [智慧社区-终端底座, 2014年4月1日]
     */
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return list.size();
        }
        
        @Override
        public PayServiceBean getItem(int position)
        {
            return list.get(position);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.pay_commen_list, null);
                viewHolder.address = (TextView) convertView.findViewById(R.id.address);
                viewHolder.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder.detail = (TextView) convertView.findViewById(R.id.detail_pay);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.address.setText(getItem(position).getAddress());
            viewHolder.time.setText(getItem(position).getQuarter());
            viewHolder.detail.setText(getItem(position).getPayType()+"缴纳账单");
            
            return convertView;
        }
        
        class ViewHolder
        {
            public TextView address;
            
            public TextView time;
            
            public TextView detail;
            
        }
        
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }
        
    }
    
    @Override
    public void clearData()
    {
    }
    
}
