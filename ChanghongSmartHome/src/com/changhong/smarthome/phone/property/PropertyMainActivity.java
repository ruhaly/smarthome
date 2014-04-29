package com.changhong.smarthome.phone.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.fragment.ProManageFragment;
import com.changhong.smarthome.phone.property.fragment.ComplaintFragment;
import com.changhong.smarthome.phone.property.fragment.RepairsFragment;

public class PropertyMainActivity extends SuperActivity implements
        OnClickListener
{
    
    //返回按钮
    private ImageView image_back;
    
    //报修页面
    private RepairsFragment repairsFragment;
    
    //投诉页面
    private ComplaintFragment complaintFragment;
    
    //判断投诉还是报修页面标识
    private String bz;
    
    //标题
    private TextView textView1;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.propertymain);
        Intent intent = this.getIntent();
        bz = intent.getStringExtra("bz");
        initViewPage();
    }
    
    private void initViewPage()
    {
        
        image_back = (ImageView) findViewById(R.id.image_back);
        textView1 = (TextView) findViewById(R.id.textView1);
        image_back.setOnClickListener(this);
        if (bz != null)
        {
            /* FragmentManager fragmentManager = getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();*/
            
            if (bz.equals(ProManageFragment.BXBZ))
            {
                textView1.setText(getResources().getString(R.string.repair));
                repairsFragment = new RepairsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contents, repairsFragment)
                        .commit();
            }
            if (bz.equals(ProManageFragment.TSBZ))
            {
                textView1.setText(getResources().getString(R.string.suggestion_box));
                complaintFragment = new ComplaintFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contents, complaintFragment)
                        .commit();
            }
        }
        
    }
 
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.image_back:
                finish();
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
    
}
