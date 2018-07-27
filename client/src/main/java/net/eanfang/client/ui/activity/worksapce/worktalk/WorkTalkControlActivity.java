package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 选择中心
 */
public class WorkTalkControlActivity extends BaseActivity {

    @BindView(R.id.iv_work_receiver)
    ImageView ivWorkReceiver;
    @BindView(R.id.iv_work_create)
    ImageView ivWorkCreate;
    @BindView(R.id.iv_repairNew)
    ImageView ivRepairNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_talk_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("面谈员工");
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
                JumpItent.jump(WorkTalkControlActivity.this, WorkTalkCreateActivity.class);
                break;
        }
    }

    public void doJump(String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", title);
        JumpItent.jump(WorkTalkControlActivity.this, WorkTalkListActivity.class, bundle);
    }
}
