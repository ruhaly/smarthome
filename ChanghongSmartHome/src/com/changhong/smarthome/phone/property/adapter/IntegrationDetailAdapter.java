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
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.IntegrationChangeItemVO;
import com.lidroid.xutils.BitmapUtils;

/**
 * <功能详细描述>
 * 积分详情adapter
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:29:30
 */
public class IntegrationDetailAdapter extends BaseAdapter
{
    private static final String TAG = "IntegrationDetailAdapter";
    
    LayoutInflater mInflater = null;
    
    List<IntegrationChangeItemVO> list;
    
    private Context context;
    
    public IntegrationDetailAdapter(Context context,
    		List<IntegrationChangeItemVO> list)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        Log.i(TAG, "-----list.size():" + list.size());
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
            convertView = mInflater.inflate(R.layout.integrationdetailitem,
                    null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.integrationChange = (TextView) convertView.findViewById(R.id.integrationChange);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getPath());
        holder.date.setText(list.get(position).getActiveDate());
        long score = list.get(position).getPoint();
        if(score>0){
            holder.integrationChange.setText("+"+score);
            holder.integrationChange.setTextColor(context.getResources().getColor(R.color.color_21b86d));
        }else{
            holder.integrationChange.setText(""+score);
            holder.integrationChange.setTextColor(context.getResources().getColor(R.color.color_ed4e4e));
        }
        
        return convertView;
    }
    
    static class ViewHolder
    {
        public TextView name;
        
        public TextView date;
        
        public TextView integrationChange;
    }
}
