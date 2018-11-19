package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    //    private TimePickerView mTimeYearMonthDay;
    private String url = "";
    private AptitudeCertificateEntity aptitudeCertificateEntity;

    // 起始 or 结束
    private boolean isBegin = true;

    // 认证状态
    private String isAuth;

    private Long mOrgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auth_qualify);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        setTitle("技能资质");
        setLeftBack();
        isAuth = getIntent().getStringExtra("isAuth");
        mOrgId = getIntent().getLongExtra("orgid", 0);
        // 查看详情
        aptitudeCertificateEntity = (AptitudeCertificateEntity) getIntent().getSerializableExtra("bean");

        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CERTIFICATE, REQUEST_CODE_PHOTO_CERTIFICATE));
        snplMomentAccident.setData(picList_certificate);

        doSelectYearMonthDay();

        if (aptitudeCertificateEntity != null) {
            fillData();
        }
        if ("2".equals(isAuth) || "1".equals(isAuth)) {//已认证  进行查看资质认证
            doUnWrite();
        }
    }

    private void initData() {

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
        String mLevel = etLevel.getText().toString().trim();
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
                runOnUiThread(() -> {
                    EanfangHttp.post(url).upJson(JSONObject.toJSONString(bean))
                            .execute(new EanfangCallback<JSONObject>(AddAuthQualifyActivity.this, true, JSONObject.class, bean1 -> {
                                setResult(RESULT_OK);
                                finishSelf();
                            }));
                });
            }
        });
    }

    /**
     * 只进行查看操作不看编辑
     */
    private void doUnWrite() {
        tvSave.setVisibility(View.GONE);
        etCertificateName.setEnabled(false);
        etOrg.setEnabled(false);
        etLevel.setEnabled(false);
        etNum.setEnabled(false);
        snplMomentAccident.setEditable(false);
        llBeginDate.setEnabled(false);
        llEndDate.setEnabled(false);
    }

    @OnClick({R.id.ll_begin_date, R.id.ll_end_date, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_begin_date:
                isBegin = true;
                // 隐藏软键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AddAuthQualifyActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                mTimeYearMonthDay.show();
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.ll_end_date:
                isBegin = false;
                // 隐藏软键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AddAuthQualifyActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                mTimeYearMonthDay.show();
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_save:
                doVerify();
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
//                if (isBegin) {
//                    tvBeginTime.setText(ETimeUtils.getTimeByYearMonthDay(date));
//                } else {
//                    tvEndTime.setText(ETimeUtils.getTimeByYearMonthDay(date));
//                }
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
