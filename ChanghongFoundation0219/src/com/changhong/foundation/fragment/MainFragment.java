/**
 * MainFragment.java
 * com.pactera.ch_bedframe.fragment
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-3 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.activity.BaseActivity;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.entity.Ad;
import com.changhong.foundation.logic.AdLogic;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.foundation.logic.PluginLogic;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ClassName:MainFragment Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午2:21:53
 */
public class MainFragment extends BaseFragment
{
    
    @ViewInject(R.id.gallery)
    private Gallery gallery;
    
    private ImageAdapter adapter;
    
    @ViewInject(R.id.frame_gallery)
    private LinearLayout frame_gallery;
    
    @ViewInject(R.id.frame_dian)
    public LinearLayout frame_dian;
    
    private BitmapUtils bitmaputils;
    
    // private int index = 0;// 当前索引
    
    @ViewInject(R.id.gridview)
    private GridView gridView;
    
    public PluginLogic pLogic;
    
    public Adapter padapter;
    
    public HttpUtils httpUtils;
    
    public View convertView;
    
    public AdLogic adLogic;
    
    public class ImageAdapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return adLogic.adList.size();
        }
        
        @Override
        public Ad getItem(int position)
        {
            return adLogic.adList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolder holder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getActivity().getBaseContext())
                        .inflate(R.layout.ad_item_layout, null);
                holder = new ViewHolder();
                holder.img_ad = (ImageView) convertView.findViewById(R.id.img_ad);
                holder.frame = (FrameLayout) convertView.findViewById(R.id.frame);
                holder.progressBarFrame = (FrameLayout) convertView.findViewById(R.id.progressBarFrame);
                
                convertView.setTag(holder);
                
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.frame.setLayoutParams(new LinearLayout.LayoutParams(
                    CHUtils.getScreenWidth(getActivity().getBaseContext()), 500));
            //            holder.img_ad.setOnTouchListener(new OnTouchListener()
            //            {
            //                
            //                @Override
            //                public boolean onTouch(View v, MotionEvent event)
            //                {
            //                    //提交给gallery处理  
            //                    gallery.onTouchEvent(event);
            //                    return false;
            //                }
            //            });
            //            holder.img_ad.setTag(position);
            //            holder.img_ad.setOnClickListener(new OnClickListener()
            //            {
            //                
            //                @Override
            //                public void onClick(View v)
            //                {
            //                    goToInternet(adLogic.adList.get((Integer) v.getTag())
            //                            .getLink());
            //                    
            //                }
            //            });
            bitmaputils.display(holder.img_ad,
                    getItem(position).getUrl(),
                    new BitmapLoadCallBack<View>()
                    {
                        @Override
                        public void onLoadCompleted(View container, String uri,
                                Bitmap bitmap, BitmapDisplayConfig config,
                                BitmapLoadFrom from)
                        {
                            ((ImageView) container).setImageBitmap(bitmap);
                            holder.progressBarFrame.setVisibility(View.GONE);
                        }
                        
                        @Override
                        public void onLoadFailed(View container, String uri,
                                Drawable drawable)
                        {
                            ((ImageView) container).setImageDrawable(drawable);
                            holder.progressBarFrame.setVisibility(View.GONE);
                            
                        }
                        
                    });
            
            // holder.img_ad.setImageResource(R.drawable.defaultloadingimage);
            return convertView;
        }
        
        class ViewHolder
        {
            private ImageView img_ad;
            
            private FrameLayout frame;
            
            private FrameLayout progressBarFrame;
        }
    }
    
    public static void SetImageSlide(View v, float startX, float toX,
            float startY, float toY)
    {
        TranslateAnimation anim = new TranslateAnimation(startX, toX, startY,
                toY);
        anim.setDuration(100);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
    
    public class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return pLogic.shortcutList.size();
        }
        
        @Override
        public BusinessInfo getItem(int position)
        {
            return pLogic.shortcutList.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;
            if (null == convertView)
            {
                convertView = LayoutInflater.from(getActivity().getBaseContext())
                        .inflate(R.layout.plugin_item_layout, null);
                holder = new ViewHolder();
                holder.img_plugin = (ImageView) convertView.findViewById(R.id.img_plugin);
                holder.tv_pluginname = (TextView) convertView.findViewById(R.id.tv_pluginname);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img_plugin.setImageBitmap(((BitmapDrawable) getItem(position).getShorcut()).getBitmap());
            holder.tv_pluginname.setText(getItem(position).getBusinessName());
            return convertView;
        }
        
        class ViewHolder
        {
            private ImageView img_plugin;
            
            private TextView tv_pluginname;
        }
    }
    
    @Override
    public void onClick(View v)
    {
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.main_layout, container, false);
        ViewUtils.inject(this, convertView);
        frame_gallery.setLayoutParams(new LinearLayout.LayoutParams(
                CHUtils.getScreenWidth(getActivity().getBaseContext()), 500));
        bitmaputils = new BitmapUtils(getActivity().getBaseContext());
        //        bitmaputils.configDefaultLoadingImage(R.drawable.defaultloadingimage);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.defaultloadingimage);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initAdAdapter();
        initPluginAdapter();
        requestAds();
        if (!(CHUtils.checkIsExit(getActivity().getBaseContext(),
                "com.changhong")))
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    installWuyeApk();
                    installSnsApk();
                    installStore();
                    installCinema();
                }
            });
        }
        //        if (!CHUtils.checkIsExit(getActivity().getBaseContext(),
        //                "com.changhong.sns"))
        //        {
        //            getActivity().runOnUiThread(new Runnable()
        //            {
        //                @Override
        //                public void run()
        //                {
        //                    installSnsApk();
        //                }
        //            });
        //        }
        
        return convertView;
    }
    
    public void initAdAdapter()
    {
        
        // for (int i = 0; i < purls.length; i++) {
        // ImageView iv = new ImageView(getActivity().getBaseContext());
        // LayoutParams lp = (LayoutParams) iv.getLayoutParams();
        // if (null == lp) {
        // lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
        // LayoutParams.WRAP_CONTENT);
        // }
        // iv.setImageResource(R.drawable.dian);
        // lp.rightMargin = 5;
        // iv.setLayoutParams(lp);
        // frame_dian.addView(iv);
        // imgViews.add(iv);
        //
        // }
        
        if (null == adapter)
        {
            adapter = new ImageAdapter();
            gallery.setAdapter(adapter);
            gallery.setSelection(0);
            gallery.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    
                }
            });
            
            gallery.setOnItemSelectedListener(new OnItemSelectedListener()
            {
                
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                        int position, long arg3)
                {
                    if (position == 0)
                    {
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((BaseActivity) getActivity()).setSlideMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            }
                        });
                    }
                    else
                    {
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((BaseActivity) getActivity()).setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
                            }
                        });
                    }
                    // ((ImageView)
                    // frame_dian.getChildAt(index)).setImageResource(R.drawable.dian);
                    // ((ImageView)
                    // frame_dian.getChildAt(position)).setImageResource(R.drawable.dian_h);
                    // index = position;
                }
                
                @Override
                public void onNothingSelected(AdapterView<?> arg0)
                {
                    
                }
            });
            gallery.setOnTouchListener(new OnTouchListener()
            {
                
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            int postion = gallery.getSelectedItemPosition();
                            if (postion == 0)
                            {
                                getActivity().runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ((BaseActivity) getActivity()).setSlideMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                                    }
                                });
                            }
                            else
                            {
                                getActivity().runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ((BaseActivity) getActivity()).setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
                                    }
                                });
                            }
                            break;
                    }
                    return false;
                    
                }
            });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    public void goToInternet(String link)
    {
        if (!StringUtils.isEmpty(link))
        {
            Uri uri = Uri.parse(link);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
    
    public void initPluginAdapter()
    {
        if (null == padapter)
        {
            padapter = new Adapter();
            gridView.setAdapter(padapter);
            gridView.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    //                    Intent intent = padapter.getItem(position).getIntent();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName(
                            padapter.getItem(position).getPackageName(),
                            padapter.getItem(position).getMainActivityPath());
                    intent.setComponent(cn);
                    
                    intent.putExtra("account",
                            LoginLogic.getInstance().user.getAccount());
                    intent.putExtra("nickname",
                            LoginLogic.getInstance().user.getNickName());
                    intent.putExtra("organId",
                            LoginLogic.getInstance().user.getOrganId());
                    intent.putExtra("communtyId",
                            LoginLogic.getInstance().user.getCommuntyId());
                    intent.putExtra("userId",
                            LoginLogic.getInstance().user.getUid());
                    intent.putExtra("pwd",
                            LoginLogic.getInstance().user.getPwd());
                    intent.putExtra("headUrl",
                            LoginLogic.getInstance().user.getHeadUrl());
                    intent.putExtra("cbsUrl", HttpUrl.CBS);
                    intent.putExtra("snsUrl", HttpUrl.SNS);
                    intent.putExtra("address",
                            LoginLogic.getInstance().user.getAddress());
                    intent.putExtra("mobile",
                            LoginLogic.getInstance().user.getMobile());
                    intent.putExtra("reallyName",
                            LoginLogic.getInstance().user.getReallyName());
                    startActivity(intent);
                }
            });
        }
        else
        {
            padapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void initData()
    {
        pLogic = PluginLogic.getInstance();
        adLogic = AdLogic.getInstance();
        
        pLogic.setShortcutList(CHUtils.getApplicationInfo(getActivity().getBaseContext(),
                Constant.PREFIX));
    }
    
    /**
     * 
     * 
     * installWuyeApk(安装物业apk)
     * 
     * 
     * @param name
     * 
     * @param @return 设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since 2014年1月2日
     */
    public void installWuyeApk()
    {
        
        String path = BitmapCommonUtils.getDiskCacheDir(getActivity().getBaseContext(),
                "chapp");
        if (CHUtils.copyApkFromAssets((getActivity()),
                "wuye.mp3",
                path,
                "/wuye.apk"))
        {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + path + "/wuye.apk"),
                    "application/vnd.android.package-archive");
            startActivity(intent);
            isupdate = true;
        }
        
    }
    
    public void installSnsApk()
    {
        
        String path = BitmapCommonUtils.getDiskCacheDir(getActivity().getBaseContext(),
                "chapp");
        if (CHUtils.copyApkFromAssets((getActivity()),
                "sns.mp3",
                path,
                "/sns.apk"))
        {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + path + "/sns.apk"),
                    "application/vnd.android.package-archive");
            startActivity(intent);
            isupdate = true;
        }
        
    }
    
    public void installStore()
    {
        
        String path = BitmapCommonUtils.getDiskCacheDir(getActivity().getBaseContext(),
                "chapp");
        if (CHUtils.copyApkFromAssets((getActivity()),
                "store.mp3",
                path,
                "/store.apk"))
        {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + path + "/store.apk"),
                    "application/vnd.android.package-archive");
            startActivity(intent);
            isupdate = true;
        }
        
    }
    
    public void installCinema()
    {
        
        String path = BitmapCommonUtils.getDiskCacheDir(getActivity().getBaseContext(),
                "chapp");
        if (CHUtils.copyApkFromAssets((getActivity()),
                "cinema.mp3",
                path,
                "/cinema.apk"))
        {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + path + "/cinema.apk"),
                    "application/vnd.android.package-archive");
            startActivity(intent);
            isupdate = true;
        }
        
    }
    
    boolean isupdate = false;
    
    @Override
    public void onResume()
    {
        super.onResume();
        // if (isupdate) {
        pLogic.setShortcutList(CHUtils.getApplicationInfo(getActivity().getBaseContext(),
                Constant.PREFIX));
        initPluginAdapter();
        isupdate = false;
        // }LoginLogic.getInstance().user.getAccount()
    }
    
    public void requestAds()
    {
        httpUtils = new HttpUtils();
        adLogic.setData(fHandler);
        adLogic.requestAds(LoginLogic.getInstance().baseAccountInfo, httpUtils);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_ADLIST_SUCCESS:
            {
                initAdAdapter();
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    public void reqeustAdClick(int adId)
    {
        
        httpUtils = new HttpUtils();
        adLogic.setData(fHandler);
        adLogic.requestAdClick(adId, httpUtils);
        
    }
    
    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
}
