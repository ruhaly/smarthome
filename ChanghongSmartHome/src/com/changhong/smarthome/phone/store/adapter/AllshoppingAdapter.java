package com.changhong.smarthome.phone.store.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.activity.ShoppingDetailActivity;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.bean.GoodsDetailInfo;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

public class AllshoppingAdapter extends BaseAdapter
{
    private final static String TAG = "AllshoppingAdapter";
    
    private Context context;
    
    private List<GoodsDetailInfo> mList;
    
    private BitmapUtils mBitmapUtils ;
    
    private BitmapDisplayConfig bigPicDisplayConfig ;
    
    private int screenHeight;
    
    private int screenWidth;
    
    public AllshoppingAdapter(Context context, BitmapUtils bitmapUtils,List<GoodsDetailInfo> list)
    {
        // TODO Auto-generated constructor stub
        this.context = context;
        mBitmapUtils = bitmapUtils;
        mList = list;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        bigPicDisplayConfig = new BitmapDisplayConfig();
        //bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用, 图片太大时容易OOM。
        bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
        BitmapSize bitmapSize = new BitmapSize((screenWidth * 220) / 720,(screenWidth * 220) / 720);
        bigPicDisplayConfig.setBitmapMaxSize(bitmapSize);

    }
    
    public void setList(List<GoodsDetailInfo> list)
    {
        mList = list;
    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        if(mList != null)
        {
            return mList.size();
        }
        return 0;
    }
    
    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        if(mList != null)
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
        // TODO Auto-generated method stub
        viewholder holder = null;
        
        if (convertView == null)
        {
            holder = new viewholder();
            convertView = View.inflate(context,
                    R.layout.allshoppingadapter,
                    null);
            int height = (screenHeight * 260) / 1280;
            
            holder.subLayout = (RelativeLayout) convertView.findViewById(R.id.allshoping_item_layout);
            android.widget.AbsListView.LayoutParams layoutParams = new android.widget.AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, height);

            holder.subLayout.setLayoutParams(layoutParams);
           
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_id);
            RelativeLayout.LayoutParams params1 = (android.widget.RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            params1.height =(screenWidth * 220) / 720;
            params1.width = (screenWidth * 220) / 720;
            params1.leftMargin = (screenWidth * 22) / 720;
            holder.imageView.setLayoutParams(params1);
            
            holder.textView = (TextView) convertView.findViewById(R.id.shopping_name);
            RelativeLayout.LayoutParams params2 = (android.widget.RelativeLayout.LayoutParams) holder.textView.getLayoutParams();
            params2.height = LayoutParams.WRAP_CONTENT;
            params2.width = LayoutParams.WRAP_CONTENT;//(screenWidth * 240) / 720;
            params2.leftMargin = (screenWidth * 36) / 720;
            holder.textView.setLayoutParams(params2);
            
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 36) / 720);
            
            holder.desc = (TextView)convertView.findViewById(R.id.shopping_desc);
            RelativeLayout.LayoutParams params6 = (android.widget.RelativeLayout.LayoutParams) holder.desc.getLayoutParams();
            params6.leftMargin = (screenWidth * 42) / 720;
            holder.desc.setLayoutParams(params6);
            holder.desc.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 28) / 720);
            //原价
            holder.price = (TextView)convertView.findViewById(R.id.shopping_price);
            RelativeLayout.LayoutParams params4 = (android.widget.RelativeLayout.LayoutParams) holder.price.getLayoutParams();
            params4.bottomMargin = (screenHeight * 20)/1280;
            holder.price.setLayoutParams(params4);
            holder.price.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 48) / 720);
            //优惠价
            holder.price1 = (TextView)convertView.findViewById(R.id.shopping_price_1);
            RelativeLayout.LayoutParams params5 = (android.widget.RelativeLayout.LayoutParams) holder.price1.getLayoutParams();
            params5.bottomMargin = (screenHeight * 20)/1280;
            holder.price1.setLayoutParams(params5);
            holder.price1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 48) / 720);
            
            holder.ratingbar = (RatingBar)convertView.findViewById(R.id.shopping_star);
            holder.ratingbar.setOnTouchListener(new OnTouchListener() {
                

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    // TODO Auto-generated method stub
                    return true;
                }
                });
            LayoutParams params3 = holder.ratingbar.getLayoutParams();
            params3.height = LayoutParams.WRAP_CONTENT;//(screenHeight * 36) / 1280;
            params3.width = LayoutParams.WRAP_CONTENT;//(screenWidth * 162) / 720;
            holder.ratingbar.setLayoutParams(params3);
            
            convertView.setTag(holder);
            
        }
        else
        {
            holder = (viewholder) convertView.getTag();
        }
        
        final GoodsDetailInfo item = mList.get(position);
        if(null != item)
        {
            holder.ratingbar.setRating(item.getScore());
            //原价
            double unitPrice = item.getOriginalPrice();
            //优惠价
            double rebatePrice = item.getSalePrice();
            
            String str1 = String.format(context.getResources().getString(R.string.shopping_detail_price_1),
                    String.valueOf(unitPrice));
            
            String str2 = String.format(context.getResources().getString(R.string.shopping_detail_price),
                    String.valueOf(rebatePrice));
            
            
            SpannableString  spannableString = new SpannableString(str1);
            //优惠价用删除线
            spannableString.setSpan(new StrikethroughSpan(), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
            
            holder.price.setText(spannableString);
            
            holder.price1.setText(str2);
            
            holder.textView.setText(item.getName());
            holder.desc.setText(item.getDesc());
            
            String picUrl = null;
            if(item.getPicURL() != null && item.getPicURL().size() > 0)
            {
                picUrl = item.getPicURL().get(0);
            }
            
            mBitmapUtils.display(holder.imageView, picUrl,bigPicDisplayConfig,null);
        }
        
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
              Log.d(TAG, "onItemClick | position = " + position);
              Intent intent = new Intent(context, ShoppingDetailActivity.class);
              Bundle bundle = new Bundle();
              bundle.putSerializable(StoreConstant.GOODSDETAIL, item);
              intent.putExtras(bundle);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);
            }
        });
        return convertView;
    }
    
    
    final class viewholder
    {
        private RelativeLayout subLayout;
        
        private ImageView imageView;
        
        private TextView textView;
        
        private TextView desc,price,price1;
        
        private RatingBar ratingbar;
    }
}