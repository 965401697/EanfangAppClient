package net.eanfang.client.ui.activity.worksapce;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
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
import com.eanfang.biz.model.AddDesignOrderBean;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:35
 * @email houzhongzhou@yeah.net
 * @desc 免费设计
 */

public class DesignActivity extends BaseClientActivity {

    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_detail_address)
    EditText et_detail_address;

    @BindView(R.id.et_receive_user_name)
    EditText et_receive_user_name;

    @BindView(R.id.et_receive_phone)
    EditText et_receive_phone;

    @BindView(R.id.ll_reply_limit)
    LinearLayout ll_reply_limit;
    @BindView(R.id.tv_reply_limit)
    TextView tv_reply_limit;

    @BindView(R.id.ll_business_one)
    LinearLayout ll_business_one;
    @BindView(R.id.tv_business_one)
    TextView tv_business_one;

    @BindView(R.id.ll_plan_limit)
    LinearLayout ll_plan_limit;
    @BindView(R.id.tv_plan_limit)
    TextView tv_plan_limit;

    @BindView(R.id.ll_budget_limit)
    LinearLayout ll_budget_limit;
    @BindView(R.id.tv_budget_limit)
    TextView tv_budget_limit;

    @BindView(R.id.et_remark)
    EditText et_remark;

    private static int REQUSERT_ADDRESS_CODE = 100;

    /**
     * 经度
     */
    private String lon;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    private String zone;

    /**
     * 区县
     */
    private String contry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_design_order);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("免费设计");
        setLeftBack();

        //选择地址
        ll_address.setOnClickListener((v) -> {
            Intent intent = new Intent(DesignActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, REQUSERT_ADDRESS_CODE);
        });
        //回复时限选择
        ll_reply_limit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_reply_limit, GetConstDataUtils.getRevertList());
        });
        //业务类型一级
        ll_business_one.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_business_one, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
        });
        //预计工期
        ll_plan_limit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_plan_limit, GetConstDataUtils.getPredictList());
        });
        //预算范围
        ll_budget_limit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_budget_limit, GetConstDataUtils.getBudgetList());
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
        if (StringUtils.isEmpty(et_user_name.getText())) {
            et_user_name.setText(name);
            et_receive_user_name.setText(user.getAccount().getRealName());
            et_receive_phone.setText(user.getAccount().getMobile());
        }
    }

    private void submit() {
        String userName = et_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String mapAddress = tv_address.getText().toString().trim();
        if (TextUtils.isEmpty(mapAddress)) {
            Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = et_detail_address.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String receiveUserName = et_receive_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(receiveUserName)) {
            Toast.makeText(this, "联系人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String receivePhone = et_receive_phone.getText().toString().trim();
        if (TextUtils.isEmpty(receivePhone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String replyLimit = tv_reply_limit.getText().toString().trim();
        if (TextUtils.isEmpty(replyLimit)) {
            Toast.makeText(this, "回复期限不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String businessOne = tv_business_one.getText().toString().trim();
        if (TextUtils.isEmpty(businessOne)) {
            Toast.makeText(this, "业务类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String planLimit = tv_plan_limit.getText().toString().trim();
        if (TextUtils.isEmpty(planLimit)) {
            Toast.makeText(this, "预计工期不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String budgetLimit = tv_budget_limit.getText().toString().trim();
        if (TextUtils.isEmpty(budgetLimit)) {
            Toast.makeText(this, "预算范围不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String remark = et_remark.getText().toString().trim();
        if (TextUtils.isEmpty(remark)) {
            Toast.makeText(this, "需求描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (remark.length() > 50) {
            showToast("需求描述不能超过50个字");
            return;
        }
        AddDesignOrderBean bean = new AddDesignOrderBean();
        bean.setDetailPlace(address);
        bean.setBudgetLimit(GetConstDataUtils.getBudgetList().indexOf(budgetLimit));
        bean.setBusinessOneCode(Config.get().getBusinessCodeByName(businessOne, 1));
        bean.setBusinessOneId(Long.valueOf(Config.get().getBusinessIdByCode(bean.getBusinessOneCode(), 1)));
        bean.setZoneCode(Config.get().getAreaCodeByName(city, contry));
        bean.setZoneId(Long.valueOf(Config.get().getBaseIdByCode(bean.getZoneCode(), 3, Constant.AREA)));
        bean.setLatitude(lat);
        bean.setLongitude(lon);
        bean.setPredictTime(GetConstDataUtils.getPredictList().indexOf(planLimit));
        bean.setContactUser(receiveUserName);
        bean.setContact_phone(receivePhone);
        bean.setRemarkInfo(remark);
        bean.setRevertTimeLimit(GetConstDataUtils.getRevertList().indexOf(replyLimit));
        bean.setUserName(userName);

        doHttp(JSON.toJSONString(bean));

    }

    private void submitSuccess() {
        runOnUiThread(() -> {
            Intent intent = new Intent(DesignActivity.this, StateChangeActivity.class);
            Bundle bundle = new Bundle();
            Message message = new Message();
            message.setTitle("免费设计提交成功");
            message.setMsgTitle("您的免费设计已提交成功");
            message.setMsgContent("稍后客服会与您取得联系，请保持电话畅通");
            message.setShowOkBtn(true);
            message.setShowLogo(true);
            message.setTip("确定");
            bundle.putSerializable("message", message);
            intent.putExtras(bundle);
            startActivity(intent);
            finishSelf();

        });
    }

    public void doHttp(String json) {
        EanfangHttp.post(NewApiService.ADD_WORK_DESIGN)
                .upJson(json)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        submitSuccess();
                    });

                }));
    }

    /**
     * 是否放弃修改
     */
    private void giveUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否放弃免费设计？");
        builder.setTitle("提示");
        builder.setPositiveButton("是", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        builder.setNegativeButton("否", (dialog, which) -> {
            dialog.dismiss();
        });
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
        if (requestCode == REQUSERT_ADDRESS_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            Log.e("address", item.toString());
            lat = item.getLatitude().toString();
            lon = item.getLongitude().toString();
            province = item.getProvince();
            city = item.getCity();
            zone = item.getZone();
            contry = item.getAddress();
            tv_address.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
            //地图选址 取 显示值
            et_detail_address.setText(item.getName());
        }
    }


    @OnClick(R.id.tv_sub)
    public void onViewClicked() {
        submit();
    }
}
