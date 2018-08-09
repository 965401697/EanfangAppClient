package net.eanfang.client.ui.activity.worksapce.oa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.WorkTaskListActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskParentActivity extends BaseClientActivity {
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
        ivAdd.setOnClickListener(v -> startActivity(new Intent(this, TaskActivity.class)));
        llMineAssignment.setOnClickListener(v -> jump("我创建的", "1"));
        llMineAccept.setOnClickListener(v -> jump("我负责的", "2"));
        llMineCompany.setOnClickListener(v -> jump("本公司的", "0"));
    }

    private void jump(String title, String type) {
        Intent intent = new Intent(this, WorkTaskListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
