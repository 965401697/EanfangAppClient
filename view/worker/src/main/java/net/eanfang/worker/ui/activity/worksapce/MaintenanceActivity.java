package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.MaintenanceBean;
import com.eanfang.biz.model.bean.Message;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.AddMaintenanceDetailActivity;
import net.eanfang.worker.ui.adapter.MaintenanceDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/27  19:40
 * @email houzhongzhou@yeah.net
 * @desc 维修保养
 */

public class MaintenanceActivity extends BaseWorkerActivity {
    public static final String TAG = MaintenanceActivity.class.getSimpleName();
    MaintenanceBean maintenanceBean = new MaintenanceBean();
    List<MaintenanceBean.MaintainDetailsBean> beanList;
    private TextView tv_maintenance_circle;
    private LinearLayout ll_maintenance_circle;
    private ImageView iv_add;
    private RecyclerView rcv_detail;
    private EditText et_contract;
    private EditText et_contract_phone;
    private TextView tv_commit;
    private EditText et_client_company_name;
    private MaintenanceDetailAdapter maintenanceDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maintenance);
        super.onCreate(savedInstanceState);
        initView();
        setTitle("维修保养");
        setLeftBack();
    }

    private void initView() {
        tv_maintenance_circle = findViewById(R.id.tv_maintenance_circle);
        ll_maintenance_circle = findViewById(R.id.ll_maintenance_circle);
        iv_add = findViewById(R.id.iv_add);
        rcv_detail = findViewById(R.id.rcv_detail);
        et_contract = findViewById(R.id.et_contract);
        et_contract_phone = findViewById(R.id.et_contract_phone);
        tv_commit = findViewById(R.id.tv_commit);
        et_client_company_name = findViewById(R.id.et_client_company_name);

        registerListener();
    }

    private void initAdapter() {
        maintenanceDetailAdapter = new MaintenanceDetailAdapter(R.layout.item_quotation_detail, beanList);
        rcv_detail.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv_detail.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail.setAdapter(maintenanceDetailAdapter);
    }


    private void registerListener() {
        ll_maintenance_circle.setOnClickListener((v) -> {
                    PickerSelectUtil.singleTextPicker(this, "", tv_maintenance_circle, GetConstDataUtils.getCycleList());
                }
        );

        iv_add.setOnClickListener((v) -> {
            Intent intent = new Intent(MaintenanceActivity.this, AddMaintenanceDetailActivity.class);
            startActivityForResult(intent, 10002);
        });
        tv_commit.setOnClickListener((v) -> {
                    submit();
                }
        );
    }

    private void submit() {

        String clientCompanyName = et_client_company_name.getText().toString().trim();
        if (TextUtils.isEmpty(clientCompanyName)) {
            showToast("请输入客户姓名");
            return;
        }
        maintenanceBean.setClientCompanyName(clientCompanyName);

        String circle = tv_maintenance_circle.getText().toString().trim();
        if (TextUtils.isEmpty(circle)) {
            showToast("请选择维保周期");
            return;
        }
        //保养周期
        maintenanceBean.setCycle(GetConstDataUtils.getCycleList().indexOf(circle));


        // validate
        String contract = et_contract.getText().toString().trim();
        if (TextUtils.isEmpty(contract)) {
            showToast("联系人不能为空");
            return;
        }

        maintenanceBean.setClientUserName(contract);
        String phone = et_contract_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("联系电话不能为空");
            return;
        }
        maintenanceBean.setClientUserPhone(phone);
        maintenanceBean.setMaintainDetails(beanList);
        String json = JSONObject.toJSONString(maintenanceBean);
        addRepairandProtect(json);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null || data.getSerializableExtra("result") == null) {
            return;
        }
        beanList = new ArrayList<>();
        beanList.add((MaintenanceBean.MaintainDetailsBean) data.getSerializableExtra("result"));
        initAdapter();
        maintenanceDetailAdapter.notifyDataSetChanged();

    }


    private void addRepairandProtect(String json) {
        EanfangHttp.post(NewApiService.POST_ADD_MAINTAIN)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    Bundle bundle = new Bundle();
                    Message message = new Message();
                    message.setMsgContent("下单成功。");
                    message.setTip("确定");
                    bundle.putSerializable("message", message);
                    JumpItent.jump(MaintenanceActivity.this, StateChangeActivity.class, bundle);
                    WorkerApplication.get().closeActivity(MaintenanceActivity.class);
                }));
    }
}
