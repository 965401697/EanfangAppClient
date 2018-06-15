package com.eanfang.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import com.eanfang.ui.items.OrgTwoLevelItem;
import com.eanfang.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    private OrganizationBean organizationBean;
    private SectionBean mSectionBean;
    private Object mOldObj;
    public static String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_contact);
        ButterKnife.bind(this);
        supprotToolbar();
        startTransaction(true);
        isRadio = getIntent().getStringExtra("isRadio");//单选 多选
        companyId = getIntent().getStringExtra("companyId");//组织架构

        organizationBean = (OrganizationBean) getIntent().getSerializableExtra("organizationBean");//组织架构

        if (TextUtils.isEmpty(isRadio)) {
            setTitle("选择组织联系人");
        } else {
            setTitle("选择组织部门");
        }

        if (!TextUtils.isEmpty(companyId)) {
            setTitle("组织架构");
        }

        initViews();
    }

    /**
     * 获取数据
     */
    private void initData() {

        if (TextUtils.isEmpty(companyId)) {

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
        } else {
            EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
                    .params("companyId", companyId)
                    .execute(new EanfangCallback<SectionBean>(SelectOrganizationContactActivity.this, true, SectionBean.class, true, (sectionBeanList) -> {

                        organizationBean.setSectionBeanList(sectionBeanList);
                        List<OrganizationBean> list = new ArrayList<>();
                        list.add(organizationBean);
                        treeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(list, OrgOneLevelItem.class, null));
                        recyclerView.setAdapter(treeRecyclerAdapter);

                    }));
        }
    }

    private void initViews() {

        Uri uri = getIntent().getData();
        String str = uri.getHost();
        if (!TextUtils.isEmpty(str)) {
            tv_sure.setBackgroundColor(getResources().getColor(R.color.color_main_worker));
        }

        if (!TextUtils.isEmpty(isRadio)) {
            tv_sure.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();

        if (TextUtils.isEmpty(isRadio)) return;

        treeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewHolder viewHolder, int i) {

                Object o = treeRecyclerAdapter.getData(i).getData();

                //如果是第三级就不做任何操作
                if (o instanceof SectionBean.ChildrenBean) {
                    return;
                }

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
