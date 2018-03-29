package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.MaintenanceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  16:42
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MaintainCtrlView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    private Activity mContext;

    public MaintainCtrlView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_maintain_ctrl);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("维保管控");
        ivRight.setImageResource(R.drawable.nav_ic_add_pressed);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, MaintenanceActivity.class)));
        llMineAssignment.setOnClickListener((v) -> {
            new PersonMaintainHistoryView(mContext, true, EanfangApplication.getApplication().getUserId(), 0).show();
        });
        llMineCompany.setOnClickListener((v) -> {
            new PersonMaintainHistoryView(mContext, true, EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId(), 1).show();
        });
    }
}
