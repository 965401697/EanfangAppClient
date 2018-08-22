package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogActivity;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogWriteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by O u r on 2018/5/22.
 */

public class OpenShopLogView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_mine_assignment)
    ImageView ivMineAssignment;
    @BindView(R.id.iv_mine_accept)
    ImageView ivMineAccept;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private Activity mContext;

    public OpenShopLogView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_open_shop_log);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("开店日志");
        ivAdd.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, OpenShopLogWriteActivity.class)));
        ivMineAssignment.setOnClickListener(v -> jump("我创建的", 1));
        ivMineAccept.setOnClickListener(v -> jump("我接收的", 2));
    }

    private void jump(String title, int type) {
        Intent intent = new Intent(mContext, OpenShopLogActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }
}
