package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.JobListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.entity.JobExperienceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkHistoryActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    private WorkListAdapter adapter;

    private final int ADD_WORK_CODE = 101;
    private int mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_work_history);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        startTransaction(true);
        setTitle("工作经历");
        setLeftBack();
        initViews();

        getData();
    }

    private void initViews() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkListAdapter();
        adapter.bindToRecyclerView(recyclerView);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                delete(adapter, position);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivityForResult(new Intent(WorkHistoryActivity.this, AddWorkActivity.class).putExtra("bean", (JobExperienceEntity) adapter.getData().get(position)), ADD_WORK_CODE);
            }
        });
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("accId", String.valueOf(WorkerApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.GET_TECH_WORKER_WORK_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JobListBean>(this, true, JobListBean.class) {
                    @Override
                    public void onSuccess(JobListBean bean) {

                        if (bean.getList().size() > 0) {
                            adapter.setNewData(bean.getList());
                            if (tvSub.getVisibility() == View.GONE) {
                                tvSub.setVisibility(View.VISIBLE);
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {

                    }

                    @Override
                    public void onCommitAgain() {

                    }
                });
    }


    private void delete(BaseQuickAdapter adapter, int position) {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_WORK_DELETE + "/" + ((JobExperienceEntity) adapter.getData().get(position)).getId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        adapter.remove(position);

                        if (adapter.getData().size() == 0) {
                            tvSub.setVisibility(View.GONE);
                        }
                    }

                });
    }

    @OnClick({R.id.tv_add_work, R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_work:
                startActivityForResult(new Intent(WorkHistoryActivity.this, AddWorkActivity.class), ADD_WORK_CODE);
                break;
            case R.id.tv_sub:
                Intent intent = new Intent(WorkHistoryActivity.this, OwnDataHintActivity.class);
                intent.putExtra("info", "尊敬的用户，您可以添加荣誉证书，\n" +
                        "以展示更强的权威性");
                intent.putExtra("go", "去添加荣誉证书");
                intent.putExtra("desc", "如有疑问，请联系客服处理");
                intent.putExtra("service", "客服热线：" + R.string.text_service_telphone);
                intent.putExtra("class",CertificateListActivity.class);
                startActivity(intent);
                endTransaction(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_WORK_CODE) {
            getData();

        }

    }

}
