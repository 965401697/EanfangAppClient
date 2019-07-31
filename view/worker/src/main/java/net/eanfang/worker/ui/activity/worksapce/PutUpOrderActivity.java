package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.biz.model.entity.TransferLogEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.BughandleConfirmEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.repair.finishwork.FillRepairInfoActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;


/**
 * @author guanluocang
 * @data 2018/11/27
 * @description 转单
 */


public class PutUpOrderActivity extends BaseWorkerActivity {

    public static final int REQUEST_CODE = 100;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;

    @BindView(R.id.ll_to_worker)
    LinearLayout llToWorker;

    @BindView(R.id.tv_put_up_apply)
    TextView tvPutUpApply;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_reason)
    TextView tvOrderReason;
    @BindView(R.id.ll_hang)
    LinearLayout llHang;
    @BindView(R.id.tv_no_history)
    TextView tvNoHistory;

    // 新增转单
    private TransferLogEntity transferLogEntity = new TransferLogEntity();
    // 转单记录
    private TransferLogEntity transferLogEntityHistory = new TransferLogEntity();
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
    private Long orderId;


    // 转单原因
    private int mOrderReason = 100;

    private int maxWordsNum = 200; //输入限制的最大字数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_put_up_order);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    private void initView() {
        setTitle("转单处理");
        setLeftBack();
        companyId = getIntent().getLongExtra("companyId", 0);
        businessId = getIntent().getStringArrayListExtra("businessId");
        orderId = getIntent().getLongExtra("orderId", 0);

    }

    private void initData() {
        bughandleConfirmEntity = (BughandleConfirmEntity) getIntent().getSerializableExtra("bean");
        transferLogEntityHistory = bughandleConfirmEntity.getTransferLogEntity();
        addReapirResultMode(GetConstDataUtils.getTransferCauseList());
        getHistory(transferLogEntityHistory);

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

    }

    public void doVerify() {
        if (mOrderReason == 100) {
            showToast("请选择原因");
            return;
        }
        if (StrUtil.isEmpty(etRemarks.getText().toString().trim())) {
            showToast("请输入原因描述");
            return;
        }
        if (transferLogEntity.getReceiveUserId() == null) {
            showToast("请选择技师");
            return;
        }
        doHttp();
    }

    private BughandleConfirmEntity fillBean() {
        transferLogEntity.setCause(mOrderReason);
        transferLogEntity.setOrderId(orderId);
        transferLogEntity.setOriginalUserId(WorkerApplication.get().getUserId());
        transferLogEntity.setRemark(etRemarks.getText().toString().trim());
        transferLogEntity.setOrderType(0);
        bughandleConfirmEntity.setTransferLogEntity(transferLogEntity);
        return bughandleConfirmEntity;
    }

    private void doHttp() {

        String json = JSONObject.toJSONString(fillBean());
        EanfangHttp.post(RepairApi.GET_REPAIR_TRANFER)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        WorkerApplication.get().closeActivity(FillRepairInfoActivity.class);
                        showToast("转单申请已经提交");
                        finishSelf();
                        WorkerApplication.get().closeActivity(FillRepairInfoActivity.class);
                    });
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE) {// 选择技师
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
                } else {
                    mOrderReason = 100;
                }
            }

        });
    }

    public void getHistory(TransferLogEntity transferLogEntity) {
        if (transferLogEntity == null) {
            llHang.setVisibility(View.GONE);
            tvNoHistory.setVisibility(View.VISIBLE);
            return;
        }
        llHang.setVisibility(View.VISIBLE);
        tvNoHistory.setVisibility(View.GONE);
        GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + transferLogEntity.getOriginalUserEntity().getAccountEntity().getAvatar()),ivHeader);
        tvOrderNum.setText(transferLogEntity.getOrderNum() + "");
        tvOrderTime.setText(DateUtil.date(transferLogEntity.getCreateTime()).toString());
        tvOrderReason.setText(GetConstDataUtils.getTransferCauseList().get(transferLogEntity.getCause()));
    }


}

