package com.changhong.smarthome.phone.sns.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.widget.CustomGallery;
import com.changhong.sdk.widget.InnerScrollViewPullRefreshListView;
import com.changhong.sdk.widget.InnerScrollViewPullRefreshListView.NewScrollerListener;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.adapter.ImageAdapter;
import com.changhong.smarthome.phone.sns.adapter.ShareInfoAdapter;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.changhong.smarthome.phone.sns.bean.TSnsReply;
import com.changhong.smarthome.phone.sns.bean.TSnsThemePic;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * <功能详细描述>
 * 二手信息/活动的分享详情界面
 * @author wanghonghong
 * @version [版本号, 2014-2-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ShareBusinessActivity extends SnsSuperActivity implements
        OnClickListener, OnItemLongClickListener
{
    
    private static final String TAG = "ShareBusinessActivity";
    
    private ImageView exit_button;//退出返回按钮
    
    /**
     * 图片下载工具
     */
    private BitmapUtils bitmapUtilsPic;
    
    private String themeId;
    
    private String codeId;
    
    private String replyCount;
    
    private String postType;
    
    private ShareBean shareBean = new ShareBean();
    
    private HttpUtils httpUtil;
    
    private IntShareLogic logic;
    
    private MCloudProgressDialog progressDialog;
    
    /**
     * 一页请求的个数
     */
    private int pageSize = 20;
    
    /**
     * 当前请求的是第几页(真正数据从1开始)
     */
    private int curPage = 1;
    
    private TextView editBtn;
    
    private TextView confirmSwap;
    
    private TextView shareTitle;
    
    private TextView shareDescription;
    
    private TextView shareContact;
    
    private TextView commentLL;
    
    private InnerScrollViewPullRefreshListView list;
    
    private LinearLayout frame_gallery;
    
    private Gallery gallery;
    
    public LinearLayout frame_dian;
    
    private LinearLayout listLl;
    
    private ScrollView scrollContent;
    
    private RelativeLayout bottomAllLayout;
    
    private RelativeLayout swapLayout;
    
    /**
     * 标记加载更多
     */
    private boolean isMore = false;
    
    /**
     * 标记是否有加载更多请求
     */
    private boolean isLoadingMore = false;
    
    /**
     * 标记是否有更多
     */
    private boolean hasMore = true;
    
    /**
     * 回复的总个数
     */
    private int replyTotalNum;
    
    private List<TSnsReply> replies = new ArrayList<TSnsReply>();
    
    private ShareInfoAdapter adapter;
    
    private ImageAdapter picAdapter;
    
    /**
     * 标记是否是自己的动态或者评论
     */
    private boolean isMe = false;
    
    /**
     * 删除的是第几个
     */
    private int pos;
    
    private List<TSnsThemePic> pics = new ArrayList<TSnsThemePic>();
    
    private TextView titleTextView;
    
    private LinearLayout pagerLayout;
    
    private ViewPager pagerImage;
    
    private ArrayList<ImageView> viewlist = new ArrayList<ImageView>();
    
    private Animation animationsmall_big;
    
    private Animation animationbig_small;
    
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    
    /**
     * lisView滑动加载监听
     * @return
     */
    public NewScrollerListener createScroller()
    {
        NewScrollerListener scroller = new NewScrollerListener()
        {
            private boolean isLoadMoreFile = false;
            
            @Override
            public void newScrollChanged(AbsListView view, int scrollState)
            {
                if ((scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING)
                        && isLoadMoreFile)
                {
                    
                    startToLoadMore();
                }
            }
            
            @Override
            public void newScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount)
            {
                isLoadMoreFile = (firstVisibleItem + visibleItemCount == totalItemCount) ? true
                        : false;
            }
        };
        return scroller;
    }
    
    @Override
    public void onCreate(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        super.onCreate(paramBundle);
        setContentView(R.layout.second_business);
        logic = IntShareLogic.getInstance();
        bitmapUtilsPic = new BitmapUtils(ShareBusinessActivity.this);
        bitmapUtilsPic.configDefaultLoadingImage(R.drawable.picture);
        bitmapUtilsPic.configDefaultLoadFailedImage(R.drawable.picture);
        bitmapUtilsPic.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initView();
        Intent intent = getIntent();
        if (null != intent)
        {
            
            themeId = intent.getStringExtra("themeId");
            codeId = intent.getStringExtra("codeId");
            replyCount = intent.getStringExtra("replyCount");
            postType = intent.getStringExtra("postType");
            shareBean.setId(themeId);
            if (intent.getStringExtra("userId").equals(UserUtils.getUser()
                    .getUid()))
            {
                isMe = true;
            }
            else
            {
                isMe = false;
            }
        }
        if (isMe)
        {
            confirmSwap.setVisibility(View.VISIBLE);
            commentLL.setVisibility(View.GONE);
        }
        else
        {
            confirmSwap.setVisibility(View.GONE);
            commentLL.setVisibility(View.VISIBLE);
        }
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(
                    ShareBusinessActivity.this,
                    getResources().getString(R.string.loading));
        }
        progressDialog.show();
        //        curPage = 1;
        //        getDetail(curPage);
        Constant.isNeedToRefreshReplyList = true;
        replyCount = String.valueOf(Integer.parseInt(replyCount) - 1);
    }
    
    private void getDetail(int curPager)
    {
        httpUtil = new HttpUtils();
        logic.setData(mHandler);
        logic.requestShareDetails(themeId,
                codeId,
                postType,
                curPage,
                pageSize,
                replyCount,
                httpUtil);
    }
    
    private void initView()
    {
        
        shareTitle = (TextView) findViewById(R.id.share_title);
        
        shareDescription = (TextView) findViewById(R.id.description_content);
        shareContact = (TextView) findViewById(R.id.contact_content);
        editBtn = (TextView) findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(this);
        confirmSwap = (TextView) findViewById(R.id.comfirm_swap);
        confirmSwap.setOnClickListener(this);
        listLl = (LinearLayout) findViewById(R.id.list_ll);
        scrollContent = (ScrollView) findViewById(R.id.scroll_content);
        bottomAllLayout = (RelativeLayout) findViewById(R.id.bottom_all);
        swapLayout = (RelativeLayout) findViewById(R.id.swap_suceess_rl);
        titleTextView = (TextView) findViewById(R.id.comment_title);
        list = (InnerScrollViewPullRefreshListView) findViewById(R.id.share_list);
        pagerLayout = (LinearLayout) findViewById(R.id.pagerLayout);
        pagerImage = (ViewPager) findViewById(R.id.pagerImage);
        list.setOnItemLongClickListener(this);
        //可在ScrollView里滑动的ListView需要设置的代码
        list.setParentScrollView(scrollContent);
        list.setMaxHeight(400);
        list.setNewScrollerListener(createScroller());
        //Gallery显示左右滑动的图片
        frame_gallery = (LinearLayout) findViewById(R.id.frame_gallery);
        frame_gallery.setLayoutParams(new LinearLayout.LayoutParams(
                CHUtils.getScreenWidth(getBaseContext()), 500));
        frame_dian = (LinearLayout) findViewById(R.id.frame_dian);
        gallery = (CustomGallery) findViewById(R.id.gallery);
        
        commentLL = (TextView) findViewById(R.id.comment_ll);
        commentLL.setOnClickListener(this);
        //初始化点击查看大图的动画
        animationsmall_big = AnimationUtils.loadAnimation(this,
                R.anim.scalesamall_big);
        animationbig_small = AnimationUtils.loadAnimation(this,
                R.anim.scalesbig_small);
        exit_button = (ImageView) findViewById(R.id.exit_button);
        
        super.exitButtonClick(exit_button);
        initPicAdapter();
    }
    
    /**
     * 点击查看大图的Viewpager的Adapter
     */
    PagerAdapter pagerAdapter = new PagerAdapter()
    {
        
        @Override
        public int getCount()
        {
            // TODO Auto-generated method stub
            return viewlist.size();
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
        
        @Override
        public void destroyItem(View container, int position, Object object)
        {
            ((ViewPager) container).removeView(viewlist.get(position));
        }
        
        @Override
        public Object instantiateItem(View container, int position)
        {
            // TODO Auto-generated method stub
            ((ViewPager) container).addView(viewlist.get(position));
            viewlist.get(position).setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    if (pagerLayout.getVisibility() == View.VISIBLE)
                    {
                        pagerLayout.setVisibility(View.GONE);
                        pagerLayout.setAnimation(animationbig_small);
                        titleTextView.setVisibility(View.VISIBLE);
                        scrollContent.setVisibility(View.VISIBLE);
                        if (shareBean.isActive())
                        {
                            swapLayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            bottomAllLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
            return viewlist.get(position);
        }
        
    };
    
    /**
     * 初始化可左右滑动的图片Adapter
     */
    private void initPicAdapter()
    {
        int size = pics.size();
        final List<ImageView> imgViews = new ArrayList<ImageView>();
        imgViews.clear();
        frame_dian.removeAllViews();
        for (int i = 0; i < size; i++)
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
            picAdapter = new ImageAdapter(ShareBusinessActivity.this, pics);
            gallery.setAdapter(picAdapter);
            gallery.setSelection(0);
            gallery.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    pagerImage.setAdapter(pagerAdapter);
                    pagerImage.setCurrentItem(position);
                    titleTextView.setVisibility(View.GONE);
                    scrollContent.setVisibility(View.GONE);
                    bottomAllLayout.setVisibility(View.GONE);
                    swapLayout.setVisibility(View.GONE);
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
    
    /**
     * 加载更多
     */
    protected void startToLoadMore()
    {
        if (hasMore)
        {
            if (!isLoadingMore)
            {
                
                list.showLoading();
                curPage = curPage + 1;
                isLoadingMore = true;
                isMore = true;
                Log.d(TAG, "startToLoadMore--->");
                getDetail(curPage);
            }
        }
    }
    
    private void updateView()
    {
        shareTitle.setText(shareBean.getTitle());
        listLl.setVisibility(View.VISIBLE);
        shareContact.setText(shareBean.getBusinessContact() + " "
                + shareBean.getBusinessTel());
        commentLL.setVisibility(View.VISIBLE);
        shareDescription.setText(shareBean.getContent());
        pics.clear();
        pics.addAll(shareBean.getPics());
        //初始化大图查看Vie数组
        viewlist.clear();
        for (int h = 0; h < pics.size(); h++)
        {
            ImageView imageView_pic = new ImageView(this);
            String url = Constant.URL_iconUrl + pics.get(h).getPicPath();
            bitmapUtilsPic.display(imageView_pic, url);
            viewlist.add(imageView_pic);
        }
        if (shareBean.isActive())
        {
            bottomAllLayout.setVisibility(View.GONE);
            swapLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            bottomAllLayout.setVisibility(View.VISIBLE);
            swapLayout.setVisibility(View.GONE);
        }
        initPicAdapter();
        initAdapter();
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
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        if (Constant.isNeedToRefreshReplyList)
        {
            curPage = 1;
            replies.clear();
            isMore = false;
            replyCount = String.valueOf(Integer.parseInt(replyCount) + 1);
            getDetail(curPage);
            Constant.isNeedToRefreshReplyList = false;
        }
        else if (Constant.isNeedToRefreshBusiness)
        {
            curPage = 1;
            replies.clear();
            isMore = false;
            getDetail(curPage);
            Constant.isNeedToRefreshBusiness = false;
        }
        
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
        switch (v.getId())
        {
            case R.id.comment_ll:
                Intent intent = new Intent();
                intent.setClass(ShareBusinessActivity.this,
                        CommentActivity.class);
                intent.putExtra("pics", (Serializable) pics);
                intent.putExtra("themeId", themeId);
                startActivity(intent);
                break;
            case R.id.edit_btn:
                Intent intentEdit = new Intent();
                intentEdit.setClass(ShareBusinessActivity.this,
                        PubEditActivity.class);
                intentEdit.putExtra("pics", (Serializable) pics);
                intentEdit.putExtra("subThemeType",
                        Constant.DYNAMIC_TYPE_ID_SECOND);
                intentEdit.putExtra("editTitle", shareBean.getTitle());
                intentEdit.putExtra("editContent", shareBean.getContent());
                intentEdit.putExtra("editContactName",
                        shareBean.getBusinessContact());
                intentEdit.putExtra("editConatctTel",
                        shareBean.getBusinessTel());
                intentEdit.putExtra("themeId", shareBean.getId());
                intentEdit.putExtra("isModify", true);
                startActivity(intentEdit);
                break;
            case R.id.comfirm_swap:
                confirmSwapReq();
                break;
            default:
                break;
        }
        
    }
    
    /**
     * 确认交换请求
     */
    private void confirmSwapReq()
    {
        httpUtil = new HttpUtils();
        logic.setData(mHandler);
        logic.requestModifyPayRequest(themeId, httpUtil);
    }
    
    private void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new ShareInfoAdapter(ShareBusinessActivity.this, replies);
            list.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
            
        }
        
    }
    
    /**
     * 删除请求
     * @param themeOrReplyId
     */
    private void deleteComment(String themeOrReplyId)
    {
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(
                    ShareBusinessActivity.this,
                    getResources().getString(R.string.common_pagetip_deleting));
        }
        progressDialog.show();
        httpUtil = new HttpUtils();
        logic.setData(mHandler);
        logic.requestDelete(themeOrReplyId, Constant.DELETE_COMMENT, httpUtil);
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
            case Constant.SHARE_DETAILS_SUCCESS:
                list.showLoadFinish();
                shareBean = (ShareBean) msg.obj;
                
                isLoadingMore = false;
                if (!isMore)
                {
                    replies.clear();
                }
                replies.addAll(shareBean.getReplies());
                if (replies.size() < replyTotalNum)
                {
                    hasMore = true;
                }
                else
                {
                    hasMore = false;
                }
                
                replyCount = shareBean.getCount();
                //                numContent.setText(shareBean.getCount());
                //                Log.d(TAG, "shareBean--->" + shareBean + "  replies,size--->"
                //                        + replies.size());
                if (!isMore)
                {
                    updateView();
                }
                else
                {
                    initAdapter();
                }
                
                break;
            case Constant.SHARE_DETAILS_FAILED:
                //                list.showLoadFinish();
                //                if (!isLoadingMore)
                //                {
                Toast.makeText(ShareBusinessActivity.this,
                        getResources().getString(R.string.detail_req_failed),
                        Toast.LENGTH_SHORT).show();
                //                }
                //                else
                //                {
                //                Toast.makeText(ShareBusinessActivity.this,
                //                        getResources().getString(R.string.detail_reply_req_failed),
                //                        Toast.LENGTH_SHORT)
                //                        .show();
                //                }
                break;
            case Constant.DELETE_SUCCESS:
                Toast.makeText(ShareBusinessActivity.this,
                        getResources().getString(R.string.delete_success),
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "deleteHandler   pos-->" + pos);
                replies.remove(pos);
                initAdapter();
                replyTotalNum = replyTotalNum - 1;
                if (replies.size() < replyTotalNum)
                {
                    hasMore = true;
                }
                else
                {
                    hasMore = false;
                }
                Constant.isNeedToRefreshPagerList = true;
                break;
            case Constant.DELETE_FAILED:
                Toast.makeText(ShareBusinessActivity.this,
                        getResources().getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            case Constant.GET_MODIFYPAY_SUCCESS:
                //                Toast.makeText(ShareBusinessActivity.this,
                //                        getResources().getString(R.string.swap_suceess),
                //                        Toast.LENGTH_SHORT).show();
                bottomAllLayout.setVisibility(View.GONE);
                swapLayout.setVisibility(View.VISIBLE);
                shareBean.setActive(true);
                break;
            case Constant.GET_MODIFYPAY_FAILED:
                Toast.makeText(ShareBusinessActivity.this,
                        getResources().getString(R.string.swap_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    
    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
            long arg3)
    {
        // TODO Auto-generated method stub
        pos = arg2 - 1;
        if (isMe)
        {
            showAlertDialog(0,
                    getString(R.string.tip),
                    getString(R.string.delete_tip_comment),
                    null,
                    new DialogInterface.OnClickListener()
                    {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // TODO Auto-generated method stub
                            
                            deleteComment(String.valueOf(replies.get(pos)
                                    .getId()));
                        }
                    }, DEFAULT_BTN, null, true, false);
        }
        else
        {
            String cuUserId = replies.get(pos).getUser().getUserId();
            if (cuUserId.equals(UserUtils.getUser().getUid()))
            {
                showAlertDialog(0,
                        getString(R.string.tip),
                        getString(R.string.delete_tip_comment),
                        null,
                        new DialogInterface.OnClickListener()
                        {
                            
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which)
                            {
                                // TODO Auto-generated method stub
                                
                                deleteComment(String.valueOf(replies.get(pos)
                                        .getId()));
                            }
                        }, DEFAULT_BTN, null, true, false);
            }
        }
        
        return false;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (pagerLayout.getVisibility() == View.VISIBLE)
            {
                pagerLayout.setVisibility(View.GONE);
                pagerLayout.setAnimation(animationbig_small);
                titleTextView.setVisibility(View.VISIBLE);
                scrollContent.setVisibility(View.VISIBLE);
                if (shareBean.isActive())
                {
                    swapLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    bottomAllLayout.setVisibility(View.VISIBLE);
                }
                
            }
            else
            {
                super.onKeyDown(keyCode, event);
            }
        }
        return true;
        
    }
}
