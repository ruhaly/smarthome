
package com.changhong.smarthome.phone.property.entry;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListView;

/**
 * ClassName:CustomViewPager Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-4 上午10:56:54
 */
public class CustomViewPager extends ViewPager {

	private boolean isCanScroll = true;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		if (isCanScroll) {
			super.scrollTo(x, y);
		}
	}
	

	

	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if (v instanceof GridView) {
			return super.canScroll(v, checkV, dx, x, y);
		} else if (v instanceof Gallery || v instanceof ListView) {
			return true;
		}
		return super.canScroll(v, checkV, dx, x, y);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;

	}
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(arg0);
	}
	
}
