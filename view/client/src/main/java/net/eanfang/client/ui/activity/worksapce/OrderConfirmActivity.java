package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camera.util.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.base.kit.V;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.yaf.base.entity.PayLogEntity;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.pay.NewPayActivity;
import net.eanfang.client.ui.activity.worksapce.repair.AddTroubleActivity;
import net.eanfang.client.ui.activity.worksapce.repair.FaultDetailActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.activity.worksapce.repair.TroubleListActivity;
import net.eanfang.client.ui.adapter.RepairOrderConfirmAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.base.kit.V.v;


/**
 * 报修订单确认
 * Created by Administrator on 2017/4/6.
 */
public class OrderConfirmActivity extends BaseClientActivity {

    private static final String TAG = "OrderConfirmActivity";
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.sv)
    NestedScrollView scrollView;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_home_type)
    TextView tvHomeType;
    @BindView(R.id.tv_home_address)
    TextView tvHomeAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_projectName)
    TextView tvProjectName;
    @BindView(R.id.tv_projectInfo)
    TextView tvProjectInfo;
    @BindView(R.id.tv_faultNum)
    TextView tvFaultNum;
    @BindView(R.id.iv_faultIcon)
    ImageView ivFaultIcon;
    @BindView(R.id.rl_faultList)
    RelativeLayout rlFaultList;
    private ArrayList<RepairBugEntity> mDataList = new ArrayList<>();
    private LinearLayoutManager llm;

    private RepairOrderEntity repairOrderEntity;
    private PayLogEntity payLogEntity;

    //  头像 名称 公司名
    private String headUrl = "";
    private String workerName = "";
    private String comapnyName = "";

    private int mDoorFee;
    /**
     * 个人信息
     */
    private RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity;
    /**
     * 故障列表默认显示
     */
    private boolean isShowFaultList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        getData();
        initView();
        initData();
        initAdapter();
        registerListener();
    }

    private void initView() {
        llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setNestedScrollingEnabled(false);
        scrollView.smoothScrollTo(0, 20);
        setTitle("工单确认");
        headUrl = getIntent().getStringExtra("headUrl");
        workerName = getIntent().getStringExtra("workName");
        comapnyName = getIntent().getStringExtra("companyName");
        mDoorFee = getIntent().getIntExtra("doorFee", 0);
        repairPersonalInfoEntity = v(() -> (RepairPersonalInfoEntity.ListBean) getIntent().getSerializableExtra("topInfo"));
    }

    private void initData() {
        //姓名
        tvName.setText(repairPersonalInfoEntity.getName());
        // 性别0女1男
        tvSex.setText(repairPersonalInfoEntity.getGender() == 0 ? " (女士) " : " (先生) ");
        // 电话
        tvPhone.setText(repairPersonalInfoEntity.getPhone());
        // 单位
        tvHomeType.setText("[" + repairPersonalInfoEntity.getSelectAddress() + "]");
        tvHomeAddress.setText(repairPersonalInfoEntity.getConmpanyName());
        // 地址
        tvAddress.setText(repairPersonalInfoEntity.getAddress());
        // 是否默认信息
        tvDefault.setVisibility(repairPersonalInfoEntity.getIsDefault() == 1 ? View.VISIBLE : View.GONE);
        // 项目名称
        tvProjectName.setText(repairOrderEntity.getProjectName());
        // 备注信息
        tvProjectInfo.setText(repairOrderEntity.getRemarkInfo());
        GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + headUrl), ivHeader);
        tvRealname.setText(workerName);
        tvCompanyName.setText(comapnyName);

        if (repairOrderEntity.getArriveTimeLimit() >= 0) {
            tvTime.setText(GetConstDataUtils.getArriveList().get(repairOrderEntity.getArriveTimeLimit()));
        }
        tvAddress.setText(Config.get().getAddressByCode(repairOrderEntity.getPlaceCode()) + "\r\n" + repairOrderEntity.getAddress());

        mDataList = (ArrayList<RepairBugEntity>) repairOrderEntity.getBugEntityList();
        if (mDataList != null && mDataList.size() > 0) {
            tvFaultNum.setVisibility(View.VISIBLE);
            tvFaultNum.setText(mDataList.size() + "");
        }
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new RepairOrderConfirmAdapter(R.layout.item_order_confirm, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setNestedScrollingEnabled(false);
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
        evaluateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("faultDeatail", mDataList.get(position));
                JumpItent.jump(OrderConfirmActivity.this, FaultDetailActivity.class, bundle);
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    private void getData() {
        Intent intent = getIntent();
        repairOrderEntity = V.v(() -> (RepairOrderEntity) intent.getSerializableExtra("bean"));
        repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) intent.getSerializableExtra("topInfo");
    }

    private void registerListener() {
        tvComplete.setOnClickListener(new MultiClickListener(this, this::doHttpSubmit));
        setLeftBack((v) -> {
            giveUp();
        });
        rlFaultList.setOnClickListener((v) -> {
            if (!isShowFaultList) {
                mRecyclerView.setVisibility(View.GONE);
                ivFaultIcon.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_up));
                isShowFaultList = true;
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                ivFaultIcon.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_down));
                isShowFaultList = false;
            }
        });
    }

    private void doHttpSubmit() {
        // TODO: 2018/8/30 算要支付的费用 提交给后台
        EanfangHttp.post(RepairApi.ADD_CLIENT_REPAIR)
                .upJson(JSON.toJSONString(repairOrderEntity))
                .execute(new EanfangCallback<RepairOrderEntity>(this, true, RepairOrderEntity.class, (bean) -> {
                    if (bean == null) {
                        return;
                    }
                    //待支付
                    if (Constant.RepairStatus.CREATED.v == bean.getStatus()) {
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
        message.setTip("确定");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);

        finish();
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
        payLogEntity.setOriginPrice(mDoorFee);// 原始价格
        payLogEntity.setPayPrice(mDoorFee);//实际支付价格
        payLogEntity.setReducedPrice(0);// 优惠价格

        EanfangHttp.post(RepairApi.GET_REPAIR_PAY_RECORD)
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    LogUtil.e(TAG, "支付记录执行成功");
//                    Intent intent = new Intent(OrderConfirmActivity.this, PayActivity.class);
                    Intent intent = new Intent(OrderConfirmActivity.this, NewPayActivity.class);
                    intent.putExtra("payLogEntity", payLogEntity);
                    startActivity(intent);
                    closeActivity();
                }));
    }

    private void closeActivity() {
        ClientApplication.get().closeActivity(RepairTypeActivity.class);
        ClientApplication.get().closeActivity(AddTroubleActivity.class);
        ClientApplication.get().closeActivity(TroubleListActivity.class);
        ClientApplication.get().closeActivity(RepairActivity.class);
        ClientApplication.get().closeActivity(SelectWorkerActivity.class);
        ClientApplication.get().closeActivity(WorkerDetailActivity.class);
        finishSelf();
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

    /**
     * 放弃报修
     */

    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃报修？", () -> {
            closeActivity();
        }).showDialog();
    }

}
