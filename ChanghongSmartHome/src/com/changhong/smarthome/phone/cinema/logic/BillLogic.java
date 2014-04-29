/**
 * BillLogic.java
 * com.pactera.ch_bedframe.logic
 *
 * Function�?TODO 
 *
 *   ver     date      		author
 *   		 2013-12-5 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.com.example.changhongwyfw.logic.foundation.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.HttpException;
import com.pactera.ch.foundation.entity.BillInfo;

/**
 * ClassName:BillLogic Function: TODO ADD FUNCTION
 * 
 * @author hanliangru
 * @version
 * @since Ver 1.1
 * @Date 2013-12-5 下午2:16:03
 */
package com.changhong.smarthome.phone.cinema.logic;
/*
 * 临时储存数据 和 初始化时候数据的初始化
 */
import java.io.InputStream;
import java.util.ArrayList;

import com.changhong.sdk.entity.BaseAccountInfo;
import com.changhong.sdk.logic.SuperLogic;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.Title;
import com.lidroid.xutils.exception.HttpException;

public class BillLogic extends SuperLogic {

	public static ArrayList<Title>  titleList_easy = new ArrayList<Title>();
	public static ArrayList<Title>  titleList_complex = new ArrayList<Title>();
	public static ArrayList<Cinema>  cinemaList = new ArrayList<Cinema>();
    public static BaseAccountInfo accountInfo = new BaseAccountInfo();
	private String personId="";

	private static BillLogic ins;

	public static synchronized BillLogic getInstance() {
	   
        
		if (null == ins) {
			ins = new BillLogic();
			   Title title7 = new Title();
		        title7.setTitle_id("1");
		        title7.setTitle_name("新上架");
		        titleList_complex.add(title7);
		        
		        Title title1 = new Title();
		        title1.setTitle_id("2");
		        title1.setTitle_name("点播排行");
		        titleList_complex.add(title1);
		        
		        Title title2 = new Title();
		        title2.setTitle_id("3");
		        title2.setTitle_name("超高清");
		        titleList_complex.add(title2);
		        
		        Title title3 = new Title();
		        title3.setTitle_id("4");
		        title3.setTitle_name("欧美");
		        titleList_complex.add(title3);
		        
		        Title title4 = new Title();
		        title4.setTitle_id("5");
		        title4.setTitle_name("港台");
		        titleList_complex.add(title4);
		        
		        Title title5 = new Title();
		        title5.setTitle_id("6");
		        title5.setTitle_name("大陆");
		        titleList_complex.add(title5);
		        
		        Title title6 = new Title();
		        title6.setTitle_id("7");
		        title6.setTitle_name("日韩");
		        titleList_complex.add(title6);
		        
		        
		        Title titlex1 = new Title();
		        titlex1.setTitle_id("8");
		        titlex1.setTitle_name("最近更新");
		        titleList_complex.add(titlex1);
		        
		        Title titlex2 = new Title();
		        titlex2.setTitle_id("9");
		        titlex2.setTitle_name("全部");
		        titleList_complex.add(titlex2);
		        
		        

                Title titlex10 = new Title();
                titlex10.setTitle_id("8");
                titlex10.setTitle_name("最近更新");
                titleList_easy.add(titlex10);
                
                Title titlex11 = new Title();
                titlex11.setTitle_id("9");
                titlex11.setTitle_name("全部");
                titleList_easy.add(titlex11);
		        
		        
		        
		        
		      
		       
		        
		        
		       
		}
		return ins;
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void clear() {
	    titleList_easy.clear();
	    titleList_complex.clear();
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

    @Override
    public void stopRequest()
    {
        // TODO Auto-generated method stub
        
    }
	

}
