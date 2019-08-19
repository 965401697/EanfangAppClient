package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.ContactsDs;

public class ContactsRepo extends BaseRepo<ContactsDs> {
    public ContactsRepo(ContactsDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 加入默认公司
     *
     * @return MutableLiveData<LoginBean>
     */
    public MutableLiveData<LoginBean> joinDefCompany() {
        MutableLiveData<LoginBean> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.joinDefCompany(loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }

    /**
     * 退出组织
     *
     * @param userId userId
     * @return MutableLiveData<LoginBean>
     */
    public MutableLiveData<LoginBean> quitCompany(Long userId) {
        MutableLiveData<LoginBean> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.quitCompany(userId, loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }
}
