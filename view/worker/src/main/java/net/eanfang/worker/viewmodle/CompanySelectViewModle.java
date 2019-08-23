package net.eanfang.worker.viewmodle;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.adpater.TreeRecyclerType;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.OrganizationBean;
import com.eanfang.biz.model.bean.RoleBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.model.vo.CompanySelectVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.CompanySelectDs;
import com.eanfang.biz.rds.sys.repo.CompanySelectRepo;
import com.eanfang.ui.items.OrgOneLevelItem;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.ActivityAddStaffNextBinding;
import net.eanfang.worker.databinding.FragmentSelectOrganizationBinding;
import net.eanfang.worker.databinding.FragmentSelectRoleBinding;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description 企业管理添加员工
 */
public class CompanySelectViewModle extends BaseViewModel {
    @Setter
    @Getter
    private FragmentSelectOrganizationBinding fragmentSelectOrganizationBinding;
    @Setter
    @Getter
    private FragmentSelectRoleBinding fragmentSelectRoletypeBinding;
    @Setter
    @Getter
    private ActivityAddStaffNextBinding activityAddStaffNextBinding;
    @Getter
    private MutableLiveData<Object> submitSuccessData;

    private CompanySelectRepo companySelectRepo;

    private TreeRecyclerAdapter treeRecyclerAdapter;
    private CompanySelectVo companySelectVo;

    private Object mOldObj;
    private OrganizationBean mOrganizationBean;
    private SectionBean mSectionBean;
    private SectionBean.ChildrenBean mChildrenBean;

    /**
     * 总公司
     */
    private OrganizationBean mAllOrganizationBean;
    private List<OrganizationBean> organizationBeans = new ArrayList<>();

    private AolltRoleAdapter aolltRoleAdapter;

    public CompanySelectViewModle() {
        submitSuccessData = new MutableLiveData<>();
        companySelectVo = new CompanySelectVo();
        companySelectRepo = new CompanySelectRepo(new CompanySelectDs(this));

        mAllOrganizationBean = new OrganizationBean();
        mAllOrganizationBean.setOrgId(String.valueOf(WorkerApplication.get().getUser().getCompanyEntity().getOrgId()));
        mAllOrganizationBean.setOrgName(WorkerApplication.get().getUser().getCompanyEntity().getOrgName());
        mAllOrganizationBean.setFlag(2);
        mAllOrganizationBean.setAdd(true);
    }

    /**
     * 提交
     *
     * @param accId
     * @param mobile
     */
    public void doSubmit(String accId, String mobile) {
        if (companySelectVo.getOrganiaztionBean().get() == null) {
            showToast("部门不能为空");
            return;
        }
        if (companySelectVo.getRoleIdList() != null && companySelectVo.getRoleIdList().size() <= 0) {
            showToast("角色不能为空");
            return;
        }


        UserEntity userEntity = new UserEntity();

        if (mSectionBean != null) {
            userEntity.setDepartmentId(Long.parseLong(mSectionBean.getOrgId()));
        } else if (mChildrenBean != null) {
            userEntity.setDepartmentId(Long.parseLong(mChildrenBean.getOrgId()));
        } else {
            userEntity.setDepartmentId(Long.parseLong(mOrganizationBean.getOrgId()));
        }

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccId(Long.parseLong(accId));
        accountEntity.setMobile(mobile);
        userEntity.setAccountEntity(accountEntity);
        doSubmit(userEntity);

    }

    private void doSubmit(UserEntity userEntity) {
        companySelectRepo.doSubmitFirst(userEntity).observe(lifecycleOwner, user -> {
            doSubmitSecond(user.getUserId());
        });
    }

    private void doSubmitSecond(Long mUserId) {
        companySelectRepo.doSubmitSecond(String.valueOf(mUserId), companySelectVo.getRoleIdList()).observe(lifecycleOwner, object -> {
            submitSuccessData.setValue(object);
        });
    }

    /**
     * 获取员工
     */
    public void doGetStaff(String companyId) {
        organizationBeans.clear();
        companySelectRepo.doGetStaffList(companyId).observe(lifecycleOwner, staffBeanPageBean -> {
            for (SectionBean sectionBean : staffBeanPageBean) {
                sectionBean.setFlag(2);
                mAllOrganizationBean.setSectionBeanList(staffBeanPageBean);
            }
            organizationBeans.add(mAllOrganizationBean);

            treeRecyclerAdapter.setDatas(ItemHelperFactory.createTreeItemList(organizationBeans, OrgOneLevelItem.class, null));
            fragmentSelectOrganizationBinding.rvPersonalList.setAdapter(treeRecyclerAdapter);
        });
    }

    /**
     * 获取角色
     */
    public void doGetRoleData() {
        companySelectRepo.doGetRoleList().observe(lifecycleOwner, roleBeanPageBean -> {
            if (companySelectVo.getRoleNameList() != null) {
                for (RoleBean roleBean : roleBeanPageBean) {
                    if (companySelectVo.getRoleNameList().contains(roleBean.getRoleName())) {
                        roleBean.setChecked(true);
                        companySelectVo.getRoleIdList().add(roleBean.getRoleId());
                    }
                }
            }
            aolltRoleAdapter.setNewData(roleBeanPageBean);
        });
    }


    /**
     * 获取公司
     */
    public void initOrganiaztionListener(String mCompanyId) {
        treeRecyclerAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_EXPAND);
        fragmentSelectOrganizationBinding.rvPersonalList.setLayoutManager(new LinearLayoutManager(fragmentSelectOrganizationBinding.getRoot().getContext()));
        fragmentSelectOrganizationBinding.rvPersonalList.setItemAnimator(new DefaultItemAnimator());

        treeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewHolder viewHolder, int i) {

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
                companySelectVo.getOrganiaztionBean().set(mOldObj);
            }
        });
        doGetStaff(mCompanyId);
    }

    /**
     * 获取角色
     */
    public void initRoleTypeListener() {
        fragmentSelectRoletypeBinding.rvPersonalList.setLayoutManager(new LinearLayoutManager(fragmentSelectRoletypeBinding.getRoot().getContext()));
        aolltRoleAdapter = new AolltRoleAdapter(R.layout.item_role);
        aolltRoleAdapter.bindToRecyclerView(fragmentSelectRoletypeBinding.rvPersonalList);


        aolltRoleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RoleBean bean = (RoleBean) adapter.getData().get(position);

                if (bean.isChecked()) {
                    bean.setChecked(false);
                    companySelectVo.getRoleIdList().remove(bean.getRoleId());
                    companySelectVo.getRoleNameList().remove(bean.getRoleName());

                } else {
                    bean.setChecked(true);
                    companySelectVo.getRoleIdList().add(bean.getRoleId());
                    companySelectVo.getRoleNameList().add(bean.getRoleName());
                }
                adapter.notifyDataSetChanged();
            }
        });

        doGetRoleData();
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
