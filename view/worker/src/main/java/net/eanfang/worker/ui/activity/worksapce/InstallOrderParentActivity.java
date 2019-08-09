package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.base.kit.V.v;

public class InstallOrderParentActivity extends BaseWorkerActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_accept)
    RelativeLayout llMineAccept;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_install_order_parent);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("报装管控");
        setLeftBack();
        initView();
    }

    private void initView() {
        ivAdd.setVisibility(View.GONE);
        llMineAssignment.setVisibility(View.GONE);
        llMineAccept.setVisibility(View.VISIBLE);
        llMineAccept.setOnClickListener(v -> jump("我负责的", 0));
        llMineCompany.setOnClickListener(v -> jump("本公司的", 1));
        String orgName = v(() -> (WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            llMineCompany.setVisibility(View.GONE);
        } else {
            llMineCompany.setVisibility(View.VISIBLE);
        }
    }

    private void jump(String title, int type) {
        if (!PermKit.get().getInstallListPrem()) {
            return;
        }
        Intent intent = new Intent(this, InstallOrderListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
