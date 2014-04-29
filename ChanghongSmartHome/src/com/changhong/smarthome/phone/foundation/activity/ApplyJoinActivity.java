package com.changhong.smarthome.phone.foundation.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.logic.ApplyJoinLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * 申请加入界面
 * [功能详细描述]
 * @author hanliangru
 * @version [智慧社区-终端底座, 2014年4月18日]
 */
public class ApplyJoinActivity extends BaseActivity
{
    @ViewInject(R.id.etPhone)
    private EditText etPhone;
    
    private HttpUtils httpUtils;
    
    private ApplyJoinLogic logic;
    
    @Override
    public void initData()
    {
        logic = ApplyJoinLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.apply_join_layout);
        ViewUtils.inject(this);
    }
    
    @OnClick(R.id.btnBind)
    public void btnBindClick(View view)
    {
        requestApply();
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    /**
     * 
     * 申请加入请求
     * [功能详细描述]
     */
    private void requestApply()
    {
        if (checkEditTextIsEmpty())
        {
            return;
        }
        if (etPhone.getText().toString().length() != 11)
        {
            showToast(getString(R.string.check_phone));
            return;
        }
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestApplyJoin(getUser().getUid(),
                etPhone.getText().toString(),
                LoginLogic.getInstance().curCommunity.getCommunityId(),
                httpUtils);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_APPLY_JOIN_SUCCESS:
            {
                if (null != msg.obj)
                {
                    if ((Boolean) msg.obj)
                    {
                        showToast(getString(R.string.apply_join_of_owner_success));
                    }
                    else
                    {
                        showToast(getString(R.string.apply_join_success));
                    }
                }
                
                finish();
                break;
            }
            case MSGWHAT_APPLY_JOIN_FAILED:
            {
                showToast(getString(R.string.apply_join_failed));
                break;
            }
            
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.imgBack)
    public void imgBack(View view)
    {
        finish();
    }
}
