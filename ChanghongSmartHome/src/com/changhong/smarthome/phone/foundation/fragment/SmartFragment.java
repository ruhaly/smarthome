package com.changhong.smarthome.phone.foundation.fragment;

import java.util.List;

import android.content.ComponentName;
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

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.MainActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.logic.PluginLogic;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SmartFragment extends SuperFragment
{
    
    private PluginLogic logic;
    
    private View convertView;
    
    private PluginAdapter padapter;
    
    @ViewInject(R.id.gvPlugin)
    private GridView gvPlugin;
    
    @Override
    public void onClick(View v)
    {
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        
    }
    
    @Override
    public void updateView(Intent intent)
    {
        
    }
    
    @Override
    public void initData()
    {
        logic = PluginLogic.getInstance();
        
        for (int i = 0; i < MainActivity.threeApkPkg.length; i++)
        {
            
            List<BusinessInfo> list = CHUtils.getApplicationInfo(getActivity().getBaseContext(),
                    MainActivity.threeApkPkg[i]);
            if (null != list && list.size() > 0)
            {
                logic.pList.addAll(list);
            }
        }
        
        for (BusinessInfo bi : logic.pList)
        {
            bi.setMainActivityPath(MainActivity.threeApkMainActivityMap.get(bi.getPackageName()));
        }
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.smart_layout, container, false);
        ViewUtils.inject(this, convertView);
        initPluginAdapter();
        return convertView;
    }
    
    /**
     * 
     * 插件适配器
     * [功能详细描述]
     * @author hanliangru
     * @version [智慧社区-终端底座, 2014年4月24日]
     */
    public class PluginAdapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return logic.pList.size();
        }
        
        @Override
        public BusinessInfo getItem(int position)
        {
            return logic.pList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolder holder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getActivity().getBaseContext())
                        .inflate(R.layout.promanage_plugin_item_layout, null);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(holder);
                
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img.setImageBitmap((((BitmapDrawable) getItem(position).getShorcut()).getBitmap()));
            holder.tvName.setText(getItem(position).getBusinessName());
            return convertView;
        }
        
        class ViewHolder
        {
            private ImageView img;
            
            private TextView tvName;
        }
    }
    
    public void initPluginAdapter()
    {
        if (null == padapter)
        {
            padapter = new PluginAdapter();
            gvPlugin.setAdapter(padapter);
            gvPlugin.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName(
                            padapter.getItem(position).getPackageName(),
                            padapter.getItem(position).getMainActivityPath());
                    intent.setComponent(cn);
                    startActivity(intent);
                }
            });
        }
        else
        {
            padapter.notifyDataSetChanged();
        }
    }
    
}
