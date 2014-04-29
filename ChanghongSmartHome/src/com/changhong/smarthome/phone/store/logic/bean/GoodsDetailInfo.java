package com.changhong.smarthome.phone.store.logic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class GoodsDetailInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String id;//  number  商品ID
//    private String spId;//    string  所属SPID
    private String name;//    string  商品名称
    private List<String> picURL;//  string[]    商品图片列表
    private double originalPrice;// 原始价格
    private double salePrice;//销售价格
    private String desc ;//   string  商品描述
    private int stock;//  string  商品库存
//    private String address;//地址
    
    private SpInfoBean sInfo;
    
    private int score;//商品评分(1,2,3,4,5)
    
    private int waresType;//商品分类 1美食 2电影 3健身 4美容美发 5KTV 6酒店

    
    private int distanceType;//距离类型(暂时定义，以后无效)1.离我最近, 2. 1km, 3. 3km, 4.全城

    private String detail;//商品详细介绍
    
    private String saleRule;//商品使用规范、注意事项等
    private double longitude;//经度
    
    private double latitude;//纬度
    
    public double getOriginalPrice()
    {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice)
    {
        this.originalPrice = originalPrice;
    }

    public double getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(double salePrice)
    {
        this.salePrice = salePrice;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getWaresType()
    {
        return waresType;
    }

    public void setWaresType(int waresType)
    {
        this.waresType = waresType;
    }

    public int getDistanceType()
    {
        return distanceType;
    }

    public void setDistanceType(int distanceType)
    {
        this.distanceType = distanceType;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getSaleRule()
    {
        return saleRule;
    }

    public void setSaleRule(String saleRule)
    {
        this.saleRule = saleRule;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public SpInfoBean getsInfo()
    {
        return sInfo;
    }

    public void setsInfo(SpInfoBean sInfo)
    {
        this.sInfo = sInfo;
    }
    
    
    
    /**
     * [保存到数据库的图片地址  url1,url2,url3]<BR>
     * [功能详细描述]
     * @return
     */
    public String getPicurlByList()
    {
        String picurlByDB = "";
        if (picURL != null && picURL.size() > 0)
        {
            for (int i = 0; i < picURL.size(); i++)
            {
                picurlByDB += (picURL.get(i) + ",");
                
            }
        }
        picurlByDB = picurlByDB.substring(0, picurlByDB.length() - 1);
        Log.d("GoodsDetailInfo", "getPicurlByDB | picurlByDB = " + picurlByDB);
        return picurlByDB;
    }
    
    /**
     * [根据从数据库获取的字段，生成图片list]<BR>
     * [功能详细描述]
     * @param picurlFromDB
     */
    public void setListByDB(String picurlFromDB)
    {
        if (picurlFromDB == null || picurlFromDB.equals(""))
        {
            Log.d("GoodsDetailInfo", "setPicurlByDB | picurlFromDB == NULL ");
            return;
        }
        String[] temp = picurlFromDB.split(",");
        if (temp != null)
        {
            
            List<String> tempList = new ArrayList<String>();
            
            for (int i = 0; i < temp.length; i++)
            {
                tempList.add(temp[i]);
            }
            picURL = tempList;
        }
        
    }
    
//    public String getAddress()
//    {
//        return address;
//    }
//    public void setAddress(String address)
//    {
//        this.address = address;
//    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
//    public String getSpId()
//    {
//        return spId;
//    }
//    public void setSpId(String spId)
//    {
//        this.spId = spId;
//    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public List<String> getPicURL()
    {
        return picURL;
    }
    public void setPicURL(List<String> picURL)
    {
        this.picURL = picURL;
    }
//    public double getActualPrice()
//    {
//        return actualPrice;
//    }
//    public void setActualPrice(double actualPrice)
//    {
//        this.actualPrice = actualPrice;
//    }
    public String getDesc()
    {
        return desc;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    public int getStock()
    {
        return stock;
    }
    public void setStock(int stock)
    {
        this.stock = stock;
    }

}
