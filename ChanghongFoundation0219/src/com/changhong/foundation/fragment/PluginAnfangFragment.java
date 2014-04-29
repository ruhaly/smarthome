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

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.baseapi.DbUtils;
import com.changhong.foundation.logic.PluginLogic;
import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.sdk.fragment.SuperFragment;
import com.lidroid.xutils.ViewUtils;

/**
 * ClassName:MainFragment Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午2:21:53
 */
public class PluginAnfangFragment extends SuperFragment
{
    
    private GridView gridView;
    
    public PluginLogic pLogic;
    
    public Adapter padapter;
    
    View convertView;
    
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return pLogic.afList.size();
        }
        
        @Override
        public BusinessInfo getItem(int position)
        {
            return pLogic.afList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getActivity().getBaseContext())
                        .inflate(R.layout.plugin_item_layout, null);
                holder = new ViewHolder();
                holder.img_plugin = (ImageView) convertView.findViewById(R.id.img_plugin);
                holder.tv_pluginname = (TextView) convertView.findViewById(R.id.tv_pluginname);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!StringUtils.isEmpty(getItem(position).getIcon()))
            {
                
            }
            else
            {
                BitmapDrawable b = (BitmapDrawable) ((SuperActivity) getActivity()).getPackageInfo(getItem(position).getPackageName())
                        .getShorcut();
                if (null != b)
                {
                    holder.img_plugin.setImageBitmap(b.getBitmap());
                }
            }
            holder.tv_pluginname.setText(getItem(position).getBusinessName());
            return convertView;
        }
        
        class ViewHolder
        {
            private ImageView img_plugin;
            
            private TextView tv_pluginname;
        }
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.plugin_keshi_layout,
                container,
                false);
        ViewUtils.inject(this, convertView);
        gridView = (GridView) convertView.findViewById(R.id.gridview);
        initAdapter();
        return convertView;
    }
    
    public void initAdapter()
    {
        if (null == padapter)
        {
            padapter = new Adapter();
            gridView.setAdapter(padapter);
            gridView.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    startActivity(padapter.getItem(position).getIntent());
                }
            });
        }
        else
        {
            padapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void initData()
    {
        pLogic = PluginLogic.getInstance();
        // 加载插件
        pLogic.setAfList(DbUtils.queryBusinessInfoList(getActivity(),
                null,
                null,
                Constant.PLUGIN_ANFANG));
        
    }
    
    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
    
}
