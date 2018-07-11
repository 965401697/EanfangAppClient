package net.eanfang.worker.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.V;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.FaultDetailActivity;
import net.eanfang.worker.ui.adapter.OrderConfirmAdapter;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private android.support.v7.widget.RecyclerView rv_list;
    private com.facebook.drawee.view.SimpleDraweeView iv_pic;
    private TextView tv_worker_name;
    private TextView tv_worker_company;
    private ImageView iv_phone;
    private TextView tv_number;
    private TextView tv_feature_time;
    // 故障明细数量
    private TextView mTvFaultNum;
    // 保修人
    private TextView repairContacts;
    // 保修人电话
    private TextView repairContactsPhone;
    // 个人订单
    private LinearLayout mLlOrderPay;
    private LinearLayout mLlOrderMoney;
    // 支付方式
    private TextView mTvPayType;
    // 支付时间
    private TextView mTvPayTime;
    private String mPayType = "";
    // 上门费
    private TextView mTvDoorFee;
    // 订单金额
    private TextView mTvOrderAllPrice;

    // 订单状态
    private int mOrderStatus;
    private Long id;

    //分享用的必要参数
    private HashMap hashMap = new HashMap();

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
        getData();
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setNestedScrollingEnabled(false);

        tv_company_name = findViewById(R.id.tv_company_name);
        tv_contract_name = findViewById(R.id.tv_contract_name);
        tv_contract_phone = findViewById(R.id.tv_contract_phone);
        tv_address = findViewById(R.id.tv_address);
        rv_list = findViewById(R.id.rv_list);
        iv_pic = findViewById(R.id.iv_pic);
        tv_worker_name = findViewById(R.id.tv_worker_name);
        tv_worker_company = findViewById(R.id.tv_worker_company);
        iv_phone = findViewById(R.id.iv_phone);
        tv_number = findViewById(R.id.tv_number);
        tv_feature_time = findViewById(R.id.tv_feature_time);
        mTvFaultNum = findViewById(R.id.tv_faultNum);
        repairContacts = findViewById(R.id.tv_repairContacts);
        repairContactsPhone = findViewById(R.id.tv_repairContactPhone);
        mLlOrderPay = findViewById(R.id.ll_orderPay);
        mLlOrderMoney = findViewById(R.id.ll_orderMoney);
        mTvPayType = findViewById(R.id.tv_payType);
        mTvPayTime = findViewById(R.id.tv_payTime);
        mTvDoorFee = findViewById(R.id.tv_doorFee);
        mTvOrderAllPrice = findViewById(R.id.tv_orderAllPrice);
    }


    @Override
    protected void setListener() {

    }

    private void initAdapter() {
        if (mDataList.size() == 0) {
            return;
        }

        BaseQuickAdapter evaluateAdapter = new OrderConfirmAdapter(R.layout.item_order_confirm, mDataList, "");
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
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
                JumpItent.jump(getActivity(), FaultDetailActivity.class, bundle);
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);

        iv_phone.setOnClickListener(v ->
                CallUtils.call(getActivity(), iv_phone.getTag().toString())
        );
    }

    private void getData() {
        EanfangHttp.get(RepairApi.GET_REPAIR_DETAIL)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<RepairOrderEntity>(getActivity(), true, RepairOrderEntity.class, (bean) -> {

                    hashMap.put("id", String.valueOf(bean.getId()));
                    if (bean.getBugEntityList() != null && !TextUtils.isEmpty(bean.getBugEntityList().get(0).getPictures())) {
                        hashMap.put("picUrl", bean.getBugEntityList().get(0).getPictures().split(",")[0]);
                    }
                    hashMap.put("orderNum", bean.getOrderNum());
                    hashMap.put("creatTime", GetDateUtils.dateToDateTimeString(bean.getCreateTime()));
                    hashMap.put("workerName", bean.getAssigneeUser().getAccountEntity().getRealName());
                    hashMap.put("status", String.valueOf(bean.getStatus()));
                    hashMap.put("shareType", "1");

                    tv_company_name.setText(V.v(() -> bean.getRepairCompany()));//单位名称
                    tv_contract_name.setText(V.v(() -> bean.getAssigneeUser().getAccountEntity().getRealName()));//联系人
                    tv_contract_phone.setText(V.v(() -> bean.getAssigneeUser().getAccountEntity().getMobile()));
                    tv_address.setText(Config.get().getAddressByCode(bean.getPlaceCode()) + "\r\n" + bean.getAddress());
                    // 现场联系人
                    repairContacts.setText(V.v(() -> bean.getRepairContacts()));
                    // 联系人手机号
                    repairContactsPhone.setText(V.v(() -> bean.getRepairContactPhone()));
                    // 订单状态
                    mOrderStatus = bean.getStatus();
                    tv_number.setText(bean.getOrderNum());
                    tv_feature_time.setText(GetDateUtils.dateToDateTimeString(bean.getCreateTime()));
//                    tv_money.setText(bean.getTotalfee() + "");
//                    tv_alipay.setText(bean.getPaytype());
                    //      获取：是否电话解决（0：未解决，1：已解决）
//                    if (bean.getIsPhoneSolve() == null || bean.getIsPhoneSolve() == 0) {
//                        tv_phone_solve.setText("否");
//                    } else {
//                        tv_phone_solve.setText("是");
//                    }

                    //技师端
                    if (bean.getOwnerUser() != null) {
                        iv_pic.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(bean.getOwnerUser().getAccountEntity().getAvatar()));
                        tv_worker_name.setText(bean.getRepairContacts());
                        if (bean.getOwnerOrg() != null) {
                            if (bean.getOwnerOrg().getBelongCompany().getOrgName() != null)
                                tv_worker_company.setText(bean.getOwnerOrg().getBelongCompany().getOrgName());
                        }
                        iv_phone.setTag(bean.getRepairContactPhone());
                    }
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
//                            tvOrderMoney.setText("¥" + NumberUtil.getEndTwoNum(bean.getPayLogEntity().getPayPrice() / 100.00));
                            mTvDoorFee.setText(NumberUtil.getEndTwoNum(bean.getPayLogEntity().getPayPrice() / 100.00));
                            mTvOrderAllPrice.setText(NumberUtil.getEndTwoNum(bean.getPayLogEntity().getPayPrice() / 100.00));
                        }
                        // 支付方式
                        if (bean.getPayLogEntity().getPayType() != null) {
                            mPayType = GetConstDataUtils.getPayTypeList().get(bean.getPayLogEntity().getPayType());
                            mTvPayType.setText(mPayType);
                        }
                        // 支付时间
                        if (bean.getPayLogEntity().getPayTime() != null) {
                            mTvPayTime.setText(GetDateUtils.dateToDateTimeString(bean.getCreateTime()));
                        }
                    }
//                    else {
//                        llPay.setVisibility(View.GONE);
//                    }
                    if (mOrderStatus == 0) {// 待付款
                        // 支付方式布局
                        mLlOrderPay.setVisibility(View.GONE);
                        mLlOrderMoney.setVisibility(View.GONE);
                    }

                    mDataList = bean.getBugEntityList();
                    if (mDataList != null && mDataList.size() > 0) {
                        mTvFaultNum.setText(mDataList.size() + "");
                    }
                    initAdapter();
                }));
    }

    public HashMap getHashMap() {
        return hashMap;
    }
}
