package com.changhong.smarthome.phone.property.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.HouseDescription;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.GetHouseDescriptionLogic;
import com.lidroid.xutils.HttpUtils;

/**
 * [房屋托管主界面]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class HomeEscrowMainActivity extends SuperActivity implements
        OnClickListener
{
    
    //    private String bz;
    
    //标题
    //    private TextView textView1;
    
    private ImageView house_lefttitle;
    
    private GridView house_gridView;
    
    private HttpUtils httpUtil;
    
    private GetHouseDescriptionLogic getHouseDescriptionLogic;
    
    private ArrayList<HouseDescription> houselist;
    
    private Context context;
    
    private HousedAdapter house_gridViewAdpater;
    
    private int screenWidth;
    
    @SuppressWarnings("unchecked")
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.HOUSED_SUCCESS_MSGWHAT:
                houselist = (ArrayList<HouseDescription>) msg.obj;
                house_gridViewAdpater.notifyDataSetChanged();
                //                initPage();
                break;
        }
        super.handleMsg(msg);
    }
    
    //    private void initPage()
    //    {
    //        HousedAdapter house_gridViewAdpater = new HousedAdapter();
    //        house_gridView.setAdapter(house_gridViewAdpater);
    //        house_gridView.setOnItemClickListener(new OnItemClickListener()
    //        {
    //            
    //            @Override
    //            public void onItemClick(AdapterView<?> parent, View view,
    //                    int position, long id)
    //            {
    //                Intent intent = new Intent();
    //                intent.putExtra("state", houselist.get(position).getState());
    //                intent.putExtra("id", houselist.get(position).getId());
    //                intent.setClass(context, HouseEscrowDetailActivity.class);
    //                startActivity(intent);
    //                
    //            }
    //            
    //        });
    //        
    //    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.housemain);
        context = this;
        //        Intent intent = this.getIntent();
        //        bz = intent.getStringExtra("bz");
        initViewPage();
        
    }
    
    //private int screenHeight;
    
    private void initViewPage()
    {
        
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        //        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        //        if (bz.equals(ProManageFragment.HOMEESCROW))
        {
            house_lefttitle = (ImageView) findViewById(R.id.house_lefttitle);
            //            textView1 = (TextView) findViewById(R.id.textView1);
            house_gridView = (GridView) findViewById(R.id.house_gridView);
            house_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            int width = (screenWidth - 56 - 56 - 60) / 3;
            
            house_gridView.setColumnWidth(width);
            
            house_lefttitle.setOnClickListener(this);
            httpUtil = new HttpUtils();
            getHouseDescriptionLogic.setData(mHandler);
            getHouseDescriptionLogic.getHouseDescriptionList(httpUtil);
        }
        
        house_gridViewAdpater = new HousedAdapter();
        house_gridView.setAdapter(house_gridViewAdpater);
        house_gridView.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent intent = new Intent();
                intent.putExtra("state", houselist.get(position).getState());
                intent.putExtra("id", houselist.get(position).getId());
                intent.setClass(context, HouseEscrowDetailActivity.class);
                startActivity(intent);
                
            }
            
        });
        
    }
    
    private class HousedAdapter extends BaseAdapter
    {
        private LayoutInflater m_inflater;
        
        private HousedAdapter()
        {
            m_inflater = LayoutInflater.from(context);
        }
        
        @Override
        public int getCount()
        {
            // TODO Auto-generated method stub
            if (houselist == null)
            {
                return 0;
            }
            return houselist.size();
        }
        
        @Override
        public Object getItem(int position)
        {
            // TODO Auto-generated method stub
            if (houselist == null)
            {
                return null;
            }
            return houselist.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            // TODO Auto-generated method stub
            if (houselist == null)
            {
                return -1;
            }
            return houselist.size();
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolder viewHolder;
            
            if (convertView == null)
            {
                viewHolder = new ViewHolder();
                convertView = m_inflater.inflate(R.layout.housed_item,
                        parent,
                        false);
                //                viewHolder.subLayout = (RelativeLayout) convertView.findViewById(R.id.house_item_layout);
                //                LayoutParams params = new LayoutParams(182, 194);
                //                viewHolder.subLayout.setLayoutParams(params);
                
                convertView.setLayoutParams(new GridView.LayoutParams(182, 196));
                
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.childimage);
                viewHolder.icon.setLayoutParams(new RelativeLayout.LayoutParams(
                        182, 153));
                viewHolder.desc = (TextView) convertView.findViewById(R.id.childtext);
                if (houselist.get(position).getState() == 1)
                {
                    viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.house_onrend));
                }
                else if (houselist.get(position).getState() == 2)
                {
                    viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.house_overrend));
                }
                else if (houselist.get(position).getState() == 3)
                {
                    viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.house_onsell));
                }
                else if (houselist.get(position).getState() == 4)
                {
                    viewHolder.icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.house_oversell));
                }
                if (null != houselist.get(position).getHouse_no())
                {
                    viewHolder.desc.setText(houselist.get(position)
                            .getHouse_no());
                }
            }
            
            return convertView;
        }
        
        final class ViewHolder
        {
            // item的布局
            //            public RelativeLayout subLayout;
            
            public TextView desc;
            
            public ImageView icon;
            
        }
        
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.house_lefttitle:
                finish();
                break;
            default:
                break;
        }
        
    }
    
    @Override
    public void initData()
    {
        getHouseDescriptionLogic = GetHouseDescriptionLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
}
