package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.HonorCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import cn.qqtheme.framework.util.DateUtils;


public class SpecialistAddCertificationActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener {

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
    @BindView(R.id.picture_recycler)
    PictureRecycleView pictureRecycler;


    /**
     * 证书照片
     */
    private List<LocalMedia> picList_certificate = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();


    private static final int REQUEST_CODE_CHOOSE_CERTIFICATE = 1;
    private static final int REQUEST_CODE_PHOTO_CERTIFICATE = 101;
    private String pic;


    //        private TimePickerView mTimeYearMonthDay;
    private HonorCertificateEntity bean;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_specialist_add_certification);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();

    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
    @Override
    public void initView() {
        setLeftBack(true);

        bean = (HonorCertificateEntity) getIntent().getSerializableExtra("bean");
        doSelectYearMonthDay();
        if (bean != null) {
            fillData();
            setTitle("修改证书");
            picList_certificate = pictureRecycler.setData(bean.getHonorPics());
            pictureRecycler.showImagev(picList_certificate, listener);
            pictureRecycler.isShow(true, picList_certificate);
        } else {
            setTitle("添加证书");
            pictureRecycler.addImagev(listener);
        }
    }
    PictureRecycleView.ImageListener listener = list -> picList_certificate = list;
    /**
     * 只进行查看操作不看编辑
     */
    private void doUnWrite() {
        tvSave.setVisibility(View.GONE);
        etCertificate.setEnabled(false);
        etName.setEnabled(false);
        etOrg.setEnabled(false);
        llDate.setEnabled(false);
    }

    private void fillData() {
        etName.setText(bean.getHonorName());
        etOrg.setText(bean.getAwardOrg());
        tvTime.setText(DateUtils.formatDate(bean.getAwardTime(), "yyyy-MM-dd"));
        etName.setText(bean.getHonorName());
        etCertificate.setText(bean.getIntro());
    }

    @OnClick({R.id.ll_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                // 隐藏软键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SpecialistAddCertificationActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

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
        entity.setAccId(WorkerApplication.get().getAccId());
        url = "";
        if (bean != null) {
            entity.setId(bean.getId());
            url = UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_UPDATE;
        } else {
            url = UserApi.GET_TECH_WORKER_ADD_CERTIFICATE;

        }

        entity.setHonorName(etName.getText().toString().trim());
        entity.setAwardOrg(etOrg.getText().toString().trim());
        entity.setAwardTime(DateUtils.parseDate(tvTime.getText().toString().trim(), "yyyy-MM-dd"));
        entity.setHonorPics(pic);
        entity.setIntro(etCertificate.getText().toString().trim());
        entity.setType(1);
        SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
            runOnUiThread(() -> {

                EanfangHttp.post(url)
                        .upJson(JSONObject.toJSONString(entity))
                        .execute(new EanfangCallback<JSONObject>(SpecialistAddCertificationActivity.this, true, JSONObject.class, (bean) -> {
                            setResult(RESULT_OK);
                            finish();
                        }));


            });
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

        pic = PhotoUtils.getPhotoUrl("", picList_certificate, uploadMap, false);
        if (StrUtil.isEmpty(pic)) {
            showToast("请添加荣誉证书照片");
            return true;
        }

        if (TextUtils.isEmpty(etCertificate.getText().toString())) {
            ToastUtil.get().showToast(this, "请输入荣誉说明");
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

    @Override
    public void getData(String time) {
        if (StrUtil.isEmpty(time) || " ".equals(time)) {
            tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            tvTime.setText(time);
        }
    }
}
