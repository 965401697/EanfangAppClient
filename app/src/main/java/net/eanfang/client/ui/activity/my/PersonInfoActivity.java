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

import com.alibaba.fastjson.JSONObject;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.IDCardUtil;
import com.eanfang.util.PermissionCheckUtil;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jph.takephoto.model.TResult;
import com.yaf.model.LoginBean;
import com.yaf.sys.entity.AccountEntity;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.UserApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.oss.OSSCallBack;
import net.eanfang.client.ui.activity.SelectAddressActivity;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.base.BaseActivityWithTakePhoto;
import net.eanfang.client.ui.model.SelectAddressItem;
import net.eanfang.client.util.OSSUtils;
import net.eanfang.client.util.StringUtils;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 我的-个人资料
 * Created by Administrator on 2017/3/15.
 */

public class PersonInfoActivity extends BaseActivityWithTakePhoto {

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
    private String path1;
    private boolean isUploadHead = false;
    private final int HEAD_PHOTO = 100;

    public static void jumpToActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PersonInfoActivity.class);
        ((BaseActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_person_info);
        ButterKnife.bind(this);
        initView();
        initData();


    }


    private void initView() {
        setTitle("我的资料");
        setRightTitle("完成");
        setLeftBack();
        rbMan.isChecked();
        ivUpload.setOnClickListener(v -> {
            if (PermissionCheckUtil.cameraIsCanUse() == true) {
                takePhoto(HEAD_PHOTO);
            } else {
                showToast("相机权限异常，请检查权限");
            }
        });
        llArea.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, 23221);
        });

        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
    }


    private void initData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .tag(this)
                .execute(new EanfangCallback<LoginBean>(PersonInfoActivity.this, false, LoginBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        fillData(bean);
                    });
                }));
    }


    private void fillData(LoginBean infoBackBean) {
        if (!StringUtils.isEmpty(infoBackBean.getAccount().getAvatar())) {
            ivUpload.setImageURI(Uri.parse(infoBackBean.getAccount().getAvatar()));
        }
        if (infoBackBean.getAccount().getNickName() != null) {
            tvNickname.setText(infoBackBean.getAccount().getNickName());
        }

        if (infoBackBean.getAccount().getRealName() != null) {
            etRealname.setText(infoBackBean.getAccount().getRealName());
            etRealname.setEnabled(false);
        }
        rbMan.setClickable(false);
        rbWoman.setClickable(false);
        if (infoBackBean.getAccount().getGender() == 1) {
            rbMan.setChecked(true);
        } else {
            rbWoman.setChecked(true);
        }
        if (infoBackBean.getAccount().getIdCard() != null) {
            etIdcard.setText(infoBackBean.getAccount().getIdCard());
            etIdcard.setEnabled(false);
        }

        String address = infoBackBean.getAccount().getAdress();
        if (address != null) {
            String area = address.substring(0, 11);
            String streetAddress = address.substring(12, address.length());
            tvArea.setText(area);
            etAddress.setText(streetAddress);
        }



    }


    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        OSSCallBack callback = null;
        String imgKey = UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEAD_PHOTO:
                ivUpload.setImageURI("file://" + result.getImage().getOriginalPath());
                callback = new OSSCallBack(this, true) {
                    @Override
                    public void onOssSuccess() {
                        runOnUiThread(() -> {
                            LoginBean entity = EanfangApplication.getApplication().getUser();
                            entity.getAccount().setAvatar(BuildConfig.OSS_SERVER + imgKey);
                            path1 = entity.getAccount().getAvatar();
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
//        String nickname = tvNickname.getText().toString().trim();
//        if (TextUtils.isEmpty(nickname)) {
//            showToast("请输入昵称");
//            return false;
//        }
//        if (nickname.length() > 8) {
//            showToast("昵称长度为8");
//            return false;
//        }
        String realname = etRealname.getText().toString().trim();
        if (TextUtils.isEmpty(realname)) {
            showToast("请输入真实姓名");
            return false;
        }
        if (realname.length() > 6) {
            showToast("真实姓名长度为6");
            return false;
        }
        String idcard = etIdcard.getText().toString().trim();
        if (TextUtils.isEmpty(idcard)) {
            showToast("请输入证件号码");
            return false;
        }
        try {
            if (IDCardUtil.IDCardValidate(idcard) == false) {
                showToast("证件格式有误，请重新输入");
                return false;
            }
        } catch (ParseException e) {
        }
//        String address = etAddress.getText().toString().trim();
//        if (TextUtils.isEmpty(address)) {
//            showToast("请输入详细地址");
//            return false;
//        }
        //如果为空，则代表头像
        if (isUploadHead && StringUtils.isEmpty(path1)) {
            showToast("正在上传头像，请稍等");
            return false;
        }
        return true;

    }

    private void submit() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAvatar(path1);
        accountEntity.setRealName(etRealname.getText().toString().trim());
        accountEntity.setNickName(tvNickname.getText().toString().trim());
        if (rbMan.isChecked()) {
            accountEntity.setGender(1);
        } else {
            accountEntity.setGender(0);
        }
        accountEntity.setIdCard(etIdcard.getText().toString().trim());
        String address = tvArea.getText().toString().trim() + etAddress.getText().toString().trim();
        accountEntity.setAdress(address);
        Gson gson = new Gson();
        String json = gson.toJson(accountEntity);
        Log.e("json", json);
        submitSuccess(json);
    }

    private void submitSuccess(String json) {
        EanfangHttp.post(UserApi.USER_INFO_UPDATE)
                .upJson(json.toString())
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        showToast("成功");
                        LoginBean user = EanfangApplication.get().getUser();
                        user.getAccount().setAvatar(path1);
                        user.getAccount().setRealName(etRealname.getText().toString().trim());
                        EanfangApplication.get().saveUser(user);
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
            case 23221:
                SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
                Log.e("address", item.toString());
                tvArea.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
                etAddress.setText(item.getName());
                break;
            default:
                break;
        }
    }

}
