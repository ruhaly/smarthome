package com.changhong.smarthome.phone.sns.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.Base64;
import com.changhong.sdk.baseapi.BitMapUtil;
import com.changhong.sdk.baseapi.DateUtil;
import com.changhong.sdk.widget.MCloudProgressDialog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.baseapi.UserUtils;
import com.changhong.smarthome.phone.property.entry.SharedPreferenceUtil;
import com.changhong.smarthome.phone.sns.Constant;
import com.changhong.smarthome.phone.sns.bean.GroupBuyingAddVO;
import com.changhong.smarthome.phone.sns.bean.TSnsThemeCode;
import com.changhong.smarthome.phone.sns.bean.TSnsThemePay;
import com.changhong.smarthome.phone.sns.bean.TSnsThemePic;
import com.changhong.smarthome.phone.sns.bean.TSnsThemeType;
import com.changhong.smarthome.phone.sns.bean.ThemeSend;
import com.changhong.smarthome.phone.sns.logic.IntShareLogic;
import com.changhong.smarthome.phone.store.tools.StringUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

/**
 * <功能详细描述>
 * 
 * @author wanghonghong
 * @version [版本号, 2014-3-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PubEditActivity extends SnsSuperActivity implements
        OnClickListener, OnTouchListener

{
    private ImageView exit_button;//退出返回按钮
    
    /**
     * 标题最大输入值
     */
    private int titleMaxNum = 66;
    
    /**
     * 描述内容最大输入值
     */
    private int contentMaxNum = 1333;
    
    /**
     * 地点最大输入值
     */
    private int placeMaxNum = 66;
    
    /**
     * 价格最大输入值
     */
    private int priceMaxNum = 8;
    
    /**
     * 数量最大输入值
     */
    private int countMaxNum = 12;
    
    /**
     * 联系人最大输入值
     */
    private int contactMaxNum = 20;
    
    /**
     * 联系人电话最大输入值
     */
    private int contactTelMaxNum = 15;
    
    private Bitmap bitMap;
    
    private String filePath = "";
    
    private static final String TAG = "PubEditActivity";
    
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    
    private File mCurrentPhotoFile;//
    
    private String fileName;// 图片名称
    
    /**
     * SharedPreference中存储照相机文件名对应的key值
     */
    public static final String CAMERA_FILE_NAME_KEY = "cameraName";
    
    /**
     * 存储照相机拍摄照片名称的SharedPreference文件名
     */
    public static final String SP_CAMERA_INFO_FILE_NAME = "camera_info";
    
    ProgressDialog delLoadingDialog = null;
    
    private ThemeSend req;
    
    private GroupBuyingAddVO groupBuyingAddVO;
    
    private PopupWindow popupWindowHead;
    
    /**
     * 发帖界面的随拍布局
     */
    private LinearLayout lLayoutShot;
    
    /**
     * 发帖界面的二手买卖/活动
     */
    private LinearLayout lLayoutSecondHand;
    
    private LinearLayout activityLL;
    
    private EditText etStartTime;
    
    private EditText etEndTime;
    
    private EditText etPlace;
    
    private LinearLayout picLl;
    
    private LinearLayout ll_title;
    
    private ImageView sharePubQuan;
    
    private ImageView shareFriendQuan;
    
    private LinearLayout sharePubQuanLL;
    
    private LinearLayout shareFriendQuanLL;
    
    private ImageView sharePrivateQuan;
    
    private EditText activityEtTitle;
    
    private EditText shotEtTitle;
    
    private EditText shotEtContent;
    
    private EditText activityEtContent;
    
    private LinearLayout contactLL;
    
    private EditText contactName;
    
    private EditText contactNumb;
    
    private ImageView addImageView1;
    
    private ImageView addImageView2;
    
    private ImageView addImageView3;
    
    private ImageView addImageView4;
    
    private TextView publishThemeButton;
    
    private TextView title;
    
    private TextView privateBtn;
    
    //
    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 1001;
    
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 1002;
    
    private boolean hasImage; //是否已经选择了图片       
    
    private List<TSnsThemePic> picList = new ArrayList<TSnsThemePic>();
    
    /**
     * 发帖类型对象
     */
    private String subThemeType;
    
    /**
     * 动态ID
     */
    private String themeId;
    
    /**
     * 是否是修改
     */
    private boolean isModify = false;
    
    /**
     * 重新编辑时传过来的标题
     */
    private String editTitle;
    
    /**
     * 重新编辑时传过来的内容
     */
    private String editContent;
    
    /**
     * 重新编辑时传过来的联系人名字
     */
    private String editContactName;
    
    /**
     * 重新编辑时传过来的联系人电话
     */
    private String editConatctTel;
    
    private HttpUtils httpUtils;
    
    private IntShareLogic snsLogic;
    
    private MCloudProgressDialog progressDialog;
    
    /**
     * 添加的是哪张图片
     */
    private int addInt = 1;
    
    /**
     * 图片下载工具
     */
    private BitmapUtils bitmapUtilsPic1;
    
    /**
     * 图片下载工具
     */
    private BitmapUtils bitmapUtilsPic2;
    
    /**
     * 图片下载工具
     */
    private BitmapUtils bitmapUtilsPic3;
    
    /**
     * 图片下载工具
     */
    private BitmapUtils bitmapUtilsPic4;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        initView();
        intiListData();
        snsLogic = IntShareLogic.getInstance();
        req = new ThemeSend();
        groupBuyingAddVO = new GroupBuyingAddVO();
        bitmapUtilsPic1 = new BitmapUtils(PubEditActivity.this);
        bitmapUtilsPic1.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsPic1.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsPic1.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtilsPic2 = new BitmapUtils(PubEditActivity.this);
        bitmapUtilsPic2.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsPic2.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsPic2.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtilsPic3 = new BitmapUtils(PubEditActivity.this);
        bitmapUtilsPic3.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsPic3.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsPic3.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtilsPic4 = new BitmapUtils(PubEditActivity.this);
        bitmapUtilsPic4.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsPic4.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsPic4.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        Intent intent = getIntent();
        if (intent != null)
        {
            subThemeType = intent.getStringExtra("subThemeType");
            
            if (subThemeType.equals(Constant.DYNAMIC_TYPE_ID_SECOND))
            {
                isModify = intent.getBooleanExtra("isModify", false);
                editTitle = intent.getStringExtra("editTitle");
                editContent = intent.getStringExtra("editContent");
                editContactName = intent.getStringExtra("editContactName");
                editConatctTel = intent.getStringExtra("editConatctTel");
                themeId = intent.getStringExtra("themeId");
                if (isModify)
                {
                    picList = (List<TSnsThemePic>) intent.getSerializableExtra("pics");
                    showEditContent();
                }
            }
        }
        
        showActionView(subThemeType);
    }
    
    /**
     * 
     */
    private void intiListData()
    {
        for (int i = 0; i <= 3; i++)
        {
            picList.add(i, null);
        }
        
    }
    
    private void initView()
    {
        lLayoutShot = (LinearLayout) findViewById(R.id.ll_shot);
        picLl = (LinearLayout) findViewById(R.id.pic_ll);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        lLayoutSecondHand = (LinearLayout) findViewById(R.id.ll_second_hand);
        sharePubQuan = (ImageView) findViewById(R.id.rb_interact_share_pub_quan);
        shareFriendQuan = (ImageView) findViewById(R.id.rb_interact_share_friend_quan);
        sharePrivateQuan = (ImageView) findViewById(R.id.rb_interact_share_private_letter);
        sharePubQuanLL = (LinearLayout) findViewById(R.id.rb_interact_share_pub_quan_ll);
        shareFriendQuanLL = (LinearLayout) findViewById(R.id.rb_interact_share_friend_quan_ll);
        activityEtTitle = (EditText) findViewById(R.id.activity_et_title);
        shotEtTitle = (EditText) findViewById(R.id.et_title);
        shotEtContent = (EditText) findViewById(R.id.et_say_content);
        activityEtContent = (EditText) findViewById(R.id.et_product_introduce);
        contactLL = (LinearLayout) findViewById(R.id.contact_ll);
        contactName = (EditText) findViewById(R.id.contact_name);
        contactNumb = (EditText) findViewById(R.id.contact_numb);
        publishThemeButton = (TextView) findViewById(R.id.bt_interact_share_publish);
        title = (TextView) findViewById(R.id.title);
        addImageView1 = (ImageView) findViewById(R.id.iv_add_1);
        addImageView2 = (ImageView) findViewById(R.id.iv_add_2);
        addImageView3 = (ImageView) findViewById(R.id.iv_add_3);
        addImageView4 = (ImageView) findViewById(R.id.iv_add_4);
        privateBtn = (TextView) findViewById(R.id.private_btn);
        etStartTime = (EditText) findViewById(R.id.et_start_time);
        etEndTime = (EditText) findViewById(R.id.et_end_time);
        etPlace = (EditText) findViewById(R.id.et_place);
        activityLL = (LinearLayout) findViewById(R.id.activity);
        privateBtn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        privateBtn.setOnClickListener(this);
        addImageView1.setOnClickListener(this);
        addImageView2.setOnClickListener(this);
        addImageView3.setOnClickListener(this);
        addImageView4.setOnClickListener(this);
        shareFriendQuanLL.setOnClickListener(this);
        sharePubQuanLL.setOnClickListener(this);
        sharePrivateQuan.setOnClickListener(this);
        publishThemeButton.setOnClickListener(this);
        contactName.setText(Constant.TELNAME);
        contactNumb.setText(Constant.TEL);
        etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);
        
        exit_button = (ImageView) findViewById(R.id.exit_button);
        
        super.exitButtonClick(exit_button);
    }
    
    /**
     * 编辑时，展示原来内容
     */
    private void showEditContent()
    {
        activityEtTitle.setText(editTitle);
        activityEtContent.setText(editContent);
        contactName.setText(editContactName);
        contactNumb.setText(editConatctTel);
        if (null != picList)
        {
            int size = picList.size();
            for (int i = 0; i < size; i++)
            {
                switch (i)
                {
                    case 0:
                        String uri = Constant.URL_iconUrl
                                + picList.get(0).getPicPath();
                        bitmapUtilsPic1.display(addImageView1,
                                uri,
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        // TODO Auto-generated method stub
                                        addImageView1.setImageBitmap(bitmap);
                                        String res = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                        TSnsThemePic pic = new TSnsThemePic();
                                        pic.setPicStr(res);
                                        picList.remove(0);
                                        picList.add(0, pic);
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        // TODO Auto-generated method stub
                                        
                                    }
                                });
                        
                        break;
                    case 1:
                        String uri1 = Constant.URL_iconUrl
                                + picList.get(1).getPicPath();
                        bitmapUtilsPic2.display(addImageView2,
                                uri1,
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        // TODO Auto-generated method stub
                                        addImageView2.setImageBitmap(bitmap);
                                        String res = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                        TSnsThemePic pic = new TSnsThemePic();
                                        pic.setPicStr(res);
                                        picList.remove(1);
                                        picList.add(1, pic);
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        // TODO Auto-generated method stub
                                        
                                    }
                                });
                        break;
                    case 2:
                        String uri2 = Constant.URL_iconUrl
                                + picList.get(2).getPicPath();
                        bitmapUtilsPic3.display(addImageView3,
                                uri2,
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        // TODO Auto-generated method stub
                                        addImageView3.setImageBitmap(bitmap);
                                        String res = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                        TSnsThemePic pic = new TSnsThemePic();
                                        pic.setPicStr(res);
                                        picList.remove(2);
                                        picList.add(2, pic);
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        // TODO Auto-generated method stub
                                        
                                    }
                                });
                        break;
                    case 3:
                        String uri3 = Constant.URL_iconUrl
                                + picList.get(3).getPicPath();
                        bitmapUtilsPic4.display(addImageView4,
                                uri3,
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        // TODO Auto-generated method stub
                                        addImageView4.setImageBitmap(bitmap);
                                        String res = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                        TSnsThemePic pic = new TSnsThemePic();
                                        pic.setPicStr(res);
                                        picList.remove(3);
                                        picList.add(3, pic);
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        // TODO Auto-generated method stub
                                        
                                    }
                                });
                        break;
                    default:
                        break;
                }
            }
        }
        
    }
    
    /**
     * 根据发帖不同Action展示不同布局
     * @param id
     */
    private void showActionView(String id)
    {
        
        req.setThemeType(String.valueOf(Constant.PUBLIC_RANGE));
        
        if (Constant.DYNAMIC_TYPE_ID_ACTIVITY.equals(id))
        {
            showPostViewActivity();
            
            req.setThemeCode(id);
        }
        else if (Constant.DYNAMIC_TYPE_ID_SECOND.equals(id))
        {
            showPostViewSecond();
            TSnsThemeCode themeCo1 = new TSnsThemeCode();
            themeCo1.setCode(id);
            req.setThemeCode(id);
        }
        else
        {
            showPostViewShot();
            TSnsThemeCode themeCo = new TSnsThemeCode();
            themeCo.setCode(id);
            req.setThemeCode(id);
        }
        //        if (isNormalType())
        //        {
        //            clickFalse(shotEtTitle);
        //            clickTrue(shotEtContent);
        //        }
        //        else
        //        {
        //            clickFalse(activityEtTitle);
        //            clickTrue(activityEtContent);
        //            clickFalse(etPlace);
        //            clickFalse(etProductNum);
        //            clickFalse(etProductSale);
        //            clickFalse(contactName);
        //            clickFalse(contactNumb);
        //        }
    }
    
    /**
     * 私信按钮不可点击
     * @param v
     */
    private void clickFalse(View v)
    {
        v.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                // TODO Auto-generated method stub
                if (hasFocus)
                {
                    privateBtn.setEnabled(false);
                    privateBtn.setClickable(false);
                }
                
            }
        });
        
    }
    
    /**
     *私信按钮可点击
     * @param v
     */
    private void clickTrue(View v)
    {
        v.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                // TODO Auto-generated method stub
                if (hasFocus)
                {
                    privateBtn.setEnabled(true);
                    privateBtn.setClickable(true);
                }
                
            }
        });
    }
    
    /**
     * 展示发帖的活动
     */
    private void showPostViewActivity()
    {
        lLayoutShot.setVisibility(View.GONE);
        lLayoutSecondHand.setVisibility(View.VISIBLE);
        sharePrivateQuan.setVisibility(View.GONE);
        contactLL.setVisibility(View.VISIBLE);
        ll_title.setVisibility(View.VISIBLE);
        picLl.setVisibility(View.VISIBLE);
        activityLL.setVisibility(View.VISIBLE);
        etPlace.setVisibility(View.VISIBLE);
    }
    
    /**
     * 展示发帖的随拍
     */
    private void showPostViewShot()
    {
        lLayoutShot.setVisibility(View.VISIBLE);
        lLayoutSecondHand.setVisibility(View.VISIBLE);
        sharePrivateQuan.setVisibility(View.GONE);
        contactLL.setVisibility(View.GONE);
        ll_title.setVisibility(View.GONE);
        picLl.setVisibility(View.VISIBLE);
        activityLL.setVisibility(View.GONE);
        etPlace.setVisibility(View.GONE);
    }
    
    /**
     * 展示发帖的二手买卖
     */
    private void showPostViewSecond()
    {
        lLayoutShot.setVisibility(View.GONE);
        lLayoutSecondHand.setVisibility(View.VISIBLE);
        sharePrivateQuan.setVisibility(View.GONE);
        contactLL.setVisibility(View.VISIBLE);
        ll_title.setVisibility(View.VISIBLE);
        picLl.setVisibility(View.VISIBLE);
        activityLL.setVisibility(View.GONE);
        etPlace.setVisibility(View.GONE);
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch (v.getId())
            {
                case R.id.et_end_time:
                    showDateSelectDialog(getResources().getString(R.string.input_end_time),
                            etEndTime);
                    break;
                case R.id.et_start_time:
                    showDateSelectDialog(getResources().getString(R.string.input_start_time),
                            etStartTime);
                    break;
                default:
                    break;
            }
        }
        return true;
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
        //发帖范围选择--公开圈
            case R.id.rb_interact_share_pub_quan_ll:
                if (!(req.getThemeType().equals(String.valueOf(Constant.PRIVATE_RANGE))))
                {
                    sharePubQuan.setImageResource(R.drawable.checked);
                    shareFriendQuan.setImageResource(R.drawable.unchecked);
                    req.setThemeType(String.valueOf(Constant.PUBLIC_RANGE));
                }
                
                break;
            //发帖范围选择--好友圈
            case R.id.rb_interact_share_friend_quan_ll:
                if (!(req.getThemeType().equals(String.valueOf(Constant.PRIVATE_RANGE))))
                {
                    sharePubQuan.setImageResource(R.drawable.unchecked);
                    shareFriendQuan.setImageResource(R.drawable.checked);
                    req.setThemeType(String.valueOf(Constant.FRIEND_RANGE));
                }
                
                break;
            //发帖范围选择--@ME
            case R.id.private_btn:
                //                Intent intent = new Intent();
                //                intent.setClass(PubEditActivity.this,
                //                        SelectFriendActivity.class);
                //                startActivityForResult(intent, 100);
                break;
            //发表
            case R.id.bt_interact_share_publish:
                if (isModify)
                {
                    modify();
                }
                else
                {
                    post();
                }
                
                break;
            //发帖添加图片
            case R.id.iv_add_1:
                addInt = 1;
                addPic();
                break;
            case R.id.iv_add_2:
                addInt = 2;
                addPic();
                break;
            case R.id.iv_add_3:
                addInt = 3;
                addPic();
                break;
            case R.id.iv_add_4:
                addInt = 4;
                addPic();
                break;
            default:
                break;
        }
    }
    
    private void modify()
    {
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(PubEditActivity.this,
                    getResources().getString(R.string.common_pagetip_operating));
        }
        progressDialog.show();
        httpUtils = new HttpUtils();
        snsLogic.setData(mHandler);
        editContent = activityEtContent.getText().toString().trim();
        editTitle = activityEtTitle.getText().toString().trim();
        editContactName = contactName.getText().toString().trim();
        editConatctTel = contactNumb.getText().toString().trim();
        snsLogic.requestUpdateThemeRequest(editTitle,
                editContent,
                themeId,
                editConatctTel,
                editContactName,
                picList,
                httpUtils);
    }
    
    /**
     * 展示时间选择对话框
     * @param title
     * @param editText
     */
    private void showDateSelectDialog(String title, final EditText editText)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.date_time_dialog, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
        builder.setView(view);
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                null);
        
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(Calendar.MINUTE);
        final int inType = editText.getInputType();
        //先设置为TYPE_NULL 不弹出输入键盘
        editText.setInputType(InputType.TYPE_NULL);
        editText.setInputType(inType);
        editText.setSelection(editText.getText().length());
        builder.setTitle(title);
        builder.setPositiveButton(getResources().getString(R.string.btn_confirm),
                new DialogInterface.OnClickListener()
                {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append(" ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":")
                                .append(timePicker.getCurrentMinute());
                        
                        editText.setText(sb);
                        //                        etEndTime.requestFocus();
                        
                        dialog.cancel();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }
    
    /**
     * 添加图片
     */
    private void addPic()
    {
        View photoSelect = getLayoutInflater().inflate(R.layout.photo_select,
                null);
        popupWindowHead = new PopupWindow(photoSelect,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindowHead.setBackgroundDrawable(new BitmapDrawable());
        popupWindowHead.setOutsideTouchable(true);
        popupWindowHead.showAtLocation(PubEditActivity.this.findViewById(R.id.root),
                Gravity.BOTTOM,
                0,
                0);
        photoSelect.findViewById(R.id.take_photo)
                .setOnClickListener(new OnClickListener()
                {
                    
                    @Override
                    public void onClick(View v)
                    {
                        // TODO Auto-generated method stub
                        doTakePhoto();
                        popupWindowHead.dismiss();
                    }
                });
        photoSelect.findViewById(R.id.gallery)
                .setOnClickListener(new OnClickListener()
                {
                    
                    @Override
                    public void onClick(View v)
                    {
                        // TODO Auto-generated method stub
                        doSelectImageFromLoacal();
                        popupWindowHead.dismiss();
                    }
                });
        photoSelect.findViewById(R.id.cancel)
                .setOnClickListener(new OnClickListener()
                {
                    
                    @Override
                    public void onClick(View v)
                    {
                        // TODO Auto-generated method stub
                        popupWindowHead.dismiss();
                    }
                });
    }
    
    /**       
     * 拍照获取图片       
     *        
     */
    private void doTakePhoto()
    {
        
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
        {// 判断是否有SD卡
            if (!PHOTO_DIR.exists())
            {
                PHOTO_DIR.mkdirs();// 创建照片的存储目录
            }
            fileName = getPhotoFileName();
            mCurrentPhotoFile = new File(PHOTO_DIR, fileName);// 给新照的照片文件命名
            Intent cIntent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(cIntent, CAMERA_WITH_DATA);
        }
    }
    
    private Intent getTakePickIntent(File f)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        return intent;
    }
    
    // 用当前时间给取得的图片命名
    private String getPhotoFileName()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        
        String photoName = dateFormat.format(date) + ".jpg";
        saveImagePhoto(photoName);
        return photoName;
    }
    
    /**
     * 将照相机拍摄生成的文件名存到SharedPreference配置文件 <BR>
     */
    private void saveImagePhoto(String photoName)
    {
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getApplicationContext(),
                SP_CAMERA_INFO_FILE_NAME);
        sharedPreferenceUtil.putString(CAMERA_FILE_NAME_KEY, photoName);
    }
    
    /**
     * 获取照相机拍摄生成的文件名 <BR>
     * 
     * @return
     */
    private String getImagePhoto()
    {
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getApplicationContext(),
                SP_CAMERA_INFO_FILE_NAME);
        return sharedPreferenceUtil.getString(CAMERA_FILE_NAME_KEY);
    }
    
    /**       
     * 从本地手机中选择图片       
     */
    private void doSelectImageFromLoacal()
    {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction("android.intent.action.GET_CONTENT");
        Intent localIntent2 = Intent.createChooser(localIntent, "选择图片");
        startActivityForResult(localIntent2, PHOTO_PICKED_WITH_DATA);
    }
    
    /**
     * 是否是除二手信息和活动之外的类型
     * @return
     */
    private boolean isNormalType()
    {
        if (req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_HELP)
                || req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_ESSAY)
                || req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_SHOT)
                || req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_OPINION)
                || req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_PROPERTY))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //用来保存图片       
        //        Bitmap bitMap = null;
        int degree = 0;
        
        if (resultCode != RESULT_OK)
        
        {
            return;
        }
        //图片压缩 防止OOM
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;//图片宽高都为原来的四分之一，即图片为原来的十六分之一  
        switch (requestCode)
        {
        
            case PHOTO_PICKED_WITH_DATA: //从本地选择图片        
                //                if (bitMap != null && !bitMap.isRecycled())
                //                {
                //                    bitMap.recycle();
                //                }
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null)
                {
                    try
                    {
                        bitMap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri),
                                null,
                                options);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    
                }
                //获取选择相册里的照片的路径
                String[] proj = { MediaStore.Images.Media.DATA };
                
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(selectedImageUri,
                        proj,
                        null,
                        null,
                        null);
                Log.d(TAG, "cursor---->" + cursor);
                //按我个人理解 这个是获得用户选择的图片的索引值
                String path = "";
                if (null != cursor)
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    path = cursor.getString(column_index);
                    
                    try
                    {
                        //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)  
                        if (Integer.parseInt(Build.VERSION.SDK) < 14)
                        {
                            cursor.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, "error:" + e);
                    }
                    
                }
                else
                {
                    path = selectedImageUri.toString().substring(7);
                }
                Log.d(TAG, "path-------->" + path);
                degree = BitMapUtil.readPictureDegree(path);
                break;
            case CAMERA_WITH_DATA: //拍照        
                if (null == mCurrentPhotoFile)
                {
                    Log.d(TAG, "mCurrentPhotoFile   is  null ------->");
                    fileName = getImagePhoto();
                    mCurrentPhotoFile = new File(PHOTO_DIR, fileName);// 给新照的照片文件命名
                }
                if (null == fileName || fileName.equals("")
                        || !mCurrentPhotoFile.exists())
                {
                    Log.d(TAG, "拍照失败！");
                    return;
                }
                filePath = mCurrentPhotoFile.getAbsolutePath();
                Log.d(TAG, "mCurrentPhotoFile getPath --->" + filePath);
                //及时回收图片(多次拍照时，图片会被回收，Imageview显示不正常)
                //                if (bitMap != null && !bitMap.isRecycled())
                //                {
                //                    bitMap.recycle();
                //                }
                
                bitMap = BitmapFactory.decodeFile(filePath, options);
                /** 
                 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 
                 */
                
                degree = BitMapUtil.readPictureDegree(filePath);
                
                break;
        }
        /** 
         * 把图片旋转为正的方向 
         */
        Log.d(TAG, "degree--->" + degree);
        bitMap = BitMapUtil.rotaingImageView(degree, bitMap);
        
        addPicList(bitMap);
        
    }
    
    /**
     * 添加图片到图片数组里
     * @param bitmap
     */
    private void addPicList(Bitmap bitmap)
    {
        String res = Base64.encode(BitMapUtil.getBitmapData(bitmap));
        String img[] = new String[4];
        
        TSnsThemePic pic = new TSnsThemePic();
        pic.setPicStr(res);
        
        switch (addInt)
        {
            case 1:
                addImageView1.setImageBitmap(bitmap);
                if (picList.size() > 0)
                {
                    picList.remove(0);
                }
                picList.add(0, pic);
                img[0] = res;
                break;
            case 2:
                addImageView2.setImageBitmap(bitmap);
                if (picList.size() > 1)
                {
                    picList.remove(1);
                    
                }
                picList.add(1, pic);
                img[1] = res;
                
                break;
            case 3:
                addImageView3.setImageBitmap(bitmap);
                if (picList.size() > 2)
                {
                    picList.remove(2);
                    
                }
                picList.add(2, pic);
                img[2] = res;
                break;
            case 4:
                addImageView4.setImageBitmap(bitmap);
                if (picList.size() > 3)
                {
                    picList.remove(3);
                }
                picList.add(3, pic);
                img[3] = res;
                break;
            default:
                break;
        }
        req.setPicList(picList);
        groupBuyingAddVO.setImg(img);
    }
    
    /**
     * 发帖
     */
    private void post()
    {
        String title = "";
        String content = "";
        String contact = "";
        String numb = "";
        TSnsThemePay pay = new TSnsThemePay();
        if (isNormalType())
        {
            title = shotEtTitle.getText().toString().trim();
            content = shotEtContent.getText().toString().trim();
            
        }
        else
        {
            
            title = activityEtTitle.getText().toString().trim();
            content = activityEtContent.getText().toString().trim();
            contact = contactName.getText().toString().trim();
            numb = contactNumb.getText().toString().trim();
            //二手买卖
            if (req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_SECOND))
            {
                
                pay.setIsActive("1");
                pay.setTelName(contact);
                pay.setTel(numb);
                req.setPay(pay);
            }
            else
            {
                TSnsThemeType detail = new TSnsThemeType();
                if (StringUtil.isNotEmpty(etStartTime.getText()
                        .toString()
                        .trim()))
                {
                    detail.setActivityStartTime(etStartTime.getText()
                            .toString()
                            .trim());
                    groupBuyingAddVO.setStarttime(etStartTime.getText()
                            .toString()
                            .trim());
                }
                else
                {
                    detail.setActivityStartTime("");
                }
                if (StringUtil.isNotEmpty(etEndTime.getText().toString().trim()))
                {
                    detail.setActivityEndTime(etEndTime.getText()
                            .toString()
                            .trim());
                    groupBuyingAddVO.setEndtime(etEndTime.getText()
                            .toString()
                            .trim());
                }
                else
                {
                    detail.setActivityEndTime("");
                }
                
                if (StringUtil.isNotEmpty(etPlace.getText().toString().trim()))
                {
                    detail.setPlace(etPlace.getText().toString().trim());
                    groupBuyingAddVO.setAddr(etPlace.getText()
                            .toString()
                            .trim());
                }
                else
                {
                    detail.setPlace("");
                }
                detail.setTel(numb);
                detail.setTelName(contact);
                groupBuyingAddVO.setPhone(numb);
                groupBuyingAddVO.setContact(contact);
                req.setDetail(detail);
                
            }
            
        }
        Log.d(TAG, "title--->" + title + "  ,content-->" + content);
        req.setCreator(UserUtils.getUser().getAccount());
        groupBuyingAddVO.setAccountId(UserUtils.getUser().getAccount());//???????TODO
        //参数校验
        
        //        Log.d(TAG, "Contactname------->"
        //                + req.getPay().getTelName().getBytes().length);
        if (StringUtil.isEmpty(title))
        {
            Toast.makeText(PubEditActivity.this,
                    getResources().getString(R.string.input_title),
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (title.length() > titleMaxNum)
            {
                Toast.makeText(PubEditActivity.this,
                        getResources().getString(R.string.too_long_title),
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                req.setTitle(title);
                groupBuyingAddVO.setTitle(title);
                if (StringUtil.isEmpty(content))
                {
                    Toast.makeText(PubEditActivity.this,
                            getResources().getString(R.string.input_content),
                            Toast.LENGTH_SHORT).show();
                }
                else if (content.length() < 10)
                {
                    Toast.makeText(PubEditActivity.this,
                            getResources().getString(R.string.input_content_less),
                            Toast.LENGTH_SHORT)
                            .show();
                    
                }
                else
                {
                    if (content.length() > contentMaxNum)
                    {
                        Toast.makeText(PubEditActivity.this,
                                getResources().getString(R.string.too_long_content),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    else
                    {
                        req.setContent(content);
                        groupBuyingAddVO.setDescripte(content);
                        if (req.getThemeCode()
                                .equals(Constant.DYNAMIC_TYPE_ID_ACTIVITY))
                        {
                            if (StringUtil.isEmpty(req.getDetail()
                                    .getActivityStartTime()))
                            {
                                Toast.makeText(PubEditActivity.this,
                                        getResources().getString(R.string.input_start_time),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            else if (StringUtil.isEmpty(req.getDetail()
                                    .getActivityEndTime()))
                            {
                                Toast.makeText(PubEditActivity.this,
                                        getResources().getString(R.string.input_end_time),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            else
                                try
                                {
                                    if (DateUtil.DateCompare(req.getDetail()
                                            .getActivityStartTime(),
                                            req.getDetail()
                                                    .getActivityEndTime()))
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.date_toast),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else if (StringUtil.isEmpty(req.getDetail()
                                            .getPlace()))
                                    
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.input_place),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else if (req.getDetail()
                                            .getPlace()
                                            .length() > placeMaxNum)
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.too_long_place),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else if (StringUtil.isEmpty(req.getDetail()
                                            .getTelName()))
                                    
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.input_contact_name),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else if (req.getDetail()
                                            .getTelName()
                                            .length() > contactMaxNum)
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.too_long_contact),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else if (StringUtil.isEmpty(req.getDetail()
                                            .getTel()))
                                    
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.input_contact_tel),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else if (req.getDetail().getTel().length() > contactTelMaxNum)
                                    {
                                        Toast.makeText(PubEditActivity.this,
                                                getResources().getString(R.string.too_long_tel),
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    else
                                    {
                                        groupBuyingAddVO.setPrice("120");
                                        groupBuyingAddVO.setOriginalcost("150");
                                        saveTheme(groupBuyingAddVO);
                                    }
                                }
                                catch (NotFoundException e)
                                {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                catch (Exception e)
                                {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                        }
                        else if (req.getThemeCode()
                                .equals(Constant.DYNAMIC_TYPE_ID_SECOND))
                        {
                            pay.setPrice(new BigDecimal(0));
                            pay.setCount(new BigDecimal(0));
                            if (StringUtil.isEmpty(req.getPay().getTelName()))
                            {
                                Toast.makeText(PubEditActivity.this,
                                        getResources().getString(R.string.input_contact_name),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            else if (req.getPay().getTelName().length() > contactMaxNum)
                            {
                                Toast.makeText(PubEditActivity.this,
                                        getResources().getString(R.string.too_long_contact),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            else if (StringUtil.isEmpty(req.getPay().getTel()))
                            
                            {
                                Toast.makeText(PubEditActivity.this,
                                        getResources().getString(R.string.input_contact_tel),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            else if (req.getPay().getTel().length() > contactTelMaxNum)
                            {
                                Toast.makeText(PubEditActivity.this,
                                        getResources().getString(R.string.too_long_tel),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            else
                            {
                                saveTheme(req);
                            }
                            
                        }
                        else
                        {
                            saveTheme(req);
                        }
                    }
                    
                }
            }
            
        }
    }
    
    /**
     * @param groupBuyingAddVO2
     */
    private void saveTheme(GroupBuyingAddVO groupBuyingAddVO2)
    {
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(PubEditActivity.this,
                    getResources().getString(R.string.common_pagetip_operating));
        }
        progressDialog.show();
        httpUtils = new HttpUtils();
        snsLogic.setData(mHandler);
        snsLogic.requestSaveTheme(groupBuyingAddVO2, httpUtils);
    }
    
    /**
     * 发表动态
     * @param theme
     */
    private void saveTheme(ThemeSend req)
    {
        Log.d(TAG, "size---->" + req.getPicList().size());
        if (null == progressDialog)
        {
            progressDialog = new MCloudProgressDialog(PubEditActivity.this,
                    getResources().getString(R.string.common_pagetip_operating));
        }
        progressDialog.show();
        httpUtils = new HttpUtils();
        snsLogic.setData(mHandler);
        snsLogic.requestSaveTheme(req, httpUtils);
    }
    
    @Override
    public void initData()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        // TODO Auto-generated method stub
        super.handleMsg(msg);
        if (null != progressDialog)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
        switch (msg.what)
        {
            case Constant.SAVE_THEME_SUCCESS:
                Toast.makeText(PubEditActivity.this,
                        getResources().getString(R.string.pub_success),
                        Toast.LENGTH_SHORT).show();
                Constant.isNeedToRefresh = true;
                if (req.getThemeCode().equals(Constant.DYNAMIC_TYPE_ID_SECOND)
                        || req.getThemeCode()
                                .equals(Constant.DYNAMIC_TYPE_ID_ACTIVITY))
                {
                    activityEtTitle.setText("");
                    activityEtContent.setText("");
                    contactName.setText("");
                    contactNumb.setText("");
                }
                else
                {
                    shotEtTitle.setText("");
                    shotEtContent.setText("");
                    
                }
                sharePubQuan.setImageResource(R.drawable.checked);
                shareFriendQuan.setImageResource(R.drawable.unchecked);
                addImageView1.setBackgroundResource(R.drawable.add_image);
                addImageView2.setBackgroundResource(R.drawable.add_image);
                addImageView3.setBackgroundResource(R.drawable.add_image);
                addImageView4.setBackgroundResource(R.drawable.add_image);
                //                req = new ThemeSend();
                Constant.isNeedToRefresh = true;
                finish();
                break;
            case Constant.SAVE_THEME_FAILED:
                Toast.makeText(PubEditActivity.this,
                        getResources().getString(R.string.pub_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            case Constant.GET_UPDATETHEME_SUCCESS:
                Toast.makeText(PubEditActivity.this,
                        getResources().getString(R.string.modify_success),
                        Toast.LENGTH_SHORT).show();
                Constant.isNeedToRefreshBusiness = true;
                finish();
                break;
            case Constant.GET_UPDATETHEME_FAILED:
                Toast.makeText(PubEditActivity.this,
                        getResources().getString(R.string.modify_failed),
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState---->");
        //三星等手机会拍照时会销毁Activity 重新获取拍的图片
        Bitmap bMap = savedInstanceState.getParcelable("bitMap");
        String fPthString = savedInstanceState.getString("filePath");
        if (null != bMap)
        {
            /** 
             * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 
             */
            Log.d(TAG, "filePath--->" + filePath);
            int degree = BitMapUtil.readPictureDegree(fPthString);
            Log.d(TAG, "degree--->" + degree);
            /** 
             * 把图片旋转为正的方向 
             */
            //            bMap = BitMapUtil.rotaingImageView(degree, bMap);
            //            addImageView1.setImageBitmap(bMap);
            //            String res = Base64.encode(BitMapUtil.getBitmapData(bMap));
            //            TSnsThemePic pic = new TSnsThemePic();
            //            pic.setPicStr(res);
            //            picList.add(pic);
            //            req.setPicList(picList);
            //            hasImage = true;
            addPicList(bMap);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        //防止三星等手机拍照销毁当前Activity，保存拍的照片
        savedInstanceState.putParcelable("bitMap", bitMap);
        savedInstanceState.putString("filePath", filePath);
        //        savedInstanceState.putParcelableArrayList("bitmaps",
        //                (ArrayList<? extends Parcelable>) bitmaps);
    }
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        
    }
    
    @Override
    public void onBackPressed()
    {
        //        super.onBackPressed();
        // TODO Auto-generated method stub
        
        if (null != popupWindowHead && popupWindowHead.isShowing())
        {
            popupWindowHead.dismiss();
        }
        else
        {
            super.onBackPressed();
        }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        
        super.onConfigurationChanged(newConfig);
    }
}
