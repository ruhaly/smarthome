package com.changhong.smarthome.phone.property.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.property.adapter.IntegrationDetailAdapter;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.IntegrationDetailLogic;
import com.changhong.smarthome.phone.property.logic.IntegrationLogic;

/**
 * <功能详细描述> 积分详情
 * 
 * @Copyright: Copyright (c) 2014
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:30:15
 */
public class IntegrationDetailActivity extends SuperActivity {
	private ImageView userImg;

	private TextView userNameValue;

	private TextView userIntegtationvalue;

	private ListView listView;

	private ImageButton detailRulesButton;

	private IntegrationDetailLogic detailLogic;

	private IntegrationDetailAdapter adapter;

	private RelativeLayout check_detail;

	// 设置查询积分详情：0:查询最近一个月的积分变化详情 1：查询更多的积分变化详情
	private int findFlag;

	@Override
	public void handleMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case HttpAction.INTEGRATION_DETAIL_SUCCESS_GET:
			initPage();
			break;
		}
		super.handleMsg(msg);
	}

	private void initPage() {
		// TODO Auto-generated method stub
		if (detailLogic.respVO.getPointDetailList().size() > 0) {
			adapter = new IntegrationDetailAdapter(
					IntegrationDetailActivity.this,
					detailLogic.respVO.getPointDetailList());
			listView.setAdapter(adapter);
		} else if (detailLogic.respVO.getPointDetailList().size() == 0) {
			showToast("当前无积分明细");
		}

		if (detailLogic.respVO.getTotalPoint() != 0) {
			userIntegtationvalue.setText(detailLogic.respVO.getTotalPoint()
					+ "");
		}
		userNameValue.setText(UserUtils.getUser().getAccount());

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.integrationdetail);
		initWidgets();
	}

	/**
     * 
     */
	private void initWidgets() {
		TextView topTitle = (TextView) findViewById(R.id.intergrationtitletext);
		topTitle.setText(R.string.integration_details);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout1);
		rl.setVisibility(View.GONE);

		userImg = (ImageView) findViewById(R.id.userImg);
		userNameValue = (TextView) findViewById(R.id.userNameValue);
		userIntegtationvalue = (TextView) findViewById(R.id.userIntegtationvalue);

		detailRulesButton = (ImageButton) findViewById(R.id.detailRulesButton);

		listView = (ListView) findViewById(R.id.listViewIntegrationDetail);

		check_detail = (RelativeLayout) findViewById(R.id.check_detail);

		detailLogic.setData(mHandler);

		findFlag = 0;
		detailLogic.requestIntegrationInfo("", findFlag);
		showProcessDialog(dismiss);

		ImageView exitImg1 = (ImageView) findViewById(R.id.exitImg1);

		exitImg1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		check_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findFlag = 1;
				detailLogic.requestIntegrationInfo("", findFlag);
				showProcessDialog(dismiss);
				//check_detail.setVisibility(View.GONE);
			}
		});

		detailRulesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(IntegrationDetailActivity.this,
						IntegrationDetailRulesActivity.class);
				startActivity(intent);
			}
		});

	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			detailLogic.stopRequest();
		}
	};

	@Override
	public void initData() {
		if (null != IntegrationDetailLogic.getInstance()) {
			detailLogic = IntegrationDetailLogic.getInstance();
		}
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

	}

}
