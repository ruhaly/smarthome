/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-9 下午4:42:26 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.sns.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;

public class TimerTextView extends TextView implements Runnable
{
    private static final String TAG = "TimerTextView";
    
    private boolean isOver = false;
    
    private TextView goShopBtn;
    private TextView tvh;
    private TextView tvm;
    private TextView tvs;
    
    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    
    private long mhour, mmin, msecond;//天，小时，分钟，秒
            
    private boolean run = false; //是否启动了
    
    public TimerTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TimerTextView);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }
    
    public TimerTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TimerTextView);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }
    
    public TimerTextView(Context context)
    {
        super(context);
    }
    
    /**
     * @param h
     * @param m
     * @param s
     */
    public void setTimes(long h, long m, long s, TextView goShopBtn,TextView tvh,TextView tvm,TextView tvs)
    {
                mhour = h;
                mmin =m;
                msecond = s;
        this.goShopBtn = goShopBtn;
        this.tvh = tvh;
        this.tvm = tvm;
        this.tvs = tvs;
        this.setText(h+":"+m+":"+s);
    }
    
    /**
     * 倒计时计算
     */
    private void ComputeTime()
    {
        msecond--;
        if (msecond < 0)
        {
            mmin--;
            msecond = 59;
            if (mmin < 0)
            {
                mmin = 59;
                mhour--;
                if (mhour < 0)
                {
                    goShopBtn.setBackgroundResource(R.drawable.activity_over);
                    // 倒计时结束
                    mhour = 0;
                    mmin = 0;
                    msecond = 0;
                    isOver = true;
                }
            }
        }
    }
    
    public boolean isRun()
    {
        return run;
    }
    
    public void setRun(boolean run)
    {
        this.run = run;
    }
    
    @Override
    public void run()
    {
        
        //标示已经启动
        run = true;
        ComputeTime();
        tvh.setText(mhour+"");
        tvm.setText(mmin+"");
        tvs.setText(msecond+"");
        if (!isOver)
        {
            postDelayed(this, 1000);
        }
    }
    
}