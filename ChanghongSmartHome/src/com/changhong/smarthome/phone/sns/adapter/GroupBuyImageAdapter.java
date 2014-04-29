/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-15 下午5:32:29 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.sns.adapter;

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.adapter.ImageAdapter.ViewHolder;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GroupBuyImageAdapter extends BaseAdapter

{
    private Context context;
    
    private String picsArray[];
    
    private BitmapUtils bitmaputils;
    
    /**
     * @param context2
     * @param picsArray
     */
    public GroupBuyImageAdapter(Context context, String[] picsArray)
    {
        this.context = context;
        this.picsArray = picsArray;
        bitmaputils = new BitmapUtils(context);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.picture);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }
    
    @Override
    public int getCount()
    {
        return picsArray==null?0:picsArray.length;
    }
    
    @Override
    public Object getItem(int position)
    {
        return picsArray[position];
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (null == convertView)
        {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.ad_item_layout, null);
            holder = new ViewHolder();
            holder.img_ad = (ImageView) convertView.findViewById(R.id.img_ad);
            holder.frame = (FrameLayout) convertView.findViewById(R.id.frame);
            holder.progressBarFrame = (FrameLayout) convertView.findViewById(R.id.progressBarFrame);
            
            convertView.setTag(holder);
            
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.frame.setLayoutParams(new LinearLayout.LayoutParams(
                CHUtils.getScreenWidth(context), 500));
       
        String url = picsArray[position];
        bitmaputils.display(holder.img_ad, url, new BitmapLoadCallBack<View>()
        {
            @Override
            public void onLoadCompleted(View container, String uri,
                    Bitmap bitmap, BitmapDisplayConfig config,
                    BitmapLoadFrom from)
            {
                ((ImageView) container).setImageBitmap(bitmap);
                holder.progressBarFrame.setVisibility(View.GONE);
            }
            
            @Override
            public void onLoadFailed(View container, String uri,
                    Drawable drawable)
            {
                ((ImageView) container).setImageDrawable(drawable);
                holder.progressBarFrame.setVisibility(View.GONE);
                
            }
            
        });
        
        // holder.img_ad.setImageResource(R.drawable.defaultloadingimage);
        return convertView;
    }
    
    class ViewHolder
    {
        private ImageView img_ad;
        
        private FrameLayout frame;
        
        private FrameLayout progressBarFrame;
    }
}
