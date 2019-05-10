package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrgUnitListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;
import com.eanfang.model.sys.OrgUnitEntity;

import net.eanfang.worker.R;

import net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo.AuthCompanyFirstActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatTeamActivity extends BaseWorkerActivity {

    @BindView(R.id.et_input_company)
    EditText etInputCompany;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    private CompanyListAdapter mCompanyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_team);
        ButterKnife.bind(this);
        setLeftBack();
        setRightTitle("创建");
        startTransaction(true);
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (etInputCompany.getText().toString().trim().length() <= 3) {
//                    showToast("真实团队/公司名称必须大于三个字符");
//                    return;
//                }
                startActivity(getIntent().setClass(CreatTeamActivity.this, CreatTeamDetailActivity.class).putExtra("name", etInputCompany.getText().toString()));
            }
        });
        setTitle("创建团队");
        etInputCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    recyclerView.setVisibility(View.GONE);
                    tvDesc.setVisibility(View.GONE);
                } else {
                    //输入之后
                    searchClaimCompany(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 获取待认领企业的列表
     */
    private void searchClaimCompany(String nameKey) {

        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getLike().put("name", nameKey);
        EanfangHttp.post(UserApi.GET_CLAIM_ORGUNIT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<OrgUnitListBean>(this, false, OrgUnitListBean.class, (bean) -> {
                    if (bean.getList().size() > 0) {
                        initAdapter(bean);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvDesc.setVisibility(View.GONE);
                    }
                }));
    }


    private void initAdapter(OrgUnitListBean bean) {

        recyclerView.setVisibility(View.VISIBLE);
        tvDesc.setVisibility(View.VISIBLE);

        if (mCompanyListAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mCompanyListAdapter = new CompanyListAdapter();
            mCompanyListAdapter.bindToRecyclerView(recyclerView);

            mCompanyListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_claim) {

                        OrgUnitEntity orgUnitEntity = (OrgUnitEntity) adapter.getData().get(position);

                        if (orgUnitEntity.getIsclaim() == 1) {
                            ToastUtil.get().showToast(CreatTeamActivity.this, "当前企业已被认领");
                            return;
                        }

                        startActivity(new Intent(CreatTeamActivity.this, AuthCompanyFirstActivity.class).
                                putExtra("orgName", orgUnitEntity.getName()).putExtra("orgid", orgUnitEntity.getOrgId()));

                        finishSelf();
                    }
                }
            });
        }
        mCompanyListAdapter.setNewData(bean.getList());
    }
}
