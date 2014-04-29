package com.changhong.foundation.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.HttpUrl;
import com.changhong.foundation.baseapi.MsgWhat;
import com.changhong.foundation.logic.LoginLogic;
import com.changhong.foundation.logic.PersonInfoLogic;
import com.changhong.sdk.activity.SuperActivity;
import com.changhong.sdk.baseapi.Base64;
import com.changhong.sdk.baseapi.BitMapUtil;
import com.changhong.sdk.baseapi.StringUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PersonInfoActivity extends SuperActivity
{
    
    private HttpUtils httpUtils;
    
    private PersonInfoLogic logic;
    
    @ViewInject(R.id.tvNickName)
    private TextView tvNickName;
    
    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;
    
    @ViewInject(R.id.tvUserType)
    private TextView tvUserType;
    
    @ViewInject(R.id.rgSex)
    private RadioGroup rgSex;
    
    @ViewInject(R.id.tvBirthday)
    private TextView tvBirthday;
    
    @ViewInject(R.id.tvAddress)
    private TextView tvAddress;
    
    @ViewInject(R.id.rgCheck)
    private RadioGroup rgCheck;
    
    @ViewInject(R.id.rgFriend)
    private RadioGroup rgFriend;
    
    @ViewInject(R.id.imgHead)
    private ImageView imgHead;
    
    private BitmapUtils bitmaputils;
    
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    
    private File mCurrentPhotoFile;//
    
    private String fileName;// 图片名称
    
    private Bitmap bitMap;
    
    private String filePath = "";
    
    /**
     * SharedPreference中存储照相机文件名对应的key值
     */
    public static final String CAMERA_FILE_NAME_KEY = "cameraName";
    
    /**
     * 存储照相机拍摄照片名称的SharedPreference文件名
     */
    public static final String SP_CAMERA_INFO_FILE_NAME = "camera_info";
    
    //
    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 1001;
    
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 1002;
    
    //裁剪图片
    private static final int CROP_PICTURE = 1003;
    
    private static final int HEAD_WIDTH = 120;
    
    public void updateView()
    {
        tvNickName.setText(logic.user.getNickName());
        tvPhone.setText(logic.user.getMobile());
        if ("0".equals(logic.user.getRole()))
        {
            tvUserType.setText(getString(R.string.huzhu));
            if ("0".equals(logic.user.getJoinCheck()))
            {
                ((RadioButton) rgCheck.getChildAt(0)).setChecked(true);
            }
            else
            {
                ((RadioButton) rgCheck.getChildAt(1)).setChecked(true);
            }
        }
        else if ("1".equals(logic.user.getRole()))
        {
            rgCheck.setClickable(false);
            tvUserType.setText(getString(R.string.jiatingchengyuan));
        }
        else
        {
            rgCheck.setClickable(false);
            tvUserType.setText(getString(R.string.zuke));
        }
        
        if ("1".equals(logic.user.getSex()))
        {
            ((RadioButton) rgSex.getChildAt(0)).setChecked(true);
        }
        else
        {
            ((RadioButton) rgSex.getChildAt(1)).setChecked(true);
        }
        
        rgSex.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.rgMan:
                        logic.user.setSex("1");
                        break;
                    case R.id.rgWoman:
                        logic.user.setSex("0");
                        break;
                    default:
                        break;
                }
            }
        });
        
        tvBirthday.setText(logic.user.getBirth());
        tvAddress.setText(logic.user.getAddress());
        
        rgCheck.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.rgYes:
                        logic.user.setJoinCheck("0");
                        break;
                    case R.id.rgNo:
                        logic.user.setJoinCheck("1");
                        break;
                    default:
                        break;
                }
            }
        });
        
        if ("0".equals(logic.user.getJoinFriend()))
        {
            ((RadioButton) rgFriend.getChildAt(0)).setChecked(true);
        }
        else
        {
            ((RadioButton) rgFriend.getChildAt(1)).setChecked(true);
        }
        
        rgFriend.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.rgFriendYes:
                        logic.user.setJoinFriend("0");
                        break;
                    case R.id.rgFriendNo:
                        logic.user.setJoinFriend("1");
                        break;
                    default:
                        break;
                }
            }
        });
        bitmaputils.display(imgHead,
                HttpUrl.OSS + logic.user.getHeadUrl(),
                new BitmapLoadCallBack<View>()
                {
                    
                    @Override
                    public void onLoadCompleted(View container, String uri,
                            Bitmap bitmap, BitmapDisplayConfig config,
                            BitmapLoadFrom from)
                    {
                        imgHead.setImageBitmap(BitMapUtil.toRoundCorner(bitmap,
                                90));
                    }
                    
                    @Override
                    public void onLoadFailed(View container, String uri,
                            Drawable drawable)
                    {
                        imgHead.setImageDrawable(drawable);
                    }
                });
        
    }
    
    @Override
    public void initData()
    {
        logic = PersonInfoLogic.getInstance();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.personinfo_layout);
        ViewUtils.inject(this);
        bitmaputils = new BitmapUtils(getBaseContext());
        bitmaputils.configDefaultLoadingImage(R.drawable.login_new_image);
        bitmaputils.configDefaultLoadFailedImage(R.drawable.login_new_image);
        bitmaputils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        requestPersonInfo();
    }
    
    OnDismissListener dismiss = new OnDismissListener()
    {
        
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    public void requestPersonInfo()
    {
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        showProcessDialog(dismiss);
        logic.requestPersonInfo(LoginLogic.getInstance().user.getAccount(),
                httpUtils);
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MsgWhat.MSGWHAT_PERSON_INFO_SUCCESS:
            {
                updateView();
                break;
            }
            case MsgWhat.MSGWHAT_UPDATE_PERSON_INFO_SUCCESS:
            {
                showToast(getString(R.string.update_info_success));
                LoginLogic.getInstance().user.setNickName(logic.user.getNickName());
                LoginLogic.getInstance().user.setBirth(logic.user.getBirth());
                LoginLogic.getInstance().user.setSex(logic.user.getSex());
                LoginLogic.getInstance().user.setMobile(logic.user.getMobile());
                finish();
                break;
            }
            default:
                break;
        }
        
        super.handleMsg(msg);
    }
    
    @Override
    public void clearData()
    {
        logic.clear();
    }
    
    @OnClick(R.id.img_back)
    public void imgBackClick(View view)
    {
        finish();
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
    
    PopupWindow popupWindowHead;
    
    @OnClick(R.id.headFrame)
    public void headFrameClick(View view)
    {
        View photoSelect = getLayoutInflater().inflate(R.layout.photo_select,
                null);
        popupWindowHead = new PopupWindow(photoSelect,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindowHead.setBackgroundDrawable(new BitmapDrawable());
        popupWindowHead.setTouchable(true);
        popupWindowHead.setOutsideTouchable(true);
        
        popupWindowHead.showAtLocation(findViewById(R.id.mainFrame),
                Gravity.BOTTOM,
                0,
                0);
        photoSelect.findViewById(R.id.take_photo)
                .setOnClickListener(new OnClickListener()
                {
                    
                    @Override
                    public void onClick(View v)
                    {
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
        {
            // 判断是否有SD卡
            //如果想自定义存放地址 将下面的放开
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
    
    private Intent getTakePickIntent(File f)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //f：你要存放文件的地址
        //注意：这里有一点要注意的，如果你设置了intent的MediaStore.EXTRA_OUTPUT参数，
        //那么在OnActivityResult里面获取到的intent为null，反之如果没有设置的时候，intent是有数据。
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }
    
    // 用当前时间给取得的图片命名
    private String getPhotoFileName()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        
        String photoName = dateFormat.format(date) + ".jpg";
        return photoName;
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CAMERA_WITH_DATA:
            {
                Uri uri = Uri.fromFile(mCurrentPhotoFile);
                cropImage(uri, HEAD_WIDTH, HEAD_WIDTH, CROP_PICTURE);
                break;
            }
            case PHOTO_PICKED_WITH_DATA:
            {
                Uri uri = data.getData();
                cropImage(uri, HEAD_WIDTH, HEAD_WIDTH, CROP_PICTURE);
                break;
            }
            case CROP_PICTURE:
            {
                if (null != data)
                {
                    //从intent中获取图片
                    final Bitmap headBitmap = (Bitmap) data.getExtras()
                            .get("data");
                    if (headBitmap == null)
                    {
                        return;
                    }
                    updateHead(headBitmap);
                }
                break;
            }
            
            default:
                break;
        }
    }
    
    public void updateHead(Bitmap headBitmap)
    {
        bitMap = headBitmap;
        imgHead.setImageBitmap(BitMapUtil.toRoundCorner(headBitmap, 90));
    }
    
    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode)
    {
        //裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        //裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //设置是否允许拉伸
        intent.putExtra("scale", true);
        //图片格式
        intent.putExtra("outputFormat", "JPEG");
        //        intent.putExtra("noFaceDetection", true);
        // 如果要在给定的uri中获取图片，则必须设置为true，如果设置为false，那么便不会在给定的uri中获取到裁剪的图片
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }
    
    @OnClick(R.id.tvBirthday)
    public void tvBirthdayClick(View view)
    {
        
        View dateView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.time_layout, null);
        final DatePicker datePicker = (DatePicker) dateView.findViewById(R.id.datePicker);
        
        if (!StringUtils.isEmpty(logic.user.getBirth())
                && logic.user.getBirth().contains("-"))
        {
            String[] split = logic.user.getBirth().split("-");
            int year = Integer.valueOf(split[0]);
            int month = Integer.valueOf(split[1]);
            int day = Integer.valueOf(split[2]);
            datePicker.init(year, month - 1, day, null);
        }
        
        Button cancel = (Button) dateView.findViewById(R.id.btnCancel);
        Button confirm = (Button) dateView.findViewById(R.id.btnConfirm);
        cancel.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                dismissDialog();
            }
        });
        confirm.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                tvBirthday.setText(datePicker.getYear() + "-"
                        + (datePicker.getMonth() + 1) + "-"
                        + datePicker.getDayOfMonth());
                dismissDialog();
            }
        });
        showDialog(dateView, false, R.style.MyDialog);
    }
    
    @OnClick(R.id.btnSave)
    public void btnSave(View view)
    {
        if (!checkIsEmpty())
        {
            logic.user.setNickName(tvNickName.getText().toString());
            logic.user.setMobile(tvPhone.getText().toString());
            logic.user.setBirth(tvBirthday.getText().toString());
            String res = "";
            if (null != bitMap)
            {
                res = Base64.encode(BitMapUtil.getBitmapData(bitMap));
            }
            logic.user.setHeadStr(res);
            requestUpdate();
        }
    }
    
    public boolean checkIsEmpty()
    {
        if (checkEditTextIsEmpty())
        {
            return true;
        }
        if (StringUtils.isEmpty(tvBirthday.getText().toString()))
        {
            showToast(getString(R.string.please_input_all));
            return true;
        }
        return false;
    }
    
    public void requestUpdate()
    {
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        showProcessDialog(dismiss);
        logic.user.setAccount(LoginLogic.getInstance().user.getAccount());
        logic.user.setUid(LoginLogic.getInstance().user.getUid());
        logic.requestUpdateInfo(logic.user, httpUtils);
    }
}
