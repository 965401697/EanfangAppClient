package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/30  20:31
 * @email houzhongzhou@yeah.net
 * @desc 技师认证 填写个人资料 照片 等
 */
public class AuthWorkerInfoActivity extends BaseActivityWithTakePhoto {


    private final static int RESULT_ADD_PHOTO = 200;
    private final static int REQUETST_ADD_PHOTO = 100;

    private final int HEADER_PIC = 107;

    //联系人姓名
    @BindView(R.id.tv_contact_name)
    TextView tvContactName;
    // 联系人电话
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;
    // 能力等级
    @BindView(R.id.tv_workingLevel)
    TextView tvWorkingLevel;
    @BindView(R.id.ll_workingLevel)
    LinearLayout llWorkingLevel;
    // 从业年限
    @BindView(R.id.tv_workingYear)
    TextView tvWorkingYear;
    @BindView(R.id.ll_workingYear)
    LinearLayout llWorkingYear;
    // 个人简介
    @BindView(R.id.et_intro)
    EditText etIntro;
    // 支付账户
    @BindView(R.id.et_payAccount)
    EditText etPayAccount;
    // 支付类型
    @BindView(R.id.ll_payType)
    LinearLayout llPayType;
    @BindView(R.id.tv_payType)
    TextView tvPayType;

    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    // 紧急联系电话
    @BindView(R.id.et_urgent_phone)
    EditText etUrgentPhone;
    // 紧急联系人
    @BindView(R.id.et_urgent_name)
    EditText etUrgentName;
    // 上传身份信息
    @BindView(R.id.rl_upload_info)
    RelativeLayout rlUploadInfo;
    // 提交认证
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private String isAuthen = "";

