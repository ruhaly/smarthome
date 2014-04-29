/**
 * FamilyAccountActiviy.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-4 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.entity.PayInfo;
import com.changhong.foundation.logic.AccountLogic;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.sdk.baseapi.CHUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:FamilyAccountActiviy Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-4 下午3:38:56
 */
public class FamilyAccountActiviy extends BaseActivity
{
    
    public AccountLogic logic;
    
    @ViewInject(R.id.listview)
    public ListView listview;
    
    public Adapter adapter;
    
    @ViewInject(R.id.tv_youhui)
    private TextView tv_youhui;
    
    private HttpUtils httpUtils;
    
    @ViewInject(R.id.tv_amount)
    private TextView tv_amount;
    
    @Override
    public void initData()
    {
        logic = AccountLogic.getInstance();
        // PayInfo pl = new PayInfo();
        // pl.setMsg("您有一笔水费已经到期，请尽快缴纳，金额80元");
        //
        // PayInfo pl2 = new PayInfo();
        // pl2.setMsg("您有一笔水费已经到期，请尽快缴纳，如期未交，需缴纳一定滞纳金，金额80元");
        //
        // logic.plList.add(pl);
        // logic.plList.add(pl2);
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.family_account_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        CHUtils.addUnderlineTextView(tv_youhui);
        initAdapter();
        
        requestPayInfos(true);
    }
    
    public void initAdapter()
    {
        if (null == adapter)
        {
            adapter = new Adapter();
            listview.setAdapter(adapter);
            // listview.setOnItemClickListener(new OnItemClickListener() {
            //
            // @Override
            // public void onItemClick(AdapterView<?> parent, View view, int
            // position, long id) {
            // showPayMethod(position);
            // }
            //
            // });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    DialogInterface.OnDismissListener dismiss1 = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest1();
        }
    };
    
    /**
     * 请求催缴费信息列表
     * 
     * @param showDialog
     *            TODO
     */
    public void requestPayInfos(boolean showDialog)
    {
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        if (showDialog)
            showProcessDialog(dismiss);
        logic.requestPayInfos(LoginLogic.getInstance().user.getAccount(),
                LoginLogic.getInstance().user.getFamilyAccount(),
                httpUtils);
        
    }
    
    /**
     * 发送付款请求到后台
     * 
     * @param position
     */
    public void reqeustPay(int position)
    {
        
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        showProcessDialog(dismiss1);
        logic.requestPaySuccessToSever(LoginLogic.getInstance().user.getAccount(),
                logic.plList.get(position).getId(),
                httpUtils);
        
    }
    
    private Button btn_yinlian;
    
    private Button btn_zhifubao;
    
    private Button btn_shequ;
    
    private Button btn_cancel;
    
    private Button btn_confirm;
    
    private TextView paytip;
    
    private TextView pay_tip2;
    
    private int position;
    
    private TextView tv_cancel;
    
    /**
     * 
      * showPayMethod(弹出支付方式对话框)  *  *void  * @exception  * @since  1.0.0
     */
    public void showPayMethod(int position)
    {
        this.position = position;
        View viewLogin = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.pay_method_layout, null);
        tv_cancel = (TextView) viewLogin.findViewById(R.id.tv_cancel);
        btn_yinlian = (Button) viewLogin.findViewById(R.id.btn_yinlian);
        btn_zhifubao = (Button) viewLogin.findViewById(R.id.btn_zhifubao);
        btn_shequ = (Button) viewLogin.findViewById(R.id.btn_shequ);
        paytip = (TextView) viewLogin.findViewById(R.id.paytip);
        paytip.setText(String.format(getString(R.string.pay_tip),
                logic.plList.get(position).getMoney()));
        btn_yinlian.setOnClickListener(this);
        btn_zhifubao.setOnClickListener(this);
        btn_shequ.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        showDialog(viewLogin, false, R.style.MyDialog);
    }
    
    public void showPayConfirm()
    {
        if (Float.valueOf(logic.plList.get(position).getMoney()) > Float.valueOf(logic.amount))
        {
            showToast(getString(R.string.no_enough_money));
            return;
        }
        View viewLogin = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.pay_confirm_layout, null);
        btn_cancel = (Button) viewLogin.findViewById(R.id.btn_cancel);
        btn_confirm = (Button) viewLogin.findViewById(R.id.btn_confirm);
        pay_tip2 = (TextView) viewLogin.findViewById(R.id.pay_tip2);
        pay_tip2.setText(String.format(getString(R.string.pay_tip2),
                logic.plList.get(position).getMoney()));
        TextView tv = (TextView) viewLogin.findViewById(R.id.tv_amount2);
        tv.setText(null == logic.amount ? "0" : logic.amount
                + getString(R.string.yuan));
        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        showDialog(viewLogin, false, R.style.MyDialog);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_cancel:
            {
                dismissDialog();
                break;
            }
            case R.id.btn_confirm:
            {
                reqeustPay(position);
                break;
            }
            case R.id.btn_yinlian:
            {
                showPayConfirm();
                break;
            }
            case R.id.tv_cancel:
            {
                dismissDialog();
                break;
            }
            case R.id.btn_zhifubao:
            {
                showPayConfirm();
                break;
            }
            case R.id.btn_shequ:
            {
                showPayConfirm();
                break;
            }
            default:
                break;
        }
    }
    
    class Adapter extends BaseAdapter
    {
        
        @Override
        public int getCount()
        {
            return logic.plList.size();
        }
        
        @Override
        public PayInfo getItem(int arg0)
        {
            return logic.plList.get(arg0);
        }
        
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup arg2)
        {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.pay_info_item_layout, null);
                holder = new ViewHolder();
                holder.tvPayinfo = (TextView) convertView.findViewById(R.id.tv_payinfo);
                holder.btn_pay = (TextView) convertView.findViewById(R.id.btn_pay);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvPayinfo.setText(getItem(position).getMsg());
            holder.btn_pay.setTag(position);
            holder.btn_pay.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    int temp = (Integer) v.getTag();
                    showPayMethod(temp);
                    
                }
            });
            return convertView;
        }
        
        class ViewHolder
        {
            TextView tvPayinfo;
            
            TextView btn_pay;
        }
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
    }
    
    @OnClick(R.id.btn_billinfo)
    public void btnBillinfoClick(View view)
    {
        startActivity(new Intent(getBaseContext(), BillInfoActivity.class),
                true);
    }
    
    @OnClick(R.id.frame_yicard)
    public void frameYicardClick(View view)
    {
        Intent intent = new Intent(getBaseContext(), YiCardActivity.class);
        intent.putExtra("amount", null == logic.amount ? "0" : logic.amount);
        startActivity(intent, true);
    }
    
    @OnClick(R.id.frame_integral)
    public void frameIntegralClick(View view)
    {
        startActivity(new Intent(getBaseContext(),
                IntegralExchangeActivity.class), true);
    }
    
    @OnClick(R.id.tv_youhui)
    public void tvYouhuiClick(View view)
    {
        startActivity(new Intent(getBaseContext(), PayPrivilegeActivity.class),
                true);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MsgWhat.MSGWHAT_PAY_INFO_SUCCESS:
            {
                tv_amount.setText(null == logic.amount ? "0" : logic.amount);
                initAdapter();
                break;
            }
            case MsgWhat.MSGWHAT_PAY_TO_SERVER_SUCCESS:
            {
                // logic.plList.remove(position);
                // initAdapter();
                requestPayInfos(false);
                break;
            }
            default:
                break;
        }
        super.handleMsg(msg);
    }
}
