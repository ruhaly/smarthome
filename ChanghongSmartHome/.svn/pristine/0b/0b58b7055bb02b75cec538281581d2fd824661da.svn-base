package com.changhong.smarthome.phone.store.dialog;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;

public class SingleChoiceListAdapter extends BaseAdapter
{
    
    private Context context;
    
    private ArrayList<String> arrayList;
    
    private LayoutInflater inflater;
    
    private int[] inintIndex;
    
    private int selectedIndex;
    
    public SingleChoiceListAdapter(Context context,
            ArrayList<String> arrayList, int[] perChoiceIndex)
    {
        super();
        this.context = context;
        this.arrayList = arrayList;
        this.inintIndex = perChoiceIndex;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public SingleChoiceListAdapter()
    {
        super();
    }
    
    @Override
    public int getCount()
    {
        
        return arrayList.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        
        return arrayList.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        
        View simpleChoiceListItem = inflater.inflate(R.layout.singlechoice_listview_item,
                null);
        TextView tView = (TextView) simpleChoiceListItem.findViewById(R.id.tv_singlechoice);
        ImageView icon = (ImageView) simpleChoiceListItem.findViewById(R.id.iv_singlechoice);
        tView.setText(arrayList.get(position));
        if (inintIndex[position] == 1)
        {
            icon.setImageResource(R.drawable.radiobutton_check_on);
        }
        else
        {
            icon.setImageResource(R.drawable.radiobutton_check_off);
        }
        return simpleChoiceListItem;
    }
    
    public void setItemChecked(int position)
    {
        for (int i = 0; i < inintIndex.length; i++)
        {
            if (i != position)
            {
                inintIndex[i] = 0;
                
            }
            else
            {
                inintIndex[i] = 1;
            }
            
        }
        notifyDataSetChanged();
    }
    
    public int getSelectedIndex()
    {
        for (int i = 0; i < inintIndex.length; i++)
        {
            
            if (inintIndex[i] == 1)
            {
                selectedIndex = i;
            }
        }
        return selectedIndex;
    }
    
    public void setSelectedIndex(int selectedIndex)
    {
        this.selectedIndex = selectedIndex;
    }
    
}
