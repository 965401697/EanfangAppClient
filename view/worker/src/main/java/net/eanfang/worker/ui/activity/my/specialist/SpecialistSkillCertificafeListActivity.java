package net.eanfang.worker.ui.activity.my.specialist;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.QualifyCertificafeListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.biz.model.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.my.certification.QualificationAdapter;
import net.eanfang.worker.ui.activity.worksapce.OwnDataHintActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 已废弃
 *
 * @author jornl
 * @date 2019-07-17 14:44:10
 */
@Deprecated
public class SpecialistSkillCertificafeListActivity extends BaseWorkerActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private final int ADD_EDUCATION_CODE = 101;
    private QualificationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specialist_skill_certificafe_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("资质证书");
        setLeftBack();
        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QualificationAdapter();
        adapter.bindToRecyclerView(recyclerView);

        adapter.setOnItemChildClickListener((adapter, view, position) -> delete(adapter, position));

        adapter.setOnItemClickListener((adapter, view, position) -> startActivityForResult(new Intent(SpecialistSkillCertificafeListActivity.this, SpecialistAddSkillCertificafeActivity.class).putExtra("bean", (QualificationCertificateEntity) adapter.getData().get(position)), ADD_EDUCATION_CODE));

        getData();
    }


    private void getData() {
        JSONObject object = new JSONObject();
        object.put("accId", String.valueOf(WorkerApplication.get().getAccId()));
        object.put("type", "1");
        EanfangHttp.post(UserApi.TECH_WORKER_LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(object))
                .execute(new EanfangCallback<QualifyCertificafeListBean>(this, true, QualifyCertificafeListBean.class) {
                    @Override
                    public void onSuccess(QualifyCertificafeListBean bean) {

                        if (bean.getList().size() > 0) {
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
                    }

                });
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sub:
                Intent intent = new Intent(SpecialistSkillCertificafeListActivity.this, OwnDataHintActivity.class);
                intent.putExtra("info", "尊敬的用户，您可以添加个人经历\n" +
                        "达到更高的知名度");
                intent.putExtra("go", "去添加个人经历");
                intent.putExtra("desc", "如有疑问，请联系客服处理");
                intent.putExtra("service", "客服热线：400-890-9280");
                intent.putExtra("class", SpecialistOwmHistoryActivity.class);
                startActivity(intent);
                endTransaction(true);
                break;

            case R.id.tv_add:
                startActivityForResult(new Intent(SpecialistSkillCertificafeListActivity.this, SpecialistAddSkillCertificafeActivity.class), ADD_EDUCATION_CODE);
                endTransaction(false);
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
