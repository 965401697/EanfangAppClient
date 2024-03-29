package net.eanfang.client.ui.activity.worksapce.contacts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.widget.customview.LtReView;
import com.eanfang.biz.model.bean.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.bean.ZdBusinessCertification;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.RecognizeService;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.ContactsFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

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
    TextView jzRqLrv;
    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private static final int REQUEST_CODE_BUSINESS_LICENSE = 123;
    private Long mOrgId;
    private int status = 0;
    private int order = 1;
    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();
    private Date date;
    /**
     * 纳税识别号长度
     */
    private ArrayList<Integer> nshLength = new ArrayList<>();

    {
        nshLength.add(15);
        nshLength.add(18);
        nshLength.add(20);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_business_certification);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
        String nshString = nshLrv.getText().toString().trim();
        if (dwMcLrv.getText().toString().trim().equals("无") & frDbLrv.getText().toString().trim().equals("无")) {
            showToast("请上传正确的营业执照");
        } else if ("".equals(infoBean.getLicensePic()) || infoBean.getLicensePic() == null) {
            showToast("请点击加号,上传营业执照");
        } else if (StrUtil.isEmpty(dwMcLrv.getText().toString().trim())) {
            showToast("请输入单位名称");
        } else if (StrUtil.isEmpty(nshString)) {
            showToast("纳税人识别号");
        } else if (StrUtil.isEmpty(zcDzLrv.getText().toString().trim())) {
            showToast("请输入注册地址");
        } else if (StrUtil.isEmpty(frDbLrv.getText().toString().trim())) {
            showToast("请输入法人代表");
        } else if (StrUtil.isEmpty(zcZjLrv.getText().toString().trim())) {
            showToast("请输入注册资金");
        } else if (StrUtil.isEmpty(clRqLrv.getText().toString().trim())) {
            showToast("请选择成立日期");
        } else if (StrUtil.isEmpty(jzRqLrv.getText().toString().trim())) {
            showToast("营业截至日期");
        } else if (!nshLength.contains(nshString.length())) {
            showToast("纳税人识别号有误");
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

    private void setRq(TextView textView) {

        Date date;
        if (!StrUtil.isEmpty(textView.getText()) && !"无".equals(textView.getText())) {
            date = DateUtil.parse(textView.getText().toString());
        } else {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) ->
                textView.setText(DateUtil.parse(year1 + "-" + month1 + "-" + dayOfMonth, "yyyy-M-dd").toDateStr()), year, month, day);
        datePickerDialog.show();

//        View view = getLayoutInflater().inflate(R.layout.activity_dialog_date, null);
//        CalendarView datePicker = view.findViewById(R.id.calendarView);
//        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
//            date = DateUtil.parse(year + "-" + month + "-" + dayOfMonth);
//            clRqLrv.setText(DateUtil.date(date).toDateStr());
//        });
//        new AlertDialog.Builder(this).setView(view).setCancelable(false).setPositiveButton("确定", (dialogInterface, i) -> {
//            dialogInterface.dismiss();
//        }).show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        status = getIntent().getIntExtra("status", 0);
        clRqLrv.setOnClickListener(view -> setRq(clRqLrv));
        jzRqLrv.setOnClickListener(view -> setRq(jzRqLrv));
        initAccessToken();
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + mOrgId).execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
            infoBean = beans;
            if ((infoBean.getStatus() == 1) || (infoBean.getStatus() == 2)) {
                setRightTitle("只读");
                setData(beans);
                setView();
            } else {
                setData(beans);
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
            String path = PhotoUtils.downloadImg(BaseApplication.get().getAccount().getQrCode());
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ivUploadlogo.setImageBitmap(bitmap);
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
        EanfangHttp.get(UserApi.JS_RZ_TJ_INFO + mOrgId).execute(new EanfangCallback<String>(this, true, String.class, (bean) -> {
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
                        String imgKey = StrUtil.uuid() + ".jpg";
                        SDKManager.ossKit(this).asyncPutImage(imgKey, fileString, (isSuccess) -> {
                        });
                        ivUploadlogo.setImageBitmap(bitmap);
                        infoBean.setLicensePic(imgKey);
                        ZdBusinessCertification myclass = JSONObject.parseObject(result, ZdBusinessCertification.class);
                        setData(myclass);
                    });
        }
    }
}
