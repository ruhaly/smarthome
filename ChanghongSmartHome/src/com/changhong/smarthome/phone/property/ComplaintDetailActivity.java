package com.changhong.smarthome.phone.property;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.ServicesItem;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.GetTsItemList_wLogic;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

public class ComplaintDetailActivity extends SuperActivity
{
    private EditText tsfw_delailcontent;
    
    private ImageView ts_lefttitle;
    
    //public TextView ts_lefttext;
    private LinearLayout pj_layout;
    
    private LinearLayout pj_layoutsure;
    
//    private TextView tsfw_delaillr1;
    
//    private TextView tsfw_delaillr2;
    
    private TextView timeText;
    
    private RatingBar rating;
    
    private RatingBar ratinghide;
    
    private Button tsfw_update;
    
    private Button tsfw_updatehide;
    
    private TextView ts_df;
    
    private TextView ts_surepj;
    
    private RatingBar ratingsure;
    
    private GetTsItemList_wLogic listtsitemlogic_w;
    
    private HttpUtils httpUtil;
    
    private ImageView ts_imageview;
    
    private TextView ts_textview;
    
    private ImageView addImg;
    
    private Animation animationsmall_big;
    
    private Animation animationbig_small;
    
    private ScrollView scrollView_ts;
    
    private RelativeLayout title_tsfwdelail;
    
    private LinearLayout pagerLayout;
    
    private ViewPager pagerImage;
    
    private ArrayList<ImageView> viewlist;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        			WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.tsfwlist_detail);
        BitmapUtils bitmapUtilsHead = new BitmapUtils(this);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        Intent intent = getIntent();
        final ServicesItem item = (ServicesItem) intent.getSerializableExtra("servicesitem");
        String content = item.getContent();
        int fwzt = item.getFwzt();
        scrollView_ts = (ScrollView) findViewById(R.id.scrollView_ts);
        title_tsfwdelail = (RelativeLayout) findViewById(R.id.title_tsfwdelail);
        animationsmall_big = AnimationUtils.loadAnimation(this,
                R.anim.scalesamall_big);
        animationbig_small = AnimationUtils.loadAnimation(this,
                R.anim.scalesbig_small);
        pagerLayout = (LinearLayout) findViewById(R.id.pagerLayout);
        pagerImage = (ViewPager) findViewById(R.id.pagerImage);
        ts_lefttitle = (ImageView) findViewById(R.id.ts_lefttitle);
        //ts_lefttext = (TextView) findViewById(R.id.ts_lefttext);
        
        tsfw_delailcontent = (EditText) findViewById(R.id.tsfw_delailcontent);
        
        ratinghide = (RatingBar) findViewById(R.id.ratinghide);
        rating = (RatingBar) findViewById(R.id.rating);
        tsfw_update = (Button) findViewById(R.id.tsfw_update);
        tsfw_updatehide = (Button) findViewById(R.id.tsfw_updatehide);
        pj_layout = (LinearLayout) findViewById(R.id.pj_layout);
        pj_layoutsure = (LinearLayout) findViewById(R.id.pj_layoutsure);
        ts_surepj = (TextView) findViewById(R.id.ts_surepj);
        ts_df = (TextView) findViewById(R.id.ts_df);
        ratingsure = (RatingBar) findViewById(R.id.ratingsure);
        ts_imageview = (ImageView) findViewById(R.id.ts_imgview);
        addImg = (ImageView) findViewById(R.id.addImg);
        viewlist = new ArrayList<ImageView>();
        if (null != item.getPicStrList())
        {
            for (int h = 0; h < item.getPicStrList().size(); h++)
            {
                ImageView imageView_pic = new ImageView(this);
                bitmapUtilsHead.display(imageView_pic,
                        (String) item.getPicStrList().get(h));
                viewlist.add(imageView_pic);
            }
        }
        
        final PagerAdapter pagerAdapter = new PagerAdapter()
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
                return viewlist.get(position);
            }
            
        };
        if(item.getPicStrList() != null && item.getPicStrList().size() > 0)
        {
            addImg.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    if (addImg.getBackground() != getResources().getDrawable(R.drawable.add_image))
                    {
                        pagerImage.setAdapter(pagerAdapter);
                        title_tsfwdelail.setVisibility(View.GONE);
                        scrollView_ts.setVisibility(View.GONE);
                        pagerLayout.setVisibility(View.VISIBLE);
                        pagerLayout.startAnimation(animationsmall_big);
                    }
                    
                }
            });
        }
        
        
        if (null != item.getPicStrList())
        {
            if (item.getPicStrList().size() > 0)
            {
                bitmapUtilsHead.display(addImg, (String) item.getPicStrList()
                        .get(0));
            }
        }
        if (item.getPropertyallback() != null
                && !item.getPropertyallback().equals(""))
        {
            ts_df.setText(item.getPropertyallback());
        }
        
        if (fwzt == 1)
        {
            
            /*
             * findViewById(R.id.ts_fwjd00delail).setBackgroundResource(
             * R.drawable.dian_h);
             */
            timeText = (TextView) findViewById(R.id.ts_time1);
            ts_textview = (TextView) findViewById(R.id.ts_text1);
            tsfw_delailcontent.setHint(getResources().getString(R.string.bx_detail_not_pl_hint));
            tsfw_delailcontent.setEnabled(false);
            rating.setVisibility(View.GONE);
            ratinghide.setVisibility(View.VISIBLE);
            tsfw_update.setVisibility(View.GONE);
            tsfw_updatehide.setVisibility(View.VISIBLE);
        }
        if (fwzt == 2)
        {
            
            timeText = (TextView) findViewById(R.id.ts_time2);
            ts_textview = (TextView) findViewById(R.id.ts_text2);
            ts_imageview.setBackgroundResource(R.drawable.progressbar2);
            tsfw_delailcontent.setHint(getResources().getString(R.string.bx_detail_not_pl_hint));
            tsfw_delailcontent.setEnabled(false);
            rating.setVisibility(View.GONE);
            ratinghide.setVisibility(View.VISIBLE);
            tsfw_update.setVisibility(View.GONE);
            tsfw_updatehide.setVisibility(View.VISIBLE);
        }
        if (fwzt == 3)
        {
            
            timeText = (TextView) findViewById(R.id.ts_time3);
            ts_textview = (TextView) findViewById(R.id.ts_text3);
            ts_imageview.setBackgroundResource(R.drawable.progressbar3);
            // rating.setRating(item.getPf());
            tsfw_update.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    rating.getRating();
                    tsfw_delailcontent.getText().toString();
                    
                    if (!tsfw_delailcontent.getText().toString().equals(""))
                    {
                        httpUtil = new HttpUtils();
                        listtsitemlogic_w = new GetTsItemList_wLogic();
                        listtsitemlogic_w.setData(mHandler);
                        listtsitemlogic_w.updateItem("3",
                                item.getRyid() + "",
                                item.getFwid() + "",
                                tsfw_delailcontent.getText().toString(),
                                rating.getRating() + "",
                                httpUtil);
                        
                    }
                    else
                    {
                        showToast("评论内容不能为空!");
                        
                    }
                    
                }
                
            });
        }
        if (fwzt == 4)
        {
            
            timeText = (TextView) findViewById(R.id.ts_time4);
            ts_textview = (TextView) findViewById(R.id.ts_text4);
            ts_imageview.setBackgroundResource(R.drawable.progressbar4);
            // tsfw_update.setVisibility(View.GONE);
            pj_layout.setVisibility(View.GONE);
            pj_layoutsure.setVisibility(View.VISIBLE);
            tsfw_delailcontent.setVisibility(View.GONE);
            ts_surepj.setText(item.getContent_pj());
            ratingsure.setRating(item.getPf());
        }
        timeText.setVisibility(View.VISIBLE);
