package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sdk.picture.GridImageAdapter;
import com.eanfang.sdk.picture.PictureInvoking;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.picker.DoubleDatePickerDialog;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.EducationExperienceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 教育经历
 */
public class AddEducationHistoryActivity extends BaseWorkeActivity {

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
    @BindView(R.id.ll_education)
    LinearLayout llEducation;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.cheng_ji)
    EditText chengJi;
    @BindView(R.id.recycleview)
    PictureRecycleView recycleview;

    private HashMap<String, String> uploadMap = new HashMap<>();
    /**
     * 证书照片
     */
    private List<LocalMedia> selectList = new ArrayList<>();
    private String pic;
    private String url;
    private EducationExperienceEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_education_history);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("添加教育培训");
        setLeftBack(true);
        bean = (EducationExperienceEntity) getIntent().getSerializableExtra("bean");
        setRightTitleOnClickListener(view -> setData());
        if (bean != null) {
            setTitle("教育培训");
            setRightTitle("编辑");
            setZhiDu(false);
            fillData();
            selectList = recycleview.setData(bean.getCertificatePics());
            recycleview.showImagev(selectList, listener);
            setRightTitleOnClickListener(view -> {
                        setRightTitle("保存");
                        setZhiDu(true);
                        recycleview.isShow(true, selectList);
                        setRightTitleOnClickListener(view1 -> setData());
                    }

            );
        } else {
            setTitle("教育培训");
            setRightTitle("保存");
            tvSave.setVisibility(View.GONE);
            recycleview.addImagev(listener);
        }
    }

    PictureRecycleView.ImageListener listener = list -> selectList = list;

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void setZhiDu(boolean isZd) {
        tvSave.setVisibility(isZd ? View.VISIBLE : View.GONE);
        etNum.setEnabled(isZd);
        etSchoolName.setEnabled(isZd);
        etMajor.setEnabled(isZd);
        llEducation.setEnabled(isZd);
        llDate.setEnabled(isZd);
        chengJi.setEnabled(isZd);
    }

    private void fillData() {
        etSchoolName.setText(bean.getSchoolName());
        etMajor.setText(bean.getMajorName());
        chengJi.setText(bean.getScore());
        if (bean.getDiploma() != null && bean.getDiploma() >= 0) {
            tvEducation.setText(GetConstDataUtils.getDiplomaList().get(bean.getDiploma()));
        }
        if (bean.getBeginTime() != null && bean.getEndTime() != null) {
            tvTime.setText(DateUtils.formatDate(bean.getBeginTime(), "yyyy-MM-dd") + " ～ " + DateUtils.formatDate(bean.getEndTime(), "yyyy-MM-dd"));
        }
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
                new DoubleDatePickerDialog(AddEducationHistoryActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d～%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                        if (GetDateUtils.getTimeStamp(startTime, "yyyy-MM-dd") > GetDateUtils.getTimeStamp(endTime, "yyyy-MM-dd")) {
                            ToastUtil.get().showToast(AddEducationHistoryActivity.this, "开始时间不能大于结束时间");
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
        EanfangHttp.post(UserApi.GET_TECH_WORKER_EDUCATION_DELETE + "/" + bean.getId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        showToast("删除成功");
                        finish();
                    }
                });
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
        entity.setScore(chengJi.getText().toString().trim());
        entity.setDiploma(GetConstDataUtils.getDiplomaList().indexOf(tvEducation.getText().toString().trim()));
        entity.setCertificateNumber(etNum.getText().toString().trim());
        if (!StringUtils.isEmpty(tvTime.getText().toString().trim())) {
            entity.setBeginTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[0], "yyyy-MM-dd"));
            entity.setEndTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[1], "yyyy-MM-dd"));
        }
        pic = PhotoUtils.getPhotoUrl("", selectList, uploadMap, false);
        entity.setCertificatePics(pic);
        entity.setType(0);
        SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
            runOnUiThread(() -> {
                EanfangHttp.post(url).upJson(JSONObject.toJSONString(entity)).execute(new EanfangCallback<JSONObject>(AddEducationHistoryActivity.this, true, JSONObject.class, (bean) -> {
                    setResult(RESULT_OK);
                    finish();
                }));
            });
        });
    }

    private boolean checkedData() {
        if (TextUtils.isEmpty(etMajor.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入专业或培训内容");
            return true;
        }
        return false;
    }
}
