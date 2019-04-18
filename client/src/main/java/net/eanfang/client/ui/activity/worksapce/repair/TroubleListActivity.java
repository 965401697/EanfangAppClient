package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RepairOpenAreaBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.OrderConfirmActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.adapter.ToRepairAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/4/15
 * @description 故障列表
 */

public class TroubleListActivity extends BaseActivity {

    /**
     * 添加故障明细回调
     */
    private final int ADD_TROUBLE_CALLBACK_CODE = 2;
    /**
     * 故障列表
     */
    private List<RepairBugEntity> beanList = new ArrayList<>();
    /**
     * 故障列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ToRepairAdapter evaluateAdapter = null;


    /**
     * 扫码报修
     */
    private boolean isScanRepair = false;

    private Long mOwnerOrgId;

    /**
     * 直接添加报修
     */

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_list);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        initListener();
    }

    private void initView() {
        setLeftBack();
        setTitle("故障列表");
        setRightTitle("添加");
    }

    private void initListener() {
        setRightTitleOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("beanList", (Serializable) beanList);
            bundle.putBoolean("fromTroubleList", true);
            JumpItent.jump(this, AddTroubleActivity.class, bundle, ADD_TROUBLE_CALLBACK_CODE);
        });
    }

    private void initAdapter() {
        evaluateAdapter = new ToRepairAdapter(R.layout.item_trouble, beanList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                beanList.remove(position);
                evaluateAdapter.notifyDataSetChanged();
            }
        });
        rvList.setAdapter(evaluateAdapter);
        getTroubleInfo(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            /**
             * 右上角添加
             * */
            case ADD_TROUBLE_CALLBACK_CODE:
                getTroubleInfo(data);
                break;
            default:
                break;

        }
    }

    private void getTroubleInfo(Intent data) {
        if (data != null) {
            isScanRepair = data.getBooleanExtra("isScanRepair", false);
            RepairBugEntity repairBugEntity = (RepairBugEntity) data.getSerializableExtra("bean");
            beanList.add(repairBugEntity);
            evaluateAdapter.notifyDataSetChanged();
            mOwnerOrgId = getIntent().getLongExtra("mOwnerOrgId", 0);
        }
    }


    /**
     * 放弃报修
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃报修？", () -> {
            finish();
        }).showDialog();
    }
    /**
     * 选择技师
     */
    private void goSelectWorker() {
        if (!checkInfo()) {
            return;
        }
        // 查找上门费 判断当前城市是否开通  如果没有开通，提示 您报修的城市暂未开通服务。不能继续选技师
        EanfangHttp.post(RepairApi.GET_REAPIR_PAY_PRICE)
                .params("baseDataId", mAreaId)
                .execute(new EanfangCallback<RepairOpenAreaBean>(TroubleListActivity.this, true, RepairOpenAreaBean.class, bean -> {
                    /**
                     * status   0：停用，1启用
                     * */
                    if (bean.getStatus() == 1) {
                        // 扫码已经选择完技师 ，直接确认
                        if (!StringUtils.isEmpty(isScan) && isScan.equals("scaning")) {
                            Intent intent_scan = new Intent(RepairActivity.this, OrderConfirmActivity.class);
                            intent_scan.putExtra("bean", doQrFillBean());
                            intent_scan.putExtra("doorFee", bean.getDoorFee());
                            startActivity(intent_scan);
                        } else {
                            Intent intent = new Intent(RepairActivity.this, SelectWorkerActivity.class);
                            intent.putExtra("bean", fillBean());
                            intent.putExtra("doorFee", bean.getDoorFee());
                            intent.putExtra("mOwnerOrgId", mOwnerOrgId);
                            intent.putStringArrayListExtra("businessIds", (ArrayList<String>) Stream.of(beanList).map(beans -> Config.get().getBusinessIdByCode(beans.getBusinessThreeCode(), 1) + "").distinct().toList());
                            startActivity(intent);
                        }
                    } else {
                        showToast("所在城市暂未开通服务");
                    }
                }));
    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    @OnClick({R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                giveUp();
                break;
            default:
                break;
        }
    }
}
