package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.CooperationSearchBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.CooperationSearchAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CooperationSearchClientActivity extends BaseWorkerActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private CooperationSearchAdapter mCooperationSearchAdapter;
    private int mOldPosition = -1;//标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cooperation_search_client);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("搜索客户");
        setLeftBack();
        initViews();
        startTransaction(true);
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCooperationSearchAdapter = new CooperationSearchAdapter(R.layout.item_cooperation_search);
        mCooperationSearchAdapter.bindToRecyclerView(recyclerView);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    initPhoneData(etSearch.getText().toString().trim());
                }
            }
        });
        mCooperationSearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cb_checked) {
                    CooperationSearchBean.ListBean bean = (CooperationSearchBean.ListBean) adapter.getData().get(position);

                    if (mOldPosition == -1) {
                        mOldPosition = position;
                        bean.setChecked(true);
                        adapter.notifyItemChanged(position);
                        return;
                    }

                    if (position == mOldPosition) {
                        mOldPosition = -1;
                        bean.setChecked(false);
                        adapter.notifyItemChanged(position);
                    } else {
                        ((CooperationSearchBean.ListBean) adapter.getData().get(mOldPosition)).setChecked(false);
                        adapter.notifyItemChanged(mOldPosition);

                        mOldPosition = position;
                        bean.setChecked(true);
                        adapter.notifyItemChanged(position);
                    }
                }
            }
        });
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        if (mOldPosition == -1) {
            ToastUtil.get().showToast(CooperationSearchClientActivity.this, "请先搜索客户公司");
            return;
        }

        Intent intent = new Intent(CooperationSearchClientActivity.this, CooperationAddActivity.class);
        intent.putExtra("bean", mCooperationSearchAdapter.getData().get(mOldPosition));
        startActivity(intent);
    }

    /**
     * 根据手机号查找用户
     *
     * @param
     */
    private void initPhoneData(String phone) {

        QueryEntry queryEntry = new QueryEntry();


        queryEntry.getEquals().put("mobile", phone);
        queryEntry.getEquals().put("unitType", "2");

        EanfangHttp.post(NewApiService.COOPERATION_WORKCOMPANY_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CooperationSearchBean>(CooperationSearchClientActivity.this, true, CooperationSearchBean.class, (bean) -> {
                    if (bean.getList().size() > 0) {
                        llSearch.setVisibility(View.VISIBLE);
                        mCooperationSearchAdapter.setNewData(bean.getList());
                    } else {
                        mCooperationSearchAdapter.getData().clear();
                        llSearch.setVisibility(View.INVISIBLE);
                        ToastUtil.get().showToast(CooperationSearchClientActivity.this, "没有客户公司");
                    }
                }));
    }
}
