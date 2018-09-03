package net.eanfang.client.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvoiceActivity extends BaseClientActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile_phone)
    EditText etMobilePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.rb_normal)
    RadioButton rbNormal;
    @BindView(R.id.rb_pro)
    RadioButton rbPro;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.et_bank_number)
    EditText etBankNumber;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.rg_invoice_type)
    RadioGroup rgInvoiceType;
    @BindView(R.id.ll_pro)
    LinearLayout llPro;

    //地址回调 code
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fapiao);
        ButterKnife.bind(this);
        setTitle("填写发票信息");
        setRightTitle("保存");
        setLeftBack();
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rgInvoiceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_pro) {
                    llPro.setVisibility(View.VISIBLE);
                } else {
                    llPro.setVisibility(View.GONE);
                }
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
                String province = item.getProvince();
                String city = item.getCity();
                String county = item.getAddress();
                String address = item.getName();
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
