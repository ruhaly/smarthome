package com.changhong.smarthome.phone.property.entry;

import java.io.Serializable;
import java.util.ArrayList;

public class ServicesItem implements Serializable {
    
	private int ryid;
	private int fwid;
	private int fwlx;
	
	private int fwzt;
	private String content;
	
	private int pf;
	private String content_pj;
	private String stateTimeStr;
	
	private String propertyallback;
	private String lxry;
	
	private long lxryhm;
	
	private ArrayList picStrList;//图片地址
	
	private String voicePath;//声音地址
	
	//public static SlideView slideView;
	
	
	public int getFwzt() {
		return fwzt;
	}
	
   

    public ArrayList getPicStrList()
    {
        return picStrList;
    }



    public void setPicStrList(ArrayList picStrList)
    {
        this.picStrList = picStrList;
    }



    public String getVoicePath()
    {
        return voicePath;
    }
    public void setVoicePath(String voicePath)
    {
        this.voicePath = voicePath;
    }
    public void setFwzt(int fwzt) {
		this.fwzt = fwzt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getPf() {
		return pf;
	}
	public void setPf(int pf) {
		this.pf = pf;
	}
	public String getContent_pj() {
		return content_pj;
	}
	public void setContent_pj(String content_pj) {
		this.content_pj = content_pj;
	}
	public int getFwid() {
		return fwid;
	}
	public void setFwid(int fw_id) {
		this.fwid = fw_id;
	}
	public int getRyid() {
		return ryid;
	}
	public void setRyid(int ryid) {
		this.ryid = ryid;
	}
	public String getPropertyallback() {
		return propertyallback;
	}
	public void setPropertyallback(String propertyallback) {
		this.propertyallback = propertyallback;
	}
	public String getLxry() {
		return lxry;
	}
	public void setLxry(String lxry) {
		this.lxry = lxry;
	}
	public long getLxryhm() {
		return lxryhm;
	}
	public void setLxryhm(long lxryhm) {
		this.lxryhm = lxryhm;
	}
	public int getFwlx() {
		return fwlx;
	}
	public void setFwlx(int fwlx) {
		this.fwlx = fwlx;
	}
	public String getStateTimeStr() {
		return stateTimeStr;
	}
	public void setStateTimeStr(String stateTimeStr) {
		this.stateTimeStr = stateTimeStr;
	}

    

}
