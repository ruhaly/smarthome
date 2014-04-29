/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-8 下午4:22:37 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.sns.bean;

import java.util.List;

/**
 *我的团购发布 返回
 */
public class GroupBuyingMyVO
{
    private List<GroupBuyingMy> data;
    
    public List<GroupBuyingMy> getData()
    {
        return data;
    }
    
    public void setData(List<GroupBuyingMy> data)
    {
        this.data = data;
    }
    
    public class GroupBuyingMy
    {
        private String contact;
        
        private String descripte;
        
        private String endtime;
        
        private int id;
        
        private String img[];
        
        private String num;
        
        private int originalcost;
        
        private String phone;
        
        private int price;
        
        private String seller;
        
        public String getNum()
        {
            return num;
        }

        public void setNum(String num)
        {
            this.num = num;
        }

        private String starttime;
        
        private String title;
        
        public String getContact()
        {
            return contact;
        }
        
        public void setContact(String contact)
        {
            this.contact = contact;
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
        
        public int getId()
        {
            return id;
        }
        
        public void setId(int id)
        {
            this.id = id;
        }
        
        public String[] getImg()
        {
            return img;
        }
        
        public void setImg(String[] img)
        {
            this.img = img;
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
