package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

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
            new PersonMaintainHistoryView(mContext, true, EanfangApplication.getApplication().getUserId(), 0).show();
        });
        llMineCompany.setOnClickListener((v) -> {
            new PersonMaintainHistoryView(mContext, true, EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId(), 1).show();
        });
    }
}
