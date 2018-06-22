package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentParameterActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_voltage)
    TextView tvVoltage;
    @BindView(R.id.tv_electricity)
    TextView tvElectricity;
    @BindView(R.id.tv_ip)
    TextView tvIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_parameter);
        ButterKnife.bind(this);
        setTitle("设备参数");
        setLeftBack();

        ContentValues contentValues = getIntent().getParcelableExtra("params");
        if (contentValues != null) {
            if (!TextUtils.isEmpty(contentValues.getAsString("0"))) {
                tvVoltage.setText(contentValues.getAsString("0"));
            } else if (!TextUtils.isEmpty(contentValues.getAsString("1"))) {
                tvVoltage.setText(contentValues.getAsString("1"));
            } else if (!TextUtils.isEmpty(contentValues.getAsString("2"))) {
                tvVoltage.setText(contentValues.getAsString("2"));
            }
        }
    }
}
