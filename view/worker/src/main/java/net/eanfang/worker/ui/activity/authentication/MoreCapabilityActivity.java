package net.eanfang.worker.ui.activity.authentication;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SgZzNlBean;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MoreCapabilityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class MoreCapabilityActivity extends BaseActivity {

    @BindView(R.id.header_l_tv)
    TextView headerLTv;
    @BindView(R.id.header_r_tv)
    TextView headerRTv;
    @BindView(R.id.recycler_view_a)
    RecyclerView recyclerViewA;
    @BindView(R.id.footer_sz_tv)
    TextView footerSzTv;
    @BindView(R.id.footer_l_tv)
    TextView footerLTv;
    @BindView(R.id.footer_r_tv)
    TextView footerRTv;
    @BindView(R.id.recycler_view_b)
    RecyclerView recyclerViewB;
    @BindView(R.id.footer_gj_tv)
    TextView footerGjTv;
    private boolean visibilityRvA = false;
    private boolean visibilityRvB = false;
    private int isAuth;
    private Long mOrgId;
    private MoreCapabilityAdapter listAdapterA;
    private MoreCapabilityAdapter listAdapterB;
    private List<SgZzNlBean.ListBean> listBeanA = new ArrayList<>();
    private List<SgZzNlBean.ListBean> listBeanB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_capability);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("更多能力");
        isAuth = getIntent().getIntExtra("isAuth", 0);
        mOrgId = getIntent().getLongExtra("orgid", 0);
        initRecyclerViewA();
        initRecyclerViewB();
    }

    private void initRecyclerViewA() {
        recyclerViewA.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewA.setNestedScrollingEnabled(false);
        listAdapterA = new MoreCapabilityAdapter(true);
        listAdapterA.bindToRecyclerView(recyclerViewA);
        initDataA();
    }

    private void initRecyclerViewB() {
        recyclerViewB.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewB.setNestedScrollingEnabled(false);
        listAdapterB = new MoreCapabilityAdapter(true);
        listAdapterB.bindToRecyclerView(recyclerViewB);
        initDataB();
    }

    private void initDataA() {
        EanfangHttp.post(UserApi.SG_ZZ_NL_QY_NL).params("orgId", mOrgId).execute(new EanfangCallback<SgZzNlBean>(this, true, SgZzNlBean.class, (bean) -> {
            listBeanA = bean.getList();
            listAdapterA.setNewData(listBeanA);
        }));
    }

    private void initDataB() {
        EanfangHttp.post(UserApi.GJ_JX_SB_QY_NL).params("orgId", mOrgId).execute(new EanfangCallback<SgZzNlBean>(this, true, SgZzNlBean.class, (bean) -> {
            listBeanB = bean.getList();
            listAdapterB.setNewData(listBeanB);
        }));
    }

    @OnClick({R.id.header_r_tv, R.id.footer_sz_tv, R.id.footer_r_tv, R.id.footer_gj_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_r_tv:
                Intent intent = new Intent(this, MechanicalOrganizationActivity.class);
                intent.putExtra("orgid", mOrgId);
                intent.putExtra("isAuth", isAuth);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.footer_r_tv:
                Intent intentb = new Intent(this, MechanicalOrganizationActivity.class);
                intentb.putExtra("orgid", mOrgId);
                intentb.putExtra("isAuth", isAuth);
                intentb.putExtra("type", 2);
                startActivity(intentb);
                break;
            case R.id.footer_sz_tv:
                showRvView(visibilityRvA, recyclerViewA, footerSzTv);
                visibilityRvA = !visibilityRvA;
                break;
            case R.id.footer_gj_tv:
                showRvView(visibilityRvB, recyclerViewB, footerGjTv);
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
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }
}
