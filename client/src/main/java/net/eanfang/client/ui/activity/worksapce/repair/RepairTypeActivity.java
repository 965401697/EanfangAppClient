package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;
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
 * @date on 2018/5/14  15:55
 * @decision 选择 我要保修 报修管控
 */
public class RepairTypeActivity extends BaseActivity {

    @BindView(R.id.iv_repairList)
    ImageView ivRepairList;
    @BindView(R.id.iv_repairNew)
    ImageView ivRepairNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_type);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle(R.string.text_home_reapir);
    }

    @OnClick({R.id.iv_repairList, R.id.iv_repairNew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_repairList:
                JumpItent.jump(RepairTypeActivity.this, RepairCtrlActivity.class);
                break;
            case R.id.iv_repairNew:
                JumpItent.jump(RepairTypeActivity.this, RepairActivity.class);
                break;
        }
    }
}
