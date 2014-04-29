/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.changhong.smarthome.phone.store.entity;


/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public final class StoreConstant
{
    /**
     * 服务器地址
     */
    public static String URL_STORE = "";
    /**
     * 搜索的key，从主界面传到搜索结果界面
     */
    public final static String SEARCHKEY = "searchkey";
    
    /**
     * 主界面显示的tab,从主界面传到搜索结果界面
     */
    public final static String MAIN_TAB = "maintab";
    
    /**
     * 分类信息，从主界面的分类列表传到分类展现界面
     */
    public final static String GOODS_COLUMN = "goods_column";
    
    /**
     * 商品的详情，从列表界面进入详情界面
     */
    public final static String GOODSDETAIL = "goods_detail";
    
    /**
     * 经纬度 从商品详情  进入 展现商店地图界面
     */
    public final static String LONGITUDE = "longitude";
    
    public final static String LATITUDE = "latitude";
    
    /**
     * 订单展现的类型 0 确认订单 1 显示订单
     */
    public final static String ORDER_TYPE = "order_type";
    
    public final static int ORDER_CONFORM = 0;
    
    public final static int ORDER_SHOW = 1;
    
//    /**
//     * 订单展现的类型 0 确认订单 1 显示订单
//     */
//    public final static String SEARCH_TYPE = "search_type";
//    
//    public final static int COLUMN_SHOW = 0;
//    
//    public final static int SEARCH_RESULT = 1;
    
    /**
     * 订单详情，从订单列表界面传到订单详情界面
     */
    public final static String ORDER_DETAIL = "order_detail";
    
    /**
     * 搜索商品 接口成功处理，有数据
     */
    public final static int GET_FINDGOODS_SUCCESS = 9100;
    
    public final static int GET_FINDGOODS_FAILED = 9101;
    
    public final static int GET_FINDGOODS_NO_DATA = 9102;
    
    /**
     * 搜索商品 接口,获取更多成功处理，有数据
     */
    public final static int GET_FINDGOODS_GET_MORE_SUCCESS = 9103;
    
    public final static int GET_FINDGOODS_GET_MORE_FAILED = 9104;
    
    /**
     * 获取栏目信息
     */
    public final static int GET_FINDGOODSCOLUMN_SUCCESS = 9105;
    
    public final static int GET_FINDGOODSCOLUMN_FAILED = 9106;
    
    public final static int GET_FINDGOODSCOLUMN_NO_DATA = 9107;
    
    /**
     * 获取订单接口
     */
    public final static int GET_FINDORDERS_SUCCESS = 9108;
    
    public final static int GET_FINDORDERS_FAILED = 9109;
    
    /**
     * 获取订单接口,获取更多成功处理，有数据
     */
    public final static int GET_FINDORDERS_GETMORE_SUCCESS = 9110;
    
    public final static int GET_FINDORDERS_GETMORE_FAILED = 9111;
    
    /**
     * 获取历史订单接口,有数据
     */
    public final static int GET_FINDORDERS_OLD_SUCCESS = 9112;
    
    public final static int GET_FINDORDERS_OLD_FAILED = 9113;
    
    /**
     * 获取历史订单接口,获取更多成功处理，有数据
     */
    public final static int GET_FINDORDERS_OLD_GETMORE_SUCCESS = 9114;
    
    public final static int GET_FINDORDERS_OLD_GETMORE_FAILED = 9115;
    
    /**
     * 添加订单
     */
    public final static int GET_ADDORDER_SUCCESS = 9116;
    
    public final static int GET_ADDORDER_FAILED = 9117;
    
    /**
     * 点击所有分类
     */
    public final static int ALL_TYPE = 9118;
    
    /**
     * 点击距离分类
     */
    public final static int NEAR_TYPE = 9119;
    
    private StoreConstant()
    {
    }
    
//    public static class Config
//    {
//        public static final boolean DEVELOPER_MODE = false;
//    }
//    
//    public static class Extra
//    {
//        public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
//        
//        public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
//    }
}
