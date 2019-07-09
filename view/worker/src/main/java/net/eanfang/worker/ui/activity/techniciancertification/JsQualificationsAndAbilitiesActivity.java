package net.eanfang.worker.ui.activity.techniciancertification;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.EducationListBean;
import com.eanfang.biz.model.HonorCerticificateListBean;
import com.eanfang.biz.model.JobListBean;
import com.eanfang.biz.model.JsCapabilityTagBean;
import com.eanfang.biz.model.QualifyCertificafeListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.AptitudeCertificateEntity;
import com.yaf.base.entity.EducationExperienceEntity;
import com.yaf.base.entity.HonorCertificateEntity;
import com.yaf.base.entity.JobExperienceEntity;
import com.yaf.base.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.my.certification.AddCertificationActivity;
import net.eanfang.worker.ui.activity.my.certification.AddEducationHistoryActivity;
import net.eanfang.worker.ui.activity.my.certification.AddSkillCertificafeActivity;
import net.eanfang.worker.ui.activity.my.certification.AddWorkActivity;
import net.eanfang.worker.ui.adapter.JsaQualificationsAndAbilitiesAdapter;
import net.eanfang.worker.ui.adapter.JsbQualificationsAndAbilitiesAdapter;
import net.eanfang.worker.ui.adapter.JscQualificationsAndAbilitiesAdapter;
import net.eanfang.worker.ui.adapter.JsdQualificationsAndAbilitiesAdapter;
import net.eanfang.worker.ui.adapter.JseQualificationsAndAbilitiesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class JsQualificationsAndAbilitiesActivity extends BaseActivity {
    @BindView(R.id.recycler_view_a)
    RecyclerView recyclerViewA;
    @BindView(R.id.recycler_view_b)
    RecyclerView recyclerViewB;
    @BindView(R.id.recycler_view_c)
    RecyclerView recyclerViewC;
    @BindView(R.id.recycler_view_d)
    RecyclerView recyclerViewD;
    @BindView(R.id.recycler_view_e)
    RecyclerView recyclerViewE;
    @BindView(R.id.header_l_tv)
    TextView headerLTv;
    @BindView(R.id.header_r_tv)
    TextView headerRTv;
    @BindView(R.id.footer_zz_tv)
    TextView footerZzTv;
    @BindView(R.id.footer_l_tv)
    TextView footerLTv;
    @BindView(R.id.footer_r_tv)
    TextView footerRTv;
    @BindView(R.id.footer_ry_tv)
    TextView footerRyTv;
    @BindView(R.id.footer_jy_tv)
    TextView footerJyTv;
    @BindView(R.id.footer_gz_tv)
    TextView footerGzTv;
    @BindView(R.id.footer_nl_tv)
    TextView footerNlTv;
    private JsaQualificationsAndAbilitiesAdapter qualifyListAdapterA;
    private JsbQualificationsAndAbilitiesAdapter qualifyListAdapterB;
    private JscQualificationsAndAbilitiesAdapter qualifyListAdapterC;
    private JsdQualificationsAndAbilitiesAdapter qualifyListAdapterD;
    private JseQualificationsAndAbilitiesAdapter qualifyListAdapterE;
    List<AptitudeCertificateEntity> list = new ArrayList<>();
    private Long userId = WorkerApplication.get().getLoginBean().getAccount().getNullUser();
    private final int ADD_CERTIFICATION_CODE = 101;
    private final int ADD_EDUCATION_CODE = 101;
    private final int ADD_WORK_CODE = 101;
    private QueryEntry queryEntry;
    private boolean visibilityRvA = false;
    private boolean visibilityRvB = false;
    private boolean visibilityRvC = false;
    private boolean visibilityRvD = false;
    private boolean visibilityRvE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_js_qualifications_and_abilities);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("资质与能力");
        queryEntry = new QueryEntry();
        queryEntry.getEquals().put("accId", String.valueOf(WorkerApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        initRecyclerViewA();
        initRecyclerViewB();
        initRecyclerViewC();
        initRecyclerViewD();
        initRecyclerViewE();
    }

    private void initRecyclerViewA() {
        recyclerViewA.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewA.setNestedScrollingEnabled(false);
        qualifyListAdapterA = new JsaQualificationsAndAbilitiesAdapter(this,true);
        qualifyListAdapterA.bindToRecyclerView(recyclerViewA);
        qualifyListAdapterA.setOnItemClickListener((adapter, view, position) -> startActivityForResult(new Intent(this, AddSkillCertificafeActivity.class).putExtra("bean", (QualificationCertificateEntity) adapter.getData().get(position)), ADD_EDUCATION_CODE));
        initDataA();
    }


    private void initRecyclerViewB() {
        recyclerViewB.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewB.setNestedScrollingEnabled(false);
        qualifyListAdapterB = new JsbQualificationsAndAbilitiesAdapter(true);
        qualifyListAdapterB.bindToRecyclerView(recyclerViewB);
        qualifyListAdapterB.setOnItemClickListener((adapter, view, position) -> startActivityForResult(new Intent(this, AddCertificationActivity.class).putExtra("role", "worker").putExtra("bean", (HonorCertificateEntity) adapter.getData().get(position)), ADD_CERTIFICATION_CODE));
        initDataB();
    }

    private void initRecyclerViewC() {
        recyclerViewC.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewC.setNestedScrollingEnabled(false);
        qualifyListAdapterC = new JscQualificationsAndAbilitiesAdapter(true);
        qualifyListAdapterC.bindToRecyclerView(recyclerViewC);
        qualifyListAdapterC.setOnItemClickListener((adapter, view, position) -> startActivityForResult(new Intent(this, AddEducationHistoryActivity.class).putExtra("bean", (EducationExperienceEntity) adapter.getData().get(position)), ADD_EDUCATION_CODE));
        initDataC();
    }

    private void initRecyclerViewD() {
        recyclerViewD.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewD.setNestedScrollingEnabled(false);
        qualifyListAdapterD = new JsdQualificationsAndAbilitiesAdapter(true);
        qualifyListAdapterD.bindToRecyclerView(recyclerViewD);
        qualifyListAdapterD.setOnItemClickListener((adapter, view, position) -> startActivityForResult(new Intent(this, AddWorkActivity.class).putExtra("bean", (JobExperienceEntity) adapter.getData().get(position)), ADD_WORK_CODE));
        initDataD();
    }

    private void initRecyclerViewE() {
        recyclerViewE.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewE.setNestedScrollingEnabled(false);
        qualifyListAdapterE = new JseQualificationsAndAbilitiesAdapter(true);
        qualifyListAdapterE.bindToRecyclerView(recyclerViewE);
        initDataE();
    }


    private void initDataA() {
        EanfangHttp.post(UserApi.TECH_WORKER_LIST_QUALIFY).upJson(JsonUtils.obj2String(queryEntry)).execute(new EanfangCallback<QualifyCertificafeListBean>(this, true, QualifyCertificafeListBean.class) {
            @Override
            public void onSuccess(QualifyCertificafeListBean bean) {
                if (bean.getList().size() > 0) {
                    qualifyListAdapterA.setNewData(bean.getList());
                }
            }
        });

    }

    private void initDataB() {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD_CERTIFICATE_LIST).upJson(JsonUtils.obj2String(queryEntry)).execute(new EanfangCallback<HonorCerticificateListBean>(this, true, HonorCerticificateListBean.class) {
            @Override
            public void onSuccess(HonorCerticificateListBean bean) {
                if (bean.getList().size() > 0) {
                    qualifyListAdapterB.setNewData(bean.getList());
                }
            }
        });
    }

    private void initDataC() {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_EDUCATION_LIST).upJson(JsonUtils.obj2String(queryEntry)).execute(new EanfangCallback<EducationListBean>(this, true, EducationListBean.class) {
            @Override
            public void onSuccess(EducationListBean bean) {
                if (bean.getList().size() > 0) {
                    qualifyListAdapterC.setNewData(bean.getList());
                }
            }
        });
    }

    private void initDataD() {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_WORK_LIST).upJson(JsonUtils.obj2String(queryEntry)).execute(new EanfangCallback<JobListBean>(this, true, JobListBean.class) {
            @Override
            public void onSuccess(JobListBean bean) {
                if (bean.getList().size() > 0) {
                    qualifyListAdapterD.setNewData(bean.getList());
                }
            }
        });
    }

    private void initDataE() {
        EanfangHttp.get(UserApi.GET_JS_NL + userId + "/TAG").execute(new EanfangCallback<JsCapabilityTagBean>(this, true, JsCapabilityTagBean.class, (bean) -> {
            qualifyListAdapterE.setNewData(bean.getList());
        }));

    }


    @OnClick({R.id.header_r_tv, R.id.nl_r_tv, R.id.gz_r_tv, R.id.jy_r_tv, R.id.footer_zz_tv, R.id.footer_r_tv, R.id.footer_ry_tv, R.id.footer_jy_tv, R.id.footer_gz_tv, R.id.footer_nl_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_r_tv:
                startActivityForResult(new Intent(this, AddSkillCertificafeActivity.class), ADD_EDUCATION_CODE);
                break;
            case R.id.footer_r_tv:
                startActivityForResult(new Intent(this, AddCertificationActivity.class).putExtra("role", "worker"), ADD_CERTIFICATION_CODE);
                break;
            case R.id.jy_r_tv:
                startActivityForResult(new Intent(this, AddEducationHistoryActivity.class), ADD_EDUCATION_CODE);
                break;
            case R.id.gz_r_tv:
                startActivityForResult(new Intent(this, AddWorkActivity.class), ADD_WORK_CODE);
                break;
            case R.id.nl_r_tv:
                startAnimActivity(new Intent(this, JsCapabilityTagActivity.class));
                break;
            case R.id.footer_zz_tv:
                showRvView(visibilityRvA, recyclerViewA, footerZzTv);
                visibilityRvA = !visibilityRvA;
                break;
            case R.id.footer_ry_tv:
                showRvView(visibilityRvB, recyclerViewB, footerRyTv);
                visibilityRvB = !visibilityRvB;
                break;
            case R.id.footer_jy_tv:
                showRvView(visibilityRvC, recyclerViewC, footerJyTv);
                visibilityRvC = !visibilityRvC;
                break;
            case R.id.footer_gz_tv:
                showRvView(visibilityRvD, recyclerViewD, footerGzTv);
                visibilityRvD = !visibilityRvD;
                break;
            case R.id.footer_nl_tv:
                showRvView(visibilityRvE, recyclerViewE, footerNlTv);
                visibilityRvE = !visibilityRvE;
                break;
            default:
        }
    }

    private void showRvView(boolean isV, RecyclerView recyclerView, TextView ryTv) {
        if (!isV) {
            recyclerView.setVisibility(View.VISIBLE);
            ryTv.setText("收起");
        } else {
            recyclerView.setVisibility(View.GONE);
            ryTv.setText("查看所有");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }
}
