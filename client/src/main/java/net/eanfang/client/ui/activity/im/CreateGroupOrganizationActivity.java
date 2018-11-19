package net.eanfang.client.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGroupOrganizationActivity extends BaseClientActivity {


    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;


    private String companyId;
    private String companyName;


    private List<TemplateBean> mTemplateBeanList = new ArrayList<>();

    private List<TemplateBean.Preson> mDataList;
    private CreateGroupOrganizationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_organization);
        ButterKnife.bind(this);
        setTitle("选择联系人");
        setLeftBack();
        setRightTitle("下一步");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroupOrganizationActivity.this, CreateGroupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) mAdapter.getSeletePerson());
                intent.putExtras(bundle);
                startActivity(intent);
                endTransaction(true);
            }
        });

        companyId = getIntent().getStringExtra("companyId");//单选 多选
        companyName = getIntent().getStringExtra("companyName");//单选 多选

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDataList = (List<TemplateBean.Preson>) bundle.getSerializable("list");
        }


        getData();
    }

    private void getData() {
        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE + companyId)
                .execute(new EanfangCallback<UserEntity>(this, true, UserEntity.class, true, (list) -> {
                    OrganizationBean bean = new OrganizationBean();
                    bean.setOrgName(companyName);
                    bean.setCompanyId(companyId);
                    bean.setStaff(list);
                    getSection(bean);

                }));
    }


    private void getSection(OrganizationBean bean) {
        //重新赋值变量   因为两个类 this 无法取到
        final List<TemplateBean.Preson> oldPersonList = mDataList;

        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
                .params("companyId", bean.getCompanyId())
                .execute(new EanfangCallback<SectionBean>(CreateGroupOrganizationActivity.this, true, SectionBean.class, true, (sectionBeanList) -> {

                    bean.setSectionBeanList(sectionBeanList);
                    OrganizationBean organizationBean = bean;

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
                                if (childrens.getStaff() != null) {
                                    for (SectionBean.ChildrenBean.StaffBean staffBean : childrens.getStaff()) {
                                        TemplateBean.Preson preson = new TemplateBean.Preson();


                                        preson.setId(staffBean.getAccId());
                                        preson.setOrgCode(sectionBean.getOrgCode());
                                        preson.setUserId(staffBean.getUserId());
                                        preson.setName(staffBean.getAccountEntity().getRealName());
                                        preson.setOrgName(childrens.getOrgName());
                                        preson.setProtraivat(staffBean.getAccountEntity().getAvatar());
                                        preson.setMobile(staffBean.getAccountEntity().getMobile());
                                        preson.setDepartmentId(staffBean.getDepartmentId());

                                        //循环已选的数据的 设置为cheked的
                                        if (oldPersonList != null && oldPersonList.contains(preson)) {
                                            preson.setChecked(true);
                                        }

                                        presonArrayList.add(preson);
                                    }

                                    templateBean.setPresons(presonArrayList);
                                    mTemplateBeanList.add(templateBean);
                                }
                            }
                        }

                        if (sectionBean.getStaff() != null) {
                            List<SectionBean.StaffBeanX> staffBeanXList = sectionBean.getStaff();

                            templateBean1.setOrgName(sectionBean.getOrgName());

                            for (SectionBean.StaffBeanX staffBeanX : staffBeanXList) {

                                TemplateBean.Preson preson = new TemplateBean.Preson();

                                //循环已选的数据的 设置为cheked的


                                preson.setId(staffBeanX.getAccId());
                                preson.setName(staffBeanX.getAccountEntity().getRealName());
                                preson.setProtraivat(staffBeanX.getAccountEntity().getAvatar());
                                preson.setMobile(staffBeanX.getAccountEntity().getMobile());
                                preson.setUserId(staffBeanX.getUserId());
                                preson.setDepartmentId(staffBeanX.getDepartmentId());
                                preson.setOrgName(sectionBean.getOrgName());
                                preson.setOrgCode(sectionBean.getOrgCode());

                                if (oldPersonList != null && oldPersonList.contains(preson)) {
                                    preson.setChecked(true);
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
                            preson.setName(userEntity.getAccountEntity().getRealName());
                            preson.setProtraivat(userEntity.getAccountEntity().getAvatar());
                            preson.setMobile(userEntity.getAccountEntity().getMobile());
                            preson.setUserId(String.valueOf(userEntity.getUserId()));
                            preson.setDepartmentId(String.valueOf(userEntity.getDepartmentId()));
                            preson.setOrgName(organizationBean.getOrgName());
                            preson.setOrgCode(organizationBean.getOrgCode());


                            if (oldPersonList != null && oldPersonList.contains(preson)) {
                                preson.setChecked(true);
                            }
                            presonArrayList2.add(preson);

                        }
                        templateBean2.setPresons(presonArrayList2);


                    }

                    if (templateBean2.getPresons() != null && templateBean2.getPresons().size() > 0) {
                        mTemplateBeanList.add(0, templateBean2);

                    }

                }));

        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CreateGroupOrganizationAdapter(R.layout.item_one_leve);

        mAdapter.bindToRecyclerView(recyclerView);
        mAdapter.setNewData(mTemplateBeanList);
        //添加上次选中的数据
        if (mDataList != null)
            mAdapter.getSeletePerson().addAll(mDataList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean bean = (TemplateBean) adapter.getData().get(position);
                if (view.getId() == R.id.cb_all_checked) {
                    ((CreateGroupOrganizationAdapter) adapter).checkedAll(position, bean.isChecked() == true ? false : true);
                } else if (view.getId() == R.id.rl_parent) {
                    ImageView imageView = view.findViewById(R.id.iv_select);
                    ((CreateGroupOrganizationAdapter) adapter).isShow(position, imageView);
                }
            }
        });
    }
}
