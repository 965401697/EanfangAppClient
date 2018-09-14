package net.eanfang.worker.ui.activity.worksapce.scancode;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.CustomeApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.equipment.EquipmentDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static com.eanfang.util.StringUtils.getValueByName;

/**
 * @author Guanluocang
 * @date on 2018/5/23  11:10
 * @decision 扫码界面
 */
public class ScanCodeActivity extends BaseActivity {
    private DecoratedBarcodeView barcodeScannerView;

    // 从哪来传输来的  参数
    private String mFromWhere = "";
    //添加朋友
    private String mAddFriend = "";


    // 扫描什么类型的二维码
    private String mScanType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        initView();
    }


    private void initView() {
        setLeftBack();
        setTitle("二维码扫描");
        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeScannerView.decodeContinuous(callback);

        mFromWhere = getIntent().getStringExtra("from");
        mAddFriend = getIntent().getStringExtra(EanfangConst.QR_ADD_FRIEND);
        mScanType = getIntent().getStringExtra("scanType");
        if (mScanType.equals("scan_device")) {// 扫码设备
            barcodeScannerView.setStatusText(getResources().getString(R.string.zxing_device));
        }

    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            barcodeScannerView.pause();
            String resultString = result.getText();
            if (StringUtils.isEmpty(resultString)) {
                return;
            }
            if (resultString.equals("")) {
                showToast("扫描失败!");
                // 扫码个人二维码
            } else if (resultString.contains("?_account=")) {
                //如果是true 则 解析扫到的二维码里的字符串 得到account的 ID
                String accountId = resultString.substring(resultString.indexOf("=") + 1);
                if (!TextUtils.isEmpty(mAddFriend)) {
                    EventBus.getDefault().post(accountId);
                    finishSelf();
                } else {
                    doGetAccount(accountId);
                }
            } else if (resultString.contains("/login/")) {
                /**
                 * 从哪里传输进来的
                 * */
                switch (mFromWhere) {
                    case "client_code":
                        // 登录web端
                        String[] strs = resultString.split("/login/");
                        String dataStr = strs[1];
                        String[] data = dataStr.split("/");
                        String uuid = data[0];
                        String requestFrom = data[1];
                        doQRLoginPermission(uuid, requestFrom);
                        break;
                    case "worker_code":
                        break;
                }
            } else if (isInteger(resultString)) {//如果是纯数字 就说明是加群
                //申请进群
                Intent intent = new Intent();
                if (!TextUtils.isEmpty(mFromWhere)) {
                    intent.setAction("client_group");
                } else {
                    intent.setAction("worker_group");
                }
                intent.putExtra("groupId", resultString);
                startActivity(intent);
                finishSelf();
            } else if (resultString.contains("qr?uid=")) {// 扫描设备
                String deviceId = getValueByName(result.getText(), "uid");
                String assigneeCompanyId = getValueByName(result.getText(), "assigneeCompanyId");
                String businessOneCode = getValueByName(result.getText(), "businessOneCode");
                Bundle bundle = new Bundle();
                bundle.putString("id", deviceId);
                bundle.putString("assigneeCompanyId", assigneeCompanyId);
                bundle.putString("businessOneCode", businessOneCode);
                JumpItent.jump(ScanCodeActivity.this, EquipmentDetailActivity.class, bundle);
                finishSelf();
            } else {
                showToast("二维码无效");
                finishSelf();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeScannerView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * 获取个人信息
     */
    private void doGetAccount(String accountId) {
        EanfangHttp.post(NewApiService.QR_GETACCOUNT)
                .params("accountId", accountId)
                .execute(new EanfangCallback<WorkerEntity>(ScanCodeActivity.this, true, WorkerEntity.class) {
                    @Override
                    public void onSuccess(WorkerEntity bean) {
                        super.onSuccess(bean);
                        doGetWorkDetail(bean);
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        Log.e("GG", "失败");
                        finishSelf();
                    }
                });
    }

    /**
     * 获取技师详情
     */
    private void doGetWorkDetail(WorkerEntity workerEntity) {
        EventBus.getDefault().post(workerEntity);
        finishSelf();
    }

    /**
     * 获取登录权限
     */
    private void doQRLoginPermission(String uuid, String requestFrom) {
        EanfangHttp.post(NewApiService.QR_CODE)
                .params("uuid", uuid)
                .params("requestFrom", requestFrom)
                .params("accountId", CustomeApplication.get().getAccId())
                .execute(new EanfangCallback<JSONObject>(ScanCodeActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        String isLogin = (String) bean.get("data");
                        if ("true".equals(isLogin)) {
                            // 进行登录
                            startActivity(new Intent(ScanCodeActivity.this, LoginConfirmActivity.class)
                                    .putExtra("which", requestFrom)
                                    .putExtra("uuid", uuid));
                        } else {
                            showToast("暂无权限");
                            finish();
                        }
                    }

                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        showToast("扫描失败");
                        finish();
                    }
                });
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
