/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-25 上午9:57:41 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.cinema.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.entity.StoreConstant;

/**
* @author yang_jun
*@Date:2014-4-25 上午9:57:41  
* @ClassName: CinemaSuperActivity 
* @Description: TODO(这里用一句话描述这个类的作用)  
*/
public class CinemaSuperActivity extends SuperActivity
{
    
    private EditText seacrhing_park_et;
    
    private PopupWindow searchPopupWindow;
    
    private View searchPopupWindowView;
    
    void showSearchPop(int yoffset, final Context context,
            RelativeLayout exitLayout)
    {
        int screenWidth = getBaseContext().getResources()
                .getDisplayMetrics().widthPixels;
        
        int screenHeight = getBaseContext().getResources()
                .getDisplayMetrics().heightPixels;
        
        if (null != searchPopupWindow)
        {
            searchPopupWindow.dismiss();
        }
        searchPopupWindowView = getLayoutInflater().inflate(R.layout.cinema_search_pop,
                null);
        seacrhing_park_et = (EditText) searchPopupWindowView.findViewById(R.id.seacrhing_park_et);
        if (yoffset > 0)
        {
            seacrhing_park_et.setHeight(yoffset);
        }
        
        searchPopupWindowView.setFocusable(true);
        //)
        searchPopupWindow = new PopupWindow(searchPopupWindowView, screenWidth,
                screenHeight, true);
        searchPopupWindow.setFocusable(true);
        searchPopupWindowView.setOnTouchListener(new OnTouchListener()
        {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (searchPopupWindow != null && searchPopupWindow.isShowing())
                {
                    
                    searchPopupWindow.dismiss();
                    searchPopupWindow = null;
                    Log.d("onTouchPopup", "onTouch");
                }
                
                return false;
            }
        });
        
        if (seacrhing_park_et != null)
        {
            seacrhing_park_et.setOnEditorActionListener(new OnEditorActionListener()
            {
                
                @Override
                public boolean onEditorAction(TextView v, int actionId,
                        KeyEvent event)
                {
                    Log.i(TAG, "--------------001---actionId:" + actionId);
                    String key = seacrhing_park_et.getText().toString();
                    if (key != null && !key.equals(""))
                    {
                        Intent intent = new Intent(context,
                                SearchActivity.class);
                        intent.putExtra(StoreConstant.SEARCHKEY, key);
                        startActivity(intent);
                    }
                    else
                    {
                        showToast(getResources().getString(R.string.park_place_edit_hint));
                    }
                    
                    return false;
                }
            });
            
            Log.i(TAG, "------seacrhing_park_et-1=null");
        }
        else
        {
            Log.i(TAG, "------seacrhing_park_et-=========null");
        }
        searchPopupWindow.showAsDropDown(exitLayout);
    }
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        
    }
    
    @Override
    public void clearData()
    {
        
    }
    
}
