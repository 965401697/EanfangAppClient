package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.WorkAddReportBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.AddReportDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.CompleteWorkView;
import net.eanfang.client.ui.widget.FindTroubleView;
import net.eanfang.client.ui.widget.WorkPlanView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:54
 * @email houzhongzhou@yeah.net
 * @desc 工作汇报
 */

public class ReportActivity extends BaseClientActivity implements View.OnClickListener {

    @BindView(R.id.et_task_name)
    TextView etTaskName;
    @BindView(R.id.btn_add_complete)
    TextView btnAddComplete;
    @BindView(R.id.report_complete_list)
    RecyclerView reportCompleteList;
    @BindView(R.id.btn_add_find)
    TextView btnAddFind;
    @BindView(R.id.report_find_list)
    RecyclerView reportFindList;
    @BindView(R.id.btn_add_plan)
    TextView btnAddPlan;
    @BindView(R.id.report_plan_list)
    RecyclerView reportPlanList;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    @BindView(R.id.ll_comit)
    Button llComit;
    @BindView(R.id.ll_report_type)
    LinearLayout llReportType;
    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_department_name)
    TextView etDepartmentName;

    private OptionsPickerView pvOptions_NoLink;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private int posistion;
    private WorkAddReportBean bean = new WorkAddReportBean();
    private List<WorkAddReportBean.WorkReportDetailsBean> beanList = new ArrayList<>();
    private List<WorkAddReportBean.WorkReportDetailsBean> findList = new ArrayList<>();
    private List<WorkAddReportBean.WorkReportDetailsBean> planList = new ArrayList<>();
    private WorkAddReportBean.WorkReportDetailsBean detailsBean;
    private ArrayList<String> typeList = new ArrayList<>();
    private AddReportDetailAdapter addReportDetialAdapter;
    private AddReportDetailAdapter findAdapter;
    private AddReportDetailAdapter planAdapter;
    private Long assigneeUserId;
    private String assigneeOrgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("新建汇报");
        setLeftBack();

        btnAddComplete.setOnClickListener(this);
        btnAddFind.setOnClickListener(this);
        btnAddPlan.setOnClickListener(this);
        llComit.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llReportType.setOnClickListener(this);
//        etCompanyName.setText(EanfangApplication.get().getUser().getCompanyName());

        addReportDetialAdapter = new AddReportDetailAdapter(R.layout.item_quotation_detail, beanList);
        reportCompleteList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        reportCompleteList.setLayoutManager(new LinearLayoutManager(this));
        reportCompleteList.setAdapter(addReportDetialAdapter);
        addReportDetialAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    addReportDetialAdapter.remove(position);
                }
            }
        });

        findAdapter = new AddReportDetailAdapter(R.layout.item_quotation_detail, findList);
        reportFindList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        reportFindList.setLayoutManager(new LinearLayoutManager(this));
        reportFindList.setAdapter(findAdapter);
        findAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    findAdapter.remove(position);
                }
            }
        });

        planAdapter = new AddReportDetailAdapter(R.layout.item_quotation_detail, planList);
        reportPlanList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        reportPlanList.setLayoutManager(new LinearLayoutManager(this));
        reportPlanList.setAdapter(planAdapter);
        planAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    planAdapter.remove(position);
                }
            }
        });

        etCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        etDepartmentName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
        getData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_complete://完成工作
                Intent intent = new Intent(ReportActivity.this, AddReportCompleteActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_add_find://发现问题
                intent = new Intent(ReportActivity.this, AddReportFindActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.btn_add_plan://后续计划
                intent = new Intent(ReportActivity.this, AddReportPlanActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.ll_depend_person://联系人
                showDependPerson();
                break;
            case R.id.ll_report_type://类型
                PickerSelectUtil.singleTextPicker(this, "", etTaskName, GetConstDataUtils.getWorkReportTypeList());
                break;

            case R.id.ll_comit://提交
                submit();
                break;
            default:
                break;
        }

    }


    private void submit() {
        //类型
        String task_title = etTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(task_title)) {
            showToast("请选择类型");
            return;
        }
        String connectName = tvDependPerson.getText().toString().trim();
        if (TextUtils.isEmpty(connectName)) {
            showToast("请选择联系人");
            return;
        }
        bean.setType(GetConstDataUtils.getWorkReportTypeList().indexOf(task_title));
        bean.setAssigneeUserId(assigneeUserId);
        bean.setAssigneeOrgCode(assigneeOrgCode);
        beanList.addAll(findList);
        beanList.addAll(planList);
        bean.setWorkReportDetails(beanList);

        doHttp(JSON.toJSONString(bean));
    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_REPORT)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(ReportActivity.this, StateChangeActivity.class);
                        Bundle bundle = new Bundle();
                        Message message = new Message();
                        message.setTitle("汇报发送成功");
                        message.setMsgTitle("您的工作汇报已发送成功");
                        message.setMsgContent("您可以随时通过我的汇报查看");
                        message.setShowOkBtn(true);
                        message.setShowLogo(true);
                        message.setTip("");
                        bundle.putSerializable("message", message);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finishSelf();
                    });
                }));

    }

    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(ReportActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }

    /**
     * 责任人
     */
    private void showDependPerson() {
        if (userlist == null || userlist.isEmpty()) {
            showToast("暂无其他员工可选");
            return;
        }
        pvOptions_NoLink = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                posistion = options1;
                etPhoneNum.setText(userlist.get(posistion).getAccountEntity().getMobile());
                tvDependPerson.setText(userlist.get(posistion).getAccountEntity().getRealName());
                assigneeUserId = userlist.get(posistion).getUserId();
                assigneeOrgCode = userlist.get(posistion).getDepartmentEntity().getOrgCode();

            }
        }).build();
        pvOptions_NoLink.setPicker(userNameList);
        pvOptions_NoLink.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }
        WorkAddReportBean.WorkReportDetailsBean resultBean = (WorkAddReportBean.WorkReportDetailsBean) data.getSerializableExtra("result");
        if (EanfangConst.TYPE_REPORT_DETAIL_FINISH == resultBean.getType()) {
            beanList.add(resultBean);
            addReportDetialAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    detailsBean = beanList.get(position);
                    new CompleteWorkView(ReportActivity.this, true, detailsBean).show();
                }
            });
            addReportDetialAdapter.notifyDataSetChanged();
        } else if (EanfangConst.TYPE_REPORT_DETAIL_FIND == resultBean.getType()) {
            findList.add(resultBean);
            findAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    detailsBean = beanList.get(position);
                    new FindTroubleView(ReportActivity.this, true, detailsBean).show();
                }
            });
            findAdapter.notifyDataSetChanged();
        } else if (EanfangConst.TYPE_REPORT_DETAIL_PLAN == resultBean.getType()) {
            planList.add(resultBean);
            planAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    detailsBean = beanList.get(position);
                    new WorkPlanView(ReportActivity.this, true, detailsBean).show();
                }
            });
            planAdapter.notifyDataSetChanged();
        }


    }
}
