package com.changhong.smarthome.phone.store.logic.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;

/**
 * [展现历史订单的数据结构]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class OrderInfoGroup
{
    public String guoupname;
    
    public List<OrderInfoBean> sList;
    
//    private String disName;

    @SuppressLint("SimpleDateFormat")
    public String getDisName()
    {
        String disName = "";
        if(guoupname != null & !guoupname.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date;
            try
            {
                date = sdf.parse(guoupname);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH) + 1;
                disName = month + "月";
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return disName;
    }
    
    
}
