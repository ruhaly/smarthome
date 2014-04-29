/**
 * MessageAdapter.java
 * com.pactera.ch_bedframe.adapter
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-3 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.entity.MessageInfo;
import com.changhong.foundation.logic.MsgDeleteLogic;

/**
 * ClassName:MessageAdapter Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午5:31:03
 */
public class MessageAdapter extends BaseAdapter
{
    
    public Context context;
    
    public List<MessageInfo> list;
    
    public ListView listview;
    
    public MessageAdapter(Context context, List<MessageInfo> list,
            ListView listview, MsgDeleteLogic msglogic)
    {
        super();
        this.context = context;
        this.list = list;
        this.listview = listview;
    }
    
    @Override
    public int getCount()
    {
        return list.size();
    }
    
    @Override
    public MessageInfo getItem(int arg0)
    {
        return list.get(arg0);
    }
    
    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.msg_item_layout, parent, false);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tvMsg = (TextView) convertView.findViewById(R.id.tv_msg);
            holder.imgMsg = (ImageView) convertView.findViewById(R.id.imgMsg);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //        holder.tvDate.setText(DateFormat(getItem(position).getDate()));
        switch (getItem(position).getOriginaltype())
        {
            case 1:
            {
                holder.imgMsg.setImageResource(R.drawable.huiming);
                break;
            }
            case 2:
            {
                holder.imgMsg.setImageResource(R.drawable.wuye);
                break;
            }
            case 3:
            {
                holder.imgMsg.setImageResource(R.drawable.wuye);
                break;
            }
            case 4:
            {
                holder.imgMsg.setImageResource(R.drawable.wuye);
                break;
            }
            case 5:
            {
                holder.imgMsg.setImageResource(R.drawable.anfang);
                break;
            }
            case 6:
            {
                holder.imgMsg.setImageResource(R.drawable.system);
                break;
            }
            default:
                break;
        }
        holder.tvMsg.setText(getItem(position).getTitle());
        return convertView;
    }
    
    class ViewHolder
    {
        TextView tvMsg;
        
        ImageView imgMsg;
        
        TextView tvDate;
        // Button btnDelete;
    }
    
    public String DateFormat(String s)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dDate = null;
        String reTime = "";
        try
        {
            dDate = format.parse(s);
            DateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
            reTime = format2.format(dDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return reTime;
    }
}
