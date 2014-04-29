package com.changhong.smarthome.phone.foundation.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap; 
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.changhong.sdk.baseapi.HttpUrl;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.logic.GetTwoDimensionCodeLogic;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * 
 * 二维码页面<BR>
 * [功能详细描述]
 * @author yanglina
 * @version [智慧社区-终端底座, 2014年4月16日]
 */
public class TwoDimensionCodeActivity extends BaseActivity
{
    
    private ImageView back_list;
    
    private GetTwoDimensionCodeLogic logic;
    
    private ImageView erweima;
    
    private HttpUtils httpUtil;
    
    private BitmapUtils bitmaputils;
    
    /**
     * 
     * 初始化数据<BR>
     * [功能详细描述]
     * @see com.changhong.sdk.activity.SuperActivity#initData()
     */
    @Override
    public void initData()
    {
        logic = GetTwoDimensionCodeLogic.getInstance();     
    }
    
    /**
     * 
     *初始化界面<BR>
     * [功能详细描述]
     * @param paramBundle
     * @see com.changhong.sdk.activity.SuperActivity#initLayout(android.os.Bundle)
     */
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.two_dimension_code);
        back_list = (ImageView) findViewById(R.id.back_list);
        erweima = (ImageView) findViewById(R.id.erweima);
        
        bitmaputils = new BitmapUtils(getBaseContext());
        bitmaputils.configDefaultLoadingImage(R.drawable.erweima);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.erweima);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        
        back_list.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        requestDate();
    }
    
    /**
     * 
     * 发送请求<BR>
     * [功能详细描述]
     */
    public void requestDate()
    {
        showProcessDialog(dismiss);
        logic.setData(mHandler);
        httpUtil = new HttpUtils();
        int clientCode = 1;
        logic.requestCommunity(clientCode, httpUtil);
    }
    
    /**
     * 设置加载标志
     */
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
     * 处理响应<BR>
     * [功能详细描述]
     * @param msg
     * @see com.changhong.sdk.activity.SuperActivity#handleMsg(android.os.Message)
     */
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_GET_TWODIMENSION_SUCCESS:
            {
                if (!StringUtils.isEmpty(logic.path))
                {
                    String path = HttpUrl.OSS_PIC + logic.path;
                    bitmaputils.display(erweima, path);
                }
                break;
            }
            default:
            {
                break;
            }
        }
        super.handleMsg(msg);
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
}
