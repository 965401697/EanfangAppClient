package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.MineTakePublishListActivity;
import net.eanfang.worker.ui.activity.worksapce.TakeTaskListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/17  22:25
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TakePubCtrlView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private Activity mContext;

    public TakePubCtrlView(Activity context, boolean isfull) {
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
        tvTitle.setText("我的接包");
        tvMine.setText("我负责的");
        llMineAssignment.setOnClickListener(v -> jump("我负责的", "1"));
        llMineCompany.setOnClickListener(v -> jump("我公司的", "2"));
        ivAdd.setOnClickListener((v) -> {
            jumpInDialog(mContext, TakeTaskListActivity.class);
        });
    }

    private void jump(String title, String type) {
        Intent intent = new Intent(mContext, MineTakePublishListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }
}
