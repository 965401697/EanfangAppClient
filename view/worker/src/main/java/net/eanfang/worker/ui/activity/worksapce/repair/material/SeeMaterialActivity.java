package net.eanfang.worker.ui.activity.worksapce.repair.material;

import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.biz.model.bean.ZjZgBean;
import com.eanfang.biz.model.entity.BughandleUseDeviceEntity;
import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/11  15:25
 * @decision 查看耗材详情
 */
public class SeeMaterialActivity extends BaseActivity {

    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_equipment)
    TextView tvEquipment;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;


    private BughandleUseDeviceEntity bughandleUseDeviceEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_see_material);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitle("耗用材料");
        setLeftBack();
        bughandleUseDeviceEntity = (BughandleUseDeviceEntity) getIntent().getSerializableExtra("bughandleUseDeviceEntity");

        tvBusiness.setText(Config.get().getBusinessNameByCode(bughandleUseDeviceEntity.getBusinessThreeCode(), 2));
        tvModel.setText(Config.get().getModelNameByCode(bughandleUseDeviceEntity.getModelCode(), 2));
        tvEquipment.setText(bughandleUseDeviceEntity.getDeviceName());
        tvNum.setText(bughandleUseDeviceEntity.getCount() + "");
        tvRemarks.setText(bughandleUseDeviceEntity.getRemarkInfo());


    }
}
