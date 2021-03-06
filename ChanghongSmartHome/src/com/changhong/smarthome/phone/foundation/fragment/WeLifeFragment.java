package com.changhong.smarthome.phone.foundation.fragment;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.CHApplication;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.YellowPages.activity.YellowPagesActivity;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.activity.GroupBuyListActivity;
import com.changhong.smarthome.phone.sns.activity.ShareListActivity;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingListVO;
import com.changhong.smarthome.phone.sns.bean.Pager;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.changhong.smarthome.phone.store.activity.StoreMainActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WeLifeFragment extends SuperFragment
{
    private Context context;
    
    private View convertView;
    
    private static final int shaiSH = 1;
    
    private static final int jiaoHKj = 2;
    
    //    @ViewInject(R.id.sun_life_tv)
    //    private TextView shaiSHTv;
    
    @ViewInject(R.id.sun_life_bg_iv)
    private ImageView shaiShIv;
    
    @ViewInject(R.id.sun_life_fl)
    private FrameLayout shaiSHFl;
    
    @ViewInject(R.id.group_nuy_textView)
    private TextView groupBuyTv;
    
    //    @ViewInject(R.id.swap_space_tv)
    //    private TextView jiaoHKTv;
    
    @ViewInject(R.id.swap_space_bg_iv)
    private ImageView jiaoHKIv;
    
    @ViewInject(R.id.swap_space_fl)
    private FrameLayout jiaoHKFl;
    
    private HttpUtils httpUtils;
    
    private IntShareLogic snsLogic;
    
    private BitmapUtils bitMapUtilSwap;
    
    private BitmapUtils bitMapUtilSun;
    
    private Pager pager = new Pager();
    
    //    private GroupBuyBean groupBuyBean;
    
    @Override
    public void onClick(View v)
    {
        
    }
    
    @Override
    public void updateView(Message msg)
    {
        if (msg.what == com.changhong.sdk.baseapi.Constant.UPDATE_VIEW)
        {
            return;//TODO  add for reslease
            //            getDynamicList(String.valueOf(Constant.PUBLIC_RANGE),
            //                    Constant.DYNAMIC_TYPE_ID_SHOT,
            //                    shaiSH);
            //            getDynamicList(String.valueOf(Constant.PUBLIC_RANGE),
            //                    Constant.DYNAMIC_TYPE_ID_SECOND,
            //                    jiaoHKj);
            //            pager.setPageId(1);
            //            pager.setPageSize(1);
            //            getGroupList(LoginLogic.getInstance().curCommunity.getCommunityId(),
            //                    pager);
            
        }
    }
    
    @Override
    public void updateView(Intent intent)
    {
        
    }
    
    @Override
    public void initData()
    {
        snsLogic = new IntShareLogic();
        bitMapUtilSwap = new BitmapUtils(getActivity());
        bitMapUtilSwap.configDefaultLoadingImage(R.drawable.picture);
        bitMapUtilSwap.configDefaultLoadFailedImage(R.drawable.picture);
        bitMapUtilSwap.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitMapUtilSun = new BitmapUtils(getActivity());
        bitMapUtilSun.configDefaultLoadingImage(R.drawable.picture);
        bitMapUtilSun.configDefaultLoadFailedImage(R.drawable.picture);
        bitMapUtilSun.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        convertView = inflater.inflate(R.layout.welife_layout, container, false);
        ViewUtils.inject(this, convertView);
        return convertView;
    }
    
    @OnClick(R.id.textView3)
    public void tvStoreClick(View view)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), StoreMainActivity.class);
        startActivity(intent);
    }
    
    @OnClick(R.id.sun_life_fl)//晒生活和交换空间只是类型不同，ShareListActivity
    public void tvShaiSHFlClick(View view)
    {
        showList(Constant.DYNAMIC_TYPE_ID_SHOT);
        
    }
    
    @OnClick(R.id.swap_space_fl)
    public void tvJiaoHkFlClick(View view)
    {
        showList(Constant.DYNAMIC_TYPE_ID_SECOND);
        
    }
    
    @OnClick(R.id.group_buy_fl)
    public void tvGroupBuyClick(View view)//
    {
        showGroupBuyList(Constant.DYNAMIC_TYPE_ID_ACTIVITY);
        
    }
    
    @OnClick(R.id.group_buy_f2)
    public void tvGroupStoreClick(View view)
    {
        //        showGroupBuyList(Constant.DYNAMIC_TYPE_ID_ACTIVITY);
        Intent intent = new Intent();
        intent.setClass(getActivity(), StoreMainActivity.class);
        startActivity(intent);
        
    }
    
    /**
     * 展示动态列表
     * 
     * @param subThemeType
     */
    private void showList(String subThemeType)
    {
        
        if (!CHApplication.LOGIN)
        {
            ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
            return;
        }
        
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShareListActivity.class);
        intent.putExtra("subThemeType", subThemeType);
        startActivity(intent);
    }
    
    /**
     * 展示动态列表
     * 
     * @param subThemeType
     */
    private void showGroupBuyList(String subThemeType)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GroupBuyListActivity.class);
        intent.putExtra("subThemeType", subThemeType);
        startActivity(intent);
    }
    
    /**
     * 获取动态列表
     * 
     * @param themeType
     * @param pageIndex
     * @param pageSize
     */
    
    private void getDynamicList(String themeType, String themeSubType, int pos)
    {
        httpUtils = new HttpUtils();
        snsLogic.setData(fHandler);
        snsLogic.requestGetDynamicInfo(themeSubType, 1, 1,true,false);
    }
    
    /**
     * 
     * @param communityCode
     * @param pager
     */
    private void getGroupList(String communityCode, Pager pager)
    {
        httpUtils = new HttpUtils();
        snsLogic.setData(fHandler);
        snsLogic.requestgroupBuyingList(communityCode, pager, httpUtils);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        // TODO Auto-generated method stub
        super.handleMsg(msg);
        switch (msg.what)
        {
            case Constant.GET_DYNAMIC_INFO_LIST_SUCCESS:
                ShareBean shareBean = (ShareBean) msg.obj;
                List<ShareBean> shareBeans = shareBean.getShareBeans();
                if (null != shareBeans && shareBeans.size() > 0)
                {
                    ShareBean sBean = shareBeans.get(0);
                    String title = "";
                    title = sBean.getTitle();
                    switch (msg.arg1)
                    {
                        case shaiSH:
                            
                            //                            shaiSHTv.setText(title);
                            shaiSHFl.setTag(sBean);
                            if (null != sBean.getPics()
                                    && sBean.getPics().size() > 0)
                            {
                                String url = Constant.URL_iconUrl
                                        + sBean.getPics().get(0).getPicPath();
                                bitMapUtilSun.display(shaiShIv, url);
                            }
                            
                            break;
                        case jiaoHKj:
                            //                            jiaoHKTv.setText(title);
                            jiaoHKFl.setTag(sBean);
                            if (null != sBean.getPics()
                                    && sBean.getPics().size() > 0)
                            {
                                String url = Constant.URL_iconUrl
                                        + sBean.getPics().get(0).getPicPath();
                                bitMapUtilSun.display(jiaoHKIv, url);
                            }
                            break;
                        default:
                            break;
                    }
                    
                }
                break;
            case Constant.GET_DYNAMIC_INFO_LIST_FAILED:
                switch (msg.arg1)
                {
                    case shaiSH:
                        
                        showToast(getResources().getString(R.string.get_sun_life_failed));
                        break;
                    case jiaoHKj:
                        
                        showToast(getResources().getString(R.string.get_swap_space_failed));
                        break;
                    default:
                        break;
                }
                
                break;
            case Constant.GET_GROUPBUYINGLIST_SUCCESS:
                GroupBuyingListVO vo = (GroupBuyingListVO) msg.obj;
                //                groupBuyBean = (GroupBuyBean) msg.obj;
                //                List<GroupBuyBean> groupBuyBeans = groupBuyBean.getGroupBuyBeans();
                //                if (null != groupBuyBeans && groupBuyBeans.size() > 0)
                //                {
                //                    groupBuyTv.setText(groupBuyBeans.get(0).getContent());
                //                }
                
                break;
            case Constant.GET_GROUPBUYINGLIST_FAILED:
                showToast(getResources().getString(R.string.get_group_buy_failed));
                break;
            default:
                break;
        }
    }
    
    @OnClick(R.id.tvCinema)
    public void tvCinemaClick(View view)
    {
        //        showToast("跳转到社区影院");
        Log.i("-----", "001---跳转到。。。。");
        if (!CHApplication.LOGIN)
        {
            ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
            return;
        }
        
        Intent intenta = new Intent();
        intenta.setClass(getActivity(),
                com.changhong.smarthome.phone.cinema.activity.CinemaMainActivity.class);
        if (getActivity() == null)
        {
            Log.i("-----getActivity", "getActivity-null");
        }
        
        startActivity(intenta);
    }
    
    //    @Override
    //    public void setUserVisibleHint(boolean isVisibleToUser)
    //    {
    //        // TODO Auto-generated method stub
    //        if (isVisibleToUser)
    //        {
    //            //fragment可见时加载数据
    //            if (isFirst)
    //            {
    //                getDynamicList(String.valueOf(Constant.PUBLIC_RANGE),
    //                        Constant.DYNAMIC_TYPE_ID_SHOT,
    //                        shaiSH);
    //                getDynamicList(String.valueOf(Constant.PUBLIC_RANGE),
    //                        Constant.DYNAMIC_TYPE_ID_SECOND,
    //                        jiaoHKj);
    //                isFirst = false;
    //            }
    //            else
    //            {
    //                
    //            }
    //            
    //        }
    //        else
    //        {
    //            //不可见时不执行操作
    //        }
    //        super.setUserVisibleHint(isVisibleToUser);
    //    }
    @OnClick(R.id.tvYellowPages)
    public void tvYellowPagesClick(View view)
    {
        //showToast("跳转到社区黄页");
        Log.i("-----", "001---跳转到。。。。");
        if (!CHApplication.LOGIN)
        {
            ((com.changhong.smarthome.phone.MainActivity) getActivity()).tvLoginClick(null);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), YellowPagesActivity.class);
        if (getActivity() == null)
        {
            Log.i("-----getActivity", "getActivity-null");
        }
        startActivity(intent);
    }
}
