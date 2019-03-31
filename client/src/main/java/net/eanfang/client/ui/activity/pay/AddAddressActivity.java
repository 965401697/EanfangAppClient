package net.eanfang.client.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.ReceiveAddressEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressActivity extends BaseClientActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile_phone)
    EditText etMobilePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;


    //地址回调 code
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    private String province;
    private String city;
    private String county;
    private String address;
    private ReceiveAddressEntity addressEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);

        setLeftBack();

        setRightTitle("保存");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressEntity == null) {
                    subAddress();
                } else {
                    subEdit();
                }
            }
        });

        addressEntity = (ReceiveAddressEntity) getIntent().getSerializableExtra("bean");
        if (addressEntity == null) {
            setTitle("添加地址");
        } else {
            setTitle("修改地址");
            initViews();
        }
    }

    private void initViews() {
        etName.setText(addressEntity.getName());
        etMobilePhone.setText(addressEntity.getPhone());
        tvAddress.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getCity());
        etDetailAddress.setText(addressEntity.getAddress());
        etName.setText(addressEntity.getName());
    }

    private boolean checkInfo() {
        if (StringUtils.isEmpty(etName.getText().toString().trim())) {
            showToast("请填写姓名");
            return false;
        }
        if (StringUtils.isEmpty(etMobilePhone.getText().toString().trim())) {
            showToast("请填写电话");
            return false;
        }

        if (etMobilePhone.getText().length() < 11) {
            showToast("电话不合法");
            return false;
        }


        if (StringUtils.isEmpty(tvAddress.getText().toString().trim())) {
            showToast("请选择住址");
            return false;
        }
        if (StringUtils.isEmpty(etDetailAddress.getText().toString().trim())) {
            showToast("请填写详细地址");
            return false;
        }
        return true;
    }

    private void subAddress() {
        if (!checkInfo()) {
            return;
        }

        ReceiveAddressEntity receiveAddressEntity = new ReceiveAddressEntity();
        receiveAddressEntity.setUserId(EanfangApplication.get().getUserId());
        receiveAddressEntity.setName(etName.getText().toString().trim());
        receiveAddressEntity.setPhone(etMobilePhone.getText().toString().trim());
        receiveAddressEntity.setProvince(province);
        receiveAddressEntity.setCity(city);
        receiveAddressEntity.setCounty(county);
        receiveAddressEntity.setAddress(address);
        receiveAddressEntity.setIsDefault(0);

        EanfangHttp.post(NewApiService.CREATE_ADDRESS)
                .upJson(JSON.toJSONString(receiveAddressEntity))
                .execute(new EanfangCallback(AddAddressActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        showToast("添加成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    private void subEdit() {

        if (!checkInfo()) {
            return;
        }

        addressEntity.setUserId(EanfangApplication.get().getUserId());
        addressEntity.setName(etName.getText().toString().trim());
        addressEntity.setPhone(etMobilePhone.getText().toString().trim());
        if (!TextUtils.isEmpty(province)) {
            addressEntity.setProvince(province);
            addressEntity.setCity(city);
            addressEntity.setCounty(county);
            addressEntity.setAddress(address);
        }
        addressEntity.setIsDefault(addressEntity.getIsDefault());

        EanfangHttp.post(NewApiService.UPDATE_ADDRESS)
                .upJson(JSON.toJSONString(addressEntity))
                .execute(new EanfangCallback(AddAddressActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        showToast("修改成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }


    @OnClick(R.id.ll_address)
    public void onViewClicked() {
        Intent intent = new Intent(this, SelectAddressActivity.class);
        startActivityForResult(intent, REPAIR_ADDRESS_CALLBACK_CODE);
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
                String latitude = item.getLatitude().toString();
                String longitude = item.getLongitude().toString();
                province = item.getProvince();
                city = item.getCity();
                county = item.getAddress();
                address = item.getName();
                int mAreaId = Config.get().getBaseIdByCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()), 3, Constant.AREA);
                tvAddress.setText(province + "-" + city + "-" + county);

                //将选择的地址 取 显示值
                etDetailAddress.setText(address);
                break;

            default:
                break;

        }
    }
}
