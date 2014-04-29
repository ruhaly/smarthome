/**
 * MainFragment.java
 * com.pactera.ch_bedframe.fragment
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-3 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.changhong.foundation.R;
import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.sdk.widget.CustomViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:MainFragment Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午2:21:53
 */
public class AssistPageFragment extends SuperFragment
{
    
    @ViewInject(R.id.vp)
    private CustomViewPager vp;
    
    @ViewInject(R.id.frame_tab)
    private LinearLayout frame_tab;
    
    private Adapter adapter;
    
    View convertView;
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        setUserVisibleHint(false);
        super.onSaveInstanceState(outState);
        
    }
    
    public void initAdapter()
    {
        
        if (null == adapter)
        {
            adapter = new Adapter(getChildFragmentManager());
            vp.setAdapter(adapter);
            //            vp.setCanHorizontalScroll(true);
            
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
        
        vp.setOnTouchListener(new OnTouchListener()
        {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                
                return true;
                
            }
        });
    }
    
    public void changeTabBg()
    {
        for (int i = 0; i < frame_tab.getChildCount(); i++)
        {
            switch (i)
            {
                case 0:
                {
                    if (index == i)
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.bianmin_press);
                    }
                    else
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.bianmin_def);
                    }
                    break;
                }
                case 1:
                {
                    if (index == i)
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.zonghe_press);
                    }
                    else
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.zonghe_def);
                    }
                    break;
                }
                case 2:
                {
                    if (index == i)
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.anfang_press);
                    }
                    else
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.anfang_def);
                    }
                    break;
                }
                case 3:
                {
                    if (index == i)
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.other_press);
                    }
                    else
                    {
                        frame_tab.getChildAt(i)
                                .setBackgroundResource(R.drawable.other_def);
                    }
                    break;
                }
            }
        }
        
    }
    
    int index = 0;
    
    public void setVpItem(int i, boolean b)
    {
        index = i;
        vp.setCurrentItem(i, b);
        changeTabBg();
    }
    
    @OnClick(R.id.btn_huimin)
    public void btnAllClick(View view)
    {
        
        setVpItem(0, true);
    }
    
    @OnClick(R.id.btn_zonghe)
    public void btnHuiminlClick(View view)
    {
        setVpItem(1, true);
    }
    
    @OnClick(R.id.btn_anfang)
    public void btnZongheClick(View view)
    {
        setVpItem(2, true);
    }
    
    //    @OnClick(R.id.btn_kuapin)
    //    public void btnKuapinClick(View view)
    //    {
    //        setVpItem(3, true);
    //    }
    //    
    @OnClick(R.id.btn_other)
    public void btnKeshiClick(View view)
    {
        setVpItem(3, true);
    }
    
    public List<SuperFragment> frags = new ArrayList<SuperFragment>();
    
    class Adapter extends FragmentPagerAdapter
    {
        
        public Adapter(FragmentManager fm)
        {
            super(fm);
            SuperFragment pf = new PluginBianminFragment();
            SuperFragment pf1 = new PluginZongheFragment();
            SuperFragment pf2 = new PluginAnfangFragment();
            SuperFragment pf3 = new PluginOtherFragment();
            frags.add(pf);
            frags.add(pf1);
            frags.add(pf2);
            frags.add(pf3);
        }
        
        @Override
        public Fragment getItem(int arg0)
        {
            return frags.get(arg0);
            
        }
        
        @Override
        public int getCount()
        {
            return frags.size();
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
    
    @Override
    public void onClick(View v)
    {
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        
    }
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.assistpage_layout,
                container,
                false);
        ViewUtils.inject(this, convertView);
        initAdapter();
        changeTabBg();
        return convertView;
    }
    
    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
}
