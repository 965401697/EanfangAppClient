package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkTransferDetailBean;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.oa.SelectOAGroupActivity;
import net.eanfang.client.ui.adapter.SendPersonAdapter;
import net.eanfang.client.ui.adapter.worktransfer.WorkTransferDetailAttentionAdapter;
import net.eanfang.client.ui.adapter.worktransfer.WorkTransferDetailFinishWorkAdapter;
import net.eanfang.client.ui.adapter.worktransfer.WorkTransferDetailFollowThingAdapter;
import net.eanfang.client.ui.adapter.worktransfer.WorkTransferDetailHandItemAdapter;
import net.eanfang.client.ui.adapter.worktransfer.WorkTransferDetailUnFinishWorkAdapter;
import net.eanfang.client.ui.widget.WorkTrancferCreateSelectClassListView;
import net.eanfang.client.util.SendContactUtils;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @BindView(R.id.tv_add_finish_work)
    TextView tvAddFinishWork;
    @BindView(R.id.tv_add_unfinish_things)
    TextView tvAddUnfinishThings;
    @BindView(R.id.tv_add_follow_things)
    TextView tvAddFollowThings;
    @BindView(R.id.tv_add_attention_things)
    TextView tvAddAttentionThings;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    @BindView(R.id.tv_send_group)
    TextView tvSendGroup;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    /**
     * 接收人Id
     */
    private String mReceiverId = "";
    /**
     * 用户ID
     */
    private Long mUserId;
    /**
     * 当前公司Id
     */
    private Long mCompanyId;
    /**
     * 当前顶级公司Id
     */
    private Long mTopCompanyId;
    /**
     * 部门ID
     */
    private String mDepartmentId = "";
    /**
     * 班次ID
     */
    private int mClassID = 100;
    /**
     * 接收人部门id
     */
    private String mReceiverDeparrmentID = "";

    @BindView(R.id.rv_hand_item)
    RecyclerView rvHandItem;
    /**
     * 交接物品
     */
    private List<WorkTransferDetailBean.ChangeGoodEntityListBean> mChangeGoodList = new ArrayList<>();
    private WorkTransferDetailHandItemAdapter workTransferDetailHandItemAdapter;

    @BindView(R.id.rv_finsh_work)
    RecyclerView rvFinshWork;
    /**
     * 完成工作
     */
    private List<WorkTransferDetailBean.FinishWorkEntityListBean> mFinishWorkList = new ArrayList<>();
    private WorkTransferDetailFinishWorkAdapter workTransferDetailFinishWorkAdapter;
    @BindView(R.id.rv_unfinish_things)
    RecyclerView rvUnfinishThings;
    /**
     * 未处理事项
     */
    private List<WorkTransferDetailBean.NotDidEntityListBean> mUnFinishList = new ArrayList<>();
    private WorkTransferDetailUnFinishWorkAdapter workTransferDetailUnFinishWorkAdapter;
    @BindView(R.id.rv_follow_things)
    RecyclerView rvFollowThings;
    /**
     * 跟进处理事项
     */
    private List<WorkTransferDetailBean.FollowUpEntityListBean> mFollowThingList = new ArrayList<>();
    private WorkTransferDetailFollowThingAdapter workTransferDetailFollowThingAdapter;

    @BindView(R.id.rv_attention_things)
    RecyclerView rvAttentionThings;
    /**
     * 注意事项
     */
    private List<WorkTransferDetailBean.NoticeEntityListBean> mAttentionList = new ArrayList<>();
    private WorkTransferDetailAttentionAdapter workTransferDetailAttentionAdapter;

    private WorkTransferDetailBean workTransferDetailBean = new WorkTransferDetailBean();


    private final int REQUEST_CODE_GROUP = 102;
    private ArrayList<TemplateBean.Preson> newGroupList = new ArrayList<>();
    private SendPersonAdapter sendGroupAdapter;
    private int isSend = -1;
    private SendPersonAdapter sendPersonAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(WorkTransferCreateActivity.this, "发送成功");
            finishSelf();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_create);
        ButterKnife.bind(this);
        initView();
        initListener();
    }


    private void initView() {
        setLeftBack();
        setTitle("交接班");
        mUserId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getUserId();
        mCompanyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        mTopCompanyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getTopCompanyId();
        mDepartmentId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
        tvCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity() != null) {
            tvDepartmentName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
        }

        workTransferDetailFinishWorkAdapter = new WorkTransferDetailFinishWorkAdapter(true);
        rvFinshWork.setLayoutManager(new LinearLayoutManager(this));
        rvFinshWork.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailFinishWorkAdapter.bindToRecyclerView(rvFinshWork);

        workTransferDetailUnFinishWorkAdapter = new WorkTransferDetailUnFinishWorkAdapter(true);
        rvUnfinishThings.setLayoutManager(new LinearLayoutManager(this));
        rvUnfinishThings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailUnFinishWorkAdapter.bindToRecyclerView(rvUnfinishThings);

        workTransferDetailFollowThingAdapter = new WorkTransferDetailFollowThingAdapter(true);
        rvFollowThings.setLayoutManager(new LinearLayoutManager(this));
        rvFollowThings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailFollowThingAdapter.bindToRecyclerView(rvFollowThings);

        workTransferDetailAttentionAdapter = new WorkTransferDetailAttentionAdapter(true);
        rvAttentionThings.setLayoutManager(new LinearLayoutManager(this));
        rvAttentionThings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailAttentionAdapter.bindToRecyclerView(rvAttentionThings);

        workTransferDetailHandItemAdapter = new WorkTransferDetailHandItemAdapter(true);
        rvHandItem.setLayoutManager(new LinearLayoutManager(this));
        rvHandItem.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailHandItemAdapter.bindToRecyclerView(rvHandItem);

        GridLayoutManager layoutManage = new GridLayoutManager(this, 5);
        rvTeam.setLayoutManager(layoutManage);

        GridLayoutManager manage = new GridLayoutManager(this, 5);
        rvGroup.setLayoutManager(manage);


        sendPersonAdapter = new SendPersonAdapter();
        sendPersonAdapter.bindToRecyclerView(rvTeam);
        sendPersonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                adapter.getData().remove(preson);
                adapter.notifyDataSetChanged();
            }
        });

        sendGroupAdapter = new SendPersonAdapter();
        sendGroupAdapter.bindToRecyclerView(rvGroup);
        sendGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                adapter.getData().remove(preson);
                adapter.notifyDataSetChanged();
            }
        });

        String targetId = getIntent().getStringExtra("targetId");
        if (!TextUtils.isEmpty(targetId)) {
            getGroupDetail(targetId);
        }
    }

    @OnClick({R.id.ll_shift, R.id.ll_receiver_person, R.id.tv_add_hand, R.id.tv_add_finish_work, R.id.tv_add_unfinish_things, R.id.tv_add_follow_things, R.id.tv_add_attention_things, R.id.tv_send, R.id.tv_send_group, R.id.rl_confirm})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            // 班次
            case R.id.ll_shift:
                doGetClass();
                break;
            // 接收人
            case R.id.ll_receiver_person:
                Intent in_receiver = new Intent(this, SelectOrganizationActivity.class);
                in_receiver.putExtra("isRadio", "isRadio");
                startActivity(in_receiver);
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
            case R.id.tv_send:
                isSend = 1;

                startActivity(new Intent(WorkTransferCreateActivity.this, SelectOAPresonActivity.class));
                break;
            case R.id.tv_send_group:
                isSend = 2;
                startActivityForResult(new Intent(WorkTransferCreateActivity.this, SelectOAGroupActivity.class), REQUEST_CODE_GROUP);
                break;
            // 提交日志
            case R.id.rl_confirm:
                doSubmit();
                break;
        }
    }

    /**
     * 获取当前的群组信息
     *
     * @param targetId
     */
    private void getGroupDetail(String targetId) {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL_RY)
                .params("ryGroupId", targetId)
                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {

                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setName(bean.getGroup().getGroupName());
                    preson.setProtraivat(bean.getGroup().getHeadPortrait());
                    preson.setId(bean.getGroup().getRcloudGroupId());
                    newGroupList.add(preson);
                    sendGroupAdapter.setNewData(newGroupList);
                }));
    }

    private void initListener() {
        rvHandItem.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("changeGoodDetail", mChangeGoodList.get(position));
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvUnfinishThings.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("unFinishDetail", (Serializable) mUnFinishList.get(position));
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvFinshWork.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("finishDetail", (Serializable) mFinishWorkList.get(position));
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvAttentionThings.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("attentionDetail", (Serializable) mAttentionList.get(position));
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvFollowThings.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("followThingDetail", (Serializable) mFollowThingList.get(position));
                JumpItent.jump(WorkTransferCreateActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        workTransferDetailFinishWorkAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_right_icon:
                        workTransferDetailFinishWorkAdapter.remove(position);
                        break;
                }
            }
        });
        workTransferDetailHandItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_right_icon:
                        workTransferDetailHandItemAdapter.remove(position);
                        break;
                }
            }
        });
        workTransferDetailFollowThingAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_right_icon:
                        workTransferDetailFollowThingAdapter.remove(position);
                        break;
                }
            }
        });
        workTransferDetailAttentionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_right_icon:
                        workTransferDetailAttentionAdapter.remove(position);
                        break;
                }
            }
        });
        workTransferDetailUnFinishWorkAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_right_icon:
                        workTransferDetailUnFinishWorkAdapter.remove(position);
                        break;
                }
            }
        });

    }

    /**
     * 获取班次
     */
    private void doGetClass() {
        new WorkTrancferCreateSelectClassListView(WorkTransferCreateActivity.this, "class", name -> {
            tvShift.setText(name);
            mClassID = GetConstDataUtils.getWorkTransferCreateClass().indexOf(name);
        }).show();
    }

    /**
     * 返回面谈对象
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {
        if (presonList.size() > 0) {
//            TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);
//            tvReceiverName.setText(bean.getName());
//            mReceiverId = bean.getUserId();
//            tvTelphone.setText(bean.getMobile());
//            mReceiverDeparrmentID = bean.getOrgCode();
            if (isSend == 1) {

                Set hashSet = new HashSet();
                hashSet.addAll(sendPersonAdapter.getData());
                hashSet.addAll(presonList);

                if (newPresonList.size() > 0) {
                    newPresonList.clear();
                }
                newPresonList.addAll(hashSet);
                sendPersonAdapter.setNewData(newPresonList);

            } else if (isSend == 0) {
//                        TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);
//
//                        etPhoneNum.setText(bean.getMobile());
//                        tvDependPerson.setText(bean.getName());
//
//                        assigneeUserId = Long.parseLong(bean.getUserId());
//                        if (bean.getOrgCode() != null && !TextUtils.isEmpty(bean.getOrgCode())) {
//                            assigneeOrgCode = bean.getOrgCode();
//                        } else {
//                            assigneeOrgCode = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
//                        }
            } else {

                Set hashSet = new HashSet();
                hashSet.addAll(sendGroupAdapter.getData());
                hashSet.addAll(presonList);

                if (newGroupList.size() > 0) {
                    newGroupList.clear();
                }
                newGroupList.addAll(hashSet);

                sendGroupAdapter.setNewData(newGroupList);
            }
        }
    }

    private void doSubmit() {
        if (!doCheckInfo())
            return;
        EanfangHttp.post(NewApiService.WORK_TRANSFER_ADD)
                .upJson(JSONObject.toJSONString(workTransferDetailBean))
                .execute(new EanfangCallback<WorkTransferDetailBean>(WorkTransferCreateActivity.this, true, WorkTransferDetailBean.class, (bean) -> {

                    //分享
                    if (newPresonList.size() == 0 && newGroupList.size() == 0) {
                        showToast("添加完毕");
                        finishSelf();
                        return;
                    }
                    if (newGroupList.size() > 0) {


                        Set hashSet = new HashSet();
                        hashSet.addAll(sendGroupAdapter.getData());
                        hashSet.addAll(sendPersonAdapter.getData());

                        if (newGroupList.size() > 0) {
                            newGroupList.clear();
                        }

                        newGroupList.addAll(hashSet);
                    } else {
                        newGroupList.addAll(newPresonList);
                    }

                    Bundle b = new Bundle();

                    b.putString("id", String.valueOf(bean.getId()));
                    b.putString("orderNum", bean.getOrderNum());

                    b.putString("creatTime", bean.getCreateTime());
                    if (newPresonList.size() > 0 && !newPresonList.get(0).getUserId().equals(EanfangApplication.get().getUserId())) {

                        b.putString("workerName", "接收人：" + newPresonList.get(0).getName());
                    } else {

                        b.putString("workerName", "创建人：" + EanfangApplication.get().getUser().getAccount().getRealName());
                    }
                    b.putString("status", "0");
                    b.putString("shareType", "6");


                    new SendContactUtils(b, handler, newGroupList, DialogUtil.createLoadingDialog(WorkTransferCreateActivity.this), "交接班").send();
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                //交接物品
                case ADD_HAND:
                    mChangeGoodList.add((WorkTransferDetailBean.ChangeGoodEntityListBean) data.getSerializableExtra("detail"));
                    if (mChangeGoodList.size() <= 0) {
                        workTransferDetailHandItemAdapter.setNewData(mChangeGoodList);
                    } else {
                        workTransferDetailHandItemAdapter.addData((WorkTransferDetailBean.ChangeGoodEntityListBean) data.getSerializableExtra("detail"));
                    }
                    break;
                //完成工作
                case ADD_FINISH_WORK:
                    mFinishWorkList.add((WorkTransferDetailBean.FinishWorkEntityListBean) data.getSerializableExtra("detail"));
                    if (mFinishWorkList.size() <= 0) {
                        workTransferDetailFinishWorkAdapter.setNewData(mFinishWorkList);
                    } else {
                        workTransferDetailFinishWorkAdapter.addData((WorkTransferDetailBean.FinishWorkEntityListBean) data.getSerializableExtra("detail"));
                    }
                    break;
                //未处理事项
                case ADD_UNFINISH_THINGS:
                    mUnFinishList.add((WorkTransferDetailBean.NotDidEntityListBean) data.getSerializableExtra("detail"));
                    if (mUnFinishList.size() <= 0) {
                        workTransferDetailUnFinishWorkAdapter.setNewData(mUnFinishList);
                    } else {
                        workTransferDetailUnFinishWorkAdapter.addData((WorkTransferDetailBean.NotDidEntityListBean) data.getSerializableExtra("detail"));
                    }
                    break;
                //跟进处理事项
                case ADD_FLOW_THINGS:
                    mFollowThingList.add((WorkTransferDetailBean.FollowUpEntityListBean) data.getSerializableExtra("detail"));
                    if (mFollowThingList.size() <= 0) {
                        workTransferDetailFollowThingAdapter.setNewData(mFollowThingList);
                    } else {
                        workTransferDetailFollowThingAdapter.addData((WorkTransferDetailBean.FollowUpEntityListBean) data.getSerializableExtra("detail"));
                    }
                    break;
                //注意事项
                case ADD_ATTENTION_THINGS:
                    mAttentionList.add((WorkTransferDetailBean.NoticeEntityListBean) data.getSerializableExtra("detail"));
                    if (mAttentionList.size() <= 0) {
                        workTransferDetailAttentionAdapter.setNewData(mAttentionList);
                    } else {
                        workTransferDetailAttentionAdapter.addData((WorkTransferDetailBean.NoticeEntityListBean) data.getSerializableExtra("detail"));
                    }
                    break;
                //添加群组
                case REQUEST_CODE_GROUP:
                    TemplateBean.Preson preson = (TemplateBean.Preson) data.getSerializableExtra("bean");
                    if (sendGroupAdapter.getData().size() > 0) {
                        if (!sendGroupAdapter.getData().contains(preson)) {
                            sendGroupAdapter.addData(preson);
                            newGroupList.add(preson);
                        }
                    } else {

                        sendGroupAdapter.addData(preson);
                        newGroupList.add(preson);
                    }
                    break;
            }
        }
    }

    public boolean doCheckInfo() {
        try {
            if (StringUtils.isEmpty(mUserId + "")) {
                showToast("用户id为空");
                return false;
            } else {
                workTransferDetailBean.setOwnerUserId(mUserId + "");
            }

            if (StringUtils.isEmpty(mCompanyId + "")) {
                showToast("公司id为空");
                return false;
            } else {
                // 创建人公司ID
                workTransferDetailBean.setOwnerCompanyId(mCompanyId + "");
                // 创建人顶级公司Id
                workTransferDetailBean.setOwnerTopCompanyId(mTopCompanyId + "");
            }
            if (StringUtils.isEmpty(mCompanyId + "")) {
                showToast("部门id为空");
                return false;
            } else {
                // 创建人部门编码
                workTransferDetailBean.setOwnerOrgCode(mDepartmentId);
            }
//            if (StringUtils.isEmpty(tvReceiverName.getText().toString().trim()) && StringUtils.isEmpty(mReceiverId)) {
//                showToast("请选择接收人");
//                return false;
//            } else {
            // 接收人id
//            workTransferDetailBean.setAssigneeUserId(mReceiverId + "");
//            //接收人所在公司id
//            workTransferDetailBean.setAssigneeCompanyId(mCompanyId + "");
//            // 接收人顶级公司id
//            workTransferDetailBean.setAssigneeTopCompanyId(mTopCompanyId + "");
//            // 接收人部门编码
//            workTransferDetailBean.setAssigneeOrgCode(mReceiverDeparrmentID);


            if (newPresonList.size() == 0) {
                //工作协同默认值
                workTransferDetailBean.setAssigneeUserId(String.valueOf(EanfangApplication.get().getUserId()));
                workTransferDetailBean.setAssigneeOrgCode(EanfangApplication.get().getOrgCode());
                workTransferDetailBean.setAssigneeCompanyId(String.valueOf(EanfangApplication.getApplication().getCompanyId()));
                workTransferDetailBean.setAssigneeTopCompanyId(String.valueOf(EanfangApplication.getApplication().getTopCompanyId()));
            } else {
                //工作协同默认值
                workTransferDetailBean.setAssigneeUserId(newPresonList.get(0).getUserId());
                workTransferDetailBean.setAssigneeOrgCode(newPresonList.get(0).getOrgCode());
                workTransferDetailBean.setAssigneeCompanyId(String.valueOf(EanfangApplication.getApplication().getCompanyId()));
                workTransferDetailBean.setAssigneeTopCompanyId(String.valueOf(EanfangApplication.getApplication().getTopCompanyId()));
            }
//            }
            if (mClassID == 100) {
                showToast("请选择班次");
                return false;
            } else {
                workTransferDetailBean.setWorkClasses(mClassID);
            }
            if (mChangeGoodList.size() != 0 && mChangeGoodList != null) {
                workTransferDetailBean.setChangeGoodEntityList(mChangeGoodList);
            }
            if (mFinishWorkList.size() != 0 && mFinishWorkList != null) {
                workTransferDetailBean.setFinishWorkEntityList(mFinishWorkList);
            }
            if (mUnFinishList.size() != 0 && mUnFinishList != null) {
                workTransferDetailBean.setNotDidEntityList(mUnFinishList);
            }
            if (mFollowThingList.size() != 0 && mFollowThingList != null) {
                workTransferDetailBean.setFollowUpEntityList(mFollowThingList);
            }
            if (mAttentionList.size() != 0 && mAttentionList != null) {
                workTransferDetailBean.setNoticeEntityList(mAttentionList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }


}
