package com.eanfang.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择联系人
 */
public class SelectContactActivity extends BaseActivity {


    @BindView(R2.id.rl_selected)
    RelativeLayout rlSelected;
    @BindView(R2.id.recycler_view_hori)
    RecyclerView recyclerViewHori;
    @BindView(R2.id.tv_sure)
    TextView tvSure;
    private HeaderIconAdapter mHeaderIconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("选择联系人");

        findViewById(R.id.rl_organization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectContactActivity.this, OrganizationContactActivity.class));
            }
        });

        initViews();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHori.setLayoutManager(linearLayoutManager);
        mHeaderIconAdapter = new HeaderIconAdapter(R.layout.item_header_icon);
        mHeaderIconAdapter.bindToRecyclerView(recyclerViewHori);
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {
            tvSure.setVisibility(View.VISIBLE);
            rlSelected.setVisibility(View.VISIBLE);
            mHeaderIconAdapter.getData().clear();
            mHeaderIconAdapter.setNewData(presonList);
        }
    }

    @OnClick(R2.id.tv_sure)
    public void onViewClicked() {
        ToastUtil.get().showToast(this, "待开发");
    }

    class HeaderIconAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {

        public HeaderIconAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
            ((ImageView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()));
        }
    }
}
