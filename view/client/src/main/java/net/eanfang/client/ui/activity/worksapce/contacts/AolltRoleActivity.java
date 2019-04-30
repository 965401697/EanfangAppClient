package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RoleBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
@Deprecated
public class AolltRoleActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private AolltRoleAdapter aolltRoleAdapter;

    private ArrayList<String> roleIdList = new ArrayList<>();
    private ArrayList<String> roleNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aollt_role);
        ButterKnife.bind(this);
        setTitle("分配角色");
        setLeftBack();
        initViews();
        initData();
        setRightTitle("确定");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roleIdList.size() > 0) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("roleIdList", roleIdList);
                    intent.putStringArrayListExtra("roleNameList", roleNameList);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.get().showToast(AolltRoleActivity.this, "请选择角色");
                }
            }
        });

        roleNameList = getIntent().getStringArrayListExtra("roleNameList");
    }

    private void initData() {
        EanfangHttp.get(NewApiService.MY_LIST_ROLE)
                .execute(new EanfangCallback<RoleBean>(AolltRoleActivity.this, true, RoleBean.class, true, (list) -> {
                    if (roleNameList != null) {
                        for (RoleBean roleBean : list) {
                            if (roleNameList.contains(roleBean.getRoleName())) {
                                roleBean.setChecked(true);
                                roleIdList.add(roleBean.getRoleId());
                            }
                        }
                    } else {
                        roleNameList = new ArrayList<>();
                    }
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

                if (bean.isChecked()) {
                    bean.setChecked(false);
                    roleIdList.remove(bean.getRoleId());
                    roleNameList.remove(bean.getRoleName());

                } else {
                    bean.setChecked(true);
                    roleIdList.add(bean.getRoleId());
                    roleNameList.add(bean.getRoleName());
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
