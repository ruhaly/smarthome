/**
 * Message.java
 * com.pactera.ch_bedframe.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-3 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.smarthome.phone.foundation.bean;

/**
 * ClassName:Message Function: TODO ADD FUNCTION
 * 
 * @author hanliangru
 * @version
 * @since Ver 1.1
 * @Date 2013-12-3 下午5:31:55
 */
public class MessageInfo
{
    
    private int id;
    
    private String img;
    
    private String content;
    
    private String date;
    
    private String title;
    
    //    1惠民(电子优惠券)
    //    2 物业(公告，通知，催缴费)
    //    3 安防
    //    4 版本信息
    private String msgtype;
    
    //    1 惠民 2 公告 3 通知 4 催缴费   5 安防 6 系统版本
    private int originaltype;
    
    public int getOriginaltype()
    {
        return originaltype;
    }
    
    public void setOriginaltype(int originaltype)
    {
        this.originaltype = originaltype;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getImg()
    {
        return img;
    }
    
    public void setImg(String img)
    {
        this.img = img;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getMsgtype()
    {
        return msgtype;
    }
    
    public void setMsgtype(String msgtype)
    {
        this.msgtype = msgtype;
    }
    
}
