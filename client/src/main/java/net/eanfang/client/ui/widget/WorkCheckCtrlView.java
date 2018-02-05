package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.WorkCheckListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  13:34
 * @email houzhongzhou@yeah.net
 * @desc 检查管控
 */

public class WorkCheckCtrlView extends BaseDialog {
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

    public WorkCheckCtrlView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_check_ctrl);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("检查管控");
        llMineAssignment.setOnClickListener((v) -> {
            jump("我创建的检查", "1");
        });
        llMineAccept.setOnClickListener((v) -> {
            jump("我负责的检查", "2");
        });
        llMineCompany.setOnClickListener((v) -> {
            jump("本公司的检查", "0");
        });
    }

    /**
     * 跳转列表界面
     */
    private void jump(String title, String type) {
        Intent intent = new Intent(mContext, WorkCheckListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }
}
