package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.SignListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/24  22:18
 * @email houzhongzhou@yeah.net
 * @desc 签到列表
 */

public class SignListActivity extends BaseActivity {
    @BindView(R.id.rev_list)
    RecyclerView revList;
    @BindView(R.id.ll_sign_layout)
    LinearLayout llSignLayout;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    private String title;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        setTitle("足迹");
        setLeftBack();
        llSignLayout.setOnClickListener(v -> startActivity(new Intent(SignListActivity.this, SignActivity.class)
                .putExtra("title", title)
                .putExtra("status", status)));
        initData();
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("status", status + "");
        queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(UserApi.GET_SIGN_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SignListBean>(this, true, SignListBean.class, (bean) -> {
                    initAdapter(bean.getList());
                }));
    }

    private void initAdapter(List<SignListBean.ListBean> mDataList) {
        revList.setLayoutManager(new LinearLayoutManager(this));
        SignListAdapter adapter = new SignListAdapter(R.layout.item_sign_list, mDataList);
        revList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(SignListActivity.this, SignListDetailActivity.class)
                        .putExtra("id", mDataList.get(position).getId())
                        .putExtra("bean", mDataList.get(position))
                        .putExtra("title", title)
                        .putExtra("status", status));
            }
        });
        revList.setAdapter(adapter);
    }
}
