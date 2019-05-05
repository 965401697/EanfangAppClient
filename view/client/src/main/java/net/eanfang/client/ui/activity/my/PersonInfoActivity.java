package net.eanfang.client.ui.activity.my;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.sys.AccountEntity;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.IDCardUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TResult;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.OwnDataHintActivity;

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
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
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
    private String path;
    private boolean isUploadHead = false;

    private LoginBean loginBean;
    /**
     * 城市
     */
    private String city = "";
    private String contry = "";

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
        rbMan.isChecked();
        llHeader.setOnClickListener(v -> {
            takePhoto(PersonInfoActivity.this, HEAD_PHOTO);
        });
        tvArea.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, SELECT_ADDRESS_CALL_BACK_CODE);
        });

        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
    }


    private void initData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .tag(this)
                .execute(new EanfangCallback<LoginBean>(PersonInfoActivity.this, true, LoginBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        fillData(bean);
                        loginBean = bean;
                    });
                }));
    }


    private void fillData(LoginBean infoBackBean) {
        /**
         * 操作过快会闪退
         * */
        if (infoBackBean == null) {
            return;
        }
        // 头像
        if (!StringUtils.isEmpty(infoBackBean.getAccount().getAvatar())) {
            ivUpload.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + infoBackBean.getAccount().getAvatar()));
        }
        // 昵称
        if (infoBackBean.getAccount().getNickName() != null && !"待提供".equals(infoBackBean.getAccount().getNickName())) {
            tvNickname.setText(infoBackBean.getAccount().getNickName());
        }
        //真实姓名
        if (infoBackBean.getAccount().getRealName() != null && !"待提供".equals(infoBackBean.getAccount().getRealName())) {
            etRealname.setText(infoBackBean.getAccount().getRealName());
//            etRealname.setEnabled(false);
        }
        // 头像
        if (infoBackBean.getAccount().getGender() == null) {
            rbMan.setClickable(true);
            rbWoman.setClickable(true);
            rbMan.setChecked(true);
        } else if (infoBackBean.getAccount().getGender() == 1) {// 男
//            rbMan.setClickable(false);
//            rbWoman.setClickable(false);
            rbMan.setChecked(true);
        } else {
//            rbMan.setClickable(false);
//            rbWoman.setClickable(false);
            rbWoman.setChecked(true);// 女
        }
        // 证件号码
        if (!StringUtils.isEmpty(infoBackBean.getAccount().getIdCard())) {
            etIdcard.setText(infoBackBean.getAccount().getIdCard());
//            etIdcard.setEnabled(false);
        }

        String address = infoBackBean.getAccount().getAddress();

        if (address != null) {
            etAddress.setText(address);
        }
        if (!StringUtils.isEmpty(infoBackBean.getAccount().getAreaCode())) {
            tvArea.setText(Config.get().getAddressByCode(infoBackBean.getAccount().getAreaCode()));
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
                        });

                    }
                };
                break;
            default:
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, result.getImage().getOriginalPath(), callback);


    }


    private boolean checkInfo() {
        String nickname = tvNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            showToast("请输入昵称");
            return false;
        }
        if (nickname.length() > 8) {
            showToast("昵称长度为8");
            return false;
        }
        String realname = etRealname.getText().toString().trim();
        if (TextUtils.isEmpty(realname)) {
            showToast("请输入真实姓名");
            return false;
        }
//        if (realname.length() > 6) {
//            showToast("真实姓名长度为6");
//            return false;
//        }

        if (StringUtils.isEmpty(city) && StringUtils.isEmpty(loginBean.getAccount().getAreaCode())) {
            showToast("请选择所在城市");
            return false;
        }
        String address = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            showToast("请输入详细地址");
            return false;
        }
        //如果为空，则代表头像
        if (isUploadHead && StringUtils.isEmpty(path)) {
            showToast("正在上传头像，请稍等");
            return false;
        }
        if (!rbMan.isChecked() && !rbWoman.isChecked()) {
            showToast("请选择性别");
            return false;
        }

        String idcard = etIdcard.getText().toString().trim();
        if (!StringUtils.isEmpty(idcard)) {
            if (IDCardUtil.IDCardValidate(idcard) == false) {
                showToast("证件格式有误，请重新输入");
                etIdcard.setText("");
                etIdcard.setEnabled(true);
                return false;
            }
        }

        return true;

    }

    private void submit() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAvatar(path);
        accountEntity.setRealName(etRealname.getText().toString().trim());
        accountEntity.setNickName(tvNickname.getText().toString().trim());
        if (rbMan.isChecked()) {
            accountEntity.setGender(1);
        } else {
            accountEntity.setGender(0);
        }
        accountEntity.setIdCard(etIdcard.getText().toString().trim());
        String address = etAddress.getText().toString().trim();
        accountEntity.setAddress(address);
        if (!StringUtils.isEmpty(city) && !StringUtils.isEmpty(contry)) {
            accountEntity.setAreaCode(Config.get().getAreaCodeByName(city, contry));
        }

        submitSuccess(JSON.toJSONString(accountEntity));
    }

    private void submitSuccess(String json) {
        EanfangHttp.post(UserApi.USER_INFO_UPDATE)
                .upJson(json)
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
                        EanfangApplication.get().set(LoginBean.class.getName(), user);

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

}
