package com.changhong.foundation.entity;

import java.util.ArrayList;
import java.util.List;

public class Address
{
    private String id;
    
    private String name;
    
    /**
     * 市列表
     */
    private List<Address> cityList = new ArrayList<Address>();
    
    /**
     * 区列表
     */
    private List<Address> communityList = new ArrayList<Address>();
    
    /**
     * 楼栋列表
     */
    private List<Address> buildingList = new ArrayList<Address>();
    
    /**
     * 单元列表
     */
    private List<Address> unitList = new ArrayList<Address>();
    
    /**
     * 门牌列表
     */
    private List<Address> doorPlateList = new ArrayList<Address>();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public List<Address> getCityList()
    {
        return cityList;
    }
    
    public void setCityList(List<Address> cityList)
    {
        this.cityList = cityList;
    }
    
    public List<Address> getCommunityList()
    {
        return communityList;
    }
    
    public void setCommunityList(List<Address> communityList)
    {
        this.communityList = communityList;
    }
    
    public List<Address> getBuildingList()
    {
        return buildingList;
    }
    
    public void setBuildingList(List<Address> buildingList)
    {
        this.buildingList = buildingList;
    }
    
    public List<Address> getUnitList()
    {
        return unitList;
    }
    
    public void setUnitList(List<Address> unitList)
    {
        this.unitList = unitList;
    }
    
    public List<Address> getDoorPlateList()
    {
        return doorPlateList;
    }
    
    public void setDoorPlateList(List<Address> doorPlateList)
    {
        this.doorPlateList = doorPlateList;
    }
    
}
