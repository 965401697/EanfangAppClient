package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AptitudeCertificateBean;
import com.eanfang.biz.model.Message;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.AptitudeCertificateEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.adapter.QualifyListAdapter;
import net.eanfang.worker.ui.fragment.ContactsFragment;

import org.greenrobot.eventbus.EventBus;
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
                .execute(new EanfangCallback<AptitudeCertificateBean>(this, true, AptitudeCertificateBean.class) {
                    @Override
                    public void onSuccess(AptitudeCertificateBean bean) {
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
                commitVerfiy();
                break;
        }
    }

    /**
     * 提交认证
     */
    private void commitVerfiy() {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SEND_VERIFY + mOrgId)
                .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(this, true, com.alibaba.fastjson.JSONObject.class, (bean) -> {
                    doFinish();
                }));
    }

    private void doFinish() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent("尊敬的用户，您可以添加荣誉证书， 以获得更多订单");
        message.setTip("确定");
        bundle.putSerializable("message", message);
        JumpItent.jump(AuthQualifyListActivity.this, StateChangeActivity.class, bundle);
        WorkerApplication.get().closeActivity(AuthQualifyFirstActivity.class);
        WorkerApplication.get().closeActivity(AuthQualifySecondActivity.class);
        EventBus.getDefault().post("workerIsAuthing");
        ContactsFragment.isRefresh = true;//从认领企业过来 完成认证刷新公司
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
