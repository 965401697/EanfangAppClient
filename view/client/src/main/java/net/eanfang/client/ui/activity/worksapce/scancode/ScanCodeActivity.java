package net.eanfang.client.ui.activity.worksapce.scancode;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.entity.WorkerEntity;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JumpItent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.im.AddFriendActivity;
import net.eanfang.client.ui.activity.worksapce.equipment.EquipmentDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import cn.hutool.core.util.StrUtil;

import static com.eanfang.util.StringUtils.getValueByName;

/**
 * @author Guanluocang
 * @date on 2018/5/23  11:10
 * @decision 扫码界面
 */
public class ScanCodeActivity extends BaseActivity {
    private DecoratedBarcodeView barcodeScannerView;

    /**
     * 从哪来传输来的  参数
     */
    private String mFromWhere = "";
    /**
     * 添加朋友
     */
    private String mAddFriend = "";

    /**
     * 扫描什么类型的二维码
     */
    private String mScanType = "";
//    private ActivityScanCodeBinding scanCodeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan_code);
//        scanCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_code);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("二维码扫描");
        mFromWhere = getIntent().getStringExtra("from");
        mScanType = getIntent().getStringExtra("scanType");
        mAddFriend = getIntent().getStringExtra(EanfangConst.QR_ADD_FRIEND);
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        if (!StrUtil.isEmpty(mScanType)) {
            // 扫码设备
            if (mScanType.equals("scan_device")) {
                barcodeScannerView.setStatusText(getResources().getString(R.string.zxing_device));
                // 扫码技师/客户
            } else if (mScanType.equals("scan_person")) {
                barcodeScannerView.setStatusText(getResources().getString(R.string.zxing_scan_person));
                //扫码登录
            } else if (mScanType.equals("scan_login")) {
                barcodeScannerView.setStatusText(getResources().getString(R.string.zxing_scan_login));
                // 扫码添加好友
            } else if (mScanType.equals("scan_addfriend")) {
                barcodeScannerView.setStatusText(getResources().getString(R.string.zxing_scan_addfriend));
            }
        }

        barcodeScannerView.decodeContinuous(callback);

    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            barcodeScannerView.pause();
            String resultString = result.getText();
            if (StrUtil.isEmpty(resultString)) {
                return;
            }
            if (resultString.equals("")) {
                showToast("扫描失败!");
                // 扫码个人二维码
            } else if (resultString.contains("?_account=")) {
                //如果是true 则 解析扫到的二维码里的字符串 得到account的 ID
                String accountId = resultString.substring(resultString.indexOf("=") + 1);
                if (!TextUtils.isEmpty(mAddFriend)) {
                    if (mFromWhere.equals("home_addfriend")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("add_friend", "add_friend");
                        bundle.putString("accountId", accountId);
                        JumpItent.jump(ScanCodeActivity.this, AddFriendActivity.class, bundle);
                    } else {
                        EventBus.getDefault().post(accountId);
                    }
                    finish();

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
            } else if (isInteger(resultString)) {
                //申请进群 //如果是纯数字 就说明是加群
                Intent intent = new Intent();
                if (!TextUtils.isEmpty(mFromWhere)) {
                    intent.setAction("client_group");
                } else {
                    intent.setAction("worker_group");
                }
                intent.putExtra("groupId", resultString);
                startActivity(intent);
                finish();
            } else if (resultString.contains("qr?uid=")) {
                // 扫描设备 查看设备详情 并报修
                Bundle bundle = new Bundle();
                bundle.putString("id", getValueByName(result.getText(), "uid"));
                bundle.putBoolean("scan", true);
                JumpItent.jump(ScanCodeActivity.this, EquipmentDetailActivity.class, bundle);
                finish();
            } else {
                showToast("二维码无效");
                finish();
            }
            // 扫码结果
//            beepManager.playBeepSoundAndVibrate();
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
                        finish();
                    }
                });
    }

    /**
     * 获取技师详情
     */
    private void doGetWorkDetail(WorkerEntity workerEntity) {
        EventBus.getDefault().post(workerEntity);
        finish();
    }

    /**
     * 获取登录权限
     */
    private void doQRLoginPermission(String uuid, String requestFrom) {
        EanfangHttp.post(NewApiService.QR_CODE)
                .params("uuid", uuid)
                .params("requestFrom", requestFrom)
                .params("accountId", ClientApplication.get().getAccId())
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
                            finish();

                        } else {
                            showToast("暂无权限");
                            finish();
                        }
                    }

                    public void onFail(Integer code, String message, com.alibaba.fastjson.JSONObject jsonObject) {
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
