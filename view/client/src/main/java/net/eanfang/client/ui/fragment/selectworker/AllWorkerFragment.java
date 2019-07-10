package net.eanfang.client.ui.fragment.selectworker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.PayLogEntity;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Guanluocang
 * @date on 2018/4/27  14:01
 * @decision 附近的技师
 */
public class AllWorkerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    Unbinder unbinder;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout swipreFresh;
    @BindView(R.id.btn_key_two)
    Button btnKeyTwo;
    @BindView(R.id.rv_allWorker)
    RecyclerView rvAllWorker;


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

    public static AllWorkerFragment getInstance(RepairOrderEntity toRepairBean, RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity, ArrayList<String> businessIds, int doorfee, Long ownerOrgId) {
        AllWorkerFragment allWorkerFragment = new AllWorkerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("toRepairBean", toRepairBean);
        bundle.putStringArrayList("bussinsList", businessIds);
        bundle.putInt("doorFee", doorfee);
        bundle.putSerializable("topInfo", repairPersonalInfoEntity);
        bundle.putLong("mOwnerOrgId", ownerOrgId);
        allWorkerFragment.setArguments(bundle);
        return allWorkerFragment;
    }


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_all_worker;
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
        selectWorkerAdapter = new SelectWorkerAdapter();
        rvAllWorker.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAllWorker.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        selectWorkerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipreFresh.setOnRefreshListener(this);
        selectWorkerAdapter.setOnLoadMoreListener(this, rvAllWorker);
        rvAllWorker.setAdapter(selectWorkerAdapter);
    }

    @Override
    protected void setListener() {
        rvAllWorker.addOnItemTouchListener(new OnItemClickListener() {
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
    }

    @Override
    protected void onLazyLoad() {
        mPage = 1;
        // 获取全部技师
        initWorker(0, 0);
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
        mQueryEntry.getEquals().put("userId", ClientApplication.get().getUserId() + "");
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
                                rvAllWorker.setVisibility(View.VISIBLE);
                                llNodata.setVisibility(View.GONE);
                                btnKeyTwo.setVisibility(View.VISIBLE);
                            } else {
                                rvAllWorker.setVisibility(View.GONE);
                                llNodata.setVisibility(View.VISIBLE);
                                btnKeyTwo.setVisibility(View.GONE);
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
        initWorker(0, 0);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        initWorker(0, 0);
    }

    private void doHttpSubmit() {
        EanfangHttp.post(RepairApi.ADD_CLIENT_REPAIR)
                .upJson(JSON.toJSONString(toRepairBean))
                .execute(new EanfangCallback<RepairOrderEntity>(getActivity(), true, RepairOrderEntity.class, (bean) -> {
                    //待支付
                    if (bean == null) {
                        return;
                    }
                    if (Constant.RepairStatus.CREATED.v == bean.getStatus().intValue()) {
                        payment(bean);
                    } else {
                        submitSuccess();
                    }
                }));
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
//        closeActivity();
        finishSelf();

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

    @OnClick({R.id.btn_key_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_key_two:
                doHttpSubmit();
                break;
            default:
                break;
        }
    }
}
