/**
 * IntegralExchangeActivity.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-6 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.entity.Integral;
import com.changhong.foundation.logic.IntegralLogic;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.sdk.baseapi.CHUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:IntegralExchangeActivity Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-6 上午10:23:10
 */
public class IntegralExchangeActivity extends BaseActivity
{
    
    private IntegralLogic logic;
    
    @ViewInject(R.id.gridview)
    private GridView gridview;
    
    @ViewInject(R.id.tv_integral_description)
    private TextView tv_integral_description;
    
    private Adapter adapter;
    
    private int integral;
    
    private HttpUtils httpUtil;
    
    private BitmapUtils bitmapUtils;
    
    @ViewInject(R.id.tvTime)
    private TextView tvTime;
    
    @Override
    public void initData()
    {
        logic = IntegralLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.integral_exchange_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        CHUtils.addUnderlineTextView(tv_integral_description);
        integral = 500;//getIntent().getIntExtra("integral", 0);
        bitmapUtils = new BitmapUtils(getBaseContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.menu_head);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.menu_head);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initGridView();
        requestIntegral();
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    private void requestIntegral()
    {
        httpUtil = new HttpUtils();
        showProcessDialog(dismiss);
        logic.setData(mHandler);
        logic.requestIntegralList(LoginLogic.getInstance().baseAccountInfo,
                integral,
                httpUtil);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_INTEGRAL_LIST_SUCCESS:
            {
                tvTime.setText(String.format(getString(R.string.integral_active),
                        logic.startTime,
                        logic.endTime));
                initGridView();
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    public void initGridView()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            gridview.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
    }
    
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            
            return logic.list.size();
            
        }
        
        @Override
        public Integral getItem(int arg0)
        {
            return logic.list.get(arg0);
            
        }
        
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
            
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup arg2)
        {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.integral_item_layout, null);
                holder = new ViewHolder();
                holder.tv_pname = (TextView) convertView.findViewById(R.id.tv_pname);
                holder.tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);
                holder.img_p = (ImageView) convertView.findViewById(R.id.img_p);
                
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_pname.setText(getItem(position).getName());
            holder.tv_integral.setText(getString(R.string.needintegral) + " "
                    + getItem(position).getIntegral() + getString(R.string.fen));
            bitmapUtils.display(holder.img_p, getItem(position).getPic());
            return convertView;
        }
        
        /**
         * 
         * [一句话功能简述]<BR>
         * [功能详细描述]
         * @author b
         * @version [智慧社区-终端底座, 2014年1月17日]
         */
        class ViewHolder
        {
            ImageView img_p;
            
            TextView tv_pname;
            
            TextView tv_integral;
        }
    }
}
