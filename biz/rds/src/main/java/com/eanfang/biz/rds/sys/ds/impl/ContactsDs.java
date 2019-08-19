package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.ContactsApi;
import com.eanfang.biz.rds.sys.ds.IContactsDs;

/**
 * 通讯录相关接口
 *
 * @author jornl
 * @date 2019年8月8日
 */
public class ContactsDs extends BaseRemoteDataSource implements IContactsDs {

    public ContactsDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void joinDefCompany(RequestCallback<LoginBean> callback) {
        execute(getService(ContactsApi.class).joinDefCompany(), callback);
    }

    @Override
    public void quitCompany(Long userId, RequestCallback<LoginBean> callback) {
        execute(getService(ContactsApi.class).quitCompany(userId), callback);
    }
}
