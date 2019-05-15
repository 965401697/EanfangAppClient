package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
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
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.picker.DoubleDatePickerDialog;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSkillCertificafeActivity extends BaseActivityWithTakePhoto {

    @BindView(R.id.et_certificate_name)
    EditText etCertificateName;
    @BindView(R.id.et_org)
    EditText etOrg;
    @BindView(R.id.et_level)
    EditText etLevel;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    /**
     * 证书照片
     */
    private ArrayList<String> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();


    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    private String pic;
    private String url;

    private QualificationCertificateEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skill_certificafe);
        ButterKnife.bind(this);
        setLeftBack();
        bean = (QualificationCertificateEntity) getIntent().getSerializableExtra("bean");
        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
        setRightTitleOnClickListener(view -> setData());
        if (bean != null) {
            setTitle("资质证书");
            setRightTitle("编辑");
            setZhiDu(false);
            fillData();
            setRightTitleOnClickListener(view -> {
                        setRightTitle("保存");
                        setZhiDu(true);
                        setRightTitleOnClickListener(view1 -> setData());
                    }

            );

        } else {
            setTitle("资质证书");
            setRightTitle("保存");
            tvSave.setVisibility(View.GONE);
        }
    }

    private void setZhiDu(boolean isZd) {
        tvSave.setVisibility(isZd ? View.VISIBLE : View.GONE);
        etCertificateName.setEnabled(isZd);
        etNum.setEnabled(isZd);
        etOrg.setEnabled(isZd);
        llDate.setEnabled(isZd);
        snplMomentAccident.setPlusEnable(isZd);
        snplMomentAccident.setEditable(isZd);
    }


    private void fillData() {

        ArrayList<String> picList = new ArrayList<>();

        String[] pics = bean.getCertificatePics().split(",");

        for (int i = 0; i < pics.length; i++) {
            picList.add(BuildConfig.OSS_SERVER + pics[i]);
        }

        etCertificateName.setText(bean.getCertificateName());
        etOrg.setText(bean.getAwardOrg());
        etNum.setText(bean.getCertificateNumber());
        tvTime.setText(DateUtils.formatDate(bean.getBeginTime(), "yyyy-MM-dd") + " ～ " + DateUtils.formatDate(bean.getEndTime(), "yyyy-MM-dd"));
        snplMomentAccident.setData(picList);
    }


    private void setData() {

        if (checkedData()) {
            return;
        }

        QualificationCertificateEntity entity = new QualificationCertificateEntity();
        entity.setAccId(EanfangApplication.get().getAccId());
        if (bean != null) {
            entity.setId(bean.getId());
            url = UserApi.TECH_WORKER_UPDATA_QUALIFY;
        } else {
            url = UserApi.TECH_WORKER_ADD_QUALIFY;
        }
        entity.setCertificateName(etCertificateName.getText().toString().trim());
        entity.setAwardOrg(etOrg.getText().toString().trim());
        entity.setAccId(EanfangApplication.get().getAccId());
        entity.setBeginTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[0], "yyyy-MM-dd"));
        entity.setEndTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[1], "yyyy-MM-dd"));
        entity.setCertificateNumber(etNum.getText().toString().trim());
        entity.setCertificatePics(pic);

        entity.setType(0);

        OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                runOnUiThread(() -> {

                    EanfangHttp.post(url).upJson(JSONObject.toJSONString(entity)).execute(new EanfangCallback<JSONObject>(AddSkillCertificafeActivity.this, true, JSONObject.class, (bean) -> {
                                setResult(RESULT_OK);
                                finish();
                            }));


                });
            }
        });


    }


    private boolean checkedData() {
        if (TextUtils.isEmpty(etCertificateName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入证书名称");
            return true;
        }
        if (TextUtils.isEmpty(etNum.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入证书编号");
            return true;
        }
        if (TextUtils.isEmpty(etOrg.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入颁发机构");
            return true;
        }
        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.get().showToast(this, "请选择有效时间");
            return true;
        }

        pic = PhotoUtils.getPhotoUrl("", snplMomentAccident, uploadMap, false);
        if (StringUtils.isEmpty(pic)) {
            showToast("请添加证书");
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

    @OnClick({R.id.ll_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(AddSkillCertificafeActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d～%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                        if (GetDateUtils.getTimeStamp(startTime, "yyyy-MM-dd") > GetDateUtils.getTimeStamp(endTime, "yyyy-MM-dd")) {
                            ToastUtil.get().showToast(AddSkillCertificafeActivity.this, "开始时间不能大于结束时间");
                            return;
                        }
                        tvTime.setText(textString);

                    }

                    @Override
                    public void cancle() {

                    }


                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();

                break;
            case R.id.tv_save:
                delete();

                break;
            default:
        }
    }

    private void delete() {
        EanfangHttp.post(UserApi.TECH_WORKER_DELETE_QUALIFY + "/" + bean.getId()).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
            @Override
            public void onSuccess(JSONObject bean) {
                showToast("删除成功");
                finish();
            }
        });
    }
}