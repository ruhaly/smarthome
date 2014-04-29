package com.changhong.smarthome.phone.foundation.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.Constant;
import com.changhong.sdk.entity.Room;
import com.changhong.sdk.entity.User;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.foundation.logic.NewMemberLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 新成员界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class NewMemberActivity extends BaseActivity
{
    @ViewInject(R.id.listView)
    private ListView listView;
    
    private Adapter adapter;
    
    private NewMemberLogic logic;
    
    private RoomAdapter roomAdapter;
    
    private HttpUtils httpUtils;
    
    //忽略或者拒绝回调
    private CallBack cBack;
    
    private boolean changed = false;
    
    @Override
    public void initData()
    {
        logic = NewMemberLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.new_member_layout);
        ViewUtils.inject(this);
        initAdapter();
        requesMembers();
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    private void requesMembers()
    {
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestNewMembers(getUser().getUid(),
                LoginLogic.getInstance().curCommunity.getCommunityId(),
                httpUtils);
        
    }
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            listView.setAdapter(adapter);
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
            return logic.mm.userList.size();
        }
        
        @Override
        public User getItem(int position)
        {
            return logic.mm.userList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(final int position, View convertView,
                ViewGroup parent)
        {
            final ViewHolder viewHolder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.new_member_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.imgHead = (ImageView) convertView.findViewById(R.id.imgHead);
                viewHolder.btnIgnore = (Button) convertView.findViewById(R.id.btnIgnore);
                viewHolder.btnAccept = (Button) convertView.findViewById(R.id.btnAccept);
                viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
                viewHolder.tvState = (TextView) convertView.findViewById(R.id.tvState);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //状态0：待审批，1忽略，2：通过
            setStatus(viewHolder.btnIgnore,
                    viewHolder.btnAccept,
                    viewHolder.tvState,
                    getItem(position).getStatus());
            viewHolder.btnIgnore.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    ignor(new CallBack()
                    {
                        
                        @Override
                        public void update(Object object)
                        {
                            String type = (String) object;
                            String processType = "0";
                            if ("0".equals(type))
                            {
                                processType = "2";
                            }
                            else if ("1".equals(type))
                            {
                                processType = "1";
                            }
                            setStatus(viewHolder.btnIgnore,
                                    viewHolder.btnAccept,
                                    viewHolder.tvState,
                                    processType);
                        }
                    },
                            adapter.getItem(position),
                            Constant.PROCESSTYPE_REJECT);
                }
            });
            viewHolder.btnAccept.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    
                    showSelectRoomListDialog(new CallBack()
                    {
                        
                        @Override
                        public void update(Object object)
                        {
                            String type = (String) object;
                            String processType = "0";
                            if ("0".equals(type))
                            {
                                processType = "2";
                            }
                            else if ("1".equals(type))
                            {
                                processType = "1";
                            }
                            setStatus(viewHolder.btnIgnore,
                                    viewHolder.btnAccept,
                                    viewHolder.tvState,
                                    processType);
                        }
                    },
                            adapter.getItem(position),
                            Constant.PROCESSTYPE_ACCEPT);
                }
            });
            viewHolder.tvPhone.setText(getItem(position).getMobile());
            return convertView;
        }
        
        class ViewHolder
        {
            public ImageView imgHead;
            
            public Button btnIgnore;
            
            public Button btnAccept;
            
            public TextView tvPhone;
            
            public TextView tvState;
        }
    }
    
    public void setStatus(Button btnIgnore, Button btnAccept, TextView tvState,
            String status)
    {
        if ("0".equals(status))
        {
            btnIgnore.setVisibility(View.VISIBLE);
            btnAccept.setVisibility(View.VISIBLE);
            tvState.setVisibility(View.GONE);
        }
        else if ("1".equals(status))
        {
            btnIgnore.setVisibility(View.GONE);
            btnAccept.setVisibility(View.GONE);
            tvState.setVisibility(View.VISIBLE);
            tvState.setText(getString(R.string.has_ignore));
        }
        else
        {
            btnIgnore.setVisibility(View.GONE);
            btnAccept.setVisibility(View.GONE);
            tvState.setVisibility(View.VISIBLE);
            tvState.setText(getString(R.string.has_accept));
        }
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
    public String processType;
    
    /**
     * 
     * 选择房间
     * @param callBack
     * @param user
     * @param type 0同意1拒绝2清空
     */
    public void showSelectRoomListDialog(final CallBack callBack,
            final User user, String type)
    {
        processType = type;
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.room_select_layout, null);
        final Dialog dialog = getDialog(view, false, R.style.MyDialog);
        
        ListView lvRoom = (ListView) view.findViewById(R.id.lvRoom);
        roomAdapter = new RoomAdapter();
        lvRoom.setAdapter(roomAdapter);
        lvRoom.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                cBack = callBack;
                dialog.dismiss();
                requestAudit(user, roomAdapter.getItem(position), processType);
            }
        });
        dialog.show();
    }
    
    public void ignor(final CallBack callBack, final User user, String type)
    {
        processType = type;
        cBack = callBack;
        requestAudit(user, new Room(), processType);
    }
    
    /**
     * 
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * @param user 申请的用户
     * @param room 申请的房间
     * @param type  0同意1拒绝2清空
     */
    public void requestAudit(User user, Room room, String type)
    {
        
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestAuditMember(user.getId() + "",
                user.getUid(),
                LoginLogic.getInstance().curCommunity.getCommunityId(),
                room.getId(),
                getUser().getUid(),
                type,
                httpUtils);
        
    }
    
    class RoomAdapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return logic.mm.listRoom.size();
        }
        
        @Override
        public Room getItem(int position)
        {
            return logic.mm.listRoom.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolder viewHolder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.room_select_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.tvRoomNum = (TextView) convertView.findViewById(R.id.tvRoomNum);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvRoomNum.setText(getItem(position).getBuildingName());
            return convertView;
        }
        
        class ViewHolder
        {
            public TextView tvRoomNum;
        }
    }
    
    @OnClick(R.id.tvClear)
    public void tvClearClick(View view)
    {
        clearData();
        initAdapter();
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_NEW_MEMBER_SUCCESS:
            {
                initAdapter();
                break;
            }
            case MSGWHAT_AUDIT_MEMBER_SUCCESS:
            {
                cBack.update(processType);
                changed = true;
                break;
            }
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (changed)
        {
            setResult(100);
        }
        return super.onKeyDown(keyCode, event);
        
    }
    
    @OnClick(R.id.imgBack)
    public void imgBackClick(View view)
    {
        if (changed)
        {
            setResult(100);
        }
        finish();
    }
}
