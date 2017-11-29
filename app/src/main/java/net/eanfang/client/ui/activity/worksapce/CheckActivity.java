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
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.AddCheckDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.CompanyStaffBean;
import net.eanfang.client.ui.model.Message;
import net.eanfang.client.ui.model.WorkAddCheckBean;
import net.eanfang.client.ui.widget.CheckInfoView;
import net.eanfang.client.util.PickerSelectUtil;

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

public class CheckActivity extends BaseActivity {
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
    private CompanyStaffBean staffBean;
    private List<CompanyStaffBean.AllBean> userlist = new ArrayList<>();
    private WorkAddCheckBean bean = new WorkAddCheckBean();
    private WorkAddCheckBean.DetailsBean detailBean;
    private List<WorkAddCheckBean.DetailsBean> beanList = new ArrayList<>();
    private AddCheckDetailAdapter maintenanceDetailAdapter;

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
            startActivityForResult(intent, 10084);
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

        //用户id
        bean.setCreateUser(EanfangApplication.get().getUser().getPersonId());

        String endtime = tvEndTime.getText().toString().trim();
        if (TextUtils.isEmpty(endtime)) {
            showToast("请选择截至期限");
            return;
        }
        bean.setChangeDeadline(endtime);

        String changeContent = etInputChangeWorkContent.getText().toString().trim();
        if (TextUtils.isEmpty(changeContent)) {
            showToast("请填写整改要求");
            return;
        }
        bean.setChangeRequire(changeContent);

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
        bean.setDetails(beanList);

        doHttp(new Gson().toJson(bean));

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

    private void doHttp(String jsonString) {
        EanfangHttp.post(ApiService.ADD_CHECK_WORK_INSPECT)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true) {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        Log.e("addworkReportActivity", message.toString());
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }

        detailBean = (WorkAddCheckBean.DetailsBean) data.getSerializableExtra("result");
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