    //edittext拦截器
    InputFilter inputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                showToast("不支持输入表情");
                return "";
            }
            return null;
        }
    };

    private WorkerInfoBean workerInfoBean;
    private Map<String, String> uploadMap = new HashMap<>();
    private ArrayList<String> honorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_worker);
        ButterKnife.bind(this);
        initView();
        setOnClick();

    }

    private void initView() {
        setTitle("填写技师资料");
        setLeftBack();
        //设置表情过滤，最多输入字数为100
        etIntro.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(100)});
        // 解决自动滑动
        nestedScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        nestedScrollView.setFocusable(true);
        nestedScrollView.setFocusableInTouchMode(true);
        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
        fillData();
    }

    private void setOnClick() {
        llWorkingLevel.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvWorkingLevel, GetConstDataUtils.getWorkingLevelList()));
        llWorkingYear.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvWorkingYear, GetConstDataUtils.getWorkingYearList()));
        llPayType.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvPayType, GetConstDataUtils.getPayTypeList()));

        ivHeader.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthWorkerInfoActivity.this, HEADER_PIC)));
        setRightTitleOnClickListener((v) -> {

        });

        rlUploadInfo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("workerInfoBean", workerInfoBean);
            if (workerInfoBean.getIdCardFront() != null) {
                bundle.putBoolean("isAdd", true);
            }
            JumpItent.jump(this, AuthPhotoActivity.class, bundle, REQUETST_ADD_PHOTO);
        });

        tvConfim.setOnClickListener((v) -> {
            if (workerInfoBean.getStatus() == 0 || workerInfoBean.getStatus() == 3) {
                setData();
            } else {
                finishSelf();
            }
        });

        // 已经认证成功/ 已经提交认证，正在认证中 无法编辑
        if (workerInfoBean.getStatus() == 2 || workerInfoBean.getStatus() == 1) {
            doSetGone();
        }

    }

    private void fillData() {
        String contactName = EanfangApplication.get().getUser().getAccount().getRealName();
        String mobile = EanfangApplication.get().getUser().getAccount().getMobile();
        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            tvContactPhone.setText(mobile);
        }
        if (workerInfoBean != null) {
            if (workerInfoBean.getContactName() != null) {
                etUrgentName.setText(workerInfoBean.getContactName());
            }
            if (workerInfoBean.getContactPhone() != null) {
                etUrgentPhone.setText(workerInfoBean.getContactPhone());
            }
            if (workerInfoBean.getWorkingLevel() >= 0) {
                tvWorkingLevel.setText(GetConstDataUtils.getWorkingLevelList().get(workerInfoBean.getWorkingLevel()));
            }
            if (workerInfoBean.getWorkingYear() >= 0) {
                tvWorkingYear.setText(GetConstDataUtils.getWorkingYearList().get(workerInfoBean.getWorkingYear()));
            }
            if (workerInfoBean.getPayType() >= 0) {
                tvPayType.setText(GetConstDataUtils.getPayTypeList().get(workerInfoBean.getPayType()));
            }
            if (!StringUtils.isEmpty(workerInfoBean.getPayAccount())) {
                etPayAccount.setText(workerInfoBean.getPayAccount());
            }

            if (!StringUtils.isEmpty(workerInfoBean.getAvatarPhoto())) {
                ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getAvatarPhoto()));
            }
            if (!StringUtils.isEmpty(workerInfoBean.getIntro())) {
                etIntro.setText(workerInfoBean.getIntro());
            }
        }
    }

    private void setData() {

        String mPayAccount = etPayAccount.getText().toString().trim();
        String mUrgentName = etUrgentName.getText().toString().trim();
        String mUrgentPhone = etUrgentPhone.getText().toString().trim();
        String mEtIntro = etIntro.getText().toString().trim();

        if (StringUtils.isEmpty(workerInfoBean.getAvatarPhoto())) {
            showToast("请选择技师头像");
            return;
        } else if (StringUtils.isEmpty(workerInfoBean.getAvatarPhoto())) {
            workerInfoBean.setAvatarPhoto(workerInfoBean.getAvatarPhoto());
        }

        if (StringUtils.isEmpty(mUrgentName)) {
            showToast("请输入紧急联系人");
            return;
        }
        if (StringUtils.isEmpty(mUrgentPhone)) {
            showToast("请输入紧急联系人电话");
            return;
        }
        if (StringUtils.isEmpty(mPayAccount)) {
            showToast("请输入支付账户");
            return;
        }
        if (StringUtils.isEmpty(mEtIntro)) {
            showToast("请填写个人简介");
            return;
        }

        if (StringUtils.isEmpty(workerInfoBean.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StringUtils.isEmpty(workerInfoBean.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StringUtils.isEmpty(workerInfoBean.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }
        workerInfoBean.setContactName(mUrgentName);
        workerInfoBean.setContactPhone(mUrgentPhone);
        workerInfoBean.setPayAccount(mPayAccount);
        workerInfoBean.setIntro(mEtIntro);
        workerInfoBean.setPayType(GetConstDataUtils.getPayTypeList().indexOf(tvPayType.getText().toString().trim()));
        workerInfoBean.setWorkingLevel(GetConstDataUtils.getWorkingLevelList().indexOf(tvWorkingLevel.getText().toString().trim()));
        workerInfoBean.setWorkingYear(GetConstDataUtils.getWorkingYearList().indexOf(tvWorkingYear.getText().toString().trim()));
        workerInfoBean.setAccId(workerInfoBean.getAccId());
        workerInfoBean.setUserId(EanfangApplication.getApplication().getUser().getAccount().getNullUser());
        workerInfoBean.setId(workerInfoBean.getId());

        workerInfoBean.setStatus(0);
        String json = JSONObject.toJSONString(workerInfoBean);
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> submitSuccess(json));
                }
            });
        } else {
            submitSuccess(json);
        }
    }

    private void submitSuccess(String json) {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    finishSelf();
                }));
    }

    // 提交成功 文本不可编辑 图片不可修改
    private void doSetGone() {
        etPayAccount.setEnabled(false);
        llWorkingLevel.setEnabled(false);
        llWorkingYear.setEnabled(false);
        llPayType.setEnabled(false);
        etIntro.setEnabled(false);
        etUrgentPhone.setEnabled(false);
        etUrgentName.setEnabled(false);
    }


    /**
     * 图片选择 回调
     *
     * @param result
     * @param resultCode
     */
    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEADER_PIC:
                workerInfoBean.setAvatarPhoto(imgKey);
                ivHeader.setImageURI("file://" + image.getOriginalPath());
                break;
            default:
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUETST_ADD_PHOTO && resultCode == RESULT_ADD_PHOTO) {
            workerInfoBean = (WorkerInfoBean) data.getSerializableExtra("photos");
        }

    }
}
