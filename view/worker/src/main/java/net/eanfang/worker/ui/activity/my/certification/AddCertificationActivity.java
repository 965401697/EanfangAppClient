package net.eanfang.worker.ui.activity.my.certification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/10/22
 * @description 添加荣誉证书
 */

public class AddCertificationActivity extends BaseActivityWithTakePhoto implements SelectTimeDialogFragment.SelectTimeListener {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_org)
    EditText etOrg;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;
    @BindView(R.id.et_certificate)
    EditText etCertificate;
    @BindView(R.id.tv_save)
    TextView tvSave;


    /**
     * 证书照片
     */
    private ArrayList<String> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();


    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    private String pic;


    //        private TimePickerView mTimeYearMonthDay;
    private HonorCertificateEntity bean;
    private String url = "";
    // 是否安防公司的荣誉证书
    private String isCompany = "";
    private Long orgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_certification);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        setLeftBack();
        isCompany = getIntent().getStringExtra("role");
        orgid = getIntent().getLongExtra("orgid", 0);
        bean = (HonorCertificateEntity) getIntent().getSerializableExtra("bean");

        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
        snplMomentAccident.setData(picList_certificate);

        doSelectYearMonthDay();

        if (bean != null) {
            fillData();
            setTitle("修改证书");

        } else {
            setTitle("添加证书");
        }
    }

    /**
     * 只进行查看操作不看编辑
     */
    private void doUnWrite() {
        tvSave.setVisibility(View.GONE);
        etCertificate.setEnabled(false);
        etName.setEnabled(false);
        etOrg.setEnabled(false);
        llDate.setEnabled(false);
        snplMomentAccident.setEditable(false);
    }

    private void fillData() {

        ArrayList<String> picList = new ArrayList<>();

        if (bean.getHonorPics() != null) {

            String[] pics = bean.getHonorPics().split(",");

            for (int i = 0; i < pics.length; i++) {
                picList.add(BuildConfig.OSS_SERVER + pics[i]);
            }
        }
        etName.setText(bean.getHonorName());
        etOrg.setText(bean.getAwardOrg());
        tvTime.setText(DateUtils.formatDate(bean.getAwardTime(), "yyyy-MM-dd"));
        snplMomentAccident.setData(picList);
        etName.setText(bean.getHonorName());
        etCertificate.setText(bean.getIntro());
    }

    @OnClick({R.id.ll_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                // 隐藏软键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AddCertificationActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //                mTimeYearMonthDay.show();
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_save:
                setData();
                break;
        }
    }

    private void setData() {

        if (checkedData()) {
            return;
        }

        HonorCertificateEntity entity = new HonorCertificateEntity();
        entity.setAccId(EanfangApplication.get().getAccId());
        if (!TextUtils.isEmpty(isCompany) && isCompany.equals("company")) {// 安防公司
            if (bean != null) {
                entity.setId(bean.getId());
                url = UserApi.COMPANY_CERTIFICATE_UPDATE;
            } else {
                url = UserApi.COMPANY_ADD_CERTIFICATE;
            }
            entity.setOrgId(orgid);
        } else {// 技师
            if (bean != null) {
                entity.setId(bean.getId());
                url = UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_UPDATE;
            } else {
                url = UserApi.GET_TECH_WORKER_ADD_CERTIFICATE;
            }
        }

        entity.setHonorName(etName.getText().toString().trim());
        entity.setAwardOrg(etOrg.getText().toString().trim());
        entity.setAwardTime(DateUtils.parseDate(tvTime.getText().toString().trim(), "yyyy-MM-dd"));
        entity.setHonorPics(pic);
        entity.setIntro(etCertificate.getText().toString().trim());
        entity.setType(0);

        OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                runOnUiThread(() -> {

                    EanfangHttp.post(url)
                            .upJson(JSONObject.toJSONString(entity))
                            .execute(new EanfangCallback<JSONObject>(AddCertificationActivity.this, true, JSONObject.class, (bean) -> {
                                setResult(RESULT_OK);
                                finish();
                            }));


                });
            }
        });


    }


    private boolean checkedData() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入荣誉名称");
            return true;
        }

        if (TextUtils.isEmpty(etOrg.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入颁发机构名称");
            return true;
        }

        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.get().showToast(this, "请选择颁发时间");
            return true;
        }

        pic = PhotoUtils.getPhotoUrl("", snplMomentAccident, uploadMap, false);
        if (StringUtils.isEmpty(pic)) {
            showToast("请添加荣誉证书照片");
            return true;
        }

        if (TextUtils.isEmpty(etCertificate.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入荣誉说明");
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_CERTIFICATE:
                snplMomentAccident.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
        }
    }

    /**
     * 选择年月日
     */
    public void doSelectYearMonthDay() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1960, 1, 1);
        endDate.set(2040, 11, 31);
//        mTimeYearMonthDay = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvTime.setText(ETimeUtils.getTimeByYearMonthDay(date));
//            }
//        })
//                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
////                .setTitleText("上门日期")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
    }

//    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            tvTime.setText(time);
        }
    }
}
