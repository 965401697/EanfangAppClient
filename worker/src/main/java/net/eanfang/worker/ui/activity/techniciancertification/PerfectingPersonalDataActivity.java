package net.eanfang.worker.ui.activity.techniciancertification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.yaf.sys.entity.AccountEntity;

import net.eanfang.worker.R;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class PerfectingPersonalDataActivity extends BaseActivityWithTakePhoto {
    @BindView(R.id.nc_et)
    EditText ncEt;
    @BindView(R.id.dq_dz_tv)
    TextView dqDzTv;
    @BindView(R.id.dq_dz_et)
    EditText dqDzEt;
    @BindView(R.id.sr_et)
    EditText srEt;
    private int mStatus;
    private int statusB;
    private final int HEADER_PIC = 107;

    @BindView(R.id.ll_headers)
    LinearLayout llHeaders;
    //联系人姓名

    @BindView(R.id.tv_contact_name)
    TextView tvContactName;

    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.et_card_id)
    EditText etCardId;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.tv_office_address)
    TextView tvOfficeAddress;
    @BindView(R.id.ll_office_address)
    LinearLayout llOfficeAddress;
    private String itemcity;
    private String itemzone;
    private String areaCode = "";
    private LoginBean loginBean = new LoginBean();
    /**
     * 地址回掉code
     */
    private final int ADDRESS_CALLBACK_CODE = 100;
    AccountEntity accountEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfecting_personal_data);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("个人资料");
        mStatus = getIntent().getIntExtra("status", -1);
        statusB = getIntent().getIntExtra("statusB", -1);
        String contactName = EanfangApplication.get().getUser().getAccount().getRealName();

        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        llHeaders.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(this, HEADER_PIC)));
        if (mStatus > 0) {
            EanfangHttp.get(UserApi.GET_USER_INFO).params("userId", EanfangApplication.get().getUserId()).tag(this).execute(new EanfangCallback<LoginBean>(this, true, LoginBean.class, (bean) -> {
                runOnUiThread(() -> {
                    loginBean = bean;
                    accountEntity = loginBean.getAccount();
                    fillData();
                });
            }));
        } else {
            loginBean = new LoginBean();
        }
        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
    }

    @SuppressLint("NewApi")
    private void fillData() {
        ivHeader.setImageURI(BuildConfig.OSS_SERVER + loginBean.getAccount().getAvatar());
        String contactName = EanfangApplication.get().getUser().getAccount().getRealName();
        if (!StringUtils.isEmpty(contactName)) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(loginBean.getAccount().getNickName())) {
            ncEt.setText(loginBean.getAccount().getNickName());
        }
        if (loginBean.getAccount().getAreaCode() != null) {
            areaCode = loginBean.getAccount().getAreaCode();
            tvOfficeAddress.setText(Config.get().getAddressByCode(loginBean.getAccount().getAreaCode()));
        }
        if (loginBean.getAccount().getAddress() != null) {
            dqDzEt.setText(loginBean.getAccount().getAddress());
        }
        if (loginBean.getAccount().getPersonalNote() != null) {
            etIntro.setText(loginBean.getAccount().getPersonalNote());
        }
        if (loginBean.getAccount().getBirthday() != null) {
            srEt.setText(new SimpleDateFormat("yyyyMMdd").format(loginBean.getAccount().getBirthday()));
        }
        etCardId.setText(EanfangApplication.get().getUser().getAccount().getIdCard());
        //0女1男
        if (EanfangApplication.get().getUser().getAccount().getGender() == 0) {
            rbWoman.setChecked(true);
        } else {
            rbMan.setChecked(true);
        }
        etIntro.setText(loginBean.getAccount().getPersonalNote());
    }

    private void initData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == ADDRESS_CALLBACK_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            itemcity = item.getCity();
            itemzone = item.getAddress();
            tvOfficeAddress.setText(item.getProvince() + itemcity + itemzone);
            //将选择的地址 取 显示值
            areaCode = Config.get().getAreaCodeByName(itemcity, itemzone);
            Log.d("58ma", "onActivityResult: " + areaCode);
            tvOfficeAddress.setText(item.getName());
            accountEntity.setAreaCode(areaCode);
        }
    }

    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = "account/" + UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEADER_PIC:
                accountEntity.setAvatar(imgKey);
                ivHeader.setImageURI("file://" + image.getOriginalPath());
                break;
            default:
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {


        });

    }

    @SuppressLint("NewApi")
    private void setData() {
        String cardId = etCardId.getText().toString().trim();
        String info = etIntro.getText().toString().trim();
        String ncE = ncEt.getText().toString().trim();
        String dqDzEtS = dqDzEt.getText().toString().trim();
        String srEts = srEt.getText().toString().trim();

        if (StringUtils.isEmpty(loginBean.getAccount().getAvatar())) {
            showToast("请选择技师头像");
            return;
        }
        if (StringUtils.isEmpty(ncE)) {
            showToast("请输入昵称");
            return;
        }
        if (StringUtils.isEmpty(areaCode)) {
            showToast("请选择当前城市");
            return;
        }
        if (StringUtils.isEmpty(dqDzEtS)) {
            showToast("请输入当前地址");
            return;
        }

        if (StringUtils.isEmpty(srEts)) {
            showToast("请输入生日");
            return;
        }

        if (StringUtils.isEmpty(cardId)) {
            showToast("请输入身份证号");
            return;
        }
        if (StringUtils.isEmpty(info)) {
            showToast("请输入个人简介");
            return;
        }
        try {
            accountEntity.setBirthday( new SimpleDateFormat("yyyy-MM-dd").parse(dqDzEtS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        accountEntity.setAddress(dqDzEtS);
        accountEntity.setNickName(ncE);
        accountEntity.setAccId(EanfangApplication.get().getAccId());
        accountEntity.setRealName(tvContactName.getText().toString().trim());
        accountEntity.setIdCard(cardId);
        accountEntity.setGender(rbMan.isChecked() ? 1 : 0);
        accountEntity.setPersonalNote(info);
        accountEntity.getDefaultUser().setUserId( EanfangApplication.get().getUserId());
        accountEntity.setAccId( EanfangApplication.get().getAccId());

        loginBean.setAccount(accountEntity);
        Log.d("78987", "setData: " + loginBean.getAccount().getNickName() + "  " + loginBean.getAccount().getPersonalNote() + JSONObject.toJSONString(loginBean) + "\n" + UserApi.BC_GR_ZL);
        EanfangHttp.post(UserApi.BC_GR_ZL).upJson(JSONObject.toJSONString(accountEntity)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            Intent intent = new Intent(this, RealNameAuthenticationActivity.class);
            intent.putExtra("bean", loginBean);
            intent.putExtra("statusB", statusB);
            startActivity(intent);
            finish();
        }));
    }

    @OnClick(R.id.tv_confim)
    public void onViewClicked() {
        setData();
    }
}
