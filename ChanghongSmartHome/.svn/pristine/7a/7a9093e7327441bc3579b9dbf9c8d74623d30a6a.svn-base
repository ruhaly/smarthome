package com.changhong.smarthome.phone.store.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.changhong.smarthome.phone.store.logic.OrderDetailLogic;
import com.changhong.smarthome.phone.store.logic.bean.OrderInfoBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

/**
 * [订单详情界面]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-28] 
 */
public class OrderNewDatailActivity extends SuperActivity
{
    private OrderInfoBean item;
    
    private ImageView imageView;
    
    /**
     * 商品名称
     */
    private TextView nameTextView;
    
    /**
     * 商品详情
     */
    private TextView descTextView;
    
    /**
     * 套餐详情
     */
    private TextView shoppingdeseTextView1;
    
    private TextView priceTextView;
    
    private TextView priceTextView1;
    
    /**
     * 订单编号
     */
    private TextView orderNumberValueView;
    
    /**
     * 订单日期
     */
    private TextView orderDateValueView;
    
    private RatingBar ratingbar;
    
    private OrderDetailLogic orderDetailLogic;
    
    private BitmapUtils mBitmapUtils;
    
    private int screenHeight;
    
    private int screenWidth;
    
    private StoreTitleItem titleItem;
    
    /**
     * 提交订单按钮
     */
    private Button order_button;
    
    /**
     * 提交订单布局
     */
    private LinearLayout orderLayout;
    
    private RatingBar endBar;
    
    private TextView endTextView;
    
