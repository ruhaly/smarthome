/**
* 
*@Copyright: Copyright (c) 2014  
* @author yang_jun
*@Date:2014-4-8 下午8:55:05 
*@QQ: 756427684 
*/
package com.changhong.smarthome.phone.sns.bean;

import java.util.List;

/**
 *我发布的详情，跟单人员信息
 */
public class GroupBuyingOrderListVO
{
    public GroupBuyingOrderList data;
    
    public GroupBuyingOrderList getData()
    {
        return data;
    }
    
    public void setData(GroupBuyingOrderList data)
    {
        this.data = data;
    }
    
    public class GroupBuyingOrderList
    {
        private int id;
        
        private String img[];
        
        private List<Orders> orders;
        
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
        
        public List<Orders> getOrders()
        {
            return orders;
        }
        
        public void setOrders(List<Orders> orders)
        {
            this.orders = orders;
        }
        
        public class Orders
        {
            String accountId;
            
            int num;
            
            String phone;
            
            public String getAccountId()
            {
                return accountId;
            }
            
            public void setAccountId(String accountId)
            {
                this.accountId = accountId;
            }
            
            public int getNum()
            {
                return num;
            }
            
            public void setNum(int num)
            {
                this.num = num;
            }
            
            public String getPhone()
            {
                return phone;
            }
            
            public void setPhone(String phone)
            {
                this.phone = phone;
            }
            
        }
        
    }
}
