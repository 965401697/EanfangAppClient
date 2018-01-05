package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.listener.MultiClickListener;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.pay.PayActivity;
import net.eanfang.client.ui.adapter.RepairOrderConfirmAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.Message;
import net.eanfang.client.ui.model.repair.RepairBugEntity;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 报修订单确认
 * Created by Administrator on 2017/4/6.
 */

public class OrderConfirmActivity extends BaseActivity {

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
    private View header;
    private String business;
    private String id;
    private String ordernum;
    private String status;
    private String doorfee;
    private RepairOrderEntity repairOrderEntity;

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
        repairOrderEntity = (RepairOrderEntity) intent.getSerializableExtra("bean");
        id = intent.getStringExtra("id");
    }

    private void registerListener() {
        btnComplete.setOnClickListener(new MultiClickListener(this, this::doHttpSubmit));
    }

    private void doHttpSubmit() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(repairOrderEntity);

        EanfangHttp.post(RepairApi.ADD_CLIENT_REPAIR)
                .upJson(jsonString)
                .execute(new EanfangCallback<JSONObject>(this, true,JSONObject.class,(bean)->{
                    submitSuccess();
                }));
    }


    private void submitSuccess(/*OrderReturnBean bean*/) {
//        ordernum = bean.getOrdernum();
//        status = bean.getStatus();
//        doorfee = bean.getDoorfee();
        showToast("下单成功");

        if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getIsVerify() == 1) {
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
        } else {
            Intent intent = new Intent(OrderConfirmActivity.this, PayActivity.class);
            intent.putExtra("ordernum", ordernum);
            intent.putExtra("doorfee", doorfee);
            intent.putExtra("orderType", "报修");
            startActivity(intent);
        }
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
//        tvCompany.setText();
        tvTime.setText(Config.getConfig().getConstBean().getConst().get(Constant.ARRIVE_LIMIT).get(repairOrderEntity.getArriveTimeLimit()));
        tvAddress.setText(Config.getConfig().getAddress(repairOrderEntity.getPlaceCode())+"-"+repairOrderEntity.getAddress());

        mDataList = (ArrayList<RepairBugEntity>) repairOrderEntity.getBugEntityList();
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new RepairOrderConfirmAdapter(R.layout.item_order_confirm, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (view.getId() == R.id.iv_pic) {
//                    ArrayList<String> picList = new ArrayList<String>();
//                    if (!StringUtils.isEmpty(bean.getBeanList().get(position).getBugpic1())) {
//                        picList.add(bean.getBeanList().get(position).getBugpic1());
//                    }
//                    if (!StringUtils.isEmpty(bean.getBeanList().get(position).getBugpic2())) {
//                        picList.add(bean.getBeanList().get(position).getBugpic2());
//                    }
//                    if (!StringUtils.isEmpty(bean.getBeanList().get(position).getBugpic3())) {
//                        picList.add(bean.getBeanList().get(position).getBugpic3());
//                    }
//                    if (picList.size() == 0) {
//                        showToast("当前没有图片");
//                        return;
//                    }
//                    ImagePerviewUtil.perviewImage(OrderConfirmActivity.this, picList);
//                }
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
