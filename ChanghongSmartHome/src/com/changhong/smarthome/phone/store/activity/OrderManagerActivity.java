package com.changhong.smarthome.phone.store.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.adapter.FragmentAdapter;
import com.changhong.smarthome.phone.store.fragment.OrderManagerFragment;
import com.changhong.smarthome.phone.store.fragment.OrderManagerFragment1;
import com.changhong.smarthome.phone.store.logic.MainLogic;

/**
 * [订单管理界面]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class OrderManagerActivity extends SuperActivity implements
        OnClickListener, OnCheckedChangeListener, OnPageChangeListener
{
//    private final static String TAG = "OrderManagerActivity";
    
    private static final int TAB_ALL = 0;
    
    private static final int TAB_SUB = 1;
    
    private MainLogic mainLogic;
    
    private ViewPager viewPager;
    
    private RadioGroup radioGroup;
    
    private RadioButton radiobutton1;
    
    private RadioButton radiobutton2;
    
    private Fragment fragment1;
    
    private Fragment fragment2;
    
    private List<Fragment> list = new ArrayList<Fragment>();
    
    private FragmentAdapter fragmentAdapter;
    
    /**
     * 标题栏
     */
    private StoreTitleItem titleItem;
    
    private int curTab = TAB_ALL;
    
    
   
    private void initListener()
    {
        // TODO Auto-generated method stub
        radioGroup.setOnCheckedChangeListener(this);
//        backButton.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            { 
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    
        
    private void initview()
    {
        titleItem = (StoreTitleItem)findViewById(R.id.ordermanager_main_title);
        // TODO Auto-generated method stub
        viewPager = (ViewPager) findViewById(R.id.vp);
        
        radioGroup = (RadioGroup) findViewById(R.id.radiobutton);
        RelativeLayout.LayoutParams radioGroupParams = (android.widget.RelativeLayout.LayoutParams) radioGroup.getLayoutParams();
        radioGroupParams.height = ((mainLogic.screenHeight * 90) / 1280);
        radioGroupParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        radioGroup.setLayoutParams(radioGroupParams);
        
        radiobutton1 = (RadioButton) findViewById(R.id.radiobutton1);
        radiobutton1.setChecked(true);
        radiobutton2 = (RadioButton) findViewById(R.id.radiobutton2);
        radiobutton2.setChecked(false);
        
        fragment1 = new OrderManagerFragment();
        list.add(fragment1);
        fragment2 = new OrderManagerFragment1();
        list.add(fragment2);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), list);
        
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        // TODO Auto-generated method stub
        switch (checkedId)
        {
            case R.id.radiobutton1:
                curTab = TAB_ALL;
                viewPager.setCurrentItem(curTab);
                break;
            case R.id.radiobutton2:
                curTab = TAB_SUB;
                viewPager.setCurrentItem(curTab);
                ((OrderManagerFragment1)fragment2).findOrders();
                break;
            default:
                break;
        }
        
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onPageScrollStateChanged(int arg0)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onPageSelected(int arg0)
    {
        radiobutton1.setChecked(arg0 == TAB_ALL ? true : false);
        radiobutton2.setChecked(arg0 == TAB_ALL ? false : true);
        curTab = arg0;
        
    }

    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        mainLogic = MainLogic.getInstance(getApplicationContext());
    }

    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.ordermanager);
        initview();
        initListener();
//        findOrders();
    }

    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
   
    
}
