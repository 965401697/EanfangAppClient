package com.eanfang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.items.OrgOneLevelItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationContactActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    TreeRecyclerAdapter treeRecyclerAdapter = new TreeRecyclerAdapter();

    private int i = 0;
    private String isRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_contact);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("选择组织联系人");
        startTransaction(true);
        isRadio = getIntent().getStringExtra("isRadio");

        initViews();
    }

    /**
     * 获取数据
     */
    private void initData() {
        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_ALL)
                .execute(new EanfangCallback<OrganizationBean>(this, true, OrganizationBean.class, true, (list) -> {

                    for (OrganizationBean organizationBean : list) {

                        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
                                .params("companyId", organizationBean.getCompanyId())
//                                .params("orgCode", organizationBean.getOrgCode())
                                .execute(new EanfangCallback<SectionBean>(OrganizationContactActivity.this, true, SectionBean.class, true, (sectionBeanList) -> {

                                    organizationBean.setSectionBeanList(sectionBeanList);
                                    i++;
                                    if (i == list.size()) {
                                        treeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(list, OrgOneLevelItem.class, null));
                                        recyclerView.setAdapter(treeRecyclerAdapter);
                                    }
                                }));
                    }
                }));
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();

        if (!TextUtils.isEmpty(isRadio)) {
            treeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ViewHolder viewHolder, int i) {
                    if (i != 0) {
                        SectionBean bean = (SectionBean) treeRecyclerAdapter.getData(i).getData();
                        EventBus.getDefault().post(bean);
                        finishSelf();
                    }
                }
            });
        }
    }
}
