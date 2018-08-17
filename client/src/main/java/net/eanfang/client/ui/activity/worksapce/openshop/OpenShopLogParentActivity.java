package net.eanfang.client.ui.activity.worksapce.openshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.util.PermKit;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogActivity;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogWriteActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenShopLogParentActivity extends BaseClientActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_mine_assignment)
    ImageView ivMineAssignment;
    @BindView(R.id.iv_mine_accept)
    ImageView ivMineAccept;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop_log_parent);
        ButterKnife.bind(this);
        setTitle("开店日志");
        setLeftBack();
        initView();
    }

    private void initView() {
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getOpenShopCreatePrem()) return;
                startActivity(new Intent(OpenShopLogParentActivity.this, OpenShopLogWriteActivity.class));
            }
        });
        ivMineAssignment.setOnClickListener(v -> jump("我创建的", 1));
        ivMineAccept.setOnClickListener(v -> jump("我接收的", 2));
    }

    private void jump(String title, int type) {
        if (!PermKit.get().getOpenShopCreatePrem()) return;
        Intent intent = new Intent(this, OpenShopLogActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
