package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkControlActivity;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkCreateActivity;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkListActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 交接班 选择中心
 */
public class WorkTransferControlActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("交接班");
        setLeftBack();
    }

    @OnClick({R.id.iv_work_receiver, R.id.iv_work_create, R.id.iv_repairNew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_receiver:
                doJump("我接收的");
                break;
            case R.id.iv_work_create:
                doJump("我创建的");
                break;
            case R.id.iv_repairNew:
                JumpItent.jump(WorkTransferControlActivity.this, WorkTransferCreateActivity.class);
                break;
        }
    }

    public void doJump(String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", title);
        JumpItent.jump(WorkTransferControlActivity.this, WorkTransferListActivity.class, bundle);
    }
}
