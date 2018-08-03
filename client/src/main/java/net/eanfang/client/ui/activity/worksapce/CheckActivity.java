package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.WorkAddCheckBean;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.adapter.AddCheckDetailAdapter;
import net.eanfang.client.ui.adapter.SendPersonAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.CheckInfoView;
import net.eanfang.client.util.SendContactUtils;

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
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;

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
    private static int CHECK_REQUEST_CODE = 101;

    private boolean isSend = false;
    private SendPersonAdapter sendPersonAdapter;
    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ToastUtil.get().showToast(CheckActivity.this, "发送成功");
            finishSelf();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("新建检查");
        setLeftBack();
        getData();
        //截至期限
        llReportEndtime.setOnClickListener((v) -> {
            PickerSelectUtil.onYearMonthDayPicker(CheckActivity.this, "截止期限", tvEndTime);
        });
        //责任人
        llDependPerson.setOnClickListener((v) -> {
            isSend = false;
            Intent intent = new Intent(this, SelectOrganizationActivity.class);
            intent.putExtra("isRadio", "isRadio");
            startActivity(intent);
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSend = true;

                startActivity(new Intent(CheckActivity.this, SelectIMContactActivity.class).putExtra("flag", 2));
            }
        });

        //提交
        llComit.setOnClickListener((v) -> {
            submit();
        });
        //添加明细
        btnAddDetail.setOnClickListener((v) -> {
            Intent intent = new Intent(CheckActivity.this, AddWorkCheckDetailActivity.class);
            startActivityForResult(intent, CHECK_REQUEST_CODE);
        });

        maintenanceDetailAdapter = new AddCheckDetailAdapter(R.layout.item_question_detail, beanList);
        checkDetailList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        checkDetailList.setLayoutManager(new LinearLayoutManager(this));
        checkDetailList.setAdapter(maintenanceDetailAdapter);
        maintenanceDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    maintenanceDetailAdapter.remove(position);
                }
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvTeam.setLayoutManager(manager);

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
                .execute(new EanfangCallback<UserEntity>(CheckActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }

    /**
     * 责任人
     */
    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {


        if (presonList.size() > 0) {
            if (isSend) {

                if (sendPersonAdapter == null) {
                    sendPersonAdapter = new SendPersonAdapter();
                    sendPersonAdapter.bindToRecyclerView(rvTeam);
                }

                Set hashSet = new HashSet();
                hashSet.addAll(sendPersonAdapter.getData());
                hashSet.addAll(presonList);

                if (newPresonList.size() > 0) {
                    newPresonList.clear();
                }
                newPresonList.addAll(hashSet);
                sendPersonAdapter.setNewData(newPresonList);

            } else {
                TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);

                etPhoneNum.setText(bean.getMobile());
                tvDependPerson.setText(bean.getName());

                assigneeUserId = Long.parseLong(bean.getUserId());
                if (bean.getOrgCode() != null && !TextUtils.isEmpty(bean.getOrgCode())) {
                    assigneeOrgCode = bean.getOrgCode();
                } else {
                    assigneeOrgCode = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgCode();
                }

            }
        }
    }

//    private void showDependPerson() {
//        if (userlist == null || userlist.isEmpty()) {
//            showToast("暂无其他员工可选");
//            return;
//        }
//        pvOptions_NoLink = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
//            posistion = options1;
//            etPhoneNum.setText(userlist.get(posistion).getAccountEntity().getMobile());
//            tvDependPerson.setText(userlist.get(posistion).getAccountEntity().getRealName());
//            assigneeUserId = userlist.get(posistion).getUserId();
//            assigneeOrgCode = userlist.get(posistion).getDepartmentEntity().getOrgCode();
//
//        }).build();
//        pvOptions_NoLink.setPicker(userNameList);
//        pvOptions_NoLink.show();
//    }


    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_CHECK)
                .upJson(jsonString)
                .execute(new EanfangCallback<WorkCheckInfoBean>(this, true, WorkCheckInfoBean.class, (bean) -> {
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


                                //分享

                                if (newPresonList.size() > 0) {

                                    Bundle b = new Bundle();

                                    b.putString("id", String.valueOf(bean.getId()));
                                    b.putString("orderNum", EanfangApplication.get().getUser().getAccount().getRealName());
                                    if (bean.getWorkInspectDetails() != null && bean.getWorkInspectDetails().size() > 0 && !TextUtils.isEmpty(bean.getWorkInspectDetails().get(0).getPictures())) {
                                        bundle.putString("picUrl", bean.getWorkInspectDetails().get(0).getPictures().split(",")[0]);
                                    }
                                    b.putString("creatTime", tvDependPerson.getText().toString().trim());
                                    b.putString("workerName", tvEndTime.getText().toString().trim());
                                    b.putString("status", "0");
                                    b.putString("shareType", "5");

                                    new SendContactUtils(b, handler, newPresonList, DialogUtil.createLoadingDialog(CheckActivity.this)).send();

                                } else {
                                    finishSelf();
                                }
                            });
                        })
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
        maintenanceDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                new CheckInfoView(CheckActivity.this, true, beanList.get(position)).show();
            }
        });
        maintenanceDetailAdapter.notifyDataSetChanged();
    }
}
