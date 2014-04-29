/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-3-27 下午2:08:37 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.cinema.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.MediaOrderVO;

/**
 *付费方式的adapter,listView,按年，按月，按次
 */
public class PaymethodAdapter extends BaseAdapter
{
    private static final String TAG = "PaymethodAdapter";
    
    private LayoutInflater mInflater = null;
    
    private MediaOrderVO vo;
    
    private Context context;
    
    public PaymethodAdapter(Context context, MediaOrderVO vo)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.vo = vo;
    }
    
    @Override
    public int getCount()
    {
        return vo.getPriceList().size();
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return vo.getPriceList().get(position);
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
        ViewHolder holder = new ViewHolder();
        ;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.paymethod_item, null);
            holder.payType = (TextView) convertView.findViewById(R.id.payType);
            holder.payNum = (TextView) convertView.findViewById(R.id.payMoneyNum);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.payType.setText(vo.getPriceList()
                .get(position)
                .getStrategyName());
        holder.payNum.setText(String.valueOf(vo.getPriceList()
                .get(position)
                .getMoney()));
        
        return convertView;
    }
    
}

class ViewHolder
{
    public TextView payType;
    
    public TextView payNum;
}