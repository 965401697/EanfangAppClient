package net.eanfang.client.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.IDCardUtil;
import com.eanfang.util.PermissionCheckUtil;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jph.takephoto.model.TResult;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.UserApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.oss.OSSCallBack;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.base.BaseActivityWithTakePhoto;
import net.eanfang.client.ui.model.InfoBackBean;
import net.eanfang.client.ui.model.SelectAddressItem;
import net.eanfang.client.ui.model.User;
import net.eanfang.client.util.OSSUtils;
import net.eanfang.client.util.StringUtils;

import java.text.ParseException;


/**
 * 我的-个人资料
 * Created by Administrator on 2017/3/15.
 */

public class PersonInfoActivity extends BaseActivityWithTakePhoto {

    private SimpleDraweeView iv_upload;
    private EditText tv_nickname;
    private EditText et_realname;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private RadioGroup rg_sex;
    private TextView tv_date;
    private EditText et_idcard;
    private TextView tv_area;
    private EditText et_address;
    private LinearLayout ll_area;
    private LinearLayout ll_birthday;


    private String path1;
    private boolean isUploadHead = false;
    private String city, zone;
    private String year, month, date;

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
        initView();
        initListener();
        initData();

        setTitle("我的资料");
        setLeftBack();
        setRightTitle("保存");
    }

    private void initListener() {
        ll_area.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, 23221);
        });
        iv_upload.setOnClickListener(v -> {
            if (PermissionCheckUtil.cameraIsCanUse() == true) {
                takePhoto(HEAD_PHOTO);
            } else {
                showToast("相机权限异常，请检查权限");
            }
        });
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
    }

    private void initView() {
        iv_upload = (SimpleDraweeView) findViewById(R.id.iv_upload);
        tv_nickname = (EditText) findViewById(R.id.tv_nickname);
        et_realname = (EditText) findViewById(R.id.et_realname);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        tv_date = (TextView) findViewById(R.id.tv_date);
        et_idcard = (EditText) findViewById(R.id.et_idcard);
        tv_area = (TextView) findViewById(R.id.tv_area);
        et_address = (EditText) findViewById(R.id.et_address);

        ll_birthday = (LinearLayout) findViewById(R.id.ll_birthday);
        ll_area = (LinearLayout) findViewById(R.id.ll_area);
        rb_man.isChecked();
    }


    private void initData() {
        EanfangHttp.get(UserApi.INFO_BACK)
                .tag(this)
                .execute(new EanfangCallback<InfoBackBean>(PersonInfoActivity.this, false) {
                    @Override
                    public void onSuccess(InfoBackBean bean) {
                        fillData(bean);
                    }
                });
    }

    private void fillData(InfoBackBean infoBackBean) {
        ll_birthday.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(infoBackBean.getHeadpic()))
            iv_upload.setImageURI(Uri.parse(infoBackBean.getHeadpic()));
        tv_nickname.setText(infoBackBean.getNickname());
        et_realname.setText(infoBackBean.getRealname());
        if (infoBackBean.getSex().equals("男"))
            rb_man.setChecked(true);
        else
            rb_woman.setChecked(true);

        tv_date.setText(GetDateUtils.dateToDateString(GetDateUtils.getDate(infoBackBean.getBirthday())));
        et_idcard.setText(infoBackBean.getIdentity());
        tv_area.setText(infoBackBean.getCity() + infoBackBean.getZone());
        et_address.setText(infoBackBean.getStreet());
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
                city = item.getCity();
                zone = item.getAddress();
                tv_area.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
                et_address.setText(item.getName());
                break;
        }
    }


    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        OSSCallBack callback = null;
        String imgKey = UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case HEAD_PHOTO:
                iv_upload.setImageURI("file://" + result.getImage().getOriginalPath());
                callback = new OSSCallBack(this, true) {
                    @Override
                    public void onOssSuccess() {
                        runOnUiThread(() -> {
                            User user = EanfangApplication.get().getUser();
                            user.setHeadpic(BuildConfig.OSS_SERVER + imgKey);
                            path1 = user.getHeadpic();
                        });

                    }
                };
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, result.getImage().getOriginalPath(), callback);


    }


    private boolean checkInfo() {
        String nickname = tv_nickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            showToast("请输入昵称");
            return false;
        }
        if (nickname.length() > 8) {
            showToast("昵称长度为8");
            return false;
        }
        String realname = et_realname.getText().toString().trim();
        if (TextUtils.isEmpty(realname)) {
            showToast("请输入真实姓名");
            return false;
        }
        if (realname.length() > 6) {
            showToast("真实姓名长度为6");
            return false;
        }
        String idcard = et_idcard.getText().toString().trim();
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
        String address = et_address.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            showToast("请输入详细地址");
            return false;
        }
        //如果为空，则代表头像
        if (isUploadHead && StringUtils.isEmpty(path1)) {
            showToast("正在上传头像，请稍等");
            return false;
        }
        return true;

    }

    private void submit() {
        InfoBackBean bean = new InfoBackBean();
        String idCare = et_idcard.getText().toString().trim();
        year = idCare.substring(6, 10);
        month = idCare.substring(10, 12);
        date = idCare.substring(12, 14);
        bean.setBirthday(year + "-" + month + "-" + date);
        bean.setCity(city);
        bean.setHeadpic(path1);
        bean.setIdentity(et_idcard.getText().toString().trim());
        bean.setNickname(tv_nickname.getText().toString().trim());
        bean.setRealname(et_realname.getText().toString().trim());
        if (rb_man.isChecked()) {
            bean.setSex("男");
        } else {
            bean.setSex("女");
        }

        bean.setStreet(et_address.getText().toString().trim());
        bean.setZone(zone);
        Gson gson = new Gson();
        String json = gson.toJson(bean);
        Log.e("json", json);
        EanfangHttp.post(UserApi.INFO_HEAD_UPDATE)
                .tag(this)
                .params("json", json.toString())
                .execute(new EanfangCallback(PersonInfoActivity.this, false) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        showToast("保存成功");
                        User user = EanfangApplication.get().getUser();
                        user.setInfoGood("1");
                        user.setName(et_realname.getText().toString().trim());
                        user.setHeadpic(path1);
                        EanfangApplication.get().saveUser(user);
                        finish();
                    }
                });
    }


}
