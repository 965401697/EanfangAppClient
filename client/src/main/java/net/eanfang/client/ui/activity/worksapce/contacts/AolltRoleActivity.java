package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RoleBean;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AolltRoleActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private AolltRoleAdapter aolltRoleAdapter;

    private int oldPositon = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aollt_role);
        ButterKnife.bind(this);
        setTitle("分配角色");
        setLeftBack();
        initViews();
        initData();

        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPositon != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("bean", aolltRoleAdapter.getData().get(oldPositon));
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    private void initData() {
        EanfangHttp.get(NewApiService.MY_LIST_ROLE)
                .execute(new EanfangCallback<RoleBean>(AolltRoleActivity.this, true, RoleBean.class, true, (list) -> {

                    aolltRoleAdapter.setNewData(list);
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aolltRoleAdapter = new AolltRoleAdapter(R.layout.item_role);
        aolltRoleAdapter.bindToRecyclerView(recyclerView);


        aolltRoleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RoleBean bean = (RoleBean) adapter.getData().get(position);

                if (oldPositon == -1) {
                    oldPositon = position;
                    bean.setChecked(true);
                    adapter.notifyDataSetChanged();
                    return;
                }

                if (position == oldPositon) {
                    oldPositon = -1;
                    bean.setChecked(false);
                } else {
                    ((RoleBean) adapter.getData().get(oldPositon)).setChecked(false);
                    oldPositon = position;

                    bean.setChecked(true);
                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    /**
     * 角色选择的adapter
     */
    class AolltRoleAdapter extends BaseQuickAdapter<RoleBean, BaseViewHolder> {


        public AolltRoleAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, RoleBean item) {

            if (item.isChecked()) {
                helper.getView(R.id.iv_checked).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.iv_checked).setVisibility(View.INVISIBLE);
            }

            helper.setText(R.id.tv_role, item.getRoleName());
        }
    }
}
