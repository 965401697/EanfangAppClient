package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.HonorCerticificateListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 荣誉证书认证
 */
public class CertificateListActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sub)
    TextView tvSub;

    private final int ADD_CERTIFICATION_CODE = 101;
    private CertificateListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        ButterKnife.bind(this);
        setTitle("荣誉证书");
        setLeftBack();


        initViews();

    }

    private void initViews() {
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CertificateListActivity.this, AddCertificationActivity.class), ADD_CERTIFICATION_CODE);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CertificateListAdapter();
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
                startActivityForResult(new Intent(CertificateListActivity.this, AddCertificationActivity.class).putExtra("bean", (HonorCertificateEntity) adapter.getData().get(position)), ADD_CERTIFICATION_CODE);
            }
        });
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("accId", String.valueOf(EanfangApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<HonorCerticificateListBean>(this, true, HonorCerticificateListBean.class) {
                    @Override
                    public void onSuccess(HonorCerticificateListBean bean) {

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
        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_DELETE + "/" + ((HonorCertificateEntity) adapter.getData().get(position)).getId())
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

    @OnClick(R.id.tv_sub)
    public void onViewClicked() {
        Intent intent = new Intent(CertificateListActivity.this, OwnDataHintActivity.class);
        intent.putExtra("info", "尊敬的技师，祝贺您！");
        intent.putExtra("go", "");
        intent.putExtra("desc", "如有疑问，请联系客服处理");
        intent.putExtra("service", "客服热线：010-5877-8731");
        startActivity(intent);
        finishSelf();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_CERTIFICATION_CODE) {
            getData();

            if (tvSub.getVisibility() == View.GONE) {
                tvSub.setVisibility(View.VISIBLE);
            }
        }

    }
}
