package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.AddReportDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.CompanyStaffBean;
import net.eanfang.client.ui.model.Message;
import net.eanfang.client.ui.model.WorkAddReportBean;
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

public class ReportActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_department_name)
    EditText etDepartmentName;
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

    private OptionsPickerView pvOptions_NoLink;
    private List<String> userNameList = new ArrayList<>();
    private CompanyStaffBean staffBean;
    private List<CompanyStaffBean.AllBean> userlist = new ArrayList<>();
    private int posistion;
    private WorkAddReportBean bean = new WorkAddReportBean();
    private List<WorkAddReportBean.DetailsBean> beanList = new ArrayList<>();
    private List<WorkAddReportBean.DetailsBean> findList = new ArrayList<>();
    private List<WorkAddReportBean.DetailsBean> planList = new ArrayList<>();
    private WorkAddReportBean.DetailsBean detailsBean;
    private ArrayList<String> typeList = new ArrayList<>();
    private AddReportDetailAdapter addReportDetialAdapter;
    private AddReportDetailAdapter findAdapter;
    private AddReportDetailAdapter planAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("工作汇报");
        setLeftBack();

        btnAddComplete.setOnClickListener(this);
        btnAddFind.setOnClickListener(this);
        btnAddPlan.setOnClickListener(this);
        llComit.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llReportType.setOnClickListener(this);
        etCompanyName.setText(EanfangApplication.get().getUser().getCompanyName());

        addReportDetialAdapter = new AddReportDetailAdapter(R.layout.item_quotation_detail, beanList);
        reportCompleteList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        reportCompleteList.setLayoutManager(new LinearLayoutManager(this));
        reportCompleteList.setAdapter(addReportDetialAdapter);

        findAdapter = new AddReportDetailAdapter(R.layout.item_quotation_detail, findList);
        reportFindList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        reportFindList.setLayoutManager(new LinearLayoutManager(this));
        reportFindList.setAdapter(findAdapter);

        planAdapter = new AddReportDetailAdapter(R.layout.item_quotation_detail, planList);
        reportPlanList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        reportPlanList.setLayoutManager(new LinearLayoutManager(this));
        reportPlanList.setAdapter(planAdapter);

        getData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_complete://完成工作
                Intent intent = new Intent(ReportActivity.this, AddReportCompleteActivity.class);
                startActivityForResult(intent, 10081);
                break;
            case R.id.btn_add_find://发现问题
                intent = new Intent(ReportActivity.this, AddReportFindActivity.class);
                startActivityForResult(intent, 10082);
                break;
            case R.id.btn_add_plan://后续计划
                intent = new Intent(ReportActivity.this, AddReportPlanActivity.class);
                startActivityForResult(intent, 10083);
                break;
            case R.id.ll_depend_person://联系人
                showDependPerson();
                break;
            case R.id.ll_report_type://类型
                initType();
                showType();
                break;

            case R.id.ll_comit://提交
                submit();
                break;
            default:
                break;
        }

    }

    /**
     * 责任人
     */
    private void showDependPerson() {
        if (userlist == null || userlist.isEmpty()) {
            showToast("暂无其他员工可选");
            return;
        }
        pvOptions_NoLink = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                posistion = options1;
                etPhoneNum.setText(userlist.get(posistion).getPhone());
                tvDependPerson.setText(userlist.get(posistion).getName());

            }
        }).build();
        pvOptions_NoLink.setPicker(userNameList);
        pvOptions_NoLink.show();
    }

    /**
     * 类型
     */
    private void showType() {
        pvOptions_NoLink = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                etTaskName.setText(typeList.get(options1));
            }
        }).build();
        pvOptions_NoLink.setPicker(typeList);
        pvOptions_NoLink.show();
    }

    private void initType() {
        typeList.clear();
        typeList.add("日报");
        typeList.add("周报");
        typeList.add("月报");
        typeList.add("季报");
        typeList.add("年报");
        typeList.add("事件汇报");

    }

    private void submit() {
        //设置公司名
        String company_name = etCompanyName.getText().toString().trim();
        bean.setCompanyName(company_name);

        //用户id
        bean.setCreateUser(EanfangApplication.get().getUser().getPersonId());

        //部门名称
        String department_name = etDepartmentName.getText().toString().trim();
        if (TextUtils.isEmpty(department_name)) {
            showToast("请输入部门名称");
            return;
        }
        bean.setDepartmentName(department_name);

        //类型
        String task_title = etTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(task_title)) {
            showToast("请选择类型");
            return;
        }
        bean.setType(task_title);

        String receiveUser = tvDependPerson.getText().toString().trim();
        if (TextUtils.isEmpty(receiveUser)) {
            showToast("请选择联系人");
            return;
        }

        //接收者
        bean.setReceiveUser(staffBean.getAll().get(posistion).getUid());
        bean.setCreateCompanyUid(EanfangApplication.get().getUser().getCompanyId());
        //手机号
        String phone_num = etPhoneNum.getText().toString().trim();
        bean.setReceivePhone(phone_num);
        bean.setReceiveCompanyUid(EanfangApplication.get().getUser().getCompanyId());

        beanList.addAll(findList);
        beanList.addAll(planList);
        bean.setDetails(beanList);


        doHttp(new Gson().toJson(bean));


    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(ApiService.ADD_WORK_REPORT)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true) {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        Log.e("addworkReportActivity", message.toString());
                    }
                });

    }

    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(ApiService.GET_COMPANY_STAFF)
                .tag(this)
                .params("depId", "5")
                .execute(new EanfangCallback<CompanyStaffBean>(this, true) {
                    @Override
                    public void onSuccess(CompanyStaffBean bean) {
                        staffBean = bean;
                        userlist = staffBean.getAll();
                        for (int i = 0; i < userlist.size(); i++) {
                            userNameList.add(userlist.get(i).getName());
                        }
                    }

                    @Override
                    public void onError(String message) {
                        showToast(message);
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }
        WorkAddReportBean.DetailsBean resultBean = (WorkAddReportBean.DetailsBean) data.getSerializableExtra("result");
        if (EanfangConst.TYPE_REPORT_DETAIL_FINISH.equals(resultBean.getType())) {
            beanList.add(resultBean);
            reportCompleteList.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    detailsBean = beanList.get(position);
                    new CompleteWorkView(ReportActivity.this, true, detailsBean).show();
                }
            });
            addReportDetialAdapter.notifyDataSetChanged();
        } else if (EanfangConst.TYPE_REPORT_DETAIL_FIND.equals(resultBean.getType())) {
            findList.add(resultBean);
            reportFindList.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    detailsBean = findList.get(position);
                    new FindTroubleView(ReportActivity.this, true, detailsBean).show();
                }
            });
            findAdapter.notifyDataSetChanged();
        } else if (EanfangConst.TYPE_REPORT_DETAIL_PLAN.equals(resultBean.getType())) {
            planList.add(resultBean);
            reportPlanList.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    detailsBean = planList.get(position);
                    new WorkPlanView(ReportActivity.this, true, detailsBean).show();
                }
            });
            planAdapter.notifyDataSetChanged();
        }


    }
}
