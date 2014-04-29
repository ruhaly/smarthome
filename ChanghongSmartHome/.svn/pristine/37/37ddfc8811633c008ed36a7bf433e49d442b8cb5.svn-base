package com.changhong.smarthome.phone.sns.adapter;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.sdk.baseapi.BitMapUtil;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class InteractShareAdapter extends BaseAdapter

{
    private static final String TAG = "InteractShareAdapter";
    
    private Context context;
    
    private List<ShareBean> shareBeans;
    
    private LayoutInflater mInflater;
    
    private BitmapUtils bitmapUtilsHead;
    
    private Handler handler;
    
    public InteractShareAdapter(Context context, List<ShareBean> shareBeans,
            Handler handler)
    {
        this.context = context;
        this.shareBeans = shareBeans;
        this.handler = handler;
        mInflater = LayoutInflater.from(context);
        bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.login_new_image);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.login_new_image);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return shareBeans.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return shareBeans.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        final ViewHolder viewHolder;
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.interact_share_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvNickName = (TextView) convertView.findViewById(R.id.tv_nick_name);
            viewHolder.replyBtn = (TextView) convertView.findViewById(R.id.msg_reply_btn);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.msgReplyList = (LinearLayout) convertView.findViewById(R.id.msg_reply_list);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShareBean shareBean = shareBeans.get(position);
        viewHolder.tvNickName.setText(shareBean.getNickName());
        
        viewHolder.tvContent.setText(shareBean.getTitle());
        
        final Calendar c = Calendar.getInstance();
        
        int mYear = c.get(Calendar.YEAR); //获取当前年份 
        
        int mMonth = c.get(Calendar.MONTH) + 1;//获取当前月份 
        
        int mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码     
        //        try
        //        {
        //            String timeY = DateUtil.Y_DF.format(DateUtil.STANDARD_DF.parse(shareBean.getCreateTime()));
        //            String timeM = DateUtil.M_DF.format(DateUtil.STANDARD_DF.parse(shareBean.getCreateTime()));
        //            String timeD = DateUtil.D_DF.format(DateUtil.STANDARD_DF.parse(shareBean.getCreateTime()));
        //            String timeHM = DateUtil.TIME_H_M_DF.format(DateUtil.STANDARD_DF.parse(shareBean.getCreateTime()));
        //            String timeString = DateUtil.M_D_DF.format(DateUtil.STANDARD_DF.parse(shareBean.getCreateTime()));
        //            if (timeM.startsWith("0"))
        //            {
        //                timeM = timeM.substring(1, timeM.length());
        //            }
        //            if (timeD.startsWith("0"))
        //            {
        //                timeD = timeD.substring(1, timeD.length());
        //            }
        //            if (timeY.equals(String.valueOf(mYear))
        //                    && timeM.equals(String.valueOf(mMonth))
        //                    && timeD.equals(String.valueOf(mDay)))
        //            {
        //                viewHolder.tvTime.setText(context.getResources()
        //                        .getString(R.string.unit_today) + " " + timeHM);
        //            }
        //            else
        //            {
        //                viewHolder.tvTime.setText(timeString);
        //            }
        //            
        //        }
        //        catch (ParseException e)
        //        {
        //            // TODO Auto-generated catch block
        //            e.printStackTrace();
        //        }
        
        String url = Constant.URL_iconUrl + shareBean.getCreatorPicUrl();
        Log.d(TAG, "url-->" + url);
        bitmapUtilsHead.display(viewHolder.ivIcon,
                url,
                new BitmapLoadCallBack<View>()
                {
                    
                    @Override
                    public void onLoadCompleted(View container, String uri,
                            Bitmap bitmap, BitmapDisplayConfig config,
                            BitmapLoadFrom from)
                    {
                        // TODO Auto-generated method stub
                        viewHolder.ivIcon.setImageBitmap(BitMapUtil.toRoundCorner(bitmap,
                                Constant.BITMAP_PIXELS));
                    }
                    
                    @Override
                    public void onLoadFailed(View container, String uri,
                            Drawable drawable)
                    {
                        // TODO Auto-generated method stub
                        
                    }
                });
        //        bitmapUtilsHead.display(viewHolder.ivIcon, url);
        viewHolder.ivIcon.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                //                intent.setClass(context, FriendInfoActivity.class);
                intent.putExtra("userId", shareBean.getCreatorId());
                //                context.startActivity(intent);
                Log.d(TAG, "ivIcon.setOnClick---->");
            }
        });
        
        //        viewHolder.msgReplyList.setVisibility(View.VISIBLE);
        //        List<String> ss = new ArrayList<String>();
        //        ss.add("aa");
        //        ss.add("bb");
        //        ss.add("cc");
        //        dynamicAdd(viewHolder.msgReplyList, ss);
        viewHolder.replyBtn.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Message msg = new Message();
                msg.what = Constant.MSG_REPLY;
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }
    
    /**
     * 添加TextView
     * @param linearLayout
     * @param ss
     */
    public void dynamicAdd(LinearLayout linearLayout, List<String> ss)
    {
        for (String s : ss)
        {
            TextView tw = new TextView(linearLayout.getContext());
            tw.setText(s);
            linearLayout.addView(tw);
        }
    }
    
    class ViewHolder
    {
        private ImageView ivIcon;
        
        private TextView tvNickName;
        
        private TextView tvContent;
        
        private TextView tvTime;
        
        private TextView replyBtn;
        
        private LinearLayout msgReplyList;
    }
}
