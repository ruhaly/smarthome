package com.changhong.smarthome.phone.store.dialog;

/**
 * 普�?dialog方法
 * 
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;

public class CustomCommonDialog extends Dialog
{
    private Context context;
    
    private ImageView title_icon;
    
    private TextView title_tv, message_tv;
    
    private Button cancel_but, ok_but, ignore_but;
    
    public EditText editText;
    
    public static String textString;
    
    private View.OnClickListener innerListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            
            dismiss();
        }
    };
    
    public CustomCommonDialog(Context context)
    {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.commondialog);
        initView();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    public void setTitle(CharSequence title)
    {
        super.setTitle(title);
        title_tv.setText(title);
    }
    
    public void setTitle(int titleId)
    {
        super.setTitle(titleId);
        title_tv.setText(context.getResources().getText(titleId));
    }
    
    public void setMessage(CharSequence message)
    {
        message_tv.setText(message);
    }
    
    public void setMessage(int resid)
    {
        message_tv.setText(context.getResources().getString(resid));
        
    }
    
    private void initView()
    {
        title_icon = (ImageView) findViewById(R.id.commondialog_title_icon);
        title_tv = (TextView) findViewById(R.id.commondialog_title_tv);
        message_tv = (TextView) findViewById(R.id.commondialog_message_tv);
        cancel_but = (Button) findViewById(R.id.commondialog_cancel_but);
        ok_but = (Button) findViewById(R.id.commondialog_ok_but);
        ignore_but = (Button) findViewById(R.id.commondialog_ignore_but);
        editText = (EditText) findViewById(R.id.edittext_id);
    }
    
    public void setIcon(Bitmap bitmap)
    {
        title_icon.setImageBitmap(bitmap);
        title_icon.setVisibility(View.VISIBLE);
    }
    
    public void setIcon(Drawable drawable)
    {
        title_icon.setImageDrawable(drawable);
    }
    
    public void setIcon(int resId)
    {
        title_icon.setImageDrawable(context.getResources().getDrawable(resId));
    }
    
    //确定按钮
    public void setOnOkButton(String text, View.OnClickListener listener)
    {
        ok_but.setText(text);
        ok_but.setVisibility(View.VISIBLE);
        ok_but.setOnClickListener(listener);
    }
    
    public void setEdittext()
    {
        
        editText.setVisibility(View.VISIBLE);
        
    }
    
    public void setEditInputType(int type)
    {
        editText.setInputType(type);
    }
    
    public void setOnOkButton(String text)
    {
        ok_but.setText(text);
        ok_but.setVisibility(View.VISIBLE);
        ok_but.setOnClickListener(innerListener);
    }
    
    public void setOnOkButton(int text, View.OnClickListener listener)
    {
        ok_but.setText(context.getResources().getText(text));
        ok_but.setVisibility(View.VISIBLE);
        ok_but.setOnClickListener(listener);
    }
    
    public void setOnOkButton(int text)
    {
        ok_but.setText(context.getResources().getText(text));
        ok_but.setVisibility(View.VISIBLE);
        
        ok_but.setOnClickListener(innerListener);
    }
    
    //取消按钮
    public void setOnCancelButton(String text, View.OnClickListener listener)
    {
        cancel_but.setText(text);
        cancel_but.setVisibility(View.VISIBLE);
        cancel_but.setOnClickListener(listener);
    }
    
    public void setOnCancelButton(String text)
    {
        cancel_but.setText(text);
        cancel_but.setVisibility(View.VISIBLE);
        cancel_but.setOnClickListener(innerListener);
    }
    
    public void setOnCancelButton(int text)
    {
        cancel_but.setText(context.getResources().getText(text));
        cancel_but.setVisibility(View.VISIBLE);
        cancel_but.setOnClickListener(innerListener);
    }
    
    public void setOnCancelButton(int text, View.OnClickListener listener)
    {
        cancel_but.setText(context.getResources().getText(text));
        cancel_but.setVisibility(View.VISIBLE);
        cancel_but.setOnClickListener(listener);
    }
    
    //忽略按钮
    public void setNeutralButton(String text, View.OnClickListener listener)
    {
        ignore_but.setText(text);
        ignore_but.setVisibility(View.VISIBLE);
        ignore_but.setOnClickListener(listener);
    }
    
    public void setNeutralButton(String text)
    {
        ignore_but.setText(text);
        ignore_but.setVisibility(View.VISIBLE);
        ignore_but.setOnClickListener(innerListener);
    }
    
    public void setNeutralButton(int text, View.OnClickListener listener)
    {
        ignore_but.setText(context.getResources().getText(text));
        ignore_but.setVisibility(View.VISIBLE);
        ignore_but.setOnClickListener(listener);
    }
    
    public void setNeutralButton(int text)
    {
        ignore_but.setText(context.getResources().getText(text));
        ignore_but.setVisibility(View.VISIBLE);
        ignore_but.setOnClickListener(innerListener);
    }
}
