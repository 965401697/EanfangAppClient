package net.eanfang.worker.ui.activity.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AptitudeCertificateBean;
import com.eanfang.model.HonorCerticificateListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.HonorCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.AddCertificationActivity;
import net.eanfang.worker.ui.activity.my.certification.CertificateListAdapter;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AddAuthQualifyActivity;
import net.eanfang.worker.ui.adapter.QualifyListAdapter;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class QualificationsAndHonoursActivity extends BaseActivity {
    @BindView(R.id.recycler_view_a)
    RecyclerView recyclerViewA;
    @BindView(R.id.recycler_view_b)
    RecyclerView recyclerViewB;
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
    private int isAuth;
    private Long mOrgId;
    private QualifyListAdapter qualifyListAdapterA;
    private CertificateListAdapter qualifyListAdapterB;
    private boolean visibilityRvA = false;
    private boolean visibilityRvB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualifications_and_honours);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("资质与荣誉");
        isAuth = getIntent().getIntExtra("isAuth", 0);
        mOrgId = getIntent().getLongExtra("orgid", 0);
        initRecyclerViewA();
        initRecyclerViewB();
    }

    private void initRecyclerViewA() {
        recyclerViewA.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewA.setNestedScrollingEnabled(false);
        qualifyListAdapterA = new QualifyListAdapter(true);
        qualifyListAdapterA.bindToRecyclerView(recyclerViewA);
        qualifyListAdapterA.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", (Serializable) adapter.getData().get(position));
            bundle.putString("isAuth", isAuth + "");
            bundle.putLong("orgid", mOrgId);
            JumpItent.jump(this, AddAuthQualifyActivity.class, bundle);
        });
        initDataA();
    }

    private void initRecyclerViewB() {
        recyclerViewB.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewB.setNestedScrollingEnabled(false);
        qualifyListAdapterB = new CertificateListAdapter();
        qualifyListAdapterB.bindToRecyclerView(recyclerViewB);
        qualifyListAdapterB.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", (HonorCertificateEntity) adapter.getData().get(position));
            bundle.putString("role", "company");
            bundle.putLong("orgid", mOrgId);
            JumpItent.jump(this, AddCertificationActivity.class, bundle);
        });
        initDataB();
    }

    private void initDataA() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orgId", mOrgId + "");
        queryEntry.getEquals().put("type", "0");
        queryEntry.setPage(1);
        queryEntry.setSize(100);
        EanfangHttp.post(UserApi.LIST_QUALIFY).upJson(JsonUtils.obj2String(queryEntry)).execute(new EanfangCallback<AptitudeCertificateBean>(this, true, AptitudeCertificateBean.class) {
            @Override
            public void onSuccess(AptitudeCertificateBean bean) {
                Log.d("wq66", "onSuccess: " + bean.toString());
                if (bean.getList().size() > 0) {
                    qualifyListAdapterA.setNewData(bean.getList());
                }
            }
        });
    }

    private void initDataB() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orgId", mOrgId + "");
        queryEntry.getEquals().put("type", "0");
        queryEntry.setPage(1);
        queryEntry.setSize(100);
        EanfangHttp.post(UserApi.COMPANY_CERTIFICATE_LIST).upJson(JsonUtils.obj2String(queryEntry)).execute(new EanfangCallback<HonorCerticificateListBean>(this, true, HonorCerticificateListBean.class) {
            @Override
            public void onSuccess(HonorCerticificateListBean bean) {
                if (bean.getList().size() > 0) {
                    qualifyListAdapterB.setNewData(bean.getList());
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    @OnClick({R.id.header_r_tv, R.id.footer_zz_tv, R.id.footer_r_tv, R.id.footer_ry_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_r_tv:
                Intent intentA = new Intent(this, AddAuthQualifyActivity.class);
                intentA.putExtra("orgid", mOrgId);
                intentA.putExtra("isAuth", isAuth + "");
                startActivity(intentA);
                break;
            case R.id.footer_r_tv:
                Bundle bundle = new Bundle();
                bundle.putLong("orgid", mOrgId);
                bundle.putString("role", "company");
                JumpItent.jump(this, AddCertificationActivity.class, bundle);
                break;
            case R.id.footer_zz_tv:
                showRvView(visibilityRvA,recyclerViewA,footerZzTv);
                visibilityRvA = !visibilityRvA;
                break;
            case R.id.footer_ry_tv:
                showRvView(visibilityRvB,recyclerViewB,footerRyTv);
                visibilityRvB = !visibilityRvB;
                break;
            default:
        }
    }
    private void showRvView(boolean isV, RecyclerView recyclerView, TextView ryTv) {
        if (!isV) {
            recyclerView.setVisibility(View.VISIBLE);
            ryTv.setText("收起 ⇧");
        } else {
            recyclerView.setVisibility(View.GONE);
            ryTv.setText("查看所有 ⇩");
        }
    }
}
