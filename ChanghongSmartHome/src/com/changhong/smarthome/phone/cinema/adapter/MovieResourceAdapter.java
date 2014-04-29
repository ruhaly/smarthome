package com.changhong.smarthome.phone.cinema.adapter;

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
import com.changhong.smarthome.phone.cinema.entry.MovieSource;
import com.lidroid.xutils.BitmapUtils;

/**
* @ClassName: MovieResourceAdapter
* @author yang_jun
* @Description: show the resources of the movie,like qiyi,youku
*/
public class MovieResourceAdapter extends BaseAdapter
{
    
    // ListView listView;
    LayoutInflater m_inflater;
    
    Context context;
    
    ArrayList<MovieSource> cinemaList;
    
    public MovieResourceAdapter(Context context, ArrayList<MovieSource> cinemaList2)
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
        MovieResourceViewHolder viewholder;
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            viewholder = new MovieResourceViewHolder();
            convertView = m_inflater.inflate(R.layout.movieresource_item,
                    parent,
                    false);
            viewholder.sourceTextView = (TextView) convertView.findViewById(R.id.sourceTextView);
            
            viewholder.sourceImageView = (ImageView) convertView.findViewById(R.id.sourceImageView);
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (MovieResourceViewHolder) convertView.getTag();
        }
        viewholder.sourceTextView.setText(cinemaList.get(position).getName());
        
        bitmapUtilsHead.display(viewholder.sourceImageView, cinemaList.get(position)
                .getSourcePicUrl());
        
        // viewholder.filecontentProvider.setText(contentProviders[position]);
        return convertView;
    }
    
}

class MovieResourceViewHolder
{
    ImageView sourceImageView;
    
    TextView sourceTextView;
    
}
