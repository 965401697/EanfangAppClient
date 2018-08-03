package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkCreateActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/27  15:50
 * @decision 交接班创建
 */
public class WorkTransferCreateActivity extends BaseActivity {

    //交接物品
    private static final int ADD_HAND = 100;
    //完成工作
    private static final int ADD_FINISH_WORK = 200;
    //未处理事项
    private static final int ADD_UNFINISH_THINGS = 300;
    //跟进处理事项
    private static final int ADD_FLOW_THINGS = 400;
    //注意事项
    private static final int ADD_ATTENTION_THINGS = 500;

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
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            // 部门名称
            case R.id.ll_department:
                break;
            // 班次
            case R.id.ll_shift:
                break;
            // 接收人
            case R.id.ll_receiver_person:
                break;
            //交接物品
            case R.id.tv_add_hand:
                bundle.putSerializable("switch", "ADD_HAND");
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle, ADD_HAND);
                break;
            // 完成工作
            case R.id.tv_add_finish_work:
                bundle.putSerializable("switch", "ADD_FINISH_WORK");
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle, ADD_FINISH_WORK);
                break;
            // 未处理事项
            case R.id.tv_add_unfinish_things:
                bundle.putSerializable("switch", "ADD_UNFINISH_THINGS");
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle, ADD_UNFINISH_THINGS);
                break;
            // 跟进处理事项
            case R.id.tv_add_follow_things:
                bundle.putSerializable("switch", "ADD_FLOW_THINGS");
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle, ADD_FLOW_THINGS);
                break;
            // 注意事项
            case R.id.tv_add_attention_things:
                bundle.putSerializable("switch", "ADD_ATTENTION_THINGS");
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle, ADD_ATTENTION_THINGS);
                break;
            // 提交日志
            case R.id.rl_confirm:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
//        if (!doCheckInfo())
//            return;
//        EanfangHttp.post(NewApiService.WORK_TRANSFER_ADD)
//                .upJson(object)
//                .execute(new EanfangCallback<JSONObject>(WorkTalkCreateActivity.this, true, JSONObject.class, (bean) -> {
//                    showToast("添加完毕");
//                    finishSelf();
//                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                //交接物品
                case ADD_HAND:
                    showToast("交接物品添加了一条");
                    break;
                //完成工作
                case ADD_FINISH_WORK:
                    showToast("完成工作添加了一条");
                    break;
                //未处理事项
                case ADD_UNFINISH_THINGS:
                    showToast("未处理事项添加了一条");
                    break;
                //跟进处理事项
                case ADD_FLOW_THINGS:
                    showToast("跟进处理事项添加了一条");
                    break;
                //注意事项
                case ADD_ATTENTION_THINGS:
                    showToast("注意事项添加了一条");
                    break;
            }
        }
    }

//    public boolean doCheckInfo() {
//        try {
//            if (StringUtils.isEmpty(mUserId + "")) {
//                showToast("用户id为空");
//                return false;
//            } else {
//                object.put("owner_user_id", mUserId);
//            }
//
//            if (StringUtils.isEmpty(mCompanyId + "")) {
//                showToast("公司id为空");
//                return false;
//            } else {
//                object.put("owner_top_company_id", mCompanyId);
//            }
//            if (StringUtils.isEmpty(tvTalkObject.getText().toString().trim()) && StringUtils.isEmpty(mTalkId)) {
//                showToast("请选择面谈对象");
//                return false;
//            } else {
//                object.put("worker_user_id", mTalkId);
//                object.put("owner_department_id", mTalkId);
//            }
//            if (StringUtils.isEmpty(tvReceiverName.getText().toString().trim()) && StringUtils.isEmpty(mReceiverId)) {
//                showToast("请选择接收人");
//                return false;
//            } else {
//                object.put("assignee_user_id", mReceiverId);
//            }
//            if (!StringUtils.isEmpty(etWrokTalkOne.getText().toString().trim())) {
//                object.put("question1", etWrokTalkOne.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkTwo.getText().toString().trim())) {
//                object.put("question2", etWrokTalkTwo.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkThree.getText().toString().trim())) {
//                object.put("question3", etWrokTalkThree.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkFour.getText().toString().trim())) {
//                object.put("question4", etWrokTalkFour.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkFive.getText().toString().trim())) {
//                object.put("question5", etWrokTalkFive.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkSix.getText().toString().trim())) {
//                object.put("question6", etWrokTalkSix.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkSeven.getText().toString().trim())) {
//                object.put("question7", etWrokTalkSeven.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkEight.getText().toString().trim())) {
//                object.put("question8", etWrokTalkEight.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkNine.getText().toString().trim())) {
//                object.put("question9", etWrokTalkNine.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkTen.getText().toString().trim())) {
//                object.put("question10", etWrokTalkTen.getText().toString().trim());
//            }
//            if (!StringUtils.isEmpty(etWrokTalkEleven.getText().toString().trim())) {
//                object.put("question11", etWrokTalkEleven.getText().toString().trim());
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
