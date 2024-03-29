package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.entity.PayLogEntity;
import com.eanfang.biz.model.entity.RepairBugEntity;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.NumberUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.pay.NewPayActivity;
import net.eanfang.client.ui.activity.worksapce.EvaluateWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.PsTroubleDetailActivity;
import net.eanfang.client.ui.activity.worksapce.TroubleDetailActivity;
import net.eanfang.client.ui.activity.worksapce.repair.FaultDetailActivity;
import net.eanfang.client.ui.adapter.OrderConfirmAdapter;
import net.eanfang.client.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.hutool.core.date.DateUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  19:48
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class OrderDetailFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager llm;
    private List<RepairBugEntity> mDataList = new ArrayList<>();
    private TextView tv_company_name;
    private TextView tv_contract_name;
    private TextView tv_contract_phone;
    private TextView tv_address;
    private RecyclerView rv_list;
    private ImageView iv_pic;
    private TextView tv_worker_name;
    private TextView tv_worker_company;
    private ImageView iv_phone;
    private TextView tv_number;
    private TextView tv_feature_time;
    private TextView tv_project_name;
    private TextView tv_repair_remarkinfo;
    // 支付布局
    private LinearLayout llPay;
    // 去支付
    private TextView tvDoPay;
    // 支付金额
    private TextView tvOrderMoney;
    // 确认布局
    private LinearLayout llFinish;
    // 底部右边 按钮
    private TextView tvBottomRight;
    private TextView tv_bottomLeft;

    // userid
    private Long mUserId = ClientApplication.get().getUserId();
    private Long mItemId;
    private int mIsPhoneSolve;
    // 订单状态
    private int mOrderStatus;
    //
    private String mOrderNum = "";
    private Long mOwnerUserId;
    private Long mAssigneeUserId;

    private Long id;
    // 保修人
    private TextView repairContacts;
    // 保修人电话
    private TextView repairContactsPhone;
    // 故障数量
    private TextView mFaultNum;
    // 个人订单
    private LinearLayout mLlOrderPay;
    private LinearLayout mLlOrderMoney;
    // 支付时间
    private TextView mTvPayTime;
    private String mPayType = "";
    // 上门费
    private TextView mTvDoorFee;
    // 订单金额
    private TextView mTvOrderAllPrice;
    // 头像地址
    private String mHeadUrl = "";

    //分享用的必要参数
    private HashMap hashMap = new HashMap();
    private TextView tv_name_desc;
    private LinearLayout ll_worker_info;
    private TextView tv_phone_desc;
    private LinearLayout ll_wait;
    private View line;
    private PayLogEntity payLogEntity;
    private OrderConfirmAdapter orderConfirmAdapter;

    public static OrderDetailFragment getInstance(Long id) {
        OrderDetailFragment sf = new OrderDetailFragment();
        sf.id = id;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void initData(Bundle arguments) {
//        getData();
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setNestedScrollingEnabled(false);
        tv_company_name = findViewById(R.id.tv_company_name);
//--------------------------
        ll_worker_info = findViewById(R.id.ll_worker_info);
        ll_wait = findViewById(R.id.ll_wait);
        line = findViewById(R.id.line);
        tv_name_desc = findViewById(R.id.tv_name_desc);
        tv_phone_desc = findViewById(R.id.tv_phone_desc);
        tv_contract_name = findViewById(R.id.tv_contract_name);
        tv_contract_phone = findViewById(R.id.tv_contract_phone);
        tv_project_name = findViewById(R.id.tv_project_name);
        tv_repair_remarkinfo = findViewById(R.id.tv_repair_remarkinfo);
//----------------------------
        tv_address = findViewById(R.id.tv_address);
        iv_pic = findViewById(R.id.iv_pic);
        tv_worker_name = findViewById(R.id.tv_worker_name);
        tv_worker_company = findViewById(R.id.tv_worker_company);
        iv_phone = findViewById(R.id.iv_phone);
        tv_number = findViewById(R.id.tv_number);
        tv_feature_time = findViewById(R.id.tv_feature_time);
        llPay = findViewById(R.id.ll_pay);
        llFinish = findViewById(R.id.ll_finish);
        tvBottomRight = findViewById(R.id.tv_bottomRight);
        tv_bottomLeft = findViewById(R.id.tv_bottomLeft);
        tvDoPay = findViewById(R.id.tv_doPay);
        tvOrderMoney = findViewById(R.id.tv_orderMoney);
        repairContacts = findViewById(R.id.tv_repairContacts);
        repairContactsPhone = findViewById(R.id.tv_repairContactPhone);
        mFaultNum = findViewById(R.id.tv_faultNum);
        mLlOrderPay = findViewById(R.id.ll_orderPay);
        mLlOrderMoney = findViewById(R.id.ll_orderMoney);
        mTvPayTime = findViewById(R.id.tv_payTime);
        mTvDoorFee = findViewById(R.id.tv_doorFee);
        mTvOrderAllPrice = findViewById(R.id.tv_orderAllPrice);

        orderConfirmAdapter = new OrderConfirmAdapter(R.layout.item_order_confirm, mDataList, "");
        orderConfirmAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(orderConfirmAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void setListener() {
        tvDoPay.setOnClickListener((v) -> {
            if (doCompare(mOwnerUserId, mUserId)) {
                startActivity(new Intent(getActivity(), NewPayActivity.class).putExtra("payLogEntity", payLogEntity));
            }
        });

        // 确认完工  立即评价
        tvBottomRight.setOnClickListener((v) -> {
            if (mOrderStatus == 4) {// 确认完工
                if (doCompare(mOwnerUserId, mUserId)) {
                    doJumpTroubleDetail("待确认", mItemId, mIsPhoneSolve);
                }

            } else if (mOrderStatus == 5) {//立即评价
                if (doCompare(mOwnerUserId, mUserId)) {
                    startActivity(new Intent(getActivity(), EvaluateWorkerActivity.class)
                            .putExtra("ordernum", mOrderNum)
                            .putExtra("workerUid", mAssigneeUserId)
                            .putExtra("orderId", mItemId)
                            .putExtra("avatar", mHeadUrl));
                }
            }
        });
        // 查看完工报告
        tv_bottomLeft.setOnClickListener((v) -> {
            if (doCompare(mOwnerUserId, mUserId)) {
                doJumpTroubleDetail("完成", mItemId, mIsPhoneSolve);
            }
        });

        iv_phone.setOnClickListener(v -> {
            CallUtils.call(getActivity(), iv_phone.getTag().toString());
        });

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_pic) {
                    ArrayList<String> picList = new ArrayList<String>();
                    String[] urls = mDataList.get(position).getPictures().split(",");
                    if (urls.length >= 1) {
                        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[0]);
                    }
                    if (urls.length >= 2) {
                        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[1]);
                    }
                    if (urls.length >= 3) {
                        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[2]);
                    }
                    if (picList.size() == 0) {
//                        showToast("当前没有图片");
                        return;
                    }
                    ImagePerviewUtil.perviewImage(getActivity(), picList);
                }