    private int type;//0 商圈1团购
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        mBitmapUtils = new BitmapUtils(getApplicationContext());
        orderDetailLogic = OrderDetailLogic.getInstance(getApplicationContext());
        orderDetailLogic.setData(mHandler);
        item = (OrderInfoBean) getIntent().getSerializableExtra(StoreConstant.ORDER_DETAIL);
        type = item.getOrderType();
        screenHeight = orderDetailLogic.screenHeight;
        screenWidth = orderDetailLogic.screenWidth;
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.ordernewdetail);
        initView();
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void initView()
    {
        titleItem = (StoreTitleItem) findViewById(R.id.order_new_detail_title);
        titleItem.setBackListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
            }
        });
        
        imageView = (ImageView) findViewById(R.id.news_item_icon);
        RelativeLayout.LayoutParams params1 = (android.widget.RelativeLayout.LayoutParams) imageView.getLayoutParams();
        params1.height = (screenHeight * 471) / 1280;
        params1.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        imageView.setLayoutParams(params1);
        
        orderNumberValueView = (TextView) findViewById(R.id.order_number_value);
        orderDateValueView = (TextView) findViewById(R.id.order_date_value);
        nameTextView = (TextView) findViewById(R.id.shoping_name);
        descTextView = (TextView) findViewById(R.id.shoping_detail);
        shoppingdeseTextView1 = (TextView) findViewById(R.id.shoping_detail_1_value);
        
        ratingbar = (RatingBar) findViewById(R.id.order_star);
        priceTextView = (TextView) findViewById(R.id.shoping_price);
        priceTextView1 = (TextView) findViewById(R.id.shoping_price_1);
        
        ((TextView) findViewById(R.id.order_detaol_info)).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        ((TextView) findViewById(R.id.order_number)).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        ((TextView) findViewById(R.id.order_date)).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        orderNumberValueView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        orderDateValueView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        descTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        shoppingdeseTextView1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        priceTextView1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 30) / 720);
        
        if (item != null)
        {
            if (type == 1)
            {
                nameTextView.setVisibility(View.GONE);
                ratingbar.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) descTextView.getLayoutParams();
                params.topMargin = (15 * screenWidth) / 720;
                //                params.rightMargin = (20 * screenWidth) / 720;
                descTextView.setLayoutParams(params);
            }
            else
            {
                nameTextView.setVisibility(View.VISIBLE);
                nameTextView.setText(item.getGoodsDetailInfo().getName());
                
                ratingbar.setVisibility(View.VISIBLE);
                ratingbar.setRating(item.getGoodsDetailInfo().getScore());
                
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) descTextView.getLayoutParams();
                params.topMargin = 0;
                descTextView.setLayoutParams(params);
                
            }
            
            orderNumberValueView.setText(item.getOrderId());
            
            orderDateValueView.setText(item.getPayTime());
            
            descTextView.setText(item.getGoodsDetailInfo().getDesc());
            
            shoppingdeseTextView1.setText(item.getGoodsDetailInfo().getDetail());
            //原价
            double unitPrice = item.getGoodsDetailInfo().getOriginalPrice();
            //优惠价
            double rebatePrice = item.getGoodsDetailInfo().getSalePrice();
            
            String str1 = String.format(getResources().getString(R.string.shopping_detail_price),
                    String.valueOf(unitPrice));
            
            String str2 = String.format(getResources().getString(R.string.shopping_detail_price_1),
                    String.valueOf(rebatePrice));
            
            SpannableString spannableString = new SpannableString(str2);
            //元价用删除线
            spannableString.setSpan(new StrikethroughSpan(),
                    0,
                    str2.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            priceTextView.setText(str1);
            
            priceTextView1.setText(spannableString);
            
            BitmapDisplayConfig bigPicDisplayConfig = new BitmapDisplayConfig();
            BitmapSize bitmapSize = new BitmapSize(screenWidth,
                    (screenHeight * 471) / 1280);
            bigPicDisplayConfig.setBitmapMaxSize(bitmapSize);
            
            mBitmapUtils.display(imageView, item.getGoodsDetailInfo()
                    .getPicURL()
                    .get(0), bigPicDisplayConfig, null);
            
        }
        
        //评价按钮布局
        if (type == 0)
        {
            orderLayout = (LinearLayout) findViewById(R.id.order_button_layout);
            orderLayout.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams orderLayoutParams = (RelativeLayout.LayoutParams) orderLayout.getLayoutParams();
            orderLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            orderLayoutParams.height = 168 * screenHeight / 1280;
            orderLayout.setLayoutParams(orderLayoutParams);
            
            order_button = (Button) findViewById(R.id.order_button);
            order_button.setOnClickListener(this);
            LinearLayout.LayoutParams order_buttonParams = (android.widget.LinearLayout.LayoutParams) order_button.getLayoutParams();
            order_buttonParams.height = screenHeight * 82 / 1280;
            order_buttonParams.width = (screenWidth * 304) / 720;
            order_button.setLayoutParams(order_buttonParams);
            order_button.setPadding((screenWidth * 78) / 720,
                    screenHeight * 22 / 1280,
                    (screenWidth * 78) / 720,
                    screenHeight * 22 / 1280);
            order_button.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 26) / 720);
            order_button.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    showRatingDialog();
                }
            });
            
            endBar = (RatingBar) findViewById(R.id.end_star);
            endBar.setVisibility(View.GONE);
            endTextView = (TextView) findViewById(R.id.end_desc);
            endTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    (screenWidth * 36) / 720);
            endTextView.setVisibility(View.GONE);
        }
    }
    
    private void showRatingDialog()
    {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,dialog.xml文件中定义view内容
        window.setContentView(R.layout.showratingdialog);
        
        RelativeLayout show_rating_item = (RelativeLayout) window.findViewById(R.id.show_rating_item);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) show_rating_item.getLayoutParams();
        params.width = (620 * screenWidth) / 720;
        params.height = (341 * screenHeight) / 1280;
        show_rating_item.setLayoutParams(params);
        
        final RatingBar msg = (RatingBar) window.findViewById(R.id.order_star);
        
        msg.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
        {
            
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                    boolean fromUser)
            {
                // TODO Auto-generated method stub
                if (msg.getRating() != rating)
                {
                    msg.setRating(rating);
                }
            }
        });
        
        TextView descTextView = (TextView) window.findViewById(R.id.item_name);
        descTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                (screenWidth * 36) / 720);
        
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.item_ok);
        
        ok.setTextSize(TypedValue.COMPLEX_UNIT_PX, (screenWidth * 36) / 720);
        
        ok.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mHandler.sendMessage(mHandler.obtainMessage(999,
                        msg.getRating()));
                
                dlg.cancel();
            }
        });
        
        // 关闭alert对话框架
        //       Button cancel = (Button) window.findViewById(R.id.dialog_button_cancel);
        //       cancel.setOnClickListener(new View.OnClickListener()
        //       {
        //           public void onClick(View v)
        //           {
        //               dlg.cancel();
        //           }
        //       });
    }
    
    public void handleMsg(Message msg)
    {
        super.handleMsg(msg);
        switch (msg.what)
        {
            //            case StoreConstant.GET_ADDORDER_SUCCESS:
            //                showToast(getResources().getString(R.string.order_add_suessce));
            //                break;
            //            case StoreConstant.GET_ADDORDER_FAILED:
            //                showToast(getResources().getString(R.string.error_1));
            //                break;
            case 999:

                order_button.setVisibility(View.GONE);
                endBar.setVisibility(View.VISIBLE);
                endTextView.setVisibility(View.VISIBLE);
                float rating = (Float) msg.obj;
                endBar.setRating(rating);
                
                endBar.setOnTouchListener(new OnTouchListener()
                {
                    
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        // TODO Auto-generated method stub
                        return true;
                    }
                });
                
                break;
            
        }
    };
    
}
