/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-15 下午2:01:02 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.sns.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *groupBuyingList返回的对象，所有的团购列表
 */
public class GroupBuyingListVO
{
    private List<GroupBuyingList> data = new ArrayList<GroupBuyingListVO.GroupBuyingList>();

    public List<GroupBuyingList> getData()
    {
        return data;
    }

    public void setData(List<GroupBuyingList> data)
    {
        this.data = data;
    }

    public class GroupBuyingList
    {
        String accountid;
        
        String addr;
        
        String community;
        
        String contact;
        
        String createtime;
        
        String descripte;
        
        String endtime;
        
        int hot;//1代表有热度，0没有
        
        int id;
        
        String img;
        
        int num;
        
        int originalcost;
        
        String phone;
        
        int price;
        
        String seller;
        
        String starttime;
        
        String time;
        
        String title;

        public String getAccountid()
        {
            return accountid;
        }

        public void setAccountid(String accountid)
        {
            this.accountid = accountid;
        }

        public String getAddr()
        {
            return addr;
        }

        public void setAddr(String addr)
        {
            this.addr = addr;
        }

        public String getCommunity()
        {
            return community;
        }

        public void setCommunity(String community)
        {
            this.community = community;
        }

        public String getContact()
        {
            return contact;
        }

        public void setContact(String contact)
        {
            this.contact = contact;
        }

        public String getCreatetime()
        {
            return createtime;
        }

        public void setCreatetime(String createtime)
        {
            this.createtime = createtime;
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

        public int getHot()
        {
            return hot;
        }

        public void setHot(int hot)
        {
            this.hot = hot;
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

        public int getNum()
        {
            return num;
        }

        public void setNum(int num)
        {
            this.num = num;
        }

        public int getOriginalcost()
        {
            return originalcost;
        }

        public void setOriginalcost(int originalcost)
        {
            this.originalcost = originalcost;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public int getPrice()
        {
            return price;
        }

        public void setPrice(int price)
        {
            this.price = price;
        }

        public String getSeller()
        {
            return seller;
        }

        public void setSeller(String seller)
        {
            this.seller = seller;
        }

        public String getStarttime()
        {
            return starttime;
        }

        public void setStarttime(String starttime)
        {
            this.starttime = starttime;
        }

        public String getTime()
        {
            return time;
        }

        public void setTime(String time)
        {
            this.time = time;
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
}