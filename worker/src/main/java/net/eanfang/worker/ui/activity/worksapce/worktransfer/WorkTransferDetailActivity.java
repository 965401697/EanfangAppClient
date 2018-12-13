package net.eanfang.worker.ui.activity.worksapce.worktransfer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTransferDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferDetailAttentionAdapter;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferDetailFinishWorkAdapter;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferDetailFollowThingAdapter;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferDetailHandItemAdapter;
import net.eanfang.worker.ui.adapter.worktransfer.WorkTransferDetailUnFinishWorkAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date on 2018/7/27  16:11
 * @decision 交接班详情
 */
public class WorkTransferDetailActivity extends BaseActivity {

    @BindView(R.id.iv_report_header)
    SimpleDraweeView ivReportHeader;
    @BindView(R.id.tv_talker_name)
    TextView tvTalkerName;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @BindView(R.id.tv_work_classes)
    TextView tvWorkClasses;
    @BindView(R.id.tv_accept_preson)
    TextView tvAcceptPreson;
    @BindView(R.id.tv_accept_phone)
    TextView tvAcceptPhone;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;

    /**
     * itemid
     */
    private String mItemId = "";
    /**
     * userid
     */
//    private String mUserId = "";
    /**
     * 订单status
     */
    private int mStatus = 100;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setLeftBack();
        setTitle("日志详情");
        mItemId = getIntent().getStringExtra("itemId");
        mStatus = getIntent().getIntExtra("status", 100);
//        mUserId = getIntent().getStringExtra("userId");

        workTransferDetailFinishWorkAdapter = new WorkTransferDetailFinishWorkAdapter(false);
        rvFinshWork.setLayoutManager(new LinearLayoutManager(this));
        rvFinshWork.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailFinishWorkAdapter.bindToRecyclerView(rvFinshWork);

        workTransferDetailUnFinishWorkAdapter = new WorkTransferDetailUnFinishWorkAdapter(false);
        rvUnfinishThings.setLayoutManager(new LinearLayoutManager(this));
        rvUnfinishThings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailUnFinishWorkAdapter.bindToRecyclerView(rvUnfinishThings);

        workTransferDetailFollowThingAdapter = new WorkTransferDetailFollowThingAdapter(false);
        rvFollowThings.setLayoutManager(new LinearLayoutManager(this));
        rvFollowThings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailFollowThingAdapter.bindToRecyclerView(rvFollowThings);

        workTransferDetailAttentionAdapter = new WorkTransferDetailAttentionAdapter(false);
        rvAttentionThings.setLayoutManager(new LinearLayoutManager(this));
        rvAttentionThings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailAttentionAdapter.bindToRecyclerView(rvAttentionThings);

        workTransferDetailHandItemAdapter = new WorkTransferDetailHandItemAdapter(false);
        rvHandItem.setLayoutManager(new LinearLayoutManager(this));
        rvHandItem.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTransferDetailHandItemAdapter.bindToRecyclerView(rvHandItem);


    }

    private void initData() {
        EanfangHttp.post(NewApiService.WORK_TRANSFER_DETAIL + mItemId)
                .execute(new EanfangCallback<WorkTransferDetailBean>(WorkTransferDetailActivity.this, true, WorkTransferDetailBean.class, bean -> {
                    initContent(bean);
                }));
    }

    private void initContent(WorkTransferDetailBean bean) {
        ivReportHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getOwnerUserEntity().getAccountEntity().getAvatar()));
        // 创建人
        tvTalkerName.setText(bean.getOwnerUserEntity().getAccountEntity().getRealName() + "(汇报人)");
        // 部门
        tvDepartment.setText(bean.getOwnerDepartmentEntity().getOrgName());
        //时间
        String[] dataOne = bean.getCreateTime().split("-");
        String[] dateTwo = dataOne[2].split(" ");
        tvYear.setText(dataOne[0] + "-" + dataOne[1]);
        tvWeek.setText(GetDateUtils.dateToWeek(dataOne[0] + "-" + dataOne[1] + "-" + dateTwo[0]));
        tvData.setText(dateTwo[0]);
        //单位名称
        tvCompanyName.setText(bean.getOwnerCompanyEntity().getOrgName());
        // 单位电话
        tvCompanyPhone.setText(bean.getOwnerUserEntity().getAccountEntity().getMobile());
        //班次
        tvWorkClasses.setText(GetConstDataUtils.getWorkTransferCreateClass().get(bean.getWorkClasses()));
        // 接收人
        tvAcceptPreson.setText(bean.getAssigneeUserEntity().getAccountEntity().getRealName());
        //接收人电话
        tvAcceptPhone.setText(bean.getAssigneeUserEntity().getAccountEntity().getMobile());
        mChangeGoodList = bean.getChangeGoodEntityList();
        mUnFinishList = bean.getNotDidEntityList();
        mAttentionList = bean.getNoticeEntityList();
        mFollowThingList = bean.getFollowUpEntityList();
        mFinishWorkList = bean.getFinishWorkEntityList();
        workTransferDetailHandItemAdapter.setNewData(mChangeGoodList);
        workTransferDetailUnFinishWorkAdapter.setNewData(mUnFinishList);
        workTransferDetailAttentionAdapter.setNewData(mAttentionList);
        workTransferDetailFollowThingAdapter.setNewData(mFollowThingList);
        workTransferDetailFinishWorkAdapter.setNewData(mFinishWorkList);


        if (bean.getAssigneeUserEntity().getUserId().equals(String.valueOf(EanfangApplication.get().getUserId()))) {
            if (mStatus == 0) {
                rlConfirm.setVisibility(View.VISIBLE);
            } else {
                rlConfirm.setVisibility(View.GONE);
            }
        } else {
            rlConfirm.setVisibility(View.GONE);
        }

    }

    private void initListener() {
        rvHandItem.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("changeGoodDetail", (Serializable) mChangeGoodList.get(position));
                JumpItent.jump(WorkTransferDetailActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvUnfinishThings.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("unFinishDetail", (Serializable) mUnFinishList.get(position));
                JumpItent.jump(WorkTransferDetailActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvFinshWork.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("finishDetail", (Serializable) mFinishWorkList.get(position));
                JumpItent.jump(WorkTransferDetailActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvAttentionThings.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("attentionDetail", (Serializable) mAttentionList.get(position));
                JumpItent.jump(WorkTransferDetailActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rvFollowThings.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("followThingDetail", (Serializable) mFollowThingList.get(position));
                JumpItent.jump(WorkTransferDetailActivity.this, WorkTransferCreateDetailActivity.class, bundle);
            }
        });
        rlConfirm.setOnClickListener(view -> {
            doTransferConfim();
        });
    }

    /**
     * 确认交接
     */
    private void doTransferConfim() {
        EanfangHttp.post(NewApiService.WORK_TRANSFER_CONFIM + mItemId)
                .execute(new EanfangCallback<WorkTransferDetailBean>(WorkTransferDetailActivity.this, true, WorkTransferDetailBean.class, bean -> {
                    setResult(RESULT_OK);
                    finishSelf();
                }));

    }
}

