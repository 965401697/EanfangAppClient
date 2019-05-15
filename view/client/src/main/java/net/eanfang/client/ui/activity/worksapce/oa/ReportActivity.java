package net.eanfang.client.ui.activity.worksapce.oa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.model.WorkAddReportBean;
import com.eanfang.biz.model.WorkReportInfoBean;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.biz.model.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.AddReportCompleteActivity;
import net.eanfang.client.ui.activity.worksapce.AddReportFindActivity;
import net.eanfang.client.ui.activity.worksapce.AddReportPlanActivity;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.adapter.AddReportDetailAdapter;
import net.eanfang.client.ui.adapter.SendPersonAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.CompleteWorkView;
import net.eanfang.client.ui.widget.FindTroubleView;
import net.eanfang.client.ui.widget.WorkPlanView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:54
 * @email houzhongzhou@yeah.net
 * @desc 工作汇报
 */
@Deprecated
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
    @BindView(R.id.tv_add_plan)
    TextView tvAddPlan;
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
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    @BindView(R.id.tv_send_group)
    TextView tvSendGroup;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;

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

    private SendPersonAdapter sendPersonAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private ArrayList<TemplateBean.Preson> newGroupList = new ArrayList<>();
    private SendPersonAdapter sendGroupAdapter;
    private final int REQUEST_CODE_GROUP = 101;
    private int isSend = -1;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(ReportActivity.this, "发送成功");
            finishSelf();
        }
    };

    private boolean isEventBus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("新建汇报");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否要保存
                giveUp();
            }
        });

        btnAddComplete.setOnClickListener(this);
        btnAddFind.setOnClickListener(this);
        tvAddPlan.setOnClickListener(this);
        llComit.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llReportType.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        tvSendGroup.setOnClickListener(this);
//        etCompanyName.setText(EanfangApplication.get().getUser().getCompanyName());

        addReportDetialAdapter = new AddReportDetailAdapter(R.layout.item_question_detail, beanList);
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

        findAdapter = new AddReportDetailAdapter(R.layout.item_question_detail, findList);
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

        planAdapter = new AddReportDetailAdapter(R.layout.item_question_detail, planList);
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


        getData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_complete://完成工作
                isEventBus = false;
                Intent intent = new Intent(ReportActivity.this, AddReportCompleteActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_add_find://发现问题
                isEventBus = false;
                intent = new Intent(ReportActivity.this, AddReportFindActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_add_plan://后续计划
                isEventBus = false;
                intent = new Intent(ReportActivity.this, AddReportPlanActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.ll_depend_person://联系人
                isEventBus = true;
                isSend = 0;

                Intent in = new Intent(this, SelectOrganizationActivity.class);
                in.putExtra("isRadio", "isRadio");
                startActivity(in);
                break;
            case R.id.ll_report_type://类型
                PickerSelectUtil.singleTextPicker(this, "", etTaskName, GetConstDataUtils.getWorkReportTypeList());
                break;

            case R.id.tv_send://选择人员
                isEventBus = true;
                isSend = 1;
                startActivity(new Intent(ReportActivity.this, SelectOAPresonActivity.class));
                break;

            case R.id.tv_send_group://选择群组
                isEventBus = true;
                isSend = 2;
                startActivityForResult(new Intent(ReportActivity.this, SelectOAGroupActivity.class), REQUEST_CODE_GROUP);
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

        if (beanList.size() == 0) {
            showToast("请填写完成工作的内容");
            return;
        }

        if (planList.size() == 0) {
            showToast("请填写后续计划的内容");
            return;
        }

        if (beanList.size() < 3) {
            showToast("完成工作的内容少于3条");
            return;
        }

        if (planList.size() < 3) {
            showToast("后续计划的内容少于3条");
            return;
        }

        if (newPresonList.size() == 0) {
            //工作协同默认值
            bean.setAssigneeUserId(EanfangApplication.get().getUserId());
            bean.setAssigneeOrgCode(EanfangApplication.get().getOrgCode());
        } else {
            //工作协同默认值
            bean.setAssigneeUserId(Long.parseLong(newPresonList.get(0).getUserId()));
            bean.setAssigneeOrgCode(newPresonList.get(0).getOrgCode());
        }
        bean.setType(GetConstDataUtils.getWorkReportTypeList().indexOf(task_title));
//        bean.setAssigneeUserId(assigneeUserId);
//        bean.setAssigneeOrgCode(assigneeOrgCode);
        beanList.addAll(findList);
        beanList.addAll(planList);
        bean.setWorkReportDetails(beanList);

        doHttp(JSON.toJSONString(bean));
    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_REPORT)
                .upJson(jsonString)
                .execute(new EanfangCallback<WorkReportInfoBean>(this, true, WorkReportInfoBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(ReportActivity.this, StateChangeActivity.class);
                        Bundle bundle = new Bundle();
                        Message message = new Message();
                        message.setTitle("汇报发送成功");
                        message.setMsgTitle("您的工作汇报已发送成功");
                        message.setMsgContent("您可以随时通过我的汇报查看");
                        message.setShowOkBtn(true);
                        message.setShowLogo(true);
                        message.setTip("确定");
                        bundle.putSerializable("message", message);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        //分享
                        if (newPresonList.size() == 0 && newGroupList.size() == 0) {
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
                        b.putString("orderNum", etDepartmentName.getText().toString().trim());
                        if (bean.getWorkReportDetails() != null && bean.getWorkReportDetails().size() > 0 && !TextUtils.isEmpty(bean.getWorkReportDetails().get(0).getPictures())) {
                            bundle.putString("picUrl", bean.getWorkReportDetails().get(0).getPictures().split(",")[0]);
                        }
                        b.putString("creatTime", String.valueOf(GetConstDataUtils.getWorkReportTypeList().indexOf(etTaskName.getText().toString().trim())));
                        b.putString("workerName", EanfangApplication.get().getUser().getAccount().getRealName());
                        b.putString("status", "0");
                        b.putString("shareType", "3");

//                        new SendContactUtils(b, handler, newGroupList, DialogUtil.createLoadingDialog(ReportActivity.this)).send();

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
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (!isEventBus) {
            return;
        }

        if (presonList.size() > 0) {
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
                TemplateBean.Preson bean = presonList.get(0);

                etPhoneNum.setText(bean.getMobile());
                tvDependPerson.setText(bean.getName());

                assigneeUserId = Long.parseLong(bean.getUserId());
                if (bean.getOrgCode() != null && !TextUtils.isEmpty(bean.getOrgCode())) {
                    assigneeOrgCode = bean.getOrgCode();
                } else {
                    assigneeOrgCode = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
                }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GROUP) {
            TemplateBean.Preson preson = (TemplateBean.Preson) data.getSerializableExtra("bean");
            if (sendGroupAdapter.getData().size() > 0) {
                if (!sendGroupAdapter.getData().contains(preson)) {
                    sendGroupAdapter.addData(preson);
                    newGroupList.add(preson);
                }
            } else {
                newGroupList.add(preson);
                sendGroupAdapter.addData(preson);
            }
            return;
        }

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

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    /**
     * 放弃新建汇报
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃工作汇报？", () -> {
            finish();
        }).showDialog();
    }
}
