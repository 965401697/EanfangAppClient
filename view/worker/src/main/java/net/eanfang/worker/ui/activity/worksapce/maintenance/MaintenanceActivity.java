package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eanfang.base.kit.V.v;

public class MaintenanceActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_section)
    ImageView ivSection;
    @BindView(R.id.iv_company)
    ImageView ivCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maintenance2);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("维保管控");
        setLeftBack();
        String orgName = v(() -> (WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            ivSection.setVisibility(View.GONE);
            ivCompany.setVisibility(View.GONE);
        } else {
            ivSection.setVisibility(View.VISIBLE);
            ivCompany.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_own, R.id.iv_section, R.id.iv_company})
    public void onViewClicked(View view) {
        if (!PermKit.get().getMaintenanceListPrem()) {
            return;
        }
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
