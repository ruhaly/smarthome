package com.changhong.smarthome.phone.property.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.AppLog;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.Base64;
import com.changhong.smarthome.phone.property.entry.BitMapUtil;
import com.changhong.smarthome.phone.property.entry.HouseInfo;
import com.changhong.smarthome.phone.property.entry.HouseInfoRent;
import com.changhong.smarthome.phone.property.entry.SharedPreferenceUtil;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.GetHouseInfoLogic;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;


/**
 * [房屋托管详情]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-22] 
 */
public class HouseEscrowDetailActivity extends SuperActivity implements
        OnClickListener
{
//    private String bz;
    
    //标题
//    private TextView textView1;
    
    private ImageView house_lefttitle;
    
    private String id;
    
    private int state;
    
    
    private GetHouseInfoLogic getHouseInfoLogic;
    
    private HttpUtils httpUtil;
    
    private Bitmap bitMap;
    
    private String filePath = "";
    
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    
    private File mCurrentPhotoFile;//
    
    private String fileName;// 图片名称
    
    /**
     * SharedPreference中存储照相机文件名对应的key值
     */
    public static final String CAMERA_FILE_NAME_KEY = "cameraName";
    
    public static final int IV_VIDEO_BZ = 8888;
    
    /**
     * 存储照相机拍摄照片名称的SharedPreference文件名
     */
    public static final String SP_CAMERA_INFO_FILE_NAME = "camera_info";
    
    ProgressDialog delLoadingDialog = null;
    
    //private ThemeSend req;
    
    private PopupWindow popupWindowHead;
    
    //
    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 1001;
    
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 1002;
    
//    private boolean hasImage; //是否已经选择了图片      
    
    private ImageView addImageView1;
    
    private ImageView addImageView2;
    
    private ImageView addImageView3;
    
    private ImageView addImageView4;
    
    //用来标识第几张图片
    private int addImage_bz;
    
//    private ImageView iv_video_1;
    
//    private List<TSnsThemePic> picList = new ArrayList<TSnsThemePic>();
    
    private String picStr1;
    
    private String picStr2;
    
    private String picStr3;
    
    private String picStr4;
    
    private ArrayList<String> addImageList;
    
//    private String voiceStr;
    
//    private String recordPath;
    
    private LinearLayout oversendLay;
    
//    private RelativeLayout title_house;
    
    private ScrollView mainRela;
    
    private TextView textViewUpdate;
    
    private EditText house_editmin;
    
    private EditText house_editmax;
    
    private EditText bx_popupe;
    
    private TextView bx_popupq;
    
//    private RadioGroup house_rp;
    
    private RadioButton house_rbsend;
    
    private RadioButton house_rbsell;
    
    private int low_price;
    
    private int high_price;
    
    private String description;
    
    private int type;
    
    //标识是更新还是取消按钮 0更新 （提交） 1取消托管
    private int houseupdateOrcancel = 0;
    
    private RelativeLayout house_popup;
    
    private TextView houser_over1;
    
    private TextView houser_over2;
    
    private TextView houser_over3;
    
    private TextView houser_over4;
    
    private TextView houser_over5;
    
    private TextView houser_over6;
    
    private TextView houser_over7;
    
    private Animation animationsmall_big;
    
    private Animation animationbig_small;
    
    private TextView house_popupTextView;
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.HOUSEI_SUCCESS_MSGWHAT:
                HouseInfo houseInfo1 = (HouseInfo) msg.obj;
                initdataPager(houseInfo1);
                break;
            case HttpAction.HOUSEPUT_SUCCESS_MSGWHAT:
                /* showToast("代管成功");*/

                house_popup.setVisibility(View.VISIBLE);
                house_popup.setAnimation(animationsmall_big);
                house_popup.setOnClickListener(this);
                break;
            case HttpAction.HOUSECANEL_SUCCESS_MSGWHAT:

                house_popup.setVisibility(View.VISIBLE);
                house_popup.setAnimation(animationsmall_big);
                house_popupTextView.setText(getResources().getString(R.string.house_exitsuccess));
                house_popup.setOnClickListener(this);
                break;
            case HttpAction.HOUSERENTAL_SUCCESS_MSGWHAT:
                HouseInfoRent houseInfoRent = (HouseInfoRent) msg.obj;
                initoversendPager(houseInfoRent);
                break;
        }
        super.handleMsg(msg);
    }
    
    private void initoversendPager(HouseInfoRent houseInfoRent)
    {
        if (houseInfoRent.getHouse_no() != null)
        {
            houser_over1.setText(houseInfoRent.getHouse_no()+"");
        }
        houser_over2.setText(houseInfoRent.getPrice() + "");
        if (houseInfoRent.getRental_time() != null)
        {
            houser_over3.setText(houseInfoRent.getRental_time());
        }
        
        houser_over4.setText(houseInfoRent.getDeposit() + "");
        if (houseInfoRent.getRental_type() != null)
        {
            houser_over5.setText(houseInfoRent.getRental_type()+"");
        }
        if (houseInfoRent.getName() != null)
        {
            houser_over6.setText(houseInfoRent.getName());
        }
        if (houseInfoRent.getMobile() != null)
        {
            houser_over7.setText(houseInfoRent.getMobile());
        }
    }
    
    private void initdataPager(HouseInfo houseInfo)
    {
        
        house_editmin.setText(houseInfo.getLow_price() + "");
        house_editmax.setText(houseInfo.getHigh_price() + "");
        if (houseInfo.getDescription() != null)
        {
            bx_popupe.setText(houseInfo.getDescription() + "");
        }
        houseupdateOrcancel = 1;
        bx_popupq.setBackgroundResource(R.drawable.house_cancel);
        
        BitmapUtils bitmapUtilsHead = new BitmapUtils(this);
        bitmapUtilsHead.configDefaultLoadingImage(R.drawable.add_image);
        bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.add_image);
        bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        
        if (null != houseInfo.getImgs())
        {
            if (houseInfo.getImgs().size() > 0)
            {
                
                for (int i = 0; i < houseInfo.getImgs().size(); i++)
                {
                    if (i == 0)
                    {
                        bitmapUtilsHead.display(addImageView1,
                                (String) houseInfo.getImgs().get(i),
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        picStr1 = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                        addImageView1.setImageBitmap(bitmap);
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        //imgHead.setImageDrawable(drawable);
                                    }
                                });
                        
                        // bitmapUtilsHead.getBitmapFromMemCache((String) houseInfo.getImgs().get(i),null);
                        
                    }
                    if (i == 1)
                    {
                        bitmapUtilsHead.display(addImageView2,
                                (String) houseInfo.getImgs().get(i),
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        addImageView2.setImageBitmap(bitmap);
                                        picStr2 = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        //imgHead.setImageDrawable(drawable);
                                    }
                                });
                        /* picStr2 = Base64.encode(BitMapUtil.getBitmapData(bitmapUtilsHead.getBitmapFromMemCache((String) houseInfo.getImgs()
                                 .get(i),
                                 null)));*/
                    }
                    if (i == 2)
                    {
                        bitmapUtilsHead.display(addImageView3,
                                (String) houseInfo.getImgs().get(i),
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        addImageView3.setImageBitmap(bitmap);
                                        picStr3 = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        //imgHead.setImageDrawable(drawable);
                                    }
                                });
                        /* picStr3 = Base64.encode(BitMapUtil.getBitmapData(bitmapUtilsHead.getBitmapFromMemCache((String) houseInfo.getImgs()
                                 .get(i),
                                 null)));*/
                    }
                    if (i == 3)
                    {
                        bitmapUtilsHead.display(addImageView4,
                                (String) houseInfo.getImgs().get(i),
                                new BitmapLoadCallBack<View>()
                                {
                                    
                                    @Override
                                    public void onLoadCompleted(View container,
                                            String uri, Bitmap bitmap,
                                            BitmapDisplayConfig config,
                                            BitmapLoadFrom from)
                                    {
                                        addImageView4.setImageBitmap(bitmap);
                                        picStr4 = Base64.encode(BitMapUtil.getBitmapData(bitmap));
                                    }
                                    
                                    @Override
                                    public void onLoadFailed(View container,
                                            String uri, Drawable drawable)
                                    {
                                        //imgHead.setImageDrawable(drawable);
                                    }
                                });
                        /* picStr4 = Base64.encode(BitMapUtil.getBitmapData(bitmapUtilsHead.getBitmapFromMemCache((String) houseInfo.getImgs()
                                 .get(i),
                                 null)));*/
                    }
                }
            }
        }
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.housedetail);
        AppLog.out("onCreateH", "home", AppLog.LEVEL_INFO);
        initId();
        animationsmall_big = AnimationUtils.loadAnimation(this,
                R.anim.scalesamall_big);
        animationbig_small = AnimationUtils.loadAnimation(this,
                R.anim.scalesbig_small);
        /* WheelView catalogWheel = new WheelView(this);
         catalogWheel.setVisibleItems(2);
         catalogWheel.setCyclic(true);
         
         catalogWheel.setAdapter(new ArrayWheelAdapter<String>(names));*/
        
        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        state = intent.getIntExtra("state", 0);
        
        if (state == 1 || state == 3)
        {
            if (null != id && !id.equals(""))
            {
                httpUtil = new HttpUtils();
                getHouseInfoLogic.setData(mHandler);
                getHouseInfoLogic.getHouseInfoList(id, httpUtil);
                if (state == 1)
                {
                    house_rbsend.setChecked(true);
                }
                else
                {
                    house_rbsell.setChecked(true);
                }
                
            }
        }
        else if (state == 2)
        {
            oversendLay.setVisibility(View.VISIBLE);
            /*         title_house.setVisibility(View.GONE);*/
            textViewUpdate.setVisibility(View.GONE);
            mainRela.setVisibility(View.GONE);
            httpUtil = new HttpUtils();
            getHouseInfoLogic.setData(mHandler);
            getHouseInfoLogic.getHouseInfoRentalList(id, httpUtil);
            
        }
        else if (state == 0)
        {
            textViewUpdate.setVisibility(View.GONE);
        }
        
    }
    
    private void initId()
    {
        addImageView1 = (ImageView) findViewById(R.id.iv_add_1);
        addImageView2 = (ImageView) findViewById(R.id.iv_add_2);
        addImageView3 = (ImageView) findViewById(R.id.iv_add_3);
        addImageView4 = (ImageView) findViewById(R.id.iv_add_4);
        houser_over1 = (TextView) findViewById(R.id.houser_over1);
        houser_over2 = (TextView) findViewById(R.id.houser_over2);
        houser_over3 = (TextView) findViewById(R.id.houser_over3);
        houser_over4 = (TextView) findViewById(R.id.houser_over4);
        houser_over5 = (TextView) findViewById(R.id.houser_over5);
        houser_over6 = (TextView) findViewById(R.id.houser_over6);
        houser_over7 = (TextView) findViewById(R.id.houser_over7);
        house_popup = (RelativeLayout) findViewById(R.id.house_popup);
        house_popupTextView = (TextView) findViewById(R.id.house_popupTextView);
        house_lefttitle = (ImageView) findViewById(R.id.house_lefttitle);
        
        textViewUpdate = (TextView) findViewById(R.id.textViewUpdate);
        house_editmin = (EditText) findViewById(R.id.house_editmin);
        house_editmax = (EditText) findViewById(R.id.house_editmax);
        bx_popupe = (EditText) findViewById(R.id.bx_popupe);
//        house_rp = (RadioGroup) findViewById(R.id.house_rp);
        house_rbsend = (RadioButton) findViewById(R.id.house_rbsend);
        house_rbsell = (RadioButton) findViewById(R.id.house_rbsell);
        bx_popupq = (TextView) findViewById(R.id.bx_popupq);
        
        addImageView1.setOnClickListener(this);
        addImageView2.setOnClickListener(this);
        addImageView3.setOnClickListener(this);
        addImageView4.setOnClickListener(this);
        textViewUpdate.setOnClickListener(this);
        house_lefttitle.setOnClickListener(this);
        //bx_popupe.setOnClickListener(this);
        bx_popupq.setOnClickListener(this);
        
        oversendLay = (LinearLayout) findViewById(R.id.oversendLay);
//        title_house = (RelativeLayout) findViewById(R.id.title_house);
        mainRela = (ScrollView) findViewById(R.id.mainRela);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.wwfwmain, menu);
        return true;
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.house_lefttitle:
                finish();
                break;
            case R.id.iv_add_1:
                addImage_bz = 1;
                addPic();
                break;
            case R.id.iv_add_2:
                addImage_bz = 2;
                addPic();
                break;
            case R.id.iv_add_3:
                addImage_bz = 3;
                addPic();
                break;
            case R.id.iv_add_4:
                addImage_bz = 4;
                addPic();
                break;
            case R.id.bx_popupq:
                if (houseupdateOrcancel == 0)
                {
                    submitAndUpdateHouse();
                }
                else
                {
                    httpUtil = new HttpUtils();
                    getHouseInfoLogic.setData(mHandler);
                    getHouseInfoLogic.canelHouseInfo(id, httpUtil);
                }
                
                break;
            case R.id.textViewUpdate:
                submitAndUpdateHouse();
                break;
            case R.id.house_popup:
                house_popup.setVisibility(View.GONE);
                house_popup.setAnimation(animationbig_small);
                break;
            default:
                break;
        }
        
    }
    
    private void submitAndUpdateHouse()
    {
        httpUtil = new HttpUtils();
        getHouseInfoLogic.setData(mHandler);
        addImageList = new ArrayList<String>();
        if (picStr1 != null && !picStr1.equals(""))
        {
            addImageList.add(picStr1);
        }
        if (picStr2 != null && !picStr2.equals(""))
        {
            addImageList.add(picStr2);
        }
        if (picStr3 != null && !picStr3.equals(""))
        {
            addImageList.add(picStr3);
        }
        if (picStr4 != null && !picStr4.equals(""))
        {
            addImageList.add(picStr4);
        }
        low_price = Integer.parseInt(house_editmin.getText()+"");
        high_price = Integer.parseInt(house_editmax.getText()+"");
        description = bx_popupe.getText()+"";
        if (house_rbsend.isChecked())
        {
            type = 1;
        }
        else
        {
            type = 3;
        }
        
        getHouseInfoLogic.putHouseInfoList(id,
                low_price,
                high_price,
                description,
                type,
                addImageList,
                httpUtil);
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetHouseInfoLogic.getInstance())
        {
            getHouseInfoLogic = GetHouseInfoLogic.getInstance();
        }
        
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
    
    /**
     * 添加图片
     */
    private void addPic()
    {
        
        View photoSelect = getLayoutInflater().inflate(R.layout.photo_select,
                null);
        popupWindowHead = new PopupWindow(photoSelect,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindowHead.setOutsideTouchable(false);
        popupWindowHead.setFocusable(true);
        popupWindowHead.showAtLocation(photoSelect, Gravity.BOTTOM, 0, 0);
        
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
    @SuppressLint("SimpleDateFormat")
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
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this,
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
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this,
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
    
    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
//        int oo = requestCode;
        //用来保存图片       
        //        Bitmap bitMap = null;
        int degree = 0;
        //图片压缩 防止OOM
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;//图片宽高都为原来的二分之一，即图片为原来的四分之一  
        if (resultCode != -1)
        
        {
            return;
        }
        switch (requestCode)
        {
        
            case PHOTO_PICKED_WITH_DATA: //从本地选择图片        
                /* if (bitMap != null && !bitMap.isRecycled())
                 {
                     bitMap.recycle();
                 }*/
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null)
                {
                    try
                    {
                        bitMap = BitmapFactory.decodeStream(this.getContentResolver()
                                .openInputStream(selectedImageUri),
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
                if (cursor != null)
                {
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    /** 
                     * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 
                     */
                    degree = BitMapUtil.readPictureDegree(path);
                }
                
                break;
            case CAMERA_WITH_DATA: //拍照        
                //                    Bundle bundle = data.getExtras();
                //                    bitMap = (Bitmap) bundle.get("data");
                //                    if (bitMap != null)
                //                        bitMap.recycle();
                //                    bitMap = (Bitmap) data.getExtras().get("data");
                //                int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(),
                //                        bitMap.getHeight(),
                //                        500,
                //                        600);
                //                bitMap = ImageThumbnail.PicZoom(bitMap,
                //                        (int) (bitMap.getWidth() / scale),
                //                        (int) (bitMap.getHeight() / scale));
                //                imageView.setImageBitmap(bitMap);
                //                imageView.setVisibility(View.VISIBLE);
                
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
                /* //及时回收图片
                 if (bitMap != null && !bitMap.isRecycled())
                 {
                     bitMap.recycle();
                 }*/
                
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
        
        if (addImage_bz == 1)
        {
            addImageView1.setImageBitmap(bitMap);
            picStr1 = Base64.encode(BitMapUtil.getBitmapData(bitMap));
            
        }
        else if (addImage_bz == 2)
        {
            addImageView2.setImageBitmap(bitMap);
            picStr2 = Base64.encode(BitMapUtil.getBitmapData(bitMap));
        }
        else if (addImage_bz == 3)
        {
            addImageView3.setImageBitmap(bitMap);
            picStr3 = Base64.encode(BitMapUtil.getBitmapData(bitMap));
        }
        else if (addImage_bz == 4)
        {
            addImageView4.setImageBitmap(bitMap);
            picStr4 = Base64.encode(BitMapUtil.getBitmapData(bitMap));
        }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    super.onConfigurationChanged(newConfig);
    }
}
