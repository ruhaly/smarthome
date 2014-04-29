package com.changhong.smarthome.phone.property.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.ExchangeHisList;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.lidroid.xutils.BitmapUtils;

/**
 * <功能详细描述>
 * 我的积分兑换adapter
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:28:21
 */
public class MyIntegrationAdapter extends BaseAdapter
{
    
    private static final String TAG = "MyIntegrationAdapter";
    
    LayoutInflater mInflater = null;
    
    List<ExchangeHisList> list = null;
    
    private Context context;
    
    public MyIntegrationAdapter(Context context, List<ExchangeHisList> list)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }
    
    @Override
    public int getCount()
    {
    	if (list.size() > 0)
        {
            return list.size();
        }
        else
        {
            return 0;
        }
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
    	return list.size() > 0 ? list.get(position) : null;
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
        ViewHolder holder = null;
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.myintegrationexchangeitem,
                    null);
            holder.goodsImage = (ImageView) convertView.findViewById(R.id.goodsImage);
            holder.goodsName = (TextView) convertView.findViewById(R.id.goodsName);
            holder.needIntegration = (TextView) convertView.findViewById(R.id.needIntegration);
            holder.validityTimeFrom = (TextView) convertView.findViewById(R.id.validityTimeFrom);
            holder.validityTimeTo = (TextView) convertView.findViewById(R.id.validityTimeTo);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.goodsName.setText(list.get(position).getName());
        holder.needIntegration.setText(list.get(position).getPoint() + "");
        holder.validityTimeFrom.setText(list.get(position).getBeginDate().toString());
        holder.validityTimeTo.setText(list.get(position).getEndDate().toString());
        bitmapUtilsHead.display(holder.goodsImage,list.get(position).getGiftPicUrl());
        return convertView;
    }
    
    static class ViewHolder
    {
        private ImageView goodsImage;
        
        public TextView goodsName;
        
        public TextView needIntegration;
        
        public TextView validityTimeFrom;
        
        public TextView validityTimeTo;
    }
    
}
