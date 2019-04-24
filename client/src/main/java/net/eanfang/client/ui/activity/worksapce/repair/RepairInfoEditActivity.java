package net.eanfang.client.ui.activity.worksapce.repair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/4/23
 * @description 报修个人信息页面 编辑页面
 */

public class RepairInfoEditActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    /**
     * 报修地址回调 code
     */
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    /**
     * 地址信息 省市区 详细地址
     */
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_addressDetail)
    TextView tvAddressDetail;
    /**
     * 性别
     */
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_companyName)
    EditText etCompanyName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    /**
     * 默认是男
     */
    private int mSex = 1;


    private String latitude = "";
    private String longitude = "";
    private String province = "";
    private String city = "";
    private String county = "";
    private String address = "";

    private RepairPersonalInfoEntity repairPersonalInfoEntity;

    /**
     * 是否是編輯
     */
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_info_edit);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("编辑用户信息");
    }

    private void initData() {
        repairPersonalInfoEntity = new RepairPersonalInfoEntity();
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        repairPersonalInfoEntity = (RepairPersonalInfoEntity) getIntent().getSerializableExtra("infoEntity");
        if (isEdit) {
            doFillBean();
        }
    }


    public void doCreatePersonalInfo() {
        String mUrl;
        if (isEdit) {
            mUrl = NewApiService.REPAIR_PRESONAL_INFO_UPDATAE;
        } else {
            mUrl = NewApiService.REPAIR_PRESONAL_INFO_ADD;
        }
        EanfangHttp.post(mUrl)
                .upJson(JsonUtils.obj2String(fillBean()))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        setResult(RESULT_OK);
                        finishSelf();
                        showToast("创建成功");
                    }
                });
    }

    /**
     * 字段填写检查约束
     */
    private boolean checkInfo() {

        if ("请选择地址".equals(etCompanyName.getText().toString().trim())) {
            showToast("请选择单位名称");
            return false;
        }
        if ("请选择地址".equals(tvAddress.getText().toString().trim())) {
            showToast("请选择地址");
            return false;
        }
        String placeCode = Config.get().getAreaCodeByName(city, county);
        if (StringUtils.isEmpty(placeCode)) {
            showToast("请重新选择地址");
            return false;
        }
        if (StringUtils.isEmpty(Config.get().getBaseIdByCode(placeCode, 3, Constant.AREA) + "")) {
            showToast("请重新选择地址");
            return false;
        }

        if (StringUtils.isEmpty(etCompanyName.getText().toString().trim())) {
            showToast("请输入联系人姓名");
            return false;
        }
        if (StringUtils.isEmpty(etPhone.getText().toString().trim())) {
            showToast("请输入电话");
            return false;
        }
        return true;
    }

    private RepairPersonalInfoEntity fillBean() {
        repairPersonalInfoEntity.setAccId(EanfangApplication.get().getAccId());
        repairPersonalInfoEntity.setName(etName.getText().toString().trim());
        repairPersonalInfoEntity.setPhone(etPhone.getText().toString().trim());
        repairPersonalInfoEntity.setLatitude(latitude);
        repairPersonalInfoEntity.setLongitude(longitude);
        repairPersonalInfoEntity.setPlaceCode(Config.get().getAreaCodeByName(city, county));
        repairPersonalInfoEntity.setPlaceId(Config.get().getBaseIdByCode(repairPersonalInfoEntity.getPlaceCode(), 3, Constant.AREA));
        repairPersonalInfoEntity.setConmpanyName(etCompanyName.getText().toString().trim());
        repairPersonalInfoEntity.setGender(mSex);
        repairPersonalInfoEntity.setAddress(tvAddressDetail.getText().toString().trim());
        repairPersonalInfoEntity.setSelectAddress("测试家");
        return repairPersonalInfoEntity;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_man:
                mSex = 1;
                break;
            case R.id.rb_woman:
                mSex = 0;
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.ll_selectAddress, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择地址
            case R.id.ll_selectAddress:
                JumpItent.jump(this, SelectAddressActivity.class, REPAIR_ADDRESS_CALLBACK_CODE);
                break;
            case R.id.tv_next:
                new MultiClickListener(RepairInfoEditActivity.this, this::checkInfo, this::doCreatePersonalInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 编辑数据回显
     */
    @SuppressLint("SetTextI18n")
    private void doFillBean() {
        etCompanyName.setText(repairPersonalInfoEntity.getConmpanyName());
        tvAddress.setText(repairPersonalInfoEntity.getProvince() + "-" + repairPersonalInfoEntity.getCity() + "-" + repairPersonalInfoEntity.getCounty());
        tvAddressDetail.setText(repairPersonalInfoEntity.getAddress());
        etName.setText(repairPersonalInfoEntity.getName());
        etPhone.setText(repairPersonalInfoEntity.getPhone());
        rbMan.setChecked(repairPersonalInfoEntity.getGender() == 1);
        rbWoman.setChecked(repairPersonalInfoEntity.getGender() == 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REPAIR_ADDRESS_CALLBACK_CODE:
                SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
                Log.e("address", item.toString());
                latitude = item.getLatitude().toString();
                longitude = item.getLongitude().toString();
                province = item.getProvince();
                city = item.getCity();
                county = item.getAddress();
                address = item.getName();
                tvAddress.setText(province + "-" + city + "-" + county);
                //将选择的地址 取 显示值
                tvAddressDetail.setText(address);
                repairPersonalInfoEntity.setCity(item.getCity());
                repairPersonalInfoEntity.setCounty(item.getAddress());
                break;
            default:
                break;

        }
    }
}
