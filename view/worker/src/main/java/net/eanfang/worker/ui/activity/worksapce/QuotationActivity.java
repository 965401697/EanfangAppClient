package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.Message;
import com.eanfang.model.QuotationBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.model.sys.UserEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
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
    private static final int QUOTAION_REQUEST_CODE = 1;
    private static final int QUOTAION_REQUEST_DEVICES_CODE = 2;
    private static final int QUOTAION_REQUEST_PARTS_CODE = 3;
    private static final int QUOTAION_REQUEST_SERVICES_CODE = 4;
    public List<QuotationBean.QuoteDevicesBean> devicesBeanList = new ArrayList<>();
    public List<QuotationBean.QuotePartsBean> partsBeanList = new ArrayList<>();
    public List<QuotationBean.QuoteServicesBean> servicesBeanList = new ArrayList<>();
    private EditText et_project_name, tv_detail_address, et_client_company_name_wr;
    private EditText et_contract, et_contract_phone;
    private ImageView iv_add, iv_add2, iv_add3;
    private TextView tv_address, tv_modifyOrder;
    private TextView tv_count_money, tv_commit, tv_relate_order;
    private RecyclerView rcv_detail, rcv_detail2, rcv_detail3;
    private String lon, lat, province, city, zone, contry;
    private String orderID, clientName, clientPhone, assigneeOrgCode;
    private Long oid, topid, assigneeUserId;
    private int deviceSum = 0, partsSum = 0, serviceSum = 0;
    private List<UserEntity> userlist = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private QuotationDetailAdapter devicesAdapter;
    private QuotationPartsAdapter partsAdapter;
    private QuotationServiceAdapter serviceAdapter;

    private RadioGroup radioGroup;
    private RadioButton radioClient, radioLeader;
    private LinearLayout llProjectAddress;
    private RelativeLayout ll_client_name, ll_clent_phone;

    //    private OptionsPickerView pvOptions_NoLink;
    private QuotationBean bean = new QuotationBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_payment);
        setTitle("报价申请");
        setLeftBack();
        initViews();
    }

    private void initViews() {
        et_project_name = findViewById(R.id.et_project_name);
        tv_modifyOrder = findViewById(R.id.tv_modifyOrder);
        iv_add = findViewById(R.id.iv_add);
        iv_add2 = findViewById(R.id.iv_add2);
        iv_add3 = findViewById(R.id.iv_add3);
        rcv_detail = findViewById(R.id.rcv_detail);
        rcv_detail2 = findViewById(R.id.rcv_detail2);
        rcv_detail3 = findViewById(R.id.rcv_detail3);
        et_contract = findViewById(R.id.et_contract);
        et_contract_phone = findViewById(R.id.et_contract_phone);
        tv_count_money = findViewById(R.id.tv_sum_monkey);
        tv_commit = findViewById(R.id.tv_commit);
        tv_address = findViewById(R.id.tv_address);
        llProjectAddress = findViewById(R.id.ll_address);
        tv_detail_address = findViewById(R.id.et_detail_address);
        tv_relate_order = findViewById(R.id.tv_relate_order);
        et_client_company_name_wr = findViewById(R.id.et_client_company_name_wr);
        radioGroup = findViewById(R.id.rg_check);
        radioClient = findViewById(R.id.rb_client);
        radioLeader = findViewById(R.id.rb_leader);
        radioGroup.setOnCheckedChangeListener(this);
        radioClient.setChecked(true);
        ll_clent_phone = findViewById(R.id.ll_client_phone);
        ll_client_name = findViewById(R.id.ll_client_name);
        getData();
        registerListener();
        initAdapter();
    }

    /**
     * 获取公司部门员工信息
     */
    private void getData() {

        EanfangHttp.get(NewApiService.GET_COLLEAGUE)
                .params("id", WorkerApplication.get().getUserId())
                .params("companyId", WorkerApplication.get().getCompanyId())
                .execute(new EanfangCallback<UserEntity>(QuotationActivity.this, true, UserEntity.class, true, (list) -> {
                    userlist = list;
                    userNameList.addAll(Stream.of(userlist).map((user) -> user.getAccountEntity().getRealName()).toList());
                }));
    }

    private void registerListener() {
        tv_modifyOrder.setOnClickListener(v ->
                startActivityForResult(new Intent(QuotationActivity.this, ModifyOrderActivity.class), RESULT_SELECT_ORDER)
        );
        llProjectAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(QuotationActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, QUOTAION_REQUEST_CODE);
        });
        iv_add.setOnClickListener((v) -> {
            startActivityForResult(new Intent(QuotationActivity.this, QuotationDetailActivity.class), QUOTAION_REQUEST_DEVICES_CODE);
        });
        iv_add2.setOnClickListener((v) -> {
//            if (devicesBeanList.size() == 0) {
//                showToast("请先完善设备明细");
//                return;
//            }
            startActivityForResult(new Intent(QuotationActivity.this, QuotationPartsActivity.class), QUOTAION_REQUEST_PARTS_CODE);
        });
        iv_add3.setOnClickListener((v) -> {
//            if (partsBeanList.size() == 0) {
//                showToast("请先完善配件明细");
//                return;
//            }
            startActivityForResult(new Intent(QuotationActivity.this, QuotationServicesActivity.class), QUOTAION_REQUEST_SERVICES_CODE);
        });
        ll_client_name.setOnClickListener(v -> showDependPerson());
