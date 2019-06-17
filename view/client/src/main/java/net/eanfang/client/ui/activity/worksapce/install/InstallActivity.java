package net.eanfang.client.ui.activity.worksapce.install;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.vo.InstallOrderConfirmVo;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.InstallOrderConfirmBean;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityInstallBinding;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.databinding.DataBindingUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:35
 * @email houzhongzhou@yeah.net
 * @desc 我要报装
 */

public class InstallActivity extends BaseClientActivity {
//    @BindView(R.id.et_company)
//    EditText etCompany;
//    @BindView(R.id.tv_address)
//    TextView tvAddress;
//    @BindView(R.id.ll_address)
//    LinearLayout llAddress;
//    @BindView(R.id.et_detail_address)
//    EditText etDetailAddress;
//    @BindView(R.id.et_contact)
//    EditText etContact;
//    @BindView(R.id.et_phone)
//    EditText etPhone;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.ll_time)
//    LinearLayout llTime;
//    @BindView(R.id.tv_business)
//    TextView tvBusiness;
//    @BindView(R.id.ll_business)
//    LinearLayout llBusiness;
//    @BindView(R.id.tv_project_time)
//    TextView tvProjectTime;
//    @BindView(R.id.ll_project_time)
//    LinearLayout LLProjectTime;
//    @BindView(R.id.tv_budget)
//    TextView tvBudget;
//    @BindView(R.id.ll_budget)
//    LinearLayout llBudget;
//    @BindView(R.id.et_desc)
//    EditText etDesc;
//    @BindView(R.id.iv_left)
//    ImageView ivLeft;
//    @BindView(R.id.rl_confirm)
//    RelativeLayout rlConfirm;

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

    private ActivityInstallBinding binding;
    private InstallOrderConfirmVo installOrderConfirmVo;
    private LoginBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_install);
        super.onCreate(savedInstanceState);
        setTitle("我要报装");
        setLeftBack(view -> giveUp());
        initDialog();
        initData();
    }

    private void initData() {
        user = ClientApplication.get().getLoginBean();
        binding.setUser(user);
    }


    private void initDialog() {
        installOrderConfirmVo = CacheKit.get().getVo("installOrder", InstallOrderConfirmVo.class);
        if (null == installOrderConfirmVo) {
            installOrderConfirmVo = new InstallOrderConfirmVo();
            binding.setInstallbean(installOrderConfirmVo);

            return;
        }

        into(new InvokingData() {
            @Override
            public void invoke() {
                binding.setInstallbean(installOrderConfirmVo);
            }
        });
    }

