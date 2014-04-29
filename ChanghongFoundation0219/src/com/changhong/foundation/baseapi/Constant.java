/**
 * 
 * 类名称： Constant.java 
 * 作者： hlr 
 * 创建时间： 2012-5-7 下午4:06:28 
 * 版权声明 ： Copyright(C) 2008-2010 RichPeak
 *
 */
package com.changhong.foundation.baseapi;

/**
 * 
 * 
 * 项目名称：CH_foundation 类名称：Constant 类描述： 创建人：b 创建时间：2014年1月14日 上午9:39:40 修改人：b
 * 修改时间：2014年1月14日 上午9:39:40 修改备注：
 * 
 * @version
 * 
 */
public class Constant
{
    
    public static final String TAG = "ch_foundation";
    
    // 是否成功登陆过的 key
    public static final String LOGGED_ON = "logged_on";
    
    public static final String AUTO_LOGIN = "auto_login";
    
    public static final String PROVINCE = "0";
    
    public static final String CITY = "1";
    
    public static final String COMMUNITY = "2";
    
    public static final String ZUTUAN = "3";
    
    public static final String BUILDING = "4";
    
    public static final String UNIT = "5";
    
    public static final String DOORPLATE = "6";
    
    public static final String TYPE_REGISTER_VALICEDE = "1";
    
    public static final String TYPE_FORGET_PWD_VALICEDE = "2";
    
    //终端类型
    public static final int TERMINAL_TYPE = 1;
    
    //终端类型0:手机
    //    1:TV
    //    2:PAD    
    public static final String CLIENTTYPE = "0";
    
    public static final String OTHER_APP_MAIN_ACTIVITY = ".MainActivity";
    
    public static final String PREFIX = "com.changhong";
    
    public static final String CURRENT_PACKAGE = "com.changhong.foundation";
    
    //安装状态 未安装0 已安装1 已卸载2
    public static final String INSTALLED = "1";
    
    //权限
    //0:已购买
    //1:未购买
    //2:免费使用
    public static final String HAS_BUY = "0";
    
    public static final String NO_BUY = "1";
    
    public static final String PLUGIN_BIANMING = "1";
    
    public static final String PLUGIN_GUANGKONG = "2";
    
    public static final String PLUGIN_ANFANG = "3";
    
    public static final String PLUGIN_OTHER = "4";
    
    //0:已购买1:未购买2:免费使用
    public static final String FREE_USE = "2";
    
    public static final String UPDATE_PLUGIN_VIEW = "com.update_plugin_view";
    
    public static final String[] bianmingArray = { "com.changhong.cinema",
            "com.changhong.property", "com.changhong.store",
            "com.changhong.sns" };
    
    public static final String[] guankongArray = { "" };
    
    public static final String[] anfangArray = { "" };
    
    public static final String[] otherArray = { "" };
    
    public static final String apk_install_update = "0";
    
    public static final String apk_uninstall = "1";
    
}