//        tv_commit.setOnClickListener(v -> postCommit());
        tv_commit.setOnClickListener((v) -> {
            if (!checkInfo(true)) {
                return;
            }
            postCommit();
        });

    }

    private void initAdapter() {
        devicesAdapter = new QuotationDetailAdapter(R.layout.item_quotation_detail, devicesBeanList);
        rcv_detail.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv_detail.setAdapter(devicesAdapter);

        partsAdapter = new QuotationPartsAdapter(R.layout.item_quotation_detail, partsBeanList);
        rcv_detail2.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail2.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv_detail2.setAdapter(partsAdapter);

        serviceAdapter = new QuotationServiceAdapter(R.layout.item_quotation_detail, servicesBeanList);
        rcv_detail3.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail3.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv_detail3.setAdapter(serviceAdapter);
    }

    /**
     * 责任人
     */
    private void showDependPerson() {

        if (radioClient.isChecked()) {
            showToast("汇报给客户不需要选择人员");
            return;
        }

        if (userlist == null || userlist.isEmpty()) {
            showToast("暂无其他员工可选");
            return;
        }
        PickerSelectUtil.singleTextPicker(this, "", userNameList, new PickerSelectUtil.OnOptionPickListener() {
            @Override
            public void picker(int index, String item) {
                et_contract_phone.setText(userlist.get(index).getAccountEntity().getMobile());
                et_contract.setText(userlist.get(index).getAccountEntity().getRealName());
                assigneeUserId = userlist.get(index).getUserId();
                assigneeOrgCode = userlist.get(index).getDepartmentEntity().getOrgCode();
                topid = userlist.get(index).getTopCompanyId();
            }
        });

    }

    private boolean checkInfo(boolean check) {
        if (StringUtils.isEmpty(et_client_company_name_wr.getText().toString().trim())) {
            showToast("请填写用户名称");
            return false;
        }

        if (StringUtils.isEmpty(et_project_name.getText().toString().trim())) {
            showToast("请填写项目名称");
            return false;
        }

        if (StringUtils.isEmpty(tv_address.getText().toString().trim())) {
            showToast("请填写地址名称");
            return false;
        }

        if (radioLeader.isChecked() && StringUtils.isEmpty(et_contract.getText().toString().trim())) {
            showToast("请选择联系人");
            return false;
        }
        int detailSum = (devicesBeanList.size() > 0 ? 1 : 0) + (partsBeanList.size() > 0 ? 1 : 0) + (servicesBeanList.size() > 0 ? 1 : 0);

        if (detailSum < 1) {
            showToast("设备、配件、服务请至少添加一项");
            return false;
        }
//        if (partsBeanList.size() == 0) {
//            showToast("请填写配件明细");
//            return false;
//        }
//        if (servicesBeanList.size() == 0) {
//            showToast("请填写服务明细");
//            return false;
//        }
        return true;
    }

    private void postCommit() {
        if (radioClient.isChecked()) {
            if (StringUtils.isEmpty(orderID)) {
                showToast("汇报给客户需要关联订单");
                return;
            }
        }

        EanfangHttp.post(NewApiService.QUOTE_ORDER_INSERT)
                .upJson(fillbean())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    Bundle bundle = new Bundle();
                    Message message = new Message();
                    message.setMsgContent("提交成功。");
                    message.setTip("确定");
                    bundle.putSerializable("message", message);
                    JumpItent.jump(QuotationActivity.this, StateChangeActivity.class, bundle);
                    finishSelf();
                }));
    }

    private String fillbean() {


        bean.setQuoteServices(servicesBeanList);
        bean.setQuoteParts(partsBeanList);
        bean.setQuoteDevices(devicesBeanList);
        bean.setRepairOrderNum(orderID);
        bean.setTotalCost((deviceSum + partsSum + serviceSum));
        bean.setAssigneeOrgCode(assigneeOrgCode);
        bean.setAssigneeUserId(assigneeUserId);
//        bean.setZone_code(Config.get().getAreaCodeByName(city, contry));
//        bean.setZone_id(Long.parseLong(Config.get().getBaseIdByCode(bean.getZone_code(), 3, Constant.AREA)));
        bean.setLatitude(lat);
        bean.setLongitude(lon);
        bean.setDetail_place(tv_detail_address.getText().toString().trim());
        bean.setProjectName(et_project_name.getText().toString().trim());
        bean.setReporter(et_contract.getText().toString().trim());
        bean.setReporterPhone(et_contract_phone.getText().toString().trim());
        bean.setQuoteCost((deviceSum + partsSum + serviceSum));
        bean.setOrderId(oid);
        bean.setClientName(et_client_company_name_wr.getText().toString().trim());
        bean.setAssigneeTopCompanyId(topid);
        String json = JSONObject.toJSONString(bean);
        return json;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                assigneeOrgCode = data.getStringExtra("orgcode");
                assigneeUserId = data.getLongExtra("assignUid", 0);
                oid = data.getLongExtra("orderid", 0);
                topid = data.getLongExtra("topId", 0);
                String detailAddress = data.getStringExtra("tvAddress");
                tv_detail_address.setText(detailAddress);
                String code = data.getStringExtra("placeCode");
                tv_address.setText(Config.get().getAddressByCode(code));
                lat = data.getStringExtra("lat");
                bean.setLatitude(lat);
                lon = data.getStringExtra("lon");
                bean.setLongitude(lon);
                bean.setZone_code(code);
                bean.setZone_id(Long.valueOf(Config.get().getBaseIdByCode(code, 3, Constant.AREA)));
                if (radioClient.isChecked() == true) {
                    if (StringUtils.isEmpty(orderID)) {
                        return;
                    }
                    tv_relate_order.setText(orderID);
                    et_contract.setText(clientName);
                    et_contract_phone.setText(clientPhone);
                }


                break;
            case QUOTAION_REQUEST_DEVICES_CODE:
                if (data.getSerializableExtra("quotedevices") == null) {
                    return;
                }
                QuotationBean.QuoteDevicesBean devicesBean = (QuotationBean.QuoteDevicesBean) data.getSerializableExtra("quotedevices");
                devicesBeanList.add(devicesBean);
                devicesAdapter.notifyDataSetChanged();
                List<Integer> devicesSumList = Stream.of(devicesBeanList).map(beans -> beans.getSum()).toList();
                deviceSum = Stream.of(devicesSumList).mapToInt((x) -> x).sum();
                tv_count_money.setText((((deviceSum + partsSum + serviceSum) / 100)) + "");
                break;
            case QUOTAION_REQUEST_PARTS_CODE:
                if (data.getSerializableExtra("quoteparts") == null) {
                    return;
                }
                QuotationBean.QuotePartsBean partsBean = (QuotationBean.QuotePartsBean) data.getSerializableExtra("quoteparts");
                partsBeanList.add(partsBean);
                partsAdapter.notifyDataSetChanged();
                List<Integer> partSumList = Stream.of(partsBeanList).map(beans -> beans.getSum()).toList();
                partsSum = Stream.of(partSumList).mapToInt((x) -> x).sum();
                tv_count_money.setText((((deviceSum + partsSum + serviceSum) / 100)) + "");
                break;
            case QUOTAION_REQUEST_SERVICES_CODE:
                if (data.getSerializableExtra("quoteservices") == null) {
                    return;
                }
                QuotationBean.QuoteServicesBean servicesBean = (QuotationBean.QuoteServicesBean) data.getSerializableExtra("quoteservices");
                servicesBeanList.add(servicesBean);
                serviceAdapter.notifyDataSetChanged();
                List<Integer> servicesSumList = Stream.of(servicesBeanList).map(beans -> beans.getSum()).toList();
                serviceSum = Stream.of(servicesSumList).mapToInt((x) -> x).sum();
                tv_count_money.setText((((deviceSum + partsSum + serviceSum) / 100)) + "");
                break;
            case QUOTAION_REQUEST_CODE:
                SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
                lat = item.getLatitude().toString();
                lon = item.getLongitude().toString();
                province = item.getProvince();
                city = item.getCity();
                zone = item.getZone();
                contry = item.getAddress();
                tv_address.setText(item.getProvince() + "-" + item.getCity() + "-" + item.getAddress());
                //地图选址 取 显示值
                tv_detail_address.setText(item.getName());
                bean.setZone_code(Config.get().getAreaCodeByName(city, contry));
                bean.setZone_id(Long.valueOf(Config.get().getBaseIdByCode(bean.getZone_code(), 3, Constant.AREA)));
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

}
