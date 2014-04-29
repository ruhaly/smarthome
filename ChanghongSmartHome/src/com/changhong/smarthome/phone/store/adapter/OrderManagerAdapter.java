package com.changhong.smarthome.phone.store.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.activity.OrderNewDatailActivity;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

public class OrderManagerAdapter extends BaseAdapter
{
    private static final String TAG = "OrderManagerAdapter";
    
    private List<OrderInfoBean> mList;
    
    private Context mContext;
    
    private BitmapUtils mBitmapUtils;
    
    private BitmapDisplayConfig bigPicDisplayConfig;
    
    private int screenHeight;
    
    private int screenWidth;
    
    private int itemHeight;
    
    public OrderManagerAdapter(Context context, BitmapUtils bitmapUtils,
            List<OrderInfoBean> list)
    {
        mContext = context;
        mBitmapUtils = bitmapUtils;
        mList = list;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        bigPicDisplayConfig = new BitmapDisplayConfig();
        BitmapSize bitmapSize = new BitmapSize((screenWidth * 270) / 720,
                (screenHeight * 199) / 1280);
        bigPicDisplayConfig.setBitmapMaxSize(bitmapSize);
        itemHeight = (screenHeight * 200) / 1280;
    }
    
    //    public void setList(List<OrderInfoBean> list)
    //    {
    //        mList = list;
    //    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        if (mList != null)
        {
            return mList.size();
        }
        return 0;
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        if (mList != null && position < mList.size() && position > 0)
        {
            return mList.get(position);
        }
        return null;
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
        viewholder holder = null;
        
        if (convertView == null)
        {
            holder = new viewholder();
            convertView = View.inflate(mContext, R.layout.order_item, null);
            holder.subLayout = (RelativeLayout) convertView.findViewById(R.id.order_item_layout);
            //            android.widget.AbsListView.LayoutParams layoutParams = new android.widget.AbsListView.LayoutParams(
            //                    screenWidth * (720-28)/720, itemHeight);
            //            holder.subLayout.setLayoutParams(layoutParams);
            
            RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) holder.subLayout.getLayoutParams();
            layoutParams.width = screenWidth * (720 - 28) / 720;
            layoutParams.height = itemHeight;
            layoutParams.rightMargin = screenWidth * 14 / 720;
            layoutParams.leftMargin = screenWidth * 14 / 720;
            holder.subLayout.setLayoutParams(layoutParams);
            
