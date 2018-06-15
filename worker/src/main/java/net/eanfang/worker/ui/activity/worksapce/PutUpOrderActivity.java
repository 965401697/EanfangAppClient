package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.TransferLogEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.PutUpOrderRecordAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:18
 * @email houzhongzhou@yeah.net
 * @desc 转单
 */

public class PutUpOrderActivity extends BaseWorkerActivity {

    public static final int REQUEST_CODE = 100;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;

    @BindView(R.id.ll_to_worker)
    LinearLayout llToWorker;

    @BindView(R.id.tv_put_up_apply)
    TextView tvPutUpApply;

    @BindView(R.id.rv_put_up_history)
    RecyclerView rvPutUpHistory;
    TransferLogEntity transferLogEntity = new TransferLogEntity();
    // 转单原因
    @BindView(R.id.tag_reason)
    TagFlowLayout tagReason;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.tv_num)
    TextView tvNum;


    private BughandleConfirmEntity bughandleConfirmEntity;
    private Long companyId;
    private List<String> businessId;
    private List<TransferLogEntity> mDataList = new ArrayList<>();
    private List<TransferLogEntity> mNewPutUpList = new ArrayList<>();
    private Long orderId;

    private PutUpOrderRecordAdapter putUpOrderRecordAdapter;

    // 转单原因
    private int mOrderReason = 100;

    private int maxWordsNum = 200; //输入限制的最大字数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_up_order);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        setTitle("挂单处理");
        setLeftBack();
        companyId = getIntent().getLongExtra("companyId", 0);
        businessId = getIntent().getStringArrayListExtra("businessId");
        orderId = getIntent().getLongExtra("orderId", 0);

        rvPutUpHistory.setLayoutManager(new LinearLayoutManager(this));
        rvPutUpHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvPutUpHistory.setNestedScrollingEnabled(false);
    }

    private void initData() {
        bughandleConfirmEntity = (BughandleConfirmEntity) getIntent().getSerializableExtra("bean");
        mDataList = bughandleConfirmEntity.getTransferLogEntityList();
        addReapirResultMode(GetConstDataUtils.getTransferCauseList());

        llToWorker.setOnClickListener((v) -> {
            Intent intent = new Intent(PutUpOrderActivity.this, PutUpSelectWorkerActivity.class);
            intent.putExtra("companyId", companyId);
            intent.putExtra("businessId", (ArrayList<String>) businessId);
            startActivityForResult(intent, REQUEST_CODE);
        });

        tvPutUpApply.setOnClickListener(v -> doVerify());

        etRemarks.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(s.length() + "/" + maxWordsNum);
                selectionStart = etRemarks.getSelectionStart();
                selectionEnd = etRemarks.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
//                    toast("不能超过" + maxWordsNum + "个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etRemarks.setText(s);
                    etRemarks.setSelection(tempSelection); //设置光标在最后
                }
            }
        });
        if (mDataList != null) {
            putUpOrderRecordAdapter = new PutUpOrderRecordAdapter(R.layout.layout_item_put_up_order, mDataList);
            putUpOrderRecordAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            rvPutUpHistory.setAdapter(putUpOrderRecordAdapter);
        }

    }

    public void doVerify() {
        if (mOrderReason == 100) {
            showToast("请选择原因");
            return;
        }
        if (StringUtils.isEmpty(etRemarks.getText().toString().trim())) {
            showToast("请选择技师");
            return;
        }
        doHttp();
    }

    private BughandleConfirmEntity fillBean() {
        transferLogEntity.setCause(mOrderReason);
        transferLogEntity.setOrderId(orderId);
        transferLogEntity.setOriginalUserId(EanfangApplication.getApplication().getUserId());
        transferLogEntity.setRemark(etRemarks.getText().toString().trim());
        transferLogEntity.setOrderType(0);

        mNewPutUpList.add(transferLogEntity);
        bughandleConfirmEntity.setTransferLogEntityList(mNewPutUpList);
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
                        EanfangApplication.get().closeActivity(FillRepairInfoActivity.class.getName());
                    });
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            transferLogEntity.setReceiveUserId(data.getLongExtra("id", 0));
            tvWorkerName.setText(data.getStringExtra("name"));
        }


    }

    public void addReapirResultMode(List<String> stringList) {
        if (tagReason.getSelectedList().size() > 0) {
            tagReason.getSelectedList().clear();
            tagReason.getAdapter().notifyDataChanged();
        }
        tagReason.setAdapter(new TagAdapter<String>(stringList) {
            @Override
            public View getView(FlowLayout parent, int position, String mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(PutUpOrderActivity.this).inflate(R.layout.layout_trouble_result_item, tagReason, false);
                tv.setText(mrepairResult);
                return tv;
            }
        });
        tagReason.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    String str = selectPosSet.toString().substring(1, selectPosSet.toString().length() - 1);
                    int position = Integer.parseInt(str);
                    mOrderReason = position;
                }
            }
        });
    }
}

