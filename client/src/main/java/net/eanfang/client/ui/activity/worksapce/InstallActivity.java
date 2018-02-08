package net.eanfang.client.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.InstallOrderConfirmBean;
import com.eanfang.model.LoginBean;
import com.eanfang.model.Message;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:35
 * @email houzhongzhou@yeah.net
 * @desc 我要报装
 */

public class InstallActivity extends BaseClientActivity {
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.ll_business)
    LinearLayout llBusiness;
    @BindView(R.id.tv_project_time)
    TextView tvProjectTime;
    @BindView(R.id.LL_project_time)
    LinearLayout LLProjectTime;
    @BindView(R.id.tv_budget)
    TextView tvBudget;
    @BindView(R.id.ll_budget)
    LinearLayout llBudget;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

    private String latitude;
    private String longitude;
    //选中的业务大类的uid
    private String bugOneUid;

    private String city;
    private String zone;

    private static int INSTALL_REQUEST_CODE = 102;

    public static void jumpActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, InstallActivity.class);
        ((BaseClientActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        ButterKnife.bind(this);

        initView();
        initData();
        setListener();
    }


    private void initView() {

        setTitle("我要报装");
        setRightTitle("下一步");
        ivLeft.setOnClickListener(v -> giveUp());
        //下一步事件
        setRightTitleOnClickListener((v) -> {
            submit();
        });

    }

    private void initData() {
        LoginBean user = EanfangApplication.getApplication().getUser();
        String name = "";
        if (StringUtils.isEmpty(user.getAccount().getDefaultUser().getCompanyEntity().getOrgName())) {
            name = user.getAccount().getRealName();
        } else {
            name = user.getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        }
        //如果公司名称为空 则取当前登陆人的公司
        if (StringUtils.isEmpty(etCompany.getText())) {
            etCompany.setText(name);
        }
        etContact.setText(user.getAccount().getRealName());
        etPhone.setText(user.getAccount().getMobile());
    }

    private void setListener() {
        //选择地址
        llAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(InstallActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, INSTALL_REQUEST_CODE);
        });
        //回复时限选择
        llTime.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvTime, GetConstDataUtils.getRevertList());
        });
        //业务类型一级
        llBusiness.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvBusiness, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
        });
        //预计工期
        LLProjectTime.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvProjectTime, GetConstDataUtils.getPredictList());
        });
        //预算范围
        llBudget.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvBudget, GetConstDataUtils.getBudgetList());
        });

    }

    private void submit() {
        // validate
        String company = etCompany.getText().toString().trim();
        if (TextUtils.isEmpty(company)) {
            Toast.makeText(this, "单位名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String bigAddress = tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(bigAddress)) {
            Toast.makeText(this, "报装地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String contact = etContact.getText().toString().trim();
        if (TextUtils.isEmpty(contact)) {
            Toast.makeText(this, "联系人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String business = tvBusiness.getText().toString().trim();
        if (TextUtils.isEmpty(business)) {
            Toast.makeText(this, "业务类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        String desc = etDesc.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "需求描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.length() > 50) {
            showToast("需求描述不能超过50个字");
            return;
        }
        String budget = tvBudget.getText().toString().trim();
        String revertime = tvTime.getText().toString().trim();
        String predictTime = tvProjectTime.getText().toString().trim();

        InstallOrderConfirmBean installOrderConfirmBean = new InstallOrderConfirmBean();
        installOrderConfirmBean.setLatitude(latitude);
        installOrderConfirmBean.setLongitude(longitude);
        installOrderConfirmBean.setClientCompanyName(company);
        installOrderConfirmBean.setZone(Config.get().getAreaCodeByName(city, zone));
        installOrderConfirmBean.setZoneId(Long.valueOf(Config.get().getBaseIdByCode(installOrderConfirmBean.getZone(), Constant.AREA)));
        installOrderConfirmBean.setConnector(contact);
        installOrderConfirmBean.setConnectorPhone(phone);
        installOrderConfirmBean.setDetailPlace(etDetailAddress.getText().toString().trim());
        installOrderConfirmBean.setDescription(desc);
        installOrderConfirmBean.setPredictTime(GetConstDataUtils.getPredictList().indexOf(predictTime));
        installOrderConfirmBean.setRevertTimeLimit(GetConstDataUtils.getRevertList().indexOf(revertime));
        installOrderConfirmBean.setBudget(GetConstDataUtils.getBudgetList().indexOf(budget));
        installOrderConfirmBean.setBusinessOneCode(Config.get().getBusinessCodeByName(business, 1));
        installOrderConfirmBean.setBusinessOneId(Long.valueOf(Config.get().getBusinessIdByCode(installOrderConfirmBean.getBusinessOneCode())));

        doHttp(JSON.toJSONString(installOrderConfirmBean));

    }

    public void doHttp(String json) {
        EanfangHttp.post(NewApiService.ADD_WORK_INSTALL)
                .upJson(json)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        submitSuccess();
                    });

                }));
    }

    private void submitSuccess() {
        runOnUiThread(() -> {
            Intent intent = new Intent(InstallActivity.this, StateChangeActivity.class);
            Bundle bundle = new Bundle();
            Message message = new Message();
            message.setTitle("报装申请提交成功");
            message.setMsgTitle("您的报装申请已提交成功");
            message.setMsgContent("稍后客服会与您取得联系，请保持电话畅通");
            message.setShowOkBtn(true);
            message.setShowLogo(true);
            message.setTip("");
            bundle.putSerializable("message", message);
            intent.putExtras(bundle);
            startActivity(intent);
            finishSelf();

        });
    }

    /**
     * 是否放弃修改
     */
    private void giveUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否放弃报装？");
        builder.setTitle("提示");
        builder.setPositiveButton("是", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        builder.setNegativeButton("否", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    /**
     * 地图选址 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == INSTALL_REQUEST_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            Log.e("address", item.toString());
            latitude = item.getLatitude().toString();
            longitude = item.getLongitude().toString();
            city = item.getCity();
            zone = item.getAddress();
            tvAddress.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
            //地图选址 取 显示值
            etDetailAddress.setText(item.getName());
        }
    }


}
