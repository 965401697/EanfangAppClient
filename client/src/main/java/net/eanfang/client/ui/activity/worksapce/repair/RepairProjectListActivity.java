package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.repair.RepairPersonalInfoAdapter;
import net.eanfang.client.ui.adapter.repair.RepairProjectListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/4/24
 * @description 报修 项目选择列表
 */

public class RepairProjectListActivity extends BaseActivity {

    @BindView(R.id.rv_projectList)
    RecyclerView rvProjectList;
    private RepairProjectListAdapter projectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_project_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("项目选择");

        rvProjectList.setLayoutManager(new LinearLayoutManager(this));
        projectListAdapter = new RepairProjectListAdapter();
        projectListAdapter.bindToRecyclerView(rvProjectList);
    }

    private void initData() {

    }
}
