package com.changhong.smarthome.phone.cinema.entry;

import android.graphics.Bitmap;

/**  
* @author yang_jun
* @date 2014-3-19 上午9:24:18 
*/
public class LocalVideoInfo
{
    String DisplayName;
    String Path;
    Bitmap videoBitMap;
    public Bitmap getVideoBitMap()
    {
        return videoBitMap;
    }
    public void setVideoBitMap(Bitmap videoBitMap)
    {
        this.videoBitMap = videoBitMap;
    }
    public String getDisplayName()
    {
        return DisplayName;
    }
    public void setDisplayName(String displayName)
    {
        DisplayName = displayName;
    }
    public String getPath()
    {
        return Path;
    }
    public void setPath(String path)
    {
        Path = path;
    }
    
}
