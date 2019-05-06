package com.eanfang.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.kit.bean.OSSBean;
import com.eanfang.rds.base.BaseRepo;
import com.eanfang.rds.sys.ds.OSSDs;

public class OSSRepo extends BaseRepo<OSSDs> {

    public OSSRepo(OSSDs remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<OSSBean> getToken() {
        MutableLiveData<OSSBean> ossMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getToken(ossMutableLiveData::setValue);
        return ossMutableLiveData;
    }
}
