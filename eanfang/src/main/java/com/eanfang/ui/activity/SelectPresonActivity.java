package com.eanfang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.adpater.TreeRecyclerType;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.baozi.treerecyclerview.manager.ItemManager;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.items.OrgSelectGroupMultipleItem;
import com.eanfang.ui.items.OrgSelectGroupSingleItem;
import com.eanfang.ui.items.OrgSelectItem;
import com.eanfang.util.ToastUtil;
import com.yaf.sys.entity.UserEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPresonActivity extends BaseActivity {

    @BindView(R2.id.tv_search)
    TextView tvSearch;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private List<TemplateBean.Preson> presonList = new ArrayList<>();
    private List<TemplateBean> mTemplateBeanList = new ArrayList<>();
    private TreeRecyclerAdapter mTreeRecyclerAdapter;

    private int mFlag;
    private String isRadio;

    private TemplateBean.Preson oldPreson;
    private String isOrganization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_level);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        supprotToolbar();
        isRadio = getIntent().getStringExtra("isRadio");//单选 多选
        isOrganization = getIntent().getStringExtra("isOrganization");//组织架构
        mFlag = getIntent().getIntExtra("flag", 0);
        if (mFlag == 2) {
//            mSecondDataList = (ArrayList<SectionBean>) getIntent().getSerializableExtra("list");
            SectionBean sectionBean = (SectionBean) getIntent().getSerializableExtra("bean");
            setTitle(sectionBean.getOrgName());
//            for (SectionBean sectionBean : mSecondDataList) {//循环一个公司全部 部门和员工
            TemplateBean templateBean = new TemplateBean();
            TemplateBean templateBean1 = new TemplateBean();
            List<TemplateBean.Preson> presonArrayList1 = new ArrayList<>();
            if (sectionBean.getChildren() != null) {
                for (SectionBean.ChildrenBean childrens : sectionBean.getChildren()) {
                    List<TemplateBean.Preson> presonArrayList = new ArrayList<>();
                    templateBean.setOrgName(sectionBean.getOrgName() + "-" + childrens.getOrgName());
                    if (!TextUtils.isEmpty(isOrganization)) {
                        templateBean.setVisible(true);
                    }
                    if (childrens.getStaff() != null) {
                        for (SectionBean.ChildrenBean.StaffBean staffBean : childrens.getStaff()) {
                            TemplateBean.Preson preson = new TemplateBean.Preson();
                            preson.setId(staffBean.getAccId());
                            preson.setOrgCode(sectionBean.getOrgCode());
                            preson.setUserId(staffBean.getUserId());
                            preson.setName(staffBean.getAccountEntity().getNickName());
                            preson.setProtraivat(staffBean.getAccountEntity().getAvatar());
                            preson.setMobile(staffBean.getAccountEntity().getMobile());
                            preson.setDepartmentId(staffBean.getDepartmentId());
                            if (!TextUtils.isEmpty(isOrganization)) {
                                preson.setVisible(true);
                            }
                            presonArrayList.add(preson);
                        }

                        templateBean.setPresons(presonArrayList);
                    }
                }
            }

            if (templateBean.getPresons() != null && templateBean.getPresons().size() > 0) {
                mTemplateBeanList.add(templateBean);
            }

            if (!TextUtils.isEmpty(isOrganization)) {
                templateBean1.setVisible(true);
            }

            if (sectionBean.getStaff() != null) {
                List<SectionBean.StaffBeanX> staffBeanXList = sectionBean.getStaff();

                templateBean1.setOrgName(sectionBean.getOrgName());

                for (SectionBean.StaffBeanX staffBeanX : staffBeanXList) {

                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setId(staffBeanX.getAccId());
                    preson.setName(staffBeanX.getAccountEntity().getNickName());
                    preson.setProtraivat(staffBeanX.getAccountEntity().getAvatar());
                    preson.setMobile(staffBeanX.getAccountEntity().getMobile());
                    preson.setUserId(staffBeanX.getUserId());
                    preson.setDepartmentId(staffBeanX.getDepartmentId());
                    if (!TextUtils.isEmpty(isOrganization)) {
                        preson.setVisible(true);
                    }
                    presonArrayList1.add(preson);

                }
                templateBean1.setPresons(presonArrayList1);
            }


            mTemplateBeanList.add(0, templateBean1);


        } else if (mFlag == 3) {
            SectionBean.ChildrenBean childrenBean = (SectionBean.ChildrenBean) getIntent().getSerializableExtra("bean");
            setTitle(childrenBean.getOrgName());
            TemplateBean templateBean = new TemplateBean();
            templateBean.setOrgName(childrenBean.getOrgName());
            if (!TextUtils.isEmpty(isOrganization)) {
                templateBean.setVisible(true);
            }
            List<TemplateBean.Preson> staffBeanList = new ArrayList<>();
            for (SectionBean.ChildrenBean.StaffBean staffBean : childrenBean.getStaff()) {
                TemplateBean.Preson preson = new TemplateBean.Preson();
                preson.setId(staffBean.getAccId());
                preson.setOrgCode(childrenBean.getOrgCode());
                preson.setUserId(staffBean.getUserId());
                preson.setName(staffBean.getAccountEntity().getNickName());
                preson.setProtraivat(staffBean.getAccountEntity().getAvatar());
                preson.setMobile(staffBean.getAccountEntity().getMobile());
                preson.setDepartmentId(staffBean.getDepartmentId());
                if (!TextUtils.isEmpty(isOrganization)) {
                    preson.setVisible(true);
                }
                staffBeanList.add(preson);
            }
            templateBean.setPresons(staffBeanList);
            mTemplateBeanList.add(templateBean);

        } else {

            OrganizationBean organizationBean = (OrganizationBean) getIntent().getSerializableExtra("bean");

            TemplateBean templateBean2 = new TemplateBean();
            List<TemplateBean.Preson> presonArrayList2 = new ArrayList<>();

            setTitle(organizationBean.getOrgName());
            for (SectionBean sectionBean : organizationBean.getSectionBeanList()) {//循环一个公司全部 部门和员工

                TemplateBean templateBean1 = new TemplateBean();
                List<TemplateBean.Preson> presonArrayList1 = new ArrayList<>();

                TemplateBean templateBean = new TemplateBean();
                if (sectionBean.getChildren() != null) {
                    for (SectionBean.ChildrenBean childrens : sectionBean.getChildren()) {
                        List<TemplateBean.Preson> presonArrayList = new ArrayList<>();
                        templateBean.setOrgName(sectionBean.getOrgName() + "-" + childrens.getOrgName());
                        if (!TextUtils.isEmpty(isOrganization)) {
                            templateBean.setVisible(true);
                        }
                        if (childrens.getStaff() != null) {
                            for (SectionBean.ChildrenBean.StaffBean staffBean : childrens.getStaff()) {
                                TemplateBean.Preson preson = new TemplateBean.Preson();
                                preson.setId(staffBean.getAccId());
                                preson.setOrgCode(sectionBean.getOrgCode());
                                preson.setUserId(staffBean.getUserId());
                                preson.setName(staffBean.getAccountEntity().getNickName());
                                preson.setProtraivat(staffBean.getAccountEntity().getAvatar());
                                preson.setMobile(staffBean.getAccountEntity().getMobile());
                                preson.setDepartmentId(staffBean.getDepartmentId());
                                if (!TextUtils.isEmpty(isOrganization)) {
                                    preson.setVisible(true);
                                }
                                presonArrayList.add(preson);
                            }

                            templateBean.setPresons(presonArrayList);
                            mTemplateBeanList.add(templateBean);
                        }
                    }
                }


                if (!TextUtils.isEmpty(isOrganization)) {
                    templateBean1.setVisible(true);
                }

                if (sectionBean.getStaff() != null) {
                    List<SectionBean.StaffBeanX> staffBeanXList = sectionBean.getStaff();

                    templateBean1.setOrgName(sectionBean.getOrgName());

                    for (SectionBean.StaffBeanX staffBeanX : staffBeanXList) {

                        TemplateBean.Preson preson = new TemplateBean.Preson();
                        preson.setId(staffBeanX.getAccId());
                        preson.setName(staffBeanX.getAccountEntity().getNickName());
                        preson.setProtraivat(staffBeanX.getAccountEntity().getAvatar());
                        preson.setMobile(staffBeanX.getAccountEntity().getMobile());
                        preson.setUserId(staffBeanX.getUserId());
                        preson.setDepartmentId(staffBeanX.getDepartmentId());
                        if (!TextUtils.isEmpty(isOrganization)) {
                            preson.setVisible(true);
                        }
                        presonArrayList1.add(preson);

                    }
                    templateBean1.setPresons(presonArrayList1);
                }
                if (templateBean1.getPresons() != null && templateBean1.getPresons().size() > 0) {
                    mTemplateBeanList.add(templateBean1);
                }
            }


            if (organizationBean.getStaff() != null && organizationBean.getStaff().size() > 0) {
                templateBean2.setOrgName("本部门/本公司");

                List<UserEntity> userEntityList = organizationBean.getStaff();

                for (UserEntity userEntity : userEntityList) {

                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setId(String.valueOf(userEntity.getAccountEntity().getAccId()));
                    preson.setName(userEntity.getAccountEntity().getNickName());
                    preson.setProtraivat(userEntity.getAccountEntity().getAvatar());
                    preson.setMobile(userEntity.getAccountEntity().getMobile());
                    preson.setUserId(String.valueOf(userEntity.getUserId()));
                    preson.setDepartmentId(String.valueOf(userEntity.getDepartmentId()));
                    if (!TextUtils.isEmpty(isOrganization)) {
                        preson.setVisible(true);
                    }
                    presonArrayList2.add(preson);

                }
                templateBean2.setPresons(presonArrayList2);


            }

            if (templateBean2.getPresons() != null && templateBean2.getPresons().size() > 0) {
                mTemplateBeanList.add(0, templateBean2);
            }

        }


        initViews();
        if (TextUtils.isEmpty(isOrganization)) {
            setRightTitle("确定");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSure();
                }
            });
        }
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mTreeRecyclerAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_EXPAND);
        if (!TextUtils.isEmpty(isRadio)) {// 单选

            List<TreeItem> treeItemList = ItemHelperFactory.createTreeItemList(mTemplateBeanList, OrgSelectGroupSingleItem.class, null);
            for (TreeItem t : treeItemList) {

                if (t instanceof OrgSelectGroupSingleItem) {
                    ((OrgSelectGroupSingleItem) t).setExpand(true);
                }

            }
            mTreeRecyclerAdapter.getItemManager().replaceAllItem(treeItemList);

        } else {//多选

            List<TreeItem> treeItemList = ItemHelperFactory.createTreeItemList(mTemplateBeanList, OrgSelectGroupMultipleItem.class, null);
            for (TreeItem t : treeItemList) {

                if (t instanceof OrgSelectGroupMultipleItem) {
                    ((OrgSelectGroupMultipleItem) t).setExpand(true);
                }

            }
            mTreeRecyclerAdapter.getItemManager().replaceAllItem(treeItemList);
        }
        recyclerView.setAdapter(mTreeRecyclerAdapter);


        mTreeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewHolder viewHolder, int i) {
                Object o = mTreeRecyclerAdapter.getData(i).getData();
                if (o instanceof TemplateBean.Preson) {
                    TemplateBean.Preson b = (TemplateBean.Preson) o;

                    if (oldPreson == null) {
                        b.setChecked(true);
                        oldPreson = b;
                    } else {
                        if (oldPreson.getId() == b.getId()) {
                            b.setChecked(false);
                            oldPreson = null;
                        } else {
                            oldPreson.setChecked(false);
                            b.setChecked(true);
                            oldPreson = b;
                        }
                    }
                    mTreeRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    public void onSure() {
        List<TreeItem> treeItems = new ArrayList<>();
        List<TreeItem> list = mTreeRecyclerAdapter.getDatas();
        for (TreeItem tree : list) {

            TreeItemGroup parentItem = tree.getParentItem();
            if (parentItem instanceof OrgSelectGroupMultipleItem) {
                treeItems = ((OrgSelectGroupMultipleItem) parentItem).getSelectItems();
                for (TreeItem t : treeItems) {
                    TemplateBean.Preson preson = ((OrgSelectItem) t).getData();
                    if (!presonList.contains(preson)) {
                        presonList.add(preson);
                    }

                }
            } else if (parentItem instanceof OrgSelectGroupSingleItem) {
                if (oldPreson != null) {
                    presonList.add(oldPreson);
                    break;
                }

            }
        }


        if (presonList.size() == 0) {
            ToastUtil.get().showToast(this, "你还没有选择人员");
            return;
        }
        EventBus.getDefault().post(presonList);
        endTransaction(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
