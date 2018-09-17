package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineTakePublishListReceiveParentActivity extends BaseWorkerActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_take_publish_list_parent);
        ButterKnife.bind(this);
        setTitle("我的找活");
        setLeftBack();
        initView();
    }

    private void initView() {
//        tvTitle.setText("我的接包");
        tvMine.setText("我负责的");
        llMineAssignment.setOnClickListener(v -> jump("我负责的", 1));
        llMineCompany.setOnClickListener(v -> jump("我公司的", 2));
        ivAdd.setOnClickListener((v) -> {
            startActivity(new Intent(this, TakeTaskListActivity.class));
        });
    }

    private void jump(String title, int type) {
        Intent intent = new Intent(this, MineTakePublishListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
