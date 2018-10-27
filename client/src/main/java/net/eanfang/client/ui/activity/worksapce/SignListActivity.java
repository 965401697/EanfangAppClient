package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.SignListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
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
    @BindView(R.id.tv_sign)
    TextView tvSign;
    private String title;
    private int status;

    private SignListAdapter signListAdapter;

    private List<SignListBean> signListBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("足迹");
        setLeftBack();
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        if (status == 1) tvSign.setText("签退");


        revList.setLayoutManager(new LinearLayoutManager(this));
        revList.addItemDecoration(new BGASpaceItemDecoration(30));
        signListAdapter = new SignListAdapter();
        signListAdapter.bindToRecyclerView(revList);

        llSignLayout.setOnClickListener(v -> finishSelf());

    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(UserApi.SIGN_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SignListBean>(this, true, SignListBean.class, true, (bean) -> {
                    signListBeanList = bean;
                    initAdapter();
                }));
    }

    private void initAdapter() {
        signListAdapter.setNewData(signListBeanList);
        revList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (status == 0) {
                    if (!PermKit.get().getSignInDetailPrem()) return;
                } else {
                    if (!PermKit.get().getSignOutDetailPrem()) return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("id", signListBeanList.get(position).getList().get(position).getId());
                bundle.putInt("status", status);
                bundle.putSerializable("bean", (Serializable) signListBeanList.get(position).getList().get(position));
                JumpItent.jump(SignListActivity.this, SignListDetailActivity.class, bundle);
            }
        });
        revList.setAdapter(signListAdapter);
    }
}
