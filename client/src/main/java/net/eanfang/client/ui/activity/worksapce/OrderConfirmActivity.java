package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.Message;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.V;
import com.yaf.base.entity.PayLogEntity;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.pay.PayActivity;
import net.eanfang.client.ui.adapter.RepairOrderConfirmAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 报修订单确认
 * Created by Administrator on 2017/4/6.
 */
public class OrderConfirmActivity extends BaseClientActivity {

    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.sv)
    ScrollView scrollView;
    private ArrayList<RepairBugEntity> mDataList = new ArrayList<>();
    private LinearLayoutManager llm;

    private RepairOrderEntity repairOrderEntity;
    private PayLogEntity payLogEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        getData();
        initView();
        initData();
        initAdapter();
        registerListener();
        setLeftBack();

        setTitle("订单确认");
    }

    private void getData() {
        Intent intent = getIntent();
        repairOrderEntity = V.v(()->(RepairOrderEntity) intent.getSerializableExtra("bean"));
    }

    private void registerListener() {
        btnComplete.setOnClickListener(new MultiClickListener(this, this::doHttpSubmit));
    }

    private void doHttpSubmit() {
        EanfangHttp.post(RepairApi.ADD_CLIENT_REPAIR)
                .upJson(JSON.toJSONString(repairOrderEntity))
                .execute(new EanfangCallback<RepairOrderEntity>(this, true, RepairOrderEntity.class, (bean) -> {
                    //待支付
                    if (Constant.RepairStatus.CREATED.v == bean.getStatus().intValue()) {
                        payment(bean);
                    } else {
                        submitSuccess();
                    }
                }));
    }


    private void submitSuccess(/*OrderReturnBean bean*/) {
//        ordernum = bean.getOrdernum();
//        status = bean.getStatus();
//        doorfee = bean.getDoorfee();
//        showToast("下单成功");
        Intent intent = new Intent(OrderConfirmActivity.this, StateChangeActivity.class);
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setTitle("下单成功");
        message.setMsgTitle("您的报修单已下单成功");
        message.setMsgContent("稍后技师会和您取得联系,请保持电话畅通。");
        message.setTip("");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);

        closeActivity();


    }

    /**
     * 支付
     *
     * @param orderEntity
     */
    private void payment(RepairOrderEntity orderEntity) {

        payLogEntity = new PayLogEntity();
        payLogEntity.setOrderId(orderEntity.getId());
        payLogEntity.setOrderNum(orderEntity.getOrderNum());
        payLogEntity.setOrderType(Constant.OrderType.REPAIR.ordinal());
        payLogEntity.setAssigneeUserId(orderEntity.getOwnerUserId());
        payLogEntity.setAssigneeOrgCode(orderEntity.getOwnerOrgCode());
        payLogEntity.setAssigneeTopCompanyId(orderEntity.getOwnerTopCompanyId());

        //查询上门费
        payLogEntity.setOriginPrice(100);

        Intent intent = new Intent(OrderConfirmActivity.this, PayActivity.class);
        intent.putExtra("payLogEntity", payLogEntity);
        startActivity(intent);

        closeActivity();
    }

    private void closeActivity() {
        EanfangApplication.get().closeActivity(RepairActivity.class.getName());
        EanfangApplication.get().closeActivity(SelectWorkerActivity.class.getName());
        EanfangApplication.get().closeActivity(WorkerDetailActivity.class.getName());
        finish();
    }


    private void initView() {
        llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        scrollView.smoothScrollTo(0, 20);

    }

    private void initData() {
        tvContact.setText(repairOrderEntity.getRepairContacts());
        tvPhone.setText(repairOrderEntity.getRepairContactPhone());
        tvCompany.setText(repairOrderEntity.getRepairCompany());
        if (repairOrderEntity.getArriveTimeLimit() >= 0) {
            tvTime.setText(GetConstDataUtils.getArriveList().get(repairOrderEntity.getArriveTimeLimit()));
        }
        tvAddress.setText(Config.get().getAddressByCode(repairOrderEntity.getPlaceCode()) + "\r\n" + repairOrderEntity.getAddress());

        mDataList = (ArrayList<RepairBugEntity>) repairOrderEntity.getBugEntityList();
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new RepairOrderConfirmAdapter(R.layout.item_order_confirm, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_item) {
                    View secondItem = llm.findViewByPosition(position).findViewById(R.id.second_item);
                    if (secondItem.getVisibility() == View.VISIBLE) {
                        secondItem.setVisibility(View.GONE);
                    } else {
                        secondItem.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

}
