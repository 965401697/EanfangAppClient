package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;
import com.yaf.base.entity.ShopMaintenanceExamResultEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaintenanceHandleEditActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_os_name)
    TextView tvOsName;
    @BindView(R.id.iv_upload)
    SimpleDraweeView ivUpload;
    @BindView(R.id.tv_location_num)
    TextView tvLocationNum;
    @BindView(R.id.tv_position_location)
    TextView tvPositionLocation;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.et_check)
    EditText etCheck;
    @BindView(R.id.et_handle)
    EditText etHandle;
    @BindView(R.id.et_suggest)
    EditText etSuggest;
    private ShopMaintenanceExamDeviceEntity examDeviceEntity;

    //添加检查结果
    private final int ADD_HANDLE_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_handle_add);
        ButterKnife.bind(this);
        setTitle("维保处理");
        setLeftBack();

        initData();
        initViews();
    }

    private void initData() {
        examDeviceEntity = (ShopMaintenanceExamDeviceEntity) getIntent().getSerializableExtra("bean");
    }

    private void initViews() {
        tvOsName.setText(Config.get().getBaseNameByCode(Constant.SYS_TYPE, examDeviceEntity.getBusinessThreeCode(), 3));
        if (!TextUtils.isEmpty(examDeviceEntity.getPicture())) {
            ivUpload.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + examDeviceEntity.getPicture().split(",")[0]));
        }
        tvLocationNum.setText("位置编号    " + examDeviceEntity.getLocationNumber());
        tvPositionLocation.setText("安装位置    " + examDeviceEntity.getLocation());
        if (examDeviceEntity.getStatus() == 0) {
            tvStatus.setText("维保状态    保内");
        } else {
            tvStatus.setText("维保状态    保外");
        }

        tvHistory.setText("维修历史    " + String.valueOf(examDeviceEntity.getRepairCount()));
        if (examDeviceEntity.getMaintenanceDetailEntity() != null) {
            etCheck.setText(examDeviceEntity.getMaintenanceDetailEntity().getCheckProcess());
            etHandle.setText(examDeviceEntity.getMaintenanceDetailEntity().getMeasure());
            etSuggest.setText(examDeviceEntity.getMaintenanceDetailEntity().getUseAdvice());
        }
    }

    @OnClick({R.id.ll_photo, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_photo:
                Intent intent = new Intent(this, MeintenanceEditPhotoActivity.class);
                intent.putExtra("bean", examDeviceEntity);
                startActivityForResult(intent, ADD_HANDLE_RESULT);
                break;
            case R.id.tv_add:
                doSubData();
                break;
        }
    }

    private void doSubData() {

        if (!isCheck()) return;

        if (examDeviceEntity.getMaintenanceDetailEntity() == null) {
            ToastUtil.get().showToast(this, "请选择照片");
            return;
        }

        examDeviceEntity.getMaintenanceDetailEntity().setCheckProcess(etCheck.getText().toString().trim());
        examDeviceEntity.getMaintenanceDetailEntity().setMeasure(etHandle.getText().toString().trim());
        examDeviceEntity.getMaintenanceDetailEntity().setUseAdvice(etSuggest.getText().toString().trim());

        Intent intent = new Intent();
        intent.putExtra("bean", examDeviceEntity);
        setResult(RESULT_OK, intent);
        finishSelf();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_HANDLE_RESULT) {
                ShopMaintenanceExamDeviceEntity deviceEntity = (ShopMaintenanceExamDeviceEntity) data.getSerializableExtra("bean");
                examDeviceEntity = deviceEntity;
            }
        }

    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(etCheck.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入检查过程与方法");
            return false;
        }
        if (TextUtils.isEmpty(etHandle.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入处理措施");
            return false;
        }
        if (TextUtils.isEmpty(etSuggest.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入使用建议");
            return false;
        }


        return true;
    }
}
