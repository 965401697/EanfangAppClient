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

import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
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
import net.eanfang.worker.base.WorkerApplication;

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
    private JobExperienceEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);
        ButterKnife.bind(this);
        setLeftBack();
        bean = (JobExperienceEntity) getIntent().getSerializableExtra("bean");
        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
        snplMomentAccident.setData(picList_certificate);
        setRightTitleOnClickListener(view -> setData());
        if (bean != null) {
            fillData();
            setTitle("工作经历");
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
            setTitle("工作经历");
            setRightTitle("保存");
            tvSave.setVisibility(View.GONE);
        }

    }

    private void setZhiDu(boolean isZd) {
        tvSave.setVisibility(isZd ? View.VISIBLE : View.GONE);
        etCompanyName.setEnabled(isZd);
        etPosition.setEnabled(isZd);
        etJobPosition.setEnabled(isZd);
        llDate.setEnabled(isZd);
        etCertificate.setEnabled(isZd);
        snplMomentAccident.setPlusEnable(isZd || StringUtils.isEmpty(bean.getCardPics()));
        snplMomentAccident.setEditable(isZd || StringUtils.isEmpty(bean.getCardPics()));
        snplMomentAccident.setItemClickble(isZd);
    }

    private void fillData() {
        ArrayList<String> picList = new ArrayList<>();
        if (bean.getCardPics() != null && bean.getCardPics().length() > 0) {
            String[] pics = bean.getCardPics().split(",");
            for (String pic1 : pics) {
                picList.add(BuildConfig.OSS_SERVER + pic1);
            }
        }
        etCompanyName.setText(bean.getCompanyName());
        etPosition.setText(bean.getJob());
        etJobPosition.setText(bean.getWorkplace());
        if (bean.getBeginTime() != null && bean.getEndTime() != null) {
            tvTime.setText(DateUtils.formatDate(bean.getBeginTime(), "yyyy-MM-dd") + " ～ " + DateUtils.formatDate(bean.getEndTime(), "yyyy-MM-dd"));
        }
        snplMomentAccident.setData(picList);
        etCertificate.setText(bean.getJobIntro());

    }

    private void setData() {

        if (checkedData()) {
            return;
        }

        JobExperienceEntity entity = new JobExperienceEntity();
        entity.setAccId(WorkerApplication.get().getAccId());
        if (bean != null) {
            entity.setId(bean.getId());
            url = UserApi.GET_TECH_WORKER_WORK_UPDATE;
        } else {
            url = UserApi.GET_TECH_WORKER_ADD_WORK;
        }
        entity.setCompanyName(etCompanyName.getText().toString().trim());
        entity.setJob(etPosition.getText().toString().trim());
        entity.setWorkplace(etJobPosition.getText().toString().trim());
        if (!StringUtils.isEmpty(tvTime.getText().toString().trim())) {
            entity.setBeginTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[0], "yyyy-MM-dd"));
            entity.setEndTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[1], "yyyy-MM-dd"));
        }
        entity.setJobIntro(etCertificate.getText().toString().trim());
        pic = PhotoUtils.getPhotoUrl("", snplMomentAccident, uploadMap, false);
        entity.setCardPics(pic);
        entity.setType(0);
        SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> runOnUiThread(() -> {
            EanfangHttp.post(url)
                    .upJson(JSONObject.toJSONString(entity))
                    .execute(new EanfangCallback<JSONObject>(AddWorkActivity.this, true, JSONObject.class, (bean) -> {
                        setResult(RESULT_OK);
                        finish();
                    }));
        }));


    }

    private boolean checkedData() {
        if (TextUtils.isEmpty(etCompanyName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入任职机构");
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
                delete();

                break;
            default:
        }
    }

    private void delete() {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_WORK_DELETE + "/" + bean.getId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        showToast("删除成功");
                        finish();
                    }
                });
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
