package com.changhong.smarthome.phone.foundation.baseapi;

public abstract interface HttpAction
{
    public static final String ACTION_FOUNDATION_CHECK_UPDATE = "QueryVersionInfo";
    
    public static final String ACTION_LOGIN = "UserLoginAction";
    
    public static final String ACTION_REGISTER = "UserRegAction";
    
    public static final String ACTION_VALICODE_FOR_REGISTER = "UserRegAction";
    
    public static final String ACTION_GET_PROVINCE = "QueryProvince";
    
    public static final String ACTION_GET_CITY = "QueryCityHandler";
    
    public static final String ACTION_GET_COMMUNITY = "QueryOrgByCity";
    
    public static final String ACTION_GET_ZUTUAN = "QueryGroupByOrg";
    
    public static final String ACTION_GET_BUILDING = "QueryBuildingByGroup";
    
    public static final String ACTION_GET_DOORPLATE = "QueryHouseByBudUnit";
    
    public static final String ACTION_REGISTER_VALICODE = "SMSCodeAction";
    
    public static final String ACTION_EXIT = "/account/end_session";
    
    public static final String ACTION_GET_MSGLIST = "findMsgList";
    
    public static final String ACTION_DELETE_MSG = "deleteMsg";
    
    public static final String ACTION_CLEAN_MSG = "cleanMsg";
    
    public static final String ACTION_GETRECORDS = "GetRecordsAction";
    
    public static final String ACTION_PAY_INFOS = "DunningAction";
    
    public static final String ACTION_PAYSUCCESS_TO_SEVER = "DetaileRecordsAction";
    
    public static final String ACTION_FINDPWD_VALICODE = "SMSCodeAction";
    
    public static final String ACTION_FINDPWD = "PwdRecoverAction";
    
    public static final String ACTION_MODIFYPWD = "changePwdAction";
    
    public static final String ACTION_AD_LIST = "findAd";
    
    public static final String ACTION_AD_CLICK = "modifyAdClick";
    
    public static final String ACTION_INTEGRALEXCHANGE = "findIntegralExchange";
    
    public static final String ACTION_PERSON_INFO = "UserInfoAction";
    
    public static final String ACTION_UPDATE_PERSON_INFO = "ModifyUserInfo";
    
    public static final String ACTION_APPLY_JOIN = "UserBindingHouseAction";
    
    public static final String ACTION_MEMBER = "QueryHouseRelationInfo";
    
    public static final String ACTION_GET_COMMUNITY_MANAGE = "QueryHouseCommunity";
    
    public static final String ACTION_ADD_COMMUNITY_MANAGE = "SaveHouseCommunity";
    
    public static final String ACTION_DELETE_COMMUNITY_MANAGE = "DeleteHouseCommunity";
    
    public static final String ACTION_SELECT_COMMUNITY_MANAGE = "QueryOrgByCity";
    
    public static final String ACTION_NEW_MEMBER = "QueryHouseRelationApply";
    
    public static final String ACTION_AUDIT_MEMBER = "ProcessHouseRelationApply";
    
    public static final String ACTION_DELETE_MEMBER = "DeleteHouseRelation";
    
    public static final String ACTION_PERMIT_MEMBER = "UserPermitAction";
    
    public static final String ACTION_CANCEL_PERMIT_MEMBER = "UserCancelPermitAction";
    
    public static final String ACTION_GETCOMMUNITY_BYNAME = "QueryHouseByNameActionHandler";
    
    public static final String ACTION_GET_TWODIMENSIONCODE = "QueryClientImage";
    
    public static final String ACTION_CHANGE_COMMUNITY = "ChangeOrgan";
    
    public static final String ACTION_MODIFY_PERSONINFO = "ModifyUserInfo";
}