//        timeText.setText(item.getStateTimeStr().substring(0, 10));
        String time = item.getStateTimeStr().substring(0, 10);
        String[] temp = time.split("-");
        String month = temp[1];
        if(month.startsWith("0"))
        {
            month = month.substring(1);
        }
        timeText.setText(temp[0] + "-"+ month + "-"+ temp[2]);
        timeText.setTextSize(TypedValue.COMPLEX_UNIT_PX,16);
        ts_textview.setTextColor(getResources().getColor(R.color.title_text_black));
        
        ((TextView) findViewById(R.id.ts_textdelail)).setText(content);
        ts_lefttitle.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                
                finish();
            }
        });
        
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
        /*
         * case SuperLogic.BXLIST_SUCCESS_MSGWHAT: initBxAdapter(); break;
         */
            case HttpAction.TSUPDATE_SUCCESS_MSGWHAT:
                Intent intentx = new Intent();
                
                intentx.putExtra("result", "ok");
                intentx.putExtra("content_pj", tsfw_delailcontent.getText()
                        .toString());
                intentx.putExtra("pf", rating.getRating() + "");
                
                ComplaintDetailActivity.this.setResult(2, intentx);
                ComplaintDetailActivity.this.finish();
                break;
            
            default:
                break;
        }
        super.handleMsg(msg);
        
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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (pagerLayout.getVisibility() == View.VISIBLE)
            {
                pagerLayout.setVisibility(View.GONE);
                pagerLayout.setAnimation(animationbig_small);
                title_tsfwdelail.setVisibility(View.VISIBLE);
                scrollView_ts.setVisibility(View.VISIBLE);
                
            }
        }
        return true;
        
    }
    
}
