package com.changhong.smarthome.phone.property.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.changhong.smarthome.phone.foundation.bean.PayServiceBean;

public class PayHistoryListActivity extends BaseActivity
{
    
    private ImageView back_history;
    
    private RelativeLayout yearLeft;
    
    private RelativeLayout yearRight;
    
    private TextView year;
    
    private ListView historyList;
    
    private int index = 0;
    
    private String[] yearMore;
    
    private List<PayServiceBean> list = new ArrayList<PayServiceBean>();
    
    private Adapter historyAdapter;
    
    /**
     * 
     * 初始化数据<BR>
     * [功能详细描述]
     * @see com.changhong.sdk.activity.SuperActivity#initData()
     */
    @Override
    public void initData()
    {
        PayServiceBean bean = new PayServiceBean();
        bean.setMoney("220");
        bean.setQuarter("第一季度");
        bean.setState("已缴清");
        list.add(bean);
        bean = new PayServiceBean();
        bean.setMoney("220");
        bean.setQuarter("第二季度");
        bean.setState("已缴清");
        list.add(bean);
        bean = new PayServiceBean();
        bean.setMoney("220");
        bean.setQuarter("第三季度");
        bean.setState("待缴费");
        list.add(bean);
        
    }
    
    /**
     * 
     * 初始化界面<BR>
     * [功能详细描述]
     * @param paramBundle
     * @see com.changhong.sdk.activity.SuperActivity#initLayout(android.os.Bundle)
     */
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.pay_history);
        back_history = (ImageView) findViewById(R.id.back_history);
        yearLeft = (RelativeLayout) findViewById(R.id.left);
        yearRight = (RelativeLayout) findViewById(R.id.right);
        year = (TextView) findViewById(R.id.year);
        historyList = (ListView) findViewById(R.id.histoty_list);
        yearMore = new String[] { "2014", "2013", "2012", "2012", "2011",
                "2010" };
        year.setText(yearMore[index]);
        back_history.setOnClickListener(this);
        yearLeft.setOnClickListener(this);
        yearRight.setOnClickListener(this);
        initAdapter();
    }
    
    /**
    *
    *  初始化adapter<BR>
    * [功能详细描述]
    */
    public void initAdapter()
    {
        if (null == historyAdapter)
        {
            historyAdapter = new Adapter();
            historyList.setAdapter(historyAdapter);
        }
        else
        {
            historyAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * @param v
     * @see com.changhong.sdk.activity.SuperActivity#onClick(android.view.View)
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(android.view.View v)
    {
        switch (v.getId())
        {
            case R.id.back_history:
            {
                finish();
                break;
            }
            case R.id.left:
            {
                if (index == 0)
                {
                    System.out.println("----------------"+index+"---------------");
                }
                else
                {
                    index--;
                    year.setText(yearMore[index]);
                    PayServiceBean bean = new PayServiceBean();
                    bean.setMoney("220");
                    bean.setQuarter("第一季度");
                    bean.setState("已缴清");
                    list.add(bean);
                    initAdapter();
                    System.out.println("----------------"+index+"---------------");
                }
                break;
            }
            case R.id.right:
            {
                if (index == yearMore.length - 1)
                {
                    System.out.println("----------------"+index+"---------------");
                }
                else
                {
                    index++;
                    PayServiceBean bean = new PayServiceBean();
                    bean.setMoney("220");
                    bean.setQuarter("第一季度");
                    bean.setState("已缴清");
                    list.add(bean);
                    bean = new PayServiceBean();
                    bean.setMoney("220");
                    bean.setQuarter("第二季度");
                    bean.setState("已缴清");
                    list.add(bean);
                    initAdapter();
                    year.setText(yearMore[index]);
                    System.out.println("right");
                }
                break;
            }
            default:
            {
                break;
            }
        }
    };
    
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
                        .inflate(R.layout.pay_history_list, null);
                viewHolder.quarter = (TextView) convertView.findViewById(R.id.quarter);
                viewHolder.money = (TextView) convertView.findViewById(R.id.money);
                viewHolder.state = (TextView) convertView.findViewById(R.id.pay_state);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.money.setText(getItem(position).getMoney());
            viewHolder.quarter.setText(getItem(position).getQuarter());
            viewHolder.state.setText(getItem(position).getState());
            
            return convertView;
        }
        
        class ViewHolder
        {
            public TextView quarter;
            
            public TextView money;
            
            public TextView state;
            
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
