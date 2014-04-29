package com.changhong.smarthome.phone.foundation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.CHApplication;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.activity.PromanageNoticeActivity;
import com.changhong.smarthome.phone.foundation.baseapi.MsgWhat;
import com.changhong.smarthome.phone.foundation.bean.Ad;
import com.changhong.smarthome.phone.foundation.logic.AdLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.property.PropertyMainActivity;
import com.changhong.smarthome.phone.property.activity.HomeEscrowMainActivity;
import com.changhong.smarthome.phone.property.activity.IntegrationActivity;
import com.changhong.smarthome.phone.property.activity.PayServiceActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ProManageFragment extends SuperFragment
{
    private static final String TAG = "ProManageFragment";
    
    private View convertView;
    
    //报修标识
    public static String BXBZ = "frameRepair";
    
    //投诉标识
    public static String TSBZ = "frameSuggestionBox";
    
    //房屋代管
    public static String HOMEESCROW = "frameHomeEscrow";
    
    @ViewInject(R.id.frame01)
    private LinearLayout frame01;
    
    @ViewInject(R.id.gallery)
    private Gallery gallery;
    
    public HttpUtils httpUtils;
    
    private ImageAdapter adapter;
    
    @ViewInject(R.id.frame_gallery)
    private LinearLayout frame_gallery;
    
    @ViewInject(R.id.frame_dian)
    public LinearLayout frame_dian;
    
    private BitmapUtils bitmaputils;
    
    public AdLogic adLogic;
    
    @ViewInject(R.id.gvPlugin)
    private GridView gvPlugin;
    
    private PluginAdapter pAdapter;
    
    private List<BusinessInfo> pList = new ArrayList<BusinessInfo>();
    
    @Override
    public void updateView(Message msg)
    {
    }
    
    @Override
    public void updateView(Intent intent)
    {
        
    }
    
    @Override
    public void initData()
    {
        adLogic = AdLogic.getInstance();
        
        initPlugin();
        
    }
    
    /**
     * 
     * 初始化界面
     * [功能详细描述]
     */
    private void initPlugin()
    {
        BusinessInfo bi = new BusinessInfo();
        bi.setBusinessName(getString(R.string.repair));
        bi.setIconResId(R.drawable.baoxiu);
        
        BusinessInfo bi1 = new BusinessInfo();
        bi1.setBusinessName(getString(R.string.suggestion_box));
        bi1.setIconResId(R.drawable.yijianxiang);
        
        BusinessInfo bi2 = new BusinessInfo();
        bi2.setBusinessName(getString(R.string.phone_manage));
        bi2.setIconResId(R.drawable.hujiaowuguan);
        
        BusinessInfo bi3 = new BusinessInfo();
        bi3.setBusinessName(getString(R.string.integral));
        bi3.setIconResId(R.drawable.jifen);
        
        BusinessInfo bi4 = new BusinessInfo();
        bi4.setBusinessName(getString(R.string.home_escrow));
        bi4.setIconResId(R.drawable.fangwudaiguan);
        
        BusinessInfo bi5 = new BusinessInfo();
        bi5.setBusinessName(getString(R.string.pay_service));
        bi5.setIconResId(R.drawable.jiaofeifuwu);
        
        pList.add(bi);
        pList.add(bi1);
        pList.add(bi2);
        pList.add(bi3);
        pList.add(bi4);
        pList.add(bi5);
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.promanage_layout2,
                container,
                false);
        ViewUtils.inject(this, convertView);
        bitmaputils = new BitmapUtils(getActivity().getBaseContext());
        //        bitmaputils.configDefaultLoadingImage(R.drawable.defaultloadingimage);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.defaultloadingimage);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        initPluginAdapter();
        initAdAdapter();
        requestAds();
        return convertView;
    }
    
    /**
     * 
     * 获取广告
     * [功能详细描述]
     */
    public void requestAds()
    {
        httpUtils = new HttpUtils();
        adLogic.setData(fHandler);
        adLogic.requestAds(LoginLogic.getInstance().getBaseAccountInfo(),
                httpUtils);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MsgWhat.MSGWHAT_ADLIST_SUCCESS:
            {
                initAdAdapter();
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
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
                    goToInternet(adapter.getItem(position).getLink());
                }
            });
            
            gallery.setOnItemSelectedListener(new OnItemSelectedListener()
            {
                
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                        int position, long arg3)
                {
                    // frame_dian.getChildAt(index)).setImageResource(R.drawable.dian);
                    // frame_dian.getChildAt(position)).setImageResource(R.drawable.dian_h);
                    // index = position;
                }
                
                @Override
                public void onNothingSelected(AdapterView<?> arg0)
                {
                    
                }
            });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    private void initPluginAdapter()
    {
        if (null == pAdapter)
        {
            pAdapter = new PluginAdapter();
            gvPlugin.setAdapter(pAdapter);
            gvPlugin.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    switch (position)
                    {
                        case 0:
                        {
                            
                            if (!CHApplication.LOGIN)
                            {
                                ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
                                return;
                            }
                            
                            Intent intent = new Intent();
                            intent.putExtra("bz", BXBZ);
                            intent.setClass(getActivity(),
                                    PropertyMainActivity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.anim_enter,
                                    R.anim.anim_exit);
                            
                            break;
                        }
                        case 1:
                        {
                            
                            if (!CHApplication.LOGIN)
                            {
                                ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
                                return;
                            }
                            Intent intent = new Intent();
                            intent.putExtra("bz", TSBZ);
                            intent.setClass(getActivity(),
                                    PropertyMainActivity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.anim_enter,
                                    R.anim.anim_exit);
                            
                            break;
                        }
                        case 2:
                        {
                            
                            if (!CHApplication.LOGIN)
                            {
                                ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
                                return;
                            }
                            Intent intent = new Intent(
                                    "android.intent.action.CALL",
                                    Uri.parse("tel:" + "10010"));
                            startActivity(intent);
                            
                            break;
                        }
                        case 3:
                        {
                            
                            if (!CHApplication.LOGIN)
                            {
                                ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
                                return;
                            }
                            Log.i(TAG,
                                    "----------------------start IntegrationActivity");
                            Intent intent = new Intent();
                            intent.putExtra("bz", HOMEESCROW);
                            intent.setClass(getActivity(),
                                    IntegrationActivity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.anim_enter,
                                    R.anim.anim_exit);
                            
                            break;
                        }
                        case 4:
                        {
                            
                            if (!CHApplication.LOGIN)
                            {
                                ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
                                return;
                            }
                            Intent intent = new Intent();
                            intent.putExtra("bz", HOMEESCROW);
                            intent.setClass(getActivity(),
                                    HomeEscrowMainActivity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.anim_enter,
                                    R.anim.anim_exit);
                            
                            break;
                        }
                        case 5:
                        {
                            
                            if (!CHApplication.LOGIN)
                            {
                                ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setClass(getActivity(),
                                    PayServiceActivity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.anim_enter,
                                    R.anim.anim_exit);
                            
                            break;
                        }
                        default:
                            break;
                    }
                }
            });
        }
        else
        {
            pAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 
     * 广告点击
     * [功能详细描述]
     * @param link
     */
    public void goToInternet(String link)
    {
        if (!StringUtils.isEmpty(link))
        {
            Uri uri = Uri.parse(link);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.frameIntegral:
                Log.i(TAG, "jijijijifen");
                break;
            
            default:
                break;
        }
    }
    
    /**
     * 
     * 广告适配器
     * [功能详细描述]
     * @author hanliangru
     * @version [智慧社区-终端底座, 2014年4月18日]
     */
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
    
    /**
     * 
     * 插件适配器
     * [功能详细描述]
     * @author hanliangru
     * @version [智慧社区-终端底座, 2014年4月24日]
     */
    public class PluginAdapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return pList.size();
        }
        
        @Override
        public BusinessInfo getItem(int position)
        {
            return pList.get(position);
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
                        .inflate(R.layout.promanage_plugin_item_layout, null);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(holder);
                
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img.setImageResource(getItem(position).getIconResId());
            holder.tvName.setText(getItem(position).getBusinessName());
            return convertView;
        }
        
        class ViewHolder
        {
            private ImageView img;
            
            private TextView tvName;
        }
    }
    
    @OnClick(R.id.frameProNotice)
    public void frameProNoticeClick(View view)
    {
        startActivity(new Intent(getActivity().getBaseContext(),
                PromanageNoticeActivity.class));
    }
}
