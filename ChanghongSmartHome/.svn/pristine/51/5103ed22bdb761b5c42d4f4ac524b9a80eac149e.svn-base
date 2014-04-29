package com.changhong.smarthome.phone.foundation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.changhong.smarthome.phone.foundation.logic.MoreGroupLogic;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 此界面废除
 * 更多关系圈
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class MoreGroupActivity extends BaseActivity
{
    @ViewInject(R.id.gridView)
    private GridView gridView;
    
    private MoreGroupLogic mgLogic;
    
    private Adapter adapter;
    
    @Override
    public void initData()
    {
        mgLogic = MoreGroupLogic.getInstance();
        Community c = new Community();
        c.setName("招商雍华府");
        c.setNewMemberNum("3");
        c.setMemberNum("2");
        
        Community c1 = new Community();
        c1.setName("锦绣小区");
        c1.setNewMemberNum("5");
        c1.setMemberNum("1");
        
        Community c2 = new Community();
        c2.setName("春江新城");
        c2.setNewMemberNum("4");
        c2.setMemberNum("3");
        mgLogic.communityList.add(c);
        mgLogic.communityList.add(c1);
        mgLogic.communityList.add(c2);
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.more_group_layout);
        ViewUtils.inject(this);
        initAdapter();
    }
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    startActivity(new Intent(getBaseContext(),
                            MemberManageActivity.class));
                }
            });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return mgLogic.communityList.size();
        }
        
        @Override
        public Community getItem(int position)
        {
            return mgLogic.communityList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.more_group_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
                viewHolder.tvNewMemberNum = (Button) convertView.findViewById(R.id.tvNewMemberNum);
                viewHolder.tvMemberNum = (TextView) convertView.findViewById(R.id.tvMemberNum);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                viewHolder.frameBg = (FrameLayout) convertView.findViewById(R.id.frameBg);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            
            int width = (getResources().getDrawable(R.drawable.more_group_item_tip_circle)).getIntrinsicWidth();
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.leftMargin = width / 2 - 2;
            params.topMargin = width / 2 - 2;
            params.rightMargin = width / 2 - 2;
            params.bottomMargin = width / 3 - 2;
            viewHolder.frameBg.setLayoutParams(params);
            viewHolder.tvNewMemberNum.setText(getItem(position).getNewMemberNum());
            viewHolder.tvMemberNum.setText(getItem(position).getMemberNum());
            viewHolder.tvName.setText(getItem(position).getName());
            
            return convertView;
        }
        
        class ViewHolder
        {
            public ImageView img;
            
            public Button tvNewMemberNum;
            
            public TextView tvMemberNum;
            
            public TextView tvName;
            
            public FrameLayout frameBg;
        }
    }
    
    @Override
    public void clearData()
    {
        mgLogic.clear();
    }
    
}
