package net.eanfang.client.ui.activity.worksapce.install;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.util.PermKit;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.base.kit.V.v;

public class InstallOrderParentActivity extends BaseClientActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_accept)
    RelativeLayout llMineAccept;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_install_parent);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("报装管控");
        setLeftBack();
        initView();
    }

    private void initView() {

        ivAdd.setOnClickListener((v) -> {
            if (PermKit.get().getInstallCreatePrem()) {
                InstallActivity.jumpActivity(this);
            }
        });
        llMineAssignment.setOnClickListener(v -> jump("我创建的", 1));
        llMineAccept.setOnClickListener(v -> jump("我负责的", 2));
        llMineCompany.setOnClickListener(v -> jump("本公司的", 0));
        String orgName = v(() -> (ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            llMineCompany.setVisibility(View.GONE);
        } else {
            llMineCompany.setVisibility(View.VISIBLE);
        }
    }

    private void jump(String title, int type) {
        if (PermKit.get().getInstallListPrem()) {
            Intent intent = new Intent(this, InstallOrderActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }
}
