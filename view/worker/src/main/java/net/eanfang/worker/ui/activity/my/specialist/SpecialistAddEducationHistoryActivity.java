package net.eanfang.worker.ui.activity.my.specialist;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.util.DoubleDatePickerDialog;
import com.eanfang.biz.model.entity.EducationExperienceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.qqtheme.framework.util.DateUtils;


public class SpecialistAddEducationHistoryActivity extends BaseActivity {

    @BindView(R.id.et_school_name)
    EditText etSchoolName;
    @BindView(R.id.et_major)
    EditText etMajor;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.picture_recycler)
    PictureRecycleView pictureRecycler;


    /**
     * 证书照片
     */
    private List<LocalMedia> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();
    private String pic;
    private String url;
    private EducationExperienceEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specialist_add_education_history);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("添加教育经历");
        setLeftBack(true);

        bean = (EducationExperienceEntity) getIntent().getSerializableExtra("bean");

        if (bean != null) {
            fillData();
            setTitle("修改教育经历");
            picList_certificate = pictureRecycler.setData(bean.getCertificatePics());
            pictureRecycler.showImagev(picList_certificate, listener);
            pictureRecycler.isShow(true, picList_certificate);
        } else {
            setTitle("添加教育经历");
            pictureRecycler.addImagev(listener);
        }
    }
    PictureRecycleView.ImageListener listener = list -> picList_certificate = list;

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void fillData() {
        etSchoolName.setText(bean.getSchoolName());
        etMajor.setText(bean.getMajorName());
        tvEducation.setText(GetConstDataUtils.getDiplomaList().get(bean.getDiploma()));
        tvTime.setText(DateUtils.formatDate(bean.getBeginTime(), "yyyy-MM-dd") + " ～ " + DateUtils.formatDate(bean.getEndTime(), "yyyy-MM-dd"));
        etNum.setText(bean.getCertificateNumber());
    }

    @OnClick({R.id.ll_education, R.id.ll_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_education:

                PickerSelectUtil.singleTextPicker(this, "", tvEducation, GetConstDataUtils.getDiplomaList());

                break;
            case R.id.ll_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(SpecialistAddEducationHistoryActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d～%d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);

                        String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);

                        if (DateUtil.parse(startTime,"yyyy-M-dd").getTime()>DateUtil.parse(endTime,"yyyy-M-dd").getTime()) {
                            ToastUtil.get().showToast(SpecialistAddEducationHistoryActivity.this, "开始时间不能大于结束时间");

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


    private void setData() {

        if (checkedData()) {
            return;
        }

        EducationExperienceEntity entity = new EducationExperienceEntity();
        entity.setAccId(WorkerApplication.get().getAccId());
        if (bean != null) {
            entity.setId(bean.getId());
            url = UserApi.GET_TECH_WORKER_EDUCATION_UPDATE;
        } else {
            url = UserApi.GET_TECH_WORKER_ADD_EDUCATION;
        }
        entity.setSchoolName(etSchoolName.getText().toString().trim());
        entity.setMajorName(etMajor.getText().toString().trim());
        entity.setDiploma(GetConstDataUtils.getDiplomaList().indexOf(tvEducation.getText().toString().trim()));
        entity.setCertificateNumber(etNum.getText().toString().trim());
        entity.setBeginTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[0], "yyyy-M-dd"));
        entity.setEndTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[1], "yyyy-M-dd"));
        entity.setCertificatePics(pic);
        entity.setType(1);
        SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
            runOnUiThread(() -> {

                EanfangHttp.post(url)
                        .upJson(JSONObject.toJSONString(entity))
                        .execute(new EanfangCallback<JSONObject>(SpecialistAddEducationHistoryActivity.this, true, JSONObject.class, (bean) -> {
                            setResult(RESULT_OK);
                            finish();
                        }));


            });
        });


    }

    private boolean checkedData() {
        if (TextUtils.isEmpty(etSchoolName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入学校名称");
            return true;
        }

        if (TextUtils.isEmpty(etMajor.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入专业名称");
            return true;
        }

        if (TextUtils.isEmpty(tvEducation.getText().toString())) {
            ToastUtil.get().showToast(this, "请选学历层次");
            return true;
        }
        if (TextUtils.isEmpty(etNum.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入学历编号");
            return true;
        }
        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.get().showToast(this, "请选择起止时间");
            return true;
        }

        pic = PhotoUtils.getPhotoUrl("", picList_certificate, uploadMap, false);
        if (StrUtil.isEmpty(pic)) {
            showToast("请添加证书照片");
            return true;
        }

        return false;
    }
}
