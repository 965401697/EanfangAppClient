package net.eanfang.client.ui.fragment.selectworker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
import com.eanfang.model.Message;
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

import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/4/27  14:01
 * @decision 收藏的技师
 */
public class CollectWorkerFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private TextView mTvNoData;
    private List<WorkerEntity> selectWorkerList = new ArrayList<>();

    private RepairOrderEntity toRepairBean;
    private ArrayList<String> businessIds = new ArrayList<>();
    private int mDoorFee;
    private SelectWorkerAdapter selectWorkerAdapter;

    private Long mOwnerOrgId;

    public static CollectWorkerFragment getInstance(RepairOrderEntity toRepairBean, ArrayList<String> businessIds, int doorfee, Long ownerOrgId) {
        CollectWorkerFragment collectWorkerFragment = new CollectWorkerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("toRepairBean", toRepairBean);
        bundle.putStringArrayList("bussinsList", businessIds);
        bundle.putInt("doorFee", doorfee);
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
        businessIds = bundle.getStringArrayList("bussinsList");
        mDoorFee = bundle.getInt("doorFee", 0);
        mOwnerOrgId = bundle.getLong("mOwnerOrgId", 0);

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_collectWorker);
        mTvNoData = findViewById(R.id.tv_noData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        selectWorkerAdapter = new SelectWorkerAdapter(R.layout.item_collection_worker, selectWorkerList);
        selectWorkerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(selectWorkerAdapter);

    }

    @Override
    protected void setListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
                intent.putExtra("toRepairBean", toRepairBean);
                intent.putExtra("companyUserId", selectWorkerList.get(position).getCompanyUserId() + "");
                intent.putExtra("workerId", selectWorkerList.get(position).getId() + "");
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
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
        queryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
        queryEntry.getIsIn().put("businessId", Stream.of(businessIds).distinct().toList());
        queryEntry.getEquals().put("served", serviceId + "");
        queryEntry.getEquals().put("collect", collectId + "");
        if (mOwnerOrgId != 0) {
            queryEntry.getEquals().put("companyId", mOwnerOrgId + "");
        }
        queryEntry.getEquals().put("userId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkerEntity>(getActivity(), true, WorkerEntity.class, true, (list) -> {
                    selectWorkerList = list;
                    initAdapter();
//                    initMarker();
                }));
    }

    private void initAdapter() {
        if (selectWorkerList == null || selectWorkerList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mTvNoData.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTvNoData.setVisibility(View.GONE);
            selectWorkerAdapter.refreshList(selectWorkerList);
            selectWorkerAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btn_key)
    public void onViewClicked() {
        //一键报修
        doHttpSubmit();
    }


    private void doHttpSubmit() {
        EanfangHttp.post(RepairApi.ADD_CLIENT_REPAIR)
                .upJson(JSON.toJSONString(toRepairBean))
                .execute(new EanfangCallback<RepairOrderEntity>(getActivity(), true, RepairOrderEntity.class, (bean) -> {
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
}
