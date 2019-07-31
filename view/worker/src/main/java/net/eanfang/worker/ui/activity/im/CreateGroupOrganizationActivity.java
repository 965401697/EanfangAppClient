package net.eanfang.worker.ui.activity.im;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.biz.model.bean.OrganizationBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGroupOrganizationActivity extends BaseWorkerActivity {


    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;


    private String companyId;
    private String companyName;
    private String companyOrgCode;


    private List<TemplateBean> mTemplateBeanList = new ArrayList<>();

    private List<TemplateBean.Preson> mDataList;
    private CreateGroupOrganizationAdapter mAdapter;
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_create_group_organization);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        startTransaction(true);
        setTitle("选择联系人");
        setLeftBack();
        String isFromOA = getIntent().getStringExtra("isFrom");
        if (TextUtils.isEmpty(isFromOA)) {
            setRightTitle("下一步");
        } else {
            setRightTitle("确定");
        }
        companyId = getIntent().getStringExtra("companyId");
        companyName = getIntent().getStringExtra("companyName");
        companyOrgCode = getIntent().getStringExtra("companyOrgCode");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDataList = (List<TemplateBean.Preson>) bundle.getSerializable("list");
        }

        mLoadingDialog = LoadKit.dialog(this);
        getData();
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(isFromOA)) {
                    Intent intent = new Intent(CreateGroupOrganizationActivity.this, CreateGroupActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) mAdapter.getSeletePerson());
                    intent.putExtras(bundle);
                    intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                    intent.putExtra("imgKey", getIntent().getStringExtra("imgKey"));
                    intent.putExtra("locationPortrait", getIntent().getStringExtra("locationPortrait"));

                    startActivity(intent);
                } else {
                    EventBus.getDefault().post(mAdapter.getSeletePerson());
                }
                endTransaction(true);
            }
        });
    }

    private void getData() {
        mLoadingDialog.show();
        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE + companyId)
                .execute(new EanfangCallback<UserEntity>(this, false, UserEntity.class, true, (list) -> {
                    OrganizationBean bean = new OrganizationBean();
                    bean.setOrgName(companyName);
                    bean.setCompanyId(companyId);
                    bean.setOrgCode(companyOrgCode);
                    bean.setStaff(list);
                    getSection(bean);

                }));
    }


    private void getSection(OrganizationBean bean) {
        //重新赋值变量   因为两个类 this 无法取到
        final List<TemplateBean.Preson> oldPersonList = mDataList;

        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST_TREE_ALL)
                .params("companyId", bean.getCompanyId())
                .execute(new EanfangCallback<SectionBean>(CreateGroupOrganizationActivity.this, false, SectionBean.class, true, new EanfangCallback.ISuccessArray<SectionBean>() {
                    @Override
                    public void success(List<SectionBean> sectionBeanList) {
                        bean.setSectionBeanList(sectionBeanList);
                        OrganizationBean organizationBean = bean;

                        TemplateBean templateBean2 = new TemplateBean();
                        List<TemplateBean.Preson> presonArrayList2 = new ArrayList<>();

                        setTitle(organizationBean.getOrgName());
                        for (SectionBean sectionBean : organizationBean.getSectionBeanList()) {//循环一个公司全部 部门和员工

                            TemplateBean templateBean1 = new TemplateBean();
                            List<TemplateBean.Preson> presonArrayList1 = new ArrayList<>();


                            if (sectionBean.getChildren() != null) {
                                for (SectionBean.ChildrenBean childrens : sectionBean.getChildren()) {
                                    List<TemplateBean.Preson> presonArrayList = new ArrayList<>();
                                    TemplateBean templateBean = new TemplateBean();
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
                                if (templateBean1.getPresons() != null && templateBean1.getPresons().size() > 0) {
                                    mTemplateBeanList.add(templateBean1);
                                }
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

                        mLoadingDialog.dismiss();
                    }
                }) {

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onError(String message) {
                        super.onError(message);
                        mLoadingDialog.dismiss();
                    }
                });

        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CreateGroupOrganizationAdapter(R.layout.item_one_leve, new CreateGroupOrganizationAdapter.SetAutoCheckedParentListener() {
            @Override
            public void autoCheckedParentListener(CreateGroupOrganizationAdapter adapter, int position, List<TemplateBean.Preson> list) {
                if (adapter.getSeletePerson().containsAll(list)) {
                    adapter.getData().get(position).setChecked(true);
                } else {
                    adapter.getData().get(position).setChecked(false);
                }

                adapter.notifyItemChanged(position);
            }
        });

        mAdapter.bindToRecyclerView(recyclerView);
        mAdapter.setNewData(mTemplateBeanList);
        //添加上次选中的数据
        if (mDataList != null) {
            mAdapter.getSeletePerson().addAll(mDataList);
        }
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean bean = (TemplateBean) adapter.getData().get(position);
                if (view.getId() == R.id.cb_all_checked) {
                    ((CreateGroupOrganizationAdapter) adapter).checkedAll(position, bean.isChecked() != true);
                } else if (view.getId() == R.id.rl_parent) {
                    ImageView imageView = view.findViewById(R.id.iv_select);
                    RecyclerView recyclerView = view.findViewById(R.id.recycler_view_group);
                    ((CreateGroupOrganizationAdapter) adapter).isShow(position, imageView);
                }
            }
        });

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroupOrganizationActivity.this, SearchPersonCompanyActivity.class);
                intent.putExtra("data", (Serializable) mTemplateBeanList);

                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) mAdapter.getSeletePerson());
                intent.putExtras(bundle);
                intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                intent.putExtra("imgKey", getIntent().getStringExtra("imgKey"));
                intent.putExtra("locationPortrait", getIntent().getStringExtra("locationPortrait"));
                startActivity(intent);
            }
        });

    }
}
