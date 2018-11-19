package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkCompanyListBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.SearchCompanyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  21:00
 * @email houzhongzhou@yeah.net
 * @desc 搜索公司
 */

/* *
 * 2018年10月10日 10:12:30
 *
 * */
@Deprecated
public class SearchCompanyListView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_searchphone)
    EditText etSearchphone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Activity mContext;

    public SearchCompanyListView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_search_cooperation_company);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("搜索客户");
        tvSearch.setOnClickListener((v) -> {
            //处理绑定公司搜索时的bug
            String searchPhone = etSearchphone.getText().toString().trim();
            if (StringUtils.isEmpty(searchPhone)) {
                showToast("搜索内容不能为空");
                return;
            }
            submit();
        });

    }

    private void submit() {
        String searchphone = etSearchphone.getText().toString().trim();
        if (TextUtils.isEmpty(searchphone)) {
            showToast("搜索客户名称");
            return;
        }
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyName", searchphone);
        queryEntry.getEquals().put("unitType", "2");
        EanfangHttp.post(UserApi.GET_WORKCOMPANY_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkCompanyListBean>(mContext, true, WorkCompanyListBean.class, (bean) -> {
                    initAdapter(bean.getList());
                }));
    }

    private void initAdapter(List<WorkCompanyListBean.ListBean> mDataList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        SearchCompanyAdapter adapter = new SearchCompanyAdapter(mDataList);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            new BindCompanyView(mContext, true, mDataList.get(position).getOrgId(),
                    mDataList.get(position).getCompanyEntity().getName()).show();
            dismiss();
        });
        recyclerView.setAdapter(adapter);
    }
}
