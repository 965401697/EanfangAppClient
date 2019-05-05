package net.eanfang.client.ui.activity.worksapce.repair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ProjectListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.ProjectEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.repair.RepairProjectListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/4/24
 * @description 报修 项目选择列表
 */

public class RepairProjectListActivity extends BaseActivity {

    private static final int REQUEST_ADD_PROJECT_NAME = 1008;
    @BindView(R.id.rv_projectList)
    RecyclerView rvProjectList;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    private RepairProjectListAdapter projectListAdapter;
    private List<ProjectEntity> mProjectList;

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
        setRightTitle("手动输入");
        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(this, RepairAddProjectAcitivity.class, REQUEST_ADD_PROJECT_NAME);
        });

        rvProjectList.setLayoutManager(new LinearLayoutManager(this));
        projectListAdapter = new RepairProjectListAdapter();
        projectListAdapter.bindToRecyclerView(rvProjectList);

        projectListAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("projectId", projectListAdapter.getData().get(position).getId() + "");
            bundle.putString("projectName", projectListAdapter.getData().get(position).getProjectName());
            setResult(RESULT_OK, new Intent().putExtras(bundle));
            finishSelf();
        }));
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        EanfangHttp.post(NewApiService.REPAIR_PROJECT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ProjectListBean>(RepairProjectListActivity.this, false, ProjectListBean.class, (bean) -> {
                    if (bean.getList() != null && bean.getList().size() > 0) {
                        mProjectList = bean.getList();
                        projectListAdapter.setNewData(mProjectList);
                        rvProjectList.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);
                    } else {
                        rvProjectList.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                    }
                }));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD_PROJECT_NAME:
                Intent intent = new Intent();
                intent.putExtra("projectName", data.getStringExtra("projectName"));
                setResult(Activity.RESULT_OK, intent);
                finishSelf();
                break;
            default:
                break;
        }
    }
}
