package com.changhong.smarthome.phone.store.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class FragmentAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragments;
    
    private FragmentManager fm;
    
    public FragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }
    
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        // TODO Auto-generated constructor stub
        super(fm);
        this.fragments = fragments;
        this.fm = fm;
    }
    
    public void setFragments(List<Fragment> fragments)
    {
        if (this.fragments != null)
        {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments)
            {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }
    
    @Override
    public Fragment getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return fragments.get(arg0);
    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return fragments.size();
    }
    
}
