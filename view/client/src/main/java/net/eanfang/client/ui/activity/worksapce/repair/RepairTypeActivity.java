package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;

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

    @BindView(R.id.iv_repairNew)
    ImageView ivRepairNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair_type);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle(R.string.text_home_reapir);
        setRightTitle("历史报修");
        setRightImageResId(R.mipmap.ic_client_repair_list);
        setRightTitleOnClickListener((v) -> {
            if (PermKit.get().getRepairListPerm()) {
                JumpItent.jump(RepairTypeActivity.this, RepairCtrlActivity.class);
            }
        });
    }

    @OnClick({R.id.iv_repairNew, R.id.tv_repairPhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_repairNew:
                if (PermKit.get().getRepairCreatePerm()) {
                    JumpItent.jump(RepairTypeActivity.this, AddTroubleActivity.class);
                }
                break;
            case R.id.tv_repairPhone:
                JumpItent.jump(RepairTypeActivity.this, RepairPhoneActivity.class);
                break;
            default:
                break;
        }
    }
}
