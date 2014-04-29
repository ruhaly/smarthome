package com.changhong.smarthome.phone.sns;

import com.changhong.sdk.baseapi.HttpUrl;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-1-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Constant
{
    
    //  test url 
    //        public static String URL_SNS = "http://172.17.184.41:8081/SNS/service";
    //        
    //        public static String URL_httpUrl = "http://172.17.184.51:8081/SNS";
    //        
    //        public static String URL_CBS = "http://172.17.184.150:8080/cbs/msg";
    
    //release environment
    
    public static String URL_SNS = HttpUrl.URL_SNS;
    
    public static String URL_iconUrl = HttpUrl.SNS;
    
    public static String URL_CBS = HttpUrl.URL_CBS;
    
    public static String icon = "/OSS";
    
    public static String URL_service = "/service";
    
    /**
     * 用户名
     */
    public static String NIKENAME = "lovesky";
    
    /**
     * 电话
     */
    public static String TEL = "15231469874";
    
    /**
     * 姓名
     */
    public static String TELNAME = "张三";
    
    /**
     * 用户头像地址
     */
    public static String ICON_URL = "";
    
    /**
     *当前用户所在的社区ID
     */
    public static String COMMUNITYID = "LaQGsv1sRsiNQ0g_Lg4Qpw";
    
    /**
     * 图片圆角处理的度数
     */
    public static int BITMAP_PIXELS = 90;
    
    /**
     * 用来判断是否需要刷新动态
     */
    public static boolean isNeedToRefresh = false;
    
    /**
     * 用来判断是否需要刷新对应index下的动态
     */
    public static boolean isNeedToRefreshPagerList = false;
    
    /**
     * 用来判断是否需要刷新评论列表
     */
    public static boolean isNeedToRefreshReplyList = false;
    
    /**
     * 用来判断是否需要刷新二手信息的详情
     */
    public static boolean isNeedToRefreshBusiness = false;
    
    /**
     * 用来判断是否需要强制更新Viewpager标签显示
     */
    public static boolean isNeedToUpdatePagerView = false;
    
    /**
     * 用来判断是否需要刷新好友列表
     */
    public static boolean isNeedToRefreshFriendList = true;
    
    /**
     * 分享类型
     */
    //    public static List<DynamicBean> dynamicBeans = new ArrayList<DynamicBean>();
    
    /**
     * 求助ID--1
     */
    public static final String DYNAMIC_TYPE_ID_HELP = "tcode_01";
    
    /**
     * 二手ID--2。交换空间
     */
    public static final String DYNAMIC_TYPE_ID_SECOND = "tcode_02";
    
    /**
     * 随笔ID--3
     */
    public static final String DYNAMIC_TYPE_ID_ESSAY = "tcode_03";
    
    /**
     * 随拍ID--4，晒生活
     */
    public static final String DYNAMIC_TYPE_ID_SHOT = "tcode_04";
    
    /**
     * 意见ID--5
     */
    public static final String DYNAMIC_TYPE_ID_OPINION = "tcode_05";
    
    /**
     * 活动ID--6,团购
     */
    public static final String DYNAMIC_TYPE_ID_ACTIVITY = "tcode_06";
    
    /**
     * 物业ID--7
     */
    public static final String DYNAMIC_TYPE_ID_PROPERTY = "tcode_07";
    
    /**
     * 转发--1
     */
    public static final String POSTTYPE_FORWARD = "1";
    
    /**
     * 原始信息--0
     */
    public static final String POSTTYPE_ORIGINAL = "0";
    
    /**
     * 公开圈--0
     */
    public static final int PUBLIC_RANGE = 0;
    
    /**
     * 好友圈--1
     */
    public static final int FRIEND_RANGE = 1;
    
    /**
     * 私信--2
     */
    public static final int PRIVATE_RANGE = 2;
    
    /**
     * 删除我分享动态--0
     */
    public static final String DELETE_SHARE = "0";
    
    /**
     * 删除我评论-1
     */
    public static final String DELETE_COMMENT = "1";
    
    /**
     * 删除私信--2
     */
    public static final String DELETE_PRIVATE = "2";
    
    /**
     * 清除待回复好友类型--0
     */
    public static final String DELETE_FRIEND_TYPE_SINGLE = "0";
    
    /**
     * 清除待回复好友类型--1
     */
    public static final String DELETE_FRIEND_TYPE_ALL = "1";
    
    /**
     * 处理好友申请（接受）--0
     */
    public static final String OPERAT_FRIEND_ACCEPT = "0";
    
    /**
     * 处理好友申请（拒绝）--1
     */
    public static final String OPERAT_FRIEND_RESFUSE = "1";
    
    // SNS
    public static final int GET_DYNAMIC_TYPE_SUCCESS = 1000;
    
    public static final int GET_DYNAMIC_TYPE_FAILED = 1001;
    
    public static final int GET_DYNAMIC_INFO_LIST_SUCCESS = 1002;
    
    public static final int GET_DYNAMIC_INFO_LIST_FAILED = 1003;
    
    public static final int SAVE_THEME_SUCCESS = 1004;
    
    public static final int SAVE_THEME_FAILED = 1005;
    
    public static final int SHARE_DETAILS_SUCCESS = 1006;
    
    public static final int SHARE_DETAILS_FAILED = 1007;
    
    public static final int FORWARD_SUCCESS = 1008;
    
    public static final int FORWARD_FAILED = 1009;
    
    public static final int REPLY_SUCCESS = 1010;
    
    public static final int REPLY_FAILED = 1011;
    
    public static final int DELETE_SUCCESS = 1012;
    
    public static final int DELETE_FAILED = 1013;
    
    public static final int GET_COMMENT_LIST_SUCCESS = 1014;
    
    public static final int GET_COMMENT_LIST_FAILED = 1015;
    
    public static final int GET_MODIFYPAY_SUCCESS = 1016;
    
    public static final int GET_MODIFYPAY_FAILED = 1017;
    
    public static final int GET_QUERYPERSONAL_SUCCESS = 1018;
    
    public static final int GET_QUERYPERSONAL_FAILED = 1019;
    
    public static final int GET_SAVEPERSONAL_SUCCESS = 1020;
    
    public static final int GET_SAVEPERSONAL_FAILED = 1021;
    
    public static final int GET_DELETEPERSONAL_SUCCESS = 1022;
    
    public static final int GET_DELETEPERSONAL_FAILED = 1023;
    
    public static final int GET_UPDATETHEME_SUCCESS = 1024;
    
    public static final int GET_UPDATETHEME_FAILED = 1025;
    
    public static final int GET_GROUPBUYINGLIST_SUCCESS = 1026;
    
    public static final int GET_GROUPBUYINGLIST_FAILED = 1027;
    
    public static final int GET_GROUPBUYINGDETAIL_SUCCESS = 1028;
    
    public static final int GET_GROUPBUYINGDETAIL_FAILED = 1029;
    
    public static final int GET_GROUPBUYINGGO_SUCCESS = 1030;
    
    public static final int GET_GROUPBUYINGGO_FAILED = 1031;
    
    public static final int GET_USERRELATION_SUCCESS = 1060;
    
    public static final int GET_USERRELATION_FAILED = 1061;
    
    public static final int GET_COMMUNTYUSERINFO_SUCCESS = 1062;
    
    public static final int GET_COMMUNTYUSERINFO_FAILED = 1063;
    
    public static final int GET_QUERYBYNICKNAME_SUCCESS = 1064;
    
    public static final int GET_QUERYBYNICKNAME_FAILED = 1065;
    
    public static final int GET_QUERYTOREPLYFRIEND_SUCCESS = 1066;
    
    public static final int GET_QUERYTOREPLYFRIEND_FAILED = 1067;
    
    public static final int GET_CREATENEWFRIENDRELATION_SUCCESS = 1068;
    
    /**
     * 已经申请过了
     */
    public static final int GET_CREATENEWFRIENDRELATION_FAILED_REQED = 1069;
    
    /**
     * 已经是好友
     */
    public static final int GET_CREATENEWFRIENDRELATION_FAILED_IS_FRIEND = 1070;
    
    public static final int GET_CREATENEWFRIENDRELATION_FAILED = 1071;
    
    public static final int GET_REPLYNEWFRIENDRELATION_SUCCESS = 1072;
    
    public static final int GET_REPLYNEWFRIENDRELATION_FAILED = 1073;
    
    public static final int GET_DELETEFRIENDRELATION_SUCCESS = 1074;
    
    public static final int GET_DELETEFRIENDRELATION_FAILED = 1075;
    
    public static final int GET_QUERYUSERINFODETAIL_SUCCESS = 1076;
    
    public static final int GET_QUERYUSERINFODETAIL_FAILED = 1077;
    
    public static final int MSG_REPLY = 2000;
    
    public static final int MSG_REFRESH_VIEW = 2001;
    
    public static final int GET_GROUPBUYINGMY_SUCCESS = 2002;
    
    public static final int GET_GROUPBUYINGMY_FAILED = 2003;
    
    public static final int GET_GROUPBUYING_ORDER_LIST_SUCCESS = 2004;
    
    public static final int GET_GROUPBUYING_ORDER_LIST_FAILED = 2005;
    
    //GET_GROUPBUYING__SUCCESS
    public static final int GET_GROUPBUYING_MESSAGE_SUCCESS = 2006;
    
    public static final int GET_GROUPBUYING_MESSAGE_FAILED = 2007;
}
