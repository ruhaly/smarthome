package com.changhong.foundation.baseapi;

public class CHResource
{
    
    public static final String ENCODE_UTF = "UTF-8";
    
    public static final byte NO_ERROR = 0;
    
    public class ShareKey
    {
        public static final String UID = "UID";
        
        public static final String ACCOUNT = "ACCOUNT";
        
        public static final String PWD = "PWD";
        
        public static final String NAME = "NAME";
        
        public static final String NICKNAME = "NICKNAME";
        
        public static final String SEX = "SEX";
    }
    
    // 是否保存到sd卡
    public static boolean saveToSD = false;
    
    public static final String F_PATH = "/ch_foundation";
    
    public static final String PIC_PATH = F_PATH + "/ch_pic";
    
    public static final String CONFIG_NAME = "ch_foundation_config";
    
    public static final String NOTIFYCATION_CHECK = "notifycation_check";
    
    public static final String ACTION_BACKGROUND = "com.ch_foundation.action_background";
    
    public static final int TIMEOUT = 20000;
}
