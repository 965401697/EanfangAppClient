package net.eanfang.worker.ui.activity.my.certification;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.PhotoUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.eanfang.biz.model.entity.TechWorkerVerifyEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.techniciancertification.SubmitSuccessfullyJsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

public class OtherDataActivity extends BaseActivity {

    @BindView(R.id.et_urgent_name)
    EditText etUrgentName;
    @BindView(R.id.et_urgent_phone)
    EditText etUrgentPhone;
    @BindView(R.id.ts_lr_tv)
    TextView tsLrTv;
    @BindView(R.id.yx_et)
    EditText yxEt;
    /**
     * 犯罪照片
     * （3张）
     */
    @BindView(R.id.recycleview_crim)
    PictureRecycleView recycleviewCrim;
    /**
     * 保险照片
     * （3张）
     */
    @BindView(R.id.recycleview_accident)
    PictureRecycleView recycleviewAccident;
    private TechWorkerVerifyEntity mTechWorkerVerifyEntity;
    private List<LocalMedia> picList_accident = new ArrayList<>();
    private List<LocalMedia> picList_crim = new ArrayList<>();
    private HashMap<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_other_data);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("保障信息");
        EanfangHttp.get(UserApi.GET_WORKER_INFO).execute(new EanfangCallback<TechWorkerVerifyEntity>(this, true, TechWorkerVerifyEntity.class, (bean) -> {
            mTechWorkerVerifyEntity = bean;
            if (!TextUtils.isEmpty(mTechWorkerVerifyEntity.getContactName())) {
                fillData();
            }
        }));
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void fillData() {
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.geteMail()) | mTechWorkerVerifyEntity.geteMail() == null) {

        } else {
            yxEt.setText(mTechWorkerVerifyEntity.geteMail());
        }
        if (StrUtil.isEmpty(mTechWorkerVerifyEntity.getContactName()) | mTechWorkerVerifyEntity.getContactName() == null) {

        } else {
            etUrgentName.setText(mTechWorkerVerifyEntity.getContactName());
        }
        etUrgentPhone.setText(mTechWorkerVerifyEntity.getContactPhone());

        if (!StrUtil.isEmpty(mTechWorkerVerifyEntity.getCrimePic())) {
            picList_crim=recycleviewCrim.setData(mTechWorkerVerifyEntity.getCrimePic());
            recycleviewCrim.showImagev(picList_crim,listener_crim);
            recycleviewCrim.isShow(true,picList_crim);
        }else{
            recycleviewCrim.addImagev(listener_crim);
        }

        if (!StrUtil.isEmpty(mTechWorkerVerifyEntity.getAccidentPics())) {
            picList_accident=recycleviewAccident.setData(mTechWorkerVerifyEntity.getAccidentPics());
            recycleviewAccident.showImagev(picList_accident,listener_accident);
            recycleviewAccident.isShow(true,picList_accident);
        }else{
            recycleviewAccident.addImagev(listener_accident);
        }
    }
    PictureRecycleView.ImageListener listener_crim = list -> picList_crim = list;
    PictureRecycleView.ImageListener listener_accident = list -> picList_accident = list;

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        doSave();
    }

    private void doSave() {
        String mUrgentName = etUrgentName.getText().toString().trim();
        String mUrgentPhone = etUrgentPhone.getText().toString().trim();
        String yxEtS = yxEt.getText().toString().trim();

        if (!isEmail(yxEtS)) {
            showToast("请输入正确的电子邮箱");
            return;
        }
        if (StrUtil.isEmpty(mUrgentName)) {
            showToast("请输入紧急联系人");
            return;
        }
        if (!isMobileNO(mUrgentPhone)) {
            showToast("请输入正确的电话号码");
            return;
        }


        mTechWorkerVerifyEntity.seteMail(yxEtS);
        mTechWorkerVerifyEntity.setContactName(mUrgentName);
        mTechWorkerVerifyEntity.setContactPhone(mUrgentPhone);


        String crimePic = PhotoUtils.getPhotoUrl("account/verify/", picList_crim, uploadMap, false);
        if (crimePic.equals("")) {
            showToast("请上传无犯罪证明");
            return;

        } else {
            mTechWorkerVerifyEntity.setCrimePic(crimePic);
        }

        String accidentPic = PhotoUtils.getPhotoUrl("account/verify/", picList_accident, uploadMap, false);
        if (accidentPic.equals("")) {
            showToast("请上传保险状态");
            return;
        } else {
            mTechWorkerVerifyEntity.setAccidentPics(accidentPic);
        }

        Log.d("5648", "doSave: " + accidentPic + " " + crimePic);
        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                runOnUiThread(() -> EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V4).upJson(JSONObject.toJSONString(mTechWorkerVerifyEntity)).execute(new EanfangCallback<JSONObject>(OtherDataActivity.this, true, JSONObject.class, (bean) -> {
                    Intent intent = new Intent(OtherDataActivity.this, SubmitSuccessfullyJsActivity.class);
                    intent.putExtra("order", 3);
                    startActivity(intent);
                    closeActivity();
                })));
            });
            return;
        } else {
            EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_V4).upJson(JSONObject.toJSONString(mTechWorkerVerifyEntity)).execute(new EanfangCallback<JSONObject>(OtherDataActivity.this, true, JSONObject.class, (bean) -> {
                Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
                intent.putExtra("order", 3);
                startActivity(intent);
                closeActivity();
            }));

        }

    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_ACCIDENT:
                snplMomentAccident.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_CRIM:
                snplMomentCrim.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            default:
        }
    }*/

    private void closeActivity() {
        WorkerApplication.get().closeActivity(CertificationActivity.class);
        WorkerApplication.get().closeActivity(IdentityCardCertification.class);
        finish();
    }

    @OnClick(R.id.ts_lr_tv)
    public void onClick() {
        View layout = LayoutInflater.from(this).inflate(R.layout.activity_ts_wz, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(true);
        builder.create().show();
    }

    /**
     * 判断手机格式是否正确
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
