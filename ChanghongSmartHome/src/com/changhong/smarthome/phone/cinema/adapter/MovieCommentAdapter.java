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
import com.changhong.smarthome.phone.cinema.entry.MovieComment;
import com.lidroid.xutils.BitmapUtils;

/**
* @ClassName: MovieCommentAdapter
* @author yang_jun
* @Description: 影片的评论
*/
public class MovieCommentAdapter extends BaseAdapter
{
    
    // ListView listView;
    LayoutInflater m_inflater;
    
    Context context;
    
    ArrayList<MovieComment> cinemaList;
    
    public MovieCommentAdapter(Context context, ArrayList<MovieComment> cinemaList2)
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
        MovieSelectsViewHolder viewholder;
        BitmapUtils bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        if (convertView == null)
        {
            viewholder = new MovieSelectsViewHolder();
            convertView = m_inflater.inflate(R.layout.moviecomment_item,
                    parent,
                    false);
            viewholder.movieCommentUserId = (TextView) convertView.findViewById(R.id.movieCommentUserId);
            
            viewholder.movieCommentComment = (TextView) convertView.findViewById(R.id.movieCommentComment);
            viewholder.movieCommentTime = (TextView) convertView.findViewById(R.id.movieCommentTime);
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (MovieSelectsViewHolder) convertView.getTag();
        }
        viewholder.movieCommentUserId.setText(cinemaList.get(position).getUserId());
        
        viewholder.movieCommentComment.setText(cinemaList.get(position).getComment());
        viewholder.movieCommentTime.setText("发表于"+cinemaList.get(position).getTime());
        
        // viewholder.filecontentProvider.setText(contentProviders[position]);
        return convertView;
    }
    
}

class MovieSelectsViewHolder
{
    TextView movieCommentUserId;
    
    TextView movieCommentComment;
    
    TextView movieCommentTime;
    
}