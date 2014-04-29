package com.changhong.smarthome.phone.cinema.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.changhong.smarthome.phone.R;

/**  
* @author yang_jun
* @date 2014-3-21 下午3:23:31 
*/
public class VideoPayDialog extends Dialog implements
        android.view.View.OnClickListener
{
    private OnCustomDialogListener dialogListener;
    
    private Button onetimepay;
    
    private Button longtimepay;
    
    private Button paycancel;
    
    public VideoPayDialog(Context context, OnCustomDialogListener dialogListener)
    {
        super(context);
        this.dialogListener = dialogListener;
    }
    
    //callback
    
    public interface OnCustomDialogListener
    {
        public void callBack();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_movie_dialog);
        onetimepay = (Button) findViewById(R.id.pay_one_time);
        longtimepay = (Button) findViewById(R.id.pay_long_time);
        paycancel = (Button) findViewById(R.id.pay_cancel);
        setTitle("支付提醒");
        
    }
    
    @Override
    public void onClick(View v)
    {
        VideoPayDialog.this.dismiss();
        dialogListener.callBack();
        
        switch (v.getId())
        {
            case R.id.pay_one_time:
                
                break;
            case R.id.pay_long_time:
                
                break;
            case R.id.pay_cancel:
                
                break;
            
            default:
                break;
        }
    }
    
}
