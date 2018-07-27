package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/27  15:50
 * @decision 交接班创建
 */
public class WorkTransferCreateActivity extends BaseActivity {

    @BindView(R.id.tv_department_name)
    TextView tvDepartmentName;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_shift)
    TextView tvShift;
    @BindView(R.id.ll_shift)
    LinearLayout llShift;
    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.ll_receiver_person)
    LinearLayout llReceiverPerson;
    @BindView(R.id.tv_telphone)
    TextView tvTelphone;
    @BindView(R.id.tv_add_hand)
    TextView tvAddHand;
    @BindView(R.id.rv_hand_item)
    RecyclerView rvHandItem;
    @BindView(R.id.tv_add_finish_work)
    TextView tvAddFinishWork;
    @BindView(R.id.rv_finsh_work)
    RecyclerView rvFinshWork;
    @BindView(R.id.tv_add_unfinish_things)
    TextView tvAddUnfinishThings;
    @BindView(R.id.rv_unfinish_things)
    RecyclerView rvUnfinishThings;
    @BindView(R.id.tv_add_follow_things)
    TextView tvAddFollowThings;
    @BindView(R.id.rv_follow_things)
    RecyclerView rvFollowThings;
    @BindView(R.id.tv_add_attention_things)
    TextView tvAddAttentionThings;
    @BindView(R.id.rv_attention_things)
    RecyclerView rvAttentionThings;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_create);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("交接班");
    }

    @OnClick({R.id.ll_department, R.id.ll_shift, R.id.ll_receiver_person, R.id.tv_add_hand, R.id.tv_add_finish_work, R.id.tv_add_unfinish_things, R.id.tv_add_follow_things, R.id.tv_add_attention_things, R.id.rl_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_department:
                break;
            case R.id.ll_shift:
                break;
            case R.id.ll_receiver_person:
                break;
            case R.id.tv_add_hand:
                break;
            case R.id.tv_add_finish_work:
                break;
            case R.id.tv_add_unfinish_things:
                break;
            case R.id.tv_add_follow_things:
                break;
            case R.id.tv_add_attention_things:
                break;
            case R.id.rl_confirm:
                break;
        }
    }

}
