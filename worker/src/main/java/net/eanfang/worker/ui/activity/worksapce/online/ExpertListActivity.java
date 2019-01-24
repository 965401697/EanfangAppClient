package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpertListActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private BaseDataEntity mBrand;
    private ExpertListAdapter mExpertListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_list);
        ButterKnife.bind(this);
        mBrand = (BaseDataEntity) getIntent().getSerializableExtra("brand");
        setTitle(mBrand.getDataName());
        setLeftBack();

        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExpertListAdapter = new ExpertListAdapter();
        mExpertListAdapter.bindToRecyclerView(recyclerView);
        ArrayList list = new ArrayList<String>(5);
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        mExpertListAdapter.setNewData(list);
        mExpertListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ExpertListActivity.this, AskExpertActivity.class);
                intent.putExtra("brand",mBrand);
                startActivity(intent);
            }
        });
    }
}
