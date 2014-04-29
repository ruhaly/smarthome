package com.changhong.smarthome.phone.property.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.ServicesItem;
import com.lidroid.xutils.BitmapUtils;

public class BxlistAdapter extends BaseAdapter
{
    private Context context;
    
    private LayoutInflater m_inflater;
    
    private List<ServicesItem> dt;
    
    private int fwzt;
    
    private BitmapUtils bitmapUtilsHead;
    
    
    public BxlistAdapter(Context context, List<ServicesItem> services_bx)
    {
        m_inflater = LayoutInflater.from(context);
        this.context = context;
        this.dt = services_bx;
        bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }

    
    public BxlistAdapter()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public int getCount()
    {
        // TODO Auto-generated method stub
        return dt.size();
    }
    
    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return dt.get(arg0);
    }
    
    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return dt.get(arg0).getFwid();
    }
    
    public void refish()
    {
        this.notifyDataSetChanged();
    }
    
    public void updateData(ArrayList<ServicesItem> data)
    {
        this.dt = data;
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        
        fwzt = dt.get(position).getFwzt();
        BxViewHolder viewholder;
        int progressbar = 0;
        if (convertView == null)
        {
            viewholder = new BxViewHolder();
            TextView textView1 = null;
            TextView textview = null;
            convertView = m_inflater.inflate(R.layout.propertylist_item,
                    parent,
                    false);
            
            viewholder.addImg = (ImageView) convertView.findViewById(R.id.addImg);
            viewholder.content = (TextView) convertView.findViewById(R.id.bx_text);
            viewholder.imageview = (ImageView) convertView.findViewById(R.id.imgview);
            
            if (fwzt == 1)
            {
                
                textView1 = (TextView) convertView.findViewById(R.id.time1);
                
                textview = (TextView) convertView.findViewById(R.id.text1);
            }
            else if (fwzt == 2)
            {
                
                textView1 = (TextView) convertView.findViewById(R.id.time2);
                
                textview = (TextView) convertView.findViewById(R.id.text2);
            }
            else if (fwzt == 3)
            {
                
                textView1 = (TextView) convertView.findViewById(R.id.time3);
                
                textview = (TextView) convertView.findViewById(R.id.text3);
            }
            else
            {
                
                textView1 = (TextView) convertView.findViewById(R.id.time4);
                
                textview = (TextView) convertView.findViewById(R.id.text4);
            }
            viewholder.timeText = textView1;
            viewholder.textview = textview;
            
            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (BxViewHolder) convertView.getTag();
        }
        if (fwzt == 1)
        {
            progressbar = R.drawable.progressbar1;
        }
        else if (fwzt == 2)
        {
            progressbar = R.drawable.progressbar2;
        }
        else if (fwzt == 3)
        {
            progressbar = R.drawable.progressbar3;
            
        }
        else
        {
            progressbar = R.drawable.progressbar4;
            
        }
        
        ServicesItem item = dt.get(position);
        viewholder.content.setText((String) item.getContent());
        viewholder.imageview.setBackgroundResource(progressbar);
        viewholder.timeText.setVisibility(View.VISIBLE);
        String time = item.getStateTimeStr().substring(0, 10);
        String[] temp = time.split("-");
        String month = temp[1];
        if(month.startsWith("0"))
        {
            month = month.substring(1);
        }
        viewholder.timeText.setText(temp[0] + "-"+ month + "-"+ temp[2]);
//        viewholder.timeText.setText(item.getStateTimeStr().subSequence(0, 10));
        viewholder.timeText.setTextSize(TypedValue.COMPLEX_UNIT_PX,16);
        
        viewholder.textview.setTextColor(context.getResources()
                .getColor(R.color.title_text_black));
        if (null != item.getPicStrList())
        {
            if (item.getPicStrList().size() > 0)
            {
                bitmapUtilsHead.display(viewholder.addImg,
                        (String) item.getPicStrList().get(0));
            }
        }
        
        return convertView;
    }

    
}

class BxViewHolder
{
    public TextView content;
    
    public ImageView imagebutton;
    
    public TextView fwid;
    
    public TextView fwzt;
    
    public TextView timeText;
    
    public ImageView imageview;
    
    public TextView textview;
    
    public ImageView addImg;
    //public TextView bx_fwztl;
}
