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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

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
import net.eanfang.worker.base.WorkerApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

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
        setRightTitleOnClickListener(view -> setData());
        if (bean != null) {
            setTitle("荣誉");
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
            setRightTitle("保存");
            setTitle("荣誉");
            tvSave.setVisibility(View.GONE);
        }
    }

    private void setZhiDu(boolean isZd) {
        tvSave.setVisibility(isZd ? View.VISIBLE : View.GONE);
        etOrg.setEnabled(isZd);
        etName.setEnabled(isZd);
        llDate.setEnabled(isZd);
        snplMomentAccident.setPlusEnable(isZd || StringUtils.isEmpty(bean.getHonorPics()));
        snplMomentAccident.setEditable(isZd || StringUtils.isEmpty(bean.getHonorPics()));
        snplMomentAccident.setItemClickble(isZd);
    }

    private void fillData() {
        ArrayList<String> picList = new ArrayList<>();
        if (bean.getHonorPics() != null && bean.getHonorPics().length() > 0) {
            String[] pics = bean.getHonorPics().split(",");
            for (String pic1 : pics) {
                picList.add(BuildConfig.OSS_SERVER + pic1);
            }
        }
        etName.setText(bean.getHonorName());
        etOrg.setText(bean.getAwardOrg());
        if (bean.getAwardTime() != null) {
            tvTime.setText(DateUtils.formatDate(bean.getAwardTime(), "yyyy-MM-dd"));
        }
        snplMomentAccident.setData(picList);
        etName.setText(bean.getHonorName());

    }

    @OnClick({R.id.ll_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(Objects.requireNonNull(AddCertificationActivity.this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_save:
                if (bean != null) {
                    delete();
                } else {
                    finish();
                }
                break;
            default:
        }
    }

    private void delete() {
        String url;
        if (isCompany.equals("company")) {
            url = UserApi.COMPANY_CERTIFICATE_DELETE;
            // 安防公司
        } else {
            url = UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_DELETE;
            // 技师认证
        }
        EanfangHttp.post(url + "/" + bean.getId()).execute(new EanfangCallback<org.json.JSONObject>(this, true, org.json.JSONObject.class) {
            @Override
            public void onSuccess(org.json.JSONObject bean) {
                Toast.makeText(AddCertificationActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                finish();
            }

        });
    }

    private void setData() {
        if (checkedData()) {
            return;
        }
        HonorCertificateEntity entity = new HonorCertificateEntity();
        entity.setAccId(WorkerApplication.get().getAccId());
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
        pic = PhotoUtils.getPhotoUrl("", snplMomentAccident, uploadMap, false);
        entity.setHonorPics(pic);
        entity.setIntro("");
        entity.setType(0);
        SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
            runOnUiThread(() -> EanfangHttp.post(url).upJson(JSONObject.toJSONString(entity)).execute(new EanfangCallback<JSONObject>(AddCertificationActivity.this, true, JSONObject.class, (bean) -> {
                setResult(RESULT_OK);
                finish();
            })));
        });
    }


    private boolean checkedData() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入荣誉名称");
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
    }

    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            tvTime.setText(time);
        }
    }
}
