package com.changhong.smarthome.phone.sns.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.widget.CustomGallery;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.sdk.widget.PullRefreshListView;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.adapter.BigPicAdapter;
import com.changhong.smarthome.phone.sns.adapter.FollowPeopleAdapter;
import com.changhong.smarthome.phone.sns.adapter.GroupBuyImageAdapter;
import com.changhong.smarthome.phone.sns.adapter.ImageAdapter;
import com.changhong.smarthome.phone.sns.bean.GroupBuyBean;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingOrderListVO;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.lidroid.xutils.HttpUtils;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong,团购详情界面
 * @version [版本号, 2014-3-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GroupBuyDetailActivity extends SnsSuperActivity
{
    private ImageView exit_button;//退出返回按钮
    
    private Button goShopBtn;
    
    private ScrollView allDetailContent;
    
    private LinearLayout myDetailContent;
    
    private TextView myTip;
    
    private PullRefreshListView followPeopleList;
    
    private EditText noticeContent;
    
    private Button noticeAllBtn;
    
    private Button sendNoticeMsgBtn;
    
    private TextView detailContent;
    
    private TextView activityTime;
    
    private TextView launchBusiness;
    
    private TextView contactTel;
    
    private ImageView mapIcon;
    
    /**
     *标记是其他人的团购单还是自己的
     */
    private boolean isMy = true;
    
    /**
     *标记是显示跟单人员列表还是发通知编辑框
     */
    private boolean isList = true;
    
    //    private List<FollowPeopleBean> followPeopleBeans;
    
    private FollowPeopleAdapter adapter;
    
    private LinearLayout pagerLayout;
    
    private ViewPager pagerImage;
    
    private ArrayList<ImageView> viewlist = new ArrayList<ImageView>();
    
    private Animation animationsmall_big;
    
    private Animation animationbig_small;
    
    private List<ImageView> imgViews = new ArrayList<ImageView>();
    
    private LinearLayout frame_gallery;
    
    private Gallery gallery;
    
    public LinearLayout frame_dian;
    
    private GroupBuyImageAdapter picAdapter;
    
    private BigPicAdapter pagerAdapter;
    
    //    private List<TSnsThemePic> pics = new ArrayList<TSnsThemePic>();
    private IntShareLogic logic;
    
    private HttpUtils httpUtils;
    
    private int id;
    
    private MCloudProgressDialog progressDialog;
    
    private GroupBuyBean groupBuyBean;
    
    private GroupBuyingOrderListVO groupBuyingOrderListVO;//
    
    /**
     * 查看大图，点击图片，图片缩小
     */
    private Handler handler = new Handler()
    {
        
        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what)
            {
                case Constant.MSG_REFRESH_VIEW:
                    showView();
                    pagerLayout.setVisibility(View.GONE);
                    pagerLayout.setAnimation(animationbig_small);
                    if (isList)
                    {
                        sendNoticeMsgBtn.setVisibility(View.VISIBLE);
                        noticeAllBtn.setVisibility(View.GONE);
                    }
                    else
                    {
                        noticeAllBtn.setVisibility(View.VISIBLE);
                        sendNoticeMsgBtn.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
        
    };
    
    @Override
    public void onCreate(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        super.onCreate(paramBundle);
        setContentView(R.layout.group_buy_detail);
        Intent intent = getIntent();
        if (null != intent)
        {
            isMy = intent.getBooleanExtra("isMy", false);
            id = intent.getIntExtra("id", 0);
        }
        else
        {
            return;
        }
        initView();
        showView();
        logic = IntShareLogic.getInstance();
        
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(
                    GroupBuyDetailActivity.this,
                    getResources().getString(R.string.loading));
        }
        progressDialog.show();
        getDeatil();
    }
    
    private void getDeatil()
    {
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        if (isMy)
        {
            logic.requestgroupBuyingOrderList(id);
        }
        else
        {
            logic.requestGroupBuyingdetail(id, httpUtils);
        }
        
    }
    
    private void initView()
    {
        goShopBtn = (Button) findViewById(R.id.detail_group_buy_go_shop_btn);
        allDetailContent = (ScrollView) findViewById(R.id.scroll_content);
        myDetailContent = (LinearLayout) findViewById(R.id.my_group_shop_ll);
        myTip = (TextView) findViewById(R.id.group_buy_follow_people);
        followPeopleList = (PullRefreshListView) findViewById(R.id.follow_people_list);
        noticeContent = (EditText) findViewById(R.id.notice_msg);
        noticeAllBtn = (Button) findViewById(R.id.detail_notice_all_btn);
        sendNoticeMsgBtn = (Button) findViewById(R.id.notice_send_btn);
        detailContent = (TextView) findViewById(R.id.detail_content);
        activityTime = (TextView) findViewById(R.id.group_buy_activity_time);
        launchBusiness = (TextView) findViewById(R.id.group_buy_activity_launch_business);
        contactTel = (TextView) findViewById(R.id.group_buy_contact_tel);
        mapIcon = (ImageView) findViewById(R.id.group_buy_map_icon);
        mapIcon.setOnClickListener(this);
        goShopBtn.setOnClickListener(this);
        noticeAllBtn.setOnClickListener(this);
        sendNoticeMsgBtn.setOnClickListener(this);
        exit_button = (ImageView) findViewById(R.id.exit_button);
        
        super.exitButtonClick(exit_button);
        //
        pagerLayout = (LinearLayout) findViewById(R.id.pagerLayout);
        pagerImage = (ViewPager) findViewById(R.id.pagerImage);
        //Gallery显示左右滑动的图片 
        frame_gallery = (LinearLayout) findViewById(R.id.frame_gallery);
        frame_gallery.setLayoutParams(new LinearLayout.LayoutParams(
                CHUtils.getScreenWidth(getBaseContext()), 500));
        frame_dian = (LinearLayout) findViewById(R.id.frame_dian);
        gallery = (CustomGallery) findViewById(R.id.gallery);
        //初始化点击查看大图的动画
        animationsmall_big = AnimationUtils.loadAnimation(this,
                R.anim.scalesamall_big);
        animationbig_small = AnimationUtils.loadAnimation(this,
                R.anim.scalesbig_small);
    }
    
    /**
     * 初始化可左右滑动的图片Adapter
     */
    private void initPicAdapter(String imgs[])
    {
        for (int i = 0; i < imgs.length; i++)
        {
            ImageView iv = new ImageView(getBaseContext());
            LayoutParams lp = (LayoutParams) iv.getLayoutParams();
            if (null == lp)
            {
                lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
            }
            iv.setImageResource(R.drawable.dot_grey2x);
            lp.rightMargin = 5;
            iv.setLayoutParams(lp);
            frame_dian.addView(iv);
            imgViews.add(iv);
            
        }
        
        if (null == picAdapter)
        {
            picAdapter = new GroupBuyImageAdapter(GroupBuyDetailActivity.this,
                    imgs);
            gallery.setAdapter(picAdapter);
            gallery.setSelection(0);
            gallery.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    if (null == pagerAdapter)
                    {
                        pagerAdapter = new BigPicAdapter(viewlist, handler);
                        pagerImage.setAdapter(pagerAdapter);
                    }
                    pagerImage.setCurrentItem(position);
                    allDetailContent.setVisibility(View.GONE);
                    myDetailContent.setVisibility(View.GONE);
                    goShopBtn.setVisibility(View.GONE);
                    noticeAllBtn.setVisibility(View.GONE);
                    sendNoticeMsgBtn.setVisibility(View.GONE);
                    pagerLayout.setVisibility(View.VISIBLE);
                    pagerLayout.startAnimation(animationsmall_big);
                }
            });
            gallery.setOnItemSelectedListener(new OnItemSelectedListener()
            {
                
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                        int position, long arg3)
                {
                    
                    for (int i = 0; i < imgViews.size(); i++)
                    {
                        if (i == position)
                        {
                            ((ImageView) frame_dian.getChildAt(i)).setImageResource(R.drawable.dot_yellow2x);
                        }
                        else
                        {
                            ((ImageView) frame_dian.getChildAt(i)).setImageResource(R.drawable.dot_grey2x);
                        }
                        
                    }
                    
                }
                
                @Override
                public void onNothingSelected(AdapterView<?> arg0)
                {
                    
                }
            });
        }
        else
        {
            picAdapter.notifyDataSetChanged();
        }
    }
    
    private void showView()
    {
        if (!isMy)
        {
            allDetailContent.setVisibility(View.VISIBLE);
            myDetailContent.setVisibility(View.GONE);
            goShopBtn.setVisibility(View.VISIBLE);
            noticeAllBtn.setVisibility(View.GONE);
            sendNoticeMsgBtn.setVisibility(View.GONE);
        }
        else
        {
            allDetailContent.setVisibility(View.GONE);
            myDetailContent.setVisibility(View.VISIBLE);
            followPeopleList.setVisibility(View.VISIBLE);
            noticeContent.setVisibility(View.GONE);
            myTip.setText(getResources().getString(R.string.group_buy_follow_people_text));
            goShopBtn.setVisibility(View.GONE);
            noticeAllBtn.setVisibility(View.VISIBLE);
            sendNoticeMsgBtn.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.detail_group_buy_go_shop_btn:
                Intent intent = new Intent();
                intent.setClass(GroupBuyDetailActivity.this,
                        ConfirmOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.detail_notice_all_btn:
                followPeopleList.setVisibility(View.GONE);
                noticeContent.setVisibility(View.VISIBLE);
                myTip.setText(getResources().getString(R.string.detail_nitice_msg));
                noticeAllBtn.setVisibility(View.GONE);
                sendNoticeMsgBtn.setVisibility(View.VISIBLE);
                isList = false;
                break;
            case R.id.notice_send_btn:
                //发送 
                logic.requestGroupBuyingMessage(id, noticeContent.getText()
                        .toString());
                break;
            case R.id.group_buy_map_icon:
                break;
            default:
                break;
        }
    }
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
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
    
    private void updateView()
    {
        if (null != groupBuyBean)
        {
            detailContent.setText(groupBuyBean.getContent());
            activityTime.setText(groupBuyBean.getActivityTime());
            launchBusiness.setText(groupBuyBean.getLaunchBusiness());
            contactTel.setText(groupBuyBean.getContactTel());
        }
        
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        // TODO Auto-generated method stub
        super.handleMsg(msg);
        if (null != progressDialog)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
        switch (msg.what)
        {
            case Constant.GET_GROUPBUYINGDETAIL_SUCCESS:
                groupBuyBean = (GroupBuyBean) msg.obj;
                updateView();
                break;
            case Constant.GET_GROUPBUYINGDETAIL_FAILED:
                Toast.makeText(GroupBuyDetailActivity.this,
                        getResources().getString(R.string.get_detail_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            
            case Constant.GET_GROUPBUYING_ORDER_LIST_SUCCESS:
                GroupBuyingOrderListVO vo = (GroupBuyingOrderListVO) msg.obj;
                List<GroupBuyingOrderListVO.GroupBuyingOrderList.Orders> list = vo.getData()
                        .getOrders();
                //                pics.clear();
                if (vo != null && vo.getData() != null
                        && vo.getData().getImg() != null
                        && vo.getData().getImg().length > 0)
                {
                    initPicAdapter(vo.getData().getImg());
                }
                //                Iterator it = list.iterator();
                //                FollowPeopleBean bean = new FollowPeopleBean();
                //                while (it.hasNext())
                //                {
                //                    GroupBuyingOrderListVO.GroupBuyingOrderList.Orders or = (GroupBuyingOrderListVO.GroupBuyingOrderList.Orders) it.next();
                //                    
                //                    bean.setName(or.getAccountId());
                //                    bean.setOrderNum(or.getNum() + "");
                //                    bean.setTel(or.getPhone());
                //                }
                //TODO get followPeopleList
                if (vo != null && vo.getData() != null
                        && vo.getData().getOrders() != null
                        && vo.getData().getOrders().size() > 0)
                {
                    adapter = new FollowPeopleAdapter(
                            GroupBuyDetailActivity.this, vo.getData()
                                    .getOrders());
                    followPeopleList.setAdapter(adapter);
                }
                
                //                adapter.notifyDataSetChanged();
                
                break;
            case Constant.GET_GROUPBUYING_MESSAGE_SUCCESS://发送消息成功
                AlertDialog.Builder builder = new Builder(this);
                builder.setMessage("发送成功");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        //send message'
                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                break;
            case Constant.GET_GROUPBUYING_MESSAGE_FAILED://send message failed
                AlertDialog.Builder builderFail = new Builder(this);
                builderFail.setMessage("发送失败");
                builderFail.setTitle("提示");
                builderFail.setPositiveButton("确认", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        //send message'
                        finish();
                    }
                });
                builderFail.setCancelable(false);
                builderFail.create().show();
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onBackPressed()
    {
        //        super.onBackPressed();
        // TODO Auto-generated method stub
        
        if (pagerLayout.getVisibility() == View.VISIBLE)
        {
            showView();
            pagerLayout.setVisibility(View.GONE);
            pagerLayout.setAnimation(animationbig_small);
            if (isList)
            {
                sendNoticeMsgBtn.setVisibility(View.VISIBLE);
                noticeAllBtn.setVisibility(View.GONE);
            }
            else
            {
                noticeAllBtn.setVisibility(View.VISIBLE);
                sendNoticeMsgBtn.setVisibility(View.GONE);
            }
            
        }
        else
        {
            if (!isList)
            {
                myTip.setText(getResources().getString(R.string.group_buy_follow_people_text));
                goShopBtn.setVisibility(View.GONE);
                noticeAllBtn.setVisibility(View.VISIBLE);
                sendNoticeMsgBtn.setVisibility(View.GONE);
                followPeopleList.setVisibility(View.VISIBLE);
                noticeContent.setVisibility(View.GONE);
                isList = true;
            }
            else
            {
                super.onBackPressed();
            }
        }
        
    }
}