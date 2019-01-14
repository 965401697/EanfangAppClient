package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.yaf.base.entity.JobExperienceEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加工作经历
 */
public class AddWorkActivity extends BaseActivityWithTakePhoto {

    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_position)
    EditText etPosition;
    @BindView(R.id.et_job_position)
    EditText etJobPosition;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_certificate)
    EditText etCertificate;
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;

    /**
     * 证书照片
     */
    private ArrayList<String> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();

    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    private String pic;
    private String url;
    private JobExperienceEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);
        ButterKnife.bind(this);
        setTitle("个人经历");
        setLeftBack();

        bean = (JobExperienceEntity) getIntent().getSerializableExtra("bean");


        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
        snplMomentAccident.setData(picList_certificate);

        if (bean != null) {
            fillData();
            setTitle("修改个人经历");
        } else {
            setTitle("添加个人经历");
        }

    }

    private void fillData() {

        ArrayList<String> picList = new ArrayList<>();

        String[] pics = bean.getCardPics().split(",");

        for (int i = 0; i < pics.length; i++) {
            picList.add(BuildConfig.OSS_SERVER + pics[i]);
        }

        etCompanyName.setText(bean.getCompanyName());
        etPosition.setText(bean.getJob());
        etJobPosition.setText(bean.getWorkplace());
        tvTime.setText(DateUtils.formatDate(bean.getBeginTime(), "yyyy-MM-dd") + " ～ " + DateUtils.formatDate(bean.getEndTime(), "yyyy-MM-dd"));
        snplMomentAccident.setData(picList);
        etCertificate.setText(bean.getJobIntro());

    }

    private void setData() {

        if (checkedData()) return;

        JobExperienceEntity entity = new JobExperienceEntity();
        entity.setAccId(EanfangApplication.get().getAccId());
        if (bean != null) {
            entity.setId(bean.getId());
            url = UserApi.GET_TECH_WORKER_WORK_UPDATE;
        } else {
            url = UserApi.GET_TECH_WORKER_ADD_WORK;
        }
        entity.setCompanyName(etCompanyName.getText().toString().trim());
        entity.setJob(etPosition.getText().toString().trim());
        entity.setWorkplace(etJobPosition.getText().toString().trim());
        entity.setBeginTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[0], "yyyy-MM-dd"));
        entity.setEndTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[1], "yyyy-MM-dd"));
        entity.setJobIntro(etCertificate.getText().toString().trim());
        entity.setCardPics(pic);
        entity.setType(0);

        OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                runOnUiThread(() -> {

                    EanfangHttp.post(url)
                            .upJson(JSONObject.toJSONString(entity))
                            .execute(new EanfangCallback<JSONObject>(AddWorkActivity.this, true, JSONObject.class, (bean) -> {
                                setResult(RESULT_OK);
                                finish();
                            }));


                });
            }
        });


    }

    private boolean checkedData() {
        if (TextUtils.isEmpty(etCompanyName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入公司名称");
            return true;
        }

        if (TextUtils.isEmpty(etPosition.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入职位名称");
            return true;
        }

        if (TextUtils.isEmpty(etJobPosition.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入工作地点");
            return true;
        }
        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.get().showToast(this, "请选起止时间");
            return true;
        }
        if (TextUtils.isEmpty(etCertificate.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入工作描述");
            return true;
        }

        pic = PhotoUtils.getPhotoUrl("", snplMomentAccident, uploadMap, false);
        if (StringUtils.isEmpty(pic)) {
            showToast("请添加名牌或者工牌照片");
            return true;
        }

        return false;
    }

    @OnClick({R.id.ll_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:

                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(AddWorkActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d～%d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);

                        String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);

                        if (GetDateUtils.getTimeStamp(startTime, "yyyy-MM-dd") > GetDateUtils.getTimeStamp(endTime, "yyyy-MM-dd")) {
                            ToastUtil.get().showToast(AddWorkActivity.this, "开始时间不能大于结束时间");

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

                setData();
                break;
        }
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
}
