package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.TransferLogEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:18
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PutUpOrderActivity extends BaseWorkerActivity {
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_cause)
    TextView tvCause;
    @BindView(R.id.ll_cause)
    LinearLayout llCause;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;
    @BindView(R.id.ll_to_worker)
    LinearLayout llToWorker;
    @BindView(R.id.tv_put_up_apply)
    TextView tvPutUpApply;
    @BindView(R.id.rv_put_up_history)
    RecyclerView rvPutUpHistory;
    @BindView(R.id.et_dec)
    EditText etDec;
    TransferLogEntity transferLogEntity = new TransferLogEntity();
    private BughandleConfirmEntity bughandleConfirmEntity;
    private String companyName;
    private Long companyId;
    private List<String> businessId;
    private List<TransferLogEntity> mDataList;
    private Long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_up_order);
        ButterKnife.bind(this);
        initView();
        setTitle("挂单处理");

    }

    private void initView() {

        companyName = getIntent().getStringExtra("companyName");
        companyId = getIntent().getLongExtra("companyId", 0);
        businessId = getIntent().getStringArrayListExtra("businessId");
        orderId = getIntent().getLongExtra("orderId", 0);
        tvCompany.setText(companyName);
        llCause.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvCause, GetConstDataUtils.getTransferCauseList());
        });

        llToWorker.setOnClickListener((v) -> {
            Intent intent = new Intent(PutUpOrderActivity.this, PutUpSelectWorkerActivity.class);
            intent.putExtra("companyId", companyId);
            intent.putExtra("businessId", (ArrayList<String>) businessId);
            startActivityForResult(intent, 10091);
        });


        tvPutUpApply.setOnClickListener(v -> doHttp());
    }

    private BughandleConfirmEntity fillBean() {
        bughandleConfirmEntity = (BughandleConfirmEntity) getIntent().getSerializableExtra("bean");
//        mDataList = bughandleConfirmEntity.getTransferLogEntityList();
        mDataList=new ArrayList<>();
        transferLogEntity.setCause(GetConstDataUtils.getTransferCauseList().indexOf(tvCause.getText().toString().trim()));
        transferLogEntity.setOrderId(orderId);
        transferLogEntity.setOriginalUserId(EanfangApplication.getApplication().getUserId());
        transferLogEntity.setRemark(etDec.getText().toString().trim());
        transferLogEntity.setOrderType(0);
        mDataList.add(transferLogEntity);
        bughandleConfirmEntity.setTransferLogEntityList(mDataList);
        return bughandleConfirmEntity;
    }

    private void doHttp() {
        String json = JSONObject.toJSONString(fillBean());
        EanfangHttp.post(RepairApi.GET_REPAIR_TRANFER)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        EanfangApplication.get().closeActivity(FillRepairInfoActivity.class.getName());
                        showToast("挂单申请已经提交");
                        finishSelf();
                    });
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        transferLogEntity.setReceiveUserId(data.getLongExtra("id", 0));
        tvWorkerName.setText(data.getStringExtra("name"));
        //        mDataList.add(transferLogEntity);


    }
}

