package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.ShopMaintenanceExamDeviceEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaintenanceHandleEditShowActivity extends BaseClientActivity {

    @BindView(R.id.tv_os_name)
    TextView tvOsName;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
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

    private void initData() {
        examDeviceEntity = (ShopMaintenanceExamDeviceEntity) getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maintenance_handle_add_show);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("维保处理");
        setLeftBack();

        initData();
        initViews();
    }

    private void initViews() {
        tvOsName.setText(Config.get().getBaseNameByCode(Constant.SYS_TYPE, examDeviceEntity.getBusinessThreeCode(), 3));
        if (!TextUtils.isEmpty(examDeviceEntity.getPicture())) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + examDeviceEntity.getPicture().split(",")[0]),ivUpload);
        }
        tvLocationNum.setText("位置编号    " + examDeviceEntity.getLocationNumber());
        tvPositionLocation.setText("安装位置    " + examDeviceEntity.getLocation());
        if (examDeviceEntity.getStatus() == 0) {
            tvStatus.setText("维保状态    保内");
        } else {
            tvStatus.setText("维保状态    保外");
        }

        tvHistory.setText("维修历史    " + examDeviceEntity.getRepairCount());

        etCheck.setText(examDeviceEntity.getMaintenanceDetailEntity().getCheckProcess());
        etHandle.setText(examDeviceEntity.getMaintenanceDetailEntity().getMeasure());
        etSuggest.setText(examDeviceEntity.getMaintenanceDetailEntity().getUseAdvice());

    }

    @OnClick(R.id.ll_photo)
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_photo:
                Intent intent = new Intent(this, MeintenanceEditPhotoActivity.class);
                intent.putExtra("bean", examDeviceEntity);
                intent.putExtra("isShow", true);
                startActivity(intent);
                break;

        }
    }


}
