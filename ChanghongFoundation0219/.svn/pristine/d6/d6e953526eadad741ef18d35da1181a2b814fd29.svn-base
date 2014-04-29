/**
 * PayPrivilegeActivity.java
 * com.pactera.ch.bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-17 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.entity.Privilege;
import com.changhong.foundation.logic.PrivilegeLogic;
import com.changhong.sdk.baseapi.CHUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:PayPrivilegeActivity Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-17 下午4:47:30
 */
public class PayPrivilegeActivity extends BaseActivity
{
    private PrivilegeLogic logic;
    
    @ViewInject(R.id.listview)
    private ListView listview;
    
    private Adapter adapter;
    
    public RadioButton lastRb;
    
    @Override
    public void initData()
    {
        logic = PrivilegeLogic.getInstance();
        
        for (int i = 0; i < 10; i++)
        {
            Privilege p = new Privilege();
            
            p.setName(i % 2 == 0 ? "电费" : "水费");
            Map<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < 3; j++)
            {
                map.put("" + j + 100, "" + j * 100 + 100);
            }
            p.setMap(map);
            logic.list.add(p);
        }
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.payprivilege_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        initAdapter();
    }
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            listview.setAdapter(adapter);
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
        public Privilege getItem(int arg0)
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
            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.payprivilege_item_layout, null);
                holder = new ViewHolder();
                holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
                holder.frame = (LinearLayout) convertView.findViewById(R.id.frame);
                holder.frameGroup = (LinearLayout) convertView.findViewById(R.id.frameGroup);
                holder.frameChild = (LinearLayout) convertView.findViewById(R.id.frameChild);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            Map<String, String> map = getItem(position).getMap();
            Set<String> keySet = map.keySet();
            holder.frame.removeAllViews();
            int n = 0;
            for (String key : keySet)
            {
                
                LinearLayout l = new LinearLayout(getBaseContext());
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                holder.frame.addView(l, lp);
                l.setOrientation(LinearLayout.HORIZONTAL);
                
                RadioButton rb = new RadioButton(getBaseContext());
                rb.setTextColor(getBaseContext().getResources()
                        .getColor(R.color.black));
                rb.setTag(key + " " + getString(R.string.yuan));
                rb.setButtonDrawable(R.drawable.radiobutton_bg);
                rb.setText(Html.fromHtml("<font color=\"#f80000\">" + key
                        + "</font>")
                        + " " + getString(R.string.yuan));
                if (n == 0)
                {
                    rb.setChecked(true);
                }
                rb.setOnCheckedChangeListener(new OnCheckedChangeListener()
                {
                    
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked)
                    {
                        if (isChecked)
                        {
                            fixRadioBox(holder.frame,
                                    (String) buttonView.getTag());
                        }
                        
                    }
                });
                lp.weight = 1;
                l.addView(rb, lp);
                
                TextView tv = new TextView(getBaseContext());
                tv.setTextColor(getBaseContext().getResources()
                        .getColor(R.color.black));
                tv.setText(Html.fromHtml("<font color=\"#f80000\">"
                        + map.get(key) + "</font>")
                        + " " + getString(R.string.fen));
                tv.setTextColor(getBaseContext().getResources()
                        .getColor(R.color.black));
                l.addView(tv, lp);
                n++;
            }
            holder.frameGroup.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    if (View.VISIBLE == holder.frameChild.getVisibility())
                    {
                        holder.frameChild.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.frameChild.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.tv_pay.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    showToast(getCheckedRadioBox(holder.frame));
                }
            });
            CHUtils.addUnderlineTextView(holder.tv_id);
            holder.tv_name.setText(getItem(position).getName());
            return convertView;
        }
        
        class ViewHolder
        {
            TextView tv_id;
            
            TextView tv_name;
            
            LinearLayout frame;
            
            TextView tv_pay;
            
            LinearLayout frameGroup;
            
            LinearLayout frameChild;
        }
    }
    
    public String getCheckedRadioBox(LinearLayout frame)
    {
        String str = "";
        for (int i = 0; i < frame.getChildCount(); i++)
        {
            LinearLayout tempL = (LinearLayout) frame.getChildAt(i);
            if (tempL instanceof LinearLayout)
            {
                if (tempL.getChildCount() > 1)
                {
                    if (tempL.getChildAt(0) instanceof RadioButton)
                    {
                        if (((RadioButton) (tempL.getChildAt(0))).isChecked())
                        {
                            str = (String) tempL.getChildAt(0).getTag();
                        }
                    }
                }
                
            }
        }
        return str;
        
    }
    
    public void fixRadioBox(LinearLayout frame, String tag)
    {
        for (int i = 0; i < frame.getChildCount(); i++)
        {
            LinearLayout tempL = (LinearLayout) frame.getChildAt(i);
            if (tempL instanceof LinearLayout)
            {
                if (tempL.getChildCount() > 1)
                {
                    if (tempL.getChildAt(0) instanceof RadioButton)
                    {
                        if (!tempL.getChildAt(0).getTag().equals(tag))
                        {
                            ((RadioButton) (tempL.getChildAt(0))).setChecked(false);
                        }
                    }
                }
                
            }
        }
    }
}
