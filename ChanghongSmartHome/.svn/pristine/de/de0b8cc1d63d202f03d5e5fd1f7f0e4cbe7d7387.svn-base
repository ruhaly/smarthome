package com.changhong.smarthome.phone.sns.adapter;

import java.text.ParseException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.sdk.baseapi.DateUtil;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.lidroid.xutils.BitmapUtils;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-2-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MySharingAdapter extends BaseAdapter
{
    private static final String TAG = "MySharingAdapter";
    
    private Context context;
    
    private List<ShareBean> shareBeans;
    
    private LayoutInflater mInflater;
    
    private BitmapUtils bitmapUtilsPic;
    
    private BitmapUtils bitmapUtilsHead;
    
    private boolean isSwap;
    
    public MySharingAdapter(Context context, List<ShareBean> shareBeans,
            boolean isSwap)
    {
        this.context = context;
        this.shareBeans = shareBeans;
        this.isSwap = isSwap;
        mInflater = LayoutInflater.from(context);
        bitmapUtilsPic = new BitmapUtils(context);
        bitmapUtilsPic.configDefaultLoadingImage(R.drawable.picture);
        bitmapUtilsPic.configDefaultLoadFailedImage(R.drawable.picture);
        bitmapUtilsPic.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtilsHead = new BitmapUtils(context);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.login_new_image);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.login_new_image);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }
    
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return shareBeans.size();
    }
    
    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return shareBeans.get(arg0);
    }
    
    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.my_sharing_item1, null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.date_tv);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic_iv);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon_iv);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content_tv);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_tv);
            viewHolder.divider = (ImageView) convertView.findViewById(R.id.vertical_divider_iv);
            viewHolder.staueOverIv = (ImageView) convertView.findViewById(R.id.staute_over_iv);
            viewHolder.contactTv = (TextView) convertView.findViewById(R.id.contact_tv);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //        if (position == shareBeans.size() - 1)
        //        {
        //            viewHolder.divider.setVisibility(View.GONE);
        //        }
        //        else
        //        {
        //            viewHolder.divider.setVisibility(View.VISIBLE);
        //            
        //        }
        ShareBean shareBean = shareBeans.get(position);
        if (isSwap)
        {
            viewHolder.contactTv.setVisibility(View.VISIBLE);
            String contactString = "";
            if (!StringUtils.isEmpty(shareBean.getBusinessContact()))
            {
                contactString = shareBean.getBusinessContact();
            }
            String tel = "";
            if (!StringUtils.isEmpty(shareBean.getBusinessTel()))
            {
                tel = shareBean.getBusinessTel();
            }
            viewHolder.contactTv.setText(contactString + " " + tel);
        }
        else
        {
            viewHolder.contactTv.setVisibility(View.GONE);
        }
        viewHolder.content.setText(shareBean.getContent());
        if (shareBean.isActive())
        {
            viewHolder.staueOverIv.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.staueOverIv.setVisibility(View.GONE);
        }
        if (null == shareBean.getPics() || shareBean.getPics().size() == 0)
        {
            viewHolder.pic.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.pic.setVisibility(View.VISIBLE);
            String url = Constant.URL_iconUrl
                    + shareBean.getPics().get(0).getPicPath();
            bitmapUtilsPic.display(viewHolder.pic, url);
        }
        try
        {
            String timeY = DateUtil.STANDARD_NO_S_DF.format(DateUtil.STANDARD_NO_S_DF.parse(shareBean.getCreateTime()));
            viewHolder.date.setText(timeY);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String urlIcon = Constant.ICON_URL + shareBean.getCreatorPicUrl();
        bitmapUtilsHead.display(viewHolder.icon, urlIcon);
        viewHolder.title.setText(shareBean.getTitle());
        viewHolder.name.setText(shareBean.getNickName());
        return convertView;
    }
    
    class ViewHolder
    {
        private TextView date;
        
        private ImageView pic;
        
        private ImageView icon;
        
        private ImageView divider;
        
        private ImageView staueOverIv;
        
        private TextView title;
        
        private TextView contactTv;
        
        private TextView name;
        
        private TextView content;
    }
}
