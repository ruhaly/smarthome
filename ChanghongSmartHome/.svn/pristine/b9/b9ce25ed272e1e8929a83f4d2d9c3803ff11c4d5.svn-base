package com.changhong.smarthome.phone.foundation.baseapi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.changhong.sdk.baseapi.BaseJson;
import com.changhong.sdk.baseapi.NumberUtils;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.entity.BusinessInfo;
import com.changhong.sdk.entity.Room;
import com.changhong.sdk.entity.User;
import com.changhong.sdk.widget.sortlistview.CharacterParser;
import com.changhong.smarthome.phone.foundation.bean.Ad;
import com.changhong.smarthome.phone.foundation.bean.Address;
import com.changhong.smarthome.phone.foundation.bean.BillInfo;
import com.changhong.smarthome.phone.foundation.bean.Community;
import com.changhong.smarthome.phone.foundation.bean.Integral;
import com.changhong.smarthome.phone.foundation.bean.MemberManage;
import com.changhong.smarthome.phone.foundation.bean.MessageInfo;
import com.changhong.smarthome.phone.foundation.bean.PayInfo;
import com.changhong.smarthome.phone.foundation.bean.VersionInfo;
import com.changhong.smarthome.phone.foundation.logic.AccountLogic;
import com.changhong.smarthome.phone.foundation.logic.IntegralLogic;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;

public class JsonParse extends BaseJson
{
    
