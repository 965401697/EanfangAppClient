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
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.util.DoubleDatePickerDialog;
import com.eanfang.biz.model.entity.QualificationCertificateEntity;

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
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.qqtheme.framework.util.DateUtils;

public class SpecialistAddSkillCertificafeActivity extends BaseWorkeActivity {

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
    @BindView(R.id.recycleview)
    PictureRecycleView recycleview;

    /**
     * 证书照片
     */
    private List<LocalMedia> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();


    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    private String pic;
    private String url;

    private QualificationCertificateEntity bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specialist_add_skill_certificafe);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        bean = (QualificationCertificateEntity) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            fillData();
            picList_certificate=recycleview.setData(bean.getCertificatePics());
            recycleview.showImagev(picList_certificate,listener);
            recycleview.isShow(true,picList_certificate);
            setTitle("修改技能资质");
        } else {
            setTitle("添加技能资质");
            recycleview.addImagev(listener);
        }
    }
    PictureRecycleView.ImageListener listener = list -> picList_certificate = list;
    @Override
    protected ViewModel initViewModel() {
        return null;
    }


    private void fillData() {
        etCertificateName.setText(bean.getCertificateName());
        etOrg.setText(bean.getAwardOrg());
        etLevel.setText(bean.getCertificateLevel());
        etNum.setText(bean.getCertificateNumber());
        tvTime.setText(DateUtils.formatDate(bean.getBeginTime(), "yyyy-MM-dd") + " ～ " + DateUtils.formatDate(bean.getEndTime(), "yyyy-MM-dd"));
    }


    private void setData() {

        if (checkedData()) {
            return;
        }

        QualificationCertificateEntity entity = new QualificationCertificateEntity();
        entity.setAccId(WorkerApplication.get().getAccId());
        if (bean != null) {
            entity.setId(bean.getId());
            url = UserApi.TECH_WORKER_UPDATA_QUALIFY;
        } else {
            url = UserApi.TECH_WORKER_ADD_QUALIFY;
        }
        entity.setCertificateName(etCertificateName.getText().toString().trim());
        entity.setAwardOrg(etOrg.getText().toString().trim());
        entity.setAccId(WorkerApplication.get().getAccId());
        entity.setBeginTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[0], "yyyy-M-dd"));
        entity.setEndTime(DateUtils.parseDate(tvTime.getText().toString().trim().split("～")[1], "yyyy-M-dd"));
        entity.setCertificateLevel(etLevel.getText().toString().trim());
        entity.setCertificateNumber(etNum.getText().toString().trim());
        entity.setCertificatePics(pic);

        entity.setType(1);
        SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
            runOnUiThread(() -> {

                EanfangHttp.post(url)
                        .upJson(JSONObject.toJSONString(entity))
                        .execute(new EanfangCallback<JSONObject>(SpecialistAddSkillCertificafeActivity.this, true, JSONObject.class, (bean) -> {
                            setResult(RESULT_OK);
                            finish();
                        }));


            });
        });


    }


    private boolean checkedData() {
        if (TextUtils.isEmpty(etCertificateName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入证书名称");
            return true;
        }

        if (TextUtils.isEmpty(etOrg.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入发证机构");
            return true;
        }
        if (TextUtils.isEmpty(etLevel.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入资质等级");
            return true;
        }
        if (TextUtils.isEmpty(etNum.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入证书编号");
            return true;
        }

        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.get().showToast(this, "请选择有效时间");
            return true;
        }

        pic = PhotoUtils.getPhotoUrl("", picList_certificate, uploadMap, false);
        if (StrUtil.isEmpty(pic)) {
            showToast("请添加证书照片");
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
                new DoubleDatePickerDialog(SpecialistAddSkillCertificafeActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d～%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                        if (DateUtil.parse(startTime,"yyyy-M-dd").getTime()>DateUtil.parse(endTime,"yyyy-M-dd").getTime()) {
                            ToastUtil.get().showToast(SpecialistAddSkillCertificafeActivity.this, "开始时间不能大于结束时间");
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
            default:
        }
    }
}
