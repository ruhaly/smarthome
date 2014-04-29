package com.changhong.smarthome.phone.property.adapter;

import io.vov.vitamio.utils.Log;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.ExchangeVO;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.lidroid.xutils.BitmapUtils;

/**
 * <功能详细描述>
 * 可兑换积分adapter
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:28:43
 */
public class IntegrationExchangeAdapter extends BaseAdapter

{
    LayoutInflater m_inflater;
    
    Context context;
    
    List<ExchangeVO> list = null;
    
    public IntegrationExchangeAdapter(Context context, List<ExchangeVO> list)
    {
        m_inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        Log.i( "----------------"+list.size()+"-------------");
        
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
        ViewHolder viewholder;
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            viewholder = new ViewHolder();
            convertView = m_inflater.inflate(R.layout.intergrationexchangeitem,
                    parent,
                    false);
            viewholder.image = (ImageView) convertView.findViewById(R.id.exchangeimage);
            viewholder.textViewName = (TextView) convertView.findViewById(R.id.exhange_name);
            viewholder.textViewNeedScore = (TextView) convertView.findViewById(R.id.exhange_intergration);
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }
        
            viewholder.textViewName.setText(list.get(position)
                    .getName());
            viewholder.textViewNeedScore.setText(list.get(position)
                    .getPoint()+"");
            bitmapUtilsHead.display(viewholder.image, list.get(position)
                    .getGiftPicUrl());
        return convertView;
    }
    
}

class ViewHolder
{
    ImageView image;
    
    TextView textViewName;
    
    TextView textViewNeedScore;
}
