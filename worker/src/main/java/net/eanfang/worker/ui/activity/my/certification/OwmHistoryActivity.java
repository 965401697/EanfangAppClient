package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.EducationListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.EducationExperienceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人经历
 */
public class OwmHistoryActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_add_education)
    TextView tvAddEducation;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private final int ADD_EDUCATION_CODE = 101;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    private EducationListAdapter adapter;
    private int mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owm_history);
        ButterKnife.bind(this);
        startTransaction(true);
        setTitle("个人经历");
        setLeftBack();
        initViews();


        getData();

    }

    private void initViews() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EducationListAdapter();
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
                startActivityForResult(new Intent(OwmHistoryActivity.this, AddEducationHistoryActivity.class).putExtra("bean", (EducationExperienceEntity) adapter.getData().get(position)), ADD_EDUCATION_CODE);
            }
        });
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("accId", String.valueOf(EanfangApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.GET_TECH_WORKER_EDUCATION_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<EducationListBean>(this, true, EducationListBean.class) {
                    @Override
                    public void onSuccess(EducationListBean bean) {

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
        EanfangHttp.post(UserApi.GET_TECH_WORKER_EDUCATION_DELETE + "/" + ((EducationExperienceEntity) adapter.getData().get(position)).getId())
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

    @OnClick({R.id.tv_sub, R.id.tv_add_education})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sub:
                Intent intent = new Intent(OwmHistoryActivity.this, WorkHistoryActivity.class);
                intent.putExtra("status", mStatus);
                startActivity(intent);
                break;

            case R.id.tv_add_education:

                startActivityForResult(new Intent(OwmHistoryActivity.this, AddEducationHistoryActivity.class), ADD_EDUCATION_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_EDUCATION_CODE) {
            getData();
        }

    }

}
