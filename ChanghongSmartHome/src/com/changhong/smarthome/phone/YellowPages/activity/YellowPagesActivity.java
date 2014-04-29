package com.changhong.smarthome.phone.YellowPages.activity;

import io.vov.vitamio.utils.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.YellowPages.activity.LineServiceActivity;

/**
 * <功能详细描述>
 * 社区黄页
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:31:11
 */
public class YellowPagesActivity extends Activity implements OnClickListener {
	

	//返回
	private RelativeLayout exitRelative; 
	
	//外卖
	private LinearLayout toflinearlayout;
	
	//物业服务
	private RelativeLayout psreativelayout;
	
	//家政服务
	private RelativeLayout hhsrelativelayout;
	
	//维修
	private RelativeLayout mrelativelayout;
	
	//解锁
	private RelativeLayout urelativelayout;
	
	//疏通
	private RelativeLayout drelativelayout;
	
	//教育培训
	private LinearLayout aetlinearlayout;
	
	//快递
	private RelativeLayout dprelativelayout;
	
	//医院
	private RelativeLayout hrelativelayout;
	
	//汽车服务
	private RelativeLayout bsrelativelayout;
	
	//搬家
	private RelativeLayout mhrealtivelayout;
	
	//出租车电招
	private RelativeLayout trelativelayout;
	
	//其他
	private RelativeLayout osrelativelayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shequ_yellowpages);
		initId();
	}
	
	/*
	 * [功能详细描述]
	 * 初始化参数
	 */
	private void initId(){
		exitRelative = (RelativeLayout)findViewById(R.id.exitRelative);
		toflinearlayout = (LinearLayout)findViewById(R.id.toflinearlayout);
		psreativelayout = (RelativeLayout)findViewById(R.id.psreativelayout);
		hhsrelativelayout = (RelativeLayout)findViewById(R.id.hhsrelativelayout);
		mrelativelayout = (RelativeLayout)findViewById(R.id.mrelativelayout);
		urelativelayout = (RelativeLayout)findViewById(R.id.urelativelayout);
		drelativelayout = (RelativeLayout)findViewById(R.id.drelativelayout);
		aetlinearlayout = (LinearLayout)findViewById(R.id.aetlinearlayout);
		dprelativelayout = (RelativeLayout)findViewById(R.id.dprelativelayout);
		hrelativelayout = (RelativeLayout)findViewById(R.id.hrelativelayout);
		bsrelativelayout = (RelativeLayout)findViewById(R.id.bsrelativelayout);
		mhrealtivelayout = (RelativeLayout)findViewById(R.id.mhrealtivelayout);
		trelativelayout = (RelativeLayout)findViewById(R.id.trelativelayout);
		osrelativelayout = (RelativeLayout)findViewById(R.id.osrelativelayout);
		
		
		exitRelative.setOnClickListener(this);
		toflinearlayout.setOnClickListener(this);
		psreativelayout.setOnClickListener(this);
		hhsrelativelayout.setOnClickListener(this);
		mrelativelayout.setOnClickListener(this);
		urelativelayout.setOnClickListener(this);
		drelativelayout.setOnClickListener(this);
		aetlinearlayout.setOnClickListener(this);
		dprelativelayout.setOnClickListener(this);
		hrelativelayout.setOnClickListener(this);
		bsrelativelayout.setOnClickListener(this);
		mhrealtivelayout.setOnClickListener(this);
		trelativelayout.setOnClickListener(this);
		osrelativelayout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.exitRelative:
			finish();break;
		case R.id.toflinearlayout:
			intent.putExtra("category", "takeoutfood");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.psreativelayout:
			intent.putExtra("category", "propertyService");
            intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
            startActivity(intent);break;
		case R.id.hhsrelativelayout:
			intent.putExtra("category", "householdService");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.mrelativelayout:
			intent.putExtra("category", "maintain");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.urelativelayout:
			intent.putExtra("category", "unlock");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.drelativelayout:
			intent.putExtra("category", "dredge");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.aetlinearlayout:
			intent.putExtra("category", "edutrain");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.dprelativelayout:
			intent.putExtra("category", "dispatch");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.hrelativelayout:
			intent.putExtra("category", "hospital");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.bsrelativelayout:
			intent.putExtra("category", "busservice");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.mhrealtivelayout:
			intent.putExtra("category", "movehouse");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.trelativelayout:
			intent.putExtra("category", "taxi");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
		case R.id.osrelativelayout:
			intent.putExtra("category", "others");
			intent.setClass(YellowPagesActivity.this, LineServiceActivity.class);
			startActivity(intent);break;
        default:break;
		}
	}

}
