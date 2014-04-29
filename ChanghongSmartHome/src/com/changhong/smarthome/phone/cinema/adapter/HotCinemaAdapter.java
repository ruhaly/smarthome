package com.changhong.smarthome.phone.cinema.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.view.MyTextView;
import com.lidroid.xutils.BitmapUtils;

/**
* @ClassName: HotCinemaAdapter
* @author yang_jun
* @Description: adapter for gridview
*/
public class HotCinemaAdapter extends BaseAdapter
{
    
    // ListView listView;
    LayoutInflater m_inflater;
    
    Context context;
    
    ArrayList<Cinema> cinemaList;
    
    public HotCinemaAdapter(Context context, ArrayList<Cinema> cinemaList2)
    {
        
        m_inflater = LayoutInflater.from(context);
        this.context = context;
        this.cinemaList = cinemaList2;
    }
    
    @Override
    public int getCount()
    { 
        return cinemaList.size();
    }
    
    @Override
    public Object getItem(int position)
    { 
        return cinemaList.get(position);
    }
    
    @Override
    public long getItemId(int position)
    { 
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder1 viewholder;
        String payornot = cinemaList.get(position).getPrice();
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            viewholder = new ViewHolder1();
            convertView = m_inflater.inflate(R.layout.hotcinema_item,
                    parent,
                    false);
            viewholder.hottitle_name = (TextView) convertView.findViewById(R.id.hottitle_name);
            viewholder.hottitle_point = (TextView) convertView.findViewById(R.id.hottitle_point);
            viewholder.hotimage = (ImageView) convertView.findViewById(R.id.hotimage);
            viewholder.hotLayout_shadow = (RelativeLayout) convertView.findViewById(R.id.hotLayout_shadow);
            viewholder.mytextview = (MyTextView) convertView.findViewById(R.id.mytextview);
           // viewholder.row_title = (ImageView) convertView.findViewById(R.id.row_title);
            //viewholder.row_text = (TextView) convertView.findViewById(R.id.row_text);
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (ViewHolder1) convertView.getTag();
        }
        viewholder.hottitle_name.setText(cinemaList.get(position)
                .getContentName());
        if(!cinemaList.get(position)
        .getContentScore().equals("")){
        viewholder.hottitle_point.setText(cinemaList.get(position)
                .getContentScore());}else{
                    viewholder.hottitle_point.setText(cinemaList.get(position)
                            .getContentScore());
                }
        bitmapUtilsHead.display(viewholder.hotimage, cinemaList.get(position)
                .getPicUrl());
        if(!cinemaList.get(position)
                .getPrice().equals("")){
        viewholder.hotLayout_shadow.setVisibility(View.VISIBLE);
        viewholder.mytextview.setVisibility(View.VISIBLE);
        viewholder.mytextview.setText("￥"+cinemaList.get(position).getPrice());
        }
        return convertView;
    }
    
}

class ViewHolder1
{
    TextView hottitle_name;
    
    TextView hottitle_point;
    
    ImageView hotimage;
    
    ImageView row_title;
    
    TextView row_text;
    
    RelativeLayout hotLayout_shadow;
    
    MyTextView mytextview;
    
}
