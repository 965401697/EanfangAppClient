package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.RoleBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.CompanySelectDs;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description
 */
public class CompanySelectRepo extends BaseRepo<CompanySelectDs> {

    public CompanySelectRepo(CompanySelectDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 选择组织
     */
    public MutableLiveData<List<RoleBean>> doGetRoleList() {
        MutableLiveData<List<RoleBean>> roleBeanMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getRoleType((val) -> {
            roleBeanMutableLiveData.setValue(val);
        });
        return roleBeanMutableLiveData;
    }

    /**
     * 选择员工
     */
    public MutableLiveData<List<SectionBean>> doGetStaffList(String companyId) {
        MutableLiveData<List<SectionBean>> staffMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getStaff(companyId, (val) -> {
            staffMutableLiveData.setValue(val);
        });
        return staffMutableLiveData;
    }

    /**
     * 添加第一步
     */
    public MutableLiveData<UserEntity> doSubmitFirst(UserEntity userEntity) {
        MutableLiveData<UserEntity> submitFirstMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doSubmitFirst(userEntity, (val) -> {
            submitFirstMutableLiveData.setValue(val);
        });
        return submitFirstMutableLiveData;
    }

    /**
     * 添加第二步
     */
    public MutableLiveData<Object> doSubmitSecond(String userId, List<String> roleIdList) {
        MutableLiveData<Object> submitSecondMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doSubmitSecond(userId,   roleIdList, (val) -> {
            submitSecondMutableLiveData.setValue(val);
        });
        return submitSecondMutableLiveData;
    }
}
