package net.eanfang.client.ui.fragment.selectworker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.bean.Message;
import com.eanfang.biz.model.entity.PayLogEntity;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.biz.model.entity.RepairPersonalInfoEntity;
import com.eanfang.biz.model.entity.SelectWorkEntitity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.pay.NewPayActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.activity.worksapce.repair.TroubleListActivity;
import net.eanfang.client.ui.adapter.SelectWorkerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hutool.core.util.StrUtil;

/**
 * @author Guanluocang
 * @date on 2018/4/27  14:01
 * @decision 服务过的技师
 */
public class ServicedWorkerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_selectedWorker)
    RecyclerView rvSelectedWorker;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout swipreFresh;
    Unbinder unbinder;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.iv_hot)
    ImageView ivHot;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.tv_mouth)
    TextView tvMouth;
    @BindView(R.id.iv_mouth)
    ImageView ivMouth;
    @BindView(R.id.ll_mouth)
    LinearLayout llMouth;
    @BindView(R.id.tv_praise)
    TextView tvPraise;
    @BindView(R.id.iv_praise)
    ImageView ivPraise;
    @BindView(R.id.ll_praise)
    LinearLayout llPraise;
    @BindView(R.id.tv_repair)
    TextView tvRepair;
    @BindView(R.id.iv_repair)
    ImageView ivRepair;
    @BindView(R.id.ll_repair)
    LinearLayout llRepair;
    @BindView(R.id.tv_construction)
    TextView tvConstruction;
    @BindView(R.id.iv_construction)
    ImageView ivConstruction;
    @BindView(R.id.ll_construction)
    LinearLayout llConstruction;
    private RepairOrderEntity toRepairBean;
    private ArrayList<String> businessIds = new ArrayList<>();
    private int mDoorFee;
    private SelectWorkerAdapter selectWorkerAdapter;

    private Long mOwnerOrgId;
    public int mPage = 1;
    private QueryEntry mQueryEntry;
    /**
     * 个人信息
     */
    RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity;
    private String mAreaCode;
    /**
     * title 文字
     */
    private List<TextView> mScreenTitleList = new ArrayList<>();
    /**
     * title 图片
     */
    private List<ImageView> mScreenIconList = new ArrayList<>();
    private boolean mScreenIconUp = true;
    private String mClickType = "mouth";

    /**
     * 排序 参数
     * 排序 值
     */
    private String mOrderByType = "";
    private String mOrderByValue = "";

    public static ServicedWorkerFragment getInstance(RepairOrderEntity toRepairBean, RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity, ArrayList<String> businessIds, int doorfee, Long ownerOrgId, String areaCode) {
        ServicedWorkerFragment servicedWorkerFragment = new ServicedWorkerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("toRepairBean", toRepairBean);
        bundle.putStringArrayList("bussinsList", businessIds);
        bundle.putSerializable("topInfo", repairPersonalInfoEntity);
        bundle.putInt("doorFee", doorfee);
        bundle.putLong("mOwnerOrgId", ownerOrgId);
        bundle.putString("mAreaCode", areaCode);
        servicedWorkerFragment.setArguments(bundle);
        return servicedWorkerFragment;
    }


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_serviced_worker;
    }

    @Override
    protected void initData(Bundle arguments) {
        Bundle bundle = getArguments();
        toRepairBean = (RepairOrderEntity) bundle.getSerializable("toRepairBean");
        repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) bundle.getSerializable("topInfo");
        businessIds = bundle.getStringArrayList("bussinsList");
        mDoorFee = bundle.getInt("doorFee", 0);
        mOwnerOrgId = bundle.getLong("mOwnerOrgId", 0);
        mAreaCode = bundle.getString("mAreaCode");
        mScreenTitleList.add(tvMouth);
        mScreenTitleList.add(tvPraise);
        mScreenTitleList.add(tvRepair);
        mScreenTitleList.add(tvConstruction);
        mScreenTitleList.add(tvHot);

        mScreenIconList.add(ivHot);
        mScreenIconList.add(ivMouth);
        mScreenIconList.add(ivPraise);
        mScreenIconList.add(ivRepair);
        mScreenIconList.add(ivConstruction);

    }

    @Override
    protected void initView() {
        rvSelectedWorker.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSelectedWorker.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        selectWorkerAdapter = new SelectWorkerAdapter();
        selectWorkerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipreFresh.setOnRefreshListener(this);
        selectWorkerAdapter.setOnLoadMoreListener(this, rvSelectedWorker);
        rvSelectedWorker.setAdapter(selectWorkerAdapter);
    }

    @Override
    protected void setListener() {
        rvSelectedWorker.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
                intent.putExtra("toRepairBean", toRepairBean);
                intent.putExtra("topInfo", repairPersonalInfoEntity);
                intent.putExtra("companyUserId", selectWorkerAdapter.getData().get(position).getCompanyUserId() + "");
                intent.putExtra("workerId", selectWorkerAdapter.getData().get(position).getId() + "");
                intent.putExtra("doorFee", mDoorFee);
                startActivity(intent);
            }
        });
        // 热门排序
        llHot.setOnClickListener((v) -> {
            doChangetState("hot");
        });
        //口碑
        llMouth.setOnClickListener((v) -> {
            doChangetState("mouth");
        });
        // 评价
        llPraise.setOnClickListener((v) -> {
            doChangetState("praise");
        });
        // 维修
        llRepair.setOnClickListener((v) -> {
            doChangetState("repair");
        });
        // 施工
        llConstruction.setOnClickListener((v) -> {
            doChangetState("construction");
        });
    }

    private void doChangetState(String mType) {
        switch (mType) {
            //热门
            case "hot":
                doUpdataTextColor("weight", "hot", tvHot, ivHot);
                break;
            //口碑
            case "mouth":
                doUpdataTextColor("publicPraise", "mouth", tvMouth, ivMouth);
                break;
            // 评价
            case "praise":
                doUpdataTextColor("goodRate", "praise", tvPraise, ivPraise);
                break;
            // 维修
            case "repair":
                doUpdataTextColor("repairCount", "repair", tvRepair, ivRepair);
                break;
            // 施工
            case "construction":
                doUpdataTextColor("installNum", "construction", tvConstruction, ivConstruction);
                break;
            default:
                break;
        }
    }

    /**
     * @param orderByType 接口排序条件 two three four five
     * @param clickType   点击类型 口碑 评价 报修 施工
     * @param textView
     * @param imageView
     */
    private void doUpdataTextColor(String orderByType, String clickType, TextView textView, ImageView imageView) {
        mOrderByType = orderByType;
        mPage = 1;
        // 判断文字
        for (TextView mTextViews : mScreenTitleList) {
            // 变黄
            if (textView.equals(mTextViews)) {
                mTextViews.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_home_company_install_bg));
            } else {
                // 变黑
                mTextViews.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_client_neworder));
            }
        }
        //判断图片
        for (ImageView mImageViews : mScreenIconList) {
            // 热门 desc
            if (imageView.equals(ivHot)) {
                initWorker(mOrderByType, "desc");
                mImageViews.setImageResource(R.mipmap.ic_client_screen_none);
            } else {
                if (mImageViews.equals(imageView)) {
                    if (clickType.equals(mClickType)) {
                        if (mScreenIconUp) {
                            mScreenIconUp = false;
                            mOrderByValue = "asc";
                            mImageViews.setImageResource(R.mipmap.ic_client_screen_up);
                        } else {
                            mScreenIconUp = true;
                            mOrderByValue = "desc";
                            mImageViews.setImageResource(R.mipmap.ic_client_screen_down);
                        }
                    } else {
                        mClickType = clickType;
                        mScreenIconUp = false;
                        mOrderByValue = "asc";
                        mImageViews.setImageResource(R.mipmap.ic_client_screen_up);
                    }
                    initWorker(mOrderByType, mOrderByValue);
                } else {
                    mImageViews.setImageResource(R.mipmap.ic_client_screen_none);
                }
            }

        }

    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        // 获取全部技师
        initWorker("", "");
    }

    //加载技师
    private void initWorker(String mOrderByType, String mOrderByValue) {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        if (toRepairBean != null) {
            mQueryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
        } else if (!StrUtil.isEmpty(mAreaCode)) {
            mQueryEntry.getEquals().put("regionCode", mAreaCode);
        }
        if (businessIds != null) {
            mQueryEntry.getIsIn().put("businessId", Stream.of(businessIds).distinct().toList());
        }
        mQueryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
        mQueryEntry.getEquals().put("served", 1 + "");
        mQueryEntry.getOrderBy().put(mOrderByType, mOrderByValue);
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
//        queryEntry.getEquals().put("collect", collectId + "");
        if (mOwnerOrgId != 0) {
//            queryEntry.getEquals().put("companyId", mOwnerOrgId + "");
        }
//        queryEntry.getEquals().put("userId", ClientApplication.get().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SelectWorkEntitity>(getActivity(), true, SelectWorkEntitity.class) {
                    @Override
                    public void onSuccess(SelectWorkEntitity bean) {
                        if (mPage == 1) {
                            selectWorkerAdapter.getData().clear();
                            selectWorkerAdapter.setNewData(bean.getList());
                            swipreFresh.setRefreshing(false);
                            selectWorkerAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                selectWorkerAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }
                            if (bean.getList().size() > 0) {
                                rvSelectedWorker.setVisibility(View.VISIBLE);
                                llNodata.setVisibility(View.GONE);
                            } else {
                                rvSelectedWorker.setVisibility(View.GONE);
                                llNodata.setVisibility(View.VISIBLE);
                            }
                        } else {
                            selectWorkerAdapter.addData(bean.getList());
                            selectWorkerAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                selectWorkerAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onCommitAgain() {
                        swipreFresh.setRefreshing(false);
                        selectWorkerAdapter.loadMoreEnd();//没有数据了
                        if (selectWorkerAdapter.getData().size() == 0) {
                            llNodata.setVisibility(View.VISIBLE);
                        } else {
                            llNodata.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        swipreFresh.setRefreshing(false);
                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mQueryEntry = null;
        mPage = 1;
        initWorker(mOrderByType, mOrderByValue);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        initWorker(mOrderByType, mOrderByValue);
    }

    private void submitSuccess(/*OrderReturnBean bean*/) {
        Intent intent = new Intent(getActivity(), StateChangeActivity.class);
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
        closeActivity();

    }

    /**
     * 支付
     *
     * @param orderEntity
     */
    private void payment(RepairOrderEntity orderEntity) {

        PayLogEntity payLogEntity = new PayLogEntity();
        payLogEntity.setOrderId(orderEntity.getId());
        payLogEntity.setOrderNum(orderEntity.getOrderNum());
        payLogEntity.setOrderType(Constant.OrderType.REPAIR.ordinal());
        payLogEntity.setAssigneeUserId(orderEntity.getOwnerUserId());
        payLogEntity.setAssigneeOrgCode(orderEntity.getOwnerOrgCode());
        payLogEntity.setAssigneeTopCompanyId(orderEntity.getOwnerTopCompanyId());

        //查询上门费
        payLogEntity.setOriginPrice(mDoorFee);
//        payLogEntity.setPayPrice(1);//测试专用

        EanfangHttp.post(RepairApi.GET_REPAIR_PAY_RECORD)
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    Intent intent = new Intent(getActivity(), NewPayActivity.class);
                    intent.putExtra("payLogEntity", payLogEntity);
                    startActivity(intent);
                    closeActivity();
                }));
    }

    private void closeActivity() {
        ClientApplication.get().closeActivity(TroubleListActivity.class);
        ClientApplication.get().closeActivity(RepairTypeActivity.class);
        ClientApplication.get().closeActivity(RepairActivity.class);
        ClientApplication.get().closeActivity(SelectWorkerActivity.class);
        finishSelf();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
