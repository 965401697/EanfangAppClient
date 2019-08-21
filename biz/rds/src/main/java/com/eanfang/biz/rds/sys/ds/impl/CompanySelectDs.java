package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.ICompanySelectDs;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description
 */
public class CompanySelectDs extends BaseRemoteDataSource implements ICompanySelectDs {
    public CompanySelectDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getOrganization(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback) {

    }
}
