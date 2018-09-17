package net.eanfang.client.ui.activity.worksapce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.util.PermKit;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DesignOrderActivity extends BaseClientActivity {
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
        setContentView(R.layout.activity_design_order);
        ButterKnife.bind(this);
        setTitle("安防设计");
        setLeftBack();
        initView();
    }

    private void initView() {
        ivAdd.setOnClickListener((v) -> {
            if (PermKit.get().getDesignCreatePrem()) {
                startActivity(new Intent(this, DesignActivity.class));
            }
        });
        llMineAssignment.setOnClickListener((v) -> {
            jumpInDialog(this, DesignOrderListActivity.class, "我的设计单", "1");
        });
        //如果是个人 隐藏公司
        if (EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId() == 0) {
            llMineCompany.setVisibility(View.GONE);
        }
        llMineCompany.setOnClickListener((v) -> {
            jumpInDialog(this, DesignOrderListActivity.class, "公司的设计单", "0");
        });
    }

    public void jumpInDialog(Activity activity, Class<?> cls, String title, String type) {
        if (PermKit.get().getDesignListPrem()) {
            Intent intent = new Intent(activity, cls);
            intent.putExtra("title", title);
            intent.putExtra("type", type);
            activity.startActivity(intent);
        }
    }
}
