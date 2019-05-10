package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.HonorCerticificateListBean;
import com.eanfang.util.JsonUtils;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.CertificateListAdapter;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecialistCertificateListActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sub)
    TextView tvSub;

    private final int ADD_CERTIFICATION_CODE = 101;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;
    private CertificateListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_certificate_list);
        ButterKnife.bind(this);
        setTitle("荣誉证书");
        setLeftBack();
        initViews();
        getData();
    }

    private void initViews() {

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SpecialistCertificateListActivity.this, SpecialistAddCertificationActivity.class), ADD_CERTIFICATION_CODE);
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
                startActivityForResult(new Intent(SpecialistCertificateListActivity.this, SpecialistAddCertificationActivity.class)
                                .putExtra("bean", (HonorCertificateEntity) adapter.getData().get(position))
                        , ADD_CERTIFICATION_CODE);
            }
        });
    }


    private void getData() {


        JSONObject object = new JSONObject();
        object.put("accId", String.valueOf(EanfangApplication.get().getAccId()));
        object.put("type", "1");

        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_LIST)
                .upJson(JsonUtils.obj2String(object))
                .execute(new EanfangCallback<HonorCerticificateListBean>(this, true, HonorCerticificateListBean.class) {
                    @Override
                    public void onSuccess(HonorCerticificateListBean bean) {

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
        Intent intent = new Intent(SpecialistCertificateListActivity.this, OwnDataHintActivity.class);
        intent.putExtra("info", "尊敬的专家，您可以解答行业问题，\n帮助其他人，以获取知名度！");
        intent.putExtra("go", "");
        intent.putExtra("desc", "如有疑问，请联系客服处理");
        intent.putExtra("service", "客服热线：400-890-9280");
        startActivity(intent);
        finishSelf();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_CERTIFICATION_CODE) {
            getData();
        }

    }
}