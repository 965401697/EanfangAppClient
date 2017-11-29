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

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.AddTaskDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.CompanyStaffBean;
import net.eanfang.client.ui.model.Message;
import net.eanfang.client.ui.model.WorkTaskBean;
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

public class TaskActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_company_name)
    TextView etCompanyName;
    @BindView(R.id.et_department_name)
    EditText etDepartmentName;
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
    private OptionsPickerView pvOptions_NoLink;

    private CompanyStaffBean staffBean;
    private int posistion;
    private List<CompanyStaffBean.AllBean> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private List<WorkTaskBean.DetailsBean> beanList = new ArrayList<>();
    private AddTaskDetailAdapter maintenanceDetailAdapter;
    private WorkTaskBean bean = new WorkTaskBean();
    private WorkTaskBean.DetailsBean detailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityu_task);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("工作任务");
        setLeftBack();
        btnAddTask.setOnClickListener(this);
        llDependPerson.setOnClickListener(this);
        llComit.setOnClickListener(this);

        maintenanceDetailAdapter = new AddTaskDetailAdapter(R.layout.item_quotation_detail, beanList);
        taskDetialList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        taskDetialList.setLayoutManager(new LinearLayoutManager(this));
        taskDetialList.setAdapter(maintenanceDetailAdapter);

        etCompanyName.setText(EanfangApplication.getApplication().getUser().getCompanyName());
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_task:
                Intent intent = new Intent(TaskActivity.this, AddWorkTaskDeitailActivity.class);
                startActivityForResult(intent, 10016);
                break;
            case R.id.ll_depend_person://责任人
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }

        detailsBean = (WorkTaskBean.DetailsBean) data.getSerializableExtra("result");
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

        String company_name = etCompanyName.getText().toString().trim();
        bean.setCompanyName(company_name);
        bean.setCreateUser(EanfangApplication.get().getUser().getPersonId());
        String department_name = etDepartmentName.getText().toString().trim();
        if (TextUtils.isEmpty(department_name)) {
            showToast("请输入部门名称");
            return;
        }
        bean.setDepartmentName(department_name);

        String task_title = etTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(task_title)) {
            showToast("请输入任务标题");
            return;
        }
        bean.setTitle(task_title);

        String receiveUser = tvDependPerson.getText().toString().trim();
        if (TextUtils.isEmpty(receiveUser)) {
            showToast("请选择联系人");
            return;
        }

        bean.setReceiveUser(staffBean.getAll().get(posistion).getUid());
        bean.setCreateCompanyUid(EanfangApplication.get().getUser().getCompanyId());

        String phone_num = etPhoneNum.getText().toString().trim();
        bean.setReceivePhone(phone_num);

        // TODO: 2017/8/31 跨公司时候需要改变
        bean.setReceiveCompanyUid(EanfangApplication.get().getUser().getCompanyId());

        bean.setDetails(beanList);
        doHttp(new Gson().toJson(bean));


    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(ApiService.ADD_WORK_TASK)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true) {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {

                    }
                });

    }

}
