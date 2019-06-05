package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.MaintenanceActivity;
import net.eanfang.worker.ui.activity.worksapce.PersonMaintainHistoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  16:42
 * @email houzhongzhou@yeah.net
 * @desc 维保管控
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
    @BindView(R.id.iv_add)
    ImageView ivAdd;
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
        llMineAssignment.setOnClickListener((v) -> {
            Intent intent = new Intent(mContext, PersonMaintainHistoryActivity.class);
            intent.putExtra("id", WorkerApplication.get().getUserId());
            intent.putExtra("type", 0);
            mContext.startActivity(intent);

//            new PersonMaintainHistoryView(mContext, true, WorkerApplication.get().getUserId(), 0).show();
        });
        llMineCompany.setOnClickListener((v) -> {

            Intent intent = new Intent(mContext, PersonMaintainHistoryActivity.class);
            intent.putExtra("id", WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId());
            intent.putExtra("type", 1);
            mContext.startActivity(intent);

//            new PersonMaintainHistoryView(mContext, true, WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId(), 1).show();
        });

        ivAdd.setOnClickListener((v) -> {
            jumpInDialog(mContext, MaintenanceActivity.class);
        });
    }
}
