package com.changhong.smarthome.phone.property;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.sdk.activity.SuperActivity;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.property.entry.Base64;
import com.changhong.smarthome.phone.property.entry.BitMapUtil;
import com.changhong.smarthome.phone.property.entry.SharedPreferenceUtil;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.GetTypeLogic;
import com.lidroid.xutils.HttpUtils;

public class PopupWindowActivity extends SuperActivity implements
        OnClickListener
{
    private static final String TAG = "PopupWindowActivity";
    
    //回退按钮
    private ImageView image_back;
    
    //提交按钮
    private TextView bx_popupq;
    
    //意见或者报修内容
    private EditText bx_popupe;
    
    //手机号码
    private EditText bx_hm;
    
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
    
    private ImageView addImageView1;
    
    private ImageView addImageView2;
    
    private ImageView addImageView3;
    
    private ImageView addImageView4;
    
    //用来标识第几张图片
    private int addImage_bz;
    
    private ImageView iv_video_1;
    
    private String picStr1;
    
    private String picStr2;
    
    private String picStr3;
    
    private String picStr4;
    
    private List<String> addImageList;
    
    private String voiceStr;
    
    private String recordPath;
    
    //选择类型的层级页面
    private RelativeLayout type_relative;
    
    /*    //水电
        private TextView type_sd;
        
        //建筑
        private TextView type_jz;
        
        //环境卫生
        private TextView type_hjws;
        */
    //类型：代表的是1水电 2 建筑  3卫生  动态类型
    private String typec;
    
    private GetTypeLogic getTypeLogic;
    
    //是投诉还是报修页面传来的标识
    private String bz;
    
    private HashMap<?, ?> map_type_bx;
    
    private HashMap<?, ?> map_type_ts;
    
    private LinearLayout bxtype_Layout;
    
    private HttpUtils httpUtil;
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            //报修类型
            case HttpAction.BXTYPE_SUCCESS_MSGWHAT:
                if(map_type_bx != null)
                {
                    map_type_bx.clear();
                }
                map_type_bx = (HashMap<?, ?>) msg.obj;
                if (map_type_bx != null)
                {
                    //动态生成类型页面
                    newView(map_type_bx);
                }
                
                break;
            case HttpAction.TSTYPE_SUCCESS_MSGWHAT:
                if(map_type_ts != null)
                {
                    map_type_ts.clear();
                }
                map_type_ts = (HashMap<?, ?>) msg.obj;
                if (map_type_ts != null)
                {
                    //动态生成类型页面
                    newView(map_type_ts);
                }
                
                break;
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    private void newView(HashMap<?, ?> map_type)
    {
        type_relative.setVisibility(View.VISIBLE);
        Iterator<?> it = map_type.entrySet().iterator();
        while (it.hasNext())
        {
            final Entry<?, ?> obj = (Entry<?, ?>) it.next();
           
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, 90);
            lp.setMargins(80, 0, 80, 24);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setText(obj.getValue() + "");
            textView.setBackgroundResource(R.drawable.propertytype);
            textView.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    type_relative.setVisibility(View.GONE);
                    typec = obj.getKey() + "";
                    getAddItem();
                }
            });
            Log.d(TAG, "newView | onClick | typec :: " + typec);
            bxtype_Layout.addView(textView);
        }
    }
    
    private void getAddItem()
    {
        String newfwcontent = bx_popupe.getText() + "";
        String phonenum = bx_hm.getText() + "";
        
        if (typec.equals(""))
        {
            type_relative.setVisibility(View.GONE);
            showToast(getResources().getString(R.string.type_lb));
        }
        else
        {
            if (!newfwcontent.equals("") && !phonenum.equals(""))
            {
                
                Intent intent = new Intent();
                intent.putExtra("phonenum", phonenum);
                intent.putExtra("newfwcontent", newfwcontent);
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
                intent.putStringArrayListExtra("addImageList",
                        (ArrayList<String>) addImageList);
                intent.putExtra("voiceStr", voiceStr);
                intent.putExtra("typec", typec);
                String bz = getIntent().getStringExtra("bz");
                if (bz.equals("repairs"))
                {
                    PopupWindowActivity.this.setResult(8, intent);
                    
                }
                if (bz.equals("complaint"))
                {
                    PopupWindowActivity.this.setResult(7, intent);
                }
                PopupWindowActivity.this.finish();

            }
            else
            {
                if (newfwcontent.equals(""))
                {
                    type_relative.setVisibility(View.GONE);
                    showToast(getResources().getString(R.string.bx_notnull));
                    
                }
                if (phonenum.equals(""))
                {
                    type_relative.setVisibility(View.GONE);
                    showToast(getResources().getString(R.string.bx_phonenotnull));
                    
                }
            }
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bx_popup);
        getTypeLogic.setData(mHandler);
        httpUtil = new HttpUtils();
        addImageList = new ArrayList<String>();
        initId();
        bz = this.getIntent().getStringExtra("bz");
        if (bz.equals("complaint"))
        {
            ((TextView) findViewById(R.id.textView1)).setText(getResources().getString(R.string.wyff_tab_2_bnt));
            bx_popupe.setHint(getResources().getString(R.string.take_ts));
            
//            getTypeLogic.getBxType("6", httpUtil);
            
        }
        else
        {
//            getTypeLogic.getTsType("6", httpUtil);
        }
        if(getIntent().getStringExtra("first") != null)
        {
            if(getIntent().getStringExtra("first").equals("first"))
            {
                addImage_bz = 1;
                doTakePhoto();
            }
        }
    }
    
    private void initId()
    {
        image_back = (ImageView) findViewById(R.id.image_back);
        bx_popupq = (TextView) findViewById(R.id.bx_popupq);
        bx_popupe = (EditText) findViewById(R.id.bx_popupe);
        bx_hm = (EditText) findViewById(R.id.bx_hm);
        addImageView1 = (ImageView) findViewById(R.id.iv_add_1);
        addImageView2 = (ImageView) findViewById(R.id.iv_add_2);
        addImageView3 = (ImageView) findViewById(R.id.iv_add_3);
        addImageView4 = (ImageView) findViewById(R.id.iv_add_4);
        bxtype_Layout = (LinearLayout) findViewById(R.id.bxtype_Layout);
        
        type_relative = (RelativeLayout) findViewById(R.id.type_relative);

        image_back.setOnClickListener(this);
        bx_popupq.setOnClickListener(this);
        addImageView1.setOnClickListener(this);
        addImageView2.setOnClickListener(this);
        addImageView3.setOnClickListener(this);
        addImageView4.setOnClickListener(this);
        
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.image_back:
                finish();
                break;
            case R.id.bx_popupq:
                if (bz.equals("complaint"))
                {
                    
                    
                    getTypeLogic.getBxType("6", httpUtil);
                    
                }
                else
                {
                    getTypeLogic.getTsType("6", httpUtil);
                }
                
                
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
            /*  case R.id.iv_video_1:
                  Intent intent_iv_video_1 = new Intent();
                  intent_iv_video_1.setClass(this, RecordActivity.class);
                  startActivityForResult(intent_iv_video_1, IV_VIDEO_BZ);
                  overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                  break;
              */
            /*  case R.id.type_sd:
                  type_relative.setVisibility(View.GONE);
                  typec = 1;
                  break;
              case R.id.type_jz:
                  type_relative.setVisibility(View.GONE);
                  typec = 2;
                  break;
              case R.id.type_hjws:
                  type_relative.setVisibility(View.GONE);
                  typec = 3;
                  break;*/
            default:
                break;
        }
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetTypeLogic.getInstance())
        {
            getTypeLogic = GetTypeLogic.getInstance();
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
    @SuppressLint("SimpleDateFormat")
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //用来保存图片       
        //        Bitmap bitMap = null;
        int degree = 0;
        //图片压缩 防止OOM
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;//图片宽高都为原来的二分之一，即图片为原来的四分之一  
        if (resultCode == 9999)
        {
            recordPath = data.getExtras().getString("recordPath");
            if (recordPath != null)
            {
                showToast(recordPath);
                iv_video_1.setBackgroundResource(R.drawable.chatting_setmode_msg_btn);
            }
            try
            {
                voiceStr = Base64.encode(BitMapUtil.getRecordData(recordPath));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            if (resultCode != -1)

            {
                return;
            }
            switch (requestCode)
            {
                
                case PHOTO_PICKED_WITH_DATA: //从本地选择图片        
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
                    @SuppressWarnings("deprecation")
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
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        //防止三星等手机拍照销毁当前Activity，保存拍的照片
        savedInstanceState.putParcelable("bitMap", bitMap);
        savedInstanceState.putString("filePath", filePath);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        
        super.onConfigurationChanged(newConfig);
    }
    
}
