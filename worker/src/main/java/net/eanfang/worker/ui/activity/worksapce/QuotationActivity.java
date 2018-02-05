package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.QuotationDetailAdapter;
import net.eanfang.worker.ui.adapter.QuotationPartsAdapter;
import net.eanfang.worker.ui.adapter.QuotationServiceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/27  19:38
 * @email houzhongzhou@yeah.net
 * @desc 报价申请
 */

public class QuotationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static final String TAG = QuotationActivity.class.getSimpleName();

    public static final int RESULT_SELECT_ORDER = 100;
    private EditText et_project_name;
    private TextView tv_modifyOrder;
    private TextView tv_delete, tv_delete2, tv_delete3;
    private TextView tv_add, tv_add2, tv_add3;
    private RecyclerView rcv_detail, rcv_detail2, rcv_detail3;
    private EditText et_contract;
    private EditText et_contract_phone;
    private TextView tv_count_money;
    private TextView tv_commit;
    private String orderID;
    private String clientName;
    private String clientPhone;
    private Long oid, topid;
    private TextView tv_relate_order;
    private QuotationDetailAdapter quotationDetailAdapter;
    private QuotationPartsAdapter quotationPartsAdapter;
    private QuotationServiceAdapter quotationServiceAdapter;
    private EditText et_client_company_name_wr;
    private RadioGroup radioGroup;
    private RadioButton radioClient;
    private RadioButton radioLeader;
    private RelativeLayout ll_client_name, ll_clent_phone;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private OptionsPickerView pvOptions_NoLink;
    private int posistion;
    private QuotationBean bean = new QuotationBean();
    public List<QuotationBean.QuoteDevicesBean> quoteDevicesBeanList;
    public List<QuotationBean.QuotePartsBean> quotePartsBeanList;
    public List<QuotationBean.QuoteServicesBean> quoteServicesBeanList;
    private int deviceSum, partsSum, serviceSum;
    private Long assigneeUserId;
    private String assigneeOrgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_payment);
        supprotToolbar();
        setTitle("报价申请");
        initViews();
    }

    private void initViews() {
        et_project_name = (EditText) findViewById(R.id.et_project_name);
        tv_modifyOrder = (TextView) findViewById(R.id.tv_modifyOrder);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_delete2 = (TextView) findViewById(R.id.tv_delete2);
        tv_delete3 = (TextView) findViewById(R.id.tv_delete3);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add2 = (TextView) findViewById(R.id.tv_add2);
        tv_add3 = (TextView) findViewById(R.id.tv_add3);
        rcv_detail = (RecyclerView) findViewById(R.id.rcv_detail);
        rcv_detail2 = (RecyclerView) findViewById(R.id.rcv_detail2);
        rcv_detail3 = (RecyclerView) findViewById(R.id.rcv_detail3);
        et_contract = (EditText) findViewById(R.id.et_contract);
        et_contract_phone = (EditText) findViewById(R.id.et_contract_phone);
        tv_count_money = (TextView) findViewById(R.id.tv_sum_monkey);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_relate_order = (TextView) findViewById(R.id.tv_relate_order);
        et_client_company_name_wr = (EditText) findViewById(R.id.et_client_company_name_wr);
        radioGroup = (RadioGroup) findViewById(R.id.rg_check);
        radioClient = (RadioButton) findViewById(R.id.rb_client);
        radioLeader = (RadioButton) findViewById(R.id.rb_leader);
        radioGroup.setOnCheckedChangeListener(this);
        radioClient.setChecked(true);
        ll_clent_phone = findViewById(R.id.ll_client_phone);
        ll_client_name = findViewById(R.id.ll_client_name);
        getData();
        registerListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdapter();
    }

    private void initAdapter() {
        quoteDevicesBeanList = bean.getQuoteDevices();
        quotationDetailAdapter = new QuotationDetailAdapter(R.layout.item_quotation_detail, quoteDevicesBeanList);
        rcv_detail.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail.setAdapter(quotationDetailAdapter);
        quotationDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_item_detail:
                        Intent intent = new Intent(QuotationActivity.this, QuotationDetailActivity.class);
                        intent.putExtra("data", bean.getQuoteDevices().get(position));
                        startActivity(intent);
                        break;
                    case R.id.tv_delete:
                        bean.getQuoteDevices().remove(position);
                        quotationDetailAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });

        quotePartsBeanList = bean.getQuoteParts();
        quotationPartsAdapter = new QuotationPartsAdapter(R.layout.item_quotation_detail, quotePartsBeanList);
        rcv_detail2.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail2.setAdapter(quotationPartsAdapter);

        quoteServicesBeanList = bean.getQuoteServices();
        quotationServiceAdapter = new QuotationServiceAdapter(R.layout.item_quotation_detail, quoteServicesBeanList);
        rcv_detail3.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail3.setAdapter(quotationServiceAdapter);
    }

    private void registerListener() {
        tv_modifyOrder.setOnClickListener(v ->
                startActivityForResult(new Intent(QuotationActivity.this, ModifyOrderActivity.class), 100)
        );


//        tv_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String projectName = et_project_name.getText().toString();
//                String relateOrder = tv_relate_order.getText().toString();
//                String contact = et_contract.getText().toString().trim();
//                String phone = et_contract_phone.getText().toString().trim();
//                //2017年7月5日 lin
//                String clientcompanynamewr = et_client_company_name_wr.getText().toString().trim();
//
//                if (StringUtils.isEmpty(projectName)) {
//                    showToast("请输入项目名称");
//                    return;
//                }
//                bean.setItemname(projectName);
//                //2017年7月5日 lin
//                if (StringUtils.isEmpty(clientcompanynamewr)) {
//                    showToast("请输入客户名称");
//                    return;
//                }
//                bean.setClientcompanynamewr(clientcompanynamewr);
//
//                bean.setOrdernum(relateOrder);
//
//                if (bean.getQuotebugdetails() == null || bean.getQuotebugdetails().size() == 0) {
//                    showToast("请填写报价明细");
//                    return;
//                }
//
//                if (StringUtils.isEmpty(contact)) {
//                    showToast("请填写联系人姓名");
//                    return;
//                }
//                bean.setClientname(contact);
//                if (StringUtils.isEmpty(phone)) {
//                    showToast("请填写联系电话");
//                }
//                bean.setClientphone(phone);
//                EanfangHttp.post(ApiService.QUOTE_APPLY)
//                        .params("json", new Gson().toJson(bean))
//                        .execute(new EanfangCallback(QuotationActivity.this, true) {
//                            @Override
//                            public void onSuccess(Object bean) {
//                                super.onSuccess(bean);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Intent intent = new Intent(QuotationActivity.this, StateChangeActivity.class);
//                                        Message message = new Message();
//                                        message.setTitle("下单成功");
//                                        message.setMsgTitle("你的报价申请已下单成功！");
//                                        message.setMsgContent("请等待客户查阅。");
//                                        message.setShowLogo(true);
//                                        message.setShowOkBtn(true);
//                                        intent.putExtra("message", message);
//                                        startActivity(intent);
//                                        finishSelf();
//                                    }
//                                });
//
//                            }
//                        });
//
//            }
//        });

        tv_add.setOnClickListener((v) -> {
            startActivityForResult(new Intent(QuotationActivity.this, QuotationDetailActivity.class), 101);
        });
        tv_add2.setOnClickListener((v) -> {
            startActivityForResult(new Intent(QuotationActivity.this, QuotationPartsActivity.class), 102);
        });
        tv_add3.setOnClickListener((v) -> {
            startActivityForResult(new Intent(QuotationActivity.this, QuotationServicesActivity.class), 103);
        });
        ll_client_name.setOnClickListener(v -> showDependPerson());
        tv_commit.setOnClickListener(v -> postCommit());

    }

    private void postCommit() {
        EanfangHttp.post(NewApiService.QUOTE_ORDER_INSERT)
                .upJson(fillbean())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    Intent intent = new Intent(QuotationActivity.this, StateChangeActivity.class);
                    Message message = new Message();
                    message.setTitle("下单成功");
                    message.setMsgTitle("你的报价申请已下单成功！");
                    message.setMsgContent("请等待客户查阅。");
                    message.setShowLogo(true);
                    message.setShowOkBtn(true);
                    intent.putExtra("message", message);
                    startActivity(intent);
                    finishSelf();
                }));
    }

    private String fillbean() {
        bean.setQuoteServices(quoteServicesBeanList);
        bean.setQuoteParts(quotePartsBeanList);
        bean.setQuoteDevices(quoteDevicesBeanList);
        bean.setRepairOrderNum(orderID);
        bean.setTotalCost(deviceSum + partsSum + serviceSum);
        bean.setAssigneeOrgCode(assigneeOrgCode);
        bean.setAssigneeUserId(assigneeUserId);
        bean.setProjectName(et_project_name.getText().toString().trim());
        bean.setReporter(et_contract.getText().toString().trim());
        bean.setReporterPhone(et_contract_phone.getText().toString().trim());
        bean.setQuoteCost(deviceSum + partsSum + serviceSum);
        bean.setOrderId(oid);
        bean.setClientName(et_client_company_name_wr.getText().toString().trim());
        bean.setAssigneeTopCompanyId(topid);
        String json = JSONObject.toJSONString(bean);
        return json;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case RESULT_SELECT_ORDER:
                orderID = data.getStringExtra("orderID");
                clientName = data.getStringExtra("clientName");
                clientPhone = data.getStringExtra("clientPhone");
                assigneeOrgCode=data.getStringExtra("orgcode");
                assigneeUserId=data.getLongExtra("assignUid",0);
                oid = data.getLongExtra("orderid", 0);
                topid = data.getLongExtra("topId", 0);
                if (radioClient.isChecked() == true) {
                    if (StringUtils.isEmpty(orderID)) {
                        return;
                    }
                    tv_relate_order.setText(orderID);
                    et_contract.setText(clientName);
                    et_contract_phone.setText(clientPhone);
                }


                break;
            case 101:
                if (data.getSerializableExtra("result") == null) {
                    return;
                }
                quoteDevicesBeanList = new ArrayList<>();
                quoteDevicesBeanList.add((QuotationBean.QuoteDevicesBean) data.getSerializableExtra("result"));
                bean.setQuoteDevices(quoteDevicesBeanList);
                quotationDetailAdapter.notifyDataSetChanged();
                deviceSum = Stream.of(quoteDevicesBeanList).collect(Collectors.summingInt(c -> c.getSum()));
                break;
            case 102:
                if (data.getSerializableExtra("result") == null) {
                    return;
                }
                quotePartsBeanList = new ArrayList<>();
                quotePartsBeanList.add((QuotationBean.QuotePartsBean) data.getSerializableExtra("result"));
                bean.setQuoteParts(quotePartsBeanList);
                quotationPartsAdapter.notifyDataSetChanged();
                partsSum = Stream.of(quotePartsBeanList).collect(Collectors.summingInt(c -> c.getSum()));
                break;
            case 103:
                if (data.getSerializableExtra("result") == null) {
                    return;
                }
                quoteServicesBeanList = new ArrayList<>();
                quoteServicesBeanList.add((QuotationBean.QuoteServicesBean) data.getSerializableExtra("result"));
                bean.setQuoteServices(quoteServicesBeanList);
                quotationServiceAdapter.notifyDataSetChanged();
                serviceSum = Stream.of(quoteServicesBeanList).collect(Collectors.summingInt(c -> c.getSum()));
                tv_count_money.setText(String.valueOf(deviceSum + partsSum + serviceSum));
                break;
            default:
                break;

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_client:
                radioClient.setChecked(true);
                if (StringUtils.isEmpty(orderID)) {
                    return;
                }
                tv_relate_order.setText(orderID);
                et_contract.setText(clientName);
                et_contract_phone.setText(clientPhone);
                bean.setReportType(0);
                break;
            case R.id.rb_leader:
                radioLeader.setChecked(true);
                tv_relate_order.setText("");
                et_contract.setText("");
                et_contract_phone.setText("");
                bean.setReportType(1);
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
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("companyId", EanfangApplication.getApplication().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(QuotationActivity.this, false, UserEntity.class, true, (list) -> {
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
                et_contract_phone.setText(userlist.get(posistion).getAccountEntity().getMobile());
                et_contract.setText(userlist.get(posistion).getAccountEntity().getRealName());
                assigneeUserId = userlist.get(posistion).getUserId();
                assigneeOrgCode = userlist.get(posistion).getDepartmentEntity().getOrgCode();
                topid = userlist.get(posistion).getTopCompanyId();
            }
        }).build();
        pvOptions_NoLink.setPicker(userNameList);
        pvOptions_NoLink.show();
    }

}
