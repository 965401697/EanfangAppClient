package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.listener.MultiClickListener;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.RepairOrderConfirmAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.AddTroubleBean;
import net.eanfang.client.ui.model.OrderReturnBean;
import net.eanfang.client.ui.model.RepairOrderBean;
import net.eanfang.client.ui.model.ToRepairBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;



/**
 * 报修订单确认
 * Created by Administrator on 2017/4/6.
 */

public class OrderConfirmActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<AddTroubleBean> mDataList = new ArrayList<>();
    private LinearLayoutManager llm;
    private View header;
    private ScrollView mScrollView;
    private ToRepairBean bean;
    private TextView tv_number;
    private TextView tv_contact;
    private TextView tv_phone;
    private TextView tv_company;
    private TextView tv_time;
    private TextView tv_address;
    private String business;
    private Button btn_complete;
    private String id;
    private String ordernum;
    private String status;
    private String doorfee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
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
        bean = (ToRepairBean) intent.getSerializableExtra("bean");
        id = intent.getStringExtra("id");
    }

    private void registerListener() {
        btn_complete.setOnClickListener(new MultiClickListener(this, this::doHttpSubmit));
    }

    private void doHttpSubmit() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(fillData());

        EanfangHttp.post(ApiService.ORDER_CONFIRM)
                .params("json", jsonString)
                .execute(new EanfangCallback<OrderReturnBean>(this, true) {
                    @Override
                    public void onSuccess(OrderReturnBean bean) {
                        submitSuccess(bean);
                    }
                });
    }

    private RepairOrderBean fillData() {
        RepairOrderBean repairOrderBean = new RepairOrderBean();
//        repairOrderBean.setAccount(user().getAccount());
//        repairOrderBean.setClientcompanyuid(user().getCompanyId());
        repairOrderBean.setCompanyname(bean.getCompany());
        repairOrderBean.setProvince(bean.getProvince());
        repairOrderBean.setCity(bean.getCity());
        repairOrderBean.setZone(bean.getArea());
        repairOrderBean.setDetailplace(bean.getDetailAddress());
        repairOrderBean.setLatitude(bean.getLatitude());
        repairOrderBean.setLongitude(bean.getLongitude());
        repairOrderBean.setClientconnector(bean.getName());
        repairOrderBean.setClientphone(bean.getPhone());
        repairOrderBean.setArrivetime(bean.getTime());
        repairOrderBean.setBugone(bean.getBugOneUid());
        repairOrderBean.setBugonename(bean.getBusiness());
        repairOrderBean.setWorkeruid(id);
        repairOrderBean.setBugdetails(bean.getBeanList());
        return repairOrderBean;
    }

    private void submitSuccess(OrderReturnBean bean) {
        ordernum = bean.getOrdernum();
        status = bean.getStatus();
        doorfee = bean.getDoorfee();
        showToast("下单成功");
//
//        if (!StringUtils.isEmpty(user().getCompanyverify()) && "1".equals(user().getCompanyverify())) {
//            Intent intent = new Intent(OrderConfirmActivity.this, StateChangeActivity.class);
//            Bundle bundle = new Bundle();
//            Message message = new Message();
//            message.setTitle("下单成功");
//            message.setMsgTitle("您的报修单已下单成功");
//            message.setMsgContent("稍后技师会和您取得联系,请保持电话畅通。");
//            message.setTip("");
//            message.setShowOkBtn(true);
//            message.setShowLogo(true);
//            bundle.putSerializable("message", message);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(OrderConfirmActivity.this, PayActivity.class);
//            intent.putExtra("ordernum", ordernum);
//            intent.putExtra("doorfee", doorfee);
//            intent.putExtra("orderType", "报修");
//            startActivity(intent);
//        }
        EanfangApplication.get().closeActivity(RepairActivity.class.getName());
        EanfangApplication.get().closeActivity(SelectWorkerActivity.class.getName());
        EanfangApplication.get().closeActivity(WorkerDetailActivity.class.getName());
        finish();

    }


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mScrollView = (ScrollView) findViewById(R.id.sv);
        mScrollView.smoothScrollTo(0, 20);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_address = (TextView) findViewById(R.id.tv_address);
        btn_complete = (Button) findViewById(R.id.btn_complete);


    }

    private void initData() {
        tv_contact.setText(bean.getName());
        tv_phone.setText(bean.getPhone());
        tv_company.setText(bean.getCompany());
        tv_time.setText(bean.getTime());
        tv_address.setText(bean.getProvince() + bean.getCity() + bean.getArea() + bean.getDetailAddress());

        mDataList = (ArrayList<AddTroubleBean>) bean.getBeanList();
        business = bean.getBusiness();
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new RepairOrderConfirmAdapter(R.layout.item_order_confirm, mDataList, business);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_pic) {
                    ArrayList<String> picList = new ArrayList<String>();
                    if (!StringUtils.isEmpty(bean.getBeanList().get(position).getBugpic1())) {
                        picList.add(bean.getBeanList().get(position).getBugpic1());
                    }
                    if (!StringUtils.isEmpty(bean.getBeanList().get(position).getBugpic2())) {
                        picList.add(bean.getBeanList().get(position).getBugpic2());
                    }
                    if (!StringUtils.isEmpty(bean.getBeanList().get(position).getBugpic3())) {
                        picList.add(bean.getBeanList().get(position).getBugpic3());
                    }
                    if (picList.size() == 0) {
                        showToast("当前没有图片");
                        return;
                    }
                    ImagePerviewUtil.perviewImage(OrderConfirmActivity.this, picList);
                }
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
