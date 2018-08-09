package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineTaskPublishListSendParentActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_task_publish_list_send);
        ButterKnife.bind(this);
        setTitle("我的发包");
        setLeftBack();

        initView();
    }

    private void initView() {

        llMineAssignment.setOnClickListener(v -> jump("我创建的", 1));
        llMineCompany.setOnClickListener(v -> jump("我公司的", 2));
        ivAdd.setOnClickListener((v) -> {
            startActivity(new Intent(this, TaskPublishActivity.class));
        });
    }

    private void jump(String title, int type) {
        Intent intent = new Intent(this, MineTaskPublishListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
