package net.eanfang.client.ui.activity.worksapce.oa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.util.PermKit;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.WorkReportListActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportParentActivity extends BaseClientActivity {

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
        setContentView(R.layout.activity_report_parent);
        ButterKnife.bind(this);
        setTitle("工作汇报");
        setLeftBack();
        initView();
    }

    private void initView() {
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermKit.get().getWorkReportCreatePrem()) {
                    startActivity(new Intent(ReportParentActivity.this, ReportActivity.class));
                }
            }
        });
        llMineAssignment.setOnClickListener(v -> jump("我创建的", "1"));
        llMineAccept.setOnClickListener(v -> jump("我负责的", "2"));
        llMineCompany.setOnClickListener(v -> jump("本公司的", "0"));
    }

    private void jump(String title, String type) {
        if (PermKit.get().getWorkReportListPrem()) {
            Intent intent = new Intent(this, WorkReportListActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }
}