//                else if (view.getId() == R.id.ll_item) {// 展开收起
//                    View secondItem = llm.findViewByPosition(position).findViewById(R.id.second_item);
//                    if (secondItem.getVisibility() == View.VISIBLE) {
//                        secondItem.setVisibility(View.GONE);
//                    } else {
//                        secondItem.setVisibility(View.VISIBLE);
//                    }
//                }
            }
        });
        orderConfirmAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("faultDeatail", mDataList.get(position));
                JumpItent.jump(getActivity(), FaultDetailActivity.class, bundle);
            }
        });


    }

    private void initAdapter() {
        if (mDataList.size() == 0) {
            mFaultNum.setVisibility(View.GONE);
            return;
        }
        orderConfirmAdapter.setNewData(mDataList);
        mFaultNum.setText(mDataList.size() + "");


    }

    private void getData() {
        EanfangHttp.get(RepairApi.GET_REPAIR_DETAIL)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<RepairOrderEntity>(getActivity(), true, RepairOrderEntity.class, (bean) -> {
                    if (bean == null) {
                        return;
                    }
                    //================================================================

                    //初始化支付对象
                    payLogEntity = new PayLogEntity();
                    payLogEntity.setOrderId(bean.getId());
                    payLogEntity.setOrderNum(bean.getOrderNum());
                    payLogEntity.setOrderType(Constant.OrderType.REPAIR.ordinal());
                    payLogEntity.setAssigneeUserId(bean.getOwnerUserId());
                    payLogEntity.setAssigneeOrgCode(bean.getOwnerOrgCode());
                    payLogEntity.setAssigneeTopCompanyId(bean.getOwnerTopCompanyId());
                    if (bean.getPayLogEntity() != null) {
                        payLogEntity.setPayPrice(bean.getPayLogEntity().getPayPrice());
                    } else {
                        payLogEntity.setPayPrice(1);
                    }
                    //==================================================================

                    hashMap.put("id", String.valueOf(bean.getId()));
                    if (bean.getBugEntityList() != null && bean.getBugEntityList().size() > 0) {
                        hashMap.put("picUrl", bean.getBugEntityList().get(0).getPictures().split(",")[0]);
                    }
                    hashMap.put("orderNum", bean.getOrderNum());
                    hashMap.put("creatTime", DateUtil.date(bean.getCreateTime()).toString());
                    if (bean.getAssigneeUser() != null) {
                        hashMap.put("workerName", bean.getAssigneeUser().getAccountEntity().getRealName());
                    }

                    hashMap.put("status", String.valueOf(bean.getStatus()));
                    hashMap.put("shareType", "1");


                    tv_company_name.setText(V.v(() -> bean.getRepairCompany()));//单位名称
                    if (bean.getAssigneeUser() != null) {
                        ll_worker_info.setVisibility(View.VISIBLE);
                        tv_name_desc.setVisibility(View.VISIBLE);
                        tv_phone_desc.setVisibility(View.VISIBLE);
                        tv_contract_name.setVisibility(View.VISIBLE);
                        tv_contract_phone.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);

                        ll_wait.setVisibility(View.GONE);

                        tv_contract_name.setText(V.v(() -> bean.getOwnerUser().getAccountEntity().getRealName()));//联系人
                        tv_contract_phone.setText(V.v(() -> bean.getOwnerUser().getAccountEntity().getMobile()));// 联系人手机号
                    }
                    // 回复时效
//                    tv_time_limit.setText(V.v(() -> GetConstDataUtils.getArriveList().get(bean.getArriveTimeLimit())));
//                    tv_address.setText(V.v(() -> Config.get().getAddressByCode(bean.getPlaceCode()) + "\n" + bean.getAddress()));
                    tv_address.setText(V.v(() -> Config.get().getAddressByCode(bean.getPlaceCode()) + bean.getAddress()));
//                    if (bean.getBookTime() != null) {
//                        tv_time.setText(V.v(() -> Optional.ofNullable(DateUtil.date(bean.getBookTime())).orElse("--")));
//                    } else {
//                        tv_time.setText("--");
//                    }

                    // 大于0 是公司  小于0 是个人
                    if (bean.getOwnerCompanyId() <= 0) {
                        mLlOrderMoney.setVisibility(View.VISIBLE);
                        mLlOrderPay.setVisibility(View.VISIBLE);
                    } else {
                        mLlOrderMoney.setVisibility(View.GONE);
                        mLlOrderPay.setVisibility(View.GONE);
                    }

                    // 支付金额  GetConstDataUtils.getPayTypeList().get(0);
                    if (bean.getPayLogEntity() != null) {
                        // 支付金额
                        if (bean.getPayLogEntity().getPayPrice() != null) {
                            tvOrderMoney.setText("¥" + NumberUtil.getEndTwoNum(bean.getPayLogEntity().getPayPrice() / 100.00));
                            mTvDoorFee.setText(NumberUtil.getEndTwoNum(bean.getPayLogEntity().getPayPrice() / 100.00));
                            mTvOrderAllPrice.setText(NumberUtil.getEndTwoNum(bean.getPayLogEntity().getPayPrice() / 100.00));
                        }
                        // 支付方式
                        if (bean.getPayLogEntity().getPayType() != null) {
                            mPayType = GetConstDataUtils.getPayTypeList().get(bean.getPayLogEntity().getPayType());
                        }
                        // 支付时间
                        if (bean.getPayLogEntity().getPayTime() != null) {
                            mTvPayTime.setText(DateUtil.date(bean.getPayLogEntity().getPayTime()).toString());
                        }
                    } else {
                        llPay.setVisibility(View.GONE);
                    }
                    repairContacts.setText(V.v(() -> bean.getRepairContacts()));
                    repairContactsPhone.setText(V.v(() -> bean.getRepairContactPhone()));
                    tv_number.setText(V.v(() -> bean.getOrderNum()));
                    tv_feature_time.setText(V.v(() -> DateUtil.date(bean.getCreateTime())).toString());

                    if (!TextUtils.isEmpty(bean.getProjectName())) {
                        tv_project_name.setText(bean.getProjectName());
                    }
                    if (!TextUtils.isEmpty(bean.getRemarkInfo())) {
                        tv_repair_remarkinfo.setText(bean.getRemarkInfo());
                    }
//                    tv_money.setText(bean.getTotalfee() + "");
//                    tv_alipay.setText(bean.getPaytype());

                    //      获取：是否电话解决（0：未解决，1：已解决）
//                    if (bean.getIsPhoneSolve() == null || bean.getIsPhoneSolve() == 0) {
//                        tv_phone_solve.setText("否");
//                    } else {
//                        tv_phone_solve.setText("是");
//                    }
                    mItemId = bean.getId();
                    mIsPhoneSolve = bean.getIsPhoneSolve();
                    mOrderStatus = bean.getStatus();
                    mOrderNum = bean.getOrderNum();
                    mOwnerUserId = bean.getOwnerUserId();
                    //客户端
                    if (bean.getAssigneeUser() != null) {
                        mHeadUrl = bean.getAssigneeUser().getAccountEntity().getAvatar();
                        GlideUtil.intoImageView(getActivity(), BuildConfig.OSS_SERVER + Uri.parse(mHeadUrl), iv_pic);
                        tv_worker_name.setText(V.v(() -> bean.getAssigneeUser().getAccountEntity().getRealName()));
                        tv_worker_company.setText(V.v(() -> bean.getAssigneeOrg().getBelongCompany().getOrgName()));
                        iv_phone.setTag(V.v(() -> bean.getAssigneeUser().getAccountEntity().getMobile()));
                    }
                    if (mOrderStatus == 0) {// 待付款
                        llPay.setVisibility(View.VISIBLE);
                        llFinish.setVisibility(View.GONE);
                        // 支付方式布局
                        mLlOrderPay.setVisibility(View.GONE);
                        mLlOrderMoney.setVisibility(View.GONE);
                    } else if (mOrderStatus == 4) {// 待确认
                        llFinish.setVisibility(View.VISIBLE);
                        llPay.setVisibility(View.GONE);
                    } else if (mOrderStatus == 5) {// 订单完成
                        llFinish.setVisibility(View.VISIBLE);
                        llPay.setVisibility(View.GONE);
                        tvBottomRight.setText("立即评价");
                        mAssigneeUserId = bean.getAssigneeUserId();
                        if (bean.getWorkerEvaluateId() == null || bean.getWorkerEvaluateId().longValue() <= 0) {
                            tvBottomRight.setVisibility(View.VISIBLE);
                        } else {
                            tvBottomRight.setVisibility(View.GONE);
                        }
                    } else {
                        llFinish.setVisibility(View.GONE);
                        llPay.setVisibility(View.GONE);
                    }
                    mDataList = bean.getBugEntityList();

                    initAdapter();

                }));
    }

    public HashMap getHashMap() {
        return hashMap;
    }


    public boolean doCompare(Long assingerUserId, Long userId) {

        if (assingerUserId.equals(userId)) {
            return true;
        }
        showToast("当前无权限操作订单");
        return false;
    }

    private void doJumpTroubleDetail(String mStatus, Long busRepairOrderId, Integer isPhoneSolve) {
        Bundle bundle = new Bundle();
        bundle.putLong("busRepairOrderId", busRepairOrderId);
        bundle.putString("status", mStatus);
        bundle.putBoolean("isVisible", false);
        if (isPhoneSolve == 0) {
            // 电话未解决
            JumpItent.jump(getActivity(), TroubleDetailActivity.class, bundle);
        } else {
            // 电话解决
            JumpItent.jump(getActivity(), PsTroubleDetailActivity.class, bundle);
        }
    }
}
