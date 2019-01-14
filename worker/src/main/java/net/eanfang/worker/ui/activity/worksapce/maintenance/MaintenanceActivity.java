package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaintenanceActivity extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance2);
        ButterKnife.bind(this);
        setTitle("维保管控");
        setLeftBack();
    }

    @OnClick({R.id.iv_own, R.id.iv_section, R.id.iv_company})
    public void onViewClicked(View view) {
        if (!PermKit.get().getMaintenanceListPrem()) return;
        Intent intent = new Intent(this, MaintenanceListActivity.class);
        switch (view.getId()) {
            case R.id.iv_own:
                intent.putExtra("type", 1);//我自己
                startAnimActivity(intent);
                break;
            case R.id.iv_section:
                intent.putExtra("type", 2);//部門
                startAnimActivity(intent);
                break;
            case R.id.iv_company:
                intent.putExtra("type", 3);//公司
                startAnimActivity(intent);
                break;
            default:
                break;
        }
    }
}
