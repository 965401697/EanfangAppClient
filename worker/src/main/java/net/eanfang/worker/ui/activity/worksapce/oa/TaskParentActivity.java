package net.eanfang.worker.ui.activity.worksapce.oa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.WorkTaskListActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskParentActivity extends BaseWorkerActivity {
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
        setContentView(R.layout.activity_task_parent);
        ButterKnife.bind(this);
        setTitle("布置任务");
        setLeftBack();
        initView();
    }

    private void initView() {
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getWorkTaskCreatePrem()) {
                    return;
                }
                startActivity(new Intent(TaskParentActivity.this, TaskActivity.class));
            }
        });
        llMineAssignment.setOnClickListener(v -> jump("我创建的", "1"));
        llMineAccept.setOnClickListener(v -> jump("我负责的", "2"));
        llMineCompany.setOnClickListener(v -> jump("本公司的", "0"));
    }

    private void jump(String title, String type) {
        if (!PermKit.get().getWorkTaskListPrem()) {
            return;
        }
        Intent intent = new Intent(this, WorkTaskListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
