package com.eanfang.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.items.OrgOneLevelItem;
import com.eanfang.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectOrganizationContactActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_sure)
    TextView tv_sure;
    TreeRecyclerAdapter treeRecyclerAdapter = new TreeRecyclerAdapter();

    private int i = 0;

    private String isRadio;

    private OrganizationBean mOrganizationBean;
    private SectionBean mSectionBean;
    private Object mOldObj;

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

                        if (!TextUtils.isEmpty(isRadio)) {
                            organizationBean.setFlag(1);//单选
                        }

                        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
                                .params("companyId", organizationBean.getCompanyId())
                                .execute(new EanfangCallback<SectionBean>(SelectOrganizationContactActivity.this, true, SectionBean.class, true, (sectionBeanList) -> {

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

        Uri uri = getIntent().getData();
        String str = uri.getHost();
        if (!TextUtils.isEmpty(str)) {
            tv_sure.setBackgroundColor(getResources().getColor(R.color.color_main_worker));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();

        if (TextUtils.isEmpty(isRadio)) return;

        treeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewHolder viewHolder, int i) {

                if (i == 2) return;

                Object o = treeRecyclerAdapter.getData(i).getData();

                if (mOldObj == null) {
                    mOldObj = o;
                }

                if (mOldObj != o) {
                    if (mOldObj instanceof OrganizationBean) {
                        mOrganizationBean = (OrganizationBean) mOldObj;
                        mOrganizationBean.setChecked(false);


                    } else if (mOldObj instanceof SectionBean) {
                        mSectionBean = (SectionBean) mOldObj;
                        mSectionBean.setChecked(false);
                    }
                }


                if (o instanceof OrganizationBean) {
                    mOrganizationBean = (OrganizationBean) o;
                    mOrganizationBean.setChecked(true);


                } else if (o instanceof SectionBean) {
                    mSectionBean = (SectionBean) o;
                    mSectionBean.setChecked(true);
                }

                mOldObj = o;

            }
        });
    }

    @OnClick(R2.id.tv_sure)
    public void onViewClicked() {

        if (mOldObj == null) {
            ToastUtil.get().showToast(this, "请选择一个部门");
            return;
        }

        EventBus.getDefault().post(mOldObj);
        finishSelf();
    }
}
