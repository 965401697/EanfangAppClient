package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.DesignOrderListActivity;
import net.eanfang.client.ui.activity.worksapce.InstallActivity;
import net.eanfang.client.ui.activity.worksapce.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  10:19
 * @email houzhongzhou@yeah.net
 * @desc 设计管控
 */

public class RepairCtrlView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_repair)
    RelativeLayout rlRepair;
    private Activity mContext;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    public RepairCtrlView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_repair_ctrl);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("报修");
        ivLeft.setOnClickListener(v -> dismiss());
        ivAdd.setOnClickListener((v) -> {
            RepairActivity.jumpToActivity(mContext);
        });
        rlRepair.setOnClickListener((v) -> {
            jumpInDialog(mContext, RepairCtrlActivity.class);
        });
    }
}
