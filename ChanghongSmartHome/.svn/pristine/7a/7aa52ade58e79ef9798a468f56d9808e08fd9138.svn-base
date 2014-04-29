package com.changhong.smarthome.phone.sns.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingOrderListVO.GroupBuyingOrderList.Orders;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FollowPeopleAdapter extends BaseAdapter
{
    private Context context;
    
//    private List<FollowPeopleBean> followPeopleBeans;
    List<Orders> list;
    
    public FollowPeopleAdapter(Context context, List<Orders> list)
    {
        this.context = context;
        this.list = list;
    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return list.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return list.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.follow_people_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.follow_people_name);
            viewHolder.tel = (TextView) convertView.findViewById(R.id.follow_people_tel);
            viewHolder.orderNum = (TextView) convertView.findViewById(R.id.follow_people_order_num);
            convertView.setTag(viewHolder);
            
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getAccountId());
        viewHolder.tel.setText(list.get(position).getPhone());
        viewHolder.orderNum.setText(list.get(position).getNum());
        return convertView;
    }
    
    class ViewHolder
    {
        private TextView name;
        
        private TextView tel;
        
        private TextView orderNum;
    }
}
