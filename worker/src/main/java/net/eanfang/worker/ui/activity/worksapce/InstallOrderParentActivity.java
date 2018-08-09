package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.InstallOrderListActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_order_parent);
        ButterKnife.bind(this);
        setTitle("报修管控");
        setLeftBack();
        initView();
    }

    private void initView() {
        ivAdd.setVisibility(View.GONE);
        llMineAssignment.setVisibility(View.GONE);
        llMineAccept.setVisibility(View.VISIBLE);
        llMineAccept.setOnClickListener(v -> jump("我负责的", 0));
        llMineCompany.setOnClickListener(v -> jump("本公司的", 1));
    }

    private void jump(String title, int type) {
        Intent intent = new Intent(this, InstallOrderListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
