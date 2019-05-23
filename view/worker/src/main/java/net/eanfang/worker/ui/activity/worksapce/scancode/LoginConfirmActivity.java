package net.eanfang.worker.ui.activity.worksapce.scancode;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.journeyapps.barcodescanner.CaptureActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;


public class LoginConfirmActivity extends BaseActivity {

    private SimpleDraweeView ivSimpleView;
    private TextView tvWhich;
    private TextView tvConfirmLogin;
    private TextView tvCancelLogin;

    // 哪个平台
    private String mWhich = "";
    // uuid
    private String mUuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("确认登录");
        setLeftBack();
        ivSimpleView = findViewById(R.id.iv_simpleView);
        tvWhich = findViewById(R.id.tv_which);
        tvConfirmLogin = findViewById(R.id.tv_confirmLogin);
        tvCancelLogin = findViewById(R.id.tv_cancelLogin);
        ivSimpleView = findViewById(R.id.iv_simpleView);
        mWhich = getIntent().getStringExtra("which");
        mUuid = getIntent().getStringExtra("uuid");
    }

    private void initData() {
        switch (mWhich) {
            //个人平台
            case "7":
                tvWhich.setText("个人平台");
                break;
            //安防公司企业平台
            case "5":
                tvWhich.setText("安防公司企业平台");
                break;
            //客户企业平台
            case "3":
                tvWhich.setText("客户企业平台");
                break;
            //城市运营平台
            case "2":
                tvWhich.setText("城市运营平台");
                break;
            //易安防总平台
            case "1":
                tvWhich.setText("易安防总平台");
                break;
        }
        // 获取当前头像
        ivSimpleView.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + WorkerApplication.get().getLoginBean().getAccount().getAvatar()));
    }

    private void initListener() {

        //允许登录
        tvConfirmLogin.setOnClickListener((v) -> {
            doLogin(mUuid);
        });
        //取消登录
        tvCancelLogin.setOnClickListener((v) -> {
            doCancelLogin(mUuid);
        });
    }

    /**
     * 取消登录
     */
    private void doCancelLogin(String uuid) {
        EanfangHttp.post(NewApiService.QR_LOGIN_CANCEL)
                .params("uuid", uuid)
                .execute(new EanfangCallback<JSONObject>(LoginConfirmActivity.this, true, JSONObject.class, (bean -> {
                    finishSelf();
                })));
    }

    /**
     * 进行登录
     */
    public void doLogin(String uuid) {
        EanfangHttp.post(NewApiService.QR_LOGIN)
                .params("uuid", uuid)
                .params("accountId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<JSONObject>(LoginConfirmActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("登录成功");
                    EanfangApplication.get().closeActivity(CaptureActivity.class.getName());
                    finishSelf();
                }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            EanfangHttp.post(NewApiService.QR_LOGIN_CANCEL)
                    .params("uuid", mUuid)
                    .execute(new EanfangCallback<JSONObject>(LoginConfirmActivity.this, true, JSONObject.class, (bean -> {
                        finishSelf();
                    })));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

