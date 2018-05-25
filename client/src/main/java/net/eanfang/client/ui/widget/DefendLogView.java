package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.DefendLogActivity;
import net.eanfang.client.ui.activity.worksapce.DefendLogWriteAndDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by O u r on 2018/5/22.
 */

public class DefendLogView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_accept)
    RelativeLayout llMineAccept;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private Activity mContext;

    public DefendLogView(Activity context, boolean isfull) {
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
        tvTitle.setText("布防日志");
        ivAdd.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, DefendLogWriteAndDetailActivity.class).putExtra("add", "add")));
        llMineAssignment.setOnClickListener(v -> jump("我创建的", 1));
        llMineAccept.setOnClickListener(v -> jump("我接收的", 2));
    }

    private void jump(String title, int type) {
        Intent intent = new Intent(mContext, DefendLogActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }
}