    @SuppressLint("NewApi")
    public static User parseLoginRes(String response) throws Exception
    {
        User user = new User();
        JSONObject tempJsb = new JSONObject(response);
        List<Community> listCommunity = new ArrayList<Community>();
        if (null != tempJsb)
        {
            JSONObject job = tempJsb.optJSONObject("data");
            JSONObject userJson = job.optJSONObject("userInfo");
            user.setProvinceName(userJson.optString("departmentName"));
            user.setProvinceId(userJson.optString("departmentId"));
            user.setCityName(userJson.optString("cityName"));
            user.setCityId(userJson.optString("cityId"));
            user.setOrganId(userJson.optString("organId"));
            user.setOrganName(userJson.optString("organName"));
            user.setCommuntyId(userJson.optString("communtyId"));
            user.setCommuntyName(userJson.optString("communtyName"));
            user.setBuildingId(userJson.optString("buildingId"));
            user.setBuildingName(userJson.optString("buildingId"));
            user.setUnitId(userJson.optString("unitId"));
            user.setDoorPlateId(userJson.optString("houseNumber"));
            user.setFamilyAccount(userJson.optString("familyAccount"));
            user.setMobile(userJson.optString("mobile"));
            user.setAccount(userJson.optString("userAccount"));
            user.setReallyName(userJson.optString("userName"));
            user.setNickName(userJson.optString("nickName"));
            user.setRole(userJson.optString("userType"));
            user.setUid(userJson.optString("userId"));
            user.setHeadUrl(userJson.optString("photo"));
            user.setIsExist(userJson.optString("isExist"));
            JSONArray jsonArray = job.optJSONArray("BusinessList");
            if (null != jsonArray && jsonArray.length() > 0)
            {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject bsiJson = (JSONObject) jsonArray.opt(i);
                    BusinessInfo bi = new BusinessInfo();
                    bi.setApkId(bsiJson.optString("apkId"));
                    bi.setBusinessCode(bsiJson.optString("businessCode"));
                    bi.setBusinessName(bsiJson.optString("businessName"));
                    bi.setBusinessType(bsiJson.optString("businessType"));
                    bi.setIsAuthority(bsiJson.optString("isAuthority"));
                    bi.setSerialNumber(bsiJson.optString("serialNumber"));
                    bi.setSourceUrl(bsiJson.optString("sourceUrl"));
                    bi.setSpId(bsiJson.optString("spId"));
                    bi.setVersionNo(bsiJson.optString("versionNo"));
                    bi.setPackageName(bsiJson.optString("packagePathName"));
                    bi.setMainActivityPath(bsiJson.optString("packageFullPath"));
                    bi.setIcon(bsiJson.optString("imageUrl"));
                    bi.setSize(bsiJson.optString("packageSize"));
                    user.list.add(bi);
                    user.map.put(bi.getPackageName(), bi);
                }
            }
            JSONArray communityArray = job.optJSONArray("houseList");
            if (null != communityArray && communityArray.length() > 0)
            {
                for (int i = 0; i < communityArray.length(); i++)
                {
                    JSONObject json = (JSONObject) communityArray.opt(i);
                    Community c = new Community();
                    c.setId(json.optString("id"));
                    c.setCommunityId(json.optString("communityId"));
                    c.setName(json.optString("communityName"));
                }
            }
        }
        for (int i = 0; i < listCommunity.size(); i++)
        {
            if (listCommunity.get(i)
                    .getCommunityId()
                    .equals(LoginLogic.getInstance().curCommunity.getCommunityId()))
            {
                listCommunity.get(i).setSelect(true);
            }
        }
        LoginLogic.getInstance().communityList = listCommunity;
        return user;
    }
    
    public static VersionInfo parseCheckUpdateRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return new VersionInfo();
        }
        VersionInfo vi = new VersionInfo();
        try
        {
            JSONObject tempJsb = new JSONObject(response);
            if (null != tempJsb)
            {
                JSONObject job = tempJsb.optJSONObject("data");
                vi.setVersionCode(job.optString("versionCode"));
                vi.setVersionName(job.optString("versionName"));
                vi.setSourceUrl(job.optString("sourceUrl"));
                vi.setCreateTime(job.optString("createTime"));
            }
        }
        catch (Exception e)
        {
            vi = new VersionInfo();
            e.printStackTrace();
        }
        return vi;
    }
    
    public static ArrayList<MessageInfo> parseMsgListRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<MessageInfo> list = new ArrayList<MessageInfo>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("dataList");
                for (int i = 0; i < joa.length(); i++)
                {
                    jsonObject = joa.optJSONObject(i);
                    MessageInfo message = new MessageInfo();
                    message.setId(jsonObject.optInt("msgId"));
                    message.setDate(jsonObject.optString("onlineTime"));
                    message.setImg(jsonObject.optString("picURL"));
                    message.setContent(jsonObject.optString("detail"));
                    message.setTitle(jsonObject.optString("title"));
                    message.setMsgtype(jsonObject.optString("msgType"));
                    message.setOriginaltype(jsonObject.optInt("originalType"));
                    list.add(message);
                }
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<PayInfo> parsePayInfoListRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<PayInfo> list = new ArrayList<PayInfo>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONObject j = job.optJSONObject("data");
                String amount = j.optString("amount");
                AccountLogic.getInstance().amount = StringUtils.isEmpty(amount) ? "0"
                        : amount;
                JSONArray joa = j.optJSONArray("dunningInfoList");
                for (int i = 0; i < joa.length(); i++)
                {
                    jsonObject = joa.optJSONObject(i);
                    PayInfo pi = new PayInfo();
                    pi.setId(String.valueOf(jsonObject.optInt("id")));
                    pi.setMsg(jsonObject.optString("content"));
                    pi.setType(String.valueOf(jsonObject.optInt("type")));
                    pi.setMoney(jsonObject.optString("money"));
                    list.add(pi);
                }
            }
        }
        catch (Exception e)
        {
            list = new ArrayList<PayInfo>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<BillInfo> parseRecentBillRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<BillInfo> list = new ArrayList<BillInfo>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray joa = job.optJSONObject("data")
                        .optJSONArray("resultList");
                for (int i = 0; i < joa.length(); i++)
                {
                    jsonObject = joa.optJSONObject(i);
                    BillInfo bi = new BillInfo();
                    bi.setId(jsonObject.optString("id"));
                    bi.setPaymoney(jsonObject.optString("price"));
                    bi.setConsumerType(jsonObject.optString("consumerType"));
                    bi.setDate(jsonObject.optString("consumerDate"));
                    bi.setContent(jsonObject.optString("consumerContent"));
                    list.add(bi);
                }
            }
        }
        catch (Exception e)
        {
            list = new ArrayList<BillInfo>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<BillInfo> parseHistoryBillRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<BillInfo> list = new ArrayList<BillInfo>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray joa = job.optJSONObject("data")
                        .optJSONArray("resultList");
                for (int i = 0; i < joa.length(); i++)
                {
                    jsonObject = joa.optJSONObject(i);
                    BillInfo bi = new BillInfo();
                    bi.setMonth(jsonObject.optString("month"));
                    JSONArray jsonArray = jsonObject.optJSONArray("list");
                    for (int j = 0; j < jsonArray.length(); j++)
                    {
                        BillInfo bi2 = new BillInfo();
                        JSONObject jobtemp = jsonArray.optJSONObject(j);
                        bi2.setId(String.valueOf(jobtemp.optInt("id")));
                        bi2.setPaymoney(String.valueOf(jobtemp.optInt("price")));
                        bi2.setConsumerType(jobtemp.optString("consumerType"));
                        bi2.setDate(jobtemp.optString("consumerDate"));
                        bi2.setContent(jobtemp.optString("consumerContent"));
                        bi.getList().add(bi2);
                    }
                    list.add(bi);
                }
            }
        }
        catch (Exception e)
        {
            list = new ArrayList<BillInfo>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Ad> parseAdList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return new ArrayList<Ad>();
        }
        ArrayList<Ad> list = new ArrayList<Ad>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONArray("dataList");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Ad ad = new Ad();
                    ad.setAdId(jsonObject.optInt("adId"));
                    ad.setTitle(jsonObject.optString("title"));
                    ad.setType(jsonObject.optInt("type"));
                    ad.setContent(jsonObject.optString("content"));
                    ad.setLink(jsonObject.optString("link"));
                    ad.setUrl(jsonObject.optString("url"));
                    ad.setAdDesc(jsonObject.optString("adDesc"));
                    list.add(ad);
                }
            }
        }
        catch (Exception e)
        {
            list = new ArrayList<Ad>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Integral> parseIntegralListRes(String response)
    {
        ArrayList<Integral> list = new ArrayList<Integral>();
        if (StringUtils.isEmpty(response))
        {
            return list;
        }
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                IntegralLogic.getInstance().startTime = job.optString("startTime");
                IntegralLogic.getInstance().endTime = job.optString("endTime");
                JSONArray joa = job.optJSONArray("dataList");
                for (int i = 0; i < joa.length(); i++)
                {
                    jsonObject = joa.optJSONObject(i);
                    Integral integral = new Integral();
                    integral.setId(jsonObject.optInt("id"));
                    integral.setName(jsonObject.optString("name"));
                    integral.setDesc(jsonObject.optString("desc"));
                    integral.setPic(jsonObject.optString("picURL"));
                    integral.setIntegral(jsonObject.optInt("score"));
                    list.add(integral);
                }
            }
        }
        catch (Exception e)
        {
            list = new ArrayList<Integral>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Address> parseProvinceList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return new ArrayList<Address>();
        }
        ArrayList<Address> list = new ArrayList<Address>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONArray("provinceList");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Address address = new Address();
                    address.setId(jsonObject.optString("provinceId"));
                    address.setName(jsonObject.optString("provinceName"));
                    address.setSortLetters(CharacterParser.getInstance()
                            .getFirstChar(address.getName()));
                    list.add(address);
                }
            }
        }
        catch (Exception e)
        {
            list = new ArrayList<Address>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Address> parseCityList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<Address> list = new ArrayList<Address>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Address address = new Address();
                    address.setId(jsonObject.optString("cityId"));
                    address.setName(jsonObject.optString("city"));
                    address.setSortLetters(jsonObject.optString("firstLetter"));
                    list.add(address);
                }
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Community> parseCommunityList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<Community> list = new ArrayList<Community>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONObject("data")
                        .optJSONArray("houseInfo");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Community community = new Community();
                    community.setId(jsonObject.optString("id"));
                    community.setCommunityId(jsonObject.optString("communityId"));
                    community.setName(jsonObject.optString("communityName"));
                    list.add(community);
                }
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Community> parseSelectCommunityList(String response)
            throws Exception
    {
        ArrayList<Community> list = new ArrayList<Community>();
        JSONObject job = new JSONObject(response);
        if (null != job)
        {
            JSONArray jsonArray = job.optJSONObject("data")
                    .optJSONArray("houseInfo");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                Community community = new Community();
                community.setId(jsonObject.optString("id"));
                community.setCommunityId(jsonObject.optString("communityId"));
                community.setName(jsonObject.optString("communityName"));
                list.add(community);
            }
        }
        return list;
    }
    
    public static ArrayList<Address> parseZutuanList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<Address> list = new ArrayList<Address>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONArray("groupList");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Address address = new Address();
                    address.setId(jsonObject.optString("groupId"));
                    address.setName(jsonObject.optString("groupName"));
                    list.add(address);
                }
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Address> parseBuildingList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<Address> list = new ArrayList<Address>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONArray("buildingList");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Address address = new Address();
                    address.setId(jsonObject.optString("buildingId"));
                    address.setName(jsonObject.optString("buildingName") + "栋");
                    String unitSum = jsonObject.optString("unitSum");
                    if (!StringUtils.isEmpty(unitSum))
                    {
                        for (int j = 1; j <= Integer.valueOf(unitSum); j++)
                        {
                            Address unit = new Address();
                            unit.setId(j + "");
                            unit.setName(NumberUtils.numberArab2CN(j) + "单元");
                            //                            address.getUnitList().add(unit);
                        }
                    }
                    
                    list.add(address);
                }
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<Address> parseDoorplateList(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ArrayList<Address> list = new ArrayList<Address>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray jsonArray = job.optJSONArray("houseList");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.optJSONObject(i);
                    Address address = new Address();
                    address.setId(jsonObject.optString("houseId"));
                    address.setName(jsonObject.optString("houseName"));
                    list.add(address);
                }
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        return list;
    }
    
    @SuppressLint("NewApi")
    public static User parsePersonInfoRes(String response)
    {
        User user = new User();
        if (StringUtils.isEmpty(response))
        {
            return user;
        }
        try
        {
            JSONObject tempJsb = new JSONObject(response);
            if (null != tempJsb)
            {
                JSONObject job = tempJsb.optJSONObject("data");
                JSONObject userJson = job.optJSONObject("userInfo");
                user.setProvinceName(userJson.optString("departmentName"));
                user.setProvinceId(userJson.optString("departmentId"));
                user.setCityName(userJson.optString("cityName"));
                user.setCityId(userJson.optString("cityId"));
                user.setOrganId(userJson.optString("organId"));
                user.setOrganName(userJson.optString("organName"));
                user.setCommuntyId(userJson.optString("communtyId"));
                user.setCommuntyName(userJson.optString("communtyName"));
                user.setBuildingId(userJson.optString("buildingId"));
                user.setBuildingName(userJson.optString("buildingId"));
                user.setUnitId(userJson.optString("unitId"));
                user.setDoorPlateId(userJson.optString("houseNumber"));
                user.setFamilyAccount(userJson.optString("familyAccount"));
                user.setMobile(userJson.optString("mobile"));
                user.setAccount(userJson.optString("userAccount"));
                user.setReallyName(userJson.optString("userName"));
                user.setNickName(userJson.optString("nickName"));
                user.setRole(userJson.optString("userType"));
                user.setUid(userJson.optString("userId"));
                user.setHeadUrl(userJson.optString("photo"));
                user.setSex(userJson.optString("sex"));
                user.setJoinCheck(userJson.optString("joinCheck"));
                user.setJoinFriend(userJson.optString("userConfig"));
                user.setBirth(userJson.optString("birthday"));
                
            }
        }
        catch (Exception e)
        {
            user = new User();
            e.printStackTrace();
        }
        return user;
    }
    
    public static User parseRegisterRes(String response) throws Exception
    {
        User user = new User();
        JSONObject tempJsb = new JSONObject(response);
        if (null != tempJsb)
        {
            JSONObject job = tempJsb.optJSONObject("userInfo");
            user.setCommuntyId(job.optString("communityId"));
            user.setCommuntyName(job.optString("communtyName"));
            user.setSex(job.optString("sex"));
            user.setAccount(job.optString("userAccount"));
            user.setUid(job.optString("userId"));
            user.setReallyName(job.optString("userName"));
            
        }
        return user;
    }
    
    public static boolean parseApplyJoinRes(String response) throws Exception
    {
        boolean isOwner = false;
        JSONObject tempJsb = new JSONObject(response);
        if (null != tempJsb)
        {
            isOwner = tempJsb.optBoolean("isOwner");
            
        }
        return isOwner;
    }
    
    public static MemberManage parseMembers(String response) throws Exception
    {
        MemberManage mm = new MemberManage();
        JSONObject tempJsb = new JSONObject(response);
        if (null != tempJsb)
        {
            mm.setNewCount(tempJsb.optInt("countApply") + "");
            JSONArray jsonArray = tempJsb.optJSONArray("houseRelationInfoList");
            if (null != jsonArray && jsonArray.length() > 0)
            {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    Room room = new Room();
                    room.setBuildingName(jsonObject.optString("budName"));
                    room.setGroupName(jsonObject.optString("groupName"));
                    room.setHouseName(jsonObject.optString("houseName"));
                    room.setUnitName(jsonObject.optString("unitName"));
                    room.setId(jsonObject.optString("houseId"));
                    JSONArray optJSONArray = jsonObject.optJSONArray("houseRelationUserInfoList");
                    if (null != optJSONArray && optJSONArray.length() > 0)
                    {
                        for (int j = 0; j < optJSONArray.length(); j++)
                        {
                            JSONObject jObject = optJSONArray.optJSONObject(j);
                            User user = new User();
                            user.setApproveBy(jObject.optString("approveBy"));
                            user.setHrId(jObject.optString("hrId"));
                            user.setNickName(jObject.optString("nickName"));
                            user.setUid(jObject.optString("userId"));
                            user.setHeadUrl(jObject.optString("userPhoto"));
                            user.setAccount(jObject.optString("userAccount"));
                            user.setCanPermit(jObject.optString("canPermit"));
                            user.setHasPermission(jObject.optString("hasPermission"));
                            room.userList.add(user);
                        }
                    }
                    mm.listRoom.add(room);
                }
            }
        }
        return mm;
    }
    
    public static MemberManage parseNewMembers(String response)
            throws Exception
    {
        MemberManage mm = new MemberManage();
        JSONObject tempJsb = new JSONObject(response);
        if (null != tempJsb)
        {
            JSONArray optJSONArray = tempJsb.optJSONArray("houseRelationApplyList");
            JSONArray optJSONArray2 = tempJsb.optJSONArray("houseList");
            
            if (null != optJSONArray && optJSONArray.length() > 0)
            {
                for (int i = 0; i < optJSONArray.length(); i++)
                {
                    JSONObject jsonObject = optJSONArray.optJSONObject(i);
                    User user = new User();
                    user.setUid(jsonObject.optInt("applyUserId") + "");
                    user.setMobile(jsonObject.optString("applyUserMobile"));
                    user.setReallyName(jsonObject.optString("applyUserName"));
                    user.setNickName(jsonObject.optString("applyUserNickName"));
                    user.setHeadUrl(jsonObject.optString("applyUserPhoto"));
                    user.setStatus(jsonObject.optString("status"));
                    user.setId(jsonObject.optInt("id"));
                    mm.userList.add(user);
                }
            }
            
            if (null != optJSONArray2 && optJSONArray2.length() > 0)
            {
                for (int j = 0; j < optJSONArray2.length(); j++)
                {
                    JSONObject jsonObject = optJSONArray2.optJSONObject(j);
                    Room room = new Room();
                    room.setBuildingName(jsonObject.optString("budName"));
                    room.setGroupName(jsonObject.optString("groupName"));
                    room.setHouseName(jsonObject.optString("houseName"));
                    room.setUnitName(jsonObject.optString("unitName"));
                    room.setId(jsonObject.optString("houseId"));
                    mm.listRoom.add(room);
                }
            }
        }
        return mm;
    }
    
    public static String parseTwoDimensionCode(String response)
            throws Exception
    {
        
        JSONObject job = new JSONObject(response);
        String path = "";
        if (null != job)
        {
            JSONObject json = job.optJSONObject("data")
                    .optJSONObject("version");
            path = json.optString("qrcodePath");
            
        }
        
        return path;
    }
    
    public static String parseChangeCommunityRes(String response)
            throws Exception
    {
        
        JSONObject job = new JSONObject(response);
        String isexist = "1";
        if (null != job)
        {
            JSONObject json = job.optJSONObject("data");
            isexist = json.optString("ISEXIST");
        }
        return isexist;
    }
    
}
