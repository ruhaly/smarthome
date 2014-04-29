package com.changhong.smarthome.phone.foundation.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.Room;
import com.changhong.sdk.entity.User;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.foundation.logic.MemberManageLogic;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 成员管理界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class MemberManageActivity extends BaseActivity
{
    //房间适配器
    private ParentAdapter adapter;
    
    private boolean isEdit = false;
    
    private int curPosition = -1;
    
    private int parentPostion = -1;
    
    @ViewInject(R.id.imgHead)
    private ImageView imgHead;
    
    @ViewInject(R.id.listView)
    private ListView listView;
    
    //可以被授权的用户 适配器
    private UserAdapter uAdapter;
    
    @ViewInject(R.id.tvCommunity)
    private TextView tvCommunity;
    
    //小区列表
    private List<Community> list = new ArrayList<Community>();
    
    private HttpUtils httpUtils;
    
    private MemberManageLogic logic;
    
    @ViewInject(R.id.tvNewMember)
    private TextView tvNewMember;
    
    private BitmapUtils bitmaputils;
    
    @Override
    public void initData()
    {
        logic = MemberManageLogic.getInstance();
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.member_manage_layout);
        ViewUtils.inject(this);
        
        bitmaputils = new BitmapUtils(getBaseContext());
        bitmaputils.configDefaultLoadingImage(R.drawable.new_member);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.new_member);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initParentAdapter();
        requesMembers(true);
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    private void requesMembers(boolean hasDismiss)
    {
        if (hasDismiss)
            showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestMembers(getUser().getUid(),
                LoginLogic.getInstance().curCommunity.getCommunityId(),
                httpUtils);
        
    }
    
    public OnClickListener ok = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            if (-1 != curPosition)
            {
                requestDeleteMember(logic.memberManage.listRoom.get(parentPostion).userList.get(curPosition)
                        .getHrId(),
                        logic.memberManage.listRoom.get(parentPostion).userList.get(curPosition)
                                .getUid());
            }
        }
    };
    
    private void requestDeleteMember(String hrId, String deleteUid)
    {
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestDeleteMember(hrId, deleteUid, httpUtils);
        
    }
    
    public void initParentAdapter()
    {
        if (null == adapter)
        {
            adapter = new ParentAdapter();
            listView.setAdapter(adapter);
            
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (isEdit)
            {
                isEdit = !isEdit;
                initParentAdapter();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
    /**
     * 
     * 房间适配器
     * [功能详细描述]
     * @author hanliangru
     * @version [智慧社区-终端底座, 2014年4月18日]
     */
    class ParentAdapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return logic.memberManage.listRoom.size();
        }
        
        @Override
        public Room getItem(int position)
        {
            return logic.memberManage.listRoom.get(position);
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
            
            ViewHolder holder = null;
            
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.member_manage_item_layout,
                                parent,
                                false);
                holder = new ViewHolder();
                holder.tvRoom = (TextView) convertView.findViewById(R.id.tvRoom);
                holder.tvSq = (TextView) convertView.findViewById(R.id.tvSq);
                holder.gridView = (GridView) convertView.findViewById(R.id.gridView);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvRoom.setText(getItem(position).getBuildingName() + "-"
                    + getItem(position).getUnitName() + "-"
                    + getItem(position).getHouseName());
            CHUtils.addUnderlineTextView(holder.tvSq);
            //自己
            User user = new User();
            //其余用户
            List<User> userListTemp = new ArrayList<User>();
            //将自己跟其他用户分开来
            for (int n = 0; n < getItem(position).userList.size(); n++)
            {
                if (getItem(position).userList.get(n)
                        .getUid()
                        .equals(getUser().getUid()))
                {
                    user = getItem(position).userList.get(n);
                }
                else
                {
                    userListTemp.add(getItem(position).userList.get(n));
                }
            }
            //是否可以授权0：可以授权，1：已授权，2：不能授权
            if ("0".equals(user.getCanPermit()))
            {
                holder.tvSq.setText(getString(R.string.shouquangei));
            }
            else if ("1".equals(user.getCanPermit()))
            {
                holder.tvSq.setText(getString(R.string.cancel_shouquangei));
            }
            else
            {
                holder.tvSq.setText("");
            }
            
            //获取可以被授权的用户 1可以被授权
            final List<User> userListTempHasPer = new ArrayList<User>();
            for (int j = 0; j < userListTemp.size(); j++)
            {
                if ("1".equals(userListTemp.get(j).getHasPermission()))
                {
                    userListTempHasPer.add(userListTemp.get(j));
                }
            }
            holder.tvSq.setTag(user);
            holder.tvSq.setOnClickListener(new View.OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    User userTemp = (User) v.getTag();
                    showAuthorizationDialog(userListTempHasPer,
                            userTemp.getCanPermit(),
                            getItem(position).getId());
                }
            });
            
            //显示每个房间的用户
            final ChildAdapter adapter = new ChildAdapter(
                    getItem(position).userList, position);
            holder.gridView.setAdapter(adapter);
            holder.gridView.setTag(position);
            holder.gridView.setOnItemLongClickListener(new OnItemLongClickListener()
            {
                
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,
                        View view, int positionC, long id)
                {
                    parentPostion = position;
                    isEdit = !isEdit;
                    initParentAdapter();
                    return true;
                }
            });
            holder.gridView.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int positionC, long id)
                {
                    if (isEdit)
                    {
                        
                        if (position == parentPostion)
                        {
                            if (getUser().getUid()
                                    .equals(adapter.getItem(positionC)
                                            .getApproveBy()))
                            {
                                curPosition = positionC;
                                showAlertDialog(0,
                                        getString(R.string.tip),
                                        getString(R.string.confirm_delete),
                                        null,
                                        ok,
                                        DEFAULT_BTN,
                                        null,
                                        true,
                                        true);
                            }
                            else
                            {
                                isEdit = !isEdit;
                                initParentAdapter();
                            }
                        }
                        else
                        {
                            isEdit = !isEdit;
                            initParentAdapter();
                        }
                        
                    }
                    else
                    {
                        showToast(adapter.getItem(positionC).getNickName());
                    }
                    
                }
            });
            return convertView;
        }
        
        class ViewHolder
        {
            
            private TextView tvRoom;
            
            private TextView tvSq;
            
            private GridView gridView;
            
        }
    }
    
    //每个房间的成员列表适配器
    class ChildAdapter extends BaseAdapter
    {
        
        private List<User> userList;
        
        private int parentIndex;
        
        public ChildAdapter(List<User> userList, int parentPostion)
        {
            super();
            this.userList = userList;
            this.parentIndex = parentPostion;
        }
        
        @Override
        public int getCount()
        {
            return userList.size();
        }
        
        @Override
        public User getItem(int position)
        {
            return userList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            
            ChildViewHolder holder = null;
            
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.member_manage_child_item_layout,
                                parent,
                                false);
                holder = new ChildViewHolder();
                holder.imgAdmin = (ImageView) convertView.findViewById(R.id.imgAdmin);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
                holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
                holder.frameItem = (FrameLayout) convertView.findViewById(R.id.frameItem);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ChildViewHolder) convertView.getTag();
            }
            if (!StringUtils.isEmpty(getItem(position).getHeadUrl()))
            {
                bitmaputils.display(holder.imgIcon,
                        getItem(position).getHeadUrl());
            }
            String nickName = getItem(position).getNickName();
            holder.tvName.setText(StringUtils.isEmpty(nickName) ? getItem(position).getAccount()
                    : nickName);
            if (isEdit && parentIndex == parentPostion)
            {
                if (getUser().getUid().equals(getItem(position).getApproveBy()))
                {
                    holder.imgDelete.setVisibility(View.VISIBLE);
                }
                holder.frameItem.startAnimation(AnimationUtils.loadAnimation(getBaseContext(),
                        R.anim.item_rotate));
            }
            else
            {
                holder.imgDelete.setVisibility(View.GONE);
                holder.frameItem.clearAnimation();
            }
            if ("-1".equals(getItem(position).getApproveBy()))
            {
                holder.imgAdmin.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.imgAdmin.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
        
        class ChildViewHolder
        {
            private ImageView imgAdmin;
            
            private ImageView imgIcon;
            
            private TextView tvName;
            
            private ImageView imgDelete;
            
            private FrameLayout frameItem;
        }
    }
    
    @OnClick(R.id.tvMoreGroup)
    public void tvMoreGroupClick(View view)
    {
        startActivity(new Intent(getBaseContext(), MoreGroupActivity.class),
                false);
    }
    
    @OnClick(R.id.frameNewMember)
    public void frameNewMemberClick(View view)
    {
        startActivityForResult(new Intent(getBaseContext(),
                NewMemberActivity.class), 200);
    }
    
    /**
     * 
     * 
     * @param userList
     * @param type 1授权0取消授权
     */
    public void showAuthorizationDialog(List<User> userList, String type,
            final String houseId)
    {
        
        //是否可以授权0：可以授权，1：已授权，2：不能授权
        if ("0".equals(type))
        {
            View view = LayoutInflater.from(getBaseContext())
                    .inflate(R.layout.authorization_layout, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvTitle.setText(getString(R.string.shouquangei));
            final Dialog dialog = getDialog(view, false, R.style.MyDialog);
            
            GridView gvUser = (GridView) view.findViewById(R.id.gvUser);
            uAdapter = new UserAdapter(userList);
            gvUser.setAdapter(uAdapter);
            gvUser.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    dialog.dismiss();
                    requestPermitMember(uAdapter.getItem(position).getUid(),
                            houseId);
                }
            });
            dialog.show();
        }
        else if ("1".equals(type))
        {
            requestCancelPermitMember(houseId);
        }
        else
        {
        }
        
    }
    
    class UserAdapter extends BaseAdapter
    {
        
        private List<User> userList;
        
        public UserAdapter(List<User> userList)
        {
            super();
            this.userList = userList;
        }
        
        @Override
        public int getCount()
        {
            return userList.size();
        }
        
        @Override
        public User getItem(int position)
        {
            return userList.get(position);
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
                        .inflate(R.layout.authorization_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.imgHead = (ImageView) convertView.findViewById(R.id.imgHead);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (!StringUtils.isEmpty(getItem(position).getHeadUrl()))
            {
                bitmaputils.display(viewHolder.imgHead,
                        getItem(position).getHeadUrl());
            }
            String nickName = getItem(position).getNickName();
            viewHolder.tvName.setText(StringUtils.isEmpty(nickName) ? getItem(position).getAccount()
                    : nickName);
            return convertView;
        }
        
        class ViewHolder
        {
            public ImageView imgHead;
            
            public TextView tvName;
        }
    }
    
    @OnClick(R.id.btnApplyJoin)
    public void btnApplyJoinClick(View view)
    {
        startActivity(new Intent(getBaseContext(), ApplyJoinActivity.class));
    }
    
    @OnClick(R.id.frameCommunitySelect)
    public void frameCommunitySelectClick(View view)
    {
        
        showChangeCommunityDialog(list, new CallBack()
        {
            
            @Override
            public void update(Object object)
            {
                tvCommunity.setText(((Community) object).getName());
            }
        }, findViewById(R.id.imgXiala));
    }
    
    public void updateNewMemberCount(String count)
    {
        tvNewMember.setText(String.format(getString(R.string.member_count),
                count));
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_MEMBER_SUCCESS:
            {
                updateNewMemberCount(logic.memberManage.newCount);
                initParentAdapter();
                break;
            }
            case MSGWHAT_DELETE_MEMBER_SUCCESS:
            {
                logic.memberManage.listRoom.get(parentPostion).userList.remove(curPosition);
                initParentAdapter();
                break;
            }
            case MSGWHAT_PERMIT_MEMBER_SUCCESS:
            {
                showToast(getString(R.string.permit_success));
                requesMembers(false);
                return;
            }
            case CANCEL_MSGWHAT_PERMIT_MEMBER_SUCCESS:
            {
                showToast(getString(R.string.cancel_permit_success));
                requesMembers(false);
                return;
            }
            case 1:
            {
                requesMembers(true);
                break;
            }
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    /**
     * 
     * 授权
     * [功能详细描述]
     * @param userPermitTo
     * @param houseId
     */
    public void requestPermitMember(String userPermitTo, String houseId)
    {
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestPermitMember(getUser().getUid(),
                userPermitTo,
                houseId,
                httpUtils);
        
    }
    
    /**
     * 
     * 取消授权
     * [功能详细描述]
     * @param houseId
     */
    public void requestCancelPermitMember(String houseId)
    {
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestCancelPermitMember(getUser().getUid(), houseId, httpUtils);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //如果是从新成员管理界面返回的就重新请求数据刷新界面
        if (100 == resultCode)
        {
            requesMembers(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @OnClick(R.id.imgBack)
    public void imgBackClick(View view)
    {
        finish();
    }
}
