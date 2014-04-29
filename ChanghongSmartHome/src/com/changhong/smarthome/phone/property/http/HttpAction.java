package com.changhong.smarthome.phone.property.http;

public class HttpAction
{
    public static final String STATUS_OK = "200";
    
    /*public static String url = "http://172.17.184.214:7777/cbs/msg";*/
    
    //public static String url = "http://172.17.184.137:8080/cbs/msg";
    
//    public static String url_home = "";
    
    public static final int TSUPDATE_SUCCESS_MSGWHAT = 0;
    
    public static final int BXLIST_SUCCESS_MSGWHAT_ALL = 10000;
    
    public static final int BXLIST_SUCCESS_MSGWHAT = 10001;
    
    public static final int BXLIST_SUCCESS_MSGWHAT_W = 10002;
    
    public static final int BXADD_SUCCESS_MSGWHAT = 10003;
    
    public static final int BXUPDATE_SUCCESS_MSGWHAT = 10004;
    
    public static final int BXDELETE_SUCCESS_MSGWHAT = 10005;
    
    public static final int BXDELETE_SUCCESS_MSGWHAT_W = 10006;
    
    public static final int BXDELETEALL_SUCCESS_MSGWHAT = 10007;
    
    public static final int BXTYPE_SUCCESS_MSGWHAT = 10008;
    
    public static final int TSADD_SUCCESS_MSGWHAT = 200001;
    
    public static final int TSDELETEALL_SUCCESS_MSGWHAT = 20002;
    
    public static final int TSDELETE_SUCCESS_MSGWHAT = 20003;
    
    public static final int TSDELETE_SUCCESS_MSGWHAT_W = 20004;
    
    public static final int TSLIST_SUCCESS_MSGWHAT_ALL = 20005;
    
    public static final int TSLIST_SUCCESS_MSGWHAT = 20006;
    
    public static final int TSLIST_SUCCESS_MSGWHAT_W = 20007;
    
    public static final int TSTYPE_SUCCESS_MSGWHAT = 20008;
    
    public static final int HOUSED_SUCCESS_MSGWHAT = 30001;
    
    public static final int HOUSEI_SUCCESS_MSGWHAT = 40001;
    
    public static final int HOUSERENTAL_SUCCESS_MSGWHAT = 50001;
    
    public static final int HOUSEPUT_SUCCESS_MSGWHAT = 60001;
    
    public static final int HOUSECANEL_SUCCESS_MSGWHAT = 70001;
    
    public static final boolean isTest = false;
    
    //积分兑换相关接口
    //积分兑换首页
    public static final String INTEGRATION_EXCHANGE_MAIN = "findPointsExchange";
    
    //积分增加或减少的详情
    public static final String INTEGRATION_DETAIL_CHANGE = "findPointDetail";
    
    //我的兑换记录
    public static final String USER_INTEGRATION_CHANGE = "findExchangeHis";
    
    //礼品兑换
    public static final String GIFT_EXCHANGE = "pointExchange";
    
    //积分成功取得数据状态
    public static final int INTEGRATION_MAIN_SUCCESSFUL_GET = 12101;
    
    //我的积分兑换成功取得数据状态
    public static final int MY_INTEGRATION_EXCHANGE_SUCCESS_GET = 12102;
    
    //礼品兑换成功取得兑换码
    public static final int GIFT_EXCHANGE_SUCCESS_GET = 12103;
    
    //积分详情数据成功取得
    public static final int INTEGRATION_DETAIL_SUCCESS_GET = 12104;
}