//    private void setListener() {
//        //选择地址
//        llAddress.setOnClickListener((v) -> {
//            Intent intent = new Intent(InstallActivity.this, SelectAddressActivity.class);
//            startActivityForResult(intent, INSTALL_REQUEST_CODE);
//        });
//        //回复时限选择
//        llTime.setOnClickListener((v) -> {
//            PickerSelectUtil.singleTextPicker(this, "", tvTime, GetConstDataUtils.getRevertList());
//        });
//        //业务类型一级
//        llBusiness.setOnClickListener((v) -> {
//            PickerSelectUtil.singleTextPicker(this, "", tvBusiness, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
//        });
//        //预计工期
//        LLProjectTime.setOnClickListener((v) -> {
//            PickerSelectUtil.singleTextPicker(this, "", tvProjectTime, GetConstDataUtils.getPredictList());
//        });
//        //预算范围
//        llBudget.setOnClickListener((v) -> {
//            PickerSelectUtil.singleTextPicker(this, "", tvBudget, GetConstDataUtils.getBudgetList());
//        });
//        rlConfirm.setOnClickListener((v) -> {
//            submit();
//        });
//    }

    private void submit() {
        // validate
        String company = binding.etCompany.getText().toString().trim();
        if (TextUtils.isEmpty(company)) {
            Toast.makeText(this, "单位名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String bigAddress = binding.tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(bigAddress)) {
            Toast.makeText(this, "报装地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        String address = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(installOrderConfirmVo.getDetailPlace().get())) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String contact = binding.etContact.getText().toString().trim();
        if (TextUtils.isEmpty(contact)) {
            Toast.makeText(this, "联系人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = binding.etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

//        String business = binding.tvBusiness.getText().toString().trim();
        if (TextUtils.isEmpty(installOrderConfirmVo.getBusinessOneId().get())) {
            Toast.makeText(this, "业务类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        String time = tvTime.getText().toString().trim();
        if (TextUtils.isEmpty(installOrderConfirmVo.getRevertTimeLimit().get())) {
            Toast.makeText(this, "回复时限不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        String projectTime = tvProjectTime.getText().toString().trim();
        if (TextUtils.isEmpty(installOrderConfirmVo.getPredictTime().get())) {
            Toast.makeText(this, "预计工期不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        String mBudget = tvBudget.getText().toString().trim();
        if (TextUtils.isEmpty(installOrderConfirmVo.getBudget().get())) {
            Toast.makeText(this, "预算范围不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


//        String desc = etDesc.getText().toString().trim();
        String desc = installOrderConfirmVo.getDescription().get();
        if (TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "需求描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.length() > 50) {
            showToast("需求描述不能超过50个字");
            return;
        }
        String budget = binding.tvBudget.getText().toString().trim();
        String revertime = binding.tvTime.getText().toString().trim();
        String predictTime = binding.tvProjectTime.getText().toString().trim();

        InstallOrderConfirmBean installOrderConfirmBean = new InstallOrderConfirmBean();
        installOrderConfirmBean.setLatitude(latitude);
        installOrderConfirmBean.setLongitude(longitude);
        installOrderConfirmBean.setClientCompanyName(company);
        installOrderConfirmBean.setZone(Config.get().getAreaCodeByName(city, zone));
        installOrderConfirmBean.setZoneId(Long.valueOf(Config.get().getBaseIdByCode(installOrderConfirmBean.getZone(), 3, Constant.AREA)));
        installOrderConfirmBean.setConnector(contact);
        installOrderConfirmBean.setConnectorPhone(phone);
        installOrderConfirmBean.setDetailPlace(installOrderConfirmVo.getDetailPlace().get());
        installOrderConfirmBean.setDescription(desc);
        installOrderConfirmBean.setPredictTime(GetConstDataUtils.getPredictList().indexOf(predictTime));
        installOrderConfirmBean.setRevertTimeLimit(GetConstDataUtils.getRevertList().indexOf(revertime));
        installOrderConfirmBean.setBudget(GetConstDataUtils.getBudgetList().indexOf(budget));
        installOrderConfirmBean.setBusinessOneCode(Config.get().getBusinessCodeByName(installOrderConfirmVo.getBusinessOneId().get(), 1));
        installOrderConfirmBean.setBusinessOneId(Long.valueOf(Config.get().getBusinessIdByCode(installOrderConfirmBean.getBusinessOneCode(), 1)));

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
            message.setTip("确定");
            bundle.putSerializable("message", message);
            intent.putExtras(bundle);
            startActivity(intent);
//            finishSelf();
            finish();
        });
    }

    /**
     * 是否放弃修改
     */
    @SuppressLint("CheckResult")
    private void giveUp() {
        out(installOrderConfirmVo);
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
            binding.tvAddress.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
            //地图选址 取 显示值
            binding.etDetailAddress.setText(item.getName());
        }
    }

    public void getAddress(View view) {
        //选择地址
        Intent intent = new Intent(InstallActivity.this, SelectAddressActivity.class);
        startActivityForResult(intent, INSTALL_REQUEST_CODE);
    }

    public void revertTime(View view) {
        //回复时限选择
        PickerSelectUtil.singleTextPicker(this, "", binding.tvTime, GetConstDataUtils.getRevertList());
    }

    public void business(View view) {
        //业务类型一级
        PickerSelectUtil.singleTextPicker(this, "", binding.tvBusiness,
                Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
//        installOrderConfirmVo.getImg().set("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4138850978,2612460506&fm=200&gp=0.jpg");
    }

    public void projectTime(View view) {
        //预计工期
        PickerSelectUtil.singleTextPicker(this, "", binding.tvProjectTime, GetConstDataUtils.getPredictList());
    }

    public void budget(View view) {
        //预算范围
        PickerSelectUtil.singleTextPicker(this, "", binding.tvBudget, GetConstDataUtils.getBudgetList());
    }

    public void confirm(View view) {
        submit();
    }

}
