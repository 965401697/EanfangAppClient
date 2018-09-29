package net.eanfang.worker.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/7/10  15:13
 * @decision 故障详情页面
 */
public class FaultDetailActivity extends BaseActivity {
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO_two = 1;
    @BindView(R.id.tv_faultDeviceName)
    TextView tvFaultDeviceName;
    @BindView(R.id.tv_deviceNum)
    TextView tvDeviceNum;
    @BindView(R.id.tv_deviceLocationNum)
    TextView tvDeviceLocationNum;
    @BindView(R.id.tv_deviceLocationHint)
    TextView tvDeviceLocationHint;
    @BindView(R.id.tv_deviceLocation)
    TextView tvDeviceLocation;
    @BindView(R.id.tv_deviceBrandHint)
    TextView tvDeviceBrandHint;
    @BindView(R.id.tv_deviceBrand)
    TextView tvDeviceBrand;
    @BindView(R.id.tv_deviceModelHint)
    TextView tvDeviceModelHint;
    @BindView(R.id.tv_deviceModel)
    TextView tvDeviceModel;
    @BindView(R.id.tv_faultSketch)
    TextView tvFaultSketch;
    @BindView(R.id.tv_faultNum)
    TextView tvFaultNum;
    @BindView(R.id.et_faultNum)
    EditText etFaultNum;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.tv_faultDescripte)
    TextView tvFaultDescripte;

    private ArrayList<String> picList1 = new ArrayList<>();
    private RepairBugEntity repairBugEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initNinePhoto();
    }

    private void initView() {
        setTitle("故障详情");
        setLeftBack();
        repairBugEntity = (RepairBugEntity) getIntent().getSerializableExtra("faultDeatail");
    }

    private void initData() {
        //故障设备
        if (TextUtils.isEmpty(repairBugEntity.getBusinessThreeName())) {
            tvFaultDeviceName.setText(Config.get().getBusinessNameByCode(repairBugEntity.getBusinessThreeCode(), 3));
        } else {
            tvFaultDeviceName.setText("其他 - " + repairBugEntity.getBusinessThreeName());
        }
        //设备编号
        tvDeviceNum.setText(repairBugEntity.getDeviceNo());
        //位置编号
        tvDeviceLocationNum.setText(repairBugEntity.getLocationNumber());
        //故障位置
        tvDeviceLocation.setText(repairBugEntity.getBugPosition());
        // 设备品牌
        if (TextUtils.isEmpty(repairBugEntity.getModelName())) {
            tvDeviceBrand.setText(Config.get().getModelNameByCode(repairBugEntity.getModelCode(), 2));
        } else {
            tvDeviceBrand.setText("其他 - " + repairBugEntity.getModelName());
        }
        // 故障简述
        tvFaultSketch.setText(repairBugEntity.getSketch());
        // 故障描述
        tvFaultDescripte.setText(repairBugEntity.getBugDescription());
    }

    private void initNinePhoto() {
        //修改小bug 图片读取问题
        if (StringUtils.isValid(repairBugEntity.getPictures())) {
            String[] prePic = repairBugEntity.getPictures().split(Constant.IMG_SPLIT);
            picList1.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
        snplMomentAddPhotos.setData(picList1);
        snplMomentAddPhotos.setEditable(false);
    }


}
