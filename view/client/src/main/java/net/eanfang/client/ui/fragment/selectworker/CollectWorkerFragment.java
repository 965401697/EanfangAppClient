package net.eanfang.client.ui.fragment.selectworker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.PayLogEntity;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.pay.NewPayActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.adapter.SelectWorkerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Guanluocang
 * @date on 2018/4/27  14:01
 * @decision 收藏的技师
 */
public class CollectWorkerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_collectWorker)
    RecyclerView rvCollectWorker;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout swipreFresh;
    Unbinder unbinder;

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
    public static CollectWorkerFragment getInstance(RepairOrderEntity toRepairBean, RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity, ArrayList<String> businessIds, int doorfee, Long ownerOrgId) {
        CollectWorkerFragment collectWorkerFragment = new CollectWorkerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("toRepairBean", toRepairBean);
        bundle.putStringArrayList("bussinsList", businessIds);
        bundle.putInt("doorFee", doorfee);
        bundle.putSerializable("topInfo", repairPersonalInfoEntity);
        bundle.putLong("mOwnerOrgId", ownerOrgId);
        collectWorkerFragment.setArguments(bundle);
        return collectWorkerFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_collect_worker;
    }

    @Override
    protected void initData(Bundle arguments) {
        Bundle bundle = getArguments();
        toRepairBean = (RepairOrderEntity) bundle.getSerializable("toRepairBean");
        repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) bundle.getSerializable("topInfo");
        businessIds = bundle.getStringArrayList("bussinsList");
        mDoorFee = bundle.getInt("doorFee", 0);
        mOwnerOrgId = bundle.getLong("mOwnerOrgId", 0);

    }

    @Override
    protected void initView() {
        rvCollectWorker.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCollectWorker.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        selectWorkerAdapter = new SelectWorkerAdapter();
        selectWorkerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipreFresh.setOnRefreshListener(this);
        selectWorkerAdapter.setOnLoadMoreListener(this, rvCollectWorker);
        rvCollectWorker.setAdapter(selectWorkerAdapter);

    }

    @Override
    protected void setListener() {
        rvCollectWorker.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
                intent.putExtra("toRepairBean", toRepairBean);
                intent.putExtra("topInfo", repairPersonalInfoEntity);
                intent.putExtra("companyUserId", selectWorkerAdapter.getData().get(position).getCompanyUserId() + "");
                intent.putExtra("workerId", selectWorkerAdapter.getData().get(position).getId() + "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        // 获取全部技师
        initWorker(0, 1);
    }

    //加载技师
    private void initWorker(int serviceId, int collectId) {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        mQueryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
        mQueryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
        mQueryEntry.getIsIn().put("businessId", Stream.of(businessIds).distinct().toList());
        mQueryEntry.getEquals().put("served", serviceId + "");
        mQueryEntry.getEquals().put("collect", collectId + "");
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        if (mOwnerOrgId != 0) {
            mQueryEntry.getEquals().put("companyId", mOwnerOrgId + "");
        }
        mQueryEntry.getEquals().put("userId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<WorkerEntity>(getActivity(), true, WorkerEntity.class, true, new EanfangCallback.ISuccessArray<WorkerEntity>() {
                    @Override
                    public void success(List<WorkerEntity> bean) {
                        if (mPage == 1) {
                            selectWorkerAdapter.getData().clear();
                            selectWorkerAdapter.setNewData(bean);
                            swipreFresh.setRefreshing(false);
                            selectWorkerAdapter.loadMoreComplete();
                            if (bean.size() < 10) {
                                selectWorkerAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }
                            if (bean.size() > 0) {
                                rvCollectWorker.setVisibility(View.VISIBLE);
                                llNodata.setVisibility(View.GONE);
                            } else {
                                rvCollectWorker.setVisibility(View.GONE);
                                llNodata.setVisibility(View.VISIBLE);
                            }
                        } else {
                            selectWorkerAdapter.addData(bean);
                            selectWorkerAdapter.loadMoreComplete();
                            if (bean.size() < 10) {
                                selectWorkerAdapter.loadMoreEnd();
                            }
                        }
                    }

                }) {
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
        initWorker(0, 1);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        initWorker(0, 1);
    }

    private void submitSuccess(/*OrderReturnBean bean*/) {
//        ordernum = bean.getOrdernum();
//        status = bean.getStatus();
//        doorfee = bean.getDoorfee();
//        showToast("下单成功");
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
//        payLogEntity.setOriginPrice(mDoorFee);
        payLogEntity.setPayPrice(1);//测试专用

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
        EanfangApplication.get().closeActivity(RepairTypeActivity.class.getName());
        EanfangApplication.get().closeActivity(RepairActivity.class.getName());
        EanfangApplication.get().closeActivity(SelectWorkerActivity.class.getName());
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
