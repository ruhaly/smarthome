package com.changhong.smarthome.phone.sns.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Message;
import android.util.Log;

import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.http.HttpSenderUtils;
import com.changhong.sdk.httpbean.ServiceResponse;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.MediaShareFtpInfo;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.CommentReqBean;
import com.changhong.smarthome.phone.sns.bean.DynamicBean;
import com.changhong.smarthome.phone.sns.bean.ForwardReqBean;
import com.changhong.smarthome.phone.sns.bean.GroupBuyBean;
import com.changhong.smarthome.phone.sns.bean.GroupBuyOrderBean;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingAddVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingListVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingMessageVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingMyVO;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingOrderListVO;
import com.changhong.smarthome.phone.sns.bean.GroupOrderVO;
import com.changhong.smarthome.phone.sns.bean.Pager;
import com.changhong.smarthome.phone.sns.bean.PrivateMsgBean;
import com.changhong.smarthome.phone.sns.bean.ShareBean;
import com.changhong.smarthome.phone.sns.bean.TSnsThemePic;
import com.changhong.smarthome.phone.sns.bean.ThemeSend;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;

/**
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IntShareLogic extends SuperLogic
{
    private static final String TAG = "IntShareLogic";
    
    private HttpHandler<String> httpHanlder;
    
    private static IntShareLogic ins;
    
    public static synchronized IntShareLogic getInstance()
    {
        if (null == ins)
        {
            ins = new IntShareLogic();
        }
        return ins;
    }
    
    /**
     * 获取动态类型请求
     * @param httpUtils
     */
    public void requestGetDynamicType(HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("userId", UserUtils.getUser().getUid());
        fixRequestParams(params,
                serviceInfo,
                "QueryThemeCodeRequest",
                "sc",
                "sc",
                "4100");
        //ACTION_LOGIN没有用到
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.REQUESTID_GET_DYNAMIC_TYPE,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 获取动态信息列表请求
     * @param themeType 判断查看的是公开或者好友圈或者私信动态0--公开圈1--好友圈2--私信
     * @param themeSubType子类型
     * @param pageIndex 获取当前页码数
     * @param pageSize 获取当前分页每页最大显示数
     * @param isShowAll true查询所有的,false 查询我的
     * @param isRefreshPage -1需要刷新，其他不用刷新
     * 
     */
    public void requestGetDynamicInfo(String themeCodeId, int pageIndex,
            int pageSize, boolean isShowAll, boolean isRefreshPage)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("themeCodeId", themeCodeId);
        serviceInfo.put("pageIndex", String.valueOf(pageIndex));
        serviceInfo.put("pageSize", String.valueOf(pageSize));
        serviceInfo.put("themeType", "0");//需求变更，查询所有的
        if (isShowAll)
        {
            serviceInfo.put("userId", "");
        }
        else
        {
            serviceInfo.put("userId", UserUtils.getUser().getAccount());
        }
        int pos;
        if (isRefreshPage)
        {
            pos = -1;
        }
        else
        {
            pos = 0;
        }
        
        fixRequestParams(params,
                serviceInfo,
                "QueryThemeRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.REQUESTID_GET_DYNAMIC_INFO_LIST,
                this,
                false,
                Constant.URL_SNS,
                pos);
    }
    
    /**
     * 发帖请求
     * @param req
     * @param httpUtils
     */
    public void requestSaveTheme(ThemeSend req, HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        Gson gson = new Gson();//初始gson对象
        
        String json = "[" + gson.toJson(req) + "]";
        Log.d(TAG, " requestSaveTheme  json-->" + json);
        serviceInfo.put("TSnsThemeInfo", req);
        
        fixRequestParams(params,
                serviceInfo,
                "SaveThemeRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("SaveThemeRequest",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.REQUESTID_PUB_DYNAMIC,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * @param groupBuyingAddVO2
     * @param httpUtils
     */
    public void requestSaveTheme(GroupBuyingAddVO groupBuyingAddVO2,
            HttpUtils httpUtils)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("publish", groupBuyingAddVO2);
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingAdd",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("ACTION",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtils,
                RequestId.REQUESTID_PUB_DYNAMIC_ADD,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
     * 获取动态详情
     * @param themeId 获取评论的主题动态ID
     * @param codeId 获取主题动态的动态类型ID
     * @param postType 发帖类型(0.原创，1.转发)
     * @param pageIndex  获取当前动态评论的当前的页码数
     * @param pageSize 当前动态评论的每页显示的最大信息数
     * @param replyCount 获取主题动态的评论数
     * @param httpUtil
     */
    public void requestShareDetails(String themeId, String codeId,
            String postType, int pageIndex, int pageSize, String replyCount,
            HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("themeId", themeId);
        serviceInfo.put("codeId", codeId);
        serviceInfo.put("replyCount", replyCount);
        serviceInfo.put("pageIndex", String.valueOf(pageIndex));
        serviceInfo.put("pageSize", String.valueOf(pageSize));
        serviceInfo.put("postType", postType);
        fixRequestParams(params,
                serviceInfo,
                "QueryReplyRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_GET_SHARE_DTAILS,
                this,
                false,
                Constant.URL_SNS,
                -1);
        
    }
    
    /**
     * 转发请求
     * @param req
     * @param httpUtil
     */
    public void requestForward(ForwardReqBean req, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        Gson gson = new Gson();//初始gson对象
        
        String json = "[" + gson.toJson(req) + "]";
        Log.d(TAG, " requestForward   json-->" + json);
        serviceInfo.put("ForwardTheme", json);
        fixRequestParams(params,
                serviceInfo,
                "ForwardThemeRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_FORWARD_DYNAMIC,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 评论请求
     * @param req
     * @param httpUtil
     */
    public void requestComment(CommentReqBean req, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("themeId", req.getThemeId());
        serviceInfo.put("content", req.getContent());
        serviceInfo.put("replyTime", req.getReplyTime());
        serviceInfo.put("creator", req.getCreator());
        //        serviceInfo.put("creatorNickName", req.getCreatorNickName());
        fixRequestParams(params,
                serviceInfo,
                "SaveReplyRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_SAVE_REPLY,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 删除自己发布的动态或者动态下的评论信息
     * @param themeOrReplyId
     * @param delType 0---动态 1---评论 2--私信
     * @param userId
     * @param httpUtil
     */
    public void requestDelete(String themeOrReplyId, String delType,
            HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("themeOrReplyId", themeOrReplyId);
        serviceInfo.put("delType", delType);
        serviceInfo.put("userId", UserUtils.getUser().getUid());
        fixRequestParams(params,
                serviceInfo,
                "DeleteThemeRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_DELETE_THEME,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 请求动态的评论信息
     * @param themeId
     * @param pageSize 
     * @param pageIndex
     * @param httpUtil
     */
    public void requestQueryAllReplyRequest(String themeId, int pageSize,
            int pageIndex, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("themeId", themeId);
        serviceInfo.put("pageSize", pageSize);
        serviceInfo.put("pageIndex", pageIndex);
        fixRequestParams(params,
                serviceInfo,
                "QueryAllReplyRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_QUERYALLREPLYREQUEST,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 请求确认交换
     * @param themeId
     * @param httpUtil
     */
    public void requestModifyPayRequest(String themeId, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("themeId", themeId);
        fixRequestParams(params,
                serviceInfo,
                "ModifyPayRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_MODIFYPAYREQUEST,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 请求私信列表
     * @param pageIndex
     * @param pageSize
     * @param httpUtil
     */
    public void requestQueryPersonalRequest(int pageIndex, int pageSize,
            HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("pageIndex", pageIndex);
        serviceInfo.put("pageSize", pageSize);
        serviceInfo.put("userId", UserUtils.getUser().getUid());
        fixRequestParams(params,
                serviceInfo,
                "QueryPersonalRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_QUERYPERSONALREQUEST,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 请求回复私信
     * @param content 回复的内容
     * @param personalId 私信的ID
     * @param httpUtil
     */
    public void requestSavePersonalRequest(String content, String personalId,
            HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("content", content);
        serviceInfo.put("personalId", personalId);
        serviceInfo.put("userId", UserUtils.getUser().getUid());
        fixRequestParams(params,
                serviceInfo,
                "SavePersonalRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_SAVEPERSONALREQUEST,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 请求删除/清空私信
     * @param type 1---清空 2---删除某一条
     * @param personalId 私信的ID
     * @param httpUtil
     */
    public void requestDeletePersonalRequest(String type, String personalId,
            HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("type", type);
        if (type.equals("1"))
        {
            serviceInfo.put("personalId", personalId);
        }
        else
        {
            serviceInfo.put("userId", UserUtils.getUser().getUid());
        }
        
        fixRequestParams(params,
                serviceInfo,
                "DeletePersonalRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_DELETEPERSONALREQUEST,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 更新二手信息
     * @param title 标题
     * @param content 内容
     * @param themeId 动态ID
     * @param tel 买卖联系人电话
     * @param telName 买卖联系人
     * @param picList 图片的集合
     * @param httpUtil
     */
    public void requestUpdateThemeRequest(String title, String content,
            String themeId, String tel, String telName,
            List<TSnsThemePic> picList, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("title", title);
        serviceInfo.put("content", content);
        serviceInfo.put("themeId", themeId);
        serviceInfo.put("tel", tel);
        serviceInfo.put("telName", telName);
        serviceInfo.put("picList", picList);
        fixRequestParams(params,
                serviceInfo,
                "UpdateThemeRequest",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_UPDATETHEMEREQUEST,
                this,
                false,
                Constant.URL_SNS,
                -1);
    }
    
    /**
     * 团购列表
     * @param communityCode 社区Coed
     * @param pager 请求参数
     * @param httpUtil
     */
    public void requestgroupBuyingList(String communityCode, Pager pager,
            HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("communityCode", communityCode);
        serviceInfo.put("pager", pager);
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingList",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_GROUPBUYINGLIST,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
     * 我发布的拼单，和团购列表差不多
     */
    public void requestGroupBuyingMy(String communityCode, Pager pager)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("accountId", UserUtils.getUser().getAccount());
        serviceInfo.put("pager", pager);
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingMy",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.REQUESTID_GROUPBUYING_MY,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
     * 团购详情
     * @param id 拼单ID
     * @param httpUtil
     */
    public void requestGroupBuyingdetail(int id, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("id", id + "");
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingDetail",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_GROUPBUYINGDETAIL,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
     * 拼单详情
     * @param id 拼单ID
     * @param httpUtil
     */
    public void requestGroupBuyingGo(int id, HttpUtils httpUtil)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("id", id);
        serviceInfo.put("accountId", UserUtils.getUser().getAccount());
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingGo",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                httpUtil,
                RequestId.REQUESTID_GROUPBUYINGGO,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
    * 参加拼单的请求
    */
    public void requestGroupBuyingOrder(GroupOrderVO vo)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("groupOrder", vo);
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingOrder",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.REQUESTID_GROUPBUYING_ORDER,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
     *  拼单的订购信息列表,我发布的团购，查询跟单列表
     * @param id 
     */
    public void requestgroupBuyingOrderList(int id)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("id", id);
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingOrderList",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.REQUESTID_GROUPBUYING_ORDER_LIST,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    //groupBuyingMessage
    public void requestGroupBuyingMessage(int groupId, String content)
    {
        RequestParams params = new RequestParams();
        Map<String, Object> serviceInfo = new HashMap<String, Object>();
        serviceInfo.put("groupId", groupId);
        serviceInfo.put("content", content);
        fixRequestParams(params,
                serviceInfo,
                "groupBuyingMessage",
                "sc",
                "sc",
                "4100");
        httpHanlder = HttpSenderUtils.sendMsgImpl("",
                params,
                HttpSenderUtils.METHOD_POST,
                new HttpUtils(),
                RequestId.REQUESTID_GROUPBUYING_MESSAGE,
                this,
                false,
                Constant.URL_CBS,
                -1);
    }
    
    /**
     * 处理获取动态类型的响应
     * @param response
     */
    private void handleGetDynamicData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                DynamicBean dynamicBeans = JsonParse.parseDynamicListRes(response.getBody()
                        .getParamsString());
                
                if (null != dynamicBeans)
                {
                    message.what = Constant.GET_DYNAMIC_TYPE_SUCCESS;
                    message.obj = dynamicBeans;
                }
                else
                {
                    message.what = Constant.GET_DYNAMIC_TYPE_FAILED;
                    
                }
            }
            else
            {
                message.what = Constant.GET_DYNAMIC_TYPE_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理获取动态信息列表的响应
     * @param response
     */
    private void handleGetDynamicInfoListData(ServiceResponse response, int pos)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                ShareBean shareBeans = JsonParse.parseDynamicInfoListRes(response.getBody()
                        .getParamsString());
                
                if (null != shareBeans)
                {
                    message.what = Constant.GET_DYNAMIC_INFO_LIST_SUCCESS;
                    message.obj = shareBeans;
                    message.arg1 = pos;
                }
                else
                {
                    message.what = Constant.GET_DYNAMIC_INFO_LIST_FAILED;
                    
                }
            }
            //没有数据
            else if (response.getBody().getResult().equals("1"))
            {
                message.what = Constant.GET_DYNAMIC_INFO_LIST_SUCCESS;
                ShareBean shareBean = new ShareBean();
                shareBean.setShareBeans(new ArrayList<ShareBean>());
                message.obj = shareBean;
                message.arg1 = pos;
            }
            else
            {
                message.what = Constant.GET_DYNAMIC_INFO_LIST_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理发帖请求的响应
     * @param response
     */
    private void handleSaveTheme(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0")
                    || response.getBody().getResult().equals("200"))
            {
                
                message.what = Constant.SAVE_THEME_SUCCESS;
            }
            else
            {
                message.what = Constant.SAVE_THEME_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理获取动态详情的响应
     * @param response
     */
    public void handleDetailData(ServiceResponse response)
    {
        ShareBean shareBean = new ShareBean();
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                shareBean = JsonParse.parseShareDetails(response.getBody()
                        .getParamsString());
                if (null != shareBean)
                {
                    message.obj = shareBean;
                    message.what = Constant.SHARE_DETAILS_SUCCESS;
                    
                }
                else
                {
                    message.what = Constant.SHARE_DETAILS_FAILED;
                    
                }
            }
            else
            {
                message.what = Constant.SHARE_DETAILS_FAILED;
                
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理转发请求的响应
     * @param response
     */
    private void handleForwardData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                
                message.what = Constant.FORWARD_SUCCESS;
            }
            else
            {
                message.what = Constant.FORWARD_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理评论请求的响应
     * @param response
     */
    private void handleReplyData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                
                message.what = Constant.REPLY_SUCCESS;
            }
            else
            {
                message.what = Constant.REPLY_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理删除动态或评论请求的响应
     * @param response
     */
    private void handleDeleteData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.DELETE_SUCCESS;
            }
            else
            {
                message.what = Constant.DELETE_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理请求动态评论请求的响应
     * @param response
     */
    private void handleQueryAllReplyRequestData(ServiceResponse response)
    {
        Message message = new Message();
        ShareBean shareBean = new ShareBean();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.GET_COMMENT_LIST_SUCCESS;
                shareBean = JsonParse.parseShareComments(response.getBody()
                        .getParamsString());
                message.obj = shareBean;
            }
            else
            {
                message.what = Constant.GET_COMMENT_LIST_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理确认交换请求的响应
     * @param response
     */
    private void handleModifyPayRequestData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.GET_MODIFYPAY_SUCCESS;
            }
            else
            {
                message.what = Constant.GET_MODIFYPAY_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理私信列表请求的响应
     * @param response
     */
    private void handleQueryPersonalRequestData(ServiceResponse response)
    {
        Message message = new Message();
        PrivateMsgBean privateMsgBean = new PrivateMsgBean();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.GET_QUERYPERSONAL_SUCCESS;
                privateMsgBean = JsonParse.parsePrivateMsgs(response.getBody()
                        .getParamsString());
                message.obj = privateMsgBean;
            }
            else
            {
                message.what = Constant.GET_QUERYPERSONAL_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理回复私信请求的响应
     * @param response
     */
    private void handleSavePersonalRequestData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.GET_SAVEPERSONAL_SUCCESS;
            }
            else
            {
                message.what = Constant.GET_SAVEPERSONAL_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理删除/清空私信请求的响应
     * @param response
     */
    private void handleDeletePersonalRequestData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.GET_DELETEPERSONAL_SUCCESS;
            }
            else
            {
                message.what = Constant.GET_DELETEPERSONAL_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     * 处理更新二手信息请求的响应
     * @param response
     */
    private void handleUpdateThemeRequestData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("0"))
            {
                message.what = Constant.GET_UPDATETHEME_SUCCESS;
            }
            else
            {
                message.what = Constant.GET_UPDATETHEME_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     *团购列表请求的响应
     * @param response
     */
    private void handlegroupBuyingListData(ServiceResponse response)
    {
        Message message = new Message();
        //        GroupBuyBean groupBuyBean = new GroupBuyBean();
        GroupBuyingListVO vo;
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = Constant.GET_GROUPBUYINGLIST_SUCCESS;
                //给对象赋值
                String resp = response.getBody().getParamsString();
                if (resp != null && resp.length() > 0)
                {
                    vo = new Gson().fromJson(resp, GroupBuyingListVO.class);
                    handler.sendEmptyMessage(HttpAction.FTP_UPLOAD_INFO_SUCCESS_GET);
                    message.obj = vo;
                }
                
                //                groupBuyBean = JsonParse.parsegroupList(response.getBody()
                //                        .getParamsString());
                //                message.obj = groupBuyBean;
            }
            else
            {
                message.what = Constant.GET_GROUPBUYINGLIST_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     *团购详情请求的响应
     * @param response
     */
    private void handleGroupBuyingdetailData(ServiceResponse response)
    {
        Message message = new Message();
        GroupBuyBean groupBuyBean = new GroupBuyBean();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = Constant.GET_GROUPBUYINGDETAIL_SUCCESS;
                groupBuyBean = JsonParse.parseGroupBuyDetail(response.getBody()
                        .getParamsString());
                message.obj = groupBuyBean;
            }
            else
            {
                message.what = Constant.GET_GROUPBUYINGDETAIL_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    /**
     *团购拼单详情请求的响应
     * @param response
     */
    private void handleGroupBuyingGoData(ServiceResponse response)
    {
        Message message = new Message();
        GroupBuyOrderBean groupBuyBean = new GroupBuyOrderBean();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = Constant.GET_GROUPBUYINGGO_SUCCESS;
                groupBuyBean = JsonParse.parseGroupBuyOrderDetail(response.getBody()
                        .getParamsString());
                message.obj = groupBuyBean;
            }
            else
            {
                message.what = Constant.GET_GROUPBUYINGGO_FAILED;
            }
            handler.sendMessage(message);
        }
        
    }
    
    //handleGroupBuyingMyData
    private void handleGroupBuyingMyData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = Constant.GET_GROUPBUYINGMY_SUCCESS;
                //                GroupBuyBean groupBuyBean = new GroupBuyBean();
                //                groupBuyBean = JsonParse.parseGroupBuyDetail(response.getBody()
                //                        .getParamsString());
                //                
                GroupBuyingMyVO vo = new Gson().fromJson(response.getBody()
                        .getParamsString(), GroupBuyingMyVO.class);
                message.obj = vo;
            }
            else
            {
                message.what = Constant.GET_GROUPBUYINGMY_FAILED;
            }
            handler.sendMessage(message);
        }
    }
    
    /**
     * @param 我发布报的详情 serviceRes查询跟单的消息
     */
    private void handleGroupBuyingOrderListData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = Constant.GET_GROUPBUYING_ORDER_LIST_SUCCESS;
                GroupBuyingOrderListVO vo = new Gson().fromJson(response.getBody()
                        .getParamsString(),
                        GroupBuyingOrderListVO.class);
                message.obj = vo;
            }
            else
            {
                message.what = Constant.GET_GROUPBUYING_ORDER_LIST_FAILED;
            }
            handler.sendMessage(message);
        }
    }
    
    //handleGroupBuyingMessageData 发送消息的返回
    private void handleGroupBuyingMessageData(ServiceResponse response)
    {
        Message message = new Message();
        if (StringUtils.isNotEmpty(response.getBody().getResult()))
        {
            if (response.getBody().getResult().equals("200"))
            {
                message.what = Constant.GET_GROUPBUYING_MESSAGE_SUCCESS;
                GroupBuyingMessageVO vo = new Gson().fromJson(response.getBody()
                        .getParamsString(),
                        GroupBuyingMessageVO.class);
                message.obj = vo;
            }
            else
            {
                message.what = Constant.GET_GROUPBUYING_MESSAGE_FAILED;
            }
            handler.sendMessage(message);
        }
    }
    
    @Override
    public void handleHttpException(HttpException error, String msg)
    {
        // TODO Auto-generated method stub
        handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
    }
    
    public void handleHttpResponse(ServiceResponse serviceRes, int requestId,
            InputStream is, int pos)
    {
        // TODO Auto-generated method stub
        switch (requestId)
        {
            case RequestId.REQUESTID_GET_DYNAMIC_TYPE:
                handleGetDynamicData(serviceRes);
                break;
            case RequestId.REQUESTID_GET_DYNAMIC_INFO_LIST:
                handleGetDynamicInfoListData(serviceRes, pos);
                break;
            case RequestId.REQUESTID_PUB_DYNAMIC:
                handleSaveTheme(serviceRes);
                break;
            case RequestId.REQUESTID_PUB_DYNAMIC_ADD:
                handleSaveTheme(serviceRes);
                break;
            case RequestId.REQUESTID_GET_SHARE_DTAILS:
                handleDetailData(serviceRes);
                break;
            case RequestId.REQUESTID_FORWARD_DYNAMIC:
                handleForwardData(serviceRes);
                break;
            case RequestId.REQUESTID_SAVE_REPLY:
                handleReplyData(serviceRes);
                break;
            case RequestId.REQUESTID_DELETE_THEME:
                handleDeleteData(serviceRes);
                break;
            case RequestId.REQUESTID_QUERYALLREPLYREQUEST:
                handleQueryAllReplyRequestData(serviceRes);
                break;
            case RequestId.REQUESTID_MODIFYPAYREQUEST:
                handleModifyPayRequestData(serviceRes);
                break;
            case RequestId.REQUESTID_QUERYPERSONALREQUEST:
                handleQueryPersonalRequestData(serviceRes);
                break;
            case RequestId.REQUESTID_SAVEPERSONALREQUEST:
                handleSavePersonalRequestData(serviceRes);
                break;
            case RequestId.REQUESTID_DELETEPERSONALREQUEST:
                handleDeletePersonalRequestData(serviceRes);
                break;
            case RequestId.REQUESTID_UPDATETHEMEREQUEST:
                handleUpdateThemeRequestData(serviceRes);
                break;
            case RequestId.REQUESTID_GROUPBUYINGLIST:
                handlegroupBuyingListData(serviceRes);
                break;
            case RequestId.REQUESTID_GROUPBUYINGDETAIL:
                handleGroupBuyingdetailData(serviceRes);
                break;
            case RequestId.REQUESTID_GROUPBUYINGGO:
                handleGroupBuyingGoData(serviceRes);
                break;
            case RequestId.REQUESTID_GROUPBUYING_MY:
                handleGroupBuyingMyData(serviceRes);
                break;
            case RequestId.REQUESTID_GROUPBUYING_ORDER_LIST:
                handleGroupBuyingOrderListData(serviceRes);
                break;
            case RequestId.REQUESTID_GROUPBUYING_MESSAGE:
                handleGroupBuyingMessageData(serviceRes);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        if (null != httpHanlder)
        {
            httpHanlder.stop();
            httpHanlder = null;
        }
        
    }
}