            holder.imageView = (ImageView) convertView.findViewById(R.id.news_item_icon);
            RelativeLayout.LayoutParams params1 = (android.widget.RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            params1.height = (screenHeight * 199) / 1280;
            params1.width = (screenWidth * 270) / 720;
            //            params1.leftMargin = (screenWidth * 40) / 720;
            //            params1.rightMargin = (screenWidth * 42) / 720;
            //            params1.topMargin = (screenWidth * 40) / 720;
            holder.imageView.setLayoutParams(params1);
            
            holder.nameTextView = (TextView) convertView.findViewById(R.id.news_item_name);
            holder.descTextView = (TextView) convertView.findViewById(R.id.news_item_desc);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.order_star);
            holder.priceTextView = (TextView) convertView.findViewById(R.id.news_item_price);
            holder.priceTextView1 = (TextView) convertView.findViewById(R.id.news_item_price_1);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.news_item_time);
            holder.haspaytTextView = (TextView) convertView.findViewById(R.id.news_item_haspay);
            
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 36) / 720);
            holder.descTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 24) / 720);
            holder.priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 24) / 720);
            holder.priceTextView1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 24) / 720);
            holder.timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 30) / 720);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (viewholder) convertView.getTag();
        }
        final OrderInfoBean item = mList.get(position);
        if (item != null)
        {
            //1-团购拼单2-商圈订单
            int type = item.getOrderType();
            Log.d(TAG, "curitem type = " + type);
            
            if (type == 2)
            {
                holder.nameTextView.setVisibility(View.GONE);
                holder.ratingbar.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) holder.descTextView.getLayoutParams();
                params.topMargin = (15 * screenWidth) / 720;
                //                params.rightMargin = (20 * screenWidth) / 720;
                holder.descTextView.setLayoutParams(params);
            }
            else
            {
                holder.nameTextView.setVisibility(View.VISIBLE);
                holder.ratingbar.setVisibility(View.VISIBLE);
                holder.nameTextView.setText(item.getGoodsDetailInfo().getName());
                holder.ratingbar.setRating(item.getGoodsDetailInfo().getScore());
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) holder.descTextView.getLayoutParams();
                params.topMargin = 0;
                //                params.rightMargin = (20 * screenWidth) / 720;
                holder.descTextView.setLayoutParams(params);
                
            }
            
            holder.descTextView.setText(item.getGoodsDetailInfo().getDesc());
            
            //原价
            double unitPrice = item.getGoodsDetailInfo().getOriginalPrice();
            //优惠价
            double rebatePrice = item.getGoodsDetailInfo().getSalePrice();
            
            String str1 = String.format(mContext.getResources()
                    .getString(R.string.order_item_price),
                    String.valueOf(unitPrice));
            
            String str2 = String.format(mContext.getResources()
                    .getString(R.string.order_item_price_1),
                    String.valueOf(rebatePrice));
            
            SpannableString spannableString = new SpannableString(str1);
            //优惠价用删除线
            spannableString.setSpan(new StrikethroughSpan(),
                    0,
                    str1.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            holder.priceTextView.setText(spannableString);
            
            holder.priceTextView1.setText(str2);
            //支付状态0-未支付1-已支付
            int state = item.getState();
            
            //是否过期0-未过期1-已过期
            int isdate = item.getIsDated();
            
//            int status = item.getStatus();
            
            //是商圈
            if (type == 0)
            {
                if (state == 0)
                {
                    holder.timeTextView.setVisibility(View.VISIBLE);
                    holder.timeTextView.setText(R.string.order_item_type);
                    holder.haspaytTextView.setVisibility(View.GONE);
                }
                //已经付款
                else if (state == 1)
                {
                    holder.timeTextView.setVisibility(View.GONE);
                    holder.haspaytTextView.setVisibility(View.GONE);
                }
                
            }
            //是团购
            else
            {
                if (state == 0)
                {
                    if(isdate == 0)
                    {
                        holder.timeTextView.setVisibility(View.GONE);
                        holder.haspaytTextView.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.timeTextView.setVisibility(View.VISIBLE);
                        holder.haspaytTextView.setVisibility(View.GONE);
                        holder.timeTextView.setText(R.string.order_item_type_1);
                    }
                }
                else
                {
//                    if (isdate == 1)
//                    {
                        holder.timeTextView.setVisibility(View.GONE);
                        holder.haspaytTextView.setVisibility(View.VISIBLE);
//                    }
//                    else if (isdate == 0)
//                    {
//                        holder.timeTextView.setVisibility(View.VISIBLE);
//                        holder.haspaytTextView.setVisibility(View.GONE);
//                        holder.timeTextView.setText(R.string.order_item_type_1);
//                    }
                }
                
            }
            
            mBitmapUtils.display(holder.imageView,
                    item.getGoodsDetailInfo().getPicURL().get(0),
                    bigPicDisplayConfig,
                    null);
        }
        convertView.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Log.d(TAG, "onItemClick | position = " + position);
                Intent intent = new Intent(mContext,
                        OrderNewDatailActivity.class);
                intent.putExtra(StoreConstant.ORDER_TYPE,
                        StoreConstant.ORDER_SHOW);
                Bundle bundle = new Bundle();
                bundle.putSerializable(StoreConstant.ORDER_DETAIL, item);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        
        return convertView;
    }
    
    final class viewholder
    {
        private RelativeLayout subLayout;
        
        private ImageView imageView;
        
        private TextView nameTextView;
        
        private TextView descTextView;
        
        private TextView priceTextView;
        
        private TextView priceTextView1;
        
        private TextView timeTextView;
        
        private RatingBar ratingbar;
        
        private TextView haspaytTextView;
    }
    
}
