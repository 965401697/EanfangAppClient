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
import com.eanfang.model.QualifyCertificafeListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SkillCertificafeListActivity extends BaseWorkerActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private final int ADD_EDUCATION_CODE = 101;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    private QualificationAdapter adapter;
    private int mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_certificafe_list);
        ButterKnife.bind(this);
        setTitle("技能认证");
        setLeftBack();
        mStatus = getIntent().getIntExtra("status", -1);
        initViews();
    }

    private void initViews() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QualificationAdapter();
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
                startActivityForResult(new Intent(SkillCertificafeListActivity.this, AddSkillCertificafeActivity.class).putExtra("bean", (QualificationCertificateEntity) adapter.getData().get(position)), ADD_EDUCATION_CODE);
            }
        });

        if (mStatus > 0) {
            getData();
        }
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("accId", String.valueOf(EanfangApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.TECH_WORKER_LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<QualifyCertificafeListBean>(this, true, QualifyCertificafeListBean.class) {
                    @Override
                    public void onSuccess(QualifyCertificafeListBean bean) {

                        if (bean.getList().size() > 0) {
//                            adapter.getData().clear();
                            adapter.setNewData(bean.getList());
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
        EanfangHttp.post(UserApi.TECH_WORKER_DELETE_QUALIFY + "/" + ((QualificationCertificateEntity) adapter.getData().get(position)).getId())
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

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sub:
                Intent intent = new Intent(SkillCertificafeListActivity.this, OwnDataHintActivity.class);
                intent.putExtra("info", "尊敬的用户，您可以添加个人经历，\n" +
                        "以提高行业内声望");
                intent.putExtra("go", "去添加个人经历");
                intent.putExtra("desc", "如有疑问，请联系客服处理");
                intent.putExtra("service", "客服热线：400-890-9280");
                startActivity(intent);
                endTransaction(true);
                break;

            case R.id.tv_add:
                startActivityForResult(new Intent(SkillCertificafeListActivity.this, AddSkillCertificafeActivity.class), ADD_EDUCATION_CODE);
                endTransaction(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_EDUCATION_CODE) {
            getData();

            if (tvSub.getVisibility() == View.GONE) {
                tvSub.setVisibility(View.VISIBLE);
            }
        }

    }

}
