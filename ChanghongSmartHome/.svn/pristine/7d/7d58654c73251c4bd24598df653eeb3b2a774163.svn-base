package com.changhong.smarthome.phone.sns.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.DynamicBean;
import com.changhong.smarthome.phone.sns.bean.GroupBuyBean;
import com.changhong.smarthome.phone.sns.bean.GroupBuyOrderBean;
import com.changhong.smarthome.phone.sns.bean.PrivateMsgBean;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.changhong.smarthome.phone.sns.bean.TSnsReply;
import com.changhong.smarthome.phone.sns.bean.TSnsThemePic;
import com.changhong.smarthome.phone.sns.bean.UserInfoDto;

public class JsonParse
{
    
    /**
     * 解析动态类型
     */
    public static DynamicBean parseDynamicListRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        DynamicBean dBean = new DynamicBean();
        ArrayList<DynamicBean> list = new ArrayList<DynamicBean>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("data");
                dBean.setPrivateLetterNum(job.optInt("counts"));
                if (null != joa)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        jsonObject = joa.optJSONObject(i);
                        DynamicBean dynamicBean = new DynamicBean();
                        dynamicBean.setId(jsonObject.optString("code"));
                        dynamicBean.setName(jsonObject.optString("codeName"));
                        
                        list.add(dynamicBean);
                    }
                }
                
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        dBean.setDynamicBeans(list);
        return dBean;
    }
    
    /**
     * 解析动态列表
     */
    public static ShareBean parseDynamicInfoListRes(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ShareBean sBean = new ShareBean();
        ArrayList<ShareBean> list = new ArrayList<ShareBean>();
        try
        {
            JSONObject job = new JSONObject(response);
            JSONObject jsonObject = null;
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("data");
                sBean.setTotalNum(job.optInt("counts"));
                if (null != joa)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        jsonObject = joa.optJSONObject(i);
                        ShareBean shareBean = new ShareBean();
                        // shareBean.setId(String.valueOf(jsonObject.optInt("id")));
                        shareBean.setNickName(jsonObject.optJSONObject("user")
                                .optString("nickName"));
                        shareBean.setCreatorPicUrl(jsonObject.optJSONObject("user")
                                .optString("photoPath"));
                        shareBean.setCreatorId(jsonObject.optJSONObject("user")
                                .optString("userId"));
                        shareBean.setContent(jsonObject.optString("content"));
                        shareBean.setForwardContent(jsonObject.optString("forwardContent"));
                        shareBean.setCreateTime(jsonObject.optString("createTime"));
                        //                        shareBean.setContent(jsonObject.optString("content"));
                        shareBean.setTitle(jsonObject.optString("title"));
                        shareBean.setCreatorType(jsonObject.optString("creatorType"));
                        shareBean.setReplyCount(jsonObject.optInt("replyCount"));
                        shareBean.setForwardCount(jsonObject.optInt("forwardCount"));
                        if (jsonObject.optString("isActive").equals("0"))
                        {
                            shareBean.setActive(true);
                        }
                        else
                        {
                            shareBean.setActive(false);
                        }
                        shareBean.setThemeCodeId(jsonObject.optJSONObject("themeCode")
                                .optString("code"));
                        shareBean.setThemeCodeName(jsonObject.optJSONObject("themeCode")
                                .optString("codeName"));
                        shareBean.setThemeType(jsonObject.optString("themeType"));
                        shareBean.setId(jsonObject.optString("id"));
                        shareBean.setPostType(jsonObject.optString("postType"));
                        List<TSnsThemePic> pics = new ArrayList<TSnsThemePic>();
                        JSONArray jarray = jsonObject.optJSONArray("picList");
                        if (null != jarray)
                        {
                            for (int j = 0; j < jarray.length(); j++)
                            {
                                JSONObject json = jarray.optJSONObject(j);
                                TSnsThemePic pic = new TSnsThemePic();
                                pic.setId(new BigDecimal(json.optString("id")));
                                pic.setPicPath(json.optString("picPath"));
                                pics.add(pic);
                            }
                            shareBean.setPics(pics);
                        }
                        list.add(shareBean);
                    }
                }
                
            }
        }
        catch (Exception e)
        {
            list = null;
            e.printStackTrace();
        }
        sBean.setShareBeans(list);
        return sBean;
    }
    
    /**
     * 解析动态详情
     * @param response
     * @return
     */
    public static ShareBean parseShareDetails(String response)
    {
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        ShareBean sb = new ShareBean();
        JSONObject job;
        try
        {
            job = new JSONObject(response);
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("TSnsTheme");
                if (null != joa && joa.length() > 0)
                {
                    JSONObject jsonObject = joa.optJSONObject(0);
                    
                    sb.setId(jsonObject.optString("id"));
                    sb.setTitle(jsonObject.optString("title"));
                    sb.setCreateTime(jsonObject.optString("createTime"));
                    sb.setForwardContent(jsonObject.optString("forwardContent"));
                    sb.setCreatorType(jsonObject.optString("creatorType"));
                    sb.setContent(jsonObject.optString("content"));
                    sb.setPostType(jsonObject.optString("postType"));
                    //                    sb.setContentLength(Integer.parseInt(jsonObject.optString("contentLength")));
                    
                    sb.setThemeCodeId(jsonObject.optJSONObject("themeCode")
                            .optString("code"));
                    sb.setThemeCodeName(jsonObject.optJSONObject("themeCode")
                            .optString("codeName"));
                    
                    sb.setuLong(jsonObject.optString("ulong"));
                    sb.setuLat(jsonObject.optString("ulat"));
                    sb.setReplyCount(jsonObject.optInt("replyCount"));
                    sb.setBrowseCount(jsonObject.optInt("browseCount"));
                    sb.setForwardCount(jsonObject.optInt("forwardCount"));
                    
                    List<TSnsThemePic> pics = new ArrayList<TSnsThemePic>();
                    JSONArray jarray = jsonObject.optJSONArray("picList");
                    if (null != jarray)
                    {
                        for (int i = 0; i < jarray.length(); i++)
                        {
                            JSONObject json = jarray.optJSONObject(i);
                            TSnsThemePic pic = new TSnsThemePic();
                            pic.setId(new BigDecimal(json.optString("id")));
                            pic.setPicPath(json.optString("picPath"));
                            pics.add(pic);
                        }
                        sb.setPics(pics);
                    }
                    sb.setPostType(jsonObject.optString("postType"));
                    JSONObject jsonObject2 = jsonObject.optJSONObject("detail");
                    sb.setActivityId(jsonObject2.optString("id"));
                    sb.setActivityEndTime(jsonObject2.optString("activityEndTime"));
                    sb.setActivityStartTime(jsonObject2.optString("activityStartTime"));
                    sb.setPlace(jsonObject2.optString("place"));
                    sb.setActivitytel(jsonObject2.optString("tel"));
                    sb.setActivitycontact(jsonObject2.optString("telName"));
                    
                    JSONObject jsonObject3 = jsonObject.optJSONObject("pay");
                    sb.setBusinessId(jsonObject3.optString("id"));
                    sb.setPrice(jsonObject3.optString("price"));
                    sb.setCount(jsonObject3.optString("count"));
                    sb.setBusinessTel(jsonObject3.optString("tel"));
                    sb.setBusinessContact(jsonObject3.optString("telName"));
                    if (jsonObject3.optString("isActive").equals("0"))
                    {
                        sb.setActive(true);
                    }
                    else
                    {
                        sb.setActive(false);
                    }
                    
                    JSONObject jobtemp = jsonObject.optJSONObject("user");
                    sb.setNickName(jobtemp.optString("nickName"));
                    sb.setCreatorId(jobtemp.optString("userId"));
                    sb.setCreatorPicUrl(jobtemp.optString("photoPath"));
                    
                    JSONObject jobtorignal = jsonObject.optJSONObject("initUser");
                    ShareBean orignalBean = new ShareBean();
                    if (null != jobtorignal)
                    {
                        orignalBean.setNickName(jobtorignal.optString("nickName"));
                        orignalBean.setCreatorId(jobtorignal.optString("userId"));
                        orignalBean.setCreatorPicUrl(jobtorignal.optString("photoPath"));
                    }
                    sb.setOriginShareBean(orignalBean);
                    sb.setForwardContent(jsonObject.optString("forwardContent"));
                    sb.setForwardTime(jsonObject.optString("forwardTime"));
                    sb.setForwardReplyCount(jsonObject.optInt("forwardReplyCount"));
                    List<TSnsReply> replies = new ArrayList<TSnsReply>();
                    JSONArray jarray1 = jsonObject.optJSONArray("replyList");
                    if (null != jarray1)
                    {
                        for (int i = 0; i < jarray1.length(); i++)
                        {
                            JSONObject json = jarray1.optJSONObject(i);
                            TSnsReply reply = new TSnsReply();
                            reply.setId(new BigDecimal(json.optString("id")));
                            reply.setThemeId(new BigDecimal(
                                    json.optString("themeId")));
                            reply.setContent(json.optString("content"));
                            reply.setReplyType(json.optString("replyType"));
                            reply.setReplyTime(json.optString("replyTime"));
                            JSONObject userObj = json.optJSONObject("user");
                            UserInfoDto userInfoDto = new UserInfoDto();
                            
                            userInfoDto.setHeadImgPath(userObj.optString("photoPath"));
                            userInfoDto.setNickname(userObj.optString("nickName"));
                            userInfoDto.setUserId(userObj.optString("userId"));
                            reply.setUser(userInfoDto);
                            replies.add(reply);
                        }
                        sb.setReplies(replies);
                    }
                    
                }
            }
        }
        catch (JSONException e)
        {
            sb = null;
            e.printStackTrace();
        }
        return sb;
    }
    
    /**
     * 解析动态评论列表
     * @param response
     * @return
     */
    public static ShareBean parseShareComments(String response)
    {
        ShareBean shareBean = new ShareBean();
        List<TSnsReply> replies = new ArrayList<TSnsReply>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        JSONObject job;
        try
        {
            job = new JSONObject(response);
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("Reply");
                shareBean.setCount(job.optString("counts"));
                if (null != joa)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        JSONObject json = joa.optJSONObject(i);
                        TSnsReply reply = new TSnsReply();
                        reply.setId(new BigDecimal(json.optString("id")));
                        reply.setThemeId(new BigDecimal(
                                json.optString("themeId")));
                        reply.setContent(json.optString("content"));
                        reply.setReplyType(json.optString("replyType"));
                        reply.setReplyTime(json.optString("replyTime"));
                        JSONObject userObj = json.optJSONObject("user");
                        UserInfoDto userInfoDto = new UserInfoDto();
                        
                        userInfoDto.setHeadImgPath(userObj.optString("photoPath"));
                        userInfoDto.setNickname(userObj.optString("nickName"));
                        userInfoDto.setUserId(userObj.optString("userId"));
                        reply.setUser(userInfoDto);
                        replies.add(reply);
                    }
                    shareBean.setReplies(replies);
                }
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return shareBean;
        
    }
    
    /**
     * 解析私信列表
     * @param response
     * @return
     */
    public static PrivateMsgBean parsePrivateMsgs(String response)
    {
        PrivateMsgBean privateMsgBean = new PrivateMsgBean();
        List<PrivateMsgBean> privateMsgBeans = new ArrayList<PrivateMsgBean>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        JSONObject job;
        try
        {
            job = new JSONObject(response);
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("data");
                privateMsgBean.setCount(Integer.parseInt(job.optString("counts")));
                if (null != joa)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        JSONObject json = joa.optJSONObject(i);
                        PrivateMsgBean pMsgBean = new PrivateMsgBean();
                        pMsgBean.setPersonalId(json.optString("id"));
                        pMsgBean.setPersonalContent(json.optString("personalContent"));
                        pMsgBean.setPersonalTime(json.optString("personalTime"));
                        
                        JSONObject userObj = json.optJSONObject("user");
                        pMsgBean.setUserName(userObj.optString("nickName"));
                        pMsgBean.setUserId(userObj.optString("userId"));
                        pMsgBean.setUserAddress(userObj.optString("address"));
                        pMsgBean.setUserBirthday(userObj.optString("birthday"));
                        pMsgBean.setUserHeadPath(userObj.optString("photoPath"));
                        pMsgBean.setUserSex(userObj.optString("sex"));
                        pMsgBean.setThemeCode(json.optString("themeCode"));
                        pMsgBean.setThemeId(json.optString("themeId"));
                        //解析回复列表
                        JSONArray jarray1 = json.optJSONArray("reply");
                        List<PrivateMsgBean> replyListBean = new ArrayList<PrivateMsgBean>();
                        if (null != jarray1)
                        {
                            for (int j = 0; j < jarray1.length(); j++)
                            {
                                PrivateMsgBean pMBean = new PrivateMsgBean();
                                JSONObject js = jarray1.optJSONObject(j);
                                pMBean.setPersonalId(js.optString("id"));
                                pMBean.setReplyPersonalId(js.optString("personalId"));
                                pMBean.setPersonalContent(js.optString("replyContent"));
                                pMBean.setPersonalTime(js.optString("replyTime"));
                                pMBean.setUserAddress(js.optString("address"));
                                pMBean.setUserBirthday(js.optString("birthday"));
                                pMBean.setUserHeadPath(js.optString("photoPath"));
                                pMBean.setUserId(js.optString("userId"));
                                pMBean.setUserName(js.optString("nickName"));
                                pMBean.setUserSex(js.optString("sex"));
                                replyListBean.add(pMBean);
                            }
                            pMsgBean.setReplyList(replyListBean);
                        }
                        privateMsgBeans.add(pMsgBean);
                    }
                    privateMsgBean.setAllList(privateMsgBeans);
                }
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return privateMsgBean;
    }
    
    /**
     * 解析团购列表
     * @param response
     * @return
     */
    public static GroupBuyBean parsegroupList(String response)
    {
        GroupBuyBean groupBuyBean = new GroupBuyBean();
        List<GroupBuyBean> groupBuyBeans = new ArrayList<GroupBuyBean>();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        JSONObject job;
        try
        {
            job = new JSONObject(response);
            if (null != job)
            {
                JSONArray joa = job.optJSONArray("data");
                //                groupBuyBean.setTotalNum(Integer.parseInt(job.optString("counts")));
                if (null != joa)
                {
                    for (int i = 0; i < joa.length(); i++)
                    {
                        JSONObject json = joa.optJSONObject(i);
                        GroupBuyBean bean = new GroupBuyBean();
                        bean.setAccountId(json.optString("accountid"));
                        bean.setCommunity(json.optString("community"));
                        bean.setSurplusTime(json.optString("endtime"));
                        bean.setPicUrl(json.optString("img"));
                        bean.setFollowPeopleNum(json.optString("num"));
                        bean.setOrignalPrice(json.optString("originalcost"));
                        bean.setContactTel(json.optString("phone"));
                        bean.setPromotionalPrice(json.optString("price"));
                        bean.setLaunchBusiness(json.optString("seller"));
                        bean.setActivityTime(json.optString("time"));
                        bean.setContent(json.optString("title"));
                        //1代表有热度，0没有
                        if (json.optString("hot").equals("1"))
                        {
                            bean.setHot(true);
                        }
                        else
                        {
                            bean.setHot(false);
                        }
                        if (json.optString("accountid")
                                .equals(UserUtils.getUser().getAccount()))
                        {
                            bean.setMy(true);
                        }
                        else
                        {
                            bean.setMy(false);
                        }
                        
                        groupBuyBeans.add(bean);
                    }
                    groupBuyBean.setGroupBuyBeans(groupBuyBeans);
                }
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return groupBuyBean;
    }
    
    /**
     * 解析团购详情
     * @param response
     * @return
     */
    public static GroupBuyBean parseGroupBuyDetail(String response)
    {
        GroupBuyBean bean = new GroupBuyBean();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        JSONObject job;
        try
        {
            job = new JSONObject(response);
            if (null != job)
            {
                JSONObject json = job.optJSONObject("data");
                if (null != json)
                {
                    bean.setAccountId(json.optString("accountid"));
                    bean.setCommunity(json.optString("community"));
                    bean.setSurplusTime(json.optString("endtime"));
                    bean.setPicUrl(json.optString("img"));
                    bean.setFollowPeopleNum(json.optString("num"));
                    bean.setOrignalPrice(json.optString("originalcost"));
                    bean.setContactTel(json.optString("phone"));
                    bean.setPromotionalPrice(json.optString("price"));
                    bean.setLaunchBusiness(json.optString("seller"));
                    bean.setActivityTime(json.optString("time"));
                    bean.setContent(json.optString("title"));
                    //1代表有热度，0没有
                    if (json.optString("hot").equals("1"))
                    {
                        bean.setHot(true);
                    }
                    else
                    {
                        bean.setHot(false);
                    }
                }
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return bean;
    }
    
    /**
     * 解析团购拼单详情
     * @param response
     * @return
     */
    public static GroupBuyOrderBean parseGroupBuyOrderDetail(String response)
    {
        GroupBuyOrderBean bean = new GroupBuyOrderBean();
        if (StringUtils.isEmpty(response))
        {
            return null;
        }
        JSONObject job;
        try
        {
            job = new JSONObject(response);
            if (null != job)
            {
                JSONObject json = job.optJSONObject("data");
                if (null != json)
                {
                    bean.setHouseNo(json.optString("houseNo"));
                    bean.setId(json.optInt("id"));
                    bean.setOrderNum(json.optString("orderNum"));
                    bean.setPhone(json.optString("phone"));
                    bean.setPrice(json.optString("price"));
                    bean.setTitle(json.optString("title"));
                }
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return bean;
    }
    //    /**
    //     *  解析新的朋友信息返回
    //     */
    //    public static FriendInfoBean parseUserReqFriend(String response)
    //    {
    //        if (StringUtil.isEmpty(response))
    //        {
    //            return null;
    //        }
    //        FriendInfoBean fInfoBean = new FriendInfoBean();
    //        ArrayList<FriendInfoBean> list = new ArrayList<FriendInfoBean>();
    //        try
    //        {
    //            JSONObject job = new JSONObject(response);
    //            
    //            if (null != job)
    //            {
    //                JSONArray joa = job.optJSONArray("friendInfoList");
    //                fInfoBean.setTotalNum(job.optInt("totalCount"));
    //                if (null != joa)
    //                {
    //                    for (int i = 0; i < joa.length(); i++)
    //                    {
    //                        JSONObject jsonObject = joa.optJSONObject(i);
    //                        FriendInfoBean friendInfoBean = new FriendInfoBean();
    //                        friendInfoBean.setFreindId(jsonObject.optString("askUserId"));
    //                        String statue = jsonObject.optString("status");
    //                        if (StringUtil.isNotEmpty(statue))
    //                        {
    //                            //0--没有对好友申请进行处理
    //                            if (statue.equals("0"))
    //                            {
    //                                friendInfoBean.setOperation(false);
    //                            }
    //                            else
    //                            {
    //                                friendInfoBean.setOperation(true);
    //                                //1--已接受
    //                                if (statue.equals("1"))
    //                                {
    //                                    friendInfoBean.setAccept(true);
    //                                }
    //                                //2-已拒绝
    //                                else
    //                                {
    //                                    friendInfoBean.setAccept(false);
    //                                }
    //                            }
    //                        }
    //                        
    //                        friendInfoBean.setNickname(jsonObject.optString("nickName"));
    //                        friendInfoBean.setHeadImgPath(jsonObject.optString("photoPath"));
    //                        list.add(friendInfoBean);
    //                    }
    //                }
    //                
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            //            list = null;
    //            e.printStackTrace();
    //        }
    //        fInfoBean.setFriendInfoBeans(list);
    //        return fInfoBean;
    //    }
    //    
    //    /**
    //     * 解析查询好友信息返回 
    //     */
    //    public static ArrayList<FriendInfoBean> parseUserRelationRsp(String response)
    //    {
    //        if (StringUtil.isEmpty(response))
    //        {
    //            return null;
    //        }
    //        ArrayList<FriendInfoBean> list = new ArrayList<FriendInfoBean>();
    //        try
    //        {
    //            JSONObject job = new JSONObject(response);
    //            JSONObject jsonObject = null;
    //            if (null != job)
    //            {
    //                JSONArray joa = job.optJSONArray("freindInfoList");
    //                
    //                if (null != joa && joa.length() > 0)
    //                {
    //                    for (int i = 0; i < joa.length(); i++)
    //                    {
    //                        jsonObject = joa.optJSONObject(i);
    //                        FriendInfoBean friendInfoBean = new FriendInfoBean();
    //                        friendInfoBean.setFreindId(jsonObject.optString("userId"));
    //                        friendInfoBean.setNickname(jsonObject.optString("nickName"));
    //                        friendInfoBean.setHeadImgPath(jsonObject.optString("photoPath"));
    //                        list.add(friendInfoBean);
    //                    }
    //                    
    //                }
    //                
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            //            list = null;
    //            e.printStackTrace();
    //        }
    //        return list;
    //    }
    //    
    //    /**
    //     * 解析用户所在小区的用户信息，查询其他小区用户信息返回
    //     */
    //    public static FriendInfoBean parseCommuntyUserInfoRsp(String response)
    //    {
    //        if (StringUtil.isEmpty(response))
    //        {
    //            return null;
    //        }
    //        FriendInfoBean fInfoBean = new FriendInfoBean();
    //        ArrayList<FriendInfoBean> list = new ArrayList<FriendInfoBean>();
    //        try
    //        {
    //            JSONObject job = new JSONObject(response);
    //            JSONObject jsonObject = null;
    //            if (null != job)
    //            {
    //                JSONArray joa = job.optJSONArray("userInfoList");
    //                fInfoBean.setTotalNum(job.optInt("totalCount"));
    //                if (null != joa)
    //                {
    //                    for (int i = 0; i < joa.length(); i++)
    //                    {
    //                        jsonObject = joa.optJSONObject(i);
    //                        FriendInfoBean friendInfoBean = new FriendInfoBean();
    //                        friendInfoBean.setFreindId(jsonObject.optString("userId"));
    //                        friendInfoBean.setNickname(jsonObject.optString("nickName"));
    //                        friendInfoBean.setHeadImgPath(jsonObject.optString("photoPath"));
    //                        friendInfoBean.setFriendStatu(false);
    //                        list.add(friendInfoBean);
    //                    }
    //                }
    //                
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            //            list = null;
    //            e.printStackTrace();
    //        }
    //        fInfoBean.setFriendInfoBeans(list);
    //        return fInfoBean;
    //    }
    //    
    //    /**
    //     * 解析搜索好友响应
    //     */
    //    public static FriendInfoBean parseSearchFriendInfoRsp(String response)
    //    {
    //        if (StringUtil.isEmpty(response))
    //        {
    //            return null;
    //        }
    //        FriendInfoBean fInfoBean = new FriendInfoBean();
    //        ArrayList<FriendInfoBean> list = new ArrayList<FriendInfoBean>();
    //        try
    //        {
    //            JSONObject job = new JSONObject(response);
    //            JSONObject jsonObject = null;
    //            if (null != job)
    //            {
    //                JSONArray joa = job.optJSONArray("userInfoList");
    //                fInfoBean.setTotalNum(job.optInt("totalCount"));
    //                if (null != joa)
    //                {
    //                    for (int i = 0; i < joa.length(); i++)
    //                    {
    //                        jsonObject = joa.optJSONObject(i);
    //                        FriendInfoBean friendInfoBean = new FriendInfoBean();
    //                        friendInfoBean.setFreindId(jsonObject.optString("userId"));
    //                        friendInfoBean.setNickname(jsonObject.optString("nickName"));
    //                        friendInfoBean.setHeadImgPath(jsonObject.optString("photoPath"));
    //                        list.add(friendInfoBean);
    //                    }
    //                }
    //                
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            //            list = null;
    //            e.printStackTrace();
    //        }
    //        fInfoBean.setFriendInfoBeans(list);
    //        return fInfoBean;
    //    }
    //    
    //    /**
    //     * 解析好友详细信息，包括所能见到该用户的所有动态的响应
    //     */
    //    public static UserInfoDetailBean parseQueryUserInfoDetailRsp(String response)
    //    {
    //        if (StringUtil.isEmpty(response))
    //        {
    //            return null;
    //        }
    //        UserInfoDetailBean userInfoDetailBean = new UserInfoDetailBean();
    //        
    //        FriendInfoBean userInfo = new FriendInfoBean();
    //        
    //        ArrayList<ThemeInfoBean> themeInfoList = new ArrayList<ThemeInfoBean>();
    //        try
    //        {
    //            JSONObject job = new JSONObject(response);
    //            
    //            if (null != job)
    //            {
    //                JSONObject userJsonObject = job.optJSONObject("userInfo");
    //                userInfo.setStatue(job.optString("isFriends"));
    //                if (null != userJsonObject)
    //                {
    //                    userInfo.setNickname(userJsonObject.optString("nickName"));
    //                    userInfo.setHeadImgPath(userJsonObject.optString("photoPath"));
    //                    userInfo.setBirth(userJsonObject.optString("birthday"));
    //                    userInfo.setSex(userJsonObject.optString("sex"));
    //                    userInfo.setHomeAddress(userJsonObject.optString("address"));
    //                    userInfo.setFreindId(userJsonObject.optString("userId"));
    //                }
    //                userInfoDetailBean.setUserInfo(userInfo);
    //                
    //                JSONArray joa = job.optJSONArray("themeInfoList");
    //                ThemeInfoBean themeInfoBean = null;
    //                if (null != joa)
    //                {
    //                    for (int i = 0; i < joa.length(); i++)
    //                    {
    //                        JSONObject jsonObject = joa.optJSONObject(i);
    //                        themeInfoBean = new ThemeInfoBean();
    //                        themeInfoBean.setThemeId(jsonObject.optString("id"));
    //                        themeInfoBean.setCodeId(jsonObject.optJSONObject("themeCode")
    //                                .optString("code"));
    //                        themeInfoBean.setThemeType(jsonObject.optJSONObject("themeCode")
    //                                .optString("codeName"));
    //                        themeInfoBean.setRange(jsonObject.optString("themeType"));
    //                        themeInfoBean.setThemeTitle(jsonObject.optString("title"));
    //                        themeInfoBean.setPostCount(Integer.parseInt(jsonObject.optString("forwardCount")));
    //                        themeInfoBean.setReplyCount(Integer.parseInt(jsonObject.optString("replyCount")));
    //                        themeInfoBean.setDate(jsonObject.optString("createTime"));
    //                        JSONObject userObject = jsonObject.optJSONObject("user");
    //                        
    //                        themeInfoBean.setUserName(userObject.optString("nickName"));
    //                        themeInfoBean.setIconUrl(userObject.optString("photoPath"));
    //                        themeInfoBean.setUserId(userObject.optString("userId"));
    //                        themeInfoBean.setPostType(userObject.optString("postType"));
    //                        List<TSnsThemePic> pics = new ArrayList<TSnsThemePic>();
    //                        JSONArray jarray = jsonObject.optJSONArray("picList");
    //                        if (null != jarray)
    //                        {
    //                            for (int j = 0; j < jarray.length(); j++)
    //                            {
    //                                JSONObject json = jarray.optJSONObject(j);
    //                                TSnsThemePic pic = new TSnsThemePic();
    //                                pic.setId(new BigDecimal(json.optString("id")));
    //                                pic.setPicPath(json.optString("picPath"));
    //                                pics.add(pic);
    //                            }
    //                            themeInfoBean.setPics(pics);
    //                        }
    //                        themeInfoList.add(themeInfoBean);
    //                    }
    //                }
    //                
    //                userInfoDetailBean.setThemeInfoList(themeInfoList);
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            //            list = null;
    //            e.printStackTrace();
    //        }
    //        return userInfoDetailBean;
    //    }
    //    
}
