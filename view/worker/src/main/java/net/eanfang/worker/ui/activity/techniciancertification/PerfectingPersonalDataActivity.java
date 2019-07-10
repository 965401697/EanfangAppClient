package net.eanfang.worker.ui.activity.techniciancertification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.model.bean.LoginBean;

import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class PerfectingPersonalDataActivity extends BaseWorkeActivity {
    @BindView(R.id.nc_et)
    EditText ncEt;
    @BindView(R.id.dq_dz_tv)
    TextView dqDzTv;
    @BindView(R.id.dq_dz_et)
    EditText dqDzEt;
    @BindView(R.id.sr_et)
    TextView srEt;
    private int mStatus;
    private int statusB;
    private final int HEADER_PIC = 107;

    @BindView(R.id.ll_headers)
    LinearLayout llHeaders;
    //联系人姓名

    @BindView(R.id.tv_contact_name)
    EditText tvContactName;

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
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
    private Date date;


    /**
     * 地址回掉code
     */
    private final int ADDRESS_CALLBACK_CODE = 100;
    AccountEntity accountEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_perfecting_personal_data);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void headImage() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = "account/" + UuidUtil.getUUID() + "tx.png";
            accountEntity.setAvatar(imgKey);
            GlideUtil.intoImageView(PerfectingPersonalDataActivity.this, "file://" + list.get(0).getPath(), ivHeader);
            SDKManager.ossKit(PerfectingPersonalDataActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
            });
        }
    };
    @Override
    public void initView() {
        setLeftBack(true);
        setTitle("个人资料");
        mStatus = getIntent().getIntExtra("status", -1);
        statusB = getIntent().getIntExtra("statusB", -1);
        llHeaders.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> headImage()));
        EanfangHttp.get(UserApi.GET_USER_INFO).params("userId", WorkerApplication.get().getUserId()).tag(this).execute(new EanfangCallback<LoginBean>(this, true, LoginBean.class, (bean) -> {
            runOnUiThread(() -> {
                accountEntity = bean.getAccount();
                fillData();
            });
        }));

        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        srEt.setOnClickListener(view -> setRq());
    }

    @SuppressLint("NewApi")
    private void fillData() {
        GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + accountEntity.getAvatar(), ivHeader);
        String contactName = WorkerApplication.get().getLoginBean().getAccount().getRealName();
        if ((!StringUtils.isEmpty(contactName)) & (!contactName.contains("易安防")) & (!contactName.contains("匿名用户"))) {
            tvContactName.setText(contactName);
        }
        if (!StringUtils.isEmpty(accountEntity.getNickName())) {
            ncEt.setText(accountEntity.getNickName());
        }
        if (accountEntity.getAreaCode() != null) {
            areaCode = accountEntity.getAreaCode();
            tvOfficeAddress.setText(Config.get().getAddressByCode(accountEntity.getAreaCode()));
        }
        if (accountEntity.getIdCard() != null) {
            etCardId.setText(accountEntity.getIdCard());
        }
        if (accountEntity.getRealName() != null) {
            tvContactName.setText(accountEntity.getRealName());
        }
        if (accountEntity.getAddress() != null) {
            dqDzEt.setText(accountEntity.getAddress());
        }
        if (accountEntity.getPersonalNote() != null) {
            etIntro.setText(accountEntity.getPersonalNote());
        }
        if (accountEntity.getBirthday() != null) {
            date = accountEntity.getBirthday();
            srEt.setText(new SimpleDateFormat("yyyy年MM月dd日").format(date));
        }
        etCardId.setText(WorkerApplication.get().getLoginBean().getAccount().getIdCard());
        //0女1男
        if (WorkerApplication.get().getLoginBean().getAccount().getGender() == 0) {
            rbWoman.setChecked(true);
        } else {
            rbMan.setChecked(true);
        }
        etIntro.setText(accountEntity.getPersonalNote());
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


    private void setRq() {
        View view = getLayoutInflater().inflate(R.layout.activity_dialog_date, null);
        CalendarView datePicker = view.findViewById(R.id.calendarView);
        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            date = new GregorianCalendar(year, month, dayOfMonth).getTime();
            srEt.setText(GetDateUtils.dateToDateString(date));
        });
        new AlertDialog.Builder(this).setView(view).setCancelable(false).setPositiveButton("确定", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
    }

    @SuppressLint("NewApi")
    private void setData() {
        String cardId = etCardId.getText().toString().trim();
        String info = etIntro.getText().toString().trim();
        String ncE = ncEt.getText().toString().trim();
        String dqDzEtS = dqDzEt.getText().toString().trim();
        String srEts = srEt.getText().toString().trim();
        String tvContactNames = tvContactName.getText().toString().trim();
        if (StringUtils.isEmpty(accountEntity.getAvatar())) {
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
        if (StringUtils.isEmpty(cardId)) {
            showToast("请输入身份证号");
            return;
        }
        if (StringUtils.isEmpty(tvContactNames)) {
            showToast("请输入真实姓名");
            return;
        }
        if (StringUtils.isEmpty(srEts)) {
            showToast("请输入生日");
            return;
        }
        if (StringUtils.isEmpty(info)) {
            showToast("请输入个人简介");
            return;
        }
        accountEntity.setBirthday(date);
        accountEntity.setRealName(tvContactNames);
        accountEntity.setAddress(dqDzEtS);
        accountEntity.setNickName(ncE);
        accountEntity.setAccId(WorkerApplication.get().getAccId());
        accountEntity.setRealName(tvContactName.getText().toString().trim());
        accountEntity.setIdCard(cardId);
        accountEntity.setGender(rbMan.isChecked() ? 1 : 0);
        accountEntity.setPersonalNote(info);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(WorkerApplication.get().getUserId());
        accountEntity.setDefaultUser(userEntity);
        accountEntity.setAccId(WorkerApplication.get().getAccId());
        LoginBean loginBean = new LoginBean();
        loginBean.setAccount(accountEntity);
        EanfangHttp.post(UserApi.BC_GR_ZL).upJson(JSONObject.toJSONString(accountEntity)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            LoginBean user = WorkerApplication.get().getLoginBean();
            AccountEntity accountEntity1 = user.getAccount();
            accountEntity1.setNickName(ncE);
            user.setAccount(accountEntity1);
            WorkerApplication.get().set(LoginBean.class.getName(), user);
            Intent intent = new Intent(this, RealNameAuthenticationActivity.class);
            intent.putExtra("statusB", statusB);
            startActivity(intent);
            finish();
        }));
    }

    @OnClick(R.id.tv_confim)
    public void onViewClicked() {
        if (accountEntity != null) {
            setData();
        } else {
            showToast("请添加头像");
        }
    }
}
