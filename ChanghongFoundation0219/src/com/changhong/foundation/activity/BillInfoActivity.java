/**
 * BillInfoActivity.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-5 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.fragment.HistoryBillFragment;
import com.changhong.foundation.fragment.RecentBillFragment;
import com.changhong.foundation.logic.HistoryBillLogic;
import com.changhong.sdk.widget.CustomViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:BillInfoActivity Function: TODO ADD FUNCTION.
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-5 下午2:14:56
 */
public class BillInfoActivity extends BaseActivity
{
    
    public static final String[] TITLE = new String[] { "近期账单", "历史账单" };
    
    private List<Fragment> list = new ArrayList<Fragment>();
    
    private Adapter adapter;
    
    @ViewInject(R.id.btn_recent)
    private TextView btnRecent;
    
    @ViewInject(R.id.btn_history)
    private TextView btnHistory;
    
    @ViewInject(R.id.vp)
    private CustomViewPager vp;
    
    @Override
    public void initData()
    {
        Fragment frg1 = new RecentBillFragment();
        Fragment frg2 = new HistoryBillFragment();
        list.add(frg1);
        list.add(frg2);
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.bill_info_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        initVp();
    }
    
    public void initVp()
    {
        if (null == adapter)
        {
            adapter = new Adapter(getSupportFragmentManager());
            // vp.setCanScroll(false);
            vp.setAdapter(adapter);
            vp.setOnTouchListener(new OnTouchListener()
            {
                
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    return true;
                }
            });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    class Adapter extends FragmentPagerAdapter
    {
        
        public Adapter(FragmentManager fm)
        {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int arg0)
        {
            return list.get(arg0);
        }
        
        @Override
        public int getCount()
        {
            return list.size();
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            
        }
        
        @Override
        public void destroyItem(View container, int position, Object object)
        {
            
        }
        
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
    }
    
    @Override
    public void finish()
    {
        HistoryBillLogic.getInstance().clear();
        super.finish();
    }
    
    @OnClick(R.id.btn_recent)
    public void btnRecentClick(View view)
    {
        changeVpItem(0);
    }
    
    @OnClick(R.id.btn_history)
    public void btnHistoryClick(View view)
    {
        changeVpItem(1);
    }
    
    public void changeVpItem(int index)
    {
        
        if (null != vp)
        {
            vp.setCurrentItem(index);
        }
        if (index == 0)
        {
            btnRecent.setBackgroundResource(R.drawable.menu_pressed_left_bg_press);
            btnHistory.setBackgroundResource(R.drawable.menu_pressed_left_bg_default);
        }
        else
        {
            btnHistory.setBackgroundResource(R.drawable.menu_pressed_left_bg_press);
            btnRecent.setBackgroundResource(R.drawable.menu_pressed_left_bg_default);
        }
    }
}
