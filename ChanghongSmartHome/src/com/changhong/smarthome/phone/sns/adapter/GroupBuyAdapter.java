package com.changhong.smarthome.phone.sns.adapter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.sdk.baseapi.DateUtil;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingListVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingMyVO;
import com.changhong.smarthome.phone.sns.view.TimerTextView;
import com.lidroid.xutils.BitmapUtils;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-3-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GroupBuyAdapter extends BaseAdapter
{
    private static final String TAG = "GroupBuyAdapter";
    
    private Context context;
    
    //    private List<GroupBuyBean> groupBuyBeans;
    private GroupBuyingMyVO groupBuyingMyVO;
    
    private GroupBuyingListVO groupBuyingListVO;
    
    private boolean isMy;//我的分享
    
    private BitmapUtils bitmapUtils;
    
    public GroupBuyAdapter(Context context,
            GroupBuyingListVO groupBuyingListVO, boolean isMy)
    {
        this.context = context;
        this.groupBuyingListVO = groupBuyingListVO;
        this.isMy = isMy;
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadingImage(R.drawable.group_buy_pic_default);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.group_buy_pic_default);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        
    }
    
    @Override
    public int getCount()
    {
        if (groupBuyingListVO != null && groupBuyingListVO.getData() != null
                && !groupBuyingListVO.getData().isEmpty())
        {
            return groupBuyingListVO.getData().size();
        }
        else
        {
            return 0;
        }
        
    }
    
    @Override
    public Object getItem(int position)
    {
        if (groupBuyingListVO != null && groupBuyingListVO.getData() != null
                && !groupBuyingListVO.getData().isEmpty())
        {
            return groupBuyingListVO.getData().get(position);
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        final ViewHolder viewHolder;
        if (null == convertView)
        {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.group_buy, null);
            viewHolder = new ViewHolder();
            viewHolder.statueHot = (ImageView) convertView.findViewById(R.id.group_buy_statue_hot);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.group_buy_pic);
            viewHolder.shopNum = (TextView) convertView.findViewById(R.id.group_buy_buy_num);
            viewHolder.shopTimeH = (TextView) convertView.findViewById(R.id.group_buy_surplus_time_h);
            viewHolder.shopTimeM = (TextView) convertView.findViewById(R.id.group_buy_surplus_time_m);
            viewHolder.shopTimeS = (TimerTextView) convertView.findViewById(R.id.timerTextView_group_buy_surplus_time_s);
            viewHolder.shopPriceLow = (TextView) convertView.findViewById(R.id.group_buy_price_low);
            viewHolder.shopPriceOriginal = (TextView) convertView.findViewById(R.id.group_buy_price_original);
            viewHolder.shopContent = (TextView) convertView.findViewById(R.id.group_buy_content);
            viewHolder.shopContent = (TextView) convertView.findViewById(R.id.group_buy_content);
            viewHolder.shopActivityTime = (TextView) convertView.findViewById(R.id.group_buy_activity_time);
            viewHolder.shopLaunchBusiness = (TextView) convertView.findViewById(R.id.group_buy_activity_launch_business);
            viewHolder.shopContactTel = (TextView) convertView.findViewById(R.id.group_buy_contact_tel);
            viewHolder.goShopBtn = (TextView) convertView.findViewById(R.id.group_buy_go_shop_btn);
            viewHolder.myBottomLL = (LinearLayout) convertView.findViewById(R.id.group_buy_num_ll_bottom);
            viewHolder.allBuyNumLL = (LinearLayout) convertView.findViewById(R.id.group_buy_num_ll);
            viewHolder.myNum = (TextView) convertView.findViewById(R.id.group_buy_buy_num_bottom);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //        final GroupBuyBean bean = groupBuyBeans.get(position);
        GroupBuyingListVO.GroupBuyingList vo = (GroupBuyingListVO.GroupBuyingList) groupBuyingListVO.getData()
                .get(position);
        
        if (isMy)
        {
            viewHolder.myBottomLL.setVisibility(View.VISIBLE);
            viewHolder.allBuyNumLL.setVisibility(View.GONE);
            viewHolder.goShopBtn.setVisibility(View.GONE);
        }
        else
        {
            if (vo.getAccountid().equals(UserUtils.getUser().getAccount()))
            {
                viewHolder.myBottomLL.setVisibility(View.VISIBLE);
                viewHolder.allBuyNumLL.setVisibility(View.GONE);
                viewHolder.goShopBtn.setVisibility(View.GONE);
            }
            else
            {
                viewHolder.myBottomLL.setVisibility(View.GONE);
                viewHolder.allBuyNumLL.setVisibility(View.VISIBLE);
                viewHolder.goShopBtn.setVisibility(View.VISIBLE);
            }
        }
        if (vo.getHot() == 1)
        {
            viewHolder.statueHot.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.statueHot.setVisibility(View.GONE);
        }
        viewHolder.goShopBtn.setBackgroundResource(R.drawable.go_shop_btn_selector);
        
        viewHolder.shopNum.setText(vo.getNum() + "");
        viewHolder.shopPriceLow.setText(context.getResources()
                .getString(R.string.order_unit_yuan_usa) + vo.getPrice());
        viewHolder.shopPriceOriginal.setText(context.getResources()
                .getString(R.string.order_unit_yuan_usa) + vo.getOriginalcost());
        viewHolder.shopContent.setText(vo.getDescripte());
        viewHolder.shopActivityTime.setText(vo.getStarttime());
        viewHolder.shopLaunchBusiness.setText(vo.getSeller());
        viewHolder.shopContactTel.setText(vo.getPhone());
        viewHolder.myNum.setText(vo.getNum() + "");
        
        try
        {
            long curTime = Calendar.getInstance().getTimeInMillis();
            long surTime = DateUtil.STANDARD_NO_S_DF.parse(vo.getEndtime())
                    .getTime();
            
            Log.d("AOAO", "curTime---<" + curTime);
            Log.d("AOAO", "surTime---<" + surTime);
            long dif = (surTime - curTime) / 1000;
            // 小时常数
            long hourMarker = 60 * 60;
            
            // 分钟常数
            long minuteMarker = 60;
            
            // 秒常数
            long secondMarker = 1;
            DecimalFormat decfmt = new DecimalFormat();
            long h = dif / hourMarker;
            long m = (dif - h * hourMarker) / minuteMarker;
            long s = (dif - h * hourMarker - m * minuteMarker) / secondMarker;
            if (h <= 0 && m <= 0 && s <= 0)
            {
                viewHolder.shopTimeH.setText("0");
                viewHolder.shopTimeM.setText("0");
                viewHolder.shopTimeS.setText("0");
                viewHolder.goShopBtn.setBackgroundResource(R.drawable.activity_over);
            }
            else
            {
                viewHolder.shopTimeH.setText(decfmt.format(h));
                viewHolder.shopTimeM.setText(decfmt.format(m));
                viewHolder.shopTimeS.setText(decfmt.format(s));
                startCounter(h,
                        m,
                        s,
                        viewHolder.goShopBtn,
                        viewHolder.shopTimeH,
                        viewHolder.shopTimeM,
                        viewHolder.shopTimeS);
            }
            
        }
        catch (ParseException e)
        {
            viewHolder.goShopBtn.setBackgroundResource(R.drawable.activity_over);
        }
        //        bitmapUtils.display(viewHolder.pic, bean.getPicUrl());
        bitmapUtils.display(viewHolder.pic, vo.getImg());
        return convertView;
    }
    
    private void startCounter(long h, long m, final long s, TextView goShopBtn,
            TextView tvH, TextView tvM, TimerTextView timerviweS)
    {
        timerviweS.setTimes(h, m, s, goShopBtn, tvH, tvM, timerviweS);
        if (!timerviweS.isRun())
        {
            timerviweS.run();
        }
        
    }
    
    class ViewHolder
    {
        private ImageView statueHot;
        
        private ImageView pic;
        
        private TextView shopNum;
        
        private TextView shopTimeH;
        
        private TextView shopTimeM;
        
        private TimerTextView shopTimeS;
        
        private TextView shopPriceLow;
        
        private TextView shopPriceOriginal;
        
        private TextView shopContent;
        
        private TextView shopActivityTime;
        
        private TextView shopLaunchBusiness;
        
        private TextView shopContactTel;
        
        private TextView goShopBtn;
        
        private LinearLayout myBottomLL;
        
        private LinearLayout allBuyNumLL;
        
        private TextView myNum;
    }
}