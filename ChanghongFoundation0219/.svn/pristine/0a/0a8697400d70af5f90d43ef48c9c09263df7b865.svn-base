package com.changhong.foundation.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MsgCustomViewPager extends ViewPager
{
    private boolean isScrollable;
    
    public MsgCustomViewPager(Context context)
    {
        super(context);
    }
    
    public MsgCustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (isScrollable == false)
        {
            return false;
        }
        else
        {
            return super.onTouchEvent(ev);
        }
        
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (isScrollable == false)
        {
            return false;
        }
        else
        {
            return super.onInterceptTouchEvent(ev);
        }
        
    }
    
    public boolean isScrollable()
    {
        return isScrollable;
    }
    
    public void setScrollable(boolean isScrollable)
    {
        this.isScrollable = isScrollable;
    }
    
}
