package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.activity.worksapce.DesignOrderListActivity;
import net.eanfang.client.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  10:19
 * @email houzhongzhou@yeah.net
 * @desc 设计管控
 */

public class DesignCtrlView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;

    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;

    public DesignCtrlView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_design_ctrl);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("安防设计");
        ivLeft.setOnClickListener(v -> dismiss());
        llMineAssignment.setOnClickListener((v) -> {
            jump("我的设计单", "1");
        });
        //如果是个人 隐藏公司
        if (StringUtils.isEmpty(EanfangApplication.get().getUser().getCompanyId())) {
            llMineCompany.setVisibility(View.GONE);
        }
        llMineCompany.setOnClickListener((v) -> {
            jump("公司的设计单", "0");
        });
    }


    private void jump(String title, String type) {
        Intent intent = new Intent(mContext, DesignOrderListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }
}
