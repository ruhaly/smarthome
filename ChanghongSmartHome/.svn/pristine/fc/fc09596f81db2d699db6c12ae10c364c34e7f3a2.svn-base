package com.changhong.smarthome.phone.cinema.adapter;


import java.util.List;

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
import com.changhong.smarthome.phone.cinema.entry.LocalVideoInfo;
import com.lidroid.xutils.BitmapUtils;


/**
* @ClassName: LocalCinemaAdapter
* @author yang_jun
* @Description:显示本地手机中的视频 
*/
public class LocalCinemaAdapter extends BaseAdapter
{
    private static final String TAG = "LocalCinemaAdapter";
    LayoutInflater m_inflater;
    
    private View selectedView;
    
    Context context;
    
    List<LocalVideoInfo> allVideoList;
    
    private boolean isSelected;
    int selected_position = -1;
    /**
     * @param uploadingActivity
     * @param allVideoList
     */
    public LocalCinemaAdapter(Context context, List<LocalVideoInfo> allVideoList)
    {
        m_inflater = LayoutInflater.from(context);
        this.context = context;
        this.allVideoList = allVideoList;
    }
    
    public void setIsSelected(boolean isShowDelete,int position)
    {
        this.isSelected = isShowDelete;
        this.selected_position = position;
        Log.i(TAG, "isSelected:"+isSelected+"-----selected_position:"+selected_position);
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount()
    {
        return allVideoList.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        return allVideoList.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderLocalCinema viewholder;
        if (convertView == null)
        {
            viewholder = new ViewHolderLocalCinema();
            convertView = m_inflater.inflate(R.layout.localcinema_item,
                    parent,
                    false);
            viewholder.videoName = (TextView) convertView.findViewById(R.id.localcinematext);
            
            viewholder.videoImage = (ImageView) convertView.findViewById(R.id.localcinemaimage);
            convertView.setTag(viewholder);
            
        }
        else
        {
            viewholder = (ViewHolderLocalCinema) convertView.getTag();
        }
        viewholder.videoName.setText(allVideoList.get(position)
                .getDisplayName());
        viewholder.videoImage.setImageBitmap(allVideoList.get(position).getVideoBitMap());
        selectedView = convertView.findViewById(R.id.select_mark);
        if(selected_position == position){
            selectedView.setVisibility( View.VISIBLE);//设置删除按钮是否显示    
        }else{
            selectedView.setVisibility(  View.GONE);
        }
        return convertView;
    }
}

class ViewHolderLocalCinema
{
    ImageView videoImage;
    
    TextView videoName;
    
}
