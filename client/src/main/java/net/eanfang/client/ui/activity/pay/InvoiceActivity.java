package net.eanfang.client.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.util.StringUtils;
import com.yaf.base.entity.InvoiceEntity;
import com.yaf.base.entity.ReceiveAddressEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvoiceActivity extends BaseClientActivity {

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
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.tv_address_info)
    TextView tvAddressInfo;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    //地址回调 code
    private final int SELECT_ADDRESS_REQUEST_CODE = 1;

    private boolean isChecked = false;
    private Long orderId;
    private int orderType;
    private ReceiveAddressEntity mReceiveAddressEntity;
    private InvoiceEntity mInvoiceEntity;
    private Long mAddressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fapiao);
        ButterKnife.bind(this);

        setRightTitle("保存");
        setLeftBack();
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subInvoice();
            }
        });

        rgInvoiceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_pro) {
                    llPro.setVisibility(View.VISIBLE);
                    isChecked = true;
                } else {
                    llPro.setVisibility(View.GONE);
                    isChecked = false;
                }
            }
        });

        orderId = getIntent().getLongExtra("orderId", 0);
        orderType = getIntent().getIntExtra("orderType", 0);
        mInvoiceEntity = (InvoiceEntity) getIntent().getSerializableExtra("bean");
        if (mInvoiceEntity == null) {
            setTitle("填写发票信息");
        } else {
            setTitle("修改发票信息");
            mReceiveAddressEntity = mInvoiceEntity.getReceiveAddressEntity();
            mAddressId = mInvoiceEntity.getReceiveAddressEntity().getId();
            if (mInvoiceEntity.getType() == 0) {
                //普票
                initInvoiceViews();
            } else {
                initInvoiceProViews();
            }
        }
    }

    private void initInvoiceViews() {
        llPerson.setVisibility(View.VISIBLE);
        tvSelectAddress.setVisibility(View.GONE);
        tvAddressInfo.setText(mInvoiceEntity.getReceiveAddressEntity().getProvince() + mInvoiceEntity.getReceiveAddressEntity().getCity() + mInvoiceEntity.getReceiveAddressEntity().getCounty() + mInvoiceEntity.getReceiveAddressEntity().getAddress());
        tvPerson.setText(mInvoiceEntity.getReceiveAddressEntity().getName() + "        " + mInvoiceEntity.getReceiveAddressEntity().getPhone());
        etCompany.setText(mInvoiceEntity.getTitle());
        etNumber.setText(mInvoiceEntity.getDutyNo());


        rbNormal.setChecked(true);
        isChecked = false;
    }

    private void initInvoiceProViews() {

        llPerson.setVisibility(View.VISIBLE);
        tvSelectAddress.setVisibility(View.GONE);
        tvAddressInfo.setText(mInvoiceEntity.getReceiveAddressEntity().getProvince() + mInvoiceEntity.getReceiveAddressEntity().getCity() + mInvoiceEntity.getReceiveAddressEntity().getCounty() + mInvoiceEntity.getReceiveAddressEntity().getAddress());
        tvPerson.setText(mInvoiceEntity.getReceiveAddressEntity().getName() + "        " + mInvoiceEntity.getReceiveAddressEntity().getPhone());
        etCompany.setText(mInvoiceEntity.getTitle());
        etNumber.setText(mInvoiceEntity.getDutyNo());
        etAddress.setText(mInvoiceEntity.getAddress());
        etPhone.setText(mInvoiceEntity.getTelPhone());
        etBank.setText(mInvoiceEntity.getBank());
        etBankNumber.setText(mInvoiceEntity.getAccount());

        rbPro.setChecked(true);
        isChecked = true;

    }


    private void subInvoice() {
        InvoiceEntity invoice;
        if (!isChecked) {
            if (!checkInfo()) {
                return;
            }

            invoice = new InvoiceEntity();
            invoice.setOrderId(orderId);
            invoice.setReceiveId(mAddressId);
            invoice.setType(0);
            invoice.setOrderType(orderType);
            invoice.setTitle(etCompany.getText().toString().trim());
            invoice.setDutyNo(etNumber.getText().toString().trim());
            invoice.setReceiveAddressEntity(mReceiveAddressEntity);

        } else {
            if (!checkInfo()) {
                return;
            }
            if (!checkProInfo()) {
                return;
            }

            invoice = new InvoiceEntity();
            invoice.setOrderId(orderId);
            invoice.setReceiveId(mAddressId);
            invoice.setType(1);
            invoice.setOrderType(orderType);
            invoice.setTitle(etCompany.getText().toString().trim());
            invoice.setDutyNo(etNumber.getText().toString().trim());
            invoice.setAddress(etAddress.getText().toString().trim());
            invoice.setTelPhone(etPhone.getText().toString().trim());
            invoice.setBank(etBank.getText().toString().trim());
            invoice.setAccount(etBankNumber.getText().toString().trim());
            invoice.setReceiveAddressEntity(mReceiveAddressEntity);
        }


        Intent intent = new Intent();
        intent.putExtra("bean", invoice);
        setResult(RESULT_OK, intent);
        finish();

    }


    private boolean checkInfo() {

        if (llPerson.getVisibility() == View.GONE) {
            showToast("请选择邮寄地址");
            return false;
        }

        if (StringUtils.isEmpty(etCompany.getText().toString().trim())) {
            showToast("请填写公司名称");
            return false;
        }
        if (StringUtils.isEmpty(etNumber.getText().toString().trim())) {
            showToast("请填写税号");
            return false;
        }
        return true;
    }

    private boolean checkProInfo() {
        if (StringUtils.isEmpty(etAddress.getText().toString().trim())) {
            showToast("请填写单位地址");
            return false;
        }
        if (StringUtils.isEmpty(etPhone.getText().toString().trim())) {
            showToast("请填写单位座机");
            return false;
        }
        if (StringUtils.isEmpty(etBank.getText().toString().trim())) {
            showToast("请填写开户行");
            return false;
        }
        if (StringUtils.isEmpty(etBankNumber.getText().toString().trim())) {
            showToast("请填写银行账号");
            return false;
        }

        if (etBankNumber.length() > 18) {
            showToast("请填写正确的银行账号");
            return false;
        }

        return true;
    }

    @OnClick(R.id.ll_address)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivityForResult(intent, SELECT_ADDRESS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_ADDRESS_REQUEST_CODE) {
            mReceiveAddressEntity = (ReceiveAddressEntity) data.getSerializableExtra("bean");
            mAddressId = mReceiveAddressEntity.getId();
            llPerson.setVisibility(View.VISIBLE);
            tvSelectAddress.setVisibility(View.GONE);
            tvAddressInfo.setText(mReceiveAddressEntity.getProvince() + mReceiveAddressEntity.getCity() + mReceiveAddressEntity.getCounty() + mReceiveAddressEntity.getAddress());
            tvPerson.setText(mReceiveAddressEntity.getName() + "        " + mReceiveAddressEntity.getPhone());
        }
    }
}
