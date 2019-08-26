package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.SecurityCompanyDetailDs;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
public class SecurityCompanyDetailRepo extends BaseRepo<SecurityCompanyDetailDs> {

    public SecurityCompanyDetailRepo(SecurityCompanyDetailDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 获取详情
     */
    public MutableLiveData<SecurityCompanyDetailBean> doGetCompanyDetail(String mOrgId) {
        MutableLiveData<SecurityCompanyDetailBean> companyDetailMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getCompanyDetail(mOrgId,(val) -> {
            companyDetailMutableLiveData.setValue(val);
        });
        return companyDetailMutableLiveData;
    }
}
