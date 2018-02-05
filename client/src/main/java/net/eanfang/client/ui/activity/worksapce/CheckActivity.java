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
import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.WorkAddCheckBean;
import com.eanfang.util.PickerSelectUtil;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.AddCheckDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.CheckInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  13:55
 * @email houzhongzhou@yeah.net
 * @desc 工作检查
 */

public class CheckActivity extends BaseClientActivity {
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.btn_add_detail)
    TextView btnAddDetail;
    @BindView(R.id.check_detail_list)
    RecyclerView checkDetailList;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_report_endtime)
    LinearLayout llReportEndtime;
    @BindView(R.id.et_input_change_work_content)
    EditText etInputChangeWorkContent;
    @BindView(R.id.ll_comit)
    Button llComit;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;

    private OptionsPickerView pvOptions_NoLink;
    private int posistion;
    private List<String> userNameList = new ArrayList<>();
    private List<UserEntity> userlist = new ArrayList<>();
    private WorkAddCheckBean bean = new WorkAddCheckBean();
    private WorkAddCheckBean.WorkInspectDetailsBean detailBean;
    private List<WorkAddCheckBean.WorkInspectDetailsBean> beanList = new ArrayList<>();
    private AddCheckDetailAdapter maintenanceDetailAdapter;
    private Long assigneeUserId;
    private String assigneeOrgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("新建检查工作");
        setLeftBack();
        getData();
        //截至期限
        llReportEndtime.setOnClickListener((v) -> {
            PickerSelectUtil.onYearMonthDayPicker(CheckActivity.this, "截止期限", tvEndTime);
        });
        //责任人
        llDependPerson.setOnClickListener((v) -> {
            showDependPerson();
        });
        //提交
        llComit.setOnClickListener((v) -> {
            submit();
        });
        //添加明细
        btnAddDetail.setOnClickListener((v) -> {
            Intent intent = new Intent(CheckActivity.this, AddWorkCheckDetailActivity.class);
            startActivityForResult(intent, AddWorkCheckDetailActivity.class.hashCode());
        });

        maintenanceDetailAdapter = new AddCheckDetailAdapter(R.layout.item_quotation_detail, beanList);
        checkDetailList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        checkDetailList.setLayoutManager(new LinearLayoutManager(this));
        checkDetailList.setAdapter(maintenanceDetailAdapter);
    }


    private void submit() {
        String name = etCompanyName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入单位名称");
            return;
        }
        bean.setCompanyName(name);

        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入标题");
            return;
        }
        bean.setTitle(title);

        String endtime = tvEndTime.getText().toString().trim();
        if (TextUtils.isEmpty(endtime)) {
            showToast("请选择截至期限");
            return;
        }
        bean.setChangeDeadlineTime(endtime);

        String changeContent = etInputChangeWorkContent.getText().toString().trim();
        if (TextUtils.isEmpty(changeContent)) {
            showToast("请填写整改要求");
            return;
        }
        bean.setChangeInfo(changeContent);

        String receiveUser = tvDependPerson.getText().toString().trim();
        if (TextUtils.isEmpty(receiveUser)) {
            showToast("请选择联系人");
            return;
        }

        //接收者
        bean.setAssigneeUserId(assigneeUserId);
        bean.setAssigneeOrgCode(assigneeOrgCode);
        //手机号
        String phone_num = etPhoneNum.getText().toString().trim();
        // TODO: 2017/12/18 手机号
//        bean.setReceivePhone(phone_num);
        bean.setWorkInspectDetails(beanList);

        doHttp(JSON.toJSONString(bean));

    }


    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .tag(this)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(CheckActivity.this, false, UserEntity.class, true, (list) -> {
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
        pvOptions_NoLink = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
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


    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_CHECK)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                            runOnUiThread(() -> {
                                Intent intent = new Intent(CheckActivity.this, StateChangeActivity.class);
                                Bundle bundle = new Bundle();
                                Message message = new Message();
                                message.setTitle("检查发送成功");
                                message.setMsgTitle("您的工作检查已发送成功");
                                message.setMsgContent("您可以随时通过我的检查查看");
                                message.setShowOkBtn(true);
                                message.setShowLogo(true);
                                message.setTip("");
                                bundle.putSerializable("message", message);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finishSelf();
                            });
                        })
//                {
//                    @Override
//                    public void onSuccess(Object object) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(CheckActivity.this, StateChangeActivity.class);
//                                Bundle bundle = new Bundle();
//                                Message message = new Message();
//                                message.setTitle("检查发送成功");
//                                message.setMsgTitle("您的工作检查已发送成功");
//                                message.setMsgContent("您可以随时通过我的检查查看");
//                                message.setShowOkBtn(true);
//                                message.setShowLogo(true);
//                                message.setTip("");
//                                bundle.putSerializable("message", message);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                finishSelf();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        Log.e("addworkReportActivity", message.toString());
//                    }
//                }
                );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }

        detailBean = (WorkAddCheckBean.WorkInspectDetailsBean) data.getSerializableExtra("result");
        beanList.add(detailBean);
        checkDetailList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new CheckInfoView(CheckActivity.this, true, beanList.get(position)).show();
            }
        });
        maintenanceDetailAdapter.notifyDataSetChanged();
    }
}
