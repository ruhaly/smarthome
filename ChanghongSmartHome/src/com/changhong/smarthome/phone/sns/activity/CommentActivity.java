package com.changhong.smarthome.phone.sns.activity;

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
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.sdk.widget.CustomGallery;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.sdk.widget.PullRefreshListView;
import com.changhong.sdk.widget.PullRefreshListView.NewScrollerListener;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.adapter.ImageAdapter;
import com.changhong.smarthome.phone.sns.adapter.ShareInfoAdapter;
import com.changhong.smarthome.phone.sns.bean.CommentReqBean;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.changhong.smarthome.phone.sns.bean.TSnsReply;
import com.changhong.smarthome.phone.sns.bean.TSnsThemePic;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.changhong.smarthome.phone.store.tools.StringUtil;
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
public class CommentActivity extends SnsSuperActivity implements OnClickListener,
        OnItemLongClickListener
{
    
    private static final String TAG = "ShareOriogionalActivity";
    
    private ImageView exit_button;//退出返回按钮
    
    private HttpUtils httpUtils;
    
    /**
     * 回复的最大字数
     */
    private int replyMaxNum = 333;
    
    /**
     * 图片下载工具
     */
    private BitmapUtils bitmapUtilsPic;
    
    private String themeId;
    
    //    private String replyCount;
    
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
    
    private TextView commentLL;
    
    private PullRefreshListView list;
    
    private LinearLayout frame_gallery;
    
    private Gallery gallery;
    
    public LinearLayout frame_dian;
    
    private LinearLayout listLl;
    
    private LinearLayout scrollContent;
    
    private RelativeLayout bottomAllLayout;
    
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
    
    private List<ImageView> imgViews = new ArrayList<ImageView>();
    
    private TextView titleTextView;
    
    private LinearLayout pagerLayout;
    
    private EditText inputContent;
    
    private ViewPager pagerImage;
    
    private ArrayList<ImageView> viewlist = new ArrayList<ImageView>();
    
    private Animation animationsmall_big;
    
    private Animation animationbig_small;
    
    private CommentReqBean commentReq;
    
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
        setContentView(R.layout.comment);
        logic = IntShareLogic.getInstance();
        bitmapUtilsPic = new BitmapUtils(CommentActivity.this);
        bitmapUtilsPic.configDefaultLoadingImage(R.drawable.picture);
        bitmapUtilsPic.configDefaultLoadFailedImage(R.drawable.picture);
        bitmapUtilsPic.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initView();
        Intent intent = getIntent();
        if (null != intent)
        {
            pics = (List<TSnsThemePic>) intent.getSerializableExtra("pics");
            themeId = intent.getStringExtra("themeId");
        }
        initPicAdapter();
        for (int h = 0; h < pics.size(); h++)
        {
            ImageView imageView_pic = new ImageView(this);
            String url = Constant.URL_iconUrl + pics.get(h).getPicPath();
            bitmapUtilsPic.display(imageView_pic, url);
            viewlist.add(imageView_pic);
        }
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(CommentActivity.this,
                    getResources().getString(R.string.loading));
        }
        progressDialog.show();
    }
    
    /**
     * 加载评论
     * @param curPager
     */
    private void getComment(int curPager)
    {
        httpUtil = new HttpUtils();
        logic.setData(mHandler);
        logic.requestQueryAllReplyRequest(themeId, pageSize, curPager, httpUtil);
        
    }
    
    private void initView()
    {
        listLl = (LinearLayout) findViewById(R.id.list_ll);
        scrollContent = (LinearLayout) findViewById(R.id.content_all);
        bottomAllLayout = (RelativeLayout) findViewById(R.id.bottom_all);
        titleTextView = (TextView) findViewById(R.id.comment_title);
        list = (PullRefreshListView) findViewById(R.id.share_list);
        pagerLayout = (LinearLayout) findViewById(R.id.pagerLayout);
        pagerImage = (ViewPager) findViewById(R.id.pagerImage);
        inputContent = (EditText) findViewById(R.id.input_content);
        exit_button = (ImageView) findViewById(R.id.exit_button);
        super.exitButtonClick(exit_button);
        
        list.setOnItemLongClickListener(this);
        //        //可在ScrollView里滑动的ListView需要设置的代码
        ////        list.setParentScrollView(scrollContent);
        
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
        for (int i = 0; i < pics.size(); i++)
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
            picAdapter = new ImageAdapter(CommentActivity.this, pics);
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
                getComment(curPage);
            }
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
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        
        curPage = 1;
        replies.clear();
        isMore = false;
        getComment(curPage);
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
        switch (v.getId())
        {
            case R.id.comment_ll:
                String contentString = inputContent.getText().toString().trim();
                if (StringUtil.isEmpty(contentString))
                {
                    
                    Toast.makeText(CommentActivity.this,
                            getResources().getString(R.string.input_comment_content),
                            Toast.LENGTH_SHORT)
                            .show();
                    
                }
                else
                {
                    if (contentString.length() > replyMaxNum)
                    {
                        Toast.makeText(CommentActivity.this,
                                getResources().getString(R.string.too_long_reply_content),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    else
                    {
                        commentReq = new CommentReqBean();
                        commentReq.setContent(contentString);
                        commentReq.setCreator(UserUtils.getUser().getUid());
                        commentReq.setCreatorNickName(Constant.NIKENAME);
                        commentReq.setReplyTime("");
                        commentReq.setThemeId(themeId);
                        commentReq();
                    }
                }
                
                break;
            default:
                break;
        }
        
    }
    
    /**
     * 评论请求
     */
    private void commentReq()
    {
        
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestComment(commentReq, httpUtils);
    }
    
    private void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new ShareInfoAdapter(CommentActivity.this, replies);
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
            progressDialog = new MCloudProgressDialog(CommentActivity.this,
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
            case Constant.GET_COMMENT_LIST_SUCCESS:
                list.showLoadFinish();
                ShareBean shareBean = (ShareBean) msg.obj;
                List<TSnsReply> rs = shareBean.getReplies();
                
                isLoadingMore = false;
                if (!isMore)
                {
                    replies.clear();
                }
                replies.addAll(rs);
                if (replies.size() < replyTotalNum)
                {
                    hasMore = true;
                }
                else
                {
                    hasMore = false;
                }
                initAdapter();
                replyTotalNum = Integer.parseInt(shareBean.getCount());
                
                break;
            case Constant.GET_COMMENT_LIST_FAILED:
                //notice when there is no commnets ,it will also return 1
                break;
            case Constant.DELETE_SUCCESS:
                Toast.makeText(CommentActivity.this,
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
                Toast.makeText(CommentActivity.this,
                        getResources().getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            case Constant.REPLY_SUCCESS:
                Toast.makeText(CommentActivity.this,
                        getResources().getString(R.string.comment_success),
                        Toast.LENGTH_SHORT).show();
                inputContent.setText("");
                Constant.isNeedToRefreshReplyList = true;
                curPage = 1;
                replies.clear();
                isMore = false;
                getComment(curPage);
                break;
            case Constant.REPLY_FAILED:
                Toast.makeText(CommentActivity.this,
                        getResources().getString(R.string.comment_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            case SuperLogic.CONNECT_ERROR_MSGWHAT:
                Toast.makeText(CommentActivity.this,
                        getResources().getString(R.string.req_failed),
                        Toast.LENGTH_SHORT).show();
                //                list.showLoadFinish();
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
                bottomAllLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                super.onKeyDown(keyCode, event);
            }
        }
        return true;
        
    }
    
}
