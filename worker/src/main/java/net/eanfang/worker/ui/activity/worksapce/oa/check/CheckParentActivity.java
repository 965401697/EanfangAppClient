package net.eanfang.worker.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author guanluocang
 * @data 2018/9/14
 * @description 设备点检 （我创建的  我负责的  本公司的）
 */
public class CheckParentActivity extends BaseWorkerActivity {
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
        setContentView(R.layout.activity_check_parent);
        ButterKnife.bind(this);
        setTitle("设备点检");
        setLeftBack();
        initView();
    }

    private void initView() {

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getWorkInspectCreatePrem()) return;
                startActivity(new Intent(CheckParentActivity.this, CheckActivity.class));
            }
        });
        llMineAssignment.setOnClickListener((v) -> {
            jump("我创建的检查", 1);
        });
        llMineAccept.setOnClickListener((v) -> {
            jump("我负责的检查", 2);
        });
        llMineCompany.setOnClickListener((v) -> {
            jump("本公司的检查", 0);
        });
    }

    /**
     * 跳转列表界面
     */
    private void jump(String title, int type) {

        if (!PermKit.get().getWorkInspectListPrem()) return;

        Intent intent = new Intent(this, WorkCheckListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
