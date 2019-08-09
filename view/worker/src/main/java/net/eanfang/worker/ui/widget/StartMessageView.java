package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.MyFriendsListActivity;
import net.eanfang.worker.ui.activity.im.MyGroupsListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/8/8
 * @description
 */
public class StartMessageView extends BaseDialog {

    private Activity mContext;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public StartMessageView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_startmessage);
        ButterKnife.bind(this);
        initView();
        tvTitle.setText("发起聊天");
    }

    private void initView() {
        findViewById(R.id.iv_left).setOnClickListener(v -> dismiss());
        findViewById(R.id.ll_my_friends).setOnClickListener(v -> {
            JumpItent.jump(mContext, MyFriendsListActivity.class);
            dismiss();
        });
        findViewById(R.id.ll_my_group).setOnClickListener(v -> {
            JumpItent.jump(mContext, MyGroupsListActivity.class);
            dismiss();
        });
    }
}
