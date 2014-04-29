package com.changhong.smarthome.phone.cinema.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.entry.MovieSelects;
import com.lidroid.xutils.BitmapUtils;

/**
* @ClassName: MovieSelectionsAdapter
* @author yang_jun
* @Description: 显示集数
*/
public class MovieSelectionsAdapter extends BaseAdapter
{
    
    // ListView listView;
    LayoutInflater m_inflater;
    
    Context context;
    
    ArrayList<MovieSelects> cinemaList;
    
    public MovieSelectionsAdapter(Context context, ArrayList<MovieSelects> cinemaList2)
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
        MovieSelectionsViewHolder viewholder;
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            viewholder = new MovieSelectionsViewHolder();
            convertView = m_inflater.inflate(R.layout.movieselctions_item,
                    parent,
                    false);
            viewholder.selectionsTextView = (TextView) convertView.findViewById(R.id.selectionsTextView);
     
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (MovieSelectionsViewHolder) convertView.getTag();
        }
        viewholder.selectionsTextView.setText(cinemaList.get(position).getSeqNum()+"");
        
     
        
        // viewholder.filecontentProvider.setText(contentProviders[position]);
        return convertView;
    }
    
}

class MovieSelectionsViewHolder
{
    
    TextView selectionsTextView;
    
}
