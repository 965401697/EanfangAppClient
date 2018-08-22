package net.eanfang.worker.ui.activity.worksapce.oa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkTaskBean;
import com.eanfang.model.WorkTaskInfoBean;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.ToastUtil;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.AddWorkTaskDeitailActivity;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.adapter.AddTaskDetailAdapter;
import net.eanfang.worker.ui.adapter.SendPersonAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.TaskInfoView;
import net.eanfang.worker.util.SendContactUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:55
 * @email houzhongzhou@yeah.net
 * @desc 布置任务
 */

public class TaskActivity extends BaseWorkerActivity implements View.OnClickListener {
    @BindView(R.id.btn_add_task)
    TextView btnAddTask;
    @BindView(R.id.task_detial_list)
    RecyclerView taskDetialList;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.ll_comit)
    Button llComit;
    @BindView(R.id.et_task_name)
    EditText etTaskName;
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
    private int posistion;

    private OptionsPickerView pvOptions_NoLink;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private List<WorkTaskBean.WorkTaskDetailsBean> beanList = new ArrayList<>();
    private AddTaskDetailAdapter maintenanceDetailAdapter;
    private WorkTaskBean workTaskBean = new WorkTaskBean();
    private WorkTaskBean.WorkTaskDetailsBean detailsBean;
    private Long assigneeUserId;
    private String assigneeOrgCode;

    private SendPersonAdapter sendGroupAdapter;
    private final int REQUEST_CODE_GROUP = 101;
    private int isSend = -1;
    private ArrayList<TemplateBean.Preson> newGroupList = new ArrayList<>();
    private SendPersonAdapter sendPersonAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(TaskActivity.this, "发送成功");
            finishSelf();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityu_task);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("新建任务");
        setLeftBack();
        btnAddTask.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llComit.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        tvSendGroup.setOnClickListener(this);

        maintenanceDetailAdapter = new AddTaskDetailAdapter(R.layout.item_question_detail, beanList);
        taskDetialList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        taskDetialList.setLayoutManager(new LinearLayoutManager(this));
        taskDetialList.setAdapter(maintenanceDetailAdapter);
        maintenanceDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    maintenanceDetailAdapter.remove(position);
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
            case R.id.btn_add_task:
                Intent intent = new Intent(TaskActivity.this, AddWorkTaskDeitailActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_depend_person://责任人

                isSend = 0;

                Intent in = new Intent(this, SelectOrganizationActivity.class);
                in.putExtra("isRadio", "isRadio");
                startActivity(in);
                break;
            case R.id.tv_send://选择人员

                isSend = 1;

                startActivity(new Intent(TaskActivity.this, SelectOAPresonActivity.class));
                break;

            case R.id.tv_send_group://选择群组
                isSend = 2;
                startActivityForResult(new Intent(TaskActivity.this, SelectOAGroupActivity.class), REQUEST_CODE_GROUP);
                break;
            case R.id.ll_comit:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(TaskActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;

                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());

                }));


    }

    /**
     * 责任人
     */
    /**
     * 责任人
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {


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
                TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK || resultCode == 1) {

            if (requestCode == 1) {

                if (data == null || data.getSerializableExtra("result") == null) {
                    return;
                }

                detailsBean = (WorkTaskBean.WorkTaskDetailsBean) data.getSerializableExtra("result");
                beanList.add(detailsBean);
                maintenanceDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        new TaskInfoView(TaskActivity.this, true, detailsBean).show();
                    }
                });
                maintenanceDetailAdapter.notifyDataSetChanged();
            } else if (requestCode == REQUEST_CODE_GROUP) {
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
            }
        }

    }

    private void submit() {

        String task_title = etTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(task_title)) {
            showToast("请输入任务标题");
            return;
        }
        if (maintenanceDetailAdapter.getData().size() == 0) {
            showToast("请添加任务明细");
            return;
        }
//        if (TextUtils.isEmpty(tvDependPerson.getText().toString().trim())) {
//            showToast("请选择责任人");
//            return;
//        }

        if (newPresonList.size() == 0) {
            //工作协同默认值
            workTaskBean.setAssigneeUserId(EanfangApplication.get().getUserId());
            workTaskBean.setAssigneeOrgCode(EanfangApplication.get().getOrgCode());
        } else {
            //工作协同默认值
            workTaskBean.setAssigneeUserId(Long.parseLong(newPresonList.get(0).getUserId()));
            workTaskBean.setAssigneeOrgCode(newPresonList.get(0).getOrgCode());
        }

        workTaskBean.setTitle(task_title);

//        workTaskBean.setAssigneeUserId(assigneeUserId);
//        workTaskBean.setAssigneeOrgCode(assigneeOrgCode);
        workTaskBean.setWorkTaskDetails(beanList);

        doHttp(JSON.toJSONString(workTaskBean));


    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_TASK)
                .upJson(jsonString)
                .execute(new EanfangCallback<WorkTaskInfoBean>(this, true, WorkTaskInfoBean.class, (bean) -> {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(TaskActivity.this, StateChangeActivity.class);
                        Bundle bundle = new Bundle();
                        Message message = new Message();
                        message.setTitle("任务指派成功");
                        message.setMsgTitle("您的工作任务已指派成功");
                        message.setMsgContent("您可以随时通过我的工作任务查看");
                        message.setShowOkBtn(true);
                        message.setShowLogo(true);
                        message.setTip("");
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
                        if (bean.getWorkTaskDetails() != null && bean.getWorkTaskDetails().size() > 0 && !TextUtils.isEmpty(bean.getWorkTaskDetails().get(0).getPictures())) {
                            bundle.putString("picUrl", bean.getWorkTaskDetails().get(0).getPictures().split(",")[0]);
                        }
                        b.putString("creatTime", etTaskName.getText().toString().trim());
                        b.putString("workerName", EanfangApplication.get().getUser().getAccount().getRealName());
                        b.putString("status", "0");
                        b.putString("shareType", "4");

                        new SendContactUtils(b, handler, newGroupList, DialogUtil.createLoadingDialog(TaskActivity.this)).send();


                    });


                }));

    }


}

