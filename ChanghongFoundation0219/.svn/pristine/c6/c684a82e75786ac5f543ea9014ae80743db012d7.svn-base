package com.changhong.foundation.activity;

import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.changhong.foundation.R;
import com.changhong.foundation.baseapi.Constant;
import com.changhong.foundation.entity.Address;
import com.changhong.foundation.entity.RegisterInfo;
import com.changhong.foundation.logic.RegisterLogic;
import com.changhong.sdk.baseapi.StringUtils;
import com.changhong.sdk.baseapi.ViewHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RegisterActivity extends BaseActivity implements OnClickListener
{
    
    @ViewInject(R.id.frame_huzhu)
    private LinearLayout frame_huzhu;
    
    @ViewInject(R.id.frame_register)
    private LinearLayout frame_register;
    
    @ViewInject(R.id.radio_group)
    private RadioGroup radio_group;
    
    private Dialog listDialog;
    
    private RegisterLogic logic;
    
    //当前选中的地址信息
    private RegisterInfo registerInfo;
    
    @ViewInject(R.id.tvProvinceFrame)
    private TextView tvProvince;
    
    @ViewInject(R.id.tvCityFrame)
    private TextView tvCity;
    
    @ViewInject(R.id.tvCommunityFrame)
    private TextView tvCommunity;
    
    @ViewInject(R.id.tvZutuanFrame)
    private TextView tvZutuan;
    
    @ViewInject(R.id.tvBuildingFrame)
    private TextView tvBuilding;
    
    @ViewInject(R.id.tvUnitFrame)
    private TextView tvUnit;
    
    @ViewInject(R.id.tvDoorPlateFrame)
    private TextView tvDoorPlate;
    
    @ViewInject(R.id.etPhone)
    private EditText etPhone;
    
    @ViewInject(R.id.etValicode)
    private EditText etValicode;
    
    @ViewInject(R.id.etNickName)
    private EditText etNickName;
    
    @ViewInject(R.id.etReallyName)
    private EditText etReallyName;
    
    @ViewInject(R.id.etIdentityCard)
    private EditText etIdentityCard;
    
    @ViewInject(R.id.etHouseholderPhone)
    private EditText etHouseholderPhone;
    
    @ViewInject(R.id.etPwd)
    private EditText etPwd;
    
    private HttpUtils httpUtils;
    
    private String role = "0";
    
    @Override
    public void initData()
    {
        logic = RegisterLogic.getInstance();
        registerInfo = new RegisterInfo();
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        setContentView(R.layout.register_layout);
        setSlideMenu(SlidingMenu.TOUCHMODE_NONE);
        ViewUtils.inject(this);
        radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.rd_huzhu)
                {
                    //                    frame_huzhu.setVisibility(View.GONE);
                    etHouseholderPhone.setText("");
                    etHouseholderPhone.setEnabled(false);
                    etHouseholderPhone.setHintTextColor(getResources().getColor(R.color.gray_font_unread));
                    role = "0";
                }
                else
                {
                    if (checkedId == R.id.rd_jiatingchengyuan)
                    {
                        role = "1";
                    }
                    else
                    {
                        role = "2";
                    }
                    etHouseholderPhone.setEnabled(true);
                    etHouseholderPhone.setHintTextColor(getResources().getColor(R.color.gray_font));
                }
            }
        });
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
    
    @OnClick(R.id.btnRegister)
    public void btnRegisterClick(View view)
    {
        if (checkEditTextIsEmpty())
        {
            return;
        }
        if (checkAddressIsEmpty(7))
        {
            return;
        }
        RegisterInfo rInfo = new RegisterInfo();
        rInfo.setRole(role);
        rInfo.setPhoneNumber(etPhone.getText().toString());
        rInfo.setValicode(etValicode.getText().toString());
        rInfo.setNickName(etNickName.getText().toString());
        rInfo.setReallyName(etReallyName.getText().toString());
        rInfo.setIdentityCard(etIdentityCard.getText().toString());
        rInfo.setHouseholderPhone(etHouseholderPhone.getText().toString());
        rInfo.setPwd(etPwd.getText().toString());
        rInfo.setProvinceId(logic.province.getId());
        rInfo.setCityId(logic.city.getId());
        rInfo.setCommunityId(logic.community.getId());
        rInfo.setZutuanId(logic.zutuan.getId());
        rInfo.setBuildingId(logic.building.getId());
        rInfo.setUnitId(logic.unit.getId());
        rInfo.setDoorPlateId(logic.doorPlate.getId());
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestRegister(rInfo, httpUtils);
    }
    
    @OnClick(R.id.tvProvinceFrame)
    public void tvProvinceClick(View view)
    {
        if (null != logic.addressList && logic.addressList.size() > 0)
        {
            showAreaListDialog(Constant.PROVINCE, logic.addressList);
            return;
        }
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestProvince(httpUtils);
    }
    
    @OnClick(R.id.tvCityFrame)
    public void tvCityClick(View view)
    {
        if (checkAddressIsEmpty(1))
        {
            return;
        }
        if (null != logic.cityMap.get(logic.province.getId())
                && logic.cityMap.get(logic.province.getId()).size() > 0)
        {
            showAreaListDialog(Constant.CITY,
                    logic.cityMap.get(logic.province.getId()));
            return;
        }
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestCity(logic.province.getId(), httpUtils);
        
    }
    
    @OnClick(R.id.tvCommunityFrame)
    public void tvCommunityClick(View view)
    {
        if (checkAddressIsEmpty(2))
        {
            return;
        }
        if (null != logic.communityMap.get(logic.city.getId())
                && logic.communityMap.get(logic.city.getId()).size() > 0)
        {
            showAreaListDialog(Constant.COMMUNITY,
                    logic.communityMap.get(logic.city.getId()));
            return;
        }
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestCommunity(logic.city.getId(), httpUtils);
    }
    
    @OnClick(R.id.tvZutuanFrame)
    public void tvZutuanClick(View view)
    {
        if (checkAddressIsEmpty(3))
        {
            return;
        }
        if (null != logic.zutuanMap.get(logic.community.getId())
                && logic.zutuanMap.get(logic.community.getId()).size() > 0)
        {
            showAreaListDialog(Constant.ZUTUAN,
                    logic.zutuanMap.get(logic.community.getId()));
            return;
        }
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestZutuan(logic.community.getId(), httpUtils);
    }
    
    @OnClick(R.id.tvBuildingFrame)
    public void tvBuildingClick(View view)
    {
        if (checkAddressIsEmpty(4))
        {
            return;
        }
        if (null != logic.buildingMap.get(logic.zutuan.getId())
                && logic.buildingMap.get(logic.zutuan.getId()).size() > 0)
        {
            showAreaListDialog(Constant.BUILDING,
                    logic.buildingMap.get(logic.zutuan.getId()));
            return;
        }
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestBuilding(logic.zutuan.getId(), httpUtils);
    }
    
    @OnClick(R.id.tvUnitFrame)
    public void tvUnitClick(View view)
    {
        if (checkAddressIsEmpty(5))
        {
            return;
        }
        if (null != logic.unitMap.get(logic.building.getId())
                && logic.unitMap.get(logic.building.getId()).size() > 0)
        {
            showAreaListDialog(Constant.UNIT,
                    logic.unitMap.get(logic.building.getId()));
        }
        else
        {
            showToast(getString(R.string.date_empty));
        }
    }
    
    @OnClick(R.id.tvDoorPlateFrame)
    public void tvDoorPlateClick(View view)
    {
        
        if (checkAddressIsEmpty(6))
        {
            return;
        }
        
        if (null != logic.doorPlateMap.get(logic.building.getId()
                + RegisterLogic.JOIN_SYMBOL + logic.unit.getId())
                && logic.doorPlateMap.get(logic.building.getId()
                        + RegisterLogic.JOIN_SYMBOL + logic.unit.getId())
                        .size() > 0)
        {
            showAreaListDialog(Constant.DOORPLATE,
                    logic.doorPlateMap.get(logic.building.getId()
                            + RegisterLogic.JOIN_SYMBOL + logic.unit.getId()));
            return;
        }
        
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestDoorplate(logic.building.getId(),
                logic.unit.getId(),
                httpUtils);
        
    }
    
    /**
     * 
     * 地方列表
     * @param type 0省份1市2区3楼栋4单元5门牌
     */
    public void showAreaListDialog(final String type, List<Address> list)
    {
        if (null == list || list.size() == 0)
        {
            showToast(getString(R.string.date_empty));
            return;
        }
        View areaView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.arealist_layout, null);
        ListView areaListView = (ListView) areaView.findViewById(R.id.areaListView);
        listDialog = getDialog(areaView, false, R.style.MyDialog);
        final Adapter adapter = new Adapter(list);
        areaListView.setAdapter(adapter);
        areaListView.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                
                updateView(type, adapter.getItem(position));
                listDialog.dismiss();
                listDialog = null;
            }
        });
        listDialog.show();
    }
    
    private void updateView(String type, Address address)
    {
        if (Constant.PROVINCE.equals(type))
        {
            if (!address.getId().equals(logic.province.getId()))
            {
                logic.province = address;
                registerInfo.setProvinceId(address.getId());
                tvProvince.setText(address.getName());
                tvCity.setText("");
                tvCommunity.setText("");
                tvZutuan.setText("");
                tvBuilding.setText("");
                tvUnit.setText("");
                tvDoorPlate.setText("");
            }
        }
        else if (Constant.CITY.equals(type))
        {
            if (!address.getId().equals(logic.city.getId()))
            {
                logic.city = address;
                registerInfo.setCityId(address.getId());
                tvCity.setText(address.getName());
                tvCommunity.setText("");
                tvZutuan.setText("");
                tvBuilding.setText("");
                tvUnit.setText("");
                tvDoorPlate.setText("");
            }
        }
        else if (Constant.COMMUNITY.equals(type))
        {
            if (!address.getId().equals(logic.community.getId()))
            {
                logic.community = address;
                tvCommunity.setText(address.getName());
                tvZutuan.setText("");
                tvBuilding.setText("");
                tvUnit.setText("");
                tvDoorPlate.setText("");
            }
            
        }
        else if (Constant.ZUTUAN.equals(type))
        {
            
            if (!address.getId().equals(logic.zutuan.getId()))
            {
                logic.zutuan = address;
                tvZutuan.setText(address.getName());
                tvBuilding.setText("");
                tvUnit.setText("");
                tvDoorPlate.setText("");
            }
        }
        else if (Constant.BUILDING.equals(type))
        {
            
            if (!address.getId().equals(logic.building.getId()))
            {
                logic.building = address;
                tvBuilding.setText(address.getName());
                tvUnit.setText("");
                tvDoorPlate.setText("");
            }
            
        }
        else if (Constant.UNIT.equals(type))
        {
            
            if (!address.getId().equals(logic.unit.getId()))
            {
                logic.unit = address;
                tvUnit.setText(address.getName());
                tvDoorPlate.setText("");
            }
            
        }
        else
        {
            if (!address.getId().equals(logic.doorPlate.getId()))
            {
                logic.doorPlate = address;
                tvDoorPlate.setText(address.getName());
            }
        }
    }
    
    class Adapter extends BaseAdapter
    {
        List<Address> list;
        
        public Adapter(List<Address> list)
        {
            this.list = list;
        }
        
        @Override
        public int getCount()
        {
            return list.size();
        }
        
        @Override
        public Address getItem(int position)
        {
            return list.get(position);
        }
        
        @Override
        public long getItemId(int position)
        {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.area_item_layout, null);
            }
            TextView tvName = ViewHolder.get(convertView, R.id.tvName);
            tvName.setText(getItem(position).getName());
            return convertView;
        }
    }
    
    @OnClick(R.id.btnGetValicode)
    public void btnGetValicodeClick(View view)
    {
        if (StringUtils.isEmpty(etPhone.getText().toString()))
        {
            showToast(etPhone.getHint().toString());
            return;
        }
        
        showProcessDialog(dismiss);
        httpUtils = new HttpUtils();
        logic.setData(mHandler);
        logic.requestGetValicode(etPhone.getText().toString().trim(),
                Constant.TYPE_REGISTER_VALICEDE,
                httpUtils);
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            logic.stopRequest();
        }
    };
    
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case MSGWHAT_LOGIN_SUCCESS:
            {
                break;
            }
            case MSGWHAT_REGISTER_GETVALICODE_SUCCESS:
            {
                showToast(getString(R.string.get_valicode_success));
                break;
            }
            case MSGWHAT_GET_PROVINCE_SUCCESS:
            {
                showAreaListDialog(Constant.PROVINCE, logic.addressList);
                break;
            }
            case MSGWHAT_GET_CITY_SUCCESS:
            {
                showAreaListDialog(Constant.CITY,
                        logic.cityMap.get(logic.province.getId()));
                break;
            }
            case MSGWHAT_GET_COMMUNITY_SUCCESS:
            {
                showAreaListDialog(Constant.COMMUNITY,
                        logic.communityMap.get(logic.city.getId()));
                break;
            }
            case MSGWHAT_GET_ZUTUAN_SUCCESS:
            {
                showAreaListDialog(Constant.ZUTUAN,
                        logic.zutuanMap.get(logic.community.getId()));
                break;
            }
            case MSGWHAT_GET_BUILDING_SUCCESS:
            {
                showAreaListDialog(Constant.BUILDING,
                        logic.buildingMap.get(logic.zutuan.getId()));
                break;
            }
            case MSGWHAT_GET_DOORPLATE_SUCCESS:
            {
                showAreaListDialog(Constant.DOORPLATE,
                        logic.doorPlateMap.get(logic.building.getId()
                                + RegisterLogic.JOIN_SYMBOL
                                + logic.unit.getId()));
                break;
            }
            case MSGWHAT_DATE_EMPTY:
            {
                showToast(getString(R.string.date_empty));
                break;
            }
            case MSGWHAT_REGISTER_SUCCESS:
            {
                showToast(getString(R.string.register_success));
                finish();
                break;
            }
            case MSGWHAT_USER_HAS_EXIST_:
            {
                showToast(getString(R.string.USER_HAS_EXIST));
                break;
            }
            case MSGWHAT_REGISTER_AGIAN:
            {
                showToast(getString(R.string.register_again));
                break;
            }
            case MSGWHAT_USERINFO_NOT_EXIST:
            {
                showToast(getString(R.string.userinfo_not_exist));
                break;
            }
            case MSGWHAT_HOUSEHOLDER_HAS_EXIST_:
            {
                showToast(getString(R.string.householder_has_exist));
                break;
            }
            case MSGWHAT_HOUSEHOLDER_PHONE_ERROR:
            {
                showToast(getString(R.string.householder_phone_error));
                break;
            }
            case MSGWHAT_HOUSEHOLDER_NOT_EXIST_:
            {
                showToast(getString(R.string.householder_not_exist));
                break;
            }
            case MSGWHAT_HOUSEHOLDER_NEED_REGISTER_:
            {
                showToast(getString(R.string.householder_need_register));
                break;
            }
            default:
                break;
        }
        super.handleMsg(msg);
    };
    
    public boolean checkAddressIsEmpty(int count)
    {
        switch (count)
        {
            case 1:
            {
                return checkProvince();
            }
            case 2:
            {
                return checkProvince() || checkCity();
            }
            case 3:
            {
                return checkProvince() || checkCity() || checkCommunity();
            }
            case 4:
            {
                return checkProvince() || checkCity() || checkCommunity()
                        || checkZutuan();
            }
            case 5:
            {
                return checkProvince() || checkCity() || checkCommunity()
                        || checkZutuan() || checkBuilding();
            }
            case 6:
            {
                return checkProvince() || checkCity() || checkCommunity()
                        || checkZutuan() || checkBuilding() || checkBuilding()
                        || checkUnit();
            }
            default:
            {
                return checkProvince() || checkCity() || checkCommunity()
                        || checkZutuan() || checkBuilding() || checkBuilding()
                        || checkUnit() || checkDoorPlate();
            }
        }
        
    }
    
    /**
     * 
     * 检查省是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkProvince()
    {
        if (StringUtils.isEmpty(tvProvince.getText().toString()))
        {
            showToast(getString(R.string.please_select_province));
            return true;
        }
        return false;
        
    }
    
    /**
     * 
     * 检查市是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkCity()
    {
        if (StringUtils.isEmpty(tvCity.getText().toString()))
        {
            showToast(getString(R.string.please_select_city));
            return true;
        }
        return false;
        
    }
    
    /**
     * 
     * 检查区是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkCommunity()
    {
        if (StringUtils.isEmpty(tvCommunity.getText().toString()))
        {
            showToast(getString(R.string.please_select)
                    + getString(R.string.please_select_community));
            return true;
        }
        return false;
        
    }
    
    /**
     * 
     * 检查组团是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkZutuan()
    {
        if (StringUtils.isEmpty(tvZutuan.getText().toString()))
        {
            showToast(getString(R.string.please_select)
                    + getString(R.string.please_select_zutuan));
            return true;
        }
        return false;
        
    }
    
    /**
     * 
     * 检查楼栋是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkBuilding()
    {
        if (StringUtils.isEmpty(tvBuilding.getText().toString()))
        {
            showToast(getString(R.string.please_select)
                    + getString(R.string.please_select_building));
            return true;
        }
        return false;
        
    }
    
    /**
     * 
     * 检查省是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkUnit()
    {
        if (StringUtils.isEmpty(tvUnit.getText().toString()))
        {
            showToast(getString(R.string.please_select)
                    + getString(R.string.please_select_unit));
            return true;
        }
        return false;
        
    }
    
    /**
     * 
     * 检查省是否为空
     * [功能详细描述]
     * @return
     */
    public boolean checkDoorPlate()
    {
        if (StringUtils.isEmpty(tvDoorPlate.getText().toString()))
        {
            showToast(getString(R.string.please_select)
                    + getString(R.string.please_select_doorplate));
            return true;
        }
        return false;
        
    }
}
