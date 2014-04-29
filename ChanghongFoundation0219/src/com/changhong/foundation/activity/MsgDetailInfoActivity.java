/**
 * MsgDetailInfoActivity.java
 * com.pactera.ch_bedframe.activity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-12-4 		b
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

package com.changhong.foundation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.sdk.baseapi.StringUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ClassName:MsgDetailInfoActivity Function: TODO ADD FUNCTION
 * 
 * @author ruhaly
 * @version
 * @since Ver 1.1
 * @Date 2013-12-4 上午10:26:52
 */
public class MsgDetailInfoActivity extends BaseActivity
{
    
    TextView tv_title, tv_content;
    
    ImageView imageview;
    
    private BitmapUtils bitmapUtils;
    
    @Override
    public void initData()
    {
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.msg_detail_info_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        bitmapUtils = new BitmapUtils(getBaseContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.msg);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.msg);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        tv_title = (TextView) findViewById(R.id.textView1);
        tv_content = (TextView) findViewById(R.id.textView3);
        imageview = (ImageView) findViewById(R.id.imageView1);
        if (null != getIntent())
        {
            Intent intent = getIntent();
            String title = intent.getExtras().getString("title");
            String content = intent.getExtras().getString("content");
            String imgurl = intent.getExtras().getString("img");
            if (!StringUtils.isEmpty(imgurl))
            {
                imageview.setVisibility(View.VISIBLE);
                bitmapUtils.display(imageview, imgurl);
            }
            else
            {
                imageview.setVisibility(View.GONE);
            }
            
            tv_title.setText(title);
            tv_content.setText(content);
            
        }
        
    }
    
    @Override
    public void clearData()
    {
        
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
    }
    
    // public class Mytask extends AsyncTask<String, Void, Bitmap> {
    // @Override
    // protected void onPreExecute() {
    // // TODO Auto-generated method stub
    // super.onPreExecute();
    //
    // }
    //
    // @Override
    // protected Bitmap doInBackground(String... params) {
    // // TODO Auto-generated method stub
    // Bitmap bitmap=null;
    // HttpClient client=new DefaultHttpClient();
    // HttpGet httpGet=new HttpGet(url);
    // try {
    // HttpResponse response=client.execute(httpGet);
    // if(response.getStatusLine().getStatusCode()==200){
    // HttpEntity httpEntity=response.getEntity();
    // byte[] bt= EntityUtils.toByteArray(httpEntity);
    // bitmap=BitmapFactory.decodeByteArray(bt, 0, bt.length);
    // }
    // } catch (ClientProtocolException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return bitmap;
    // }
    //
    // @Override
    // protected void onPostExecute(Bitmap result) {
    // // TODO Auto-generated method stub
    // super.onPostExecute(result);
    // circleView.setVisibility(View.GONE);
    // imageview.setImageBitmap(result);
    // }
    //
    // }
}
