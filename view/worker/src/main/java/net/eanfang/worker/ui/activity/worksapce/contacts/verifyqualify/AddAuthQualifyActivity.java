package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.ui.fragment.SelectTimeDialogFragment;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.AptitudeCertificateEntity;

import net.eanfang.worker.R;

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
 * @description 添加资质证书
 */

public class AddAuthQualifyActivity extends BaseActivityWithTakePhoto implements SelectTimeDialogFragment.SelectTimeListener {

    // 证书名称

    @BindView(R.id.et_certificate_name)
    EditText etCertificateName;
    // 发证机构

    @BindView(R.id.et_org)
    EditText etOrg;
    // 资质等级

    @BindView(R.id.et_level)
    EditText etLevel;
    // 证书编号

    @BindView(R.id.et_num)
    EditText etNum;
    // 有效时间

    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.ll_begin_date)
    LinearLayout llBeginDate;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_date)
    LinearLayout llEndDate;
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;
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
    private String url = "";
    private AptitudeCertificateEntity aptitudeCertificateEntity;
    private boolean isBegin = true;
    private String isAuth;
    private Long mOrgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auth_qualify);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        isAuth = getIntent().getStringExtra("isAuth");
        mOrgId = getIntent().getLongExtra("orgid", 0);
        aptitudeCertificateEntity = (AptitudeCertificateEntity) getIntent().getSerializableExtra("bean");
        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
        snplMomentAccident.setData(picList_certificate);
        doSelectYearMonthDay();
        setRightTitleOnClickListener(view -> doVerify());
        if (aptitudeCertificateEntity != null) {
            setTitle("资质证书");
            setRightTitle("编辑");
            setZhiDu(false);
            fillData();
            setRightTitleOnClickListener(view -> {
                        setRightTitle("保存");
                        setZhiDu(true);
                        setRightTitleOnClickListener(view1 -> doVerify());
                    }
            );
        } else {
            setRightTitle("保存");
            setTitle("资质证书");
            tvSave.setVisibility(View.GONE);
        }
    }

    private void setZhiDu(boolean isZd) {
        tvSave.setVisibility(isZd?View.VISIBLE:View.GONE);
        etCertificateName.setEnabled(isZd);
        etNum.setEnabled(isZd);
        etOrg.setEnabled(isZd);
        llBeginDate.setEnabled(isZd);
        llEndDate.setEnabled(isZd);
        snplMomentAccident.setPlusEnable(isZd);
        snplMomentAccident.setEditable(isZd);
    }

    private void fillData() {
        ArrayList<String> picList = new ArrayList<>();
        if (aptitudeCertificateEntity.getCertificatePics() != null) {
            String[] pics = aptitudeCertificateEntity.getCertificatePics().split(",");
            for (int i = 0; i < pics.length; i++) {
                picList.add(BuildConfig.OSS_SERVER + pics[i]);
            }
        }
        // 证书名称
        etCertificateName.setText(aptitudeCertificateEntity.getCertificateName());
        // 发证机构
        etOrg.setText(aptitudeCertificateEntity.getAwardOrg());
        // 资质等级
        etLevel.setText(aptitudeCertificateEntity.getCertificateLevel());
        // 证书编号
        etNum.setText(aptitudeCertificateEntity.getCertificateNumber());
        // 有效时间
        tvBeginTime.setText(V.v(() -> DateUtils.formatDate(aptitudeCertificateEntity.getBeginTime(), "yyyy-MM-dd")));
        // 有效时间
        tvEndTime.setText(V.v(() -> DateUtils.formatDate(aptitudeCertificateEntity.getEndTime(), "yyyy-MM-dd")));
        // 照片
        snplMomentAccident.setData(picList);
    }

    public void doVerify() {
        // 证书名称
        String mCertificateName = etCertificateName.getText().toString().trim();
        // 发证机构
        String mOrg = etOrg.getText().toString().trim();
        // 资质等级

        String mLevel = "1";
        // 证书编号
        String mNum = etNum.getText().toString().trim();
        // 有效时间
        String mBeginTime = tvBeginTime.getText().toString().trim();
        String mEndTime = tvEndTime.getText().toString().trim();

        if (StringUtils.isEmpty(mCertificateName)) {
            showToast("请输入证书名称");
            return;
        }
        if (StringUtils.isEmpty(mOrg)) {
            showToast("请输入发证机构");
            return;
        }
        if (StringUtils.isEmpty(mLevel)) {
            showToast("请输入资质等级");
            return;
        }
        if (StringUtils.isEmpty(mNum)) {
            showToast("请输入证书编号");
            return;
        }
        if (StringUtils.isEmpty(mBeginTime)) {
            showToast("请输入开始时间");
            return;
        }
        if (StringUtils.isEmpty(mEndTime)) {
            showToast("请输入结束时间");
            return;
        }

        pic = PhotoUtils.getPhotoUrl("", snplMomentAccident, uploadMap, false);
        if (StringUtils.isEmpty(pic)) {
            showToast("请输入证书照片");
            return;
        }

        AptitudeCertificateEntity bean = new AptitudeCertificateEntity();
        bean.setCertificateName(mCertificateName);
        bean.setAwardOrg(mOrg);
        bean.setOrgId(mOrgId);
        bean.setCertificateLevel(mLevel);
        bean.setCertificateNumber(mNum);
        bean.setBeginTime(DateUtils.parseDate(mBeginTime, "yyyy-MM-dd"));
        bean.setEndTime(DateUtils.parseDate(mEndTime, "yyyy-MM-dd"));
        bean.setCertificatePics(pic);
        // TODO
        bean.setType(0);
        if (aptitudeCertificateEntity != null) {
            bean.setId(aptitudeCertificateEntity.getId());
            url = UserApi.UPDATA_QUALIFY;
        } else {
            url = UserApi.ADD_QUALIFY;
        }
        OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
            @Override
            public void onOssSuccess() {
                runOnUiThread(() -> EanfangHttp.post(url).upJson(JSONObject.toJSONString(bean)).execute(new EanfangCallback<JSONObject>(AddAuthQualifyActivity.this, true, JSONObject.class, bean1 -> {
                    setResult(RESULT_OK);
                    finishSelf();
                })));
            }
        });
    }


    @OnClick({R.id.ll_begin_date, R.id.ll_end_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_begin_date:
                isBegin = true;
                ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(Objects.requireNonNull(AddAuthQualifyActivity.this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.ll_end_date:
                isBegin = false;
                ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(Objects.requireNonNull(AddAuthQualifyActivity.this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_save:
                if (aptitudeCertificateEntity != null) {
                    delete();
                } else {
                    finish();
                }
                break;
            default:
        }
    }

    private void delete() {
        EanfangHttp.post(UserApi.DELETE_QUALIFY + "/" + aptitudeCertificateEntity.getId())
                .execute(new EanfangCallback<org.json.JSONObject>(this, true, org.json.JSONObject.class) {
                    @Override
                    public void onSuccess(org.json.JSONObject bean) {
                        Toast.makeText(AddAuthQualifyActivity.this, "删除成功", Toast.LENGTH_LONG).show();
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
        if (requestCode == REQUEST_CODE_CHOOSE_CERTIFICATE) {
            snplMomentAccident.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        }
    }

    /**
     * 选择年月日
     */
    public void doSelectYearMonthDay() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1960, 1, 1);
        endDate.set(2040, 11, 31);
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void getData(String time) {
        if (isBegin) {
            if (StringUtils.isEmpty(time) || " ".equals(time)) {
                tvBeginTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                tvBeginTime.setText(time);
            }
        } else {
            if (StringUtils.isEmpty(time) || " ".equals(time)) {
                tvEndTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                tvEndTime.setText(time);
            }
        }
    }
}
