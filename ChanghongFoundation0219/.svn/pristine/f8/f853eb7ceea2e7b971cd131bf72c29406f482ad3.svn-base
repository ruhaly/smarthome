package com.changhong.foundation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.entity.User;
import com.changhong.foundation.logic.MemberLogic;
import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.ViewHolder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 成员列表界面
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年1月27日]
 */
public class MemberActivity extends SuperActivity
{
    
    @ViewInject(R.id.listView)
    private ListView listView;
    
    private MemberLogic logic;
    
    private Adapter adapter;
    
    @Override
    public void initData()
    {
        logic = MemberLogic.getInstance();
        for (int i = 0; i < 5; i++)
        {
            User u = new User();
            u.setUid("" + i);
            u.setNickName("name" + i);
            u.setReallyName("你好");
            u.setAddress("我家住在黄土高坡");
            u.setBirth("1989-11-12");
            u.setSex("男");
            u.setBoundPhone("15843358843");
            logic.uList.add(u);
        }
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.member_layout);
        ViewUtils.inject(this);
        initAdapter();
    }
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    
                    Bundle b = new Bundle();
                    b.putParcelable("parkey", adapter.getItem(position));
                    Intent intent = new Intent(getBaseContext(),
                            MemberDetailActivity.class);
                    intent.putExtra("member", b);
                    startActivity(intent);
                    
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
            return logic.uList.size();
        }
        
        @Override
        public User getItem(int arg0)
        {
            return logic.uList.get(arg0);
        }
        
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup arg2)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.member_item_layout, null);
            }
            
            TextView tvName = ViewHolder.get(convertView, R.id.tvName);
            tvName.setText(getItem(position).getNickName());
            return convertView;
        }
    }
    
}
