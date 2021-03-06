package com.changhong.smarthome.phone.cinema.adapter;

import io.vov.vitamio.utils.StringUtils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.VideoFile;
import com.lidroid.xutils.BitmapUtils;

/**
* @ClassName: RecordAdapter
* @author yang_jun
* @Description: 历史记录
*/
public class RecordAdapter extends BaseAdapter
{
    
    // ListView listView;
    LayoutInflater m_inflater;
    
    Context context;
    
    ArrayList<VideoFile> cinemaList;
    
    public RecordAdapter(Context context, ArrayList<VideoFile> cinemaList2)
    {
        
        m_inflater = LayoutInflater.from(context);
        this.context = context;
        this.cinemaList = cinemaList2;
    }
    
    @Override
    public int getCount()
    { 
        if (cinemaList != null)
        {
            return cinemaList.size();
        }
        else
        {
            return 0;
        }
        
    }
    
    @Override
    public Object getItem(int position)
    { 
        if (cinemaList != null)
        {
            return cinemaList.get(position);
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public long getItemId(int position)
    { 
        if (cinemaList != null)
        {
            return position;
        }
        else
        {
            return 0;
        }
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder3 viewholder;
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.a2);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.a2);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            viewholder = new ViewHolder3();
            
            convertView = m_inflater.inflate(R.layout.playrecorded_item,
                    parent,
                    false);
            viewholder.percent = (TextView) convertView.findViewById(R.id.percent);
            viewholder.detail = (TextView) convertView.findViewById(R.id.detail);
            viewholder.hotimage = (ImageView) convertView.findViewById(R.id.hotimage);
            
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (ViewHolder3) convertView.getTag();
        }
        viewholder.detail.setText(cinemaList.get(position).getName().toString());
        viewholder.percent.setText("播放到："
                + StringUtils.generateTime(cinemaList.get(position).getTime() / 1000));
        bitmapUtilsHead.display(viewholder.hotimage, cinemaList.get(position)
                .getPicUrl());
        /*viewholder.percent.setText(cinemaList.get(position).getPercent().toString());
        viewholder.detail.setText(cinemaList.get(position).getDetail().toString());*/
        
        // viewholder.filecontentProvider.setText(contentProviders[position]);
        return convertView;
    }
    
}

class ViewHolder3
{
    TextView percent;
    
    TextView detail;
    
    ImageView hotimage;
    //ImageView row_title;
    
}
