package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.TimePickerView;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.model.Message;
import com.eanfang.model.TakeApplyAddBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import net.eanfang.worker.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/18  18:34
 * @email houzhongzhou@yeah.net
 * @desc 任务接单新增
 */

public class TakeApplyAddActivity extends BaseActivity {
    @BindView(R.id.et_apply_company)
    EditText etApplyCompany;
    @BindView(R.id.et_contract)
    EditText etContract;
    @BindView(R.id.tv_contract_phone)
    TextView tvContractPhone;
    @BindView(R.id.et_contract_phone)
    EditText etContractPhone;
    @BindView(R.id.tv_doorTime)
    TextView tvDoorTime;
    @BindView(R.id.ll_doorTime)
    LinearLayout llDoorTime;
    @BindView(R.id.et_time_limit)
    EditText etTimeLimit;
    @BindView(R.id.tv_budget)
    EditText tvBudget;
    @BindView(R.id.et_need_desc)
    EditText etNeedDesc;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private TakeApplyAddBean applyTaskBean;
    private Long entTaskPublishId;
    private HashMap<String, String> uploadMap = new HashMap<>();
    private TimePickerView pvEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_apply_add);
        ButterKnife.bind(this);
        initView();
        initEndTimePicker();
        initData();
    }

    private void initView() {
        entTaskPublishId = getIntent().getLongExtra("entTaskPublishId", 0);
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        setTitle("接单申请");
        setLeftBack();
        llDoorTime.setOnClickListener(v -> pvEndTime.show());
        tvOk.setOnClickListener(v -> commit());
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
        if (StringUtils.isEmpty(etApplyCompany.getText())) {
            etApplyCompany.setText(name);
        }
        etContract.setText(user.getAccount().getRealName());
        etContractPhone.setText(user.getAccount().getMobile());
        tvDoorTime.setText(getIntent().getStringExtra("makeTime"));
    }

    private void commit() {
        applyTaskBean = new TakeApplyAddBean();
        String company = etApplyCompany.getText().toString().trim();
//        if (TextUtils.isEmpty(company)) {
//            Toast.makeText(this, "请输入接单单位", Toast.LENGTH_SHORT).show();
//            return;
//        }
        applyTaskBean.setApplyCompanyName(company);
        String contract = etContract.getText().toString().trim();
//        if (TextUtils.isEmpty(contract)) {
//            Toast.makeText(this, "请输入联系人姓名", Toast.LENGTH_SHORT).show();
//            return;
//        }
        applyTaskBean.setApplyContacts(contract);
        String phone = etContractPhone.getText().toString().trim();
//        if (TextUtils.isEmpty(phone)) {
//            Toast.makeText(this, "请输入联系电话", Toast.LENGTH_SHORT).show();
//            return;
//        }
        applyTaskBean.setApplyConstactsPhone(phone);
        String money = tvBudget.getText().toString().trim();
//        if (TextUtils.isEmpty(money)) {
//            Toast.makeText(this, "项目报价不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }

        applyTaskBean.setProjectQuote(Integer.parseInt(money));
        String desc = etNeedDesc.getText().toString().trim();
//        if (TextUtils.isEmpty(desc)) {
//            Toast.makeText(this, "请输入方案简述", Toast.LENGTH_SHORT).show();
//            return;
//        }

        applyTaskBean.setDescription(desc);

        String timeLimit = etTimeLimit.getText().toString().trim();
//        if (TextUtils.isEmpty(timeLimit)) {
//            showToast("请输入预计工期");
//            return;
//        }
        applyTaskBean.setPredictTime(Integer.parseInt(timeLimit));

        String doorTime = tvDoorTime.getText().toString().trim();
//        if (TextUtils.isEmpty(doorTime)) {
//            showToast("请选择踏勘时间");
//            return;
//        }
        applyTaskBean.setToDoorTime(doorTime);

        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        applyTaskBean.setPictures(ursStr);

        applyTaskBean.setShopTaskPublishId(entTaskPublishId);

        String json = JSONObject.toJSONString(applyTaskBean);
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doHttp(json);
                    });
                }
            });
        } else {
            doHttp(json);
        }
    }

    private void doHttp(String json) {
        EanfangHttp.post(NewApiService.TAKE_APPLY_LIST_ADD)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    submitSuccess();
                }));
    }

    private void submitSuccess() {
        showToast("接单成功");
        EanfangApplication.get().closeActivity(TakeTaskListActivity.class.getName(), TakeApplyAddActivity.class.getName());
        Intent intent = new Intent(TakeApplyAddActivity.this, StateChangeActivity.class);
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setTitle("任务接单");
        message.setMsgTitle("您的接单申请已提交成功");
        message.setMsgContent("请耐心等待发包方确认。");
        message.setShowOkBtn(true);
        message.setShowLogo(true);

        message.setTip("");
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);
        finishSelf();
    }

    private void initEndTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //时间选择器
        pvEndTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tvDoorTime.setText(GetDateUtils.dateToDateTimeString(date));
            }
        })
                .setTitleText("踏勘时间")
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }
}