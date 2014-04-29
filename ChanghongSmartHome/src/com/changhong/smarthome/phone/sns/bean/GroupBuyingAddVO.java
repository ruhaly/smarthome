/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-4 下午2:37:10 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.sns.bean;
//发布信息，我的发布
public class GroupBuyingAddVO
{
    String accountId;
    String addr;
    String contact;
    
    String price;//type of String???
    String originalcost;
    
    String descripte;
    String endtime;
    String img[] = new String[4];
    String phone;
    String starttime;
    String title;
    public String getAccountId()
    {
        return accountId;
    }
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
    public String getAddr()
    {
        return addr;
    }
    public void setAddr(String addr)
    {
        this.addr = addr;
    }
    public String getContact()
    {
        return contact;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    public String getOriginalcost()
    {
        return originalcost;
    }
    public void setOriginalcost(String originalcost)
    {
        this.originalcost = originalcost;
    }
    public String getDescripte()
    {
        return descripte;
    }
    public void setDescripte(String descripte)
    {
        this.descripte = descripte;
    }
    public String getEndtime()
    {
        return endtime;
    }
    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }
    public String[] getImg()
    {
        return img;
    }
    public void setImg(String[] img)
    {
        this.img = img;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getStarttime()
    {
        return starttime;
    }
    public void setStarttime(String starttime)
    {
        this.starttime = starttime;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    
}

