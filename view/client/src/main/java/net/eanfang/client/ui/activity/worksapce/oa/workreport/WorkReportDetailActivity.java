package net.eanfang.client.ui.activity.worksapce.oa.workreport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WorkReportInfoBean;
import com.eanfang.util.DateKit;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;


import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;

/**
 * 工资汇报详情
 */
public class WorkReportDetailActivity extends BaseClientActivity {

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_section)
    TextView tvSection;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_work)
    TextView tvWork;
    @BindView(R.id.rv_work_list)
    RecyclerView rvWorkList;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.rv_question_list)
    RecyclerView rvQuestionList;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.rv_plan_list)
    RecyclerView rvPlanList;

    private List<WorkReportInfoBean.WorkReportDetailsBean> completeList;
    private List<WorkReportInfoBean.WorkReportDetailsBean> findList;
    private List<WorkReportInfoBean.WorkReportDetailsBean> planList;

    private long mId;
    private WorKReportDetailAdapter completeAdapter;
    private FindQuestionDetailAdapter findAdapter;
    private PlanDetailAdapter planAdapter;

    private WorkReportInfoBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_work_report_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        setRightTitle("分享");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享聊天
                if (mBean != null) {
                    Intent intent = new Intent(WorkReportDetailActivity.this, SelectIMContactActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", String.valueOf(mBean.getId()));
                    bundle.putString("orderNum", mBean.getCreateOrg().getOrgName());
                    if (mBean.getWorkReportDetails() != null && !TextUtils.isEmpty(mBean.getWorkReportDetails().get(0).getPictures())) {
                        bundle.putString("picUrl", mBean.getWorkReportDetails().get(0).getPictures().split(",")[0]);
                    }
                    bundle.putString("creatTime", String.valueOf(mBean.getType()));
                    bundle.putString("creatReleaseTime", mBean.getCreateTime());
                    bundle.putString("workerName", mBean.getCreateUser().getAccountEntity().getRealName());
                    bundle.putString("status", String.valueOf(mBean.getStatus()));
                    bundle.putString("shareType", "3");

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        mId = getIntent().getLongExtra("id", 0);
        getData();
    }

    private void initAdapter() {
        completeAdapter = new WorKReportDetailAdapter(completeList, this);
        rvWorkList.setLayoutManager(new LinearLayoutManager(this));
        rvWorkList.setAdapter(completeAdapter);

        findAdapter = new FindQuestionDetailAdapter(findList, this);
        rvQuestionList.setLayoutManager(new LinearLayoutManager(this));
        rvQuestionList.setAdapter(findAdapter);

        planAdapter = new PlanDetailAdapter(planList, this);
        rvPlanList.setLayoutManager(new LinearLayoutManager(this));
        rvPlanList.setAdapter(planAdapter);

        setAdapterChildListener(completeAdapter);
        setAdapterChildListener(findAdapter);
        setAdapterChildListener(planAdapter);

        rvWorkList.setNestedScrollingEnabled(false);
        rvQuestionList.setNestedScrollingEnabled(false);
        rvPlanList.setNestedScrollingEnabled(false);
        rvWorkList.setFocusable(false);
        rvQuestionList.setFocusable(false);
        rvPlanList.setFocusable(false);
    }

    private void setAdapterChildListener(BaseQuickAdapter adapter) {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                WorkReportInfoBean.WorkReportDetailsBean b = (WorkReportInfoBean.WorkReportDetailsBean) adapter.getData().get(position);
                if (view.getId() == R.id.rl_show) {
                    b.setItemType(2);
                    adapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.iv_pack) {
                    b.setItemType(1);
                    adapter.notifyItemChanged(position);
                }
            }
        });

    }

    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_REPORT_INFO)
                .tag(this)
                .params("id", mId)
                .execute(new EanfangCallback<WorkReportInfoBean>(this, true, WorkReportInfoBean.class, (bean) -> {
                            mBean = bean;

                            setTitle(bean.getCreateUser().getAccountEntity().getRealName() + "的" + GetConstDataUtils.getWorkReportTypeList().get(bean.getType()));

                            completeList = new ArrayList<>();
                            findList = new ArrayList<>();
                            planList = new ArrayList<>();
                            //头像
                            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bean.getCreateUser().getAccountEntity().getAvatar()), ivHeader);
                            tvCompany.setText(bean.getCreateCompany().getOrgName());
                            tvSection.setText(bean.getCreateOrg().getOrgName());
                            tvType.setText(GetConstDataUtils.getWorkReportTypeList().get(bean.getType()));
                            tvDate.setText(bean.getCreateTime());
                            tvName.setText(bean.getCreateUser().getAccountEntity().getRealName());
                            tvWeek.setText(DateKit.get(bean.getCreateTime()).weekName());
//                            tvReportRevPerson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
//                            tvReportPhoneNumber.setText(bean.getAssigneeUser().getAccountEntity().getMobile());
                            for (int i = 0; i < bean.getWorkReportDetails().size(); i++) {
                                WorkReportInfoBean.WorkReportDetailsBean detialBean = bean.getWorkReportDetails().get(i);
                                detialBean.setItemType(1);
                                if (EanfangConst.TYPE_REPORT_DETAIL_FINISH == detialBean.getType()) {
                                    completeList.add(detialBean);
                                } else if (EanfangConst.TYPE_REPORT_DETAIL_FIND == detialBean.getType()) {
                                    findList.add(detialBean);
                                } else if (EanfangConst.TYPE_REPORT_DETAIL_PLAN == detialBean.getType()) {
                                    planList.add(detialBean);
                                }
                            }
                            if (completeList.size() == 0) {
                                tvWork.setVisibility(View.VISIBLE);
                            }
                            if (findList.size() == 0) {
                                tvQuestion.setVisibility(View.VISIBLE);
                            }
                            if (planList.size() == 0) {
                                tvPlan.setVisibility(View.VISIBLE);
                            }
                            initAdapter();
                        })
                );
    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finishSelf();
        }
        return false;
    }
}
