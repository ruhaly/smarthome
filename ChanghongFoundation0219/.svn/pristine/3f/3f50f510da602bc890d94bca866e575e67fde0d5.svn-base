/**
 * MainActivity.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-3 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.changhong.foundation.R;
import com.changhong.foundation.fragment.MainParentFragment;
import com.changhong.foundation.fragment.MenuListFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * ClassName:MainActivity Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午1:59:26
 */
public class MainActivity extends BaseActivity
{
    
    private Fragment mContent;
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }
    
    /**
     * 
     * 点击菜单更换不同的界面
     * @param fragment
     */
    public void switchContent(Fragment fragment)
    {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        getSlidingMenu().showContent();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        setSlideMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!getSlidingMenu().isMenuShowing())
            {
                showLogoutDialog();
                return true;
            }
            else
            {
                // moveTaskToBack(true);
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
        
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // set the Above View
        if (paramBundle != null)
            mContent = getSupportFragmentManager().getFragment(paramBundle,
                    "mContent");
        if (mContent == null)
            mContent = new MainParentFragment();
        
        // set the Above View
        setContentView(R.layout.content_frame);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();
        
        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new MenuListFragment())
                .commit();
        
    }
    
    @Override
    public void clearData()
    {
        
    }
}
