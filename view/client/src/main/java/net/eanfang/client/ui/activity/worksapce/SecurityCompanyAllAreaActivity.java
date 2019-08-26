package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.WorkerDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class SecurityCompanyAllAreaActivity extends BaseActivity {

    private RecyclerView rvAreaAll;
    private List<String> mAreaAll = new ArrayList<>();
    private WorkerDetailAdapter workerDetailAdapter;

    private int regionSize = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_company_all_area);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("服务区域");
        setLeftBack(true);
        rvAreaAll = SecurityCompanyAllAreaActivity.this.findViewById(R.id.rv_listArea);
        mAreaAll = getIntent().getStringArrayListExtra("areaList");
        workerDetailAdapter = new WorkerDetailAdapter(R.layout.item_worker_detail1, new ArrayList());
        workerDetailAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvAreaAll.setLayoutManager(new GridLayoutManager(this, 2));

        View footView = LayoutInflater.from(this).inflate(R.layout.foot_view_aren, null);
        workerDetailAdapter.bindToRecyclerView(rvAreaAll);

        workerDetailAdapter.addFooterView(footView);
        workerDetailAdapter.setNewData(new ArrayList<>());
        fillRegionData();
        workerDetailAdapter.getFooterLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillRegionData();
            }
        });
    }

    private void fillRegionData() {
        List<String> dataList = Stream.of(mAreaAll).filter(data -> !workerDetailAdapter.getData().contains(data)).limit(regionSize).toList();

        workerDetailAdapter.addData(dataList);
        workerDetailAdapter.loadMoreComplete();

        if (dataList.size() < regionSize) {
            workerDetailAdapter.loadMoreEnd();
            workerDetailAdapter.getFooterLayout().setVisibility(View.GONE);
        }

    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

}
