package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.RoleBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.SelectCompanyApi;
import com.eanfang.biz.rds.sys.ds.ICompanySelectDs;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description 添加人员
 */
public class CompanySelectDs extends BaseRemoteDataSource implements ICompanySelectDs {
    public CompanySelectDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }


    @Override
    public void getRoleType(RequestCallback<List<RoleBean>> callback) {
        execute(getService(SelectCompanyApi.class).getRoleTypeList(), callback);
//                new CacheModel().setCacheStrategy(CacheStrategy.firstRemote()).setClazz(SelectCompanyApi.class).setMethod("getRoleType"));
    }


    @Override
    public void getStaff(String companyId, RequestCallback<List<SectionBean>> callback) {
        execute(getService(SelectCompanyApi.class).getStaffList(companyId), callback);
//                new CacheModel().setCacheStrategy(CacheStrategy.firstRemote()).setClazz(SelectCompanyApi.class).setMethod("getRoleType"));
    }

    @Override
    public void doSubmitFirst(UserEntity userEntity, RequestCallback<UserEntity> callback) {
        execute(getService(SelectCompanyApi.class).doSubmitFirst(userEntity), callback);
    }

    @Override
    public void doSubmitSecond(String userId, List<String> roleIdList, RequestCallback<Object> callback) {
        execute(getService(SelectCompanyApi.class).doSubmitSecond(userId, roleIdList), callback);
    }

}
