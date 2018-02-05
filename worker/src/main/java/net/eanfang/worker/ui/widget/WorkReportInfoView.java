package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkReportInfoBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.LookReportDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  14:21
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkReportInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_department_name)
    TextView etDepartmentName;
    @BindView(R.id.tv_report_type)
    TextView tvReportType;
    @BindView(R.id.tv_report_commit_time)
    TextView tvReportCommitTime;
    @BindView(R.id.tv_report_commit_person)
    TextView tvReportCommitPerson;
    @BindView(R.id.tv_report_rev_person)
    TextView tvReportRevPerson;
    @BindView(R.id.tv_report_phone_number)
    TextView tvReportPhoneNumber;
    @BindView(R.id.complete_list)
    RecyclerView reportCompleteList;
    @BindView(R.id.find_list)
    RecyclerView reportFindList;
    @BindView(R.id.plan_list)
    RecyclerView reportPlanList;
    private Activity mContext;
    private Long id;
    private List<WorkReportInfoBean.WorkReportDetailsBean> completeList;
    private List<WorkReportInfoBean.WorkReportDetailsBean> findList;
    private List<WorkReportInfoBean.WorkReportDetailsBean> planList;
    private LookReportDetailAdapter completeAdapter;
    private LookReportDetailAdapter findAdapter;
    private LookReportDetailAdapter planAdapter;
    private WorkReportInfoBean.WorkReportDetailsBean detailsBean;

    public WorkReportInfoView(Activity context, boolean isfull, Long id) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_work_report_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("汇报详情");
        getData();
    }


    private void initAdapter() {
        completeAdapter = new LookReportDetailAdapter(R.layout.item_quotation_detail, completeList);
        reportCompleteList.setLayoutManager(new LinearLayoutManager(mContext));
        reportCompleteList.setAdapter(completeAdapter);

        findAdapter = new LookReportDetailAdapter(R.layout.item_quotation_detail, findList);
        reportFindList.setLayoutManager(new LinearLayoutManager(mContext));
        reportFindList.setAdapter(findAdapter);

        planAdapter = new LookReportDetailAdapter(R.layout.item_quotation_detail, planList);
        reportPlanList.setLayoutManager(new LinearLayoutManager(mContext));
        reportPlanList.setAdapter(planAdapter);
    }

    private void getData() {
        EanfangHttp.get(NewApiService.GET_WORK_REPORT_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkReportInfoBean>(mContext, true, WorkReportInfoBean.class, (bean) -> {
                            completeList = new ArrayList<>();
                            findList = new ArrayList<>();
                            planList = new ArrayList<>();

                            etCompanyName.setText(bean.getCreateCompany().getOrgName());
                            etDepartmentName.setText(bean.getCreateOrg().getOrgName());
                            tvReportType.setText(GetConstDataUtils.getWorkReportTypeList().get(bean.getType()));
                            tvReportCommitTime.setText(bean.getCreateTime());
                            tvReportCommitPerson.setText(bean.getCreateUser().getAccountEntity().getRealName());
                            tvReportRevPerson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
                            tvReportPhoneNumber.setText(bean.getAssigneeUser().getAccountEntity().getMobile());
                            for (int i = 0; i < bean.getWorkReportDetails().size(); i++) {
                                WorkReportInfoBean.WorkReportDetailsBean detialBean = bean.getWorkReportDetails().get(i);
                                if (EanfangConst.TYPE_REPORT_DETAIL_FINISH == detialBean.getType()) {
                                    completeList.add(detialBean);
                                } else if (EanfangConst.TYPE_REPORT_DETAIL_FIND == detialBean.getType()) {
                                    findList.add(detialBean);
                                } else if (EanfangConst.TYPE_REPORT_DETAIL_PLAN == detialBean.getType()) {
                                    planList.add(detialBean);
                                }
                            }
                            initAdapter();

                            reportCompleteList.addOnItemTouchListener(new OnItemClickListener() {
                                @Override
                                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    detailsBean = completeList.get(position);
                                    new LookReportCompleteInfoView(mContext, true, detailsBean).show();
                                }
                            });
                            reportFindList.addOnItemTouchListener(new OnItemClickListener() {
                                @Override
                                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    detailsBean = findList.get(position);
                                    new LookReportFindInfoView(mContext, true, detailsBean).show();
                                }
                            });
                            reportPlanList.addOnItemTouchListener(new OnItemClickListener() {
                                @Override
                                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    detailsBean = planList.get(position);
                                    new LookReportPlanInfoView(mContext, true, detailsBean).show();
                                }
                            });
                        })
//                {
//
//                    @Override
//                    public void onSuccess(final WorkReportInfoBean bean) {
//                        completeList = new ArrayList<>();
//                        findList = new ArrayList<>();
//                        planList = new ArrayList<>();
//
//                        etCompanyName.setText(bean.getCreateCompany().getOrgName());
//                        etDepartmentName.setText(bean.getCreateOrg().getOrgName());
//                        tvReportType.setText(bean.getType());
//                        tvReportCommitTime.setText(bean.getCreateTime());
//                        tvReportCommitPerson.setText(bean.getCreateUser().getAccountEntity().getRealName());
//                        tvReportRevPerson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
//                        tvReportPhoneNumber.setText(bean.getAssigneeUser().getAccountEntity().getMobile());
//                        for (int i = 0; i < bean.getWorkReportDetails().size(); i++) {
//                            WorkReportInfoBean.WorkReportDetailsBean detialBean = bean.getWorkReportDetails().get(i);
//                            if (EanfangConst.TYPE_REPORT_DETAIL_FINISH==detialBean.getType()) {
//                                completeList.add(detialBean);
//                            } else if (EanfangConst.TYPE_REPORT_DETAIL_FIND==detialBean.getType()){
//                                findList.add(detialBean);
//                            } else if (EanfangConst.TYPE_REPORT_DETAIL_PLAN==detialBean.getType()) {
//                                planList.add(detialBean);
//                            }
//                        }
//                        initAdapter();
//
//                        reportCompleteList.addOnItemTouchListener(new OnItemClickListener() {
//                            @Override
//                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                detailsBean = completeList.get(position);
//                                new LookReportCompleteInfoView(mContext, true, detailsBean).show();
//                            }
//                        });
//                        reportFindList.addOnItemTouchListener(new OnItemClickListener() {
//                            @Override
//                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                detailsBean = findList.get(position);
//                                new LookReportFindInfoView(mContext, true, detailsBean).show();
//                            }
//                        });
//                        reportPlanList.addOnItemTouchListener(new OnItemClickListener() {
//                            @Override
//                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                detailsBean = planList.get(position);
//                                new LookReportPlanInfoView(mContext, true, detailsBean).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(String message) {
//
//                    }
//                }
                );
    }
}
