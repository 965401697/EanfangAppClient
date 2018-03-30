package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.config.Config;
import com.eanfang.ui.base.BaseDialog;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  15:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MateraInfoView extends BaseDialog {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_equipment)
    TextView tvEquipment;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.et_code)
    TextView etCode;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private Activity mContext;
    private BughandleUseDeviceEntity bughandleUseDeviceEntity;

    public MateraInfoView(Activity context, boolean isfull, BughandleUseDeviceEntity bughandleUseDeviceEntity) {
        super(context, isfull);
        this.mContext = context;
        this.bughandleUseDeviceEntity = bughandleUseDeviceEntity;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_material);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("耗用材料");

        tvBusiness.setText(config.getBusinessNameByCode(bughandleUseDeviceEntity.getBusinessThreeCode(), 2));

        tvModel.setText(config.getModelNameByCode(bughandleUseDeviceEntity.getModelCode(), 2));
        tvEquipment.setText(bughandleUseDeviceEntity.getDeviceName());
        etLocation.setText(bughandleUseDeviceEntity.getCount() + "");
        etCode.setText(bughandleUseDeviceEntity.getRemarkInfo());
        etLocation.setEnabled(false);
        etCode.setEnabled(false);
    }
}
