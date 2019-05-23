package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.MainDs;

public class MainRepo extends BaseRepo<MainDs> {
    public MainRepo(MainDs remoteDataSource) {
        super(remoteDataSource);
    }


    public MutableLiveData<BaseDataBean> getBaseData(String md5) {
        MutableLiveData<BaseDataBean> baseDataMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getBaseData(md5, baseDataMutableLiveData::setValue);
        return baseDataMutableLiveData;
    }

    public MutableLiveData<ConstAllBean> getConstData(String md5) {
        MutableLiveData<ConstAllBean> constAllMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getConstData(md5, constAllMutableLiveData::setValue);
        return constAllMutableLiveData;
    }

    public MutableLiveData<AccountEntity> getAccountInfo(String id) {
        MutableLiveData<AccountEntity> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getAccountInfo(id, mutableLiveData::setValue);
        return mutableLiveData;
    }
}
