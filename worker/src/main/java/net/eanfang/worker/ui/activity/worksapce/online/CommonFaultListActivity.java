package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonFaultListActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view_fault)
    RecyclerView recyclerViewFault;
    @BindView(R.id.recycler_view_expert)
    RecyclerView recyclerViewExpert;
    private CommonFaultAdapter mCommonFaultAdapter;
    private ExpertListAdapter mExpertListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fault_list);
        ButterKnife.bind(this);
        setTitle("常见故障");
        setLeftBack();

        initViews();
    }

    private void initViews() {
        recyclerViewFault.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpert.setLayoutManager(new LinearLayoutManager(this));

        mCommonFaultAdapter = new CommonFaultAdapter();
        mCommonFaultAdapter.bindToRecyclerView(recyclerViewFault);

        mExpertListAdapter = new ExpertListAdapter();
        mExpertListAdapter.bindToRecyclerView(recyclerViewExpert);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");

        mCommonFaultAdapter.setNewData(list);
//        mExpertListAdapter.setNewData(list);


        mCommonFaultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CommonFaultListActivity.this, FaultExplainActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.ll_more)
    public void onViewClicked() {
    }
}
