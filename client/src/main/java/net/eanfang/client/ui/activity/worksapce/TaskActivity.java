package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.WorkTaskBean;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.AddTaskDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.TaskInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:55
 * @email houzhongzhou@yeah.net
 * @desc 布置任务
 */

public class TaskActivity extends BaseClientActivity implements View.OnClickListener {


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
    private OptionsPickerView pvOptions_NoLink;

    private int posistion;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private List<WorkTaskBean.WorkTaskDetailsBean> beanList = new ArrayList<>();
    private AddTaskDetailAdapter maintenanceDetailAdapter;
    private WorkTaskBean workTaskBean = new WorkTaskBean();
    private WorkTaskBean.WorkTaskDetailsBean detailsBean;
    private Long assigneeUserId;
    private String assigneeOrgCode;
    private static int TASK_REQUEST_CODE = 200;

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

        maintenanceDetailAdapter = new AddTaskDetailAdapter(R.layout.item_quotation_detail, beanList);
        taskDetialList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        taskDetialList.setLayoutManager(new LinearLayoutManager(this));
        taskDetialList.setAdapter(maintenanceDetailAdapter);

        etCompanyName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        etDepartmentName.setText(EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgName());
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_task:
                Intent intent = new Intent(TaskActivity.this, AddWorkTaskDeitailActivity.class);
                startActivityForResult(intent, TASK_REQUEST_CODE);
                break;
            //责任人
            case R.id.ll_depend_person:
                showDependPerson();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }

        detailsBean = (WorkTaskBean.WorkTaskDetailsBean) data.getSerializableExtra("result");
        beanList.add(detailsBean);
        taskDetialList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskInfoView(TaskActivity.this, true, detailsBean).show();
            }
        });
        maintenanceDetailAdapter.notifyDataSetChanged();

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
        if (TextUtils.isEmpty(tvDependPerson.getText().toString().trim())) {
            showToast("请选择责任人");
            return;
        }

        workTaskBean.setTitle(task_title);

        workTaskBean.setAssigneeUserId(assigneeUserId);
        workTaskBean.setAssigneeOrgCode(assigneeOrgCode);
        workTaskBean.setWorkTaskDetails(beanList);

        doHttp(JSON.toJSONString(workTaskBean));


    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_TASK)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
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
                        finishSelf();
                    });


                }));

    }

}
