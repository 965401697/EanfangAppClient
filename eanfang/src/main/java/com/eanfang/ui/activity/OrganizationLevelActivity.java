package com.eanfang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.items.OrgSelectGroupItem;
import com.eanfang.ui.items.OrgSelectItem;
import com.eanfang.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationLevelActivity extends BaseActivity {

    @BindView(R2.id.tv_search)
    TextView tvSearch;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.tv_sure)
    TextView tvSure;

    private List<TemplateBean.Preson> presonList = new ArrayList<>();


    private ArrayList<SectionBean.ChildrenBean> mThreeDataList;
    private ArrayList<SectionBean> mSecondDataList;
    private List<TemplateBean> mTemplateBeanList = new ArrayList<>();
    private TreeRecyclerAdapter mTreeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_level);
        ButterKnife.bind(this);
        supprotToolbar();
        int flag = getIntent().getIntExtra("flag", 0);
        if (flag == 2) {
//            mSecondDataList = (ArrayList<SectionBean>) getIntent().getSerializableExtra("list");
            SectionBean sectionBean = (SectionBean) getIntent().getSerializableExtra("bean");
            setTitle(sectionBean.getOrgName());
//            for (SectionBean sectionBean : mSecondDataList) {//循环一个公司全部 部门和员工
            TemplateBean templateBean = new TemplateBean();
            if (sectionBean.getChildren() != null) {
                for (SectionBean.ChildrenBean childrens : sectionBean.getChildren()) {
                    List<TemplateBean.Preson> presonArrayList = new ArrayList<>();
                    templateBean.setOrgName(sectionBean.getOrgName() + "-" + childrens.getOrgName());
                    if (childrens.getStaff() != null) {
                        for (SectionBean.ChildrenBean.StaffBean staffBean : childrens.getStaff()) {
                            TemplateBean.Preson preson = new TemplateBean.Preson();
                            preson.setId(staffBean.getAccId());
                            preson.setName(staffBean.getAccountEntity().getNickName());
                            preson.setProtraivat(staffBean.getAccountEntity().getAvatar());
                            preson.setMobile(staffBean.getAccountEntity().getMobile());
                            presonArrayList.add(preson);
                        }

                        templateBean.setPresons(presonArrayList);
                    }
                }
            }
            mTemplateBeanList.add(templateBean);
//            }


        } else if (flag == 3) {
            SectionBean.ChildrenBean childrenBean = (SectionBean.ChildrenBean) getIntent().getSerializableExtra("bean");
            setTitle(childrenBean.getOrgName());
//            for (SectionBean.ChildrenBean bean : mThreeDataList) {
            TemplateBean templateBean = new TemplateBean();
            templateBean.setOrgName(childrenBean.getOrgName());
            List<TemplateBean.Preson> staffBeanList = new ArrayList<>();
            for (SectionBean.ChildrenBean.StaffBean staffBean : childrenBean.getStaff()) {
                TemplateBean.Preson preson = new TemplateBean.Preson();
                preson.setId(staffBean.getAccId());
                preson.setName(staffBean.getAccountEntity().getNickName());
                preson.setProtraivat(staffBean.getAccountEntity().getAvatar());
                preson.setMobile(staffBean.getAccountEntity().getMobile());
                staffBeanList.add(preson);
            }
            templateBean.setPresons(staffBeanList);
            mTemplateBeanList.add(templateBean);
//            }

        } else {

        }
        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mTreeRecyclerAdapter = new TreeRecyclerAdapter();
        mTreeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(mTemplateBeanList, OrgSelectGroupItem.class, null));
        recyclerView.setAdapter(mTreeRecyclerAdapter);
    }

    @OnClick(R2.id.tv_sure)
    public void onViewClicked() {

        List<TreeItem> list = mTreeRecyclerAdapter.getDatas();
        for (TreeItem tree : list) {
            if (tree instanceof OrgSelectItem) {
                TemplateBean.Preson preson = ((OrgSelectItem) tree).getData();
                if (preson.isChecked()) {
                    presonList.add(preson);
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
}
