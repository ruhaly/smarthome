package com.changhong.smarthome.phone.property.activity;

import java.util.List;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.activity.SuperActivity.MewwHandler;
import com.changhong.sdk.baseapi.CHUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.foundation.bean.CallBack;
import com.changhong.smarthome.phone.property.adapter.IntegrationExchangeAdapter;
import com.changhong.smarthome.phone.property.entry.ExchangeVO;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.IntegrationLogic;
import com.lidroid.xutils.BitmapUtils;

/**
 * <功能详细描述>
 * 积分兑换
 * @Copyright: Copyright (c) 2014  
 * @author wangbaocheng
 * @date 2014年4月18日 下午2:29:56
 */
public class IntegrationActivity extends SuperActivity implements
        OnClickListener
{
	private List<ExchangeVO> exchangeList;
    
    LinearLayout myintegrationLayout;
    
    private GridView gridViews;
    
    private ImageView exitImg1;
    
    private TextView tvMyintegration;
    
    private TextView tvtime;
    
    private TextView myExchange;
    
    private IntegrationExchangeAdapter adapter;
    
    private IntegrationLogic logic;
    
    private TextView todaytime;
    
    private TextView myintergrationscore;
    
    private String exchangeCode;
    
    private TextView notshowTextView;
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.INTEGRATION_MAIN_SUCCESSFUL_GET:
                initPage();
                break;
            case HttpAction.GIFT_EXCHANGE_SUCCESS_GET:
            	exchangeCode = logic.exchangeOBJ.getExchangeCode();
            	Log.i("TAG","-----------------"+"成功取得兑换码:"+exchangeCode+"-------------------");
            	showExchangeOKDialog(exchangeCode);
            	break;
        }
        super.handleMsg(msg);
    }
    
    /**
     * 响应后初始化界面
     */
    private void initPage()
    {
    	
    	if(logic.respVO.getPointGiftList().size() > 0){
    		adapter = new IntegrationExchangeAdapter(IntegrationActivity.this,
   	             logic.respVO.getPointGiftList());
    		
    		gridViews.setAdapter(adapter);
    		
    		notshowTextView.setVisibility(View.VISIBLE);
        	gridViews.setVisibility(View.VISIBLE);
    	}else if(logic.respVO.getPointGiftList().size() == 0){
    		showToast("系统当前无兑换礼品");
    	}
        
    	todaytime = (TextView)findViewById(R.id.todaytime);
        myintergrationscore = (TextView)findViewById(R.id.myintergrationscore);
    	if(!"".equals(logic.respVO.getDeadline())){
    		todaytime.setText(logic.respVO.getDeadline());
    	}
        if(logic.respVO.getTotalPoint() != 0){
        	myintergrationscore.setText(logic.respVO.getTotalPoint()+"");
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.integration_main);
        initWidgets();
        
        
        gridViews.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    final int position, long id)
            {
                showExchangeDialog(new CallBack()
                {
                    
                    @Override
                    public void update(Object object)
                    {
                        //sendRequest
                    	logic.requestExchangeInfo(logic.respVO.getPointGiftList().get(position));
                        gridViews.invalidate();
                    }
                },position);
            }
        });
        
    }
    
    private void showExchangeDialog(
            final com.changhong.smarthome.phone.foundation.bean.CallBack callback,int position)
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.integraionexchangeconfirm, null);
        TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvUserName.setText(UserUtils.getUser().getAccount());
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkBoxRulesConfirm);
        
        TextView tvIntegrationPoint =(TextView)view.findViewById(R.id.tvIntegrationPoint);
        //显示兑换该礼品所需积分数
        tvIntegrationPoint.setText(logic.respVO.getPointGiftList().get(position).getPoint()+"");
        
        ImageView img = (ImageView)view.findViewById(R.id.exchangegoodsImg);
        //显示兑换的该礼品图片
        BitmapUtils bitmapUtilsHead = new BitmapUtils(getBaseContext());
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtilsHead.display(img, logic.respVO.getPointGiftList().get(position).getGiftPicUrl());
               
        
       
        btnConfirm.setText(R.string.confirm);
        btnCancel.setText(R.string.cancle);
        View divideView = view.findViewById(R.id.divide);
        final Dialog dialog = getDialog(view, false, R.style.MyDialog);
        dialog.show();
        btnCancel.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        
        btnConfirm.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                
                if (checkbox.isChecked())
                {
                    dialog.dismiss();
                    callback.update(null);
                }
                else
                {
                    showToast("兑换前请先确认你已了解兑换规则.");
                }
                
            }
        });
    }
    
    private void showExchangeOKDialog(String exchangeCode)
    {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.integraionexchangeok, null);
        
        TextView exchangeCodeTextVieW = (TextView)view.findViewById(R.id.exchangeCode);
        exchangeCodeTextVieW.setText(exchangeCode);
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (CHUtils.getScreenWidth(getBaseContext()) * 0.9); // 宽度设置为屏幕的0.6
        dialog.getWindow().setAttributes(p);
        dialog.show();
        
    }
    
    
    /**
     * showDialog 显示正在兑换物品的对话框
     */
    
    private void initWidgets()
    {
        
        gridViews = (GridView) findViewById(R.id.gridviews);
        tvMyintegration = (TextView) findViewById(R.id.myintergrationscore);
        tvtime = (TextView) findViewById(R.id.todaytime);
        myExchange = (TextView) findViewById(R.id.exitImg2);
        myintegrationLayout = (LinearLayout) findViewById(R.id.myintergrationtop);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        notshowTextView = (TextView)findViewById(R.id.notshowTextView);
        logic.setData(mHandler);
        logic.requestIntegrationInfo("");
        showProcessDialog(dismiss);
        exitImg1.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        
        
        myintegrationLayout.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                Log.i(TAG, "---------myintegrationLayout was clicked");
                Intent intent = new Intent(IntegrationActivity.this,
                        IntegrationDetailActivity.class);
                startActivity(intent);
            }
        });
        
        myExchange.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(IntegrationActivity.this,
                        MyIntegrationExchangeActivity.class);
                startActivity(intent);
            }
        });
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
        	logic.stopRequest();
        }
    };
    
    @Override
    public void initData()
    {
        if (null != IntegrationLogic.getInstance())
        {
            logic = IntegrationLogic.getInstance();
        }
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
