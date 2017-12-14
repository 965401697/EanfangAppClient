package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.LookReportDetailAdapter;
import net.eanfang.client.ui.model.WorkReportInfoBean;

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
    private Activity mContext;
    private int id;
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

    private List<WorkReportInfoBean.BeanBean.DetailsBean> completeList;
    private List<WorkReportInfoBean.BeanBean.DetailsBean> findList;
    private List<WorkReportInfoBean.BeanBean.DetailsBean> planList;
    private LookReportDetailAdapter completeAdapter;
    private LookReportDetailAdapter findAdapter;
    private LookReportDetailAdapter planAdapter;
    private WorkReportInfoBean.BeanBean.DetailsBean detailsBean;

    public WorkReportInfoView(Activity context, boolean isfull, int id) {
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
        reportCompleteList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        reportCompleteList.setLayoutManager(new LinearLayoutManager(mContext));
        reportCompleteList.setAdapter(completeAdapter);

        findAdapter = new LookReportDetailAdapter(R.layout.item_quotation_detail, findList);
        reportFindList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        reportFindList.setLayoutManager(new LinearLayoutManager(mContext));
        reportFindList.setAdapter(findAdapter);

        planAdapter = new LookReportDetailAdapter(R.layout.item_quotation_detail, planList);
        reportPlanList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        reportPlanList.setLayoutManager(new LinearLayoutManager(mContext));
        reportPlanList.setAdapter(planAdapter);
    }

    private void getData() {
        EanfangHttp.get(ApiService.GET_WORK_REPORT_INFO)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<WorkReportInfoBean>(mContext, true) {

                    @Override
                    public void onSuccess(final WorkReportInfoBean bean) {
                        completeList = new ArrayList<>();
                        findList = new ArrayList<>();
                        planList = new ArrayList<>();

                        etCompanyName.setText(bean.getBean().getCompanyName());
                        etDepartmentName.setText(bean.getBean().getDepartmentName());
                        tvReportType.setText(bean.getBean().getType());
                        tvReportCommitTime.setText(bean.getBean().getCreateDate());
                        tvReportCommitPerson.setText(bean.getBean().getCreateUserName());
                        tvReportRevPerson.setText(bean.getBean().getReceiveUserName());
                        tvReportPhoneNumber.setText(bean.getBean().getReceivePhone());
//                        for (int i = 0; i < bean.getBean().getDetails().size(); i++) {
//                            WorkReportInfoBean.BeanBean.DetailsBean detialBean = bean.getBean().getDetails().get(i);
//                            if (EanfangConst.TYPE_REPORT_DETAIL_FINISH.equals(detialBean.getType())) {
//                                completeList.add(detialBean);
//                            } else if (EanfangConst.TYPE_REPORT_DETAIL_FIND.equals(detialBean.getType())) {
//                                findList.add(detialBean);
//                            } else if (EanfangConst.TYPE_REPORT_DETAIL_PLAN.equals(detialBean.getType())) {
//                                planList.add(detialBean);
//                            }
//                        }
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
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }
}
