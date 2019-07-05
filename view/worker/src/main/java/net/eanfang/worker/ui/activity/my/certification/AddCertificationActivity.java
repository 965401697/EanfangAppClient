package net.eanfang.worker.ui.activity.my.certification;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;

import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/10/22
 * @description 添加荣誉证书
 */

public class AddCertificationActivity extends BaseWorkeActivity implements SelectTimeDialogFragment.SelectTimeListener {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_org)
    EditText etOrg;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_certificate)
    EditText etCertificate;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.recycleview)
    PictureRecycleView recycleview;
    private List<LocalMedia> selectList = new ArrayList<>();
    /**
     * 证书照片
     */
    private HashMap<String, String> uploadMap = new HashMap<>();
    private String pic;
    private HonorCertificateEntity bean;
    private String url = "";
    // 是否安防公司的荣誉证书

    private String isCompany = "";
    private Long orgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_certification);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    public void initView() {
        setLeftBack(true);
        isCompany = getIntent().getStringExtra("role");
        orgid = getIntent().getLongExtra("orgid", 0);
        bean = (HonorCertificateEntity) getIntent().getSerializableExtra("bean");
        doSelectYearMonthDay();
        setRightTitleOnClickListener(view -> setData());
        if (bean != null) {
            setTitle("荣誉");
            setRightTitle("编辑");
            setZhiDu(false);
            fillData();
            selectList = recycleview.setData(bean.getHonorPics());
            recycleview.showImagev(selectList, listener);
            setRightTitleOnClickListener(view -> {
                        setRightTitle("保存");
                        setZhiDu(true);
                        recycleview.isShow(true, selectList);
                        setRightTitleOnClickListener(view1 -> setData());
                    }

            );
        } else {
            setRightTitle("保存");
            setTitle("荣誉");
            tvSave.setVisibility(View.GONE);
            recycleview.addImagev(listener);
        }
    }

    PictureRecycleView.ImageListener listener = list -> selectList = list;

    private void setZhiDu(boolean isZd) {
        tvSave.setVisibility(isZd ? View.VISIBLE : View.GONE);
        etOrg.setEnabled(isZd);
        etName.setEnabled(isZd);
        llDate.setEnabled(isZd);
    }

    private void fillData() {
        etName.setText(bean.getHonorName());
        etOrg.setText(bean.getAwardOrg());
        if (bean.getAwardTime() != null) {
            tvTime.setText(DateUtils.formatDate(bean.getAwardTime(), "yyyy-MM-dd"));
        }
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
        pic = PhotoUtils.getPhotoUrl("", selectList, uploadMap, false);
        entity.setHonorPics(pic);
        entity.setIntro("");
        entity.setType(0);
        SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
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
