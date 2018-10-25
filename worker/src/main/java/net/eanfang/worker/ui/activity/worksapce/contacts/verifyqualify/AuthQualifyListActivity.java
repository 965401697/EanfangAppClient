package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

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
import com.eanfang.model.Message;
import com.eanfang.model.QualifyListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.AptitudeCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.adapter.QualifyListAdapter;

import org.json.JSONObject;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/10/22
 * @description 资质证书列表页
 */

public class AuthQualifyListActivity extends BaseActivity {

    private static final int REQUEST_AUTH = 100;

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    private QualifyListAdapter qualifyListAdapter;
    // 认证状态
    private int verifyStatus;

    private Long mOrgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_qualify_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("技能资质");
        setLeftBack();
        verifyStatus = getIntent().getIntExtra("verifyStatus", 0);
        mOrgId = getIntent().getLongExtra("orgid", 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        qualifyListAdapter = new QualifyListAdapter(true);
        qualifyListAdapter.bindToRecyclerView(recyclerView);


        qualifyListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                delete(adapter, position);
            }
        });

        qualifyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", (Serializable) adapter.getData().get(position));
                bundle.putInt("verifyStatus", verifyStatus);
                JumpItent.jump(AuthQualifyListActivity.this, AddAuthQualifyActivity.class, bundle);
            }
        });
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("orgId", mOrgId + "");
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<QualifyListBean>(this, true, QualifyListBean.class) {
                    @Override
                    public void onSuccess(QualifyListBean bean) {
                        if (bean.getList().size() > 0) {
                            qualifyListAdapter.setNewData(bean.getList());
                            tvSub.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    @OnClick({R.id.tv_add, R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                Bundle bundle = new Bundle();
                bundle.putInt("verifyStatus", verifyStatus);
                bundle.putLong("orgid", mOrgId);
                JumpItent.jump(AuthQualifyListActivity.this, AddAuthQualifyActivity.class, bundle, REQUEST_AUTH);
                break;
            case R.id.tv_sub:
                doFinish();
                break;
        }
    }

    private void doFinish() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent("尊敬的用户，您可以添加荣誉证书， 以获得更多订单");
        message.setTip("确定");
        bundle.putSerializable("message", message);
        JumpItent.jump(AuthQualifyListActivity.this, StateChangeActivity.class, bundle);
        EanfangApplication.get().closeActivity(AuthQualifyFirstActivity.class.getName());
        EanfangApplication.get().closeActivity(AuthQualifySecondActivity.class.getName());
        finishSelf();
    }

    private void delete(BaseQuickAdapter adapter, int position) {
        EanfangHttp.post(UserApi.DELETE_QUALIFY + "/" + ((AptitudeCertificateEntity) adapter.getData().get(position)).getId())
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_AUTH) {
            initData();
            if (tvSub.getVisibility() == View.GONE) {
                tvSub.setVisibility(View.VISIBLE);
            }
        }
    }
}
