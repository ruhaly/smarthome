package com.changhong.smarthome.phone.store.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.entity.StoreConstant;

public class ShowStoreLocationActivity extends SuperActivity
{
    private BMapManager mBMapMan;
    
    /**
     * 标题栏
     */
    private StoreTitleItem titleItem;
    
       
    @Override
    protected void onDestroy()
    {
        if (mBMapMan != null)
        {
            mBMapMan.destroy();
            mBMapMan = null;
        }
        super.onDestroy();
    }
    
    @Override
    protected void onPause()
    {
        if (mBMapMan != null)
        {
            mBMapMan.stop();
        }
        super.onPause();
    }
    
    @Override
    protected void onResume()
    {
        if (mBMapMan != null)
        {
            mBMapMan.start();
        }
        super.onResume();
    }
    
    /**
     * 经度
     */
    private double lon;
    
    /**
     * 维度
     */
    private double lat;
    
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        try
        {
            mBMapMan = new BMapManager(getApplication());
//            mBMapMan.init("vjIz9E2LGDMrqNt6IoQiBn5G", null);
            mBMapMan.init("vjIz9E2LGDMrqNt6IoQiBn5G", null);
            //            super.initMapActivity(mBMapMan);
            setContentView(R.layout.show_store_location);
            MapView mMapView = (MapView) findViewById(R.id.bmapsView);
            mMapView.setBuiltInZoomControls(true); //设置启用内置的缩放控件
            
            mMapView.setTraffic(true);
            
            MapController mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
            lon = getIntent().getDoubleExtra(StoreConstant.LONGITUDE, 118.756324);
            lat = getIntent().getDoubleExtra(StoreConstant.LATITUDE, 31.975182);
            Log.d("ShowStoreLocationActivity", "initData | lon = "+lon + " |lat= " + lat);
            GeoPoint point = new GeoPoint((int) (lat * 1E6),
                    (int) (lon * 1E6)); //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
            mMapController.setCenter(point); //设置地图中心点
            mMapController.setZoom(12); //设置地图zoom级别
            
            Drawable mark= getResources().getDrawable(R.drawable.icon_nearby);  
          //用OverlayItem准备Overlay数据  
            OverlayItem item1 = new OverlayItem(point,"item1","item1"); 
//            item1.setMarker(mark);  
          //创建IteminizedOverlay  
            OverlayTest itemOverlay = new OverlayTest(mark, mMapView);  
            //将IteminizedOverlay添加到MapView中  
            mMapView.getOverlays().clear();  
            mMapView.getOverlays().add(itemOverlay);  
            itemOverlay.addItem(item1); 
            mMapView.refresh();  
        }
        catch (Exception e)
        {
            // TODO: handle exception
            Log.e(TAG, "start baidu map error :: "+ e.getMessage());
        }
        
        
        
        titleItem = (StoreTitleItem) findViewById(R.id.main_title);
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    /* 
     * 要处理overlay点击事件时需要继承ItemizedOverlay 
     * 不处理点击事件时可直接生成ItemizedOverlay. 
     */  
    class OverlayTest extends ItemizedOverlay<OverlayItem> {  
        //用MapView构造ItemizedOverlay  
        public OverlayTest(Drawable mark,MapView mapView){  
                super(mark,mapView);  
        }  
        protected boolean onTap(int index) {  
            //在此处理item点击事件  
            System.out.println("item onTap: "+index);  
            return true;  
        }  
            public boolean onTap(GeoPoint pt, MapView mapView){  
                    //在此处理MapView的点击事件，当返回 true时  
                    super.onTap(pt,mapView);  
                    return false;  
            }  
            // 自2.1.1 开始，使用 add/remove 管理overlay , 无需重写以下接口  
            /* 
            @Override 
            protected OverlayItem createItem(int i) { 
                    return mGeoList.get(i); 
            } 
            
            @Override 
            public int size() { 
                    return mGeoList.size(); 
            } 
            */  
    } 
    
}
