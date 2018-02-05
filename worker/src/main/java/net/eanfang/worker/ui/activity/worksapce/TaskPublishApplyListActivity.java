package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ApplyTaskListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ApplyListAdapter;
import net.eanfang.worker.ui.widget.TaskPubApplyListDetailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/18  14:33
 * @email houzhongzhou@yeah.net
 * @desc 发包申请列表
 */

public class TaskPublishApplyListActivity extends BaseActivity {
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swiprefresh)
    SwipyRefreshLayout swiprefresh;

    private List<ApplyTaskListBean.ListBean> mDataList = new ArrayList<>();
    private ApplyListAdapter adapter;
    private Long shopTaskPublishId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("申请列表");
        setLeftBack();
        shopTaskPublishId = getIntent().getLongExtra("shopTaskPublishId", 0);
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("shopTaskPublishId", shopTaskPublishId + "");
        EanfangHttp.post(NewApiService.TASK_APPLY_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ApplyTaskListBean>(this, true, ApplyTaskListBean.class, (bean) -> {
                    mDataList = bean.getList();
                    initAdapter();
                }));
    }

    private void initAdapter() {
        adapter = new ApplyListAdapter(mDataList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskPubApplyListDetailView(TaskPublishApplyListActivity.this, true, mDataList.get(position).getId()).show();
            }
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_select:
                    ignoreOrSelect(mDataList.get(position).getShopTaskPublishId(),
                            mDataList.get(position).getId(), 1,
                            mDataList.get(position).getCreateCompanyId(),
                            mDataList.get(position).getCreateOrgCode(),
                            mDataList.get(position).getCreateTopCompanyId(),
                            mDataList.get(position).getCreateUserId(),
                            mDataList.get(position).getProjectQuote());
                    break;
                case R.id.tv_ignore:
                    ignoreOrSelect(mDataList.get(position).getShopTaskPublishId(),
                            mDataList.get(position).getId(), 2,
                            mDataList.get(position).getCreateCompanyId(),
                            mDataList.get(position).getCreateOrgCode(),
                            mDataList.get(position).getCreateTopCompanyId(),
                            mDataList.get(position).getCreateUserId(),
                            mDataList.get(position).getProjectQuote());
                    break;
                default:
                    break;
            }
        });
    }

    private void ignoreOrSelect(Long shopTaskPublishId, Long id, int status, Long createCompanyId,
                                String createOrgCode, Long createTopCompanyId, Long createUserId, int projectQuote) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shopTaskPublishId", shopTaskPublishId);
        jsonObject.put("id", id);
        jsonObject.put("status", status);
        jsonObject.put("createCompanyId", createCompanyId);
        jsonObject.put("createOrgCode", createOrgCode);
        jsonObject.put("createTopCompanyId", createTopCompanyId);
        jsonObject.put("createUserId", createUserId);
        jsonObject.put("projectQuote", projectQuote);

        String json = JSONObject.toJSONString(jsonObject);
        EanfangHttp.post(NewApiService.TASK_APPLY_LIST_UPDATE)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    if (status == 1) {
                        showToast("中标");
                    } else {
                        showToast("忽略");
                    }
                }));
    }

}
