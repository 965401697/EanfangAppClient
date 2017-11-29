package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.util.ConnectivityChangeReceiver;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.worksapce.OrderConfirmActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.adapter.WorkerListAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.SelectWorkerListBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * 我要报修中的选择技师列表
 * Created by Administrator on 2017/3/14.
 */

public class WorkerListFragment extends BaseFragment {

    private View v;
    private String mTitle;
    private RecyclerView mRecyclerView;
    private List<SelectWorkerListBean.All1Bean> mDataList;
    private TextView btn_one_button;

    public static WorkerListFragment getInstance(List<SelectWorkerListBean.All1Bean> mDataList) {
        WorkerListFragment sf = new WorkerListFragment();
        sf.mDataList = mDataList;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_one_button = findViewById(R.id.btn_one_button);
        initAdapter();
    }


    @Override
    protected void setListener() {
        btn_one_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bugOneUid = ((SelectWorkerActivity) getActivity()).getToRepairBean().getBugOneUid();
                JSONObject json = new JSONObject();
                try {
                    json.put("bugOneUid", bugOneUid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EanfangHttp.post(ApiService.ONE_BUTON_REPAIR)
                        .tag(this)
                        .params("json", json.toString())
                        .execute(new EanfangCallback<JSONObject>(getActivity(), false) {
                            @Override
                            public void onSuccess(JSONObject bean) {
                                super.onSuccess(bean);
                                String workeruid = null;
                                try {
                                    workeruid = bean.getString("workeruid");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(getActivity(), OrderConfirmActivity.class);
                                intent.putExtra("bean", ((SelectWorkerActivity) getActivity()).getToRepairBean());
                                intent.putExtra("id", workeruid);
                                startActivity(intent);
                            }
                        });
            }
        });
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new WorkerListAdapter(R.layout.item_worker_list, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (ConnectivityChangeReceiver.isNetConnected(getContext()) == true) {
                    Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
                    intent.putExtra("id", mDataList.get(position).getPersonuid());
                    intent.putExtra("bean", ((SelectWorkerActivity) getActivity()).getToRepairBean());
                    startActivity(intent);
                } else {
                    showToast("网络异常，请检查网络");
                }

            }
        });

        evaluateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (ConnectivityChangeReceiver.isNetConnected(getContext()) == true) {
                    Intent intent = new Intent(getActivity(), OrderConfirmActivity.class);
                    intent.putExtra("bean", ((SelectWorkerActivity) getActivity()).getToRepairBean());
                    intent.putExtra("id", mDataList.get(position).getPersonuid());
                    startActivity(intent);
                } else {
                    showToast("网络异常，请检查网络");
                }

            }
        });

        mRecyclerView.setAdapter(evaluateAdapter);
    }

}
