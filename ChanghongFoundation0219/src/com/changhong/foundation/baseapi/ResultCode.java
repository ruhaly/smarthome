package com.changhong.foundation.baseapi;

public class ResultCode
{
    //成功
    public static final String RESULT_SUCCESS = "200";
    
    //成功
    public static final String RESULT_SUCCESS_CBS = "0";
    
    //服务器异常
    public static final String RESULT_FAILED = "400";
    
    //数据不合法
    public static final String RESULT_DATA_INVALID = "505";
    
    //参数错误
    public static final String RESULT_EMPTY = "2";
    
    //用户不存在
    public static final String RESULT_USER_NOT_EXIT = "-3";
    
    //参数错误
    public static final String RESULT_PARAMS_ERROR = "-1";
    
    //获取验证码识别
    public static final String RESULT_VALICODE_ERROR = "-2";
    
    //506：原密码错误
    public static final String RESULT_OLDPWD_ERROR = "506";
    
    //507：密码修改失败
    public static final String RESULT_PWD_MODIFY_ERROR = "507";
    
    //501：密码错误
    public static final String RESULT_PWD_ERROR = "501";
    
    //502：用户不存在
    public static final String RESULT_USER_NOT_EXIST = "502";
    
    //503：用户未激活
    public static final String RESULT_USER_NOT_ACTIVATE = "503";
    
    //509：用户已存在
    public static final String RESULT_USER_HAS_EXIST_ = "509";
    
    //510：同一住址同一用户不能重复注册，请检查注册信息!
    public static final String RESULT_REGISTER_AGIAN = "510";
    
    //511：用户信息不存在，请联系管理员确认该用户是否在物业系统中进行备案，谢谢!
    public static final String RESULT_USERINFO_NOT_EXIST = "511";
    
    //512：业主已经存在
    public static final String RESULT_HOUSEHOLDER_HAS_EXIST_ = "512";
    
    //513：创建失败,业主手机填写不正确
    public static final String RESULT_HOUSEHOLDER_PHONE_ERROR = "513";
    
    //514：业主不存在,请检查业主手机号码是否正确
    public static final String RESULT_HOUSEHOLDER_NOT_EXIST_ = "514";
    
    //515：该地址的业主需要先注册用户，请稍候!
    public static final String RESULT_HOUSEHOLDER_NEED_REGISTER_ = "515";
}
