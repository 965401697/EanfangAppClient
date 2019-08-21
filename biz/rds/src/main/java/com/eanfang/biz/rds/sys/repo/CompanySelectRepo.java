package com.eanfang.biz.rds.sys.repo;

import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.CompanySelectDs;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description
 */
public class CompanySelectRepo extends BaseRepo<CompanySelectDs> {

    public CompanySelectRepo(CompanySelectDs remoteDataSource) {
        super(remoteDataSource);
    }
}
