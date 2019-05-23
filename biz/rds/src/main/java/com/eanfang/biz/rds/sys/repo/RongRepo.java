package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.RongTokenBean;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.RongDs;

public class RongRepo extends BaseRepo<RongDs> {


    public RongRepo(RongDs remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<RongTokenBean> getRongToken(Long userId) {
        MutableLiveData<RongTokenBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getRongToken(userId, mutableLiveData::setValue);
        return mutableLiveData;
    }

    public LiveData<GroupDetailBean> getGroupDetail(String id, boolean isUser) {
        MutableLiveData<GroupDetailBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getGroupDetail(id, isUser, mutableLiveData::setValue);
        return mutableLiveData;
    }
}
