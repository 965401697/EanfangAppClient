package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.SecurityCompanyDetailApi;
import com.eanfang.biz.rds.sys.ds.ISecurityCompanyDetailDs;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
public class SecurityCompanyDetailDs extends BaseRemoteDataSource implements ISecurityCompanyDetailDs {

    public SecurityCompanyDetailDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getCompanyDetail(String mOrgId, RequestCallback<SecurityCompanyDetailBean> callback) {
        execute(getService(SecurityCompanyDetailApi.class).getCompanyDetail(mOrgId), callback);
    }
}
