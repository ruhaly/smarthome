package com.changhong.smarthome.phone.sns.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.BitMapUtil;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.TSnsReply;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

/**
 * 随拍、二手信息详情的评论Adapter<功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ShareInfoAdapter extends BaseAdapter
{
    private static final String TAG = "ShareInfoAdapter";
    
    private Context context;
    
    private List<TSnsReply> replies;
    
    private BitmapUtils bitmapUtilsHead;
    
    public ShareInfoAdapter(Context context, List<TSnsReply> replies)
    {
        this.context = context;
        this.replies = replies;
        bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.login_new_image);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.login_new_image);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        
    }
    
    @Override
    public int getCount()
    {
        return replies.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        return replies.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        
        if (null == convertView)
        {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.share_info_item, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.friend_icon);
            viewHolder.content = (TextView) convertView.findViewById(R.id.friend_content);
            convertView.setTag(viewHolder);
            
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TSnsReply reply = replies.get(position);
        Log.d(TAG, "reply.getUser().getNickname()--->"
                + reply.getUser().getNickname());
        
        viewHolder.content.setText(reply.getContent());
        
        String url = Constant.URL_iconUrl + reply.getUser().getHeadImgPath();
        Log.d(TAG, "url-->" + url);
        bitmapUtilsHead.display(viewHolder.icon,
                url,
                new BitmapLoadCallBack<View>()
                {
                    
                    @Override
                    public void onLoadCompleted(View container, String uri,
                            Bitmap bitmap, BitmapDisplayConfig config,
                            BitmapLoadFrom from)
                    {
                        // TODO Auto-generated method stub
                        viewHolder.icon.setImageBitmap(BitMapUtil.toRoundCorner(bitmap,
                                Constant.BITMAP_PIXELS));
                    }
                    
                    @Override
                    public void onLoadFailed(View container, String uri,
                            Drawable drawable)
                    {
                        // TODO Auto-generated method stub
                        
                    }
                });
        return convertView;
    }
    
    class ViewHolder
    {
        ImageView icon;
        
        TextView content;
    }
    
    /**
     * 更新数据
     * @param shareBeans
     */
    public void updateData(List<TSnsReply> list)
    {
        this.replies = null;
        this.replies = list;
        notifyDataSetChanged();
    }
    
}
