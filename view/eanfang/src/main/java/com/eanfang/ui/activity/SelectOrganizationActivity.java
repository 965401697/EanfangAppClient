package com.eanfang.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.adpater.TreeRecyclerType;
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

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectOrganizationActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    TreeRecyclerAdapter treeRecyclerAdapter;

    private int i = 0;

    private String isRadio;
    private String isSection;

    private OrganizationBean mOrganizationBean;
    private OrganizationBean organizationBean;
    private SectionBean mSectionBean;
    private SectionBean.ChildrenBean mChildrenBean;
    private Object mOldObj;
    private String companyId;
    private String isOrganization;
    private String isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_contact);
        ButterKnife.bind(this);
        supprotToolbar();
        startTransaction(true);
        isRadio = getIntent().getStringExtra("isRadio");//单选
        isSection = getIntent().getStringExtra("isSection");//部门单选
        isAdd = getIntent().getStringExtra("isAdd");//部门添加人员  要展示第三级的checkbox
        companyId = getIntent().getStringExtra("companyId");//组织架构
        isOrganization = getIntent().getStringExtra("isOrganization");//组织架构

        mOrganizationBean = (OrganizationBean) getIntent().getSerializableExtra("organizationBean");//组织架构

        if (TextUtils.isEmpty(isSection)) {
            setTitle("选择组织联系人");
        } else {
            setTitle("选择组织部门");
            setRightTitle("确定");
            setRightTitleOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mOldObj == null) {
                        ToastUtil.get().showToast(SelectOrganizationActivity.this, "请选择一个部门");
                        return;
                    }

                    EventBus.getDefault().post(mOldObj);
                    finishSelf();
                }
            });
        }

        if (!TextUtils.isEmpty(companyId)) {
            setTitle("组织架构");
            treeRecyclerAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_ALL);
        } else {
            treeRecyclerAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_EXPAND);
        }

        initViews();
    }

    /**
     * 获取数据
     */
    private void initData() {

//        if (TextUtils.isEmpty(companyId)) {

            EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_ALL)
                    .execute(new EanfangCallback<OrganizationBean>(this, true, OrganizationBean.class, true, (list) -> {

                        for (OrganizationBean organizationBean : list) {

                            if (!TextUtils.isEmpty(isRadio)) {
                                organizationBean.setFlag(1);//单选
                            } else if (!TextUtils.isEmpty(isSection)) {
                                organizationBean.setFlag(2);//部门单选
                                if (!TextUtils.isEmpty(isAdd)) {
                                    organizationBean.setAdd(true);
                                }
                            } else if (mOrganizationBean != null) {
                                organizationBean.setFlag(3);//组织架构
                            }

                            EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
                                    .params("companyId", organizationBean.getCompanyId())
                                    .execute(new EanfangCallback<SectionBean>(SelectOrganizationActivity.this, true, SectionBean.class, true, (sectionBeanList) -> {

                                        organizationBean.setSectionBeanList(sectionBeanList);
                                        i++;
                                        if (i == list.size()) {
                                            treeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(list, OrgOneLevelItem.class, null));
                                            recyclerView.setAdapter(treeRecyclerAdapter);
                                        }
                                    }));
                        }
                    }));
//        }
//        else {
//            EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
//                    .params("companyId", companyId)
//                    .execute(new EanfangCallback<SectionBean>(SelectOrganizationActivity.this, true, SectionBean.class, true, (sectionBeanList) -> {
//                        organizationBean.setSectionBeanList(sectionBeanList);
//                        organizationBean.setFlag(3);//组织架构
//                        List<OrganizationBean> list = new ArrayList<>();
//                        list.add(organizationBean);
//                        treeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(list, OrgOneLevelItem.class, null));
//                        recyclerView.setAdapter(treeRecyclerAdapter);
//
//                    }));
//        }
    }

    private void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();

        if (TextUtils.isEmpty(isSection)) {
            return;
        }

        treeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewHolder viewHolder, int i) {

                Object o = treeRecyclerAdapter.getData(i).getData();

                //如果是第三级就不做任何操作
//                if (o instanceof SectionBean.ChildrenBean) {
//                    return;
//                }

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
                    } else if (mOldObj instanceof SectionBean.ChildrenBean) {
                        mChildrenBean = (SectionBean.ChildrenBean) mOldObj;
                        mChildrenBean.setChecked(false);
                        treeRecyclerAdapter.notifyDataSetChanged();
                    }
                }


                if (o instanceof OrganizationBean) {
                    mOrganizationBean = (OrganizationBean) o;
                    mOrganizationBean.setChecked(true);


                } else if (o instanceof SectionBean) {
                    mSectionBean = (SectionBean) o;
                    mSectionBean.setChecked(true);
                } else if (o instanceof SectionBean.ChildrenBean) {
                    mChildrenBean = (SectionBean.ChildrenBean) o;
                    mChildrenBean.setChecked(true);
                    treeRecyclerAdapter.notifyDataSetChanged();
                }

                mOldObj = o;

            }
        });
    }
}
