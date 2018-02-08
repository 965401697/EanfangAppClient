package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.InstallOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  10:02
 * @email houzhongzhou@yeah.net
 * @desc 报装管控
 */

public class InstallCtrlView extends BaseDialog {
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
    private Activity mContext;


    public InstallCtrlView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_istall_ctrl);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("报装管控");
        llMineAssignment.setOnClickListener(v -> jump("我创建的", 1));
        llMineAccept.setOnClickListener(v -> jump("我负责的", 2));
        llMineCompany.setOnClickListener(v -> jump("本公司的", 0));
    }

    private void jump(String title, int type) {
        Intent intent = new Intent(mContext, InstallOrderActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }

}
