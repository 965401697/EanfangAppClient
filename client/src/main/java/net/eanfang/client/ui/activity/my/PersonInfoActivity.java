package net.eanfang.client.ui.activity.my;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.LoginBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TResult;
import com.yaf.sys.entity.AccountEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.OwnDataHintActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * 我的-个人资料
 * Created by Administrator on 2017/3/15.
 */

public class PersonInfoActivity extends BaseActivityWithTakePhoto {

    private static final int SELECT_ADDRESS_CALL_BACK_CODE = 1;
    /**
     * 昵称最大长度
     */
    private static final int MAXIMUM_LENGTH = 8;
    private static final String DEFAULT_REAL_NAME = "待提供";
    private static final String DEFAULT_NAME = "匿名用户";
    @BindView(R.id.et_personal_note)
    EditText mEtPersonalNote;
    @BindView(R.id.tv_save)
    Button mTvSave;
    @BindView(R.id.tv_noHeader_show)
    TextView mTvNoHeaderShow;
    /**
     * 性别默认是男
     */
    private boolean mIsMan = true;

    private final int HEAD_PHOTO = 100;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUpload;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_nickname)
    EditText tvNickname;
    @BindView(R.id.et_realname)
    EditText etRealname;
    @BindView(R.id.et_departmentname)
    EditText etDepartmentname;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.img_position)
    ImageView mImgPosition;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.img_calendar)
    ImageView mImgCalendar;
    @BindView(R.id.btn_man)
    Button mBtnMan;
    @BindView(R.id.btn_woman)
    Button mBtnWoman;
    private String path;
    private boolean isUploadHead = false;

    private LoginBean loginBean;
    private String mAreaCode;
    /**
     * 城市
     */
    private String city = "";
    private String contry = "";
    private AccountEntity mAccountEntity;

    public static void jumpToActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PersonInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_person_info);
        ButterKnife.bind(this);
        initPermission();
        initView();
        initData();
    }

    /**
     * 申请拍照权限
     */
    private void initPermission() {
        PermissionUtils.get(this).getCameraPermission(() -> {
        });
    }

    private void initView() {
        setTitle("我的资料");
        setRightTitle("保存");
        setLeftBack();
        llHeader.setOnClickListener(v -> {
            takePhoto(PersonInfoActivity.this, HEAD_PHOTO);
        });
        tvArea.setOnClickListener(this::choosePosition);
        mImgPosition.setOnClickListener(this::choosePosition);
        mBtnMan.setOnClickListener(v -> {
            mIsMan = true;
            setSexChoose();
        });
        mBtnWoman.setOnClickListener(v -> {
            mIsMan = false;
            setSexChoose();
        });
        mTvBirthday.setOnClickListener(this::setBirthday);
        mImgCalendar.setOnClickListener(this::setBirthday);
        mTvSave.setOnClickListener(new MultiClickListener(this, this::checkInfo, this::submitSuccess));
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submitSuccess));
    }

    /**
     * 设置性别按钮的选中状态
     */
    private void setSexChoose() {
        mBtnMan.setSelected(mIsMan);
        mBtnWoman.setSelected(!mIsMan);
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .tag(this)
                .execute(new EanfangCallback<LoginBean>(PersonInfoActivity.this, true, LoginBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        fillData(bean);
                        loginBean = bean;
                        mAccountEntity = bean.getAccount();
                    });
                }));
    }


    private void fillData(LoginBean infoBackBean) {
        /**
         * 操作过快会闪退
         * */
        if (infoBackBean == null || infoBackBean.getAccount() == null) {
            setHeaderShow(false);
            return;
        }
        AccountEntity accountEntity = infoBackBean.getAccount();
        if (!StringUtils.isEmpty(accountEntity.getAvatar())) {
            isUploadHead = true;
            path = accountEntity.getAvatar();
            ivUpload.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + accountEntity.getAvatar()));
            setHeaderShow(true);
        } else {
            setHeaderShow(false);
            isUploadHead = false;
        }
        // 昵称
        if (accountEntity.getNickName() != null && !DEFAULT_NAME.equals(accountEntity.getNickName())) {
            tvNickname.setText(accountEntity.getNickName());
        }
        //真实姓名
        if (accountEntity.getRealName() != null && !DEFAULT_REAL_NAME.equals(accountEntity.getRealName())) {
            etRealname.setText(accountEntity.getRealName());
//            etRealname.setEnabled(false);
        }
        //个人简介
        if (accountEntity.getPersonalNote() != null) {
            mEtPersonalNote.setText(accountEntity.getPersonalNote());
        } else {
            accountEntity.setPersonalNote("");
        }
        //生日
        if (accountEntity.getBirthday() != null) {
            mTvBirthday.setText(GetDateUtils.dateToDateString(accountEntity.getBirthday()));
        }
        //gender = 1表示男的
        mIsMan = accountEntity.getGender() == null
                || accountEntity.getGender() == 1;
        setSexChoose();
        String address = accountEntity.getAddress();
        if (address != null) {
            etAddress.setText(address);
        }
        if (!StringUtils.isEmpty(accountEntity.getAreaCode())) {
            mAreaCode = accountEntity.getAreaCode();
            tvArea.setText(Config.get().getAddressByCode(accountEntity.getAreaCode()));
        }

    }

    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        OSSCallBack callback = null;
        String imgKey = "account/" + UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEAD_PHOTO:
                ivUpload.setImageURI("file://" + result.getImage().getOriginalPath());
                callback = new OSSCallBack(this, true) {
                    @Override
                    public void onOssSuccess() {
                        runOnUiThread(() -> {
                            LoginBean entity = EanfangApplication.getApplication().getUser();
                            entity.getAccount().setAvatar(imgKey);
                            path = entity.getAccount().getAvatar();
                            setHeaderShow(true);
                        });

                    }
                };
                break;
            default:
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, result.getImage().getOriginalPath(), callback);


    }


    /**
     * 校验用户输入信息是否符合标准
     *
     * @return
     */
    private boolean checkInfo() {
        //如果为空，则代表头像
        if (!isUploadHead || StringUtils.isEmpty(path)) {
            showToast("请上传头像");
            return false;
        }

        String nickname = tvNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickname) || DEFAULT_NAME.equals(nickname)) {
            showToast("请输入昵称");
            return false;
        }

        if (nickname.length() > MAXIMUM_LENGTH) {
            showToast("昵称长度为8");
            return false;
        }

        if (StringUtils.isEmpty(city) && StringUtils.isEmpty(loginBean.getAccount().getAreaCode())) {
            showToast("请选择所在城市");
            return false;
        }

        if (!accountInfoChanged()) {
            showToast("用户信息未发生改变，请确认");
            return false;
        }
        return true;
    }

    /**
     * 用户信息是否发生改变
     *
     * @return true 改变  false：未改变
     */
    private boolean accountInfoChanged() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAvatar(path);
        String realName = etRealname.getText().toString().trim();
        accountEntity.setRealName(StringUtils.isEmpty(realName) ?
                tvNickname.getText().toString().trim() : realName);
        // 昵称
        accountEntity.setNickName(tvNickname.getText().toString().trim());
        accountEntity.setGender(mIsMan ? 1 : 0);
        String address = etAddress.getText().toString().trim();
        accountEntity.setAddress(address);
        accountEntity.setBirthday(GetDateUtils.getYeanDate(mTvBirthday.getText().toString()));
        accountEntity.setPersonalNote(mEtPersonalNote.getText().toString());
        if (!StringUtils.isEmpty(city) && !StringUtils.isEmpty(contry)) {
            accountEntity.setAreaCode(Config.get().getAreaCodeByName(city, contry));
        } else {
            accountEntity.setAreaCode(mAreaCode);
        }
        return mAccountEntity.isChanged(accountEntity);
    }

    private void submitSuccess() {
        EanfangHttp.post(UserApi.USER_INFO_UPDATE)
                .upJson(JsonUtils.obj2String(mAccountEntity))
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        showToast("修改成功");
                        LoginBean user = EanfangApplication.get().getUser();
                        if (!StringUtils.isEmpty(path)) {
                            user.getAccount().setAvatar(path);
                        }
                        if (!StringUtils.isEmpty(tvNickname.getText().toString().trim())) {
                            user.getAccount().setNickName(tvNickname.getText().toString().trim());
                        }
                        EanfangApplication.get().saveUser(user);

                        UserInfo userInfo;
                        if (!StringUtils.isEmpty(path)) {
                            //刷新个人融云的信息
                            userInfo = new UserInfo(String.valueOf(EanfangApplication.getApplication().getAccId()), tvNickname.getText().toString().trim(), Uri.parse(BuildConfig.OSS_SERVER + path));
                        } else {
                            userInfo = new UserInfo(String.valueOf(EanfangApplication.getApplication().getAccId()), tvNickname.getText().toString().trim(), Uri.parse(BuildConfig.OSS_SERVER + loginBean.getAccount().getAvatar()));
                        }
                        RongIM.getInstance().refreshUserInfoCache(userInfo);

                        startActivity(new Intent(PersonInfoActivity.this, OwnDataHintActivity.class));
                        finish();
                    });
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case SELECT_ADDRESS_CALL_BACK_CODE:
                SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
                Log.e("address", item.toString());
                city = item.getCity();
                contry = item.getAddress();
                tvArea.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
                etAddress.setText(item.getName());
                break;
            default:
                break;
        }
    }

    /**
     * 控制头像显示逻辑
     *
     * @param showHeader true ：显示头像 false：显示文本
     */
    private void setHeaderShow(boolean showHeader) {
        mTvNoHeaderShow.setVisibility(!showHeader ? View.VISIBLE : View.GONE);
        ivUpload.setVisibility(showHeader ? View.VISIBLE : View.GONE);
    }

    /**
     * 跳转设置生日
     *
     * @param v
     */
    private void setBirthday(View v) {
        Date date;
        if (!StringUtils.isEmpty(mTvBirthday.getText())) {
            date = GetDateUtils.getYeanDate(mTvBirthday.getText().toString());
        } else {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTvBirthday.setText(GetDateUtils.dateToDateString(new GregorianCalendar
                        (year, month, dayOfMonth).getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * 跳转导航定位
     *
     * @param v
     */
    private void choosePosition(View v) {
        Intent intent = new Intent(PersonInfoActivity.this, SelectAddressActivity.class);
        startActivityForResult(intent, SELECT_ADDRESS_CALL_BACK_CODE);
    }
}
