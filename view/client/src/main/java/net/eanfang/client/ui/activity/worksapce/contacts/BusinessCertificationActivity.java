package net.eanfang.client.ui.activity.worksapce.contacts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.eanfang.base.widget.customview.LtReView;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.ZdBusinessCertification;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.RecognizeService;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.ContactsFragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BusinessCertificationActivity extends BaseActivity {

    @BindView(R.id.iv_uploadlogo)
    ImageView ivUploadlogo;
    @BindView(R.id.dw_mc_lrv)
    LtReView dwMcLrv;
    @BindView(R.id.nsh_lrv)
    LtReView nshLrv;
    @BindView(R.id.zc_dz_lrv)
    LtReView zcDzLrv;
    @BindView(R.id.fr_db_lrv)
    LtReView frDbLrv;
    @BindView(R.id.zc_zj_lrv)
    LtReView zcZjLrv;
    @BindView(R.id.cl_rq_lrv)
    TextView clRqLrv;
    @BindView(R.id.jz_rq_lrv)
    LtReView jzRqLrv;
    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private static final int REQUEST_CODE_BUSINESS_LICENSE = 123;
    private Long mOrgId;
    private int status = 0;
    private int order = 1;
    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();
    private Date date;
    private int bizCertify = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_certification);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("工商认证");
        setRightTitle("提交");
        alertDialog = new AlertDialog.Builder(this);
        setRightTitleOnClickListener(view -> {
            getData();
        });
    }

    public void getData() {
        if (dwMcLrv.getText().toString().trim().equals("无") & frDbLrv.getText().toString().trim().equals("无")) {
            showToast("请上传正确的营业执照");
        } else if ("".equals(infoBean.getLicensePic()) || infoBean.getLicensePic() == null) {
            showToast("请点击加号,上传营业执照");
            return;
        } else if (StringUtils.isEmpty(dwMcLrv.getText().toString().trim())) {
            showToast("请输入单位名称");
            return;
        } else if (StringUtils.isEmpty(nshLrv.getText().toString().trim())) {
            showToast("纳税人识别号");
            return;
        } else if (StringUtils.isEmpty(zcDzLrv.getText().toString().trim())) {
            showToast("请输入注册地址");
            return;
        } else if (StringUtils.isEmpty(frDbLrv.getText().toString().trim())) {
            showToast("请输入法人代表");
            return;
        } else if (StringUtils.isEmpty(zcZjLrv.getText().toString().trim())) {
            showToast("请输入注册资金");
            return;
        } else if (StringUtils.isEmpty(clRqLrv.getText().toString().trim())) {
            showToast("请选择成立日期");
            return;
        } else if (StringUtils.isEmpty(jzRqLrv.getText().toString().trim())) {
            showToast("营业截至日期");
            return;
        } else {
            infoBean.setName(dwMcLrv.getText().toString().trim());
            infoBean.setLicenseCode(nshLrv.getText().toString().trim());
            infoBean.setRegisterAddress(zcDzLrv.getText().toString().trim());
            infoBean.setLegalName(frDbLrv.getText().toString().trim());
            infoBean.setRegisterAssets(zcZjLrv.getText().toString().trim());
            infoBean.setEstablishDate(clRqLrv.getText().toString().trim());
            infoBean.setExpirationDate(jzRqLrv.getText().toString().trim());
            infoBean.setOrgId(mOrgId);
            infoBean.setUnitType(3);
            String json = JSONObject.toJSONString(infoBean);
            commit(json);
        }
    }

    @SuppressLint("LongLogTag")
    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_INSERT).upJson(json).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
            Log.d("REQUEST_CODE_BUSINESS_LICENSE6:", UserApi.GET_ORGUNIT_SHOP_INSERT + "\n" + json + "\n" + bean.toJSONString());
            showToast("保存成功");
            ContactsFragment.isRefresh = true;
            initInt();

        }));
    }

    private void setRq() {
        View view = getLayoutInflater().inflate(R.layout.activity_dialog_date, null);
        CalendarView datePicker = (CalendarView) view.findViewById(R.id.calendarView);
        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            date = new GregorianCalendar(year, month, dayOfMonth).getTime();
            clRqLrv.setText(GetDateUtils.dateToDateString(date));
        });
        new AlertDialog.Builder(this).setView(view).setCancelable(false).setPositiveButton("确定", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        status = getIntent().getIntExtra("status", 0);
        bizCertify = getIntent().getIntExtra("bizCertify", 0);
        clRqLrv.setOnClickListener(view -> setRq());
        initAccessToken();
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + mOrgId).execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
            infoBean = beans;
            switch (bizCertify) {
                case 0:
                    setRightTitle("只读");
                    setData(beans);
                    setView();
                    break;
                default:
                    if ((infoBean.getStatus() == 1) | (infoBean.getStatus() == 2)) {
                        setRightTitle("只读");
                        setData(beans);
                        setView();
                    } else {
                        setData(beans);
                    }
            }
        }));
    }

    private void setView() {
        ivUploadlogo.setEnabled(false);
        dwMcLrv.setEnabled(false);
        nshLrv.setEnabled(false);
        zcDzLrv.setEnabled(false);
        frDbLrv.setEnabled(false);
        zcZjLrv.setEnabled(false);
        clRqLrv.setEnabled(false);
        clRqLrv.setClickable(false);
        jzRqLrv.setEnabled(false);
        setRightTitleOnClickListener(null);
    }

    private void setData(AuthCompanyBaseInfoBean beans) {
        if (infoBean.getLicensePic() != null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(BuildConfig.OSS_SERVER + infoBean.getLicensePic()).build();
            okHttpClient.newCall(request).enqueue(
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) {
                            InputStream inputStream = response.body().byteStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            runOnUiThread(() -> ivUploadlogo.setImageBitmap(bitmap));
                        }
                    }
            );
        }
        dwMcLrv.setText(beans.getName());
        nshLrv.setText(beans.getLicenseCode());
        zcDzLrv.setText(beans.getRegisterAddress());
        frDbLrv.setText(beans.getLegalName());
        zcZjLrv.setText(beans.getRegisterAssets());
        clRqLrv.setText(beans.getEstablishDate());
        jzRqLrv.setText(beans.getExpirationDate());
    }

    private void setData(ZdBusinessCertification beans) {
        dwMcLrv.setText(beans.getWords_result().get单位名称().getWords());
        nshLrv.setText(beans.getWords_result().get社会信用代码().getWords());
        zcDzLrv.setText(beans.getWords_result().get地址().getWords());
        frDbLrv.setText(beans.getWords_result().get法人().getWords());
        zcZjLrv.setText(beans.getWords_result().get注册资本().getWords());
        clRqLrv.setText(beans.getWords_result().get成立日期().getWords());
        jzRqLrv.setText(beans.getWords_result().get有效期().getWords());
    }
    private void initInt() {
        EanfangHttp.get(UserApi.JS_RZ_TJ_INFO+mOrgId).execute(new EanfangCallback<String>(this, true, String.class, (bean) -> {
            Log.d("56483666", "bean: " + bean);
            finish();
        }));
    }
    @OnClick(R.id.iv_uploadlogo)
    public void onClick() {
        if (!checkTokenStatus()) {
            return;
        }
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, new File(getApplication().getFilesDir(), "pic.jpg").getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_CODE_BUSINESS_LICENSE);

    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(() -> alertDialog.setTitle(title).setMessage(message).setPositiveButton("确定", null).show());
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，营业执照识别
        if (requestCode == REQUEST_CODE_BUSINESS_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBusinessLicense(this, new File(getApplication().getFilesDir(), "pic.jpg").getAbsolutePath(),
                    result -> {
                        String fileString = new File(getApplication().getFilesDir(), "pic.jpg").getAbsolutePath();
                        Bitmap bitmap = BitmapFactory.decodeFile(fileString);
                        String imgKey = UuidUtil.getUUID() + ".jpg";
                        OSSUtils.initOSS(this).asyncPutImage(imgKey, fileString, new OSSCallBack(this, true) {
                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }
                        });
                        ivUploadlogo.setImageBitmap(bitmap);
                        infoBean.setLicensePic(imgKey);
                        ZdBusinessCertification myclass = JSONObject.parseObject(result, ZdBusinessCertification.class);
                        setData(myclass);
                    });
        }
    }
}
