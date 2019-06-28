package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkerInfoBean;

import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

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
@Deprecated
public class AuthWorkerInfoActivity extends BaseActivityWithTakePhoto {


    private final static int RESULT_ADD_PHOTO = 200;
    private final static int REQUETST_ADD_PHOTO = 100;

    private final int HEADER_PIC = 107;

    @BindView(R.id.ll_headers)
    LinearLayout llHeaders;
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
    CircleImageView ivHeader;
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
    private int status;
    // 是否编辑
    private boolean isEdit = false;
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
        setRightTitle("编辑");
        setLeftBack();
        //设置表情过滤，最多输入字数为100
        etIntro.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(100)});
        // 解决自动滑动
        nestedScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        nestedScrollView.setFocusable(true);
        nestedScrollView.setFocusableInTouchMode(true);
        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
        status = getIntent().getIntExtra("status", status);
        fillData();
    }

    private void setOnClick() {
        llWorkingLevel.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvWorkingLevel, GetConstDataUtils.getWorkingLevelList()));
        llWorkingYear.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvWorkingYear, GetConstDataUtils.getWorkingYearList()));
        llPayType.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvPayType, GetConstDataUtils.getPayTypeList()));

        llHeaders.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> takePhoto(AuthWorkerInfoActivity.this, HEADER_PIC)));

        setRightTitleOnClickListener((v) -> {
            showToast("可以进行编辑");
            isEdit = true;
            setRightGone();
            doRevoke();
        });

        // 上传身份证件信息
        rlUploadInfo.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("workerInfoBean", workerInfoBean);
            if (workerInfoBean.getIdCardFront() != null) {
                bundle.putBoolean("isAdd", true);
                bundle.putBoolean("isEdit", isEdit);
                bundle.putInt("verifyStatus", status);
            }
            JumpItent.jump(AuthWorkerInfoActivity.this, AuthPhotoActivity.class, bundle, REQUETST_ADD_PHOTO);
        });

        /**
         *  0 技师未认证，待认证
         *  1认证中
         *  2已认证
         *  3认证失败，请重新认证
         * */
        tvConfim.setOnClickListener((v) -> {
            if (status == 0 || status == 3) {
                setData();
            } else if (status == 2) {
                if (isEdit) {
                    doUndoVerify();
                } else {
                    finishSelf();
                }
            } else {
                finishSelf();
            }
        });
        // 已经认证成功/ 已经提交认证，正在认证中 无法点击操作
        if (status == 2 || status == 1) {
            tvConfim.setText("确定");
            // 不可编辑
            doSetGone();
        }
        if (status != 2) {
            setRightGone();
        }

    }

    /**
     * 进行撤销认证操作
     */
    public void doUndoVerify() {
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.WORKER_AUTH_REVOKE + WorkerApplication.get().getAccId())
                    .execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                        setData();
                    }));
        }).showDialog();
    }

    /**
     * 重新编辑
     */
    private void doRevoke() {
        // 掉编辑接口
        etPayAccount.setEnabled(true);
        llWorkingLevel.setEnabled(true);
        llWorkingYear.setEnabled(true);
        llPayType.setEnabled(true);
        etIntro.setEnabled(true);
        etUrgentPhone.setEnabled(true);
    }

    private void fillData() {
        String contactName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        String mobile = WorkerApplication.get().getLoginBean().getAccount().getMobile();
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
                GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getAvatarPhoto()),ivHeader);
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
        workerInfoBean.setUserId(WorkerApplication.get().getLoginBean().getAccount().getNullUser());
        workerInfoBean.setId(workerInfoBean.getId());

        workerInfoBean.setStatus(0);
        String json = JSONObject.toJSONString(workerInfoBean);
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                runOnUiThread(() -> submitSuccess(json));
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
        llHeaders.setEnabled(false);
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
        String imgKey = "account/"+UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEADER_PIC:
                workerInfoBean.setAvatarPhoto(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivHeader);
                break;
            default:
                break;
        }
        SDKManager.ossKit(this).asyncPutImage(imgKey, image.getOriginalPath(), (isSuccess) -> {});
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
