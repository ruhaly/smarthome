package com.changhong.smarthome.phone.foundation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.AppLog;
import com.changhong.sdk.entity.Pager;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.changhong.smarthome.phone.foundation.logic.CommunityLogic;
import com.changhong.smarthome.phone.foundation.logic.DeleteCommunityLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * 
 * 小区管理界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class CommunityManageActivity extends BaseActivity
{
    
    private ImageView backCommunity;
    
    private GridView communityGridView;
    
    private MyAdapter adapter;
    
    private DeleteCommunityLogic deleteLogic;
    
    private HttpUtils deleteHttpUtil;
    
    private CommunityLogic getCommunityLogic;
    
    private HttpUtils getCommuntityHttpUtil;
    
    private BitmapUtils bitmaputils;
    
    private int position = -2;
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.community_management);
        backCommunity = (ImageView) findViewById(R.id.community_back_list);
        communityGridView = (GridView) findViewById(R.id.community_gridview);
        
        backCommunity.setOnClickListener(this);
        bitmaputils = new BitmapUtils(getBaseContext());
        
        bitmaputils.configDefaultLoadingImage(R.drawable.new_member);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.new_member);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initAdapter();
        getCommuntityDate();
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getCommunityLogic.stopRequest();
        }
    };
    
    /**
     *
     *  初始化adapter<BR>
     * [功能详细描述]
     */
    public void initAdapter()
    {
        if (null == adapter)
        {
            //添加小区 图标
            Community c = new Community();
            c.setCommunityId("-1");
            getCommunityLogic.list.add(c);
            
            adapter = new MyAdapter();
            communityGridView.setAdapter(adapter);
            communityGridView.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3)
                {
                    //如果点击的是添加图标则跳转到城市选择界面
                    if ("-1".equals(adapter.getItem(arg2).getCommunityId()))
                    {
                        Intent intent = new Intent();
                        //typeSrc 参数表示是从小区管理界面跳过去的
                        intent.putExtra("typeSrc", 1);
                        intent.setClass(CommunityManageActivity.this,
                                CitySelectActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
            });
            communityGridView.setOnItemLongClickListener(new OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                        final int arg2, long arg3)
                {
                    //如果是添加item则返回
                    if ("-1".equals(adapter.getItem(arg2).getCommunityId()))
                    {
                        return false;
                    }
                    else
                    {
                        //否则显示解除绑定框
                        showTipDialog(adapter.getItem(arg2).getName(),
                                new CallBack()
                                {
                                    @Override
                                    public void update(Object object)
                                    {
                                        deleteCommunityData(getCommunityLogic.list.get(arg2)
                                                .getId());
                                        position = arg2;
                                    }
                                },
                                false,
                                "解除绑定");
                        return true;
                    }
                }
            });
            
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 
     * 请求及响应的消息处理<BR>
     * [功能详细描述]
     * @param msg
     * @see com.changhong.sdk.activity.SuperActivity#handleMsg(android.os.Message)
     */
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_GET_COMMUNITY_SUCCESS:
            {
                AppLog.out("----get------", "success", AppLog.LEVEL_DEBUG);
                initAdapter();
                break;
            }
            case MSGWHAT_DELETE_COMMUNITY_SUCCESS:
            {
                AppLog.out("----delete---", "success", AppLog.LEVEL_DEBUG);
                getCommunityLogic.list.remove(position);
                initAdapter();
                break;
            }
            default:
            {
                break;
            }
        }
        super.handleMsg(msg);
    };
    
    /**
     * 
     * 从服务端接收该用户已经添加的小区数据<BR>
     * [功能详细描述]
     */
    public void getCommuntityDate()
    {
        showProcessDialog(dismiss);
        getCommuntityHttpUtil = new HttpUtils();
        getCommunityLogic.setData(mHandler);
        Boolean isRefresh = true;
        int currentPage = 0;
        Pager pager = new Pager();
        pager.setPageId(isRefresh ? 1 : currentPage + 1);
        pager.setPageSize(20);
        String userId = getUser().getUid();
        getCommunityLogic.requestCommunity(userId, pager, getCommuntityHttpUtil);
    }
    
    /**
     * 
     * 从服务端删除数据<BR>
     * [功能详细描述]
     * @param id
     */
    public void deleteCommunityData(String id)
    {
        deleteLogic = DeleteCommunityLogic.getInstance();
        deleteHttpUtil = new HttpUtils();
        deleteLogic.setData(mHandler);
        deleteLogic.requestDeleteCommunity(id, deleteHttpUtil);
    }
    
    /**
     * 
     * 按钮 的onclick事件
     * [功能详细描述]
     * @param v
     * @see com.changhong.sdk.activity.SuperActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.community_back_list:
            {
                finish();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    
    /**
     * 取得选择小区页面返回的值
     * @param requestCode
     * @param resultCode
     * @param data
     * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null)
        {
            return;
        }
        else
        {
            getCommunityLogic.list.clear();
            Community c = new Community();
            c.setCommunityId("-1");
            getCommunityLogic.list.add(c);
            getCommuntityDate();
        }
    };
    
    @Override
    public void initData()
    {
        getCommunityLogic = CommunityLogic.getInstance();
    }
    
    @Override
    public void clearData()
    {
        getCommunityLogic.clear();
    }
    
    /**
     * 自定义适配器
     * @version [智慧社区-终端底座, 2014年4月1日]
     */
    class MyAdapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return getCommunityLogic.list.size();
        }
        
        @Override
        public Community getItem(int position)
        {
            return getCommunityLogic.list.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.community_add_layout, null);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.add_img);
                viewHolder.testName = (TextView) convertView.findViewById(R.id.add_text);
                viewHolder.imgDefault = (ImageView) convertView.findViewById(R.id.imgDefault);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            
            if ("-1".equals(getItem(position).getCommunityId()))
            {
                viewHolder.image.setImageResource(R.drawable.community_add);
                viewHolder.imgDefault.setVisibility(View.GONE);
            }
            else
            {
                AppLog.out("-----community--add",
                        LoginLogic.getInstance().curCommunity.getCommunityId(),
                        AppLog.LEVEL_DEBUG);
                if (getItem(position).getCommunityId()
                        .equals(LoginLogic.getInstance().curCommunity.getCommunityId()))
                {
                    LoginLogic.getInstance().communityList.get(position)
                            .setSelect(true);
                    viewHolder.imgDefault.setVisibility(View.VISIBLE);
                }
                else
                {
                    viewHolder.imgDefault.setVisibility(View.GONE);
                }
                viewHolder.image.setImageResource(R.drawable.community_default_picture);
            }
            viewHolder.testName.setText(getItem(position).getName());
            
            return convertView;
            
        }
        
        class ViewHolder
        {
            public ImageView image;
            
            public TextView testName;
            
            public ImageView imgDefault;
            
        }
    }
}
